package client.onyx.ui.clickgui.setting;

import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easings;
import client.onyx.render.ui.UiRenderer;
import client.onyx.setting.impl.ColorSetting;
import client.onyx.ui.component.ComponentHost;
import client.onyx.ui.component.Icons;
import client.onyx.ui.component.RenderContext;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.Typography;
import client.onyx.ui.theme.color.ColorUtils;

public class ColorView extends SettingView {
    private static final int[] intValueArray = new int[]{-65536, -256, -16711936, -16711681, -16776961, -65281, -65536};
    private static final float FLOAT_6_012 = 6.0F;
    private static final float FLOAT_1_01 = 1.0F;
    private static final float FLOAT_3_012 = 3.0F;
    private static final float FLOAT_11_01 = 11.0F;
    private static final float FLOAT_15_012 = 15.0F;
    private static final float FLOAT_12_01 = 12.0F;
    private static final float FLOAT_120_01 = 120.0F;
    private static final float FLOAT_0_321 = 0.32F;
    private static final float FLOAT_9_01 = 9.0F;
    private static final float FLOAT_13_01 = 13.0F;
    private static final float FLOAT_15_02 = 15.0F;
    private static final float FLOAT_14_01 = 14.0F;
    private static final float FLOAT_18_01 = 18.0F;
    private final Animation animation3 = new Animation(0.0F);
    private final ComponentHost componentHost = new ComponentHost();
    private final ComponentHost componentHost2 = new ComponentHost();
    private final Animation animation22 = new Animation(0.0F);
    private ColorView.DragTarget dragTarget = ColorView.DragTarget.NONE;
    private final ColorSetting colorSetting;
    private float floatValue32;
    private float floatValue5;
    private float floatValue22;
    private int intValue;
    private boolean flag5;

    private boolean method_2488(float var1, float var2) {
        return var1 >= this.floatValue3 && var1 < this.floatValue3 + this.floatValue4 && var2 >= this.floatValue && var2 < this.floatValue + this.method_2565();
    }

    private float method_2489() {
        return this.floatValue4 - 30.0F * this.method_2501() - 3.0F;
    }

    private float method_2490() {
        return this.method_2498() + 18.0F + 12.0F;
    }

    private float method_2491() {
        return this.floatValue3 + this.floatValue4 - 11.0F - 8.0F - 9.0F;
    }

    @Override
    public float method_2387(RenderContext var1) {
        float var4 = this.method_2565();
        float var3;
        if (this.method_2495()) {
            var3 = 29.0F;
        } else {
            var3 = 0.0F;
        }

        return var4 + (this.method_2571() - this.floatValue + 120.0F + var3 + 6.0F - var4) * this.animation3.method_606();
    }

    private void method_2492() {
        if (this.colorSetting.method_128() != this.intValue) {
            float[] var2;
            if ((var2 = ColorUtils.method_215(this.colorSetting.method_128()))[1] > 0.001F) {
                this.floatValue32 = var2[0];
            }

            this.floatValue5 = var2[1];
            this.floatValue22 = var2[2];
            this.intValue = this.colorSetting.method_128();
        }
    }

    private boolean method_2493(float var1, float var2) {
        float var4 = this.method_2496();
        return var1 >= this.floatValue3 && var1 < this.floatValue3 + this.floatValue4 && var2 >= var4 && var2 < var4 + 15.0F;
    }

    private void method_2494(RenderContext var1) {
        UiRenderer var2 = var1.uiRenderer;
        float var5 = this.method_2571();
        float var4 = this.method_2490();
        var2.method_479(var4, var5, 18.0F, 120.0F, Float.MAX_VALUE, this.method_2572(var1.method_2256().intValue11()));
        var2.method_444(
            var4,
            var5,
            18.0F,
            120.0F,
            Float.MAX_VALUE,
            this.method_2572(this.colorSetting.method_119()),
            this.method_2572(this.colorSetting.method_119()),
            0,
            0
        );
        this.method_2504(var1, var4, var5 + (1.0F - ColorUtils.method_213(this.colorSetting.method_128()) / 255.0F) * 120.0F);
    }

    private boolean method_2495() {
        return this.colorSetting.isFlag2();
    }

    private float method_2496() {
        return this.method_2571() + 120.0F + 14.0F;
    }

