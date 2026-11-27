package client.onyx.module.player;

import client.onyx.Onyx;
import client.onyx.event.impl.PlayerUpdateEvent;
import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.NumberSetting;
import java.util.Locale;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.BlockChest;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class ChestStealer extends Module {
    private static final int INT_51 = 5;
    public NumberSetting delay = new NumberSetting("Delay", 1.0, 0.0, 10.0, 1.0).method_147("t").describe("Ticks between clicks");
    public BooleanSetting filter = new BooleanSetting("Filter", true).describe("Leaves behind anything InventoryManager calls junk");
    public BooleanSetting close = new BooleanSetting("Close", true).describe("Closes the chest once there is nothing left worth taking");
    public BooleanSetting antiGUI = new BooleanSetting("Anti GUI", true)
        .describe("Only steal from a real chest. A shop, kit or menu is a chest window like any other, and clicking through one buys it out.");
    private transient int intValue;

    @Override
    public void onDisable() {
        this.intValue = 0;
    }

    private static String method_1646() {
        String var0 = "container.chest";
        String var1 = I18n.format("container.chest");
        String var2;
        if (var0.equals(var1)) {
            var2 = "chest";
        } else {
            var2 = var1;
        }

        return var2.toLowerCase(Locale.ROOT);
    }

    private boolean method_1647(ContainerChest var1) {
        return !this.antiGUI.method_146() || method_1650(var1) && this.method_1652();
    }

    @EventHandler
    private void onPlayerUpdate(PlayerUpdateEvent var1) {
        ContainerChest var3;
        if ((var3 = this.method_1648()) != null && this.method_1647(var3)) {
            int var2;
            if ((var2 = this.method_1649(var3)) < 0) {
                this.intValue = 0;
                if (this.close.method_146()) {
                    mc.thePlayer.closeScreen();
                }
            } else if (this.intValue++ >= this.delay.method_149()) {
                mc.playerController.windowClick(var3.windowId, var2, 0, 1, mc.thePlayer);
                this.intValue = 0;
            }
        } else {
            this.intValue = 0;
        }
    }

    public ChestStealer() {
        super("ChestStealer", "Takes the useful items out of a chest", Category.PLAYER);
    }

    private ContainerChest method_1648() {
        if (mc.thePlayer != null && mc.currentScreen instanceof GuiChest) {
            Container var2 = mc.thePlayer.openContainer;
            return mc.thePlayer.openContainer instanceof ContainerChest ? (ContainerChest)var2 : null;
        } else {
            return null;
        }
    }

    private int method_1649(ContainerChest var1) {
        var var3 = this;
        int var4 = var1.getLowerChestInventory().getSizeInventory();
        int var6 = 0;

        for (int var5 = 0; var5 < var4; var5 = ++var6) {
            ItemStack var2;
            if ((var2 = var1.getSlot(var6).getStack()) != null && var2.getItem() != null && !var3.method_1651(var2)) {
                return var6;
            }
        }

        return -1;
    }

    private static boolean method_1650(ContainerChest var0) {
        IChatComponent var2;
        String var3;
        return (var2 = var0.getLowerChestInventory().getDisplayName()) != null
            && (var3 = EnumChatFormatting.getTextWithoutFormattingCodes(var2.getUnformattedText())) != null
            && var3.toLowerCase(Locale.ROOT).contains(method_1646());
    }

    private boolean method_1651(ItemStack var1) {
        return this.filter.method_146() && Onyx.moduleManager.inventoryManager.method_1698(var1);
    }

    private boolean method_1652() {
        if (mc.theWorld == null) {
            return false;
        } else {
            BlockPos var4 = new BlockPos(mc.thePlayer);
            int var2 = -5;

            for (int var7 = -5; var7 <= 5; var7 = ++var2) {
                int var8 = -5;

                for (int var6 = -5; var6 <= 5; var6 = ++var8) {
                    int var1 = -5;

                    for (int var5 = -5; var5 <= 5; var5 = ++var1) {
                        if (mc.theWorld.getBlockState(var4.add(var2, var8, var1)).getBlock() instanceof BlockChest) {
                            return true;
                        }
                    }
                }
            }

            return false;
        }
    }
}
