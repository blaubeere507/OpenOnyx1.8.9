package client.onyx.module.player.scaffold.sprint;

import client.onyx.event.impl.PostPlayerUpdateEvent;
import client.onyx.setting.Mode;
import client.onyx.util.IMinecraft;
import client.onyx.util.player.MovementPredictor;
import meteordevelopment.orbit.EventHandler;

public class WatchdogSprintMode extends Mode implements IMinecraft {
    public static final WatchdogSprintMode watchdogSprintMode = new WatchdogSprintMode();
    private static final float FLOAT_0_81 = 0.8F;

    private WatchdogSprintMode() {
        super("Watchdog");
    }

    @EventHandler
    private void onPostPlayerUpdate(PostPlayerUpdateEvent var1) {
        if (mc.thePlayer != null) {
            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                if (mc.thePlayer.onGround && !method_1718()) {
                    mc.thePlayer.jump();
                }

                if (MovementPredictor.method_1915(mc.thePlayer) == 0.0) {
                    switch (MovementPredictor.method_1908(mc.thePlayer)) {
                        case 4:
                            mc.thePlayer.motionY -= 0.03;
                            return;
                        case 5:
                            mc.thePlayer.motionY -= 0.5;
                            return;
                    }
                }
            }
        }
    }

    private static boolean method_1718() {
        return Math.abs(mc.thePlayer.moveForward) >= 0.8F || Math.abs(mc.thePlayer.moveStrafing) >= 0.8F;
    }
}
