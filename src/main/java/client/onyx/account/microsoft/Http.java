package client.onyx.account.microsoft;

import client.onyx.Onyx;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.net.Proxy.Type;
import java.nio.charset.StandardCharsets;

public final class Http {
    private static final int INT_200001 = 20000;
    private static final String STRING_CONST = "okhttp/4.12.0";

    public static Http.Response method_1856(String var0, int var1) throws AuthException {
        return method_1859(var0, "GET", null, var1, Onyx.accountManager.getProxyConfig().method_1795());
    }

    public static String method_1857(String var0) {
        return URLEncoder.encode(var0, StandardCharsets.UTF_8);
    }

    public static Http.Response method_1858(String var0, String... var1) throws AuthException {
        return method_1864(var0, 20000, var1);
    }

    // $VF: Could not inline inconsistent finally blocks
    // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
    private static Http.Response method_1859(String var0, String var1, String var2, int var3, Proxy var4, String... var5) throws AuthException {
        client.onyx.account.microsoft.Http.Response var36; // declared by DeclareMissing
        String var32 = var1;
        var1 = var0;

        HttpURLConnection var6;
        try {
            var6 = (HttpURLConnection)new URL(var1).openConnection(var4);
        } catch (ClassCastException | IOException var25) {
            throw new AuthException("Invalid request URL", var25);
        }

        HttpURLConnection var10 = var6;

        try {
            try {
                var10.setConnectTimeout(var3);
                var6.setReadTimeout(var3);
                var6.setInstanceFollowRedirects(false);
                var6.setRequestMethod(var32);
                var6.setRequestProperty("User-Agent", "okhttp/4.12.0");
                int var7 = 0;

                for (byte var11 = 0; var11 + 1 < var5.length; var11 = (byte)var7) {
                    String var10001 = var5[var7];
                    int var10003 = var7 + 1;
                    var7 += 2;
                    var6.setRequestProperty(var10001, var5[var10003]);
                }

                HttpURLConnection var12;
                label163: {
                    if (var2 != null) {
                        byte[] var37;
                        byte[] var39 = var37 = var2.getBytes(StandardCharsets.UTF_8);
                        var6.setDoOutput(true);
                        var6.setFixedLengthStreamingMode(var39.length);
                        OutputStream var33 = var6.getOutputStream();

                        try {
                            var33.write(var37);
                        } catch (Throwable var29) {
                            if (var33 != null) {
                                try {
                                    var33.close();
                                } catch (Throwable var28) {
                                    var29.addSuppressed(var28);
                                    throw var29;
                                }

                                throw var29;
                            }

                            throw var29;
                        }

                        if (var33 != null) {
                            var33.close();
                            var12 = var6;
                            break label163;
                        }
                    }

                    var12 = var6;
                }

                InputStream var14;
                if ((var7 = var12.getResponseCode()) >= 400) {
                    var14 = var6.getErrorStream();
                } else {
                    var14 = var6.getInputStream();
                }

                String var8 = "";
                if (var14 != null) {
                    InputStream var35 = var14;

                    try {
                        var8 = new String(var35.readAllBytes(), StandardCharsets.UTF_8);
                    } catch (Throwable var27) {
                        if (var14 != null) {
                            try {
                                var35.close();
                            } catch (Throwable var26) {
                                var27.addSuppressed(var26);
                                throw var27;
                            }

                            throw var27;
                        }

                        throw var27;
                    }

                    if (var14 != null) {
                        var14.close();
                    }
                }

                var36 = new Http.Response(var7, var8);
            } catch (IOException var30) {
                String var16;
                if (var4.type() == Type.DIRECT) {
                    var16 = null;
                } else {
                    var16 = Onyx.accountManager.getProxyConfig().getString();
                }

                String var17;
                if (var16 != null && !var16.isBlank()) {
                    var17 = var16;
                } else {
                    var17 = var30.getMessage();
                }

                throw new AuthException(new StringBuilder().insert(0, "Network error: ").append(var17).toString(), var30);
            }
        } catch (Throwable var31) {
            var6.disconnect();
            throw var31;
        }

        var6.disconnect();
        return var36;
    }

    public static String method_1860(JsonObject var0, String var1) {
        JsonElement var3;
        return (var3 = var0.get(var1)) != null && var3.isJsonPrimitive() && var3.getAsJsonPrimitive().isString() ? var3.getAsString() : null;
    }

    public static Http.Response method_1861(String var0, String var1) throws AuthException {
        return method_1859(var0, "POST", var1, 20000, method_1865(), "Content-Type", "application/x-www-form-urlencoded");
    }

    public static JsonObject method_1862(JsonObject var0) {
        JsonElement var2;
        if ((var2 = var0.get("DisplayClaims")) != null && var2.isJsonObject()) {
            JsonElement var3;
            if ((var3 = var2.getAsJsonObject().get("xui")) != null && var3.isJsonArray()) {
                JsonArray var4;
                return (var4 = var3.getAsJsonArray()).size() != 0 && var4.get(0).isJsonObject() ? var4.get(0).getAsJsonObject() : null;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static Http.Response method_1863(String var0, String var1, String... var2) throws AuthException {
        String[] var4 = new String[var2.length + 4];
        var4[0] = "Content-Type";
        var4[1] = "application/json";
        var4[2] = "Accept";
        var4[3] = "application/json";
        System.arraycopy(var2, 0, var4, 4, var2.length);
        return method_1859(var0, "POST", var1, 20000, method_1865(), var4);
    }

    public static Http.Response method_1864(String var0, int var1, String... var2) throws AuthException {
        return method_1859(var0, "GET", null, var1, method_1865(), var2);
    }

    private Http() {
    }

    private static Proxy method_1865() {
        return Onyx.accountManager.method_1829();
    }

    public static long method_1866(JsonObject var0, String var1, long var2) {
        JsonElement var5;
        return (var5 = var0.get(var1)) != null && var5.isJsonPrimitive() && var5.getAsJsonPrimitive().isNumber() ? var5.getAsLong() : var2;
    }

    public record Response(int intValue, String string) {
        public JsonObject method_1845() throws AuthException {
            var var1 = this;

            try {
                JsonElement var3;
                if ((var3 = new JsonParser().parse(var1.string)) != null && var3.isJsonObject()) {
                    return var3.getAsJsonObject();
                } else {
                    throw new AuthException("Malformed response from the server");
                }
            } catch (RuntimeException var2) {
                throw new AuthException("Malformed response from the server", var2);
            }
        }

        public boolean method_1846() {
            return this.intValue >= 200 && this.intValue < 300;
        }
    }
}
