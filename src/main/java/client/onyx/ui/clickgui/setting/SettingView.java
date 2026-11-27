package client.onyx.ui.clickgui.setting;

import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easings;
import client.onyx.render.font.FontStyle;
import client.onyx.setting.ModeSetting;
import client.onyx.setting.Setting;
import client.onyx.setting.SettingGroup;
import client.onyx.setting.TabbedSettingGroup;
import client.onyx.setting.impl.BlockListSetting;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.ButtonSetting;
import client.onyx.setting.impl.ColorSetting;
import client.onyx.setting.impl.EnumListSetting;
import client.onyx.setting.impl.EnumSetSetting;
import client.onyx.setting.impl.EnumSetting;
import client.onyx.setting.impl.KeybindSetting;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.setting.impl.RangeSetting;
import client.onyx.setting.impl.TextSetting;
import client.onyx.ui.component.Component;
import client.onyx.ui.component.RenderContext;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.Typography;
import client.onyx.ui.theme.color.ColorUtils;

public abstract class SettingView extends Component {
    public static final FontStyle fontStyle = Typography.fontStyle15;
    public static final FontStyle fontStyle2 = Typography.fontStyle2;
    public static final float FLOAT_6_01 = 6.0F;
    public static final float FLOAT_3_01 = 3.0F;
    public static final float FLOAT_13_51 = 13.5F;
    public static final float FLOAT_15_01 = 15.0F;
    private final Animation animation = new Animation(0.0F);
    private final Animation animation2 = new Animation(0.0F);
    public final Setting setting;
    private boolean flag4;
    private boolean flag22;

    public float method_2564() {
        return !this.method_2573() ? this.method_2569() : this.method_2566() + fontStyle2.method_643();
    }

    public float method_2565() {
        return this.method_2564() - this.floatValue + 6.0F;
    }

    public float method_2566() {
        return this.method_2569() + this.method_2453().method_644() + 3.0F;
    }

    public float method_2567() {
        return this.animation.method_606();
    }

    public Setting getSetting() {
        return this.setting;
    }

    public void method_2568(RenderContext var1, float var2) {
        if (this.method_2573()) {
            var1.uiRenderer
                .method_428(
                    fontStyle2,
                    var1.uiRenderer.method_469(fontStyle2, this.setting.getDescription(), var2),
                    this.floatValue3,
                    this.method_2566(),
                    this.method_2572(Theme.method_180(var1.method_2256().intValue7(), 0.85F))
                );
        }
    }

    public float method_2569() {
        return this.floatValue + 6.0F + this.method_2453().method_643();
    }

    public abstract void method_2389(RenderContext var1, float var2);

    public float method_2570() {
        return this.animation2.method_606();
    }

    @Override
    public void method_2186(RenderContext var1) {
        float var3;
        if (!((var3 = this.animation2.method_606()) <= 0.01F)) {
            this.method_2389(var1, var3);
        }
    }

    @Override
    public boolean method_2183(float var1, float var2, int var3) {
        return this.method_2577() && super.method_2183(var1, var2, var3);
    }

    public float method_2571() {
        return this.method_2564() + 13.5F;
    }

    public int method_2572(int var1) {
        return Theme.method_180(var1, this.method_2570() * (ColorUtils.method_213(var1) / 255.0F));
    }

    public boolean method_2573() {
        return !this.setting.getDescription().isEmpty();
    }

    public static SettingView method_2574(Setting var0) {
        if (var0 instanceof BooleanSetting var15) {
            return new ButtonView(var15);
        } else if (var0 instanceof NumberSetting var14) {
            return new EnumView(var14);
        } else if (var0 instanceof RangeSetting var13) {
            return new EnumSetView(var13);
        } else if (var0 instanceof EnumSetting var12) {
            return new NumberView(var12);
        } else if (var0 instanceof EnumSetSetting var11) {
            return new SettingSectionView(var11);
        } else if (var0 instanceof EnumListSetting var10) {
            return new TextView(var10);
        } else if (var0 instanceof ColorSetting var9) {
            return new ColorView(var9);
        } else if (var0 instanceof ButtonSetting var8) {
            return new ItemListView(var8);
        } else if (var0 instanceof KeybindSetting var7) {
            return new KeybindView(var7);
        } else if (var0 instanceof BlockListSetting var6) {
            return new BlockListView(var6);
        } else if (var0 instanceof TextSetting var5) {
            return new RangeView(var5);
        } else if (var0 instanceof ModeSetting var4) {
            return new BooleanView(var4);
        } else if (var0 instanceof TabbedSettingGroup var3) {
            return new TabbedGroupView(var3);
        } else {
            return var0 instanceof SettingGroup var2 ? new ModeView(var2) : null;
        }
    }

    public SettingView(Setting var1) {
        this.setting = var1;
    }

    public void method_2575(RenderContext var1, float var2) {
        var1.uiRenderer
            .method_428(
                this.method_2453(),
                var1.uiRenderer.method_469(this.method_2453(), this.setting.getName(), var2),
                this.floatValue3,
                this.floatValue + 6.0F,
                this.method_2572(var1.method_2256().intValue15())
            );
        this.method_2568(var1, var2);
    }

    public float method_2576() {
        return this.animation2.method_606();
    }

    @Override
    public void method_2206(RenderContext var1) {
        super.method_2206(var1);
        boolean var17 = this.setting.isVisible();
        float var3 = this.method_2387(var1);
        if (!this.flag22) {
            this.flag22 = true;
            this.flag4 = var17;
            Animation var5;
            float var6;
            if (!var17) {
                Animation var4 = this.animation;
                var6 = 0.0F;
                var5 = var4;
            } else {
                var6 = var3;
                var5 = this.animation;
            }

            var5.method_607(var6);
            Animation var8;
            float var9;
            if (!var17) {
                Animation var7 = this.animation2;
                var9 = 0.0F;
                var8 = var7;
            } else {
                var9 = 1.0F;
                var8 = this.animation2;
            }

            var8.method_607(var9);
        }

        SettingView var16;
        if (var17 != this.flag4) {
            this.flag4 = var17;
            Animation var11;
            float var12;
            if (!var17) {
                Animation var10 = this.animation;
                var12 = 0.0F;
                var11 = var10;
            } else {
                var12 = var3;
                var11 = this.animation;
            }

            var11.method_603(var12, 300.0F, Easings.easing2);
            Animation var14;
            float var15;
            if (!var17) {
                Animation var13 = this.animation2;
                var15 = 0.0F;
                var14 = var13;
            } else {
                var15 = 1.0F;
                var14 = this.animation2;
            }

            var14.method_603(var15, 200.0F, Easings.easing6);
            var16 = this;
        } else {
            if (var17 && this.animation.method_604() && this.animation.getFloatValue3() != var3) {
                this.animation.method_607(var3);
            }

            var16 = this;
        }

        var16.animation.method_605(var1.floatValue);
        this.animation2.method_605(var1.floatValue);
    }

    public abstract float method_2387(RenderContext var1);

    public FontStyle method_2453() {
        return fontStyle;
    }

    public boolean method_2577() {
        return this.animation2.method_606() > 0.95F;
    }

    public float method_2578() {
        return this.floatValue + 6.0F + this.method_2453().method_643() / 2.0F;
    }
}
