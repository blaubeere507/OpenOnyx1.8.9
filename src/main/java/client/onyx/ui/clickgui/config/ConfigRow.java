package client.onyx.ui.clickgui.config;

import client.onyx.Onyx;
import client.onyx.render.anim.Easings;
import client.onyx.render.anim.Spring;
import client.onyx.render.ui.UiRenderer;
import client.onyx.ui.component.Button;
import client.onyx.ui.component.Component;
import client.onyx.ui.component.ComponentHost;
import client.onyx.ui.component.RenderContext;
import client.onyx.ui.component.Tooltip;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.Typography;
import java.util.function.Consumer;

public class ConfigRow extends Component {
    private static final float FLOAT_6_01 = 6.0F;
    public static final float FLOAT_50_01 = 50.0F;
    private static final float FLOAT_8_01 = 8.0F;
    private static final float FLOAT_76_01 = 76.0F;
    private final ComponentHost componentHost = new ComponentHost();
    private final Spring spring = new Spring(0);
    private final String string;
    private final Consumer<ConfigRow> consumer;
    private final Consumer<ConfigRow> consumer3;
    private final Consumer<ConfigRow> consumer2;
    private final Button button;
    private final Tooltip tooltip;
    private boolean flag4;

    @Override
    public boolean method_2189(float var1, float var2, int var3) {
        this.flag3 = false;
        return super.method_2189(var1, var2, var3);
    }

    public String getString() {
        return this.string;
    }

    @Override
    public void method_2186(RenderContext var1) {
        UiRenderer var2 = var1.uiRenderer;
        boolean var13 = this.method_2610();
        var2.method_479(this.floatValue3, this.floatValue, this.floatValue4, this.floatValue2, 12.0F, this.spring.method_617());
        this.componentHost.method_2219(var2, this.floatValue3, this.floatValue, this.floatValue4, this.floatValue2, 12.0F, var1.method_2256().intValue15());
        float var4 = this.floatValue3 + 12.0F;
        float var5 = this.button.getFloatValue3() - 6.0F - var4;
        String var8;
        if (var13) {
            var8 = "Loaded";
        } else {
            var8 = "Saved config";
        }

        int var10;
        if (!var13) {
            var10 = var1.method_2256().intValue15();
        } else {
            var10 = var1.method_2256().intValue30();
        }

        int var12;
        if (!var13) {
            var12 = var1.method_2256().intValue7();
        } else {
            var12 = Theme.method_180(var1.method_2256().intValue30(), 0.72F);
        }

        var2.method_432(
            Typography.getFontStyle14(),
            var2.method_469(Typography.getFontStyle14(), this.string, var5),
            var4,
            this.floatValue + this.floatValue2 * 0.33F,
            var10
        );
        var2.method_432(Typography.getFontStyle2(), var8, var4, this.floatValue + this.floatValue2 * 0.7F, var12);
        super.method_2186(var1);
    }

    public ConfigRow(String var1, Consumer<ConfigRow> var2, Consumer<ConfigRow> var3, Consumer<ConfigRow> var4) {
        this.string = var1;
        this.consumer = var2;
        this.consumer3 = var3;
        this.consumer2 = var4;
        this.button = new Button(Button.Variant.TEXT, "Load", "\ue2c4", () -> {
            if (!this.method_2610()) {
                this.consumer.accept(this);
            } else {
                this.consumer3.accept(this);
            }
        });
        this.tooltip = new Tooltip("\ue5d4", () -> var4.accept(this));
        this.floatValue2 = 50.0F;
        this.list.add(this.button);
        this.list.add(this.tooltip);
    }

    @Override
    public boolean method_2183(float var1, float var2, int var3) {
        if (this.button.method_2183(var1, var2, var3)) {
            return true;
        } else if (this.tooltip.method_2183(var1, var2, var3)) {
            return true;
        } else if (!this.method_2259(var1, var2)) {
            return false;
        } else if (var3 == 0) {
            this.flag3 = true;
            if (!this.method_2610()) {
                this.consumer.accept(this);
            }

            return true;
        } else if (var3 == 1) {
            this.consumer2.accept(this);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void method_2206(RenderContext var1) {
        boolean var17 = this.method_2610();
        Button var4;
        String var5;
        if (!var17) {
            Button var3 = this.button;
            var5 = "Load";
            var4 = var3;
        } else {
            var5 = "Save";
            var4 = this.button;
        }

        var4.method_2463(var5);
        Button var7;
        String var8;
        if (!var17) {
            Button var6 = this.button;
            var8 = "\ue2c4";
            var7 = var6;
        } else {
            var8 = "\ue161";
            var7 = this.button;
        }

        var7.method_2461(var8);
        super.method_2206(var1);
        int var10;
        if (!var17) {
            var10 = var1.method_2256().intValue19();
        } else {
            var10 = var1.method_2256().intValue5();
        }

        if (!this.flag4) {
            this.flag4 = true;
            this.spring.method_618(var10);
        }

        ComponentHost var13;
        float var14;
        boolean var15;
        ConfigRow var16;
        label39: {
            this.spring.method_613(var10, 250.0F, Easings.easing6);
            this.spring.method_614(var1.floatValue);
            ComponentHost var19 = this.componentHost;
            float var10001 = var1.floatValue;
            ComponentHost var11;
            float var12;
            if (!this.flag) {
                var12 = var1.floatValue;
                var11 = this.componentHost;
            } else if (this.button.isFlag()) {
                var12 = var10001;
                var11 = var19;
            } else {
                if (!this.tooltip.isFlag()) {
                    var16 = this;
                    var15 = true;
                    var14 = var10001;
                    var13 = var19;
                    break label39;
                }

                var12 = var10001;
                var11 = var19;
            }

            var16 = this;
            var15 = false;
            var14 = var12;
            var13 = var11;
        }

        var13.method_2220(var14, var15, var16.flag3);
    }

    @Override
    public void method_2194() {
        this.tooltip.method_2258(this.floatValue3 + this.floatValue4 - 30.0F - 8.0F, this.floatValue + (this.floatValue2 - 30.0F) / 2.0F, 30.0F, 30.0F);
        this.button.method_2258(this.tooltip.getFloatValue3() - 6.0F - 76.0F, this.floatValue + (this.floatValue2 - 30.0F) / 2.0F, 76.0F, 30.0F);
    }

    private boolean method_2610() {
        return Onyx.configManager.method_2644(this.string);
    }

    public Component method_2611() {
        return this.tooltip;
    }
}
