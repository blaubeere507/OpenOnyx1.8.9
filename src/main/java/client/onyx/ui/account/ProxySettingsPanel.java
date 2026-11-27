package client.onyx.ui.account;

import client.onyx.Onyx;
import client.onyx.account.ProxyConfig;
import client.onyx.account.microsoft.AuthException;
import client.onyx.account.microsoft.Http;
import client.onyx.input.KeyTyped;
import client.onyx.render.ui.UiRenderer;
import client.onyx.ui.clickgui.ModuleRow;
import client.onyx.ui.component.Button;
import client.onyx.ui.component.Component;
import client.onyx.ui.component.IconButton;
import client.onyx.ui.component.KeyShortcuts;
import client.onyx.ui.component.RenderContext;
import client.onyx.ui.component.SearchField;
import client.onyx.ui.theme.Typography;
import client.onyx.util.Workers;
import java.util.function.Consumer;

public class ProxySettingsPanel extends Component {
    private static final float FLOAT_14_01 = 14.0F;
    private static final float FLOAT_10_01 = 10.0F;
    private static final float FLOAT_144_01 = 144.0F;
    private static final float FLOAT_28_01 = 28.0F;
    private static final float FLOAT_88_01 = 88.0F;
    private static final int INT_80001 = 8000;
    private static final String STRING_CONST = "https://api.minecraftservices.com/publickeys";
    private static final float FLOAT_10_02 = 10.0F;
    private static final float FLOAT_8_01 = 8.0F;
    private final IconButton iconButton2 = new IconButton(() -> this.flag22, var1 -> this.flag22 = var1);
    private final IconButton iconButton = new IconButton(() -> this.flag4, var1 -> this.flag4 = var1);
    private final ModuleRow moduleRow = new ModuleRow(new String[]{"SOCKS5", "HTTP/HTTPS"}, var1 -> {
        if (var1 < 0 || var1 >= ProxyConfig.Protocol.values().length) {
            ;
        }
    });
    private final SearchField searchField3 = new SearchField("Address");
    private final SearchField searchField2 = new SearchField("Username");
    private final SearchField searchField = new SearchField("Password");
    private final Button button = new Button(Button.Variant.OUTLINED, "Test connection", () -> {
        var var1 = this;
        if (!this.flag22) {
            if (this.consumer != null) {
                this.consumer.accept("Enable a proxy before testing");
            }
        } else {
            try {
                var1.method_2391();
            } catch (IllegalArgumentException var3) {
                if (this.consumer != null) {
                    this.consumer.accept(var3.getMessage());
                }

                return;
            }

            this.button.method_2462(true);
            Workers.method_1936(() -> {
                String var1x = null;

                try {
                    Http.Response var6;
                    if (!(var6 = Http.method_1856("https://api.minecraftservices.com/publickeys", 8000)).method_1846()) {
                        var1x = new StringBuilder().insert(0, "Proxy test returned HTTP ").append(var6.intValue()).toString();
                    }
                } catch (RuntimeException | AuthException var5) {
                    String var4;
                    if (var5.getMessage() != null) {
                        var4 = var5.getMessage();
                    } else {
                        var4 = var5.toString();
                    }

                    var1x = var4;
                }

                String var7 = var1x;
                mc.addScheduledTask(() -> {
                    this.button.method_2462(false);
                    if (var7 == null) {
                        if (this.consumer2 != null) {
                            this.consumer2.accept("Proxy connection verified");
                            return;
                        }
                    } else if (this.consumer != null) {
                        this.consumer.accept(var7);
                    }
                });
            });
        }
    });
    private final Button button2 = new Button(Button.Variant.FILLED, "Save", this::method_2396);
    private Consumer<String> consumer;
    private boolean flag4;
    private boolean flag22;
    private Consumer<String> consumer2;

    public static float method_2390() {
        return 308.0F;
    }

    private void method_2391() {
        Onyx.accountManager
            .getProxyConfig()
            .method_1792(
                this.method_2394(), this.flag22, this.flag4, this.searchField3.method_2404(), this.searchField2.method_2404(), this.searchField.method_2404()
            );
        Onyx.accountManager.method_1833();
    }

    @Override
    public boolean method_2212(KeyTyped var1) {
        if (super.method_2212(var1)) {
            return true;
        } else if (KeyShortcuts.method_2273(var1)) {
            this.method_2396();
            return true;
        } else {
            return var1.intValue() == 15 && this.method_2397(var1.method_3());
        }
    }

    public void method_2392(Consumer<String> var1) {
        this.consumer2 = var1;
    }

    public ProxySettingsPanel() {
        this.searchField.method_2414(true);
        this.searchField3.method_2550(this::method_2396);
        this.searchField2.method_2550(this::method_2396);
        this.searchField.method_2550(this::method_2396);
        this.list.add(this.iconButton2);
        this.list.add(this.iconButton);
        this.list.add(this.moduleRow);
        this.list.add(this.searchField3);
        this.list.add(this.searchField2);
        this.list.add(this.searchField);
        this.list.add(this.button);
        this.list.add(this.button2);
    }

