package client.onyx.module.render;

import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.ColorSetting;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.ui.screen.ClickGuiScreen;
import client.onyx.ui.theme.Theme;

public class ClickGUI extends Module {
    public ColorSetting accent = new ColorSetting("Accent", -10006364)
        .method_117()
        .describe("Seed colour the whole theme is generated from")
        .onChange(Theme::method_178);
    public NumberSetting backgroundTint = new NumberSetting("Background tint", 25.0, 0.0, 100.0, 5.0)
        .method_147("%")
        .describe("How much the accent colours the panel background")
        .onChange(var0 -> Theme.method_174(var0.floatValue() / 100.0F));
    public NumberSetting scale = new NumberSetting("Scale", 1.0, 0.5, 1.5, 0.05)
        .method_147("x")
        .describe("Interface size")
        .onChange(var0 -> Theme.method_175(var0.floatValue()));
    public BooleanSetting frostedGlass = new BooleanSetting("Frosted glass", true).describe("Blur the world behind the panel instead of the whole screen");

    @Override
    public boolean isListed() {
        return false;
    }

    @Override
    public void onEnable() {
        if (!(mc.currentScreen instanceof ClickGuiScreen)) {
            mc.displayGuiScreen(new ClickGuiScreen(this, mc.currentScreen));
        }
    }

    @Override
    public boolean hasBindMode() {
        return false;
    }

    public ClickGUI() {
        super("ClickGUI", "Opens click GUI", Category.RENDER);
        this.getBind().setValue(54);
    }
}
