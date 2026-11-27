package client.onyx.rotation;

import client.onyx.util.IMinecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class RotationVisuals implements IMinecraft {
    private static EntityPlayerSP entityPlayerSP = null;
    private static boolean flag = false;
    private static float floatValue7 = 0.0F;
    private static float floatValue6 = 0.0F;
    private static float floatValue3 = 0.0F;
    private static float floatValue8 = 0.0F;
    private static float floatValue = 0.0F;
    private static float floatValue5 = 0.0F;
    private static float floatValue2 = 0.0F;
    private static float floatValue4 = 0.0F;
    private static final float FLOAT_50_01 = 50.0F;

    private static float method_2124() {
        double var0 = mc.thePlayer.posX - mc.thePlayer.prevPosX;
        double var2 = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
        float var4 = floatValue;
        if ((float)(var0 * var0 + var2 * var2) > 0.0025000002F) {
            float var5 = (float)(MathHelper.atan2(var2, var0) * 180.0 / Math.PI) - 90.0F;
            float var6 = Math.abs(MathHelper.wrapAngleTo180_float(floatValue3) - var5);
            float var7;
            if (95.0F <= var6 && var6 < 265.0F) {
                var7 = var5 - 180.0F;
            } else {
                var7 = var5;
            }

            var4 = var7;
        }

        if (mc.thePlayer.swingProgress > 0.0F) {
            var4 = floatValue3;
        }

        float var8 = floatValue + MathHelper.wrapAngleTo180_float(var4 - floatValue) * 0.3F;
        float var9;
        if (Math.abs(var9 = MathHelper.wrapAngleTo180_float(floatValue3 - var8)) > 50.0F) {
            var8 += var9 - Math.signum(var9) * 50.0F;
        }

        return var8;
    }

    public static void method_2125() {
        if (mc.thePlayer != null) {
            floatValue5 = floatValue3;
            floatValue2 = floatValue8;
            floatValue4 = floatValue;
            floatValue3 = floatValue7;
            floatValue8 = floatValue6;
            floatValue = method_2124();
        }
    }

    public static boolean method_2126(Entity var0) {
        return flag && var0 == mc.thePlayer;
    }

    public static float method_2127(float var0) {
        return RotationUtil.method_2161(floatValue4, floatValue, var0);
    }

    public static float method_2128(float var0) {
        return RotationUtil.method_2161(floatValue5, floatValue3, var0);
    }

    public static float method_2129(float var0) {
        return floatValue2 + (floatValue8 - floatValue2) * var0;
    }

    public static void method_2130(float var0, float var1) {
        floatValue7 = var0;
        floatValue6 = var1;
        flag = MoveFix.isFlag2();
        if (entityPlayerSP != mc.thePlayer) {
            entityPlayerSP = mc.thePlayer;
            floatValue5 = var0;
            floatValue3 = var0;
            floatValue2 = var1;
            floatValue8 = var1;
            floatValue4 = var0;
            floatValue = var0;
        }
    }
}
