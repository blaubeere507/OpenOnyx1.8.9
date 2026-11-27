package client.onyx.rotation.aim.impl;

import client.onyx.rotation.Rotation;
import client.onyx.rotation.RotationRequest;
import client.onyx.rotation.aim.AimInput;
import client.onyx.rotation.aim.TurnSpeedAimModel;
import client.onyx.setting.impl.RangeSetting;

public class LinearAimModel extends TurnSpeedAimModel {
    private final RangeSetting rangeSetting;
    private final RangeSetting rangeSetting2;

    public LinearAimModel() {
        this(180.0F, 180.0F, 180.0F, 180.0F);
    }

    @Override
    public AimInput method_1061(RotationRequest var1, Rotation var2, Rotation var3) {
        return var1 != null
            ? new AimInput(this.rangeSetting.method_103(), this.rangeSetting2.method_103())
            : new AimInput(this.rangeSetting.method_93(), this.rangeSetting2.method_93());
    }

    public LinearAimModel(float var1, float var2, float var3, float var4) {
        super("Linear");
        String var10005 = "Linear";
        this.rangeSetting = RangeSetting.method_104("Horizontal turn speed", var1, var2, 0.0, 180.0).method_94("°/t");
        this.rangeSetting2 = RangeSetting.method_104("Vertical turn speed", var3, var4, 0.0, 180.0).method_94("°/t");
    }
}
