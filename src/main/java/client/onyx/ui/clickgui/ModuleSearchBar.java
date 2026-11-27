package client.onyx.ui.clickgui;

import client.onyx.input.MouseClick;
import client.onyx.render.font.FontStyle;
import client.onyx.render.ui.UiRenderer;
import client.onyx.ui.component.ComponentHost;
import client.onyx.ui.component.Icons;
import client.onyx.ui.component.RenderContext;
import client.onyx.ui.component.TextField;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.Typography;
import java.util.function.Consumer;

public class ModuleSearchBar extends TextField {
    public static final float FLOAT_26_01 = 26.0F;
    private static final float FLOAT_9_01 = 9.0F;
    private static final float FLOAT_16_01 = 16.0F;
    private static final float FLOAT_12_01 = 12.0F;
    private static final float FLOAT_7_01 = 7.0F;
    private static final float FLOAT_2_01 = 2.0F;
    private static final String STRING_CONST = "Search modules";
    private static final float FLOAT_22_01 = 22.0F;
    private final ComponentHost componentHost = new ComponentHost();
    private final ComponentHost componentHost2 = new ComponentHost();

    public ModuleSearchBar(Consumer<String> var1) {
        this.floatValue2 = 26.0F;
        this.textBuffer.method_2418(var1);
        this.textBuffer.method_2440(() -> this.method_2407(false));
        this.textBuffer.method_2442(() -> {
            if (!this.method_2362()) {
                this.method_2407(false);
            }
        });
    }

    private boolean method_2361(float var1, float var2) {
        return !this.textBuffer.method_2450() && Math.abs(var1 - this.method_2370()) <= 11.0F && this.method_2259(var1, var2);
    }

    public boolean method_2362() {
        return this.textBuffer.method_2445();
    }

    @Override
    public float method_2363() {
        return !this.textBuffer.method_2450() ? this.method_2370() - 16.0F : this.floatValue3 + this.floatValue4 - 9.0F;
    }

    @Override
    public void method_2206(RenderContext var1) {
        boolean var2;
        ComponentHost var5;
        float var6;
        boolean var7;
        label15: {
            super.method_2206(var1);
            var2 = this.method_2361(var1.floatValue3, var1.floatValue2);
            ComponentHost var3;
            float var4;
            if (!this.flag) {
                var4 = var1.floatValue;
                var3 = this.componentHost;
            } else {
                if (!var2) {
                    var7 = true;
                    var6 = var1.floatValue;
                    var5 = this.componentHost;
                    break label15;
                }

                var4 = var1.floatValue;
                var3 = this.componentHost;
            }

            var7 = false;
            var6 = var4;
            var5 = var3;
        }

        var5.method_2220(var6, var7, false);
        this.componentHost2.method_2220(var1.floatValue, var2, false);
    }

    @Override
    public float method_2364() {
        return this.method_2365() + 16.0F - 2.0F + 7.0F;
    }

    private float method_2365() {
        return this.floatValue3 + 9.0F - 2.0F;
    }

    public boolean method_2366(MouseClick var1) {
        this.method_2407(true);
        return this.textBuffer.method_2447(var1);
    }

    public String method_2367() {
        return this.textBuffer.getString();
    }

    @Override
    public boolean method_2183(float var1, float var2, int var3) {
        if (var3 == 0 && this.method_2361(var1, var2)) {
            this.method_2362();
            this.method_2407(true);
            return true;
        } else {
            return super.method_2183(var1, var2, var3);
        }
    }

    public static float method_2368() {
        return 26.0F;
    }

    @Override
    public FontStyle method_2369() {
        return Typography.getFontStyle15();
    }

    private float method_2370() {
        return this.floatValue3 + this.floatValue4 - 9.0F + 2.0F - 8.0F;
    }

    @Override
    public void method_2186(RenderContext var1) {
        UiRenderer var2 = var1.uiRenderer;
        var1.uiRenderer.method_479(this.floatValue3, this.floatValue, this.floatValue4, this.floatValue2, Float.MAX_VALUE, var1.method_2256().intValue19());
        this.componentHost
            .method_2219(var2, this.floatValue3, this.floatValue, this.floatValue4, this.floatValue2, Float.MAX_VALUE, var1.method_2256().intValue15());
        float var3;
        if ((var3 = this.animation.method_606()) > 0.01F) {
            var2.method_494(
                this.floatValue3,
                this.floatValue,
                this.floatValue4,
                this.floatValue2,
                Float.MAX_VALUE,
                1.5F,
                Theme.method_180(var1.method_2256().intValue31(), var3)
            );
        }

        var3 = this.floatValue + this.floatValue2 / 2.0F;
        Icons.method_2229(var2, this.method_2365() + 8.0F, var3, 16.0F, var1.method_2256().intValue7());
        if (this.textBuffer.method_2450() && !this.isFlag22()) {
            var2.method_432(this.method_2369(), "Search modules", this.method_2364(), var3, Theme.method_180(var1.method_2256().intValue7(), 0.7F));
        }

        this.method_2416(var1, var3, 12.0F, var1.method_2256().intValue15());
        if (!this.textBuffer.method_2450()) {
            this.componentHost2.method_2219(var2, this.method_2370() - 11.0F, var3 - 11.0F, 22.0F, 22.0F, Float.MAX_VALUE, var1.method_2256().intValue15());
            Icons.method_2223(var2, this.method_2370(), var3, 16.0F, var1.method_2256().intValue7());
        }

        if (this.method_2361(var1.floatValue3, var1.floatValue2)) {
            var2.method_465();
        } else {
            if (this.flag) {
                var2.method_455();
            }
        }
    }
}
