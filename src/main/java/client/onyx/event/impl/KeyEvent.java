package client.onyx.event.impl;

import client.onyx.event.Event;

public class KeyEvent extends Event {
    private final int intValue2;
    private final int intValue;
    private final int intValue3;

    public int getIntValue() {
        return this.intValue;
    }

    public int getIntValue2() {
        return this.intValue2;
    }

    public int getIntValue3() {
        return this.intValue3;
    }

    public KeyEvent(int var1, int var2, int var3) {
        this.intValue2 = var1;
        this.intValue = var2;
        this.intValue3 = var3;
    }
}
