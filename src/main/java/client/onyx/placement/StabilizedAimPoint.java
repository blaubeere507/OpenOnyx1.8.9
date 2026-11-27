package client.onyx.placement;

import client.onyx.rotation.Rotation;
import client.onyx.rotation.RotationManager;
import client.onyx.util.geometry.AlignedFace;
import client.onyx.util.geometry.Ray;
import client.onyx.util.geometry.Shape;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

public class StabilizedAimPoint extends AimPoint {
    private final ScoredPoint scoredPoint;

    public StabilizedAimPoint(ScoredPoint var1) {
        this.scoredPoint = var1;
    }

    public ScoredPoint getScoredPoint() {
        return this.scoredPoint;
    }

    public Vec3 method_44(BlockPos var1, AlignedFace var2) {
        if (Shape.method_2052(var2.method_2072(), 0.0)) {
            return var2.getVec32();
        } else {
            Rotation var3 = RotationManager.INSTANCE.getRotation2();
            Ray var5 = new Ray(this.scoredPoint.vec31().subtract(var1.getX(), var1.getY(), var1.getZ()), var3.method_1049());
            return var2.method_2069(var5);
        }
    }

    @Override
    public Vec3 method_32(AlignedFace var1, BlockPos var2) {
        return this.method_44(var2, this.method_45(var1));
    }
}
