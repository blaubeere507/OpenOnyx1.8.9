package client.onyx.rotation.aim;

import client.onyx.rotation.Rotation;
import client.onyx.setting.Mode;

public abstract class AimModelMode extends Mode implements AimModel {
    public AimModelMode(String var1) {
        super(var1);
    }

    public abstract int method_1060(Rotation var1, Rotation var2);
}
