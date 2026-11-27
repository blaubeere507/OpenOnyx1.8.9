package client.onyx.rotation.aim;

import client.onyx.rotation.Rotation;
import client.onyx.rotation.RotationRequest;

public abstract class TurnSpeedAimModel extends AimModelMode {
    @Override
    public Rotation method_1059(RotationRequest var1, Rotation var2, Rotation var3) {
        AimInput var5 = this.method_1061(var1, var2, var3);
        return var2.method_1056(var3, var5.floatValue(), var5.floatValue2());
    }

    @Override
    public int method_1060(Rotation var1, Rotation var2) {
        var var7 = this;
        Rotation var6 = var1;
        int var3 = -1;

        do {
            AimInput var4 = var7.method_1061(null, var6, var2);
            float var10002 = var4.floatValue();
            var3++;
            var6 = var6.method_1056(var2, var10002, var4.floatValue2());
        } while (!var6.method_1051(var2) && var3 < 80);

        return var3;
    }

    public TurnSpeedAimModel(String var1) {
        super(var1);
    }

    public abstract AimInput method_1061(RotationRequest var1, Rotation var2, Rotation var3);
}
