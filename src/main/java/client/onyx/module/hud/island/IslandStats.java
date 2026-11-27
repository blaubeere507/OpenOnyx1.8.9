package client.onyx.module.hud.island;

import client.onyx.Onyx;
import client.onyx.module.hud.HudLayout;
import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easing;
import client.onyx.render.anim.Easings;
import client.onyx.render.ui.HealthBarAnimator;
import client.onyx.render.ui.HotbarRenderer;
import client.onyx.render.ui.PanelRenderer;
import client.onyx.render.ui.StatusIcons;
import client.onyx.render.ui.UiRenderer;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.ui.component.ComponentHost;
import client.onyx.ui.component.Icons;
import client.onyx.ui.theme.ColorRoles;
import client.onyx.ui.theme.Palette;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.Typography;
import client.onyx.util.IMinecraft;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.network.Packet;
import org.lwjgl.input.Mouse;

public final class IslandStats implements IMinecraft {
    private static final float FLOAT_1_01 = 1.0F;
    private static final float FLOAT_7_01 = 7.0F;
    private static final float FLOAT_250_01 = 250.0F;
    private static final float FLOAT_100_01 = 100.0F;
    private static final float FLOAT_10_01 = 10.0F;
    private static final float FLOAT_22_01 = 22.0F;
    private static final float FLOAT_3_01 = 3.0F;
    private static final float FLOAT_10_02 = 10.0F;
    private static final int INT_31 = 3;
    private static final float FLOAT_8_01 = 8.0F;
    private static final float FLOAT_28_01 = 28.0F;
    private static final String STRING_CONST = "stats-island";
    private static final float FLOAT_96_01 = 96.0F;
    private static final float FLOAT_5_01 = 5.0F;
    private static final float FLOAT_0_941 = 0.94F;
    private static final float FLOAT_3_02 = 3.0F;
    private static final float FLOAT_1_02 = 1.0F;
    private final IslandStats.PingTracker pingTracker = new IslandStats.PingTracker();
    private final Animation animation = new Animation(0.0F);
    private final Animation animation2 = new Animation(28.0F);
    private final ComponentHost componentHost = new ComponentHost();
    private final List<IslandStats.Sample> list2 = new ArrayList<>();
    private final List<Animation> list = new ArrayList<>();
    private float[] floatValueArray2 = new float[0];
    private float[] floatValueArray = new float[0];
    private int intValue = -1;
    private HealthBarAnimator.Ghost ghost;
    private final StatusIcons statusIcons;
    private final Watermark watermark;
    private final HudLayout hudLayout;
    private float floatValue;
    private float floatValue2;
    private boolean flag;
    private float floatValue3;
    private boolean flag2;
    private float floatValue4;
    private boolean flag3;
    private float floatValue5;
    private float floatValue6;
    private float floatValue7;
    private boolean flag4;
    private boolean flag5;
    private long longValue;
    private int intValue2;
    private boolean flag6;
    private float floatValue8;
    private float floatValue9;

    private String method_1308() {
        ServerData var2;
        if ((var2 = mc.getCurrentServerData()) != null) {
            return var2.serverIP;
        } else {
            return mc.isSingleplayer() ? "Singleplayer" : "Local";
        }
    }

    public IslandStats(Watermark var1) {
        this.ghost = HealthBarAnimator.ghost;
        this.statusIcons = new StatusIcons();
        this.watermark = var1;
        this.hudLayout = new HudLayout(var1);
    }

    public boolean method_1309() {
        return mc.thePlayer != null && mc.theWorld != null && (this.method_1328() || this.animation.method_606() > 0.0F || !this.animation.method_604());
    }

    private void method_1310() {
        if (this.intValue >= 0 && this.intValue2 != this.intValue) {
            this.watermark.stats.method_144(this.intValue, this.intValue2);
        }

        this.intValue = -1;
    }

    private List<Integer> method_1311() {
        var var2 = this;
        ArrayList var1 = new ArrayList(this.list2.size());
        int var4 = 0;

        for (int var3 = 0; var3 < var2.list2.size(); var3 = ++var4) {
            if (var4 != var2.intValue) {
                var1.add(var4);
            }
        }

        var1.add(Math.clamp((long)var2.intValue2, 0, var1.size()), var2.intValue);
        return var1;
    }

