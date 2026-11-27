package client.onyx.ui.account;

import client.onyx.input.KeyTyped;
import client.onyx.input.MouseClick;
import client.onyx.render.ui.PanelRenderer;
import client.onyx.render.ui.UiRenderer;
import client.onyx.ui.component.Component;
import client.onyx.ui.component.Label;
import client.onyx.ui.component.RenderContext;
import client.onyx.ui.component.Tooltip;
import client.onyx.ui.theme.ColorRoles;
import client.onyx.ui.theme.Typography;

public class ProxyButton extends Component {
    private static final float FLOAT_12_01 = 12.0F;
    private static final float FLOAT_420_01 = 420.0F;
    private static final float FLOAT_4_01 = 4.0F;
    private static final String STRING_CONST = "Proxy";
    private static final float FLOAT_46_01 = 46.0F;
    private final ProxySettingsPanel proxySettingsPanel = new ProxySettingsPanel();
    private final Label label = new Label();
    private float floatValue5 = 1.0F;
    private final Tooltip tooltip;

    private float method_2349() {
        return this.floatValue + 23.0F;
    }

    public float method_2350() {
        return this.floatValue + this.floatValue2 / 2.0F;
    }

    public void method_2351() {
        this.proxySettingsPanel.method_2395();
    }

    @Override
    public boolean method_2193(MouseClick var1) {
        return this.proxySettingsPanel.method_2193(var1);
    }

    @Override
    public void method_2194() {
        this.tooltip.method_2258(this.floatValue3 + this.floatValue4 - 12.0F - 30.0F, this.method_2349() - 15.0F, 30.0F, 30.0F);
        this.proxySettingsPanel.method_2258(this.floatValue3 + 12.0F, this.floatValue + 46.0F, this.floatValue4 - 24.0F, this.floatValue2 - 46.0F - 12.0F);
        this.label.method_2258(this.floatValue3, this.floatValue, this.floatValue4, this.floatValue2);
    }

    public void method_2352(float var1) {
        this.floatValue5 = var1;
    }

    @Override
    public boolean method_2183(float var1, float var2, int var3) {
        return super.method_2183(var1, var2, var3) || this.method_2259(var1, var2);
    }

    @Override
    public void method_2206(RenderContext var1) {
        super.method_2206(var1);
        this.label.method_2206(var1);
    }

    public void method_2353(float var1, float var2) {
        this.method_2258(Math.round((var1 - this.floatValue4) / 2.0F), Math.round((var2 - this.floatValue2) / 2.0F), this.floatValue4, this.floatValue2);
    }

    @Override
    public void method_2186(RenderContext var1) {
        UiRenderer var2 = var1.uiRenderer;
        UiRenderer var10001 = var1.uiRenderer;
        var2.method_440(this.floatValue5);
        var2.method_485(this.floatValue3, this.floatValue, this.floatValue4, this.floatValue2, 28.0F, 3);
        PanelRenderer.method_598(
            var2, this.floatValue3, this.floatValue, this.floatValue4, this.floatValue2, 28.0F, ColorRoles.method_182(2, var1.method_2256())
        );
        var2.method_430();
        var2.method_440(this.floatValue5);
        var2.method_432(Typography.getFontStyle18(), "Proxy", this.floatValue3 + 12.0F + 4.0F, this.method_2349(), var1.method_2256().intValue15());
        super.method_2186(var1);
        var10001.method_430();
        this.label.method_2186(var1);
    }

    public ProxyButton(Runnable var1) {
        this.floatValue4 = 420.0F;
        this.floatValue2 = 46.0F + ProxySettingsPanel.method_2390() + 12.0F;
        this.tooltip = new Tooltip("\ue5cd", var1);
        this.proxySettingsPanel.method_2392(this.label::method_2401);
        this.proxySettingsPanel.method_2393(var1x -> this.label.method_2398(var1x, "\ue001"));
        this.list.add(this.tooltip);
        this.list.add(this.proxySettingsPanel);
    }

    @Override
    public boolean method_2212(KeyTyped var1) {
        return this.proxySettingsPanel.method_2212(var1);
    }

    public float method_2354() {
        return this.floatValue3 + this.floatValue4 / 2.0F;
    }
}
