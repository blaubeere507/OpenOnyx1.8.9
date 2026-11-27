package client.onyx.module.movement;

import client.onyx.event.impl.TickEvent;
import client.onyx.module.Category;
import client.onyx.module.Module;
import meteordevelopment.orbit.EventHandler;

public class AutoSprint extends Module {
    public AutoSprint() {
        super("AutoSprint", "Automatically sprints for you", Category.MOVEMENT);
    }

    @Override
    public void onDisable() {
        mc.gameSettings.keyBindSprint.setPressed(false);
        if (mc.thePlayer != null) {
            mc.thePlayer.setSprinting(false);
        }
    }

    @EventHandler
    public void onTick(TickEvent var1) {
        mc.gameSettings.keyBindSprint.setPressed(true);
    }
}
