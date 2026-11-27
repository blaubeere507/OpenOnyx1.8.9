package client.onyx.render.gui;

import client.onyx.Onyx;
import client.onyx.module.hud.CustomGui;
import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easings;
import client.onyx.render.util.RenderTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;

public final class GuiOpenAnimation {
    private static final Animation animation = new Animation(1.0F);
    private static final RenderTarget renderTarget = new RenderTarget(false);
    private static boolean flag = true;
    private static final float FLOAT_100_01 = 100.0F;
    private static boolean flag2;
    private static boolean flag3;
    private static final float FLOAT_16_01 = 16.0F;
    private static long longValue;

    public static void method_866(GuiScreen var0) {
        if (method_874(var0)) {
            CustomGui var2;
            if ((var2 = CustomGuiState.method_977()) != null) {
                longValue = 0L;
                animation.method_607(0.0F).method_603(1.0F, var2.openAnimationSettings.duration.method_151(), Easings.easing7);
            }
        }
    }

    public static void method_867() {
        if (flag3) {
            GlStateManager.popMatrix();
            flag3 = false;
        } else if (flag2) {
            flag2 = false;
            Minecraft var10000 = Minecraft.getMinecraft();
            method_870(var10000);
            method_872(var10000);
        }
    }

    private static float method_868() {
        CustomGui var0;
        float var2;
        if ((var0 = CustomGuiState.method_977()) == null) {
            var2 = 0.92F;
        } else {
            var2 = var0.openAnimationSettings.scaleFrom.method_151();
        }

        return var2 + (1.0F - var2) * animation.method_606();
    }

    public static boolean isFlag2() {
        return flag2;
    }

    public static void method_869() {
        animation.method_607(1.0F);
        flag2 = false;
        flag3 = false;
    }

    private GuiOpenAnimation() {
    }

    private static void method_870(Minecraft var0) {
        OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
        Framebuffer var2 = var0.getFramebuffer();
        if (OpenGlHelper.isFramebufferEnabled() && var2 != null) {
            var2.bindFramebuffer(true);
        } else {
            GlStateManager.viewport(0, 0, var0.displayWidth, var0.displayHeight);
        }
    }

    public static void method_871() {
        flag2 = false;
        flag3 = false;
        Minecraft var0 = Minecraft.getMinecraft();
        if (method_873() && method_874(var0.currentScreen) && !animation.method_604()) {
            method_875();
            if (CustomGuiState.method_977().openAnimationSettings.fade.method_146() && flag && OpenGlHelper.framebufferSupported) {
                try {
                    renderTarget.method_531(var0.displayWidth, var0.displayHeight);
                    renderTarget.method_529();
                    flag2 = true;
                } catch (Throwable var2) {
                    flag = false;
                    renderTarget.method_532();
                    Onyx.LOGGER.warn("GUI open animation fell back to scaling after a target failure", var2);
                    flag3 = true;
                    method_876(var0);
                }
            } else {
                flag3 = true;
                method_876(var0);
            }
        }
    }

    private static void method_872(Minecraft var0) {
        ScaledResolution var10000 = new ScaledResolution(var0);
        float var5 = var10000.getScaledWidth();
        float var2 = var10000.getScaledHeight();
        float var6 = method_868();
        float var4 = animation.method_606();
        float var1 = var5 * (1.0F - var6) / 2.0F;
        float var7 = var2 * (1.0F - var6) / 2.0F;
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
        GlStateManager.disableLighting();
        GlStateManager.bindTexture(renderTarget.getIntValue4());
        GlStateManager.tryBlendFuncSeparate(1, 771, 1, 771);
        GlStateManager.color(var4, var4, var4, var4);
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0F, 1.0F);
        GL11.glVertex2f(var1, var7);
        GL11.glTexCoord2f(0.0F, 0.0F);
        GL11.glVertex2f(var1, var2 - var7);
        GL11.glTexCoord2f(1.0F, 0.0F);
        GL11.glVertex2f(var5 - var1, var2 - var7);
        GL11.glTexCoord2f(1.0F, 1.0F);
        GL11.glVertex2f(var5 - var1, var7);
        GL11.glEnd();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.enableAlpha();
        GlStateManager.enableDepth();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.resetColor();
    }

    private static boolean method_873() {
        CustomGui var0;
        return (var0 = CustomGuiState.method_977()) != null && var0.isToggled() && var0.openAnimationSettings.method_61();
    }

    private static boolean method_874(GuiScreen var0) {
        return var0 != null && !(var0 instanceof GuiChat) && method_873() && (var0 instanceof GuiContainer || var0 instanceof GuiIngameMenu);
    }

    private static void method_875() {
        long var0 = System.nanoTime();
        float var3;
        if (longValue == 0L) {
            var3 = 16.0F;
        } else {
            var3 = Math.min((float)(var0 - longValue) / 1000000.0F, 100.0F);
        }

        longValue = var0;
        animation.method_605(var3);
    }

    private static void method_876(Minecraft var0) {
        ScaledResolution var10000 = new ScaledResolution(var0);
        float var1 = var10000.getScaledWidth();
        float var4 = var10000.getScaledHeight();
        float var3 = method_868();
        GlStateManager.pushMatrix();
        GlStateManager.translate(var1 / 2.0F, var4 / 2.0F, 0.0F);
        GlStateManager.scale(var3, var3, 1.0F);
        GlStateManager.translate(-var1 / 2.0F, -var4 / 2.0F, 0.0F);
    }

    public static float method_877() {
        return method_873() ? animation.method_606() : 1.0F;
    }
}