    private void method_2497(RenderContext var1) {
        float var2 = this.method_2571();
        switch (this.dragTarget) {
            case SHADE:
                this.floatValue5 = Math.clamp((var1.floatValue3 - this.floatValue3) / this.method_2489(), 0.0F, 1.0F);
                this.floatValue22 = 1.0F - Math.clamp((var1.floatValue2 - var2) / 120.0F, 0.0F, 1.0F);
                this.method_2499();
                return;
            case HUE:
                this.floatValue32 = Math.clamp((var1.floatValue2 - var2) / 120.0F, 0.0F, 1.0F) * 360.0F;
                this.method_2499();
                return;
            case ALPHA:
                float var4 = 1.0F - Math.clamp((var1.floatValue2 - var2) / 120.0F, 0.0F, 1.0F);
                this.intValue = ColorUtils.method_209(this.colorSetting.method_128(), var4);
                this.colorSetting.setValue(this.intValue);
                return;
        }
    }

    @Override
    public boolean method_2189(float var1, float var2, int var3) {
        boolean var4;
        if (this.dragTarget != ColorView.DragTarget.NONE) {
            var4 = true;
        } else {
            var4 = false;
        }

        this.dragTarget = ColorView.DragTarget.NONE;
        return var4;
    }

    private float method_2498() {
        return this.floatValue3 + this.method_2489() + 12.0F;
    }

    private void method_2499() {
        int var2;
        this.intValue = var2 = ColorUtils.method_202(
            this.floatValue32, this.floatValue5, this.floatValue22, ColorUtils.method_213(this.colorSetting.method_128())
        );
        this.colorSetting.setValue(var2);
    }

    @Override
    public boolean method_2183(float var1, float var2, int var3) {
        if (!this.method_2577() || var3 != 0) {
            return false;
        } else if (this.method_2488(var1, var2)) {
            ColorView var5;
            boolean var6;
            if (this.flag5) {
                var6 = false;
                var5 = this;
            } else {
                var6 = true;
                var5 = this;
            }

            var5.flag5 = var6;
            Animation var8;
            float var9;
            if (!this.flag5) {
                Animation var7 = this.animation3;
                var9 = 0.0F;
                var8 = var7;
            } else {
                var9 = 1.0F;
                var8 = this.animation3;
            }

            var8.method_603(var9, 300.0F, Easings.easing2);
            return true;
        } else if (this.animation3.method_606() < 0.5F) {
            return false;
        } else if (this.method_2495() && this.method_2493(var1, var2)) {
            ColorSetting var17 = this.colorSetting;
            ColorSetting var11;
            boolean var12;
            if (this.colorSetting.isFlag4()) {
                ColorSetting var10 = var17;
                var12 = false;
                var11 = var10;
            } else {
                var12 = true;
                var11 = var17;
            }

            var11.method_124(var12);
            return true;
        } else if (this.colorSetting.isFlag4()) {
            return false;
        } else {
            if (!this.method_2502(var1, var2, this.floatValue3, this.method_2489())) {
                if (!this.method_2502(var1, var2, this.method_2498(), 18.0F)) {
                    if (!this.colorSetting.isFlag() || !this.method_2502(var1, var2, this.method_2490(), 18.0F)) {
                        return false;
                    }

                    this.dragTarget = ColorView.DragTarget.ALPHA;
                } else {
                    this.dragTarget = ColorView.DragTarget.HUE;
                }
            } else {
                this.dragTarget = ColorView.DragTarget.SHADE;
            }

            return true;
        }
    }

    private void method_2500(RenderContext var1) {
        UiRenderer var7 = var1.uiRenderer;
        float var8 = this.method_2496();
        float var4 = this.floatValue3 + this.floatValue4 - 15.0F;
        float var5 = var8 + 0.0F;
        float var6 = var8 + 7.5F;
        float var2 = this.animation22.method_606();
        this.componentHost2
            .method_2219(var7, this.floatValue3 - 6.0F, var8 - 6.0F, this.floatValue4 + 12.0F, 27.0F, 8.0F, this.method_2572(var1.method_2256().intValue15()));
        var7.method_432(Typography.fontStyle15, "Use accent colour", this.floatValue3, var6, this.method_2572(var1.method_2256().intValue15()));
        if (var2 > 0.01F) {
            float var9 = (1.0F - var2) * 15.0F / 2.0F;
            var7.method_479(var4 + var9, var5 + var9, 15.0F - var9 * 2.0F, 15.0F - var9 * 2.0F, 4.0F, this.method_2572(var1.method_2256().intValue31()));
        }

        if (var2 < 0.99F) {
            var7.method_494(var4, var5, 15.0F, 15.0F, 4.0F, 1.5F, this.method_2572(var1.method_2256().intValue7()));
        }

        if (var2 > 0.15F) {
            float var10 = Math.clamp((var2 - 0.15F) / 0.85F, 0.0F, 1.0F);
            Icons.method_2226(
                var7, var4 + 7.5F, var5 + 7.5F, 13.0F * (0.7F + 0.3F * var10), this.method_2572(Theme.method_180(var1.method_2256().intValue33(), var10))
            );
        }
    }

