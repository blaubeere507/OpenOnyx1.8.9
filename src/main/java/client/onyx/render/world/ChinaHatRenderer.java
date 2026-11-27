package client.onyx.render.world;

import client.onyx.Onyx;
import client.onyx.ui.theme.color.ColorUtils;
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

public final class ChinaHatRenderer implements IMinecraft {
    private static final float[] floatValueArray = new float[49];
    private static final float[] floatValueArray2 = new float[49];
    private static final int INT_N11 = -1;
    private static final float FLOAT_2_01 = 2.0F;
    private static final float FLOAT_0_071 = 0.07F;
    private static final float FLOAT_0_141 = 0.14F;
    private static final float FLOAT_6_28318551 = (float) (Math.PI * 2);
    private static final int INT_481 = 48;
    private static final float FLOAT_1_011 = 1.01F;
    private static final float FLOAT_0_121 = 0.12F;
    private static final int INT_81 = 8;
    private static final float FLOAT_0_221 = 0.22F;
    private static final int INT_482 = 48;
    private static final float FLOAT_0_0121 = 0.012F;
    private static final float FLOAT_1_351 = 1.35F;
    private boolean flag = true;
    private final int[] intValueArray = new int[49];

    private static void method_688() {
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
    }

    private void method_689(ChinaHatParams var1) {
        var var2 = this;
        int var4 = 0;

        for (int var3 = 0; var3 <= 48; var3 = var4) {
            int[] var10000 = var2.intValueArray;
            int var10001 = var4;
            int var10002 = method_702(var1, (float) (Math.PI * 2) * var4 / 48.0F);
            var4++;
            var10000[var10001] = var10002;
        }
    }

    private static void method_690(boolean var0) {
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        GlStateManager.enableBlend();
        method_688();
        GlStateManager.disableCull();
        GlStateManager.depthMask(false);
        GlStateManager.shadeModel(7425);
        if (var0) {
            GlStateManager.disableDepth();
        }

        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
    }

    private void method_691(List<ChinaHatFrame> var1, ChinaHatParams var2, Vec3 var3) {
        var var13 = this;
        Tessellator var4;
        WorldRenderer var5 = (var4 = Tessellator.getInstance()).getWorldRenderer();
        var5.begin(7, DefaultVertexFormats.POSITION_COLOR);
        int var19 = (int)(255.0F * var2.floatValue3() * 0.7F);

        for (ChinaHatFrame var7 : var1) {
            ChinaHatRenderer.Ring var20 = new ChinaHatRenderer.Ring(var7, var3);
            int var8 = 0;

            for (int var17 = 0; var17 < 8; var17 = ++var8) {
                float var9 = var8 / 8.0F;
                float var10 = (var8 + 1) / 8.0F;
                float var11 = var2.floatValue2() * method_705(var9);
                float var12 = var2.floatValue2() * method_705(var10);
                var9 = var2.floatValue5() * method_700(var9);
                var10 = var2.floatValue5() * method_700(var10);
                int var18 = 0;

                for (int var16 = 0; var16 < 48; var16 = var18) {
                    int var14 = method_704(var13.intValueArray[var18], var19);
                    int var15 = method_704(var13.intValueArray[var18 + 1], var19);
                    var20.method_687(var5, var11 * floatValueArray[var18], var9, var11 * floatValueArray2[var18], var14);
                    var20.method_687(var5, var11 * floatValueArray[var18 + 1], var9, var11 * floatValueArray2[var18 + 1], var15);
                    var20.method_687(var5, var12 * floatValueArray[var18 + 1], var10, var12 * floatValueArray2[var18 + 1], var15);
                    float var10002 = var12 * floatValueArray[var18];
                    float var10005 = floatValueArray2[var18];
                    var18++;
                    var20.method_687(var5, var10002, var10, var12 * var10005, var14);
                }
            }
        }

        var4.draw();
    }

    private static int method_692(ChinaHatParams var0, float var1, float var2) {
        float var4 = method_696(var2);
        return method_704(method_698(method_702(var0, var1), -1, 0.35F * var4), (int)(255.0F * var0.floatValue3() * 0.8F * var4));
    }

    private static void method_693() {
        GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
    }

