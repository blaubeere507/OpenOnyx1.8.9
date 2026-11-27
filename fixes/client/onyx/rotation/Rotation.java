package client.onyx.rotation;

import client.onyx.util.player.MovementPredictor;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public record Rotation(float pitch, float yaw, boolean flag) {
    public static final Rotation INSTANCE = new Rotation(0.0F, 0.0F);

    public float getPitch() {
        return this.yaw;
    }

    public float method_1047(Rotation var1) {
        return Math.min(this.method_1054(var1).method_1046(), 180.0F);
    }

    public Rotation method_1048(float var1, float var2) {
        return new Rotation(this.pitch + var2, this.yaw + var1);
    }

    public Rotation(float var1, float var2) {
        this(var1, var2, false);
        boolean var10003 = false;
    }

    public Vec3 method_1049() {
        return Vec3.directionFromRotation(this.yaw, this.pitch);
    }

    public Rotation method_1050() {
        if (this.flag) {
            return this;
        } else {
            double var1 = RotationMath.method_1037();
            // hand-fixed: the obfuscator reused the "this" slot for the base rotation
            Rotation base;
            if ((base = RotationManager.INSTANCE.getServerRotation()) == null) {
                base = MovementPredictor.method_1910(Minecraft.getMinecraft().thePlayer);
            }

            AngleDelta var10002 = base.method_1054(this);
            double var5 = Math.round(var10002.floatValue() / var1) * var1;
            var1 = Math.round(var10002.floatValue2() / var1) * var1;
            float var8 = base.getYaw() + (float)var5;
            float var4 = base.getPitch() + (float)var1;
            return new Rotation(var8, MathHelper.clamp_float(var4, -90.0F, 90.0F), true);
        }
    }

    public boolean method_1051(Rotation var1) {
        return this.method_1057(var1, 2.0F);
    }

    public static Rotation method_1052(Vec3 var0) {
        return method_1055(var0.xCoord, var0.yCoord, var0.zCoord);
    }

    public Rotation method_1053(Rotation var1, float var2) {
        return new Rotation(Math.fma(var2, var1.pitch - this.pitch, this.pitch), Math.fma(var2, var1.yaw - this.yaw, this.yaw));
    }

    public AngleDelta method_1054(Rotation var1) {
        return new AngleDelta(RotationMath.method_1042(var1.pitch, this.pitch), RotationMath.method_1042(var1.yaw, this.yaw));
    }

    public static Rotation method_1055(double var0, double var2, double var4) {
        return new Rotation(
            MathHelper.wrapAngleTo180_float((float)Math.toDegrees(Math.atan2(var4, var0)) - 90.0F),
            MathHelper.wrapAngleTo180_float((float)(-Math.toDegrees(Math.atan2(var2, Math.hypot(var0, var4)))))
        );
    }

    public Rotation method_1056(Rotation var1, float var2, float var3) {
        AngleDelta var7;
        AngleDelta var10001 = var7 = this.method_1054(var1);
        float var4 = var10001.method_1046();
        var2 = Math.abs(var10001.floatValue() / var4) * var2;
        float var6 = Math.abs(var10001.floatValue2() / var4) * var3;
        return this.method_1048(RotationMath.method_1039(var10001.floatValue2(), -var6, var6), RotationMath.method_1039(var7.floatValue(), -var2, var2));
    }

    public boolean method_1057(Rotation var1, float var2) {
        return this.method_1047(var1) <= var2;
    }

    public float getYaw() {
        return this.pitch;
    }

    public static Rotation method_1058(Vec3 var0, Vec3 var1) {
        return method_1052(var0.subtract(var1));
    }
}
