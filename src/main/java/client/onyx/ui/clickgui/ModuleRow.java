package client.onyx.ui.clickgui;

import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easings;
import client.onyx.render.ui.UiRenderer;
import client.onyx.ui.component.Component;
import client.onyx.ui.component.ComponentHost;
import client.onyx.ui.component.Icons;
import client.onyx.ui.component.RenderContext;
import client.onyx.ui.theme.Typography;
import java.util.function.IntConsumer;

public class ModuleRow extends Component {
    private static final float FLOAT_14_01 = 14.0F;
    private static final float FLOAT_5_01 = 5.0F;
    public static final float FLOAT_30_01 = 30.0F;
    private static final float FLOAT_2_01 = 2.0F;
    private final Animation animation3;
    private final Animation animation;
    private final Animation animation2;
    private int intValue2;
    private final String[] stringArray;
    private final IntConsumer intConsumer;
    private final ComponentHost[] componentHostArray;
    private boolean flag4;
    private int intValue;

    private int method_2560(float var1, float var2) {
        return !this.method_2259(var1, var2)
            ? -1
            : Math.clamp((long)((int)((var1 - this.floatValue3 - 2.0F) / this.method_2562())), 0, this.stringArray.length - 1);
    }

    public void method_2561(int var1) {
        if (var1 != this.intValue) {
            this.intValue = var1;
            this.animation3.method_603(this.method_2563(var1), 300.0F, Easings.easing2);
            this.animation.method_603(this.method_2562(), 300.0F, Easings.easing2);
            this.animation2.method_607(0.0F).method_603(19.0F, 300.0F, Easings.easing2);
            this.intConsumer.accept(var1);
        }
    }

    @Override
    public void method_2186(RenderContext var1) {
        var var3 = this;
        UiRenderer var2 = var1.uiRenderer;
        var2.method_494(this.floatValue3, this.floatValue, this.floatValue4, this.floatValue2, Float.MAX_VALUE, 1.0F, var1.method_2256().intValue8());
        var2.method_479(
            this.animation3.method_606(),
            this.floatValue + 2.0F,
            this.animation.method_606(),
            this.floatValue2 - 4.0F,
            Float.MAX_VALUE,
            var1.method_2256().intValue5()
        );
        int var13 = 0;

        for (int var12 = 0; var12 < var3.stringArray.length; var12 = ++var13) {
            boolean var8;
            if (var13 == var3.intValue) {
                var8 = true;
            } else {
                var8 = false;
            }

            var3.componentHostArray[var13]
                .method_2219(
                    var2,
                    var3.method_2563(var13),
                    var3.floatValue + 2.0F,
                    var3.method_2562(),
                    var3.floatValue2 - 4.0F,
                    Float.MAX_VALUE,
                    var1.method_2256().intValue15()
                );
            int var10;
            if (!var8) {
                var10 = var1.method_2256().intValue7();
            } else {
                var10 = var1.method_2256().intValue30();
            }

            float var11;
            if (var8) {
                var11 = var3.animation2.method_606();
            } else {
                var11 = 0.0F;
            }

            float var6 = var2.method_442(Typography.fontStyle14, var3.stringArray[var13]);
            var6 = var3.method_2563(var13) + (var3.method_2562() - var6 - var11) / 2.0F;
            float var7 = var3.floatValue + var3.floatValue2 / 2.0F;
            if (var11 > 0.5F) {
                var2.method_443(var6, var3.floatValue, var11, var3.floatValue2);
                Icons.method_2226(var2, var6 + 7.0F, var7, 14.0F, var10);
                var2.method_486();
            }

            var2.method_432(Typography.fontStyle14, var3.stringArray[var13], var6 + var11, var7, var10);
            if (var13 > 0 && var13 != var3.intValue && var13 - 1 != var3.intValue) {
                var2.method_434(var3.method_2563(var13), var3.floatValue + 2.0F, 1.0F, var3.floatValue2 - 4.0F, var1.method_2256().intValue8());
            }
        }

        if (var3.intValue2 >= 0) {
            var2.method_465();
        }
    }

    public ModuleRow(String[] var1, IntConsumer var2) {
        var var6 = this;
        this.animation3 = new Animation(0.0F);
        this.animation = new Animation(0.0F);
        this.animation2 = new Animation(0.0F);
        this.intValue2 = -1;
        this.stringArray = var1;
        this.intConsumer = var2;
        this.floatValue2 = 30.0F;
        this.componentHostArray = new ComponentHost[var1.length];
        int var5 = 0;

        for (int var3 = 0; var3 < var1.length; var3 = var5) {
            ComponentHost[] var7 = var6.componentHostArray;
            int var10001 = var5;
            ComponentHost var10002 = new ComponentHost();
            var5++;
            var7[var10001] = var10002;
        }
    }

    private float method_2562() {
        return (this.floatValue4 - 4.0F) / this.stringArray.length;
    }

    public int getIntValue() {
        return this.intValue;
    }

    @Override
    public boolean method_2183(float var1, float var2, int var3) {
        int var5 = this.method_2560(var1, var2);
        if (var3 == 0 && var5 >= 0) {
            this.method_2561(var5);
            return true;
        } else {
            return false;
        }
    }

    private float method_2563(int var1) {
        return this.floatValue3 + 2.0F + this.method_2562() * var1;
    }

    @Override
    public void method_2206(RenderContext var1) {
        RenderContext var9 = var1;
        var var10 = this;
        super.method_2206(var1);
        if (!this.flag4) {
            this.flag4 = true;
            this.animation3.method_607(this.method_2563(this.intValue));
            this.animation.method_607(this.method_2562());
            this.animation2.method_607(19.0F);
        }

        this.intValue2 = this.method_2560(var1.floatValue3, var1.floatValue2);
        int var2 = 0;

        for (int var8 = 0; var8 < var10.componentHostArray.length; var8 = var2) {
            ComponentHost var11 = var10.componentHostArray[var2];
            ComponentHost var5;
            float var6;
            boolean var7;
            if (var2 != var10.intValue2) {
                float var4 = var9.floatValue;
                ComponentHost var3 = var11;
                var7 = false;
                var6 = var4;
                var5 = var3;
            } else {
                var7 = true;
                var6 = var9.floatValue;
                var5 = var11;
            }

            var2++;
            var5.method_2220(var6, var7, false);
        }

        var10.animation3.method_605(var9.floatValue);
        var10.animation.method_605(var9.floatValue);
        var10.animation2.method_605(var9.floatValue);
    }
}
