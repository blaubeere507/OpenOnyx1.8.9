package client.onyx.account;

public enum AccountType {
    OFFLINE("offline", "Offline", false),
    ACCESS_TOKEN("access_token", "Access Token", true),
    MICROSOFT("microsoft", "Microsoft", true);

    private final String string;
    private final String string2;
    private final boolean flag;

    public String getString() {
        return this.string;
    }

    private AccountType(String var3, String var4, boolean var5) {
        this.string = var3;
        this.string2 = var4;
        this.flag = var5;
    }

    public static AccountType method_1823(String var0) {
        String var3 = var0;
        AccountType[] var1;
        int var2 = (var1 = values()).length;
        int var6 = 0;

        for (int var5 = 0; var5 < var2; var5 = ++var6) {
            AccountType var4;
            if ((var4 = var1[var6]).string.equalsIgnoreCase(var3)) {
                return var4;
            }
        }

        return null;
    }

    public boolean isFlag() {
        return this.flag;
    }

    public String getString2() {
        return this.string2;
    }
}