    private float method_2501() {
        return this.colorSetting.isFlag() ? 2.0F : 1.0F;
    }

    @Override
    public void method_2206(RenderContext var1) {
        super.method_2206(var1);
        this.animation3.method_605(var1.floatValue);
        this.componentHost.method_2220(var1.floatValue, this.method_2488(var1.floatValue3, var1.floatValue2), false);
        boolean var3;
        if (this.method_2495() && this.animation3.method_606() > 0.5F && this.method_2493(var1.floatValue3, var1.floatValue2)) {
            var3 = true;
        } else {
            var3 = false;
        }

        this.componentHost2.method_2220(var1.floatValue, var3, false);
        float var12 = this.animation22.getFloatValue3();
        float var5;
        float var6;
        if (!this.colorSetting.isFlag4()) {
            float var4 = var12;
            var6 = 0.0F;
            var5 = var4;
        } else {
            var6 = 1.0F;
            var5 = var12;
        }

        if (var5 != var6) {
            Animation var13 = this.animation22;
            Animation var8;
            float var9;
            if (!this.colorSetting.isFlag4()) {
                Animation var7 = var13;
                var9 = 0.0F;
                var8 = var7;
            } else {
                var9 = 1.0F;
                var8 = var13;
            }

            var8.method_603(var9, 200.0F, Easings.easing2);
        }

        this.animation22.method_605(var1.floatValue);
        if (this.dragTarget == ColorView.DragTarget.NONE) {
            this.method_2492();
        } else {
            this.method_2497(var1);
        }
    }

    private boolean method_2502(float var1, float var2, float var3, float var4) {
        float var6 = this.method_2571();
        return var1 >= var3 && var1 < var3 + var4 && var2 >= var6 && var2 < var6 + 120.0F;
    }

    @Override
    public void method_2389(RenderContext var1, float var2) {
        UiRenderer var5 = var1.uiRenderer;
        this.componentHost
            .method_2219(
                var5,
                this.floatValue3 - 6.0F,
                this.floatValue,
                this.floatValue4 + 12.0F,
                this.method_2565(),
                8.0F,
                this.method_2572(var1.method_2256().intValue15())
            );
        String var3 = this.colorSetting.method_121();
        float var4 = this.method_2491() - 9.0F - 8.0F;
        this.method_2575(var1, var4 - var5.method_442(Typography.fontStyle2, var3) - 12.0F - this.floatValue3);
        var5.method_476(Typography.fontStyle2, var3, var4, this.method_2578(), this.method_2572(var1.method_2256().intValue7()));
        var5.method_489(this.method_2491(), this.method_2578(), 9.0F, this.method_2572(this.colorSetting.method_118()));
        var5.method_488(this.method_2491(), this.method_2578(), 9.0F, 1.0F, this.method_2572(var1.method_2256().intValue8()));
        var5.method_441();
        var5.method_481().method_650(this.floatValue3 + this.floatValue4 - 5.5F, this.method_2578());
        var5.method_481().method_648((float) Math.PI * this.animation3.method_606());
        Icons.method_2222(var5, 0.0F, 0.0F, 11.0F, this.method_2572(var1.method_2256().intValue7()));
        var5.method_435();
        float var7 = this.animation3.method_606();
        if (!(var7 <= 0.01F)) {
            var5.method_443(this.floatValue3 - 6.0F, this.floatValue, this.floatValue4 + 12.0F, this.floatValue2);
            var5.method_440(var7);
            var5.method_440(1.0F - 0.68F * this.animation22.method_606());
            this.method_2505(var1);
            this.method_2503(var1);
            if (this.colorSetting.isFlag()) {
                this.method_2494(var1);
            }

            var5.method_430();
            if (this.method_2495()) {
                this.method_2500(var1);
            }

            var5.method_430();
            var5.method_486();
        }
    }

