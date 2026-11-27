package client.onyx.ui.clickgui.setting;

import client.onyx.render.font.FontStyle;
import client.onyx.setting.SettingGroup;
import client.onyx.ui.component.Component;
import client.onyx.ui.component.IconButton;
import client.onyx.ui.component.Popup;
import client.onyx.ui.component.RenderContext;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.Typography;
import java.util.Iterator;

public class ModeView extends SettingView {
    public static final float FLOAT_13_01 = 13.0F;
    private final Popup popup = new Popup();
    private final SettingGroup settingGroup;
    private final IconButton iconButton;
    private float floatValue5;

    @Override
    public void method_2206(RenderContext var1) {
        if (this.iconButton != null) {
            this.iconButton.method_2258(this.floatValue3 + this.floatValue4 - 38.0F, this.floatValue + (this.method_2588() - 22.0F) / 2.0F, 38.0F, 22.0F);
        }

        this.floatValue5 = this.popup.method_2553(this.method_2589(), this.floatValue + this.method_2588(), this.method_2590());
        super.method_2206(var1);
    }

    public ModeView(SettingGroup var1) {
        super(var1);
        this.settingGroup = var1;
        this.popup.method_2556(var1.getList());
        this.list.addAll(this.popup.getList());
        if (var1.method_66()) {
            this.iconButton = new IconButton(() -> var1.getEnabled().getValue(), var1x -> var1.getEnabled().setValue(var1x));
            this.list.add(this.iconButton);
        } else {
            this.iconButton = null;
        }
    }

    @Override
    public FontStyle method_2453() {
        return Typography.fontStyle18;
    }

    @Override
    public float method_2387(RenderContext var1) {
        float var10000 = this.method_2588() + this.floatValue5;
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

    private float method_2588() {
        float var2 = this.method_2565();
        return this.iconButton == null ? var2 : Math.max(var2, 34.0F);
    }

    private float method_2589() {
        return this.floatValue3 + 13.0F;
    }

    @Override
    public boolean method_2183(float var1, float var2, int var3) {
        if (!this.method_2577()) {
            return false;
        } else {
            Iterator var5 = this.list.iterator();

            while (var5.hasNext()) {
                if (((Component)var5.next()).method_2183(var1, var2, var3)) {
                    return true;
                }
            }

            if (this.iconButton != null
                && var3 == 0
                && var1 >= this.floatValue3
                && var1 < this.floatValue3 + this.floatValue4
                && var2 >= this.floatValue
                && var2 < this.floatValue + this.method_2588()) {
                this.settingGroup.getEnabled().method_145();
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public void method_2389(RenderContext var1, float var2) {
        RenderContext var6 = var1;
        float var4;
        if (this.iconButton == null) {
            var4 = 0.0F;
        } else {
            var4 = 48.0F;
        }

        this.method_2575(var1, this.floatValue4 - var4);
        if (this.floatValue5 > 0.5F) {
            var1.uiRenderer
                .method_434(
                    this.floatValue3 + 3.0F,
                    this.floatValue + this.method_2588(),
                    1.0F,
                    this.floatValue5,
                    Theme.method_180(var1.method_2256().intValue12(), var2 * 0.7F)
                );
        }

        this.popup.method_2555(var1.uiRenderer, this.method_2589(), this.method_2590(), var1.method_2256().intValue12(), var2);

        Iterator var8;
        for (Iterator var5 = var8 = this.list.iterator(); var5.hasNext(); var5 = var8) {
            ((Component)var8.next()).method_2186(var6);
        }
    }

    private float method_2590() {
        return Math.max(0.0F, this.floatValue4 - 13.0F);
    }
}
