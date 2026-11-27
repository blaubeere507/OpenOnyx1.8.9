package client.onyx.ui.theme;

import client.onyx.ui.theme.color.ColorUtils;

public final class Theme {
    private static int intValue = -10006364;
    private static float floatValue2 = 0.25F;
    private static Palette palette = Palette.method_170(-10006364, 0.25F);
    private static float floatValue = 1.0F;
    public static final float FLOAT_0_121 = 0.12F;
    public static final float FLOAT_0_251 = 0.25F;
    public static final float FLOAT_0_11 = 0.1F;
    public static final float FLOAT_0_081 = 0.08F;
    public static final float FLOAT_0_381 = 0.38F;
    public static final float FLOAT_0_161 = 0.16F;
    public static final int INT_N100063641 = -10006364;
    public static final float FLOAT_0_12 = 0.1F;

    public static float getFloatValue() {
        return floatValue;
    }

    public static Palette getPalette() {
        return palette;
    }

    public static void method_174(float var0) {
        floatValue2 = Math.clamp(var0, 0.0F, 1.0F);
        palette = Palette.method_170(intValue, floatValue2);
    }

    public static float getFloatValue2() {
        return floatValue2;
    }

    public static int getIntValue() {
        return intValue;
    }

    public static void method_175(float var0) {
        floatValue = Math.clamp(var0, 0.3F, 1.5F);
    }

    public static int method_176(float var0) {
        return method_180(palette.intValue25(), var0);
    }

    public static int method_177(int var0) {
        return method_180(var0, 0.38F);
    }

    private Theme() {
    }

    public static void method_178(int var0) {
        intValue = var0 | 0xFF000000;
        palette = Palette.method_170(intValue, floatValue2);
    }

    public static int method_179(int var0, boolean var1, boolean var2) {
        if (var2) {
            return method_180(var0, 0.1F);
        } else {
            return var1 ? method_180(var0, 0.08F) : 0;
        }
    }

    public static int method_180(int var0, float var1) {
        return ColorUtils.method_209(var0, var1);
    }
}
