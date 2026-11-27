package client.onyx.nowplaying;

import client.onyx.Onyx;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

public final class WindowsMediaSource implements MediaSource {
    private static final String STRING_CONST = "$ErrorActionPreference = 'Stop'\n$outPath = '__OUTPUT__'\n\nfunction Write-Result($obj) {\n    [System.IO.File]::WriteAllText($outPath, ($obj | ConvertTo-Json -Compress), [System.Text.Encoding]::UTF8)\n}\n\ntry {\n    Add-Type -AssemblyName System.Runtime.WindowsRuntime\n\n    $asTaskGeneric = ([System.WindowsRuntimeSystemExtensions].GetMethods() | Where-Object {\n        $_.Name -eq 'AsTask' -and $_.GetParameters().Count -eq 1 -and $_.GetParameters()[0].ParameterType.Name -eq 'IAsyncOperation`1'\n    })[0]\n\n    function Await($WinRtTask, $ResultType) {\n        $asTask = $asTaskGeneric.MakeGenericMethod($ResultType)\n        $netTask = $asTask.Invoke($null, @($WinRtTask))\n        $netTask.Wait(-1) | Out-Null\n        $netTask.Result\n    }\n\n    [Windows.Media.Control.GlobalSystemMediaTransportControlsSessionManager,Windows.Media.Control,ContentType=WindowsRuntime] | Out-Null\n\n    $manager = Await ([Windows.Media.Control.GlobalSystemMediaTransportControlsSessionManager]::RequestAsync()) ([Windows.Media.Control.GlobalSystemMediaTransportControlsSessionManager])\n    $session = $manager.GetCurrentSession()\n\n    if ($null -eq $session) {\n        Write-Result ([PSCustomObject]@{ empty = $true })\n        exit 0\n    }\n\n    $props = Await ($session.TryGetMediaPropertiesAsync()) ([Windows.Media.Control.GlobalSystemMediaTransportControlsSessionMediaProperties])\n    $playback = $session.GetPlaybackInfo()\n    $timeline = $session.GetTimelineProperties()\n\n    # SMTC hands the cover over as a stream reference rather than a URL, so it has to be\n    # read out whole and carried back as base64. Its own try/catch: a player that\n    # publishes no thumbnail, or hands back a stream that will not open, must still\n    # leave the title and the progress bar working.\n    #\n    # The stream comes back from the reflection-driven await as a bare COM object with no\n    # usable WinRT projection: its .Size reads as empty (so the old size-gated read never\n    # ran) and DataReader refuses to wrap it. AsStreamForRead projects it to an ordinary\n    # .NET stream, which drains cleanly regardless — that is the only path that works here.\n    $art = $null\n\n    try {\n        $reference = $props.Thumbnail\n\n        if ($null -ne $reference) {\n            $stream = Await ($reference.OpenReadAsync()) ([Windows.Storage.Streams.IRandomAccessStreamWithContentType])\n\n            $asStreamForRead = [System.IO.WindowsRuntimeStreamExtensions].GetMethods() | Where-Object {\n                $_.Name -eq 'AsStreamForRead' -and $_.GetParameters().Count -eq 1\n            } | Select-Object -First 1\n\n            $netStream = $asStreamForRead.Invoke($null, @($stream))\n\n            try {\n                $buffer = New-Object System.IO.MemoryStream\n                $netStream.CopyTo($buffer)\n                $bytes = $buffer.ToArray()\n\n                if ($bytes.Length -gt 0) {\n                    $art = [Convert]::ToBase64String($bytes)\n                }\n\n                $buffer.Dispose()\n            } finally {\n                $netStream.Dispose()\n            }\n        }\n    } catch {\n        $art = $null\n    }\n\n    Write-Result ([PSCustomObject]@{\n        title = $props.Title\n        artist = $props.Artist\n        playing = ($playback.PlaybackStatus.ToString() -eq 'Playing')\n        positionMs = [math]::Round($timeline.Position.TotalMilliseconds)\n        durationMs = [math]::Round(($timeline.EndTime - $timeline.StartTime).TotalMilliseconds)\n        art = $art\n    })\n} catch {\n    Write-Result ([PSCustomObject]@{ error = $_.Exception.Message })\n}\n";
    private static final long LONG_61 = 6L;
    private boolean flag;
    private Path path;
    private boolean flag2;
    private Path path2;

