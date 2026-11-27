package client.onyx.event.impl;

import client.onyx.event.Event;

public class VelocityEvent extends Event {
    private final double doubleValue3;
    private final double doubleValue;
    private final double doubleValue2;

    public double getDoubleValue() {
        return this.doubleValue;
    }

    public double getDoubleValue3() {
        return this.doubleValue3;
    }

    public double getDoubleValue2() {
        return this.doubleValue2;
    }

    public VelocityEvent(double var1, double var3, double var5) {
        this.doubleValue3 = var1;
        this.doubleValue = var3;
        this.doubleValue2 = var5;
    }
}
