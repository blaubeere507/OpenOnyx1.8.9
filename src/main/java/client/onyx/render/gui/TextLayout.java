package client.onyx.render.gui;

import client.onyx.module.hud.CustomGui;
import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easings;
import client.onyx.render.ui.UiRenderer;
import client.onyx.render.util.FilterMode;
import client.onyx.ui.theme.Palette;
import client.onyx.ui.theme.Theme;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;

public final class TextLayout {
    private static final TextLayout.Run run = new TextLayout.Run(16.0F, 9.0F, 34.0F, 9.0F, 1.06F);
    private static final TextLayout.Run run2 = new TextLayout.Run(52.0F, 9.0F, 88.0F, 9.0F);
    private static final TextLayout.Run run3 = new TextLayout.Run(25.0F, 18.0F, 16.0F, 18.0F);
    private static final float FLOAT_0_21 = 0.2F;
    private static final float FLOAT_2_01 = 2.0F;
    private static final int INT_101 = 10;
    private static final float FLOAT_471_01 = 471.0F;
    private static final float FLOAT_8_01 = 8.0F;
    public static final float FLOAT_103_01 = 103.0F;
    private static final float FLOAT_22_01 = 22.0F;
    private static final float FLOAT_8_02 = 8.0F;
    private static final float FLOAT_9_01 = 9.0F;
    private static final int INT_N46682041 = -4668204;
    private static final float FLOAT_0_61 = 0.6F;
    private static final float FLOAT_250_01 = 250.0F;
    private static final float FLOAT_0_0061 = 0.006F;
    private static final float FLOAT_0_51 = 0.5F;
    private static final float FLOAT_0_0062 = 0.006F;
    private static final int INT_N52115881 = -5211588;
    private static final float FLOAT_1_61 = 1.6F;
    private static final float FLOAT_2_02 = 2.0F;
    private static final int INT_N457451 = -45745;
    private static final int INT_2561 = 256;
    private static final int INT_N150431 = -15043;
    private static final int INT_N116881931 = -11688193;
    private static final float FLOAT_0_251 = 0.25F;
    private static final int INT_N20479041 = -2047904;
    private static final float FLOAT_4_01 = 4.0F;
    private static final float FLOAT_8_03 = 8.0F;
    private static final float FLOAT_6_01 = 6.0F;
    private final TextLayout.Line line6 = new TextLayout.Line();
    private final TextLayout.Line line5 = new TextLayout.Line();
    private final TextLayout.Line line4 = new TextLayout.Line();
    private final TextLayout.Line line3 = new TextLayout.Line();
    private final TextLayout.Line line = new TextLayout.Line();
    private final TextLayout.Line line2 = new TextLayout.Line();
    private float floatValue6 = 20.0F;
    private float floatValue5 = Float.NaN;
    private float floatValue2 = -1.0F;
    private final CustomGui customGui;
    private float floatValue;
    private float floatValue3;
    private float floatValue4;

    public void method_941(UiRenderer var1, float var2, float var3, float var4, float var5) {
        EntityPlayerSP var6;
        if ((var6 = Minecraft.getMinecraft().thePlayer) != null) {
            this.floatValue3 = (this.floatValue3 + var5) % 100000.0F;
            this.method_952(var6);
            this.method_954(var5);
            Palette var9 = Theme.getPalette();
            var4 -= 2.0F;
            if (!this.customGui.statusBarSettings.mode.method_168(CustomGui.StatusBarMode.ICONS)) {
                this.method_962(var1, var9, var6, var2, var3, var4);
            } else {
                this.method_942(var1, var9, var6, var2, var3, var4);
            }
        }
    }

