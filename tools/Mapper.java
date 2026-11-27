import org.objectweb.asm.*;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.tree.analysis.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.jar.*;
import java.util.zip.*;

/**
 * Pass 2 of the Onyx deobfuscation pipeline: naming + remapping.
 *
 * Usage: java -cp <asm jars> tools/Mapper.java <clean.jar> <libDir> <mc.jar> <classes.txt> <members.txt> <outDir>
 *
 * Produces in outDir:
 *   onyx.tiny            Tiny v2 mappings (namespaces: obf -> named), classes + fields + methods
 *   classes.csv          obf,named class table
 *   onyx-named.jar       the cleaned client jar, remapped
 *   mc-named.jar         rt/libs/mc.jar with its client hook references remapped
 *   report.txt           unresolved/placeholder names and statistics
 */
public class Mapper {
    static final String ROOT = "client/onyx/";
    static final Map<String, ClassNode> onyx = new LinkedHashMap<>();
    static final Map<String, Lib> libs = new HashMap<>();
    static final List<String> report = new ArrayList<>();

    record Lib(String name, String superName, String[] interfaces, Set<String> methods, Set<String> fields, int access) {}

    // ============================================================ main
    public static void main(String[] a) throws Exception {
        Path cleanJar = Paths.get(a[0]), libDir = Paths.get(a[1]), mcJar = Paths.get(a[2]);
        Path classesTxt = Paths.get(a[3]), membersTxt = Paths.get(a[4]), out = Paths.get(a[5]);
        Files.createDirectories(out);
        readOnyx(cleanJar);
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(libDir, "*.jar")) {
            for (Path p : ds) readLib(p);
        }
        // the stub left behind by the string obfuscator is dropped when nothing references it
        dropUnreferencedStub(mcJar);

        Map<String, String[]> manualClasses = parseClasses(classesTxt);
        Map<String, String> manualMembers = parseMembers(membersTxt);

        nameClasses(manualClasses);
        nameFields(manualMembers);
        nameMethods(manualMembers);