    private byte[] method_271(String var1) {
        var var2 = this;
        if (var1 != null && !var1.isBlank()) {
            try {
                byte[] var4 = Base64.getDecoder().decode(var1);
                if (!var2.flag) {
                    var2.flag = true;
                    Onyx.LOGGER.info("Now-playing (Windows): cover art is {} bytes", var4.length);
                }

                return var4;
            } catch (IllegalArgumentException var3) {
                this.method_274("Malformed thumbnail from PowerShell (" + var1.length() + " chars)");
                return null;
            }
        } else {
            if (!this.flag) {
                this.flag = true;
                Onyx.LOGGER.info("Now-playing (Windows): session published no cover art");
            }

            return null;
        }
    }

    private MediaSource.Snapshot method_272(String var1) {
        JsonElement var2;
        try {
            var2 = new JsonParser().parse(var1);
        } catch (RuntimeException var9) {
            this.method_274("Malformed JSON from PowerShell: " + var1);
            return null;
        }

        if (var2 != null && var2.isJsonObject()) {
            JsonObject var10;
            if ((var10 = var2.getAsJsonObject()).has("error")) {
                this.method_274(new StringBuilder().insert(0, "PowerShell script error: ").append(method_273(var10, "error")).toString());
                return null;
            } else if ((var1 = method_273(var10, "title")) != null && !var1.isBlank()) {
                String var12 = method_273(var10, "artist");
                boolean var8;
                if (var10.has("playing") && var10.get("playing").isJsonPrimitive() && var10.get("playing").getAsBoolean()) {
                    var8 = true;
                } else {
                    var8 = false;
                }

                int var5 = method_276(var10, "positionMs");
                int var6 = method_276(var10, "durationMs");
                MediaSource.Snapshot var10000;
                String var10003;
                boolean var10004;
                if (var12 == null) {
                    var10003 = "";
                    var10004 = var8;
                } else {
                    var10003 = var12;
                    var10004 = var8;
                }

                var10000 = new MediaSource.Snapshot(var1, var10003, var10004, var5, var6, null, this.method_271(method_273(var10, "art")));
                return var10000;
            } else {
                return null;
            }
        } else {
            this.method_274(new StringBuilder().insert(0, "Unexpected output from PowerShell: ").append(var1).toString());
            return null;
        }
    }

    private static String method_273(JsonObject var0, String var1) {
        JsonElement var3;
        return (var3 = var0.get(var1)) != null && var3.isJsonPrimitive() && var3.getAsJsonPrimitive().isString() ? var3.getAsString() : null;
    }

    private void method_274(String var1) {
        if (!this.flag2) {
            this.flag2 = true;
            Onyx.LOGGER.warn("Now-playing (Windows): {}", var1);
        }
    }

    @Override
    public MediaSource.Snapshot method_270() {
        var var2 = this;

        try {
            var2.method_275();
            Files.deleteIfExists(var2.path2);
            Process var10 = new ProcessBuilder("powershell.exe", "-NoProfile", "-NonInteractive", "-ExecutionPolicy", "Bypass", "-File", var2.path.toString())
                .start();
            String var3 = new String(var10.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);
            if (!var10.waitFor(6L, TimeUnit.SECONDS)) {
                var10.destroyForcibly();
                var2.method_274("powershell.exe timed out after 6s");
                return null;
            } else if (!Files.isRegularFile(var2.path2)) {
                StringBuilder var10001 = new StringBuilder().append("powershell.exe produced no output (exit ").append(var10.exitValue()).append("). stderr: ");
                WindowsMediaSource var6;
                StringBuilder var7;
                String var8;
                if (!var3.isBlank()) {
                    StringBuilder var5 = var10001;
                    var8 = var3.strip();
                    var7 = var5;
                    var6 = var2;
                } else {
                    var8 = "(none)";
                    var7 = var10001;
                    var6 = var2;
                }

                var6.method_274(var7.append(var8).append(". Script left at: ").append(var2.path).toString());
                return null;
            } else {
                MediaSource.Snapshot var11 = var2.method_272(Files.readString(var2.path2, StandardCharsets.UTF_8));
                var2.flag2 = false;
                return var11;
            }
        } catch (InterruptedException | IOException var9) {
            this.method_274(new StringBuilder().insert(0, "Failed to run powershell.exe: ").append(var9.getMessage()).toString());
            return null;
        }
    }

