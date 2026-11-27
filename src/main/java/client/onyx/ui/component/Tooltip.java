package client.onyx.ui.component;

import client.onyx.render.ui.UiRenderer;
import client.onyx.ui.theme.Theme;

public class Tooltip extends Component {
    public static final float FLOAT_30_01 = 30.0F;
    private static final float FLOAT_18_01 = 18.0F;
    private final ComponentHost componentHost = new ComponentHost();
    private String string;
    private final Runnable runnable;
    private Integer integer;

    @Override
    public void method_2186(RenderContext var1) {
        UiRenderer var2 = var1.uiRenderer;
        int var4;
        if (this.integer != null) {
            var4 = this.integer;
        } else {
            var4 = var1.method_2256().intValue7();
        }

        this.componentHost.method_2219(var2, this.floatValue3, this.floatValue, this.floatValue4, this.floatValue2, Float.MAX_VALUE, var4);
        float var10002 = this.floatValue3 + this.floatValue4 / 2.0F;
        float var10003 = this.floatValue + this.floatValue2 / 2.0F;
        UiRenderer var10;
        String var11;
        float var12;
        float var13;
        float var14;
        int var15;
        if (!this.flag2) {
            float var9 = 18.0F;
            float var8 = var10003;
            float var7 = var10002;
            String var6 = this.string;
            var15 = Theme.method_177(var4);
            var14 = var9;
            var13 = var8;
            var12 = var7;
            var11 = var6;
            var10 = var2;
        } else {
            var15 = var4;
            var14 = 18.0F;
            var13 = var10003;
            var12 = var10002;
            var11 = this.string;
            var10 = var2;
        }

        var10.method_477(var11, var12, var13, var14, var15);
        if (this.flag) {
            var2.method_465();
        }
    }

    @Override
    public void method_2206(RenderContext var1) {
        super.method_2206(var1);
        this.componentHost.method_2220(var1.floatValue, this.flag, this.flag3);
    }

    @Override
    public boolean method_2189(float var1, float var2, int var3) {
        return this.flag3 = false;
    }

    @Override
    public boolean method_2183(float var1, float var2, int var3) {
        if (var3 == 0 && this.flag2 && this.method_2259(var1, var2)) {
            this.flag3 = true;
            this.runnable.run();
            return true;
        } else {
            return false;
        }
    }

    public void method_2593(String var1) {
        this.string = var1;
    }

    public Tooltip(String var1, Runnable var2) {
        this.string = var1;
        this.runnable = var2;
        this.floatValue4 = 30.0F;
        this.floatValue2 = 30.0F;
    }

    public void method_2594(Integer var1) {
        this.integer = var1;
    }
}
