package client.onyx.placement;

import client.onyx.util.IMinecraft;
import client.onyx.util.geometry.AlignedFace;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

public abstract class AimPoint implements IMinecraft {
    public AlignedFace method_45(AlignedFace var1) {
        Vec3 var17 = var1.method_2074().scale(0.15);
        Vec3 var2 = var1.getVec32();
        Vec3 var3 = var1.getVec31();
        Vec3 var4 = var1.method_2070();
        double var5 = var2.xCoord + var17.xCoord;
        double var7 = var3.xCoord - var17.xCoord;
        double var9 = var2.yCoord + var17.yCoord;
        double var11 = var3.yCoord - var17.yCoord;
        double var13 = var2.zCoord + var17.zCoord;
        double var15 = var3.zCoord - var17.zCoord;
        if (var5 > var7) {
            var5 = var4.xCoord;
            var7 = var4.xCoord;
        }

        if (var9 > var11) {
            var9 = var4.yCoord;
            var11 = var4.yCoord;
        }

        if (var13 > var15) {
            var13 = var4.zCoord;
            var15 = var4.zCoord;
        }

        return new AlignedFace(
            new Vec3(method_46(var2.xCoord, var5, var7), method_46(var2.yCoord, var9, var11), method_46(var2.zCoord, var13, var15)),
            new Vec3(method_46(var3.xCoord, var5, var7), method_46(var3.yCoord, var9, var11), method_46(var3.zCoord, var13, var15))
        );
    }

    private static double method_46(double var0, double var2, double var4) {
        if (var0 < var2) {
            return var2;
        } else {
            return var0 > var4 ? var4 : var0;
        }
    }

    public abstract Vec3 method_32(AlignedFace var1, BlockPos var2);
}
