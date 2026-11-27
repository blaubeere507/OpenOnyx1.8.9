package client.onyx.module.movement.safewalk;

import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.setting.Mode;
import client.onyx.setting.ModeSetting;
import client.onyx.setting.NoneMode;

public class SafeWalk extends Module {
    public ModeSetting<Mode> modeSetting = method_1515("Mode");

    public static ModeSetting<Mode> method_1515(String var0) {
        NoneMode var1 = new NoneMode();
        SafeSafeWalkMode var4 = new SafeSafeWalkMode();
        SneakSafeWalkMode var3 = new SneakSafeWalkMode();
        return new ModeSetting<>(var0, var4, var1, var4, var3);
    }

    public SafeWalk() {
        super("SafeWalk", "Prevents you from falling down as if you were sneaking", Category.MOVEMENT);
    }
}
