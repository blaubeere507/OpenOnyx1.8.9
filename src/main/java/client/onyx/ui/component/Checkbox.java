package client.onyx.ui.component;

import client.onyx.module.Module;
import client.onyx.render.anim.Easings;
import client.onyx.render.anim.Spring;
import client.onyx.render.ui.UiRenderer;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.Typography;
import java.util.function.Consumer;

public class Checkbox extends Component {
    private final ComponentHost componentHost = new ComponentHost();
    private final Spring spring = new Spring(0);
    private final Module module;
    private final Consumer<Module> consumer;
    private final IconButton iconButton;
    private boolean flag4;

    public Module getModule() {
        return this.module;
    }

    private static float method_2380() {
        return 12.0F;
    }

    @Override
    public void method_2186(RenderContext var1) {
        UiRenderer var2;
        UiRenderer var13 = var2 = var1.uiRenderer;
        boolean var3 = this.module.isToggled();
        var13.method_479(this.floatValue3, this.floatValue, this.floatValue4, this.floatValue2, 12.0F, this.spring.method_617());
        this.componentHost.method_2219(var2, this.floatValue3, this.floatValue, this.floatValue4, this.floatValue2, 12.0F, var1.method_2256().intValue15());
        int var7;
        if (!var3) {
            var7 = var1.method_2256().intValue15();
        } else {
            var7 = var1.method_2256().intValue30();
        }

        int var9;
        if (!var3) {
            var9 = var1.method_2256().intValue7();
        } else {
            var9 = Theme.method_180(var1.method_2256().intValue30(), 0.72F);
        }

        float var11 = this.floatValue3 + method_2380();
        float var5 = this.iconButton.getFloatValue3() - 8.0F - var11;
        var2.method_432(
            Typography.getFontStyle14(),
            var2.method_469(Typography.getFontStyle14(), this.module.getName(), var5),
            var11,
            this.floatValue + this.floatValue2 * 0.32F,
            var7
        );
        var2.method_432(
            Typography.getFontStyle2(),
            var2.method_469(Typography.getFontStyle2(), this.module.getDescription(), var5),
            var11,
            this.floatValue + this.floatValue2 * 0.7F,
            var9
        );
        super.method_2186(var1);
    }

    @Override
    public boolean method_2183(float var1, float var2, int var3) {
        if (this.iconButton.method_2183(var1, var2, var3)) {
            return true;
        } else if (!this.method_2259(var1, var2)) {
            return false;
        } else if (var3 == 0) {
            boolean var6 = this.flag3 = true;
            this.module.toggle();
            return var6;
        } else if (var3 == 1) {
            this.consumer.accept(this.module);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void method_2206(RenderContext var1) {
        super.method_2206(var1);
        int var4;
        if (!this.module.isToggled()) {
            var4 = var1.method_2256().intValue19();
        } else {
            var4 = var1.method_2256().intValue5();
        }

        if (!this.flag4) {
            this.flag4 = true;
            this.spring.method_618(var4);
        }

        ComponentHost var7;
        float var8;
        boolean var9;
        Checkbox var10;
        label24: {
            this.spring.method_613(var4, 250.0F, Easings.easing6);
            this.spring.method_614(var1.floatValue);
            ComponentHost var13 = this.componentHost;
            float var10001 = var1.floatValue;
            ComponentHost var5;
            float var6;
            if (!this.flag) {
                var6 = var1.floatValue;
                var5 = this.componentHost;
            } else {
                if (!this.iconButton.isFlag()) {
                    var10 = this;
                    var9 = true;
                    var8 = var10001;
                    var7 = var13;
                    break label24;
                }

                var6 = var10001;
                var5 = var13;
            }

            var10 = this;
            var9 = false;
            var8 = var6;
            var7 = var5;
        }

        var7.method_2220(var8, var9, var10.flag3);
    }

    public Checkbox(Module var1, Consumer<Module> var2) {
        this.module = var1;
        this.consumer = var2;
        this.floatValue2 = method_2381();
        this.iconButton = new IconButton(var1::isToggled, var1::setEnabled);
        this.list.add(this.iconButton);
    }

    public static float method_2381() {
        return 46.0F;
    }

    @Override
    public void method_2194() {
        this.iconButton.method_2260(true);
        this.iconButton
            .method_2258(this.floatValue3 + this.floatValue4 - 38.0F - method_2380(), this.floatValue + (this.floatValue2 - 22.0F) / 2.0F, 38.0F, 22.0F);
    }

    @Override
    public boolean method_2189(float var1, float var2, int var3) {
        this.flag3 = false;
        return super.method_2189(var1, var2, var3);
    }
}
