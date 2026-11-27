package client.onyx.module.player.scaffold.technique;

import client.onyx.event.impl.MovementInputEvent;
import client.onyx.module.player.scaffold.Scaffold;
import client.onyx.setting.SettingSection;
import client.onyx.util.IMinecraft;
import client.onyx.util.geometry.Ray;
import client.onyx.util.math.VecUtil;
import client.onyx.util.player.MovementInputState;
import client.onyx.util.player.MovementPredictor;
import client.onyx.util.world.CollisionUtil;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.Vec3;

public class StabilizeSettings extends SettingSection implements IMinecraft {
    public static final StabilizeSettings stabilizeSettings = new StabilizeSettings();
    private static final double DOUBLE_0_0751 = 0.075;
    private static final double DOUBLE_0_21 = 0.2;

    private StabilizeSettings() {
        super("Stabilize movement", true);
    }

    @EventHandler(
        priority = -10
    )
    private void onMovementInput(MovementInputEvent var1) {
        if (mc.thePlayer != null) {
            if (!var1.isFlag2() || !mc.thePlayer.onGround) {
                Ray var14;
                if ((var14 = Scaffold.INSTANCE.getRay()) != null) {
                    MovementInputState var3 = var1.getMovementInputState();
                    Vec3 var4 = MovementPredictor.method_1899(mc.thePlayer);
                    Vec3 var15;
                    Vec3 var5 = (var15 = var14.method_2044(var4)).subtract(var4);
                    Vec3 var6 = VecUtil.method_1942(MovementPredictor.method_1904(mc.thePlayer), 0.0);
                    boolean var9;
                    if (var5.dotProduct(var6) > 0.0) {
                        var9 = true;
                    } else {
                        var9 = false;
                    }

                    double var10;
                    if (var9) {
                        var10 = 0.075;
                    } else {
                        var10 = 0.2;
                    }

                    if (!(var15.distanceToSqr(var4) < var10 * var10)) {
                        float var16 = CollisionUtil.method_1926(var15.subtract(var4), mc.thePlayer.rotationYaw);
                        MovementInputState var17 = CollisionUtil.method_1923(MovementInputState.INSTANCE8, var16, 0.0F);
                        boolean var12;
                        if (!var3.flag2() && !var3.flag4()) {
                            var12 = false;
                        } else {
                            var12 = true;
                        }

                        boolean var13;
                        if (!var3.flag3() && !var3.flag()) {
                            var13 = false;
                        } else {
                            var13 = true;
                        }

                        MovementInputState var10001;
                        boolean var10003;
                        boolean var10004;
                        if (var12) {
                            var10003 = var3.flag2();
                            var10004 = var12;
                        } else {
                            var10003 = var17.flag2();
                            var10004 = var12;
                        }

                        boolean var10005;
                        if (var10004) {
                            var10004 = var3.flag4();
                            var10005 = var13;
                        } else {
                            var10004 = var17.flag4();
                            var10005 = var13;
                        }

                        boolean var10006;
                        if (var10005) {
                            var10005 = var3.flag();
                            var10006 = var13;
                        } else {
                            var10005 = var17.flag();
                            var10006 = var13;
                        }

                        var10001 = new MovementInputState(var10003, var10004, var10005, var10006 ? var3.flag3() : var17.flag3());
                        var1.method_2621(var10001);
                    }
                }
            }
        }
    }
}
