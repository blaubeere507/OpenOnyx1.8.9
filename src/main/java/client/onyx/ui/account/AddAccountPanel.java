package client.onyx.ui.account;

import client.onyx.Onyx;
import client.onyx.account.AccessTokenAccount;
import client.onyx.account.Account;
import client.onyx.account.MicrosoftAccount;
import client.onyx.account.OfflineAccount;
import client.onyx.account.microsoft.AuthException;
import client.onyx.account.microsoft.MicrosoftAuth;
import client.onyx.account.microsoft.XboxLiveAuth;
import client.onyx.input.KeyTyped;
import client.onyx.input.MouseClick;
import client.onyx.render.ui.UiRenderer;
import client.onyx.ui.clickgui.ModuleRow;
import client.onyx.ui.component.Button;
import client.onyx.ui.component.Component;
import client.onyx.ui.component.IconButton;
import client.onyx.ui.component.RenderContext;
import client.onyx.ui.component.SearchField;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.Typography;
import client.onyx.util.Workers;
import java.util.function.Consumer;
import net.minecraft.client.gui.GuiScreen;

public class AddAccountPanel extends Component {
    private static final String[] stringArray = new String[]{"Offline", "Access", "Refresh", "Microsoft"};
    private static final int INT_01 = 0;
    private static final int INT_11 = 1;
    private static final float FLOAT_8_01 = 8.0F;
    private static final float FLOAT_10_01 = 10.0F;
    private static final float FLOAT_10_02 = 10.0F;
    private static final float FLOAT_16_01 = 16.0F;
    private static final int INT_31 = 3;
    private static final float FLOAT_14_01 = 14.0F;
    private static final float FLOAT_96_01 = 96.0F;
    private static final float FLOAT_14_02 = 14.0F;
    private static final int INT_21 = 2;
    private final ModuleRow moduleRow = new ModuleRow(stringArray, var1 -> {
        this.searchField3.method_2407(false);
        this.searchField.method_2407(false);
        this.searchField2.method_2407(false);
        if (var1 != 3) {
            this.method_2601();
        }
    });
    private final SearchField searchField3 = new SearchField("Username");
    private final SearchField searchField = new SearchField("Minecraft access token");
    private final SearchField searchField2 = new SearchField("Microsoft OAuth refresh token");
    private final IconButton iconButton = new IconButton(() -> this.flag4, var1 -> this.flag4 = var1);
    private final Button button3 = new Button(Button.Variant.FILLED, "Add", () -> {
        String var2;
        if ((var2 = this.searchField3.method_2404().trim()).isEmpty()) {
            this.method_2596("Enter a username");
        } else {
            OfflineAccount var3;
            (var3 = new OfflineAccount(var2)).method_1843(this.flag4);
            if (!this.flag4) {
                this.method_2599(var3);
            } else {
                this.button3.method_2462(true);
                Workers.method_1936(() -> {
                    OfflineAccount var3x = var3;

                    try {
                        var3x.method_1808();
                    } catch (AuthException var2x) {
                    }

                    mc.addScheduledTask(() -> {
                        this.button3.method_2462(false);
                        this.method_2599(var3);
                    });
                });
            }
        }
    });
    private final Button button4 = new Button(Button.Variant.FILLED, "Add", () -> {
        String var2;
        if ((var2 = this.searchField.method_2404().trim()).isEmpty()) {
            this.method_2596("Paste an access token");
        } else {
            this.button4.method_2462(true);
            Workers.method_1936(() -> {
        client.onyx.account.AccessTokenAccount var5; // declared by DeclareMissing
                String var4 = var2;

                try {
                    var5 = AccessTokenAccount.method_1828(var4);
                } catch (AuthException var3) {
                    mc.addScheduledTask(() -> {
                        this.button4.method_2462(false);
                        this.method_2596(var3.getMessage());
                    });
                    return;
                }

                mc.addScheduledTask(() -> {
                    this.button4.method_2462(false);
                    this.method_2599(var5);
                });
            });
        }
    });
    private final Button button2 = new Button(Button.Variant.FILLED, "Add", () -> {
        String var2;
        if ((var2 = this.searchField2.method_2404().trim()).isEmpty()) {
            this.method_2596("Paste a refresh token");
        } else {
            this.button2.method_2462(true);
            Workers.method_1936(() -> {
        client.onyx.account.MicrosoftAccount var5; // declared by DeclareMissing
                String var4 = var2;

                try {
                    var5 = MicrosoftAccount.method_1816(var4);
                } catch (AuthException var3) {
                    mc.addScheduledTask(() -> {
                        this.button2.method_2462(false);
                        this.method_2596(var3.getMessage());
                    });
                    return;
                }

                mc.addScheduledTask(() -> {
                    this.button2.method_2462(false);
                    this.method_2599(var5);
                });
            });
        }
    });
    private final Button button = new Button(Button.Variant.FILLED, "Sign in with Microsoft", "\ue89e", () -> {
        var var1 = this;
        if (this.microsoftAuth != null) {
            this.method_2601();
        } else {
            try {
                var1.microsoftAuth = MicrosoftAuth.method_1847(var1x -> {
                    this.method_2601();
                    this.method_2599(MicrosoftAccount.method_1817(var1x));
                }, var1x -> {
                    this.method_2601();
                    this.method_2596(var1x);
                });
            } catch (AuthException var3) {
                this.method_2596(var3.getMessage());
                return;
            }

            this.microsoftAuth.method_1851();
            this.button.method_2463("Cancel");
            this.button.method_2461("\ue5cd");
            this.string = "Waiting for the browser…";
        }
    });
    private final Button button5 = new Button(Button.Variant.TEXT, "Copy link", () -> {
        if (this.microsoftAuth == null) {
            this.method_2596("Start the sign-in first");
        } else {
            GuiScreen.setClipboardString(this.microsoftAuth.getString2());
            if (this.consumer2 != null) {
                this.consumer2.accept("Sign-in link copied");
            }
        }
    });
    private String string;
    private Consumer<String> consumer;
    private MicrosoftAuth microsoftAuth;
    private Consumer<String> consumer2;
    private boolean flag4;
    private Consumer<Account> consumer3;

