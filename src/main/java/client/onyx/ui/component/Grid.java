package client.onyx.ui.component;

import client.onyx.input.KeyTyped;
import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easings;
import client.onyx.render.ui.UiRenderer;
import client.onyx.ui.theme.ColorRoles;
import client.onyx.ui.theme.Typography;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BooleanSupplier;

public class Grid extends Component {
    private static final float FLOAT_10_01 = 10.0F;
    private static final float FLOAT_6_01 = 6.0F;
    private static final float FLOAT_16_01 = 16.0F;
    private static final float FLOAT_7_01 = 7.0F;
    private static final float FLOAT_10_02 = 10.0F;
    private static final float FLOAT_0_851 = 0.85F;
    private static final float FLOAT_30_01 = 30.0F;
    private static final int INT_21 = 2;
    private static final float FLOAT_12_01 = 12.0F;
    private static final float FLOAT_150_01 = 150.0F;
    private static final float FLOAT_14_01 = 14.0F;
    private final List<Grid.Cell> list22 = new ArrayList<>();
    private final List<ComponentHost> list2 = new ArrayList<>();
    private final Animation animation = new Animation(0.0F);
    private float floatValue5;
    private float floatValue22;
    private float floatValue32;
    private float floatValue42;
    private boolean flag4;
    private boolean flag22;

    public boolean isFlag22() {
        return this.flag22;
    }

    @Override
    public boolean method_2183(float var1, float var2, int var3) {
        if (!this.flag22) {
            return false;
        } else if (!this.method_2259(var1, var2)) {
            this.method_2482();
            return true;
        } else if (var3 != 0) {
            return true;
        } else {
            int var5;
            if ((var5 = this.method_2486(var2)) >= 0) {
                Grid.Cell var8 = this.list22.get(var5);
                Runnable var6 = var8.runnable();
                if (!var8.flag2()) {
                    this.method_2482();
                }

                if (var6 != null) {
                    var6.run();
                }
            }

            return true;
        }
    }

    public void method_2481(List<Grid.Cell> var1, Component var2, Component var3, UiRenderer var4) {
        this.method_2484(var1, var3, var4);
        float var17 = var2.getFloatValue3() + var2.getFloatValue4();
        float var19 = var2.getFloatValue() + var2.getFloatValue2();
        this.floatValue3 = Math.clamp(
            var17 - this.floatValue4, var3.getFloatValue3() + 6.0F, var3.getFloatValue3() + var3.getFloatValue4() - this.floatValue4 - 6.0F
        );
        Grid var6;
        boolean var7;
        if (!(var19 + this.floatValue2 > var3.getFloatValue() + var3.getFloatValue2() - 6.0F)) {
            var7 = false;
            var6 = this;
        } else {
            var7 = true;
            var6 = this;
        }

        var6.flag4 = var7;
        Grid var9;
        float var10;
        Component var11;
        if (!this.flag4) {
            var11 = var3;
            var10 = var19;
            var9 = this;
        } else {
            float var10001 = var2.getFloatValue() - this.floatValue2;
            var11 = var3;
            var10 = var10001;
            var9 = this;
        }

        var9.floatValue = Math.clamp(var10, var11.getFloatValue() + 6.0F, var3.getFloatValue() + var3.getFloatValue2() - this.floatValue2 - 6.0F);
        this.floatValue5 = var17;
        Grid var14;
        float var15;
        if (!this.flag4) {
            var15 = this.floatValue;
            var14 = this;
        } else {
            var15 = this.floatValue + this.floatValue2;
            var14 = this;
        }

        var14.floatValue22 = var15;
        this.animation.method_607(0.0F).method_603(1.0F, 200.0F, Easings.easing7);
    }

