package client.onyx.module.player.scaffold.feature;

import client.onyx.setting.SettingSection;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.NumberSetting;

public class AutoBlockSettings extends SettingSection {
    public static final AutoBlockSettings autoBlockSettings = new AutoBlockSettings();
    public BooleanSetting alwaysHoldBlock = new BooleanSetting("Always hold block", false)
        .describe("Keeps the block slot held server-side the whole time instead of swapping to it only on the tick a block goes out");
    public NumberSetting slotResetDelay = new NumberSetting("Slot reset delay", 5.0, 0.0, 40.0, 1.0)
        .method_147(" ticks")
        .describe("How long the silent slot is held after a placement before the real slot comes back");
    public NumberSetting doNotUseBelowCount = new NumberSetting("Do not use below count", 1.0, 0.0, 64.0, 1.0)
        .describe("Leaves a stack alone once it is down to this many blocks");

    private AutoBlockSettings() {
        super("Auto block", true);
    }
}
