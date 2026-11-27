package client.onyx.module.player.scaffold;

import client.onyx.rotation.RotationMath;
import java.util.concurrent.ThreadLocalRandom;

public final class ScaffoldPlacement {
    private int intValue;

    public float method_1714(float var1, double var2) {
        if (var2 <= 0.0) {
            return var1;
        } else {
            double var4;
            return (var4 = RotationMath.method_1037()) <= 0.0 ? var1 : (float)(var1 + this.method_1715(Math.max(1, (int)Math.floor(var2 / var4))) * var4);
        }
    }

    private int method_1715(int var1) {
        int var6; // declared by DeclareMissing
        var var2 = this;
        if (var1 <= 1) {
            ScaffoldPlacement var4;
            byte var5;
            if (this.intValue <= 0) {
                var5 = 1;
                var4 = this;
            } else {
                var5 = -1;
                var4 = this;
            }

            return var4.intValue = var5;
        } else {
            do {
                while ((var6 = ThreadLocalRandom.current().nextInt(-var1, var1 + 1)) == 0) {
                }
            } while (var6 == var2.intValue);

            return var2.intValue = var6;
        }
    }
}
