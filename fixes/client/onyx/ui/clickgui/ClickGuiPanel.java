package client.onyx.ui.clickgui;

import client.onyx.Onyx;
import client.onyx.config.ConfigManager;
import client.onyx.input.KeyTyped;
import client.onyx.input.MouseClick;
import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easings;
import client.onyx.render.font.FontStyle;
import client.onyx.render.ui.PanelRenderer;
import client.onyx.render.ui.UiRenderer;
import client.onyx.setting.SettingHistory;
import client.onyx.ui.clickgui.config.ConfigListPanel;
import client.onyx.ui.clickgui.config.ConfigsTab;
import client.onyx.ui.clickgui.setting.SettingListView;
import client.onyx.ui.component.Component;
import client.onyx.ui.component.ComponentHost;
import client.onyx.ui.component.Icons;
import client.onyx.ui.component.KeyShortcuts;
import client.onyx.ui.component.Label;
import client.onyx.ui.component.RenderContext;
import client.onyx.ui.theme.ColorRoles;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.Typography;

public class ClickGuiPanel extends Component {
    private static Category category = Category.COMBAT;
    private static final float FLOAT_36_01 = 36.0F;
    private static final float FLOAT_640_01 = 640.0F;
    private static final String STRING_CONST = "onyx client";
    private static float floatValue42;
    private static final float FLOAT_420_01 = 420.0F;
    private static final float FLOAT_96_01 = 96.0F;
    private static float floatValue8;
    private final SettingListView settingListView = new SettingListView();
    private final ConfigListPanel configListPanel = new ConfigListPanel();
    private final ModuleSearchBar moduleSearchBar = new ModuleSearchBar(var1 -> this.moduleListView.method_2371(var1));
    private final Label label = new Label();
    private final ComponentHost componentHost = new ComponentHost();
    private final Animation animation = new Animation(0.0F);
    private float floatValue32 = 1.0F;
    private ModuleListView moduleListView;
    private final ConfigsTab configsTab;
    private float floatValue5;
    private float floatValue22;
    private boolean flag4;
    private float floatValue52;
    private float floatValue6;
    private float floatValue7;
    private boolean flag22;
    private boolean flag32;
    private float floatValue9;

