package client.onyx.module.hud.targethud;

import client.onyx.setting.NamedEnum;

public enum TargetHudElement implements NamedEnum {
    AVATAR("Avatar", TargetHudElement.Kind.AVATAR),
    NAME("Name", TargetHudElement.Kind.TEXT),
    HEALTH("Health", TargetHudElement.Kind.HEALTH_TEXT),
    DISTANCE("Distance", TargetHudElement.Kind.TEXT),
    STATUS("Status", TargetHudElement.Kind.TEXT),
    HEALTH_BAR("Health bar", TargetHudElement.Kind.BAR);

    private final String string;
    private final TargetHudElement.Kind kind;

    public boolean method_1512() {
        return this.kind == TargetHudElement.Kind.BAR;
    }

    public TargetHudElement.Kind getKind() {
        return this.kind;
    }

    public boolean method_1513() {
        return this.kind == TargetHudElement.Kind.TEXT || this.kind == TargetHudElement.Kind.HEALTH_TEXT;
    }

    public boolean method_1514() {
        return this.kind == TargetHudElement.Kind.HEALTH_TEXT;
    }

    @Override
    public String getDisplayName() {
        return this.string;
    }

    private TargetHudElement(String var3, TargetHudElement.Kind var4) {
        this.string = var3;
        this.kind = var4;
    }

    public static enum Kind {
        AVATAR,
        TEXT,
        HEALTH_TEXT,
        BAR;
    }
}
