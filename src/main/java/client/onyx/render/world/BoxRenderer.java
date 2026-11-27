package client.onyx.render.world;

import client.onyx.ui.theme.color.ColorUtils;
import client.onyx.util.IMinecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public final class BoxRenderer implements IMinecraft {
    private static final int[][] intValueArray = new int[][]{{0, 1}, {1, 3}, {3, 2}, {2, 0}, {4, 5}, {5, 7}, {7, 6}, {6, 4}, {0, 4}, {1, 5}, {2, 6}, {3, 7}};
    private static Vec3 vec31 = new Vec3(0.0, 0.0, 0.0);
    private static boolean flag;

    public static void method_518(AxisAlignedBB var0, int var1, float var2, int var3) {
        if (flag) {
            double var4 = var0.minX - vec31.xCoord;
            double var6 = var0.minY - vec31.yCoord;
            double var8 = var0.minZ - vec31.zCoord;
            double var10 = var0.maxX - vec31.xCoord;
            double var12 = var0.maxY - vec31.yCoord;
            double var14 = var0.maxZ - vec31.zCoord;
            double[][] var21 = new double[][]{
                {var4, var6, var8},
                {var10, var6, var8},
                {var4, var6, var14},
                {var10, var6, var14},
                {var4, var12, var8},
                {var10, var12, var8},
                {var4, var12, var14},
                {var10, var12, var14}
            };
            Tessellator var5;
            WorldRenderer var22 = (var5 = Tessellator.getInstance()).getWorldRenderer();
            if (ColorUtils.method_213(var3) > 0) {
                var22.begin(7, DefaultVertexFormats.POSITION_COLOR);
                method_520(var22, var21, var3, 0, 1, 3, 2);
                method_520(var22, var21, var3, 4, 5, 7, 6);
                method_520(var22, var21, var3, 0, 1, 5, 4);
                method_520(var22, var21, var3, 2, 3, 7, 6);
                method_520(var22, var21, var3, 0, 2, 6, 4);
                method_520(var22, var21, var3, 1, 3, 7, 5);
                var5.draw();
            }

            if (ColorUtils.method_213(var1) > 0) {
                GL11.glLineWidth(var2);
                var22.begin(1, DefaultVertexFormats.POSITION_COLOR);
                int[][] var19 = intValueArray;
                int var18 = intValueArray.length;
                int var7 = 0;

                for (int var16 = 0; var16 < var18; var16 = var7) {
                    int[] var23 = var19[var7];
                    method_522(var22, var21[var23[0]], var1);
                    int var10002 = var23[1];
                    var7++;
                    method_522(var22, var21[var10002], var1);
                }

                var5.draw();
            }
        }
    }

    private BoxRenderer() {
    }

    public static boolean method_519(float var0, boolean var1) {
        Entity var3;
        if ((var3 = mc.getRenderViewEntity()) == null) {
            return false;
        } else {
            vec31 = new Vec3(
                var3.prevPosX + (var3.posX - var3.prevPosX) * var0,
                var3.prevPosY + (var3.posY - var3.prevPosY) * var0,
                var3.prevPosZ + (var3.posZ - var3.prevPosZ) * var0
            );
            GlStateManager.pushMatrix();
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.disableCull();
            GlStateManager.depthMask(false);
            if (var1) {
                GlStateManager.disableDepth();
            }

            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            flag = true;
            return true;
        }
    }

    private static void method_520(WorldRenderer var0, double[][] var1, int var2, int var3, int var4, int var5, int var6) {
        method_522(var0, var1[var3], var2);
        method_522(var0, var1[var4], var2);
        method_522(var0, var1[var5], var2);
        method_522(var0, var1[var6], var2);
    }

    public static void method_521() {
        if (flag) {
            flag = false;
            GL11.glDisable(2848);
            GL11.glLineWidth(1.0F);
            GlStateManager.depthMask(true);
            GlStateManager.enableDepth();
            GlStateManager.enableCull();
            GlStateManager.disableBlend();
            GlStateManager.enableLighting();
            GlStateManager.enableTexture2D();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
        }
    }

    private static void method_522(WorldRenderer var0, double[] var1, int var2) {
        var0.pos(var1[0], var1[1], var1[2])
            .color(ColorUtils.method_201(var2), ColorUtils.method_210(var2), ColorUtils.method_214(var2), ColorUtils.method_213(var2))
            .endVertex();
    }
}
