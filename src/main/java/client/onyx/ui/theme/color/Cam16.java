package client.onyx.ui.theme.color;

public final class Cam16 {
    public final double doubleValue;
    public final double doubleValue2;
    public final double doubleValue6;
    public final double doubleValue4;
    public final double doubleValue3;
    public final double doubleValue5;

    public static Cam16 method_217(int var0) {
        return method_220(ColorUtils.method_211(var0), ViewingConditions.INSTANCE);
    }

    private static double method_218(double var0, double var2) {
        var2 = Math.pow(var2 * Math.abs(var0) / 100.0, 0.42);
        return Math.signum(var0) * 400.0 * var2 / (var2 + 27.13);
    }

    public int method_219(ViewingConditions var1) {
        double var12;
        if (this.doubleValue2 != 0.0 && this.doubleValue6 != 0.0) {
            var12 = this.doubleValue2 / Math.sqrt(this.doubleValue6 / 100.0);
        } else {
            var12 = 0.0;
        }

        double var2 = Math.pow(var12 / Math.pow(1.64 - Math.pow(0.29, var1.doubleValue7), 0.73), 1.1111111111111112);
        double var4 = Math.toRadians(this.doubleValue);
        double var6 = 0.25 * (Math.cos(var4 + 2.0) + 3.8);
        double var8 = var1.doubleValue2 * Math.pow(this.doubleValue6 / 100.0, 1.0 / var1.doubleValue3 / var1.doubleValue5);
        var6 = var6 * 3846.153846153846 * var1.doubleValue6 * var1.doubleValue8;
        var8 /= var1.doubleValue;
        double var10 = Math.sin(var4);
        var4 = Math.cos(var4);
        double var16;
        var4 = (var16 = 23.0 * (var8 + 0.305) * var2 / (23.0 * var6 + 11.0 * var2 * var4 + 108.0 * var2 * var10)) * var4;
        var2 = var16 * var10;
        var6 = (460.0 * var8 + 451.0 * var4 + 288.0 * var2) / 1403.0;
        var10 = (460.0 * var8 - 891.0 * var4 - 261.0 * var2) / 1403.0;
        var2 = (460.0 * var8 - 220.0 * var4 - 6300.0 * var2) / 1403.0;
        var4 = method_222(var6, var1.doubleValue9) / var1.doubleValueArray[0];
        var6 = method_222(var10, var1.doubleValue9) / var1.doubleValueArray[1];
        var2 = method_222(var2, var1.doubleValue9) / var1.doubleValueArray[2];
        var8 = 1.86206786 * var4 - 1.01125463 * var6 + 0.14918677 * var2;
        var10 = 0.38752654 * var4 + 0.62144744 * var6 - 0.00897398 * var2;
        var2 = -0.0158415 * var4 - 0.03412294 * var6 + 1.04996444 * var2;
        return ColorUtils.method_216(var8, var10, var2);
    }

    public static Cam16 method_220(double[] var0, ViewingConditions var1) {
        double var2 = var0[0];
        double var4 = var0[1];
        double var6 = var0[2];
        double var8 = 0.401288 * var2 + 0.650173 * var4 - 0.051461 * var6;
        double var10 = -0.250268 * var2 + 1.204414 * var4 + 0.045854 * var6;
        var2 = -0.002079 * var2 + 0.048952 * var4 + 0.953127 * var6;
        var4 = method_218(var1.doubleValueArray[0] * var8, var1.doubleValue9);
        var6 = method_218(var1.doubleValueArray[1] * var10, var1.doubleValue9);
        var2 = method_218(var1.doubleValueArray[2] * var2, var1.doubleValue9);
        var8 = (11.0 * var4 - 12.0 * var6 + var2) / 11.0;
        var10 = (var4 + var6 - 2.0 * var2) / 9.0;
        double var12 = (20.0 * var4 + 20.0 * var6 + 21.0 * var2) / 20.0;
        var2 = (40.0 * var4 + 20.0 * var6 + var2) / 20.0;
        var6 = Math.toRadians(var4 = ColorUtils.method_205(Math.toDegrees(Math.atan2(var10, var8))));
        var2 *= var1.doubleValue;
        var2 = 100.0 * Math.pow(var2 / var1.doubleValue2, var1.doubleValue3 * var1.doubleValue5);
        double var16;
        if (var4 < 20.14) {
            var16 = var4 + 360.0;
        } else {
            var16 = var4;
        }

        double var14 = 0.25 * (Math.cos(Math.toRadians(var16) + 2.0) + 3.8);
        double var30;
        var10 = (
                var30 = Math.pow(3846.153846153846 * var14 * var1.doubleValue6 * var1.doubleValue8 * Math.hypot(var8, var10) / (var12 + 0.305), 0.9)
                    * Math.pow(1.64 - Math.pow(0.29, var1.doubleValue7), 0.73)
                    * Math.sqrt(var2 / 100.0)
            )
            * var1.doubleValue4;
        var12 = 1.7 * var2 / (1.0 + 0.007 * var2);
        var10 = 43.859649122807014 * Math.log1p(0.0228 * var10);
        return new Cam16(var4, var30, var2, var12, var10 * Math.cos(var6), var10 * Math.sin(var6));
    }

    public double method_221(Cam16 var1) {
        double var2 = this.doubleValue4 - var1.doubleValue4;
        double var4 = this.doubleValue3 - var1.doubleValue3;
        double var6 = this.doubleValue5 - var1.doubleValue5;
        return 1.41 * Math.pow(Math.sqrt(var2 * var2 + var4 * var4 + var6 * var6), 0.63);
    }

    private Cam16(double var1, double var3, double var5, double var7, double var9, double var11) {
        this.doubleValue = var1;
        this.doubleValue2 = var3;
        this.doubleValue6 = var5;
        this.doubleValue4 = var7;
        this.doubleValue3 = var9;
        this.doubleValue5 = var11;
    }

    private static double method_222(double var0, double var2) {
        double var4 = Math.max(0.0, 27.13 * Math.abs(var0) / (400.0 - Math.abs(var0)));
        return Math.signum(var0) * (100.0 / var2) * Math.pow(var4, 2.380952380952381);
    }

    public static Cam16 method_223(double var0, double var2, double var4) {
        ViewingConditions var6 = ViewingConditions.INSTANCE;
        double var7 = var2 * var6.doubleValue4;
        double var9 = 1.7 * var0 / (1.0 + 0.007 * var0);
        var7 = 43.859649122807014 * Math.log1p(0.0228 * var7);
        double var11 = Math.toRadians(var4);
        return new Cam16(var4, var2, var0, var9, var7 * Math.cos(var11), var7 * Math.sin(var11));
    }

    public int method_224() {
        return this.method_219(ViewingConditions.INSTANCE);
    }
}
