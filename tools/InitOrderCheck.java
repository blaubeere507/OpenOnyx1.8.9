import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

import java.nio.file.*;
import java.util.*;
import java.util.zip.*;

/**
 * Compares field initialisation order between the reference bytecode (remapped original) and the jar
 * recompiled from source: the order of first PUTSTATIC per field in <clinit>, and of first PUTFIELD on
 * "this" in each constructor. Decompilers emit field initialisers in declaration order, so a shuffled
 * declaration order silently changes initialisation order (null reads in static initialisers).
 *
 * Usage: java -cp <asm> tools/InitOrderCheck.java <reference.jar> <rebuilt.jar>
 */
public class InitOrderCheck {
    public static void main(String[] a) throws Exception {
        Map<String, Map<String, List<String>>> ref = read(a[0]), built = read(a[1]);
        int diffs = 0;
        for (String cls : new TreeSet<>(ref.keySet())) {
            Map<String, List<String>> r = ref.get(cls), b = built.getOrDefault(cls, Map.of());
            for (String m : r.keySet()) {
                List<String> ro = r.get(m), bo = b.getOrDefault(m, List.of());
                // only fields present in both, relative order
                List<String> common = new ArrayList<>(ro);
                common.retainAll(bo);
                List<String> bc = new ArrayList<>(bo);
                bc.retainAll(ro);
                if (!common.equals(bc)) {
                    diffs++;
                    System.out.println(cls + " " + m + "\n   original: " + common + "\n   rebuilt : " + bc);
                }
            }
        }
        System.out.println("classes/methods with changed initialisation order: " + diffs);
    }

    static Map<String, Map<String, List<String>>> read(String jar) throws Exception {
        Map<String, Map<String, List<String>>> out = new HashMap<>();
        try (ZipInputStream z = new ZipInputStream(Files.newInputStream(Paths.get(jar)))) {
            for (ZipEntry e; (e = z.getNextEntry()) != null; ) {
                if (!e.getName().endsWith(".class")) continue;
                ClassNode cn = new ClassNode();
                new ClassReader(z.readAllBytes()).accept(cn, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
                Map<String, List<String>> per = new LinkedHashMap<>();
                for (MethodNode mn : cn.methods) {
                    if (!mn.name.equals("<clinit>") && !mn.name.equals("<init>")) continue;
                    int op = mn.name.equals("<clinit>") ? Opcodes.PUTSTATIC : Opcodes.PUTFIELD;
                    LinkedHashSet<String> order = new LinkedHashSet<>();
                    for (AbstractInsnNode i : mn.instructions)
                        if (i instanceof FieldInsnNode f && f.getOpcode() == op && f.owner.equals(cn.name)) order.add(f.name);
                    if (order.size() > 1) per.put(mn.name + mn.desc, new ArrayList<>(order));
                }
                out.put(cn.name, per);
            }
        }
        return out;
    }
}
