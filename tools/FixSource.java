import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.*;

/**
 * Source post-processing for Vineflower output.
 *
 * The obfuscator separates "NEW T" from its constructor call with unrelated code, which Vineflower prints as
 *     T v = new T;
 *     ...
 *     v./* $VF: Unable to resugar constructor *&#47;<init>(args);
 * This rewrites it to a plain declaration plus "v = new T(args);". Expression temporaries (var10002 ...)
 * are handled the same way.
 *
 * Usage: java tools/FixSource.java <source root>
 */
public class FixSource {
    static final Pattern DECL = Pattern.compile("^(\\s*)((?:final\\s+)?[\\w.$<>\\[\\], ?]+?)\\s+(\\w+)\\s*=\\s*new\\s+([\\w.$]+(?:<[^;()]*>)?)\\s*;\\s*$");
    static final Pattern TEMP = Pattern.compile("^(\\s*)(\\w+)\\s*=\\s*new\\s+([\\w.$]+(?:<[^;()]*>)?)\\s*;\\s*$");
    static final Pattern CALL = Pattern.compile("(\\w+)\\./\\* \\$VF: Unable to resugar constructor \\*/<init>\\(");

    static final Pattern BARE_VAR = Pattern.compile("(?m)^\\s*var\\d+;\\s*\\R");

    /** Hand fixes for the few methods Vineflower cannot type. Keyed by file, applied verbatim. */
    static final Map<String, String[]> KNOWN = Map.of(
        "client/onyx/setting/SettingHistory.java", new String[]{
            "if ((var0 = (<unknown>)undoStack.peek()) != null && var0 != null && var0.target() == var0 && var3 - var0.time() <= 450L) {\n"
          + "                undoStack.pop();\n"
          + "                undoStack.push(new SettingHistory.Entry(var0, var0.undo(), var2, var3));",
            "SettingHistory.Entry last; // hand-fixed: obfuscated local reuse confused the decompiler\n"
          + "            if ((last = undoStack.peek()) != null && var0 != null && last.target() == var0 && var3 - last.time() <= 450L) {\n"
          + "                undoStack.pop();\n"
          + "                undoStack.push(new SettingHistory.Entry(var0, last.undo(), var2, var3));"
        });

    // "for (Iterator var2 = var3 = list.iterator(); ...)" - Vineflower never declares var3
    static final Pattern CHAINED_FOR = Pattern.compile("^(\\s*)for \\(((?:final\\s+)?[\\w.$<>\\[\\]]+)\\s+(var\\d+)\\s*=\\s*(var\\d+)\\s*=");

