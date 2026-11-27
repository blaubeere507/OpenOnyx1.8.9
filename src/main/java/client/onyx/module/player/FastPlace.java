package client.onyx.module.player;

import client.onyx.Onyx;
import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.RangeSetting;
import client.onyx.util.Timer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

public class FastPlace extends Module {
    public BooleanSetting place = new BooleanSetting("Place", true).describe("Removes the delay when the right click puts a block down");
    public BooleanSetting interact = new BooleanSetting("Interact", true)
        .describe("Removes the delay on everything else the right click does - using items, and clicking entities");
    public BooleanSetting jump = new BooleanSetting("Jump", false)
        .describe("Removes the 10 tick cooldown after a jump, so you leave the ground again the tick you land");
    public RangeSetting rangeSetting = RangeSetting.method_90("CPS", 12, 20, 1, 20)
        .method_94(" cps")
        .describe("The rate the held right click repeats at - re-rolled inside this range every action")
        .visibleWhen(() -> {
            FastPlace var0;
            return (var0 = method_1641()) != null && (var0.place.method_146() || var0.interact.method_146());
        });
    private final transient Timer timer = new Timer();
    private transient long longValue;

    @Override
    public String getSuffix() {
        return this.rangeSetting.method_96();
    }

    private boolean method_1640(MovingObjectPosition var1, ItemStack var2) {
        boolean var4;
        if (var1 != null && var1.typeOfHit == MovingObjectType.BLOCK && var2 != null && var2.getItem() instanceof ItemBlock) {
            var4 = true;
        } else {
            var4 = false;
        }

        return !var4 ? this.interact.method_146() : this.place.method_146();
    }

    private static FastPlace method_1641() {
        return Onyx.moduleManager == null ? null : Onyx.moduleManager.fastPlace;
    }

    public FastPlace() {
        super("FastPlace", "Places, interacts and jumps without vanilla's cooldowns", Category.PLAYER);
    }

    public static int method_1642(MovingObjectPosition var0, ItemStack var1) {
        FastPlace var3;
        if ((var3 = method_1641()) != null && var3.isToggled()) {
            var3.timer.method_2017();
            return var3.method_1640(var0, var1) ? 0 : 4;
        } else {
            return 4;
        }
    }

    @Override
    public void onEnable() {
        this.longValue = this.method_1643();
        this.timer.method_2017();
    }

    private long method_1643() {
        return (long)(1000.0 / this.rangeSetting.method_105());
    }

    public static boolean method_1644() {
        FastPlace var0;
        if ((var0 = method_1641()) == null || !var0.isToggled()) {
            return true;
        } else if (!var0.place.method_146() && !var0.interact.method_146()) {
            return true;
        } else if (!var0.timer.method_2019(var0.longValue)) {
            return false;
        } else {
            var0.longValue = var0.method_1643();
            return true;
        }
    }

    public static int method_1645(EntityLivingBase var0) {
        FastPlace var2;
        if ((var2 = method_1641()) == null || !var2.isToggled() || !var2.jump.method_146()) {
            return 10;
        } else {
            return var0 == mc.thePlayer ? 0 : 10;
        }
    }
}
