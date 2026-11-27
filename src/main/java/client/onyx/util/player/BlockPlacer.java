package client.onyx.util.player;

import client.onyx.util.IMinecraft;
import java.util.function.BooleanSupplier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class BlockPlacer implements IMinecraft {
    public static final AxisAlignedBB axisAlignedBB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);

    public static boolean method_15(MovingObjectPosition var0) {
        int var3;
        ItemStack var4;
        if ((var4 = mc.thePlayer.inventory.getStackInSlot(HotbarManager.INSTANCE.method_1973())) == null) {
            var3 = 0;
        } else {
            var3 = var4.stackSize;
        }

        if (!mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, var4, var0.getBlockPos(), var0.sideHit, var0.hitVec)) {
            return false;
        } else {
            if (var4 != null && var4.stackSize == 0) {
                mc.thePlayer.inventory.mainInventory[HotbarManager.INSTANCE.method_1973()] = null;
            } else if (var4 != null && (var4.stackSize != var3 || mc.playerController.isInCreativeMode())) {
                mc.entityRenderer.itemRenderer.resetEquippedProgress();
            }

            return true;
        }
    }

    public static AxisAlignedBB method_16(IBlockState var0, BlockPos var1) {
        Block var4;
        (var4 = var0.getBlock()).setBlockBoundsBasedOnState(mc.theWorld, var1);
        boolean var3;
        AxisAlignedBB var5;
        if (!(
                (var5 = new AxisAlignedBB(
                            var4.getBlockBoundsMinX(),
                            var4.getBlockBoundsMinY(),
                            var4.getBlockBoundsMinZ(),
                            var4.getBlockBoundsMaxX(),
                            var4.getBlockBoundsMaxY(),
                            var4.getBlockBoundsMaxZ()
                        ))
                        .getXsize()
                    <= 0.0
            )
            && !(var5.getYsize() <= 0.0)
            && !(var5.getZsize() <= 0.0)) {
            var3 = false;
        } else {
            var3 = true;
        }

        return var3 ? axisAlignedBB : var5;
    }

    public static BlockPos method_17(MovingObjectPosition var0) {
        return var0.getBlockPos().offset(var0.sideHit);
    }

    public static Block method_18(BlockPos var0) {
        IBlockState var2;
        return (var2 = method_23(var0)) == null ? null : var2.getBlock();
    }

    public static boolean method_19(BlockPos var0) {
        Block var2;
        return (var2 = method_18(var0)) == null || var2.getMaterial() == Material.air;
    }

    public static IBlockState method_20(BlockPos var0) {
        IBlockState var2;
        return (var2 = method_23(var0)) != null ? var2 : Blocks.air.getDefaultState();
    }

    public static void method_21(MovingObjectPosition var0) {
        method_24(var0, () -> true, () -> true, SwingMode.DO_NOT_HIDE);
    }

    public static boolean method_22(IBlockState var0, BlockPos var1, ItemStack var2) {
        return var0.getBlock().isReplaceable(mc.theWorld, var1);
    }

    public static IBlockState method_23(BlockPos var0) {
        return mc.theWorld == null ? null : mc.theWorld.getBlockState(var0);
    }

    public static void method_24(MovingObjectPosition var0, BooleanSupplier var1, BooleanSupplier var2, SwingMode var3) {
        int var6;
        ItemStack var7;
        if ((var7 = mc.thePlayer.inventory.getStackInSlot(HotbarManager.INSTANCE.method_1973())) == null) {
            var6 = 0;
        } else {
            var6 = var7.stackSize;
        }

        if (!mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, var7, var0.getBlockPos(), var0.sideHit, var0.hitVec)) {
            if (var7 != null) {
                if (mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, var7)) {
                    if (var2.getAsBoolean()) {
                        var3.method_14();
                    }

                    mc.entityRenderer.itemRenderer.resetEquippedProgress2();
                }
            }
        } else {
            if (var1.getAsBoolean()) {
                var3.method_14();
            }

            if (var7 != null && var7.stackSize == 0) {
                mc.thePlayer.inventory.mainInventory[HotbarManager.INSTANCE.method_1973()] = null;
            } else {
                if (var7 != null && (var7.stackSize != var6 || mc.playerController.isInCreativeMode())) {
                    mc.entityRenderer.itemRenderer.resetEquippedProgress();
                }
            }
        }
    }

    public static AxisAlignedBB method_25(BlockPos var0) {
        IBlockState var2;
        return (var2 = method_23(var0)) != null && var2.getBlock().getMaterial() != Material.air ? method_16(var2, var0) : axisAlignedBB;
    }

    public static boolean method_26(Block var0) {
        return var0 instanceof BlockSlab || var0 instanceof BlockStairs;
    }

    public static boolean method_27(BlockPos var0) {
        return method_23(var0) != null && World.doesBlockHaveSolidTopSurface(mc.theWorld, var0);
    }
}