    public boolean method_1312(double var1, double var3) {
        return var1 >= this.floatValue5 && var1 < this.floatValue5 + this.floatValue3 && var3 >= this.floatValue8 && var3 < this.floatValue8 + this.floatValue9;
    }

    private void method_1313(UiRenderer var1) {
        var var2 = this;
        this.method_1320();
        int var4 = this.list2.size();
        if (this.floatValueArray2.length != var4) {
            this.floatValueArray2 = new float[var4];
            this.floatValueArray = new float[var4];
        }

        float var3 = 8.0F;
        int var6 = 0;

        for (int var5 = 0; var5 < var4; var5 = var6) {
            if (var6 > 0) {
                var3 += 7.0F;
            }

            var2.floatValueArray2[var6] = var3;
            var2.floatValueArray[var6] = var2.method_1317(var1, var2.list2.get(var6));
            var3 += var2.floatValueArray[var6++];
        }
    }

    private void method_1314(UiRenderer var1, Palette var2, float var3) {
        var var5 = this;
        int var4 = ColorRoles.method_182(3, var2);
        var1.method_485(0.0F, 0.0F, var3, 22.0F, Float.MAX_VALUE, 3);
        PanelRenderer.method_598(var1, 0.0F, 0.0F, var3, 22.0F, Float.MAX_VALUE, var4);
        float var10 = 11.0F;
        int var8 = 0;

        for (int var7 = 0; var7 < var5.list2.size(); var7 = ++var8) {
            if (var8 != var5.intValue) {
                float var6 = var5.list.get(var8).method_606();
                if (var8 > 0 && var8 != var5.intValue) {
                    var1.method_489(var5.floatValueArray2[var8] - 3.5F + var6, var10, 1.0F, var2.intValue12());
                }

                var5.method_1323(var1, var2, var8, var5.floatValueArray2[var8] + var6, var10, 1.0F);
            }
        }

        if (var5.intValue >= 0 && var5.intValue < var5.floatValueArray.length) {
            float var9 = var5.floatValueArray[var5.intValue];
            float var11 = Math.clamp(var5.floatValue6 - var5.floatValue, 0.0F, Math.max(0.0F, var3 - var9));
            var1.method_479(var11 - 4.0F, 2.0F, var9 + 8.0F, 18.0F, 8.0F, Theme.method_180(var2.intValue5(), 0.92F));
            var5.method_1323(var1, var2, var5.intValue, var11, var10, 1.0F);
        }

        var5.componentHost.method_2219(var1, 0.0F, 0.0F, var3, 22.0F, Float.MAX_VALUE, var2.intValue15());
        var1.method_494(0.0F, 0.0F, var3, 22.0F, Float.MAX_VALUE, 1.0F, var2.intValue12());
    }

    private static float method_1315(NumberSetting var0, float var1) {
        return var1 <= 0.0F ? 0.0F : (float)(var1 * var0.getValue() / 100.0);
    }

    private void method_1316() {
        var var2 = this;
        if (this.intValue < 0) {
            Iterator var10 = this.list.iterator();

            while (var10.hasNext()) {
                Animation var9;
                if ((var9 = (Animation)var10.next()).getFloatValue3() != 0.0F) {
                    var9.method_603(0.0F, 100.0F, Easings.easing2);
                }
            }
        } else {
            List var1 = this.method_1311();
            float var8 = 8.0F;
            int var4 = 0;

            for (int var7 = 0; var7 < var1.size(); var7 = var4) {
                int var3 = (Integer)var1.get(var4);
                if (var4 > 0) {
                    var8 += 7.0F;
                }

                if (var3 != var2.intValue) {
                    float var5 = var8 - var2.floatValueArray2[var3];
                    Animation var6;
                    if ((var6 = var2.list.get(var3)).getFloatValue3() != var5) {
                        var6.method_603(var5, 100.0F, Easings.easing2);
                    }
                }

                var4++;
                var8 += var2.floatValueArray[var3];
            }
        }
    }

