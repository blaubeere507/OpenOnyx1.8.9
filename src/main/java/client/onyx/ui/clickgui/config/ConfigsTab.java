package client.onyx.ui.clickgui.config;

import client.onyx.module.Category;
import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easings;
import client.onyx.render.font.FontStyle;
import client.onyx.render.ui.UiRenderer;
import client.onyx.ui.component.Component;
import client.onyx.ui.component.ComponentHost;
import client.onyx.ui.component.Icons;
import client.onyx.ui.component.RenderContext;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.Typography;
import java.util.function.Consumer;

public class ConfigsTab extends Component {
    private final ComponentHost[] componentHostArray;
    private final ComponentHost componentHost;
    private final Animation animation2;
    private final Animation animation;
    private int intValue;
    private Category category;
    private final Consumer<Category> consumer;
    private final Runnable runnable;
    private boolean flag4;
    private boolean flag22;

    @Override
    public void method_2186(RenderContext var1) {
        RenderContext var17 = var1;
        var var18 = this;
        UiRenderer var7;
        (var7 = var1.uiRenderer)
            .method_479(
                this.floatValue3 + (this.floatValue4 - this.animation.method_606()) / 2.0F,
                this.floatValue + this.animation2.method_606() + 2.0F,
                this.animation.method_606(),
                method_2545(),
                Float.MAX_VALUE,
                var1.method_2256().intValue5()
            );
        Category[] var3;
        int var4 = (var3 = Category.values()).length;
        int var5 = 0;

        for (int var16 = 0; var16 < var4; var16 = var5) {
            Category var6 = var3[var5];
            int var2 = var6.ordinal();
            float var8 = var18.method_2547(var2);
            float var9 = var18.floatValue3 + var18.floatValue4 / 2.0F;
            boolean var11;
            if (var6 == var18.category) {
                var11 = true;
            } else {
                var11 = false;
            }

            var18.componentHostArray[var2]
                .method_2219(
                    var7,
                    var18.floatValue3 + (var18.floatValue4 - method_2542()) / 2.0F,
                    var8 + 2.0F,
                    method_2542(),
                    method_2545(),
                    Float.MAX_VALUE,
                    var17.method_2256().intValue15()
                );
            int var13;
            if (!var11) {
                var13 = var17.method_2256().intValue7();
            } else {
                var13 = var17.method_2256().intValue30();
            }

            Icons.method_2228(var7, var6, var9, var8 + 2.0F + method_2545() / 2.0F, 16.0F, var13);
            int var15;
            if (!var11) {
                var15 = Theme.method_180(var17.method_2256().intValue7(), 0.9F);
            } else {
                var15 = var17.method_2256().intValue15();
            }

            FontStyle var10001 = Typography.getFontStyle2();
            String var10002 = var6.getString();
            float var10005 = method_2543();
            var5++;
            var7.method_484(var10001, var10002, var9, var8 + var10005, var15);
        }

        float var19 = var18.floatValue + var18.floatValue2 - method_2546();
        float var20 = var18.floatValue3 + var18.floatValue4 / 2.0F;
        var18.componentHost
            .method_2219(
                var7,
                var18.floatValue3 + (var18.floatValue4 - method_2542()) / 2.0F,
                var19 + 2.0F,
                method_2542(),
                method_2545(),
                Float.MAX_VALUE,
                var17.method_2256().intValue15()
            );
        var7.method_477("\ue161", var20, var19 + 2.0F + method_2545() / 2.0F, 16.0F, var17.method_2256().intValue7());
        var7.method_484(Typography.getFontStyle2(), "Configs", var20, var19 + method_2543(), Theme.method_180(var17.method_2256().intValue7(), 0.9F));
    }

    private static float method_2542() {
        return 46.0F;
    }

    @Override
    public void method_2206(RenderContext var1) {
        RenderContext var12 = var1;
        var var13 = this;
        super.method_2206(var1);
        if (!this.flag22) {
            this.flag22 = true;
            this.animation2.method_607(this.method_2544(this.category.ordinal()));
            this.animation.method_607(method_2542());
        }

        this.intValue = -1;
        this.flag4 = false;
        Category[] var2;
        int var5 = (var2 = Category.values()).length;
        int var4 = 0;

        for (int var8 = 0; var8 < var5; var8 = var4) {
            int var3 = var2[var4].ordinal();
            boolean var7;
            if (var12.floatValue3 >= var13.floatValue3
                && var12.floatValue3 < var13.floatValue3 + var13.floatValue4
                && var12.floatValue2 >= var13.method_2547(var3)
                && var12.floatValue2 < var13.method_2547(var3) + method_2546()) {
                var7 = true;
            } else {
                var7 = false;
            }

            if (var7) {
                var13.intValue = var3;
            }

            ComponentHost var15 = var13.componentHostArray[var3];
            var4++;
            var15.method_2220(var12.floatValue, var7, false);
        }

        ConfigsTab var10;
        boolean var11;
        label38: {
            float var14 = var13.floatValue + var13.floatValue2 - method_2546();
            ConfigsTab var9;
            if (!(var12.floatValue3 >= var13.floatValue3)) {
                var9 = var13;
            } else if (!(var12.floatValue3 < var13.floatValue3 + var13.floatValue4)) {
                var9 = var13;
            } else if (!(var12.floatValue2 >= var14)) {
                var9 = var13;
            } else {
                if (var12.floatValue2 < var14 + method_2546()) {
                    var11 = true;
                    var10 = var13;
                    break label38;
                }

                var9 = var13;
            }

            var11 = false;
            var10 = var9;
        }

        var10.flag4 = var11;
        var13.componentHost.method_2220(var12.floatValue, var13.flag4, false);
        var13.animation2.method_605(var12.floatValue);
        var13.animation.method_605(var12.floatValue);
    }

    private static float method_2543() {
        return 36.0F;
    }

    private float method_2544(int var1) {
        return var1 * method_2546();
    }

    @Override
    public boolean method_2183(float var1, float var2, int var3) {
        if (var3 != 0) {
            return false;
        } else if (this.flag4) {
            this.runnable.run();
            return true;
        } else if (this.intValue < 0) {
            return false;
        } else {
            this.method_2548(Category.values()[this.intValue]);
            return true;
        }
    }

    public ConfigsTab(Category var1, Consumer<Category> var2, Runnable var3) {
        var var7 = this;
        this.componentHostArray = new ComponentHost[Category.values().length];
        this.componentHost = new ComponentHost();
        this.animation2 = new Animation(0.0F);
        this.animation = new Animation(0.0F);
        this.intValue = -1;
        this.category = var1;
        this.consumer = var2;
        this.runnable = var3;
        int var6 = 0;

        for (int var4 = 0; var4 < var7.componentHostArray.length; var4 = var6) {
            ComponentHost[] var8 = var7.componentHostArray;
            int var10001 = var6;
            ComponentHost var10002 = new ComponentHost();
            var6++;
            var8[var10001] = var10002;
        }
    }

    private static float method_2545() {
        return 26.0F;
    }

    private static float method_2546() {
        return 50.0F;
    }

    private float method_2547(int var1) {
        return this.floatValue + this.method_2544(var1);
    }

    public void method_2548(Category var1) {
        if (this.category != var1) {
            this.category = var1;
            this.animation2.method_603(this.method_2544(var1.ordinal()), 300.0F, Easings.easing2);
            this.animation.method_607(method_2542() * 0.6F).method_603(method_2542(), 300.0F, Easings.easing2);
            this.consumer.accept(var1);
        }
    }

    public Category getCategory() {
        return this.category;
    }
}
