package client.onyx.rotation.aim.impl;

import client.onyx.rotation.AngleDelta;
import client.onyx.rotation.Rotation;
import client.onyx.rotation.RotationManager;
import client.onyx.rotation.RotationMath;
import client.onyx.rotation.RotationRequest;
import client.onyx.rotation.aim.AimInput;
import client.onyx.rotation.aim.AimModelMode;
import client.onyx.setting.SettingSection;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.setting.impl.RangeSetting;
import client.onyx.util.player.MovementPredictor;
import client.onyx.util.world.EntityRaycast;
import net.minecraft.entity.Entity;

public class AccelerationAimModel extends AimModelMode {
    private final RangeSetting rangeSetting2 = RangeSetting.method_104("Yaw acceleration", 20.0, 25.0, 1.0, 180.0).method_94("°/t²");
    private final RangeSetting rangeSetting = RangeSetting.method_104("Pitch acceleration", 20.0, 25.0, 1.0, 180.0).method_94("°/t²");
    private final AccelerationAimModel.AccelerationErrorSettings accelerationErrorSettings = new AccelerationAimModel.AccelerationErrorSettings();
    private final AccelerationAimModel.ConstantErrorSettings constantErrorSettings = new AccelerationAimModel.ConstantErrorSettings();
    private final AccelerationAimModel.DynamicAccelerationSettings dynamicAccelerationSettings = new AccelerationAimModel.DynamicAccelerationSettings();
    private final AccelerationAimModel.SigmoidDecelerationSettings sigmoidDecelerationSettings = new AccelerationAimModel.SigmoidDecelerationSettings();

    private AimInput method_1066(AngleDelta var1, AngleDelta var2, boolean var3, double var4) {
        float var9;
        if (this.sigmoidDecelerationSettings.method_61()) {
            var9 = this.sigmoidDecelerationSettings.method_1062(var2.method_1046());
        } else {
            var9 = 1.0F;
        }

        boolean var10;
        if (this.dynamicAccelerationSettings.method_61() && var3) {
            var10 = true;
        } else {
            var10 = false;
        }

        float var16 = (float)(this.dynamicAccelerationSettings.distanceFactor.getValue() * var4);
        AccelerationAimModel.AxisState var21;
        float var10002;
        AccelerationAimModel var10003;
        if (this.accelerationErrorSettings.method_61()) {
            var10002 = this.accelerationErrorSettings.yawError.getValue().floatValue();
            var10003 = this;
        } else {
            var10002 = 0.0F;
            var10003 = this;
        }

        var21 = new AccelerationAimModel.AxisState(
            var10002, var10003.constantErrorSettings.method_61() ? this.constantErrorSettings.yawError.getValue().floatValue() : 0.0F
        );
        AccelerationAimModel.AxisState var20 = var21;
        if (this.accelerationErrorSettings.method_61()) {
            var10002 = this.accelerationErrorSettings.pitchError.getValue().floatValue();
            var10003 = this;
        } else {
            var10002 = 0.0F;
            var10003 = this;
        }

        var21 = new AccelerationAimModel.AxisState(
            var10002, var10003.constantErrorSettings.method_61() ? this.constantErrorSettings.pitchError.getValue().floatValue() : 0.0F
        );
        AccelerationAimModel.AxisState var5 = var21;
        RangeSetting var12;
        if (!var10) {
            var12 = this.rangeSetting2;
        } else {
            var12 = this.dynamicAccelerationSettings.rangeSetting;
        }

        RangeSetting var14;
        if (!var10) {
            var14 = this.rangeSetting;
        } else {
            var14 = this.dynamicAccelerationSettings.rangeSetting2;
        }

        float var18 = this.method_1067(var2.floatValue(), var1.floatValue(), -var12.method_103() + var16, var12.method_103() + var16, var9);
        float var17 = this.method_1067(var2.floatValue2(), var1.floatValue2(), -var14.method_103() + var16, var14.method_103() + var16, var9);
        return new AimInput(var1.floatValue() + var18 + var20.method_1064(var18), var1.floatValue2() + var17 + var5.method_1064(var17));
    }

    public AccelerationAimModel() {
        super("Acceleration");
    }

    @Override
    public Rotation method_1059(RotationRequest var1, Rotation var2, Rotation var3) {
        Rotation var4;
        if ((var4 = RotationManager.INSTANCE.getRotation22()) == null) {
            var4 = MovementPredictor.method_1887(mc.thePlayer);
        }

        AngleDelta var16 = var4.method_1054(var2);
        AngleDelta var15 = var2.method_1054(var3);
        double var8;
        Entity var12;
        if ((var12 = var1.getEntity()) != null) {
            var8 = MovementPredictor.method_1890(mc.thePlayer, var12);
        } else {
            var8 = 0.0;
        }

        boolean var10;
        if (var12 != null && EntityRaycast.method_1937(var12, Math.max(3.0, var8), var2) != null) {
            var10 = true;
        } else {
            var10 = false;
        }

        AimInput var13 = this.method_1066(var16, var15, var10, var8);
        return new Rotation(var2.getYaw() + var13.floatValue(), var2.getPitch() + var13.floatValue2());
    }

