package client.onyx.ui.component;

import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easings;
import client.onyx.render.ui.UiRenderer;
import client.onyx.ui.theme.Palette;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.Typography;

public class MenuButton extends Component {
    private static final float FLOAT_12_01 = 12.0F;
    private static final float FLOAT_0_161 = 0.16F;
    private static final float FLOAT_0_621 = 0.62F;
    private final Animation animation = new Animation(0.0F);
    private float floatValue5 = 1.0F;
    private final String string;
    private final Runnable runnable;

    public void method_2506(float var1) {
        this.floatValue5 = var1;
    }

    @Override
    public void method_2206(RenderContext var1) {
        super.method_2206(var1);
        float var3;
        if (this.flag) {
            var3 = 1.0F;
        } else {
            var3 = 0.0F;
        }

        if (this.animation.getFloatValue3() != var3) {
            this.animation.method_603(var3, 150.0F, Easings.easing6);
        }

        this.animation.method_605(var1.floatValue);
    }

    @Override
    public boolean method_2183(float var1, float var2, int var3) {
        if (var3 == 0 && this.flag2 && this.method_2259(var1, var2)) {
            this.runnable.run();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void method_2186(RenderContext var1) {
        UiRenderer var8 = var1.uiRenderer;
        Palette var5;
        Palette var11 = var5 = var1.method_2256();
        float var4 = this.animation.method_606();
        float var3 = this.floatValue4 / this.floatValue5;
        float var2 = this.floatValue2 / this.floatValue5;
        int var6 = var11.intValue30();
        var8.method_440(CaretBlink.method_2290());
        var8.method_441();
        var8.method_481().method_650(this.floatValue3, this.floatValue).method_652(this.floatValue5, this.floatValue5);
        var8.method_485(0.0F, 0.0F, var3, var2, 12.0F, 1);
        var8.method_472(0.0F, 0.0F, var3, var2, 12.0F, Theme.method_180(var5.intValue5(), 0.62F + 0.16F * var4));
        if (var4 > 0.001F) {
            var8.method_479(0.0F, 0.0F, var3, var2, 12.0F, Theme.method_180(var6, 0.08F * var4));
        }

        var8.method_484(Typography.fontStyle14, this.string, var3 / 2.0F, var2 / 2.0F, var6);
        var8.method_435();
        var8.method_430();
        if (this.flag) {
            var8.method_465();
        }
    }

    public MenuButton(String var1, Runnable var2) {
        this.string = var1;
        this.runnable = var2;
    }
}
