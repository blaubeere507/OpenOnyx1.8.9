package client.onyx.ui.clickgui.setting;

import client.onyx.setting.impl.TextSetting;
import client.onyx.ui.component.RenderContext;
import client.onyx.ui.component.SearchField;
import client.onyx.ui.theme.Theme;

public class RangeView extends SettingView {
    private static final float FLOAT_6_012 = 6.0F;
    private final TextSetting textSetting;
    private final SearchField searchField;
    private String string;

    @Override
    public void method_2389(RenderContext var1, float var2) {
        this.searchField.method_2186(var1);
        if (this.method_2573()) {
            var2 = this.floatValue + 6.0F + 40.0F + 6.0F;
            var1.uiRenderer
                .method_428(
                    fontStyle2,
                    var1.uiRenderer.method_469(fontStyle2, this.setting.getDescription(), this.floatValue4),
                    this.floatValue3,
                    var2,
                    this.method_2572(Theme.method_180(var1.method_2256().intValue7(), 0.85F))
                );
        }
    }

    @Override
    public void method_2206(RenderContext var1) {
        super.method_2206(var1);
        if (!this.textSetting.getValue().equals(this.string)) {
            this.string = this.textSetting.getValue();
            this.searchField.method_2409(this.string);
        }
    }

    @Override
    public void method_2194() {
        this.searchField.method_2258(this.floatValue3, this.floatValue + 6.0F, this.floatValue4, 40.0F);
    }

    public RangeView(TextSetting var1) {
        super(var1);
        this.textSetting = var1;
        this.searchField = new SearchField(var1.getName());
        this.string = var1.getValue();
        this.searchField.method_2409(this.string);
        this.searchField.method_2549(var1::setValue);
        this.list.add(this.searchField);
    }

    @Override
    public float method_2387(RenderContext var1) {
        float var2 = 46.0F;
        if (this.method_2573()) {
            var2 += 6.0F + fontStyle2.method_643();
        }

        return var2 + 6.0F;
    }
}
