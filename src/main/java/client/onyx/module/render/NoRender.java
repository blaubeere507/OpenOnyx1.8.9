package client.onyx.module.render;

import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.setting.impl.BooleanSetting;

public final class NoRender extends Module {
    public final BooleanSetting fire = new BooleanSetting("Fire", true);
    public final BooleanSetting hurtCamera = new BooleanSetting("Hurt camera", true);
    public final BooleanSetting handBob = new BooleanSetting("Hand bob", false);
    public final BooleanSetting bossBar = new BooleanSetting("Boss bar", false);
    public final BooleanSetting badEffects = new BooleanSetting("Bad effects", true);
    public final BooleanSetting scoreboard = new BooleanSetting("Scoreboard", false);

    public boolean method_1156(BooleanSetting var1) {
        return this.isToggled() && var1.method_146();
    }

    public NoRender() {
        super("NoRender", "Hides selected vanilla visual effects", Category.RENDER);
    }
}