    @Override
    public int method_1060(Rotation var1, Rotation var2) {
        Rotation var3;
        if ((var3 = RotationManager.INSTANCE.getRotation22()) == null) {
            var3 = MovementPredictor.method_1887(mc.thePlayer);
        }

        AngleDelta var12 = var3.method_1054(var1);
        AngleDelta var9;
        if (method_1068((var9 = var1.method_1054(var2)).floatValue(), 0.0F) && method_1068(var9.floatValue2(), 0.0F)) {
            return 0;
        } else {
            AimInput var11;
            if ((!method_1068((var11 = this.method_1066(var12, var9, false, 0.0)).floatValue(), 0.0F) || !method_1068(var11.floatValue2(), 0.0F))
                && (!(Math.abs(var9.floatValue()) < Math.abs(var11.floatValue())) || !(Math.abs(var9.floatValue2()) < Math.abs(var11.floatValue2())))) {
                double var4 = Math.floor(Math.abs(var9.floatValue()) / Math.abs(var11.floatValue()));
                double var6 = Math.floor(Math.abs(var9.floatValue2()) / Math.abs(var11.floatValue2()));
                return !Double.isNaN(var4) && !Double.isNaN(var6) ? (int)Math.max(var4, var6) : 0;
            } else {
                return 0;
            }
        }
    }

    private float method_1067(float var1, float var2, float var3, float var4, float var5) {
        return RotationMath.method_1039(RotationMath.method_1042(var1, var2), var3, var4) * var5;
    }

    private static boolean method_1068(float var0, float var1) {
        return Math.abs(var1 - var0) < 1.0E-5F;
    }

    private static class AccelerationErrorSettings extends SettingSection {
        private final NumberSetting yawError = new NumberSetting("Yaw error", 0.1, 0.01, 1.0, 0.01);
        private final NumberSetting pitchError = new NumberSetting("Pitch error", 0.1, 0.01, 1.0, 0.01);

        public AccelerationErrorSettings() {
            super("Acceleration error", true);
        }
    }

    private record AxisState(float floatValue2, float floatValue) {
        public float method_1064(float var1) {
            float var2 = method_1065(-this.floatValue2, this.floatValue2);
            float var4 = method_1065(-this.floatValue, this.floatValue);
            return var1 * var2 + var4;
        }

        private static float method_1065(float var0, float var1) {
            return var1 <= var0 ? var0 : (float)(var0 + Math.random() * (var1 - var0));
        }
    }

    private static class ConstantErrorSettings extends SettingSection {
        private final NumberSetting yawError = new NumberSetting("Yaw error", 0.1, 0.01, 1.0, 0.01);
        private final NumberSetting pitchError = new NumberSetting("Pitch error", 0.1, 0.01, 1.0, 0.01);

        public ConstantErrorSettings() {
            super("Constant error", true);
        }
    }

    private static class DynamicAccelerationSettings extends SettingSection {
        private final NumberSetting distanceFactor = new NumberSetting("Distance factor", -1.393, -2.0, 2.0, 0.001)
            .describe("How much the distance to the target scales the acceleration below");
        private final RangeSetting rangeSetting = RangeSetting.method_104("Yaw crosshair acceleration", 17.0, 20.0, 1.0, 180.0).method_94("°/t²");
        private final RangeSetting rangeSetting2 = RangeSetting.method_104("Pitch crosshair acceleration", 17.0, 20.0, 1.0, 180.0).method_94("°/t²");

        public DynamicAccelerationSettings() {
            super("Dynamic acceleration", false);
        }
    }

    private static class SigmoidDecelerationSettings extends SettingSection {
        private final NumberSetting slowdownSharpness = new NumberSetting("Slowdown sharpness", 10.0, 0.0, 20.0, 0.1)
            .describe("How abruptly the aim shifts between slow (near the target) and full speed (far from it)");
        private final NumberSetting halfSpeedDistance = new NumberSetting("Half speed distance", 0.3, 0.0, 1.0, 0.01)
            .describe("Fraction of a 120° turn left to the target at which the aim moves at half speed");

        public SigmoidDecelerationSettings() {
            super("Sigmoid deceleration", false);
        }

        public float method_1062(float var1) {
            double var2 = var1 / 120.0F;
            return RotationMath.method_1039(
                (float)(1.0 / (1.0 + Math.exp(-this.slowdownSharpness.getValue() * (var2 - this.halfSpeedDistance.getValue())))), 0.0F, 180.0F
            );
        }
    }
}
