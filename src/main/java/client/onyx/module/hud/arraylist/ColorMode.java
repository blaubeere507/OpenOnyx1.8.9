package client.onyx.module.hud.arraylist;

import client.onyx.setting.NamedEnum;

public enum ColorMode implements NamedEnum {
    ACCENT("Accent"),
    STATIC("Static"),
    FADE("Fade"),
    GRADIENT("Gradient"),
    RAINBOW("Rainbow"),
    CATEGORY("Category");

    private final String string;

    @Override
    public String getDisplayName() {
        return this.string;
    }

    public boolean method_1479() {
        return this == FADE || this == GRADIENT;
    }

    private ColorMode(String var3) {
        this.string = var3;
    }

    public boolean method_1480() {
        return this == FADE || this == RAINBOW;
    }
}
