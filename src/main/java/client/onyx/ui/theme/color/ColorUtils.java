package client.onyx.ui.theme.color;

public final class ColorUtils {
    public static final double[] doubleValueArray2 = new double[]{95.047, 100.0, 108.883};
    private static final double[][] doubleValueArray = new double[][]{
        {0.41233895, 0.35762064, 0.18051042}, {0.2126, 0.7152, 0.0722}, {0.01932141, 0.11916382, 0.95034478}
    };
    private static final double[][] doubleValueArray3 = new double[][]{
        {3.2413774792388685, -1.5376652402851851, -0.49885366846268053},
        {-0.9691452513005321, 1.8758853451067872, 0.04156585616912061},
        {0.05562093689691305, -0.20395524564742123, 1.0571799111220335}
    };

    public static double method_197(double var0) {
        return 100.0 * method_203((var0 + 16.0) / 116.0);
    }

    public static double method_198(int var0) {
        return method_208(method_211(var0)[1]);
    }

    public static int method_199(int var0, int var1) {
        return var0 & 16777215 | (var1 & 0xFF) << 24;
    }

    public static double method_200(int var0) {
        double var1;
        return (var1 = var0 / 255.0) <= 0.040449936 ? var1 / 12.92 * 100.0 : Math.pow((var1 + 0.055) / 1.055, 2.4) * 100.0;
    }

    public static int method_201(int var0) {
        return var0 >> 16 & 0xFF;
    }

    public static int method_202(float var0, float var1, float var2, int var3) {
        var0 = (float)method_205(var0) / 60.0F;
        float var11;
        float var5 = (var11 = var2 * Math.clamp(var1, 0.0F, 1.0F)) * (1.0F - Math.abs(var0 % 2.0F - 1.0F));
        var2 -= var11;
        float var6;
        float var7;
        int var8;
        switch ((int)var0 % 6) {
            case 0:
                var0 = var11;
                var6 = var5;
                var7 = 0.0F;
                var8 = var3;
                break;
            case 1:
                var0 = var5;
                var6 = var11;
                var7 = 0.0F;
                var8 = var3;
                break;
            case 2:
                var0 = 0.0F;
                var6 = var11;
                var7 = var5;
                var8 = var3;
                break;
            case 3:
                var0 = 0.0F;
                var6 = var5;
                var7 = var11;
                var8 = var3;
                break;
            case 4:
                var0 = var5;
                var6 = 0.0F;
                var7 = var11;
                var8 = var3;
                break;
            default:
                var0 = var11;
                var6 = 0.0F;
                var7 = var5;
                var8 = var3;
        }

        return method_204(var8, Math.round((var0 + var2) * 255.0F), Math.round((var6 + var2) * 255.0F), Math.round((var7 + var2) * 255.0F));
    }

    private static double method_203(double var0) {
        double var2 = 0.008856451679035631;
        double var4 = 903.2962962962963;
        double var6;
        return (var6 = var0 * var0 * var0) > var2 ? var6 : (116.0 * var0 - 16.0) / var4;
    }

    public static int method_204(int var0, int var1, int var2, int var3) {
        return (var0 & 0xFF) << 24 | (var1 & 0xFF) << 16 | (var2 & 0xFF) << 8 | var3 & 0xFF;
    }

    public static double method_205(double var0) {
        double var2;
        return (var2 = var0 % 360.0) < 0.0 ? var2 + 360.0 : var2;
    }

    public static int method_206(double var0) {
        double var2;
        double var6;
        if ((var2 = var0 / 100.0) <= 0.0031308) {
            var6 = var2 * 12.92;
        } else {
            var6 = 1.055 * Math.pow(var2, 0.4166666666666667) - 0.055;
        }

        return Math.clamp(Math.round(var6 * 255.0), 0, 255);
    }

    public static int method_207(double var0) {
        int var2 = method_206(method_197(var0));
        return method_204(255, var2, var2, var2);
    }

    public static double method_208(double var0) {
        return method_212(var0 / 100.0) * 116.0 - 16.0;
    }

    public static int method_209(int var0, float var1) {
        return method_199(var0, Math.round(Math.clamp(var1, 0.0F, 1.0F) * 255.0F));
    }

    public static int method_210(int var0) {
        return var0 >> 8 & 0xFF;
    }

    public static double[] method_211(int var0) {
        double var1 = method_200(method_201(var0));
        double var3 = method_200(method_210(var0));
        double var5 = method_200(method_214(var0));
        return new double[]{
            doubleValueArray[0][0] * var1 + doubleValueArray[0][1] * var3 + doubleValueArray[0][2] * var5,
            doubleValueArray[1][0] * var1 + doubleValueArray[1][1] * var3 + doubleValueArray[1][2] * var5,
            doubleValueArray[2][0] * var1 + doubleValueArray[2][1] * var3 + doubleValueArray[2][2] * var5
        };
    }

    private static double method_212(double var0) {
        double var2 = 0.008856451679035631;
        double var4 = 903.2962962962963;
        return var0 > var2 ? Math.cbrt(var0) : (var4 * var0 + 16.0) / 116.0;
    }

    public static int method_213(int var0) {
        return var0 >>> 24;
    }

    private ColorUtils() {
    }

    public static int method_214(int var0) {
        return var0 & 0xFF;
    }

    public static float[] method_215(int var0) {
        float var5 = method_201(var0) / 255.0F;
        float var4 = method_210(var0) / 255.0F;
        float var3 = method_214(var0) / 255.0F;
        float var2 = Math.max(var5, Math.max(var4, var3));
        float var15 = Math.min(var5, Math.min(var4, var3));
        float var16 = var2 - var15;
        float var6 = 0.0F;
        if (var16 > 0.0F) {
            if (var2 == var5) {
                var6 = 60.0F * ((var4 - var3) / var16 % 6.0F);
            } else if (var2 != var4) {
                float var7 = 60.0F;
                var6 = var7 * ((var5 - var4) / var16 + 4.0F);
            } else {
                var6 = 60.0F * ((var3 - var5) / var16 + 2.0F);
            }
        }

        float[] var10000 = new float[]{(float)method_205(var6), 0.0F, 0.0F};
        float[] var11;
        float[] var12;
        byte var13;
        float var14;
        if (var2 != 0.0F) {
            byte var10 = 1;
            float[] var9 = var10000;
            float[] var8 = var10000;
            var14 = var16 / var2;
            var13 = var10;
            var12 = var9;
            var11 = var8;
        } else {
            var14 = 0.0F;
            var13 = 1;
            var12 = var10000;
            var11 = var10000;
        }

        var12[var13] = var14;
        var11[2] = var2;
        return var11;
    }

    public static int method_216(double var0, double var2, double var4) {
        int var6 = method_206(doubleValueArray3[0][0] * var0 + doubleValueArray3[0][1] * var2 + doubleValueArray3[0][2] * var4);
        int var7 = method_206(doubleValueArray3[1][0] * var0 + doubleValueArray3[1][1] * var2 + doubleValueArray3[1][2] * var4);
        int var1 = method_206(doubleValueArray3[2][0] * var0 + doubleValueArray3[2][1] * var2 + doubleValueArray3[2][2] * var4);
        return method_204(255, var6, var7, var1);
    }
}
