package client.onyx.ui.account;

import client.onyx.Onyx;
import client.onyx.account.Account;
import client.onyx.account.AccountHeads;
import client.onyx.account.AccountManager;
import client.onyx.account.AccountType;
import client.onyx.input.KeyTyped;
import client.onyx.input.MouseClick;
import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easings;
import client.onyx.render.font.FontStyle;
import client.onyx.render.ui.PanelRenderer;
import client.onyx.render.ui.UiRenderer;
import client.onyx.ui.component.Button;
import client.onyx.ui.component.Component;
import client.onyx.ui.component.Grid;
import client.onyx.ui.component.Label;
import client.onyx.ui.component.RenderContext;
import client.onyx.ui.component.Tooltip;
import client.onyx.ui.theme.ColorRoles;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.Typography;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;

public class AccountPanel extends Component {
    private static final float FLOAT_34_01 = 34.0F;
    private static final float FLOAT_36_01 = 36.0F;
    private static float floatValue42;
    private static final float FLOAT_12_01 = 12.0F;
    private static float floatValue6;
    private static final float FLOAT_82_01 = 82.0F;
    private static final float FLOAT_12_02 = 12.0F;
    private static final float FLOAT_400_01 = 400.0F;
    private static final float FLOAT_4_01 = 4.0F;
    private static final float FLOAT_96_01 = 96.0F;
    private static final String STRING_CONST = "Accounts";
    private static final float FLOAT_46_01 = 46.0F;
    private static final float FLOAT_560_01 = 560.0F;
    private final AddAccountPanel addAccountPanel = new AddAccountPanel();
    private final ProxySettingsPanel proxySettingsPanel = new ProxySettingsPanel();
    private final Grid grid = new Grid();
    private final Label label = new Label();
    private final Animation animation = new Animation(0.0F);
    private float floatValue22 = 1.0F;
    private final AccountListView accountListView;
    private final Button button2;
    private final Button button;
    private final Button button3;
    private final Tooltip tooltip;
    private float floatValue5;
    private boolean flag4;
    private float floatValue32;
    private float floatValue52;
    private boolean flag22;
    private float floatValue7;
    private float floatValue8;
    private float floatValue9;
    private boolean flag32;
    private AccountRow accountRow;

    @Override
    public boolean method_2189(float var1, float var2, int var3) {
        this.flag32 = false;
        if (this.flag22) {
            return this.addAccountPanel.method_2189(var1, var2, var3);
        } else {
            return this.flag4
                ? this.proxySettingsPanel.method_2189(var1, var2, var3)
                : this.button2.method_2189(var1, var2, var3)
                    | this.button.method_2189(var1, var2, var3)
                    | this.button3.method_2189(var1, var2, var3)
                    | this.accountListView.method_2189(var1, var2, var3);
        }
    }

    private void method_2292(RenderContext var1, UiRenderer var2) {
        float var3 = this.floatValue + this.floatValue2 - 17.0F;
        var2.method_434(this.floatValue3 + 12.0F, this.floatValue + this.floatValue2 - 34.0F, this.floatValue4 - 24.0F, 1.0F, var1.method_2256().intValue12());
        var2.method_432(
            Typography.fontStyle2,
            var2.method_469(Typography.fontStyle2, this.method_2300(), this.button.getFloatValue3() - this.floatValue3 - 24.0F),
            this.floatValue3 + 12.0F + 4.0F,
            var3,
            var1.method_2256().intValue7()
        );
        this.button.method_2186(var1);
        this.button3.method_2186(var1);
    }

    private void method_2293(float var1, float var2) {
        this.method_2258(
            Math.round(Math.clamp(var1, 96.0F - this.floatValue4, this.floatValue9 - 96.0F)),
            Math.round(Math.clamp(var2, 0.0F, this.floatValue52 - 46.0F)),
            this.floatValue4,
            this.floatValue2
        );
        floatValue42 = this.floatValue3 - this.floatValue8;
        floatValue6 = this.floatValue - this.floatValue32;
    }

    private static String method_2294(Throwable var0) {
        Throwable var2;
        if (var0.getCause() == null) {
            var2 = var0;
        } else {
            var2 = var0.getCause();
        }

        return var2.getMessage() != null ? var2.getMessage() : var2.toString();
    }

    private void method_2295(String var1, String var2) {
        if (var2 != null && !var2.isBlank()) {
            GuiScreen.setClipboardString(var2);
            this.label.method_2401(new StringBuilder().insert(0, var1).append(" copied").toString());
        } else {
            this.method_2308("Nothing to copy");
        }
    }

