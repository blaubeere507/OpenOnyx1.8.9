package client.onyx.util.geometry;

import net.minecraft.util.Vec3;

public record Line(Vec3 vec32, Vec3 vec31) implements Shape {
    @Override
    public Vec3 vec31() {
        return this.vec32;
    }

    public static Line method_2056(Vec3 var0, Vec3 var1) {
        return new Line(var0, var1.subtract(var0));
    }

    @Override
    public Vec3 vec32() {
        return this.vec31;
    }

    @Override
    public LineBounds method_2039() {
        return LineBounds.FORWARD;
    }

    public Vec3 vec322() {
        return this.vec32;
    }

    public Line(Vec3 vec32, Vec3 vec31) {
        Shape.method_2035(vec31);
        this.vec32 = vec32;
        this.vec31 = vec31;
    }
}
