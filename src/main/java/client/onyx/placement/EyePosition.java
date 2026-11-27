package client.onyx.placement;

import client.onyx.util.IMinecraft;
import net.minecraft.util.Vec3;

public class EyePosition implements IMinecraft {
    private static final float FLOAT_1_541 = 1.54F;
    private static final float FLOAT_1_621 = 1.62F;
    private final Vec3 vec31;
    private final boolean flag;

    public boolean isFlag() {
        return this.flag;
    }

    public float method_35() {
        return method_37(this.flag);
    }

    public Vec3 getVec31() {
        return this.vec31;
    }

    public EyePosition(Vec3 var1, boolean var2) {
        this.vec31 = var1;
        this.flag = var2;
    }

    public Vec3 method_36() {
        return this.vec31.add(0.0, this.method_35(), 0.0);
    }

    public static float method_37(boolean var0) {
        return var0 ? 1.54F : 1.62F;
    }

    public EyePosition(Vec3 var1) {
        this(var1, mc.thePlayer != null && mc.thePlayer.isSneaking());
    }
}