    @Override
    public boolean method_2183(float var1, float var2, int var3) {
        if (this.moduleRow.method_2183(var1, var2, var3)) {
            return true;
        } else {
            switch (this.method_2605()) {
                case 0:
                    if (!this.searchField3.method_2183(var1, var2, var3)
                        && !this.iconButton.method_2183(var1, var2, var3)
                        && !this.button3.method_2183(var1, var2, var3)) {
                        return false;
                    }

                    return true;
                case 1:
                    if (!this.searchField.method_2183(var1, var2, var3) && !this.button4.method_2183(var1, var2, var3)) {
                        return false;
                    }

                    return true;
                case 2:
                    if (!this.searchField2.method_2183(var1, var2, var3) && !this.button2.method_2183(var1, var2, var3)) {
                        return false;
                    }

                    return true;
                default:
                    return this.button.method_2183(var1, var2, var3) || this.button5.method_2183(var1, var2, var3);
            }
        }
    }

    @Override
    public boolean method_2212(KeyTyped var1) {
        switch (this.method_2605()) {
            case 0:
                return this.searchField3.method_2212(var1);
            case 1:
                return this.searchField.method_2212(var1);
            case 2:
                return this.searchField2.method_2212(var1);
            default:
                return false;
        }
    }

    private void method_2595(RenderContext var1, UiRenderer var2, String var3, float var4) {
        var2.method_432(
            Typography.fontStyle15,
            var2.method_469(Typography.fontStyle15, var3, this.floatValue4),
            this.floatValue3,
            var4 + 8.0F + 7.0F,
            Theme.method_180(var1.method_2256().intValue7(), 0.9F)
        );
    }

    private void method_2596(String var1) {
        if (this.consumer != null) {
            this.consumer.accept(var1);
        }
    }

    @Override
    public void method_2186(RenderContext var1) {
        UiRenderer var2 = var1.uiRenderer;
        this.moduleRow.method_2186(var1);
        switch (this.method_2605()) {
            case 0:
                this.method_2597(var1, var2);
                return;
            case 1:
                this.method_2609(var1, var2);
                return;
            case 2:
                this.method_2606(var1, var2);
                return;
            default:
                this.method_2603(var1, var2);
        }
    }

    private void method_2597(RenderContext var1, UiRenderer var2) {
        this.searchField3.method_2186(var1);
        this.iconButton.method_2186(var1);
        this.button3.method_2186(var1);
        var2.method_432(
            Typography.fontStyle15,
            "Resolve premium UUID",
            this.iconButton.getFloatValue3() + 38.0F + 10.0F,
            this.iconButton.getFloatValue() + 11.0F,
            var1.method_2256().intValue15()
        );
        this.method_2595(var1, var2, "Looks the name up on Mojang so the real skin and UUID are used.", this.button3.getFloatValue() + 30.0F);
    }

