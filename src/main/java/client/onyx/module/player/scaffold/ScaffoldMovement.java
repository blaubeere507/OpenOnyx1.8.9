package client.onyx.module.player.scaffold;

import client.onyx.util.IMinecraft;
import client.onyx.util.item.BlockItemUtil;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockFalling;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public final class ScaffoldMovement implements IMinecraft {
    private static final Set<Block> set = Set.of(Blocks.tnt, Blocks.web, Blocks.portal);
    private static final Set<Block> set2 = Set.of(Blocks.crafting_table, Blocks.enchanting_table, Blocks.cauldron);

    public static boolean method_1716(ItemStack var0) {
        Block var2;
        return var0 != null
            && (var2 = BlockItemUtil.method_1989(var0)) != null
            && var2.getMaterial().isSolid()
            && var2.isFullCube()
            && !(var2 instanceof BlockFalling)
            && !set.contains(var2);
    }

    private ScaffoldMovement() {
    }

    public static boolean method_1717(ItemStack var0) {
        Block var2;
        return (var2 = BlockItemUtil.method_1989(var0)) == null
            || var2.slipperiness > 0.6F
            || var2 instanceof BlockContainer
            || !var2.isFullCube()
            || set2.contains(var2);
    }
}
