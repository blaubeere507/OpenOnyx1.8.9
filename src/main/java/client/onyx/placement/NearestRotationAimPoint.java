package client.onyx.placement;

import client.onyx.util.geometry.AlignedFace;
import client.onyx.util.math.AabbUtil;
import client.onyx.util.player.InputKeysUtil;
import client.onyx.util.player.MovementPredictor;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

public class NearestRotationAimPoint extends AimPoint {
    private final ScoredPoint scoredPoint;

    @Override
    public Vec3 method_32(AlignedFace var1, BlockPos var2) {
        AlignedFace var5 = this.method_45(var1);
        if (!InputKeysUtil.method_1969(MovementPredictor.method_1885(mc.thePlayer))) {
            return this.method_48(var2, var5);
        } else {
            Vec3 var3;
            return (var3 = this.method_47(var2, var5)) != null ? var3 : this.method_48(var2, var5);
        }
    }

    private Vec3 method_47(BlockPos var1, AlignedFace var2) {
        Vec3 var12 = var2.getVec32();
        Vec3 var3 = var2.getVec31();
        AxisAlignedBB var13 = new AxisAlignedBB(var12.xCoord, var12.yCoord, var12.zCoord, var3.xCoord, var3.yCoord, var3.zCoord);
        Vec3 var15 = MovementPredictor.method_1899(mc.thePlayer).subtract(var1.getX(), var1.getY(), var1.getZ());
        var3 = null;
        double var4 = Double.NEGATIVE_INFINITY;
        Vec3[] var14;
        int var6 = (var14 = AabbUtil.method_2030(var13)).length;
        int var7 = 0;

        for (int var11 = 0; var11 < var6; var11 = ++var7) {
            Vec3 var8;
            double var9;
            if ((var9 = (var8 = var14[var7]).distanceToSqr(var15)) > var4) {
                var4 = var9;
                var3 = var8;
            }
        }

        return var3;
    }

    public NearestRotationAimPoint(ScoredPoint var1) {
        this.scoredPoint = var1;
    }

    private Vec3 method_48(BlockPos var1, AlignedFace var2) {
        return new StabilizedAimPoint(this.scoredPoint).method_44(var1, var2);
    }

    public ScoredPoint getScoredPoint() {
        return this.scoredPoint;
    }
}
