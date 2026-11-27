package client.onyx.ui.component;

import client.onyx.render.anim.Spring;
import client.onyx.render.font.FontStyle;
import client.onyx.render.ui.UiRenderer;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.Typography;
import java.util.function.Consumer;

public class SearchField extends TextField {
    private static final float FLOAT_11_01 = 11.0F;
    private static final float FLOAT_2_01 = 2.0F;
    public static final float FLOAT_40_01 = 40.0F;
    private static final float FLOAT_1_01 = 1.0F;
    private static final float FLOAT_12_01 = 12.0F;
    private static final float FLOAT_9_01 = 9.0F;
    private static final float FLOAT_12_02 = 12.0F;
    private final ComponentHost componentHost = new ComponentHost();
    private final String string;
    private Runnable runnable;
    private Consumer<String> consumer;

    @Override
    public float method_2364() {
        return this.floatValue3 + 12.0F;
    }

    @Override
    public void method_2206(RenderContext var1) {
        super.method_2206(var1);
        this.componentHost.method_2220(var1.floatValue, this.flag, false);
    }

    public void method_2549(Consumer<String> var1) {
        this.consumer = var1;
    }

    public SearchField(String var1) {
        this.string = var1;
        this.floatValue2 = 40.0F;
        this.textBuffer.method_2418(var1x -> {
            if (this.consumer != null) {
                this.consumer.accept(var1x);
            }
        });
        this.textBuffer.method_2440(() -> {
            if (this.runnable != null) {
                this.runnable.run();
            }
        });
        this.textBuffer.method_2442(() -> this.method_2407(false));
    }

    @Override
    public FontStyle method_2369() {
        return Typography.getFontStyle15();
    }

    public void method_2550(Runnable var1) {
        this.runnable = var1;
    }

    @Override
    public float method_2363() {
        return this.floatValue3 + this.floatValue4 - 12.0F;
    }

    @Override
    public void method_2186(RenderContext var1) {
        UiRenderer var2 = var1.uiRenderer;
        float var3 = this.animation.method_606();
        boolean var5;
        if (!this.isFlag22() && this.textBuffer.method_2450()) {
            var5 = false;
        } else {
            var5 = true;
        }

        var2.method_479(this.floatValue3, this.floatValue, this.floatValue4, this.floatValue2, 4.0F, var1.method_2256().intValue11());
        this.componentHost.method_2219(var2, this.floatValue3, this.floatValue, this.floatValue4, this.floatValue2, 4.0F, var1.method_2256().intValue15());
        var2.method_494(
            this.floatValue3,
            this.floatValue,
            this.floatValue4,
            this.floatValue2,
            4.0F,
            1.0F + 1.0F * var3,
            Spring.method_616(var1.method_2256().intValue8(), var1.method_2256().intValue31(), var3)
        );
        SearchField var18;
        if (var5) {
            FontStyle var10001 = Typography.getFontStyle2();
            String var10002 = this.string;
            float var10003 = this.method_2364();
            float var10004 = this.floatValue + 9.0F;
            UiRenderer var12;
            FontStyle var13;
            String var14;
            float var15;
            float var16;
            int var17;
            if (!(var3 > 0.5F)) {
                float var10 = var10004;
                float var9 = var10003;
                String var8 = var10002;
                FontStyle var7 = var10001;
                var17 = var1.method_2256().intValue7();
                var16 = var10;
                var15 = var9;
                var14 = var8;
                var13 = var7;
                var12 = var2;
            } else {
                var17 = var1.method_2256().intValue31();
                var16 = var10004;
                var15 = var10003;
                var14 = var10002;
                var13 = var10001;
                var12 = var2;
            }

            var12.method_432(var13, var14, var15, var16, var17);
            var18 = this;
        } else {
            var2.method_432(
                this.method_2369(),
                this.string,
                this.method_2364(),
                this.floatValue + this.floatValue2 / 2.0F,
                Theme.method_180(var1.method_2256().intValue7(), 0.7F)
            );
            var18 = this;
        }

        SearchField var22;
        RenderContext var23;
        float var24;
        if (!var5) {
            var24 = this.floatValue + this.floatValue2 / 2.0F;
            var23 = var1;
            var22 = var18;
        } else {
            var24 = this.floatValue + this.floatValue2 - 11.0F;
            var23 = var1;
            var22 = var18;
        }

        var22.method_2416(var23, var24, 12.0F, var1.method_2256().intValue15());
        if (this.flag) {
            var2.method_455();
        }
    }
}
