package client.onyx.render.effect;

import client.onyx.Onyx;
import client.onyx.render.shader.ShaderProgram;
import client.onyx.util.IMinecraft;
import java.nio.ByteBuffer;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;

public final class PostProcessing implements IMinecraft {
    private static int intValue = -1;
    private static boolean flag = true;
    private static int intValue2;
    private static final int INT_58891 = 5889;
    private static int intValue3;
    private static final int INT_58881 = 5888;

    public static void method_533(ShaderProgram var0, Consumer<ShaderProgram> var1, PostProcessing.Target var2) {
        ShaderProgram var18 = var0;
        if (method_538() && var0 != null && var0.isFlag()) {
            Minecraft var6 = IMinecraft.mc;
            Framebuffer var4 = IMinecraft.mc.getFramebuffer();
            boolean var8;
            if (OpenGlHelper.isFramebufferEnabled() && var4 != null) {
                var8 = true;
            } else {
                var8 = false;
            }

            boolean var5 = var8;
            int var9;
            if (var8) {
                var9 = var4.framebufferWidth;
            } else {
                var9 = var6.displayWidth;
            }

            int var3 = var9;
            int var10;
            if (var8) {
                var10 = var4.framebufferHeight;
            } else {
                var10 = var6.displayHeight;
            }

            int var7 = var10;
            if (var9 > 0 && var10 > 0) {
                PostProcessing.Target var11;
                if (var2 == null) {
                    var11 = null;
                } else {
                    var11 = var2.method_786(var9, var10);
                }

                PostProcessing.Target var19 = var11;
                if (var2 == null || var11 != null) {
                    PostProcessing.Target var12;
                    if (var11 == null) {
                        var12 = null;
                    } else {
                        var12 = var11.method_787(var11.intValue4()).method_786(var9, var10);
                    }

                    PostProcessing.Target var17 = var12;

                    try {
                        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
                        method_540(var3, var7);
                        GlStateManager.bindTexture(intValue);
                        int var14;
                        if (var17 != null) {
                            short var13 = 3553;
                            GL11.glCopyTexSubImage2D(
                                var13, 0, var17.intValue2(), var17.intValue3(), var17.intValue2(), var17.intValue3(), var17.intValue5(), var17.intValue()
                            );
                            var14 = var3;
                        } else {
                            GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, var3, var7);
                            var14 = var3;
                        }

                        method_541(var14, var7);
                        if (var5) {
                            var4.bindFramebuffer(false);
                        }

                        GlStateManager.bindTexture(intValue);
                        var18.method_817();
                        var18.method_815("Sampler0", 0);
                        var18.method_812("uResolution", var3, var7);
                        var1.accept(var18);
                        method_539(var19, var3, var7);
                        method_542();
                    } catch (Throwable var15) {
                        method_537(var15);
                    }
                }
            }
        }
    }

    public static int method_534() {
        Framebuffer var0 = IMinecraft.mc.getFramebuffer();
        return OpenGlHelper.isFramebufferEnabled() && var0 != null ? var0.framebufferWidth : IMinecraft.mc.displayWidth;
    }

    public static void method_535(ShaderProgram var0, Consumer<ShaderProgram> var1) {
        method_533(var0, var1, null);
    }

    private PostProcessing() {
    }

    public static int method_536() {
        Framebuffer var0 = IMinecraft.mc.getFramebuffer();
        return OpenGlHelper.isFramebufferEnabled() && var0 != null ? var0.framebufferHeight : IMinecraft.mc.displayHeight;
    }

    private static void method_537(Throwable var0) {
        flag = false;

        try {
            method_543();
        } catch (Throwable var1) {
        }

        Onyx.LOGGER.warn("Post-processing unavailable, effects disabled for this session", var0);
    }

    public static boolean method_538() {
        return flag && OpenGlHelper.shadersSupported;
    }

    private static void method_539(PostProcessing.Target var0, int var1, int var2) {
        float var3 = 0.0F;
        float var4 = 0.0F;
        float var5 = 1.0F;
        float var7 = 1.0F;
        if (var0 != null) {
            var3 = (float)var0.intValue2() / var1;
            var4 = (float)var0.intValue3() / var2;
            var5 = (float)(var0.intValue2() + var0.intValue5()) / var1;
            var7 = (float)(var0.intValue3() + var0.intValue()) / var2;
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glBegin(7);
        GL11.glTexCoord2f(var3, var4);
        GL11.glVertex2f(var3, var4);
        GL11.glTexCoord2f(var5, var4);
        GL11.glVertex2f(var5, var4);
        GL11.glTexCoord2f(var5, var7);
        GL11.glVertex2f(var5, var7);
        GL11.glTexCoord2f(var3, var7);
        GL11.glVertex2f(var3, var7);
        GL11.glEnd();
    }

    private static void method_540(int var0, int var1) {
        if (intValue == -1 || intValue3 != var0 || intValue2 != var1) {
            method_543();
            intValue = TextureUtil.glGenTextures();
            GlStateManager.bindTexture(intValue);
            GL11.glTexImage2D(3553, 0, 32856, var0, var1, 0, 6408, 5121, (ByteBuffer)null);
            GL11.glTexParameteri(3553, 10241, 9729);
            GL11.glTexParameteri(3553, 10240, 9729);
            GL11.glTexParameteri(3553, 10242, 33071);
            GL11.glTexParameteri(3553, 10243, 33071);
            int var2;
            if ((var2 = GL11.glGetError()) != 0) {
                throw new IllegalStateException(new StringBuilder().insert(0, "post target allocation failed, GL error ").append(var2).toString());
            } else {
                intValue3 = var0;
                intValue2 = var1;
            }
        }
    }

    private static void method_541(int var0, int var1) {
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
        GlStateManager.enableTexture2D();
        GlStateManager.viewport(0, 0, var0, var1);
    }

    private static void method_542() {
        ShaderProgram.method_819();
        GlStateManager.matrixMode(5889);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.enableLighting();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.resetColor();
    }

    private static void method_543() {
        if (intValue != -1) {
            TextureUtil.deleteTexture(intValue);
            intValue = -1;
        }

        intValue3 = 0;
        intValue2 = 0;
    }

    public record Target(int intValue2, int intValue3, int intValue5, int intValue, int intValue4) {
        public PostProcessing.Target method_786(int var1, int var2) {
            int var4 = Math.max(0, this.intValue2);
            int var5 = Math.max(0, this.intValue3);
            var1 = Math.min(var1, this.intValue2 + this.intValue5);
            var2 = Math.min(var2, this.intValue3 + this.intValue);
            return var1 > var4 && var2 > var5 ? new PostProcessing.Target(var4, var5, var1 - var4, var2 - var5, this.intValue4) : null;
        }

        public PostProcessing.Target method_787(int var1) {
            return new PostProcessing.Target(this.intValue2 - var1, this.intValue3 - var1, this.intValue5 + var1 * 2, this.intValue + var1 * 2, this.intValue4);
        }
    }
}