    private void method_2503(RenderContext var1) {
        var var2 = this;
        UiRenderer var7 = var1.uiRenderer;
        float var3 = this.method_2571();
        float var4 = this.method_2498();
        float var5 = 20.0F;
        float var6 = 9.0F;
        int var20 = 0;

        for (int var19 = 0; var19 < 6; var19 = var20) {
            float var8;
            float var10000 = (var8 = var3 + var20 * var5) + var5;
            float var15;
            float var16;
            if (var20 != 5) {
                float var14 = var10000;
                var16 = 1.0F;
                var15 = var14;
            } else {
                var16 = 0.0F;
                var15 = var10000;
            }

            float var9 = var15 + var16;
            float var17;
            if (var20 == 0) {
                var17 = var6;
            } else {
                var17 = 0.0F;
            }

            float var18;
            if (var20 == 5) {
                var18 = var6;
            } else {
                var18 = 0.0F;
            }

            int var12 = intValueArray[var20];
            int var13 = ColorUtils.method_202((var9 - var3) / 120.0F * 360.0F, 1.0F, 1.0F, 255);
            float var10004 = var9 - var8;
            int var10009 = var2.method_2572(var12);
            int var10010 = var2.method_2572(var12);
            int var10011 = var2.method_2572(var13);
            var20++;
            var7.method_490(var4, var8, 18.0F, var10004, var17, var17, var18, var18, var10009, var10010, var10011, var2.method_2572(var13));
        }

        var2.method_2504(var1, var4, var3 + var2.floatValue32 / 360.0F * 120.0F);
    }

    private void method_2504(RenderContext var1, float var2, float var3) {
        UiRenderer var5 = var1.uiRenderer;
        var5.method_479(var2 - 3.0F, var3 - 3.0F, 24.0F, 6.0F, Float.MAX_VALUE, this.method_2572(-1));
        var5.method_494(
            var2 - 3.0F, var3 - 3.0F, 24.0F, 6.0F, Float.MAX_VALUE, 1.0F, this.method_2572(Theme.method_180(var1.method_2256().intValue25(), 0.35F))
        );
    }

    private void method_2505(RenderContext var1) {
        UiRenderer var4 = var1.uiRenderer;
        float var6 = this.method_2571();
        float var2 = this.method_2489();
        int var5 = ColorUtils.method_202(this.floatValue32, 1.0F, 1.0F, 255);
        var4.method_444(this.floatValue3, var6, var2, 120.0F, 12.0F, this.method_2572(-1), this.method_2572(var5), this.method_2572(var5), this.method_2572(-1));
        var4.method_444(this.floatValue3, var6, var2, 120.0F, 12.0F, 0, 0, this.method_2572(-16777216), this.method_2572(-16777216));
        var4.method_494(this.floatValue3, var6, var2, 120.0F, 12.0F, 1.0F, this.method_2572(var1.method_2256().intValue12()));
        float var8 = this.floatValue3 + this.floatValue5 * var2;
        float var7 = var6 + (1.0F - this.floatValue22) * 120.0F;
        var4.method_489(var8, var7, 6.0F, this.method_2572(-1));
        var4.method_489(var8, var7, 4.5F, this.method_2572(this.colorSetting.method_119()));
    }

    public ColorView(ColorSetting var1) {
        super(var1);
        this.colorSetting = var1;
        float[] var2;
        this.floatValue32 = (var2 = ColorUtils.method_215(var1.method_128()))[0];
        this.floatValue5 = var2[1];
        this.floatValue22 = var2[2];
        this.intValue = var1.method_128();
        Animation var8 = this.animation22;
        Animation var4;
        float var5;
        if (!var1.isFlag4()) {
            Animation var3 = var8;
            var5 = 0.0F;
            var4 = var3;
        } else {
            var5 = 1.0F;
            var4 = var8;
        }

        var4.method_607(var5);
    }

    private static enum DragTarget {
        NONE,
        SHADE,
        HUE,
        ALPHA;
    }
}
