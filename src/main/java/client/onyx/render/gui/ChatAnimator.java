package client.onyx.render.gui;

import client.onyx.module.hud.CustomGui;
import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easings;
import client.onyx.render.anim.Spring;
import client.onyx.render.font.FontFiles;
import client.onyx.render.font.FontStyle;
import client.onyx.render.ui.PanelRenderer;
import client.onyx.render.ui.UiRenderer;
import client.onyx.ui.theme.ColorRoles;
import client.onyx.ui.theme.Palette;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.Typography;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public final class ChatAnimator {
    private static final FontStyle fontStyle = new FontStyle(FontFiles.Size.PT9, FontFiles.Weight.BOLD, 9.0F, 11.0F);
    private static final float FLOAT_7_01 = 7.0F;
    private static final int INT_91 = 9;
    private static final float FLOAT_22_01 = 22.0F;
    private static final float FLOAT_3_01 = 3.0F;
    private static final float FLOAT_2_51 = 2.5F;
    private static final float FLOAT_16_01 = 16.0F;
    private static final float FLOAT_4_01 = 4.0F;
    private static final float FLOAT_0_01 = 0.0F;
    private static final int INT_1342177281 = 134217728;
    private static final float FLOAT_6_01 = 6.0F;
    private static final float FLOAT_1_51 = 1.5F;
    private static final float FLOAT_2_01 = 2.0F;
    private static final float FLOAT_0_51 = 0.5F;
    private static final int INT_N117408281 = -11740828;
    private static final float FLOAT_4_02 = 4.0F;
    private static final float FLOAT_0_551 = 0.55F;
    private static final float FLOAT_0_02 = 0.0F;
    private static final float FLOAT_1_01 = 1.0F;
    private static final float FLOAT_2_02 = 2.0F;
    private static final float FLOAT_1_02 = 1.0F;
    private static final int INT_N503841 = -50384;
    private static final int INT_31 = 3;
    private static final float FLOAT_4_03 = 4.0F;
    private final Animation animation;
    private final Animation[] animationArray;
    private final ItemStack[] itemStackArray;
    private final CustomGui customGui;
    private float floatValue;
    private float floatValue2;
    private float floatValue3;
    private float floatValue4;

    public void method_982(UiRenderer var1, float var2, float var3, float var4) {
        Minecraft var5;
        EntityPlayerSP var6;
        if ((var6 = (var5 = Minecraft.getMinecraft()).thePlayer) != null) {
            float var10 = this.customGui.hotbarSettings.scale.method_151();
            float var8 = 204.0F;
            float var9 = 28.0F;
            this.floatValue3 = var8 * var10;
            this.floatValue = var9 * var10;
            this.method_991(var6, var4);
            this.floatValue4 = (var2 - this.floatValue3) / 2.0F;
            this.floatValue2 = (var2 = var3 - 4.0F - this.floatValue - ChatOpenAnimation.method_997()) - this.method_992(var6, var5) * var10;
            PanelRenderer.method_601(this.customGui.hotbarSettings.blur.method_146());
            var1.method_441();
            var1.method_451(this.floatValue4, var2);
            var1.method_491(var10, 0.0F, 0.0F);
            this.method_984(var1, var5, var6, var8, var9);
            var1.method_435();
        }
    }

    private void method_983(UiRenderer var1, Palette var2, Minecraft var3, EntityPlayer var4, float var5) {
        if (method_986(var3)) {
            float var7 = var5 - 0.0F;
            float var6 = -10.0F;
            var1.method_479(0.0F, var6, var7, 6.0F, Float.MAX_VALUE, var2.intValue11());
            float var8;
            if ((var8 = var7 * Math.clamp(var4.experience, 0.0F, 1.0F)) > 0.5F) {
                var1.method_479(0.0F, var6, var8, 6.0F, Float.MAX_VALUE, var2.intValue27());
            }

            if (var4.experienceLevel > 0) {
                float var9 = var6 - 1.0F - Typography.fontStyle12.floatValue() / 2.0F;
                var1.method_484(Typography.fontStyle12, String.valueOf(var4.experienceLevel), var5 / 2.0F, var9, var2.intValue27());
            }
        }
    }

    private void method_984(UiRenderer var1, Minecraft var2, EntityPlayer var3, float var4, float var5) {
        var var12 = this;
        Palette var6 = Theme.getPalette();
        int var7 = ColorRoles.method_182(3, var6);
        var1.method_485(0.0F, 0.0F, var4, var5, 7.0F, 3);
        PanelRenderer.method_597(var1, 0.0F, 0.0F, var4, var5, 7.0F, var7, this.customGui.hotbarSettings.blur.method_146());
        float var10 = 22.0F;
        float var13;
        var1.method_479(var13 = 3.0F + this.animation.method_606() * 22.0F + 0.0F, 3.0F, var10, var10, 7.0F, Theme.method_180(var6.intValue31(), 0.28F));
        var1.method_494(var13, 3.0F, var10, var10, 7.0F, 1.5F, var6.intValue31());
        int var11 = 0;

        for (int var8 = 0; var8 < 9; var8 = var11) {
            var12.method_993(var1, var6, var3.inventory.mainInventory[var11], var11++);
        }

        var12.method_983(var1, var6, var2, var3, var4);
    }

    private void method_985(UiRenderer var1, ItemStack var2, float var3, float var4) {
        float var8 = 1.0F - (float)var2.getItemDamage() / var2.getMaxDamage();
        float var6 = 12.0F;
        var3 -= var6 / 2.0F;
        var4 = var4 + 8.0F - 2.0F - 2.0F;
        var1.method_479(var3, var4, var6, 2.0F, Float.MAX_VALUE, -1728053248);
        float var7;
        if (!((var7 = var6 * var8) <= 0.5F)) {
            var1.method_479(var3, var4, var7, 2.0F, Float.MAX_VALUE, Spring.method_616(-50384, -11740828, var8));
        }
    }

    private static boolean method_986(Minecraft var0) {
        return var0.playerController.gameIsSurvivalOrAdventure() && !var0.thePlayer.isRidingHorse();
    }

    public float getFloatValue4() {
        return this.floatValue4;
    }

    private static boolean method_987(ItemStack var0, ItemStack var1) {
        return var0 != null && var1 != null
            ? var0.getItem() != var1.getItem() || !var1.isItemStackDamageable() && var0.getMetadata() != var1.getMetadata()
            : var0 != var1;
    }

    public boolean method_988() {
        Minecraft var2 = Minecraft.getMinecraft();
        return this.customGui.isToggled() && this.customGui.hotbarSettings.method_61() && var2.thePlayer != null && !var2.playerController.isSpectator();
    }

    public float getFloatValue2() {
        return this.floatValue2;
    }

    public float method_989() {
        return this.floatValue4 + this.floatValue3;
    }

    public void method_990() {
        var var1 = this;
        this.animation.method_607(0.0F);
        int var3 = 0;

        for (int var2 = var3; var2 < 9; var2 = var3) {
            var1.animationArray[var3].method_607(1.0F);
            var1.itemStackArray[var3++] = null;
        }
    }

    private void method_991(EntityPlayer var1, float var2) {
        var var3 = this;
        int var4 = var1.inventory.currentItem;
        ChatAnimator var7;
        if (!this.customGui.hotbarSettings.slotGlide.method_146()) {
            this.animation.method_607(var4);
            var7 = this;
        } else {
            this.animation.method_603(var4, 200.0F, Easings.easing2);
            this.animation.method_605(var2);
            var7 = this;
        }

        boolean var10 = var7.customGui.hotbarSettings.itemPop.method_146();
        int var9 = 0;

        for (int var8 = 0; var8 < 9; var8 = var9) {
            ItemStack var5 = var1.inventory.mainInventory[var9];
            if (method_987(var3.itemStackArray[var9], var5)) {
                var3.itemStackArray[var9] = var5;
                if (var10 && var5 != null) {
                    var3.animationArray[var9].method_607(0.55F).method_603(1.0F, 250.0F, Easings.easing2);
                }
            }

            var3.animationArray[var9++].method_605(var2);
        }
    }

    private float method_992(EntityPlayer var1, Minecraft var2) {
        return method_986(var2) ? 10.0F : 0.0F;
    }

    private void method_993(UiRenderer var1, Palette var2, ItemStack var3, int var4) {
        if (var3 != null && var3.getItem() != null) {
            float var5 = 3.0F + var4 * 22.0F + 11.0F;
            float var6 = 14.0F;
            float var11 = this.animationArray[var4].method_606();
            var1.method_441();
            var1.method_491(var11, var5, var6);
            var1.method_431(var5 - 8.0F + 4.0F, var6 - 8.0F + 4.0F, 8.0F, 8.0F, 4.0F, 2.5F, 1.0F, 134217728);
            var1.method_456(var3, var5 - 8.0F, var6 - 8.0F);
            var1.method_435();
            if (var3.stackSize > 1) {
                String var12 = String.valueOf(var3.stackSize);
                float var7 = var5 + 8.0F;
                float var8 = var6 + 8.0F - 1.5F;
                IconFont.method_891(var1, fontStyle, var12, var7 - var1.method_442(fontStyle, var12), var1.method_433(fontStyle, var8), var2.intValue15());
            }

            if (var3.isItemDamaged()) {
                this.method_985(var1, var3, var5, var6);
            }
        }
    }

    public ChatAnimator(CustomGui var1) {
        var var5 = this;
        this.animation = new Animation(0.0F);
        this.animationArray = new Animation[9];
        this.itemStackArray = new ItemStack[9];
        this.customGui = var1;
        int var4 = 0;

        for (int var2 = 0; var2 < 9; var2 = var4) {
            Animation[] var6 = var5.animationArray;
            int var10001 = var4;
            Animation var10002 = new Animation(1.0F);
            var4++;
            var6[var10001] = var10002;
        }
    }
}
