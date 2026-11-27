package client.onyx.module.player.scaffold.feature;

import client.onyx.event.impl.PostJumpEvent;
import client.onyx.setting.Setting;
import client.onyx.setting.SettingSection;
import client.onyx.setting.impl.RangeSetting;
import client.onyx.util.IMinecraft;
import client.onyx.util.player.MovementPredictor;
import meteordevelopment.orbit.EventHandler;

public class SpeedSettings extends SettingSection implements IMinecraft {
    public static final SpeedSettings speedSettings = new SpeedSettings();
    public RangeSetting rangeSetting = RangeSetting.method_104("Straight speed", 0.48, 0.49, 0.1, 1.0);
    public RangeSetting rangeSetting2 = RangeSetting.method_104("Diagonal speed", 0.48, 0.49, 0.1, 1.0);

    private SpeedSettings() {
        super("Strafe on jump", false);
        this.visibleWhen(Setting.booleanSupplier);
    }

    @EventHandler
    private void onPostJump(PostJumpEvent var1) {
        if (mc.thePlayer != null) {
            boolean var2;
            if ((float)(Math.rint((MovementPredictor.method_1913(mc.thePlayer) + 180.0F) / 45.0F) * 45.0) % 90.0F == 0.0F) {
                var2 = true;
            } else {
                var2 = false;
            }

            RangeSetting var4;
            if (!var2) {
                var4 = this.rangeSetting2;
            } else {
                var4 = this.rangeSetting;
            }

            MovementPredictor.method_1907(mc.thePlayer, MovementPredictor.method_1884(MovementPredictor.method_1904(mc.thePlayer), var4.method_105()));
        }
    }
}
