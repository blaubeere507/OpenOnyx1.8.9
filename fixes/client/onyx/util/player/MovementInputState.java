package client.onyx.util.player;

import net.minecraft.util.MovementInput;

public record MovementInputState(boolean flag2, boolean flag4, boolean flag, boolean flag3) {
    public static final MovementInputState INSTANCE8 = new MovementInputState(false, false, false, false);
    public static final MovementInputState INSTANCE5 = new MovementInputState(true, false, false, false);
    public static final MovementInputState INSTANCE6 = new MovementInputState(false, true, false, false);
    public static final MovementInputState INSTANCE9 = new MovementInputState(false, false, true, false);
    public static final MovementInputState INSTANCE3 = new MovementInputState(false, false, false, true);
    public static final MovementInputState INSTANCE7 = new MovementInputState(true, false, true, false);
    public static final MovementInputState INSTANCE = new MovementInputState(true, false, false, true);
    public static final MovementInputState INSTANCE4 = new MovementInputState(false, true, true, false);
    public static final MovementInputState INSTANCE2 = new MovementInputState(false, true, false, true);

    public float method_1916() {
        if (this.flag2 == this.flag4) {
            return 0.0F;
        } else {
            return this.flag2 ? 1.0F : -1.0F;
        }
    }

    public MovementInputState(float var1, float var2) {
        this(var1 > 0.0F, var1 < 0.0F, var2 > 0.0F, var2 < 0.0F);
    }

    public float method_1917() {
        if (this.flag == this.flag3) {
            return 0.0F;
        } else {
            return this.flag ? 1.0F : -1.0F;
        }
    }

    public MovementInputState method_1918() {
        return new MovementInputState(this.flag4, this.flag2, this.flag3, this.flag);
    }

    public MovementInputState(MovementInput var1) {
        this(var1.moveForward, var1.moveStrafe);
    }

    public boolean method_1919() {
        return this.flag2 != this.flag4 || this.flag != this.flag3;
    }
}
