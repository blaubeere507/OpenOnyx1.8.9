package client.onyx.util.world;

import client.onyx.module.player.scaffold.ScaffoldMovement;
import client.onyx.util.ChainedComparator;
import client.onyx.util.IMinecraft;
import java.util.Comparator;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;

public final class BlockSelection implements IMinecraft {
    public static final Comparator<ItemStack> comparator4 = (var0, var1) -> {
        boolean var2;
        ItemStack var3;
        if (!ScaffoldMovement.method_1717(var0)) {
            var3 = var1;
            var2 = true;
        } else {
            var3 = var1;
            var2 = false;
        }

        boolean var5;
        boolean var6;
        if (ScaffoldMovement.method_1717(var3)) {
            var6 = false;
            var5 = var2;
        } else {
            var6 = true;
            var5 = var2;
        }

        return Boolean.compare(var5, var6);
    };
    public static final Comparator<ItemStack> comparator3 = (var0, var1) -> Boolean.compare(method_1980(var0).isNormalCube(), method_1980(var1).isNormalCube());
    public static final Comparator<ItemStack> comparator5 = (var0, var1) -> Boolean.compare(method_1980(var0).isFullCube(), method_1980(var1).isFullCube());
    public static final Comparator<ItemStack> comparator = new Comparator<ItemStack>() {
        private final ChainedComparator<Block> chainedComparator = new ChainedComparator<>(Comparator.comparingDouble(var0 -> var0.slipperiness));

        public int compare(ItemStack var1, ItemStack var2) {
            return this.chainedComparator.compare(BlockSelection.method_1980(var1), BlockSelection.method_1980(var2));
        }
    };
    public static final Comparator<ItemStack> comparator6 = Comparator.comparingInt(var0 -> var0.stackSize);
    public static final Comparator<ItemStack> comparator2 = comparator6.reversed();
    private static final double DOUBLE_0_81 = 0.8;
    private static final double DOUBLE_1_71 = 1.7;
    private static final double DOUBLE_2_01 = 2.0;

    private static Block method_1980(ItemStack var0) {
        return ((ItemBlock)var0.getItem()).getBlock();
    }

    private BlockSelection() {
    }

    private static double method_1981(ItemStack var0, boolean var1) {
        float var3 = method_1980(var0).getBlockHardness(mc.theWorld, BlockPos.ORIGIN);
        return var1 && var3 >= 0.8 && var3 <= 2.0 ? 0.0 : Math.abs(1.7 - var3);
    }

    public static Comparator<ItemStack> method_1982(boolean var0) {
        return (var1, var2) -> Double.compare(method_1981(var2, var0), method_1981(var1, var0));
    }
}
