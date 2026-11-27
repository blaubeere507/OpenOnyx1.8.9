package client.onyx.util.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class TeamUtil {
    private static Integer method_2151(ItemStack var0) {
        Item var2;
        if (var0 != null && (var2 = var0.getItem()) instanceof ItemArmor) {
            ItemArmor var4;
            ItemArmor var3 = var4 = (ItemArmor)var2;
            return !var3.hasColor(var0) ? null : var4.getColor(var0);
        } else {
            return null;
        }
    }

    private static boolean method_2152(EntityPlayer var0, EntityPlayer var1) {
        EntityPlayer var2 = var0;
        int var6 = 1;

        for (int var5 = 1; var5 <= 4; var5 = ++var6) {
            Integer var3 = method_2151(var2.getEquipmentInSlot(var6));
            Integer var4 = method_2151(var1.getEquipmentInSlot(var6));
            if (var3 != null && var3.equals(var4)) {
                return true;
            }
        }

        return false;
    }

    private static boolean method_2153(EntityPlayer var0, EntityPlayer var1) {
        EnumChatFormatting var3 = var0.getDisplayName().getChatStyle().getColor();
        EnumChatFormatting var4 = var1.getDisplayName().getChatStyle().getColor();
        return var3 != null && var3 == var4;
    }

    private static boolean method_2154(EntityPlayer var0) {
        EntityPlayer var1 = var0;
        int var3 = 1;

        for (int var2 = 1; var2 <= 4; var2 = ++var3) {
            if (method_2151(var1.getEquipmentInSlot(var3)) != null) {
                return true;
            }
        }

        return false;
    }

    public static boolean method_2155(EntityPlayer var0, EntityPlayer var1) {
        return var0 != null
            && var1 != null
            && var0 != var1
            && method_2154(var0)
            && (var0.isOnSameTeam(var1) || method_2153(var0, var1) || method_2152(var0, var1));
    }
}
