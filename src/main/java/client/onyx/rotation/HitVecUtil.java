package client.onyx.rotation;

import client.onyx.util.IMinecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class HitVecUtil implements IMinecraft {
    private static final double DOUBLE_0_051 = 0.05;

    public static double method_2131(Entity var0) {
        return mc.thePlayer == null ? Double.MAX_VALUE : method_2140(var0.getEntityBoundingBox(), method_2133());
    }

    public static Vec3 method_2132(float var0, float var1) {
        float var4 = MathHelper.cos(-var0 * (float) (Math.PI / 180.0) - (float) Math.PI);
        float var3 = MathHelper.sin(-var0 * (float) (Math.PI / 180.0) - (float) Math.PI);
        float var2 = -MathHelper.cos(-var1 * (float) (Math.PI / 180.0));
        var0 = MathHelper.sin(-var1 * (float) (Math.PI / 180.0));
        return new Vec3(var3 * var2, var0, var4 * var2);
    }

    public static Vec3 method_2133() {
        return new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
    }

    private static double method_2134(double var0, double var2, double var4) {
        return var2 + var0 * (var4 - var2);
    }

    public static Vec3 method_2135(Vec3 var0, AxisAlignedBB var1) {
        return new Vec3(
            MathHelper.clamp_double(var0.xCoord, var1.minX, var1.maxX),
            MathHelper.clamp_double(var0.yCoord, var1.minY, var1.maxY),
            MathHelper.clamp_double(var0.zCoord, var1.minZ, var1.maxZ)
        );
    }

    public static Vec3 method_2136(Entity var0, double var1, double var3, double var5, double var7) {
        Vec3 var9 = method_2133();
        AxisAlignedBB var14;
        double var11 = Math.min((var14 = var0.getEntityBoundingBox()).maxX - var14.minX, Math.min(var14.maxY - var14.minY, var14.maxZ - var14.minZ));
        Vec3 var13;
        AxisAlignedBB var15;
        if ((var15 = var14.expand(-(var11 = Math.min(0.05, var11 * 0.25)), -var11, -var11)).isVecInside(var9)) {
            var13 = method_2139(var15);
        } else {
            var13 = method_2135(var9, var15);
        }

        if (var7 <= 0.0) {
            return var13;
        } else {
            var9 = new Vec3(method_2134(var1, var15.minX, var15.maxX), method_2134(var3, var15.minY, var15.maxY), method_2134(var5, var15.minZ, var15.maxZ));
            return method_2137(var13, var9, var7);
        }
    }

    private static Vec3 method_2137(Vec3 var0, Vec3 var1, double var2) {
        return new Vec3(method_2134(var2, var0.xCoord, var1.xCoord), method_2134(var2, var0.yCoord, var1.yCoord), method_2134(var2, var0.zCoord, var1.zCoord));
    }

    public static Vec3 method_2138(Entity var0) {
        return method_2136(var0, 0.0, 0.0, 0.0, 0.0);
    }

    public static Vec3 method_2139(AxisAlignedBB var0) {
        return new Vec3((var0.minX + var0.maxX) * 0.5, (var0.minY + var0.maxY) * 0.5, (var0.minZ + var0.maxZ) * 0.5);
    }

    public static double method_2140(AxisAlignedBB var0, Vec3 var1) {
        double var2 = Math.max(Math.max(var0.minX - var1.xCoord, 0.0), var1.xCoord - var0.maxX);
        double var4 = Math.max(Math.max(var0.minY - var1.yCoord, 0.0), var1.yCoord - var0.maxY);
        double var6 = Math.max(Math.max(var0.minZ - var1.zCoord, 0.0), var1.zCoord - var0.maxZ);
        return var2 * var2 + var4 * var4 + var6 * var6;
    }
}