    @Override
    public boolean method_2183(float var1, float var2, int var3) {
        if (this.grid.method_2183(var1, var2, var3)) {
            return true;
        } else {
            if (this.flag22) {
                if (this.tooltip.method_2183(var1, var2, var3)) {
                    return true;
                }

                if (this.addAccountPanel.method_2183(var1, var2, var3)) {
                    return true;
                }
            } else if (this.flag4) {
                if (this.tooltip.method_2183(var1, var2, var3)) {
                    return true;
                }

                if (this.proxySettingsPanel.method_2183(var1, var2, var3)) {
                    return true;
                }
            } else {
                if (this.button2.method_2183(var1, var2, var3)) {
                    return true;
                }

                if (this.button.method_2183(var1, var2, var3)) {
                    return true;
                }

                if (this.button3.method_2183(var1, var2, var3)) {
                    return true;
                }

                if (this.accountListView.method_2183(var1, var2, var3)) {
                    return true;
                }
            }

            if (var3 == 0
                && var1 >= this.floatValue3
                && var1 < this.floatValue3 + this.floatValue4
                && var2 >= this.floatValue
                && var2 < this.floatValue + 46.0F) {
                this.flag32 = true;
                this.floatValue7 = var1 - this.floatValue3;
                this.floatValue5 = var2 - this.floatValue;
                return true;
            } else {
                return this.method_2259(var1, var2);
            }
        }
    }

    public float method_2296() {
        return this.floatValue3 + this.floatValue4 / 2.0F;
    }

    private AccountManager method_2297() {
        return Onyx.accountManager;
    }

    @Override
    public boolean method_2211(float var1, float var2, double var3) {
        return this.grid.method_2211(var1, var2, var3) || !this.flag22 && !this.flag4 && this.accountListView.method_2211(var1, var2, var3);
    }

    private List<Grid.Cell> method_2298(AccountRow var1) {
        Account var2 = var1.getAccount();
        ArrayList var4;
        (var4 = new ArrayList()).add(Grid.Cell.method_2382("\ue7fd", "Copy username", () -> this.method_2295("Username", var2.getString())));
        var4.add(Grid.Cell.method_2382("\ue14d", "Copy UUID", () -> {
            AccountPanel var4x;
            String var5;
            String var6;
            if (var2.getUuid() != null) {
                String var3 = "UUID";
                var6 = var2.getUuid().toString();
                var5 = var3;
                var4x = this;
            } else {
                var6 = null;
                var5 = "UUID";
                var4x = this;
            }

            var4x.method_2295(var5, var6);
        }));
        if (var2.getAccountType() != AccountType.OFFLINE) {
            var4.add(Grid.Cell.method_2382("\ue73c", "Copy access token", () -> this.method_2295("Access token", var2.getString22())));
            if (var2.getString2() != null) {
                var4.add(Grid.Cell.method_2382("\ue73c", "Copy OAuth refresh token", () -> this.method_2295("Refresh token", var2.getString2())));
            }

            var4.add(Grid.Cell.method_2383());
            var4.add(Grid.Cell.method_2382("\ue5d5", "Refresh tokens", () -> this.method_2299(var1)));
        }

        var4.add(Grid.Cell.method_2383());
        var4.add(Grid.Cell.method_2386("\ue872", "Delete", () -> this.method_2306(var2)));
        return var4;
    }

    private void method_2299(AccountRow var1) {
        var1.method_2531(true);
        this.method_2297().method_1840(var1.getAccount()).whenComplete((var2, var3) -> mc.addScheduledTask(() -> {
            var1.method_2531(false);
            if (var3 == null) {
                this.label.method_2401(new StringBuilder().insert(0, "Refreshed ").append(var1.getAccount().getString()).toString());
            } else {
                this.method_2308(method_2294(var3));
            }
        }));
    }

    @Override
    public boolean method_2212(KeyTyped var1) {
        if (this.grid.method_2212(var1)) {
            return true;
        } else {
            return this.flag22 ? this.addAccountPanel.method_2212(var1) : this.flag4 && this.proxySettingsPanel.method_2212(var1);
        }
    }

    private String method_2300() {
        Account var5 = this.method_2297().getAccount();
        StringBuilder var10000 = new StringBuilder().insert(0, "Session: ");
        StringBuilder var3;
        String var4;
        if (var5 != null) {
            StringBuilder var2 = var10000;
            var4 = var5.getString();
            var3 = var2;
        } else {
            var4 = mc.getSession().getUsername();
            var3 = var10000;
        }

        return var3.append(var4).toString();
    }

    public void method_2301(float var1) {
        this.floatValue22 = var1;
    }

