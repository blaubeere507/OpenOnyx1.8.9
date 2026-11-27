package client.onyx.render.world;

import client.onyx.module.render.BlockOverlay;
import client.onyx.ui.theme.color.ColorUtils;
import client.onyx.util.IMinecraft;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public final class BlockOverlayRenderer implements IMinecraft {
    private static final double DOUBLE_0_00151 = 0.0015;

    public static AxisAlignedBB method_553(BlockPos var0) {
        return AxisAlignedBB.fromBounds(var0.getX(), var0.getY(), var0.getZ(), var0.getX() + 1, var0.getY() + 1, var0.getZ() + 1);
    }

    private static AxisAlignedBB method_554(AxisAlignedBB var0, float var1, BlockOverlayState var2) {
        if (var2.breakMode() == BlockOverlay.BreakMode.GROW) {
            return method_558(var0, var1);
        } else {
            double var3 = (var0.maxY - var0.minY) * var1;
            return !var2.flag3()
                ? method_555(var0.minX, var0.maxY - var3, var0.minZ, var0.maxX, var0.maxY, var0.maxZ)
                : method_555(var0.minX, var0.minY, var0.minZ, var0.maxX, var0.minY + var3, var0.maxZ);
        }
    }

    private static AxisAlignedBB method_555(double var0, double var2, double var4, double var6, double var8, double var10) {
        double var12 = Math.min(0.0015, (var6 - var0) / 2.0);
        double var14 = Math.min(0.0015, (var8 - var2) / 2.0);
        double var16 = Math.min(0.0015, (var10 - var4) / 2.0);
        return AxisAlignedBB.fromBounds(var0 + var12, var2 + var14, var4 + var16, var6 - var12, var8 - var14, var10 - var16);
    }

    public void method_556() {
        BlockGlintRenderer.method_568();
    }

    public void method_557(float var1, BlockPos var2, AxisAlignedBB var3, float var4, float var5, BlockOverlayState var6) {
        if (!(var4 <= 0.0F)) {
            boolean var10;
            if (var6.flag2() && var5 > 0.001F) {
                var10 = true;
            } else {
                var10 = false;
            }

            AxisAlignedBB var11;
            if (var10) {
                var11 = method_554(var3, var5, var6);
            } else {
                var11 = var3;
            }

            boolean var12;
            if (var10 && var6.breakMode() == BlockOverlay.BreakMode.FILL) {
                var12 = true;
            } else {
                var12 = false;
            }

            boolean var27;
            label59: {
                if (var6.theme() == BlockOverlay.Theme.GLINT && var2 != null) {
                    float var15;
                    BlockPos var16;
                    AxisAlignedBB var17;
                    boolean var18;
                    if (!var12) {
                        var18 = var12;
                        var17 = var11;
                        var16 = var2;
                        var15 = var1;
                    } else {
                        var18 = var12;
                        var17 = var3;
                        var16 = var2;
                        var15 = var1;
                    }

                    float var22;
                    BlockPos var23;
                    AxisAlignedBB var24;
                    AxisAlignedBB var25;
                    float var26;
                    if (!var18) {
                        var26 = var4;
                        var25 = null;
                        var24 = var17;
                        var23 = var16;
                        var22 = var15;
                    } else {
                        var26 = var4;
                        var25 = var11;
                        var24 = var17;
                        var23 = var16;
                        var22 = var15;
                    }

                    if (BlockGlintRenderer.method_564(var22, var23, var24, var25, var26, var6)) {
                        var27 = true;
                        break label59;
                    }
                }

                var27 = false;
            }

            if (BoxRenderer.method_519(var1, var6.flag())) {
                AxisAlignedBB var28;
                label51: {
                    if (var10) {
                        if (!var27) {
                            BoxRenderer.method_518(var11, 0, 0.0F, ColorUtils.method_209(var6.intValue2(), var4 * var6.floatValue()));
                            var28 = var3;
                            break label51;
                        }
                    } else if (var6.theme() == BlockOverlay.Theme.FILL && !var27) {
                        BoxRenderer.method_518(var3, 0, 0.0F, ColorUtils.method_209(var6.intValue2(), var4 * var6.floatValue4()));
                    }

                    var28 = var3;
                }

                BoxRenderer.method_518(var28, ColorUtils.method_209(var6.intValue2(), var4 * var6.floatValue2()), var6.floatValue5(), 0);
                BoxRenderer.method_521();
            }
        }
    }

    private static AxisAlignedBB method_558(AxisAlignedBB var0, float var1) {
        double var2 = (var0.minX + var0.maxX) / 2.0;
        double var4 = (var0.minY + var0.maxY) / 2.0;
        double var6 = (var0.minZ + var0.maxZ) / 2.0;
        return AxisAlignedBB.fromBounds(
            var2 + (var0.minX - var2) * var1,
            var4 + (var0.minY - var4) * var1,
            var6 + (var0.minZ - var6) * var1,
            var2 + (var0.maxX - var2) * var1,
            var4 + (var0.maxY - var4) * var1,
            var6 + (var0.maxZ - var6) * var1
        );
    }
}
