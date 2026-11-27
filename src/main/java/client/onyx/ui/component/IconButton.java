package client.onyx.ui.component;

import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easings;
import client.onyx.render.anim.Spring;
import client.onyx.render.ui.UiRenderer;
import client.onyx.ui.theme.Theme;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public class IconButton extends Component {
    private static final float FLOAT_10_01 = 10.0F;
    public static final float FLOAT_22_01 = 22.0F;
    public static final float FLOAT_38_01 = 38.0F;
    private static final float FLOAT_6_01 = 6.0F;
    private static final float FLOAT_8_51 = 8.5F;
    private final Animation animation2 = new Animation(0.0F);
    private final Animation animation = new Animation(6.0F);
    private final Spring spring = new Spring(0);
    private final Spring spring2 = new Spring(0);
    private final BooleanSupplier booleanSupplier;
    private final Consumer<Boolean> consumer;
    private boolean flag4;
    private boolean flag22;

    public IconButton(BooleanSupplier var1, Consumer<Boolean> var2) {
        this.booleanSupplier = var1;
        this.consumer = var2;
        this.floatValue4 = 38.0F;
        this.floatValue2 = 22.0F;
    }

    @Override
    public void method_2186(RenderContext var1) {
        UiRenderer var4 = var1.uiRenderer;
        UiRenderer var10001 = var1.uiRenderer;
        float var3 = this.floatValue + this.floatValue2 / 2.0F;
        float var28 = this.floatValue4 - 22.0F;
        float var29 = this.floatValue3 + 11.0F + var28 * this.animation2.method_606();
        float var5 = this.animation.method_606();
        var10001.method_479(this.floatValue3, this.floatValue, this.floatValue4, this.floatValue2, Float.MAX_VALUE, this.spring.method_617());
        if (this.animation2.method_606() < 0.999F) {
            var4.method_494(
                this.floatValue3,
                this.floatValue,
                this.floatValue4,
                this.floatValue2,
                Float.MAX_VALUE,
                1.5F,
                Theme.method_180(var1.method_2256().intValue8(), 1.0F - this.animation2.method_606())
            );
        }

        if (this.flag) {
            UiRenderer var11;
            float var12;
            float var13;
            float var14;
            int var15;
            IconButton var16;
            if (!this.booleanSupplier.getAsBoolean()) {
                float var9 = 14.0F;
                int var10004 = var1.method_2256().intValue15();
                var16 = this;
                var15 = var10004;
                var14 = var9;
                var13 = var3;
                var12 = var29;
                var11 = var4;
            } else {
                int var30 = var1.method_2256().intValue31();
                var16 = this;
                var15 = var30;
                var14 = 14.0F;
                var13 = var3;
                var12 = var29;
                var11 = var4;
            }

            UiRenderer var22;
            float var23;
            float var24;
            float var25;
            int var26;
            float var27;
            if (!var16.flag3) {
                var27 = 0.08F;
                var26 = var15;
                var25 = var14;
                var24 = var13;
                var23 = var12;
                var22 = var11;
            } else {
                var27 = 0.1F;
                var26 = var15;
                var25 = var14;
                var24 = var13;
                var23 = var12;
                var22 = var11;
            }

            var22.method_489(var23, var24, var25, Theme.method_180(var26, var27));
        }

        var4.method_489(var29, var3, var5, this.spring2.method_617());
    }

    @Override
    public boolean method_2183(float var1, float var2, int var3) {
        if (var3 == 0 && this.method_2259(var1, var2)) {
            this.flag3 = true;
            Consumer var9 = this.consumer;
            Consumer var5;
            boolean var6;
            if (this.booleanSupplier.getAsBoolean()) {
                Consumer var4 = var9;
                var6 = false;
                var5 = var4;
            } else {
                var6 = true;
                var5 = var9;
            }

            var5.accept(var6);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean method_2189(float var1, float var2, int var3) {
        return this.flag3 = false;
    }

    @Override
    public void method_2206(RenderContext var1) {
        super.method_2206(var1);
        int var6;
        boolean var19;
        if (!(var19 = this.booleanSupplier.getAsBoolean())) {
            var6 = var1.method_2256().intValue11();
        } else {
            var6 = var1.method_2256().intValue31();
        }

        int var8;
        if (!var19) {
            var8 = var1.method_2256().intValue8();
        } else {
            var8 = var1.method_2256().intValue33();
        }

        if (!this.flag4) {
            this.flag4 = true;
            this.flag22 = var19;
            Animation var10;
            float var11;
            if (!var19) {
                Animation var9 = this.animation2;
                var11 = 0.0F;
                var10 = var9;
            } else {
                var11 = 1.0F;
                var10 = this.animation2;
            }

            var10.method_607(var11);
            Animation var13;
            float var14;
            if (!var19) {
                Animation var12 = this.animation;
                var14 = 6.0F;
                var13 = var12;
            } else {
                var14 = 8.5F;
                var13 = this.animation;
            }

            var13.method_607(var14);
            this.spring.method_618(var6);
            this.spring2.method_618(var8);
        }

        if (var19 != this.flag22) {
            this.flag22 = var19;
            Animation var16;
            float var17;
            if (!var19) {
                Animation var15 = this.animation2;
                var17 = 0.0F;
                var16 = var15;
            } else {
                var17 = 1.0F;
                var16 = this.animation2;
            }

            var16.method_603(var17, 300.0F, Easings.easing2);
        }

        this.spring.method_613(var6, 300.0F, Easings.easing6);
        this.spring2.method_613(var8, 300.0F, Easings.easing6);
        float var18;
        if (this.flag3) {
            var18 = 10.0F;
        } else if (var19) {
            var18 = 8.5F;
        } else {
            var18 = 6.0F;
        }

        if (this.animation.getFloatValue3() != var18) {
            this.animation.method_603(var18, 150.0F, Easings.easing6);
        }

        this.animation2.method_605(var1.floatValue);
        this.animation.method_605(var1.floatValue);
        this.spring.method_614(var1.floatValue);
        this.spring2.method_614(var1.floatValue);
    }
}