    private static int method_694(int var0) {
        return var0 >> 24 & 0xFF;
    }

    private void method_695(List<ChinaHatFrame> var1, ChinaHatParams var2, Vec3 var3) {
        var var11 = this;
        Tessellator var4;
        WorldRenderer var5 = (var4 = Tessellator.getInstance()).getWorldRenderer();
        GL11.glLineWidth(2.0F);
        var5.begin(1, DefaultVertexFormats.POSITION_COLOR);
        int var24 = (int)(255.0F * var2.floatValue3());
        float var6 = var2.floatValue2();
        float var7 = var2.floatValue5() * method_700(0.0F);
        Iterator var8 = var1.iterator();

        label36:
        while (true) {
            for (Iterator var19 = var8; var19.hasNext(); var19 = var8) {
                ChinaHatFrame var9 = (ChinaHatFrame)var8.next();
                ChinaHatRenderer.Ring var10 = new ChinaHatRenderer.Ring(var9, var3);
                int var21 = 0;

                for (int var18 = 0; var18 < 48; var18 = var21) {
                    var10.method_687(var5, var6 * floatValueArray[var21], var7, var6 * floatValueArray2[var21], method_704(var11.intValueArray[var21], var24));
                    float var10002 = var6 * floatValueArray[var21 + 1];
                    float var10004 = var6 * floatValueArray2[var21 + 1];
                    int var10005 = var11.intValueArray[var21 + 1];
                    var21++;
                    var10.method_687(var5, var10002, var7, var10004, method_704(var10005, var24));
                }

                if (var2.flag()) {
                    float var22;
                    float var25 = (float)Math.cos(var22 = var9.floatValue2() + var2.floatValue6());
                    float var12 = (float)Math.sin(var22);
                    int var23 = method_704(method_702(var2, var22), var24);
                    int var13 = 0;

                    for (int var20 = 0; var20 < 8; var20 = var13) {
                        float var14 = var13 / 8.0F;
                        float var15 = (var13 + 1) / 8.0F;
                        float var16 = var2.floatValue2() * method_705(var14) * 1.01F;
                        float var17 = var2.floatValue2() * method_705(var15) * 1.01F;
                        var14 = var2.floatValue5() * (method_700(var14) + 0.012F);
                        var15 = var2.floatValue5() * (method_700(var15) + 0.012F);
                        var10.method_687(var5, var16 * var25, var14, var16 * var12, var23);
                        float var28 = var17 * var25;
                        var13++;
                        var10.method_687(var5, var28, var15, var17 * var12, var23);
                    }
                    continue label36;
                }
            }

            var4.draw();
            GL11.glLineWidth(1.0F);
            return;
        }
    }

    private static float method_696(float var0) {
        float var2;
        float var10000 = var2 = Math.clamp(1.0F - var0, 0.0F, 1.0F);
        return var10000 * var10000 * (3.0F - 2.0F * var2);
    }

    private static void method_697(WorldRenderer var0, double var1, double var3, double var5, int var7) {
        var0.pos(var1, var3, var5).color(method_701(var7, 16), method_701(var7, 8), method_701(var7, 0), method_694(var7)).endVertex();
    }

    private static int method_698(int var0, int var1, float var2) {
        int var3 = method_701(var0, 16) + (int)((method_701(var1, 16) - method_701(var0, 16)) * var2);
        int var4 = method_701(var0, 8) + (int)((method_701(var1, 8) - method_701(var0, 8)) * var2);
        int var6 = method_701(var0, 0) + (int)((method_701(var1, 0) - method_701(var0, 0)) * var2);
        return var0 & 0xFF000000 | var3 << 16 | var4 << 8 | var6;
    }

