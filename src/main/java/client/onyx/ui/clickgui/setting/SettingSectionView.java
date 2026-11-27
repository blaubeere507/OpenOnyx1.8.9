package client.onyx.ui.clickgui.setting;

import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easings;
import client.onyx.render.font.FontStyle;
import client.onyx.render.ui.UiRenderer;
import client.onyx.setting.impl.EnumSetSetting;
import client.onyx.setting.impl.EnumSetting;
import client.onyx.ui.component.Icons;
import client.onyx.ui.component.RenderContext;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.Typography;
import java.util.ArrayList;
import java.util.List;

public class SettingSectionView<E extends Enum<E>> extends SettingView {
    private static final float FLOAT_9_01 = 9.0F;
    private static final float FLOAT_5_01 = 5.0F;
    private static final float FLOAT_12_01 = 12.0F;
    private static final float FLOAT_23_01 = 23.0F;
    private final List<Animation> list22;
    private final List<float[]> list2;
    private float floatValue5;
    private final EnumSetSetting<E> enumSetSetting;
    private boolean flag5;

    public SettingSectionView(EnumSetSetting<E> var1) {
        super(var1);
        EnumSetSetting<E> var4 = var1;
        var var5 = this;
        this.list22 = new ArrayList<>();
        this.list2 = new ArrayList<>();
        this.floatValue5 = 50.0F;
        this.enumSetSetting = var1;
        int var2 = 0;

        for (int var3 = 0; var3 < var4.getList2().size(); var3 = var2) {
            var5.list22.add(new Animation(0.0F));
            float[] var10002 = new float[]{0.0F, 0.0F, 0.0F};
            var2++;
            var10002[2] = 0.0F;
            var5.list2.add(var10002);
        }
    }

    @Override
    public boolean method_2183(float var1, float var2, int var3) {
        var var4 = this;
        if (this.method_2577() && var3 == 0) {
            var3 = 0;

            for (int var5 = 0; var5 < var4.list2.size(); var5 = ++var3) {
                float[] var6 = var4.list2.get(var3);
                if (var1 >= var6[0] && var1 < var6[0] + var6[2] && var2 >= var6[1] && var2 < var6[1] + 23.0F) {
                    var4.enumSetSetting.method_81(var4.enumSetSetting.getList2().get(var3));
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    @Override
    public float method_2387(RenderContext var1) {
        return this.floatValue5;
    }

    @Override
    public void method_2206(RenderContext var1) {
        var var3 = this;
        super.method_2206(var1);
        float var6 = this.floatValue3;
        float var5 = this.method_2571();
        float var4 = 23.0F;
        int var19 = 0;

        for (int var18 = 0; var18 < var3.enumSetSetting.getList2().size(); var18 = var19) {
            boolean var2 = var3.enumSetSetting.method_79(var3.enumSetSetting.getList2().get(var19));
            Animation var7 = var3.list22.get(var19);
            Animation var11;
            if (!var3.flag5) {
                Animation var9;
                float var10;
                if (!var2) {
                    var10 = 0.0F;
                    var9 = var7;
                } else {
                    var10 = 1.0F;
                    var9 = var7;
                }

                var9.method_607(var10);
                var11 = var7;
            } else {
                float var10000 = var7.getFloatValue3();
                float var13;
                float var14;
                if (!var2) {
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
                    if (!var2) {
                        var17 = 0.0F;
                        var16 = var7;
                    } else {
                        var17 = 1.0F;
                        var16 = var7;
                    }

                    var16.method_603(var17, 250.0F, Easings.easing2);
                }

                var11 = var7;
            }

            var11.method_605(var1.floatValue);
            float var20 = var3.method_2388(var1, var19, var7.method_606());
            if (var6 > var3.floatValue3 && var6 + var20 > var3.floatValue3 + var3.floatValue4) {
                var6 = var3.floatValue3;
                var5 += var4 + 5.0F;
            }

            var3.list2.get(var19)[0] = var6;
            var3.list2.get(var19)[1] = var5;
            var3.list2.get(var19)[2] = var20;
            var19++;
            var6 += var20 + 5.0F;
        }

        var3.flag5 = true;
        var3.floatValue5 = var5 + var4 + 6.0F - var3.floatValue;
    }

    private float method_2388(RenderContext var1, int var2, float var3) {
        String var5 = EnumSetting.method_167((Enum<?>)this.enumSetSetting.getList2().get(var2));
        float var6 = var1.uiRenderer.method_442(Typography.fontStyle16, var5);
        return 18.0F + var6 + 16.0F * var3;
    }

    @Override
    public void method_2389(RenderContext var1, float var2) {
        var var4 = this;
        UiRenderer var15 = var1.uiRenderer;
        this.method_2575(var1, this.floatValue4);
        int var5 = 0;

        for (int var12 = 0; var12 < var4.enumSetSetting.getList2().size(); var12 = var5) {
            float[] var13 = var4.list2.get(var5);
            float var3 = var4.list22.get(var5).method_606();
            float var14 = var13[0];
            float var6 = var13[1];
            float var7 = var13[2];
            boolean var9;
            if (var1.floatValue3 >= var14 && var1.floatValue3 < var14 + var7 && var1.floatValue2 >= var6 && var1.floatValue2 < var6 + 23.0F) {
                var9 = true;
            } else {
                var9 = false;
            }

            if (var3 > 0.01F) {
                var15.method_479(var14, var6, var7, 23.0F, 8.0F, var4.method_2572(Theme.method_180(var1.method_2256().intValue5(), var3)));
            }

            if (var3 < 0.99F) {
                var15.method_494(var14, var6, var7, 23.0F, 8.0F, 1.0F, var4.method_2572(Theme.method_180(var1.method_2256().intValue8(), 1.0F - var3)));
            }

            if (var9) {
                var15.method_479(var14, var6, var7, 23.0F, 8.0F, var4.method_2572(Theme.method_180(var1.method_2256().intValue15(), 0.08F)));
            }

            var7 = var14 + 9.0F + 16.0F * var3;
            int var11;
            if (!(var3 > 0.5F)) {
                var11 = var1.method_2256().intValue7();
            } else {
                var11 = var1.method_2256().intValue30();
            }

            if (var3 > 0.05F) {
                Icons.method_2226(var15, var14 + 9.0F + 6.0F, var6 + 11.5F, 12.0F * (0.7F + 0.3F * var3), var4.method_2572(Theme.method_180(var11, var3)));
            }

            FontStyle var17 = Typography.fontStyle16;
            String var18 = EnumSetting.method_167((Enum<?>)var4.enumSetSetting.getList2().get(var5));
            float var10004 = var6 + 11.5F;
            var5++;
            var15.method_432(var17, var18, var7, var10004, var4.method_2572(var11));
        }
    }
}
