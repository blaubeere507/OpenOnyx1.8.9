package client.onyx.rotation;

import client.onyx.util.IMinecraft;
import client.onyx.util.player.MovementPredictor;
import net.minecraft.client.entity.EntityPlayerSP;

public class MoveFix implements IMinecraft {
    private static boolean flag4 = false;
    private static float floatValue3 = 0.0F;
    private static float floatValue4 = 0.0F;
    private static MoveFixMode moveFixMode = MoveFixMode.OFF;
    private static float floatValue = 0.0F;
    private static float floatValue7 = 0.0F;
    private static boolean flag2 = false;
    private static boolean flag = false;
    private static float floatValue5 = 0.0F;
    private static float floatValue8 = 0.0F;
    private static float floatValue6 = 0.0F;
    private static float floatValue2 = 0.0F;
    private static boolean flag3 = false;
    private static EntityPlayerSP entityPlayerSP = null;

    public static void method_2143() {
        if (flag) {
            flag = false;
            if (mc.thePlayer != null) {
                mc.thePlayer.rotationYaw = floatValue5;
                mc.thePlayer.rotationPitch = floatValue8;
            }
        }
    }

    public static boolean method_2144() {
        return !method_2149() && flag4 && moveFixMode != MoveFixMode.OFF && mc.thePlayer != null;
    }

    public static boolean isFlag2() {
        return flag2;
    }

    public static YawPitch method_2145() {
        return flag3 ? new YawPitch(floatValue6, floatValue2) : new YawPitch(floatValue, floatValue7);
    }

    public static float getFloatValue7() {
        return floatValue7;
    }

    public static float getFloatValue3() {
        return floatValue3;
    }

    public static float getFloatValue4() {
        return floatValue4;
    }

    public static void method_2146(float var0, float var1) {
        floatValue6 = var0;
        floatValue2 = var1;
        flag3 = true;
        floatValue = var0;
        floatValue7 = var1;
    }

    public static float getFloatValue() {
        return floatValue;
    }

    public static void method_2147() {
        flag4 = false;
        moveFixMode = MoveFixMode.OFF;
    }

    public static void method_2148() {
        if (mc.thePlayer != null) {
            if (entityPlayerSP != mc.thePlayer) {
                entityPlayerSP = mc.thePlayer;
                floatValue = mc.thePlayer.rotationYaw;
                floatValue7 = mc.thePlayer.rotationPitch;
                floatValue6 = floatValue;
                floatValue2 = floatValue7;
                flag3 = true;
                flag2 = false;
            }

            if (method_2149()) {
                Rotation var0;
                if ((var0 = RotationManager.INSTANCE.getServerRotation()) == null) {
                    var0 = MovementPredictor.method_1910(mc.thePlayer);
                }

                flag = false;
                flag2 = true;
                floatValue = var0.getYaw();
                floatValue7 = var0.getPitch();
                RotationVisuals.method_2130(floatValue, floatValue7);
            } else {
                if (flag4) {
                    if (flag3) {
                        mc.thePlayer.lastReportedYaw = floatValue6;
                        mc.thePlayer.lastReportedPitch = floatValue2;
                    }

                    floatValue5 = mc.thePlayer.rotationYaw;
                    floatValue8 = mc.thePlayer.rotationPitch;
                    flag = true;
                    mc.thePlayer.rotationYaw = floatValue3;
                    mc.thePlayer.rotationPitch = floatValue4;
                }

                flag2 = flag4;
                floatValue = mc.thePlayer.rotationYaw;
                floatValue7 = mc.thePlayer.rotationPitch;
                RotationVisuals.method_2130(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
            }
        }
    }

    public static boolean isFlag4() {
        return flag4;
    }

    private static boolean method_2149() {
        return !flag4 && (RotationManager.INSTANCE.getServerRotation() != null || RotationManager.INSTANCE.getActiveRequest() != null);
    }

    public static MoveFixMode getMoveFixMode() {
        return moveFixMode;
    }

    public static void method_2150(YawPitch var0, MoveFixMode var1) {
        floatValue3 = var0.floatValue();
        floatValue4 = var0.floatValue2();
        moveFixMode = var1;
        flag4 = true;
    }
}
