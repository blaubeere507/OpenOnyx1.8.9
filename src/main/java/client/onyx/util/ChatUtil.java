package client.onyx.util;

import client.onyx.command.Command;
import client.onyx.module.hud.notification.NotificationType;
import client.onyx.module.hud.notification.Notifications;

public final class ChatUtil implements IMinecraft {
    public static void method_2112(String var0, String var1, NotificationType var2) {
        mc.addScheduledTask(() -> {
            Notifications.method_1349(var0, var1, var2);
            if (var2 != NotificationType.ERROR && var2 != NotificationType.WARNING) {
                Command.method_350(new StringBuilder().insert(0, var0).append(": ").append(var1).toString());
            } else {
                Command.method_348(new StringBuilder().insert(0, var0).append(": ").append(var1).toString());
            }
        });
    }

    private ChatUtil() {
    }
}
