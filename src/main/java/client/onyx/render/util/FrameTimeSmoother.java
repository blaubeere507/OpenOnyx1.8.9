package client.onyx.render.util;

import java.util.Arrays;

public final class FrameTimeSmoother {
    private float[] floatValueArray = new float[16];
    private int intValue;

    public float method_383() {
        return this.intValue == 0 ? 1.0F : this.floatValueArray[--this.intValue];
    }

    public void method_384(float var1) {
        if (this.intValue == this.floatValueArray.length) {
            this.floatValueArray = Arrays.copyOf(this.floatValueArray, this.intValue * 2);
        }

        this.floatValueArray[this.intValue++] = var1;
    }
}
