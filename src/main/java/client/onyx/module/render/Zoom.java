package client.onyx.module.render;

import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.setting.impl.KeybindSetting;
import client.onyx.setting.impl.NumberSetting;

public final class Zoom extends Module {
    public KeybindSetting zoomKey = new KeybindSetting("Zoom Key", 47);
    public NumberSetting distance = new NumberSetting("Distance", 4.0, 1.5, 12.0, 0.5).method_147("x");
    public NumberSetting animation = new NumberSetting("Animation", 180.0, 0.0, 500.0, 10.0).method_147(" ms");
    private float floatValue;
    private long longValue;

    public Zoom() {
        super("Zoom", "Smooth hold-to-zoom camera", Category.RENDER);
    }

    public float method_1113(float var1) {
        long var2 = System.nanoTime();
        float var7;
        if (this.longValue == 0L) {
            var7 = 0.0F;
        } else {
            var7 = Math.min((float)(var2 - this.longValue) / 1000000.0F, 100.0F);
        }

        this.longValue = var2;
        boolean var8;
        if (this.isToggled() && this.zoomKey.method_163() && mc.currentScreen == null && this.zoomKey.method_157()) {
            var8 = true;
        } else {
            var8 = false;
        }

        float var9;
        if (var8) {
            var9 = 1.0F;
        } else {
            var9 = 0.0F;
        }

        float var3;
        float var10;
        if ((var3 = this.animation.method_151()) <= 0.0F) {
            this.floatValue = var9;
            var10 = var1;
        } else {
            if (var7 > 0.0F) {
                float var11 = Math.clamp(var7 / var3, 0.0F, 1.0F);
                float var12 = 1.0F - (float)Math.pow(1.0F - var11, 3.0);
                this.floatValue = this.floatValue + (var9 - this.floatValue) * var12;
                if (Math.abs(var9 - this.floatValue) < 0.001F) {
                    this.floatValue = var9;
                }
            }

            var10 = var1;
        }

        return var1 + (var10 / this.distance.method_151() - var1) * method_1114(this.floatValue);
    }

    private static float method_1114(float var0) {
        float var1;
        float var10000 = var1 = Math.clamp(var0, 0.0F, 1.0F);
        return var10000 * var10000 * (3.0F - 2.0F * var1);
    }

    @Override
    public void onDisable() {
        this.longValue = 0L;
    }

    public boolean method_1115() {
        return this.isToggled() && this.floatValue > 0.02F;
    }

    public float method_1116() {
        return this.isToggled() ? this.floatValue : 0.0F;
    }
}
