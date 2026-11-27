package client.onyx.placement;

import client.onyx.util.geometry.AlignedFace;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

public final class CenterAimPoint extends AimPoint {
    public static final CenterAimPoint INSTANCE = new CenterAimPoint();

    @Override
    public Vec3 method_32(AlignedFace var1, BlockPos var2) {
        return var1.method_2070();
    }

    private CenterAimPoint() {
    }
}