    @Override
    public boolean method_2189(float var1, float var2, int var3) {
        switch (this.method_2605()) {
            case 0:
                return this.iconButton.method_2189(var1, var2, var3) | this.button3.method_2189(var1, var2, var3);
            case 1:
                return this.button4.method_2189(var1, var2, var3);
            case 2:
                return this.button2.method_2189(var1, var2, var3);
            default:
                return this.button.method_2189(var1, var2, var3) | this.button5.method_2189(var1, var2, var3);
        }
    }

    @Override
    public boolean method_2193(MouseClick var1) {
        switch (this.method_2605()) {
            case 0:
                return this.searchField3.method_2193(var1);
            case 1:
                return this.searchField.method_2193(var1);
            case 2:
                return this.searchField2.method_2193(var1);
            default:
                return false;
        }
    }

    public void method_2598(Consumer<Account> var1) {
        this.consumer3 = var1;
    }

    private void method_2599(Account var1) {
        Onyx.accountManager.method_1832(var1);
        if (this.consumer3 != null) {
            this.consumer3.accept(var1);
        }
    }

    public AddAccountPanel() {
        this.searchField.method_2414(true);
        this.searchField2.method_2414(true);
        this.searchField3.method_2550(() -> {
            String var2;
            if ((var2 = this.searchField3.method_2404().trim()).isEmpty()) {
                this.method_2596("Enter a username");
            } else {
                OfflineAccount var3;
                (var3 = new OfflineAccount(var2)).method_1843(this.flag4);
                if (!this.flag4) {
                    this.method_2599(var3);
                } else {
                    this.button3.method_2462(true);
                    Workers.method_1936(() -> {
                        OfflineAccount var3x = var3;

                        try {
                            var3x.method_1808();
                        } catch (AuthException var2x) {
                        }

                        mc.addScheduledTask(() -> {
                            this.button3.method_2462(false);
                            this.method_2599(var3);
                        });
                    });
                }
            }
        });
        this.searchField.method_2550(() -> {
            String var2;
            if ((var2 = this.searchField.method_2404().trim()).isEmpty()) {
                this.method_2596("Paste an access token");
            } else {
                this.button4.method_2462(true);
                Workers.method_1936(() -> {
        client.onyx.account.AccessTokenAccount var5; // declared by DeclareMissing
                    String var4 = var2;

                    try {
                        var5 = AccessTokenAccount.method_1828(var4);
                    } catch (AuthException var3) {
                        mc.addScheduledTask(() -> {
                            this.button4.method_2462(false);
                            this.method_2596(var3.getMessage());
                        });
                        return;
                    }

                    mc.addScheduledTask(() -> {
                        this.button4.method_2462(false);
                        this.method_2599(var5);
                    });
                });
            }
        });
        this.searchField2.method_2550(() -> {
            String var2;
            if ((var2 = this.searchField2.method_2404().trim()).isEmpty()) {
                this.method_2596("Paste a refresh token");
            } else {
                this.button2.method_2462(true);
                Workers.method_1936(() -> {
        client.onyx.account.MicrosoftAccount var5; // declared by DeclareMissing
                    String var4 = var2;

                    try {
                        var5 = MicrosoftAccount.method_1816(var4);
                    } catch (AuthException var3) {
                        mc.addScheduledTask(() -> {
                            this.button2.method_2462(false);
                            this.method_2596(var3.getMessage());
                        });
                        return;
                    }

                    mc.addScheduledTask(() -> {
                        this.button2.method_2462(false);
                        this.method_2599(var5);
                    });
                });
            }
        });
        this.list.add(this.moduleRow);
        this.list.add(this.searchField3);
        this.list.add(this.iconButton);
        this.list.add(this.button3);
        this.list.add(this.searchField);
        this.list.add(this.button4);
        this.list.add(this.searchField2);
        this.list.add(this.button2);
        this.list.add(this.button);
        this.list.add(this.button5);
    }

    private void method_2600(RenderContext var1) {
        float var2 = this.floatValue + 30.0F + 14.0F;
        float var3 = this.button.method_2464(var1.uiRenderer);
        float var8 = this.button5.method_2464(var1.uiRenderer);
        this.button.method_2258(this.floatValue3, var2, var3, 30.0F);
        this.button5.method_2258(this.floatValue3 + var3 + 10.0F, var2, var8, 30.0F);
        Button var5;
        boolean var6;
        if (this.microsoftAuth == null) {
            Button var4 = this.button5;
            var6 = false;
            var5 = var4;
        } else {
            var6 = true;
            var5 = this.button5;
        }

        var5.method_2260(var6);
    }

    private void method_2601() {
        if (this.microsoftAuth != null) {
            this.microsoftAuth.method_1850();
        }

        this.microsoftAuth = null;
        this.string = null;
        this.button.method_2463("Sign in with Microsoft");
        this.button.method_2461("\ue89e");
    }