    @Override
    public void method_2206(RenderContext var1) {
        RenderContext var11 = var1;
        var var12 = this;
        if (this.flag22) {
            this.animation.method_605(var1.floatValue);
            this.flag = this.method_2259(var1.floatValue3, var1.floatValue2);
            int var4;
            if (this.flag) {
                var4 = this.method_2486(var1.floatValue2);
            } else {
                var4 = -1;
            }

            int var2 = var4;
            int var3 = 0;

            for (int var10 = 0; var10 < var12.list2.size(); var10 = var3) {
                ComponentHost var13 = var12.list2.get(var3);
                ComponentHost var7;
                float var8;
                boolean var9;
                if (var3 != var2) {
                    float var6 = var11.floatValue;
                    ComponentHost var5 = var13;
                    var9 = false;
                    var8 = var6;
                    var7 = var5;
                } else {
                    var9 = true;
                    var8 = var11.floatValue;
                    var7 = var13;
                }

                var3++;
                var7.method_2220(var8, var9, false);
            }
        }
    }

    public void method_2482() {
        if (this.flag22) {
            this.flag22 = false;
            this.list22.clear();
            this.list2.clear();
            this.floatValue32 = 0.0F;
            this.floatValue42 = 0.0F;
        }
    }

    @Override
    public boolean method_2212(KeyTyped var1) {
        if (this.flag22 && var1.intValue() == 1) {
            this.method_2482();
            return true;
        } else {
            return false;
        }
    }

    private float method_2483() {
        float var8 = 12.0F;

        Iterator var2;
        for (Iterator var7 = var2 = this.list22.iterator(); var7.hasNext(); var7 = var2) {
            Grid.Cell var3 = (Grid.Cell)var2.next();
            float var5;
            float var6;
            if (!var3.flag3()) {
                var6 = 30.0F;
                var5 = var8;
            } else {
                var6 = 7.0F;
                var5 = var8;
            }

            var8 = var5 + var6;
        }

        return var8;
    }

    private void method_2484(List<Grid.Cell> var1, Component var2, UiRenderer var3) {
        var var7 = this;
        this.list22.clear();
        this.list2.clear();
        this.list22.addAll(var1);
        Iterator var6 = var1.iterator();

        for (Iterator var4 = var6; var4.hasNext(); var4 = var6) {
            Grid.Cell var8 = (Grid.Cell)var6.next();
            var7.list2.add(new ComponentHost());
        }

        var7.floatValue4 = var7.method_2487(var3);
        var7.floatValue32 = var7.method_2483();
        var7.floatValue2 = Math.min(var7.floatValue32, var2.getFloatValue2() - 12.0F);
        var7.floatValue42 = 0.0F;
        var7.flag22 = true;
    }

    public void method_2485(List<Grid.Cell> var1, Component var2, Component var3, UiRenderer var4) {
        this.method_2484(var1, var3, var4);
        boolean var5;
        float var15;
        if ((var15 = var2.getFloatValue3() + var2.getFloatValue4()) + this.floatValue4 > var3.getFloatValue3() + var3.getFloatValue4() - 6.0F) {
            var5 = true;
        } else {
            var5 = false;
        }

        Grid var7;
        float var8;
        Component var9;
        if (!var5) {
            var9 = var3;
            var8 = var15;
            var7 = this;
        } else {
            float var10001 = var2.getFloatValue3() - this.floatValue4;
            var9 = var3;
            var8 = var10001;
            var7 = this;
        }

        var7.floatValue3 = Math.clamp(var8, var9.getFloatValue3() + 6.0F, var3.getFloatValue3() + var3.getFloatValue4() - this.floatValue4 - 6.0F);
        this.floatValue = Math.clamp(var2.getFloatValue(), var3.getFloatValue() + 6.0F, var3.getFloatValue() + var3.getFloatValue2() - this.floatValue2 - 6.0F);
        this.flag4 = var5;
        Grid var12;
        float var13;
        if (!var5) {
            var13 = this.floatValue3;
            var12 = this;
        } else {
            var13 = this.floatValue3 + this.floatValue4;
            var12 = this;
        }

        var12.floatValue5 = var13;
        this.floatValue22 = this.floatValue;
        this.animation.method_607(0.0F).method_603(1.0F, 200.0F, Easings.easing7);
    }

