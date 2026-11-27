package client.onyx.account;

import client.onyx.Onyx;
import client.onyx.proxy.LocalProxyBridge;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.UnknownHostException;
import java.net.Proxy.Type;

public final class ProxyConfig {
    private volatile ProxyConfig.Credentials credentials = ProxyConfig.Credentials.method_1824();
    private final Object object = new Object();
    private LocalProxyBridge localProxyBridge;
    private volatile String string;

    private static String method_1786(String var0) {
        return var0 == null ? "" : var0.trim();
    }

    private Proxy method_1787() {
        var var1 = this;

        try {
            return var1.method_1795();
        } catch (RuntimeException var2) {
            this.string = var2.getMessage();
            Onyx.LOGGER.warn("Falling back to a direct connection", var2);
            return Proxy.NO_PROXY;
        }
    }

    public String method_1788() {
        return this.credentials.string2();
    }

    public String method_1789() {
        return this.credentials.string3();
    }

    public Proxy method_1790() {
        return this.method_1787();
    }

    public Proxy method_1791() {
        return this.credentials.flag2() ? this.method_1787() : Proxy.NO_PROXY;
    }

    public void method_1792(ProxyConfig.Protocol var1, boolean var2, boolean var3, String var4, String var5, String var6) {
        var var16 = this;
        var4 = method_1786(var4);
        var5 = method_1786(var5);
        String var9;
        if (var6 == null) {
            var9 = "";
        } else {
            var9 = var6;
        }

        ProxyConfig.Credentials var10002;
        if (var2) {
            var10002 = new ProxyConfig.Credentials(var1, true, var3, var4, var5, var9, method_1793(var4));
        } else {
            var10002 = new ProxyConfig.Credentials(var1, false, var3, var4, var5, var9, null);
        }

        ProxyConfig.Credentials var14 = var10002;
        synchronized (this.object) {
            var16.credentials = var14;
            var16.string = null;
            if (var16.localProxyBridge != null) {
                if (var14.flag()) {
                    var16.localProxyBridge.method_250(var14.protocol(), var14.method_1825(), var14.string3(), var14.string2());
                } else {
                    var16.method_1804();
                }
            }
        }
    }

    private static ProxyConfig.Endpoint method_1793(String var0) {
        int var7; // declared by DeclareMissing
        String var1 = var0;
        int var6;
        if ((var6 = var0.lastIndexOf(58)) > 0 && var6 != var0.length() - 1) {
            String var2;
            if ((var2 = var0.substring(0, var6).trim()).startsWith("[") && var2.endsWith("]")) {
                var2 = var2.substring(1, var2.length() - 1);
            }

            if (var2.isBlank()) {
                throw new IllegalArgumentException("Enter a proxy host");
            } else {
                try {
                    var7 = Integer.parseInt(var1.substring(var6 + 1));
                } catch (NumberFormatException var5) {
                    throw new IllegalArgumentException("Enter a valid proxy port");
                }

                if (var7 >= 1 && var7 <= 65535) {
                    try {
                        return new ProxyConfig.Endpoint(var2, var7, new InetSocketAddress(InetAddress.getByName(var2), var7));
                    } catch (UnknownHostException var4) {
                        throw new IllegalArgumentException("Could not resolve the proxy host");
                    }
                } else {
                    throw new IllegalArgumentException("Enter a valid proxy port");
                }
            }
        } else {
            throw new IllegalArgumentException("Enter proxy address as host:port");
        }
    }

    public boolean method_1794() {
        return this.credentials.flag();
    }

    public Proxy method_1795() {
        var var1 = this;
        ProxyConfig.Credentials var3;
        if (!(var3 = this.credentials).flag()) {
            return Proxy.NO_PROXY;
        } else {
            this.string = null;

            try {
                return new Proxy(Type.HTTP, var1.method_1805(var3).method_259());
            } catch (IOException var2) {
                throw new IllegalStateException("Could not start the local proxy bridge", var2);
            }
        }
    }

    public ProxyConfig.Protocol method_1796() {
        return this.credentials.protocol();
    }

    public String getString() {
        return this.string;
    }

    public InetSocketAddress method_1797() {
        return this.credentials.method_1825();
    }

