package client.onyx.account;

import client.onyx.account.microsoft.AuthException;
import client.onyx.account.microsoft.Http;
import client.onyx.account.microsoft.XboxLiveAuth;
import com.google.gson.JsonObject;
import com.mojang.util.UUIDTypeAdapter;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import net.minecraft.util.Session;

public class OfflineAccount extends Account {
    private static final String STRING_CONST = "https://api.mojang.com/users/profiles/minecraft/";
    private static final String STRING_CONST2 = "-";
    private boolean flag;

    public void method_1843(boolean var1) {
        this.flag = var1;
    }

    @Override
    public Session method_1810() throws AuthException {
        if (this.uuid == null) {
            this.method_1808();
        }

        return new Session(this.string, UUIDTypeAdapter.fromUUID(this.uuid), "-", "legacy");
    }

    @Override
    public void method_1815(JsonObject var1) {
        this.flag = method_1813(var1, "premiumUuid", false);
    }

    @Override
    public void method_1808() throws AuthException {
        if (!this.flag) {
            this.uuid = method_1844(this.string);
        } else {
            Http.Response var3;
            if (!(var3 = Http.method_1858(
                    new StringBuilder().insert(0, "https://api.mojang.com/users/profiles/minecraft/").append(Http.method_1857(this.string)).toString()
                ))
                .method_1846()) {
                this.uuid = method_1844(this.string);
            } else {
                JsonObject var10000 = var3.method_1845();
                String var4 = Http.method_1860(var10000, "id");
                String var2 = Http.method_1860(var10000, "name");
                if (var4 == null) {
                    this.uuid = method_1844(this.string);
                } else {
                    if (var2 != null) {
                        this.string = var2;
                    }

                    this.uuid = XboxLiveAuth.method_1878(var4);
                }
            }
        }
    }

    public boolean isFlag() {
        return this.flag;
    }

    public static UUID method_1844(String var0) {
        return UUID.nameUUIDFromBytes(new StringBuilder().insert(0, "OfflinePlayer:").append(var0).toString().getBytes(StandardCharsets.UTF_8));
    }

    public OfflineAccount(String var1) {
        super(AccountType.OFFLINE, var1);
        this.uuid = method_1844(var1);
    }

    @Override
    public void method_1812(JsonObject var1) {
        var1.addProperty("premiumUuid", this.flag);
    }
}