    private float method_1317(UiRenderer var1, IslandStats.Sample var2) {
        float var4 = Math.min(var1.method_442(Typography.fontStyle9, var2.string2()), 96.0F);
        float var5 = 13.0F + var4;
        if (!var2.string().isEmpty()) {
            var5 += 3.0F + var1.method_442(Typography.fontStyle8, var2.string());
        }

        return var5;
    }

    private boolean method_1318() {
        return Mouse.isButtonDown(0);
    }

    public void method_1319(Packet<?> var1) {
        this.pingTracker.method_1431();
    }

    private void method_1320() {
        for (IslandStats var1 = this; var1.list.size() < this.list2.size(); var1 = this) {
            this.list.add(new Animation(0.0F));
        }

        for (IslandStats var2 = this; var2.list.size() > this.list2.size(); var2 = this) {
            this.list.remove(this.list.size() - 1);
        }
    }

    private int method_1321() {
        if (mc.getNetHandler() != null && mc.thePlayer != null) {
            NetworkPlayerInfo var2;
            return (var2 = mc.getNetHandler().getPlayerInfo(mc.thePlayer.getUniqueID())) == null ? 0 : Math.max(0, var2.getResponseTime());
        } else {
            return 0;
        }
    }

    private boolean method_1322(boolean var1, float var2, float var3, float var4, float var5) {
        float var13 = Minecraft.getScaledMouseX() * var4 / mc.displayWidth;
        var5 = var5 - Minecraft.getScaledMouseY() * var5 / mc.displayHeight - 1.0F;
        boolean var6 = this.method_1318();
        float var14 = (var13 - this.floatValue5) / var2;
        var2 = (var5 - this.floatValue8) / var2;
        IslandStats var7;
        if (var1 && var6) {
            if (!this.flag) {
                IslandStats var9;
                boolean var10;
                int hit; // hand-fixed: the obfuscator reused the boolean slot var1 for this index
                if ((hit = this.method_1326(var14, var2)) < 0) {
                    var10 = false;
                    var9 = this;
                } else {
                    var10 = true;
                    var9 = this;
                }

                var9.flag6 = var10;
                if (this.flag6) {
                    this.intValue = hit;
                    this.floatValue = var14 - this.floatValueArray2[hit];
                    this.intValue2 = hit;
                }
            }

            this.flag = var6;
            this.floatValue6 = var14;
            if (this.intValue >= 0) {
                this.intValue2 = this.method_1333(var14);
            }

            var7 = this;
        } else {
            if (this.intValue >= 0) {
                this.method_1310();
            }

            this.flag = var6;
            this.flag6 = false;
            var7 = this;
        }

        var7.method_1316();

        Iterator var16;
        for (Iterator var11 = var16 = this.list.iterator(); var11.hasNext(); var11 = var16) {
            ((Animation)var16.next()).method_605(var3);
        }

        return this.flag6;
    }

    private void method_1323(UiRenderer var1, Palette var2, int var3, float var4, float var5, float var6) {
        IslandStats.Sample var12 = this.list2.get(var3);
        Icons.method_2224(var1, var12.stat(), var4 + 5.0F, var5, 10.0F, Theme.method_180(var2.intValue7(), var6));
        float var9 = var4 + 13.0F;
        String var7 = var1.method_469(Typography.fontStyle9, var12.string2(), 96.0F);
        var1.method_432(Typography.fontStyle9, var7, var9, var5, Theme.method_180(var2.intValue15(), var6));
        float var10 = var9 + var1.method_442(Typography.fontStyle9, var7);
        if (!var12.string().isEmpty()) {
            float var11 = var10 + 3.0F;
            var1.method_432(Typography.fontStyle8, var12.string(), var11, var5, Theme.method_180(var2.intValue7(), var6));
        }
    }

