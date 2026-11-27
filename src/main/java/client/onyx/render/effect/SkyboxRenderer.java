package client.onyx.render.effect;

import client.onyx.Onyx;
import client.onyx.module.render.Skybox;
import client.onyx.render.shader.ShaderProgram;
import client.onyx.render.util.RenderTarget;
import client.onyx.ui.theme.color.ColorUtils;
import client.onyx.util.IMinecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;

public final class SkyboxRenderer implements IMinecraft {
    private static final RenderTarget renderTarget = new RenderTarget(false);
    private static boolean flag3 = true;
    private static boolean flag2 = true;
    private static final int INT_58891 = 5889;
    private static boolean flag;
    private static int intValue;
    private static final int INT_58881 = 5888;
    private static final int INT_241 = 24;
    private static final int INT_481 = 48;
    private static final float FLOAT_16_01 = 16.0F;
    private static boolean flag4;
    private static ShaderProgram shaderProgram;
    private static ShaderProgram shaderProgram2;

    private static void method_511() {
        if (intValue == 0) {
            intValue = GLAllocation.generateDisplayLists(1);
            GL11.glNewList(intValue, 4864);
            GL11.glBegin(4);
            method_512();
            GL11.glEnd();
            GL11.glEndList();
        }

        GL11.glCallList(intValue);
    }

    private static void method_512() {
        int var0 = 0;

        for (int var15 = 0; var15 < 24; var15 = ++var0) {
            float var1 = (float)(Math.PI * var0 / 24.0 - (Math.PI / 2));
            float var2 = (float)(Math.PI * (var0 + 1) / 24.0 - (Math.PI / 2));
            float var3 = (float)Math.sin(var1);
            var1 = (float)Math.cos(var1);
            float var4 = (float)Math.sin(var2);
            var2 = (float)Math.cos(var2);
            int var5 = 0;

            for (int var14 = 0; var14 < 48; var14 = var5) {
                float var6 = (float)((Math.PI * 2) * var5 / 48.0);
                float var7 = (float)((Math.PI * 2) * (var5 + 1) / 48.0);
                float var8 = (float)Math.cos(var6);
                var6 = (float)Math.sin(var6);
                float var9 = (float)Math.cos(var7);
                var7 = (float)Math.sin(var7);
                float var10 = var1 * var8;
                float var11 = var1 * var6;
                float var12 = var1 * var9;
                float var13 = var1 * var7;
                var8 = var2 * var8;
                var6 = var2 * var6;
                var9 = var2 * var9;
                var7 = var2 * var7;
                method_514(var10, var3, var11);
                method_514(var8, var4, var6);
                method_514(var9, var4, var7);
                method_514(var10, var3, var11);
                method_514(var9, var4, var7);
                var5++;
                method_514(var12, var3, var13);
            }
        }
    }

    private static void method_513(Skybox var0) {
        int var2 = var0.method_1200();
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableFog();
        GlStateManager.depthMask(false);
        GlStateManager.disableCull();
        shaderProgram.method_817();
        shaderProgram.method_822(
            "uColorModulator",
            ColorUtils.method_201(var2) / 255.0F,
            ColorUtils.method_210(var2) / 255.0F,
            ColorUtils.method_214(var2) / 255.0F,
            var0.method_1204()
        );
        shaderProgram.method_810("uParams", var0.method_1210(), var0.method_1202(), var0.method_1199());
        shaderProgram.method_822("uKnobs", var0.method_1209(), var0.method_1205(), var0.method_1198(), var0.method_1206());
        shaderProgram.method_821("uStrikeGlow", var0.method_1207());
        shaderProgram.method_822("uFogColor", 0.0F, 0.0F, 0.0F, 0.0F);
        method_511();
        ShaderProgram.method_819();
        GlStateManager.enableCull();
        GlStateManager.depthMask(true);
        GlStateManager.enableFog();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

    private static void method_514(float var0, float var1, float var2) {
        GL11.glVertex3f(var0 * 16.0F, var1 * 16.0F, var2 * 16.0F);
    }

    private static boolean method_515(Skybox var0) {
        float var1 = var0.method_1208();
        if (!flag2 || var1 >= 0.999F) {
            return false;
        } else if (OpenGlHelper.isFramebufferEnabled() && OpenGlHelper.shadersSupported) {
            Framebuffer var4;
            if ((var4 = mc.getFramebuffer()) != null && var4.framebufferWidth > 0 && var4.framebufferHeight > 0) {
                if (!flag) {
                    flag = true;
                    shaderProgram2 = ShaderProgram.method_814("onyx_sky_blit", "onyx_post", "onyx_hand_blit");
                }

                if (shaderProgram2 != null && shaderProgram2.isFlag()) {
                    try {
                        renderTarget.method_531(Math.round(var4.framebufferWidth * var1), Math.round(var4.framebufferHeight * var1));
                    } catch (Throwable var3) {
                        flag2 = false;
                        renderTarget.method_532();
                        Onyx.LOGGER.warn("Skybox is falling back to full resolution", var3);
                        return false;
                    }

                    renderTarget.method_529();
                    method_513(var0);
                    var4.bindFramebuffer(false);
                    GlStateManager.viewport(0, 0, var4.framebufferWidth, var4.framebufferHeight);
                    method_517();
                    return true;
                } else {
                    flag2 = false;
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private SkyboxRenderer() {
    }

    public static boolean method_516() {
        if (flag3 && Onyx.moduleManager != null) {
            Skybox var0 = Onyx.moduleManager.skybox;
            if (!Onyx.moduleManager.skybox.method_1201()) {
                return false;
            } else {
                if (!flag4) {
                    flag4 = true;
                    shaderProgram = ShaderProgram.method_820("onyx_sky");
                }

                if (shaderProgram != null && shaderProgram.isFlag()) {
                    try {
                        if (!method_515(var0)) {
                            method_513(var0);
                        }

                        return true;
                    } catch (Throwable var2) {
                        flag3 = false;
                        renderTarget.method_532();
                        Onyx.LOGGER.warn("Custom skybox unavailable", var2);
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    private static void method_517() {
        GlStateManager.matrixMode(5889);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        GL11.glOrtho(0.0, 1.0, 0.0, 1.0, -1.0, 1.0);
        GlStateManager.matrixMode(5888);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 771);
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.bindTexture(renderTarget.getIntValue4());
        shaderProgram2.method_817();
        shaderProgram2.method_815("Sampler0", 0);
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
        ShaderProgram.method_819();
        GlStateManager.matrixMode(5889);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.enableFog();
        GlStateManager.resetColor();
    }
}