    private void method_699(List<ChinaHatFrame> var1, ChinaHatParams var2, Vec3 var3) {
        Tessellator var4;
        WorldRenderer var5 = (var4 = Tessellator.getInstance()).getWorldRenderer();
        var5.begin(7, DefaultVertexFormats.POSITION_COLOR);

        for (ChinaHatFrame var6 : var1) {
            ChinaHatRenderer.Ring var7 = new ChinaHatRenderer.Ring(var6, var3);
            float var25 = var6.floatValue2() + var2.floatValue6();
            int var8 = 0;

            for (int var21 = 0; var21 < 48; var21 = ++var8) {
                float var9 = var25 - var2.floatValue4() * var8 / 48.0F;
                float var22 = var25 - var2.floatValue4() * (var8 + 1) / 48.0F;
                int var11 = method_692(var2, var9, var8 / 48.0F);
                int var12 = method_692(var2, var22, (var8 + 1) / 48.0F);
                if (method_694(var11) != 0 || method_694(var12) != 0) {
                    float var13 = (float)Math.cos(var9);
                    var9 = (float)Math.sin(var9);
                    float var14 = (float)Math.cos(var22);
                    float var23 = (float)Math.sin(var22);
                    int var15 = 0;

                    for (int var20 = 0; var20 < 8; var20 = var15) {
                        float var16 = var15 / 8.0F;
                        float var17 = (var15 + 1) / 8.0F;
                        float var18 = var2.floatValue2() * method_705(var16) * 1.01F;
                        float var19 = var2.floatValue2() * method_705(var17) * 1.01F;
                        var16 = var2.floatValue5() * (method_700(var16) + 0.012F);
                        var17 = var2.floatValue5() * (method_700(var17) + 0.012F);
                        var7.method_687(var5, var18 * var13, var16, var18 * var9, var11);
                        var7.method_687(var5, var19 * var13, var17, var19 * var9, var11);
                        var7.method_687(var5, var19 * var14, var17, var19 * var23, var12);
                        float var10002 = var18 * var14;
                        var15++;
                        var7.method_687(var5, var10002, var16, var18 * var23, var12);
                    }
                }
            }
        }

        var4.draw();
    }

    private static float method_700(float var0) {
        return (float)Math.pow(var0, 1.35F) + 0.12F * (float)Math.pow(1.0F - var0, 5.0);
    }

    private static int method_701(int var0, int var1) {
        return var0 >> var1 & 0xFF;
    }

    private static int method_702(ChinaHatParams var0, float var1) {
        switch (var0.colorStyle()) {
            case STATIC:
                return var0.intValue2();
            case GRADIENT:
                return method_698(var0.intValue2(), var0.intValue(), 0.5F - 0.5F * (float)Math.cos(var1 + var0.floatValue()));
            case RAINBOW:
                float[] var3;
                return ColorUtils.method_202(
                    (var3 = ColorUtils.method_215(var0.intValue2()))[0] + (float)Math.toDegrees(var1 + var0.floatValue()), var3[1], var3[2], 255
                );
            default:
                throw new MatchException(null, null);
        }
    }

    public void method_703(float var1, List<ChinaHatFrame> var2, ChinaHatParams var3) {
        var var4 = this;
        if (this.flag && !var2.isEmpty()) {
            Entity var6;
            if ((var6 = mc.getRenderViewEntity()) != null) {
                Vec3 var7 = new Vec3(
                    var6.prevPosX + (var6.posX - var6.prevPosX) * var1,
                    var6.prevPosY + (var6.posY - var6.prevPosY) * var1,
                    var6.prevPosZ + (var6.posZ - var6.prevPosZ) * var1
                );

                try {
                    var4.method_689(var3);
                    method_690(var3.flag3());
                    var4.method_691(var2, var3, var7);
                    if (var3.flag()) {
                        var4.method_699(var2, var3, var7);
                    }

                    if (var3.flag2()) {
                        var4.method_707(var2, var3, var7);
                    }

                    var4.method_695(var2, var3, var7);
                    method_708();
                } catch (Throwable var5) {
                    this.flag = false;
                    Onyx.LOGGER.warn("ChinaHat renderer has been disabled after a render failure", var5);
                }
            }
        }
    }

    private static int method_704(int var0, int var1) {
        return Math.clamp((long)var1, 0, 255) << 24 | var0 & 16777215;
    }

    private static float method_705(float var0) {
        return 1.0F - var0;
    }

    static {
        int var0 = 0;

        for (int var2 = 0; var2 <= 48; var2 = var0) {
            float var1 = (float) (Math.PI * 2) * var0 / 48.0F;
            floatValueArray[var0] = (float)Math.cos(var1);
            float[] var10000 = floatValueArray2;
            int var10001 = var0;
            float var10002 = (float)Math.sin(var1);
            var0++;
            var10000[var10001] = var10002;
        }
    }

