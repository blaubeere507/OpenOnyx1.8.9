package client.onyx.placement;

import client.onyx.util.geometry.AlignedFace;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

public final class RandomAimPoint extends AimPoint {
    public static final RandomAimPoint INSTANCE = new RandomAimPoint();

    private RandomAimPoint() {
    }

    @Override
    public Vec3 method_32(AlignedFace var1, BlockPos var2) {
        return this.method_45(var1).method_2064();
    }
}
