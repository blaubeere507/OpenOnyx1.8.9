package client.onyx.ui.component;

import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easings;
import client.onyx.render.ui.UiRenderer;
import client.onyx.ui.theme.Theme;

public final class ComponentHost {
    private final Animation animation = new Animation(0.0F);

    public void method_2219(UiRenderer var1, float var2, float var3, float var4, float var5, float var6, int var7) {
        float var9;
        if (!((var9 = this.animation.method_606()) <= 0.001F)) {
            var1.method_479(var2, var3, var4, var5, var6, Theme.method_180(var7, var9));
        }
    }

    public void method_2220(float var1, boolean var2, boolean var3) {
        float var5;
        if (var3) {
            var5 = 0.1F;
        } else if (var2) {
            var5 = 0.08F;
        } else {
            var5 = 0.0F;
        }

        if (this.animation.getFloatValue3() != var5) {
            Animation var8;
            float var9;
            float var10;
            if (!var3) {
                Animation var6 = this.animation;
                var10 = 150.0F;
                var9 = var5;
                var8 = var6;
            } else {
                var10 = 100.0F;
                var9 = var5;
                var8 = this.animation;
            }

            var8.method_603(var9, var10, Easings.easing6);
        }

        this.animation.method_605(var1);
    }
}