    public void method_1324(UiRenderer var1, float var2, float var3) {
        float var4 = this.method_1331();
        boolean var13;
        if (this.watermark.isToggled() && mc.currentScreen instanceof GuiChat) {
            var13 = true;
        } else {
            var13 = false;
        }

        boolean var39 = this.method_1328();
        this.method_1335(var4, var39);
        Animation var15;
        float var16;
        boolean var17;
        if (!var39) {
            Animation var14 = this.animation;
            var17 = var39;
            var16 = 0.0F;
            var15 = var14;
        } else {
            var17 = var39;
            var16 = 1.0F;
            var15 = this.animation;
        }

        Animation var20;
        float var21;
        float var22;
        boolean var23;
        if (!var17) {
            var23 = var39;
            var22 = 200.0F;
            var21 = var16;
            var20 = var15;
        } else {
            var23 = var39;
            var22 = 300.0F;
            var21 = var16;
            var20 = var15;
        }

        Animation var27;
        float var28;
        float var29;
        Easing var30;
        if (!var23) {
            var30 = Easings.easing;
            var29 = var22;
            var28 = var21;
            var27 = var20;
        } else {
            var30 = Easings.easing7;
            var29 = var22;
            var28 = var21;
            var27 = var20;
        }

        var27.method_603(var28, var29, var30);
        this.animation.method_605(var4);
        if (!var39 && this.animation.method_604() && this.animation.method_606() <= 0.0F) {
            this.floatValue3 = 0.0F;
            this.floatValue9 = 0.0F;
            this.flag2 = this.method_1318();
            this.hudLayout.method_1474();
            HotbarRenderer.method_855("stats-island");
        } else {
            PanelRenderer.method_602();
            Palette var40 = Theme.getPalette();
            this.animation2.method_603(Math.max(28.0F, this.method_1327(var1)), 200.0F, Easings.easing2);
            this.animation2.method_605(var4);
            float var7 = this.watermark.scale.method_151() * (0.94F + 0.060000002F * this.animation.method_606());
            float var8;
            this.floatValue3 = (var8 = this.animation2.method_606()) * var7;
            this.floatValue9 = 22.0F * var7;
            float var9 = var2 - this.floatValue3 - 20.0F;
            float var10 = var3 - this.floatValue9 - 20.0F;
            this.floatValue5 = 10.0F + method_1315(this.watermark.statsX, var9);
            this.floatValue8 = 10.0F + method_1315(this.watermark.statsY, var10);
            HotbarRenderer.method_857("stats-island", this.floatValue5, this.floatValue8, this.floatValue3, this.floatValue9);
            this.method_1334(var1, var13, var4, var2, var3);
            boolean var11 = this.hudLayout.isFlag();
            boolean var31;
            if (var11) {
                var31 = false;
            } else {
                var31 = this.method_1322(var13, var7, var4, var2, var3);
            }

            boolean var32;
            if (var11) {
                var32 = false;
            } else {
                var32 = this.method_1325(var13, var31, var9, var10, var2, var3);
            }

            if (var13 && var32 && !this.flag4 && this.intValue < 0) {
                float[] var12;
                if ((var12 = this.statusIcons.method_865(true))[0] != 0.0F || var12[1] != 0.0F) {
                    method_1329(this.watermark.statsX, method_1315(this.watermark.statsX, var9) + var12[0], var9);
                    method_1329(this.watermark.statsY, method_1315(this.watermark.statsY, var10) + var12[1], var10);
                }
            } else {
                this.statusIcons.method_865(false);
            }

            ComponentHost var35;
            float var36;
            boolean var37;
            IslandStats var38;
            label67: {
                this.floatValue5 = 10.0F + method_1315(this.watermark.statsX, var9);
                this.floatValue8 = 10.0F + method_1315(this.watermark.statsY, var10);
                ComponentHost var33;
                float var34;
                if (!var13) {
                    var34 = var4;
                    var33 = this.componentHost;
                } else {
                    if (var32) {
                        var38 = this;
                        var37 = true;
                        var36 = var4;
                        var35 = this.componentHost;
                        break label67;
                    }

                    var34 = var4;
                    var33 = this.componentHost;
                }

                var38 = this;
                var37 = false;
                var36 = var34;
                var35 = var33;
            }

            var35.method_2220(var36, var37, var38.flag4);
            if (this.flag4) {
                HealthBarAnimator.method_862(var1, var2, var3, this.ghost);
            }

            var1.method_440(this.animation.method_606());
            var1.method_441();
            var1.method_451(this.floatValue5, this.floatValue8 + (1.0F - this.animation.method_606()) * 5.0F);
            var1.method_491(var7, 0.0F, 0.0F);
            this.method_1314(var1, var40, var8);
            var1.method_435();
            var1.method_430();
            if (this.hudLayout.method_1472()) {
                this.hudLayout.method_1471(var1, var40);
            }
        }
    }

