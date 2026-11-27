package client.onyx.render.ui;

import client.onyx.Onyx;
import client.onyx.render.effect.FrostedGlass;

public final class PanelRenderer {
    public static boolean method_596() {
        return FrostedGlass.method_401() && Onyx.moduleManager != null && Onyx.moduleManager.clickGUI.frostedGlass.method_146();
    }

    public static void method_597(UiRenderer var0, float var1, float var2, float var3, float var4, float var5, int var6, boolean var7) {
        if (!method_600(var7)) {
            var0.method_479(var1, var2, var3, var4, var5, var6);
        } else {
            var0.method_472(var1, var2, var3, var4, var5, var6);
        }
    }

    public static void method_598(UiRenderer var0, float var1, float var2, float var3, float var4, float var5, int var6) {
        method_597(var0, var1, var2, var3, var4, var5, var6, true);
    }

    public static void method_599() {
        if (method_596()) {
            FrostedGlass.method_403();
        }
    }

    public static boolean method_600(boolean var0) {
        return var0 && method_596();
    }

    private PanelRenderer() {
    }

    public static void method_601(boolean var0) {
        if (method_600(var0)) {
            FrostedGlass.method_396();
        }
    }

    public static void method_602() {
        method_601(true);
    }
}
