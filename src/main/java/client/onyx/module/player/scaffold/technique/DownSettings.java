package client.onyx.module.player.scaffold.technique;

import client.onyx.event.impl.MoveEvent;
import client.onyx.event.impl.MovementInputEvent;
import client.onyx.setting.Setting;
import client.onyx.setting.SettingSection;
import client.onyx.util.IMinecraft;
import client.onyx.util.player.BlockPlacer;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.BlockPos;

public class DownSettings extends SettingSection implements IMinecraft {
    public static final DownSettings downSettings = new DownSettings();

    private DownSettings() {
        super("Down", false);
        this.visibleWhen(Setting.booleanSupplier);
    }

    public static boolean method_1769() {
        return mc.thePlayer != null && method_1770() && BlockPlacer.method_27(new BlockPos(mc.thePlayer).add(0, -2, 0));
    }

    public static boolean method_1770() {
        return downSettings.method_61() && mc.gameSettings.keyBindSneak.isKeyDown();
    }

    @EventHandler(
        priority = -100
    )
    private void onMove(MoveEvent var1) {
        if (method_1769()) {
            var1.method_2616(false);
        }
    }

    @EventHandler(
        priority = -100
    )
    private void onMovementInput(MovementInputEvent var1) {
        if (method_1769()) {
            var1.method_2620(false);
        }
    }
}
