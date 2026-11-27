package client.onyx.module.render;

import client.onyx.event.impl.TickEvent;
import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.setting.impl.NumberSetting;
import meteordevelopment.orbit.EventHandler;

public class Fullbright extends Module {
    public NumberSetting gamma = new NumberSetting("Gamma", 100.0, 1.0, 100.0, 1.0).describe("Gamma to apply (vanilla max brightness is 1)");
    private float floatValue;

    @Override
    public void onEnable() {
        this.floatValue = mc.gameSettings.gammaSetting;
        this.method_1155();
    }

    @Override
    public void onDisable() {
        mc.gameSettings.gammaSetting = this.floatValue;
    }

    @EventHandler
    private void onTick(TickEvent var1) {
        this.method_1155();
    }

    private void method_1155() {
        mc.gameSettings.gammaSetting = this.gamma.method_151();
    }

    public Fullbright() {
        super("Fullbright", "Makes the world brighter", Category.RENDER);
    }
}
