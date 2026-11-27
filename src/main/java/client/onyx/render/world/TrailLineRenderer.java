package client.onyx.render.world;

import client.onyx.ui.theme.color.ColorUtils;
import client.onyx.util.IMinecraft;
import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public final class TrailLineRenderer implements IMinecraft {
    private static Vec3 vec31 = new Vec3(0.0, 0.0, 0.0);
    private static boolean flag;
    private static boolean flag2;

    public static void method_585(Vec3 var0, Vec3 var1, int var2, int var3) {
        if (flag) {
            WorldRenderer var5 = Tessellator.getInstance().getWorldRenderer();
            method_593(var5, var0, var2);
            method_593(var5, var1, var3);
        }
    }

    public static void method_586() {
        if (flag) {
            flag = false;
            Tessellator.getInstance().draw();
        }
    }

    public static void method_587(List<Vec3> var0, int var1, int var2) {
        List<Vec3> var3 = var0;
        if (flag2 && var0.size() >= 2) {
            Tessellator var6;
            WorldRenderer var4 = (var6 = Tessellator.getInstance()).getWorldRenderer();
            var4.begin(3, DefaultVertexFormats.POSITION_COLOR);
            int var5 = var0.size() - 1;
            int var8 = 0;

            for (int var7 = 0; var7 <= var5; var7 = var8) {
                Vec3 var10001 = (Vec3)var3.get(var8);
                float var10004 = var8;
                float var10005 = var5;
                var8++;
                method_593(var4, var10001, method_590(var1, var2, var10004 / var10005));
            }

            var6.draw();
        }
    }

    private static int method_588(int var0, int var1, float var2) {
        return Math.round(var0 + (var1 - var0) * var2);
    }

    public static void method_589(Vec3 var0, float var1, int var2) {
        if (flag2) {
            Tessellator var10000 = Tessellator.getInstance();
            WorldRenderer var4 = var10000.getWorldRenderer();
            GL11.glPointSize(var1);
            GL11.glEnable(2832);
            var4.begin(0, DefaultVertexFormats.POSITION_COLOR);
            method_593(var4, var0, var2);
            var10000.draw();
            GL11.glDisable(2832);
            GL11.glPointSize(1.0F);
        }
    }

    private static int method_590(int var0, int var1, float var2) {
        return var0 == var1
            ? var0
            : ColorUtils.method_204(
                method_588(ColorUtils.method_213(var0), ColorUtils.method_213(var1), var2),
                method_588(ColorUtils.method_201(var0), ColorUtils.method_201(var1), var2),
                method_588(ColorUtils.method_210(var0), ColorUtils.method_210(var1), var2),
                method_588(ColorUtils.method_214(var0), ColorUtils.method_214(var1), var2)
            );
    }

    private TrailLineRenderer() {
    }

    public static void method_591(Vec3 var0, Vec3 var1, int var2, int var3) {
        if (flag2) {
            method_595();
            method_585(var0, var1, var2, var3);
            method_586();
        }
    }

    public static boolean method_592(float var0, boolean var1, float var2) {
        Entity var4;
        if ((var4 = mc.getRenderViewEntity()) == null) {
            return false;
        } else {
            vec31 = new Vec3(
                var4.prevPosX + (var4.posX - var4.prevPosX) * var0,
                var4.prevPosY + (var4.posY - var4.prevPosY) * var0,
                var4.prevPosZ + (var4.posZ - var4.prevPosZ) * var0
            );
            GlStateManager.pushMatrix();
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.disableCull();
            GlStateManager.depthMask(false);
            GlStateManager.shadeModel(7425);
            if (var1) {
                GlStateManager.disableDepth();
            }

            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            GL11.glLineWidth(var2);
            flag2 = true;
            return true;
        }
    }

    private static void method_593(WorldRenderer var0, Vec3 var1, int var2) {
        var0.pos(var1.xCoord - vec31.xCoord, var1.yCoord - vec31.yCoord, var1.zCoord - vec31.zCoord)
            .color(ColorUtils.method_201(var2), ColorUtils.method_210(var2), ColorUtils.method_214(var2), ColorUtils.method_213(var2))
            .endVertex();
    }

    public static void method_594() {
        if (flag2) {
            method_586();
            flag2 = false;
            GL11.glDisable(2848);
            GL11.glLineWidth(1.0F);
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

    public static void method_595() {
        if (flag2 && !flag) {
            Tessellator.getInstance().getWorldRenderer().begin(1, DefaultVertexFormats.POSITION_COLOR);
            flag = true;
        }
    }
}
