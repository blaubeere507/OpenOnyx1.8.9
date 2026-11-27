package client.onyx.account.microsoft;

import client.onyx.Onyx;
import client.onyx.util.DesktopUtil;
import client.onyx.util.IMinecraft;
import client.onyx.util.Workers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public final class MicrosoftAuth implements IMinecraft {
    private static final int INT_13371 = 1337;
    private static final String STRING_CONST = "<!doctype html><meta charset=\"utf-8\"><title>%s</title>\n<body style=\"background:#141218;color:#e6e0e9;font-family:system-ui,sans-serif;\ndisplay:flex;align-items:center;justify-content:center;height:100vh;margin:0\">\n<div style=\"text-align:center\"><h1 style=\"font-weight:500\">%s</h1>\n<p style=\"opacity:.7\">You can close this tab and return to Minecraft.</p></div>";
    private static final String STRING_CONST2 = "127.0.0.1";
    private static final String STRING_CONST3 = "/";
    private static MicrosoftAuth INSTANCE;
    private static final int INT_3001 = 300;
    private final AtomicBoolean atomicBoolean = new AtomicBoolean();
    private final HttpServer httpServer;
    private final Consumer<XboxLiveAuth.MinecraftToken> consumer;
    private final Consumer<String> consumer2;
    private final String string;
    private final String string2;

    public static synchronized MicrosoftAuth method_1847(Consumer<XboxLiveAuth.MinecraftToken> var0, Consumer<String> var1) throws AuthException {
        if (!XboxLiveAuth.method_1870()) {
            throw new AuthException("No Azure client id configured — set MicrosoftAuth.CLIENT_ID");
        } else {
            method_1849();

            HttpServer var2;
            try {
                var2 = HttpServer.create(new InetSocketAddress("127.0.0.1", 1337), 0);
            } catch (BindException var4) {
                throw new AuthException("Microsoft sign-in requires local port 1337 to be available", var4);
            } catch (IOException var5) {
                throw new AuthException("Could not open the local login server", var5);
            }

            MicrosoftAuth var6 = new MicrosoftAuth(var2, 1337, var0, var1);
            var2.createContext("/", var6::method_1853);
            var2.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
            var2.start();
            INSTANCE = var6;
            Workers.method_1936(() -> {
                try {
                    Thread.sleep(300000L);
                } catch (InterruptedException var2x) {
                    Thread.currentThread().interrupt();
                    return;
                }

                var6.method_1855("Sign-in timed out");
            });
            return var6;
        }
    }

    public String getString2() {
        return this.string2;
    }

    private void method_1848(HttpExchange var1, String var2) {
        byte[] var10 = "<!doctype html><meta charset=\"utf-8\"><title>%s</title>\n<body style=\"background:#141218;color:#e6e0e9;font-family:system-ui,sans-serif;\ndisplay:flex;align-items:center;justify-content:center;height:100vh;margin:0\">\n<div style=\"text-align:center\"><h1 style=\"font-weight:500\">%s</h1>\n<p style=\"opacity:.7\">You can close this tab and return to Minecraft.</p></div>"
            .formatted(var2, var2)
            .getBytes(StandardCharsets.UTF_8);

        try {
            OutputStream var9 = var1.getResponseBody();

            try {
                var1.getResponseHeaders().add("Content-Type", "text/html; charset=utf-8");
                var1.sendResponseHeaders(200, var10.length);
                var9.write(var10);
            } catch (Throwable var7) {
                if (var9 != null) {
                    try {
                        var9.close();
                    } catch (Throwable var6) {
                        var7.addSuppressed(var6);
                        throw var7;
                    }

                    throw var7;
                }

                throw var7;
            }

            if (var9 != null) {
                var9.close();
                return;
            }
        } catch (IOException var8) {
            Onyx.LOGGER.warn("Failed to answer the OAuth callback", var8);
        }
    }

    public static synchronized void method_1849() {
        if (INSTANCE != null) {
            INSTANCE.method_1850();
        }
    }

    public String getString() {
        return this.string;
    }

    public void method_1850() {
        if (this.atomicBoolean.compareAndSet(false, true)) {
            this.method_1854();
        }
    }

    public void method_1851() {
        DesktopUtil.method_1934(this.string2);
    }

    private MicrosoftAuth(HttpServer var1, int var2, Consumer<XboxLiveAuth.MinecraftToken> var3, Consumer<String> var4) {
        this.httpServer = var1;
        this.consumer = var3;
        this.consumer2 = var4;
        this.string = new StringBuilder().insert(0, "http://127.0.0.1:").append(var2).append("/").toString();
        this.string2 = XboxLiveAuth.method_1879(this.string);
    }

    private static Map<String, String> method_1852(String var0) {
        HashMap var8 = new HashMap();
        if (var0 != null && !var0.isEmpty()) {
            String[] var6;
            int var5 = (var6 = var0.split("&")).length;
            int var4 = 0;

            for (int var7 = 0; var7 < var5; var7 = ++var4) {
                int var2;
                String var3;
                if ((var2 = (var3 = var6[var4]).indexOf(61)) > 0) {
                    var8.put(
                        URLDecoder.decode(var3.substring(0, var2), StandardCharsets.UTF_8), URLDecoder.decode(var3.substring(var2 + 1), StandardCharsets.UTF_8)
                    );
                }
            }

            return var8;
        } else {
            return var8;
        }
    }

    private void method_1853(HttpExchange var1) {
        client.onyx.account.microsoft.XboxLiveAuth.MinecraftToken var12; // declared by DeclareMissing
        var var2 = this;
        Map var11;
        String var3 = (String)(var11 = method_1852(var1.getRequestURI().getRawQuery())).get("code");
        String var5;
        if (var11.get("error_description") == null) {
            var5 = (String)var11.get("error");
        } else {
            var5 = (String)var11.get("error_description");
        }

        if (var3 == null) {
            this.method_1848(var1, "Sign-in failed");
            MicrosoftAuth var7;
            String var8;
            if (var5 == null) {
                var8 = "Microsoft did not return an authorization code";
                var7 = this;
            } else {
                var8 = var5;
                var7 = this;
            }

            var7.method_1855(var8);
        } else if (this.atomicBoolean.compareAndSet(false, true)) {
            try {
                var12 = XboxLiveAuth.method_1871(var3, var2.string);
            } catch (AuthException var10) {
                Onyx.LOGGER.warn("Microsoft sign-in failed", var10);
                this.method_1848(var1, "Sign-in failed");
                this.method_1854();
                mc.addScheduledTask(() -> this.consumer2.accept(var10.getMessage()));
                return;
            }

            this.method_1848(var1, "Signed in");
            this.method_1854();
            mc.addScheduledTask(() -> this.consumer.accept(var12));
        }
    }

    private void method_1854() {
        var var1 = this;
        this.httpServer.stop(0);
        synchronized (MicrosoftAuth.class) {
            if (INSTANCE == var1) {
                INSTANCE = null;
            }
        }
    }

    private void method_1855(String var1) {
        if (this.atomicBoolean.compareAndSet(false, true)) {
            this.method_1854();
            mc.addScheduledTask(() -> this.consumer2.accept(var1));
        }
    }
}
