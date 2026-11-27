package client.onyx.account;

import client.onyx.account.microsoft.AuthException;
import client.onyx.account.microsoft.XboxLiveAuth;
import com.google.gson.JsonObject;
import com.mojang.util.UUIDTypeAdapter;
import net.minecraft.util.Session;

public class AccessTokenAccount extends Account {
    private String string2;

    @Override
    public void method_1815(JsonObject var1) {
        this.string2 = method_1809(var1, "accessToken");
    }

    @Override
    public Session method_1810() throws AuthException {
        if (this.string2 != null && !this.string2.isBlank()) {
            if (this.uuid == null) {
                this.method_1808();
            }

            return new Session(this.string, UUIDTypeAdapter.fromUUID(this.uuid), this.string2, "mojang");
        } else {
            throw new AuthException("No access token stored for this account");
        }
    }

    @Override
    public void method_1812(JsonObject var1) {
        if (this.string2 != null) {
            var1.addProperty("accessToken", this.string2);
        }
    }

    public AccessTokenAccount(String var1) {
        super(AccountType.ACCESS_TOKEN, var1);
    }

    public static AccessTokenAccount method_1828(String var0) throws AuthException {
        XboxLiveAuth.XboxToken var3 = XboxLiveAuth.method_1877(var0);
        AccessTokenAccount var2 = new AccessTokenAccount(var3.string());
        var2.uuid = var3.uuid();
        var2.string2 = var0.trim();
        return var2;
    }

    @Override
    public void method_1808() throws AuthException {
        XboxLiveAuth.XboxToken var2;
        this.string = (var2 = XboxLiveAuth.method_1877(this.string2)).string();
        this.uuid = var2.uuid();
    }

    @Override
    public String getString22() {
        return this.string2;
    }
}
