package client.onyx.event.impl;

import client.onyx.event.Event;

public class GenericEvent extends Event {
    private final Object object;
    private final int intValue;

    public GenericEvent(Object var1, int var2) {
        this.object = var1;
        this.intValue = var2;
    }

    public int getIntValue() {
        return this.intValue;
    }

    public Object getObject() {
        return this.object;
    }
}
