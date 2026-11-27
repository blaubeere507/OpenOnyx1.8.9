package client.onyx.render.world;

import client.onyx.Onyx;
import client.onyx.render.shader.ShaderProgram;
import client.onyx.util.IMinecraft;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.src.Config;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.lwjgl.opengl.GL11;

public final class BlockGlintRenderer implements IMinecraft {
    private static boolean flag2 = true;
    private static final long longValue = System.nanoTime();
    private static final int INT_2561 = 256;
    private static ShaderProgram shaderProgram;
    private static boolean flag;
    private static final double DOUBLE_0_0011 = 0.001;

    private static void method_559(
        float var0, BlockPos var1, IBlockState var2, Block var3, Entity var4, AxisAlignedBB var5, AxisAlignedBB var6, float var7, BlockOverlayState var8
    ) {
        double var9 = var4.prevPosX + (var4.posX - var4.prevPosX) * var0;
        double var11 = var4.prevPosY + (var4.posY - var4.prevPosY) * var0;
        double var13 = var4.prevPosZ + (var4.posZ - var4.prevPosZ) * var0;
        AxisAlignedBB var41 = BlockOverlayRenderer.method_553(var1);
        double var15 = method_569(var5.maxX - var5.minX, var41.maxX - var41.minX);
        double var17 = method_569(var5.maxY - var5.minY, var41.maxY - var41.minY);
        double var19 = method_569(var5.maxZ - var5.minZ, var41.maxZ - var41.minZ);
        double var21 = (var41.minX + var41.maxX) / 2.0 - var9;
        double var23 = (var41.minY + var41.maxY) / 2.0 - var11;
        double var25 = (var41.minZ + var41.maxZ) / 2.0 - var13;
        double var27 = (var5.minX + var5.maxX - var41.minX - var41.maxX) / 2.0;
        double var29 = (var5.minY + var5.maxY - var41.minY - var41.maxY) / 2.0;
        double var31 = (var5.minZ + var5.maxZ - var41.minZ - var41.maxZ) / 2.0;
        method_566(var8.flag());
        GlStateManager.translate(var27, var29, var31);
        GlStateManager.translate(var21, var23, var25);
        GlStateManager.scale(var15, var17, var19);
        GlStateManager.translate(-var21, -var23, -var25);
        shaderProgram.method_817();
        shaderProgram.method_815("Sampler0", 0);
        shaderProgram.method_822("uColorPrimary", method_562(var8.intValue2()), method_560(var8.intValue2()), method_570(var8.intValue2()), var7);
        shaderProgram.method_822("uColorSecondary", method_562(var8.intValue()), method_560(var8.intValue()), method_570(var8.intValue()), var8.floatValue3());
        shaderProgram.method_822("uMotion", method_567(), var8.floatValue8(), var8.floatValue6(), var8.floatValue7());
        shaderProgram.method_822("uOrigin", (float)(var41.minX - var9), (float)(var41.minY - var11), (float)(var41.minZ - var13), 0.0F);
        if (var6 == null) {
            shaderProgram.method_822("uCrop", 0.0F, 0.0F, 0.0F, 0.0F);
        } else {
            shaderProgram.method_822(
                "uCrop", (float)method_572(var6.minY - var11, var23, var17, var29), (float)method_572(var6.maxY - var11, var23, var17, var29), 1.0F, 0.0F
            );
        }

        shaderProgram.method_822("uWorld", Math.floorMod(var1.getX(), 256), Math.floorMod(var1.getY(), 256), Math.floorMod(var1.getZ(), 256), 0.0F);
        ShaderProgram var10000 = shaderProgram;
        ShaderProgram var35;
        String var36;
        float var37;
        if (!GL11.glIsEnabled(2912)) {
            String var34 = "uFogEnabled";
            ShaderProgram var33 = var10000;
            var37 = 0.0F;
            var36 = var34;
            var35 = var33;
        } else {
            var37 = 1.0F;
            var36 = "uFogEnabled";
            var35 = var10000;
        }

        var35.method_821(var36, var37);
        Tessellator var42;
        WorldRenderer var43 = (var42 = Tessellator.getInstance()).getWorldRenderer();
        var43.begin(7, DefaultVertexFormats.BLOCK);
        var43.setTranslation(-var9, -var11, -var13);
        method_563(var43, var41);
        if (var6 != null) {
            double var39;
            boolean var44;
            if (!(var44 = var8.flag3())) {
                var39 = var6.minY;
            } else {
                var39 = var6.maxY;
            }

            method_571(var43, var41, method_572(var39 - var11, var23, var17, var29) + var11, var44);
        }

        var42.draw();
        var43.setTranslation(0.0, 0.0, 0.0);
        ShaderProgram.method_819();
        method_565();
    }

    private static float method_560(int var0) {
        return (var0 >> 8 & 0xFF) / 255.0F;
    }

    private static void method_561(WorldRenderer var0, double var1, double var3, double var5) {
        var0.pos(var1, var3, var5).color(255, 255, 255, 255).tex(0.0, 0.0).lightmap(240, 240).endVertex();
    }

    private BlockGlintRenderer() {
    }

    private static float method_562(int var0) {
        return (var0 >> 16 & 0xFF) / 255.0F;
    }

