package client.onyx.ui.component;

import client.onyx.render.ui.UiRenderer;
import client.onyx.setting.Setting;
import client.onyx.ui.clickgui.setting.SettingView;
import client.onyx.ui.theme.Theme;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class Popup {
    private final List<SettingView> list = new ArrayList<>();
    private final List<Popup.Item> list2 = new ArrayList<>();

    public void method_2552() {
        this.list.clear();
        this.list2.clear();
    }

    public float method_2553(float var1, float var2, float var3) {
        var var4 = this;
        this.list2.clear();
        float var12 = var2;
        float var5 = 0.0F;
        boolean var6 = true;
        Iterator var7;
        Iterator var11 = var7 = this.list.iterator();

        while (var11.hasNext()) {
            SettingView var8;
            SettingView var13 = var8 = (SettingView)var7.next();
            float var9 = var13.method_2567();
            float var10 = var13.method_2576();
            if (var9 <= 0.5F) {
                var8.method_2258(var1, var12, var3, 0.0F);
                var11 = var7;
            } else {
                if (!var6) {
                    var4.list2.add(new Popup.Item(var12 + 7.5F, Math.min(var5, var10)));
                    var12 += 15.0F;
                }

                var8.method_2258(var1, var12, var3, var9);
                var12 += var9;
                var5 = var10;
                var6 = false;
                var11 = var7;
            }
        }

        return var12 - var2;
    }

    public boolean method_2554() {
        return this.list.isEmpty();
    }

    public void method_2555(UiRenderer var1, float var2, float var3, int var4, float var5) {
        Iterator var10;
        Iterator var9 = var10 = this.list2.iterator();

        while (var9.hasNext()) {
            Popup.Item var7;
            float var8;
            if ((var8 = (var7 = (Popup.Item)var10.next()).floatValue2() * var5) <= 0.01F) {
                var9 = var10;
            } else {
                var1.method_434(var2, var7.floatValue(), var3, 1.0F, Theme.method_180(var4, var8 * 0.8F));
                var9 = var10;
            }
        }
    }

    public void method_2556(List<Setting> var1) {
        var var5 = this;
        this.method_2552();
        Iterator var4 = var1.iterator();

        while (var4.hasNext()) {
            SettingView var2;
            if ((var2 = SettingView.method_2574((Setting)var4.next())) != null) {
                var5.list.add(var2);
            }
        }
    }

    public List<SettingView> getList() {
        return this.list;
    }

    private record Item(float floatValue, float floatValue2) {
    }
}
