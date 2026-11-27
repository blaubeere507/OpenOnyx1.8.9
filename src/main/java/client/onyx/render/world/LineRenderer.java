package client.onyx.render.world;

import client.onyx.ui.theme.color.ColorUtils;
import client.onyx.util.IMinecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

public final class LineRenderer implements IMinecraft {
    private static Vec3 vec31 = new Vec3(0.0, 0.0, 0.0);
    private static boolean flag;

    private static void method_619(WorldRenderer var0, double var1, double var3, double var5, int var7) {
        var0.pos(var1, var3, var5)
            .color(ColorUtils.method_201(var7), ColorUtils.method_210(var7), ColorUtils.method_214(var7), ColorUtils.method_213(var7))
            .endVertex();
    }

    public static void method_620() {
        if (flag) {
            flag = false;
            GlStateManager.shadeModel(7424);
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

    private LineRenderer() {
    }

    private static void method_621(WorldRenderer var0, double var1, double var3, double var5, double var7, double var9, double var11, int var13, int var14) {
        method_619(var0, var1, var9, var3, var13);
        method_619(var0, var5, var9, var7, var13);
        method_619(var0, var5, var11, var7, var14);
        method_619(var0, var1, var11, var3, var14);
    }

    public static boolean method_622(float var0, boolean var1) {
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
            GlStateManager.shadeModel(7425);
            GlStateManager.depthMask(false);
            if (var1) {
                GlStateManager.disableDepth();
            }

            flag = true;
            return true;
        }
    }

    public static void method_623(double var0, double var2, double var4, double var6, double var8, int var10, int var11) {
        if (flag) {
            var8 /= 2.0;
            double var12 = var0 - var8 - vec31.xCoord;
            double var14 = var0 + var8 - vec31.xCoord;
            double var16 = var2 - var8 - vec31.zCoord;
            var2 = var2 + var8 - vec31.zCoord;
            var4 -= vec31.yCoord;
            var6 -= vec31.yCoord;
            Tessellator var10000 = Tessellator.getInstance();
            WorldRenderer var23 = var10000.getWorldRenderer();
            var23.begin(7, DefaultVertexFormats.POSITION_COLOR);
            method_621(var23, var12, var16, var14, var16, var4, var6, var10, var11);
            method_621(var23, var14, var16, var14, var2, var4, var6, var10, var11);
            method_621(var23, var14, var2, var12, var2, var4, var6, var10, var11);
            method_621(var23, var12, var2, var12, var16, var4, var6, var10, var11);
            var10000.draw();
        }
    }
}
