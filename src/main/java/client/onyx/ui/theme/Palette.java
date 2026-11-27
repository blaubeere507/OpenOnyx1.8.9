package client.onyx.ui.theme;

import client.onyx.ui.theme.color.ColorUtils;
import client.onyx.ui.theme.color.Hct;
import client.onyx.ui.theme.color.TonalPalette;

public record Palette(
    int intValue31,
    int intValue33,
    int intValue34,
    int intValue17,
    int intValue22,
    int intValue9,
    int intValue5,
    int intValue30,
    int intValue27,
    int intValue14,
    int intValue,
    int intValue3,
    int intValue13,
    int intValue23,
    int intValue20,
    int intValue18,
    int intValue32,
    int intValue15,
    int intValue28,
    int intValue7,
    int intValue2,
    int intValue26,
    int intValue6,
    int intValue4,
    int intValue21,
    int intValue19,
    int intValue11,
    int intValue8,
    int intValue12,
    int intValue16,
    int intValue10,
    int intValue24,
    int intValue25,
    int intValue29
) {
    private static final double DOUBLE_36_01 = 36.0;
    private static final double DOUBLE_56_01 = 56.0;
    private static final double DOUBLE_30_01 = 30.0;
    private static final double DOUBLE_50_01 = 50.0;
    private static final double DOUBLE_64_01 = 64.0;

    public static Palette method_170(int var0, float var1) {
        Hct var3;
        float[] var4;
        return method_172(
            (var3 = Hct.method_188(ColorUtils.method_202((var4 = ColorUtils.method_215(var0))[0], var4[1], 1.0F, 255))).getDoubleValue(),
            var3.getDoubleValue2(),
            var4[1],
            var4[2],
            var1
        );
    }

    private static double method_171(double var0, double var2) {
        return Math.clamp(var0 - var2 * var0 / 100.0, 0.0, 100.0);
    }

    public static Palette method_172(double var0, double var2, float var4, float var5, float var6) {
        double var7 = Math.clamp(var6, 0.0F, 1.0F) * Math.clamp(var4, 0.0F, 1.0F);
        double var9;
        double var11 = (var9 = 1.0 - Math.clamp(var5, 0.0F, 1.0F)) * 30.0;
        var9 *= 50.0;
        TonalPalette var18 = TonalPalette.method_193(var0, var2);
        TonalPalette var16 = TonalPalette.method_193(var0, var2 / 3.0);
        TonalPalette var15 = TonalPalette.method_193(var0 + 60.0, var2 / 2.0);
        TonalPalette var13 = TonalPalette.method_193(var0, 36.0 * var7);
        TonalPalette var3 = TonalPalette.method_193(var0, 56.0 * var7);
        TonalPalette var1 = TonalPalette.method_193(var0, 64.0 * var7);
        TonalPalette var19 = TonalPalette.method_193(25.0, 84.0);
        return new Palette(
            var18.method_195(method_171(80.0, var11)),
            var18.method_196(20),
            var18.method_195(method_171(30.0, var11)),
            var18.method_196(90),
            var16.method_195(method_171(80.0, var11)),
            var16.method_196(20),
            var16.method_195(method_171(30.0, var11)),
            var16.method_196(90),
            var15.method_195(method_171(80.0, var11)),
            var15.method_196(20),
            var15.method_195(method_171(30.0, var11)),
            var15.method_196(90),
            var19.method_196(80),
            var19.method_196(20),
            var19.method_196(30),
            var19.method_196(90),
            var3.method_195(method_171(6.0, var9)),
            var13.method_196(90),
            var1.method_195(method_171(30.0, var9)),
            var1.method_196(80),
            var3.method_195(method_171(6.0, var9)),
            var3.method_195(method_171(24.0, var9)),
            var3.method_195(method_171(4.0, var9)),
            var3.method_195(method_171(10.0, var9)),
            var3.method_195(method_171(12.0, var9)),
            var3.method_195(method_171(17.0, var9)),
            var3.method_195(method_171(22.0, var9)),
            var1.method_195(method_171(60.0, var9)),
            var1.method_195(method_171(30.0, var9)),
            var13.method_196(90),
            var13.method_196(20),
            var18.method_195(method_171(40.0, var11)),
            var13.method_196(0),
            var13.method_196(0)
        );
    }
}
