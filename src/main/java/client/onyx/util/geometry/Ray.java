package client.onyx.util.geometry;

import net.minecraft.util.Vec3;

public record Ray(Vec3 vec31, Vec3 vec32) implements Shape {
    public Ray(Vec3 vec31, Vec3 vec32) {
        Shape.method_2035(vec32);
        this.vec31 = vec31;
        this.vec32 = vec32;
    }

    @Override
    public LineBounds method_2039() {
        return LineBounds.UNBOUNDED;
    }

    public Vec3 vec312() {
        return this.vec31;
    }

    public static Ray method_2055(Vec3 var0, Vec3 var1) {
        return new Ray(var0, var1.subtract(var0));
    }
}
