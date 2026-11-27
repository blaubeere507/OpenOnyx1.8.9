package client.onyx.render.anim;

import client.onyx.ui.theme.color.ColorUtils;

public final class Spring {
    private final Animation animation = new Animation(1.0F);
    private int intValue2;
    private int intValue;

    public Spring method_613(int var1, float var2, Easing var3) {
        if (var1 == this.intValue) {
            return this;
        } else {
            this.intValue2 = this.method_617();
            this.intValue = var1;
            this.animation.method_607(0.0F).method_603(1.0F, var2, var3);
            return this;
        }
    }

    public void method_614(float var1) {
        this.animation.method_605(var1);
    }

    public Spring(int var1) {
        this.intValue2 = var1;
        this.intValue = var1;
    }

    private static double[] method_615(int var0) {
        return new double[]{
            ColorUtils.method_200(ColorUtils.method_201(var0)),
            ColorUtils.method_200(ColorUtils.method_210(var0)),
            ColorUtils.method_200(ColorUtils.method_214(var0))
        };
    }

    public static int method_616(int var0, int var1, float var2) {
        double[] var7 = method_615(var0);
        double[] var4 = method_615(var1);
        int var5 = ColorUtils.method_206(var7[0] + (var4[0] - var7[0]) * var2);
        int var6 = ColorUtils.method_206(var7[1] + (var4[1] - var7[1]) * var2);
        int var8 = ColorUtils.method_206(var7[2] + (var4[2] - var7[2]) * var2);
        return ColorUtils.method_204(
            Math.round(ColorUtils.method_213(var0) + (ColorUtils.method_213(var1) - ColorUtils.method_213(var0)) * var2), var5, var6, var8
        );
    }

    public int method_617() {
        float var2;
        if ((var2 = this.animation.method_606()) <= 0.0F) {
            return this.intValue2;
        } else {
            return var2 >= 1.0F ? this.intValue : method_616(this.intValue2, this.intValue, var2);
        }
    }

    public Spring method_618(int var1) {
        this.intValue2 = var1;
        this.intValue = var1;
        this.animation.method_607(1.0F);
        return this;
    }
}
