package client.onyx.module.movement.safewalk;

import client.onyx.event.impl.MovementInputEvent;
import client.onyx.setting.Mode;
import client.onyx.setting.NamedEnum;
import client.onyx.setting.RangeSampler;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.EnumSetting;
import client.onyx.setting.impl.RangeSetting;
import client.onyx.simulation.SimulatedTick;
import client.onyx.simulation.SimulationManager;
import client.onyx.util.IMinecraft;
import client.onyx.util.math.VecUtil;
import client.onyx.util.player.MovementInputState;
import client.onyx.util.player.MovementPredictor;
import client.onyx.util.world.CollisionUtil;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

public class SneakSafeWalkMode extends Mode implements IMinecraft {
    public RangeSetting rangeSetting = RangeSetting.method_104("Distance", 0.1, 0.15, 0.05, 0.5);
    public RangeSetting rangeSetting2 = RangeSetting.method_90("Keep", 1, 2, 1, 20).method_94(" ticks");
    public EnumSetting<SneakSafeWalkMode.EdgeAction> mode = new EnumSetting<>("Mode", SneakSafeWalkMode.EdgeAction.STOP);
    public RangeSetting rangeSetting3 = RangeSetting.method_90("Sneak", 0, 0, 0, 20).method_94(" ticks");
    public BooleanSetting jump = new BooleanSetting("Jump", false);
    private final transient RangeSampler rangeSampler = new RangeSampler(this.rangeSetting);
    private transient int intValue2;
    private transient int intValue22;
    private transient Vec3 vec31;

    public SneakSafeWalkMode() {
        super("On edge");
    }

    @Override
    public void method_64() {
        this.vec31 = null;
        this.intValue2 = 0;
        this.rangeSampler.method_53();
    }

    @EventHandler(
        priority = -100
    )
    private void onMovementInput(MovementInputEvent var1) {
        if (mc.thePlayer != null) {
            boolean var6;
            if (mc.thePlayer.onGround && !var1.isFlag()) {
                var6 = true;
            } else {
                var6 = false;
            }

            if (var6
                && MovementPredictor.method_1906(
                    mc.thePlayer, var1.getMovementInputState(), Math.min(MovementPredictor.method_1915(mc.thePlayer), this.rangeSampler.getDoubleValue())
                )) {
                Vec3 var3 = this.vec31;
                if (this.vec31 != null) {
                    SimulatedTick var10001 = SimulationManager.INSTANCE.method_343().method_294(1);
                    double var4 = var3.subtract(MovementPredictor.method_1899(mc.thePlayer)).horizontalDistanceSqr();
                    if (var3.subtract(var10001.vec31()).horizontalDistanceSqr() <= var4) {
                        return;
                    }
                }

                if (this.intValue2 == 0) {
                    this.rangeSampler.method_53();
                    this.intValue2 = this.rangeSetting2.method_95();
                }

                if (this.intValue22 == 0) {
                    this.intValue22 = this.rangeSetting3.method_95();
                }
            }

            if (this.intValue2 > 0) {
                this.intValue2--;
                SneakSafeWalkMode var7;
                if (this.mode.method_168(SneakSafeWalkMode.EdgeAction.INVERT)) {
                    var1.method_2621(var1.getMovementInputState().method_1918());
                    var1.method_2622(false);
                    var7 = this;
                } else if (!this.mode.method_168(SneakSafeWalkMode.EdgeAction.CENTER) && !(MovementPredictor.method_1915(mc.thePlayer) > 0.05)) {
                    if (this.mode.method_168(SneakSafeWalkMode.EdgeAction.STOP)) {
                        var1.method_2621(MovementInputState.INSTANCE8);
                        var1.method_2622(false);
                    }

                    var7 = this;
                } else {
                    Vec3 var8;
                    if (this.vec31 != null) {
                        var8 = this.vec31;
                    } else {
                        var8 = VecUtil.method_1953(new BlockPos(mc.thePlayer));
                    }

                    float var10 = CollisionUtil.method_1926(var8.subtract(MovementPredictor.method_1899(mc.thePlayer)), mc.thePlayer.rotationYaw);
                    var1.method_2621(CollisionUtil.method_1923(MovementInputState.INSTANCE8, var10, 20.0F));
                    var7 = this;
                }

                if (var7.jump.getValue()) {
                    var1.method_2622(true);
                }
            }

            if (this.intValue22 > 0) {
                this.intValue22--;
                var1.method_2620(true);
            }

            Vec3 var9 = VecUtil.method_1953(new BlockPos(mc.thePlayer));
            if (!MovementPredictor.method_1893(mc.thePlayer, var9)) {
                this.vec31 = var9;
            }
        }
    }

    public static enum EdgeAction implements NamedEnum {
        STOP("Stop"),
        INVERT("Invert"),
        CENTER("Center");

        private final String string;

        @Override
        public String getDisplayName() {
            return this.string;
        }

        public String getString() {
            return this.string;
        }

        private EdgeAction(String var3) {
            this.string = var3;
        }
    }
}
