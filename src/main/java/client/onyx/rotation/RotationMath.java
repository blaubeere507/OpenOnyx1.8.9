package client.onyx.rotation;

import client.onyx.util.IMinecraft;
import client.onyx.util.player.MovementPredictor;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public final class RotationMath implements IMinecraft {
    private static final double DOUBLE_0_151 = 0.15;

    public static double method_1037() {
        return (float)(method_1043() * 0.15);
    }

    public static Rotation method_1038(Rotation var0, double var1, double var3) {
        AngleDelta var5 = method_1045(var1, var3);
        return new Rotation(var0.getYaw() + var5.floatValue(), MathHelper.clamp_float(var0.getPitch() + var5.floatValue2(), -90.0F, 90.0F));
    }

    public static float method_1039(float var0, float var1, float var2) {
        if (var0 < var1) {
            return var1;
        } else {
            return var0 > var2 ? var2 : var0;
        }
    }

    public static void method_1040(EntityPlayerSP var0, Rotation var1) {
        Rotation var3;
        Rotation var10001 = var3 = var1.method_1050();
        var0.prevRotationPitch = var0.rotationPitch;
        var0.prevRotationYaw = var0.rotationYaw;
        var0.renderYawOffset = var0.rotationYaw;
        var0.prevRenderYawOffset = var0.rotationYaw;
        var0.rotationYaw = var3.getYaw();
        var0.rotationPitch = var10001.getPitch();
    }

    public static float method_1041(Entity var0) {
        if (mc.thePlayer == null) {
            return 0.0F;
        } else {
            Rotation var2 = Rotation.method_1058(var0.getEntityBoundingBox().getCenter(), mc.thePlayer.getPositionEyes(1.0F));
            return MovementPredictor.method_1910(mc.thePlayer).method_1047(var2);
        }
    }

    public static float method_1042(float var0, float var1) {
        return MathHelper.wrapAngleTo180_float(var0 - var1);
    }

    private static double method_1043() {
        float var0;
        float var10000 = var0 = (float)mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
        return var10000 * var10000 * var0 * 8.0F;
    }

    public static float method_1044(EntityPlayerSP var0, Rotation var1) {
        return var1.getYaw() + method_1042(var0.rotationYaw, var1.getYaw());
    }

    public static AngleDelta method_1045(double var0, double var2) {
        return new AngleDelta((float)(var0 * 0.15), (float)(var2 * 0.15));
    }

    private RotationMath() {
    }
}
