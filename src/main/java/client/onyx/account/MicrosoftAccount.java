package client.onyx.account;

import client.onyx.account.microsoft.AuthException;
import client.onyx.account.microsoft.XboxLiveAuth;
import com.google.gson.JsonObject;
import com.mojang.util.UUIDTypeAdapter;
import net.minecraft.util.Session;

public class MicrosoftAccount extends Account {
    private static final long LONG_600001 = 60000L;
    private XboxLiveAuth.ClientFlavour clientFlavour = XboxLiveAuth.ClientFlavour.LEGACY;
    private long longValue2;
    private String string2;
    private String string22;
    private String string3;

    public static MicrosoftAccount method_1816(String var0) throws AuthException {
        String var1 = var0;

        try {
            return method_1818(var1, XboxLiveAuth.ClientFlavour.LEGACY);
        } catch (AuthException var4) {
            if (!XboxLiveAuth.ClientFlavour.AZURE.method_1867()) {
                throw var4;
            } else {
                try {
                    return method_1818(var1, XboxLiveAuth.ClientFlavour.AZURE);
                } catch (AuthException var3) {
                    throw new AuthException(
                        new StringBuilder()
                            .insert(0, "No client id accepted this token. Live: ")
                            .append(var4.getMessage())
                            .append(" | Azure: ")
                            .append(var3.getMessage())
                            .toString()
                    );
                }
            }
        }
    }

    @Override
    public void method_1812(JsonObject var1) {
        var1.addProperty("refreshToken", this.string2);
        var1.addProperty("flavour", this.clientFlavour.getString3());
        if (this.string22 != null) {
            var1.addProperty("accessToken", this.string22);
            var1.addProperty("expiresAt", this.longValue2);
            if (this.string3 != null) {
                var1.addProperty("xuid", this.string3);
            }
        }
    }

    @Override
    public void method_1808() throws AuthException {
        this.method_1819(XboxLiveAuth.method_1872(this.string2, this.clientFlavour));
    }

    @Override
    public Session method_1810() throws AuthException {
        if (this.string22 == null || System.currentTimeMillis() > this.longValue2 - 60000L) {
            this.method_1808();
        }

        return new Session(this.string, UUIDTypeAdapter.fromUUID(this.uuid), this.string22, "mojang");
    }

    @Override
    public String getString22() {
        return this.string22;
    }

    public static MicrosoftAccount method_1817(XboxLiveAuth.MinecraftToken var0) {
        MicrosoftAccount var10000 = new MicrosoftAccount(var0.string());
        var10000.method_1819(var0);
        return var10000;
    }

    @Override
    public String getString2() {
        return this.string2;
    }

    public static MicrosoftAccount method_1818(String var0, XboxLiveAuth.ClientFlavour var1) throws AuthException {
        return method_1817(XboxLiveAuth.method_1872(var0, var1));
    }

    public XboxLiveAuth.ClientFlavour getClientFlavour() {
        return this.clientFlavour;
    }

    @Override
    public void method_1815(JsonObject var1) {
        this.string2 = method_1809(var1, "refreshToken");
        this.clientFlavour = XboxLiveAuth.ClientFlavour.method_1868(method_1809(var1, "flavour"));
        this.string22 = method_1809(var1, "accessToken");
        this.longValue2 = method_1807(var1, "expiresAt", 0L);
        this.string3 = method_1809(var1, "xuid");
    }

    @Override
    public long getLongValue2() {
        return this.longValue2;
    }

    private void method_1819(XboxLiveAuth.MinecraftToken var1) {
        this.string = var1.string();
        this.uuid = var1.uuid();
        this.clientFlavour = var1.clientFlavour();
        this.string2 = var1.string4();
        this.string22 = var1.string3();
        this.longValue2 = var1.longValue();
        this.string3 = var1.string2();
    }

    public String getString23() {
        return this.string2;
    }

    public MicrosoftAccount(String var1) {
        super(AccountType.MICROSOFT, var1);
    }
}
