package client.onyx.module.player.scaffold.feature;

import client.onyx.event.impl.PostTickEvent;
import client.onyx.setting.Setting;
import client.onyx.setting.SettingSection;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.util.IMinecraft;
import client.onyx.util.math.VecUtil;
import client.onyx.util.player.MovementPredictor;
import meteordevelopment.orbit.EventHandler;

public class AccelerationSettings extends SettingSection implements IMinecraft {
    public static final AccelerationSettings accelerationSettings = new AccelerationSettings();
    public NumberSetting speedMultiplier = new NumberSetting("Speed multiplier", 0.6, 0.1, 3.0, 0.01);
    public BooleanSetting onlyOnGround = new BooleanSetting("Only on ground", false);

    @EventHandler
    private void onPostTick(PostTickEvent var1) {
        if (mc.thePlayer != null) {
            if (!this.onlyOnGround.getValue() || mc.thePlayer.onGround) {
                double var2 = this.speedMultiplier.method_151();
                MovementPredictor.method_1907(mc.thePlayer, VecUtil.method_1941(MovementPredictor.method_1904(mc.thePlayer), var2, 1.0, var2));
            }
        }
    }

    private AccelerationSettings() {
        super("Acceleration", false);
        this.visibleWhen(Setting.booleanSupplier);
    }
}
