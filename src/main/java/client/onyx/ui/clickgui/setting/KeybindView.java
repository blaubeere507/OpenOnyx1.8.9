package client.onyx.ui.clickgui.setting;

import client.onyx.input.KeyTyped;
import client.onyx.module.BindMode;
import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easings;
import client.onyx.render.font.FontStyle;
import client.onyx.render.ui.UiRenderer;
import client.onyx.setting.impl.KeybindSetting;
import client.onyx.ui.component.ComponentHost;
import client.onyx.ui.component.RenderContext;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.Typography;
import client.onyx.ui.theme.color.ColorUtils;

public class KeybindView extends SettingView {
    private static final BindMode[] bindModeArray = new BindMode[]{BindMode.TOGGLE, BindMode.HOLD};
    private static final String[] stringArray = new String[]{"Toggle", "Hold"};
    private static final float FLOAT_12_01 = 12.0F;
    private static final float FLOAT_20_01 = 20.0F;
    private static final float FLOAT_8_01 = 8.0F;
    private final ComponentHost componentHost = new ComponentHost();
    private final ComponentHost[] componentHostArray = new ComponentHost[]{new ComponentHost(), new ComponentHost()};
    private final Animation animation3 = new Animation(0.0F);
    private final KeybindSetting keybindSetting;
    private boolean flag5;
    private boolean flag23;
    private float floatValue5;
    private float floatValue22;

    public KeybindView(KeybindSetting var1) {
        super(var1);
        this.keybindSetting = var1;
    }

    private boolean method_2467() {
        return this.keybindSetting.getEnumSetting() != null;
    }

    private String method_2468() {
        return this.flag23 ? "Press a key..." : this.keybindSetting.method_164();
    }

    private float method_2469(RenderContext var1) {
        return Math.max(58.0F, var1.uiRenderer.method_442(Typography.fontStyle16, this.method_2468()) + 20.0F);
    }

    private int method_2470(float var1, float var2) {
        if (!this.method_2472()) {
            return -1;
        } else if (var2 < this.method_2477() || var2 >= this.method_2477() + 20.0F) {
            return -1;
        } else {
            float var4;
            return !((var4 = var1 - this.floatValue22) < 0.0F) && !(var4 >= this.method_2474()) ? (int)(var4 / this.floatValue5) : -1;
        }
    }

    private int method_2471(int var1) {
        return Theme.method_180(var1, this.method_2570() * this.animation3.method_606() * (ColorUtils.method_213(var1) / 255.0F));
    }

    private boolean method_2472() {
        return this.method_2467() && this.animation3.method_606() > 0.95F;
    }

    @Override
    public void method_2206(RenderContext var1) {
        RenderContext var14 = var1;
        var var15 = this;
        super.method_2206(var1);
        float var4;
        if (this.method_2467() && this.keybindSetting.method_163()) {
            var4 = 1.0F;
        } else {
            var4 = 0.0F;
        }

        KeybindView var6;
        if (this.flag5) {
            if (this.animation3.getFloatValue3() != var4) {
                this.animation3.method_603(var4, 200.0F, Easings.easing6);
            }

            var6 = this;
        } else {
            this.flag5 = true;
            this.animation3.method_607(var4);
            var6 = this;
        }

        var6.animation3.method_605(var1.floatValue);
        this.floatValue5 = this.method_2476(var1);
        this.floatValue22 = this.method_2475(var1);
        boolean var7;
        if (var1.floatValue3 >= this.method_2478(var1)
            && var1.floatValue3 < this.floatValue3 + this.floatValue4
            && var1.floatValue2 >= this.method_2477()
            && var1.floatValue2 < this.method_2477() + 20.0F) {
            var7 = true;
        } else {
            var7 = false;
        }

        this.componentHost.method_2220(var1.floatValue, var7, this.flag23);
        int var2 = this.method_2470(var1.floatValue3, var1.floatValue2);
        int var3 = 0;

        for (int var13 = 0; var13 < var15.componentHostArray.length; var13 = var3) {
            ComponentHost var16 = var15.componentHostArray[var3];
            ComponentHost var10;
            float var11;
            boolean var12;
            if (var2 != var3) {
                float var9 = var14.floatValue;
                ComponentHost var8 = var16;
                var12 = false;
                var11 = var9;
                var10 = var8;
            } else {
                var12 = true;
                var11 = var14.floatValue;
                var10 = var16;
            }

            var3++;
            var10.method_2220(var11, var12, false);
        }
    }

