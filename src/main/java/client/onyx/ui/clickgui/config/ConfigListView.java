package client.onyx.ui.clickgui.config;

import client.onyx.Onyx;
import client.onyx.ui.component.Component;
import client.onyx.ui.component.RenderContext;
import client.onyx.ui.component.ScrollList;
import client.onyx.ui.theme.Typography;
import java.util.Iterator;
import java.util.function.Consumer;

public class ConfigListView extends ScrollList {
    private static final float FLOAT_9_01 = 9.0F;
    private static final float FLOAT_5_01 = 5.0F;
    private final Consumer<ConfigRow> consumer2;
    private final Consumer<ConfigRow> consumer;
    private final Consumer<ConfigRow> consumer3;

    @Override
    public boolean method_2183(float var1, float var2, int var3) {
        return this.method_2259(var1, var2) && super.method_2183(var1, var2, var3);
    }

    public ConfigListView(Consumer<ConfigRow> var1, Consumer<ConfigRow> var2, Consumer<ConfigRow> var3) {
        this.consumer2 = var1;
        this.consumer = var2;
        this.consumer3 = var3;
        this.method_2525();
    }

    public ConfigRow method_2524() {
        Component var2;
        return (var2 = this.method_2287()) instanceof ConfigRow ? (ConfigRow)var2 : null;
    }

    @Override
    public void method_2279(RenderContext var1) {
        var var8 = this;
        float var7 = this.floatValue - this.animation.method_606();

        Iterator var2;
        for (Iterator var3 = var2 = this.list.iterator(); var3.hasNext(); var3 = var2) {
            ((Component)var2.next()).method_2258(var8.floatValue3, var7, var8.floatValue4 - 9.0F, 50.0F);
            var7 += 55.0F;
        }

        ConfigListView var5;
        float var6;
        if (!var8.list.isEmpty()) {
            var6 = var8.list.size() * 55.0F - 5.0F;
            var5 = var8;
        } else {
            var6 = 0.0F;
            var5 = var8;
        }

        var5.floatValue22 = var6;
    }

    @Override
    public void method_2186(RenderContext var1) {
        if (this.list.isEmpty()) {
            var1.uiRenderer
                .method_484(
                    Typography.getFontStyle15(),
                    "No configs saved yet",
                    this.floatValue3 + this.floatValue4 / 2.0F,
                    this.floatValue + this.floatValue2 / 2.0F,
                    var1.method_2256().intValue7()
                );
        } else {
            super.method_2186(var1);
        }
    }

    public void method_2525() {
        var var1 = this;
        this.list.clear();
        this.method_2282();
        this.floatValue5 = 0.0F;
        this.animation.method_607(0.0F);
        Iterator var4 = Onyx.configManager.method_2655().iterator();

        for (Iterator var3 = var4; var3.hasNext(); var3 = var4) {
            String var2 = (String)var4.next();
            var1.list.add(new ConfigRow(var2, var1.consumer2, var1.consumer, var1.consumer3));
        }
    }

    public int method_2526() {
        return this.list.size();
    }
}
