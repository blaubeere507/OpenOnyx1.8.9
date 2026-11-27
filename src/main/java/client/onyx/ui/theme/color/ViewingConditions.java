package client.onyx.ui.theme.color;

public final class ViewingConditions {
    public static final ViewingConditions INSTANCE = method_191(
        ColorUtils.doubleValueArray2, 63.66197723675813 * ColorUtils.method_197(50.0) / 100.0, 50.0, 2.0, false
    );
    public final double doubleValue7;
    public final double doubleValue2;
    public final double doubleValue;
    public final double doubleValue8;
    public final double doubleValue3;
    public final double doubleValue6;
    public final double doubleValue9;
    public final double doubleValue4;
    public final double doubleValue5;
    public final double[] doubleValueArray;

    private static double method_190(double var0, double var2, double var4) {
        return (1.0 - var4) * var0 + var4 * var2;
    }

    public static ViewingConditions method_191(double[] var0, double var1, double var3, double var5, boolean var7) {
        double var8 = var0[0] * 0.401288 + var0[1] * 0.650173 + var0[2] * -0.051461;
        double var10 = var0[0] * -0.250268 + var0[1] * 1.204414 + var0[2] * 0.045854;
        double var12 = var0[0] * -0.002079 + var0[1] * 0.048952 + var0[2] * 0.953127;
        double var21;
        if ((var5 = 0.8 + var5 / 10.0) >= 0.9) {
            var21 = method_190(0.59, 0.69, (var5 - 0.9) * 10.0);
        } else {
            var21 = method_190(0.525, 0.59, (var5 - 0.8) * 10.0);
        }

        double var23;
        if (var7) {
            var23 = 1.0;
        } else {
            var23 = var5 * (1.0 - 0.2777777777777778 * Math.exp((-var1 - 42.0) / 92.0));
        }

        double var16 = Math.clamp(var23, 0.0, 1.0);
        double[] var26 = new double[]{var16 * (100.0 / var8) + 1.0 - var16, var16 * (100.0 / var10) + 1.0 - var16, var16 * (100.0 / var12) + 1.0 - var16};
        double var37 = var16 = 1.0 / (5.0 * var1 + 1.0);
        var16 = var37 * var37 * var16 * var16;
        double var18 = 1.0 - var16;
        var1 = var16 * var1 + 0.1 * var18 * var18 * Math.cbrt(5.0 * var1);
        var3 = ColorUtils.method_197(var3) / var0[1];
        var16 = 1.48 + Math.sqrt(var3);
        var18 = 0.725 / Math.pow(var3, 0.2);
        double[] var32 = new double[]{
            Math.pow(var1 * var26[0] * var8 / 100.0, 0.42), Math.pow(var1 * var26[1] * var10 / 100.0, 0.42), Math.pow(var1 * var26[2] * var12 / 100.0, 0.42)
        };
        double[] var20 = new double[]{400.0 * var32[0] / (var32[0] + 27.13), 400.0 * var32[1] / (var32[1] + 27.13), 400.0 * var32[2] / (var32[2] + 27.13)};
        var8 = (2.0 * var20[0] + var20[1] + 0.05 * var20[2]) * var18;
        return new ViewingConditions(var3, var8, var18, var18, var21, var5, var1, Math.pow(var1, 0.25), var16, var26);
    }

    private ViewingConditions(
        double var1, double var3, double var5, double var7, double var9, double var11, double var13, double var15, double var17, double[] var19
    ) {
        this.doubleValue7 = var1;
        this.doubleValue2 = var3;
        this.doubleValue = var5;
        this.doubleValue8 = var7;
        this.doubleValue3 = var9;
        this.doubleValue6 = var11;
        this.doubleValue9 = var13;
        this.doubleValue4 = var15;
        this.doubleValue5 = var17;
        this.doubleValueArray = var19;
    }
}