    public AccountPanel() {
        this.floatValue4 = 560.0F;
        this.floatValue2 = 400.0F;
        this.accountListView = new AccountListView(var1 -> {
            if (var1.getAccount() != this.method_2297().getAccount()) {
                var1.method_2531(true);
                this.method_2297().method_1839(var1.getAccount()).whenComplete((var2, var3) -> mc.addScheduledTask(() -> {
                    var1.method_2531(false);
                    if (var3 == null) {
                        this.label.method_2401(new StringBuilder().insert(0, "Logged in as ").append(var1.getAccount().getString()).toString());
                    } else {
                        this.method_2308(method_2294(var3));
                    }
                }));
            }
        }, var1 -> this.accountRow = var1);
        this.button2 = new Button(Button.Variant.FILLED, "Add", "\ue145", this::lambda$AccountPanel2);
        this.button = new Button(Button.Variant.TEXT, "Proxy settings", "\ue8b8", () -> {
            if (!this.flag22 && !this.flag4) {
                this.grid.method_2482();
                this.proxySettingsPanel.method_2395();
                this.flag4 = true;
                this.animation.method_603(1.0F, 300.0F, Easings.easing2);
            }
        });
        this.button3 = new Button(Button.Variant.TEXT, "Restore launcher", () -> {
            this.method_2297().method_1842();
            this.label.method_2401("Restored the launcher account");
        });
        this.tooltip = new Tooltip("\ue5c4", this::method_2307);
        this.addAccountPanel.method_2598(var1 -> {
            this.accountListView.method_2278();
            this.method_2307();
            this.label.method_2401(new StringBuilder().insert(0, "Added ").append(var1.getString()).toString());
        });
        this.addAccountPanel.method_2604(this::method_2308);
        this.addAccountPanel.method_2608(this.label::method_2401);
        this.proxySettingsPanel.method_2392(this.label::method_2401);
        this.proxySettingsPanel.method_2393(this::method_2308);
        this.list.add(this.button2);
        this.list.add(this.accountListView);
        this.list.add(this.button);
        this.list.add(this.button3);
        this.list.add(this.tooltip);
        this.list.add(this.addAccountPanel);
        this.list.add(this.proxySettingsPanel);
    }

    public void lambda$AccountPanel2() {
        if (!this.flag22 && !this.flag4) {
            this.grid.method_2482();
            this.addAccountPanel.method_2607();
            this.flag22 = true;
            this.animation.method_603(1.0F, 300.0F, Easings.easing2);
        }
    }

    @Override
    public void method_2194() {
        float var1 = this.floatValue + 46.0F;
        float var3 = this.floatValue4 - 24.0F;
        this.tooltip.method_2258(this.floatValue3 + 12.0F, this.method_2303() - 15.0F, 30.0F, 30.0F);
        this.button2.method_2258(this.floatValue3 + this.floatValue4 - 12.0F - 82.0F, this.method_2303() - 15.0F, 82.0F, 30.0F);
        this.accountListView.method_2258(this.floatValue3 + 12.0F, var1, var3, this.floatValue2 - 46.0F - 34.0F);
        this.addAccountPanel.method_2258(this.floatValue3 + 12.0F, var1, var3, this.floatValue2 - 46.0F - 12.0F);
        this.proxySettingsPanel.method_2258(this.floatValue3 + 12.0F, var1, var3, this.floatValue2 - 46.0F - 12.0F);
        this.label.method_2258(this.floatValue3, this.floatValue, this.floatValue4, this.floatValue2);
    }

    private void method_2302(RenderContext var1) {
        UiRenderer var8 = var1.uiRenderer;
        float var2 = this.floatValue + this.floatValue2 - 17.0F;
        float var3 = this.button3.method_2464(var8);
        this.button3.method_2258(this.floatValue3 + this.floatValue4 - 12.0F - var3, var2 - 15.0F, var3, 30.0F);
        Button var11 = this.button3;
        Button var5;
        boolean var6;
        if (this.method_2297().getAccount() == null) {
            Button var4 = var11;
            var6 = false;
            var5 = var4;
        } else {
            var6 = true;
            var5 = var11;
        }

        var5.method_2260(var6);
        float var9 = this.button.method_2464(var8);
        this.button.method_2258(this.button3.getFloatValue3() - var9, var2 - 15.0F, var9, 30.0F);
    }

    @Override
    public void method_2206(RenderContext var1) {
        this.method_2302(var1);
        this.animation.method_605(var1.floatValue);
        this.flag = this.method_2259(var1.floatValue3, var1.floatValue2);
        float var2;
        if ((var2 = this.animation.method_606()) < 0.999F) {
            this.button2.method_2206(var1);
            this.accountListView.method_2206(var1);
            this.button.method_2206(var1);
            this.button3.method_2206(var1);
        }

        AccountPanel var4;
        label22: {
            if (var2 > 0.001F) {
                this.tooltip.method_2206(var1);
                if (this.flag22) {
                    this.addAccountPanel.method_2206(var1);
                    var4 = this;
                    break label22;
                }

                this.proxySettingsPanel.method_2206(var1);
            }

            var4 = this;
        }

        if (var4.accountRow != null) {
            this.grid.method_2481(this.method_2298(this.accountRow), this.accountRow.method_2529(), this, var1.uiRenderer);
            this.accountRow = null;
        }

        this.grid.method_2206(var1);
        this.label.method_2206(var1);
    }

