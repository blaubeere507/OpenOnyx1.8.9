package client.onyx.event.impl;

import client.onyx.event.Event;
import client.onyx.util.player.MovementInputState;

public class MovementInputEvent extends Event {
    private MovementInputState movementInputState;
    private boolean flag2;
    private boolean flag;

    public void method_2620(boolean var1) {
        this.flag = var1;
    }

    public MovementInputEvent(MovementInputState var1, boolean var2, boolean var3) {
        this.movementInputState = var1;
        this.flag2 = var2;
        this.flag = var3;
    }

    public boolean isFlag2() {
        return this.flag2;
    }

    public MovementInputState getMovementInputState() {
        return this.movementInputState;
    }

    public void method_2621(MovementInputState var1) {
        this.movementInputState = var1;
    }

    public void method_2622(boolean var1) {
        this.flag2 = var1;
    }

    public boolean isFlag() {
        return this.flag;
    }
}