    @Override
    public void method_2186(RenderContext var1) {
        var var3 = this;
        if (this.flag22) {
            UiRenderer var2;
            UiRenderer var10000 = var2 = var1.uiRenderer;
            float var28 = this.animation.method_606();
            var2.method_474();
            var2.method_440(var28);
            var2.method_441();
            var2.method_491(0.85F + 0.14999998F * var28, this.floatValue5, this.floatValue22);
            var2.method_485(this.floatValue3, this.floatValue, this.floatValue4, this.floatValue2, 4.0F, 2);
            var2.method_479(this.floatValue3, this.floatValue, this.floatValue4, this.floatValue2, 4.0F, ColorRoles.method_182(2, var1.method_2256()));
            var10000.method_443(this.floatValue3, this.floatValue, this.floatValue4, this.floatValue2);
            float var29 = this.floatValue + 6.0F - this.floatValue42;
            int var4 = 0;

            for (int var27 = 0; var27 < var3.list22.size(); var27 = ++var4) {
                Grid.Cell var5;
                if ((var5 = var3.list22.get(var4)).flag3()) {
                    if (var29 + 7.0F >= var3.floatValue && var29 <= var3.floatValue + var3.floatValue2) {
                        var2.method_434(var3.floatValue3 + 12.0F, var29 + 3.5F, var3.floatValue4 - 24.0F, 1.0F, var1.method_2256().intValue12());
                    }

                    var29 += 7.0F;
                } else if (!(var29 + 30.0F < var3.floatValue) && !(var29 > var3.floatValue + var3.floatValue2)) {
                    int var12;
                    if (!var5.flag()) {
                        var12 = var1.method_2256().intValue15();
                    } else {
                        var12 = var1.method_2256().intValue13();
                    }

                    var3.list2.get(var4).method_2219(var2, var3.floatValue3, var29, var3.floatValue4, 30.0F, 0.0F, var12);
                    float var7 = var3.floatValue3 + 12.0F;
                    UiRenderer var15;
                    if (var5.booleanSupplier() != null) {
                        boolean var8 = var5.booleanSupplier().getAsBoolean();
                        float var9 = var7 + 1.0F;
                        float var10 = var29 + 8.0F;
                        float var14;
                        if (!var8) {
                            var2.method_494(var9, var10, 14.0F, 14.0F, 4.0F, 1.0F, var1.method_2256().intValue7());
                            var14 = var7;
                        } else {
                            var2.method_479(var9, var10, 14.0F, 14.0F, 4.0F, var1.method_2256().intValue31());
                            Icons.method_2226(var2, var9 + 7.0F, var10 + 7.0F, 10.0F, var1.method_2256().intValue33());
                            var14 = var7;
                        }

                        var7 = var14 + 26.0F;
                        var15 = var2;
                    } else {
                        if (var5.string() != null) {
                            String var10001 = var5.string();
                            float var10002 = var7 + 8.0F;
                            float var10003 = var29 + 15.0F;
                            UiRenderer var21;
                            String var22;
                            float var23;
                            float var24;
                            float var25;
                            int var26;
                            if (!var5.flag()) {
                                float var20 = 16.0F;
                                float var19 = var10003;
                                float var18 = var10002;
                                String var17 = var10001;
                                var26 = var1.method_2256().intValue7();
                                var25 = var20;
                                var24 = var19;
                                var23 = var18;
                                var22 = var17;
                                var21 = var2;
                            } else {
                                var26 = var12;
                                var25 = 16.0F;
                                var24 = var10003;
                                var23 = var10002;
                                var22 = var10001;
                                var21 = var2;
                            }

                            var21.method_477(var22, var23, var24, var25, var26);
                            var7 += 26.0F;
                        }

                        var15 = var2;
                    }

                    var15.method_432(
                        Typography.fontStyle14,
                        var2.method_469(Typography.fontStyle14, var5.string2(), var3.floatValue3 + var3.floatValue4 - 12.0F - var7),
                        var7,
                        var29 + 15.0F,
                        var12
                    );
                    var29 += 30.0F;
                } else {
                    var29 += 30.0F;
                }
            }

            var2.method_486();
            float var30;
            if ((var30 = Math.max(0.0F, var3.floatValue32 - var3.floatValue2)) > 0.0F) {
                float var31 = var3.floatValue2 - 12.0F;
                float var6 = Math.max(24.0F, var31 * var3.floatValue2 / var3.floatValue32);
                float var32 = var3.floatValue + 6.0F + (var31 - var6) * var3.floatValue42 / var30;
                var2.method_479(var3.floatValue3 + var3.floatValue4 - 5.0F, var32, 3.0F, var6, Float.MAX_VALUE, var1.method_2256().intValue12());
            }

            var2.method_435();
            var2.method_430();
            if (var3.flag) {
                var2.method_465();
            }
        }
    }

