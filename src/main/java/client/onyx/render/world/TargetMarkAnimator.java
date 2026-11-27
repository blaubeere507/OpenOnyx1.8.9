package client.onyx.render.world;

public final class TargetMarkAnimator<T> {
    public static final float FLOAT_250_01 = 250.0F;
    private float floatValue;
    private T object;
    private T object2;

    public float getFloatValue() {
        return this.floatValue;
    }

    public T getObject2() {
        return this.object2;
    }

    public void method_765(T var1, float var2) {
        float var7 = Math.max(0.0F, var2) / 250.0F;
        if (var1 == this.object2) {
            this.object = null;
            TargetMarkAnimator var4;
            float var5;
            if (this.object2 != null) {
                var5 = Math.min(1.0F, this.floatValue + var7);
                var4 = this;
            } else {
                var5 = Math.max(0.0F, this.floatValue - var7);
                var4 = this;
            }

            var4.floatValue = var5;
        } else {
            this.object = (T)var1;
            if (this.object2 != null && this.floatValue > 0.0F) {
                this.floatValue = Math.max(0.0F, this.floatValue - var7);
                if (this.floatValue > 0.0F) {
                    return;
                }
            }

            this.object2 = this.object;
            this.object = null;
            this.floatValue = 0.0F;
        }
    }

    public void method_766() {
        this.object = this.object2 = null;
        this.floatValue = 0.0F;
    }

    public T getObject() {
        return this.object;
    }
}
