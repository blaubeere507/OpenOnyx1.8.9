package client.onyx.module;

import client.onyx.setting.NamedEnum;

public enum Category implements NamedEnum {
    COMBAT("Combat"),
    MOVEMENT("Movement"),
    PLAYER("Player"),
    RENDER("Render"),
    HUD("HUD");

    private final String string;

    @Override
    public String getDisplayName() {
        return this.string;
    }

    public String getString() {
        return this.string;
    }

    private Category(String var3) {
        this.string = var3;
    }
}
