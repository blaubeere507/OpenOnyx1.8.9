package client.onyx.rotation;

import client.onyx.util.IMinecraft;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class RotationUtil implements IMinecraft {
    public static float method_2156(float var0, float var1, float var2, float var3, float var4) {
        if ((var0 = method_2157(var0, var1, var2)) >= var3 && var0 <= var4) {
            return var0;
        } else if (var2 <= 0.0F) {
            return MathHelper.clamp_float(var0, var3, var4);
        } else {
            float var7;
            if (var0 > var4) {
                var7 = var4;
            } else {
                var7 = var3;
            }

            double var5 = (var7 - var0) / var2;
            return (float)(var0 + (double)((long)var5) * var2);
        }
    }

    public static float method_2157(float var0, float var1, float var2) {
        if (var2 <= 0.0F) {
            return var1;
        } else {
            double var3 = var1 - var0;
            return (float)(var0 + (double)Math.round(var3 / var2) * var2);
        }
    }

    public static YawPitch method_2158(Vec3 var0, YawPitch var1) {
        Vec3 var9 = HitVecUtil.method_2133();
        double var3 = var0.xCoord - var9.xCoord;
        double var5 = var0.yCoord - var9.yCoord;
        double var7 = var0.zCoord - var9.zCoord;
        if (var3 * var3 + var5 * var5 + var7 * var7 < 1.0E-8) {
            return var1;
        } else {
            float var10 = (float)(MathHelper.atan2(var7, var3) * 180.0 / Math.PI) - 90.0F;
            float var11 = (float)(-(MathHelper.atan2(var5, Math.sqrt(var3 * var3 + var7 * var7)) * 180.0 / Math.PI));
            return new YawPitch(var1.floatValue() + MathHelper.wrapAngleTo180_float(var10 - var1.floatValue()), MathHelper.clamp_float(var11, -90.0F, 90.0F));
        }
    }

    public static float method_2159() {
        double var0;
        double var10000 = var0 = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
        return (float)(var10000 * var10000 * var0 * 8.0) * 0.15F;
    }

    public static YawPitch method_2160(Vec3 var0) {
        return method_2158(var0, new YawPitch(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch));
    }

    public static float method_2161(float var0, float var1, float var2) {
        return var0 + MathHelper.wrapAngleTo180_float(var1 - var0) * var2;
    }
}
