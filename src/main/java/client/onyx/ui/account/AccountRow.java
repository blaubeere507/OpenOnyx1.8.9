package client.onyx.ui.account;

import client.onyx.Onyx;
import client.onyx.account.Account;
import client.onyx.account.AccountHeads;
import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easings;
import client.onyx.render.anim.Spring;
import client.onyx.render.ui.UiRenderer;
import client.onyx.ui.component.Component;
import client.onyx.ui.component.ComponentHost;
import client.onyx.ui.component.RenderContext;
import client.onyx.ui.component.Tooltip;
import client.onyx.ui.theme.Palette;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.Typography;
import java.util.function.Consumer;

public class AccountRow extends Component {
    public static final float FLOAT_56_01 = 56.0F;
    private static final float FLOAT_1_81 = 1.8F;
    private static final float FLOAT_14_01 = 14.0F;
    private static final float FLOAT_24_01 = 24.0F;
    private static final float FLOAT_20_01 = 20.0F;
    private static final float FLOAT_3_01 = 3.0F;
    private static final float FLOAT_10_01 = 10.0F;
    private static final long LONG_600001 = 60000L;
    private static final float FLOAT_900_01 = 900.0F;
    private static final float FLOAT_6_01 = 6.0F;
    private static final float FLOAT_11_01 = 11.0F;
    private static final float FLOAT_8_01 = 8.0F;
    private static final float FLOAT_32_01 = 32.0F;
    private static final long LONG_6000001 = 600000L;
    private static final float FLOAT_12_01 = 12.0F;
    private static final long LONG_36000001 = 3600000L;
    private final ComponentHost componentHost = new ComponentHost();
    private final Spring spring = new Spring(0);
    private final Animation animation = new Animation(0.0F);
    private final Account account;
    private final Consumer<AccountRow> consumer;
    private final Consumer<AccountRow> consumer2;
    private final Tooltip tooltip;
    private boolean flag4;
    private float floatValue5;
    private boolean flag22;

    public AccountRow(Account var1, Consumer<AccountRow> var2, Consumer<AccountRow> var3) {
        this.account = var1;
        this.floatValue2 = 56.0F;
        this.consumer = var2;
        this.consumer2 = var3;
        this.tooltip = new Tooltip("\ue5d4", () -> var3.accept(this));
        this.list.add(this.tooltip);
    }

    @Override
    public void method_2194() {
        this.tooltip.method_2258(this.floatValue3 + this.floatValue4 - 30.0F - 8.0F, this.floatValue + (this.floatValue2 - 30.0F) / 2.0F, 30.0F, 30.0F);
    }

    private int method_2527(Palette var1) {
        long var2;
        if ((var2 = this.account.getLongValue2() - System.currentTimeMillis()) <= 600000L) {
            return var1.intValue13();
        } else {
            return var2 <= 3600000L ? var1.intValue22() : var1.intValue27();
        }
    }

    private boolean method_2528() {
        return Onyx.accountManager.getAccount() == this.account;
    }

    @Override
    public void method_2186(RenderContext var1) {
        UiRenderer var16 = var1.uiRenderer;
        UiRenderer var10002 = var1.uiRenderer;
        boolean var5 = this.method_2528();
        float var4 = this.floatValue + this.floatValue2 / 2.0F;
        var10002.method_479(this.floatValue3, this.floatValue, this.floatValue4, this.floatValue2, 16.0F, this.spring.method_617());
        this.componentHost.method_2219(var16, this.floatValue3, this.floatValue, this.floatValue4, this.floatValue2, 16.0F, var1.method_2256().intValue15());
        float var3;
        if ((var3 = this.animation.method_606()) > 0.01F) {
            var16.method_489(this.floatValue3 + 11.0F, var4, 3.0F * var3, Theme.method_180(var1.method_2256().intValue31(), var3));
        }

        var3 = var4 - 16.0F;
        var16.method_479(this.floatValue3 + 20.0F, var3, 32.0F, 32.0F, 4.0F, var1.method_2256().intValue11());
        var16.method_463(AccountHeads.method_1821(this.account.getUuid(), this.account.getString()), this.floatValue3 + 20.0F, var3, 32.0F);
        int var12;
        if (!var5) {
            var12 = var1.method_2256().intValue15();
        } else {
            var12 = var1.method_2256().intValue30();
        }

        int var14;
        if (!var5) {
            var14 = var1.method_2256().intValue7();
        } else {
            var14 = Theme.method_180(var1.method_2256().intValue30(), 0.72F);
        }

        String var6;
        float var15;
        if ((var6 = this.method_2530()) == null) {
            var15 = 0.0F;
        } else {
            var15 = var16.method_442(Typography.fontStyle2, var6) + 10.0F;
        }

        float var8 = this.floatValue3 + 20.0F + 32.0F + 12.0F;
        float var7 = this.tooltip.getFloatValue3() - 10.0F - var15;
        String var9 = new StringBuilder().insert(0, "(").append(this.account.getAccountType().getString2()).append(")").toString();
        float var10 = var16.method_442(Typography.fontStyle2, var9);
        String var18 = var16.method_469(Typography.fontStyle14, this.account.getString(), Math.max(24.0F, var7 - var8 - var10 - 6.0F));
        var16.method_432(Typography.fontStyle14, var18, var8, var4, var12);
        var16.method_432(Typography.fontStyle2, var9, var8 + var16.method_442(Typography.fontStyle14, var18) + 6.0F, var4, var14);
        if (var6 != null) {
            var16.method_476(Typography.fontStyle2, var6, this.tooltip.getFloatValue3() - 10.0F, var4, this.method_2527(var1.method_2256()));
        }

        if (this.flag22) {
            var16.method_495(this.tooltip.getFloatValue3() + 15.0F, var4, 7.0F, 1.8F, this.floatValue5 / 900.0F, var1.method_2256().intValue31());
        } else {
            super.method_2186(var1);
        }
    }

