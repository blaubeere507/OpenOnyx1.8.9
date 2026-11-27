package client.onyx.module.hud.notification;

import client.onyx.setting.NamedEnum;
import client.onyx.ui.theme.Palette;

public enum NotificationType implements NamedEnum {
    INFO("Info"),
    SUCCESS("Success"),
    WARNING("Warning"),
    ERROR("Error");

    private final String string;

    private NotificationType(String var3) {
        this.string = var3;
    }

    @Override
    public String getDisplayName() {
        return this.string;
    }

    public String method_1426() {
        switch (this) {
            case INFO:
                return "\ue88e";
            case SUCCESS:
                return "\ue5ca";
            case WARNING:
                return "\ue002";
            case ERROR:
                return "\ue001";
            default:
                throw new MatchException(null, null);
        }
    }

    public int method_1427(Palette var1) {
        switch (this) {
            case INFO:
                return var1.intValue31();
            case SUCCESS:
                return var1.intValue27();
            case WARNING:
                return var1.intValue22();
            case ERROR:
                return var1.intValue13();
            default:
                throw new MatchException(null, null);
        }
    }
}
