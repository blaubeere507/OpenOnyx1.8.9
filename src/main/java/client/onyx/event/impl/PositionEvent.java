package client.onyx.event.impl;

import client.onyx.event.Event;
import net.minecraft.util.Vec3;

public class PositionEvent extends Event {
    private Vec3 vec31;

    public Vec3 getVec31() {
        return this.vec31;
    }

    public PositionEvent(Vec3 var1) {
        this.vec31 = var1;
    }

    public void method_2614(Vec3 var1) {
        this.vec31 = var1;
    }
}
