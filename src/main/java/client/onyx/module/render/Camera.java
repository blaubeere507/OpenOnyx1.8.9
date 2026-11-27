package client.onyx.module.render;

import client.onyx.event.impl.WorldLoadEvent;
import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.render.effect.MotionBlurRenderer;
import client.onyx.render.world.CameraState;
import client.onyx.setting.SettingGroup;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.NumberSetting;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;

public class Camera extends Module {
    private static final double DOUBLE_6_01 = 6.0;
    private static final double DOUBLE_8_01 = 8.0;
    public NumberSetting smoothing = new NumberSetting("Smoothing", 95.0, 0.0, 95.0, 5.0).method_147("%").describe("How far the camera lags behind you");
    public NumberSetting distance = new NumberSetting("Distance", 50.0, 50.0, 160.0, 5.0).method_147("%").describe("Third-person camera distance");
    public Camera.FirstPersonSettings firstPersonSettings = new Camera.FirstPersonSettings();
    public Camera.MotionBlurSettings motionBlurSettings = new Camera.MotionBlurSettings();
    private boolean flag;
    private double doubleValue;
    private double doubleValue2;
    private double doubleValue3;
    private float floatValue;
    private float floatValue2;
    private boolean flag2;
    private long longValue;
    private float floatValue3;
    private double doubleValue4;
    private double doubleValue5;
    private long longValue2;
    private double doubleValue6;
    private float floatValue4;
    private boolean flag3;
    private double doubleValue7;
    private float floatValue5;
    private double doubleValue8;
    private float floatValue6;
    private double doubleValue9;
    private long longValue3;

    public double getDoubleValue8() {
        return this.doubleValue8;
    }

    public float getFloatValue2() {
        return this.floatValue2;
    }

    public double getDoubleValue3() {
        return this.doubleValue3;
    }

    public void method_1101(float var1, float var2) {
        long var3 = System.nanoTime();
        float var6;
        if (this.longValue2 == 0L) {
            var6 = 16.0F;
        } else {
            var6 = Math.min((float)(var3 - this.longValue2) / 1000000.0F, 100.0F);
        }

        this.longValue2 = var3;
        if (!this.flag2) {
            this.flag2 = true;
            this.floatValue2 = var1;
            this.floatValue5 = var2;
        } else {
            double var7 = this.method_1105(var6);
            this.floatValue2 = this.floatValue2 + method_1107(var1 - this.floatValue2) * (float)var7;
            this.floatValue5 = this.floatValue5 + (var2 - this.floatValue5) * (float)var7;
        }
    }

    public float method_1102() {
        if (this.isToggled() && this.motionBlurSettings.method_61()) {
            float var4 = this.motionBlurSettings.amount.method_151() / 100.0F;
            if (this.motionBlurSettings.onlyWhenMoving.method_146() && mc.thePlayer != null) {
                double var2 = Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
                return var4 * (float)Math.min(1.0, var2 / 0.14);
            } else {
                return var4;
            }
        } else {
            return 0.0F;
        }
    }

    @EventHandler
    private void onWorldLoad(WorldLoadEvent var1) {
        MotionBlurRenderer.method_709();
        this.flag3 = false;
        this.flag = false;
    }

    public void method_1103(Entity var1) {
        if (this.isToggled() && this.firstPersonSettings.method_61() && var1 != null) {
            if (mc.gameSettings.thirdPersonView == 0 && !mc.gameSettings.debugCamEnable) {
                float var13 = var1.rotationYaw;
                float var2 = var1.rotationPitch;
                long var3 = System.nanoTime();
                float var7;
                if (this.longValue == 0L) {
                    var7 = 16.0F;
                } else {
                    var7 = Math.min((float)(var3 - this.longValue) / 1000000.0F, 100.0F);
                }

                this.longValue = var3;
                Camera var9;
                if (this.flag3) {
                    this.floatValue6 = this.floatValue3;
                    this.floatValue4 = this.floatValue;
                    float var10;
                    double var15;
                    if ((var15 = this.firstPersonSettings.smoothing.method_151() / 100.0 * 260.0) <= 0.0) {
                        var10 = 1.0F;
                    } else {
                        var10 = (float)(1.0 - Math.exp(-var7 / var15));
                    }

                    this.floatValue3 = this.floatValue3 + method_1107(var13 - this.floatValue3) * var10;
                    if (!this.firstPersonSettings.smoothPitch.method_146()) {
                        this.floatValue = var2;
                        var9 = this;
                    } else {
                        this.floatValue = this.floatValue + (var2 - this.floatValue) * var10;
                        var9 = this;
                    }
                } else {
                    this.flag3 = true;
                    this.floatValue3 = this.floatValue6 = var13;
                    this.floatValue = this.floatValue4 = var2;
                    var9 = this;
                }

                CameraState.method_831(var9.floatValue3, this.floatValue, this.floatValue3, this.floatValue);
            } else {
                this.flag3 = false;
            }
        }
    }

