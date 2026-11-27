package client.onyx.module.hud.arraylist;

import client.onyx.setting.NamedEnum;

public enum RowAnimation implements NamedEnum {
    SLIDE("Slide"),
    FADE("Fade"),
    SLIDE_FADE("Slide + Fade"),
    SCALE("Scale");

    private final String string;

    public boolean method_1476() {
        return this == SCALE;
    }

    private RowAnimation(String var3) {
        this.string = var3;
    }

    public boolean method_1477() {
        return this != SLIDE;
    }

    @Override
    public String getDisplayName() {
        return this.string;
    }

    public boolean method_1478() {
        return this == SLIDE || this == SLIDE_FADE;
    }
}
