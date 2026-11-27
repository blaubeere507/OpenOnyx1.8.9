package client.onyx.render.world;

import client.onyx.ui.theme.color.ColorUtils;
import client.onyx.util.IMinecraft;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public final class TargetMarkRenderer implements IMinecraft {
    private static final ResourceLocation resourceLocation = new ResourceLocation("onyx", "textures/effect/target_bloom.png");
    private static final float[] floatValueArray = new float[]{0.3F, 0.13F, 0.05F};
    private static final float[] floatValueArray2 = new float[]{0.16F, 0.26F, 0.42F};
    private static final int INT_4001 = 400;
    private static final int INT_241 = 24;
    private static final int INT_1601 = 160;
    private static final float FLOAT_130_01 = 130.0F;
    private static final int INT_961 = 96;
    private final List<TargetMarkRenderer.Shard> list = new ArrayList<>();
    private final Random random = new Random();
    private float floatValue;
    private float floatValue2;
    private boolean flag;
    private float floatValue3;
    private float floatValue4;
    private int intValue;

    private static int method_741(int var0, int var1, float var2) {
        float var4;
        float var7 = var4 = Math.clamp(var2, 0.0F, 1.0F);
        float var5 = var7 * var7 * (3.0F - 2.0F * var4);
        return ColorUtils.method_204(
            method_755(ColorUtils.method_213(var0), ColorUtils.method_213(var1), var5),
            method_755(ColorUtils.method_201(var0), ColorUtils.method_201(var1), var5),
            method_755(ColorUtils.method_210(var0), ColorUtils.method_210(var1), var5),
            method_755(ColorUtils.method_214(var0), ColorUtils.method_214(var1), var5)
        );
    }

    private void method_742(float[] var1, Vec3 var2, float var3, float var4) {
        var var8 = this;
        if (!this.list.isEmpty()) {
            GlStateManager.enableTexture2D();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            mc.getTextureManager().bindTexture(resourceLocation);
            GL11.glTexParameteri(3553, 10241, 9729);
            GL11.glTexParameteri(3553, 10240, 9729);
            GlStateManager.blendFunc(770, 1);
            Tessellator var5;
            WorldRenderer var6 = (var5 = Tessellator.getInstance()).getWorldRenderer();
            var6.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            Iterator var7;
            Iterator var17 = var7 = this.list.iterator();

            while (var17.hasNext()) {
                TargetMarkRenderer.Shard var18 = (TargetMarkRenderer.Shard)var7.next();
                float var9;
                if ((var9 = 1.0F - (float)var18.intValue / Math.max(1, var18.intValue2)) <= 0.0F) {
                    var17 = var7;
                } else {
                    double var10 = var18.doubleValue + (var18.doubleValue2 - var18.doubleValue) * var3 - var2.xCoord;
                    double var12 = var18.doubleValue10 + (var18.doubleValue7 - var18.doubleValue10) * var3 - var2.yCoord;
                    double var14 = var18.doubleValue6 + (var18.doubleValue5 - var18.doubleValue6) * var3 - var2.zCoord;
                    float var16 = 0.72F + 0.28F * (float)Math.sin((var18.intValue + var18.intValue3) * 0.7F);
                    var16 = var4 * var9 * var16 * 0.82F;
                    var9 = var18.floatValue * (0.75F + var9 * 0.45F);
                    TargetRingRenderer.method_771(var6, var10, var12, var14, var9 * 5.4F * 2.0F, var8.method_747(var1, var18.intValue3, var16 * 0.24F));
                    TargetRingRenderer.method_771(var6, var10, var12, var14, var9 * 2.0F, var8.method_747(var1, var18.intValue3, var16));
                    var17 = var7;
                }
            }

            var5.draw();
        }
    }

    private float method_743(float var1) {
        return (float)((Math.sin(Math.toRadians(var1 + this.floatValue)) + 1.0) * 0.5);
    }

    private void method_744(float[] var1, double var2, double var4, double var6, float var8, float var9, float var10) {
        var var13 = this;
        Tessellator var11;
        WorldRenderer var12 = (var11 = Tessellator.getInstance()).getWorldRenderer();
        var12.begin(3, DefaultVertexFormats.POSITION_COLOR);
        int var18 = 0;

        for (int var17 = 0; var17 <= 160; var17 = var18) {
            float var14;
            double var15 = Math.toRadians(var14 = var18 / 160.0F * 360.0F);
            double var10001 = var2 + Math.cos(var15) * var8;
            double var10003 = var6 + Math.sin(var15) * var8;
            int var10006 = (int)(var14 + var9);
            var18++;
            method_746(var12, var10001, var4, var10003, var13.method_747(var1, var10006, var10));
        }

        var11.draw();
    }

    private void method_745(float[] var1, double var2, double var4, double var6, float var8, float var9, float var10, float var11) {
        var var12 = this;
        GlStateManager.disableTexture2D();
        GlStateManager.blendFunc(770, 1);
        GlStateManager.shadeModel(7425);
        if (var11 > 0.0F) {
            int var15 = 0;

            for (int var14 = 0; var14 < floatValueArray.length; var14 = ++var15) {
                float var13;
                if (!((var13 = floatValueArray2[var15] * var11) <= 0.0F)) {
                    var12.method_756(var1, var2, var4, var6, var8, var9, var10 * Math.min(1.0F, var13), var8 * floatValueArray[var15]);
                }
            }
        }

        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(1.4F);
        var12.method_744(var1, var2, var4, var6, var8, var9, var10);
        GL11.glLineWidth(1.0F);
        GL11.glDisable(2848);
        GlStateManager.shadeModel(7424);
    }

    private static void method_746(WorldRenderer var0, double var1, double var3, double var5, int var7) {
        var0.pos(var1, var3, var5)
            .color(ColorUtils.method_201(var7), ColorUtils.method_210(var7), ColorUtils.method_214(var7), ColorUtils.method_213(var7))
            .endVertex();
    }

    private int method_747(float[] var1, int var2, float var3) {
        int var10 = Math.clamp((long)((int)(255.0F * var3)), 0, 255);
        float var11 = var1[0];
        float var5;
        int var6;
        if (!this.flag) {
            float var4 = var11;
            var6 = 0;
            var5 = var4;
        } else {
            var6 = var2;
            var5 = var11;
        }

        int var8 = ColorUtils.method_202(var5 + var6, var1[1], var1[2], var10);
        return this.floatValue3 <= 0.0F ? var8 : method_741(var8, ColorUtils.method_204(var10, 255, 32, 32), this.floatValue3);
    }

    private void method_748(float[] var1, double var2, double var4, double var6, float var8, float var9, float var10, float var11, float var12) {
        var var23 = this;
        if (!(var11 <= 0.0F)) {
            Tessellator var13;
            WorldRenderer var14 = (var13 = Tessellator.getInstance()).getWorldRenderer();
            var12 = 130.0F * var12;
            GlStateManager.disableTexture2D();
            GlStateManager.blendFunc(770, 1);
            GlStateManager.shadeModel(7425);
            int var15 = 0;

            for (int var30 = 0; var30 < 24; var30 = var15) {
                double var16 = var4 + var8 * var23.method_743(var10 - var12 * var15 / 24.0F);
                double var18 = var4 + var8 * var23.method_743(var10 - var12 * (var15 + 1) / 24.0F);
                float var20 = method_751(var15 / 24.0F);
                float var21 = method_751((var15 + 1) / 24.0F);
                var14.begin(5, DefaultVertexFormats.POSITION_COLOR);
                int var22 = 0;

                for (int var29 = 0; var29 <= 96; var29 = var22) {
                    float var31;
                    double var24 = Math.toRadians(var31 = var22 / 96.0F * 360.0F);
                    double var26 = var2 + Math.cos(var24) * var9;
                    var24 = var6 + Math.sin(var24) * var9;
                    float var28 = method_757(var31, var10);
                    int var32 = (int)(var31 + var10);
                    method_746(var14, var26, var16, var24, var23.method_747(var1, var32, var11 * var20 * var28));
                    float var10007 = var11 * var21;
                    var22++;
                    method_746(var14, var26, var18, var24, var23.method_747(var1, var32, var10007 * var28));
                }

                var15++;
                var13.draw();
            }

            GlStateManager.shadeModel(7424);
        }
    }

    public void method_749() {
        this.list.clear();
        this.floatValue3 = 0.0F;
        this.intValue = 0;
        this.floatValue2 = 0.0F;
        this.floatValue4 = 0.0F;
    }

    public void method_750(EntityLivingBase var1, float var2, TargetMarkFrame var3) {
        TargetMarkFrame var7 = var3;
        var var8 = this;
        this.floatValue = var3.floatValue2();
        if (var1 == null) {
            this.method_749();
        } else {
            this.method_752(var1, var3);
            this.floatValue4 = this.floatValue2;
            this.floatValue2 = this.floatValue2 + var3.floatValue9() * 6.0F;
            this.method_754();
            if (var3.flag4() && this.list.size() < 400) {
                int var4 = (int)((3 + this.random.nextInt(4)) * Math.max(0.15F, var2));
                int var5 = 0;

                for (int var6 = 0; var6 < var4 && var8.list.size() < 400; var6 = var5) {
                    var5++;
                    var8.method_758(var1, var2, var7);
                }
            }
        }
    }

    private static float method_751(float var0) {
        float var2;
        float var10000 = var2 = 1.0F - var0;
        return var10000 * var10000 * var2 * 0.55F;
    }

    private void method_752(EntityLivingBase var1, TargetMarkFrame var2) {
        if (var2.flag() && var1 != null) {
            TargetMarkRenderer var3;
            int var6;
            if ((var6 = var1.hurtTime) <= this.intValue && (var6 <= 0 || this.intValue != 0)) {
                if (var6 <= 0) {
                    this.floatValue3 = Math.max(0.0F, this.floatValue3 - var2.floatValue7());
                    var3 = this;
                } else {
                    this.floatValue3 = Math.min(var2.floatValue5(), this.floatValue3 + var2.floatValue() * 0.5F);
                    var3 = this;
                }
            } else {
                this.floatValue3 = Math.min(var2.floatValue5(), this.floatValue3 + var2.floatValue());
                var3 = this;
            }

            var3.intValue = var6;
        } else {
            this.floatValue3 = 0.0F;
            this.intValue = 0;
        }
    }

    public void method_753(EntityLivingBase var1, float var2, float var3, TargetMarkFrame var4) {
        Entity var17;
        if ((var17 = mc.getRenderViewEntity()) != null) {
            Vec3 var18 = new Vec3(
                var17.prevPosX + (var17.posX - var17.prevPosX) * var3,
                var17.prevPosY + (var17.posY - var17.prevPosY) * var3,
                var17.prevPosZ + (var17.posZ - var17.prevPosZ) * var3
            );
            this.flag = var4.flag2();
            this.floatValue = var4.floatValue2();
            float var6 = 0.72F + 0.28F * var2;
            var6 = (var1.width * 0.86F + 0.18F) * var4.floatValue3() * var6;
            float var7 = this.floatValue4 + (this.floatValue2 - this.floatValue4) * var3;
            var2 *= var2;
            Vec3 var8 = TargetRingRenderer.method_768(var1, var3);
            double var9 = var8.xCoord - var18.xCoord;
            double var11;
            double var13 = (var11 = var8.yCoord - var18.yCoord) + var1.height * this.method_743(var7);
            double var15 = var8.zCoord - var18.zCoord;
            float[] var21 = ColorUtils.method_215(var4.intValue());
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.disableCull();
            GlStateManager.disableLighting();
            GlStateManager.disableAlpha();
            GlStateManager.enableDepth();
            GlStateManager.depthMask(false);
            if (var4.flag3()) {
                this.method_748(var21, var9, var11, var15, var1.height, var6, var7, var2 * var4.floatValue4(), var4.floatValue10());
            }

            this.method_745(var21, var9, var13, var15, var6, var7, var2 * 0.92F, var4.floatValue6());
            if (var4.flag4()) {
                this.method_742(var21, var18, var3, var2);
            }

            GlStateManager.depthMask(true);
            GlStateManager.enableAlpha();
            GlStateManager.enableCull();
            GlStateManager.disableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            GlStateManager.enableTexture2D();
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
        }
    }

    private void method_754() {
        Iterator var1 = this.list.iterator();

        while (var1.hasNext()) {
            TargetMarkRenderer.Shard var3;
            TargetMarkRenderer.Shard var10000 = var3 = (TargetMarkRenderer.Shard)var1.next();
            var10000.doubleValue = var10000.doubleValue2;
            var10000.doubleValue10 = var10000.doubleValue7;
            var10000.doubleValue6 = var10000.doubleValue5;
            var10000.doubleValue2 = var10000.doubleValue2 + var3.doubleValue3;
            var10000.doubleValue7 = var10000.doubleValue7 + var3.doubleValue9;
            var10000.doubleValue5 = var10000.doubleValue5 + var3.doubleValue8;
            var10000.doubleValue3 *= 0.96;
            var10000.doubleValue9 -= 0.0022;
            var10000.doubleValue8 *= 0.96;
            var10000.intValue++;
            if (var10000.intValue >= var3.intValue2 || var3.doubleValue7 < var3.doubleValue4) {
                var1.remove();
            }
        }
    }

    private static int method_755(int var0, int var1, float var2) {
        return (int)(var0 + (var1 - var0) * var2);
    }

    private void method_756(float[] var1, double var2, double var4, double var6, float var8, float var9, float var10, double var11) {
        var var17 = this;
        Tessellator var13;
        WorldRenderer var14 = (var13 = Tessellator.getInstance()).getWorldRenderer();
        byte var15 = -1;

        for (byte var32 = -1; var32 <= 1; var32 = var15) {
            var14.begin(5, DefaultVertexFormats.POSITION_COLOR);
            int var16 = 0;

            for (int var31 = 0; var31 <= 160; var31 = var16) {
                double var18;
                float var33;
                double var20 = Math.cos(var18 = Math.toRadians(var33 = var16 / 160.0F * 360.0F));
                var18 = Math.sin(var18);
                double var23 = var2 + var20 * var8;
                double var25 = var6 + var18 * var8;
                double var27 = -var20 * var4;
                var20 = var20 * var23 + var18 * var25;
                var18 = -var18 * var4;
                double var29;
                if ((var29 = Math.sqrt(var27 * var27 + var20 * var20 + var18 * var18)) < 1.0E-6) {
                    var27 = 0.0;
                    var20 = 1.0;
                    var18 = 0.0;
                    var29 = 1.0;
                }

                var27 = var27 / var29 * var11 * var15;
                var20 = var20 / var29 * var11 * var15;
                var18 = var18 / var29 * var11 * var15;
                int var22 = var17.method_747(var1, (int)(var33 + var9), var10);
                int var34 = var17.method_747(var1, (int)(var33 + var9), 0.0F);
                method_746(var14, var23, var4, var25, var22);
                double var10001 = var23 + var27;
                double var10002 = var4 + var20;
                var16++;
                method_746(var14, var10001, var10002, var25 + var18, var34);
            }

            var15 += 2;
            var13.draw();
        }
    }

    private static float method_757(float var0, float var1) {
        double var2 = Math.sin(Math.toRadians(var0 * 3.0F + var1 * 1.3F));
        double var4 = Math.sin(Math.toRadians(var0 * 7.0F - var1 * 0.6F));
        return (float)(0.4 + 0.6 * (0.5 + 0.5 * var2) * (0.65 + 0.35 * var4));
    }

    private void method_758(EntityLivingBase var1, float var2, TargetMarkFrame var3) {
        float var4 = (var1.width * 0.86F + 0.18F) * var3.floatValue3() * var2;
        float var5 = this.method_743(this.floatValue2);
        double var6 = var1.posX;
        double var8 = var1.posY + 0.03;
        double var10;
        double var12 = var8 + ((var10 = var1.posY + var1.height - 0.03) - var8) * var5;
        double var14 = var1.posZ;
        double var16 = this.random.nextDouble() * Math.PI * 2.0;
        double var18 = var4 * (0.88 + this.random.nextDouble() * 0.22);
        double var20 = var1.height * var3.floatValue11();
        double var30;
        if (var3.floatValue11() >= 0.98F) {
            double var22;
            var30 = var22 = var8 + this.random.nextDouble() * (var10 - var8);
        } else {
            double var24 = var20 * 0.5;
            double var35 = var12 + (this.random.nextDouble() - 0.5) * var20;
            double var26 = Math.max(var8, var12 - var24);
            double var28;
            if ((var28 = Math.min(var10, var12 + var24)) > var26) {
                var35 = Math.max(var26, Math.min(var28, var35));
            }

            var30 = var35;
        }

        double var36 = var30 + (this.random.nextDouble() - 0.5) * 0.025;
        var36 = Math.max(var8, Math.min(var10, var36));
        double var38 = Math.cos(var16);
        double var39 = Math.sin(var16);
        double var40 = (this.random.nextDouble() - 0.5) * 0.01;
        TargetMarkRenderer.Shard var33 = new TargetMarkRenderer.Shard(
            var6 + var38 * var18,
            var36,
            var14 + var39 * var18,
            var38 * var40,
            -8.0E-4 - this.random.nextDouble() * 0.006,
            var39 * var40,
            var8 - 0.08,
            Math.max(1, (int)((34 + this.random.nextInt(28)) * var3.floatValue8())),
            (0.0065F + this.random.nextFloat() * 0.014F) * var3.floatValue3() * var2,
            this.random.nextInt(360)
        );
        this.list.add(var33);
    }

    private static final class Shard {
        private double doubleValue;
        private double doubleValue10;
        private double doubleValue6;
        private double doubleValue2;
        private double doubleValue7;
        private double doubleValue5;
        private double doubleValue3;
        private double doubleValue9;
        private double doubleValue8;
        private final double doubleValue4;
        private final int intValue2;
        private final float floatValue;
        private final int intValue3;
        private int intValue;

        private Shard(double var1, double var3, double var5, double var7, double var9, double var11, double var13, int var15, float var16, int var17) {
            this.doubleValue = var1;
            this.doubleValue10 = var3;
            this.doubleValue6 = var5;
            this.doubleValue2 = var1;
            this.doubleValue7 = var3;
            this.doubleValue5 = var5;
            this.doubleValue3 = var7;
            this.doubleValue9 = var9;
            this.doubleValue8 = var11;
            this.doubleValue4 = var13;
            this.intValue2 = var15;
            this.floatValue = var16;
            this.intValue3 = var17;
        }
    }
}
