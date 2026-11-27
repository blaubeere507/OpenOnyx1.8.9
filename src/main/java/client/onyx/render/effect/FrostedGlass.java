package client.onyx.render.effect;

import client.onyx.Onyx;
import client.onyx.render.shader.ShaderProgram;
import java.nio.ByteBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;

public final class FrostedGlass {
    private static int intValue9 = -1;
    private static int intValue11 = -1;
    private static int intValue = -1;
    private static int intValue13 = -1;
    private static int intValue10 = -1;
    private static boolean flag3 = true;
    private static final int INT_58881 = 5888;
    private static final int INT_321 = 32;
    private static final float FLOAT_8_01 = 8.0F;
    private static boolean flag;
    private static boolean flag2;
    private static int intValue2;
    private static int intValue3;
    private static int intValue4;
    private static final int INT_641 = 64;
    private static ShaderProgram shaderProgram;
    private static int intValue5;
    private static int intValue6;
    private static int intValue7;
    private static final int INT_58891 = 5889;
    private static int intValue8;
    private static boolean flag4;
    private static int intValue12;
    private static boolean flag5;
    private static final int INT_21 = 2;

    public static void method_387() {
        if (flag2 && method_401()) {
            flag2 = false;

            try {
                Minecraft var0;
                Framebuffer var1 = (var0 = Minecraft.getMinecraft()).getFramebuffer();
                boolean var9;
                if (OpenGlHelper.isFramebufferEnabled() && var1 != null) {
                    var9 = true;
                } else {
                    var9 = false;
                }

                int var10;
                if (var9) {
                    var10 = var1.framebufferWidth;
                } else {
                    var10 = var0.displayWidth;
                }

                int var11;
                if (var9) {
                    var11 = var1.framebufferHeight;
                } else {
                    var11 = var0.displayHeight;
                }

                if (var10 <= 0 || var11 <= 0) {
                    return;
                }

                if (shaderProgram == null) {
                    shaderProgram = ShaderProgram.method_820("box_blur");
                }

                if (shaderProgram.isFlag()) {
                    int var5 = Math.max(1, var10 / 2);
                    int var6 = Math.max(1, var11 / 2);
                    method_407(var5, var6);
                    int var12;
                    if (var9) {
                        var12 = var1.framebufferTexture;
                    } else {
                        var12 = method_389(var10, var11);
                    }

                    method_402(var5, var6);
                    method_406(var5, var6, var10, var11);
                    GlStateManager.bindTexture(var12);
                    GL11.glTexParameteri(3553, 10241, 9729);
                    GL11.glTexParameteri(3553, 10240, 9729);
                    method_400(var12, intValue11, var5, var6, 0.0F, 0.0F);
                    method_400(intValue9, intValue13, var5, var6, 1.0F, 0.0F);
                    method_400(intValue, intValue11, var5, var6, 0.0F, 1.0F);
                    method_400(intValue9, intValue13, var5, var6, 1.0F, 0.0F);
                    method_400(intValue, intValue11, var5, var6, 0.0F, 1.0F);
                    if (var9) {
                        GlStateManager.bindTexture(var12);
                        GL11.glTexParameteri(3553, 10241, 9728);
                        GL11.glTexParameteri(3553, 10240, 9728);
                    }

                    method_393();
                    method_392(var0, var1, var9, var10, var11);
                    return;
                }

                method_399(null);
            } catch (Throwable var15) {
                method_399(var15);
                method_405();
                return;
            } finally {
                method_395();
            }
        }
    }

