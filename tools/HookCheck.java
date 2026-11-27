import org.objectweb.asm.*;

import java.nio.file.*;
import java.util.*;
import java.util.zip.*;

/**
 * Checks that every Minecraft -> client hook listed in docs/hooks.txt resolves in a client jar
 * (methods/fields looked up through superclasses and interfaces of the client classes).
 *
 * Usage: java -cp <asm> tools/HookCheck.java <client.jar> <hooks.txt>
 */
public class HookCheck {
    static final Map<String, Set<String>> members = new HashMap<>();
    static final Map<String, List<String>> supers = new HashMap<>();

    public static void main(String[] a) throws Exception {
        try (ZipInputStream z = new ZipInputStream(Files.newInputStream(Paths.get(a[0])))) {
            for (ZipEntry e; (e = z.getNextEntry()) != null; ) {
                if (!e.getName().endsWith(".class")) continue;
                ClassReader cr = new ClassReader(z.readAllBytes());
                Set<String> m = new HashSet<>();
                cr.accept(new ClassVisitor(Opcodes.ASM9) {
                    @Override public MethodVisitor visitMethod(int acc, String n, String d, String s, String[] x) { m.add(n + d); return null; }
                    @Override public FieldVisitor visitField(int acc, String n, String d, String s, Object v) { m.add(n); return null; }
                }, ClassReader.SKIP_CODE);
                members.put(cr.getClassName(), m);
                List<String> sup = new ArrayList<>(Arrays.asList(cr.getInterfaces()));
                if (cr.getSuperName() != null) sup.add(0, cr.getSuperName());
                supers.put(cr.getClassName(), sup);
            }
        }
        int ok = 0, bad = 0;
        for (String line : Files.readAllLines(Paths.get(a[1]))) {
            String[] t = line.split("\t");
            if (t.length < 2) continue;
            String[] w = t[1].split(" ", 2);
            String ref = w[1];
            boolean found;
            switch (w[0]) {
                case "call" -> { int dot = ref.lastIndexOf('.', ref.indexOf('(')); found = has(ref.substring(0, dot), ref.substring(dot + 1)); }
                case "field" -> { int dot = ref.lastIndexOf('.'); found = has(ref.substring(0, dot), ref.substring(dot + 1)); }
                case "new", "type" -> found = members.containsKey(ref);
                default -> { continue; }
            }
            if (found) ok++; else { bad++; System.out.println("MISSING " + line); }
        }
        System.out.println("hooks resolved: " + ok + ", missing: " + bad);
    }

    static boolean has(String owner, String member) {
        Deque<String> q = new ArrayDeque<>(List.of(owner));
        Set<String> seen = new HashSet<>();
        while (!q.isEmpty()) {
            String c = q.poll();
            if (!seen.add(c)) continue;
            Set<String> m = members.get(c);
            if (m == null) continue;
            if (m.contains(member)) return true;
            q.addAll(supers.getOrDefault(c, List.of()));
        }
        return false;
    }
}
