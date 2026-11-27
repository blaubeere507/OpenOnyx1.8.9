package client.onyx.render.effect;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public final class ColorGrading {
    private static final double DOUBLE_10_51 = 10.5;
    private static final double DOUBLE_12_01 = 12.0;
    private static final double DOUBLE_0_058593751 = 0.05859375;
    private static final double DOUBLE_4_01 = 4.0;

    public static ColorGrading.Params method_825(EntityLivingBase var0, float var1) {
        float var27 = var0.prevLimbSwingAmount + (var0.limbSwingAmount - var0.prevLimbSwingAmount) * var1;
        float var3 = var0.limbSwing - var0.limbSwingAmount * (1.0F - var1);
        if (var27 > 1.0F) {
            var27 = 1.0F;
        }

        float var4 = method_828(var0.prevRenderYawOffset, var0.renderYawOffset, var1);
        float var5 = method_828(var0.prevRotationYawHead, var0.rotationYawHead, var1);
        float var6 = method_828(var0.prevRotationPitch, var0.rotationPitch, var1);
        var5 = method_830(var5 - var4) * (float) (Math.PI / 180.0);
        var6 *= (float) (Math.PI / 180.0);
        boolean var7 = var0.isSneaking();
        float var8 = MathHelper.cos(var3 * 0.6662F + (float) Math.PI) * 2.0F * var27 * 0.5F;
        float var9 = MathHelper.cos(var3 * 0.6662F) * 2.0F * var27 * 0.5F;
        float var10 = MathHelper.cos(var3 * 0.6662F) * 1.4F * var27;
        float var28 = MathHelper.cos(var3 * 0.6662F + (float) Math.PI) * 1.4F * var27;
        var3 = 0.0F;
        double var11 = 0.0;
        double var13 = 12.0;
        double var15 = 0.1;
        if (var7) {
            var3 = 0.5F;
            var8 += 0.4F;
            var9 += 0.4F;
            var11 = 1.0;
            var13 = 9.0;
            var15 = 4.0;
        }

        double var17 = var0.prevPosX + (var0.posX - var0.prevPosX) * var1;
        double var19 = var0.prevPosY + (var0.posY - var0.prevPosY) * var1;
        double var21 = var0.prevPosZ + (var0.posZ - var0.prevPosZ) * var1;
        double var23;
        double var25 = Math.sin(var23 = var4 * (Math.PI / 180.0));
        var23 = Math.cos(var23);
        double[] var30;
        double[] var33 = method_826(var30 = new double[]{0.0, var11, 0.0}, method_827(new double[]{0.0, -4.0, 0.0}, var6, var5));
        double[] var32 = method_827(new double[]{0.0, 12.0, 0.0}, var3, 0.0);
        double[] var35 = new double[]{-5.0, 2.0, 0.0};
        double[] var37 = new double[]{5.0, 2.0, 0.0};
        double[] var38 = method_826(var35, method_827(new double[]{0.0, 12.0, 0.0}, var8, 0.0));
        double[] var39 = method_826(var37, method_827(new double[]{0.0, 12.0, 0.0}, var9, 0.0));
        double[] var40 = new double[]{-1.9, var13, var15};
        double[] var42 = new double[]{1.9, var13, var15};
        double[] var41 = method_826(var40, method_827(new double[]{0.0, 10.5, 0.0}, var10, 0.0));
        double[] var29 = method_826(var42, method_827(new double[]{0.0, 10.5, 0.0}, var28, 0.0));
        return new ColorGrading.Params(
            method_829(var33, var17, var19, var21, var25, var23),
            method_829(var30, var17, var19, var21, var25, var23),
            method_829(var32, var17, var19, var21, var25, var23),
            method_829(var35, var17, var19, var21, var25, var23),
            method_829(var38, var17, var19, var21, var25, var23),
            method_829(var37, var17, var19, var21, var25, var23),
            method_829(var39, var17, var19, var21, var25, var23),
            method_829(var40, var17, var19, var21, var25, var23),
            method_829(var41, var17, var19, var21, var25, var23),
            method_829(var42, var17, var19, var21, var25, var23),
            method_829(var29, var17, var19, var21, var25, var23)
        );
    }

    private static double[] method_826(double[] var0, double[] var1) {
        return new double[]{var0[0] + var1[0], var0[1] + var1[1], var0[2] + var1[2]};
    }

    private ColorGrading() {
    }

    private static double[] method_827(double[] var0, double var1, double var3) {
        double var5 = Math.cos(var1);
        var1 = Math.sin(var1);
        double var7 = var0[1] * var5 - var0[2] * var1;
        var1 = var0[1] * var1 + var0[2] * var5;
        var5 = var0[0];
        if (var3 != 0.0) {
            double var9 = Math.cos(var3);
            var3 = Math.sin(var3);
            double var11 = var5 * var9 + var1 * var3;
            var1 = -var5 * var3 + var1 * var9;
            var5 = var11;
        }

        return new double[]{var5, var7, var1};
    }

    private static float method_828(float var0, float var1, float var2) {
        return var0 + method_830(var1 - var0) * var2;
    }

    private static Vec3 method_829(double[] var0, double var1, double var3, double var5, double var7, double var9) {
        return new Vec3(
            var1 + (var0[0] * var9 + var0[2] * var7) * 0.05859375, var3 + (24.0 - var0[1]) * 0.05859375, var5 + (var0[0] * var7 - var0[2] * var9) * 0.05859375
        );
    }

    private static float method_830(float var0) {
        if ((var0 = var0 % 360.0F) >= 180.0F) {
            var0 -= 360.0F;
        }

        if (var0 < -180.0F) {
            var0 += 360.0F;
        }

        return var0;
    }

    public record Params(Vec3 vec310, Vec3 vec36, Vec3 vec32, Vec3 vec38, Vec3 vec35, Vec3 vec39, Vec3 vec311, Vec3 vec31, Vec3 vec34, Vec3 vec37, Vec3 vec33) {
    }
}
