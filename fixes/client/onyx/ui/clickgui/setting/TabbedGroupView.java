package client.onyx.ui.clickgui.setting;

import client.onyx.render.font.FontStyle;
import client.onyx.setting.SettingGroup;
import client.onyx.setting.TabbedSettingGroup;
import client.onyx.ui.clickgui.ModuleRow;
import client.onyx.ui.component.Component;
import client.onyx.ui.component.Popup;
import client.onyx.ui.component.RenderContext;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.Typography;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class TabbedGroupView extends SettingView {
    private static final float FLOAT_8_01 = 8.0F;
    private static final float FLOAT_13_01 = 13.0F;
    private final List<Popup> list2;
    private final ModuleRow moduleRow;
    private Popup popup;
    private float floatValue5;

    public TabbedGroupView(TabbedSettingGroup var1) {
        super(var1);
        var var3 = this;
        this.list2 = new ArrayList<>();
        List var2 = var1.getList().stream().filter(SettingGroup.class::isInstance).map(SettingGroup.class::cast).toList();
        List<String> var8;
        if ((var8 = var1.getList2()).size() != var2.size()) {
            throw new IllegalArgumentException("Tabbed setting labels must match child groups");
        } else {
            Iterator var9;
            for (Iterator var5 = var9 = var2.iterator(); var5.hasNext(); var5 = var9) {
                SettingGroup var6 = (SettingGroup)var9.next();
                ArrayList var4 = new ArrayList();
                if (var6.getEnabled() != null) {
                    var4.add(var6.getEnabled());
                }

                var4.addAll(var6.getList());
                Popup var7 = new Popup();
                var7.method_2556(var4);
                var3.list2.add(var7);
            }

            this.moduleRow = new ModuleRow(var8.toArray(String[]::new), var3::method_2457);
            var3.list.add(var3.moduleRow);
            if (!var3.list2.isEmpty()) {
                var3.method_2457(0);
            }
        }
    }

    @Override
    public float method_2387(RenderContext var1) {
        if (this.list2.isEmpty()) {
            return this.method_2565();
        } else {
            float var10000 = this.method_2456() - this.floatValue + this.floatValue5;
            float var3;
            float var4;
            if (!(this.floatValue5 > 0.5F)) {
                float var2 = var10000;
                var4 = 0.0F;
                var3 = var2;
            } else {
                var4 = 6.0F;
                var3 = var10000;
            }

            return var3 + var4;
        }
    }

    @Override
    public void method_2389(RenderContext var1, float var2) {
        RenderContext var4 = var1;
        this.method_2575(var1, this.floatValue4);
        if (this.popup != null && this.floatValue5 > 0.5F) {
            var1.uiRenderer
                .method_434(this.floatValue3 + 3.0F, this.method_2456(), 1.0F, this.floatValue5, Theme.method_180(var1.method_2256().intValue12(), var2 * 0.7F));
            this.popup.method_2555(var1.uiRenderer, this.method_2458(), this.method_2454(), var1.method_2256().intValue12(), var2);
        }

        Iterator var6;
        for (Iterator var3 = var6 = this.list.iterator(); var3.hasNext(); var3 = var6) {
            ((Component)var6.next()).method_2186(var4);
        }
    }

    @Override
    public FontStyle method_2453() {
        return Typography.fontStyle18;
    }

    private float method_2454() {
        return Math.max(0.0F, this.floatValue4 - 13.0F);
    }

    @Override
    public void method_2206(RenderContext var1) {
        this.moduleRow.method_2258(this.method_2458(), this.method_2455(), this.method_2454(), 30.0F);
        TabbedGroupView var3;
        float var4;
        if (this.popup != null) {
            var4 = this.popup.method_2553(this.method_2458(), this.method_2456(), this.method_2454());
            var3 = this;
        } else {
            var4 = 0.0F;
            var3 = this;
        }

        var3.floatValue5 = var4;
        super.method_2206(var1);
    }

    private float method_2455() {
        return this.floatValue + this.method_2565();
    }

    @Override
    public boolean method_2183(float var1, float var2, int var3) {
        if (!this.method_2577()) {
            return false;
        } else if (this.moduleRow.method_2183(var1, var2, var3)) {
            return true;
        } else {
            if (this.popup != null) {
                Iterator var5 = this.popup.getList().iterator();

                while (var5.hasNext()) {
                    if (((SettingView)var5.next()).method_2183(var1, var2, var3)) {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    private float method_2456() {
        return this.method_2455() + 30.0F + 8.0F;
    }

    private void method_2457(int var1) {
        if (var1 >= 0 && var1 < this.list2.size()) {
            if (this.popup != null) {
                this.list.removeAll(this.popup.getList());
            }

            this.popup = this.list2.get(var1);
            this.list.addAll(this.popup.getList());
        }
    }

    private float method_2458() {
        return this.floatValue3 + 13.0F;
    }
}
