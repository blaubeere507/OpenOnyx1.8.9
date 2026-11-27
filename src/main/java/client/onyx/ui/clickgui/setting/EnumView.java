package client.onyx.ui.clickgui.setting;

import client.onyx.input.KeyTyped;
import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easings;
import client.onyx.render.ui.UiRenderer;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.ui.component.RenderContext;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.Typography;

public class EnumView extends SettingView {
    private static final float FLOAT_3_012 = 3.0F;
    private static final float FLOAT_3_02 = 3.0F;
    private static final float FLOAT_16_01 = 16.0F;
    private static final float FLOAT_5_01 = 5.0F;
    private static final float FLOAT_3_03 = 3.0F;
    private final Animation animation3 = new Animation(3.0F);
    private final NumberSetting numberSetting;
    private boolean flag5;

    private double method_2537() {
        return !(this.numberSetting.getDoubleValue3() > 0.0)
            ? (this.numberSetting.getDoubleValue() - this.numberSetting.getDoubleValue2()) / 100.0
            : this.numberSetting.getDoubleValue3();
    }

    @Override
    public boolean method_2212(KeyTyped var1) {
        if (this.method_2577() && this.flag) {
            switch (var1.intValue()) {
                case 203:
                    this.numberSetting.setValue(this.numberSetting.getValue() - this.method_2537());
                    return true;
                case 205:
                    this.numberSetting.setValue(this.numberSetting.getValue() + this.method_2537());
                    return true;
                default:
                    return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public float method_2387(RenderContext var1) {
        return this.method_2571() - this.floatValue + 3.0F + 6.0F;
    }

    @Override
    public void method_2389(RenderContext var1, float var2) {
        UiRenderer var5;
        UiRenderer var10000 = var5 = var1.uiRenderer;
        float var21 = var1.uiRenderer.method_442(Typography.fontStyle2, this.numberSetting.method_150());
        this.method_2575(var1, this.floatValue4 - var21 - 12.0F);
        var10000.method_476(
            Typography.fontStyle2,
            this.numberSetting.method_150(),
            this.floatValue3 + this.floatValue4,
            this.method_2578(),
            this.method_2572(var1.method_2256().intValue7())
        );
        float var22 = this.method_2540();
        float var3 = (float)this.numberSetting.method_152();
        var3 = this.method_2541() + this.method_2538() * var3;
        float var6 = this.animation3.method_606() / 2.0F;
        float var7;
        if ((var7 = var3 - var6 - 5.0F) > this.floatValue3) {
            var5.method_479(this.floatValue3, var22 - 1.5F, var7 - this.floatValue3, 3.0F, Float.MAX_VALUE, this.method_2572(var1.method_2256().intValue31()));
        }

        if ((var7 = var3 + var6 + 5.0F) < this.floatValue3 + this.floatValue4) {
            var5.method_479(
                var7, var22 - 1.5F, this.floatValue3 + this.floatValue4 - var7, 3.0F, Float.MAX_VALUE, this.method_2572(var1.method_2256().intValue11())
            );
        }

        if (this.flag || this.flag5) {
            int var10004 = var1.method_2256().intValue31();
            UiRenderer var14;
            float var15;
            float var16;
            float var17;
            int var18;
            float var19;
            float var20;
            if (!this.flag5) {
                int var12 = var10004;
                float var11 = 11.0F;
                var20 = 0.08F;
                var19 = var2;
                var18 = var12;
                var17 = var11;
                var16 = var22;
                var15 = var3;
                var14 = var5;
            } else {
                var20 = 0.1F;
                var19 = var2;
                var18 = var10004;
                var17 = 11.0F;
                var16 = var22;
                var15 = var3;
                var14 = var5;
            }

            var14.method_489(var15, var16, var17, Theme.method_180(var18, var19 * var20));
        }

        var5.method_479(var3 - var6, var22 - 8.0F, this.animation3.method_606(), 16.0F, Float.MAX_VALUE, this.method_2572(var1.method_2256().intValue31()));
    }

    @Override
    public boolean method_2188(float var1, float var2, int var3, float var4, float var5) {
        if (!this.flag5) {
            return false;
        } else {
            this.method_2539(var1);
            return true;
        }
    }

    @Override
    public boolean method_2183(float var1, float var2, int var3) {
        if (!this.method_2577() || var3 != 0 || !this.method_2259(var1, var2)) {
            return false;
        } else if (var2 < this.method_2564()) {
            return false;
        } else {
            this.flag5 = true;
            this.method_2539(var1);
            return true;
        }
    }

    @Override
    public void method_2206(RenderContext var1) {
        super.method_2206(var1);
        float var3;
        if (this.flag5) {
            var3 = 1.5F;
        } else if (this.flag) {
            var3 = 4.5F;
        } else {
            var3 = 3.0F;
        }

        if (this.animation3.getFloatValue3() != var3) {
            this.animation3.method_603(var3, 150.0F, Easings.easing6);
        }

        this.animation3.method_605(var1.floatValue);
        if (this.flag5) {
            this.method_2539(var1.floatValue3);
        }
    }

    private float method_2538() {
        return this.floatValue4 - 6.0F;
    }

    private void method_2539(float var1) {
        this.numberSetting.method_148((var1 - this.method_2541()) / this.method_2538());
    }

    public EnumView(NumberSetting var1) {
        super(var1);
        this.numberSetting = var1;
    }

    private float method_2540() {
        return this.method_2571() + 1.5F;
    }

    @Override
    public boolean method_2189(float var1, float var2, int var3) {
        boolean var4 = this.flag5;
        this.flag5 = false;
        return var4;
    }

    private float method_2541() {
        return this.floatValue3 + 3.0F;
    }
}
