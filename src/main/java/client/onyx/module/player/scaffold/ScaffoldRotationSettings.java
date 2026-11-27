package client.onyx.module.player.scaffold;

import client.onyx.rotation.Rotation;
import client.onyx.rotation.RotationRequest;
import client.onyx.rotation.RotationSettings;
import client.onyx.rotation.aim.AimModel;
import client.onyx.setting.Setting;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.NumberSetting;
import java.util.Iterator;
import java.util.List;

public class ScaffoldRotationSettings extends RotationSettings {
    public BooleanSetting considerInventory = new BooleanSetting("Consider inventory", false).visibleWhen(Setting.booleanSupplier);
    public NumberSetting rotationVariance = new NumberSetting("Rotation variance", 0.3, 0.0, 3.0, 0.05)
        .method_147("°")
        .describe(
            "Wobbles the sent yaw by a mouse step or two each tick so no two placements go out with the same yaw (Grim DuplicateRotPlace). 0 turns it off."
        );
    private final transient ScaffoldPlacement scaffoldPlacement = new ScaffoldPlacement();

    public RotationRequest method_1713(RotationRequest var1) {
        return this.rotationVariance.getValue() <= 0.0
            ? var1
            : new RotationRequest(
                var1.getRotation(),
                var1.getEntity(),
                List.of(new ScaffoldRotationSettings.ScaffoldAimModel(var1.getList())),
                var1.getIntValue(),
                var1.getFloatValue(),
                var1.isFlag(),
                var1.getMovementCorrection(),
                var1.getConditionalAction()
            );
    }

    private final class ScaffoldAimModel implements AimModel {
        private final List<AimModel> list;

        private ScaffoldAimModel(List<AimModel> var2) {
            this.list = var2;
        }

        @Override
        public Rotation method_1059(RotationRequest var1, Rotation var2, Rotation var3) {
            Rotation var7 = var3;

            Iterator var4;
            for (Iterator var5 = var4 = this.list.iterator(); var5.hasNext(); var5 = var4) {
                var7 = ((AimModel)var4.next()).method_1059(var1, var2, var7);
            }

            return new Rotation(
                ScaffoldRotationSettings.this.scaffoldPlacement.method_1714(var7.getYaw(), ScaffoldRotationSettings.this.rotationVariance.getValue()),
                var7.getPitch()
            );
        }
    }
}
