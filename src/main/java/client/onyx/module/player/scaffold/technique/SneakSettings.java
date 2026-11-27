package client.onyx.module.player.scaffold.technique;

import client.onyx.event.impl.MovementInputEvent;
import client.onyx.setting.RangeSampler;
import client.onyx.setting.SettingSection;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.RangeSetting;
import client.onyx.util.IMinecraft;
import client.onyx.util.player.MovementInputState;
import client.onyx.util.player.MovementPredictor;
import meteordevelopment.orbit.EventHandler;

public class SneakSettings extends SettingSection implements IMinecraft {
    public static final SneakSettings sneakSettings = new SneakSettings();
    public RangeSetting rangeSetting = RangeSetting.method_90("Blocks to sneak", 0, 0, 0, 10).describe("How many blocks to place between sneaks");
    public RangeSetting rangeSetting2 = RangeSetting.method_104("Edge distance", 0.01, 0.05, 0.01, 1.3).method_94(" blocks");
    public BooleanSetting onlyOnGround = new BooleanSetting("Only on ground", true);
    private final transient RangeSampler rangeSampler2 = new RangeSampler(this.rangeSetting);
    private final transient RangeSampler rangeSampler = new RangeSampler(this.rangeSetting2);
    private transient int intValue2;

    public void method_1767() {
        if (this.method_61()) {
            this.intValue2++;
            if (this.intValue2 > this.rangeSampler2.method_54()) {
                this.intValue2 = 0;
                this.rangeSampler2.method_53();
                this.rangeSampler.method_53();
            }
        }
    }

    @EventHandler(
        priority = -50
    )
    private void onMovementInput(MovementInputEvent var1) {
        if (!var1.isFlag() && this.method_1768(var1.getMovementInputState())) {
            var1.method_2620(true);
        }
    }

    private SneakSettings() {
        super("Sneak", false);
    }

    public boolean method_1768(MovementInputState var1) {
        if (mc.thePlayer == null) {
            return false;
        } else if (DownSettings.method_1769()) {
            return false;
        } else if (!mc.thePlayer.onGround && this.onlyOnGround.getValue()) {
            return false;
        } else {
            boolean var3;
            if (!mc.thePlayer.capabilities.isFlying && this.intValue2 == 0) {
                var3 = true;
            } else {
                var3 = false;
            }

            return var3 && MovementPredictor.method_1906(mc.thePlayer, var1, this.rangeSampler.getDoubleValue());
        }
    }
}
