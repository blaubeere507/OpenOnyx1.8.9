package client.onyx.skin;

import client.onyx.account.microsoft.AuthException;
import client.onyx.account.microsoft.Http;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import javax.imageio.ImageIO;

public final class MojangSkinLookup {
    private static final ExecutorService executorService = Executors.newSingleThreadExecutor(var0 -> {
        Thread var10000 = new Thread(var0, "onyx-skin-lookup");
        var10000.setDaemon(true);
        return var10000;
    });
    private static final int INT_150001 = 15000;

    private MojangSkinLookup() {
    }

    private static String method_260(String var0) throws AuthException {
        Http.Response var2;
        if ((var2 = Http.method_1858(
                        new StringBuilder().insert(0, "https://api.mojang.com/users/profiles/minecraft/").append(Http.method_1857(var0)).toString()
                    ))
                    .intValue()
                == 204
            || var2.intValue() == 404) {
            throw new AuthException(new StringBuilder().insert(0, "No account named ").append(var0).toString());
        } else if (!var2.method_1846()) {
            throw new AuthException(new StringBuilder().insert(0, "Mojang lookup failed (").append(var2.intValue()).append(")").toString());
        } else if ((var0 = Http.method_1860(var2.method_1845(), "id")) != null && var0.length() == 32) {
            return var0;
        } else {
            throw new AuthException(new StringBuilder().insert(0, "Malformed profile for ").append(var0).toString());
        }
    }

    private static JsonObject method_261(String var0) throws AuthException {
        Http.Response var1;
        if (!(var1 = Http.method_1858(new StringBuilder().insert(0, "https://sessionserver.mojang.com/session/minecraft/profile/").append(var0).toString()))
            .method_1846()) {
            throw new AuthException(new StringBuilder().insert(0, "Profile fetch failed (").append(var1.intValue()).append(")").toString());
        } else {
            JsonElement var12;
            if ((var12 = var1.method_1845().get("properties")) != null && var12.isJsonArray()) {
                Iterator var13 = var12.getAsJsonArray().iterator();

                label47:
                while (true) {
                    Iterator var5 = var13;

                    while (var5.hasNext()) {
                        JsonElement var8;
                        if (!(var8 = (JsonElement)var13.next()).isJsonObject()) {
                            var5 = var13;
                        } else {
                            JsonObject var9 = var8.getAsJsonObject();
                            if ("textures".equals(Http.method_1860(var9, "name"))) {
                                if ((var0 = Http.method_1860(var9, "value")) == null) {
                                    throw new AuthException("Profile has no textures");
                                }

                                try {
                                    var0 = new String(Base64.getDecoder().decode(var0), StandardCharsets.UTF_8);
                                    JsonElement var4;
                                    JsonElement var6;
                                    if ((var4 = new JsonParser().parse(var0)).isJsonObject()) {
                                        var6 = var4.getAsJsonObject().get("textures");
                                    } else {
                                        var6 = null;
                                    }

                                    if (var6 != null && var6.isJsonObject()) {
                                        return var6.getAsJsonObject();
                                    }
                                    continue label47;
                                } catch (RuntimeException var7) {
                                    throw new AuthException("Malformed texture property", var7);
                                }
                            }

                            var5 = var13;
                        }
                    }

                    throw new AuthException("Profile has no textures");
                }
            } else {
                throw new AuthException("Profile has no textures");
            }
        }
    }

    public static void method_262(String var0, Consumer<MojangSkinLookup.SkinResult> var1, Consumer<String> var2) {
        executorService.submit(() -> {
            String var4 = var0;
            Consumer<MojangSkinLookup.SkinResult> var5 = var1;

            try {
                var5.accept(method_263(var4));
            } catch (AuthException var3) {
                var2.accept(var3.getMessage());
            }
        });
    }

    private static MojangSkinLookup.SkinResult method_263(String var0) throws AuthException {
        JsonElement var3;
        if ((var3 = method_261(method_260(var0)).get("SKIN")) != null && var3.isJsonObject()) {
            String var2;
            if ((var2 = Http.method_1860(var3.getAsJsonObject(), "url")) == null) {
                throw new AuthException(new StringBuilder().insert(0, var0).append(" has no custom skin").toString());
            } else {
                return new MojangSkinLookup.SkinResult(method_264(var2), method_265(var3.getAsJsonObject()));
            }
        } else {
            throw new AuthException(new StringBuilder().insert(0, var0).append(" has no custom skin").toString());
        }
    }

    private static BufferedImage method_264(String var0) throws AuthException {
        String var2 = var0;
        HttpURLConnection var1 = null;

        BufferedImage var4;
        try {
            var1 = (HttpURLConnection)new URL(var2).openConnection();
            var1.setConnectTimeout(15000);
            var1.setReadTimeout(15000);
            var1.setRequestProperty("User-Agent", "onyx");
            InputStream var15 = var1.getInputStream();

            try {
                BufferedImage var3;
                if ((var3 = ImageIO.read(var15)) == null) {
                    throw new AuthException("Skin download was not an image");
                }

                var4 = var3;
            } catch (Throwable var12) {
                if (var15 != null) {
                    try {
                        var15.close();
                    } catch (Throwable var11) {
                        var12.addSuppressed(var11);
                        throw var12;
                    }

                    throw var12;
                }

                throw var12;
            }

            if (var15 != null) {
                var15.close();
            }
        } catch (IOException var13) {
            throw new AuthException("Couldn't download the skin", var13);
        } finally {
            if (var1 != null) {
                var1.disconnect();
            }
        }

        return var4;
    }

    private static String method_265(JsonObject var0) {
        JsonElement var2;
        if ((var2 = var0.get("metadata")) != null && var2.isJsonObject()) {
            String var3 = Http.method_1860(var2.getAsJsonObject(), "model");
            if ("slim".equals(var3)) {
                return "slim";
            }
        }

        return "default";
    }

    public record SkinResult(BufferedImage bufferedImage, String string) {
    }
}
