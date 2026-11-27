package client.onyx.render.world;

import client.onyx.Onyx;
import client.onyx.module.render.JumpCircles;
import client.onyx.render.font.FontFiles;
import client.onyx.render.font.GlyphAtlasFont;
import client.onyx.util.IMinecraft;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public final class JumpCircleRenderer implements IMinecraft {
    private static final ResourceLocation resourceLocation = new ResourceLocation("onyx", "textures/effect/jump_circle.png");
    private static final String STRING_CONST = "  ";
    private static final float FLOAT_0_491 = 0.49F;
    private static final float FLOAT_0_351 = 0.35F;
    private static final float FLOAT_6_28318551 = (float) (Math.PI * 2);
    private static final float FLOAT_0_0151 = 0.015F;
    private final JumpGlassRenderer jumpGlassRenderer = new JumpGlassRenderer();
    private boolean flag = true;

    private static int method_998(JumpCircle var0, float var1) {
        return Math.clamp((long)((int)(255.0F * var0.floatValue2() * Math.max(0.0F, var1))), 0, 255) << 24 | var0.intValue() & 16777215;
    }

    private static void method_999(boolean var0) {
        GlStateManager.pushMatrix();
        GlStateManager.enableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        GlStateManager.disableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 1);
        GlStateManager.disableCull();
        GlStateManager.depthMask(false);
        GlStateManager.shadeModel(7425);
        if (var0) {
            GlStateManager.disableDepth();
        }
    }

    public void method_1000(float var1, List<JumpCircleParams> var2, JumpCircle var3) {
        var var4 = this;
        if (this.flag && !var2.isEmpty()) {
            Entity var8;
            if ((var8 = mc.getRenderViewEntity()) != null) {
                Vec3 var9 = new Vec3(
                    var8.prevPosX + (var8.posX - var8.prevPosX) * var1,
                    var8.prevPosY + (var8.posY - var8.prevPosY) * var1,
                    var8.prevPosZ + (var8.posZ - var8.prevPosZ) * var1
                );
                long var5 = System.currentTimeMillis();

                try {
                    var4.jumpGlassRenderer.method_1017(var2, var3, var5);
                    method_999(var3.flag());
                    var4.method_1001(var2, var3, var9, var5);
                    if (var3.circleMode() == JumpCircles.CircleMode.TEXT) {
                        var4.method_1005(var2, var3, var9, var5);
                    }

                    method_1006();
                } catch (Throwable var7) {
                    this.flag = false;
                    Onyx.LOGGER.warn("JumpCircles renderer has been disabled after a render failure", var7);
                }
            }
        }
    }

    private void method_1001(List<JumpCircleParams> var1, JumpCircle var2, Vec3 var3, long var4) {
        mc.getTextureManager().bindTexture(resourceLocation);
        Tessellator var6;
        WorldRenderer var7 = (var6 = Tessellator.getInstance()).getWorldRenderer();
        var7.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        Iterator var13;
        Iterator var11 = var13 = var1.iterator();

        while (var11.hasNext()) {
            JumpCircleParams var8;
            float var9;
            float var12;
            if ((var12 = method_1008(var2, var9 = method_1010(var8 = (JumpCircleParams)var13.next(), var2, var4)) / 0.49F) <= 0.0F) {
                var11 = var13;
            } else {
                int var15 = method_998(var2, method_1003(var9));
                Vec3 var14 = var8.vec31().subtract(var3);
                method_1002(var7, var14, -var12, -var12, 0.0F, 0.0F, var15);
                method_1002(var7, var14, -var12, var12, 0.0F, 1.0F, var15);
                method_1002(var7, var14, var12, var12, 1.0F, 1.0F, var15);
                method_1002(var7, var14, var12, -var12, 1.0F, 0.0F, var15);
                var11 = var13;
            }
        }

        var6.draw();
    }

    private static void method_1002(WorldRenderer var0, Vec3 var1, float var2, float var3, float var4, float var5, int var6) {
        var0.pos(var1.xCoord + var2, var1.yCoord + 0.015F, var1.zCoord + var3)
            .tex(var4, var5)
            .color(method_1004(var6, 16), method_1004(var6, 8), method_1004(var6, 0), method_1004(var6, 24))
            .endVertex();
    }

    private static float method_1003(float var0) {
        return 1.0F - var0;
    }

    private static int method_1004(int var0, int var1) {
        return var0 >> var1 & 0xFF;
    }

    private void method_1005(List<JumpCircleParams> var1, JumpCircle var2, Vec3 var3, long var4) {
        String var6;
        if (!(var6 = var2.string().trim()).isEmpty() && !(var2.floatValue6() <= 0.0F)) {
            GlyphAtlasFont var7 = FontFiles.method_625(FontFiles.Size.PT36, FontFiles.Weight.BOLD);
            var6 = new StringBuilder().insert(0, var6).append("  ").toString();
            int var8 = 0;

            for (int var22 = 0; var22 < var6.length(); var22 = var8) {
                var7.method_635(var6.charAt(var8++));
            }

            if ((var8 = var7.getIntValue2()) != -1) {
                GlStateManager.bindTexture(var8);
                float var32 = var2.floatValue4() / var7.getFloatValue();
                float var9;
                if (!((var9 = var2.floatValue6() - var2.floatValue4() * 1.35F) <= 0.0F)) {
                    float var10;
                    if (!((var10 = var7.method_631(var6) * var32) <= 0.0F)) {
                        float var11 = (float) (Math.PI * 2) * var9;
                        int var12 = Math.max(1, Math.round(var11 / var10));
                        var10 = var11 / (var12 * var10);
                        Iterator var29;
                        Iterator var23 = var29 = var1.iterator();

                        while (var23.hasNext()) {
                            float var13;
                            float var26;
                            JumpCircleParams var34;
                            if ((var26 = method_1003(var13 = method_1010(var34 = (JumpCircleParams)var29.next(), var2, var4))) <= 0.0F) {
                                var23 = var29;
                            } else if ((var13 = method_1008(var2, var13) / var2.floatValue6()) <= 0.0F) {
                                var23 = var29;
                            } else {
                                float var15 = var32 * var13;
                                var13 = var9 * var13;
                                int var27;
                                GL11.glColor4f(
                                    method_1004(var27 = method_998(var2, var26), 16) / 255.0F,
                                    method_1004(var27, 8) / 255.0F,
                                    method_1004(var27, 0) / 255.0F,
                                    method_1004(var27, 24) / 255.0F
                                );
                                GL11.glBegin(7);
                                Vec3 var28 = var34.vec31().subtract(var3);
                                var11 = (float)Math.toRadians(var2.floatValue3() * method_1011(var34, var4));
                                float var16 = 0.0F;
                                int var17 = 0;

                                for (int var25 = 0; var25 < var12; var25 = ++var17) {
                                    int var18 = 0;

                                    for (int var24 = 0; var24 < var6.length(); var24 = var18) {
                                        GlyphAtlasFont.Glyph var19;
                                        if (!(var19 = var7.method_635(var6.charAt(var18))).method_647()) {
                                            float var20 = var19.floatValue2() * var15;
                                            float var21 = var16 + (var19.floatValue3() * var15 + var20 / 2.0F) * var10;
                                            method_1009(
                                                var28,
                                                var19,
                                                var11 + var21 / var13,
                                                var13,
                                                var20 / 2.0F,
                                                var19.floatValue4() * var15,
                                                var19.floatValue7() * var15
                                            );
                                        }

                                        float var10001 = var19.floatValue8() * var15;
                                        var18++;
                                        var16 += var10001 * var10;
                                    }
                                }

                                GL11.glEnd();
                                var23 = var29;
                            }
                        }

                        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                        GlStateManager.resetColor();
                    }
                }
            }
        }
    }

    private static void method_1006() {
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

    private static void method_1007(Vec3 var0, double var1, double var3, double var5, double var7, float var9, float var10) {
        GL11.glTexCoord2f(var9, var10);
        GL11.glVertex3d(var0.xCoord + var5 * var1 - var7 * var3, var0.yCoord + 0.015F, var0.zCoord + var5 * var3 + var7 * var1);
    }

    private static float method_1008(JumpCircle var0, float var1) {
        return var0.floatValue6() * (1.0F - (float)Math.pow(1.0F - var1, 4.0));
    }

    private static void method_1009(Vec3 var0, GlyphAtlasFont.Glyph var1, float var2, float var3, float var4, float var5, float var6) {
        double var7 = Math.cos(var2);
        double var9 = Math.sin(var2);
        double var11;
        double var13 = (var11 = var3 - var5) - var6;
        method_1007(var0, var7, var9, var11, -var4, var1.floatValue(), var1.floatValue6());
        method_1007(var0, var7, var9, var13, -var4, var1.floatValue(), var1.floatValue9());
        method_1007(var0, var7, var9, var13, var4, var1.floatValue5(), var1.floatValue9());
        method_1007(var0, var7, var9, var11, var4, var1.floatValue5(), var1.floatValue6());
    }

    private static float method_1010(JumpCircleParams var0, JumpCircle var1, long var2) {
        return Math.clamp((float)(var2 - var0.longValue()) / (float)var1.longValue(), 0.0F, 1.0F);
    }

    private static float method_1011(JumpCircleParams var0, long var1) {
        return (float)(var1 - var0.longValue()) / 1000.0F;
    }

    public void method_1012() {
        this.flag = true;
    }
}
