package client.onyx.render.ui;

import java.util.List;

public final class HealthBarAnimator {
    private static final float[] floatValueArray = new float[]{0.25F, 0.5F, 0.75F};
    public static final HealthBarAnimator.Ghost ghost = new HealthBarAnimator.Ghost(0.0F, 0.0F, Float.NaN, Float.NaN);
    private static final int INT_N18855598911 = -1885559891;
    private static final float FLOAT_4_01 = 4.0F;
    private static final int INT_14687645691 = 1468764569;
    private static final float FLOAT_7_01 = 7.0F;
    private static final float FLOAT_1_01 = 1.0F;
    private static final float FLOAT_5_01 = 5.0F;

    public static HealthBarAnimator.Ghost method_859(float var0, float var1, float var2, float var3, float var4, float var5, float var6) {
        return method_861(var0, var1, var2, var3, var4, var5, var6, List.of());
    }

    private HealthBarAnimator() {
    }

    private static HealthBarAnimator.Bar method_860(float var0, float var1, float var2, float var3, List<float[]> var4, boolean var5) {
        float var6 = var3;
        float var7 = Math.max(var3, var2 - var3 - var1);
        float var8;
        float var9 = var8 = Math.clamp(var0, var3, var7);
        float var10 = Float.NaN;
        float var11 = 8.0F;
        float[] var22 = new float[]{var3, var2 * 0.25F, var2 * 0.5F, var2 * 0.75F, var2 - var3};
        float[] var25 = new float[]{
            var3,
            Math.clamp(var22[1] - var1 / 2.0F, var3, var7),
            Math.clamp(var22[2] - var1 / 2.0F, var3, var7),
            Math.clamp(var22[3] - var1 / 2.0F, var3, var7),
            var7
        };
        int var12 = 0;

        for (int var15 = 0; var15 < var25.length; var15 = ++var12) {
            float var13;
            if (!((var13 = Math.abs(var8 - var25[var12])) >= var11) && !(var13 > 7.0F)) {
                var9 = var25[var12];
                var10 = var22[var12];
                var11 = var13;
            }
        }

        for (float[] var29 : var4) {
            float var17;
            if (!var5) {
                var17 = var29[1];
            } else {
                var17 = var29[0];
            }

            float var19;
            if (!var5) {
                var19 = var29[3];
            } else {
                var19 = var29[2];
            }

            float[] var27 = new float[]{var17, var17 + var19 - var1, var17 + var19 / 2.0F - var1 / 2.0F, var17 + var19, var17 - var1};
            float[] var23 = new float[]{var17, var17 + var19, var17 + var19 / 2.0F, var17 + var19, var17};
            int var26 = 0;

            for (int var20 = 0; var20 < var27.length; var20 = ++var26) {
                float var14;
                if (!(var27[var26] < var6) && !(var27[var26] > var7) && !((var14 = Math.abs(var8 - var27[var26])) >= var11) && !(var14 > 7.0F)) {
                    var9 = var27[var26];
                    var10 = var23[var26];
                    var11 = var14;
                }
            }
        }

        return new HealthBarAnimator.Bar(var9, var10);
    }

    public static HealthBarAnimator.Ghost method_861(float var0, float var1, float var2, float var3, float var4, float var5, float var6, List<float[]> var7) {
        HealthBarAnimator.Bar var9 = method_860(var0, var2, var4, var6, var7, true);
        HealthBarAnimator.Bar var10 = method_860(var1, var3, var5, var6, var7, false);
        return new HealthBarAnimator.Ghost(var9.floatValue(), var10.floatValue(), var9.floatValue2(), var10.floatValue2());
    }

    public static void method_862(UiRenderer var0, float var1, float var2, HealthBarAnimator.Ghost var3) {
        UiRenderer var10 = var0;
        float[] var4 = floatValueArray;
        int var5 = floatValueArray.length;
        int var6 = 0;

        for (int var8 = 0; var8 < var5; var8 = var6) {
            float var7 = var4[var6];
            method_863(var10, var1 * var7, var2, 1468764569);
            float var10001 = var2 * var7;
            var6++;
            method_864(var10, var10001, var1, 1468764569);
        }

        if (Float.isFinite(var3.floatValue3())) {
            method_863(var10, var3.floatValue3(), var2, -1885559891);
        }

        if (Float.isFinite(var3.floatValue())) {
            method_864(var10, var3.floatValue(), var1, -1885559891);
        }
    }

    private static void method_863(UiRenderer var0, float var1, float var2, int var3) {
        UiRenderer var4 = var0;
        float var6 = 4.0F;

        for (float var5 = 4.0F; var5 < var2 - 4.0F; var5 = var6 += 9.0F) {
            var4.method_461(var1, var6, var1, Math.min(var6 + 5.0F, var2 - 4.0F), 1.0F, var3);
        }
    }

    private static void method_864(UiRenderer var0, float var1, float var2, int var3) {
        UiRenderer var4 = var0;
        float var6 = 4.0F;

        for (float var5 = 4.0F; var5 < var2 - 4.0F; var5 = var6 += 9.0F) {
            var4.method_461(var6, var1, Math.min(var6 + 5.0F, var2 - 4.0F), var1, 1.0F, var3);
        }
    }

    private record Bar(float floatValue, float floatValue2) {
    }

    public record Ghost(float floatValue4, float floatValue2, float floatValue3, float floatValue) {
    }
}
