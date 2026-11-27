package client.onyx.placement;

import client.onyx.setting.NamedEnum;

public enum AimPointMode implements NamedEnum {
    CENTER("Center"),
    RANDOM("Random"),
    STABILIZED("Stabilized"),
    NEAREST_ROTATION("NearestRotation"),
    EDGE_POINT("EdgePoint");

    private final String string;

    @Override
    public String getDisplayName() {
        return this.string;
    }

    private AimPointMode(String var3) {
        this.string = var3;
    }
}
