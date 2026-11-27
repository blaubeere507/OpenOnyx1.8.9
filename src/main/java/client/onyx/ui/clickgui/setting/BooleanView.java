package client.onyx.ui.clickgui.setting;

import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easings;
import client.onyx.render.font.FontStyle;
import client.onyx.render.ui.UiRenderer;
import client.onyx.setting.Mode;
import client.onyx.setting.ModeSetting;
import client.onyx.ui.component.Component;
import client.onyx.ui.component.ComponentHost;
import client.onyx.ui.component.Popup;
import client.onyx.ui.component.RenderContext;
import client.onyx.ui.theme.Palette;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.Typography;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BooleanView<M extends Mode> extends SettingView {
    private static final float FLOAT_13_01 = 13.0F;
    private static final float FLOAT_4_01 = 4.0F;
    private static final float FLOAT_7_01 = 7.0F;
    private static final float FLOAT_24_01 = 24.0F;
    private static final float FLOAT_8_01 = 8.0F;
    private static final float FLOAT_1_51 = 1.5F;
    private final List<Animation> list22;
    private final List<ComponentHost> list2;
    private final Popup popup;
    private final Animation animation3;
    private int intValue;
    private final ModeSetting<M> modeSetting;
    private boolean flag5;
    private M mode;
    private float floatValue5;

    private float method_2532() {
        return this.method_2534(this.modeSetting.getList2().size() - 1) + 24.0F - 4.0F;
    }

    @Override
    public void method_2206(RenderContext var1) {
        var var2 = this;
        if (this.mode != this.modeSetting.getValue()) {
            this.method_2536();
        }

        this.floatValue5 = this.popup.method_2553(this.floatValue3 + 13.0F, this.method_2535(), Math.max(0.0F, this.floatValue4 - 13.0F));
        BooleanView var7;
        if (this.flag5) {
            if (this.animation3.getFloatValue3() != this.floatValue5) {
                this.animation3.method_603(this.floatValue5, 300.0F, Easings.easing2);
            }

            var7 = this;
        } else {
            this.animation3.method_607(this.floatValue5);
            var7 = this;
        }

        var7.animation3.method_605(var1.floatValue);
        this.intValue = -1;
        int var21 = 0;

        for (int var20 = 0; var20 < var2.modeSetting.getList2().size(); var20 = var21) {
            boolean var8;
            if (var2.modeSetting.getValue() == var2.modeSetting.getList2().get(var21)) {
                var8 = true;
            } else {
                var8 = false;
            }

            boolean var9;
            if (var1.floatValue3 >= var2.floatValue3
                && var1.floatValue3 < var2.floatValue3 + var2.floatValue4
                && var1.floatValue2 >= var2.method_2534(var21)
                && var1.floatValue2 < var2.method_2534(var21) + 24.0F) {
                var9 = true;
            } else {
                var9 = false;
            }

            if (var9) {
                var2.intValue = var21;
            }

            Animation var3 = var2.list22.get(var21);
            Animation var13;
            if (!var2.flag5) {
                Animation var11;
                float var12;
                if (!var8) {
                    var12 = 0.0F;
                    var11 = var3;
                } else {
                    var12 = 1.0F;
                    var11 = var3;
                }

                var11.method_607(var12);
                var13 = var3;
            } else {
                float var10000 = var3.getFloatValue3();
                float var15;
                float var16;
                if (!var8) {
                    float var14 = var10000;
                    var16 = 0.0F;
                    var15 = var14;
                } else {
                    var16 = 1.0F;
                    var15 = var10000;
                }

                if (var15 != var16) {
                    Animation var18;
                    float var19;
                    if (!var8) {
                        var19 = 0.0F;
                        var18 = var3;
                    } else {
                        var19 = 1.0F;
                        var18 = var3;
                    }

                    var18.method_603(var19, 200.0F, Easings.easing2);
                }

                var13 = var3;
            }

            var13.method_605(var1.floatValue);
            ComponentHost var22 = var2.list2.get(var21);
            var21++;
            var22.method_2220(var1.floatValue, var9, false);
        }

        var2.flag5 = true;
        var2.method_2206(var1);
    }

    @Override
    public boolean method_2183(float var1, float var2, int var3) {
        if (this.method_2577() && var3 == 0) {
            if (this.intValue >= 0) {
                this.modeSetting.setValue(this.modeSetting.getList2().get(this.intValue));
                return true;
            } else {
                Iterator var5 = this.list.iterator();

                while (var5.hasNext()) {
                    if (((Component)var5.next()).method_2183(var1, var2, var3)) {
                        return true;
                    }
                }

                return false;
            }
        } else {
            return false;
        }
    }

    private float method_2533() {
        return this.method_2571() - 1.5F;
    }

    private float method_2534(int var1) {
        return this.method_2533() - 4.0F + var1 * 24.0F;
    }

    @Override
    public void method_2389(RenderContext var1, float var2) {
        var var3 = this;
        UiRenderer var4 = var1.uiRenderer;
        this.method_2575(var1, this.floatValue4);
        int var14 = 0;

        for (int var12 = 0; var12 < var3.modeSetting.getList2().size(); var12 = var14) {
            Mode var5 = var3.modeSetting.getList2().get(var14);
            float var6;
            float var7 = (var6 = var3.method_2534(var14)) + 12.0F;
            boolean var9;
            if (var3.modeSetting.getValue() == var5) {
                var9 = true;
            } else {
                var9 = false;
            }

            var3.list2
                .get(var14)
                .method_2219(var4, var3.floatValue3 - 4.0F, var6, var3.floatValue4 + 8.0F, 24.0F, 8.0F, var3.method_2572(var1.method_2256().intValue15()));
            int var11;
            if (!var9) {
                var11 = var1.method_2256().intValue7();
            } else {
                var11 = var1.method_2256().intValue31();
            }

            var4.method_488(var3.floatValue3 + 8.0F, var7, 8.0F, 1.5F, var3.method_2572(var11));
            float var8;
            if ((var8 = var3.list22.get(var14).method_606()) > 0.01F) {
                var4.method_489(var3.floatValue3 + 8.0F, var7, 4.0F * var8, var3.method_2572(var1.method_2256().intValue31()));
            }

            FontStyle var10001 = Typography.fontStyle15;
            String var10002 = var5.getName();
            float var10003 = var3.floatValue3 + 16.0F + 9.0F;
            Palette var10006 = var1.method_2256();
            var14++;
            var4.method_432(var10001, var10002, var10003, var7, var3.method_2572(var10006.intValue15()));
        }

        float var15;
        if (!((var15 = var3.animation3.method_606()) <= 0.5F)) {
            var4.method_443(var3.floatValue3, var3.method_2535(), var3.floatValue4, var15);
            var4.method_434(var3.floatValue3 + 3.0F, var3.method_2535(), 1.0F, var15, Theme.method_180(var1.method_2256().intValue12(), var2 * 0.7F));
            var3.popup.method_2555(var4, var3.floatValue3 + 13.0F, Math.max(0.0F, var3.floatValue4 - 13.0F), var1.method_2256().intValue12(), var2);

            Iterator var16;
            for (Iterator var13 = var16 = var3.list.iterator(); var13.hasNext(); var13 = var16) {
                Component var17 = (Component)var16.next();
                var17.method_2186(var1);
            }

            var4.method_486();
        }
    }

    @Override
    public float method_2387(RenderContext var1) {
        float var5 = this.animation3.method_606();
        float var10000 = this.method_2532() - this.floatValue;
        float var3;
        float var4;
        if (!(var5 > 0.5F)) {
            float var2 = var10000;
            var4 = 0.0F;
            var3 = var2;
        } else {
            var4 = 7.0F + var5;
            var3 = var10000;
        }

        return var3 + var4 + 6.0F;
    }

    private float method_2535() {
        return this.method_2532() + 7.0F;
    }

    public BooleanView(ModeSetting<M> var1) {
        super(var1);
        ModeSetting<M> var4 = var1;
        var var5 = this;
        this.list22 = new ArrayList<>();
        this.list2 = new ArrayList<>();
        this.popup = new Popup();
        this.animation3 = new Animation(0.0F);
        this.intValue = -1;
        this.modeSetting = var1;
        int var2 = 0;

        for (int var3 = 0; var3 < var4.getList2().size(); var3 = var2) {
            var5.list22.add(new Animation(0.0F));
            var2++;
            var5.list2.add(new ComponentHost());
        }

        var5.method_2536();
    }

    private void method_2536() {
        this.mode = this.modeSetting.getValue();
        this.list.clear();
        this.popup.method_2556(this.mode.getList());
        this.list.addAll(this.popup.getList());
        this.floatValue5 = 0.0F;
    }
}
