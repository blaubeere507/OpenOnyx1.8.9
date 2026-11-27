package client.onyx.rotation;

import client.onyx.setting.impl.NumberSetting;
import client.onyx.util.IMinecraft;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class Aimer implements IMinecraft {
    private static final float FLOAT_0_11 = 0.1F;
    private final Random random = new Random();
    private int intValue = -1;
    private int intValue2 = 0;
    private YawPitch yawPitch2 = null;
    private YawPitch yawPitch = null;
    private final AimSettings aimSettings;
    private float floatValue;
    private double doubleValue;
    private double doubleValue2;
    private float floatValue2;
    private float floatValue3;
    private double doubleValue3;
    private float floatValue4;

    public boolean method_2113(Entity var1, YawPitch var2, double var3) {
        if (var2 != null && mc.thePlayer != null) {
            Vec3 var5 = HitVecUtil.method_2133();
            Vec3 var8 = HitVecUtil.method_2132(var2.floatValue(), var2.floatValue2());
            Vec3 var9 = var5.addVector(var8.xCoord * var3, var8.yCoord * var3, var8.zCoord * var3);
            float var12 = var1.getCollisionBorderSize();
            AxisAlignedBB var6;
            AxisAlignedBB var10 = (var6 = var1.getEntityBoundingBox().expand(var12, var12, var12))
                .offset(var1.prevPosX - var1.posX, var1.prevPosY - var1.posY, var1.prevPosZ - var1.posZ);
            return this.method_2115(var6, var5, var9) || this.method_2115(var10, var5, var9);
        } else {
            return false;
        }
    }

    public Aimer(AimSettings var1) {
        this.aimSettings = var1;
        this.method_2114();
        this.method_2116();
    }

    private void method_2114() {
        this.doubleValue = this.random.nextDouble();
        this.doubleValue3 = this.random.nextDouble();
        this.doubleValue2 = this.random.nextDouble();
    }

    public YawPitch getYawPitch() {
        return this.yawPitch;
    }

    private boolean method_2115(AxisAlignedBB var1, Vec3 var2, Vec3 var3) {
        return var1.isVecInside(var2) || var1.calculateIntercept(var2, var3) != null;
    }

    private void method_2116() {
        this.floatValue3 = this.method_2121(this.aimSettings.yawSpeed, this.aimSettings.yawSpeedMax);
        this.floatValue = this.method_2121(this.aimSettings.pitchSpeed, this.aimSettings.pitchSpeedMax);
    }

    private float method_2117(float var1) {
        return var1 * (this.random.nextFloat() - this.random.nextFloat());
    }

    public void method_2118(Entity var1, double var2) {
        if (mc.thePlayer == null) {
            this.method_2120();
        } else {
            boolean var4 = MoveFix.isFlag2();
            boolean var7;
            if (var1.getEntityId() != this.intValue) {
                var7 = true;
            } else {
                var7 = false;
            }

            boolean var9;
            if (!var7) {
                if (this.aimSettings.aimReRoll.method_149() > 0 && ++this.intValue2 >= this.aimSettings.aimReRoll.method_149()) {
                    this.intValue2 = 0;
                    this.method_2114();
                }

                var9 = var4;
            } else {
                this.intValue = var1.getEntityId();
                this.intValue2 = 0;
                this.method_2114();
                var9 = var4;
            }

            if (!var9) {
                this.floatValue2 = 0.0F;
                this.floatValue4 = 0.0F;
            }

            if (!var4 || var7) {
                this.method_2116();
            }

            YawPitch var16 = MoveFix.method_2145();
            Vec3 var10 = HitVecUtil.method_2133();
            Vec3 var6 = HitVecUtil.method_2136(var1, this.doubleValue, this.doubleValue3, this.doubleValue2, this.aimSettings.aimSpread.getValue() / 100.0);
            if (var10.squareDistanceTo(var6) > var2 * var2) {
                var6 = HitVecUtil.method_2138(var1);
            }

            this.yawPitch2 = RotationUtil.method_2158(var6, var16);
            float var14 = MathHelper.wrapAngleTo180_float(this.yawPitch2.floatValue() - var16.floatValue());
            float var11 = this.yawPitch2.floatValue2() - var16.floatValue2();
            float var15 = var16.floatValue() + this.method_2119(var14, this.method_2123(this.floatValue3), true);
            float var12 = var16.floatValue2() + this.method_2119(var11, this.method_2123(this.floatValue), false);
            if (this.aimSettings.yawJitter.getValue() > 0.0) {
                var15 += this.method_2117(this.aimSettings.yawJitter.method_151());
            }

            if (this.aimSettings.pitchJitter.getValue() > 0.0) {
                var12 += this.method_2117(this.aimSettings.pitchJitter.method_151());
            }

            float var13 = MathHelper.clamp_float(var12, -90.0F, 90.0F);
            if (this.aimSettings.sensitivityFix.method_146()) {
                float var17 = RotationUtil.method_2159();
                var15 = RotationUtil.method_2157(var16.floatValue(), var15, var17);
                var13 = RotationUtil.method_2156(var16.floatValue2(), var13, var17, -90.0F, 90.0F);
            }

            this.yawPitch = new YawPitch(var15, var13);
            MoveFix.method_2150(this.yawPitch, this.aimSettings.moveFix.getValue());
        }
    }

    private float method_2119(float var1, float var2, boolean var3) {
        switch ((AimSettings.RotationMode)this.aimSettings.rotation.getValue()) {
            case INSTANT:
                return var1;
            case LINEAR:
                return MathHelper.clamp_float(var1, -var2, var2);
            case SMOOTH:
                float var16 = 1.0F - this.aimSettings.smoothing.method_151() / 100.0F;
                float var17 = MathHelper.clamp_float(var1 * var16, -var2, var2);
                float var7;
                if (this.aimSettings.sensitivityFix.method_146()) {
                    var7 = RotationUtil.method_2159();
                } else {
                    var7 = 0.01F;
                }

                return Math.copySign(Math.min(Math.abs(var1), Math.max(Math.abs(var17), var7)), var1);
            default:
                float var9;
                if (!var3) {
                    var9 = this.floatValue4;
                } else {
                    var9 = this.floatValue2;
                }

                float var5 = this.aimSettings.acceleration.method_151();
                float var6 = var2 * this.aimSettings.startSpeed.method_151() / 100.0F;
                float var4 = MathHelper.clamp_float(var9 + var5, var6, var2);
                float var13 = (float)Math.sqrt(2.0 * var5 * Math.abs(var1));
                float var14 = Math.min(Math.min(var4, var13), Math.abs(var1));
                float var11;
                if (!var3) {
                    this.floatValue4 = var14;
                    var11 = var14;
                } else {
                    this.floatValue2 = var14;
                    var11 = var14;
                }

                return Math.copySign(var11, var1);
        }
    }

    public void method_2120() {
        this.intValue = -1;
        this.intValue2 = 0;
        this.floatValue2 = 0.0F;
        this.floatValue4 = 0.0F;
        this.yawPitch2 = null;
        this.yawPitch = null;
        MoveFix.method_2147();
    }

    private float method_2121(NumberSetting var1, NumberSetting var2) {
        if (!this.aimSettings.speedVariance.method_146()) {
            return var1.method_151();
        } else {
            float var4;
            float var6 = Math.max(var4 = var1.method_151(), var2.method_151());
            return var4 + this.random.nextFloat() * (var6 - var4);
        }
    }

    public float method_2122(YawPitch var1) {
        return var1 != null && this.yawPitch2 != null
            ? Math.max(
                Math.abs(MathHelper.wrapAngleTo180_float(this.yawPitch2.floatValue() - var1.floatValue())),
                Math.abs(this.yawPitch2.floatValue2() - var1.floatValue2())
            )
            : 180.0F;
    }

    private float method_2123(float var1) {
        return !this.aimSettings.speedVariance.method_146() ? var1 : var1 * (1.0F + this.method_2117(0.1F));
    }

    public YawPitch getYawPitch2() {
        return this.yawPitch2;
    }
}