    private static void method_563(WorldRenderer var0, AxisAlignedBB var1) {
        AxisAlignedBB var7 = var1;
        WorldRenderer var8 = var0;
        EnumFacing[] var4;
        int var3 = (var4 = EnumFacing.values()).length;
        int var2 = 0;

        for (int var6 = 0; var6 < var3; var6 = ++var2) {
            EnumFacing var5 = var4[var2];
            switch (var5) {
                case DOWN:
                    method_561(var8, var7.minX, var7.minY, var7.minZ);
                    method_561(var8, var7.maxX, var7.minY, var7.minZ);
                    method_561(var8, var7.maxX, var7.minY, var7.maxZ);
                    method_561(var8, var7.minX, var7.minY, var7.maxZ);
                    break;
                case UP:
                    method_561(var8, var7.minX, var7.maxY, var7.maxZ);
                    method_561(var8, var7.maxX, var7.maxY, var7.maxZ);
                    method_561(var8, var7.maxX, var7.maxY, var7.minZ);
                    method_561(var8, var7.minX, var7.maxY, var7.minZ);
                    break;
                case NORTH:
                    method_561(var8, var7.minX, var7.minY, var7.minZ);
                    method_561(var8, var7.minX, var7.maxY, var7.minZ);
                    method_561(var8, var7.maxX, var7.maxY, var7.minZ);
                    method_561(var8, var7.maxX, var7.minY, var7.minZ);
                    break;
                case SOUTH:
                    method_561(var8, var7.minX, var7.minY, var7.maxZ);
                    method_561(var8, var7.maxX, var7.minY, var7.maxZ);
                    method_561(var8, var7.maxX, var7.maxY, var7.maxZ);
                    method_561(var8, var7.minX, var7.maxY, var7.maxZ);
                    break;
                case WEST:
                    method_561(var8, var7.minX, var7.minY, var7.minZ);
                    method_561(var8, var7.minX, var7.minY, var7.maxZ);
                    method_561(var8, var7.minX, var7.maxY, var7.maxZ);
                    method_561(var8, var7.minX, var7.maxY, var7.minZ);
                    break;
                case EAST:
                    method_561(var8, var7.maxX, var7.minY, var7.minZ);
                    method_561(var8, var7.maxX, var7.maxY, var7.minZ);
                    method_561(var8, var7.maxX, var7.maxY, var7.maxZ);
                    method_561(var8, var7.maxX, var7.minY, var7.maxZ);
            }
        }
    }

    public static boolean method_564(float var0, BlockPos var1, AxisAlignedBB var2, AxisAlignedBB var3, float var4, BlockOverlayState var5) {
        BlockPos var10 = var1;
        float var11 = var0;
        if (!flag2 || mc.theWorld == null) {
            return false;
        } else if (!OpenGlHelper.shadersSupported) {
            return false;
        } else if (Config.isShaders()) {
            return false;
        } else {
            Entity var6;
            if ((var6 = mc.getRenderViewEntity()) == null) {
                return false;
            } else {
                IBlockState var7;
                Block var8;
                if ((var8 = (var7 = mc.theWorld.getBlockState(var1)).getBlock()).getRenderType() != 3) {
                    return false;
                } else {
                    if (!flag) {
                        flag = true;
                        shaderProgram = ShaderProgram.method_820("onyx_block_glint");
                    }

                    if (shaderProgram != null && shaderProgram.isFlag()) {
                        try {
                            method_559(var11, var10, var7, var8, var6, var2, var3, var4, var5);
                            return true;
                        } catch (Throwable var9) {
                            flag2 = false;
                            Onyx.LOGGER.warn("Block overlay glint has been disabled after a render failure", var9);
                            return false;
                        }
                    } else {
                        flag2 = false;
                        return false;
                    }
                }
            }
        }
    }

    private static void method_565() {
        GlStateManager.doPolygonOffset(0.0F, 0.0F);
        GlStateManager.disablePolygonOffset();
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

    private static void method_566(boolean var0) {
        GlStateManager.pushMatrix();
        mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        GlStateManager.enableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.disableAlpha();
        GlStateManager.enableCull();
        GlStateManager.depthMask(false);
        GlStateManager.doPolygonOffset(-1.0F, -10.0F);
        GlStateManager.enablePolygonOffset();
        if (var0) {
            GlStateManager.disableDepth();
        }
    }

    private static float method_567() {
        return (float)((System.nanoTime() - longValue) % 3600000000000L) / 1.0E9F;
    }

    public static void method_568() {
        flag2 = true;
    }

    private static double method_569(double var0, double var2) {
        return var0 / Math.max(var2, 1.0E-4);
    }

    private static float method_570(int var0) {
        return (var0 & 0xFF) / 255.0F;
    }

    private static void method_571(WorldRenderer var0, AxisAlignedBB var1, double var2, boolean var4) {
        double var7;
        if (var4) {
            var7 = var2 - 0.001;
        } else {
            var7 = var2 + 0.001;
        }

        method_561(var0, var1.minX, var7, var1.maxZ);
        method_561(var0, var1.maxX, var7, var1.maxZ);
        method_561(var0, var1.maxX, var7, var1.minZ);
        method_561(var0, var1.minX, var7, var1.minZ);
        method_561(var0, var1.minX, var7, var1.minZ);
        method_561(var0, var1.maxX, var7, var1.minZ);
        method_561(var0, var1.maxX, var7, var1.maxZ);
        method_561(var0, var1.minX, var7, var1.maxZ);
    }

    private static double method_572(double var0, double var2, double var4, double var6) {
        return (var0 - var2 - var6) / Math.max(var4, 1.0E-4) + var2;
    }
}
