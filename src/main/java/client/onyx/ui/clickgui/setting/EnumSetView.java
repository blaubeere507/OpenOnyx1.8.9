package client.onyx.ui.clickgui.setting;

import client.onyx.input.KeyTyped;
import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easings;
import client.onyx.render.ui.UiRenderer;
import client.onyx.setting.Range;
import client.onyx.setting.impl.RangeSetting;
import client.onyx.ui.component.RenderContext;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.Typography;

public class EnumSetView extends SettingView {
    private static final float FLOAT_5_01 = 5.0F;
    private static final float FLOAT_16_01 = 16.0F;
    private static final float FLOAT_3_012 = 3.0F;
    private static final float FLOAT_3_02 = 3.0F;
    private static final float FLOAT_3_03 = 3.0F;
    private final Animation animation22 = new Animation(3.0F);
    private final Animation animation3 = new Animation(3.0F);
    private int intValue2 = -1;
    private int intValue = -1;
    private final RangeSetting rangeSetting;
    private float floatValue5;

    private void method_2579(float var1) {
        double var2 = this.rangeSetting.method_97((var1 - this.method_2581()) / this.method_2587());
        double var7;
        if (this.intValue2 != 0) {
            var7 = this.rangeSetting.method_92();
        } else {
            var7 = this.rangeSetting.method_106();
        }

        this.rangeSetting.setValue(new Range(var7, var2));
        if (var2 < var7) {
            this.intValue2 = 0;
        } else {
            if (var2 > var7) {
                this.intValue2 = 1;
            }
        }
    }

    @Override
    public float method_2387(RenderContext var1) {
        return this.method_2571() - this.floatValue + 3.0F + 6.0F;
    }

    public EnumSetView(RangeSetting var1) {
        super(var1);
        this.rangeSetting = var1;
    }

    private float method_2580() {
        return this.method_2571() + 1.5F;
    }

    @Override
    public void method_2389(RenderContext var1, float var2) {
        UiRenderer var7;
        UiRenderer var10000 = var7 = var1.uiRenderer;
        float var10 = var1.uiRenderer.method_442(Typography.fontStyle2, this.rangeSetting.method_96());
        this.method_2575(var1, this.floatValue4 - var10 - 12.0F);
        var10000.method_476(
            Typography.fontStyle2,
            this.rangeSetting.method_96(),
            this.floatValue3 + this.floatValue4,
            this.method_2578(),
            this.method_2572(var1.method_2256().intValue7())
        );
        float var11 = this.method_2580();
        float var5 = this.method_2583(this.rangeSetting.method_92());
        float var6 = this.method_2583(this.rangeSetting.method_106());
        float var3 = this.animation22.method_606() / 2.0F;
        float var8 = this.animation3.method_606() / 2.0F;
        float var9;
        if ((var9 = var5 - var3 - 5.0F) > this.floatValue3) {
            var7.method_479(this.floatValue3, var11 - 1.5F, var9 - this.floatValue3, 3.0F, Float.MAX_VALUE, this.method_2572(var1.method_2256().intValue11()));
        }

        var3 = var5 + var3 + 5.0F;
        if ((var9 = var6 - var8 - 5.0F) > var3) {
            var7.method_479(var3, var11 - 1.5F, var9 - var3, 3.0F, Float.MAX_VALUE, this.method_2572(var1.method_2256().intValue31()));
        }

        if ((var3 = var6 + var8 + 5.0F) < this.floatValue3 + this.floatValue4) {
            var7.method_479(
                var3, var11 - 1.5F, this.floatValue3 + this.floatValue4 - var3, 3.0F, Float.MAX_VALUE, this.method_2572(var1.method_2256().intValue11())
            );
        }

        this.method_2586(var1, var2, var5, var11, this.animation22.method_606(), 0);
        this.method_2586(var1, var2, var6, var11, this.animation3.method_606(), 1);
    }

    private float method_2581() {
        return this.floatValue3 + 3.0F;
    }

    @Override
    public boolean method_2189(float var1, float var2, int var3) {
        boolean var4;
        if (this.intValue2 >= 0) {
            var4 = true;
        } else {
            var4 = false;
        }

        this.intValue2 = -1;
        return var4;
    }

    private void method_2582(Animation var1, int var2) {
        float var4;
        if (this.intValue2 == var2) {
            var4 = 1.5F;
        } else if (this.intValue == var2) {
            var4 = 4.5F;
        } else {
            var4 = 3.0F;
        }

        if (var1.getFloatValue3() != var4) {
            var1.method_603(var4, 150.0F, Easings.easing6);
        }
    }

