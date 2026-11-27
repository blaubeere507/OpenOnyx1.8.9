package client.onyx.module.player.scaffold.feature;

import client.onyx.module.player.scaffold.Scaffold;
import client.onyx.placement.PlacementTarget;
import client.onyx.rotation.Rotation;
import client.onyx.util.IMinecraft;
import client.onyx.util.player.MovementPredictor;

public class ScaffoldPredictor implements IMinecraft {
    public static PredictedPlacement method_1771(PlacementTarget var0, Rotation var1, SneakController var2) {
        if (MovementPredictor.method_1891(mc.thePlayer)) {
            int var6;
            if (ClutchSettings.clutchSettings.method_61()) {
                var6 = ClutchSettings.clutchSettings.method_1773(var1);
            } else {
                var6 = Scaffold.INSTANCE.scaffoldRotationSettings.method_1030(var1);
            }

            boolean var7;
            if (Scaffold.INSTANCE.method_1663() <= 0) {
                var7 = true;
            } else {
                var7 = false;
            }

            boolean var8;
            if (var6 >= 1) {
                var8 = true;
            } else {
                var8 = false;
            }

            if (var7 || var8) {
                return new PredictedPlacement(false, Math.max(1, var6), false, false);
            }
        }

        if (var2 == null) {
            return PredictedPlacement.INSTANCE;
        } else {
            PredictedPlacement var9;
            return (var9 = var2.method_1762(var0, var1)) != null ? var9 : PredictedPlacement.INSTANCE;
        }
    }
}
