package client.onyx.ui.clickgui;

import client.onyx.Onyx;
import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.render.font.FontStyle;
import client.onyx.render.ui.UiRenderer;
import client.onyx.ui.component.Checkbox;
import client.onyx.ui.component.Component;
import client.onyx.ui.component.RenderContext;
import client.onyx.ui.component.ScrollList;
import client.onyx.ui.theme.Typography;
import java.util.Locale;
import java.util.function.Consumer;

public class ModuleListView extends ScrollList {
    private String string = "";
    private final Consumer<Module> consumer;
    private Category category;

    public void method_2371(String var1) {
        String var3;
        if (!(var3 = var1.trim().toLowerCase(Locale.ROOT)).equals(this.string)) {
            this.string = var3;
            this.method_2379();
        }
    }

    public ModuleListView(Category var1, Consumer<Module> var2) {
        this.consumer = var2;
        this.category = var1;
        this.method_2379();
    }

    private static float method_2372() {
        return 5.0F;
    }

    public Category getCategory() {
        return this.category;
    }

    public int method_2373() {
        return this.list.size();
    }

    public void method_2374(Category var1) {
        if (this.category != var1) {
            this.category = var1;
            this.method_2379();
        }
    }

    @Override
    public void method_2186(RenderContext var1) {
        if (this.list.isEmpty()) {
            UiRenderer var10 = var1.uiRenderer;
            FontStyle var10001 = Typography.getFontStyle15();
            UiRenderer var4;
            FontStyle var5;
            String var6;
            ModuleListView var7;
            if (!this.method_2376()) {
                FontStyle var3 = var10001;
                UiRenderer var2 = var10;
                var7 = this;
                var6 = "No modules here yet";
                var5 = var3;
                var4 = var2;
            } else {
                var7 = this;
                var6 = "No modules match";
                var5 = var10001;
                var4 = var10;
            }

            var4.method_484(var5, var6, var7.floatValue3 + this.floatValue4 / 2.0F, this.floatValue + this.floatValue2 / 2.0F, var1.method_2256().intValue7());
        } else {
            super.method_2186(var1);
        }
    }

    private static int method_2375() {
        return 1;
    }

    @Override
    public int method_2283() {
        return method_2375();
    }

    @Override
    public void method_2279(RenderContext var1) {
        var var3 = this;
        float var12 = Checkbox.method_2381();
        float var6 = method_2372();
        int var11 = method_2375();
        float var4 = (this.floatValue4 - 9.0F - var6 * (var11 - 1)) / var11;
        float var5 = this.floatValue - this.animation.method_606();
        int var2 = 0;

        for (int var7 = 0; var7 < var3.list.size(); var7 = var2) {
            Component var10000 = var3.list.get(var2);
            float var10001 = var3.floatValue3 + var2 % var11 * (var4 + var6);
            float var10002 = var5 + var2 / var11 * (var12 + var6);
            var2++;
            var10000.method_2258(var10001, var10002, var4, var12);
        }

        ModuleListView var9;
        float var10;
        if ((var2 = (var3.list.size() + var11 - 1) / var11) != 0) {
            var10 = var2 * (var12 + var6) - var6;
            var9 = var3;
        } else {
            var10 = 0.0F;
            var9 = var3;
        }

        var9.floatValue22 = var10;
    }

    public boolean method_2376() {
        return !this.string.isEmpty();
    }

    public Module method_2377() {
        Component var2;
        return (var2 = this.method_2287()) instanceof Checkbox ? ((Checkbox)var2).getModule() : null;
    }

    private boolean method_2378(Module var1) {
        return var1.getName().toLowerCase(Locale.ROOT).contains(this.string)
            || var1.getDescription().toLowerCase(Locale.ROOT).contains(this.string)
            || var1.getCategory().getString().toLowerCase(Locale.ROOT).contains(this.string);
    }

    private void method_2379() {
        var var2 = this;
        this.list.clear();
        this.method_2282();
        this.floatValue5 = 0.0F;
        this.animation.method_607(0.0F);

        for (Module var5 : Onyx.moduleManager.getArrayList()) {
            boolean var4;
            if (var2.method_2376()) {
                var4 = var2.method_2378(var5);
            } else if (var5.getCategory() == var2.category) {
                var4 = true;
            } else {
                var4 = false;
            }

            if (var4) {
                var2.list.add(new Checkbox(var5, var2.consumer));
            }
        }
    }
}
