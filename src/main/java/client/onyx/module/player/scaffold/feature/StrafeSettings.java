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
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class StrafeSettings extends SettingSection implements IMinecraft {
    public static final StrafeSettings strafeSettings = new StrafeSettings();
    public NumberSetting speed = new NumberSetting("Speed", 0.247, 0.0, 5.0, 0.001);
    public BooleanSetting hypixel = new BooleanSetting("Hypixel", false);
    public BooleanSetting onlyOnGround = new BooleanSetting("Only on ground", false);
    private transient int intValue2;

    @EventHandler
    private void onPostTick(PostTickEvent var1) {
        if (mc.thePlayer != null) {
            StrafeSettings var5;
            if (!MovementPredictor.method_1896(mc.thePlayer)) {
                this.intValue2 = 0;
                var5 = this;
            } else {
                this.intValue2++;
                var5 = this;
            }

            if (!var5.onlyOnGround.getValue() || mc.thePlayer.onGround) {
                if (this.hypixel.getValue()) {
                    double var2 = 0.207;
                    int var6;
                    PotionEffect var7;
                    if ((var7 = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed)) != null) {
                        var6 = var7.getAmplifier();
                    } else {
                        var6 = -1;
                    }

                    if (var6 >= 0) {
                        var2 = 0.295;
                    }

                    if (mc.thePlayer.ticksExisted % 20 == 0 || this.intValue2 <= 7) {
                        var2 = 0.09800000190734863;
                    }

                    MovementPredictor.method_1907(mc.thePlayer, MovementPredictor.method_1884(MovementPredictor.method_1904(mc.thePlayer), var2));
                } else {
                    MovementPredictor.method_1907(
                        mc.thePlayer, MovementPredictor.method_1884(MovementPredictor.method_1904(mc.thePlayer), this.speed.method_151())
                    );
                }
            }
        }
    }

    @Override
    public void method_64() {
        if (this.hypixel.getValue() && mc.thePlayer != null) {
            MovementPredictor.method_1907(mc.thePlayer, VecUtil.method_1941(MovementPredictor.method_1904(mc.thePlayer), 0.5, 1.0, 0.5));
        }
    }

    @Override
    public void method_62() {
        this.intValue2 = 0;
    }

    private StrafeSettings() {
        super("Strafe", false);
        this.visibleWhen(Setting.booleanSupplier);
    }
}
