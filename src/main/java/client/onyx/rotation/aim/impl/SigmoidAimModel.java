package client.onyx.rotation.aim.impl;

import client.onyx.rotation.Rotation;
import client.onyx.rotation.RotationMath;
import client.onyx.rotation.RotationRequest;
import client.onyx.rotation.aim.AimInput;
import client.onyx.rotation.aim.TurnSpeedAimModel;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.setting.impl.RangeSetting;

public class SigmoidAimModel extends TurnSpeedAimModel {
    private final RangeSetting rangeSetting = RangeSetting.method_104("Horizontal turn speed", 180.0, 180.0, 0.0, 180.0).method_94("°/t");
    private final RangeSetting rangeSetting2 = RangeSetting.method_104("Vertical turn speed", 180.0, 180.0, 0.0, 180.0).method_94("°/t");
    private final NumberSetting slowdownSharpness = new NumberSetting("Slowdown sharpness", 10.0, 0.0, 20.0, 0.1)
        .describe("How abruptly the aim shifts between slow (near the target) and full speed (far from it)");
    private final NumberSetting halfSpeedDistance = new NumberSetting("Half speed distance", 0.3, 0.0, 1.0, 0.01)
        .describe("Fraction of a 120° turn left to the target at which the aim moves at half speed");

    private float method_1063(float var1, float var2) {
        double var3 = var1 / 120.0F;
        return RotationMath.method_1039(
            (float)(1.0 / (1.0 + Math.exp(-this.slowdownSharpness.getValue() * (var3 - this.halfSpeedDistance.getValue()))) * var2), 0.0F, 180.0F
        );
    }

    @Override
    public AimInput method_1061(RotationRequest var1, Rotation var2, Rotation var3) {
        float var7 = var2.method_1047(var3);
        float var6;
        float var8;
        if (var1 == null) {
            var8 = this.rangeSetting.method_93();
            var6 = this.rangeSetting2.method_93();
        } else {
            var8 = this.rangeSetting.method_103();
            var6 = this.rangeSetting2.method_103();
        }

        return new AimInput(this.method_1063(var7, var8), this.method_1063(var7, var6));
    }

    public SigmoidAimModel() {
        super("Sigmoid");
    }
}