    public boolean method_2602() {
        return this.searchField3.isFlag22() || this.searchField.isFlag22() || this.searchField2.isFlag22();
    }

    private void method_2603(RenderContext var1, UiRenderer var2) {
        this.button.method_2186(var1);
        this.button5.method_2186(var1);
        float var3 = this.button.getFloatValue() + 30.0F;
        if (!XboxLiveAuth.method_1870()) {
            float var4 = var3 + 8.0F + 7.0F;
            var2.method_477("\ue001", this.floatValue3 + 8.0F, var4, 16.0F, var1.method_2256().intValue13());
            var2.method_432(
                Typography.fontStyle15,
                "No Azure client id is configured for this build.",
                this.floatValue3 + 16.0F + 10.0F,
                var4,
                var1.method_2256().intValue13()
            );
        } else {
            if (this.string != null) {
                var2.method_432(Typography.fontStyle15, this.string, this.floatValue3, var3 + 8.0F + 7.0F, var1.method_2256().intValue31());
            }

            AddAccountPanel var10;
            RenderContext var11;
            UiRenderer var12;
            String var13;
            float var14;
            float var15;
            if (this.string != null) {
                String var8 = "Opens your browser. Use Copy link if it does not open by itself.";
                var15 = 14.0F;
                var14 = var3;
                var13 = var8;
                var12 = var2;
                var11 = var1;
                var10 = this;
            } else {
                var15 = 0.0F;
                var14 = var3;
                var13 = "Opens your browser. Use Copy link if it does not open by itself.";
                var12 = var2;
                var11 = var1;
                var10 = this;
            }

            var10.method_2595(var11, var12, var13, var14 + var15);
        }
    }

    public void method_2604(Consumer<String> var1) {
        this.consumer = var1;
    }

    private int method_2605() {
        return this.moduleRow.getIntValue();
    }

    private void method_2606(RenderContext var1, UiRenderer var2) {
        this.searchField2.method_2186(var1);
        this.button2.method_2186(var1);
        this.method_2595(var1, var2, "The msaartifacts refresh token from a launcher. Works without an Azure app.", this.button2.getFloatValue() + 30.0F);
    }

    @Override
    public void method_2206(RenderContext var1) {
        this.method_2600(var1);
        this.flag = this.method_2259(var1.floatValue3, var1.floatValue2);
        this.moduleRow.method_2206(var1);
        switch (this.method_2605()) {
            case 0:
                this.searchField3.method_2206(var1);
                this.iconButton.method_2206(var1);
                this.button3.method_2206(var1);
                return;
            case 1:
                this.searchField.method_2206(var1);
                this.button4.method_2206(var1);
                return;
            case 2:
                this.searchField2.method_2206(var1);
                this.button2.method_2206(var1);
                return;
            default:
                this.button.method_2206(var1);
                this.button5.method_2206(var1);
        }
    }

    public void method_2607() {
        this.method_2601();
        this.searchField3.method_2409("");
        this.searchField.method_2409("");
        this.searchField2.method_2409("");
        this.searchField3.method_2407(false);
        this.searchField.method_2407(false);
        this.searchField2.method_2407(false);
        this.string = null;
    }

    public void method_2608(Consumer<String> var1) {
        this.consumer2 = var1;
    }

    @Override
    public void method_2194() {
        this.moduleRow.method_2258(this.floatValue3, this.floatValue, this.floatValue4, 30.0F);
        float var2 = this.floatValue + 30.0F + 14.0F;
        this.searchField3.method_2258(this.floatValue3, var2, this.floatValue4, 40.0F);
        this.searchField.method_2258(this.floatValue3, var2, this.floatValue4, 40.0F);
        this.searchField2.method_2258(this.floatValue3, var2, this.floatValue4, 40.0F);
        float var3 = var2 + 40.0F + 10.0F;
        this.iconButton.method_2258(this.floatValue3, var3 + 4.0F, 38.0F, 22.0F);
        this.button3.method_2258(this.floatValue3 + this.floatValue4 - 96.0F, var3, 96.0F, 30.0F);
        this.button4.method_2258(this.floatValue3 + this.floatValue4 - 96.0F, var3, 96.0F, 30.0F);
        this.button2.method_2258(this.floatValue3 + this.floatValue4 - 96.0F, var3, 96.0F, 30.0F);
    }

    private void method_2609(RenderContext var1, UiRenderer var2) {
        this.searchField.method_2186(var1);
        this.button4.method_2186(var1);
        this.method_2595(var1, var2, "A Minecraft session token from a launcher. Expires in ~24h and cannot be renewed.", this.button4.getFloatValue() + 30.0F);
    }
}
