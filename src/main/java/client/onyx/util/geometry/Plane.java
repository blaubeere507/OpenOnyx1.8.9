package client.onyx.util.geometry;

import client.onyx.util.math.VecUtil;
import net.minecraft.util.Vec3;

public class Plane {
    private final Vec3 vec31;
    private final Vec3 vec32;

    public Vec3 method_2075(Shape var1) {
        Double var3;
        return (var3 = this.method_2076(var1)) == null ? null : var1.method_2046(var3);
    }

    public Double method_2076(Shape var1) {
        double var2 = this.vec31.dotProduct(this.vec32);
        double var4;
        return Shape.method_2052(var4 = var1.vec32().dotProduct(this.vec32), 0.0) ? null : (var2 - var1.vec31().dotProduct(this.vec32)) / var4;
    }

    public Vec3 getVec31() {
        return this.vec31;
    }

    public Plane(Vec3 var1, Vec3 var2) {
        this.vec31 = var1;
        this.vec32 = VecUtil.method_1966(var2);
    }

    public Vec3 getVec32() {
        return this.vec32;
    }

    public static Plane method_2077(Vec3 var0, Vec3 var1, Vec3 var2) {
        if (VecUtil.method_1959(var0 = var1.crossProduct(var2).normalize())) {
            throw new IllegalArgumentException("Points must not be on the same line");
        } else {
            return new Plane(var0, var0);
        }
    }

    public static Plane method_2078(Vec3 var0, Vec3 var1, Vec3 var2) {
        return method_2077(var0, var1.subtract(var0), var2.subtract(var0));
    }

    public Ray method_2079(Plane var1) {
        Vec3 var2 = var1.vec32;
        Vec3 var4 = this.vec32;
        double var5;
        Vec3 var11;
        if (Shape.method_2052(var5 = (var11 = var2.crossProduct(var4)).lengthSqr(), 0.0)) {
            return null;
        } else {
            double var7 = var2.dotProduct(var1.vec31);
            double var9 = var4.dotProduct(this.vec31);
            Vec3 var12 = var4.crossProduct(var11).scale(var7).add(var11.crossProduct(var2).scale(var9)).scale(1.0 / var5);
            return new Ray(var12, var11);
        }
    }
}
