package client.onyx.util.player;

public class InputKeysUtil {
    public static boolean method_1967(InputKeys var0) {
        return var0.flag5() || var0.flag6() || var0.flag3() || var0.flag4();
    }

    public static InputKeys method_1968(InputKeys var0, boolean var1, boolean var2, boolean var3, boolean var4, boolean var5, boolean var6, boolean var7) {
        return new InputKeys(var1, var2, var3, var4, var5, var6, var7);
    }

    public static boolean method_1969(MovementInputState var0) {
        return var0.flag2() || var0.flag4() || var0.flag() || var0.flag3();
    }
}
