package client.onyx.event.impl;

import client.onyx.event.Event;
import net.minecraft.util.Vec3;

public class StrafeEvent extends Event {
    private final Vec3 vec32;
    private final float floatValue;
    private final float floatValue2;
    private Vec3 vec31;

    public float getFloatValue2() {
        return this.floatValue2;
    }

    public Vec3 getVec32() {
        return this.vec32;
    }

    public StrafeEvent(Vec3 var1, float var2, float var3, Vec3 var4) {
        this.vec32 = var1;
        this.floatValue = var2;
        this.floatValue2 = var3;
        this.vec31 = var4;
    }

    public float getFloatValue() {
        return this.floatValue;
    }

    public Vec3 getVec31() {
        return this.vec31;
    }

    public void method_2615(Vec3 var1) {
        this.vec31 = var1;
    }
}