    private float method_2303() {
        return this.floatValue + 23.0F;
    }

    @Override
    public boolean method_2193(MouseClick var1) {
        return this.flag22 ? this.addAccountPanel.method_2193(var1) : this.flag4 && this.proxySettingsPanel.method_2193(var1);
    }

    @Override
    public void method_2186(RenderContext var1) {
        UiRenderer var13 = var1.uiRenderer;
        UiRenderer var10000 = var1.uiRenderer;
        UiRenderer var10003 = var1.uiRenderer;
        UiRenderer var10002 = var1.uiRenderer;
        var13.method_440(this.floatValue22);
        var10003.method_485(this.floatValue3, this.floatValue, this.floatValue4, this.floatValue2, 28.0F, 3);
        var10002.method_430();
        PanelRenderer.method_598(
            var10000,
            this.floatValue3,
            this.floatValue,
            this.floatValue4,
            this.floatValue2,
            28.0F,
            Theme.method_180(ColorRoles.method_182(2, var1.method_2256()), this.floatValue22)
        );
        float var3;
        if ((var3 = this.animation.method_606()) < 0.999F) {
            float var4;
            float var10004 = var4 = this.floatValue22 * (1.0F - var3);
            var13.method_432(
                Typography.fontStyle18,
                "Accounts",
                this.floatValue3 + 12.0F + 4.0F,
                this.method_2303(),
                Theme.method_180(var1.method_2256().intValue15(), var4)
            );
            var13.method_440(var10004);
            this.button2.method_2186(var1);
            var13.method_430();
            var13.method_440(var4);
            var13.method_441();
            var13.method_451(-36.0F * var3, 0.0F);
            this.accountListView.method_2186(var1);
            this.method_2292(var1, var13);
            var13.method_435();
            var13.method_430();
        }

        if (var3 > 0.001F) {
            float var14;
            var13.method_440(var14 = this.floatValue22 * var3);
            this.tooltip.method_2186(var1);
            UiRenderer var7;
            FontStyle var8;
            String var9;
            AccountPanel var10;
            if (!this.flag22) {
                FontStyle var6 = Typography.fontStyle18;
                var10 = this;
                var9 = "Proxy settings";
                var8 = var6;
                var7 = var13;
            } else {
                var10 = this;
                var9 = "Add account";
                var8 = Typography.fontStyle18;
                var7 = var13;
            }

            var7.method_432(var8, var9, var10.tooltip.getFloatValue3() + 30.0F + 6.0F, this.method_2303(), var1.method_2256().intValue15());
            var13.method_430();
            var13.method_440(var14);
            var13.method_441();
            var13.method_451(36.0F * (1.0F - var3), 0.0F);
            UiRenderer var12;
            if (!this.flag22) {
                this.proxySettingsPanel.method_2186(var1);
                var12 = var13;
            } else {
                this.addAccountPanel.method_2186(var1);
                var12 = var13;
            }

            var12.method_435();
            var13.method_430();
        }

        this.grid.method_2186(var1);
        this.label.method_2186(var1);
    }

    public void method_2304(float var1, float var2) {
        this.floatValue9 = var1;
        this.floatValue52 = var2;
        this.floatValue8 = (var1 - this.floatValue4) / 2.0F;
        this.floatValue32 = (var2 - this.floatValue2) / 2.0F;
        this.method_2293(this.floatValue8 + floatValue42, this.floatValue32 + floatValue6);
    }

    @Override
    public boolean method_2188(float var1, float var2, int var3, float var4, float var5) {
        if (!this.flag32) {
            return false;
        } else {
            this.method_2293(var1 - this.floatValue7, var2 - this.floatValue5);
            return true;
        }
    }

    public float method_2305() {
        return this.floatValue + this.floatValue2 / 2.0F;
    }

    private void method_2306(Account var1) {
        AccountHeads.method_1820(var1.getUuid());
        this.method_2297().method_1841(var1);
        this.accountListView.method_2278();
        this.label.method_2401(new StringBuilder().insert(0, "Removed ").append(var1.getString()).toString());
    }

    public boolean method_2307() {
        if (!this.flag22 && !this.flag4) {
            return false;
        } else {
            if (this.flag22) {
                this.addAccountPanel.method_2607();
            }

            this.flag22 = false;
            this.flag4 = false;
            this.animation.method_603(0.0F, 300.0F, Easings.easing2);
            return true;
        }
    }

    private void method_2308(String var1) {
        this.label.method_2398(var1, "\ue001");
    }
}
