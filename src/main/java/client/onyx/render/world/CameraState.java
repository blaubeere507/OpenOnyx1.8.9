package client.onyx.render.world;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public final class CameraState {
    private static float floatValue;
    private static float floatValue2;
    private static float floatValue3;
    private static boolean flag;
    private static float floatValue4;
    private static float floatValue5;
    private static float floatValue6;
    private static float floatValue7;
    private static float floatValue8;
    private static float floatValue9;
    private static boolean flag2;
    private static float floatValue10;

    private CameraState() {
    }

    public static void method_831(float var0, float var1, float var2, float var3) {
        floatValue3 = var0;
        floatValue4 = var1;
        floatValue2 = var2;
        floatValue = var3;
        flag2 = true;
    }

    public static void method_832() {
        flag2 = false;
    }

    public static void method_833(Entity var0) {
        if (flag && var0 != null) {
            var0.rotationYaw = floatValue5;
            var0.rotationPitch = floatValue6;
            var0.prevRotationYaw = floatValue9;
            var0.prevRotationPitch = floatValue7;
            if (var0 instanceof EntityLivingBase var10000) {
                ((EntityLivingBase)var0).rotationYawHead = floatValue8;
                var10000.prevRotationYawHead = floatValue10;
            }

            flag = false;
        }
    }

    public static boolean method_834(Entity var0) {
        if (flag2 && var0 != null && !flag) {
            floatValue5 = var0.rotationYaw;
            floatValue6 = var0.rotationPitch;
            floatValue9 = var0.prevRotationYaw;
            floatValue7 = var0.prevRotationPitch;
            var0.rotationYaw = floatValue3;
            var0.rotationPitch = floatValue4;
            var0.prevRotationYaw = floatValue2;
            var0.prevRotationPitch = floatValue;
            if (var0 instanceof EntityLivingBase var10000) {
                EntityLivingBase var10001 = (EntityLivingBase)var0;
                floatValue8 = ((EntityLivingBase)var0).rotationYawHead;
                floatValue10 = var10000.prevRotationYawHead;
                var10001.rotationYawHead = floatValue3;
                var10000.prevRotationYawHead = floatValue2;
            }

            flag = true;
            return true;
        } else {
            return false;
        }
    }

    public static boolean isFlag2() {
        return flag2;
    }
}
