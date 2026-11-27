package client.onyx.input;

import java.util.Arrays;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public final class KeyboardState {
    private static final boolean[] flagArray = new boolean[256];
    private static boolean flag = true;

    private KeyboardState() {
    }

    private static boolean method_5(int var0, int var1) {
        return method_6(var0) || method_6(var1);
    }

    private static boolean method_6(int var0) {
        if (!flagArray[var0]) {
            return false;
        } else if (Keyboard.isKeyDown(var0)) {
            return true;
        } else {
            flagArray[var0] = false;
            return false;
        }
    }

    public static boolean method_7() {
        return method_5(42, 54);
    }

    public static void method_8() {
        Arrays.fill(flagArray, false);
    }

    public static boolean method_9() {
        return method_5(29, 157);
    }

    public static void method_10() {
        boolean var0;
        if (!(var0 = Display.isActive()) || !flag) {
            method_8();
        }

        flag = var0;
    }

    public static boolean method_11() {
        return method_5(56, 184);
    }

    public static boolean method_12() {
        return method_5(219, 220);
    }

    public static void method_13(int var0, boolean var1) {
        if (var0 > 0 && var0 < flagArray.length) {
            flagArray[var0] = var1;
        }
    }
}