    public void method_706() {
        this.flag = true;
    }

    private void method_707(List<ChinaHatFrame> var1, ChinaHatParams var2, Vec3 var3) {
        var var12 = this;
        Tessellator var4;
        WorldRenderer var5 = (var4 = Tessellator.getInstance()).getWorldRenderer();
        method_693();
        var5.begin(7, DefaultVertexFormats.POSITION_COLOR);
        int var19 = (int)(255.0F * var2.floatValue3() * 0.22F);
        float var20 = var2.floatValue2();
        float var6 = var2.floatValue5() * method_700(0.0F);
        float var7 = var2.floatValue2() * 1.07F;
        float var8 = var2.floatValue2() * method_705(0.14F);
        float var9 = var2.floatValue5() * method_700(0.14F);

        for (ChinaHatFrame var11 : var1) {
            ChinaHatRenderer.Ring var21 = new ChinaHatRenderer.Ring(var11, var3);
            int var18 = 0;

            for (int var17 = 0; var17 < 48; var17 = var18) {
                int var13 = method_704(var12.intValueArray[var18], var19);
                int var14 = method_704(var12.intValueArray[var18 + 1], var19);
                int var15 = method_704(var13, 0);
                int var16 = method_704(var14, 0);
                var21.method_687(var5, var20 * floatValueArray[var18], var6, var20 * floatValueArray2[var18], var13);
                var21.method_687(var5, var20 * floatValueArray[var18 + 1], var6, var20 * floatValueArray2[var18 + 1], var14);
                var21.method_687(var5, var7 * floatValueArray[var18 + 1], var6, var7 * floatValueArray2[var18 + 1], var16);
                var21.method_687(var5, var7 * floatValueArray[var18], var6, var7 * floatValueArray2[var18], var15);
                var21.method_687(var5, var20 * floatValueArray[var18], var6, var20 * floatValueArray2[var18], var13);
                var21.method_687(var5, var20 * floatValueArray[var18 + 1], var6, var20 * floatValueArray2[var18 + 1], var14);
                var21.method_687(var5, var8 * floatValueArray[var18 + 1], var9, var8 * floatValueArray2[var18 + 1], var16);
                float var22 = var8 * floatValueArray[var18];
                float var10005 = floatValueArray2[var18];
                var18++;
                var21.method_687(var5, var22, var9, var8 * var10005, var15);
            }
        }

        var4.draw();
        method_688();
    }

    private static void method_708() {
        GL11.glDisable(2848);
        GlStateManager.shadeModel(7424);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableCull();
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

    private static final class Ring {
        private final double doubleValue3;
        private final double doubleValue;
        private final double doubleValue2;
        private final float floatValue;
        private final float floatValue4;
        private final float floatValue2;
        private final float floatValue5;
        private final float floatValue3;

        private void method_687(WorldRenderer var1, float var2, float var3, float var4, int var5) {
            float var8;
            float var6 = (var8 = var3 + this.floatValue) * this.floatValue4 - var4 * this.floatValue2;
            float var9 = var8 * this.floatValue2 + var4 * this.floatValue4;
            ChinaHatRenderer.method_697(
                var1,
                this.doubleValue3 + var2 * this.floatValue5 - var9 * this.floatValue3,
                this.doubleValue + var6,
                this.doubleValue2 + var2 * this.floatValue3 + var9 * this.floatValue5,
                var5
            );
        }

        private Ring(ChinaHatFrame var1, Vec3 var2) {
            this.doubleValue3 = var1.vec31().xCoord - var2.xCoord;
            this.doubleValue = var1.vec31().yCoord - var2.yCoord;
            this.doubleValue2 = var1.vec31().zCoord - var2.zCoord;
            this.floatValue = var1.floatValue();
            float var4 = (float)Math.toRadians(var1.floatValue4());
            float var6 = (float)Math.toRadians(var1.floatValue3());
            this.floatValue4 = (float)Math.cos(var4);
            this.floatValue2 = (float)Math.sin(var4);
            this.floatValue5 = (float)Math.cos(var6);
            this.floatValue3 = (float)Math.sin(var6);
        }
    }
}
