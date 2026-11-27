import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.file.*;
import java.util.*;
import java.util.jar.*;
import java.util.zip.*;

/**
 * Pass 1 of the Onyx deobfuscation pipeline: bytecode cleanup.
 *
 * Usage: java -cp <asm jars> tools/Clean.java <in.jar> <out.jar> <dump.txt> <libDir>
 *
 *  - folds obfuscated integer/long constant expressions (e.g. "5 >> 1 ^ --3")
 *  - removes junk calls to empty static (I)V methods (lqzxvptmnk.qpwoeirzn)
 *  - removes dead "boolean x = true" stores the obfuscator sprinkles around
 *  - decrypts strings: static String m(String) decryptors are verified to only use
 *    a whitelist of pure instructions, copied into an isolated class and executed
 *  - writes a per-class analysis dump used to build the rename map
 */
public class Clean {
    static final Map<String, ClassNode> onyx = new LinkedHashMap<>();
    static final Map<String, String[]> headers = new HashMap<>(); // name -> [super, ifaces...]

    public static void main(String[] a) throws Exception {
        Path in = Paths.get(a[0]), out = Paths.get(a[1]), dump = Paths.get(a[2]), libDir = Paths.get(a[3]);
        readJar(in, true);
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(libDir, "*.jar")) {
            for (Path p : ds) readJar(p, false);
        }

        Set<String> junk = findJunkMethods();
        System.out.println("junk methods: " + junk);
        int folded = 0, junkCalls = 0, deadStores = 0;
        for (ClassNode cn : onyx.values())
            for (MethodNode mn : cn.methods) {
                junkCalls += removeJunkCalls(mn, junk);
                folded += foldConstants(mn);
                folded += foldOpaqueJumps(cn, mn);
                folded += foldConstants(mn);
                deadStores += removeDeadStores(mn);
            }
        System.out.printf("folded=%d junkCalls=%d deadStores=%d%n", folded, junkCalls, deadStores);

        Map<String, Method> decryptors = findDecryptors();
        System.out.println("decryptors: " + decryptors.size());
        int decrypted = 0, failed = 0;
        Map<String, Integer> remainingRefs = new HashMap<>();
        for (ClassNode cn : onyx.values())
            for (MethodNode mn : cn.methods) {
                int[] r = decryptMethod(cn, mn, decryptors, remainingRefs);
                decrypted += r[0];
                failed += r[1];
            }
        System.out.printf("decrypted=%d failed=%d %s%n", decrypted, failed, remainingRefs);
        // drop decryptors and junk methods that are no longer referenced
        Set<String> removable = new HashSet<>(decryptors.keySet());
        removable.addAll(junk);
        removable.removeAll(remainingRefs.keySet());
        for (ClassNode cn : onyx.values())
            for (MethodNode mn : cn.methods)
                for (AbstractInsnNode insn : mn.instructions)
                    if (insn instanceof MethodInsnNode mi) removable.remove(mi.owner + "." + mi.name + mi.desc);
                    else if (insn instanceof InvokeDynamicInsnNode) { /* decryptors never used via indy */ }
        int removed = 0;
        for (ClassNode cn : onyx.values())
            removed += cn.methods.removeIf(mn -> removable.contains(cn.name + "." + mn.name + mn.desc)) ? 1 : 0;
        System.out.println("classes with removed decryptor/junk methods: " + removed);
        // a class left completely empty (the junk holder) is dropped
        onyx.values().removeIf(cn -> cn.methods.isEmpty() && cn.fields.isEmpty() && cn.name.endsWith("lqzxvptmnk"));

        int hoisted = 0;
        for (ClassNode cn : onyx.values())
            for (MethodNode mn : cn.methods) if (mn.name.equals("<init>")) hoisted += hoistSuperCall(cn, mn);
        System.out.println("constructors with super()/this() moved back to the start: " + hoisted);

        int spilled = 0;
        for (ClassNode cn : onyx.values())
            for (MethodNode mn : cn.methods) spilled += normalizeStack(cn, mn);
        System.out.println("merge points with stack values spilled to locals: " + spilled);

