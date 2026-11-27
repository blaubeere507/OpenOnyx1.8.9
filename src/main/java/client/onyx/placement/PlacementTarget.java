package client.onyx.placement;

import client.onyx.rotation.Rotation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

public record PlacementTarget(BlockPos blockPos, BlockPos blockPos2, EnumFacing enumFacing, double doubleValue, Rotation rotation) {
    public boolean method_51(MovingObjectPosition var1) {
        return var1 != null
            && var1.typeOfHit == MovingObjectType.BLOCK
            && var1.getBlockPos().equals(this.blockPos)
            && var1.sideHit == this.enumFacing
            && !(var1.hitVec.yCoord < this.doubleValue);
    }

    public MovingObjectPosition method_52() {
        return new MovingObjectPosition(Vec3.atCenterOf(this.blockPos), this.enumFacing, this.blockPos);
    }
}
