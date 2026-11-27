package client.onyx.render.world;

public final class TargetMarkState {
    private static final float FLOAT_260_01 = 260.0F;
    private static final float FLOAT_0_31 = 0.3F;
    private static final int INT_71 = 7;
    private static final float FLOAT_80_01 = 80.0F;
    private final float[] floatValueArray = new float[18];
    private final float[] floatValueArray2 = new float[18];
    private int intValue;

    public void method_759() {
        var var1 = this;
        int var3 = 0;

        for (int var2 = 0; var2 < var1.floatValueArray.length; var2 = var3) {
            var1.floatValueArray[var3] = 0.0F;
            var1.floatValueArray2[var3++] = 0.0F;
        }

        var1.intValue = 0;
    }

    private int method_760(int var1) {
        var var3 = this;
        int var2 = 0;

        for (int var4 = 0; var4 < var3.floatValueArray.length; var4 = ++var2) {
            int var5 = (var1 + var2) % var3.floatValueArray.length;
            if (var3.floatValueArray2[var5] <= 0.0F) {
                return var5;
            }
        }

        return var1;
    }

    public void method_761(float var1) {
        var var2 = this;
        int var4 = 0;

        for (int var3 = 0; var3 < var2.floatValueArray.length; var3 = ++var4) {
            if (!(var2.floatValueArray2[var4] <= 0.0F)) {
                var2.floatValueArray[var4] = var2.floatValueArray[var4] + Math.max(0.0F, var1);
                if (var2.floatValueArray[var4] >= var2.floatValueArray2[var4]) {
                    var2.floatValueArray2[var4] = 0.0F;
                }
            }
        }
    }

    public void method_762(int var1, float var2) {
        var var3 = this;
        var2 = 1.0F / Math.max(var2, 0.1F);
        var1 = Math.clamp((long)var1, 1, this.floatValueArray.length);
        int var4 = Math.max(1, this.floatValueArray.length / var1);
        int var7 = 0;

        for (int var6 = 0; var6 < var1; var6 = var7) {
            int var5 = var3.method_760((var3.intValue + var7 * var4) % var3.floatValueArray.length);
            var3.floatValueArray[var5] = -var7 * 80.0F * var2;
            float var10002 = 260.0F * var2;
            var7++;
            var3.floatValueArray2[var5] = var10002;
        }

        var3.intValue = (var3.intValue + 7) % var3.floatValueArray.length;
    }

    private static float method_763(float var0) {
        return var0 * var0 * (3.0F - 2.0F * var0);
    }

    public float method_764(int var1) {
        float var2;
        if ((var2 = this.floatValueArray2[var1]) <= 0.0F) {
            return 0.0F;
        } else {
            float var4;
            if ((var4 = this.floatValueArray[var1] / var2) <= 0.0F || var4 >= 1.0F) {
                return 0.0F;
            } else {
                return var4 < 0.3F ? method_763(var4 / 0.3F) : 1.0F - method_763((var4 - 0.3F) / 0.7F);
            }
        }
    }
}
