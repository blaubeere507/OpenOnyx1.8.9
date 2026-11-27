package client.onyx.module.player;

import client.onyx.event.impl.TickEvent;
import client.onyx.event.impl.WorldLoadEvent;
import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.setting.NamedEnum;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.EnumSetSetting;
import client.onyx.setting.impl.EnumSetting;
import client.onyx.setting.impl.RangeSetting;
import client.onyx.util.HypixelUtil;
import client.onyx.util.Timer;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

public class Deposit extends Module {
    public static final Deposit INSTANCE = new Deposit();
    private static final int INT_271 = 27;
    private static final int INT_401 = 40;
    private static final int INT_11 = 1;
    private static final int INT_91 = 9;
    public EnumSetting<Deposit.DepositSource> mode = new EnumSetting<>("Mode", Deposit.DepositSource.HELD)
        .describe("What a scroll at a chest sends over - just what you are holding, or a sweep of the hotbar");
    public EnumSetSetting<Deposit.DepositItems> items = new EnumSetSetting<>(
            "Items",
            Deposit.DepositItems.class,
            Deposit.DepositItems.IRON,
            Deposit.DepositItems.GOLD,
            Deposit.DepositItems.DIAMOND,
            Deposit.DepositItems.EMERALD
        )
        .method_77(false)
        .describe("Which of the hotbar a sweep picks up - the rest stays on you")
        .visibleWhen(this.mode, var0 -> var0 == Deposit.DepositSource.HOTBAR);
    public BooleanSetting hypixelOnly = new BooleanSetting("Hypixel only", true).describe("Leaves the scroll wheel alone on every other server");
    public RangeSetting rangeSetting = RangeSetting.method_90("Delay", 50, 100, 0, 500)
        .method_94(" ms")
        .describe("Spacing between the individual shift clicks");
    private transient Deposit.DepositState depositState = Deposit.DepositState.IDLE;
    private final transient Timer timer = new Timer();
    private transient int intValue;
    private transient EnumFacing enumFacing;
    private transient BlockPos blockPos;
    private transient int intValue2;
    private transient long longValue;
    private transient Vec3 vec31;

    @EventHandler
    private void onTick(TickEvent var1) {
        if (mc.thePlayer != null && mc.theWorld != null) {
            switch (this.depositState) {
                case OPENING:
                    this.method_1686();
                    return;
                case DEPOSITING:
                    this.method_1687();
                    return;
            }
        } else {
            this.method_1691();
        }
    }

    @Override
    public void onDisable() {
        this.method_1691();
    }

    private void method_1686() {
        if (this.method_1690() != null) {
            Deposit var2;
            int var3;
            if (!this.mode.method_168(Deposit.DepositSource.HELD)) {
                var3 = 0;
                var2 = this;
            } else {
                var3 = mc.thePlayer.inventory.currentItem;
                var2 = this;
            }

            var2.intValue2 = var3;
            this.longValue = 0L;
            this.timer.method_2017();
            this.depositState = Deposit.DepositState.DEPOSITING;
        } else {
            if (++this.intValue >= 40) {
                this.method_1691();
            }
        }
    }

    private void method_1687() {
        ContainerChest var1;
        if ((var1 = this.method_1690()) == null) {
            this.method_1691();
        } else if (this.timer.method_2019(this.longValue)) {
            int var6;
            if ((var6 = this.method_1692()) < 0) {
                mc.thePlayer.closeScreen();
                this.method_1691();
            } else {
                mc.playerController.windowClick(var1.windowId, this.method_1693(var1, var6), 0, 1, mc.thePlayer);
                Deposit var4;
                int var5;
                if (!this.mode.method_168(Deposit.DepositSource.HELD)) {
                    var5 = var6 + 1;
                    var4 = this;
                } else {
                    var5 = 9;
                    var4 = this;
                }

                var4.intValue2 = var5;
                this.longValue = (long)this.rangeSetting.method_105();
                this.timer.method_2017();
            }
        }
    }

    private boolean method_1688(BlockPos var1) {
        Block var2;
        return (var2 = mc.theWorld.getBlockState(var1).getBlock()) instanceof BlockChest || var2 instanceof BlockEnderChest;
    }