    private static void method_388() {
        if (intValue11 != -1) {
            OpenGlHelper.glDeleteFramebuffers(intValue11);
            intValue11 = -1;
        }

        if (intValue13 != -1) {
            OpenGlHelper.glDeleteFramebuffers(intValue13);
            intValue13 = -1;
        }

        if (intValue9 != -1) {
            TextureUtil.deleteTexture(intValue9);
            intValue9 = -1;
        }

        if (intValue != -1) {
            TextureUtil.deleteTexture(intValue);
            intValue = -1;
        }

        if (intValue10 != -1) {
            TextureUtil.deleteTexture(intValue10);
            intValue10 = -1;
        }

        intValue7 = 0;
        intValue5 = 0;
        intValue3 = 0;
        intValue8 = 0;
    }

    private static int method_389(int var0, int var1) {
        OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
        if (intValue10 != -1 && intValue3 == var0 && intValue8 == var1) {
            GlStateManager.bindTexture(intValue10);
        } else {
            if (intValue10 == -1) {
                intValue10 = TextureUtil.glGenTextures();
            }

            GlStateManager.bindTexture(intValue10);
            method_394(var0, var1);
            intValue3 = var0;
            intValue8 = var1;
        }

        GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, var0, var1);
        return intValue10;
    }

    private static int method_390(int var0) {
        int var1 = OpenGlHelper.glGenFramebuffers(); // hand-fixed: obfuscated slot reuse overwrote texture id var0
        if (var1 == -1) {
            return -1;
        } else {
            OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, var1);
            OpenGlHelper.glFramebufferTexture2D(OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.GL_COLOR_ATTACHMENT0, 3553, var0, 0);
            int var2 = OpenGlHelper.glCheckFramebufferStatus(OpenGlHelper.GL_FRAMEBUFFER);
            OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
            return var2 == OpenGlHelper.GL_FRAMEBUFFER_COMPLETE ? var1 : -1;
        }
    }

    private FrostedGlass() {
    }

    private static int method_391(int var0, int var1) {
        int var3;
        GlStateManager.bindTexture(var3 = TextureUtil.glGenTextures());
        method_394(var0, var1);
        return var3;
    }

    private static void method_392(Minecraft var0, Framebuffer var1, boolean var2, int var3, int var4) {
        OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
        if (var2) {
            var1.bindFramebuffer(true);
        } else {
            GlStateManager.viewport(0, 0, var3, var4);
        }
    }

    private static void method_393() {
        ShaderProgram.method_819();
        GlStateManager.matrixMode(5889);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.resetColor();
    }

    private static void method_394(int var0, int var1) {
        GL11.glTexImage2D(3553, 0, 32856, var0, var1, 0, 6408, 5121, (ByteBuffer)null);
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        GL11.glTexParameteri(3553, 10242, 33071);
        GL11.glTexParameteri(3553, 10243, 33071);
    }

    private static void method_395() {
        if (flag5) {
            flag5 = false;
            GL11.glDisable(3089);
        }
    }

    public static void method_396() {
        if (method_401()) {
            flag2 = true;
        }
    }

    public static void method_397(int var0, int var1, int var2, int var3) {
        if (var2 > 0 && var3 > 0) {
            if (flag4) {
                intValue4 = Math.min(intValue4, var0);
                intValue12 = Math.min(intValue12, var1);
                intValue2 = Math.max(intValue2, var0 + var2);
                intValue6 = Math.max(intValue6, var1 + var3);
            } else {
                intValue4 = var0;
                intValue12 = var1;
                intValue2 = var0 + var2;
                intValue6 = var1 + var3;
                flag4 = true;
            }
        }
    }

    private static void method_398(int var0) {
        OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, var0);
        GlStateManager.clearColor(0.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.clear(16384);
        OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
    }

    private static void method_399(Throwable var0) {
        flag3 = false;

        try {
            method_388();
        } catch (Throwable var1) {
        }

        Onyx.LOGGER.warn("Frosted glass unavailable, falling back to an opaque panel", var0);
    }

    private static void method_400(int var0, int var1, int var2, int var3, float var4, float var5) {
        ShaderProgram var8;
        String var9;
        float var10;
        label15: {
            OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, var1);
            GlStateManager.bindTexture(var0);
            shaderProgram.method_817();
            shaderProgram.method_815("Sampler0", 0);
            shaderProgram.method_812("uResolution", var2, var3);
            shaderProgram.method_812("uBlurDir", var4, var5);
            ShaderProgram var6;
            String var7;
            if (var4 != 0.0F) {
                var7 = "uRadius";
                var6 = shaderProgram;
            } else {
                if (var5 == 0.0F) {
                    var10 = 0.0F;
                    var9 = "uRadius";
                    var8 = shaderProgram;
                    break label15;
                }

                var7 = "uRadius";
                var6 = shaderProgram;
            }

            var10 = 8.0F;
            var9 = var7;
            var8 = var6;
        }

        var8.method_821(var9, var10);
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

    public static boolean method_401() {
        return flag3 && OpenGlHelper.framebufferSupported;
    }

    private static void method_402(int var0, int var1) {
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
        GlStateManager.enableTexture2D();
        GlStateManager.viewport(0, 0, var0, var1);
    }

    public static void method_403() {
        if (method_401()) {
            flag2 = true;
            flag = true;
        }
    }

    public static int method_404() {
        return method_401() ? intValue9 : -1;
    }

    private static void method_405() {
        try {
            Minecraft var0;
            Minecraft var3;
            Framebuffer var4;
            boolean var5;
            Minecraft var6;
            label19: {
                Minecraft var10000 = var0 = Minecraft.getMinecraft();
                Framebuffer var10001 = var10000.getFramebuffer();
                Minecraft var1;
                Framebuffer var2;
                if (!OpenGlHelper.isFramebufferEnabled()) {
                    var2 = var10001;
                    var1 = var10000;
                } else {
                    if (var0.getFramebuffer() != null) {
                        var6 = var0;
                        var5 = true;
                        var4 = var10001;
                        var3 = var10000;
                        break label19;
                    }

                    var2 = var10001;
                    var1 = var10000;
                }

                var6 = var0;
                var5 = false;
                var4 = var2;
                var3 = var1;
            }

            method_392(var3, var4, var5, var6.displayWidth, var0.displayHeight);
        } catch (Throwable var7) {
        }
    }

    private static void method_406(int var0, int var1, int var2, int var3) {
        boolean var9;
        if (flag4 && !flag) {
            var9 = true;
        } else {
            var9 = false;
        }

        int var5 = intValue4;
        int var6 = intValue12;
        int var7 = intValue2;
        int var8 = intValue6;
        flag4 = false;
        flag = false;
        if (var9) {
            float var11 = (float)var0 / var2;
            float var15 = (float)var1 / var3;
            int var4 = 32 + Math.round(64.0F * Math.max(var11, var15));
            var5 = Math.clamp((long)((int)Math.floor(var5 * var11) - var4), 0, var0);
            var6 = Math.clamp((long)((int)Math.floor(var6 * var15) - var4), 0, var1);
            var0 = Math.clamp((long)((int)Math.ceil(var7 * var11) + var4), var5, var0);
            var1 = Math.clamp((long)((int)Math.ceil(var8 * var15) + var4), var6, var1);
            if (var0 > var5 && var1 > var6) {
                GL11.glEnable(3089);
                GL11.glScissor(var5, var6, var0 - var5, var1 - var6);
                flag5 = true;
            }
        }
    }

    private static void method_407(int var0, int var1) {
        if (intValue11 == -1 || intValue7 != var0 || intValue5 != var1) {
            method_388();
            intValue9 = method_391(var0, var1);
            intValue = method_391(var0, var1);
            intValue11 = method_390(intValue9);
            intValue13 = method_390(intValue);
            if (intValue11 != -1 && intValue13 != -1) {
                intValue7 = var0;
                intValue5 = var1;
                method_398(intValue11);
                method_398(intValue13);
            } else {
                throw new IllegalStateException("could not create blur render targets");
            }
        }
    }
}
