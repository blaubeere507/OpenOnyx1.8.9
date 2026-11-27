package client.onyx.module.player;

import client.onyx.event.impl.PlayerUpdateEvent;
import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.ButtonSetting;
import client.onyx.setting.impl.ItemListSetting;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.setting.impl.SlotLayoutSetting;
import client.onyx.ui.screen.InventoryRulesScreen;
import client.onyx.util.player.InventoryUtil;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.ItemStack;

public class InventoryManager extends Module {
    private static final int INT_41 = 4;
    public ButtonSetting editRules = new ButtonSetting("Edit rules", () -> mc.displayGuiScreen(new InventoryRulesScreen(mc.currentScreen)))
        .describe("Drag items onto the slots they belong in");
    public NumberSetting delay = new NumberSetting("Delay", 1.0, 0.0, 10.0, 1.0).method_147("t").describe("Ticks between clicks");
    public BooleanSetting manageArmor = new BooleanSetting("Manage armor", true).describe("Wears the best piece you are carrying in each armour slot");
    public BooleanSetting dropJunk = new BooleanSetting("Drop junk", true).describe("Throws away anything on the Drop list that the Keep list does not rescue");
    public SlotLayoutSetting slots = new SlotLayoutSetting(
        "Slots",
        SlotLayoutSetting.method_138(
            0, "@SWORD", 1, "minecraft:golden_apple", 2, "@AXE", 3, "@PICKAXE", 4, "@BLOCK", 5, "@BLOCK", 6, "@BLOCK", 7, "@BLOCK", 8, "@BLOCK"
        )
    );
    public ItemListSetting keep = new ItemListSetting("Keep");
    public ItemListSetting drop = new ItemListSetting(
        "Drop",
        "minecraft:snowball",
        "minecraft:egg",
        "minecraft:experience_bottle",
        "minecraft:flint",
        "minecraft:flint_and_steel",
        "minecraft:lava_bucket",
        "minecraft:string",
        "minecraft:chest",
        "minecraft:trapped_chest",
        "minecraft:ender_chest",
        "@HOE",
        "@BAD_POTION",
        "~anvil",
        "~tnt",
        "~seed",
        "~table",
        "~eye",
        "~mushroom",
        "~skull",
        "~pressure_plate"
    );
    private transient int intValue;

    @EventHandler
    private void onPlayerUpdate(PlayerUpdateEvent var1) {
        if (mc.thePlayer != null && mc.currentScreen instanceof GuiInventory && mc.thePlayer.openContainer == mc.thePlayer.inventoryContainer) {
            if (this.intValue++ >= this.delay.method_149()) {
                if (this.method_1699() || this.method_1696() || this.method_1697()) {
                    this.intValue = 0;
                }
            }
        } else {
            this.intValue = 0;
        }
    }

    public InventoryManager() {
        super("InventoryManager", "Sorts your inventory the way you laid it out", Category.PLAYER);
    }

    private boolean method_1695(ItemStack var1) {
        return this.drop.method_110(var1) && !this.keep.method_110(var1) && this.slots.method_132(var1) < 0;
    }

    @Override
    public void onDisable() {
        this.intValue = 0;
    }

    private boolean method_1696() {
        var var2 = this;
        boolean[] var4 = new boolean[45];
        int var13 = 0;

        for (int var12 = 0; var12 < 9; var12 = ++var13) {
            if (!var2.slots.method_135(var13).isEmpty()) {
                int var7 = 36 + var13;
                int var1 = -1;
                double var5 = Double.NEGATIVE_INFINITY;
                int var3 = 9;

                for (int var11 = 9; var11 < 45; var11 = ++var3) {
                    ItemStack var8;
                    double var9;
                    if (!var4[var3]
                        && (var8 = InventoryUtil.method_2164(var3)) != null
                        && var2.slots.method_133(var13, var8)
                        && !((var9 = InventoryUtil.method_2173(var8)) <= var5)) {
                        var5 = var9;
                        var1 = var3;
                    }
                }

                if (var1 >= 0) {
                    var4[var1] = true;
                    if (var1 != var7) {
                        InventoryUtil.method_2172(var1, var13);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean method_1697() {
        var var1 = this;
        if (!this.dropJunk.method_146()) {
            return false;
        } else {
            int var4 = 9;

            for (int var3 = 9; var3 < 45; var3 = ++var4) {
                ItemStack var2;
                if ((var2 = InventoryUtil.method_2164(var4)) != null && var1.method_1695(var2)) {
                    InventoryUtil.method_2170(var4);
                    return true;
                }
            }

            return false;
        }
    }

    public boolean method_1698(ItemStack var1) {
        ItemStack var5 = var1;
        var var6 = this;
        if (var1 == null || var1.getItem() == null) {
            return false;
        } else if (this.keep.method_110(var1)) {
            return false;
        } else if (this.drop.method_110(var1)) {
            return true;
        } else if (this.isToggled() && mc.thePlayer != null) {
            int var2;
            if ((var2 = InventoryUtil.method_2169(var1)) >= 0) {
                ItemStack var8;
                return this.manageArmor.method_146()
                    && (var8 = InventoryUtil.method_2164(InventoryUtil.method_2174(var2))) != null
                    && InventoryUtil.method_2168(var1) <= InventoryUtil.method_2168(var8);
            } else if (this.slots.method_132(var1) < 0) {
                return false;
            } else {
                int var3 = 0;

                for (int var4 = 0; var4 < 9; var4 = ++var3) {
                    ItemStack var7;
                    if (var6.slots.method_133(var3, var5)
                        && (
                            (var7 = InventoryUtil.method_2164(36 + var3)) == null
                                || !var6.slots.method_133(var3, var7)
                                || InventoryUtil.method_2173(var5) > InventoryUtil.method_2173(var7)
                        )) {
                        return false;
                    }
                }

                return true;
            }
        } else {
            return false;
        }
    }

    private boolean method_1699() {
        var var3 = this;
        if (!this.manageArmor.method_146()) {
            return false;
        } else {
            int var1 = 0;

            for (int var14 = 0; var14 < 4; var14 = ++var1) {
                ItemStack var6;
                int var7;
                double var11;
                if ((var6 = InventoryUtil.method_2164(var7 = InventoryUtil.method_2174(var1))) == null) {
                    var11 = Double.NEGATIVE_INFINITY;
                } else {
                    var11 = InventoryUtil.method_2168(var6);
                }

                double var4 = var11;
                int var15 = -1;
                int var2 = 9;

                for (int var13 = 9; var13 < 45; var13 = ++var2) {
                    ItemStack var8;
                    double var9;
                    if (InventoryUtil.method_2169(var8 = InventoryUtil.method_2164(var2)) == var1
                        && !var3.method_1695(var8)
                        && !((var9 = InventoryUtil.method_2168(var8)) <= var4)) {
                        var4 = var9;
                        var15 = var2;
                    }
                }

                if (var15 >= 0) {
                    if (var6 == null) {
                        InventoryUtil.method_2167(var15);
                    } else {
                        InventoryUtil.method_2170(var7);
                    }

                    return true;
                }
            }

            return false;
        }
    }
}
