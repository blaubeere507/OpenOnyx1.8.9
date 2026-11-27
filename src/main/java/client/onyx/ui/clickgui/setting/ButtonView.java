package client.onyx.ui.clickgui.setting;

import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easings;
import client.onyx.render.ui.UiRenderer;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.ui.component.ComponentHost;
import client.onyx.ui.component.Icons;
import client.onyx.ui.component.RenderContext;
import client.onyx.ui.theme.Theme;

public class ButtonView extends SettingView {
    private static final float FLOAT_13_01 = 13.0F;
    private static final float FLOAT_15_012 = 15.0F;
    private final ComponentHost componentHost = new ComponentHost();
    private final Animation animation3 = new Animation(0.0F);
    private final BooleanSetting booleanSetting;
    private boolean flag5;
    private boolean flag23;

    @Override
    public float method_2387(RenderContext var1) {
        return this.method_2565();
    }

    private float method_2612() {
        return this.floatValue3 + this.floatValue4 - 15.0F;
    }

    private float method_2613() {
        return this.method_2578() - 7.5F;
    }

    @Override
    public void method_2206(RenderContext var1) {
        super.method_2206(var1);
        if (!this.flag5) {
            this.flag5 = true;
            this.flag23 = this.booleanSetting.getValue();
            Animation var3;
            float var4;
            if (!this.flag23) {
                Animation var2 = this.animation3;
                var4 = 0.0F;
                var3 = var2;
            } else {
                var4 = 1.0F;
                var3 = this.animation3;
            }

            var3.method_607(var4);
        }

        if (this.booleanSetting.getValue() != this.flag23) {
            this.flag23 = this.booleanSetting.getValue();
            Animation var6;
            float var7;
            if (!this.flag23) {
                Animation var5 = this.animation3;
                var7 = 0.0F;
                var6 = var5;
            } else {
                var7 = 1.0F;
                var6 = this.animation3;
            }

            var6.method_603(var7, 200.0F, Easings.easing2);
        }

        this.animation3.method_605(var1.floatValue);
        this.componentHost.method_2220(var1.floatValue, this.flag, this.flag3);
    }

    public ButtonView(BooleanSetting var1) {
        super(var1);
        this.booleanSetting = var1;
    }

    @Override
    public boolean method_2183(float var1, float var2, int var3) {
        if (this.method_2577() && var3 == 0 && this.method_2259(var1, var2)) {
            this.booleanSetting.method_145();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void method_2389(RenderContext var1, float var2) {
        UiRenderer var9 = var1.uiRenderer;
        float var6 = this.method_2612();
        float var4 = this.method_2613();
        float var5 = this.animation3.method_606();
        this.method_2575(var1, var6 - 10.0F - this.floatValue3);
        this.componentHost.method_2219(var9, var6 - 6.0F, var4 - 6.0F, 27.0F, 27.0F, Float.MAX_VALUE, this.method_2572(var1.method_2256().intValue15()));
        if (var5 > 0.01F) {
            float var7 = (1.0F - var5) * 15.0F / 2.0F;
            var9.method_479(var6 + var7, var4 + var7, 15.0F - var7 * 2.0F, 15.0F - var7 * 2.0F, 4.0F, this.method_2572(var1.method_2256().intValue31()));
        }

        if (var5 < 0.99F) {
            var9.method_494(var6, var4, 15.0F, 15.0F, 4.0F, 1.5F, this.method_2572(var1.method_2256().intValue7()));
        }

        if (var5 > 0.15F) {
            float var8 = Math.clamp((var5 - 0.15F) / 0.85F, 0.0F, 1.0F);
            Icons.method_2226(
                var9, var6 + 7.5F, var4 + 7.5F, 13.0F * (0.7F + 0.3F * var8), this.method_2572(Theme.method_180(var1.method_2256().intValue33(), var8))
            );
        }
    }
}
