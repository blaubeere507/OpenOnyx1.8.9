package client.onyx.util;

import client.onyx.Onyx;
import java.io.File;
import java.net.URI;
import java.nio.file.Path;

public final class DesktopUtil {
    public static void method_1932(Path var0) {
        Path var1 = var0;

        try {
            Class var4;
            Object var2 = (var4 = Class.forName("java.awt.Desktop")).getMethod("getDesktop").invoke(null);
            var4.getMethod("open", File.class).invoke(var2, var1.toFile());
        } catch (Throwable var3) {
            method_1933(var0.toUri());
        }
    }

    public static void method_1933(URI var0) {
        URI var1 = var0;

        try {
            Class var4;
            Object var2 = (var4 = Class.forName("java.awt.Desktop")).getMethod("getDesktop").invoke(null);
            var4.getMethod("browse", URI.class).invoke(var2, var1);
        } catch (Throwable var3) {
            Onyx.LOGGER.error("Could not open {}", var0, var3);
        }
    }

    public static void method_1934(String var0) {
        method_1933(URI.create(var0));
    }

    private DesktopUtil() {
    }
}
