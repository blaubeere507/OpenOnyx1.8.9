package client.onyx.module.hud.targethud;

import client.onyx.setting.NamedEnum;

public enum HealthReadout implements NamedEnum {
    NUMBER("Points"),
    HEARTS("Hearts"),
    ICON("Hearts + icon");

    private final String string;

    @Override
    public String getDisplayName() {
        return this.string;
    }

    public boolean method_1502() {
        return this != NUMBER;
    }

    private HealthReadout(String var3) {
        this.string = var3;
    }

    public boolean method_1503() {
        return this == ICON;
    }
}