    private float method_2473(int var1) {
        return this.floatValue22 + var1 * this.floatValue5;
    }

    private float method_2474() {
        return this.floatValue5 * bindModeArray.length;
    }

    private float method_2475(RenderContext var1) {
        return this.method_2478(var1) - 8.0F - this.method_2474();
    }

    private float method_2476(RenderContext var1) {
        float var8 = 0.0F;
        String[] var5 = stringArray;
        int var4 = stringArray.length;
        int var3 = 0;

        for (int var7 = 0; var7 < var4; var7 = var3) {
            String var6 = var5[var3];
            var3++;
            var8 = Math.max(var8, var1.uiRenderer.method_442(Typography.fontStyle16, var6));
        }

        return var8 + 24.0F;
    }

    @Override
    public boolean method_2183(float var1, float var2, int var3) {
        if (this.method_2577() && this.method_2259(var1, var2)) {
            int var8;
            if ((var8 = this.method_2470(var1, var2)) >= 0) {
                if (var3 != 0) {
                    return true;
                } else {
                    this.keybindSetting.getEnumSetting().setValue(bindModeArray[var8]);
                    return true;
                }
            } else if (var3 == 1) {
                this.keybindSetting.method_158();
                this.flag23 = false;
                return true;
            } else if (var3 == 0) {
                KeybindView var5;
                boolean var6;
                if (this.flag23) {
                    var6 = false;
                    var5 = this;
                } else {
                    var6 = true;
                    var5 = this;
                }

                var5.flag23 = var6;
                return true;
            } else if (this.flag23 && var3 >= 2) {
                this.flag23 = false;
                this.keybindSetting.method_166(var3);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private float method_2477() {
        return this.method_2578() - 10.0F;
    }

    private float method_2478(RenderContext var1) {
        return this.floatValue3 + this.floatValue4 - this.method_2469(var1);
    }

    @Override
    public boolean method_2212(KeyTyped var1) {
        if (!this.flag23) {
            return false;
        } else {
            this.flag23 = false;
            if (var1.intValue() == 1) {
                this.keybindSetting.method_158();
            } else {
                this.keybindSetting.method_161(var1.intValue());
            }

            return true;
        }
    }

    @Override
    public void method_2389(RenderContext var1, float var2) {
        UiRenderer var26 = var1.uiRenderer;
        float var4 = this.method_2469(var1);
        float var3 = this.method_2478(var1);
        float var5 = this.method_2477();
        float var7;
        if (this.animation3.method_606() > 0.01F) {
            var7 = this.floatValue22;
        } else {
            var7 = var3;
        }

        float var6 = var7 - 10.0F - this.floatValue3;
        this.method_2575(var1, var6);
        this.method_2480(var1, var5);
        KeybindView var9;
        if (!this.flag23) {
            var26.method_494(var3, var5, var4, 20.0F, Float.MAX_VALUE, 1.0F, this.method_2572(var1.method_2256().intValue8()));
            var9 = this;
        } else {
            var26.method_479(var3, var5, var4, 20.0F, Float.MAX_VALUE, this.method_2572(var1.method_2256().intValue5()));
            var9 = this;
        }

        var9.componentHost.method_2219(var26, var3, var5, var4, 20.0F, Float.MAX_VALUE, this.method_2572(var1.method_2256().intValue15()));
        FontStyle var10001 = Typography.fontStyle16;
        String var10002 = this.method_2468();
        float var10003 = var3 + var4 / 2.0F;
        float var10004 = this.method_2578();
        UiRenderer var17;
        FontStyle var18;
        String var19;
        float var20;
        float var21;
        KeybindView var22;
        int var23;
        if (!this.flag23) {
            float var14 = var10004;
            float var13 = var10003;
            String var12 = var10002;
            FontStyle var11 = var10001;
            var23 = var1.method_2256().intValue15();
            var22 = this;
            var21 = var14;
            var20 = var13;
            var19 = var12;
            var18 = var11;
            var17 = var26;
        } else {
            var23 = var1.method_2256().intValue30();
            var22 = this;
            var21 = var10004;
            var20 = var10003;
            var19 = var10002;
            var18 = var10001;
            var17 = var26;
        }

        var17.method_484(var18, var19, var20, var21, var22.method_2572(var23));
    }

    private int method_2479() {
        return this.keybindSetting.getEnumSetting() != null && this.keybindSetting.getEnumSetting().method_168(BindMode.HOLD) ? 1 : 0;
    }

    @Override
    public float method_2387(RenderContext var1) {
        return this.method_2565();
    }

    private void method_2480(RenderContext var1, float var2) {
        var var3 = this;
        if (this.method_2467() && !(this.animation3.method_606() <= 0.01F)) {
            UiRenderer var6;
            UiRenderer var10000 = var6 = var1.uiRenderer;
            int var4 = this.method_2479();
            float var5 = 10.0F;
            var10000.method_494(this.floatValue22, var2, this.method_2474(), 20.0F, Float.MAX_VALUE, 1.0F, this.method_2471(var1.method_2256().intValue8()));
            int var29 = 0;

            for (int var28 = 0; var28 < bindModeArray.length; var28 = var29) {
                float var7 = var3.method_2473(var29);
                boolean var11;
                if (var29 == 0) {
                    var11 = true;
                } else {
                    var11 = false;
                }

                boolean var12;
                if (var29 == bindModeArray.length - 1) {
                    var12 = true;
                } else {
                    var12 = false;
                }

                float var13;
                if (var11) {
                    var13 = var5;
                } else {
                    var13 = 0.0F;
                }

                float var14;
                if (var12) {
                    var14 = var5;
                } else {
                    var14 = 0.0F;
                }

                if (var29 == var4) {
                    var6.method_482(var7, var2, var3.floatValue5, 20.0F, var13, var14, var14, var13, var3.method_2471(var1.method_2256().intValue5()));
                }

                var3.componentHostArray[var29]
                    .method_2219(var6, var7, var2, var3.floatValue5, 20.0F, Math.max(var13, var14), var3.method_2471(var1.method_2256().intValue15()));
                if (!var11) {
                    var6.method_434(var7, var2, 1.0F, 20.0F, var3.method_2471(var1.method_2256().intValue8()));
                }

                FontStyle var10001 = Typography.fontStyle16;
                String var10002 = stringArray[var29];
                float var10003 = var7 + var3.floatValue5 / 2.0F;
                float var10004 = var3.method_2578();
                UiRenderer var21;
                FontStyle var22;
                String var23;
                float var24;
                float var25;
                KeybindView var26;
                int var27;
                if (var29 != var4) {
                    float var19 = var10004;
                    float var18 = var10003;
                    String var17 = var10002;
                    FontStyle var16 = var10001;
                    var27 = var1.method_2256().intValue7();
                    var26 = var3;
                    var25 = var19;
                    var24 = var18;
                    var23 = var17;
                    var22 = var16;
                    var21 = var6;
                } else {
                    var27 = var1.method_2256().intValue30();
                    var26 = var3;
                    var25 = var10004;
                    var24 = var10003;
                    var23 = var10002;
                    var22 = var10001;
                    var21 = var6;
                }

                int var10005 = var26.method_2471(var27);
                var29++;
                var21.method_484(var22, var23, var24, var25, var10005);
            }
        }
    }
}