    public Account getAccount() {
        return this.account;
    }

    public Component method_2529() {
        return this.tooltip;
    }

    @Override
    public boolean method_2183(float var1, float var2, int var3) {
        if (this.flag22) {
            return this.method_2259(var1, var2);
        } else if (this.tooltip.method_2183(var1, var2, var3)) {
            return true;
        } else if (!this.method_2259(var1, var2)) {
            return false;
        } else if (var3 == 0) {
            boolean var6 = this.flag3 = true;
            this.consumer.accept(this);
            return var6;
        } else if (var3 == 1) {
            this.consumer2.accept(this);
            return true;
        } else {
            return false;
        }
    }

    private String method_2530() {
        long var1;
        if ((var1 = this.account.getLongValue2()) <= 0L) {
            return null;
        } else if ((var1 = var1 - System.currentTimeMillis()) <= 0L) {
            return "Expired";
        } else {
            long var3 = var1 / 3600000L;
            var1 = var1 % 3600000L / 60000L;
            long var7;
            int var10000 = (var7 = var3 - 0L) == 0L ? 0 : (var7 < 0L ? -1 : 1);
            StringBuilder var10002;
            if (var10000 > 0) {
                var10002 = new StringBuilder();
                return var10002.append(var3).append("h").append(var1).append("m").toString();
            } else {
                var10002 = new StringBuilder();
                return var10002.append(Math.max(1L, var1)).append("m").toString();
            }
        }
    }

    @Override
    public boolean method_2189(float var1, float var2, int var3) {
        this.flag3 = false;
        return super.method_2189(var1, var2, var3);
    }

    public void method_2531(boolean var1) {
        this.flag22 = var1;
    }

    @Override
    public void method_2206(RenderContext var1) {
        super.method_2206(var1);
        boolean var2;
        int var5;
        if (!(var2 = this.method_2528())) {
            var5 = var1.method_2256().intValue19();
        } else {
            var5 = var1.method_2256().intValue5();
        }

        if (!this.flag4) {
            this.flag4 = true;
            this.spring.method_618(var5);
            Animation var7;
            float var8;
            if (!var2) {
                Animation var6 = this.animation;
                var8 = 0.0F;
                var7 = var6;
            } else {
                var8 = 1.0F;
                var7 = this.animation;
            }

            var7.method_607(var8);
        }

        this.spring.method_613(var5, 250.0F, Easings.easing6);
        this.spring.method_614(var1.floatValue);
        Animation var10;
        float var11;
        if (!var2) {
            Animation var9 = this.animation;
            var11 = 0.0F;
            var10 = var9;
        } else {
            var11 = 1.0F;
            var10 = this.animation;
        }

        ComponentHost var14;
        float var15;
        boolean var16;
        AccountRow var17;
        label40: {
            var10.method_603(var11, 250.0F, Easings.easing6);
            this.animation.method_605(var1.floatValue);
            ComponentHost var20 = this.componentHost;
            float var10001 = var1.floatValue;
            ComponentHost var12;
            float var13;
            if (!this.flag) {
                var13 = var1.floatValue;
                var12 = this.componentHost;
            } else if (this.flag22) {
                var13 = var1.floatValue;
                var12 = this.componentHost;
            } else {
                if (!this.tooltip.isFlag()) {
                    var17 = this;
                    var16 = true;
                    var15 = var10001;
                    var14 = var20;
                    break label40;
                }

                var13 = var10001;
                var12 = var20;
            }

            var17 = this;
            var16 = false;
            var15 = var13;
            var14 = var12;
        }

        var14.method_2220(var15, var16, var17.flag3);
        if (this.flag22) {
            this.floatValue5 = (this.floatValue5 + var1.floatValue) % 900.0F;
        }
    }
}
