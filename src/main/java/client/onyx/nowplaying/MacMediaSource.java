package client.onyx.nowplaying;

import client.onyx.Onyx;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public final class MacMediaSource implements MediaSource {
    private static final String STRING_CONST = "on readSpotify()\n    if application \"Spotify\" is running then\n        tell application \"Spotify\"\n            if player state is playing or player state is paused then\n                set trackName to name of current track\n                set trackArtist to artist of current track\n                set isPlaying to (player state is playing)\n                set pos to player position\n                set dur to duration of current track\n                set artUrl to artwork url of current track\n                return trackName & \"@@@\" & trackArtist & \"@@@\" & isPlaying & \"@@@\" & pos & \"@@@\" & dur & \"@@@\" & artUrl\n            end if\n        end tell\n    end if\n    return \"\"\nend readSpotify\n\non readMusic()\n    if application \"Music\" is running then\n        tell application \"Music\"\n            if player state is playing or player state is paused then\n                set trackName to name of current track\n                set trackArtist to artist of current track\n                set isPlaying to (player state is playing)\n                set pos to player position\n                set dur to duration of current track\n                -- Music.app's artwork is embedded binary data, not a URL — no cover here.\n                return trackName & \"@@@\" & trackArtist & \"@@@\" & isPlaying & \"@@@\" & pos & \"@@@\" & dur & \"@@@\"\n            end if\n        end tell\n    end if\n    return \"\"\nend readMusic\n\nset nowPlayingResult to readSpotify()\nif nowPlayingResult is \"\" then set nowPlayingResult to readMusic()\nreturn nowPlayingResult\n";
    private static final String STRING_CONST2 = "@@@";
    private static final long LONG_61 = 6L;
    private boolean flag;
    private Path path;

    private void method_288(String var1) {
        if (!this.flag) {
            this.flag = true;
            Onyx.LOGGER.warn("Now-playing (macOS): {}", var1);
        }
    }

    private void method_289() throws IOException {
        if (this.path == null || !Files.isRegularFile(this.path)) {
            this.path = Files.createTempFile("onyx-nowplaying", ".applescript");
            this.path.toFile().deleteOnExit();
            Files.writeString(
                this.path,
                "on readSpotify()\n    if application \"Spotify\" is running then\n        tell application \"Spotify\"\n            if player state is playing or player state is paused then\n                set trackName to name of current track\n                set trackArtist to artist of current track\n                set isPlaying to (player state is playing)\n                set pos to player position\n                set dur to duration of current track\n                set artUrl to artwork url of current track\n                return trackName & \"@@@\" & trackArtist & \"@@@\" & isPlaying & \"@@@\" & pos & \"@@@\" & dur & \"@@@\" & artUrl\n            end if\n        end tell\n    end if\n    return \"\"\nend readSpotify\n\non readMusic()\n    if application \"Music\" is running then\n        tell application \"Music\"\n            if player state is playing or player state is paused then\n                set trackName to name of current track\n                set trackArtist to artist of current track\n                set isPlaying to (player state is playing)\n                set pos to player position\n                set dur to duration of current track\n                -- Music.app's artwork is embedded binary data, not a URL — no cover here.\n                return trackName & \"@@@\" & trackArtist & \"@@@\" & isPlaying & \"@@@\" & pos & \"@@@\" & dur & \"@@@\"\n            end if\n        end tell\n    end if\n    return \"\"\nend readMusic\n\nset nowPlayingResult to readSpotify()\nif nowPlayingResult is \"\" then set nowPlayingResult to readMusic()\nreturn nowPlayingResult\n",
                StandardCharsets.UTF_8
            );
        }
    }

    private static MediaSource.Snapshot method_290(String var0) {
        if (var0.isEmpty()) {
            return null;
        } else {
            String[] var1;
            if ((var1 = var0.split("@@@", -1)).length < 5) {
                return null;
            } else {
                String var5;
                if ((var5 = var1[0].trim()).isEmpty()) {
                    return null;
                } else {
                    String var3 = var1[1].trim();
                    boolean var4 = Boolean.parseBoolean(var1[2].trim());
                    String var12;
                    if (var1.length > 5) {
                        var12 = var1[5].trim();
                    } else {
                        var12 = "";
                    }

                    var0 = var12;
                    if (var12.isEmpty()) {
                        var0 = null;
                    }

                    try {
                        double var6 = Double.parseDouble(method_291(var1[3]));
                        double var8;
                        double var13;
                        if ((var8 = Double.parseDouble(method_291(var1[4]))) > 1000.0) {
                            var13 = var8 / 1000.0;
                        } else {
                            var13 = var8;
                        }

                        return new MediaSource.Snapshot(var5, var3, var4, (int)Math.round(var6 * 1000.0), (int)Math.round(var13 * 1000.0), var0, null);
                    } catch (NumberFormatException var15) {
                        return new MediaSource.Snapshot(var5, var3, var4, 0, 0, var0, null);
                    }
                }
            }
        }
    }

    private static String method_291(String var0) {
        return var0.trim().replace(',', '.');
    }

    @Override
    public MediaSource.Snapshot method_270() {
        var var2 = this;

        try {
            var2.method_289();
            Process var11 = new ProcessBuilder("osascript", var2.path.toString()).start();
            String var4 = new String(var11.getInputStream().readAllBytes(), StandardCharsets.UTF_8).strip();
            String var3 = new String(var11.getErrorStream().readAllBytes(), StandardCharsets.UTF_8).strip();
            if (!var11.waitFor(6L, TimeUnit.SECONDS)) {
                var11.destroyForcibly();
                var2.method_288("osascript timed out after 6s");
                return null;
            } else if (var11.exitValue() != 0) {
                StringBuilder var10001 = new StringBuilder().append("osascript exited ").append(var11.exitValue()).append(": ");
                MacMediaSource var7;
                StringBuilder var8;
                String var9;
                if (!var3.isEmpty()) {
                    StringBuilder var6 = var10001;
                    var9 = var3;
                    var8 = var6;
                    var7 = var2;
                } else {
                    var9 = "(no stderr)";
                    var8 = var10001;
                    var7 = var2;
                }

                var7.method_288(var8.append(var9).append(". Script left at: ").append(var2.path).toString());
                return null;
            } else {
                var2.flag = false;
                return method_290(var4);
            }
        } catch (InterruptedException | IOException var10) {
            this.method_288(new StringBuilder().insert(0, "Failed to run osascript: ").append(var10.getMessage()).toString());
            return null;
        }
    }
}
