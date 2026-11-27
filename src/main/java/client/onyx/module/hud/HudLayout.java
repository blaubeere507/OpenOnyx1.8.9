package client.onyx.module.hud;

import client.onyx.module.hud.island.Watermark;
import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easings;
import client.onyx.render.font.FontStyle;
import client.onyx.render.ui.PanelRenderer;
import client.onyx.render.ui.UiRenderer;
import client.onyx.ui.component.ComponentHost;
import client.onyx.ui.component.Icons;
import client.onyx.ui.theme.ColorRoles;
import client.onyx.ui.theme.Palette;
import client.onyx.ui.theme.Typography;

public final class HudLayout {
    private static final Watermark.Stat[] statArray = Watermark.Stat.values();
    private static final float FLOAT_6_01 = 6.0F;
    private static final float FLOAT_12_01 = 12.0F;
    private static final float FLOAT_22_01 = 22.0F;
    private static final float FLOAT_13_01 = 13.0F;
    private static final float FLOAT_8_01 = 8.0F;
    private static final float FLOAT_8_02 = 8.0F;
    private static final float FLOAT_6_02 = 6.0F;
    private static final int INT_31 = 3;
    private static final float FLOAT_1_01 = 1.0F;
    private static final float FLOAT_3_01 = 3.0F;
    private final ComponentHost[] componentHostArray;
    private final Animation animation;
    private final Watermark watermark;
    private float floatValue;
    private boolean flag;
    private float floatValue2;
    private float floatValue3;
    private float floatValue4;

    public HudLayout(Watermark var1) {
        var var5 = this;
        this.componentHostArray = new ComponentHost[statArray.length];
        this.animation = new Animation(0.0F);
        this.watermark = var1;
        int var4 = 0;

        for (int var2 = 0; var2 < var5.componentHostArray.length; var2 = var4) {
            ComponentHost[] var6 = var5.componentHostArray;
            int var10001 = var4;
            ComponentHost var10002 = new ComponentHost();
            var4++;
            var6[var10001] = var10002;
        }
    }

    public boolean isFlag() {
        return this.flag;
    }

    public void method_1468(UiRenderer var1, float var2, float var3, float var4, float var5) {
        this.flag = true;
        this.floatValue3 = method_1469(var1);
        this.floatValue4 = 6.0F + 22.0F * statArray.length;
        this.floatValue2 = Math.clamp(var2, 6.0F, Math.max(6.0F, var4 - this.floatValue3 - 6.0F));
        this.floatValue = Math.clamp(var3, 6.0F, Math.max(6.0F, var5 - this.floatValue4 - 6.0F));
        this.animation.method_603(1.0F, 200.0F, Easings.easing7);
    }

    private static float method_1469(UiRenderer var0) {
        UiRenderer var1 = var0;
        float var7 = 0.0F;
        Watermark.Stat[] var4 = statArray;
        int var3 = statArray.length;
        int var2 = 0;

        for (int var6 = 0; var6 < var3; var6 = var2) {
            Watermark.Stat var5 = var4[var2];
            FontStyle var10002 = Typography.fontStyle16;
            String var10003 = var5.getDisplayName();
            var2++;
            var7 = Math.max(var7, var1.method_442(var10002, var10003));
        }

        return 57.0F + var7;
    }

    public void method_1470(float var1, float var2, float var3) {
        var var4 = this;
        this.animation.method_605(var1);
        int var8 = 0;

        for (int var7 = 0; var7 < var4.componentHostArray.length; var7 = var8) {
            boolean var6;
            if (var4.flag && var4.method_1473(var8, var2, var3)) {
                var6 = true;
            } else {
                var6 = false;
            }

            ComponentHost var10000 = var4.componentHostArray[var8];
            var8++;
            var10000.method_2220(var1, var6, false);
        }
    }

    public void method_1471(UiRenderer var1, Palette var2) {
        var var3 = this;
        float var5;
        if (!((var5 = this.animation.method_606()) <= 0.01F)) {
            PanelRenderer.method_602();
            int var4 = ColorRoles.method_182(3, var2);
            float var12 = (1.0F - var5) * 6.0F;
            var1.method_440(var5);
            var1.method_441();
            var1.method_451(0.0F, var12);
            var1.method_485(this.floatValue2, this.floatValue, this.floatValue3, this.floatValue4, 12.0F, 3);
            PanelRenderer.method_598(var1, this.floatValue2, this.floatValue, this.floatValue3, this.floatValue4, 12.0F, var4);
            int var18 = 0;

            for (int var11 = 0; var11 < statArray.length; var11 = var18) {
                Watermark.Stat var17 = statArray[var18];
                float var13;
                float var6 = (var13 = var3.floatValue + 3.0F + var18 * 22.0F) + 11.0F;
                boolean var7 = var3.watermark.stats.method_143(var17);
                var3.componentHostArray[var18].method_2219(var1, var3.floatValue2 + 2.0F, var13, var3.floatValue3 - 4.0F, 22.0F, 8.0F, var2.intValue15());
                float var14 = var3.floatValue2 + 8.0F;
                float var8 = var6 - 6.0F;
                float var10;
                if (!var7) {
                    var1.method_494(var14, var8, 12.0F, 12.0F, 4.0F, 1.5F, var2.intValue7());
                    var10 = var14;
                } else {
                    var1.method_479(var14, var8, 12.0F, 12.0F, 4.0F, var2.intValue31());
                    Icons.method_2226(var1, var14 + 6.0F, var8 + 6.0F, 9.6F, var2.intValue33());
                    var10 = var14;
                }

                float var15;
                float var10001 = var15 = var10 + 12.0F + 8.0F;
                Icons.method_2224(var1, var17, var15 + 6.5F, var6, 13.0F, var2.intValue7());
                float var16 = var10001 + 13.0F + 8.0F;
                FontStyle var19 = Typography.fontStyle16;
                String var10002 = var17.getDisplayName();
                var18++;
                var1.method_432(var19, var10002, var16, var6, var2.intValue15());
            }

            var1.method_494(var3.floatValue2, var3.floatValue, var3.floatValue3, var3.floatValue4, 12.0F, 1.0F, var2.intValue12());
            var1.method_435();
            var1.method_430();
        }
    }

    public boolean method_1472() {
        return this.flag || this.animation.method_606() > 0.001F || !this.animation.method_604();
    }

    private boolean method_1473(int var1, float var2, float var3) {
        float var5 = this.floatValue + 3.0F + var1 * 22.0F;
        return var2 >= this.floatValue2 && var2 < this.floatValue2 + this.floatValue3 && var3 >= var5 && var3 < var5 + 22.0F;
    }

    public void method_1474() {
        if (this.flag) {
            this.flag = false;
            this.animation.method_603(0.0F, 100.0F, Easings.easing);
        }
    }

    public boolean method_1475(float var1, float var2) {
        var var3 = this;
        if (!this.flag) {
            return false;
        } else if (!(var1 < this.floatValue2)
            && !(var1 >= this.floatValue2 + this.floatValue3)
            && !(var2 < this.floatValue)
            && !(var2 >= this.floatValue + this.floatValue4)) {
            int var5 = 0;

            for (int var4 = 0; var4 < statArray.length; var4 = ++var5) {
                if (var3.method_1473(var5, var1, var2)) {
                    var3.watermark.stats.method_141(statArray[var5]);
                    break;
                }
            }

            return true;
        } else {
            return false;
        }
    }
}
