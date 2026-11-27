package client.onyx.rotation;

import client.onyx.rotation.aim.AimModelMode;
import client.onyx.rotation.aim.impl.AccelerationAimModel;
import client.onyx.rotation.aim.impl.LinearAimModel;
import client.onyx.rotation.aim.impl.SigmoidAimModel;
import client.onyx.setting.ModeSetting;
import client.onyx.setting.SettingGroup;
import client.onyx.setting.impl.EnumSetting;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.util.ConditionalAction;
import java.util.List;
import net.minecraft.entity.Entity;

public class RotationSettings extends SettingGroup {
    private final ModeSetting<AimModelMode> angleSmooth;
    private final EnumSetting<MovementCorrection> movementCorrection;
    private final NumberSetting resetThreshold;
    private final NumberSetting ticksUntilReset;

    public RotationSettings(MovementCorrection var1) {
        super("Rotations");
        String var10001 = "Rotations";
        LinearAimModel var3 = new LinearAimModel();
        this.angleSmooth = new ModeSetting<>("Angle smooth", var3, var3, new AccelerationAimModel(), new SigmoidAimModel());
        this.movementCorrection = new EnumSetting<>("Movement correction", var1);
        this.resetThreshold = new NumberSetting("Reset threshold", 2.0, 1.0, 180.0, 1.0)
            .method_147("°")
            .describe("How close the aim has to get to your real look while easing back before control snaps to you");
        this.ticksUntilReset = new NumberSetting("Ticks until reset", 5.0, 1.0, 30.0, 1.0)
            .method_147(" ticks")
            .describe("How long the last aim is held after the module stops updating it, before it eases back");
    }

    public RotationRequest method_1029(Rotation var1, Entity var2, boolean var3, ConditionalAction var4) {
        List var6 = List.of(this.angleSmooth.getValue());
        return new RotationRequest(
            var1,
            var2,
            var6,
            (int)Math.round(this.ticksUntilReset.getValue()),
            this.resetThreshold.getValue().floatValue(),
            var3,
            this.movementCorrection.getValue(),
            var4
        );
    }

    public RotationSettings() {
        this(MovementCorrection.SILENT);
    }

    public int method_1030(Rotation var1) {
        return this.angleSmooth.getValue().method_1060(RotationManager.INSTANCE.getRotation(), var1);
    }

    public RotationRequest method_1031(Rotation var1) {
        return this.method_1029(var1, null, false, null);
    }

    public MovementCorrection method_1032() {
        return this.movementCorrection.getValue();
    }

    public RotationRequest method_1033(Rotation var1, boolean var2) {
        return this.method_1029(var1, null, var2, null);
    }
}