        fixFlagsAndOrder();
        writeTiny(out.resolve("onyx.tiny"));
        writeCsv(out.resolve("classes.csv"));
        remapJar(onyx.values(), out.resolve("onyx-named.jar"), true);
        remapMc(mcJar, out.resolve("mc-named.jar"));
        Files.write(out.resolve("report.txt"), report);
        System.out.println(String.join("\n", report.subList(0, Math.min(report.size(), 60))));
        System.out.println("... full report in " + out.resolve("report.txt"));
    }

    // ============================================================ loading
    static void readOnyx(Path jar) throws IOException {
        try (ZipInputStream zin = new ZipInputStream(Files.newInputStream(jar))) {
            for (ZipEntry e; (e = zin.getNextEntry()) != null; ) {
                if (!e.getName().endsWith(".class")) continue;
                byte[] b = zin.readAllBytes();
                ClassNode cn = new ClassNode();
                new ClassReader(b).accept(cn, 0);
                onyx.put(cn.name, cn);
                rawConstantPools.put(cn.name, new String(b, java.nio.charset.StandardCharsets.ISO_8859_1));
            }
        }
    }

    static void readLib(Path jar) throws IOException {
        try (ZipInputStream zin = new ZipInputStream(Files.newInputStream(jar))) {
            for (ZipEntry e; (e = zin.getNextEntry()) != null; ) {
                if (!e.getName().endsWith(".class")) continue;
                Lib l = libFrom(new ClassReader(zin.readAllBytes()));
                libs.putIfAbsent(l.name, l);
            }
        }
    }

    static Lib libFrom(ClassReader cr) {
        Set<String> ms = new HashSet<>(), fs = new HashSet<>();
        cr.accept(new ClassVisitor(Opcodes.ASM9) {
            @Override public MethodVisitor visitMethod(int acc, String n, String d, String s, String[] ex) { ms.add(acc + ":" + n + d); return null; }
            @Override public FieldVisitor visitField(int acc, String n, String d, String s, Object v) { fs.add(n); return null; }
        }, ClassReader.SKIP_CODE);
        return new Lib(cr.getClassName(), cr.getSuperName(), cr.getInterfaces(), ms, fs, cr.getAccess());
    }

    static Lib lib(String name) {
        Lib l = libs.get(name);
        if (l != null || libs.containsKey(name)) return l;
        try (InputStream is = ClassLoader.getSystemResourceAsStream(name + ".class")) {
            l = is == null ? null : libFrom(new ClassReader(is));
        } catch (IOException e) { l = null; }
        libs.put(name, l);
        return l;
    }

    static String superOf(String n) {
        ClassNode cn = onyx.get(n);
        if (cn != null) return cn.superName;
        Lib l = lib(n);
        return l == null ? null : l.superName;
    }

    static List<String> directSupers(String n) {
        List<String> r = new ArrayList<>();
        ClassNode cn = onyx.get(n);
        if (cn != null) {
            if (cn.superName != null) r.add(cn.superName);
            r.addAll(cn.interfaces);
        } else {
            Lib l = lib(n);
            if (l != null) {
                if (l.superName != null) r.add(l.superName);
                r.addAll(Arrays.asList(l.interfaces));
            }
        }
        return r;
    }

    static final Map<String, Set<String>> ancestorCache = new HashMap<>();

    /** All transitive supertypes (classes and interfaces), excluding the class itself. */
    static Set<String> ancestors(String n) {
        Set<String> c = ancestorCache.get(n);
        if (c != null) return c;
        Set<String> s = new LinkedHashSet<>();
        Deque<String> q = new ArrayDeque<>(directSupers(n));
        while (!q.isEmpty()) {
            String x = q.poll();
            if (s.add(x)) q.addAll(directSupers(x));
        }
        ancestorCache.put(n, s);
        return s;
    }

    static boolean isSubclass(String n, String of) { return n.equals(of) || ancestors(n).contains(of); }

    static final Map<String, Set<String>> descendants = new HashMap<>();

    static Set<String> descendants(String n) {
        if (descendants.isEmpty())
            for (ClassNode cn : onyx.values())
                for (String anc : ancestors(cn.name)) descendants.computeIfAbsent(anc, k -> new HashSet<>()).add(cn.name);
        return descendants.getOrDefault(n, Set.of());
    }

    static final Map<String, String> rawConstantPools = new HashMap<>(); // class -> bytes as latin-1 (onyx + hooked mc classes)

    /** Drops the empty helper class left by the string obfuscator; any class whose name occurs elsewhere is kept. */
    static void dropUnreferencedStub(Path mcJar) throws IOException {
        try (ZipInputStream zin = new ZipInputStream(Files.newInputStream(mcJar))) {
            for (ZipEntry e; (e = zin.getNextEntry()) != null; ) {
                if (!e.getName().endsWith(".class")) continue;
                String s = new String(zin.readAllBytes(), java.nio.charset.StandardCharsets.ISO_8859_1);
                if (s.contains(ROOT)) rawConstantPools.put("mc:" + e.getName(), s);
            }
        }
        for (Iterator<ClassNode> it = onyx.values().iterator(); it.hasNext(); ) {
            ClassNode cn = it.next();
            boolean onlyCtor = cn.fields.isEmpty() && cn.methods.stream().allMatch(m -> m.name.equals("<init>"));
            if (!onlyCtor || cn.superName == null || !cn.superName.equals("java/lang/Object") || !cn.interfaces.isEmpty()) continue;
            boolean referenced = false;
            for (Map.Entry<String, String> e : rawConstantPools.entrySet())
                if (!e.getKey().equals(cn.name) && e.getValue().contains(cn.name)) { referenced = true; break; }
            if (!referenced) { it.remove(); report.add("dropped unreferenced stub class " + cn.name); }
        }
    }

    // ============================================================ manual map parsing
    static Map<String, String[]> parseClasses(Path p) throws IOException {
        Map<String, String[]> m = new HashMap<>();
        for (String line : Files.readAllLines(p)) {
            line = line.strip();
            if (line.isEmpty() || line.startsWith("#")) continue;
            String[] t = line.split("\\s+");
            m.put(t[0], new String[]{t[1], t.length > 2 && t[2].equals("?") ? "?" : ""});
        }
        return m;
    }

    /** members.txt: "<owner obf> <name obf> <desc obf> <new name>" (desc "-" for fields: any desc) */
    static Map<String, String> parseMembers(Path p) throws IOException {
        Map<String, String> m = new HashMap<>();
        if (!Files.exists(p)) return m;
        for (String line : Files.readAllLines(p)) {
            line = line.strip();
            if (line.isEmpty() || line.startsWith("#")) continue;
            String[] t = line.split("\\s+");
            m.put(t[0] + "." + t[1] + (t[2].equals("-") ? "" : t[2]), t[3]);
        }
        return m;
    }

    // ============================================================ analysis helpers
    /** SourceInterpreter that keeps the original producer through dup/swap/load/store copies. */
    static class CopyKeepingInterpreter extends SourceInterpreter {
        CopyKeepingInterpreter() { super(Opcodes.ASM9); }
        @Override public SourceValue copyOperation(AbstractInsnNode insn, SourceValue v) { return v; }
    }

    static final Map<MethodNode, Frame<SourceValue>[]> frameCache = new IdentityHashMap<>();

    static Frame<SourceValue>[] frames(ClassNode cn, MethodNode mn) {
        return frameCache.computeIfAbsent(mn, k -> {
            try { return new Analyzer<>(new CopyKeepingInterpreter()).analyze(cn.name, mn); }
            catch (AnalyzerException e) { return null; }
        });
    }

    /** Stack values consumed by a method call (receiver first for instance calls). */
    static List<SourceValue> callArgs(ClassNode cn, MethodNode mn, MethodInsnNode call) {
        Frame<SourceValue>[] fr = frames(cn, mn);
        if (fr == null) return null;
        Frame<SourceValue> f = fr[mn.instructions.indexOf(call)];
        if (f == null) return null;
        int n = Type.getArgumentTypes(call.desc).length + (call.getOpcode() == Opcodes.INVOKESTATIC ? 0 : 1);
        List<SourceValue> r = new ArrayList<>();
        for (int i = f.getStackSize() - n; i < f.getStackSize(); i++) r.add(f.getStack(i));
        return r;
    }

    static SourceValue stackTop(ClassNode cn, MethodNode mn, AbstractInsnNode insn) {
        Frame<SourceValue>[] fr = frames(cn, mn);
        if (fr == null) return null;
        Frame<SourceValue> f = fr[mn.instructions.indexOf(insn)];
        return f == null || f.getStackSize() == 0 ? null : f.getStack(f.getStackSize() - 1);
    }

    static AbstractInsnNode single(SourceValue v) {
        return v != null && v.insns.size() == 1 ? v.insns.iterator().next() : null;
    }

    static String ldcString(SourceValue v) {
        return single(v) instanceof LdcInsnNode l && l.cst instanceof String s ? s : null;
    }

    /** Follows fluent builder chains ("new X(..).a().b()") back to the NEW instruction. */
    static TypeInsnNode resolveNew(ClassNode cn, MethodNode mn, SourceValue v, int depth) {
        AbstractInsnNode s = single(v);
        if (s instanceof TypeInsnNode t && t.getOpcode() == Opcodes.NEW) return t;
        if (depth < 12 && s instanceof MethodInsnNode mi && mi.getOpcode() != Opcodes.INVOKESTATIC
                && Type.getReturnType(mi.desc).getSort() == Type.OBJECT) {
            List<SourceValue> args = callArgs(cn, mn, mi);
            if (args != null && !args.isEmpty()) return resolveNew(cn, mn, args.get(0), depth + 1);
        }
        if (depth < 12 && s instanceof TypeInsnNode t && t.getOpcode() == Opcodes.CHECKCAST)
            return resolveNew(cn, mn, stackTop(cn, mn, t), depth + 1);
        return null;
    }

    /** The constructor call that initialises the object created by a NEW instruction. */
    static MethodInsnNode ctorFor(ClassNode cn, MethodNode mn, TypeInsnNode nw) {
        for (AbstractInsnNode i = nw.getNext(); i != null; i = i.getNext()) {
            if (i instanceof MethodInsnNode mi && mi.getOpcode() == Opcodes.INVOKESPECIAL && mi.name.equals("<init>") && mi.owner.equals(nw.desc)) {
                List<SourceValue> args = callArgs(cn, mn, mi);
                if (args != null && single(args.get(0)) == nw) return mi;
            }
        }
        return null;
    }

    static String firstStringArg(ClassNode cn, MethodNode mn, MethodInsnNode call) {
        List<SourceValue> args = callArgs(cn, mn, call);
        if (args == null) return null;
        Type[] types = Type.getArgumentTypes(call.desc);
        int off = call.getOpcode() == Opcodes.INVOKESTATIC ? 0 : 1;
        for (int i = 0; i < types.length; i++)
            if (types[i].getDescriptor().equals("Ljava/lang/String;")) return ldcString(args.get(i + off));
        return null;
    }

    /** The label passed to the superclass constructor (e.g. Module name, setting-group label). */
    static String superCtorLabel(ClassNode cn) {
        for (MethodNode mn : cn.methods) {
            if (!mn.name.equals("<init>")) continue;
            for (AbstractInsnNode i : mn.instructions)
                if (i instanceof MethodInsnNode mi && mi.getOpcode() == Opcodes.INVOKESPECIAL && mi.name.equals("<init>")
                        && mi.owner.equals(cn.superName)) {
                    String s = firstStringArg(cn, mn, mi);
                    if (s != null) return s;
                }
        }
        return null;
    }

    // ============================================================ class naming
    static final Map<String, String> cmap = new HashMap<>();
    static final Set<String> usedLower = new HashSet<>();
    static final Map<String, Integer> anonCounter = new HashMap<>();
    static final Map<String, String> enumConstNames = new HashMap<>(); // "owner.field" -> constant name

    static String outerOf(ClassNode cn) {
        for (InnerClassNode ic : cn.innerClasses)
            if (ic.name.equals(cn.name)) return ic.outerName != null ? ic.outerName : cn.outerClass;
        return null;
    }

    static boolean isAnonymous(ClassNode cn) {
        for (InnerClassNode ic : cn.innerClasses)
            if (ic.name.equals(cn.name)) return ic.outerName == null && ic.innerName == null;
        return false;
    }

    static void nameClasses(Map<String, String[]> manual) {
        for (ClassNode cn : onyx.values()) collectEnumConstants(cn);
        for (ClassNode cn : onyx.values()) resolveClass(cn.name, manual);
        // sanity: no case-insensitive clash between class files and package directories
        Set<String> dirs = new HashSet<>();
        for (String n : cmap.values()) {
            String[] parts = n.split("/");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < parts.length - 1; i++) { sb.append(parts[i]).append('/'); dirs.add(sb.toString().toLowerCase(Locale.ROOT)); }
        }
        for (String n : cmap.values()) if (dirs.contains((n + "/").toLowerCase(Locale.ROOT))) report.add("WARNING class name equals a package: " + n);
    }

    static String resolveClass(String obf, Map<String, String[]> manual) {
        String done = cmap.get(obf);
        if (done != null) return done;
        ClassNode cn = onyx.get(obf);
        if (cn == null) return obf;
        String outer = outerOf(cn);
        String result;
        String[] man = manual.get(obf);
        if (outer != null && onyx.containsKey(outer)) {
            String o = resolveClass(outer, manual);
            String simple;
            if (isAnonymous(cn) || (man != null && man[0].matches("\\$\\d+"))) {
                simple = String.valueOf(anonCounter.merge(o, 1, Integer::sum));
            } else if (man != null) {
                simple = man[0].startsWith("$") ? man[0].substring(1) : man[0].substring(man[0].lastIndexOf('/') + 1);
                if (!man[0].startsWith("$")) report.add("note: " + obf + " is nested in " + outer + "; kept nested as " + simple);
            } else {
                simple = autoNestedName(cn);
            }
            result = unique(o + "$" + simple);
        } else if (man != null && !man[0].startsWith("$")) {
            result = unique(man[0]);
        } else if (isSubclass(obf, "client/onyx/r/K") && !obf.equals("client/onyx/r/K")) {
            result = unique(moduleName(cn));
        } else {
            String auto = autoTopLevel(cn);
            result = unique(auto);
            report.add("placeholder class name: " + obf + " -> " + result);
        }
        cmap.put(obf, result);
        return result;
    }

    static String unique(String n) {
        String cand = n;
        for (int i = 2; usedLower.contains(cand.toLowerCase(Locale.ROOT)); i++) cand = n + i;
        usedLower.add(cand.toLowerCase(Locale.ROOT));
        return cand;
    }

    static String pascal(String label) {
        StringBuilder sb = new StringBuilder();
        boolean up = true;
        for (char c : label.toCharArray()) {
            if (Character.isLetterOrDigit(c) && c < 128) { sb.append(up ? Character.toUpperCase(c) : c); up = false; }
            else up = true;
        }
        if (sb.length() == 0) return "Unnamed";
        if (Character.isDigit(sb.charAt(0))) sb.insert(0, '_');
        return sb.toString();
    }

    static String camel(String label) {
        String p = pascal(label);
        if (p.startsWith("_")) return p;
        // keep acronyms readable: "FOV" -> "fov", "ESPColor" -> "espColor"
        int k = 0;
        while (k < p.length() && Character.isUpperCase(p.charAt(k))) k++;
        if (k > 1 && k < p.length()) k--;
        if (k == 0) k = 1;
        String r = p.substring(0, k).toLowerCase(Locale.ROOT) + p.substring(k);
        return JAVA_KEYWORDS.contains(r) ? r + "_" : r;
    }

    static final Set<String> JAVA_KEYWORDS = Set.of("abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class",
            "const", "continue", "default", "do", "double", "else", "enum", "extends", "final", "finally", "float", "for", "goto", "if",
            "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void",
            "volatile", "while", "true", "false", "null", "var", "record", "yield");

    static String moduleName(ClassNode cn) {
        String label = null, category = "misc";
        for (MethodNode mn : cn.methods) {
            if (!mn.name.equals("<init>")) continue;
            for (AbstractInsnNode i : mn.instructions)
                if (i instanceof MethodInsnNode mi && mi.getOpcode() == Opcodes.INVOKESPECIAL && mi.name.equals("<init>") && mi.owner.equals(cn.superName)) {
                    List<SourceValue> args = callArgs(cn, mn, mi);
                    if (args == null) continue;
                    for (SourceValue v : args) {
                        String s = ldcString(v);
                        if (s != null && label == null) label = s;
                        if (single(v) instanceof FieldInsnNode f && f.getOpcode() == Opcodes.GETSTATIC) {
                            String c = enumConstNames.get(f.owner + "." + f.name);
                            if (c != null) category = c.toLowerCase(Locale.ROOT);
                        }
                    }
                }
        }
        if (label == null) { report.add("module without name label: " + cn.name); label = "Module_" + cn.name.replace('/', '_'); }
        String n = pascal(label);
        if (n.equals("ArrayList")) n = "ModuleList";
        return ROOT + "module/" + category + "/" + n;
    }

    static String autoNestedName(ClassNode cn) {
        if (isSubclass(cn.name, "client/onyx/d/J")) { // a setting / group / mode
            String label = superCtorLabel(cn);
            String suffix = isSubclass(cn.name, "client/onyx/d/l") ? "Mode" : "Settings";
            if (label != null) return pascal(label) + suffix;
            return suffix;
        }
        if ((cn.access & Opcodes.ACC_ENUM) != 0) return "Kind";
        if ("java/lang/Record".equals(cn.superName)) return "Data";
        if ((cn.access & Opcodes.ACC_INTERFACE) != 0) return "Callback";
        return "Inner";
    }

    static String autoTopLevel(ClassNode cn) {
        String rel = cn.name.substring(ROOT.length());
        String pkg = rel.contains("/") ? rel.substring(0, rel.lastIndexOf('/')) : "";
        return ROOT + "unmapped/" + (pkg.isEmpty() ? "" : pkg.replace('/', '_') + "_") + "Class_" + rel.substring(rel.lastIndexOf('/') + 1);
    }

    /** Enum constants: field -> name passed to the Enum constructor in <clinit>. */
    static void collectEnumConstants(ClassNode cn) {
        if ((cn.access & Opcodes.ACC_ENUM) == 0) return;
        for (MethodNode mn : cn.methods) {
            if (!mn.name.equals("<clinit>")) continue;
            for (AbstractInsnNode i : mn.instructions) {
                if (!(i instanceof FieldInsnNode f) || f.getOpcode() != Opcodes.PUTSTATIC || !f.owner.equals(cn.name)) continue;
                if (!f.desc.equals("L" + cn.name + ";")) continue;
                TypeInsnNode nw = resolveNew(cn, mn, stackTop(cn, mn, f), 0);
                if (nw == null) continue;
                MethodInsnNode ctor = ctorFor(cn, mn, nw);
                if (ctor == null) continue;
                String s = firstStringArg(cn, mn, ctor);
                if (s != null) enumConstNames.put(cn.name + "." + f.name, s);
            }
        }
    }

    // ============================================================ field naming
    static final Map<String, String> fmap = new HashMap<>(); // "owner.name" -> new (fields unique per class by name in obf? no: by name+desc)

    static String fkey(String owner, String name, String desc) { return owner + "." + name + ":" + desc; }

    static void nameFields(Map<String, String> manual) {
        List<ClassNode> ordered = new ArrayList<>(onyx.values());
        ordered.sort(Comparator.comparingInt(c -> ancestors(c.name).size()));
        for (ClassNode cn : ordered) {
            Map<String, String> wanted = new LinkedHashMap<>();
            // settings: PUTFIELD/PUTSTATIC of a value built by "new SomeSetting("Label", ...)"
            for (MethodNode mn : cn.methods) {
                if (!mn.name.equals("<init>") && !mn.name.equals("<clinit>")) continue;
                for (AbstractInsnNode i : mn.instructions) {
                    if (!(i instanceof FieldInsnNode f) || !f.owner.equals(cn.name)) continue;
                    if (f.getOpcode() != Opcodes.PUTFIELD && f.getOpcode() != Opcodes.PUTSTATIC) continue;
                    String key = fkey(cn.name, f.name, f.desc);
                    if (wanted.containsKey(key)) continue;
                    if (enumConstNames.containsKey(cn.name + "." + f.name) && f.desc.equals("L" + cn.name + ";")) {
                        wanted.put(key, enumConstNames.get(cn.name + "." + f.name));
                        continue;
                    }
                    Type t = Type.getType(f.desc);
                    if (t.getSort() != Type.OBJECT || !onyx.containsKey(t.getInternalName())) continue;
                    if (!isSubclass(t.getInternalName(), "client/onyx/d/J")) continue;
                    TypeInsnNode nw = resolveNew(cn, mn, stackTop(cn, mn, f), 0);
                    if (nw == null) continue;
                    MethodInsnNode ctor = ctorFor(cn, mn, nw);
                    String label = ctor == null ? null : firstStringArg(cn, mn, ctor);
                    if (label == null) {
                        String mapped = cmap.getOrDefault(nw.desc, nw.desc);
                        label = mapped.substring(Math.max(mapped.lastIndexOf('/'), mapped.lastIndexOf('$')) + 1);
                    }
                    wanted.put(key, camel(label));
                }
            }
            Set<String> taken = new HashSet<>();
            for (String anc : ancestors(cn.name)) {
                ClassNode ac = onyx.get(anc);
                if (ac != null) for (FieldNode af : ac.fields) taken.add(fmap.getOrDefault(fkey(ac.name, af.name, af.desc), af.name));
            }
            Map<String, Integer> typeCount = new HashMap<>();
            // manual + derived names first, then type-based names
            List<FieldNode> rest = new ArrayList<>();
            for (FieldNode f : cn.fields) {
                String key = fkey(cn.name, f.name, f.desc);
                String m = manual.get(cn.name + "." + f.name);
                if (m == null) m = manual.get(cn.name + "." + f.name + f.desc);
                if (m == null) m = wanted.get(key);
                if (m == null) m = specialFieldName(cn, f);
                if (m != null) fmap.put(key, claim(m, taken));
                else rest.add(f);
            }
            for (FieldNode f : rest) {
                String base = typeFieldName(cn, f);
                int n = typeCount.merge(base, 1, Integer::sum);
                fmap.put(fkey(cn.name, f.name, f.desc), claim(n == 1 && !base.matches(".*\\d") ? base : base + n, taken));
            }
        }
    }

    static String claim(String n, Set<String> taken) {
        String c = n;
        for (int i = 2; taken.contains(c); i++) c = n + i;
        taken.add(c);
        return c;
    }

    static String specialFieldName(ClassNode cn, FieldNode f) {
        boolean stat = (f.access & Opcodes.ACC_STATIC) != 0;
        if (f.desc.equals("[L" + cn.name + ";") && (cn.access & Opcodes.ACC_ENUM) != 0 && stat) return "$VALUES";
        if (f.desc.equals("Lnet/minecraft/client/Minecraft;")) return "mc";
        if (f.desc.equals("Lorg/apache/logging/log4j/Logger;") && stat) return "LOGGER";
        if (f.desc.equals("Lmeteordevelopment/orbit/IEventBus;") && stat) return "EVENT_BUS";
        if (f.desc.equals("L" + cn.name + ";") && stat && (cn.access & Opcodes.ACC_ENUM) == 0) return "INSTANCE";
        if (f.name.startsWith("$SwitchMap$")) return f.name;
        return null;
    }

    static String typeFieldName(ClassNode cn, FieldNode f) {
        boolean constant = (f.access & (Opcodes.ACC_STATIC | Opcodes.ACC_FINAL)) == (Opcodes.ACC_STATIC | Opcodes.ACC_FINAL);
        Type t = Type.getType(f.desc);
        String base = typeWord(t);
        if (constant && f.value instanceof Number num) {
            // compile-time constants are named after their value: FLOAT_0_45, INT_96, LONG_450 ...
            String v = num.toString().replace("-", "N").replace('.', '_').replace("E", "E").replaceAll("[^A-Za-z0-9_]", "");
            return t.getClassName().toUpperCase(Locale.ROOT) + "_" + v;
        }
        if (constant && f.desc.equals("Ljava/lang/String;")) return "STRING_CONST";
        return base;
    }

    static String typeWord(Type t) {
        return switch (t.getSort()) {
            case Type.BOOLEAN -> "flag";
            case Type.INT -> "intValue";
            case Type.FLOAT -> "floatValue";
            case Type.DOUBLE -> "doubleValue";
            case Type.LONG -> "longValue";
            case Type.CHAR -> "charValue";
            case Type.BYTE -> "byteValue";
            case Type.SHORT -> "shortValue";
            case Type.ARRAY -> typeWord(t.getElementType()) + "Array";
            default -> {
                String n = t.getInternalName();
                n = cmap.getOrDefault(n, n);
                n = n.substring(Math.max(n.lastIndexOf('/'), n.lastIndexOf('$')) + 1);
                if (n.isEmpty() || Character.isDigit(n.charAt(0))) n = "Obj" + n;
                yield camel(n);
            }
        };
    }

    // ============================================================ method naming
    static final Map<String, String> parent = new HashMap<>(); // union-find over "owner.name+desc"
    static final Set<String> locked = new HashSet<>();
    static final Map<String, String> mmap = new HashMap<>();

    static String find(String x) {
        String p = parent.getOrDefault(x, x);
        if (p.equals(x)) return x;
        String r = find(p);
        parent.put(x, r);
        return r;
    }

    static void union(String a, String b) {
        String ra = find(a), rb = find(b);
        if (!ra.equals(rb)) parent.put(ra, rb);
    }

    static boolean overridable(int acc) { return (acc & (Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC)) == 0; }

    static boolean declares(String owner, String nameDesc, boolean requireOverridable) {
        ClassNode cn = onyx.get(owner);
        if (cn != null) {
            for (MethodNode m : cn.methods) if ((m.name + m.desc).equals(nameDesc) && (!requireOverridable || overridable(m.access))) return true;
            return false;
        }
        Lib l = lib(owner);
        if (l == null) return false;
        for (String s : l.methods) {
            int c = s.indexOf(':');
            int acc = Integer.parseInt(s.substring(0, c));
            if (s.substring(c + 1).equals(nameDesc) && (!requireOverridable || overridable(acc))) return true;
        }
        return false;
    }

    static void nameMethods(Map<String, String> manual) {
        // 1. families
        for (ClassNode cn : onyx.values()) {
            Map<String, List<String>> declsByKey = new HashMap<>();
            for (String anc : ancestors(cn.name)) {
                ClassNode ac = onyx.get(anc);
                if (ac != null) {
                    for (MethodNode m : ac.methods) if (overridable(m.access) && !m.name.startsWith("<"))
                        declsByKey.computeIfAbsent(m.name + m.desc, k -> new ArrayList<>()).add(anc);
                } else {
                    Lib l = lib(anc);
                    if (l == null) continue;
                    for (String s : l.methods) {
                        int c = s.indexOf(':');
                        if (overridable(Integer.parseInt(s.substring(0, c))))
                            declsByKey.computeIfAbsent(s.substring(c + 1), k -> new ArrayList<>()).add(anc);
                    }
                }
            }
            for (MethodNode m : cn.methods) {
                if (m.name.startsWith("<")) continue;
                String self = cn.name + "." + m.name + m.desc;
                parent.putIfAbsent(self, self);
                if (!overridable(m.access)) continue;
                declsByKey.computeIfAbsent(m.name + m.desc, k -> new ArrayList<>()).add(cn.name);
            }
            for (Map.Entry<String, List<String>> e : declsByKey.entrySet()) {
                List<String> owners = e.getValue();
                if (owners.size() < 2 && !owners.contains(cn.name)) continue;
                String first = null;
                boolean lib = false;
                for (String o : owners) {
                    if (onyx.containsKey(o)) {
                        String k = o + "." + e.getKey();
                        parent.putIfAbsent(k, k);
                        if (first == null) first = k; else union(first, k);
                    } else lib = true;
                }
                if (lib && first != null) locked.add(first);
            }
        }
        // bridges: covariant / generic bridge methods share the name of their target
        for (ClassNode cn : onyx.values())
            for (MethodNode m : cn.methods) {
                MethodInsnNode target = bridgeTarget(cn, m);
                if (target != null) union(cn.name + "." + m.name + m.desc, target.owner + "." + target.name + target.desc);
            }
        // propagate locks to roots
        Set<String> lockedRoots = new HashSet<>();
        for (String l : locked) lockedRoots.add(find(l));
        for (ClassNode cn : onyx.values())
            for (MethodNode m : cn.methods) {
                String k = cn.name + "." + m.name + m.desc;
                if (m.name.startsWith("<")) continue;
                if ((cn.access & Opcodes.ACC_ENUM) != 0 && (m.name.equals("values") || m.name.equals("valueOf")) && (m.access & Opcodes.ACC_STATIC) != 0)
                    lockedRoots.add(find(k));
                if (m.name.equals("main") && m.desc.equals("([Ljava/lang/String;)V")) lockedRoots.add(find(k));
                if (m.name.length() > 2) lockedRoots.add(find(k)); // obfuscated names are 1-2 chars; keep real ones (toString, run, ...)
            }

        Map<String, List<String>> families = new LinkedHashMap<>();
        for (ClassNode cn : onyx.values())
            for (MethodNode m : cn.methods) {
                if (m.name.startsWith("<")) continue;
                String k = cn.name + "." + m.name + m.desc;
                families.computeIfAbsent(find(k), x -> new ArrayList<>()).add(k);
            }

        // 2. name proposals per family, by priority
        Map<String, String> proposal = new HashMap<>();
        Map<String, Integer> prio = new HashMap<>();
        for (Map.Entry<String, List<String>> fam : families.entrySet()) {
            String root = fam.getKey();
            if (lockedRoots.contains(root)) continue;
            String best = null;
            int bestPrio = 99;
            for (String k : fam.getValue()) {
                String owner = k.substring(0, k.lastIndexOf('.', k.indexOf('(')));
                String nd = k.substring(owner.length() + 1);
                String name = nd.substring(0, nd.indexOf('(')), desc = nd.substring(nd.indexOf('('));
                ClassNode cn = onyx.get(owner);
                MethodNode m = cn.methods.stream().filter(x -> x.name.equals(name) && x.desc.equals(desc)).findFirst().orElseThrow();
                String p;
                if ((p = manual.get(owner + "." + name + desc)) != null && bestPrio > 0) { best = p; bestPrio = 0; }
                else if ((p = accessorName(cn, m)) != null && bestPrio > 1) { best = p; bestPrio = 1; }
                else if ((p = eventHandlerName(m)) != null && bestPrio > 2) { best = p; bestPrio = 2; }
                else if ((p = lambdaName(cn, m)) != null && bestPrio > 3) { best = p; bestPrio = 3; }
            }
            if (best != null) { proposal.put(root, best); prio.put(root, bestPrio); }
        }
        // 3. assign, highest priority first, resolving clashes
        List<String> roots = new ArrayList<>(families.keySet());
        roots.sort(Comparator.comparingInt(r -> lockedRoots.contains(r) ? -1 : prio.getOrDefault(r, 50)));
        Map<String, Set<String>> visibleNames = new HashMap<>(); // class -> set of "name(args)"
        int counter = 0;
        for (String root : roots) {
            List<String> members = families.get(root);
            if (lockedRoots.contains(root)) {
                // a locked family keeps the library/real name; bridge targets joined to it take that name too
                String keep = null;
                for (String k : members) if (nameOf(k).length() > 2 || locked.contains(k)) { keep = nameOf(k); if (nameOf(k).length() > 2) break; }
                for (String k : members) { String n = keep != null ? keep : nameOf(k); mmap.put(k, n); reserve(visibleNames, k, n); }
                continue;
            }
            String base = proposal.get(root);
            if (base == null) base = "method_" + (++counter);
            String args = argsOf(members.get(0));
            String chosen = base;
            for (int i = 2; clashes(visibleNames, members, chosen, args); i++) chosen = base + i;
            for (String k : members) { mmap.put(k, chosen); reserve(visibleNames, k, chosen); }
        }
        report.add("method families: " + families.size() + ", locked: " + lockedRoots.size() + ", placeholder-named: " + counter);
    }

    static String nameOf(String key) { String nd = key.substring(key.lastIndexOf('.', key.indexOf('(')) + 1); return nd.substring(0, nd.indexOf('(')); }
    static String ownerOf(String key) { return key.substring(0, key.lastIndexOf('.', key.indexOf('('))); }
    static String argsOf(String key) { String d = key.substring(key.indexOf('(')); return d.substring(0, d.indexOf(')') + 1); }

    static void reserve(Map<String, Set<String>> vis, String key, String name) {
        vis.computeIfAbsent(ownerOf(key), k -> new HashSet<>()).add(name + argsOf(key));
    }

    static boolean clashes(Map<String, Set<String>> vis, List<String> members, String name, String args) {
        String sig = name + args;
        for (String k : members) {
            String owner = ownerOf(k);
            Set<String> scope = new HashSet<>(ancestors(owner));
            scope.add(owner);
            scope.addAll(descendants(owner));
            for (String c : scope) {
                if (vis.getOrDefault(c, Set.of()).contains(sig)) return true;
                if (!onyx.containsKey(c)) { Lib l = lib(c); if (l != null && declaresPrefix(l, sig)) return true; }
            }
        }
        return false;
    }

    /** Library methods are matched by name + argument list (any return type). */
    static boolean declaresPrefix(Lib l, String prefix) {
        for (String s : l.methods) if (s.substring(s.indexOf(':') + 1).startsWith(prefix)) return true;
        return false;
    }

    /**
     * A bridge: the body does nothing but shuffle locals/stack, cast, and make exactly one call to a same-named
     * method of the same class with the same number of arguments but a different descriptor, then return.
     * (The obfuscator scrambles bridge bodies with local-slot swaps, so no fixed instruction pattern is used.)
     */
    static MethodInsnNode bridgeTarget(ClassNode cn, MethodNode m) {
        if ((m.access & Opcodes.ACC_STATIC) != 0 || m.name.startsWith("<")) return null;
        MethodInsnNode call = null;
        for (AbstractInsnNode i : m.instructions) {
            int op = i.getOpcode();
            if (op < 0) continue;
            if (i instanceof MethodInsnNode mi) { if (call != null) return null; call = mi; continue; }
            boolean ok = (op >= Opcodes.ILOAD && op <= Opcodes.ALOAD) || (op >= Opcodes.ISTORE && op <= Opcodes.ASTORE)
                    || op == Opcodes.CHECKCAST || (op >= Opcodes.POP && op <= Opcodes.SWAP) || (op >= Opcodes.IRETURN && op <= Opcodes.RETURN);
            if (!ok) return null;
        }
        if (call == null || call.getOpcode() == Opcodes.INVOKESTATIC || call.desc.equals(m.desc)) return null;
        if (!call.owner.equals(cn.name) || Type.getArgumentTypes(call.desc).length != Type.getArgumentTypes(m.desc).length) return null;
        if (!declares(cn.name, call.name + call.desc, false)) return null;
        // the obfuscator renames bridge targets independently of the bridge ("L" -> "d"), so a differently named
        // target is accepted when the delegating method overrides a supertype method, as every javac bridge does
        if (!call.name.equals(m.name) && !overridesSomething(cn, m)) return null;
        return call;
    }

    /** get/is/set for trivial accessors, record component accessors keep the component name. */
    static String accessorName(ClassNode cn, MethodNode m) {
        List<AbstractInsnNode> code = new ArrayList<>();
        for (AbstractInsnNode i : m.instructions) if (i.getOpcode() >= 0) code.add(i);
        boolean stat = (m.access & Opcodes.ACC_STATIC) != 0;
        Type[] args = Type.getArgumentTypes(m.desc);
        Type ret = Type.getReturnType(m.desc);
        // getter
        if (args.length == 0 && ret.getSort() != Type.VOID) {
            FieldInsnNode f = null;
            if (!stat && code.size() == 3 && code.get(0).getOpcode() == Opcodes.ALOAD && ((VarInsnNode) code.get(0)).var == 0
                    && code.get(1).getOpcode() == Opcodes.GETFIELD) f = (FieldInsnNode) code.get(1);
            if (stat && code.size() == 2 && code.get(0).getOpcode() == Opcodes.GETSTATIC) f = (FieldInsnNode) code.get(0);
            if (f != null && f.owner.equals(cn.name) && code.get(code.size() - 1).getOpcode() >= Opcodes.IRETURN && f.desc.equals(ret.getDescriptor())) {
                String fn = fmap.get(fkey(f.owner, f.name, f.desc));
                if (fn == null || fn.startsWith("$")) return null;
                if ("java/lang/Record".equals(cn.superName) && !stat) return fn;
                if (fn.equals(fn.toUpperCase(Locale.ROOT))) return "get" + pascal(fn.toLowerCase(Locale.ROOT));
                if (ret.getSort() == Type.BOOLEAN) return fn.startsWith("is") && fn.length() > 2 && Character.isUpperCase(fn.charAt(2)) ? fn : "is" + cap(fn);
                return "get" + cap(fn);
            }
        }
        // setter
        if (args.length == 1 && ret.getSort() == Type.VOID && !stat && code.size() == 4
                && code.get(0).getOpcode() == Opcodes.ALOAD && ((VarInsnNode) code.get(0)).var == 0
                && code.get(1) instanceof VarInsnNode v && v.var == 1 && code.get(2).getOpcode() == Opcodes.PUTFIELD
                && code.get(3).getOpcode() == Opcodes.RETURN) {
            FieldInsnNode f = (FieldInsnNode) code.get(2);
            if (!f.owner.equals(cn.name)) return null;
            String fn = fmap.get(fkey(f.owner, f.name, f.desc));
            if (fn == null) return null;
            return "set" + cap(fn.startsWith("is") && fn.length() > 2 && Character.isUpperCase(fn.charAt(2)) ? fn.substring(2) : fn);
        }
        return null;
    }

    static String cap(String s) { return s.isEmpty() ? s : Character.toUpperCase(s.charAt(0)) + s.substring(1); }

    static String eventHandlerName(MethodNode m) {
        if (m.visibleAnnotations == null) return null;
        boolean handler = m.visibleAnnotations.stream().anyMatch(an -> an.desc.equals("Lmeteordevelopment/orbit/EventHandler;"));
        if (!handler) return null;
        Type[] args = Type.getArgumentTypes(m.desc);
        if (args.length != 1 || args[0].getSort() != Type.OBJECT) return "onEvent";
        String n = cmap.getOrDefault(args[0].getInternalName(), args[0].getInternalName());
        n = n.substring(Math.max(n.lastIndexOf('/'), n.lastIndexOf('$')) + 1);
        if (n.endsWith("Event") && n.length() > 5) n = n.substring(0, n.length() - 5);
        return "on" + n;
    }

    static Set<String> lambdaImpls; // "owner.name+desc" of methods only reachable through LambdaMetafactory handles

    static Set<String> lambdaImpls() {
        if (lambdaImpls != null) return lambdaImpls;
        Set<String> viaHandle = new HashSet<>(), invoked = new HashSet<>();
        for (ClassNode cn : onyx.values())
            for (MethodNode m : cn.methods)
                for (AbstractInsnNode i : m.instructions) {
                    if (i instanceof InvokeDynamicInsnNode indy)
                        for (Object arg : indy.bsmArgs)
                            if (arg instanceof Handle h && h.getOwner().equals(cn.name)) {
                                String k = h.getOwner() + "." + h.getName() + h.getDesc();
                                // an instance-method handle used from a static method is a method reference
                                // ("auth::handle"), not a lambda body - javac lambdas there are always static
                                boolean instanceImpl = h.getTag() == Opcodes.H_INVOKEVIRTUAL || h.getTag() == Opcodes.H_INVOKESPECIAL || h.getTag() == Opcodes.H_INVOKEINTERFACE;
                                if (instanceImpl && (m.access & Opcodes.ACC_STATIC) != 0) invoked.add(k);
                                else viaHandle.add(k);
                            }
                    if (i instanceof MethodInsnNode mi) invoked.add(mi.owner + "." + mi.name + mi.desc);
                }
        viaHandle.removeAll(invoked);
        lambdaImpls = viaHandle;
        return lambdaImpls;
    }

    static String lambdaName(MethodNode m) { return null; }

    static String lambdaName(ClassNode cn, MethodNode m) {
        if ((cn.access & Opcodes.ACC_ENUM) != 0 && (m.access & Opcodes.ACC_STATIC) != 0 && m.desc.equals("()[L" + cn.name + ";") && !m.name.equals("values"))
            return "$values";
        return lambdaImpls().contains(cn.name + "." + m.name + m.desc) ? "lambda$" + cmap.get(cn.name).replaceAll(".*[/$]", "") : null;
    }

    /**
     * The obfuscator marks ordinary private methods/constructors as synthetic so decompilers hide them, and shuffles
     * enum constants. Synthetic is kept only where javac would emit it (lambda bodies, $values, $VALUES, outer this,
     * switch maps); enum constants are put back in ordinal order so recompiled code keeps the same ordinals.
     */
    static void fixFlagsAndOrder() {
        int cleared = 0, reordered = 0;
        for (ClassNode cn : onyx.values()) {
            boolean isEnum = (cn.access & Opcodes.ACC_ENUM) != 0;
            for (MethodNode m : cn.methods) {
                // real bridges (a delegating method that overrides a supertype signature) get their flags back
                if (bridgeTarget(cn, m) != null && overridesSomething(cn, m)) { m.access |= Opcodes.ACC_BRIDGE | Opcodes.ACC_SYNTHETIC; continue; }
                if ((m.access & Opcodes.ACC_SYNTHETIC) == 0) continue;
                boolean keep = lambdaImpls().contains(cn.name + "." + m.name + m.desc)
                        || (isEnum && (m.access & Opcodes.ACC_STATIC) != 0 && m.desc.equals("()[L" + cn.name + ";") && !m.name.equals("values"));
                if (!keep) { m.access &= ~(Opcodes.ACC_SYNTHETIC | Opcodes.ACC_BRIDGE); cleared++; }
            }
            String outer = outerOf(cn);
            boolean localOrAnon = isAnonymous(cn) || cn.outerMethod != null;
            for (FieldNode f : cn.fields) {
                if ((f.access & Opcodes.ACC_SYNTHETIC) == 0) continue;
                if (localOrAnon) continue; // captured variables (val$x) and outer refs stay synthetic
                boolean keep = (isEnum && f.desc.equals("[L" + cn.name + ";"))
                        || (outer != null && f.desc.equals("L" + outer + ";") && (f.access & Opcodes.ACC_STATIC) == 0)
                        || f.name.startsWith("$SwitchMap$") || f.desc.equals("[I") && (f.access & Opcodes.ACC_STATIC) != 0 && isAnonymous(cn);
                if (!keep) { f.access &= ~Opcodes.ACC_SYNTHETIC; cleared++; }
            }
            if (isEnum) {
                Map<String, Integer> ordinal = enumOrdinals(cn);
                if (!ordinal.isEmpty()) {
                    List<FieldNode> consts = cn.fields.stream().filter(f -> ordinal.containsKey(f.name)).sorted(Comparator.comparingInt(f -> ordinal.get(f.name))).toList();
                    List<FieldNode> others = cn.fields.stream().filter(f -> !ordinal.containsKey(f.name)).toList();
                    cn.fields.clear();
                    cn.fields.addAll(consts);
                    cn.fields.addAll(others);
                    reordered++;
                }
            }
            if (orderFieldsByInitialisation(cn, isEnum)) reordered++;
        }
        report.add("synthetic flags cleared: " + cleared + ", enums reordered by ordinal: " + reordered);
    }

    /**
     * Decompilers turn field assignments in <clinit>/constructors into field initialisers emitted in
     * declaration order, and the obfuscator shuffled declarations. Static fields are re-declared in the order
     * <clinit> first assigns them, instance fields in the order the (largest) constructor first assigns them,
     * so recompiled code initialises in the original order. Enum constants stay first, in ordinal order.
     */
    static boolean orderFieldsByInitialisation(ClassNode cn, boolean isEnum) {
        Map<String, Integer> staticIdx = new HashMap<>(), instIdx = new HashMap<>();
        MethodNode bestCtor = null;
        int bestCount = -1;
        for (MethodNode mn : cn.methods) {
            if (mn.name.equals("<clinit>")) {
                for (AbstractInsnNode i : mn.instructions)
                    if (i instanceof FieldInsnNode f && f.getOpcode() == Opcodes.PUTSTATIC && f.owner.equals(cn.name))
                        staticIdx.putIfAbsent(f.name + ":" + f.desc, staticIdx.size());
            } else if (mn.name.equals("<init>")) {
                int c = 0;
                for (AbstractInsnNode i : mn.instructions) if (i instanceof FieldInsnNode f && f.getOpcode() == Opcodes.PUTFIELD && f.owner.equals(cn.name)) c++;
                if (c > bestCount) { bestCount = c; bestCtor = mn; }
            }
        }
        if (bestCtor != null)
            for (AbstractInsnNode i : bestCtor.instructions)
                if (i instanceof FieldInsnNode f && f.getOpcode() == Opcodes.PUTFIELD && f.owner.equals(cn.name))
                    instIdx.putIfAbsent(f.name + ":" + f.desc, instIdx.size());
        List<FieldNode> before = new ArrayList<>(cn.fields);
        List<FieldNode> enumConsts = new ArrayList<>(), statics = new ArrayList<>(), insts = new ArrayList<>();
        for (FieldNode f : cn.fields) {
            if (isEnum && (f.access & Opcodes.ACC_ENUM) != 0) enumConsts.add(f);
            else if ((f.access & Opcodes.ACC_STATIC) != 0) statics.add(f);
            else insts.add(f);
        }
        // stable sort: assigned fields by first assignment, never-assigned ones keep their place at the end
        statics.sort(Comparator.comparingInt(f -> staticIdx.getOrDefault(f.name + ":" + f.desc, Integer.MAX_VALUE)));
        insts.sort(Comparator.comparingInt(f -> instIdx.getOrDefault(f.name + ":" + f.desc, Integer.MAX_VALUE)));
        cn.fields.clear();
        cn.fields.addAll(enumConsts);
        cn.fields.addAll(statics);
        cn.fields.addAll(insts);
        return !before.equals(cn.fields);
    }

    static boolean overridesSomething(ClassNode cn, MethodNode m) {
        for (String anc : ancestors(cn.name)) if (declares(anc, m.name + m.desc, true)) return true;
        return false;
    }

    /**
     * Classes were moved into new packages, so package-private and protected access that used to be
     * same-package is no longer legal. Everything package-private/protected becomes public.
     */
    static void widenAccess(ClassNode cn) {
        cn.access = widen(cn.access);
        for (InnerClassNode ic : cn.innerClasses) ic.access = widen(ic.access);
        boolean isEnum = (cn.access & Opcodes.ACC_ENUM) != 0;
        for (MethodNode m : cn.methods) {
            if (m.name.equals("<clinit>") || (m.access & Opcodes.ACC_PRIVATE) != 0) continue;
            if (isEnum && m.name.equals("<init>")) continue; // enum constructors cannot be public
            m.access = widen(m.access);
        }
        for (FieldNode f : cn.fields) if ((f.access & Opcodes.ACC_PRIVATE) == 0) f.access = widen(f.access);
    }

    static int widen(int acc) {
        if ((acc & Opcodes.ACC_PRIVATE) != 0) return acc;
        return (acc & ~Opcodes.ACC_PROTECTED) | Opcodes.ACC_PUBLIC;
    }

    static Map<String, Integer> enumOrdinals(ClassNode cn) {
        Map<String, Integer> r = new HashMap<>();
        for (MethodNode mn : cn.methods) {
            if (!mn.name.equals("<clinit>")) continue;
            for (AbstractInsnNode i : mn.instructions) {
                if (!(i instanceof FieldInsnNode f) || f.getOpcode() != Opcodes.PUTSTATIC || !f.owner.equals(cn.name) || !f.desc.equals("L" + cn.name + ";")) continue;
                TypeInsnNode nw = resolveNew(cn, mn, stackTop(cn, mn, f), 0);
                MethodInsnNode ctor = nw == null ? null : ctorFor(cn, mn, nw);
                if (ctor == null) continue;
                List<SourceValue> args = callArgs(cn, mn, ctor);
                if (args == null || args.size() < 3) continue;
                AbstractInsnNode o = single(args.get(2));
                Integer ord = o == null ? null : intConst(o);
                if (ord != null) r.put(f.name, ord);
            }
        }
        return r;
    }

    static Integer intConst(AbstractInsnNode i) {
        int op = i.getOpcode();
        if (op >= Opcodes.ICONST_M1 && op <= Opcodes.ICONST_5) return op - Opcodes.ICONST_0;
        if (op == Opcodes.BIPUSH || op == Opcodes.SIPUSH) return ((IntInsnNode) i).operand;
        if (i instanceof LdcInsnNode l && l.cst instanceof Integer v) return v;
        return null;
    }

    // ============================================================ output
    static void writeTiny(Path p) throws IOException {
        try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(p))) {
            pw.println("tiny\t2\t0\tobf\tnamed");
            for (ClassNode cn : onyx.values()) {
                pw.println("c\t" + cn.name + "\t" + cmap.get(cn.name));
                for (FieldNode f : cn.fields)
                    pw.println("\tf\t" + f.desc + "\t" + f.name + "\t" + fmap.getOrDefault(fkey(cn.name, f.name, f.desc), f.name));
                for (MethodNode m : cn.methods) {
                    if (m.name.startsWith("<")) continue;
                    pw.println("\tm\t" + m.desc + "\t" + m.name + "\t" + mmap.getOrDefault(cn.name + "." + m.name + m.desc, m.name));
                }
            }
        }
    }

    static void writeCsv(Path p) throws IOException {
        List<String> lines = new ArrayList<>(List.of("obf,named"));
        onyx.keySet().stream().sorted().forEach(k -> lines.add(k + "," + cmap.get(k)));
        Files.write(p, lines);
    }

    // ============================================================ remapping
    static String findFieldOwner(String owner, String name, String desc) {
        Deque<String> q = new ArrayDeque<>(List.of(owner));
        Set<String> seen = new HashSet<>();
        while (!q.isEmpty()) {
            String c = q.poll();
            if (!seen.add(c)) continue;
            ClassNode cn = onyx.get(c);
            if (cn == null) continue;
            for (FieldNode f : cn.fields) if (f.name.equals(name) && f.desc.equals(desc)) return c;
            q.addAll(directSupers(c));
        }
        return null;
    }

    static String findMethodOwner(String owner, String name, String desc) {
        Deque<String> q = new ArrayDeque<>(List.of(owner));
        Set<String> seen = new HashSet<>();
        while (!q.isEmpty()) {
            String c = q.poll();
            if (!seen.add(c)) continue;
            ClassNode cn = onyx.get(c);
            if (cn == null) continue;
            for (MethodNode m : cn.methods) if (m.name.equals(name) && m.desc.equals(desc)) return c;
            q.addAll(directSupers(c));
        }
        return null;
    }

    static final Remapper REMAPPER = new Remapper() {
        @Override public String map(String internalName) { return cmap.getOrDefault(internalName, internalName); }

        @Override public String mapFieldName(String owner, String name, String desc) {
            String o = findFieldOwner(owner, name, desc);
            return o == null ? name : fmap.getOrDefault(fkey(o, name, desc), name);
        }

        @Override public String mapRecordComponentName(String owner, String name, String desc) { return mapFieldName(owner, name, desc); }

        @Override public String mapMethodName(String owner, String name, String desc) {
            if (owner.startsWith("[")) return name;
            String o = findMethodOwner(owner, name, desc);
            return o == null ? name : mmap.getOrDefault(o + "." + name + desc, name);
        }

        @Override public String mapInvokeDynamicMethodName(String name, String desc) {
            Type iface = Type.getReturnType(desc);
            if (iface.getSort() != Type.OBJECT || !onyx.containsKey(iface.getInternalName())) return name;
            // the functional interface's single abstract method with that name
            Deque<String> q = new ArrayDeque<>(List.of(iface.getInternalName()));
            Set<String> seen = new HashSet<>();
            while (!q.isEmpty()) {
                String c = q.poll();
                if (!seen.add(c)) continue;
                ClassNode cn = onyx.get(c);
                if (cn == null) continue;
                for (MethodNode m : cn.methods)
                    if (m.name.equals(name) && (m.access & Opcodes.ACC_ABSTRACT) != 0) return mmap.getOrDefault(c + "." + m.name + m.desc, name);
                q.addAll(cn.interfaces);
            }
            return name;
        }
    };

    static void remapJar(Collection<ClassNode> classes, Path out, boolean postProcess) throws IOException {
        try (JarOutputStream jo = new JarOutputStream(Files.newOutputStream(out))) {
            for (ClassNode cn : classes) {
                ClassNode mapped = new ClassNode();
                cn.accept(new ClassRemapper(mapped, REMAPPER));
                if (postProcess) postProcess(mapped);
                ClassWriter cw = new ClassWriter(0);
                mapped.accept(cw);
                jo.putNextEntry(new JarEntry(mapped.name + ".class"));
                jo.write(cw.toByteArray());
                jo.closeEntry();
            }
        }
    }

    static void postProcess(ClassNode cn) {
        // inner class entries that the obfuscator left without a simple name
        for (InnerClassNode ic : cn.innerClasses)
            if (ic.outerName != null && ic.innerName == null && ic.name.startsWith(ic.outerName + "$"))
                ic.innerName = ic.name.substring(ic.outerName.length() + 1);
        rebuildRecordComponents(cn);
        widenAccess(cn);
        String top = cn.name.contains("$") ? cn.name.substring(0, cn.name.indexOf('$')) : cn.name;
        cn.sourceFile = top.substring(top.lastIndexOf('/') + 1) + ".java";
        cn.sourceDebug = null;
        if (cn.invisibleAnnotations != null) cn.invisibleAnnotations.removeIf(an -> an.desc.equals("Llombok/Generated;"));
        if (cn.visibleAnnotations != null) cn.visibleAnnotations.removeIf(an -> an.desc.equals("Llombok/Generated;"));
        for (MethodNode m : cn.methods) {
            m.localVariables = null; // obfuscated ("a"); the decompiler names them from types instead
            m.parameters = null;
            if (m.invisibleAnnotations != null) m.invisibleAnnotations.removeIf(an -> an.desc.equals("Llombok/Generated;"));
            if (m.visibleAnnotations != null) m.visibleAnnotations.removeIf(an -> an.desc.equals("Llombok/Generated;"));
        }
        for (FieldNode f : cn.fields) {
            if (f.invisibleAnnotations != null) f.invisibleAnnotations.removeIf(an -> an.desc.equals("Llombok/Generated;"));
            if (f.visibleAnnotations != null) f.visibleAnnotations.removeIf(an -> an.desc.equals("Llombok/Generated;"));
        }
    }

    /**
     * The obfuscator stripped the Record attribute. Components are recovered from the canonical constructor:
     * the constructor that stores each of its parameters into a distinct instance field. Component order is the
     * parameter order; instance fields are reordered to match so decompilers emit a proper record header.
     */
    static void rebuildRecordComponents(ClassNode cn) {
        if (!"java/lang/Record".equals(cn.superName) || (cn.recordComponents != null && !cn.recordComponents.isEmpty())) return;
        List<FieldNode> inst = cn.fields.stream().filter(f -> (f.access & Opcodes.ACC_STATIC) == 0).toList();
        for (MethodNode m : cn.methods) {
            if (!m.name.equals("<init>")) continue;
            Type[] params = Type.getArgumentTypes(m.desc);
            if (params.length != inst.size()) continue;
            int[] slotOf = new int[params.length];
            int slot = 1;
            for (int i = 0; i < params.length; i++) { slotOf[i] = slot; slot += params[i].getSize(); }
            // parameters are tagged with marker nodes carrying their original slot; copies (loads, stores, dups,
            // the obfuscator's local-slot swapping) keep the tag, so a PUTFIELD reveals which parameter it stores
            Map<Integer, String> fieldForSlot = new HashMap<>();
            Map<AbstractInsnNode, Integer> markers = new HashMap<>();
            CopyKeepingInterpreter interp = new CopyKeepingInterpreter() {
                @Override public SourceValue newParameterValue(boolean isInstanceMethod, int local, Type type) {
                    VarInsnNode marker = new VarInsnNode(Opcodes.ILOAD, local);
                    markers.put(marker, local);
                    return new SourceValue(type.getSize(), marker);
                }
            };
            Frame<SourceValue>[] fr;
            try { fr = new Analyzer<>(interp).analyze(cn.name, m); } catch (AnalyzerException e) { continue; }
            for (AbstractInsnNode i : m.instructions) {
                if (i instanceof FieldInsnNode f && f.getOpcode() == Opcodes.PUTFIELD && f.owner.equals(cn.name)) {
                    Frame<SourceValue> frame = fr[m.instructions.indexOf(i)];
                    if (frame == null) continue;
                    SourceValue v = frame.getStack(frame.getStackSize() - 1);
                    // a value merged from several parameters (e.g. a compact constructor that swaps min/max)
                    // goes to the lowest parameter slot not claimed yet, in PUTFIELD order
                    List<Integer> cands = new ArrayList<>();
                    for (AbstractInsnNode s : v.insns) { Integer sl = markers.get(s); if (sl != null && sl != 0) cands.add(sl); }
                    if (cands.size() != v.insns.size() || cands.isEmpty()) continue;
                    Collections.sort(cands);
                    for (int sl : cands) if (!fieldForSlot.containsKey(sl)) { fieldForSlot.put(sl, f.name + ":" + f.desc); break; }
                }
            }
            List<FieldNode> ordered = new ArrayList<>();
            for (int i = 0; i < params.length; i++) {
                String fd = fieldForSlot.get(slotOf[i]);
                if (fd == null) { ordered = null; break; }
                FieldNode f = inst.stream().filter(x -> (x.name + ":" + x.desc).equals(fd)).findFirst().orElse(null);
                if (f == null || ordered.contains(f) || !f.desc.equals(params[i].getDescriptor())) { ordered = null; break; }
                ordered.add(f);
            }
            if (ordered == null) continue;
            cn.recordComponents = new ArrayList<>();
            for (FieldNode f : ordered) {
                RecordComponentNode rc = new RecordComponentNode(f.name, f.desc, f.signature);
                cn.recordComponents.add(rc);
            }
            List<FieldNode> statics = cn.fields.stream().filter(f -> (f.access & Opcodes.ACC_STATIC) != 0).toList();
            cn.fields.clear();
            cn.fields.addAll(ordered);
            cn.fields.addAll(statics);
            return;
        }
        report.add("record without recoverable canonical constructor: " + cn.name);
    }

    static void remapMc(Path mcJar, Path out) throws IOException {
        int changed = 0;
        try (ZipInputStream zin = new ZipInputStream(Files.newInputStream(mcJar));
             JarOutputStream jo = new JarOutputStream(Files.newOutputStream(out))) {
            for (ZipEntry e; (e = zin.getNextEntry()) != null; ) {
                byte[] b = zin.readAllBytes();
                if (e.getName().endsWith(".class") && new String(b, java.nio.charset.StandardCharsets.ISO_8859_1).contains(ROOT)) {
                    ClassReader cr = new ClassReader(b);
                    ClassWriter cw = new ClassWriter(0);
                    cr.accept(new ClassRemapper(cw, REMAPPER), 0);
                    b = cw.toByteArray();
                    changed++;
                }
                if (e.isDirectory()) continue;
                jo.putNextEntry(new JarEntry(e.getName()));
                jo.write(b);
                jo.closeEntry();
            }
        }
        report.add("mc.jar classes with remapped hook references: " + changed);
    }
}
