package client.onyx.placement;

import client.onyx.util.geometry.AlignedFace;
import client.onyx.util.geometry.Ray;
import client.onyx.util.player.MovementPredictor;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

public class EdgeAimPoint extends AimPoint {
    private final ScoredPoint scoredPoint;
    private final Ray ray;

    public Ray getRay() {
        return this.ray;
    }

    @Override
    public Vec3 method_32(AlignedFace var1, BlockPos var2) {
        var1 = this.method_45(var1).method_2061(var2);
        AlignedFace var3;
        if ((var3 = this.method_33(mc.thePlayer, var1)) == null) {
            var3 = var1;
        }

        return new StabilizedAimPoint(this.scoredPoint).method_44(var2, var3.method_2067(new Vec3(-var2.getX(), -var2.getY(), -var2.getZ())));
    }

    private AlignedFace method_33(EntityPlayerSP var1, AlignedFace var2) {
        if (this.ray == null) {
            return null;
        } else {
            Vec3 var6 = MovementPredictor.method_1899(var1);
            Vec3 var3 = this.ray.method_2044(var6);
            var3 = var6.subtract(var3).normalize();
            Ray var4 = new Ray(this.scoredPoint.vec31(), this.ray.vec32());
            Vec3 var12;
            if ((var12 = var2.method_2065().method_2075(var4)) == null) {
                return null;
            } else {
                var3 = var6.add(var3.scale(2.0));
                AxisAlignedBB var7 = new AxisAlignedBB(var12.xCoord, var6.yCoord - 2.0, var12.zCoord, var3.xCoord, var6.yCoord + 1.0, var3.zCoord);
                AlignedFace var8;
                return (var8 = var2.method_2068(var7)).method_2072() < 1.0E-4 ? null : var8;
            }
        }
    }

    public EdgeAimPoint(ScoredPoint var1, Ray var2) {
        this.scoredPoint = var1;
        this.ray = var2;
    }

    public ScoredPoint getScoredPoint() {
        return this.scoredPoint;
    }
}
