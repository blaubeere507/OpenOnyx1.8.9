package client.onyx.ui.clickgui.setting;

import client.onyx.module.Module;
import client.onyx.ui.component.Popup;
import client.onyx.ui.component.RenderContext;
import client.onyx.ui.component.ScrollList;
import client.onyx.ui.theme.Typography;
import java.util.ArrayList;

public class SettingListView extends ScrollList {
    private static final float FLOAT_460_01 = 460.0F;
    private final Popup popup = new Popup();
    private Module module;

    @Override
    public void method_2186(RenderContext var1) {
        if (this.module != null) {
            if (this.popup.method_2554()) {
                var1.uiRenderer
                    .method_484(
                        Typography.fontStyle15,
                        "This module has no settings",
                        this.floatValue3 + this.floatValue4 / 2.0F,
                        this.floatValue + this.floatValue2 / 2.0F,
                        var1.method_2256().intValue7()
                    );
            } else {
                super.method_2186(var1);
            }
        }
    }

    public Module getModule() {
        return this.module;
    }

    private float method_2557() {
        return Math.min(this.floatValue4 - 12.0F, 460.0F);
    }

    public void method_2558(Module var1) {
        if (this.module != var1) {
            this.module = var1;
            this.list.clear();
            this.popup.method_2552();
            this.floatValue5 = 0.0F;
            this.animation.method_607(0.0F);
            if (var1 != null) {
                ArrayList var2;
                (var2 = new ArrayList()).add(var1.getBind());
                var2.addAll(var1.getSettings());
                this.popup.method_2556(var2);
                this.list.addAll(this.popup.getList());
            }
        }
    }

    @Override
    public void method_2288(RenderContext var1) {
        this.popup.method_2555(var1.uiRenderer, this.method_2559(), this.method_2557(), var1.method_2256().intValue12(), 1.0F);
    }

    private float method_2559() {
        return this.floatValue3 + (this.floatValue4 - this.method_2557()) / 2.0F;
    }

    @Override
    public void method_2279(RenderContext var1) {
        this.floatValue22 = this.popup.method_2553(this.method_2559(), this.floatValue - this.animation.method_606(), this.method_2557());
    }
}
