package client.onyx.ui.clickgui.setting;

import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easings;
import client.onyx.render.font.FontStyle;
import client.onyx.render.ui.UiRenderer;
import client.onyx.setting.impl.EnumSetting;
import client.onyx.ui.component.ComponentHost;
import client.onyx.ui.component.RenderContext;
import client.onyx.ui.theme.Palette;
import client.onyx.ui.theme.Typography;
import java.util.ArrayList;
import java.util.List;

public class NumberView<E extends Enum<E>> extends SettingView {
    private static final float FLOAT_24_01 = 24.0F;
    private static final float FLOAT_8_01 = 8.0F;
    private static final float FLOAT_1_51 = 1.5F;
    private static final float FLOAT_4_01 = 4.0F;
    private final List<Animation> list2;
    private final List<ComponentHost> list22;
    private int intValue;
    private final EnumSetting<E> enumSetting;
    private boolean flag5;

    @Override
    public float method_2387(RenderContext var1) {
        return this.method_2507() - this.floatValue + this.enumSetting.getList2().size() * 24.0F - 8.0F + 6.0F;
    }

    @Override
    public void method_2206(RenderContext var1) {
        var var2 = this;
        super.method_2206(var1);
        this.intValue = -1;
        int var19 = 0;

        for (int var18 = 0; var18 < var2.enumSetting.getList2().size(); var18 = var19) {
            boolean var6;
            if (var2.enumSetting.getValue() == var2.enumSetting.getList2().get(var19)) {
                var6 = true;
            } else {
                var6 = false;
            }

            boolean var7;
            if (var1.floatValue3 >= var2.floatValue3
                && var1.floatValue3 < var2.floatValue3 + var2.floatValue4
                && var1.floatValue2 >= var2.method_2508(var19)
                && var1.floatValue2 < var2.method_2508(var19) + 24.0F) {
                var7 = true;
            } else {
                var7 = false;
            }

            if (var7) {
                var2.intValue = var19;
            }

            Animation var3 = var2.list2.get(var19);
            Animation var11;
            if (!var2.flag5) {
                Animation var9;
                float var10;
                if (!var6) {
                    var10 = 0.0F;
                    var9 = var3;
                } else {
                    var10 = 1.0F;
                    var9 = var3;
                }

                var9.method_607(var10);
                var11 = var3;
            } else {
                float var10000 = var3.getFloatValue3();
                float var13;
                float var14;
                if (!var6) {
                    float var12 = var10000;
                    var14 = 0.0F;
                    var13 = var12;
                } else {
                    var14 = 1.0F;
                    var13 = var10000;
                }

                if (var13 != var14) {
                    Animation var16;
                    float var17;
                    if (!var6) {
                        var17 = 0.0F;
                        var16 = var3;
                    } else {
                        var17 = 1.0F;
                        var16 = var3;
                    }

                    var16.method_603(var17, 200.0F, Easings.easing2);
                }

                var11 = var3;
            }

            var11.method_605(var1.floatValue);
            ComponentHost var20 = var2.list22.get(var19);
            var19++;
            var20.method_2220(var1.floatValue, var7, false);
        }

        var2.flag5 = true;
    }

    private float method_2507() {
        return this.method_2571() - 1.5F;
    }

    public NumberView(EnumSetting<E> var1) {
        super(var1);
        EnumSetting<E> var4 = var1;
        var var5 = this;
        this.list2 = new ArrayList<>();
        this.list22 = new ArrayList<>();
        this.intValue = -1;
        this.enumSetting = var1;
        int var2 = 0;

        for (int var3 = 0; var3 < var4.getList2().size(); var3 = var2) {
            var5.list2.add(new Animation(0.0F));
            var2++;
            var5.list22.add(new ComponentHost());
        }
    }

    private float method_2508(int var1) {
        return this.method_2507() - 4.0F + var1 * 24.0F;
    }

    @Override
    public void method_2389(RenderContext var1, float var2) {
        var var5 = this;
        UiRenderer var14 = var1.uiRenderer;
        this.method_2575(var1, this.floatValue4);
        int var3 = 0;

        for (int var11 = 0; var11 < var5.enumSetting.getList2().size(); var11 = var3) {
            Enum var4 = var5.enumSetting.getList2().get(var3);
            float var12;
            float var6 = (var12 = var5.method_2508(var3)) + 12.0F;
            boolean var8;
            if (var5.enumSetting.getValue() == var4) {
                var8 = true;
            } else {
                var8 = false;
            }

            var5.list22
                .get(var3)
                .method_2219(var14, var5.floatValue3 - 4.0F, var12, var5.floatValue4 + 8.0F, 24.0F, 8.0F, var5.method_2572(var1.method_2256().intValue15()));
            int var10;
            if (!var8) {
                var10 = var1.method_2256().intValue7();
            } else {
                var10 = var1.method_2256().intValue31();
            }

            var14.method_488(var5.floatValue3 + 8.0F, var6, 8.0F, 1.5F, var5.method_2572(var10));
            float var13;
            if ((var13 = var5.list2.get(var3).method_606()) > 0.01F) {
                var14.method_489(var5.floatValue3 + 8.0F, var6, 4.0F * var13, var5.method_2572(var1.method_2256().intValue31()));
            }

            FontStyle var10001 = Typography.fontStyle15;
            String var10002 = EnumSetting.method_167(var4);
            float var10003 = var5.floatValue3 + 16.0F + 9.0F;
            Palette var10006 = var1.method_2256();
            var3++;
            var14.method_432(var10001, var10002, var10003, var6, var5.method_2572(var10006.intValue15()));
        }
    }

    @Override
    public boolean method_2183(float var1, float var2, int var3) {
        if (this.method_2577() && var3 == 0 && this.intValue >= 0) {
            this.enumSetting.setValue(this.enumSetting.getList2().get(this.intValue));
            return true;
        } else {
            return false;
        }
    }
}
