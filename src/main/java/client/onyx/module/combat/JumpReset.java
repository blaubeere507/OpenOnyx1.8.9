package client.onyx.module.combat;

import client.onyx.Onyx;
import client.onyx.event.impl.MovementInputEvent;
import client.onyx.event.impl.PostPlayerUpdateEvent;
import client.onyx.event.impl.TickEvent;
import client.onyx.event.impl.VelocityEvent;
import client.onyx.event.impl.WorldLoadEvent;
import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.module.player.scaffold.Scaffold;
import client.onyx.rotation.MoveFix;
import client.onyx.rotation.MoveFixMode;
import client.onyx.rotation.YawPitch;
import client.onyx.setting.Setting;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.EnumSetSetting;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.util.Timer;
import client.onyx.util.player.MovementInputState;
import java.util.concurrent.ThreadLocalRandom;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;

public class JumpReset extends Module {
    private static final long LONG_5501 = 550L;
    private static final int INT_31 = 3;
    public NumberSetting chance = new NumberSetting("Chance", 100.0, 0.0, 100.0, 1.0).method_147("%");
    public NumberSetting fov = new NumberSetting("FOV", 180.0, 0.0, 360.0, 1.0).method_147("°").visibleWhen(Setting.booleanSupplier);
    public NumberSetting range = new NumberSetting("Range", 5.0, 1.0, 8.0, 0.1);
    public BooleanSetting requireMoving = new BooleanSetting("Require moving", true);
    public BooleanSetting reduce = new BooleanSetting("Reduce", false)
        .describe("Silently faces the knockback source so the forced input runs into it")
        .visibleWhen(Setting.booleanSupplier);
    public EnumSetSetting<TargetType> targets = new EnumSetSetting<>("Targets", TargetType.class, TargetType.PLAYERS);
    private final Timer timer = new Timer();
    private boolean flag;
    private boolean flag2;
    private int intValue;
    private volatile boolean flag3;

    public boolean method_1571() {
        return mc.thePlayer != null
            && (!this.requireMoving.method_146() || new MovementInputState(mc.thePlayer.movementInput).method_1919())
            && !(ThreadLocalRandom.current().nextDouble(100.0) >= this.chance.getValue())
            && !mc.thePlayer.isPotionActive(Potion.jump)
            && this.flag3
            && mc.thePlayer.isSprinting();
    }

    @EventHandler
    private void onPostPlayerUpdate(PostPlayerUpdateEvent var1) {
        if (this.intValue > 0) {
            this.intValue--;
        } else {
            if (this.flag && this.timer.method_2019(550L)) {
                MoveFix.method_2147();
                this.flag = false;
            }
        }
    }

    private void method_1572() {
        this.flag2 = false;
        this.intValue = 0;
        if (this.flag) {
            MoveFix.method_2147();
            this.flag = false;
        }
    }

    @EventHandler
    private void onWorldLoad(WorldLoadEvent var1) {
        this.method_1572();
    }

    public JumpReset() {
        super("JumpReset", "Jump resets and reduces knockback in combat", Category.COMBAT);
    }

    @EventHandler
    private void onMovementInput(MovementInputEvent var1) {
        if (this.flag2) {
            this.flag2 = false;
            MovementInputState var3 = var1.getMovementInputState();
            var1.method_2621(new MovementInputState(true, false, var3.flag(), var3.flag3()));
            var1.method_2622(true);
        }
    }

    public static boolean method_1573() {
        JumpReset var1;
        if (Onyx.moduleManager == null) {
            var1 = null;
        } else {
            var1 = Onyx.moduleManager.jumpReset;
        }

        return var1 != null && var1.isToggled() && var1.method_1571();
    }

    @EventHandler
    private void onTick(TickEvent var1) {
        JumpReset var3;
        boolean var4;
        label15: {
            JumpReset var2;
            if (mc.thePlayer == null) {
                var2 = this;
            } else {
                if (TargetType.method_1583(this.range.getValue(), this.fov.method_151(), this.targets.getValue())) {
                    var4 = true;
                    var3 = this;
                    break label15;
                }

                var2 = this;
            }

            var4 = false;
            var3 = var2;
        }

        var3.flag3 = var4;
    }

    @Override
    public void onDisable() {
        this.method_1572();
    }

    @EventHandler
    private void onVelocity(VelocityEvent var1) {
        if (!(var1.getDoubleValue() <= 0.0)) {
            if (this.method_1571()) {
                this.flag2 = true;
                this.intValue = 3;
                if (this.reduce.method_146() && !MoveFix.isFlag4() && !Scaffold.INSTANCE.isToggled()) {
                    float var3 = (float)MathHelper.wrapAngleTo180_double(Math.toDegrees(Math.atan2(-var1.getDoubleValue3(), var1.getDoubleValue2())) - 180.0);
                    MoveFix.method_2150(new YawPitch(var3, mc.thePlayer.rotationPitch), MoveFixMode.SILENT);
                    this.flag = true;
                    this.timer.method_2017();
                }
            }
        }
    }
}
