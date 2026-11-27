package client.onyx.simulation;

import client.onyx.Onyx;
import client.onyx.event.impl.MoveEvent;
import client.onyx.util.IMinecraft;
import client.onyx.util.player.InputKeys;
import client.onyx.util.player.MovementInputState;
import client.onyx.util.player.MovementPredictor;
import client.onyx.util.world.CollisionUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class SimulatedPlayerInput implements IMinecraft {
    private static final double DOUBLE_0_1211 = 0.121;
    private final MovementInputState movementInputState;
    private boolean flag;
    private boolean flag2;
    public InputKeys inputKeys;
    private float floatValue;
    private float floatValue2;
    private boolean flag3;

    public boolean isFlag2() {
        return this.flag2;
    }

    public void method_297(boolean var1) {
        this.flag3 = var1;
    }

    @Override
    public String toString() {
        return new StringBuilder()
            .insert(0, "SimulatedPlayerInput(forwards={")
            .append(this.inputKeys.flag5())
            .append("}, backwards={")
            .append(this.inputKeys.flag6())
            .append("}, left={")
            .append(this.inputKeys.flag3())
            .append("}, right={")
            .append(this.inputKeys.flag4())
            .append("}, jumping={")
            .append(this.inputKeys.flag())
            .append("}, sprinting=")
            .append(this.flag)
            .append(", slowDown=")
            .append(this.inputKeys.flag2())
            .append(")")
            .toString();
    }

    public float getFloatValue() {
        return this.floatValue;
    }

    public boolean isFlag3() {
        return this.flag3;
    }

    public boolean isFlag() {
        return this.flag;
    }

    public InputKeys getInputKeys() {
        return this.inputKeys;
    }

    public float getFloatValue2() {
        return this.floatValue;
    }

    public SimulatedPlayerInput(MovementInputState var1, boolean var2, boolean var3, boolean var4) {
        this(var1, var2, var3, var4, false);
        boolean var10005 = false;
    }

    public MovementInputState getMovementInputState() {
        return this.movementInputState;
    }

    public static SimulatedPlayerInput method_298(MovementInputState var0, boolean var1, boolean var2, boolean var3) {
        SimulatedPlayerInput var6 = new SimulatedPlayerInput(var0, var1, var2, var3);
        MoveEvent var5 = new MoveEvent();
        Onyx.EVENT_BUS.post(var5);
        if (var5.isFlag()) {
            var6.flag3 = true;
        }

        return var6;
    }

    public void method_299(boolean var1) {
        this.flag2 = var1;
    }

    public float getFloatValue22() {
        return this.floatValue2;
    }

    public SimulatedPlayerInput(MovementInputState var1, boolean var2, boolean var3, boolean var4, boolean var5) {
        this.movementInputState = var1;
        this.flag = var3;
        this.flag2 = var5;
        this.inputKeys = new InputKeys(var1.flag2(), var1.flag4(), var1.flag(), var1.flag3(), var2, var4, var3);
    }

    public float getFloatValue23() {
        return this.floatValue2;
    }

    public void method_300(boolean var1) {
        this.flag = var1;
    }

    public static SimulatedPlayerInput method_301(MovementInputState var0) {
        MovementInputState var1;
        if (mc.thePlayer.movementInput == null) {
            var1 = var0;
        } else {
            if (mc.thePlayer.movementInput.jump) {
                boolean var4 = true;
                return method_298(var0, var4, mc.thePlayer.isSprinting(), mc.thePlayer.isSneaking());
            }

            var1 = var0;
        }

        boolean var3 = false;
        return method_298(var1, var3, mc.thePlayer.isSprinting(), mc.thePlayer.isSneaking());
    }

    public static SimulatedPlayerInput method_302(EntityPlayer var0) {
        double var2;
        boolean var5;
        Vec3 var6;
        if ((var2 = (var6 = MovementPredictor.method_1899(var0).subtract(MovementPredictor.method_1903(var0))).horizontalDistanceSqr()) >= 0.014641) {
            var5 = true;
        } else {
            var5 = false;
        }

        MovementInputState var8;
        if (var2 > 0.0025000000000000005) {
            float var7 = CollisionUtil.method_1926(var6, var0.rotationYaw);
            var8 = CollisionUtil.method_1927(MovementInputState.INSTANCE8, MathHelper.wrapAngleTo180_float(var7));
        } else {
            var8 = MovementInputState.INSTANCE8;
        }

        SimulatedPlayerInput var10000;
        boolean var10003;
        boolean var10004;
        if (!var0.onGround) {
            var10003 = true;
            var10004 = var5;
        } else {
            var10003 = false;
            var10004 = var5;
        }

        var10000 = new SimulatedPlayerInput(var8, var10003, var10004, var0.isSneaking());
        return var10000;
    }

    public void method_303() {
        float var1;
        SimulatedPlayerInput var4;
        if (this.inputKeys.flag5() != this.inputKeys.flag6()) {
            float var3;
            if (this.inputKeys.flag5()) {
                var3 = 1.0F;
            } else {
                var3 = -1.0F;
            }

            var1 = var3;
            var4 = this;
        } else {
            var1 = 0.0F;
            var4 = this;
        }

        SimulatedPlayerInput var5;
        float var6;
        if (var4.inputKeys.flag3() == this.inputKeys.flag4()) {
            var6 = 0.0F;
            var5 = this;
        } else if (this.inputKeys.flag3()) {
            var6 = 1.0F;
            var5 = this;
        } else {
            var6 = -1.0F;
            var5 = this;
        }

        if (var5.inputKeys.flag2()) {
            var6 = (float)(var6 * 0.3);
            var1 = (float)(var1 * 0.3);
        }

        this.floatValue = var6;
        this.floatValue2 = var1;
    }
}
