package client.onyx.module.hud;

import client.onyx.Onyx;
import client.onyx.discord.DiscordIpc;
import client.onyx.event.impl.TickEvent;
import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.setting.impl.BooleanSetting;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.multiplayer.ServerData;

public class DiscordRPC extends Module {
    private static final String STRING_CONST = "https://discord.gg/onyxmcclient";
    private static final String STRING_CONST2 = "1543222663961382952";
    private static final int INT_51 = 5;
    private static final String STRING_CONST3 = "onyx";
    public BooleanSetting showServer = new BooleanSetting("Show Server", true).describe("Reveal the current server IP in the status; off keeps it private");
    public BooleanSetting elapsedTime = new BooleanSetting("Elapsed Time", true).describe("Show how long the client has been running");
    private volatile String string = "In the Main Menu";
    private long longValue;
    private ScheduledExecutorService scheduledExecutorService;
    private DiscordIpc discordIpc;
    private String string2;

    @Override
    public void onDisable() {
        ScheduledExecutorService var3 = this.scheduledExecutorService;
        DiscordIpc var2 = this.discordIpc;
        this.scheduledExecutorService = null;
        this.discordIpc = null;
        if (var3 != null) {
            var3.execute(() -> {
                if (var2 != null) {
                    var2.method_2083();
                }
            });
            var3.shutdown();
        }
    }

    @Override
    public void onEnable() {
        this.longValue = System.currentTimeMillis() / 1000L;
        this.string2 = null;
        this.discordIpc = new DiscordIpc();
        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(var0 -> {
            Thread var10000 = new Thread(var0, "Onyx-DiscordRPC");
            var10000.setDaemon(true);
            return var10000;
        });
        this.scheduledExecutorService.scheduleWithFixedDelay(() -> {
            var var2 = this;
            DiscordIpc var1 = this.discordIpc;
            if (this.discordIpc != null) {
                try {
                    if (!var1.isFlag()) {
                        if (!var1.method_2085("1543222663961382952")) {
                            return;
                        }

                        var2.string2 = null;
                        Onyx.LOGGER.info("Discord RPC: connected to Discord");
                    }

                    String var5;
                    if (!(var5 = var2.method_1358()).equals(var2.string2)) {
                        var1.method_2086(1, method_1359(var5));
                        var2.string2 = var5;
                        Onyx.LOGGER.info("Discord RPC: pushed activity {}", var5);
                    }
                } catch (IOException var3) {
                    var1.method_2083();
                    this.string2 = null;
                    Onyx.LOGGER.warn("Discord RPC update failed; will reconnect", var3);
                } catch (Throwable var4) {
                    Onyx.LOGGER.warn("Discord RPC tick failed", var4);
                }
            }
        }, 0L, 5L, TimeUnit.SECONDS);
    }

    private String method_1357() {
        if (mc.theWorld == null) {
            return "In the Main Menu";
        } else if (mc.isSingleplayer()) {
            return "Playing Singleplayer";
        } else {
            ServerData var2;
            return (var2 = mc.getCurrentServerData()) != null && this.showServer.method_146()
                ? new StringBuilder().insert(0, "Playing on ").append(var2.serverIP).toString()
                : "Playing Multiplayer";
        }
    }

    private String method_1358() {
        StringBuilder var2;
        (var2 = new StringBuilder("{")).append("\"details\":\"").append(method_1360(this.string)).append("\",");
        var2.append("\"state\":\"").append(method_1360(new StringBuilder().insert(0, "Username: ").append(Onyx.getUser()).toString())).append("\",");
        if (this.elapsedTime.method_146()) {
            var2.append("\"timestamps\":{\"start\":").append(this.longValue).append("},");
        }

        var2.append("\"assets\":{").append("\"large_image\":\"").append(method_1360("onyx")).append("\",").append("\"large_text\":\"Onyx Client\"},");
        var2.append("\"buttons\":[{\"label\":\"Join our Discord\",\"url\":\"").append(method_1360("https://discord.gg/onyxmcclient")).append("\"}]");
        var2.append("}");
        return var2.toString();
    }

    public DiscordRPC() {
        super("DiscordRPC", "Shows Onyx as your Discord status", Category.HUD);
        this.setEnabled(true);
    }

    @EventHandler
    private void onTick(TickEvent var1) {
        this.string = this.method_1357();
    }

    private static String method_1359(String var0) {
        return new StringBuilder()
            .insert(0, "{\"cmd\":\"SET_ACTIVITY\",\"nonce\":\"")
            .append(UUID.randomUUID())
            .append("\",\"args\":{\"pid\":")
            .append(ProcessHandle.current().pid())
            .append(",\"activity\":")
            .append(var0)
            .append("}}")
            .toString();
    }

    private static String method_1360(String var0) {
        String var1 = var0;
        StringBuilder var5 = new StringBuilder(var0.length() + 8);
        int var2 = 0;

        for (int var4 = 0; var4 < var1.length(); var4 = ++var2) {
            char var3;
            switch (var3 = var1.charAt(var2)) {
                case '\t':
                    var5.append("\\t");
                    break;
                case '\n':
                    var5.append("\\n");
                    break;
                case '\r':
                    var5.append("\\r");
                    break;
                case '"':
                    var5.append("\\\"");
                    break;
                case '\\':
                    var5.append("\\\\");
                    break;
                default:
                    if (var3 < ' ') {
                        var5.append(String.format("\\u%04x", Integer.valueOf(var3)));
                    } else {
                        var5.append(var3);
                    }
            }
        }

        return var5.toString();
    }
}
