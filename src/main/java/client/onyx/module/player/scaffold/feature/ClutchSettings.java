package client.onyx.module.player.scaffold.feature;

import client.onyx.rotation.Rotation;
import client.onyx.rotation.RotationManager;
import client.onyx.rotation.RotationRequest;
import client.onyx.rotation.aim.AimModel;
import client.onyx.setting.Setting;
import client.onyx.setting.SettingSection;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.util.IMinecraft;
import client.onyx.util.player.MovementPredictor;
import java.util.Iterator;
import java.util.List;

public class ClutchSettings extends SettingSection implements IMinecraft {
    public static final ClutchSettings clutchSettings = new ClutchSettings();
    public NumberSetting clutchSpeed = new NumberSetting("Clutch speed", 90.0, 1.0, 180.0, 1.0)
        .method_147("°/t")
        .describe(
            "How fast the aim may move while clutching a fall. Higher lands more placements but looks less like the slow rotation; lower keeps more of the slow rotation at the cost of the odd fall."
        );
    public NumberSetting clutchDistance = new NumberSetting("Clutch distance", 0.2, 0.05, 0.6, 0.01)
        .describe("How close to the fall-off edge the aim starts clutching. Larger engages earlier, so the aim is ready before the edge is reached.");

    public boolean method_1772(Rotation var1) {
        return this.method_61()
            && mc.thePlayer != null
            && var1 != null
            && MovementPredictor.method_1896(mc.thePlayer)
            && MovementPredictor.method_1911(mc.thePlayer, this.clutchDistance.method_151())
            && RotationManager.INSTANCE.getRotation().method_1047(var1) > 1.0F;
    }

    private ClutchSettings() {
        super("Clutch", true);
        this.visibleWhen(Setting.booleanSupplier);
    }

    public int method_1773(Rotation var1) {
        return (int)Math.ceil(RotationManager.INSTANCE.getRotation().method_1047(var1) / Math.max(1.0F, this.clutchSpeed.method_151()));
    }

    public RotationRequest method_1774(RotationRequest var1) {
        return !this.method_61()
            ? var1
            : new RotationRequest(
                var1.getRotation(),
                var1.getEntity(),
                List.of(new ClutchSettings.ClutchAimModel(var1.getList())),
                var1.getIntValue(),
                var1.getFloatValue(),
                var1.isFlag(),
                var1.getMovementCorrection(),
                var1.getConditionalAction()
            );
    }

    private final class ClutchAimModel implements AimModel {
        private final List<AimModel> list;

        private ClutchAimModel(List<AimModel> var2) {
            this.list = var2;
        }

        @Override
        public Rotation method_1059(RotationRequest var1, Rotation var2, Rotation var3) {
            if (ClutchSettings.this.method_1772(var3)) {
                float var9 = Math.min(ClutchSettings.this.clutchSpeed.method_151(), var2.method_1047(var3));
                return var2.method_1056(var3, var9, var9);
            } else {
                Rotation var4 = var3;

                Iterator var7;
                for (Iterator var5 = var7 = this.list.iterator(); var5.hasNext(); var5 = var7) {
                    var4 = ((AimModel)var7.next()).method_1059(var1, var2, var4);
                }

                return var4;
            }
        }
    }
}
