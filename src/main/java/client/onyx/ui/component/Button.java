package client.onyx.ui.component;

import client.onyx.render.ui.UiRenderer;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.Typography;

public class Button extends Component {
    private static final float FLOAT_16_01 = 16.0F;
    private static final float FLOAT_6_01 = 6.0F;
    public static final float FLOAT_30_01 = 30.0F;
    private static final float FLOAT_18_01 = 18.0F;
    private static final float FLOAT_900_01 = 900.0F;
    private static final float FLOAT_12_01 = 12.0F;
    private static final float FLOAT_1_81 = 1.8F;
    private static final float FLOAT_12_02 = 12.0F;
    private final ComponentHost componentHost = new ComponentHost();
    private final Button.Variant variant;
    private String string2;
    private String string;
    private final Runnable runnable;
    private boolean flag4;
    private float floatValue5;

    private boolean method_2459() {
        return this.flag2 && !this.flag4;
    }

    private void method_2460(UiRenderer var1, int var2) {
        float var7 = var1.method_442(Typography.fontStyle14, this.string2);
        float var6;
        if (this.string == null) {
            var6 = 0.0F;
        } else {
            var6 = 22.0F;
        }

        float var8 = this.floatValue3 + (this.floatValue4 - var7 - var6) / 2.0F;
        float var5 = this.floatValue + this.floatValue2 / 2.0F;
        if (this.string != null) {
            var1.method_477(this.string, var8 + 8.0F, var5, 16.0F, var2);
            var8 += var6;
        }

        var1.method_432(Typography.fontStyle14, this.string2, var8, var5, var2);
    }

