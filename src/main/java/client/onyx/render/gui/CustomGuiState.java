package client.onyx.render.gui;

import client.onyx.Onyx;
import client.onyx.module.hud.CustomGui;
import java.util.function.Predicate;

public final class CustomGuiState {
    private static float floatValue = 16.0F;
    private static boolean flag;
    private static long longValue;
    private static final float FLOAT_100_01 = 100.0F;
    private static final float FLOAT_16_01 = 16.0F;
    public static final int INT_221 = 22;

    public static boolean method_963() {
        return method_972(var0 -> var0.chatSettings.method_61());
    }

    public static boolean method_964() {
        return method_972(var0 -> var0.statusBarSettings.method_61());
    }

    public static float getFloatValue() {
        return floatValue;
    }

    public static float method_965() {
        long var0 = System.nanoTime();
        float var2;
        if (longValue == 0L) {
            var2 = 16.0F;
        } else {
            var2 = Math.min((float)(var0 - longValue) / 1000000.0F, 100.0F);
        }

        floatValue = var2;
        longValue = var0;
        return floatValue;
    }

    public static boolean method_966() {
        return method_972(var0 -> var0.chatSettings.method_61() && var0.chatSettings.smoothCaret.method_146());
    }

    public static boolean isFlag() {
        return flag;
    }

    public static void method_967() {
        flag = true;
    }

    public static float method_968() {
        CustomGui var0;
        return (var0 = method_977()) != null && var0.isToggled() && var0.shadowSettings.method_61() ? var0.shadowSettings.keepDim.method_151() / 100.0F : 1.0F;
    }

    public static void method_969() {
        flag = false;
    }

    public static boolean method_970() {
        return method_972(var0 -> var0.openAnimationSettings.centeredContainers.method_146());
    }

    public static boolean method_971() {
        return method_972(var0 -> var0.bossbarSettings.method_61());
    }

    private static boolean method_972(Predicate<CustomGui> var0) {
        CustomGui var2;
        return (var2 = method_977()) != null && var2.isToggled() && var0.test(var2);
    }

    private CustomGuiState() {
    }

    public static boolean method_973() {
        return method_972(var0 -> var0.hotbarSettings.method_61());
    }

    public static boolean method_974() {
        return method_972(var0 -> var0.titleSettings.method_61());
    }

    public static boolean method_975() {
        return method_972(var0 -> var0.scoreboardSettings.method_61());
    }

    public static boolean method_976() {
        CustomGui var0;
        return (var0 = method_977()) != null && var0.isToggled();
    }

    public static CustomGui method_977() {
        return Onyx.moduleManager == null ? null : Onyx.moduleManager.customGui;
    }
}
