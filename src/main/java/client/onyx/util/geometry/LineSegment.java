package client.onyx.util.geometry;

import client.onyx.util.math.VecUtil;
import net.minecraft.util.Vec3;

public record LineSegment(Vec3 vec31, Vec3 vec32) implements Shape {
    @Override
    public LineBounds method_2039() {
        return LineBounds.SEGMENT_01;
    }

    public LineSegment(Vec3 vec31, Vec3 vec32) {
        if (VecUtil.method_1959(vec32.subtract(vec31))) {
            throw new IllegalArgumentException(
                new StringBuilder().insert(0, "Line segment must not have zero length, actual: ").append(vec31).append(" -> ").append(vec32).toString()
            );
        } else {
            this.vec31 = vec31;
            this.vec32 = vec32;
        }
    }

    public Vec3 vec322() {
        return this.vec32;
    }

    public double method_2080() {
        return this.vec32().length();
    }

    public Vec3 vec312() {
        return this.vec31;
    }

    @Override
    public Vec3 vec32() {
        return this.vec32.subtract(this.vec31);
    }
}
