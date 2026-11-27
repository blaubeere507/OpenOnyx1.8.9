package client.onyx.ui.component;

import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easings;

public final class CaretBlink {
    private static final Animation animation = new Animation(1.0F);
    private static long longValue;

    public static void method_2289(boolean var0) {
        long var1 = System.nanoTime();
        float var4;
        if (longValue == 0L) {
            var4 = 16.0F;
        } else {
            var4 = Math.min((float)(var1 - longValue) / 1000000.0F, 100.0F);
        }

        longValue = var1;
        float var5;
        if (var0) {
            var5 = 1.0F;
        } else {
            var5 = 0.0F;
        }

        if (animation.getFloatValue3() != var5) {
            animation.method_603(var5, 300.0F, Easings.easing2);
        }

        animation.method_605(var4);
    }

    public static float method_2290() {
        return animation.method_606();
    }

    private CaretBlink() {
    }
}
