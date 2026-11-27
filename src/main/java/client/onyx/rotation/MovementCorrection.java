package client.onyx.rotation;

import client.onyx.setting.NamedEnum;

public enum MovementCorrection implements NamedEnum {
    OFF("Off"),
    STRICT("Strict"),
    SILENT("Silent"),
    CHANGE_LOOK("Change look");

    private final String string;

    @Override
    public String getDisplayName() {
        return this.string;
    }

    private MovementCorrection(String var3) {
        this.string = var3;
    }
}
