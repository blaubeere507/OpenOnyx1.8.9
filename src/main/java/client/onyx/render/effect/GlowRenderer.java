package client.onyx.render.effect;

import client.onyx.module.render.GlowESP;
import java.nio.FloatBuffer;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderManager;
import net.minecraft.client.shader.ShaderUniform;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public final class GlowRenderer {
    private static final long longValue = System.nanoTime();
    private static final FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(4);
    private static final float FLOAT_24_01 = 24.0F;
    private static final int INT_97291 = 9729;
    private static final float FLOAT_10000_01 = 10000.0F;
    private static final float FLOAT_4_01 = 4.0F;
    private static final float FLOAT_2_01 = 2.0F;
    private static final float FLOAT_0_11 = 0.1F;
    private static final float FLOAT_6_01 = 6.0F;
    private static final float FLOAT_1_01 = 1.0F;
    private static boolean flag;
    private static final float FLOAT_1_61 = 1.6F;

    private static void method_573(ShaderManager var0, String var1, float var2, float var3, float var4) {
        ShaderUniform var6;
        if ((var6 = var0.getShaderUniform(var1)) != null) {
            var6.set(var2, var3, var4);
        }
    }

    private static void method_574(ShaderManager var0, String var1, float var2, float var3, float var4, float var5) {
        ShaderUniform var7;
        if ((var7 = var0.getShaderUniform(var1)) != null) {
            var7.set(var2, var3, var4, var5);
        }
    }

    private GlowRenderer() {
    }

    private static float method_575() {
        GlowESP var0;
        float var2;
        if ((var0 = GlowESP.method_1242()) == null) {
            var2 = 24.0F;
        } else {
            var2 = Math.clamp(var0.radius.method_151(), 2.0F, 24.0F);
        }

        return var2 * 1.6F + 6.0F;
    }

    public static boolean isFlag() {
        return flag;
    }

    public static float method_576() {
        GlowESP var0;
        return (var0 = GlowESP.method_1242()) != null && var0.method_1239() ? 4.0F : method_575();
    }

    public static void method_577() {
        flag = false;
        GL11.glTexEnvi(8960, 8704, 8448);
    }

    private static void method_578(ShaderGroup var0, float var1, float var2) {
        float var6 = Math.max(1, Minecraft.getMinecraft().displayWidth);
        float var4 = var1 * 1.6F + 6.0F;
        List var5 = var0.getListShaders();
        int var13 = 0;

        for (int var12 = 0; var12 < var5.size(); var12 = ++var13) {
            Shader var10000 = (Shader)var5.get(var13);
            ShaderManager var7 = var10000.getShaderManager();
            float var8 = var10000.framebufferIn.framebufferTextureWidth / var6;
            method_583(var7, "Radius", Math.max(1.0F, var1 * var8));
            method_583(var7, "Intensity", var2);
            Shader var10;
            float var11;
            if (var13 != 0) {
                Shader var9 = var10000;
                var11 = var4;
                var10 = var9;
            } else {
                var11 = var4 * 2.0F;
                var10 = var10000;
            }

            OutlineRenderer.method_657(var10, var11);
        }

        method_582(var0, "final");
        method_582(var0, "blurx");
        method_582(var0, "halo");
    }

    public static float method_579() {
        GlowESP var0;
        return (var0 = GlowESP.method_1242()) != null && var0.method_1239() ? 8.0F : 3.0F * method_575();
    }

    public static void method_580(int var0) {
        flag = true;
        floatBuffer.clear();
        floatBuffer.put((var0 >> 16 & 0xFF) / 255.0F).put((var0 >> 8 & 0xFF) / 255.0F).put((var0 & 0xFF) / 255.0F).put(1.0F);
        floatBuffer.flip();
        GL11.glTexEnv(8960, 8705, floatBuffer);
        GL11.glTexEnvi(8960, 8704, 34160);
        GL11.glTexEnvi(8960, 34161, 7681);
        GL11.glTexEnvi(8960, 34176, 34166);
        GL11.glTexEnvi(8960, 34192, 768);
        GL11.glTexEnvi(8960, 34162, 8448);
        GL11.glTexEnvi(8960, 34184, 5890);
        GL11.glTexEnvi(8960, 34200, 770);
        GL11.glTexEnvi(8960, 34185, 34167);
        GL11.glTexEnvi(8960, 34201, 770);
    }

    private static void method_581(ShaderGroup var0, float var1) {
        float var2 = (float)(System.nanoTime() - longValue) / 1.0E9F % 10000.0F;

        Iterator var6;
        for (Iterator var5 = var6 = var0.getListShaders().iterator(); var5.hasNext(); var5 = var6) {
            Shader var10000 = (Shader)var6.next();
            ShaderManager var4 = var10000.getShaderManager();
            method_574(var4, "BaseColor", 0.0F, 0.0F, 0.0F, var1);
            method_574(var4, "EffectData", var2, 0.0F, 0.0F, 0.0F);
            OutlineRenderer.method_657(var10000, 4.0F);
        }
    }

    private static void method_582(ShaderGroup var0, String var1) {
        Framebuffer var3;
        if ((var3 = var0.getFramebufferRaw(var1)) != null && var3.framebufferFilter != 9729) {
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
            var3.setFramebufferFilter(9729);
        }
    }

    private static void method_583(ShaderManager var0, String var1, float var2) {
        ShaderUniform var4;
        if ((var4 = var0.getShaderUniform(var1)) != null) {
            var4.set(var2);
        }
    }

    public static void method_584(ShaderGroup var0) {
        GlowESP var3 = GlowESP.method_1242();
        if (var0 != null && var3 != null && var3.isToggled()) {
            float var2 = Math.clamp(var3.intensity.method_151() / 100.0F, 0.1F, 1.0F);
            if (var3.method_1239()) {
                method_581(var0, var2);
            } else {
                method_578(var0, Math.clamp(var3.radius.method_151(), 2.0F, 24.0F), var2);
            }
        }
    }
}
