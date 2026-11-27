package client.onyx.ui.component;

import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easings;
import client.onyx.render.font.FontStyle;
import client.onyx.render.ui.UiRenderer;
import client.onyx.ui.theme.Typography;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Label extends Component {
    private static final float FLOAT_12_01 = 12.0F;
    private static final float FLOAT_34_01 = 34.0F;
    private static final float FLOAT_16_01 = 16.0F;
    private static final float FLOAT_13_01 = 13.0F;
    private static final int INT_41 = 4;
    private static final float FLOAT_10_01 = 10.0F;
    private static final float FLOAT_10_02 = 10.0F;
    private static final float FLOAT_16_02 = 16.0F;
    private static final int INT_31 = 3;
    private static final float FLOAT_380_01 = 380.0F;
    private final Animation animation = new Animation(0.0F);
    private float floatValue5;
    private boolean flag4;
    private String string;
    private String string2;

    public void method_2398(String var1, String var2) {
        this.method_2403(var1, var2, true);
    }

    private boolean method_2399() {
        return this.string2 != null && (this.animation.method_606() > 0.001F || this.animation.getFloatValue3() > 0.0F);
    }

    public void method_2400() {
        this.floatValue5 = 0.0F;
        this.animation.method_603(0.0F, 200.0F, Easings.easing6);
    }

    public void method_2401(String var1) {
        this.method_2403(var1, null, false);
    }

    @Override
    public void method_2186(RenderContext var1) {
        if (this.method_2399()) {
            UiRenderer var2 = var1.uiRenderer;
            float var6 = this.animation.method_606();
            int var12;
            if (!this.flag4) {
                var12 = var1.method_2256().intValue16();
            } else {
                var12 = var1.method_2256().intValue20();
            }

            int var14;
            if (!this.flag4) {
                var14 = var1.method_2256().intValue10();
            } else {
                var14 = var1.method_2256().intValue18();
            }

            int var20 = var14;
            float var15;
            if (this.string == null) {
                var15 = 0.0F;
            } else {
                var15 = 26.0F;
            }

            float var3 = Math.min(380.0F, this.floatValue4) - 32.0F - var15;
            List var21 = this.method_2402(var2, var3);
            float var7 = 0.0F;

            Iterator var8;
            for (Iterator var16 = var8 = var21.iterator(); var16.hasNext(); var16 = var8) {
                String var9 = (String)var8.next();
                var7 = Math.max(var7, var2.method_442(Typography.fontStyle15, var9));
            }

            float var25 = 32.0F + var15 + var7;
            float var26 = Math.max(34.0F, 21.0F + var21.size() * 13.0F);
            var7 = this.floatValue3 + (this.floatValue4 - var25) / 2.0F;
            float var10 = this.floatValue + this.floatValue2 - 12.0F - var26 + 10.0F * (1.0F - var6);
            var2.method_474();
            var2.method_440(var6);
            var2.method_485(var7, var10, var25, var26, 4.0F, 3);
            var2.method_479(var7, var10, var25, var26, 4.0F, var12);
            var6 = var7 + 16.0F;
            float var18 = var10 + var26 / 2.0F;
            if (this.string != null) {
                var2.method_477(this.string, var6 + 8.0F, var18, 16.0F, var14);
                var6 += var15;
            }

            float var19 = var18 - (var21.size() - 1) * 13.0F / 2.0F;

            Iterator var5;
            for (Iterator var17 = var5 = var21.iterator(); var17.hasNext(); var17 = var5) {
                String var22 = (String)var5.next();
                var2.method_432(Typography.fontStyle15, var22, var6, var19, var20);
                var19 += 13.0F;
            }

            var2.method_430();
        }
    }

    private List<String> method_2402(UiRenderer var1, float var2) {
        ArrayList var4 = new ArrayList();
        StringBuilder var13 = new StringBuilder();
        String[] var5;
        int var6 = (var5 = this.string2.split(" ")).length;
        int var7 = 0;
        int var11 = 0;

        ArrayList var10000;
        while (true) {
            if (var11 >= var6) {
                var10000 = var4;
                break;
            }

            String var8;
            if (!(var8 = var5[var7]).isEmpty()) {
                String var10;
                if (var13.isEmpty()) {
                    var10 = var8;
                } else {
                    var10 = var13 + " " + var8;
                }

                if (!(var1.method_442(Typography.fontStyle15, var10) <= var2) && !var13.isEmpty()) {
                    var4.add(var13.toString());
                    var13.setLength(0);
                    var13.append(var8);
                    if (var4.size() == 4) {
                        var10000 = var4;
                        break;
                    }
                } else {
                    var13.setLength(0);
                    var13.append(var10);
                }
            }

            var11 = ++var7;
        }

        if (var10000.size() < 4 && !var13.isEmpty()) {
            var4.add(var13.toString());
        }

        int var14 = 0;

        for (int var12 = 0; var12 < var4.size(); var12 = var14) {
            int var10001 = var14;
            FontStyle var10003 = Typography.fontStyle15;
            String var10004 = (String)var4.get(var14);
            var14++;
            var4.set(var10001, var1.method_469(var10003, var10004, var2));
        }

        return var4;
    }

    public void method_2403(String var1, String var2, boolean var3) {
        this.string2 = var1;
        this.string = var2;
        this.flag4 = var3;
        Label var6;
        float var7;
        float var8;
        if (!var3) {
            float var5 = 800.0F;
            var8 = 1.0F;
            var7 = var5;
            var6 = this;
        } else {
            var8 = 2.0F;
            var7 = 800.0F;
            var6 = this;
        }

        var6.floatValue5 = var7 * var8;
        this.animation.method_603(1.0F, 300.0F, Easings.easing7);
    }

    @Override
    public void method_2206(RenderContext var1) {
        if (this.string2 != null) {
            if (this.floatValue5 > 0.0F) {
                this.floatValue5 = this.floatValue5 - var1.floatValue;
                if (this.floatValue5 <= 0.0F) {
                    this.method_2400();
                }
            }

            this.animation.method_605(var1.floatValue);
            if (this.animation.getFloatValue3() == 0.0F && this.animation.method_604()) {
                this.string2 = null;
            }
        }
    }
}