    private void method_942(UiRenderer var1, Palette var2, EntityPlayer var3, float var4, float var5, float var6) {
        float var24 = var6 - 8.0F;
        boolean var10;
        if (var3.getTotalArmorValue() > 0) {
            var10 = true;
        } else {
            var10 = false;
        }

        boolean var7 = method_955(var3);
        float var8 = this.method_957(var1, var3, var4, var24);
        float var12;
        if (method_961(var3) == null) {
            this.method_946(var1, method_949(var3), this.line5, var5, var24, false, 1.0F, 0.0F);
            var12 = var8;
        } else {
            this.method_946(var1, run2, this.line2, var5, var24, false, 1.0F, 0.0F);
            var12 = var8;
        }

        float var23 = var12 - 8.0F - 2.0F;
        float var9 = var24 - 8.0F - 2.0F;
        if (var10) {
            this.method_946(var1, run, this.line4, var4, var23, true, 1.0F, 0.0F);
        }

        if (var7) {
            this.method_946(var1, run3, this.line3, var5, var9, false, 1.0F, 0.0F);
        }

        TextLayout var14;
        float var15;
        boolean var16;
        if (!var10) {
            var16 = var7;
            var15 = var8;
            var14 = this;
        } else {
            var16 = var7;
            var15 = var23;
            var14 = this;
        }

        TextLayout var19;
        float var20;
        float var21;
        if (!var16) {
            var21 = var24;
            var20 = var15;
            var19 = var14;
        } else {
            var21 = var9;
            var20 = var15;
            var19 = var14;
        }

        var19.floatValue5 = Math.min(var20, var21);
    }

    private static TextLayout.Run method_943(EntityPlayer var0) {
        float var3;
        if (var0.isPotionActive(Potion.poison)) {
            var3 = 52.0F;
        } else if (var0.isPotionActive(Potion.wither)) {
            var3 = 88.0F;
        } else {
            var3 = 16.0F;
        }

        float var4;
        if (var0.worldObj.getWorldInfo().isHardcoreModeEnabled()) {
            var4 = 45.0F;
        } else {
            var4 = 0.0F;
        }

        return new TextLayout.Run(16.0F, var4, var3 + 36.0F, var4);
    }

    private float method_944() {
        return this.floatValue4 <= 0.0F ? 0.0F : (float)Math.sin(this.floatValue3 * 0.05F) * 1.6F * this.floatValue4;
    }

    public boolean method_945() {
        Minecraft var2 = Minecraft.getMinecraft();
        return this.customGui.isToggled() && this.customGui.statusBarSettings.method_61() && var2.thePlayer != null && var2.playerController.shouldDrawHUD();
    }

    private void method_946(UiRenderer var1, TextLayout.Run var2, TextLayout.Line var3, float var4, float var5, boolean var6, float var7, float var8) {
        var var22 = this;
        float var9 = var3.method_905() * 10.0F;
        float var10 = var3.method_907() * 10.0F;
        boolean var11 = this.customGui.statusBarSettings.animate.method_146();
        boolean var12 = this.customGui.statusBarSettings.damageTrail.method_146();
        var1.method_440(var7);
        int var21 = 0;

        for (int var19 = 0; var19 < 10; var19 = var21) {
            float var16;
            float var17;
            if (!var6) {
                float var23 = var4 - (var21 + 1) * 8.0F;
                var17 = var8;
                var16 = var23;
            } else {
                float var24 = var4 + var21 * 8.0F;
                var17 = var8;
                var16 = var24;
            }

            float var13 = var16 + var17;
            float var18;
            if (var11) {
                var18 = var22.method_960(var21, var3);
            } else {
                var18 = 1.0F;
            }

            var1.method_441();
            var1.method_491(var18, var13 + 4.0F, var5 + 4.0F);
            var22.method_947(var1, var2, var13, var5);
            if (var12) {
                var22.method_956(var1, var2, var13, var5, Math.clamp(var10 - var21, 0.0F, 1.0F), var6, 0.45F);
            }

            float var10005 = Math.clamp(var9 - var21, 0.0F, 1.0F);
            var21++;
            var22.method_956(var1, var2, var13, var5, var10005, var6, 1.0F);
            var1.method_435();
        }

        var1.method_430();
    }

    private void method_947(UiRenderer var1, TextLayout.Run var2, float var3, float var4) {
        float var7 = var2.method_920();
        float var6 = var2.method_919();
        var1.method_487(Gui.icons, var3 + var6, var4 + var6, var7, var7, var2.floatValue3(), var2.floatValue5(), 9.0F, 9.0F, 256, 256, -1, FilterMode.PIXELS);
    }

    private float method_948() {
        return this.customGui.statusBarSettings.animate.method_146() && !(this.line6.method_905() > 0.2F)
            ? 1.0F - 0.25F * (0.5F + 0.5F * (float)Math.sin(this.floatValue3 * 0.006F * Math.PI))
            : 1.0F;
    }

