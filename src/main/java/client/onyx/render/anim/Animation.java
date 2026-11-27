package client.onyx.render.anim;

public final class Animation {
    private Easing easing = Easings.easing6;
    private float floatValue4;
    private float floatValue3;
    private float floatValue2;
    private float floatValue;

    public Animation method_603(float var1, float var2, Easing var3) {
        if (var1 == this.floatValue3) {
            return this;
        } else {
            this.floatValue4 = this.method_606();
            this.floatValue3 = var1;
            this.floatValue2 = Math.max(var2, 0.0F);
            this.easing = var3;
            this.floatValue = 0.0F;
            return this;
        }
    }

    public float getFloatValue3() {
        return this.floatValue3;
    }

    public boolean method_604() {
        return this.floatValue2 <= 0.0F || this.floatValue >= this.floatValue2;
    }

    public void method_605(float var1) {
        if (!this.method_604()) {
            this.floatValue = Math.min(this.floatValue + var1, this.floatValue2);
        }
    }

    public float method_606() {
        if (this.floatValue2 <= 0.0F) {
            return this.floatValue3;
        } else {
            float var2 = (float)this.easing.method_608(this.floatValue / this.floatValue2);
            return this.floatValue4 + (this.floatValue3 - this.floatValue4) * var2;
        }
    }

    public Animation(float var1) {
        this.floatValue4 = var1;
        this.floatValue3 = var1;
        this.floatValue2 = 0.0F;
        this.floatValue = 0.0F;
    }

    public Animation method_607(float var1) {
        this.floatValue4 = var1;
        this.floatValue3 = var1;
        this.floatValue2 = 0.0F;
        this.floatValue = 0.0F;
        return this;
    }
}
