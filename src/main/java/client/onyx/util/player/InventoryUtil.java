package client.onyx.util.player;

import client.onyx.util.IMinecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

public final class InventoryUtil {
    public static final int INT_91 = 9;
    public static final int INT_451 = 45;
    public static final int INT_11 = 1;
    public static final int INT_21 = 2;
    public static final int INT_361 = 36;
    public static final int INT_41 = 4;
    public static final int INT_51 = 5;
    public static final int INT_92 = 9;

    private static void method_2162(int var0, int var1, int var2) {
        Container var4;
        if ((var4 = method_2165()) != null) {
            IMinecraft.mc.playerController.windowClick(var4.windowId, var0, var1, var2, IMinecraft.mc.thePlayer);
        }
    }

    public static double method_2163(ItemStack var0) {
        if (var0 != null && var0.getItem() != null) {
            double var4;
            Item var6;
            if ((var6 = var0.getItem()) instanceof ItemSword) {
                var4 = ((ItemSword)var6).getDamageVsEntity();
            } else {
                var4 = 1.0;
            }

            double var1;
            return (var1 = var4 + method_2166(var0, Enchantment.sharpness) * 1.25)
                + method_2166(var0, Enchantment.fireAspect) * 0.3
                + method_2166(var0, Enchantment.knockback) * 0.15
                + method_2166(var0, Enchantment.unbreaking) * 0.1;
        } else {
            return Double.NEGATIVE_INFINITY;
        }
    }

    public static ItemStack method_2164(int var0) {
        Container var2;
        return (var2 = method_2165()) != null && var0 >= 0 && var0 < var2.inventorySlots.size() ? var2.getSlot(var0).getStack() : null;
    }

    public static Container method_2165() {
        return IMinecraft.mc.thePlayer.inventoryContainer;
    }

    private static int method_2166(ItemStack var0, Enchantment var1) {
        return EnchantmentHelper.getEnchantmentLevel(var1.effectId, var0);
    }

    public static void method_2167(int var0) {
        method_2162(var0, 0, 1);
    }

    public static double method_2168(ItemStack var0) {
        Item var4;
        if (var0 != null && (var4 = var0.getItem()) instanceof ItemArmor) {
            ItemArmor var1 = (ItemArmor)var4;
            return method_2171(var0, var1);
        } else {
            return Double.NEGATIVE_INFINITY;
        }
    }

    public static int method_2169(ItemStack var0) {
        Item var4;
        if (var0 != null && (var4 = var0.getItem()) instanceof ItemArmor) {
            ItemArmor var1;
            ItemArmor var3 = var1 = (ItemArmor)var4;
            return var3.armorType;
        } else {
            return -1;
        }
    }

    private InventoryUtil() {
    }

    public static void method_2170(int var0) {
        method_2162(var0, 1, 4);
    }

    private static double method_2171(ItemStack var0, ItemArmor var1) {
        double var10001 = var1.damageReduceAmount;
        return var1.damageReduceAmount
            + method_2166(var0, Enchantment.protection) * 1.25
            + method_2166(var0, Enchantment.blastProtection) * 0.15
            + method_2166(var0, Enchantment.fireProtection) * 0.15
            + method_2166(var0, Enchantment.projectileProtection) * 0.15
            + method_2166(var0, Enchantment.thorns) * 0.1
            + method_2166(var0, Enchantment.unbreaking) * 0.1;
    }

    public static void method_2172(int var0, int var1) {
        method_2162(var0, var1, 2);
    }

    public static double method_2173(ItemStack var0) {
        if (var0 != null && var0.getItem() != null) {
            Item var3;
            if ((var3 = var0.getItem()) instanceof ItemArmor) {
                ItemArmor var2 = (ItemArmor)var3;
                return method_2171(var0, var2);
            } else if (var3 instanceof ItemSword) {
                return method_2163(var0);
            } else if (var3 instanceof ItemTool) {
                return method_2175(var0);
            } else {
                return var3 instanceof ItemBlock ? var0.stackSize : var0.stackSize;
            }
        } else {
            return Double.NEGATIVE_INFINITY;
        }
    }

    public static int method_2174(int var0) {
        return 5 + var0;
    }

    public static double method_2175(ItemStack var0) {
        if (var0 != null && var0.getItem() != null) {
            double var4;
            Item var6;
            if ((var6 = var0.getItem()) instanceof ItemTool) {
                var4 = ((ItemTool)var6).getToolMaterial().getEfficiencyOnProperMaterial();
            } else {
                var4 = 1.0;
            }

            double var1;
            return (var1 = var4 + method_2166(var0, Enchantment.fortune) * 1.25)
                + method_2166(var0, Enchantment.efficiency) * 0.5
                + method_2166(var0, Enchantment.unbreaking) * 0.3;
        } else {
            return Double.NEGATIVE_INFINITY;
        }
    }
}