    private boolean method_2182(KeyTyped var1) {
        if (KeyShortcuts.method_2273(var1)) {
            this.method_2208();
            return true;
        } else if (KeyShortcuts.method_2274(var1)) {
            Label var11 = this.label;
            Label var3;
            String var4;
            if (!SettingHistory.undo()) {
                Label var2 = var11;
                var4 = "Nothing to undo";
                var3 = var2;
            } else {
                var4 = "Undone";
                var3 = var11;
            }

            var3.method_2401(var4);
            return true;
        } else if (KeyShortcuts.method_2276(var1)) {
            Label var10 = this.label;
            Label var6;
            String var7;
            if (!SettingHistory.redo()) {
                Label var5 = var10;
                var7 = "Nothing to redo";
                var6 = var5;
            } else {
                var7 = "Redone";
                var6 = var10;
            }

            var6.method_2401(var7);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean method_2183(float var1, float var2, int var3) {
        if (var3 == 0 && this.method_2196(var1, var2)) {
            this.method_2203();
            return true;
        } else {
            if (this.flag32) {
                if (this.flag4) {
                    if (this.configListPanel.method_2183(var1, var2, var3)) {
                        return true;
                    }
                } else if (this.settingListView.method_2183(var1, var2, var3)) {
                    return true;
                }

                if (var3 == 1 && this.method_2259(var1, var2)) {
                    this.method_2203();
                    return true;
                }
            } else {
                if (this.moduleSearchBar.method_2183(var1, var2, var3)) {
                    return true;
                }

                if (this.configsTab.method_2183(var1, var2, var3)) {
                    return true;
                }

                if (this.moduleListView.method_2183(var1, var2, var3)) {
                    return true;
                }
            }

            if (var3 == 0
                && var1 >= this.floatValue3
                && var1 < this.floatValue3 + this.floatValue4
                && var2 >= this.floatValue
                && var2 < this.floatValue + method_2205()) {
                this.flag22 = true;
                this.floatValue22 = var1 - this.floatValue3;
                this.floatValue9 = var2 - this.floatValue;
                return true;
            } else {
                return this.method_2259(var1, var2);
            }
        }
    }

    private float method_2184() {
        return this.floatValue + method_2205() / 2.0F;
    }

    public float method_2185() {
        return this.floatValue3 + this.floatValue4 / 2.0F;
    }

    @Override
    public void method_2186(RenderContext var1) {
        UiRenderer var6 = var1.uiRenderer;
        var6.method_440(this.floatValue32);
        var6.method_485(this.floatValue3, this.floatValue, this.floatValue4, this.floatValue2, 28.0F, 3);
        PanelRenderer.method_598(
            var6, this.floatValue3, this.floatValue, this.floatValue4, this.floatValue2, 28.0F, ColorRoles.method_182(2, var1.method_2256())
        );
        var6.method_430();
        float var3;
        this.method_2199(var1, var3 = this.animation.method_606());
        if (var3 < 0.999F) {
            var6.method_440(this.floatValue32 * (1.0F - var3));
            var6.method_441();
            var6.method_451(-36.0F * var3, 0.0F);
            this.configsTab.method_2186(var1);
            this.moduleListView.method_2186(var1);
            var6.method_435();
            var6.method_430();
        }

        if (var3 > 0.001F) {
            var6.method_440(this.floatValue32 * var3);
            var6.method_441();
            var6.method_451(36.0F * (1.0F - var3), 0.0F);
            UiRenderer var5;
            if (!this.flag4) {
                this.settingListView.method_2186(var1);
                var5 = var6;
            } else {
                this.configListPanel.method_2186(var1);
                var5 = var6;
            }

            var5.method_435();
            var6.method_430();
        }

        this.label.method_2186(var1);
    }

    private String method_2187() {
        if (!this.moduleListView.method_2376()) {
            return this.configsTab.getCategory().getString();
        } else {
            int var2;
            return (var2 = this.moduleListView.method_2373()) == 1 ? "1 result" : var2 + " results";
        }
    }

    @Override
    public boolean method_2188(float var1, float var2, int var3, float var4, float var5) {
        if (this.flag22) {
            this.method_2195(var1 - this.floatValue22, var2 - this.floatValue9);
            return true;
        } else if (!this.flag32) {
            return this.moduleSearchBar.method_2188(var1, var2, var3, var4, var5);
        } else {
            return !this.flag4
                ? this.settingListView.method_2188(var1, var2, var3, var4, var5)
                : this.configListPanel.method_2188(var1, var2, var3, var4, var5);
        }
    }

    @Override
    public boolean method_2189(float var1, float var2, int var3) {
        this.flag22 = false;
        if (this.flag32) {
            return !this.flag4 ? this.settingListView.method_2189(var1, var2, var3) : this.configListPanel.method_2189(var1, var2, var3);
        } else {
            return this.moduleListView.method_2189(var1, var2, var3);
        }
    }

    private static float method_2190() {
        return 10.0F;
    }

    private boolean method_2191(KeyTyped var1) {
        switch (var1.intValue()) {
            case 200:
                return this.moduleListView.method_2281(0, -1);
            case 201:
            case 202:
            case 204:
            case 206:
            case 207:
            default:
                return false;
            case 203:
                if (!this.moduleSearchBar.isFlag22() && this.moduleListView.method_2281(-1, 0)) {
                    return true;
                }

                return false;
            case 205:
                if (!this.moduleSearchBar.isFlag22() && this.moduleListView.method_2281(1, 0)) {
                    return true;
                }

                return false;
            case 208:
                return this.moduleListView.method_2281(0, 1);
        }
    }

    private static float method_2192() {
        return 62.0F;
    }

    @Override
    public boolean method_2193(MouseClick var1) {
        if (this.flag32) {
            return !this.flag4 ? this.settingListView.method_2193(var1) : this.configListPanel.method_2193(var1);
        } else {
            return this.moduleSearchBar.isFlag22()
                ? this.moduleSearchBar.method_2193(var1)
                : KeyShortcuts.method_2268(var1) && !Character.isWhitespace(var1.intValue2()) && this.moduleSearchBar.method_2366(var1);
        }
    }

    public boolean isFlag32() {
        return this.flag32;
    }

    @Override
    public void method_2194() {
        float var1 = this.floatValue + method_2205();
        float var3 = this.floatValue2 - method_2205() - method_2190();
        this.configsTab.method_2258(this.floatValue3 + method_2190(), var1, method_2192(), var3);
        this.settingListView.method_2258(this.floatValue3 + method_2190(), var1, this.floatValue4 - method_2190() * 2.0F, var3);
        this.configListPanel.method_2258(this.floatValue3 + method_2190(), var1, this.floatValue4 - method_2190() * 2.0F, var3);
        this.label.method_2258(this.floatValue3, this.floatValue, this.floatValue4, this.floatValue2);
    }

    private void method_2195(float var1, float var2) {
        this.method_2258(
            Math.round(Math.clamp(var1, 96.0F - this.floatValue4, this.floatValue6 - 96.0F)),
            Math.round(Math.clamp(var2, 0.0F, this.floatValue5 - method_2205())),
            this.floatValue4,
            this.floatValue2
        );
        floatValue8 = this.floatValue3 - this.floatValue7;
        floatValue42 = this.floatValue - this.floatValue52;
    }

    private boolean method_2196(float var1, float var2) {
        return this.flag32
            && var1 >= this.method_2214()
            && var1 < this.method_2214() + method_2209()
            && var2 >= this.method_2200()
            && var2 < this.method_2200() + method_2209();
    }

    public float method_2197() {
        return this.floatValue + this.floatValue2 / 2.0F;
    }

    public void method_2198(Module var1) {
        this.moduleSearchBar.method_2407(false);
        this.settingListView.method_2558(var1);
        this.flag4 = false;
        this.flag32 = true;
        this.animation.method_603(1.0F, 300.0F, Easings.easing2);
    }

    private void method_2199(RenderContext var1, float var2) {
        UiRenderer var4 = var1.uiRenderer;
        if (var2 < 0.999F) {
            float var3;
            float var10003 = var3 = this.floatValue32 * (1.0F - var2);
            var4.method_432(
                Typography.getFontStyle18(),
                "onyx client",
                this.floatValue3 + method_2190() + 6.0F,
                this.method_2184(),
                Theme.method_180(var1.method_2256().intValue15(), var3)
            );
            var4.method_476(
                Typography.getFontStyle2(),
                this.method_2187(),
                this.floatValue3 + this.floatValue4 - method_2190() - 6.0F,
                this.method_2184(),
                Theme.method_180(var1.method_2256().intValue7(), var3)
            );
            var4.method_440(var10003);
            this.moduleSearchBar.method_2186(var1);
            var4.method_430();
        }

        Module var20 = this.settingListView.getModule();
        if (var2 > 0.001F && (this.flag4 || var20 != null)) {
            float var18 = this.floatValue32 * var2;
            this.componentHost
                .method_2219(var4, this.method_2214(), this.method_2200(), method_2209(), method_2209(), Float.MAX_VALUE, var1.method_2256().intValue15());
            Icons.method_2221(
                var4,
                this.method_2214() + method_2209() / 2.0F,
                this.method_2200() + method_2209() / 2.0F,
                15.0F,
                Theme.method_180(var1.method_2256().intValue15(), var18)
            );
            FontStyle var10001 = Typography.getFontStyle18();
            UiRenderer var7;
            FontStyle var8;
            String var9;
            ClickGuiPanel var10;
            if (!this.flag4) {
                FontStyle var6 = var10001;
                String var10002 = var20.getName();
                var10 = this;
                var9 = var10002;
                var8 = var6;
                var7 = var4;
            } else {
                var10 = this;
                var9 = "Configs";
                var8 = var10001;
                var7 = var4;
            }

            var7.method_432(
                var8, var9, var10.method_2214() + method_2209() + 8.0F, this.method_2184(), Theme.method_180(var1.method_2256().intValue15(), var18)
            );
            var10001 = Typography.getFontStyle2();
            UiRenderer var13;
            FontStyle var14;
            String var15;
            ClickGuiPanel var16;
            if (!this.flag4) {
                FontStyle var12 = var10001;
                String var22 = var20.getCategory().getString();
                var16 = this;
                var15 = var22;
                var14 = var12;
                var13 = var4;
            } else {
                String var23 = this.configListPanel.method_2515();
                var16 = this;
                var15 = var23;
                var14 = var10001;
                var13 = var4;
            }

            var13.method_476(
                var14,
                var15,
                var16.floatValue3 + this.floatValue4 - method_2190() - 6.0F,
                this.method_2184(),
                Theme.method_180(var1.method_2256().intValue7(), var18)
            );
        }
    }

    private float method_2200() {
        return this.method_2184() - method_2209() / 2.0F;
    }

    private void method_2201(RenderContext var1) {
        UiRenderer var5 = var1.uiRenderer;
        float var6 = this.floatValue3 + method_2190() + 6.0F + var5.method_442(Typography.getFontStyle18(), "onyx client");
        float var7 = Math.max(this.floatValue3 + method_2190() + method_2192() + method_2190(), var6 + method_2213());
        float var2 = var5.method_442(Typography.getFontStyle2(), this.method_2187());
        float var3 = this.floatValue + method_2205();
        var2 = this.floatValue3 + this.floatValue4 - method_2190() - 6.0F - var2 - method_2213();
        this.moduleSearchBar.method_2258(var7, this.method_2184() - ModuleSearchBar.method_2368() / 2.0F, var2 - var7, ModuleSearchBar.method_2368());
        this.moduleListView
            .method_2258(var7, var3, this.floatValue3 + this.floatValue4 - method_2190() - var7, this.floatValue2 - method_2205() - method_2190());
    }

    private boolean method_2202() {
        Module var2;
        if ((var2 = this.moduleListView.method_2377()) == null) {
            return false;
        } else {
            this.method_2198(var2);
            return true;
        }
    }

    public boolean method_2203() {
        if (!this.flag32) {
            return false;
        } else {
            if (this.flag4) {
                this.configListPanel.method_2520();
            }

            this.flag32 = false;
            this.animation.method_603(0.0F, 300.0F, Easings.easing2);
            return true;
        }
    }

    public void lambda$ClickGuiPanel() {
        this.moduleSearchBar.method_2407(false);
        this.configListPanel.method_2519();
        this.flag4 = true;
        this.flag32 = true;
        this.animation.method_603(1.0F, 300.0F, Easings.easing2);
    }

    public void method_2204(float var1, float var2) {
        this.floatValue6 = var1;
        this.floatValue5 = var2;
        this.floatValue7 = (var1 - this.floatValue4) / 2.0F;
        this.floatValue52 = (var2 - this.floatValue2) / 2.0F;
        this.method_2195(this.floatValue7 + floatValue8, this.floatValue52 + floatValue42);
    }

    private static float method_2205() {
        return 38.0F;
    }

    @Override
    public void method_2206(RenderContext var1) {
        this.method_2201(var1);
        this.animation.method_605(var1.floatValue);
        this.componentHost.method_2220(var1.floatValue, this.method_2196(var1.floatValue3, var1.floatValue2), false);
        this.flag = this.method_2259(var1.floatValue3, var1.floatValue2);
        float var2;
        if ((var2 = this.animation.method_606()) < 0.999F) {
            this.configsTab.method_2206(var1);
            this.moduleSearchBar.method_2206(var1);
            this.moduleListView.method_2206(var1);
        }

        ClickGuiPanel var4;
        label18: {
            if (var2 > 0.001F) {
                if (this.flag4) {
                    this.configListPanel.method_2206(var1);
                    var4 = this;
                    break label18;
                }

                this.settingListView.method_2206(var1);
            }

            var4 = this;
        }

        var4.label.method_2206(var1);
    }

    public ClickGuiPanel() {
        this.floatValue4 = 640.0F;
        this.floatValue2 = 420.0F;
        this.moduleListView = new ModuleListView(category, this::method_2198);
        this.configsTab = new ConfigsTab(category, var1 -> {
            category = var1;
            this.moduleSearchBar.method_2362();
            this.moduleListView.method_2374(var1);
        }, this::lambda$ClickGuiPanel);
        this.list.add(this.configsTab);
        this.list.add(this.moduleSearchBar);
        this.list.add(this.moduleListView);
        this.list.add(this.settingListView);
        this.list.add(this.configListPanel);
    }

    private boolean method_2207() {
        Module var2;
        if ((var2 = this.moduleListView.method_2377()) == null) {
            return false;
        } else {
            var2.toggle();
            return true;
        }
    }

    private void method_2208() {
        ConfigManager var1 = Onyx.configManager;
        String var5 = Onyx.configManager.method_2635();
        Label var10000 = this.label;
        boolean var10001 = var1.method_2629(var5);
        StringBuilder var10003;
        Label var3;
        String var4;
        if (var10001) {
            var10003 = new StringBuilder();
            var4 = var10003.insert(0, "Saved ").append(var5).toString();
            var3 = var10000;
        } else {
            var10003 = new StringBuilder();
            var4 = var10003.insert(0, "Could not save ").append(var5).toString();
            var3 = var10000;
        }

        var3.method_2401(var4);
    }

    private static float method_2209() {
        return 26.0F;
    }

    public void method_2210(float var1) {
        this.floatValue32 = var1;
    }

    @Override
    public boolean method_2211(float var1, float var2, double var3) {
        if (this.flag32) {
            return !this.flag4 ? this.settingListView.method_2211(var1, var2, var3) : this.configListPanel.method_2211(var1, var2, var3);
        } else {
            return this.moduleListView.method_2211(var1, var2, var3);
        }
    }

    @Override
    public boolean method_2212(KeyTyped var1) {
        if (!this.flag32) {
            if (KeyShortcuts.method_2272(var1)) {
                this.moduleSearchBar.method_2407(true);
                this.moduleSearchBar.method_2413();
                return true;
            } else {
                return this.method_2182(var1)
                    || this.method_2191(var1)
                    || KeyShortcuts.method_2275(var1) && this.method_2202()
                    || this.moduleSearchBar.method_2212(var1)
                    || var1.intValue() == 57 && this.method_2207()
                    || KeyShortcuts.method_2269(var1) && !this.moduleSearchBar.isFlag22();
            }
        } else {
            return (this.flag4 ? this.configListPanel.method_2212(var1) : this.settingListView.method_2212(var1))
                || this.method_2182(var1)
                || KeyShortcuts.method_2269(var1) && this.method_2203();
        }
    }

    private static float method_2213() {
        return 12.0F;
    }

    private float method_2214() {
        return this.floatValue3 + method_2190();
    }
}