        writeJar(out);
        writeDump(dump);
    }

    // ---------------------------------------------------------------- IO
    static void readJar(Path p, boolean isOnyx) throws IOException {
        try (ZipInputStream zin = new ZipInputStream(Files.newInputStream(p))) {
            for (ZipEntry e; (e = zin.getNextEntry()) != null; ) {
                if (!e.getName().endsWith(".class")) continue;
                byte[] b = zin.readAllBytes();
                ClassReader cr = new ClassReader(b);
                if (isOnyx) {
                    ClassNode cn = new ClassNode();
                    cr.accept(cn, 0);
                    onyx.put(cn.name, cn);
                }
                String[] ifs = cr.getInterfaces();
                String[] h = new String[ifs.length + 1];
                h[0] = cr.getSuperName();
                System.arraycopy(ifs, 0, h, 1, ifs.length);
                headers.putIfAbsent(cr.getClassName(), h);
            }
        }
    }

    static String[] header(String name) {
        String[] h = headers.get(name);
        if (h != null) return h;
        try (InputStream is = ClassLoader.getSystemResourceAsStream(name + ".class")) {
            if (is == null) return null;
            ClassReader cr = new ClassReader(is);
            String[] ifs = cr.getInterfaces();
            h = new String[ifs.length + 1];
            h[0] = cr.getSuperName();
            System.arraycopy(ifs, 0, h, 1, ifs.length);
            headers.put(name, h);
            return h;
        } catch (IOException e) { return null; }
    }

    static boolean isInterface(String name) {
        ClassNode cn = onyx.get(name);
        if (cn != null) return (cn.access & Opcodes.ACC_INTERFACE) != 0;
        try {
            Class<?> c = Class.forName(name.replace('/', '.'), false, ClassLoader.getPlatformClassLoader());
            return c.isInterface();
        } catch (Throwable t) { return false; }
    }

    static List<String> superChain(String n) {
        List<String> l = new ArrayList<>();
        while (n != null) { l.add(n); String[] h = header(n); n = h == null ? null : h[0]; }
        return l;
    }

    static boolean assignable(String to, String from) {
        Deque<String> q = new ArrayDeque<>(List.of(from));
        Set<String> seen = new HashSet<>();
        while (!q.isEmpty()) {
            String c = q.poll();
            if (!seen.add(c)) continue;
            if (c.equals(to)) return true;
            String[] h = header(c);
            if (h == null) continue;
            for (String s : h) if (s != null) q.add(s);
        }
        return false;
    }

    static void writeJar(Path out) throws IOException {
        try (JarOutputStream jo = new JarOutputStream(Files.newOutputStream(out))) {
            for (ClassNode cn : onyx.values()) {
                ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES) {
                    @Override protected String getCommonSuperClass(String t1, String t2) {
                        if (assignable(t1, t2)) return t1;
                        if (assignable(t2, t1)) return t2;
                        if (isInterface(t1) || isInterface(t2)) return "java/lang/Object";
                        for (String s : superChain(t1)) if (assignable(s, t2)) return s;
                        return "java/lang/Object";
                    }
                };
                byte[] b;
                try {
                    cn.accept(cw);
                    b = cw.toByteArray();
                } catch (Throwable t) {
                    System.out.println("frame computation failed for " + cn.name + ": " + t + " (writing without frames)");
                    ClassWriter cw2 = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                    cn.accept(cw2);
                    b = cw2.toByteArray();
                }
                jo.putNextEntry(new JarEntry(cn.name + ".class"));
                jo.write(b);
                jo.closeEntry();
            }
        }
    }

    // ---------------------------------------------------------------- junk
    static Set<String> findJunkMethods() {
        Set<String> s = new HashSet<>();
        for (ClassNode cn : onyx.values())
            for (MethodNode mn : cn.methods) {
                if ((mn.access & Opcodes.ACC_STATIC) == 0 || !mn.desc.equals("(I)V")) continue;
                AbstractInsnNode f = firstReal(mn.instructions.getFirst());
                if (f != null && f.getOpcode() == Opcodes.RETURN) s.add(cn.name + "." + mn.name + mn.desc);
            }
        return s;
    }

    static int removeJunkCalls(MethodNode mn, Set<String> junk) {
        int n = 0;
        for (AbstractInsnNode insn : mn.instructions.toArray()) {
            if (insn instanceof MethodInsnNode mi && junk.contains(mi.owner + "." + mi.name + mi.desc)) {
                AbstractInsnNode p = prevReal(insn);
                if (p != null && intValue(p) != null) {
                    mn.instructions.remove(p);
                    mn.instructions.remove(insn);
                    n++;
                }
            }
        }
        return n;
    }

    /** "ICONST_1; ISTORE n" where n is never read -> removed. */
    static int removeDeadStores(MethodNode mn) {
        Set<Integer> read = new HashSet<>();
        for (AbstractInsnNode i : mn.instructions) {
            if (i instanceof VarInsnNode v && v.getOpcode() >= Opcodes.ILOAD && v.getOpcode() <= Opcodes.ALOAD) read.add(v.var);
            if (i instanceof IincInsnNode ii) read.add(ii.var);
        }
        int n = 0;
        for (AbstractInsnNode insn : mn.instructions.toArray()) {
            if (insn instanceof VarInsnNode v && v.getOpcode() == Opcodes.ISTORE && !read.contains(v.var)) {
                AbstractInsnNode p = insn.getPrevious();
                if (p != null && intValue(p) != null) {
                    mn.instructions.remove(p);
                    mn.instructions.remove(insn);
                    n++;
                }
            }
        }
        if (n > 0 && mn.localVariables != null) mn.localVariables.removeIf(lv -> !read.contains(lv.index) && lv.index >= argSlots(mn));
        return n;
    }

    static int argSlots(MethodNode mn) {
        int s = (mn.access & Opcodes.ACC_STATIC) != 0 ? 0 : 1;
        for (Type t : Type.getArgumentTypes(mn.desc)) s += t.getSize();
        return s;
    }

    // ---------------------------------------------------------------- constant folding
    static Integer intValue(AbstractInsnNode i) {
        int op = i.getOpcode();
        if (op >= Opcodes.ICONST_M1 && op <= Opcodes.ICONST_5) return op - Opcodes.ICONST_0;
        if (op == Opcodes.BIPUSH || op == Opcodes.SIPUSH) return ((IntInsnNode) i).operand;
        if (i instanceof LdcInsnNode l && l.cst instanceof Integer v) return v;
        return null;
    }

    static Long longValue(AbstractInsnNode i) {
        int op = i.getOpcode();
        if (op == Opcodes.LCONST_0) return 0L;
        if (op == Opcodes.LCONST_1) return 1L;
        if (i instanceof LdcInsnNode l && l.cst instanceof Long v) return v;
        return null;
    }

    static AbstractInsnNode pushInt(int v) {
        if (v >= -1 && v <= 5) return new InsnNode(Opcodes.ICONST_0 + v);
        if (v >= Byte.MIN_VALUE && v <= Byte.MAX_VALUE) return new IntInsnNode(Opcodes.BIPUSH, v);
        if (v >= Short.MIN_VALUE && v <= Short.MAX_VALUE) return new IntInsnNode(Opcodes.SIPUSH, v);
        return new LdcInsnNode(v);
    }

    static AbstractInsnNode pushLong(long v) {
        if (v == 0) return new InsnNode(Opcodes.LCONST_0);
        if (v == 1) return new InsnNode(Opcodes.LCONST_1);
        return new LdcInsnNode(v);
    }

    static Set<LabelNode> referencedLabels(MethodNode mn) {
        Set<LabelNode> s = new HashSet<>();
        for (AbstractInsnNode i : mn.instructions) {
            if (i instanceof JumpInsnNode j) s.add(j.label);
            if (i instanceof TableSwitchInsnNode t) { s.add(t.dflt); s.addAll(t.labels); }
            if (i instanceof LookupSwitchInsnNode t) { s.add(t.dflt); s.addAll(t.labels); }
        }
        for (TryCatchBlockNode t : mn.tryCatchBlocks) { s.add(t.start); s.add(t.end); s.add(t.handler); }
        return s;
    }

    static Set<LabelNode> barrierLabels = Set.of();

    static AbstractInsnNode prevReal(AbstractInsnNode i) {
        for (i = i.getPrevious(); i != null; i = i.getPrevious()) {
            if (i instanceof LineNumberNode) continue;
            if (i instanceof LabelNode l && !barrierLabels.contains(l)) continue;
            if (i instanceof LabelNode || i instanceof FrameNode) return null;
            return i;
        }
        return null;
    }

    static AbstractInsnNode firstReal(AbstractInsnNode i) {
        for (; i != null; i = i.getNext()) if (i.getOpcode() >= 0) return i;
        return null;
    }

    static int foldConstants(MethodNode mn) {
        barrierLabels = referencedLabels(mn);
        int total = 0;
        boolean changed = true;
        while (changed) {
            changed = false;
            for (AbstractInsnNode insn : mn.instructions.toArray()) {
                if (insn.getPrevious() == null && insn != mn.instructions.getFirst()) continue; // removed
                int op = insn.getOpcode();
                AbstractInsnNode p1 = prevReal(insn);
                if (p1 == null) continue;
                AbstractInsnNode repl = null;
                List<AbstractInsnNode> consumed = new ArrayList<>();
                Integer b = intValue(p1);
                Long lb = longValue(p1);
                switch (op) {
                    case Opcodes.DUP -> { if (b != null) { repl = pushInt(b); } }
                    case Opcodes.POP -> { if (b != null) { mn.instructions.remove(p1); mn.instructions.remove(insn); total++; changed = true; } }
                    case Opcodes.POP2 -> {
                        if (lb != null) { mn.instructions.remove(p1); mn.instructions.remove(insn); total++; changed = true; break; }
                        if (b == null) break;
                        AbstractInsnNode p2 = prevReal(p1);
                        if (p2 != null && intValue(p2) != null) {
                            mn.instructions.remove(p2); mn.instructions.remove(p1); mn.instructions.remove(insn); total++; changed = true;
                        }
                    }
                    case Opcodes.INEG -> { if (b != null) { repl = pushInt(-b); consumed.add(p1); } }
                    case Opcodes.I2B -> { if (b != null) { repl = pushInt((byte) (int) b); consumed.add(p1); } }
                    case Opcodes.I2S -> { if (b != null) { repl = pushInt((short) (int) b); consumed.add(p1); } }
                    case Opcodes.I2C -> { if (b != null) { repl = pushInt((char) (int) b); consumed.add(p1); } }
                    case Opcodes.I2L -> { if (b != null) { repl = pushLong(b); consumed.add(p1); } }
                    case Opcodes.L2I -> { if (lb != null) { repl = pushInt((int) (long) lb); consumed.add(p1); } }
                    case Opcodes.LNEG -> { if (lb != null) { repl = pushLong(-lb); consumed.add(p1); } }
                    case Opcodes.IADD, Opcodes.ISUB, Opcodes.IMUL, Opcodes.IAND, Opcodes.IOR, Opcodes.IXOR,
                         Opcodes.ISHL, Opcodes.ISHR, Opcodes.IUSHR, Opcodes.IDIV, Opcodes.IREM -> {
                        if (b == null) break;
                        AbstractInsnNode p2 = prevReal(p1);
                        Integer x = p2 == null ? null : intValue(p2);
                        if (x == null) break;
                        int y = b;
                        if ((op == Opcodes.IDIV || op == Opcodes.IREM) && y == 0) break;
                        int r = switch (op) {
                            case Opcodes.IADD -> x + y; case Opcodes.ISUB -> x - y; case Opcodes.IMUL -> x * y;
                            case Opcodes.IAND -> x & y; case Opcodes.IOR -> x | y; case Opcodes.IXOR -> x ^ y;
                            case Opcodes.ISHL -> x << y; case Opcodes.ISHR -> x >> y; case Opcodes.IUSHR -> x >>> y;
                            case Opcodes.IDIV -> x / y; default -> x % y;
                        };
                        repl = pushInt(r); consumed.add(p2); consumed.add(p1);
                    }
                    case Opcodes.LADD, Opcodes.LSUB, Opcodes.LMUL, Opcodes.LAND, Opcodes.LOR, Opcodes.LXOR -> {
                        if (lb == null) break;
                        AbstractInsnNode p2 = prevReal(p1);
                        Long x = p2 == null ? null : longValue(p2);
                        if (x == null) break;
                        long y = lb;
                        long r = switch (op) {
                            case Opcodes.LADD -> x + y; case Opcodes.LSUB -> x - y; case Opcodes.LMUL -> x * y;
                            case Opcodes.LAND -> x & y; case Opcodes.LOR -> x | y; default -> x ^ y;
                        };
                        repl = pushLong(r); consumed.add(p2); consumed.add(p1);
                    }
                    case Opcodes.LSHL, Opcodes.LSHR, Opcodes.LUSHR -> {
                        if (b == null) break;
                        AbstractInsnNode p2 = prevReal(p1);
                        Long x = p2 == null ? null : longValue(p2);
                        if (x == null) break;
                        long r = op == Opcodes.LSHL ? x << b : op == Opcodes.LSHR ? x >> b : x >>> b;
                        repl = pushLong(r); consumed.add(p2); consumed.add(p1);
                    }
                    default -> {}
                }
                if (repl != null) {
                    for (AbstractInsnNode c : consumed) mn.instructions.remove(c);
                    mn.instructions.set(insn, repl);
                    total++;
                    changed = true;
                }
            }
        }
        return total;
    }

    // ---------------------------------------------------------------- string decryption
    static final Set<String> ALLOWED_CALLS = Set.of(
            "java/lang/String.length()I", "java/lang/String.charAt(I)C", "java/lang/String.<init>([C)V",
            "java/lang/String.toCharArray()[C", "java/lang/String.intern()Ljava/lang/String;",
            "java/lang/String.valueOf([C)Ljava/lang/String;");

    static boolean isPure(MethodNode mn) {
        for (AbstractInsnNode i : mn.instructions) {
            int op = i.getOpcode();
            if (op < 0) continue;
            if (i instanceof MethodInsnNode m) {
                if (!ALLOWED_CALLS.contains(m.owner + "." + m.name + m.desc)) return false;
                continue;
            }
            if (i instanceof TypeInsnNode t) {
                if ((op == Opcodes.NEW || op == Opcodes.CHECKCAST) && t.desc.equals("java/lang/String")) continue;
                return false;
            }
            if (i instanceof FieldInsnNode || i instanceof InvokeDynamicInsnNode || i instanceof MultiANewArrayInsnNode) return false;
            if (op == Opcodes.ATHROW || op == Opcodes.MONITORENTER || op == Opcodes.MONITOREXIT) return false;
            if (op == Opcodes.ANEWARRAY) return false;
        }
        return true;
    }

    static Map<String, Method> findDecryptors() throws Exception {
        Map<String, Method> out = new HashMap<>();
        Map<String, byte[]> defs = new HashMap<>();
        Map<String, String> keyOf = new HashMap<>();
        int idx = 0;
        for (ClassNode cn : onyx.values())
            for (MethodNode mn : cn.methods) {
                if ((mn.access & Opcodes.ACC_STATIC) == 0 || !mn.desc.equals("(Ljava/lang/String;)Ljava/lang/String;")) continue;
                if (!mn.tryCatchBlocks.isEmpty() || !isPure(mn)) continue;
                boolean hasXor = false;
                for (AbstractInsnNode i : mn.instructions) hasXor |= i.getOpcode() == Opcodes.IXOR;
                if (!hasXor) continue; // pure String->String helpers that are not decryptors
                String sandboxName = "sandbox/Decryptor" + (idx++);
                ClassNode sc = new ClassNode();
                sc.version = Opcodes.V1_8;
                sc.access = Opcodes.ACC_PUBLIC;
                sc.name = sandboxName;
                sc.superName = "java/lang/Object";
                MethodNode copy = new MethodNode(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "d", mn.desc, null, null);
                mn.accept(copy);
                copy.access = Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC;
                copy.name = "d";
                copy.visibleAnnotations = null; copy.invisibleAnnotations = null;
                sc.methods.add(copy);
                ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES) {
                    @Override protected String getCommonSuperClass(String a, String b) { return "java/lang/Object"; }
                };
                sc.accept(cw);
                defs.put(sandboxName.replace('/', '.'), cw.toByteArray());
                keyOf.put(sandboxName.replace('/', '.'), cn.name + "." + mn.name + mn.desc);
            }
        ClassLoader sandbox = new ClassLoader(ClassLoader.getPlatformClassLoader()) {
            @Override protected Class<?> findClass(String name) throws ClassNotFoundException {
                byte[] b = defs.get(name);
                if (b == null) throw new ClassNotFoundException(name);
                return defineClass(name, b, 0, b.length);
            }
        };
        for (Map.Entry<String, String> e : keyOf.entrySet()) {
            Method m = sandbox.loadClass(e.getKey()).getMethod("d", String.class);
            out.put(e.getValue(), m);
        }
        return out;
    }

    /**
     * Data-flow based decryption: follows each encrypted LDC through dup/swap/local copies to the decryptor
     * call that consumes it. An LDC is rewritten only when every consumer is a decryptor call (same method)
     * whose argument comes exclusively from rewritable LDCs; those calls are then removed.
     */
    static int[] decryptMethod(ClassNode cn, MethodNode mn, Map<String, Method> decryptors, Map<String, Integer> remaining) {
        List<MethodInsnNode> calls = new ArrayList<>();
        for (AbstractInsnNode i : mn.instructions)
            if (i instanceof MethodInsnNode mi && mi.getOpcode() == Opcodes.INVOKESTATIC && decryptors.containsKey(mi.owner + "." + mi.name + mi.desc))
                calls.add(mi);
        if (calls.isEmpty()) return new int[]{0, 0};
        Map<AbstractInsnNode, Set<AbstractInsnNode>> consumers = new HashMap<>();
        org.objectweb.asm.tree.analysis.SourceInterpreter interp = new org.objectweb.asm.tree.analysis.SourceInterpreter(Opcodes.ASM9) {
            void use(AbstractInsnNode insn, org.objectweb.asm.tree.analysis.SourceValue... vs) {
                for (var v : vs) for (AbstractInsnNode s : v.insns) consumers.computeIfAbsent(s, k -> new HashSet<>()).add(insn);
            }
            @Override public org.objectweb.asm.tree.analysis.SourceValue copyOperation(AbstractInsnNode insn, org.objectweb.asm.tree.analysis.SourceValue v) { return v; }
            @Override public org.objectweb.asm.tree.analysis.SourceValue unaryOperation(AbstractInsnNode insn, org.objectweb.asm.tree.analysis.SourceValue v) { use(insn, v); return super.unaryOperation(insn, v); }
            @Override public org.objectweb.asm.tree.analysis.SourceValue binaryOperation(AbstractInsnNode insn, org.objectweb.asm.tree.analysis.SourceValue a, org.objectweb.asm.tree.analysis.SourceValue b) { use(insn, a, b); return super.binaryOperation(insn, a, b); }
            @Override public org.objectweb.asm.tree.analysis.SourceValue ternaryOperation(AbstractInsnNode insn, org.objectweb.asm.tree.analysis.SourceValue a, org.objectweb.asm.tree.analysis.SourceValue b, org.objectweb.asm.tree.analysis.SourceValue c) { use(insn, a, b, c); return super.ternaryOperation(insn, a, b, c); }
            @Override public org.objectweb.asm.tree.analysis.SourceValue naryOperation(AbstractInsnNode insn, List<? extends org.objectweb.asm.tree.analysis.SourceValue> vs) { use(insn, vs.toArray(new org.objectweb.asm.tree.analysis.SourceValue[0])); return super.naryOperation(insn, vs); }
            @Override public void returnOperation(AbstractInsnNode insn, org.objectweb.asm.tree.analysis.SourceValue v, org.objectweb.asm.tree.analysis.SourceValue e) { use(insn, v); }
        };
        org.objectweb.asm.tree.analysis.Frame<org.objectweb.asm.tree.analysis.SourceValue>[] frames;
        try {
            frames = new org.objectweb.asm.tree.analysis.Analyzer<>(interp).analyze(cn.name, mn);
        } catch (Exception e) {
            System.out.println("analysis failed " + cn.name + "." + mn.name + ": " + e);
            for (MethodInsnNode c : calls) remaining.merge(c.owner + "." + c.name + c.desc, 1, Integer::sum);
            return new int[]{0, calls.size()};
        }
        Map<MethodInsnNode, Set<AbstractInsnNode>> cand = new LinkedHashMap<>();
        for (MethodInsnNode c : calls) {
            var f = frames[mn.instructions.indexOf(c)];
            if (f == null) continue; // unreachable
            var v = f.getStack(f.getStackSize() - 1);
            boolean ok = !v.insns.isEmpty();
            for (AbstractInsnNode s : v.insns) ok &= s instanceof LdcInsnNode l && l.cst instanceof String;
            if (ok) cand.put(c, v.insns);
        }
        boolean changed = true;
        Set<AbstractInsnNode> rewritable = new HashSet<>();
        while (changed) {
            changed = false;
            rewritable.clear();
            Set<AbstractInsnNode> allSrc = new HashSet<>();
            cand.values().forEach(allSrc::addAll);
            for (AbstractInsnNode l : allSrc) {
                Set<AbstractInsnNode> cs = consumers.getOrDefault(l, Set.of());
                String key = null;
                boolean ok = !cs.isEmpty();
                for (AbstractInsnNode c : cs) {
                    if (!(c instanceof MethodInsnNode mi) || !cand.containsKey(mi)) { ok = false; break; }
                    String k = mi.owner + "." + mi.name + mi.desc;
                    if (key != null && !key.equals(k)) { ok = false; break; }
                    key = k;
                }
                if (ok) rewritable.add(l);
            }
            for (Iterator<Map.Entry<MethodInsnNode, Set<AbstractInsnNode>>> it = cand.entrySet().iterator(); it.hasNext(); )
                if (!rewritable.containsAll(it.next().getValue())) { it.remove(); changed = true; }
        }
        int ok = 0, bad = 0;
        Map<AbstractInsnNode, String> decryptedOf = new HashMap<>();
        for (Map.Entry<MethodInsnNode, Set<AbstractInsnNode>> e : cand.entrySet()) {
            MethodInsnNode c = e.getKey();
            Method m = decryptors.get(c.owner + "." + c.name + c.desc);
            for (AbstractInsnNode l : e.getValue())
                decryptedOf.computeIfAbsent(l, k -> {
                    try { return (String) m.invoke(null, (String) ((LdcInsnNode) k).cst); } catch (Throwable t) { throw new RuntimeException(t); }
                });
        }
        decryptedOf.forEach((l, s) -> ((LdcInsnNode) l).cst = s);
        for (MethodInsnNode c : calls) {
            if (cand.containsKey(c)) { mn.instructions.remove(c); ok++; }
            else if (frames[mn.instructions.indexOf(c)] == null) { /* dead code, removed later */ }
            else { bad++; remaining.merge(c.owner + "." + c.name + c.desc, 1, Integer::sum);
                   System.out.println("  undecryptable call in " + cn.name + "." + mn.name + mn.desc); }
        }
        return new int[]{ok, bad};
    }

    /** Folds "ICONST_n; IFxx L" opaque predicates and removes unreachable code. */
    static int foldOpaqueJumps(ClassNode cn, MethodNode mn) {
        barrierLabels = referencedLabels(mn);
        int n = 0;
        for (AbstractInsnNode insn : mn.instructions.toArray()) {
            int op = insn.getOpcode();
            if (op < Opcodes.IFEQ || op > Opcodes.IFLE) continue;
            AbstractInsnNode p = prevReal(insn);
            Integer v = p == null ? null : intValue(p);
            if (v == null) continue;
            boolean taken = switch (op) {
                case Opcodes.IFEQ -> v == 0; case Opcodes.IFNE -> v != 0; case Opcodes.IFLT -> v < 0;
                case Opcodes.IFGE -> v >= 0; case Opcodes.IFGT -> v > 0; default -> v <= 0;
            };
            mn.instructions.remove(p);
            if (taken) mn.instructions.set(insn, new JumpInsnNode(Opcodes.GOTO, ((JumpInsnNode) insn).label));
            else mn.instructions.remove(insn);
            n++;
        }
        if (n > 0) removeDeadCode(cn, mn);
        return n;
    }

    static void removeDeadCode(ClassNode cn, MethodNode mn) {
        org.objectweb.asm.tree.analysis.Frame<?>[] frames;
        try {
            frames = new org.objectweb.asm.tree.analysis.Analyzer<>(new org.objectweb.asm.tree.analysis.SourceInterpreter()).analyze(cn.name, mn);
        } catch (Exception e) { return; }
        AbstractInsnNode[] insns = mn.instructions.toArray();
        for (int i = 0; i < insns.length; i++)
            if (frames[i] == null && insns[i].getOpcode() >= 0) mn.instructions.remove(insns[i]);
            else if (frames[i] == null && insns[i] instanceof FrameNode) mn.instructions.remove(insns[i]);
        // drop try/catch blocks whose range became empty
        mn.tryCatchBlocks.removeIf(t -> {
            for (AbstractInsnNode i = t.start; i != null && i != t.end; i = i.getNext()) if (i.getOpcode() >= 0) return false;
            return true;
        });
    }

    // ---------------------------------------------------------------- constructor prologue
    /**
     * javac always emits super(...)/this(...) first; the obfuscator moves field stores in front of it, which
     * decompiles to illegal source. When the call's receiver is "this" and its arguments are constants, static
     * fields or untouched parameters, the call is re-emitted at the start of the constructor and the original
     * call site is replaced by POPs of the (side-effect free) values it consumed.
     */
    static int hoistSuperCall(ClassNode cn, MethodNode mn) {
        Map<AbstractInsnNode, Integer> markers = new HashMap<>();
        var interp = new org.objectweb.asm.tree.analysis.SourceInterpreter(Opcodes.ASM9) {
            @Override public org.objectweb.asm.tree.analysis.SourceValue copyOperation(AbstractInsnNode insn, org.objectweb.asm.tree.analysis.SourceValue v) { return v; }
            @Override public org.objectweb.asm.tree.analysis.SourceValue newParameterValue(boolean isInstanceMethod, int local, Type type) {
                VarInsnNode m = new VarInsnNode(Opcodes.ALOAD, local);
                markers.put(m, local);
                return new org.objectweb.asm.tree.analysis.SourceValue(type.getSize(), m);
            }
        };
        org.objectweb.asm.tree.analysis.Frame<org.objectweb.asm.tree.analysis.SourceValue>[] fr;
        try { fr = new org.objectweb.asm.tree.analysis.Analyzer<>(interp).analyze(cn.name, mn); } catch (Exception e) { return 0; }
        MethodInsnNode call = null;
        for (AbstractInsnNode i : mn.instructions) {
            if (!(i instanceof MethodInsnNode mi) || mi.getOpcode() != Opcodes.INVOKESPECIAL || !mi.name.equals("<init>")) continue;
            if (!mi.owner.equals(cn.superName) && !mi.owner.equals(cn.name)) continue;
            var f = fr[mn.instructions.indexOf(i)];
            if (f == null) continue;
            int argc = Type.getArgumentTypes(mi.desc).length;
            var recv = f.getStack(f.getStackSize() - argc - 1);
            if (recv.insns.size() == 1 && Integer.valueOf(0).equals(markers.get(recv.insns.iterator().next()))) { call = mi; break; }
        }
        if (call == null) return 0;
        // is there anything other than value pushes before the call? (field stores, local stores ...)
        boolean needed = false;
        for (AbstractInsnNode i = mn.instructions.getFirst(); i != call; i = i.getNext()) {
            int op = i.getOpcode();
            if (op == Opcodes.PUTFIELD || op == Opcodes.PUTSTATIC || (op >= Opcodes.ISTORE && op <= Opcodes.ASTORE)
                    || i instanceof MethodInsnNode || i instanceof JumpInsnNode) { needed = true; break; }
        }
        if (!needed) return 0;
        var f = fr[mn.instructions.indexOf(call)];
        Type[] args = Type.getArgumentTypes(call.desc);
        InsnList prologue = new InsnList();
        prologue.add(new VarInsnNode(Opcodes.ALOAD, 0));
        int slot = 1;
        int[] paramSlot = new int[64];
        for (Type t : Type.getArgumentTypes(mn.desc)) { slot += t.getSize(); }
        for (int k = 0; k < args.length; k++) {
            var v = f.getStack(f.getStackSize() - args.length + k);
            if (v.insns.size() != 1) return 0;
            AbstractInsnNode src = v.insns.iterator().next();
            Integer mk = markers.get(src);
            if (mk != null) {
                if (mk == 0) return 0;
                prologue.add(new VarInsnNode(args[k].getOpcode(Opcodes.ILOAD), mk));
            } else if (src instanceof LdcInsnNode || intValue(src) != null || longValue(src) != null
                    || src.getOpcode() == Opcodes.ACONST_NULL || (src.getOpcode() >= Opcodes.FCONST_0 && src.getOpcode() <= Opcodes.DCONST_1)
                    || (src instanceof FieldInsnNode fi && fi.getOpcode() == Opcodes.GETSTATIC)) {
                prologue.add(src.clone(Map.of()));
            } else return 0;
        }
        prologue.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, call.owner, call.name, call.desc, false));
        // original site: drop the consumed values instead of calling
        InsnList pops = new InsnList();
        for (int k = args.length - 1; k >= 0; k--) pops.add(new InsnNode(args[k].getSize() == 2 ? Opcodes.POP2 : Opcodes.POP));
        pops.add(new InsnNode(Opcodes.POP));
        mn.instructions.insertBefore(call, pops);
        mn.instructions.remove(call);
        mn.instructions.insert(prologue);
        return 1;
    }

    // ---------------------------------------------------------------- stack normalisation
    /**
     * The obfuscator keeps loop operands on the operand stack across jumps (javac never does), which makes
     * decompilers emit undeclared "var5"-style variables. At every jump target whose incoming stack is non-empty
     * the values are spilled into fresh locals on each incoming edge and reloaded at the target.
     * Targets with uninitialised objects (NEW before <init>) and exception handlers are left alone.
     */
    static int normalizeStack(ClassNode cn, MethodNode mn) {
        if (mn.instructions.size() == 0) return 0;
        org.objectweb.asm.tree.analysis.Frame<org.objectweb.asm.tree.analysis.BasicValue>[] bf;
        org.objectweb.asm.tree.analysis.Frame<org.objectweb.asm.tree.analysis.SourceValue>[] sf;
        try {
            bf = new org.objectweb.asm.tree.analysis.Analyzer<>(new org.objectweb.asm.tree.analysis.BasicInterpreter()).analyze(cn.name, mn);
            sf = new org.objectweb.asm.tree.analysis.Analyzer<>(new org.objectweb.asm.tree.analysis.SourceInterpreter(Opcodes.ASM9) {
                @Override public org.objectweb.asm.tree.analysis.SourceValue copyOperation(AbstractInsnNode insn, org.objectweb.asm.tree.analysis.SourceValue v) { return v; }
            }).analyze(cn.name, mn);
        } catch (Exception e) { return 0; }
        Set<LabelNode> handlers = new HashSet<>();
        for (TryCatchBlockNode t : mn.tryCatchBlocks) handlers.add(t.handler);
        Set<LabelNode> targets = new LinkedHashSet<>();
        for (AbstractInsnNode i : mn.instructions) {
            if (i instanceof JumpInsnNode j) targets.add(j.label);
            if (i instanceof TableSwitchInsnNode t) { targets.add(t.dflt); targets.addAll(t.labels); }
            if (i instanceof LookupSwitchInsnNode t) { targets.add(t.dflt); targets.addAll(t.labels); }
        }
        Map<LabelNode, Type[]> plan = new LinkedHashMap<>();
        for (LabelNode l : targets) {
            if (handlers.contains(l)) continue;
            int idx = mn.instructions.indexOf(l);
            var f = bf[idx];
            if (f == null || f.getStackSize() == 0) continue;
            boolean uninit = false;
            Type[] types = new Type[f.getStackSize()];
            for (int s = 0; s < f.getStackSize(); s++) {
                var bv = f.getStack(s);
                if (bv == null || bv.getType() == null) { uninit = true; break; }
                types[s] = bv.getType();
                var srcs = sf[idx].getStack(s).insns;
                for (AbstractInsnNode src : srcs) if (src.getOpcode() == Opcodes.NEW) uninit = true;
                // in a constructor, a parameter-derived reference may be the still-uninitialised "this"
                if (mn.name.equals("<init>") && srcs.isEmpty() && types[s].getSort() >= Type.ARRAY) uninit = true;
            }
            if (!uninit) plan.put(l, types);
        }
        if (plan.isEmpty()) return 0;
        Map<AbstractInsnNode, Boolean> reachable = new IdentityHashMap<>();
        AbstractInsnNode[] arr = mn.instructions.toArray();
        for (int i = 0; i < arr.length; i++) reachable.put(arr[i], bf[i] != null);
        for (Map.Entry<LabelNode, Type[]> e : plan.entrySet()) {
            LabelNode l = e.getKey();
            Type[] types = e.getValue();
            int[] slots = new int[types.length];
            for (int s = 0; s < types.length; s++) { slots[s] = mn.maxLocals; mn.maxLocals += types[s].getSize(); }
            // reload at the target
            InsnList loads = new InsnList();
            for (int s = 0; s < types.length; s++) loads.add(new VarInsnNode(types[s].getOpcode(Opcodes.ILOAD), slots[s]));
            AbstractInsnNode prev = l.getPrevious();
            while (prev != null && prev.getOpcode() < 0) prev = prev.getPrevious();
            boolean fallsInto = prev != null && Boolean.TRUE.equals(reachable.get(prev)) && fallsThrough(prev.getOpcode());
            mn.instructions.insert(l, loads);
            // switch edges get a small spill block placed right before the target (keeps try ranges intact)
            InsnList switchSpills = new InsnList();
            for (AbstractInsnNode i : mn.instructions.toArray()) {
                if (i instanceof JumpInsnNode j && j.label == l && Boolean.TRUE.equals(reachable.get(j))) {
                    if (j.getOpcode() == Opcodes.GOTO) mn.instructions.insertBefore(j, stores(types, slots));
                    else {
                        // "IFxx L" -> "IF!xx skip; spill; GOTO L; skip:" inline at the jump site
                        LabelNode skip = new LabelNode();
                        InsnList inl = new InsnList();
                        inl.add(stores(types, slots));
                        inl.add(new JumpInsnNode(Opcodes.GOTO, l));
                        inl.add(skip);
                        j.setOpcode(invert(j.getOpcode()));
                        j.label = skip;
                        mn.instructions.insert(j, inl);
                    }
                } else if (i instanceof TableSwitchInsnNode t) {
                    if (t.dflt == l) t.dflt = trampoline(switchSpills, l, types, slots);
                    for (int k = 0; k < t.labels.size(); k++) if (t.labels.get(k) == l) t.labels.set(k, trampoline(switchSpills, l, types, slots));
                } else if (i instanceof LookupSwitchInsnNode t) {
                    if (t.dflt == l) t.dflt = trampoline(switchSpills, l, types, slots);
                    for (int k = 0; k < t.labels.size(); k++) if (t.labels.get(k) == l) t.labels.set(k, trampoline(switchSpills, l, types, slots));
                }
            }
            InsnList before = new InsnList();
            if (fallsInto) {
                before.add(stores(types, slots));
                if (switchSpills.size() > 0) before.add(new JumpInsnNode(Opcodes.GOTO, l));
            }
            before.add(switchSpills);
            mn.instructions.insertBefore(l, before);
        }
        return plan.size();
    }

    static int invert(int op) {
        return switch (op) {
            case Opcodes.IFEQ -> Opcodes.IFNE; case Opcodes.IFNE -> Opcodes.IFEQ;
            case Opcodes.IFLT -> Opcodes.IFGE; case Opcodes.IFGE -> Opcodes.IFLT;
            case Opcodes.IFGT -> Opcodes.IFLE; case Opcodes.IFLE -> Opcodes.IFGT;
            case Opcodes.IF_ICMPEQ -> Opcodes.IF_ICMPNE; case Opcodes.IF_ICMPNE -> Opcodes.IF_ICMPEQ;
            case Opcodes.IF_ICMPLT -> Opcodes.IF_ICMPGE; case Opcodes.IF_ICMPGE -> Opcodes.IF_ICMPLT;
            case Opcodes.IF_ICMPGT -> Opcodes.IF_ICMPLE; case Opcodes.IF_ICMPLE -> Opcodes.IF_ICMPGT;
            case Opcodes.IF_ACMPEQ -> Opcodes.IF_ACMPNE; case Opcodes.IF_ACMPNE -> Opcodes.IF_ACMPEQ;
            case Opcodes.IFNULL -> Opcodes.IFNONNULL; case Opcodes.IFNONNULL -> Opcodes.IFNULL;
            default -> throw new IllegalArgumentException("not a conditional jump: " + op);
        };
    }

    static boolean fallsThrough(int op) {
        return !(op == Opcodes.GOTO || (op >= Opcodes.IRETURN && op <= Opcodes.RETURN) || op == Opcodes.ATHROW
                || op == Opcodes.TABLESWITCH || op == Opcodes.LOOKUPSWITCH);
    }

    static InsnList stores(Type[] types, int[] slots) {
        InsnList l = new InsnList();
        for (int s = types.length - 1; s >= 0; s--) l.add(new VarInsnNode(types[s].getOpcode(Opcodes.ISTORE), slots[s]));
        return l;
    }

    static LabelNode trampoline(InsnList tramp, LabelNode target, Type[] types, int[] slots) {
        LabelNode t = new LabelNode();
        tramp.add(t);
        tramp.add(stores(types, slots));
        tramp.add(new JumpInsnNode(Opcodes.GOTO, target));
        return t;
    }

    // ---------------------------------------------------------------- analysis dump
    static void writeDump(Path dump) throws IOException {
        try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(dump))) {
            for (ClassNode cn : onyx.values()) {
                pw.printf("CLASS %s%s extends %s%s%n", cn.name, (cn.access & Opcodes.ACC_INTERFACE) != 0 ? " [interface]" :
                                (cn.access & Opcodes.ACC_ENUM) != 0 ? " [enum]" : (cn.access & Opcodes.ACC_ABSTRACT) != 0 ? " [abstract]" : "",
                        cn.superName, cn.interfaces.isEmpty() ? "" : " implements " + String.join(",", cn.interfaces));
                if (cn.outerClass != null) pw.printf("  enclosing: %s.%s%n", cn.outerClass, cn.outerMethod);
                for (InnerClassNode ic : cn.innerClasses)
                    if (ic.name.equals(cn.name)) pw.printf("  inner-of: %s (simple=%s)%n", ic.outerName, ic.innerName);
                if (cn.visibleAnnotations != null) for (AnnotationNode an : cn.visibleAnnotations) pw.printf("  @%s %s%n", an.desc, an.values);
                for (FieldNode f : cn.fields) pw.printf("  F %s %s%s%n", f.name, f.desc, f.value != null ? " = " + f.value : "");
                for (MethodNode m : cn.methods) {
                    StringBuilder ann = new StringBuilder();
                    if (m.visibleAnnotations != null) for (AnnotationNode an : m.visibleAnnotations) ann.append(" @").append(an.desc);
                    pw.printf("  M %s%s%s%n", m.name, m.desc, ann);
                }
                Set<String> strings = new LinkedHashSet<>();
                Set<String> refs = new TreeSet<>();
                for (MethodNode m : cn.methods)
                    for (AbstractInsnNode i : m.instructions) {
                        if (i instanceof LdcInsnNode l && l.cst instanceof String s) strings.add(s);
                        if (i instanceof MethodInsnNode mi && !mi.owner.startsWith("java/")) refs.add(mi.owner + "." + mi.name);
                        if (i instanceof FieldInsnNode fi && !fi.owner.startsWith("java/")) refs.add(fi.owner + "." + fi.name);
                    }
                if (!strings.isEmpty()) pw.println("  S " + strings);
                List<String> ext = refs.stream().filter(r -> !r.startsWith("client/")).limit(40).toList();
                if (!ext.isEmpty()) pw.println("  R " + ext);
                pw.println();
            }
        }
    }
}
