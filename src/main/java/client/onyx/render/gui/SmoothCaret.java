package client.onyx.render.gui;

import client.onyx.render.anim.Easings;

public final class SmoothCaret {
    private static final float FLOAT_1100_01 = 1100.0F;
    private static final float FLOAT_60_01 = 60.0F;
    private static final float FLOAT_0_151 = 0.15F;
    private static final float FLOAT_350_01 = 350.0F;
    private static final float FLOAT_0_0281 = 0.028F;
    private float floatValue = Float.NaN;
    private float floatValue2;
    private float floatValue3;
    private long longValue;

    public float method_878(float var1) {
        float var2 = this.method_880();
        if (!Float.isNaN(this.floatValue) && !(Math.abs(var1 - this.floatValue) > 60.0F)) {
            if (Math.abs(var1 - this.floatValue) > 0.01F) {
                this.floatValue2 = 0.0F;
            }

            this.floatValue = this.floatValue + (var1 - this.floatValue) * Math.clamp(0.028F * var2, 0.0F, 1.0F);
            this.floatValue3 = 0.0F;
            return this.floatValue;
        } else {
            this.floatValue = var1;
            this.floatValue2 = 0.0F;
            return this.floatValue;
        }
    }

    public float method_879() {
        if (this.floatValue2 < 350.0F) {
            return 1.0F;
        } else {
            float var3;
            float var4;
            if ((var4 = this.floatValue3 % 1100.0F / 1100.0F) < 0.5F) {
                var3 = var4 * 2.0F;
            } else {
                var3 = (1.0F - var4) * 2.0F;
            }

            return 0.15F + 0.85F * (float)Easings.easing6.method_608(var3);
        }
    }

    private float method_880() {
        long var1 = System.nanoTime();
        float var4;
        if (this.longValue == 0L) {
            var4 = 16.0F;
        } else {
            var4 = Math.min((float)(var1 - this.longValue) / 1000000.0F, 100.0F);
        }

        this.longValue = var1;
        this.floatValue2 += var4;
        this.floatValue3 += var4;
        return var4;
    }

    public void method_881() {
        this.floatValue = Float.NaN;
        this.floatValue2 = 0.0F;
        this.floatValue3 = 0.0F;
        this.longValue = 0L;
    }
}
