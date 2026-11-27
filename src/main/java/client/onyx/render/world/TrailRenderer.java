package client.onyx.render.world;

import client.onyx.Onyx;
import client.onyx.util.IMinecraft;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public final class TrailRenderer implements IMinecraft {
    private static final float FLOAT_0_81 = 0.8F;
    private static final float FLOAT_0_0041 = 0.004F;
    private static final float FLOAT_2_51 = 2.5F;
    private boolean flag = true;

    private static int method_790(int var0) {
        return var0 >> 24 & 0xFF;
    }

    public void method_791() {
        this.flag = true;
    }

    private static void method_792(boolean var0) {
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.disableCull();
        GlStateManager.depthMask(false);
        GlStateManager.shadeModel(7425);
        if (var0) {
            GlStateManager.disableDepth();
        }

        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
    }

    public void method_793(float var1, List<TrailParams> var2, float var3, TrailPoint var4) {
        var var5 = this;
        if (this.flag) {
            Entity var9;
            if ((var9 = mc.getRenderViewEntity()) != null) {
                Vec3 var10 = new Vec3(
                    var9.prevPosX + (var9.posX - var9.prevPosX) * var1,
                    var9.prevPosY + (var9.posY - var9.prevPosY) * var1,
                    var9.prevPosZ + (var9.posZ - var9.prevPosZ) * var1
                );
                long var6 = System.currentTimeMillis();

                try {
                    switch (var4.trailMode()) {
                        case WALL:
                            if (var2.size() >= 2) {
                                var5.method_801(var2, var3, var4, var10, var6);
                                return;
                            }
                            break;
                        case POINTS:
                            if (var2.isEmpty()) {
                                break;
                            }

                            var5.method_802(var2, var4, var10, var6);
                        default:
                            return;
                    }
                } catch (Throwable var8) {
                    this.flag = false;
                    Onyx.LOGGER.warn("Trails renderer has been disabled after a render failure", var8);
                }
            }
        }
    }

    private static int method_794(int var0, int var1) {
        return Math.clamp((long)var1, 0, 255) << 24 | var0 & 16777215;
    }

    private void method_795(Tessellator var1, WorldRenderer var2, List<TrailParams> var3, float var4, TrailPoint var5, Vec3 var6, long var7) {
        var2.begin(3, DefaultVertexFormats.POSITION_COLOR);

        Iterator var11;
        for (Iterator var10 = var11 = var3.iterator(); var10.hasNext(); var10 = var11) {
            TrailParams var9 = (TrailParams)var11.next();
            method_797(var2, var9.vec31(), var4, var6, method_798(var5, method_799(var9, var5, var7)));
        }

        var1.draw();
    }

    private static int method_796(int var0, int var1) {
        return var0 >> var1 & 0xFF;
    }

    private static void method_797(WorldRenderer var0, Vec3 var1, float var2, Vec3 var3, int var4) {
        var0.pos(var1.xCoord - var3.xCoord, var1.yCoord + var2 - var3.yCoord, var1.zCoord - var3.zCoord)
            .color(method_796(var4, 16), method_796(var4, 8), method_796(var4, 0), method_790(var4))
            .endVertex();
    }

    private static int method_798(TrailPoint var0, float var1) {
        return method_794(var0.intValue(), (int)(255.0F * Math.min(1.0F, var0.floatValue3() * 2.5F) * var1));
    }

    private static float method_799(TrailParams var0, TrailPoint var1, long var2) {
        return 1.0F - Math.clamp((float)(var2 - var0.longValue()) / (float)var1.longValue(), 0.0F, 1.0F);
    }

    private static int method_800(TrailPoint var0, float var1) {
        return method_794(var0.intValue(), (int)(255.0F * var0.floatValue3() * var1));
    }

    private void method_801(List<TrailParams> var1, float var2, TrailPoint var3, Vec3 var4, long var5) {
        Tessellator var7;
        WorldRenderer var15 = (var7 = Tessellator.getInstance()).getWorldRenderer();
        method_792(var3.flag());
        var15.begin(7, DefaultVertexFormats.POSITION_COLOR);
        int var9 = 0;

        for (int var14 = 0; var14 < var1.size() - 1; var14 = var9) {
            TrailParams var10 = (TrailParams)var1.get(var9);
            TrailParams var11;
            TrailParams var10001 = var11 = (TrailParams)var1.get(var9 + 1);
            int var12 = method_800(var3, method_799(var10, var3, var5));
            int var13 = method_800(var3, method_799(var10001, var3, var5));
            method_797(var15, var10.vec31(), 0.0F, var4, var12);
            method_797(var15, var10.vec31(), var2, var4, var12);
            method_797(var15, var11.vec31(), var2, var4, var13);
            Vec3 var16 = var11.vec31();
            var9++;
            method_797(var15, var16, 0.0F, var4, var13);
        }

        var7.draw();
        GL11.glLineWidth(var3.floatValue());
        this.method_795(var7, var15, var1, 0.004F, var3, var4, var5);
        this.method_795(var7, var15, var1, var2 - 0.004F, var3, var4, var5);
        method_803();
    }

    private void method_802(List<TrailParams> var1, TrailPoint var2, Vec3 var3, long var4) {
        Tessellator var6;
        WorldRenderer var7 = (var6 = Tessellator.getInstance()).getWorldRenderer();
        method_792(var2.flag());
        GL11.glEnable(2832);
        GL11.glHint(3153, 4354);
        GL11.glPointSize(var2.floatValue2());
        var7.begin(0, DefaultVertexFormats.POSITION_COLOR);

        Iterator var10;
        for (Iterator var9 = var10 = var1.iterator(); var9.hasNext(); var9 = var10) {
            TrailParams var8 = (TrailParams)var10.next();
            method_797(var7, var8.vec31(), 0.8F, var3, method_798(var2, method_799(var8, var2, var4)));
        }

        var6.draw();
        GL11.glPointSize(1.0F);
        GL11.glDisable(2832);
        method_803();
    }

    private static void method_803() {
        GL11.glLineWidth(1.0F);
        GL11.glDisable(2848);
        GlStateManager.shadeModel(7424);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableCull();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.blendFunc(770, 771);
        GlStateManager.disableFog();
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }
}
