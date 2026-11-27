package client.onyx.event.impl;

import client.onyx.event.Event;

public class MoveEvent extends Event {
    private boolean flag = false;

    public boolean isFlag() {
        return this.flag;
    }

    public void method_2616(boolean var1) {
        this.flag = var1;
    }
}
