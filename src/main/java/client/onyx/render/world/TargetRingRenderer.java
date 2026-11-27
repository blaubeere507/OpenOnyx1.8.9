package client.onyx.render.world;

import client.onyx.ui.theme.color.ColorUtils;
import client.onyx.util.IMinecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public final class TargetRingRenderer implements IMinecraft {
    private static final ResourceLocation resourceLocation = new ResourceLocation("onyx", "textures/effect/target_bloom.png");
    private static final float[][] floatValueArray2 = new float[][]{
        {0.0F, 1.5F, 0.0F}, {0.0F, -1.5F, 0.0F}, {1.0F, 0.0F, 0.0F}, {-1.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 1.0F}, {0.0F, 0.0F, -1.0F}
    };
    private static final int[][] intValueArray = new int[][]{{0, 4, 2}, {0, 3, 4}, {0, 5, 3}, {0, 2, 5}, {1, 2, 4}, {1, 4, 3}, {1, 3, 5}, {1, 5, 2}};
    private static final float[] floatValueArray = new float[]{1.0F, 0.8F, 0.6F, 0.9F, 0.7F, 0.5F, 0.4F, 0.6F};

    private void method_767(Vec3 var1, EntityLivingBase var2, float var3, long var4, float var6, TargetMarkParams var7, TargetMarkState var8) {
        GlStateManager.enableTexture2D();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(resourceLocation);
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        GlStateManager.blendFunc(770, 1);
        Tessellator var9;
        WorldRenderer var10 = (var9 = Tessellator.getInstance()).getWorldRenderer();
        var10.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        Vec3 var31 = method_768(var2, var6);
        float var11 = TargetMarkMath.method_779(var2.width);
        float var12 = TargetMarkMath.method_777(var3);
        float var13 = var3 * 0.2F * var7.floatValue4();
        int var14 = TargetMarkMath.method_784(var7.intValue2(), var13);
        float var15 = TargetMarkMath.method_776(1.5F * var7.floatValue3(), var3);
        float var16 = TargetMarkMath.method_776(0.6F * var7.floatValue3(), var3);
        Vec3 var17 = var31.addVector(0.0, var2.height / 2.0F, 0.0);
        int var18 = 0;

        for (int var28 = 0; var28 < 18; var28 = var18) {
            Vec3 var10000 = method_774(var31, var2.height, var18, var11, var12, var4, var3);
            float var29 = var8.method_764(var18);
            var10000 = method_769(var10000, var17, var29, var7.floatValue2());
            double var21 = var10000.xCoord - var1.xCoord;
            double var23 = var10000.yCoord - var1.yCoord;
            double var25 = var10000.zCoord - var1.zCoord;
            int var27;
            if (var29 <= 0.0F) {
                var27 = var14;
            } else {
                var27 = TargetMarkMath.method_784(TargetMarkMath.method_783(var7.intValue2(), var7.intValue3(), var29), var13 * (1.0F + var29 * 0.7F));
            }

            float var30 = 1.0F + var29 * 0.8F;
            method_771(var10, var21, var23, var25, var15 * var30, var27);
            var18++;
            method_771(var10, var21, var23, var25, var16 * var30, var27);
        }

        var9.draw();
    }

    public static Vec3 method_768(Entity var0, float var1) {
        return new Vec3(
            var0.lastTickPosX + (var0.posX - var0.lastTickPosX) * var1,
            var0.lastTickPosY + (var0.posY - var0.lastTickPosY) * var1,
            var0.lastTickPosZ + (var0.posZ - var0.lastTickPosZ) * var1
        );
    }

    private static Vec3 method_769(Vec3 var0, Vec3 var1, float var2, float var3) {
        return var2 <= 0.0F ? var0 : var0.add(var1.subtract(var0).scale(var2 * var3));
    }

    public void method_770(EntityLivingBase var1, float var2, long var3, float var5, TargetMarkParams var6, TargetMarkState var7) {
        Entity var9;
        if ((var9 = mc.getRenderViewEntity()) != null) {
            Vec3 var10 = new Vec3(
                var9.prevPosX + (var9.posX - var9.prevPosX) * var5,
                var9.prevPosY + (var9.posY - var9.prevPosY) * var5,
                var9.prevPosZ + (var9.posZ - var9.prevPosZ) * var5
            );
            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();
            GlStateManager.enableBlend();
            GlStateManager.disableDepth();
            GlStateManager.depthMask(false);
            GlStateManager.disableCull();
            GlStateManager.disableAlpha();
            this.method_773(var10, var1, var2, var3, var5, var6, var7);
            this.method_767(var10, var1, var2, var3, var5, var6, var7);
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.enableAlpha();
            GlStateManager.enableCull();
            GlStateManager.depthMask(true);
            GlStateManager.enableDepth();
            GlStateManager.disableBlend();
            GlStateManager.enableTexture2D();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
        }
    }

    public static void method_771(WorldRenderer var0, double var1, double var3, double var5, float var7, int var8) {
        float var18 = var7 / 2.0F;
        float var9 = ActiveRenderInfo.getRotationX();
        float var10 = ActiveRenderInfo.getRotationXZ();
        float var11 = ActiveRenderInfo.getRotationZ();
        float var12 = ActiveRenderInfo.getRotationYZ();
        float var13 = ActiveRenderInfo.getRotationXY();
        int var14 = ColorUtils.method_201(var8);
        int var15 = ColorUtils.method_210(var8);
        int var16 = ColorUtils.method_214(var8);
        var8 = ColorUtils.method_213(var8);
        var0.pos(var1 - var9 * var18 - var12 * var18, var3 - var10 * var18, var5 - var11 * var18 - var13 * var18)
            .tex(0.0, 0.0)
            .color(var14, var15, var16, var8)
            .endVertex();
        var0.pos(var1 - var9 * var18 + var12 * var18, var3 + var10 * var18, var5 - var11 * var18 + var13 * var18)
            .tex(0.0, 1.0)
            .color(var14, var15, var16, var8)
            .endVertex();
        var0.pos(var1 + var9 * var18 + var12 * var18, var3 + var10 * var18, var5 + var11 * var18 + var13 * var18)
            .tex(1.0, 1.0)
            .color(var14, var15, var16, var8)
            .endVertex();
        var0.pos(var1 + var9 * var18 - var12 * var18, var3 - var10 * var18, var5 + var11 * var18 - var13 * var18)
            .tex(1.0, 0.0)
            .color(var14, var15, var16, var8)
            .endVertex();
    }

    private static float[] method_772(float[] var0, Vec3 var1) {
        float var15 = (float)var1.xCoord;
        float var2 = (float)var1.yCoord;
        float var3 = (float)var1.zCoord;
        float var16 = -var15;
        float var4;
        if ((var4 = var3 * var3 + var16 * var16) < 1.0E-12F) {
            if (!(var2 >= 0.0F)) {
                byte var13 = 3;
                float[] var22 = new float[var13];
                var22[0] = var0[0];
                var22[1] = -var0[1];
                var22[2] = -var0[2];
                return var22;
            } else {
                return new float[]{var0[0], var0[1], var0[2]};
            }
        } else {
            var4 = (float)Math.sqrt(var4);
            var3 /= var4;
            float var17 = var16 / var4;
            float var5 = var0[0];
            float var6 = var0[1];
            float var7 = var0[2];
            float var8 = -var17 * var6;
            float var9 = var17 * var5 - var3 * var7;
            float var10 = var3 * var6;
            float var11 = var3 * var5 + var17 * var7;
            float var12 = 1.0F - var2;
            return new float[]{var5 * var2 + var8 * var4 + var3 * var11 * var12, var6 * var2 + var9 * var4, var7 * var2 + var10 * var4 + var17 * var11 * var12};
        }
    }

    private void method_773(Vec3 var1, EntityLivingBase var2, float var3, long var4, float var6, TargetMarkParams var7, TargetMarkState var8) {
        GlStateManager.disableTexture2D();
        GlStateManager.blendFunc(770, 1);
        Tessellator var9;
        WorldRenderer var10 = (var9 = Tessellator.getInstance()).getWorldRenderer();
        var10.begin(4, DefaultVertexFormats.POSITION_COLOR);
        Vec3 var38 = method_768(var2, var6);
        float var11 = TargetMarkMath.method_779(var2.width);
        float var12 = TargetMarkMath.method_777(var3);
        int var13 = TargetMarkMath.method_784(var7.intValue(), var3 * var7.floatValue());
        Vec3 var14 = var38.addVector(0.0, var2.height / 2.0F, 0.0);
        int var15 = 0;

        for (int var35 = 0; var35 < 18; var35 = ++var15) {
            Vec3 var16;
            Vec3 var10000 = var16 = method_774(var38, var2.height, var15, var11, var12, var4, var3);
            float var36 = var8.method_764(var15);
            var10000 = method_769(var10000, var14, var36, var7.floatValue2());
            double var19 = var10000.xCoord - var1.xCoord;
            double var21 = var10000.yCoord - var1.yCoord;
            double var23 = var10000.zCoord - var1.zCoord;
            var16 = new Vec3(var14.xCoord - var16.xCoord, var14.yCoord - var16.yCoord, var14.zCoord - var16.zCoord).normalize();
            int var32;
            if (var36 <= 0.0F) {
                var32 = var13;
            } else {
                var32 = TargetMarkMath.method_783(var13, var7.intValue3(), var36);
            }

            int var18 = var32;
            float var25 = 0.1F * (1.0F + var36 * 0.5F);
            float var37 = 1.0F + var36 * 0.6F;
            int var26 = 0;

            for (int var34 = 0; var34 < intValueArray.length; var34 = ++var26) {
                int var27 = TargetMarkMath.method_780(var18, floatValueArray[var26] * var37);
                int[] var28;
                int var29 = (var28 = intValueArray[var26]).length;
                int var30 = 0;

                for (int var33 = 0; var33 < var29; var33 = var30) {
                    int var31 = var28[var30];
                    float[] var40 = method_772(floatValueArray2[var31], var16);
                    WorldRenderer var42 = var10.pos(var19 + var40[0] * var25, var21 + var40[1] * var25, var23 + var40[2] * var25);
                    int var10001 = ColorUtils.method_201(var27);
                    int var10002 = ColorUtils.method_210(var27);
                    int var10003 = ColorUtils.method_214(var27);
                    var30++;
                    var42.color(var10001, var10002, var10003, ColorUtils.method_213(var27)).endVertex();
                }
            }
        }

        var9.draw();
    }

    private static Vec3 method_774(Vec3 var0, float var1, int var2, float var3, float var4, long var5, float var7) {
        float var13;
        float var12 = (float)Math.sin(var13 = TargetMarkMath.method_775(var2, var5, var7)) * var3 * var4;
        float var9 = (float)Math.cos(var13) * var3 * var4;
        var1 = TargetMarkMath.method_782(var2, var1);
        return new Vec3(var0.xCoord + var12, var0.yCoord + var1, var0.zCoord + var9);
    }
}
