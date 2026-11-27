package client.onyx.account;

import client.onyx.Onyx;
import client.onyx.account.microsoft.AuthException;
import client.onyx.util.IMinecraft;
import client.onyx.util.Workers;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import net.minecraft.util.Session;

public class AccountManager implements IMinecraft {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String STRING_CONST2 = UUID.randomUUID().toString();
    private static final String STRING_CONST = "accounts.json";
    private static final int INT_11 = 1;
    private static final String STRING_CONST3 = "accounts.json.tmp";
    private final List<Account> list = new ArrayList<>();
    private final ProxyConfig proxyConfig = new ProxyConfig();
    private Session session;
    private Account account;

    public Proxy method_1829() {
        return this.proxyConfig.method_1791();
    }

    public void method_1830() {
        var var2 = this;
        if (Files.isRegularFile(this.method_1835())) {
            JsonObject var1;
            try {
                String var7 = new String(Files.readAllBytes(var2.method_1835()), StandardCharsets.UTF_8);
                var1 = new JsonParser().parse(var7).getAsJsonObject();
            } catch (RuntimeException | IOException var6) {
                Onyx.LOGGER.error("Failed to read accounts", var6);
                return;
            }

            JsonElement var8;
            if ((var8 = var1.get("proxy")) != null && var8.isJsonObject()) {
                this.proxyConfig.method_1798(var8.getAsJsonObject());
            }

            JsonElement var11;
            if ((var11 = var1.get("accounts")) != null && var11.isJsonArray()) {
                this.list.clear();
                Iterator var12 = var11.getAsJsonArray().iterator();

                label43:
                while (true) {
                    for (Iterator var4 = var12; var4.hasNext(); var4 = var12) {
                        JsonElement var9;
                        if ((var9 = (JsonElement)var12.next()).isJsonObject()) {
                            try {
                                Account var10;
                                if ((var10 = Account.method_1814(var9.getAsJsonObject())) != null) {
                                    var2.list.add(var10);
                                }
                            } catch (RuntimeException var5) {
                                Onyx.LOGGER.warn("Skipping malformed account entry", var5);
                            }
                            continue label43;
                        }
                    }

                    return;
                }
            }
        }
    }

    public Account getAccount() {
        return this.account;
    }

    private void method_1831(Session var1) {
        if (this.session == null) {
            this.session = mc.getSession();
        }

        mc.setSession(var1);
    }

    public void method_1832(Account var1) {
        this.list.add(var1);
        this.method_1833();
    }

    public boolean method_1833() {
        var var3 = this;
        JsonArray var4 = new JsonArray();
        Iterator var10;
        synchronized (this.list) {
            for (Iterator var6 = var10 = var3.list.iterator(); var6.hasNext(); var6 = var10) {
                Account var1 = (Account)var10.next();
                var4.add(var1.method_1811());
            }
        }

        JsonObject var12 = new JsonObject();
        var12.addProperty("version", 1);
        var12.add("accounts", var4);
        var12.add("proxy", this.proxyConfig.method_1800());
        Path var11 = this.method_1834().resolve("accounts.json.tmp");

        try {
            Files.createDirectories(var3.method_1834());
            Files.write(var11, gson.toJson((JsonElement)var12).getBytes(StandardCharsets.UTF_8));

            try {
                Files.move(var11, var3.method_1835(), StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            } catch (AtomicMoveNotSupportedException var7) {
                Files.move(var11, var3.method_1835(), StandardCopyOption.REPLACE_EXISTING);
            }

            return true;
        } catch (IOException var8) {
            Onyx.LOGGER.error("Failed to save accounts", var8);
            return false;
        }
    }

    public Path method_1834() {
        return new File(mc.mcDataDir, "onyx").toPath();
    }

    public Path method_1835() {
        return this.method_1834().resolve("accounts.json");
    }

    public ProxyConfig getProxyConfig() {
        return this.proxyConfig;
    }

    public Proxy method_1836() {
        return this.proxyConfig.method_1790();
    }

    public List<Account> method_1837() {
        return List.copyOf(this.list);
    }

    public static String getStringConst2() {
        return STRING_CONST2;
    }

    public CompletableFuture<Void> method_1838() {
        Account var2;
        return (var2 = this.account) == null ? CompletableFuture.completedFuture(null) : this.method_1839(var2);
    }

    public CompletableFuture<Void> method_1839(Account var1) {
        return CompletableFuture.runAsync(() -> {
            Session var2;
            try {
                var2 = var1.method_1810();
            } catch (AuthException var5) {
                throw new AccountManager.LoginException(var5.getMessage());
            } catch (RuntimeException var6) {
                Onyx.LOGGER.error("Unexpected error logging into '{}'", var1.getString(), var6);
                throw new AccountManager.LoginException(new StringBuilder().insert(0, "Unexpected error: ").append(var6).toString());
            }

            this.method_1833();
            mc.addScheduledTask(() -> {
                this.method_1831(var2);
                this.account = var1;
            });
        }, Workers.method_1935());
    }

    public CompletableFuture<Void> method_1840(Account var1) {
        return CompletableFuture.runAsync(() -> {
            Account var4 = var1;

            try {
                var4.method_1808();
            } catch (AuthException var3) {
                throw new AccountManager.LoginException(var3.getMessage());
            }

            this.method_1833();
        }, Workers.method_1935());
    }

    public void method_1841(Account var1) {
        if (this.list.remove(var1)) {
            if (this.account == var1) {
                this.account = null;
            }

            this.method_1833();
        }
    }

    public void method_1842() {
        if (this.session != null) {
            mc.setSession(this.session);
            this.account = null;
        }
    }

    public static class LoginException extends RuntimeException {
        public LoginException(String var1) {
            super(var1);
        }
    }
}
