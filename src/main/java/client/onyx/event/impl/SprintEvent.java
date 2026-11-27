package client.onyx.event.impl;

import client.onyx.event.Event;
import client.onyx.util.player.MovementInputState;

public class SprintEvent extends Event {
    private final MovementInputState movementInputState;
    private boolean flag;

    public MovementInputState getMovementInputState() {
        return this.movementInputState;
    }

    public void method_2617(boolean var1) {
        this.flag = var1;
    }

    public boolean isFlag() {
        return this.flag;
    }

    public SprintEvent(MovementInputState var1, boolean var2) {
        this.movementInputState = var1;
        this.flag = var2;
    }
}