    private float method_2583(double var1) {
        return this.method_2581() + this.method_2587() * (float)this.rangeSetting.method_108(var1);
    }

    private double method_2584() {
        return !(this.rangeSetting.getDoubleValue3() > 0.0)
            ? (this.rangeSetting.getDoubleValue2() - this.rangeSetting.getDoubleValue()) / 100.0
            : this.rangeSetting.getDoubleValue3();
    }

    private int method_2585(float var1) {
        float var2 = Math.abs(var1 - this.method_2583(this.rangeSetting.method_92()));
        float var4 = Math.abs(var1 - this.method_2583(this.rangeSetting.method_106()));
        return var2 <= var4 ? 0 : 1;
    }

    @Override
    public void method_2206(RenderContext var1) {
        super.method_2206(var1);
        this.intValue = -1;
        this.floatValue5 = var1.floatValue3;
        if (Math.abs(var1.floatValue2 - this.method_2580()) <= 16.0F) {
            float var2 = Math.abs(var1.floatValue3 - this.method_2583(this.rangeSetting.method_92()));
            float var3 = Math.abs(var1.floatValue3 - this.method_2583(this.rangeSetting.method_106()));
            if (Math.min(var2, var3) <= 8.0F) {
                EnumSetView var5;
                byte var6;
                if (!(var2 <= var3)) {
                    var6 = 1;
                    var5 = this;
                } else {
                    var6 = 0;
                    var5 = this;
                }

                var5.intValue = var6;
            }
        }

        this.method_2582(this.animation22, 0);
        this.method_2582(this.animation3, 1);
        this.animation22.method_605(var1.floatValue);
        this.animation3.method_605(var1.floatValue);
        if (this.intValue2 >= 0) {
            this.method_2579(var1.floatValue3);
        }
    }

    @Override
    public boolean method_2183(float var1, float var2, int var3) {
        if (!this.method_2577() || var3 != 0 || !this.method_2259(var1, var2)) {
            return false;
        } else if (var2 < this.method_2564()) {
            return false;
        } else {
            this.intValue2 = this.method_2585(var1);
            this.method_2579(var1);
            return true;
        }
    }

    private void method_2586(RenderContext var1, float var2, float var3, float var4, float var5, int var6) {
        UiRenderer var21 = var1.uiRenderer;
        if (this.intValue == var6 || this.intValue2 == var6) {
            int var10004 = var1.method_2256().intValue31();
            UiRenderer var14;
            float var15;
            float var16;
            float var17;
            int var18;
            float var19;
            float var20;
            if (this.intValue2 != var6) {
                int var12 = var10004;
                float var11 = 11.0F;
                var20 = 0.08F;
                var19 = var2;
                var18 = var12;
                var17 = var11;
                var16 = var4;
                var15 = var3;
                var14 = var21;
            } else {
                var20 = 0.1F;
                var19 = var2;
                var18 = var10004;
                var17 = 11.0F;
                var16 = var4;
                var15 = var3;
                var14 = var21;
            }

            var14.method_489(var15, var16, var17, Theme.method_180(var18, var19 * var20));
        }

        var21.method_479(var3 - var5 / 2.0F, var4 - 8.0F, var5, 16.0F, Float.MAX_VALUE, this.method_2572(var1.method_2256().intValue31()));
    }

    private float method_2587() {
        return this.floatValue4 - 6.0F;
    }

    @Override
    public boolean method_2188(float var1, float var2, int var3, float var4, float var5) {
        if (this.intValue2 < 0) {
            return false;
        } else {
            this.method_2579(var1);
            return true;
        }
    }

    @Override
    public boolean method_2212(KeyTyped var1) {
        if (this.method_2577() && this.flag) {
            double var4 = switch (var1.intValue()) {
                case 203 -> -this.method_2584();
                case 205 -> this.method_2584();
                default -> 0.0;
            };
            if (var4 == 0.0) {
                return false;
            } else {
                if (this.method_2585(this.floatValue5) != 0) {
                    this.rangeSetting.method_98(this.rangeSetting.method_106() + var4);
                } else {
                    this.rangeSetting.method_100(this.rangeSetting.method_92() + var4);
                }

                return true;
            }
        } else {
            return false;
        }
    }
}
