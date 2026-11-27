package client.onyx.render.effect;

import client.onyx.module.render.Hand;
import client.onyx.render.shader.ShaderProgram;
import client.onyx.render.util.RenderTarget;
import client.onyx.ui.theme.color.ColorUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;

public final class HandGlowRenderer {
    private static final long longValue = System.nanoTime();
    private static final RenderTarget renderTarget5 = new RenderTarget(false);
    private static final RenderTarget renderTarget2 = new RenderTarget(false);
    private static final RenderTarget renderTarget = new RenderTarget(false);
    private static final RenderTarget renderTarget4 = new RenderTarget(false);
    private static final RenderTarget renderTarget3 = new RenderTarget(false);
    private static ShaderProgram shaderProgram;
    private static final long LONG_36000000000001 = 3600000000000L;
    private static final float FLOAT_0_91 = 0.9F;
    private static final float FLOAT_2_01 = 2.0F;
    private static final float FLOAT_16_01 = 16.0F;
    private static final float FLOAT_0_051 = 0.05F;
    private static final float FLOAT_2_21 = 2.2F;
    private static ShaderProgram shaderProgram2;
    private static ShaderProgram shaderProgram3;
    private static final float FLOAT_4_01 = 4.0F;
    private static ShaderProgram shaderProgram4;
    private static final float FLOAT_24_01 = 24.0F;
    private static final float FLOAT_8_01 = 8.0F;
    private static boolean flag;

    public static float method_727(Hand.GlintSettings var0, float var1) {
        float var2 = var0.padding.method_151() * var1;
        float var4 = var0.smoothing.method_151() * var1;
        return method_736(var2, var4) + method_740(var2, var4, method_728(var0, var1));
    }

    private static float method_728(Hand.GlintSettings var0, float var1) {
        return Math.min(8.0F, var0.outline.method_151() * var1);
    }

    private static float method_729(float var0) {
        return var0 < 0.05F ? 0.0F : 2.0F * Math.clamp((float)Math.ceil(var0 * 0.5F), 1.0F, 16.0F);
    }

    public static void method_730() {
        renderTarget5.method_532();
        renderTarget2.method_532();
        renderTarget.method_532();
        renderTarget4.method_532();
        renderTarget3.method_532();
    }

    private static void method_731(RenderTarget var0, RenderTarget var1, float var2, float var3, float var4) {
        var1.method_530();
        HandMaskRenderer.method_359(0, var0.getIntValue4());
        shaderProgram4.method_817();
        shaderProgram4.method_815("Sampler0", 0);
        shaderProgram4.method_812("uSourceSize", var0.getIntValue5(), var0.getIntValue2());
        shaderProgram4.method_812("uDirection", var2, var3);
        shaderProgram4.method_821("uPadding", var4);
        HandMaskRenderer.method_363();
    }

    private static void method_732(RenderTarget var0, RenderTarget var1, float var2, float var3, float var4) {
        var1.method_530();
        HandMaskRenderer.method_359(0, var0.getIntValue4());
        shaderProgram.method_817();
        shaderProgram.method_815("Sampler0", 0);
        shaderProgram.method_812("uSourceSize", var0.getIntValue5(), var0.getIntValue2());
        shaderProgram.method_812("uDirection", var2, var3);
        shaderProgram.method_821("uRadius", var4);
        HandMaskRenderer.method_363();
    }

    private static void method_733(RenderTarget var0, RenderTarget var1, Hand.GlintSettings var2, float var3, int var4, int var5) {
        var1.method_530();
        HandMaskRenderer.method_359(0, var0.getIntValue4());
        int var16 = var2.color.method_118();
        int var6 = var2.color2.method_118();
        float var7 = var2.alpha.method_151() / 100.0F;
        shaderProgram3.method_817();
        shaderProgram3.method_815("Sampler0", 0);
        shaderProgram3.method_812("uMaskSize", var0.getIntValue5(), var0.getIntValue2());
        shaderProgram3.method_812("uOutSize", var4, var5);
        shaderProgram3.method_822(
            "uColorPrimary", ColorUtils.method_201(var16) / 255.0F, ColorUtils.method_210(var16) / 255.0F, ColorUtils.method_214(var16) / 255.0F, var7
        );
        shaderProgram3.method_822(
            "uColorSecondary",
            ColorUtils.method_201(var6) / 255.0F,
            ColorUtils.method_210(var6) / 255.0F,
            ColorUtils.method_214(var6) / 255.0F,
            var2.fill.method_151() / 100.0F
        );
        shaderProgram3.method_822("uMotion", method_737(), var2.speed.method_151(), 2.0F, method_728(var2, var3));
        ShaderProgram var18 = shaderProgram3;
        float var10002 = var2.glow.method_151() / 100.0F;
        ShaderProgram var11;
        String var12;
        float var13;
        float var14;
        if (!var2.outlineOnly.method_146()) {
            float var10 = var10002;
            String var9 = "uOptions";
            ShaderProgram var8 = var18;
            var14 = 0.0F;
            var13 = var10;
            var12 = var9;
            var11 = var8;
        } else {
            var14 = 1.0F;
            var13 = var10002;
            var12 = "uOptions";
            var11 = var18;
        }

        var11.method_822(var12, var13, var14, 0.0F, 0.0F);
        HandMaskRenderer.method_363();
    }