    private int method_2486(float var1) {
        var var2 = this;
        float var8 = this.floatValue + 6.0F - this.floatValue42;
        int var3 = 0;

        for (int var7 = 0; var7 < var2.list22.size(); var7 = var3) {
            Grid.Cell var4;
            float var6;
            if ((var4 = var2.list22.get(var3)).flag3()) {
                var6 = 7.0F;
            } else {
                var6 = 30.0F;
            }

            if (!var4.flag3() && var1 >= var8 && var1 < var8 + var6) {
                return var3;
            }

            var3++;
            var8 += var6;
        }

        return -1;
    }

    private float method_2487(UiRenderer var1) {
        float var2 = 150.0F;
        Iterator var3;
        Iterator var7 = var3 = this.list22.iterator();

        while (var7.hasNext()) {
            Grid.Cell var9;
            if ((var9 = (Grid.Cell)var3.next()).flag3()) {
                var7 = var3;
            } else {
                float var5 = var1.method_442(Typography.fontStyle14, var9.string2());
                float var8;
                if (var9.string() == null && var9.booleanSupplier() == null) {
                    var8 = 0.0F;
                } else {
                    var8 = 26.0F;
                }

                var2 = Math.max(var2, 24.0F + var8 + var5);
                var7 = var3;
            }
        }

        return var2;
    }

    @Override
    public boolean method_2211(float var1, float var2, double var3) {
        if (!this.flag22) {
            return false;
        } else if (!this.method_2259(var1, var2)) {
            return true;
        } else {
            float var6 = Math.max(0.0F, this.floatValue32 - this.floatValue2);
            this.floatValue42 = Math.clamp(this.floatValue42 - (float)var3 * 30.0F, 0.0F, var6);
            return true;
        }
    }

    public record Cell(String string, String string2, boolean flag, boolean flag3, BooleanSupplier booleanSupplier, boolean flag2, Runnable runnable) {
        public static Grid.Cell method_2382(String var0, String var1, Runnable var2) {
            return new Grid.Cell(var0, var1, false, false, null, false, var2);
        }

        public static Grid.Cell method_2383() {
            return new Grid.Cell(null, null, false, true, null, false, null);
        }

        public static Grid.Cell method_2384(String var0, String var1, Runnable var2) {
            return new Grid.Cell(var0, var1, false, false, null, true, var2);
        }

        public static Grid.Cell method_2385(String var0, BooleanSupplier var1, Runnable var2) {
            return new Grid.Cell(null, var0, false, false, var1, true, var2);
        }

        public static Grid.Cell method_2386(String var0, String var1, Runnable var2) {
            return new Grid.Cell(var0, var1, true, false, null, false, var2);
        }
    }
}