    private static TextLayout.Run method_949(EntityPlayer var0) {
        boolean var10000 = var0.isPotionActive(Potion.hunger);
        TextLayout.Run var10002;
        if (var10000) {
            var10002 = new TextLayout.Run(133.0F, 27.0F, 88.0F, 27.0F);
            return var10002;
        } else {
            var10002 = new TextLayout.Run(16.0F, 27.0F, 52.0F, 27.0F);
            return var10002;
        }
    }

    private void method_950(UiRenderer var1, TextLayout.Run var2, float var3, float var4, float var5, float var6, float var7) {
        if (!(var6 <= var5)) {
            float var8 = var2.method_920();
            float var12 = var2.method_919();
            boolean var11;
            if (var5 <= 0.0F && var6 >= 1.0F) {
                var11 = true;
            } else {
                var11 = false;
            }

            var1.method_440(var7);
            if (!var11) {
                var1.method_443(var3 + var12 + var8 * var5, var4 + var12, var8 * (var6 - var5), var8);
            }

            var1.method_487(
                Gui.icons, var3 + var12, var4 + var12, var8, var8, var2.floatValue4(), var2.floatValue2(), 9.0F, 9.0F, 256, 256, -1, FilterMode.PIXELS
            );
            if (!var11) {
                var1.method_486();
            }

            var1.method_430();
        }
    }

    private static TextLayout.Run method_951(EntityPlayer var0) {
        float var3;
        if (var0.isPotionActive(Potion.poison)) {
            var3 = 52.0F;
        } else if (var0.isPotionActive(Potion.wither)) {
            var3 = 88.0F;
        } else {
            var3 = 16.0F;
        }

        float var4;
        if (var0.worldObj.getWorldInfo().isHardcoreModeEnabled()) {
            var4 = 45.0F;
        } else {
            var4 = 0.0F;
        }

        return new TextLayout.Run(16.0F, var4, var3 + 144.0F, var4);
    }

    private void method_952(EntityPlayer var1) {
        this.floatValue6 = Math.max((float)var1.getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue(), 1.0F);
        this.floatValue = Math.max(var1.getAbsorptionAmount(), 0.0F);
        float var2 = var1.getHealth();
        this.line6.method_906(var2, this.floatValue6);
        this.line.method_906(this.floatValue, this.floatValue6);
        this.line4.method_906(var1.getTotalArmorValue(), 20.0F);
        this.line5.method_906(var1.getFoodStats().getFoodLevel(), 20.0F);
        TextLayout.Line var10 = this.line3;
        TextLayout.Line var4;
        float var5;
        if (!method_955(var1)) {
            TextLayout.Line var3 = var10;
            var5 = 300.0F;
            var4 = var3;
        } else {
            var5 = var1.getAir();
            var4 = var10;
        }

        var4.method_906(var5, 300.0F);
        EntityLivingBase var7;
        if ((var7 = method_961(var1)) != null) {
            this.line2.method_906(var7.getHealth(), var7.getMaxHealth());
        }

        float var8 = var2 + this.floatValue;
        if (this.customGui.statusBarSettings.animate.method_146() && this.floatValue2 >= 0.0F && var8 < this.floatValue2 - 0.01F) {
            this.floatValue4 = 1.0F;
        }

        this.floatValue2 = var8;
    }

    public void method_953() {
        this.floatValue4 = 0.0F;
        this.floatValue2 = -1.0F;
        this.floatValue5 = Float.NaN;
    }

    private void method_954(float var1) {
        this.line6.method_904(var1);
        this.line.method_904(var1);
        this.line4.method_904(var1);
        this.line5.method_904(var1);
        this.line3.method_904(var1);
        this.line2.method_904(var1);
        this.floatValue4 = Math.max(0.0F, this.floatValue4 - var1 * 0.006F);
    }

    private static boolean method_955(EntityPlayer var0) {
        return var0.isInsideOfMaterial(Material.water) || var0.getAir() < 300;
    }

    private void method_956(UiRenderer var1, TextLayout.Run var2, float var3, float var4, float var5, boolean var6, float var7) {
        if (!(var5 <= 0.0F)) {
            if (!var6) {
                this.method_950(var1, var2, var3, var4, 1.0F - var5, 1.0F, var7);
            } else {
                this.method_950(var1, var2, var3, var4, 0.0F, var5, var7);
            }
        }
    }

