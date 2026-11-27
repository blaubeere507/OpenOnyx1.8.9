package client.onyx.ui.clickgui.setting;

import client.onyx.render.ui.UiRenderer;
import client.onyx.setting.impl.ButtonSetting;
import client.onyx.ui.component.ComponentHost;
import client.onyx.ui.component.RenderContext;
import client.onyx.ui.theme.Typography;

public class ItemListView extends SettingView {
    private static final float FLOAT_30_01 = 30.0F;
    private final ComponentHost componentHost = new ComponentHost();
    private final ButtonSetting buttonSetting;

    @Override
    public float method_2387(RenderContext var1) {
        return 30.0F;
    }

    public ItemListView(ButtonSetting var1) {
        super(var1);
        this.buttonSetting = var1;
    }

    @Override
    public boolean method_2183(float var1, float var2, int var3) {
        if (this.method_2577() && var3 == 0 && this.method_2259(var1, var2)) {
            this.buttonSetting.method_83();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void method_2206(RenderContext var1) {
        super.method_2206(var1);
        this.componentHost.method_2220(var1.floatValue, this.flag, this.flag3);
    }

    @Override
    public void method_2389(RenderContext var1, float var2) {
        UiRenderer var5;
        UiRenderer var10000 = var5 = var1.uiRenderer;
        UiRenderer var10001 = var1.uiRenderer;
        int var4 = this.method_2572(var1.method_2256().intValue30());
        var10001.method_479(this.floatValue3, this.floatValue, this.floatValue4, 30.0F, Float.MAX_VALUE, this.method_2572(var1.method_2256().intValue5()));
        this.componentHost.method_2219(var5, this.floatValue3, this.floatValue, this.floatValue4, 30.0F, Float.MAX_VALUE, var4);
        var10000.method_484(Typography.fontStyle14, this.buttonSetting.getString(), this.floatValue3 + this.floatValue4 / 2.0F, this.floatValue + 15.0F, var4);
        if (this.flag && this.method_2577()) {
            var5.method_465();
        }
    }
}