    static String declareChainedForVars(String src) {
        String[] lines = src.split("\n", -1);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lines.length; i++) {
            Matcher m = CHAINED_FOR.matcher(lines[i]);
            if (m.find()) {
                String inner = m.group(4);
                // declared already somewhere above in the same method? look back to the method start
                boolean declared = false;
                for (int k = i - 1; k >= 0 && !lines[k].matches("^ {4}\\S.*"); k--)
                    if (lines[k].matches(".*[\\w>\\]]\\s+" + inner + "\\s*[;=,)].*")) { declared = true; break; }
                if (!declared) sb.append(m.group(1)).append(m.group(2)).append(' ').append(inner).append(";\n");
            }
            sb.append(lines[i]);
            if (i < lines.length - 1) sb.append('\n');
        }
        return sb.toString();
    }

    // "SettingSectionView var4 = this;" in a generic class erases every generic member type: use "var"
    static final Pattern THIS_ALIAS = Pattern.compile("^(\\s*)(?:final\\s+)?[\\w.$]+\\s+(var\\d+)\\s*=\\s*this;\\s*$");
    // "EnumSetSetting var4 = var1;" where var1 is a parameter "EnumSetSetting<E> var1": keep the parameter's type
    static final Pattern PARAM_ALIAS = Pattern.compile("^(\\s*)([\\w.$]+)\\s+(var\\d+)\\s*=\\s*(var\\d+);\\s*$");
    static final Pattern METHOD_HEADER = Pattern.compile("^ {4}(?: {4})*\\S.*\\((.*)\\)\\s*(?:throws [\\w.$, ]+)?\\{\\s*$");

    static String keepGenericAliases(String src) {
        String[] lines = src.split("\n", -1);
        Map<String, String> params = new HashMap<>();
        for (int i = 0; i < lines.length; i++) {
            Matcher h = METHOD_HEADER.matcher(lines[i]);
            if (h.matches() && !lines[i].contains(" = ") && !lines[i].strip().startsWith("for") && !lines[i].strip().startsWith("if")) {
                params.clear();
                for (String p : splitParams(h.group(1))) {
                    p = p.strip().replaceFirst("^final\\s+", "");
                    int sp = p.lastIndexOf(' ');
                    if (sp > 0) params.put(p.substring(sp + 1), p.substring(0, sp).strip());
                }
            }
            Matcher t = THIS_ALIAS.matcher(lines[i]);
            if (t.matches()) { lines[i] = t.group(1) + "var " + t.group(2) + " = this;"; continue; }
            Matcher pa = PARAM_ALIAS.matcher(lines[i]);
            if (pa.matches()) {
                String ptype = params.get(pa.group(4));
                if (ptype != null && ptype.contains("<") && ptype.substring(0, ptype.indexOf('<')).equals(pa.group(2)))
                    lines[i] = pa.group(1) + ptype + " " + pa.group(3) + " = " + pa.group(4) + ";";
            }
        }
        return String.join("\n", lines);
    }

    static List<String> splitParams(String s) {
        List<String> r = new ArrayList<>();
        int depth = 0, start = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '<') depth++;
            else if (c == '>') depth--;
            else if (c == ',' && depth == 0) { r.add(s.substring(start, i)); start = i + 1; }
        }
        if (start < s.length()) r.add(s.substring(start));
        return r;
    }

    public static void main(String[] a) throws Exception {
        Path root = Paths.get(a[0]);
        int files = 0, fixes = 0;
        List<Path> all;
        try (Stream<Path> s = Files.walk(root)) { all = s.filter(p -> p.toString().endsWith(".java")).toList(); }
        for (Path p : all) {
            String src0 = Files.readString(p, StandardCharsets.UTF_8);
            // a bare "var12;" expression statement (left over from a DUP) is never valid Java: drop it
            String src = Arrays.stream(src0.split("\n", -1)).filter(l -> !l.strip().matches("var\\d+;")).collect(Collectors.joining("\n"));
            String rel = root.relativize(p).toString().replace('\\', '/');
            String[] known = KNOWN.get(rel);
            if (known != null) {
                if (src.contains(known[0])) { src = src.replace(known[0], known[1]); fixes++; }
                else if (!src.contains(known[1])) System.out.println("FixSource: known fix for " + rel + " no longer matches - check it by hand");
            }
            src = declareChainedForVars(src);
            src = keepGenericAliases(src);
            if (!src.equals(src0)) { Files.writeString(p, src, StandardCharsets.UTF_8); fixes++; }
            if (!src.contains("Unable to resugar constructor")) continue;
            List<String> lines = new ArrayList<>(Arrays.asList(src.split("\n", -1)));
            Map<String, String> typeOfVar = new HashMap<>();
            for (int i = 0; i < lines.size(); i++) {
                String l = lines.get(i);
                Matcher d = DECL.matcher(l);
                if (d.matches() && !d.group(2).equals("return")) {
                    typeOfVar.put(d.group(3), d.group(4));
                    lines.set(i, d.group(1) + d.group(2) + " " + d.group(3) + ";");
                    continue;
                }
                Matcher t = TEMP.matcher(l);
                if (t.matches()) {
                    typeOfVar.put(t.group(2), t.group(3));
                    lines.set(i, null); // the temporary is assigned by the rewritten constructor call
                    continue;
                }
                Matcher c = CALL.matcher(l);
                StringBuilder sb = new StringBuilder();
                int last = 0;
                boolean changed = false;
                while (c.find()) {
                    String v = c.group(1), type = typeOfVar.get(v);
                    if (type == null) continue;
                    sb.append(l, last, c.start()).append(v).append(" = new ").append(type).append('(');
                    last = c.end();
                    changed = true;
                    fixes++;
                }
                if (changed) {
                    sb.append(l.substring(last));
                    lines.set(i, sb.toString());
                }
            }
            // temporaries that were declared elsewhere (e.g. "StringBuilder var10002;") still need a declaration
            String out = lines.stream().filter(Objects::nonNull).filter(l -> !l.strip().matches("var\\d+;")).collect(Collectors.joining("\n"));
            Files.writeString(p, out, StandardCharsets.UTF_8);
            files++;
        }
        System.out.println("FixSource: " + fixes + " constructor calls rewritten in " + files + " files");
    }
}
