import java.nio.file.*;
import java.util.*;
import java.util.zip.*;

/**
 * Debug helper: builds a jar from <base.jar> where every class whose name starts with one of the given
 * prefixes (top-level class + its nested classes) is taken from <overlay.jar> instead.
 * Used to bisect behaviour differences between the remapped bytecode and the recompiled source.
 *
 * Usage: java tools/Hybrid.java <base.jar> <overlay.jar> <out.jar> <prefix>...
 */
public class Hybrid {
    public static void main(String[] a) throws Exception {
        Map<String, byte[]> base = read(a[0]), over = read(a[1]);
        List<String> prefixes = Arrays.asList(a).subList(3, a.length);
        int swapped = 0;
        for (Map.Entry<String, byte[]> e : over.entrySet())
            for (String p : prefixes)
                if (e.getKey().startsWith(p)) { base.put(e.getKey(), e.getValue()); swapped++; break; }
        try (ZipOutputStream z = new ZipOutputStream(Files.newOutputStream(Paths.get(a[2])))) {
            for (Map.Entry<String, byte[]> e : base.entrySet()) {
                z.putNextEntry(new ZipEntry(e.getKey()));
                z.write(e.getValue());
                z.closeEntry();
            }
        }
        System.out.println("swapped " + swapped + " classes");
    }

    static Map<String, byte[]> read(String p) throws Exception {
        Map<String, byte[]> m = new TreeMap<>();
        try (ZipInputStream z = new ZipInputStream(Files.newInputStream(Paths.get(p)))) {
            for (ZipEntry e; (e = z.getNextEntry()) != null; ) if (!e.isDirectory()) m.put(e.getName(), z.readAllBytes());
        }
        return m;
    }
}