    private boolean method_1325(boolean var1, boolean var2, float var3, float var4, float var5, float var6) {
        float var7 = Minecraft.getScaledMouseX() * var5 / mc.displayWidth;
        float var12 = var6 - Minecraft.getScaledMouseY() * var6 / mc.displayHeight - 1.0F;
        boolean var9 = this.method_1318();
        boolean var10 = this.method_1312(var7, var12);
        IslandStats var11;
        if (var1 && var9) {
            if (!this.flag2 && var10 && !var2) {
                this.flag4 = true;
                this.floatValue7 = var7 - this.floatValue5;
                this.floatValue2 = var12 - this.floatValue8;
            }

            var11 = this;
        } else {
            this.flag4 = false;
            this.ghost = HealthBarAnimator.ghost;
            var11 = this;
        }

        var11.flag2 = var9;
        if (this.flag4) {
            this.ghost = HealthBarAnimator.method_861(
                var7 - this.floatValue7,
                var12 - this.floatValue2,
                this.floatValue3,
                this.floatValue9,
                var5,
                var6,
                10.0F,
                HotbarRenderer.method_856("stats-island")
            );
            method_1329(this.watermark.statsX, this.ghost.floatValue4() - 10.0F, var3);
            method_1329(this.watermark.statsY, this.ghost.floatValue2() - 10.0F, var4);
        }

        return var10;
    }

    private int method_1326(float var1, float var2) {
        var var6 = this;
        if (!(var2 < 0.0F) && !(var2 >= 22.0F)) {
            int var5 = 0;

            for (int var3 = 0; var3 < var6.list2.size(); var3 = ++var5) {
                if (var1 >= var6.floatValueArray2[var5] && var1 < var6.floatValueArray2[var5] + var6.floatValueArray[var5]) {
                    return var5;
                }
            }

            return -1;
        } else {
            return -1;
        }
    }

    private float method_1327(UiRenderer var1) {
        this.method_1313(var1);
        if (this.list2.isEmpty()) {
            return 16.0F;
        } else {
            int var3 = this.list2.size() - 1;
            return this.floatValueArray2[var3] + this.floatValueArray[var3] + 8.0F;
        }
    }

    private boolean method_1328() {
        return this.watermark.isToggled() && !this.watermark.stats.method_140();
    }

    private static void method_1329(NumberSetting var0, float var1, float var2) {
        if (!(var2 <= 0.0F)) {
            var0.setValue(Math.clamp(var1 / var2, 0.0F, 1.0F) * 100.0);
        }
    }

    public void method_1330() {
        this.list2.clear();
        this.list.clear();
        this.intValue = -1;
        this.flag = false;
        this.flag6 = false;
        this.flag4 = false;
        this.floatValue4 = 0.0F;
        this.longValue = 0L;
        this.pingTracker.method_1428();
        this.hudLayout.method_1474();
        HotbarRenderer.method_855("stats-island");
    }

    private float method_1331() {
        long var1 = System.nanoTime();
        float var4;
        if (this.longValue == 0L) {
            var4 = 16.0F;
        } else {
            var4 = Math.min((float)(var1 - this.longValue) / 1000000.0F, 100.0F);
        }

        this.longValue = var1;
        return var4;
    }

    private IslandStats.Sample method_1332(Watermark.Stat var1) {
        switch (var1) {
            case FPS:
                return new IslandStats.Sample(var1, String.valueOf(Minecraft.getDebugFPS()), "fps");
            case USERNAME:
                return new IslandStats.Sample(var1, Onyx.getUser(), "");
            case PING:
                return new IslandStats.Sample(var1, String.valueOf(this.method_1321()), "ms");
            case PACKET_LOSS:
                return new IslandStats.Sample(var1, String.valueOf(this.pingTracker.method_1429()), "% loss");
            case SERVER:
                return new IslandStats.Sample(var1, this.method_1308(), "");
            default:
                throw new MatchException(null, null);
        }
    }