    public void method_1798(JsonObject var1) {
        JsonObject var5 = var1;
        var var6 = this;
        ProxyConfig.Protocol var2 = ProxyConfig.Protocol.method_1806(method_1803(var1.get("protocol")));
        boolean var3 = method_1799(var1.get("enabled"));

        try {
            var6.method_1792(
                var2,
                var3,
                method_1799(var5.get("auth")),
                method_1803(var5.get("address")),
                method_1803(var5.get("username")),
                method_1803(var5.get("password"))
            );
        } catch (IllegalArgumentException var4) {
            this.credentials = ProxyConfig.Credentials.method_1824();
        }
    }

    private static boolean method_1799(JsonElement var0) {
        return var0 != null && var0.isJsonPrimitive() && var0.getAsJsonPrimitive().isBoolean() && var0.getAsBoolean();
    }

    public JsonObject method_1800() {
        ProxyConfig.Credentials var3 = this.credentials;
        JsonObject var2 = new JsonObject();
        var2.addProperty("protocol", var3.protocol().string);
        var2.addProperty("enabled", var3.flag());
        var2.addProperty("auth", var3.flag2());
        var2.addProperty("address", var3.string());
        var2.addProperty("username", var3.string3());
        var2.addProperty("password", var3.string2());
        return var2;
    }

    public String method_1801() {
        return this.credentials.string();
    }

    public boolean method_1802() {
        return this.credentials.flag2();
    }

    private static String method_1803(JsonElement var0) {
        return var0 != null && var0.isJsonPrimitive() && var0.getAsJsonPrimitive().isString() ? var0.getAsString() : "";
    }

    private void method_1804() {
        if (this.localProxyBridge != null) {
            this.localProxyBridge.close();
        }

        this.localProxyBridge = null;
    }

    private LocalProxyBridge method_1805(ProxyConfig.Credentials var1) throws IOException {
        ProxyConfig.Credentials var5 = var1;
        var var6 = this;
        synchronized (this.object) {
            if (var6.localProxyBridge != null) {
                var6.localProxyBridge.method_250(var5.protocol(), var5.method_1825(), var5.string3(), var5.string2());
                return var6.localProxyBridge;
            } else {
                var6.localProxyBridge = new LocalProxyBridge(var5.protocol(), var5.method_1825(), var5.string3(), var5.string2(), var1x -> {
                    this.string = var1x;
                    Onyx.LOGGER.warn("Proxy tunnel failed: {}", var1x);
                });
                Onyx.LOGGER.info("Started local proxy bridge for {} at {}", var5.protocol().getString2(), var6.localProxyBridge.method_259());
                return var6.localProxyBridge;
            }
        }
    }

    private record Credentials(
        ProxyConfig.Protocol protocol, boolean flag, boolean flag2, String string, String string3, String string2, ProxyConfig.Endpoint endpoint
    ) {
        private static ProxyConfig.Credentials method_1824() {
            return new ProxyConfig.Credentials(ProxyConfig.Protocol.SOCKS5, false, false, "", "", "", null);
        }

        private InetSocketAddress method_1825() {
            return this.endpoint == null ? null : this.endpoint.inetSocketAddress();
        }

        private String method_1826() {
            return this.endpoint == null ? "" : this.endpoint.string();
        }

        private int method_1827() {
            return this.endpoint == null ? 0 : this.endpoint.intValue();
        }
    }

    private record Endpoint(String string, int intValue, InetSocketAddress inetSocketAddress) {
    }

    public static enum Protocol {
        SOCKS5("socks5", "SOCKS5"),
        HTTP("http", "HTTP/HTTPS");

        private final String string;
        private final String string2;

        private Protocol(String var3, String var4) {
            this.string = var3;
            this.string2 = var4;
        }

        public String getString2() {
            return this.string2;
        }

        public static ProxyConfig.Protocol method_1806(String var0) {
            String var3 = var0;
            ProxyConfig.Protocol[] var1;
            int var2 = (var1 = values()).length;
            int var6 = 0;

            for (int var5 = 0; var5 < var2; var5 = ++var6) {
                ProxyConfig.Protocol var4;
                if ((var4 = var1[var6]).string.equalsIgnoreCase(var3)) {
                    return var4;
                }
            }

            return SOCKS5;
        }
    }
}
