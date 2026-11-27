package client.onyx.account;

import client.onyx.account.microsoft.AuthException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.UUID;
import net.minecraft.util.Session;

public abstract class Account {
    private final AccountType accountType;
    public String string;
    public long longValue;
    public UUID uuid;

    public static long method_1807(JsonObject var0, String var1, long var2) {
        JsonElement var5;
        return (var5 = var0.get(var1)) != null && var5.isJsonPrimitive() && var5.getAsJsonPrimitive().isNumber() ? var5.getAsLong() : var2;
    }

    public Account(AccountType var1, String var2) {
        this.accountType = var1;
        this.string = var2;
        this.longValue = System.currentTimeMillis();
    }

    public String getString() {
        return this.string;
    }

    public abstract void method_1808() throws AuthException;

    public UUID getUuid() {
        return this.uuid;
    }

    public String getString22() {
        return null;
    }

    public String getString2() {
        return null;
    }

    public static String method_1809(JsonObject var0, String var1) {
        JsonElement var3;
        return (var3 = var0.get(var1)) != null && var3.isJsonPrimitive() && var3.getAsJsonPrimitive().isString() ? var3.getAsString() : null;
    }

    public long getLongValue2() {
        return 0L;
    }

    public abstract Session method_1810() throws AuthException;

    public final JsonObject method_1811() {
        JsonObject var2 = new JsonObject();
        var2.addProperty("type", this.accountType.getString());
        var2.addProperty("name", this.string);
        var2.addProperty("addedAt", this.longValue);
        if (this.uuid != null) {
            var2.addProperty("uuid", this.uuid.toString());
        }

        this.method_1812(var2);
        return var2;
    }

    public abstract void method_1812(JsonObject var1);

    public static boolean method_1813(JsonObject var0, String var1, boolean var2) {
        JsonElement var4;
        return (var4 = var0.get(var1)) != null && var4.isJsonPrimitive() && var4.getAsJsonPrimitive().isBoolean() ? var4.getAsBoolean() : var2;
    }

    public long getLongValue() {
        return this.longValue;
    }

    public static Account method_1814(JsonObject var0) {
        client.onyx.account.Account var8; // declared by DeclareMissing
        AccountType var1;
        if ((var1 = AccountType.method_1823(method_1809(var0, "type"))) == null) {
            return null;
        } else {
            String var6;
            if ((var6 = method_1809(var0, "name")) != null && !var6.isBlank()) {
                JsonObject var4;
                label37: {
                    var8 = switch (var1) {
                        case OFFLINE -> new OfflineAccount(var6);
                        case ACCESS_TOKEN -> new AccessTokenAccount(var6);
                        case MICROSOFT -> new MicrosoftAccount(var6);
                    };
                    String var7;
                    label36:
                    if ((var7 = method_1809(var0, "uuid")) != null) {
                        try {
                            ((Account)var8).uuid = UUID.fromString(var7);
                        } catch (IllegalArgumentException var5) {
                            break label36;
                        }

                        var4 = var0;
                        break label37;
                    }

                    var4 = var0;
                }

                JsonElement var3;
                if ((var3 = var4.get("addedAt")) != null && var3.isJsonPrimitive() && var3.getAsJsonPrimitive().isNumber()) {
                    ((Account)var8).longValue = var3.getAsLong();
                }

                ((Account)var8).method_1815(var0);
                return (Account)var8;
            } else {
                return null;
            }
        }
    }

    public abstract void method_1815(JsonObject var1);

    public AccountType getAccountType() {
        return this.accountType;
    }
}
