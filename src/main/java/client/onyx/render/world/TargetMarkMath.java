package client.onyx.render.world;

import client.onyx.ui.theme.color.ColorUtils;

public final class TargetMarkMath {
    public static final float FLOAT_0_61 = 0.6F;
    public static final float FLOAT_1_51 = 1.5F;
    public static final int INT_201 = 20;
    public static final float FLOAT_0_81 = 0.8F;
    public static final float FLOAT_0_71 = 0.7F;
    public static final int INT_181 = 18;
    public static final float FLOAT_0_11 = 0.1F;
    public static final float FLOAT_0_62 = 0.6F;
    public static final float FLOAT_0_21 = 0.2F;
    public static final float FLOAT_0_51 = 0.5F;

    public static float method_775(int var0, long var1, float var3) {
        return (float)Math.toRadians(var0 * 20 + method_778(var1, var3) * 0.3F);
    }

    public static float method_776(float var0, float var1) {
        return var0 * method_781(var1);
    }

    public static float method_777(float var0) {
        return 1.25F - 0.5F * method_781(var0);
    }

    public static float method_778(long var0, float var2) {
        return (float)(var0 % 360000L) / 2.5F + var2;
    }

    public static float method_779(float var0) {
        return var0 * 1.5F;
    }

    public static int method_780(int var0, float var1) {
        return ColorUtils.method_204(
            ColorUtils.method_213(var0),
            Math.min(255, (int)(ColorUtils.method_201(var0) * var1)),
            Math.min(255, (int)(ColorUtils.method_210(var0) * var1)),
            Math.min(255, (int)(ColorUtils.method_214(var0) * var1))
        );
    }

    public static float method_781(float var0) {
        return Math.clamp(var0, 0.0F, 1.0F);
    }

    public static float method_782(int var0, float var1) {
        var0 *= 20;
        return 0.1F + var1 * Math.abs((float)Math.sin(var0));
    }

    public static int method_783(int var0, int var1, float var2) {
        float var4 = method_781(var2);
        return ColorUtils.method_204(
            ColorUtils.method_213(var0),
            method_785(ColorUtils.method_201(var0), ColorUtils.method_201(var1), var4),
            method_785(ColorUtils.method_210(var0), ColorUtils.method_210(var1), var4),
            method_785(ColorUtils.method_214(var0), ColorUtils.method_214(var1), var4)
        );
    }

    public static int method_784(int var0, float var1) {
        float var3 = ColorUtils.method_213(var0) / 255.0F;
        return ColorUtils.method_209(var0, var3 * method_781(var1));
    }

    private TargetMarkMath() {
    }

    private static int method_785(int var0, int var1, float var2) {
        return Math.clamp((long)Math.round(var0 + (var1 - var0) * var2), 0, 255);
    }
}
