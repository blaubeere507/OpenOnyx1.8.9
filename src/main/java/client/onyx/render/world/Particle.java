package client.onyx.render.world;

import client.onyx.ui.theme.color.ColorUtils;

public final class Particle {
    public static final float FLOAT_0_50196081 = 0.5019608F;
    private static final long LONG_5001 = 500L;

    public static double method_722(double var0) {
        return var0 * 0.01;
    }

    public static float method_723(long var0, long var2) {
        float var4;
        if (var0 < 500L) {
            var4 = (float)var0 / 500.0F;
        } else if (var0 < var2) {
            var4 = 1.0F;
        } else if (var0 < var2 + 500L) {
            var4 = 1.0F - (float)(var0 - var2) / 500.0F;
        } else {
            var4 = 0.0F;
        }

        return 0.5019608F * Math.clamp(var4, 0.0F, 1.0F);
    }

    private static int method_724(int var0, int var1, float var2) {
        return Math.round(var0 + (var1 - var0) * var2);
    }

    public static long method_725(long var0, double var2) {
        return Math.round(var0 / Math.max(0.01, var2));
    }

    public static int method_726(int var0, int var1, int var2, long var3) {
        int var5;
        if ((var0 = (int)Math.floorMod(var3 / 10L + var2 * 50L, 360L)) >= 180) {
            var5 = 360 - var0;
        } else {
            var5 = var0;
        }

        int var8;
        float var9 = (var8 = var5 * 2) / 360.0F;
        return ColorUtils.method_204(
            255,
            method_724(ColorUtils.method_201(var0), ColorUtils.method_201(var1), var9),
            method_724(ColorUtils.method_210(var0), ColorUtils.method_210(var1), var9),
            method_724(ColorUtils.method_214(var0), ColorUtils.method_214(var1), var9)
        );
    }

    private Particle() {
    }
}