    private void method_275() throws IOException {
        if (this.path == null || !Files.isRegularFile(this.path)) {
            this.path = Files.createTempFile("onyx-nowplaying", ".ps1");
            this.path2 = Files.createTempFile("onyx-nowplaying-out", ".json");
            this.path.toFile().deleteOnExit();
            this.path2.toFile().deleteOnExit();
            String var2 = this.path2.toString().replace("\\", "\\\\").replace("'", "''");
            Files.writeString(
                this.path,
                "$ErrorActionPreference = 'Stop'\n$outPath = '__OUTPUT__'\n\nfunction Write-Result($obj) {\n    [System.IO.File]::WriteAllText($outPath, ($obj | ConvertTo-Json -Compress), [System.Text.Encoding]::UTF8)\n}\n\ntry {\n    Add-Type -AssemblyName System.Runtime.WindowsRuntime\n\n    $asTaskGeneric = ([System.WindowsRuntimeSystemExtensions].GetMethods() | Where-Object {\n        $_.Name -eq 'AsTask' -and $_.GetParameters().Count -eq 1 -and $_.GetParameters()[0].ParameterType.Name -eq 'IAsyncOperation`1'\n    })[0]\n\n    function Await($WinRtTask, $ResultType) {\n        $asTask = $asTaskGeneric.MakeGenericMethod($ResultType)\n        $netTask = $asTask.Invoke($null, @($WinRtTask))\n        $netTask.Wait(-1) | Out-Null\n        $netTask.Result\n    }\n\n    [Windows.Media.Control.GlobalSystemMediaTransportControlsSessionManager,Windows.Media.Control,ContentType=WindowsRuntime] | Out-Null\n\n    $manager = Await ([Windows.Media.Control.GlobalSystemMediaTransportControlsSessionManager]::RequestAsync()) ([Windows.Media.Control.GlobalSystemMediaTransportControlsSessionManager])\n    $session = $manager.GetCurrentSession()\n\n    if ($null -eq $session) {\n        Write-Result ([PSCustomObject]@{ empty = $true })\n        exit 0\n    }\n\n    $props = Await ($session.TryGetMediaPropertiesAsync()) ([Windows.Media.Control.GlobalSystemMediaTransportControlsSessionMediaProperties])\n    $playback = $session.GetPlaybackInfo()\n    $timeline = $session.GetTimelineProperties()\n\n    # SMTC hands the cover over as a stream reference rather than a URL, so it has to be\n    # read out whole and carried back as base64. Its own try/catch: a player that\n    # publishes no thumbnail, or hands back a stream that will not open, must still\n    # leave the title and the progress bar working.\n    #\n    # The stream comes back from the reflection-driven await as a bare COM object with no\n    # usable WinRT projection: its .Size reads as empty (so the old size-gated read never\n    # ran) and DataReader refuses to wrap it. AsStreamForRead projects it to an ordinary\n    # .NET stream, which drains cleanly regardless — that is the only path that works here.\n    $art = $null\n\n    try {\n        $reference = $props.Thumbnail\n\n        if ($null -ne $reference) {\n            $stream = Await ($reference.OpenReadAsync()) ([Windows.Storage.Streams.IRandomAccessStreamWithContentType])\n\n            $asStreamForRead = [System.IO.WindowsRuntimeStreamExtensions].GetMethods() | Where-Object {\n                $_.Name -eq 'AsStreamForRead' -and $_.GetParameters().Count -eq 1\n            } | Select-Object -First 1\n\n            $netStream = $asStreamForRead.Invoke($null, @($stream))\n\n            try {\n                $buffer = New-Object System.IO.MemoryStream\n                $netStream.CopyTo($buffer)\n                $bytes = $buffer.ToArray()\n\n                if ($bytes.Length -gt 0) {\n                    $art = [Convert]::ToBase64String($bytes)\n                }\n\n                $buffer.Dispose()\n            } finally {\n                $netStream.Dispose()\n            }\n        }\n    } catch {\n        $art = $null\n    }\n\n    Write-Result ([PSCustomObject]@{\n        title = $props.Title\n        artist = $props.Artist\n        playing = ($playback.PlaybackStatus.ToString() -eq 'Playing')\n        positionMs = [math]::Round($timeline.Position.TotalMilliseconds)\n        durationMs = [math]::Round(($timeline.EndTime - $timeline.StartTime).TotalMilliseconds)\n        art = $art\n    })\n} catch {\n    Write-Result ([PSCustomObject]@{ error = $_.Exception.Message })\n}\n"
                    .replace("__OUTPUT__", var2),
                StandardCharsets.UTF_8
            );
        }
    }

    private static int method_276(JsonObject var0, String var1) {
        JsonElement var3;
        return (var3 = var0.get(var1)) != null && var3.isJsonPrimitive() && var3.getAsJsonPrimitive().isNumber() ? var3.getAsInt() : 0;
    }
}
