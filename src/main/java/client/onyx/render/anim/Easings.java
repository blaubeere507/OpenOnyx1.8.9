package client.onyx.render.anim;

public final class Easings {
    public static final Easing easing8 = var0 -> var0;
    public static final Easing easing2 = method_612(0.2, 0.0, 0.0, 1.0);
    public static final Easing easing7 = method_612(0.05, 0.7, 0.1, 1.0);
    public static final Easing easing = method_612(0.3, 0.0, 0.8, 0.15);
    public static final Easing easing6 = method_612(0.2, 0.0, 0.0, 1.0);
    public static final Easing easing5 = method_612(0.0, 0.0, 0.0, 1.0);
    public static final Easing easing4 = method_612(0.3, 0.0, 1.0, 1.0);
    public static final Easing easing3 = method_612(0.4, 0.0, 0.2, 1.0);

    private static double method_609(double var0, double var2, double var4) {
        double var6 = var0;
        int var8 = 0;

        for (int var16 = 0; var16 < 8; var16 = var8) {
            double var9;
            if (Math.abs(var9 = method_611(var6, var2, var4) - var0) < 1.0E-6) {
                return var6;
            }

            double var11;
            if (Math.abs(var11 = method_610(var6, var2, var4)) < 1.0E-6) {
                break;
            }

            var8++;
            var6 -= var9 / var11;
        }

        double var22 = 0.0;
        double var10 = 1.0;
        var6 = var0;

        for (double var19 = var22; var19 < var10; var19 = var22) {
            double var12;
            if (Math.abs((var12 = method_611(var6, var2, var4)) - var0) < 1.0E-6) {
                return var6;
            }

            double var17;
            if (var12 < var0) {
                var22 = var6;
                var17 = var6;
            } else {
                var10 = var6;
                var17 = var22;
            }

            double var14;
            if (Math.abs((var14 = (var17 + var10) / 2.0) - var6) < 1.0E-9) {
                return var6;
            }

            var6 = var14;
        }

        return var6;
    }

    private static double method_610(double var0, double var2, double var4) {
        double var6 = 1.0 - var0;
        return 3.0 * var6 * var6 * var2 + 6.0 * var6 * var0 * (var4 - var2) + 3.0 * var0 * var0 * (1.0 - var4);
    }

    private static double method_611(double var0, double var2, double var4) {
        double var6 = 1.0 - var0;
        return 3.0 * var6 * var6 * var0 * var2 + 3.0 * var6 * var0 * var0 * var4 + var0 * var0 * var0;
    }

    private Easings() {
    }

    public static Easing method_612(double var0, double var2, double var4, double var6) {
        return var8 -> {
            if (var8 <= 0.0) {
                return 0.0;
            } else {
                return var8 >= 1.0 ? 1.0 : method_611(method_609(var8, var0, var4), var2, var6);
            }
        };
    }
}
