package client.onyx.module.movement.safewalk;

import client.onyx.event.impl.MoveEvent;
import client.onyx.setting.Mode;
import meteordevelopment.orbit.EventHandler;

public class SafeSafeWalkMode extends Mode {
    @EventHandler
    private void onMove(MoveEvent var1) {
        var1.method_2616(true);
    }

    public SafeSafeWalkMode() {
        super("Safe");
    }
}