    private float method_957(UiRenderer var1, EntityPlayer var2, float var3, float var4) {
        var var20 = this;
        TextLayout.Run var37 = method_943(var2);
        TextLayout.Run var5 = method_951(var2);
        float var6 = this.floatValue6 + this.floatValue;
        int var38 = Math.max(1, MathHelper.ceiling_float_int(var6 / 2.0F));
        int var7 = Math.max(1, MathHelper.ceiling_float_int(var38 / 10.0F));
        float var8 = Math.max(10.0F - (var7 - 2), 3.0F);
        float var9 = this.line6.method_905() * this.floatValue6;
        float var10 = this.line6.method_907() * this.floatValue6;
        float var11 = this.floatValue6 + this.line.method_905() * this.floatValue6;
        float var12 = this.floatValue6 + this.line.method_907() * this.floatValue6;
        int var13 = MathHelper.floor_float(this.floatValue6 / 2.0F);
        boolean var14 = this.customGui.statusBarSettings.animate.method_146();
        boolean var15 = this.customGui.statusBarSettings.damageTrail.method_146();
        float var16 = this.method_944();
        var1.method_440(this.method_948());
        int var17 = 0;

        for (int var35 = 0; var35 < var38; var35 = var17) {
            float var18 = var3 + var17 % 10 * 8.0F + var16;
            float var19 = var4 - var17 / 10 * var8;
            boolean var23;
            float var36;
            if ((var36 = var17 * 2.0F) >= var20.floatValue6) {
                var23 = true;
            } else {
                var23 = false;
            }

            float var34;
            if (var14) {
                TextLayout var26;
                int var27;
                boolean var28;
                if (!var23) {
                    int var10001 = var17 % 10;
                    var28 = var23;
                    var27 = var10001;
                    var26 = var20;
                } else {
                    int var39 = Math.max(var17 - var13, 0);
                    var28 = var23;
                    var27 = var39;
                    var26 = var20;
                }

                TextLayout var31;
                int var32;
                TextLayout.Line var33;
                if (!var28) {
                    var33 = var20.line6;
                    var32 = var27;
                    var31 = var26;
                } else {
                    var33 = var20.line;
                    var32 = var27;
                    var31 = var26;
                }

                var34 = var31.method_960(var32, var33);
            } else {
                var34 = 1.0F;
            }

            var1.method_441();
            var1.method_491(var34, var18 + 4.0F, var19 + 4.0F);
            var20.method_947(var1, var37, var18, var19);
            if (var15) {
                var20.method_950(var1, var37, var18, var19, 0.0F, method_959(var10 - var36), 0.45F);
                var20.method_950(var1, var5, var18, var19, method_959(var20.floatValue6 - var36), method_959(var12 - var36), 0.45F);
            }

            var20.method_950(var1, var37, var18, var19, 0.0F, method_959(var9 - var36), 1.0F);
            float var10005 = method_959(var20.floatValue6 - var36);
            float var10006 = method_959(var11 - var36);
            var17++;
            var20.method_950(var1, var5, var18, var19, var10005, var10006, 1.0F);
            var1.method_435();
        }

        var1.method_430();
        return var4 - (var7 - 1) * var8;
    }

    private void method_958(UiRenderer var1, Palette var2, TextLayout.Line var3, int var4, float var5, float var6, float var7, float var8, float var9) {
        var1.method_440(var8);
        var1.method_479(var5, var6, var7, 6.0F, Float.MAX_VALUE, var2.intValue11());
        float var11;
        if (this.customGui.statusBarSettings.damageTrail.method_146() && (var11 = var7 * var3.method_907()) > 0.5F) {
            var1.method_479(var5, var6, var11, 6.0F, Float.MAX_VALUE, Theme.method_180(var4, 0.4F));
        }

        float var12;
        if ((var12 = var7 * var3.method_905()) > 0.5F) {
            var1.method_479(var5, var6, var12, 6.0F, Float.MAX_VALUE, var4);
        }

        float var13;
        if (var9 > 0.0F && (var13 = var7 * Math.clamp(var9, 0.0F, 1.0F)) > 0.5F) {
            var1.method_479(var5, var6, var13, 6.0F, Float.MAX_VALUE, Theme.method_180(-15043, 0.85F));
        }

        var1.method_430();
    }

