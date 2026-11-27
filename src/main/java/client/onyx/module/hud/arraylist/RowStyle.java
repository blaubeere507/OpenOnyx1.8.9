package client.onyx.module.hud.arraylist;

import client.onyx.setting.NamedEnum;

public enum RowStyle implements NamedEnum {
    ACCENT_BAR("Accent Bar"),
    PILLS("Pills"),
    TEXT("Text");

    private final String string;

    private RowStyle(String var3) {
        this.string = var3;
    }

    @Override
    public String getDisplayName() {
        return this.string;
    }
}
