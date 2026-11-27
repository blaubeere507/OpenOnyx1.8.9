package client.onyx.simulation;

import net.minecraft.util.Vec3;

public record SimulatedTick(Vec3 vec31, double doubleValue, Vec3 vec32, boolean flag2, boolean flag) {
    public SimulatedTick(SimulatedPlayer var1) {
        this(var1.getVec312(), var1.getDoubleValue(), var1.getVec32(), var1.isFlag3(), var1.isFlag6());
    }
}
