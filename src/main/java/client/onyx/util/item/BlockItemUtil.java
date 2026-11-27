package client.onyx.util.item;

import client.onyx.util.IMinecraft;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class BlockItemUtil implements IMinecraft {
    public static Block method_1989(ItemStack var0) {
        Item var4;
        if (var0 != null && (var4 = var0.getItem()) instanceof ItemBlock) {
            ItemBlock var1;
            ItemBlock var3 = var1 = (ItemBlock)var4;
            return var3.getBlock();
        } else {
            return null;
        }
    }

    public static boolean method_1990(ItemStack var0) {
        Block var2;
        return (var2 = method_1989(var0)) != null && var2.isFullCube();
    }
}
