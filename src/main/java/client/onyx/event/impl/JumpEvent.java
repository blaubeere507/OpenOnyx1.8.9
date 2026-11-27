package client.onyx.event.impl;

import client.onyx.event.Event;

public class JumpEvent extends Event {
    private float floatValue2;
    private float floatValue;

    public void method_2618(float var1) {
        this.floatValue = var1;
    }

    public float getFloatValue() {
        return this.floatValue;
    }

    public float getFloatValue2() {
        return this.floatValue2;
    }

    public JumpEvent(float var1, float var2) {
        this.floatValue2 = var1;
        this.floatValue = var2;
    }

    public void method_2619(float var1) {
        this.floatValue2 = var1;
    }
}
