import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.CheckClassAdapter;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.zip.*;

/**
 * Runs ASM's bytecode verifier (SimpleVerifier, real type hierarchy) over every class of a jar.
 * Classes are only loaded for hierarchy lookups, never initialised, so no game code runs.
 *
 * Usage: java -cp <asm jars> tools/Verify.java <jar to check> <classpath entries...>
 */
public class Verify {
    public static void main(String[] a) throws Exception {
        List<URL> urls = new ArrayList<>();
        for (int i = 0; i < a.length; i++) urls.add(Paths.get(a[i]).toUri().toURL());
        try (URLClassLoader loader = new URLClassLoader(urls.toArray(new URL[0]), ClassLoader.getPlatformClassLoader());
             ZipInputStream zin = new ZipInputStream(Files.newInputStream(Paths.get(a[0])))) {
            int ok = 0, bad = 0;
            for (ZipEntry e; (e = zin.getNextEntry()) != null; ) {
                if (!e.getName().endsWith(".class")) continue;
                StringWriter sw = new StringWriter();
                try {
                    CheckClassAdapter.verify(new ClassReader(zin.readAllBytes()), loader, false, new PrintWriter(sw));
                } catch (Throwable t) { sw.write(t.toString()); }
                if (sw.toString().isBlank()) ok++;
                else { bad++; if (bad <= 10) System.out.println("FAIL " + e.getName() + "\n" + sw.toString().lines().limit(6).reduce("", (x, y) -> x + "   " + y + "\n")); }
            }
            System.out.println("verified ok: " + ok + ", failed: " + bad);
        }
    }
}