    private boolean method_1689(ItemStack var1) {
        Item var3;
        if ((var3 = var1.getItem()) == Items.iron_ingot) {
            return this.items.method_79(Deposit.DepositItems.IRON);
        } else if (var3 == Items.gold_ingot) {
            return this.items.method_79(Deposit.DepositItems.GOLD);
        } else if (var3 == Items.diamond) {
            return this.items.method_79(Deposit.DepositItems.DIAMOND);
        } else if (var3 == Items.emerald) {
            return this.items.method_79(Deposit.DepositItems.EMERALD);
        } else {
            return var3 instanceof ItemBlock ? this.items.method_79(Deposit.DepositItems.BLOCKS) : this.items.method_79(Deposit.DepositItems.OTHER);
        }
    }

    private ContainerChest method_1690() {
        if (!(mc.currentScreen instanceof GuiChest)) {
            return null;
        } else {
            Container var2 = mc.thePlayer.openContainer;
            return mc.thePlayer.openContainer instanceof ContainerChest ? (ContainerChest)var2 : null;
        }
    }

    private void method_1691() {
        this.depositState = Deposit.DepositState.IDLE;
        this.blockPos = null;
        this.enumFacing = null;
        this.vec31 = null;
        this.intValue = 0;
        this.intValue2 = 0;
        this.longValue = 0L;
    }

    private int method_1692() {
        var var1 = this;

        int var4;
        for (int var3 = var4 = this.intValue2; var3 < 9; var3 = ++var4) {
            ItemStack var2 = mc.thePlayer.inventory.getStackInSlot(var4);
            if (var1.mode.method_168(Deposit.DepositSource.HELD)) {
                if (var2 == null) {
                    return -1;
                }

                return var4;
            }

            if (var2 != null && var1.method_1689(var2)) {
                return var4;
            }
        }

        return -1;
    }

    private int method_1693(ContainerChest var1, int var2) {
        return var1.getLowerChestInventory().getSizeInventory() / 9 * 9 + 27 + var2;
    }

    private Deposit() {
        super("Deposit", "Scroll at a chest to put your items in it", Category.PLAYER);
    }

    public boolean method_1694(int var1) {
        if (var1 == 0 || !this.isToggled() || this.depositState != Deposit.DepositState.IDLE) {
            return false;
        } else if (mc.thePlayer == null || mc.theWorld == null || mc.currentScreen != null) {
            return false;
        } else if (this.hypixelOnly.method_146() && !HypixelUtil.method_2176()) {
            return false;
        } else {
            MovingObjectPosition var3 = mc.objectMouseOver;
            if (mc.objectMouseOver == null || var3.typeOfHit != MovingObjectType.BLOCK) {
                return false;
            } else if (!this.method_1688(var3.getBlockPos())) {
                return false;
            } else if (this.mode.method_168(Deposit.DepositSource.HELD) && mc.thePlayer.getHeldItem() == null) {
                return false;
            } else {
                this.blockPos = var3.getBlockPos();
                this.enumFacing = var3.sideHit;
                this.vec31 = var3.hitVec;
                this.intValue = 0;
                this.depositState = Deposit.DepositState.OPENING;
                mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), this.blockPos, this.enumFacing, this.vec31);
                return true;
            }
        }
    }

    @EventHandler
    private void onWorldLoad(WorldLoadEvent var1) {
        this.method_1691();
    }

    public static enum DepositItems implements NamedEnum {
        IRON("Iron"),
        GOLD("Gold"),
        DIAMOND("Diamond"),
        EMERALD("Emerald"),
        BLOCKS("Blocks"),
        OTHER("Anything else");

        private final String string;

        @Override
        public String getDisplayName() {
            return this.string;
        }

        private DepositItems(String var3) {
            this.string = var3;
        }
    }

    public static enum DepositSource implements NamedEnum {
        HELD("Held item"),
        HOTBAR("Hotbar");

        private final String string;

        private DepositSource(String var3) {
            this.string = var3;
        }

        @Override
        public String getDisplayName() {
            return this.string;
        }
    }

    private static enum DepositState {
        IDLE,
        OPENING,
        DEPOSITING;
    }
}