    private static boolean method_734() {
        if (!flag) {
            flag = true;
            shaderProgram4 = ShaderProgram.method_814("onyx_hand_expand", "onyx_post", "onyx_hand_expand");
            shaderProgram = ShaderProgram.method_814("onyx_hand_smooth", "onyx_post", "onyx_hand_smooth");
            shaderProgram3 = ShaderProgram.method_814("onyx_hand_aurora", "onyx_post", "onyx_hand_aurora");
            shaderProgram2 = ShaderProgram.method_814("onyx_hand_blit", "onyx_post", "onyx_hand_blit");
        }

        return shaderProgram4 != null
            && shaderProgram4.isFlag()
            && shaderProgram != null
            && shaderProgram.isFlag()
            && shaderProgram3 != null
            && shaderProgram3.isFlag()
            && shaderProgram2 != null
            && shaderProgram2.isFlag();
    }

    public static void method_735(RenderTarget var0, Framebuffer var1, Hand.GlintSettings var2, int var3, int var4) {
        if (method_734()) {
            int var11 = var0.getIntValue5();
            int var6 = var0.getIntValue2();
            float var7 = var11 / Math.max(1.0F, (float)var3);
            renderTarget5.method_531(var11, var6);
            renderTarget2.method_531(var11, var6);
            renderTarget.method_531(var11, var6);
            renderTarget4.method_531(var11, var6);
            renderTarget3.method_531(var11, var6);
            HandMaskRenderer.method_360();
            float var8 = var2.padding.method_151() * var7;
            float var9 = var2.smoothing.method_151() * var7;
            float var10;
            HandShaderRenderer.method_683((var10 = method_736(var8, var9)) + method_740(var8, var9, method_728(var2, var7)), var11, var6);
            renderTarget5.method_529();
            renderTarget2.method_529();
            renderTarget.method_529();
            renderTarget4.method_529();
            renderTarget3.method_529();
            HandShaderRenderer.method_683(var10, var11, var6);
            method_731(var0, renderTarget5, 1.0F, 0.0F, var8);
            method_731(renderTarget5, renderTarget2, 0.0F, 1.0F, var8);
            method_732(renderTarget2, renderTarget, 1.0F, 0.0F, var9);
            method_732(renderTarget, renderTarget4, 0.0F, 1.0F, var9);
            method_733(renderTarget4, renderTarget3, var2, var7, var3, var4);
            HandShaderRenderer.method_683(var10 / Math.max(var7, 1.0E-4F), var3, var4);
            method_739(renderTarget3, var1, var3, var4);
            HandShaderRenderer.method_682();
            HandMaskRenderer.method_358(var1);
        }
    }

    private static float method_736(float var0, float var1) {
        return 2.0F * (method_738(var0) + method_729(var1)) + 4.0F;
    }

    private static float method_737() {
        return (float)((System.nanoTime() - longValue) % 3600000000000L) / 1.0E9F;
    }

    private static float method_738(float var0) {
        return Math.clamp((float)Math.ceil(var0), 0.0F, 24.0F);
    }

    private HandGlowRenderer() {
    }

    private static void method_739(RenderTarget var0, Framebuffer var1, int var2, int var3) {
        var1.bindFramebuffer(false);
        GlStateManager.viewport(0, 0, var2, var3);
        HandMaskRenderer.method_359(0, var0.getIntValue4());
        shaderProgram2.method_817();
        shaderProgram2.method_815("Sampler0", 0);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 771);
        HandMaskRenderer.method_363();
        GlStateManager.disableBlend();
    }

    private static float method_740(float var0, float var1, float var2) {
        float var4 = (float)Math.ceil(var2 * 2.2F + 0.9F);
        return Math.max(Math.max(method_738(var0), method_729(var1)), var4) + 4.0F;
    }
}