    public void method_2393(Consumer<String> var1) {
        this.consumer = var1;
    }

    private ProxyConfig.Protocol method_2394() {
        return ProxyConfig.Protocol.values()[this.moduleRow.getIntValue()];
    }

    public void method_2395() {
        ProxyConfig var2 = Onyx.accountManager.getProxyConfig();
        this.flag22 = var2.method_1794();
        this.flag4 = var2.method_1802();
        this.moduleRow.method_2561(var2.method_1796().ordinal());
        this.searchField3.method_2409(var2.method_1801());
        this.searchField2.method_2409(var2.method_1789());
        this.searchField.method_2409(var2.method_1788());
        this.searchField3.method_2407(false);
        this.searchField2.method_2407(false);
        this.searchField.method_2407(false);
    }

    public void method_2396() {
        var var1 = this;

        try {
            var1.method_2391();
            Onyx.accountManager.method_1838().whenComplete((var1x, var2x) -> mc.addScheduledTask(() -> {
                if (var2x != null) {
                    if (this.consumer != null) {
                        Consumer var11 = this.consumer;
                        Consumer var4;
                        String var5;
                        if (var2x.getCause() != null) {
                            Consumer var2xx = var11;
                            var5 = var2x.getCause().getMessage();
                            var4 = var2xx;
                        } else {
                            var5 = var2x.getMessage();
                            var4 = var11;
                        }

                        var4.accept(var5);
                    }
                } else {
                    if (this.consumer2 != null) {
                        Consumer var7;
                        String var8;
                        if (!this.flag22) {
                            Consumer var6 = this.consumer2;
                            var8 = "Proxy disabled";
                            var7 = var6;
                        } else {
                            var8 = "Proxy settings applied";
                            var7 = this.consumer2;
                        }

                        var7.accept(var8);
                    }
                }
            }));
        } catch (IllegalArgumentException var2) {
            if (this.consumer != null) {
                this.consumer.accept(var2.getMessage());
            }
        }
    }

    @Override
    public void method_2194() {
        float var2 = this.floatValue;
        this.iconButton2.method_2258(this.floatValue3, var2 + 3.0F, 38.0F, 22.0F);
        float var3 = var2 + 38.0F;
        this.iconButton.method_2258(this.floatValue3, var3 + 3.0F, 38.0F, 22.0F);
        float var4 = var3 + 42.0F;
        this.moduleRow.method_2258(this.floatValue3, var4, this.floatValue4, 30.0F);
        float var5 = var4 + 44.0F;
        this.searchField3.method_2258(this.floatValue3, var5, this.floatValue4, 40.0F);
        float var6 = var5 + 50.0F;
        this.searchField2.method_2258(this.floatValue3, var6, this.floatValue4, 40.0F);
        float var7 = var6 + 50.0F;
        this.searchField.method_2258(this.floatValue3, var7, this.floatValue4, 40.0F);
        float var8 = var7 + 54.0F;
        this.button2.method_2258(this.floatValue3 + this.floatValue4 - 88.0F, var8, 88.0F, 30.0F);
        this.button.method_2258(this.button2.getFloatValue3() - 8.0F - 144.0F, var8, 144.0F, 30.0F);
    }

    @Override
    public void method_2186(RenderContext var1) {
        UiRenderer var2 = var1.uiRenderer;
        var2.method_432(
            Typography.getFontStyle14(),
            "Use proxy",
            this.iconButton2.getFloatValue3() + 38.0F + 10.0F,
            this.iconButton2.getFloatValue() + 11.0F,
            var1.method_2256().intValue15()
        );
        var2.method_432(
            Typography.getFontStyle14(),
            "Use for account login",
            this.iconButton.getFloatValue3() + 38.0F + 10.0F,
            this.iconButton.getFloatValue() + 11.0F,
            var1.method_2256().intValue7()
        );
        super.method_2186(var1);
    }

    private boolean method_2397(boolean var1) {
        SearchField[] var2 = new SearchField[]{this.searchField3, this.searchField2, this.searchField};
        int var4 = -1;
        int var11 = 0;

        for (int var6 = 0; var6 < var2.length; var6 = ++var11) {
            if (var2[var11].isFlag22()) {
                var4 = var11;
            }
        }

        int var8;
        byte var9;
        if (!var1) {
            var9 = 1;
            var8 = var4;
        } else {
            var9 = -1;
            var8 = var4;
        }

        int var12 = Math.floorMod(var8 + var9, var2.length);
        SearchField[] var13 = var2;
        var4 = var2.length;
        int var5 = 0;

        for (int var10 = 0; var10 < var4; var10 = var5) {
            var13[var5++].method_2407(false);
        }

        var2[var12].method_2407(true);
        var2[var12].method_2413();
        return true;
    }
}