    private int method_1333(float var1) {
        var var3 = this;
        int var2 = 0;
        int var6 = 0;

        for (int var5 = 0; var5 < var3.list2.size(); var5 = ++var6) {
            if (var6 != var3.intValue) {
                float var4 = var3.floatValueArray2[var6] + var3.floatValueArray[var6] / 2.0F;
                if (var1 > var4) {
                    var2++;
                }
            }
        }

        return var2;
    }

    private void method_1334(UiRenderer var1, boolean var2, float var3, float var4, float var5) {
        float var6 = Minecraft.getScaledMouseX() * var4 / mc.displayWidth;
        float var11 = var5 - Minecraft.getScaledMouseY() * var5 / mc.displayHeight - 1.0F;
        boolean var8 = Mouse.isButtonDown(1);
        boolean var9 = this.method_1318();
        IslandStats var10;
        if (!var2) {
            this.hudLayout.method_1474();
            var10 = this;
        } else if (var8 && !this.flag3 && this.method_1312(var6, var11)) {
            this.hudLayout.method_1468(var1, var6, var11, var4, var5);
            var10 = this;
        } else {
            if (var9 && !this.flag5 && this.hudLayout.isFlag() && !this.hudLayout.method_1475(var6, var11)) {
                this.hudLayout.method_1474();
            }

            var10 = this;
        }

        var10.flag3 = var8;
        this.flag5 = var9;
        this.hudLayout.method_1470(var3, var6, var11);
    }

    private void method_1335(float var1, boolean var2) {
        var var7 = this;
        this.pingTracker.method_1430();
        this.floatValue4 -= var1;
        if (var2 && (!(this.floatValue4 > 0.0F) || this.list2.isEmpty())) {
            if (this.intValue < 0) {
                this.floatValue4 = 250.0F;
                this.list2.clear();

                for (Watermark.Stat var4 : this.watermark.stats.getValue()) {
                    IslandStats.Sample var5;
                    if ((var5 = var7.method_1332(var4)) != null) {
                        var7.list2.add(var5);
                    }
                }
            }
        }
    }

    private static final class PingTracker implements IMinecraft {
        private static final int INT_201 = 20;
        private static final long LONG_2500000001 = 250000000L;
        private final AtomicInteger atomicInteger = new AtomicInteger();
        private final boolean[] flagArray = new boolean[20];
        private long longValue;
        private int intValue;
        private int intValue2;

        private void method_1428() {
            this.atomicInteger.set(0);
            this.longValue = 0L;
            this.intValue = 0;
            this.intValue2 = 0;
        }

        private int method_1429() {
            var var2 = this;
            if (this.intValue2 == 0) {
                return 0;
            } else {
                int var1 = 0;
                int var4 = 0;

                for (int var3 = 0; var3 < var2.intValue2; var3 = ++var4) {
                    if (!var2.flagArray[var4]) {
                        var1++;
                    }
                }

                return Math.round(var1 * 100.0F / var2.intValue2);
            }
        }

        private void method_1430() {
            var var5 = this;
            if (mc.getNetHandler() == null) {
                this.method_1428();
            } else {
                long var1 = System.nanoTime();
                if (this.longValue == 0L) {
                    this.longValue = var1;
                } else {
                    long var3;
                    if ((var3 = var1 - this.longValue) >= 250000000L) {
                        boolean var6;
                        if (this.atomicInteger.getAndSet(0) > 0) {
                            var6 = true;
                        } else {
                            var6 = false;
                        }

                        boolean var8 = var6;
                        int var10 = (int)Math.min(var3 / 250000000L, 20L);
                        this.longValue = var1;
                        int var9 = 0;

                        for (int var7 = 0; var7 < var10; var7 = var9) {
                            var5.flagArray[var5.intValue] = var8;
                            var5.intValue = (var5.intValue + 1) % 20;
                            int var10001 = var5.intValue2 + 1;
                            var9++;
                            var5.intValue2 = Math.min(var10001, 20);
                        }
                    }
                }
            }
        }

        private void method_1431() {
            this.atomicInteger.incrementAndGet();
        }
    }

    private record Sample(Watermark.Stat stat, String string2, String string) {
    }
}
