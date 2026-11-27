package client.onyx.ui.component;

import client.onyx.input.KeyTyped;
import client.onyx.input.MouseClick;

public final class KeyShortcuts {
    public static boolean method_2266(KeyTyped var0) {
        return method_2271(var0, 49);
    }

    public static boolean method_2267(KeyTyped var0) {
        return var0.method_2() || var0.method_1();
    }

    public static boolean method_2268(MouseClick var0) {
        int var2;
        return (var2 = var0.intValue2()) >= 32 && var2 != 127 && Character.isDefined(var2);
    }

    public static boolean method_2269(KeyTyped var0) {
        return var0.intValue() == 14 && !method_2267(var0) && !var0.method_4();
    }

    public static boolean method_2270(KeyTyped var0) {
        return method_2267(var0) || var0.method_4();
    }

    public static boolean method_2271(KeyTyped var0, int var1) {
        return var0.intValue() == var1 && method_2267(var0) && !var0.method_3() && !var0.method_4();
    }

    public static boolean method_2272(KeyTyped var0) {
        return method_2271(var0, 33);
    }

    public static boolean method_2273(KeyTyped var0) {
        return method_2271(var0, 31);
    }

    public static boolean method_2274(KeyTyped var0) {
        return method_2271(var0, 44);
    }

    public static boolean method_2275(KeyTyped var0) {
        return var0.intValue() == 28 || var0.intValue() == 156;
    }

    public static boolean method_2276(KeyTyped var0) {
        return method_2271(var0, 21) || method_2277(var0, 44);
    }

    public static boolean method_2277(KeyTyped var0, int var1) {
        return var0.intValue() == var1 && method_2267(var0) && var0.method_3() && !var0.method_4();
    }

    private KeyShortcuts() {
    }
}
