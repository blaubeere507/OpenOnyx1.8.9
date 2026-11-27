package client.onyx.module.player.scaffold.technique;

import client.onyx.event.impl.MovementInputEvent;
import client.onyx.event.impl.PostJumpEvent;
import client.onyx.event.impl.PostTickEvent;
import client.onyx.module.player.scaffold.Scaffold;
import client.onyx.rotation.RotationManager;
import client.onyx.setting.NamedEnum;
import client.onyx.setting.SettingSection;
import client.onyx.setting.impl.EnumSetting;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.setting.impl.RangeSetting;
import client.onyx.util.IMinecraft;
import client.onyx.util.player.MovementPredictor;
import meteordevelopment.orbit.EventHandler;

public class TellySettings extends SettingSection implements IMinecraft {
    public static final TellySettings tellySettings = new TellySettings();
    public EnumSetting<TellySettings.JumpAim> jumpAim = new EnumSetting<>("Jump aim", TellySettings.JumpAim.RESET)
        .describe("What the aim does while the jump goes out: keep the spoofed aim pitched down, or hand it back to your real look");
    public NumberSetting straightTicks = new NumberSetting("Straight ticks", 0.0, 0.0, 5.0, 1.0)
        .method_147(" ticks")
        .describe("Air ticks after the jump during which the aim stays straight ahead");
    public RangeSetting rangeSetting = RangeSetting.method_90("Jump delay", 0, 0, 0, 10).method_94(" ticks").describe("Ground ticks to wait between jumps");
    private transient int intValue22 = this.rangeSetting.method_95();
    private transient int intValue2;

    private TellySettings() {
        super("Telly", false);
    }

    @EventHandler
    private void onPostTick(PostTickEvent var1) {
        if (mc.thePlayer != null) {
            if (mc.thePlayer.onGround) {
                this.intValue2++;
            }
        }
    }

    public boolean method_1765() {
        return MovementPredictor.method_1908(mc.thePlayer) <= this.straightTicks.method_149() && this.intValue2 >= this.intValue22;
    }

    public boolean method_1766() {
        return this.intValue2 >= this.intValue22 && MovementPredictor.method_1896(mc.thePlayer) && this.method_61();
    }

    @EventHandler
    private void onPostJump(PostJumpEvent var1) {
        this.intValue2 = 0;
        this.intValue22 = this.rangeSetting.method_95();
    }

    @Override
    public void method_62() {
        this.intValue2 = 0;
        this.intValue22 = this.rangeSetting.method_95();
    }

    @EventHandler
    private void onMovementInput(MovementInputEvent var1) {
        if (mc.thePlayer != null) {
            if (MovementPredictor.method_1896(mc.thePlayer) && Scaffold.INSTANCE.method_1663() > 0 && mc.thePlayer.onGround) {
                boolean var3;
                if (RotationManager.INSTANCE.getServerRotation() != null && this.straightTicks.method_149() != 0) {
                    var3 = false;
                } else {
                    var3 = true;
                }

                switch ((TellySettings.JumpAim)this.jumpAim.getValue()) {
                    case REVERSE:
                        var1.method_2622(true);
                        return;
                    case RESET:
                        if (var3 && this.intValue2 >= this.intValue22) {
                            var1.method_2622(true);
                        }
                }
            }
        }
    }

    public static enum JumpAim implements NamedEnum {
        REVERSE("Look down"),
        RESET("Free look");

        private final String string;

        @Override
        public String getDisplayName() {
            return this.string;
        }

        private JumpAim(String var3) {
            this.string = var3;
        }
    }
}
