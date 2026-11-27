package client.onyx.render.effect;

import client.onyx.Onyx;
import client.onyx.module.render.Hand;
import client.onyx.render.shader.ShaderProgram;
import client.onyx.render.util.RenderTarget;
import client.onyx.render.world.WorldProjection;
import client.onyx.ui.theme.color.ColorUtils;
import client.onyx.util.IMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public final class HandTrailRenderer implements IMinecraft {
    private static final long longValue2 = System.nanoTime();
    private static final RenderTarget renderTarget6 = new RenderTarget(false);
    private static final RenderTarget renderTarget2 = new RenderTarget(false);
    private static final RenderTarget renderTarget7 = new RenderTarget(false);
    private static final RenderTarget renderTarget4 = new RenderTarget(false);
    private static final RenderTarget renderTarget5 = new RenderTarget(false);
    private static final RenderTarget renderTarget3 = new RenderTarget(false);
    private static final RenderTarget renderTarget = new RenderTarget(false);
    private static float floatValue10 = 0.016666668F;
    private static float floatValue6 = 1.0F;
    private static Vec3 vec31 = new Vec3(0.0, 0.0, 0.0);
    private static ShaderProgram shaderProgram;
    private static ShaderProgram shaderProgram2;
    private static float floatValue;
    private static long longValue;
    private static float floatValue2;
    private static final int INT_361 = 36;
    private static ShaderProgram shaderProgram3;
    private static ShaderProgram shaderProgram4;
    private static boolean flag;
    private static final float FLOAT_3_01 = 3.0F;
    private static float floatValue3;
    private static final float FLOAT_0_251 = 0.25F;
    private static float floatValue4;
    private static float floatValue5;
    private static boolean flag2;
    private static float floatValue7;
    private static ShaderProgram shaderProgram5;
    private static final float FLOAT_2_01 = 2.0F;
    private static boolean flag3;
    private static final double DOUBLE_3600_01 = 3600.0;
    private static ShaderProgram shaderProgram6;
    private static float floatValue8;
    private static ShaderProgram shaderProgram7;
    private static float floatValue9;
    private static long longValue3;
    private static final int INT_641 = 64;
    private static final long LONG_2501 = 250L;

    private static void method_496(RenderTarget var0, RenderTarget var1) {
        var1.method_530();
        HandMaskRenderer.method_359(0, var0.getIntValue4());
        shaderProgram2.method_817();
        shaderProgram2.method_815("Sampler0", 0);
        shaderProgram2.method_812("uSourceSize", var0.getIntValue5(), var0.getIntValue2());
        shaderProgram2.method_812("uOutSize", var1.getIntValue5(), var1.getIntValue2());
        HandMaskRenderer.method_363();
    }

    public static void method_497() {
        longValue = 0L;
        longValue3 = 0L;
        flag = false;
        vec31 = new Vec3(0.0, 0.0, 0.0);
        floatValue3 = 0.0F;
        floatValue5 = 0.0F;
        floatValue9 = 0.0F;
        renderTarget6.method_532();
        renderTarget2.method_532();
        renderTarget7.method_532();
        renderTarget4.method_532();
        renderTarget5.method_532();
        renderTarget3.method_532();
        renderTarget.method_532();
    }

    private static boolean method_498() {
        if (!flag2) {
            flag2 = true;
            shaderProgram3 = ShaderProgram.method_814("onyx_trail_mask", "onyx_post", "onyx_trail_mask");
            shaderProgram2 = ShaderProgram.method_814("onyx_trail_near", "onyx_post", "onyx_trail_near");
            shaderProgram4 = ShaderProgram.method_814("onyx_trail_step", "onyx_post", "onyx_trail_step");
            shaderProgram7 = ShaderProgram.method_814("onyx_trail_down", "onyx_post", "onyx_trail_down");
            shaderProgram = ShaderProgram.method_814("onyx_trail_up", "onyx_post", "onyx_trail_up");
            shaderProgram6 = ShaderProgram.method_814("onyx_trail_smoke", "onyx_post", "onyx_trail_smoke");
            shaderProgram5 = ShaderProgram.method_814("onyx_hand_blit", "onyx_post", "onyx_hand_blit");
        }

        return method_501(shaderProgram3)
            && method_501(shaderProgram2)
            && method_501(shaderProgram4)
            && method_501(shaderProgram7)
            && method_501(shaderProgram)
            && method_501(shaderProgram6)
            && method_501(shaderProgram5);
    }

    private static void method_499(ShaderProgram var0, RenderTarget var1, RenderTarget var2) {
        var2.method_530();
        HandMaskRenderer.method_359(0, var1.getIntValue4());
        var0.method_817();
        var0.method_815("Sampler0", 0);
        HandMaskRenderer.method_363();
    }

    private static void method_500(Minecraft var0, float var1) {
        floatValue = 0.0F;
        floatValue2 = 0.0F;
        if (var0.getRenderViewEntity() != null && WorldProjection.isFlag()) {
            float var2 = var0.getRenderViewEntity().prevRotationYaw
                + (var0.getRenderViewEntity().rotationYaw - var0.getRenderViewEntity().prevRotationYaw) * var1;
            float var10 = var0.getRenderViewEntity().prevRotationPitch
                + (var0.getRenderViewEntity().rotationPitch - var0.getRenderViewEntity().prevRotationPitch) * var1;
            Vec3 var3 = WorldProjection.getVec31();
            if (flag) {
                float var4 = WorldProjection.method_524();
                float var5 = WorldProjection.method_526();
                floatValue = (float)Math.toRadians(MathHelper.wrapAngleTo180_float(var2 - floatValue8)) * var4 * 0.5F;
                floatValue2 = -((float)Math.toRadians(var10 - floatValue4)) * var5 * 0.5F;
                Vec3 var6 = var3.subtract(vec31);
                Vec3 var7 = method_505(var2, var10);
                Vec3 var8 = method_510(var2);
                floatValue = floatValue + (float)var6.dotProduct(var8) * var4 * 0.5F / 2.0F;
                floatValue2 = floatValue2 + (float)var6.dotProduct(var8.crossProduct(var7)) * var5 * 0.5F / 2.0F;
                floatValue = Math.clamp(floatValue, -0.25F, 0.25F);
                floatValue2 = Math.clamp(floatValue2, -0.25F, 0.25F);
            }

            floatValue8 = var2;
            floatValue4 = var10;
            vec31 = var3;
            flag = true;
        } else {
            flag = false;
        }
    }

    private static boolean method_501(ShaderProgram var0) {
        return var0 != null && var0.isFlag();
    }

    private static void method_502(ShaderProgram var0, RenderTarget var1, RenderTarget var2, float var3) {
        var2.method_530();
        HandMaskRenderer.method_359(0, var1.getIntValue4());
        var0.method_817();
        var0.method_815("Sampler0", 0);
        var0.method_812("uSourceSize", var1.getIntValue5(), var1.getIntValue2());
        var0.method_821("uBlur", var3);
        HandMaskRenderer.method_363();
    }

    public static void method_503(RenderTarget var0, Framebuffer var1, Hand.TrailSettings var2, int var3, float var4, int var5, int var6, float var7) {
        if (method_498()) {
            Minecraft var8 = IMinecraft.mc;
            long var9;
            boolean var12;
            if ((var9 = System.currentTimeMillis()) - longValue > 250L) {
                var12 = true;
            } else {
                var12 = false;
            }

            longValue = var9;
            method_508(var8, var7);
            int var15 = var0.getIntValue5();
            int var16 = var0.getIntValue2();
            boolean var13 = var12
                | renderTarget6.method_531(var5, var6)
                | renderTarget2.method_531(var15, var16)
                | renderTarget7.method_531(64, 36)
                | renderTarget4.method_531(var15, var16)
                | renderTarget5.method_531(var15, var16)
                | renderTarget3.method_531(var15, var16)
                | renderTarget.method_531(var15, var16);
            HandMaskRenderer.method_359(0, renderTarget6.getIntValue4());
            GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, var5, var6);
            HandMaskRenderer.method_360();
            method_499(shaderProgram3, var0, renderTarget2);
            method_496(renderTarget2, renderTarget7);
            method_507(var2, var3, var4, var13, var5, var6);
            method_499(shaderProgram5, renderTarget5, renderTarget4);
            float var14 = Math.max(1.0F, 1.0F + var2.trailBlur.method_151()) * var2.method_1160();
            method_502(shaderProgram7, renderTarget5, renderTarget3, var14);
            method_502(shaderProgram7, renderTarget3, renderTarget, var14 * 2.0F);
            method_502(shaderProgram, renderTarget, renderTarget3, var14 * 2.0F);
            method_502(shaderProgram, renderTarget3, renderTarget, var14);
            method_504(var2, var1, var5, var6);
            HandMaskRenderer.method_358(var1);
        }
    }

    private static void method_504(Hand.TrailSettings var0, Framebuffer var1, int var2, int var3) {
        var1.bindFramebuffer(false);
        GlStateManager.viewport(0, 0, var2, var3);
        HandMaskRenderer.method_359(0, renderTarget6.getIntValue4());
        HandMaskRenderer.method_359(1, renderTarget.getIntValue4());
        HandMaskRenderer.method_359(2, renderTarget2.getIntValue4());
        shaderProgram6.method_817();
        shaderProgram6.method_815("SceneSampler", 0);
        shaderProgram6.method_815("SmokeSampler", 1);
        shaderProgram6.method_815("MaskSampler", 2);
        float var5 = var0.smoke.method_151();
        if (method_509()) {
            var5 *= 0.4F;
        } else if (flag3) {
            var5 *= 0.55F;
        }

        shaderProgram6.method_812("uMaskSize", renderTarget2.getIntValue5(), renderTarget2.getIntValue2());
        shaderProgram6.method_822("uSmokeParams", var0.intensity.method_151(), var0.handSoftness.method_151(), var0.handBlur.method_151(), var5);
        shaderProgram6.method_822("uSmokeExtra", floatValue3, 0.0F, 0.0F, 0.0F);
        HandMaskRenderer.method_363();
    }

    private static Vec3 method_505(float var0, float var1) {
        double var2 = Math.toRadians(var0);
        double var4;
        double var6 = Math.cos(var4 = Math.toRadians(var1));
        return new Vec3(-Math.sin(var2) * var6, -Math.sin(var4), Math.cos(var2) * var6);
    }

    private HandTrailRenderer() {
    }

    private static float method_506() {
        return (float)((System.nanoTime() - longValue2) / 1.0E9 % 3600.0);
    }

    private static void method_507(Hand.TrailSettings var0, int var1, float var2, boolean var3, int var4, int var5) {
        renderTarget5.method_530();
        HandMaskRenderer.method_359(0, renderTarget4.getIntValue4());
        HandMaskRenderer.method_359(1, renderTarget6.getIntValue4());
        HandMaskRenderer.method_359(2, renderTarget2.getIntValue4());
        HandMaskRenderer.method_359(3, renderTarget7.getIntValue4());
        shaderProgram4.method_817();
        shaderProgram4.method_815("PrevSampler", 0);
        shaderProgram4.method_815("SceneSampler", 1);
        shaderProgram4.method_815("MaskSampler", 2);
        shaderProgram4.method_815("NearSampler", 3);
        shaderProgram4.method_812("uOutSize", renderTarget5.getIntValue5(), renderTarget5.getIntValue2());
        boolean var19 = method_509();
        float var20 = var0.trailFade.method_151();
        float var6 = var0.smoke.method_151();
        float var18 = var0.length.method_151();
        if (var19) {
            var20 = Math.min(var20, 0.72F);
            var6 *= 0.4F;
            var18 *= 0.4F;
        } else if (flag3) {
            var20 = Math.min(var20, 0.8F);
            var6 *= 0.55F;
            var18 *= 0.55F;
        }

        shaderProgram4.method_822("uTrailMotion", method_506(), var0.intensity.method_151(), var0.speed.method_151(), var18);
        shaderProgram4.method_822("uTrailShape", var0.trailSoftness.method_151(), var0.trailBlur.method_151(), var6, floatValue3);
        shaderProgram4.method_822("uTrailSwing", var20, floatValue5, floatValue6, floatValue7);
        ShaderProgram var12;
        String var13;
        float var14;
        float var15;
        float var16;
        float var17;
        if (!var3) {
            float var11 = floatValue2;
            float var10 = floatValue;
            String var9 = "uTrailCamera";
            ShaderProgram var8 = shaderProgram4;
            var17 = var2;
            var16 = 0.0F;
            var15 = var11;
            var14 = var10;
            var13 = var9;
            var12 = var8;
        } else {
            var17 = var2;
            var16 = 1.0F;
            var15 = floatValue2;
            var14 = floatValue;
            var13 = "uTrailCamera";
            var12 = shaderProgram4;
        }

        var12.method_822(var13, var14, var15, var16, var17);
        shaderProgram4.method_822("uTrailTiming", floatValue10, var0.method_1160(), floatValue9, 0.0F);
        shaderProgram4.method_822(
            "uGlowColor", ColorUtils.method_201(var1) / 255.0F, ColorUtils.method_210(var1) / 255.0F, ColorUtils.method_214(var1) / 255.0F, 1.0F
        );
        HandMaskRenderer.method_363();
        HandMaskRenderer.method_359(3, 0);
    }

    private static void method_508(Minecraft var0, float var1) {
        long var2 = System.currentTimeMillis();
        float var12;
        if (longValue3 > 0L) {
            var12 = (float)(var2 - longValue3) / 1000.0F;
        } else {
            var12 = 0.016666668F;
        }

        float var22 = var12;
        longValue3 = var2;
        if (var12 <= 0.0F || var12 > 0.05F) {
            var22 = floatValue10;
        }

        float var23 = Math.clamp(var22, 0.0069444445F, 0.033333335F);
        floatValue10 = floatValue10 + (var23 - floatValue10) * 0.1F;
        float var24 = floatValue10;
        EntityPlayerSP var25 = var0.thePlayer;
        boolean var13;
        if (var0.thePlayer != null && var25.isBlocking() && var25.getHeldItem() != null && var25.getHeldItem().getItem() instanceof ItemSword) {
            var13 = true;
        } else {
            var13 = false;
        }

        boolean var14;
        if (var25 != null && var25.isSwingInProgress && !var13) {
            var14 = true;
        } else {
            var14 = false;
        }

        flag3 = var14;
        float var15;
        if (var25 != null) {
            var15 = Math.clamp(var25.getSwingProgress(var1), 0.0F, 1.0F);
        } else {
            var15 = 0.0F;
        }

        float var16;
        if (method_509()) {
            var16 = 0.3F;
        } else {
            var16 = 0.5F;
        }

        float var17;
        if (var25 == null) {
            var17 = 0.0F;
        } else {
            var17 = (float)Math.min(1.0, Math.sqrt(var25.motionX * var25.motionX + var25.motionY * var25.motionY + var25.motionZ * var25.motionZ) * 2.2);
        }

        float var18;
        if (var14) {
            var18 = (1.0F - Math.min(1.0F, var15)) * var16;
        } else {
            var18 = 0.0F;
        }

        float var19;
        if (var25 != null && var25.isUsingItem() && !var13) {
            var19 = 0.55F + Math.min(0.45F, var25.getItemInUseDuration() * 0.06F);
        } else {
            var19 = 0.0F;
        }

        float var5 = Math.max(Math.max(var18, var19), var17);
        floatValue3 = floatValue3 + (var5 - floatValue3) * (1.0F - (float)Math.exp(-var24 * 6.0));
        float var20;
        if (var25 == null) {
            var20 = 0.0F;
        } else {
            var20 = 1.0F;
        }

        floatValue7 = var20;
        Minecraft var21;
        if (var14) {
            floatValue9 = var15;
            floatValue5 = (float)Math.sin(Math.PI * var15) * var16;
            floatValue6 = 1.0F;
            var21 = var0;
        } else {
            floatValue5 = Math.max(0.0F, floatValue5 - var24 * 3.0F);
            var21 = var0;
        }

        method_500(var21, var1);
    }

    private static boolean method_509() {
        return Onyx.moduleManager != null && Onyx.moduleManager.killAura.isToggled() && Onyx.moduleManager.killAura.getEntityLivingBase2() != null;
    }

    private static Vec3 method_510(float var0) {
        double var1 = Math.toRadians(var0);
        return new Vec3(-Math.cos(var1), 0.0, -Math.sin(var1));
    }
}
