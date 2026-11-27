package client.onyx.ui.component;

import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easings;
import client.onyx.render.ui.UiRenderer;
import client.onyx.ui.theme.Theme;

public abstract class ScrollList extends Component {
    public final Animation animation = new Animation(0.0F);
    public final Animation animation2 = new Animation(0.0F);
    public int intValue = -1;
    public float floatValue5;
    public float floatValue22;
    private float floatValue32;

    public void method_2280(RenderContext var1) {
        Component var3;
        if ((var3 = this.method_2287()) != null) {
            var1.uiRenderer
                .method_494(
                    var3.getFloatValue3(),
                    var3.getFloatValue(),
                    var3.getFloatValue4(),
                    var3.getFloatValue2(),
                    this.method_2286(),
                    2.0F,
                    var1.method_2256().intValue31()
                );
        }
    }

    public boolean method_2281(int var1, int var2) {
        if (this.list.isEmpty()) {
            return false;
        } else {
            int var3 = Math.max(1, this.method_2283());
            int var9;
            if ((var9 = var1 + var2 * var3) == 0) {
                return false;
            } else {
                ScrollList var6;
                int var7;
                if (this.intValue >= 0) {
                    var7 = Math.clamp((long)(this.intValue + var9), 0, this.list.size() - 1);
                    var6 = this;
                } else if (var9 <= 0) {
                    var7 = this.list.size() - 1;
                    var6 = this;
                } else {
                    var7 = 0;
                    var6 = this;
                }

                var6.intValue = var7;
                this.method_2285();
                return true;
            }
        }
    }

    @Override
    public boolean method_2211(float var1, float var2, double var3) {
        if (!this.method_2259(var1, var2)) {
            return super.method_2211(var1, var2, var3);
        } else {
            float var6;
            if ((var6 = Math.max(0.0F, this.floatValue22 - this.floatValue2)) <= 0.0F) {
                return false;
            } else {
                this.floatValue5 = Math.clamp(this.floatValue5 - (float)var3 * 32.0F, 0.0F, var6);
                this.floatValue32 = 0.0F;
                return true;
            }
        }
    }

    public int getIntValue() {
        return this.intValue;
    }

    public void method_2282() {
        this.intValue = -1;
    }

    public int method_2283() {
        return 1;
    }

    @Override
    public void method_2206(RenderContext var1) {
        this.method_2279(var1);
        float var13 = Math.max(0.0F, this.floatValue22 - this.floatValue2);
        this.floatValue5 = Math.clamp(this.floatValue5, 0.0F, var13);
        if (this.animation.getFloatValue3() != this.floatValue5) {
            this.animation.method_603(this.floatValue5, 250.0F, Easings.easing5);
        }

        if (this.intValue >= this.list.size()) {
            ScrollList var4;
            int var5;
            if (!this.list.isEmpty()) {
                var5 = this.list.size() - 1;
                var4 = this;
            } else {
                var5 = -1;
                var4 = this;
            }

            var4.intValue = var5;
        }

        this.animation.method_605(var1.floatValue);
        this.floatValue32 = this.floatValue32 + var1.floatValue;
        boolean var6;
        if (!(var13 > 0.0F) || !this.flag && !(this.floatValue32 < 900.0F)) {
            var6 = false;
        } else {
            var6 = true;
        }

        float var14 = this.animation2.getFloatValue3();
        float var8;
        float var9;
        if (!var6) {
            float var7 = var14;
            var9 = 0.0F;
            var8 = var7;
        } else {
            var9 = 1.0F;
            var8 = var14;
        }

        if (var8 != var9) {
            Animation var11;
            float var12;
            if (!var6) {
                Animation var10 = this.animation2;
                var12 = 0.0F;
                var11 = var10;
            } else {
                var12 = 1.0F;
                var11 = this.animation2;
            }

            var11.method_603(var12, 200.0F, Easings.easing6);
        }

        this.animation2.method_605(var1.floatValue);
        super.method_2206(var1);
    }

    private void method_2284(RenderContext var1) {
        float var2 = this.animation2.method_606();
        float var6 = Math.max(0.0F, this.floatValue22 - this.floatValue2);
        if (!(var2 <= 0.01F) && !(var6 <= 0.0F)) {
            float var4 = this.floatValue2 - 8.0F;
            float var5 = Math.max(24.0F, var4 * (this.floatValue2 / this.floatValue22));
            float var7 = this.floatValue + 4.0F + (var4 - var5) * (this.animation.method_606() / var6);
            var1.uiRenderer
                .method_479(
                    this.floatValue3 + this.floatValue4 - 7.0F,
                    var7,
                    4.0F,
                    var5,
                    Float.MAX_VALUE,
                    Theme.method_180(var1.method_2256().intValue12(), var2 * 0.8F)
                );
        }
    }

    private void method_2285() {
        Component var3;
        if ((var3 = this.method_2287()) != null) {
            float var2;
            float var4 = (var2 = var3.getFloatValue() + this.animation.method_606() - this.floatValue) + var3.getFloatValue2();
            if (var2 < this.floatValue5) {
                this.floatValue5 = var2;
            }

            if (var4 > this.floatValue5 + this.floatValue2) {
                this.floatValue5 = var4 - this.floatValue2;
            }

            this.floatValue32 = 0.0F;
        }
    }

    public float method_2286() {
        return 12.0F;
    }

    public abstract void method_2279(RenderContext var1);

    public Component method_2287() {
        return this.intValue >= 0 && this.intValue < this.list.size() ? this.list.get(this.intValue) : null;
    }

    @Override
    public void method_2186(RenderContext var1) {
        UiRenderer var2 = var1.uiRenderer;
        var2.method_443(this.floatValue3, this.floatValue, this.floatValue4, this.floatValue2);
        this.method_2288(var1);
        super.method_2186(var1);
        this.method_2280(var1);
        var2.method_486();
        this.method_2284(var1);
    }

    public void method_2288(RenderContext var1) {
    }
}