    @Override
    public boolean method_2183(float var1, float var2, int var3) {
        if (var3 == 0 && this.method_2459() && this.method_2259(var1, var2)) {
            this.flag3 = true;
            this.runnable.run();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void method_2206(RenderContext var1) {
        ComponentHost var4;
        float var5;
        boolean var6;
        Button var7;
        label31: {
            super.method_2206(var1);
            ComponentHost var17 = this.componentHost;
            float var10001 = var1.floatValue;
            ComponentHost var2;
            float var3;
            if (!this.flag) {
                var3 = var1.floatValue;
                var2 = this.componentHost;
            } else {
                if (this.method_2459()) {
                    var7 = this;
                    var6 = true;
                    var5 = var10001;
                    var4 = var17;
                    break label31;
                }

                var3 = var10001;
                var2 = var17;
            }

            var7 = this;
            var6 = false;
            var5 = var3;
            var4 = var2;
        }

        ComponentHost var11;
        float var12;
        boolean var13;
        boolean var14;
        label25: {
            ComponentHost var8;
            float var9;
            boolean var10;
            if (!var7.flag3) {
                var10 = var6;
                var9 = var5;
                var8 = var4;
            } else {
                if (this.method_2459()) {
                    var14 = true;
                    var13 = var6;
                    var12 = var5;
                    var11 = var4;
                    break label25;
                }

                var10 = var6;
                var9 = var5;
                var8 = var4;
            }

            var14 = false;
            var13 = var10;
            var12 = var9;
            var11 = var8;
        }

        var11.method_2220(var12, var13, var14);
        if (this.flag4) {
            this.floatValue5 = (this.floatValue5 + var1.floatValue) % 900.0F;
        }
    }

    @Override
    public boolean method_2189(float var1, float var2, int var3) {
        return this.flag3 = false;
    }

    public void method_2461(String var1) {
        this.string = var1;
    }

    public void method_2462(boolean var1) {
        this.flag4 = var1;
    }

    public void method_2463(String var1) {
        this.string2 = var1;
    }

    public Button(Button.Variant var1, String var2, String var3, Runnable var4) {
        this.variant = var1;
        this.string2 = var2;
        this.string = var3;
        this.runnable = var4;
        this.floatValue2 = 30.0F;
    }

    public Button(Button.Variant var1, String var2, Runnable var3) {
        this(var1, var2, null, var3);
        Object var10003 = null;
    }

    @Override
    public void method_2186(RenderContext var1) {
        UiRenderer var35 = var1.uiRenderer;
        int var3 = this.method_2466(var1);
        int var4 = this.method_2465(var1);
        if (!this.method_2459()) {
            var3 = Theme.method_177(var3);
        }

        if (var4 != 0) {
            float var10001 = this.floatValue3;
            float var10002 = this.floatValue;
            float var10003 = this.floatValue4;
            float var10004 = this.floatValue2;
            UiRenderer var11;
            float var12;
            float var13;
            float var14;
            float var15;
            float var16;
            int var17;
            if (!this.method_2459()) {
                float var10 = Float.MAX_VALUE;
                float var9 = var10004;
                float var8 = var10003;
                float var7 = var10002;
                float var6 = var10001;
                var17 = Theme.method_180(var1.method_2256().intValue15(), 0.12F);
                var16 = var10;
                var15 = var9;
                var14 = var8;
                var13 = var7;
                var12 = var6;
                var11 = var35;
            } else {
                var17 = var4;
                var16 = Float.MAX_VALUE;
                var15 = var10004;
                var14 = var10003;
                var13 = var10002;
                var12 = var10001;
                var11 = var35;
            }

            var11.method_479(var12, var13, var14, var15, var16, var17);
        }

        if (this.variant == Button.Variant.OUTLINED) {
            float var36 = this.floatValue3;
            float var37 = this.floatValue;
            float var38 = this.floatValue4;
            float var39 = this.floatValue2;
            UiRenderer var26;
            float var27;
            float var28;
            float var29;
            float var30;
            float var31;
            float var32;
            int var33;
            if (!this.method_2459()) {
                float var24 = 1.0F;
                float var23 = Float.MAX_VALUE;
                float var22 = var39;
                float var21 = var38;
                float var20 = var37;
                float var19 = var36;
                var33 = Theme.method_177(var1.method_2256().intValue8());
                var32 = var24;
                var31 = var23;
                var30 = var22;
                var29 = var21;
                var28 = var20;
                var27 = var19;
                var26 = var35;
            } else {
                var33 = var1.method_2256().intValue8();
                var32 = 1.0F;
                var31 = Float.MAX_VALUE;
                var30 = var39;
                var29 = var38;
                var28 = var37;
                var27 = var36;
                var26 = var35;
            }

            var26.method_494(var27, var28, var29, var30, var31, var32, var33);
        }

        this.componentHost.method_2219(var35, this.floatValue3, this.floatValue, this.floatValue4, this.floatValue2, Float.MAX_VALUE, var3);
        Button var34;
        if (this.flag4) {
            var35.method_495(this.floatValue3 + this.floatValue4 / 2.0F, this.floatValue + this.floatValue2 / 2.0F, 6.0F, 1.8F, this.floatValue5 / 900.0F, var3);
            var34 = this;
        } else {
            this.method_2460(var35, var3);
            var34 = this;
        }

        if (var34.flag && this.method_2459()) {
            var35.method_465();
        }
    }

    public float method_2464(UiRenderer var1) {
        float var4;
        if (this.variant == Button.Variant.TEXT) {
            var4 = 12.0F;
        } else {
            var4 = 18.0F;
        }

        float var7 = var1.method_442(Typography.fontStyle14, this.string2);
        float var5;
        if (this.string == null) {
            var5 = 0.0F;
        } else {
            var5 = 22.0F;
        }

        return var4 * 2.0F + var7 + var5;
    }

    public boolean isFlag4() {
        return this.flag4;
    }

    private int method_2465(RenderContext var1) {
        switch (this.variant) {
            case FILLED:
                return var1.method_2256().intValue31();
            case TONAL:
                return var1.method_2256().intValue5();
            case OUTLINED:
            case TEXT:
                return 0;
            default:
                throw new MatchException(null, null);
        }
    }

    private int method_2466(RenderContext var1) {
        switch (this.variant) {
            case FILLED:
                return var1.method_2256().intValue33();
            case TONAL:
                return var1.method_2256().intValue30();
            case OUTLINED:
            case TEXT:
                return var1.method_2256().intValue31();
            default:
                throw new MatchException(null, null);
        }
    }

    public static enum Variant {
        FILLED,
        TONAL,
        OUTLINED,
        TEXT;
    }
}