    public float getFloatValue5() {
        return this.floatValue5;
    }

    private static float method_959(float var0) {
        return Math.clamp(var0 / 2.0F, 0.0F, 1.0F);
    }

    private float method_960(int var1, TextLayout.Line var2) {
        float var3;
        if ((var3 = var2.getFloatValue2() - var1 * 22.0F) < 0.0F) {
            return 0.6F;
        } else {
            return var3 >= 250.0F ? 1.0F : 0.6F + 0.39999998F * (float)Easings.easing7.method_608(var3 / 250.0F);
        }
    }

    public TextLayout(CustomGui var1) {
        this.customGui = var1;
    }

    private static EntityLivingBase method_961(EntityPlayer var0) {
        Entity var2;
        return (var2 = var0.ridingEntity) instanceof EntityLivingBase ? (EntityLivingBase)var2 : null;
    }

    private void method_962(UiRenderer var1, Palette var2, EntityPlayer var3, float var4, float var5, float var6) {
        float var7 = (var5 - var4 - 8.0F) / 2.0F;
        var5 -= var7;
        float var18 = var6 - 6.0F;
        boolean var10;
        if (var3.getTotalArmorValue() > 0) {
            var10 = true;
        } else {
            var10 = false;
        }

        boolean var19 = method_955(var3);
        this.method_958(var1, var2, this.line6, -45745, var4 + this.method_944(), var18, var7, this.method_948(), this.line.method_905());
        float var12;
        if (method_961(var3) == null) {
            this.method_958(var1, var2, this.line5, -5211588, var5, var18, var7, 1.0F, 0.0F);
            var12 = var18;
        } else {
            this.method_958(var1, var2, this.line2, -2047904, var5, var18, var7, 1.0F, 0.0F);
            var12 = var18;
        }

        float var9 = var12 - 6.0F - 4.0F;
        if (var10) {
            this.method_958(var1, var2, this.line4, -4668204, var4, var9, var7, 1.0F, 0.0F);
        }

        if (var19) {
            this.method_958(var1, var2, this.line3, -11688193, var5, var9, var7, 1.0F, 0.0F);
        }

        TextLayout var15;
        float var16;
        label31: {
            TextLayout var13;
            if (var10) {
                var13 = this;
            } else {
                if (!var19) {
                    var16 = var18;
                    var15 = this;
                    break label31;
                }

                var13 = this;
            }

            var16 = var9;
            var15 = var13;
        }

        var15.floatValue5 = var16;
    }

    private static final class Line {
        private final Animation animation = new Animation(0.0F);
        private final Animation animation2 = new Animation(0.0F);
        private float floatValue2 = 471.0F;
        private float floatValue;

        public void method_904(float var1) {
            this.floatValue2 = Math.min(this.floatValue2 + var1, 471.0F);
            this.animation.method_603(this.floatValue, 200.0F, Easings.easing2);
            this.animation.method_605(var1);
            if (!(this.floatValue >= this.animation2.method_606())) {
                this.animation2.method_603(this.floatValue, 500.0F, Easings.easing);
                this.animation2.method_605(var1);
            } else {
                this.animation2.method_607(Math.max(this.floatValue, this.animation.method_606()));
            }
        }

        public float method_905() {
            return this.animation.method_606();
        }

        public void method_906(float var1, float var2) {
            float var4;
            if (var2 <= 0.0F) {
                var4 = 0.0F;
            } else {
                var4 = Math.clamp(var1 / var2, 0.0F, 1.0F);
            }

            if (var4 > this.floatValue) {
                this.floatValue2 = 0.0F;
            }

            this.floatValue = var4;
        }

        public float method_907() {
            return this.animation2.method_606();
        }

        public float getFloatValue2() {
            return this.floatValue2;
        }
    }

    private record Run(float floatValue3, float floatValue5, float floatValue4, float floatValue2, float floatValue) {
        public float method_919() {
            return (8.0F - this.method_920()) / 2.0F;
        }

        public Run(float var1, float var2, float var3, float var4) {
            this(var1, var2, var3, var4, 1.0F);
            float var10005 = 1.0F;
        }

        public float method_920() {
            return 8.0F * this.floatValue;
        }
    }
}
