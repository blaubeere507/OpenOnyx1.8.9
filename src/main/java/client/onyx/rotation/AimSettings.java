package client.onyx.rotation;

import client.onyx.setting.SettingGroup;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.EnumSetting;
import client.onyx.setting.impl.NumberSetting;

public class AimSettings extends SettingGroup {
    public EnumSetting<AimSettings.RotationMode> rotation = new EnumSetting<>("Rotation", AimSettings.RotationMode.SMOOTH)
        .describe("How the aim moves toward the target");
    public NumberSetting yawSpeed = new NumberSetting("Yaw speed", 45.0, 1.0, 180.0, 1.0)
        .method_147("°/t")
        .visibleWhen(() -> !this.rotation.method_168(AimSettings.RotationMode.INSTANT));
    public NumberSetting pitchSpeed = new NumberSetting("Pitch speed", 30.0, 1.0, 180.0, 1.0)
        .method_147("°/t")
        .visibleWhen(() -> !this.rotation.method_168(AimSettings.RotationMode.INSTANT));
    public NumberSetting smoothing = new NumberSetting("Smoothing", 70.0, 0.0, 95.0, 5.0)
        .method_147("%")
        .describe("Share of the remaining angle kept each tick")
        .visibleWhen(() -> this.rotation.method_168(AimSettings.RotationMode.SMOOTH));
    public NumberSetting acceleration = new NumberSetting("Acceleration", 15.0, 1.0, 90.0, 1.0)
        .method_147("°/t²")
        .visibleWhen(() -> this.rotation.method_168(AimSettings.RotationMode.ACCELERATE));
    public NumberSetting startSpeed = new NumberSetting("Start speed", 25.0, 5.0, 100.0, 5.0)
        .method_147("%")
        .describe("Speed the ramp begins at")
        .visibleWhen(() -> this.rotation.method_168(AimSettings.RotationMode.ACCELERATE));
    public BooleanSetting speedVariance = new BooleanSetting("Speed variance", true)
        .describe("Rolls a fresh turn speed for every flick")
        .visibleWhen(() -> !this.rotation.method_168(AimSettings.RotationMode.INSTANT));
    public NumberSetting yawSpeedMax = new NumberSetting("Yaw speed max", 75.0, 1.0, 180.0, 1.0)
        .method_147("°/t")
        .visibleWhen(() -> !this.rotation.method_168(AimSettings.RotationMode.INSTANT) && this.speedVariance.method_146());
    public NumberSetting pitchSpeedMax = new NumberSetting("Pitch speed max", 50.0, 1.0, 180.0, 1.0)
        .method_147("°/t")
        .visibleWhen(() -> !this.rotation.method_168(AimSettings.RotationMode.INSTANT) && this.speedVariance.method_146());
    public NumberSetting yawJitter = new NumberSetting("Yaw jitter", 0.0, 0.0, 5.0, 0.1).method_147("°");
    public NumberSetting pitchJitter = new NumberSetting("Pitch jitter", 0.0, 0.0, 5.0, 0.1).method_147("°");
    public NumberSetting aimSpread = new NumberSetting("Aim spread", 25.0, 0.0, 100.0, 5.0)
        .method_147("%")
        .describe("How far from the closest point the aim may wander");
    public NumberSetting aimReRoll = new NumberSetting("Aim re-roll", 0.0, 0.0, 60.0, 5.0)
        .method_147("t")
        .describe("0 keeps one point per target")
        .visibleWhen(() -> this.aimSpread.getValue() > 0.0);
    public BooleanSetting sensitivityFix = new BooleanSetting("Sensitivity fix", true).describe("Snaps each delta to the mouse grid");
    public EnumSetting<MoveFixMode> moveFix = new EnumSetting<>("Move fix", MoveFixMode.SILENT)
        .describe("Keeps movement consistent with the rotation the server sees");

    public AimSettings() {
        super("Rotations");
    }

    public static enum RotationMode {
        INSTANT,
        LINEAR,
        SMOOTH,
        ACCELERATE;
    }
}
