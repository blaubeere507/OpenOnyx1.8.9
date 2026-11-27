package client.onyx.ui.theme.color;

public final class Hct {
    private final double doubleValue;
    private final double doubleValue2;
    private final double doubleValue3;
    private final int intValue;

    public Hct method_183(double var1) {
        return method_185(this.doubleValue, this.doubleValue2, var1);
    }

    public Hct method_184(double var1) {
        return method_185(this.doubleValue, var1, this.doubleValue3);
    }

    private Hct(double var1, double var3, double var5, int var7) {
        this.doubleValue = var1;
        this.doubleValue2 = var3;
        this.doubleValue3 = var5;
        this.intValue = var7;
    }

    public static Hct method_185(double var0, double var2, double var4) {
        return method_188(method_187(var0, var2, var4));
    }

    public double getDoubleValue() {
        return this.doubleValue;
    }

    public double getDoubleValue3() {
        return this.doubleValue3;
    }

    private static Cam16 method_186(double var0, double var2, double var4) {
        double var6 = 0.0;
        double var8 = 100.0;
        double var10 = 1000.0;
        Cam16 var12 = null;

        while (Math.abs(var6 - var8) > 0.01) {
            double var13;
            int var15;
            double var16 = ColorUtils.method_198(var15 = Cam16.method_223(var13 = var6 + (var8 - var6) / 2.0, var2, var0).method_224());
            if (Math.abs(var4 - var16) < 0.2) {
                Cam16 var20;
                Cam16 var10000 = var20 = Cam16.method_217(var15);
                double var18;
                if ((var18 = var10000.method_221(Cam16.method_223(var10000.doubleValue6, var2, var0))) <= 1.0 && var18 <= var10) {
                    var10 = var18;
                    var12 = var20;
                }
            }

            if (var16 < var4) {
                var6 = var13;
            } else {
                var8 = var13;
            }
        }

        return var12;
    }

    public double getDoubleValue2() {
        return this.doubleValue2;
    }

    private static int method_187(double var0, double var2, double var4) {
        if (!(var2 < 1.0) && Math.round(var4) > 0L && Math.round(var4) < 100L) {
            var0 = ColorUtils.method_205(var0);
            double var6 = 0.0;
            double var8 = var2;
            var2 = var2;
            boolean var10 = true;
            Cam16 var11 = null;
            double var13 = var6;

            while (Math.abs(var13 - var8) >= 0.4) {
                Cam16 var12 = method_186(var0, var2, var4);
                if (var10) {
                    if (var12 != null) {
                        return var12.method_224();
                    }

                    var10 = false;
                    var2 = var6 + (var8 - var6) / 2.0;
                    var13 = var6;
                } else {
                    double var15;
                    if (var12 == null) {
                        var8 = var2;
                        var15 = var6;
                    } else {
                        var11 = var12;
                        var6 = var2;
                        var15 = var2;
                    }

                    var2 = var15 + (var8 - var6) / 2.0;
                    var13 = var6;
                }
            }

            return var11 == null ? ColorUtils.method_207(var4) : var11.method_224();
        } else {
            return ColorUtils.method_207(var4);
        }
    }

    public int getIntValue() {
        return this.intValue;
    }

    public static Hct method_188(int var0) {
        Cam16 var2 = Cam16.method_217(var0);
        return new Hct(var2.doubleValue, var2.doubleValue2, ColorUtils.method_198(var0), var0 | 0xFF000000);
    }

    public Hct method_189(double var1) {
        return method_185(var1, this.doubleValue2, this.doubleValue3);
    }
}
