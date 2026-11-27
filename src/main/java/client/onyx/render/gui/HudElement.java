package client.onyx.render.gui;

import client.onyx.render.ui.HealthBarAnimator;
import client.onyx.render.ui.HotbarRenderer;
import client.onyx.render.ui.StatusIcons;
import client.onyx.render.ui.UiRenderer;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.ui.theme.Palette;
import client.onyx.ui.theme.Theme;
import client.onyx.util.IMinecraft;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;

public abstract class HudElement implements IMinecraft {
    public static final float FLOAT_10_01 = 10.0F;
    private static final float FLOAT_7_01 = 7.0F;
    private static final float FLOAT_8_01 = 8.0F;
    private static final float FLOAT_1_01 = 1.0F;
    private final StatusIcons statusIcons = new StatusIcons();
    private HealthBarAnimator.Ghost ghost;
    private final String string;
    private final NumberSetting numberSetting2;
    private final NumberSetting numberSetting3;
    private final NumberSetting numberSetting;
    private boolean flag;
    private boolean flag2;
    private float floatValue;
    private boolean flag3;
    private float floatValue2;
    private float floatValue3;
    private float floatValue4;
    private float floatValue5;
    private float floatValue6;

    public final void method_908() {
        HotbarRenderer.method_855(this.string);
        this.flag = false;
        this.flag2 = false;
    }

    public abstract boolean method_899(boolean var1);

    public abstract void method_902(UiRenderer var1, float var2, float var3, float var4);

    private boolean method_909() {
        return Mouse.isButtonDown(0);
    }

    private void method_910(UiRenderer var1, boolean var2) {
        Palette var3 = Theme.getPalette();
        int var4;
        if (!var2 && !this.flag && !this.flag2) {
            var4 = var3.intValue12();
        } else {
            var4 = var3.intValue31();
        }

        var1.method_494(this.floatValue3, this.floatValue6, this.floatValue, this.floatValue2, 4.0F, 1.0F, var4);
        var1.method_479(this.floatValue3 + this.floatValue - 7.0F, this.floatValue6 + this.floatValue2 - 7.0F, 7.0F, 7.0F, 4.0F, var4);
    }

    public final boolean method_911(double var1, double var3) {
        return var1 >= this.floatValue3 && var1 < this.floatValue3 + this.floatValue && var3 >= this.floatValue6 && var3 < this.floatValue6 + this.floatValue2;
    }

    public abstract float method_898(UiRenderer var1);

    public static float method_912(NumberSetting var0, float var1) {
        return var1 <= 0.0F ? 0.0F : (float)(var1 * var0.getValue() / 100.0);
    }

    public static void method_913(NumberSetting var0, float var1, float var2) {
        if (!(var2 <= 0.0F)) {
            var0.setValue(Math.clamp(var1 / var2, 0.0F, 1.0F) * 100.0);
        }
    }

