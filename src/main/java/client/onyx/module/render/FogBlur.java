package client.onyx.module.render;

import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.NumberSetting;

public final class FogBlur extends Module {
    public final NumberSetting distance = new NumberSetting("Distance", 24.0, 4.0, 256.0, 1.0).method_147("m");
    public final NumberSetting fade = new NumberSetting("Fade", 48.0, 4.0, 256.0, 1.0).method_147("m");
    public final BooleanSetting clientColor = new BooleanSetting("Client Color", true);
    public final NumberSetting tint = new NumberSetting("Tint", 0.5, 0.05, 0.95, 0.05).visibleWhen(this.clientColor);
    public final BooleanSetting disableOnZoom = new BooleanSetting("Disable on zoom", true);

    public FogBlur() {
        super("Fog Blur", "Blurs the world based on distance from the camera", Category.RENDER);
    }
}
