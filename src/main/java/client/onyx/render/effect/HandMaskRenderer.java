package client.onyx.render.effect;

import client.onyx.Onyx;
import client.onyx.module.render.Hand;
import client.onyx.render.shader.ShaderProgram;
import client.onyx.render.util.RenderTarget;
import client.onyx.ui.theme.color.ColorUtils;
import client.onyx.util.IMinecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;

public final class HandMaskRenderer implements IMinecraft {
    private static final RenderTarget renderTarget = new RenderTarget(true);
    private static boolean flag4 = true;
    private static final int INT_58891 = 5889;
    private static boolean flag;
    private static boolean flag2;
    private static int intValue;
    private static ShaderProgram shaderProgram;
    private static final int INT_58881 = 5888;
    private static boolean flag3;
    private static int intValue2;

    private HandMaskRenderer() {
    }

    private static void method_357(ItemRenderer var0, float var1, Framebuffer var2, float var3, int var4, Hand.GlintSettings var5) {
        ItemRenderer var10 = var0;
        renderTarget.method_531(Math.round(intValue2 * var3), Math.round(intValue * var3));
        if (var5 != null) {
            HandShaderRenderer.method_683(
                HandGlowRenderer.method_727(var5, (float)renderTarget.getIntValue5() / intValue2), renderTarget.getIntValue5(), renderTarget.getIntValue2()
            );
        }

        renderTarget.method_529();
        shaderProgram.method_817();
        shaderProgram.method_815("Sampler0", 0);
        shaderProgram.method_822(
            "uColor", ColorUtils.method_201(var4) / 255.0F, ColorUtils.method_210(var4) / 255.0F, ColorUtils.method_214(var4) / 255.0F, 1.0F
        );
        GlStateManager.disableBlend();
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.pushMatrix();
        flag2 = true;

        try {
            var10.renderItemInFirstPerson(var1);
        } finally {
            flag2 = false;
        }

        GlStateManager.popMatrix();
        ShaderProgram.method_819();
        HandShaderRenderer.method_682();
        var2.bindFramebuffer(false);
        GlStateManager.viewport(0, 0, intValue2, intValue);
    }

    public static boolean isFlag2() {
        return flag2;
    }

    public static void method_358(Framebuffer var0) {
        ShaderProgram.method_819();
        method_359(2, 0);
        method_359(1, 0);
        method_359(0, 0);
        var0.bindFramebuffer(false);
        GlStateManager.viewport(0, 0, intValue2, intValue);
        GlStateManager.matrixMode(5889);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.resetColor();
    }

    public static void method_359(int var0, int var1) {
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit + var0);
        GlStateManager.bindTexture(var1);
    }

    public static void method_360() {
        GlStateManager.matrixMode(5889);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        GL11.glOrtho(0.0, 1.0, 0.0, 1.0, -1.0, 1.0);
        GlStateManager.matrixMode(5888);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        GlStateManager.enableTexture2D();
    }

    public static void method_361(Throwable var0) {
        flag4 = false;
        HandShaderRenderer.method_682();
        renderTarget.method_532();
        HandGlowRenderer.method_730();
        HandTrailRenderer.method_497();
        if (!flag3) {
            flag3 = true;
            Onyx.LOGGER.warn("Hand effects have been disabled after a render failure", var0);
        }
    }

    public static void method_362(ItemRenderer var0, float var1) {
        ItemRenderer var3 = var0;
        if (flag4 && Onyx.moduleManager != null) {
            if (OpenGlHelper.shadersSupported) {
                Hand var5 = Onyx.moduleManager.hand;
                Hand.TrailSettings var7;
                Hand.GlintSettings var20;
                if ((var20 = Onyx.moduleManager.hand.method_1296()) == null) {
                    var7 = var5.method_1285();
                } else {
                    var7 = null;
                }

                Hand.TrailSettings var4 = var7;
                if (var20 == null && var7 == null) {
                    HandTrailRenderer.method_497();
                } else {
                    Framebuffer var2 = IMinecraft.mc.getFramebuffer();
                    if (OpenGlHelper.isFramebufferEnabled() && var2 != null) {
                        intValue2 = var2.framebufferWidth;
                        intValue = var2.framebufferHeight;
                        if (intValue2 > 0 && intValue > 0) {
                            try {
                                if (!flag) {
                                    flag = true;
                                    shaderProgram = ShaderProgram.method_820("onyx_hand_mask");
                                }

                                if (shaderProgram != null && shaderProgram.isFlag()) {
                                    float var8;
                                    if (var20 != null) {
                                        var8 = var5.method_1284();
                                    } else {
                                        var8 = var4.method_1160();
                                    }

                                    ItemRenderer var13;
                                    float var14;
                                    Framebuffer var15;
                                    float var16;
                                    int var17;
                                    Hand.GlintSettings var18;
                                    if (var20 == null) {
                                        var18 = var20;
                                        var17 = -1;
                                        var16 = var8;
                                        var15 = var2;
                                        var14 = var1;
                                        var13 = var3;
                                    } else {
                                        int var10004 = var20.color.method_118();
                                        var18 = var20;
                                        var17 = var10004;
                                        var16 = var8;
                                        var15 = var2;
                                        var14 = var1;
                                        var13 = var3;
                                    }

                                    method_357(var13, var14, var15, var16, var17, var18);
                                    if (var20 != null) {
                                        HandGlowRenderer.method_735(renderTarget, var2, var20, intValue2, intValue);
                                    } else {
                                        HandTrailRenderer.method_503(
                                            renderTarget, var2, var4, var5.method_1283(), var5.method_1279(), intValue2, intValue, var1
                                        );
                                    }
                                } else {
                                    flag4 = false;
                                }
                            } catch (Throwable var19) {
                                method_361(var19);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void method_363() {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0F, 0.0F);
        GL11.glVertex2f(0.0F, 0.0F);
        GL11.glTexCoord2f(1.0F, 0.0F);
        GL11.glVertex2f(1.0F, 0.0F);
        GL11.glTexCoord2f(1.0F, 1.0F);
        GL11.glVertex2f(1.0F, 1.0F);
        GL11.glTexCoord2f(0.0F, 1.0F);
        GL11.glVertex2f(0.0F, 1.0F);
        GL11.glEnd();
    }
}