    private boolean method_914(boolean var1, float var2, float var3, float var4, float var5, float var6, float var7) {
        float var8 = Minecraft.getScaledMouseX() * var6 / mc.displayWidth;
        float var19 = var7 - Minecraft.getScaledMouseY() * var7 / mc.displayHeight - 1.0F;
        boolean var10 = this.method_909();
        boolean var11 = this.method_911(var8, var19);
        boolean var13;
        if (var8 >= this.floatValue3 + this.floatValue - 7.0F
            && var8 < this.floatValue3 + this.floatValue
            && var19 >= this.floatValue6 + this.floatValue2 - 7.0F
            && var19 < this.floatValue6 + this.floatValue2) {
            var13 = true;
        } else {
            var13 = false;
        }

        HudElement var14;
        if (var1 && var10) {
            if (!this.flag3 && var11) {
                this.flag2 = var13;
                HudElement var16;
                boolean var17;
                if (var13) {
                    var17 = false;
                    var16 = this;
                } else {
                    var17 = true;
                    var16 = this;
                }

                var16.flag = var17;
                this.floatValue5 = var8 - this.floatValue3;
                this.floatValue4 = var19 - this.floatValue6;
            }

            var14 = this;
        } else {
            this.flag = false;
            this.flag2 = false;
            this.ghost = HealthBarAnimator.ghost;
            var14 = this;
        }

        var14.flag3 = var10;
        if (this.flag2) {
            float var20 = Math.max(var8 - this.floatValue3, var19 - this.floatValue6);
            float var18;
            if (var8 - this.floatValue3 >= var19 - this.floatValue6) {
                var18 = var2;
            } else {
                var18 = var3;
            }

            var2 = Math.max(var18, 1.0F);
            this.numberSetting.setValue((double)(var20 / var2));
            this.ghost = HealthBarAnimator.ghost;
            return var11;
        } else {
            if (this.flag) {
                this.ghost = HealthBarAnimator.method_861(
                    var8 - this.floatValue5,
                    var19 - this.floatValue4,
                    this.floatValue,
                    this.floatValue2,
                    var6,
                    var7,
                    10.0F,
                    HotbarRenderer.method_856(this.string)
                );
                method_913(this.numberSetting2, this.ghost.floatValue4() - 10.0F, var4);
                method_913(this.numberSetting3, this.ghost.floatValue2() - 10.0F, var5);
            }

            return var11;
        }
    }

    public HudElement(String var1, NumberSetting var2, NumberSetting var3, NumberSetting var4) {
        this.ghost = HealthBarAnimator.ghost;
        this.string = var1;
        this.numberSetting2 = var2;
        this.numberSetting3 = var3;
        this.numberSetting = var4;
    }

    public abstract float method_900(UiRenderer var1);

    public void method_901(float var1) {
    }

    public final void method_915(UiRenderer var1, float var2, float var3, boolean var4) {
        float var5 = CustomGuiState.getFloatValue();
        this.method_901(var5);
        if (!this.method_899(var4)) {
            HotbarRenderer.method_855(this.string);
            this.flag3 = this.method_909();
        } else {
            float var13 = this.numberSetting.method_151();
            float var7 = Math.max(8.0F, this.method_898(var1));
            float var8 = Math.max(8.0F, this.method_900(var1));
            this.floatValue = var7 * var13;
            this.floatValue2 = var8 * var13;
            float var9 = var2 - this.floatValue - 20.0F;
            float var10 = var3 - this.floatValue2 - 20.0F;
            this.floatValue3 = 10.0F + method_912(this.numberSetting2, var9);
            this.floatValue6 = 10.0F + method_912(this.numberSetting3, var10);
            HotbarRenderer.method_857(this.string, this.floatValue3, this.floatValue6, this.floatValue, this.floatValue2);
            boolean var11 = this.method_914(var4, var7, var8, var9, var10, var2, var3);
            if (var4 && var11 && !this.flag && !this.flag2) {
                float[] var12;
                if ((var12 = this.statusIcons.method_865(true))[0] != 0.0F || var12[1] != 0.0F) {
                    method_913(this.numberSetting2, method_912(this.numberSetting2, var9) + var12[0], var9);
                    method_913(this.numberSetting3, method_912(this.numberSetting3, var10) + var12[1], var10);
                }
            } else {
                this.statusIcons.method_865(false);
            }

            float var14 = this.numberSetting.method_151();
            this.floatValue = var7 * var14;
            this.floatValue2 = var8 * var14;
            this.floatValue3 = 10.0F + method_912(this.numberSetting2, var9);
            this.floatValue6 = 10.0F + method_912(this.numberSetting3, var10);
            if (this.flag || this.flag2) {
                HealthBarAnimator.method_862(var1, var2, var3, this.ghost);
            }

            var1.method_441();
            var1.method_451(this.floatValue3, this.floatValue6);
            var1.method_491(var14, 0.0F, 0.0F);
            this.method_902(var1, var7, var8, var5);
            var1.method_435();
            if (var4) {
                this.method_910(var1, var11);
            }
        }
    }
}