    public void method_1104(double var1, double var3, double var5) {
        this.doubleValue8 = var1;
        this.doubleValue6 = var3;
        this.doubleValue = var5;
        long var7 = System.nanoTime();
        float var17;
        if (this.longValue3 == 0L) {
            var17 = 16.0F;
        } else {
            var17 = Math.min((float)(var7 - this.longValue3) / 1000000.0F, 100.0F);
        }

        this.longValue3 = var7;
        double var19 = var1 - this.doubleValue2;
        double var11 = var3 - this.doubleValue9;
        double var13 = var5 - this.doubleValue4;
        boolean var18;
        if (var19 * var19 + var11 * var11 + var13 * var13 > 64.0) {
            var18 = true;
        } else {
            var18 = false;
        }

        this.doubleValue2 = var1;
        this.doubleValue9 = var3;
        this.doubleValue4 = var5;
        if (this.flag && !var18) {
            double var20 = this.method_1105(var17);
            this.doubleValue5 = this.doubleValue5 + (var1 - this.doubleValue5) * var20;
            this.doubleValue7 = this.doubleValue7 + (var3 - this.doubleValue7) * var20;
            this.doubleValue3 = this.doubleValue3 + (var5 - this.doubleValue3) * var20;
            double var21 = var1 - this.doubleValue5;
            var11 = var3 - this.doubleValue7;
            var13 = var5 - this.doubleValue3;
            double var15;
            if ((var15 = var21 * var21 + var11 * var11 + var13 * var13) > 36.0) {
                var15 = 6.0 / Math.sqrt(var15);
                this.doubleValue5 = var1 - var21 * var15;
                this.doubleValue7 = var3 - var11 * var15;
                this.doubleValue3 = var5 - var13 * var15;
            }
        } else {
            this.flag = true;
            this.doubleValue5 = var1;
            this.doubleValue7 = var3;
            this.doubleValue3 = var5;
        }
    }

    @Override
    public void onDisable() {
        MotionBlurRenderer.method_709();
        this.flag3 = false;
        this.longValue = 0L;
        this.flag2 = false;
        this.longValue2 = 0L;
        this.flag = false;
        this.longValue3 = 0L;
        this.doubleValue2 = 0.0;
        this.doubleValue9 = 0.0;
        this.doubleValue4 = 0.0;
    }

    public double method_1105(float var1) {
        float var5;
        if ((var5 = this.smoothing.method_151()) <= 0.0F) {
            return 1.0;
        } else {
            double var3 = var5 / 100.0 * 400.0;
            return 1.0 - Math.exp(-var1 / var3);
        }
    }

    public double getDoubleValue5() {
        return this.doubleValue5;
    }

    public Camera() {
        super("Camera", "Smooth trailing camera position and third-person orbit", Category.RENDER);
    }

    public double method_1106() {
        return this.distance.method_151() / 100.0;
    }

    private static float method_1107(float var0) {
        if ((var0 = var0 % 360.0F) >= 180.0F) {
            var0 -= 360.0F;
        }

        if (var0 < -180.0F) {
            var0 += 360.0F;
        }

        return var0;
    }

    public double getDoubleValue() {
        return this.doubleValue;
    }

    public float getFloatValue5() {
        return this.floatValue5;
    }

    public double getDoubleValue7() {
        return this.doubleValue7;
    }

    public double getDoubleValue6() {
        return this.doubleValue6;
    }

    public static final class FirstPersonSettings extends SettingGroup {
        public NumberSetting smoothing = new NumberSetting("Smoothing", 40.0, 5.0, 90.0, 5.0)
            .method_147("%")
            .describe("How far the first-person view lags your aim");
        public BooleanSetting smoothPitch = new BooleanSetting("Smooth pitch", true).describe("Also ease vertical movement, not just turning");

        private FirstPersonSettings() {
            super("First person", false);
        }
    }

    public static final class MotionBlurSettings extends SettingGroup {
        public NumberSetting amount = new NumberSetting("Amount", 45.0, 5.0, 90.0, 5.0).method_147("%").describe("How much of the previous frame is kept");
        public BooleanSetting onlyWhenMoving = new BooleanSetting("Only when moving", true).describe("Fade the blur in with your speed");

        private MotionBlurSettings() {
            super("Motion blur", false);
        }
    }
}
