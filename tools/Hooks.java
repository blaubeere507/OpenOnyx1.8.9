import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

import java.nio.file.*;
import java.util.*;
import java.util.zip.*;

/**
 * Lists every place where the patched Minecraft jar calls into the client package.
 * Usage: java -cp <asm> tools/Hooks.java <mc.jar> <prefix> [out.txt]
 */
public class Hooks {
    public static void main(String[] a) throws Exception {
        String prefix = a[1];
        List<String> lines = new ArrayList<>();
        try (ZipInputStream zin = new ZipInputStream(Files.newInputStream(Paths.get(a[0])))) {
            for (ZipEntry e; (e = zin.getNextEntry()) != null; ) {
                if (!e.getName().endsWith(".class")) continue;
                ClassNode cn = new ClassNode();
                new ClassReader(zin.readAllBytes()).accept(cn, ClassReader.SKIP_FRAMES);
                for (MethodNode mn : cn.methods) {
                    Set<String> seen = new LinkedHashSet<>();
                    for (AbstractInsnNode i : mn.instructions) {
                        String s = null;
                        if (i instanceof MethodInsnNode m && m.owner.startsWith(prefix)) s = "call " + m.owner + "." + m.name + m.desc;
                        else if (i instanceof FieldInsnNode f && f.owner.startsWith(prefix)) s = "field " + f.owner + "." + f.name;
                        else if (i instanceof TypeInsnNode t && t.desc.startsWith(prefix)) s = (t.getOpcode() == Opcodes.NEW ? "new " : "type ") + t.desc;
                        if (s != null) seen.add(s);
                    }
                    for (String s : seen) lines.add(cn.name + "." + mn.name + mn.desc + "\t" + s);
                }
                for (FieldNode f : cn.fields) if (f.desc.contains(prefix)) lines.add(cn.name + "#" + f.name + "\tfield-type " + f.desc);
            }
        }
        if (a.length > 2) Files.write(Paths.get(a[2]), lines);
        else lines.forEach(System.out::println);
        System.err.println(lines.size() + " hook references");
    }
}
