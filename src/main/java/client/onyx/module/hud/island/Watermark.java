package client.onyx.module.hud.island;

import client.onyx.Onyx;
import client.onyx.event.impl.PacketDirection;
import client.onyx.event.impl.PacketEvent;
import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.module.combat.BedBreaker;
import client.onyx.module.player.scaffold.Scaffold;
import client.onyx.nowplaying.NowPlaying;
import client.onyx.nowplaying.Track;
import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easing;
import client.onyx.render.anim.Easings;
import client.onyx.render.anim.Spring;
import client.onyx.render.font.FontStyle;
import client.onyx.render.ui.HealthBarAnimator;
import client.onyx.render.ui.HotbarRenderer;
import client.onyx.render.ui.PanelRenderer;
import client.onyx.render.ui.StatusIcons;
import client.onyx.render.ui.UiRenderer;
import client.onyx.render.util.FilterMode;
import client.onyx.setting.NamedEnum;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.EnumListSetting;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.ui.component.ComponentHost;
import client.onyx.ui.theme.ColorRoles;
import client.onyx.ui.theme.Palette;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.Typography;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

public final class Watermark extends Module {
    private static final ResourceLocation resourceLocation = new ResourceLocation("onyx", "textures/gui/logo.png");
    private static final Watermark.IslandState islandState3 = method_1370(Watermark.IslandMode.IDLE, "onyx", "", true);
    private static final Watermark.IslandState islandState = method_1370(Watermark.IslandMode.TOGGLE, "Drag to move", "", true);
    private static final float FLOAT_9_01 = 9.0F;
    private static final float FLOAT_6_01 = 6.0F;
    private static final float FLOAT_2_01 = 2.0F;
    private static final int INT_2561 = 256;
    private static final float FLOAT_2_51 = 2.5F;
    private static final float FLOAT_3_51 = 3.5F;
    private static final float FLOAT_2_02 = 2.0F;
    private static final float FLOAT_8_01 = 8.0F;
    private static final float FLOAT_7_01 = 7.0F;
    private static final float FLOAT_1_01 = 1.0F;
    private static final float FLOAT_6_02 = 6.0F;
    private static final float FLOAT_3_01 = 3.0F;
    private static final float FLOAT_6_03 = 6.0F;
    private static final float FLOAT_0_941 = 0.94F;
    private static final float FLOAT_5_01 = 5.0F;
    private static final int INT_31 = 3;
    private static final String STRING_CONST = "watermark";
    private static final float FLOAT_11_01 = 11.0F;
    private static final float FLOAT_0_51 = 0.5F;
    private static final float FLOAT_16_6956521 = 16.695652F;
    private static final float FLOAT_12_01 = 12.0F;
    private static final float FLOAT_22_01 = 22.0F;
    private static final float FLOAT_12_02 = 12.0F;
    private static final int INT_1841 = 184;
    private static final float FLOAT_8_02 = 8.0F;
    private static final float FLOAT_22_02 = 22.0F;
    private static final float FLOAT_10_01 = 10.0F;
    private static final float FLOAT_6_04 = 6.0F;
    private static final float FLOAT_12_03 = 12.0F;
    private static final float FLOAT_6_05 = 6.0F;
    private static final float FLOAT_90_01 = 90.0F;
    private static final int INT_32 = 3;
    private static final float FLOAT_3_02 = 3.0F;
    private static final float FLOAT_10_02 = 10.0F;
    private static final float FLOAT_5_02 = 5.0F;
    private static final float FLOAT_30_01 = 30.0F;
    private static final float FLOAT_5_03 = 5.0F;
    private static final float FLOAT_0_0071 = 0.007F;
    private static final float FLOAT_100_01 = 100.0F;
    private static final float FLOAT_2_03 = 2.0F;
    private static final float FLOAT_54_01 = 54.0F;
    private static final float FLOAT_4_51 = 4.5F;
    public NumberSetting scale = new NumberSetting("Scale", 1.0, 0.5, 2.0, 0.05).method_147("x").describe("Island size");
    public BooleanSetting moduleToggles = new BooleanSetting("Module toggles", true).describe("Announce modules turning on and off");
    public NumberSetting hold = new NumberSetting("Hold", 0.5, 0.3, 5.0, 0.1)
        .method_147("s")
        .describe("How long a notification stays expanded")
        .visibleWhen(this.moduleToggles);
    public BooleanSetting scaffoldBlocks = new BooleanSetting("Scaffold blocks", true).describe("Live block counter while Scaffold runs");
    public NumberSetting lowBlocks = new NumberSetting("Low blocks", 32.0, 0.0, 128.0, 1.0)
        .describe("Count below which the number turns red")
        .visibleWhen(this.scaffoldBlocks);
    public EnumListSetting<Watermark.Stat> stats = new EnumListSetting<>("Stats", Watermark.Stat.class, Watermark.Stat.FPS, Watermark.Stat.PING)
        .describe("Live client and connection readouts on their own island — drag them on the island itself (press T) to reorder");
    public BooleanSetting nowPlaying = new BooleanSetting("Now playing", true).describe("Show whatever's currently playing on the system (Windows/macOS only)");
    public NumberSetting positionX = new NumberSetting("Position X", 50.0, 0.0, 100.0, 0.01).visibleWhen(() -> false);
    public NumberSetting positionY = new NumberSetting("Position Y", 0.0, 0.0, 100.0, 0.01).visibleWhen(() -> false);
    public NumberSetting statsX = new NumberSetting("Stats X", 0.0, 0.0, 100.0, 0.01).visibleWhen(() -> false);
    public NumberSetting statsY = new NumberSetting("Stats Y", 0.0, 0.0, 100.0, 0.01).visibleWhen(() -> false);
    private final IslandStats islandStats = new IslandStats(this);
    private final NowPlaying nowPlaying2 = new NowPlaying();
    private final Animation animation2 = new Animation(0.0F);
    private final Animation animation6 = new Animation(22.0F);
    private final Animation animation7 = new Animation(22.0F);
    private final Animation animation4 = new Animation(1.0F);
    private final Animation animation = new Animation(1.0F);
    private final Animation animation5 = new Animation(1.0F);
    private final Animation animation3 = new Animation(1.0F);
    private final Spring spring2 = new Spring(Theme.getPalette().intValue12());
    private final Spring spring = new Spring(Theme.getPalette().intValue15());
    private final Spring spring3 = new Spring(Theme.getPalette().intValue31());
    private final ComponentHost componentHost = new ComponentHost();
    private final Set<Module> set = Collections.newSetFromMap(new IdentityHashMap<>());
    private Watermark.IslandState islandState4 = islandState3;
    private Watermark.IslandState islandState2 = islandState3;
    private float floatValue3 = 1.0F;
    private String string3 = "";
    private String string2 = "";
    private HealthBarAnimator.Ghost ghost;
    private final StatusIcons statusIcons;
    private float floatValue;
    private float floatValue2;
    private boolean flag;
    private float floatValue4;
    private Watermark.Gauge gauge;
    private Watermark.Toggle toggle;
    private long longValue;
    private boolean flag2;
    private Watermark.Music music;
    private String string;
    private float floatValue5;
    private float floatValue6;
    private float floatValue7;
    private float floatValue8;
    private float floatValue9;
    private boolean flag3;
    private boolean flag4;
    private float floatValue10;
    private int intValue;

    private void method_1361(UiRenderer var1, Palette var2, float var3, float var4, float var5) {
        var3 += 2.0F;
        float var8;
        float var10001 = var8 = var4 - 7.0F - 2.0F;
        var1.method_432(Typography.fontStyle8, this.islandState4.string2(), var3, 11.0F, var2.intValue7());
        var1.method_440(this.animation.method_606());
        var1.method_441();
        var1.method_451(0.0F, (1.0F - this.animation.method_606()) * 5.0F * this.floatValue3);
        var1.method_476(Typography.fontStyle9, this.islandState4.string(), var8, 11.0F, this.spring.method_617());
        var1.method_435();
        var1.method_430();
        float var9 = var10001 - var3;
        var5 = var5 - 8.0F - 3.0F;
        float var6 = var9 * this.animation5.method_606();
        var1.method_479(var3, var5, var9, 3.0F, Float.MAX_VALUE, var2.intValue11());
        if (var6 > 0.5F) {
            var1.method_479(var3, var5, var6, 3.0F, Float.MAX_VALUE, this.spring3.method_617());
        }
    }

    private void method_1362(UiRenderer var1, Palette var2, float var3, float var4) {
        float var5 = var4 - 6.0F;
        float var6;
        float var9 = var6 = var3 * this.islandState4.floatValue();
        var1.method_443(0.0F, var5, var3, 6.0F);
        var1.method_494(0.0F, 0.0F, var3, var4, Float.MAX_VALUE, 2.5F, var2.intValue11());
        var1.method_486();
        if (!(var9 <= 0.5F)) {
            var1.method_443(0.0F, var5, var6, 6.0F);
            var1.method_494(0.0F, 0.0F, var3, var4, Float.MAX_VALUE, 2.5F, var2.intValue31());
            var1.method_486();
        }
    }

    public IslandStats getIslandStats() {
        return this.islandStats;
    }

    private void method_1363(UiRenderer var1, float var2, Watermark.IslandState var3) {
        this.animation6.method_603(Math.max(22.0F, this.method_1381(var1, var3)), 200.0F, Easings.easing2);
        Animation var9 = this.animation7;
        Animation var5;
        float var6;
        if (var3.islandMode() != Watermark.IslandMode.GAUGE) {
            Animation var4 = var9;
            var6 = 22.0F;
            var5 = var4;
        } else {
            var6 = 30.0F;
            var5 = var9;
        }

        var5.method_603(var6, 200.0F, Easings.easing2);
        this.animation6.method_605(var2);
        this.animation7.method_605(var2);
    }

    private boolean method_1364() {
        return this.animation4.getFloatValue3() <= 0.0F;
    }

    private void method_1365(Palette var1, float var2) {
        this.spring2.method_613(var1.intValue12(), 400.0F, Easings.easing2);
        this.spring2.method_614(var2);
        boolean var4;
        if (this.music != null && this.music.flag()) {
            var4 = true;
        } else {
            var4 = false;
        }

        Spring var10000 = this.spring;
        Spring var7;
        int var8;
        if (!var4) {
            Spring var5 = this.spring;
            var8 = var1.intValue15();
            var7 = var5;
        } else {
            var8 = var1.intValue13();
            var7 = var10000;
        }

        var7.method_613(var8, 250.0F, Easings.easing2);
        this.spring.method_614(var2);
        var10000 = this.spring3;
        Spring var11;
        int var12;
        if (!var4) {
            Spring var9 = this.spring3;
            var12 = var1.intValue31();
            var11 = var9;
        } else {
            var12 = var1.intValue13();
            var11 = var10000;
        }

        var11.method_613(var12, 250.0F, Easings.easing2);
        this.spring3.method_614(var2);
    }

    public Track method_1366() {
        return this.toggle == null ? null : this.nowPlaying2.method_283();
    }

    private void method_1367(UiRenderer var1, Palette var2, int var3, float var4) {
        float var5 = var4 / 2.0F;
        float var10 = Math.min(16.695652F, var4 - 9.0F);
        float var7 = 7.0F + (16.695652F - var10) / 2.0F;
        float var8 = var5 - var10 / 2.0F;
        if (this.islandState4.resourceLocation() == null) {
            var1.method_477("\ue405", 15.347826F, var5, 12.0F, var2.intValue7());
        } else {
            var1.method_487(this.islandState4.resourceLocation(), var7, var8, var10, var10, 0.0F, 0.0F, 1.0F, 1.0F, 1, 1, -1, FilterMode.MINIFIED);
            var1.method_462(var7, var8, var10, var10, 3.5F, var3);
        }

        float var13 = 29.695652F;
        String var11 = var1.method_469(Typography.fontStyle9, this.string3, 90.0F);
        this.method_1374(var1, var2, var13, var5, var11, var4);
        float var12 = var13 + var1.method_442(Typography.fontStyle9, var11) + 6.0F;
        this.method_1382(var1, var2, var12, var5);
    }

    private void method_1368(float var1) {
        this.animation4.method_605(var1);
        if (method_1385(this.islandState2, this.islandState4)) {
            this.islandState4 = this.islandState2;
            if (this.animation4.getFloatValue3() < 1.0F) {
                this.animation4.method_603(1.0F, 200.0F, Easings.easing4);
            }
        } else if (this.animation2.method_606() <= 0.0F) {
            this.islandState4 = this.islandState2;
            this.animation4.method_607(1.0F);
        } else if (!(this.animation4.getFloatValue3() > 0.0F)) {
            if (this.animation4.method_604()) {
                this.islandState4 = this.islandState2;
                this.animation4.method_607(0.0F).method_603(1.0F, 200.0F, Easings.easing4);
                if (this.islandState4.islandMode() == Watermark.IslandMode.TOGGLE && !this.islandState4.string2().isEmpty()) {
                    this.method_1388(this.islandState4.flag());
                }
            }
        } else {
            this.animation4.method_603(0.0F, 50.0F, Easings.easing);
        }
    }

    private void method_1369(boolean var1, float var2) {
        if (var1) {
            this.islandState2 = islandState;
        } else {
            if (this.gauge != null) {
                this.floatValue8 -= var2;
                if (this.floatValue8 > 0.0F) {
                    Watermark.IslandMode var23 = Watermark.IslandMode.TOGGLE;
                    String var24 = this.gauge.string();
                    Watermark var6;
                    Watermark.IslandMode var7;
                    String var8;
                    String var9;
                    Watermark var10;
                    if (!this.gauge.flag()) {
                        String var5 = var24;
                        Watermark.IslandMode var4 = var23;
                        var10 = this;
                        var9 = "OFF";
                        var8 = var5;
                        var7 = var4;
                        var6 = this;
                    } else {
                        var10 = this;
                        var9 = "ON";
                        var8 = var24;
                        var7 = var23;
                        var6 = this;
                    }

                    var6.islandState2 = method_1370(var7, var8, var9, var10.gauge.flag());
                    return;
                }

                this.gauge = null;
            }

            if (this.music != null) {
                Watermark.IslandMode var22 = Watermark.IslandMode.GAUGE;
                String var10002 = this.music.string2();
                String var10003 = this.music.string();
                Watermark var15;
                Watermark.IslandMode var16;
                String var17;
                String var18;
                boolean var19;
                if (this.music.flag()) {
                    String var14 = var10003;
                    String var13 = var10002;
                    Watermark.IslandMode var12 = var22;
                    var19 = false;
                    var18 = var14;
                    var17 = var13;
                    var16 = var12;
                    var15 = this;
                } else {
                    var19 = true;
                    var18 = var10003;
                    var17 = var10002;
                    var16 = var22;
                    var15 = this;
                }

                var15.islandState2 = method_1370(var16, var17, var18, var19);
            } else if (this.toggle != null) {
                this.islandState2 = new Watermark.IslandState(
                    Watermark.IslandMode.MUSIC,
                    this.toggle.string(),
                    this.toggle.string2(),
                    true,
                    this.toggle.resourceLocation(),
                    this.toggle.floatValue(),
                    this.toggle.flag()
                );
            } else {
                this.islandState2 = islandState3;
            }
        }
    }

    private static Watermark.IslandState method_1370(Watermark.IslandMode var0, String var1, String var2, boolean var3) {
        return new Watermark.IslandState(var0, var1, var2, var3, null, 0.0F, false);
    }

    @Override
    public void onEnable() {
        this.flag2 = this.flag = false;
        this.longValue = 0L;
    }

    @Override
    public void onDisable() {
        this.islandStats.method_1330();
        this.flag = true;
        this.flag3 = false;
        this.gauge = null;
        this.floatValue8 = 0.0F;
        this.music = null;
        this.string = null;
        this.floatValue7 = 0.0F;
        this.intValue = 0;
        this.set.clear();
        this.islandState4 = islandState3;
        this.islandState2 = islandState3;
        this.animation4.method_607(1.0F);
        this.toggle = null;
        this.string3 = "";
        this.string2 = "";
        this.animation3.method_607(1.0F);
        this.nowPlaying2.method_285();
    }

    @EventHandler
    private void onPacket(PacketEvent var1) {
        if (var1.getPacketDirection() == PacketDirection.INCOMING) {
            this.islandStats.method_1319(var1.getPacket());
        }
    }

    private void method_1371() {
        boolean var2;
        if (this.isToggled() && this.nowPlaying.method_146() && this.nowPlaying2.method_284()) {
            var2 = true;
        } else {
            var2 = false;
        }

        if (!var2) {
            this.nowPlaying2.method_285();
            this.toggle = null;
        } else {
            this.nowPlaying2.method_286();
            Track var3;
            if ((var3 = this.nowPlaying2.method_283()) != null && var3.string2() != null && !var3.string2().isEmpty()) {
                this.toggle = new Watermark.Toggle(var3.string2(), var3.string(), var3.resourceLocation(), var3.method_269(), var3.flag());
            } else {
                this.toggle = null;
            }
        }
    }

    public void method_1372(UiRenderer var1, float var2, float var3) {
        float var40 = this.method_1377();
        boolean var14;
        if (this.isToggled() && mc.currentScreen instanceof GuiChat) {
            var14 = true;
        } else {
            var14 = false;
        }

        this.floatValue5 = (this.floatValue5 + var40) % 100000.0F;
        this.method_1390();
        this.method_1391(var40);
        this.method_1371();
        this.method_1369(var14, var40);
        this.method_1368(var40);
        this.method_1375(var40);
        Animation var41 = this.animation2;
        Animation var16;
        float var17;
        Watermark var18;
        if (!this.isToggled()) {
            Animation var15 = var41;
            var18 = this;
            var17 = 0.0F;
            var16 = var15;
        } else {
            var18 = this;
            var17 = 1.0F;
            var16 = var41;
        }

        Animation var21;
        float var22;
        float var23;
        Watermark var24;
        if (!var18.isToggled()) {
            var24 = this;
            var23 = 200.0F;
            var22 = var17;
            var21 = var16;
        } else {
            var24 = this;
            var23 = 300.0F;
            var22 = var17;
            var21 = var16;
        }

        Animation var28;
        float var29;
        float var30;
        Easing var31;
        if (!var24.isToggled()) {
            var31 = Easings.easing;
            var30 = var23;
            var29 = var22;
            var28 = var21;
        } else {
            var31 = Easings.easing7;
            var30 = var23;
            var29 = var22;
            var28 = var21;
        }

        var28.method_603(var29, var30, var31);
        this.animation2.method_605(var40);
        if (!this.isToggled() && this.animation2.method_604() && this.animation2.method_606() <= 0.0F) {
            this.method_1378();
            this.flag4 = this.method_1373();
        } else {
            PanelRenderer.method_602();
            Palette var6 = Theme.getPalette();
            this.method_1365(var6, var40);
            Watermark.IslandState var33;
            if (!this.method_1364()) {
                var33 = this.islandState2;
            } else {
                var33 = this.islandState4;
            }

            this.method_1363(var1, var40, var33);
            float var7 = this.scale.method_151() * (0.94F + 0.060000002F * this.animation2.method_606());
            float var8 = 36.695652F + this.animation6.method_606();
            float var9 = this.animation7.method_606();
            this.floatValue4 = var8 * var7;
            this.floatValue9 = var9 * var7;
            float var10 = var2 - this.floatValue4 - 20.0F;
            float var11 = var3 - this.floatValue9 - 20.0F;
            this.floatValue6 = 10.0F + method_1384(this.positionX, var10);
            this.floatValue2 = 10.0F + method_1384(this.positionY, var11);
            HotbarRenderer.method_857("watermark", this.floatValue6, this.floatValue2, this.floatValue4, this.floatValue9);
            boolean var12 = this.method_1380(var14, var10, var11, var2, var3);
            if (var14 && var12 && !this.flag3) {
                float[] var13;
                if ((var13 = this.statusIcons.method_865(true))[0] != 0.0F || var13[1] != 0.0F) {
                    method_1392(this.positionX, method_1384(this.positionX, var10) + var13[0], var10);
                    method_1392(this.positionY, method_1384(this.positionY, var11) + var13[1], var11);
                }
            } else {
                this.statusIcons.method_865(false);
            }

            ComponentHost var36;
            float var37;
            boolean var38;
            Watermark var39;
            label58: {
                this.floatValue6 = 10.0F + method_1384(this.positionX, var10);
                this.floatValue2 = 10.0F + method_1384(this.positionY, var11);
                ComponentHost var34;
                float var35;
                if (!var14) {
                    var35 = var40;
                    var34 = this.componentHost;
                } else {
                    if (var12) {
                        var39 = this;
                        var38 = true;
                        var37 = var40;
                        var36 = this.componentHost;
                        break label58;
                    }

                    var35 = var40;
                    var34 = this.componentHost;
                }

                var39 = this;
                var38 = false;
                var37 = var35;
                var36 = var34;
            }

            var36.method_2220(var37, var38, var39.flag3);
            if (this.flag3) {
                HealthBarAnimator.method_862(var1, var2, var3, this.ghost);
            }

            var1.method_440(this.animation2.method_606());
            var1.method_441();
            var1.method_451(this.floatValue6, this.floatValue2 + (1.0F - this.animation2.method_606()) * 5.0F);
            var1.method_491(var7, 0.0F, 0.0F);
            this.method_1376(var1, var6, var8, var9);
            var1.method_435();
            var1.method_430();
        }
    }

    private boolean method_1373() {
        return Mouse.isButtonDown(0);
    }

    private void method_1374(UiRenderer var1, Palette var2, float var3, float var4, String var5, float var6) {
        float var8;
        if ((var8 = this.animation3.method_606()) >= 0.999F) {
            var1.method_432(Typography.fontStyle9, var5, var3, var4, var2.intValue15());
        } else {
            var1.method_443(var3, 0.0F, 102.0F, var6);
            if (!this.string2.isEmpty()) {
                String var9 = var1.method_469(Typography.fontStyle9, this.string2, 90.0F);
                var1.method_440(1.0F - var8);
                var1.method_432(Typography.fontStyle9, var9, var3 - 12.0F * var8, var4, var2.intValue15());
                var1.method_430();
            }

            var1.method_440(var8);
            var1.method_432(Typography.fontStyle9, var5, var3 + 12.0F * (1.0F - var8), var4, var2.intValue15());
            var1.method_430();
            var1.method_486();
        }
    }

    private void method_1375(float var1) {
        if (this.islandState4.islandMode() != Watermark.IslandMode.MUSIC) {
            this.string3 = this.islandState4.string();
            this.animation3.method_607(1.0F);
        } else {
            if (!this.islandState4.string().equals(this.string3)) {
                this.string2 = this.string3;
                this.string3 = this.islandState4.string();
                this.animation3.method_607(0.0F).method_603(1.0F, 250.0F, Easings.easing7);
            }

            this.animation3.method_605(var1);
        }
    }

    private void method_1376(UiRenderer var1, Palette var2, float var3, float var4) {
        int var5 = ColorRoles.method_182(3, var2);
        var1.method_485(0.0F, 0.0F, var3, var4, Float.MAX_VALUE, 3);
        PanelRenderer.method_598(var1, 0.0F, 0.0F, var3, var4, Float.MAX_VALUE, var5);
        boolean var7;
        if (this.islandState4.islandMode() == Watermark.IslandMode.MUSIC) {
            var7 = true;
        } else {
            var7 = false;
        }

        if (!var7) {
            var1.method_487(
                resourceLocation, 7.0F, (var4 - 12.0F) / 2.0F, 16.695652F, 12.0F, 0.0F, 0.0F, 256.0F, 184.0F, 256, 184, var2.intValue31(), FilterMode.MINIFIED
            );
        }

        var1.method_440(this.animation4.method_606());
        var1.method_441();
        var1.method_451(0.0F, (1.0F - this.animation4.method_606()) * 6.0F);
        var1.method_443(0.0F, 0.0F, var3, var4);
        UiRenderer var8;
        if (var7) {
            this.method_1367(var1, var2, var5, var4);
            var8 = var1;
        } else {
            float var11 = 29.695652F;
            switch (this.islandState4.islandMode()) {
                case IDLE:
                    var1.method_432(Typography.fontStyle9, this.islandState4.string(), var11, var4 / 2.0F, var2.intValue15());
                    var8 = var1;
                    break;
                case TOGGLE:
                    this.method_1393(var1, var2, var11, var4 / 2.0F);
                    var8 = var1;
                    break;
                case GAUGE:
                    this.method_1361(var1, var2, var11, var3, var4);
                    var8 = var1;
                    break;
                case MUSIC:
                default:
                    var8 = var1;
            }
        }

        var8.method_486();
        var1.method_435();
        var1.method_430();
        this.componentHost.method_2219(var1, 0.0F, 0.0F, var3, var4, Float.MAX_VALUE, var2.intValue15());
        var1.method_494(0.0F, 0.0F, var3, var4, Float.MAX_VALUE, 1.0F, this.spring2.method_617());
        if (var7) {
            this.method_1362(var1, var2, var3, var4);
        }
    }

    private float method_1377() {
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

    private void method_1378() {
        this.flag = false;
        this.longValue = 0L;
        this.floatValue4 = 0.0F;
        this.floatValue9 = 0.0F;
        HotbarRenderer.method_855("watermark");
    }

    private static float method_1379(UiRenderer var0, String var1) {
        return 10.0F + var0.method_442(Typography.fontStyle8, var1);
    }

    private boolean method_1380(boolean var1, float var2, float var3, float var4, float var5) {
        float var6 = Minecraft.getScaledMouseX() * var4 / mc.displayWidth;
        float var11 = var5 - Minecraft.getScaledMouseY() * var5 / mc.displayHeight - 1.0F;
        boolean var8 = this.method_1373();
        boolean var9 = this.method_1383(var6, var11);
        Watermark var10;
        if (var1 && var8) {
            if (!this.flag4 && var9) {
                this.flag3 = true;
                this.floatValue10 = var6 - this.floatValue6;
                this.floatValue = var11 - this.floatValue2;
            }

            var10 = this;
        } else {
            this.flag3 = false;
            this.ghost = HealthBarAnimator.ghost;
            var10 = this;
        }

        var10.flag4 = var8;
        if (this.flag3) {
            this.ghost = HealthBarAnimator.method_861(
                var6 - this.floatValue10,
                var11 - this.floatValue,
                this.floatValue4,
                this.floatValue9,
                var4,
                var5,
                10.0F,
                HotbarRenderer.method_856("watermark")
            );
            method_1392(this.positionX, this.ghost.floatValue4() - 10.0F, var2);
            method_1392(this.positionY, this.ghost.floatValue2() - 10.0F, var3);
        }

        return var9;
    }

    private float method_1381(UiRenderer var1, Watermark.IslandState var2) {
        switch (var2.islandMode()) {
            case IDLE:
                return var1.method_442(Typography.fontStyle9, var2.string());
            case TOGGLE:
                float var3 = Math.min(var1.method_442(Typography.fontStyle9, var2.string()), 90.0F);
                if (var2.string2().isEmpty()) {
                    return var3;
                }

                return var3 + 6.0F + method_1379(var1, var2.string2());
            case GAUGE:
                return Math.max(54.0F, var1.method_442(Typography.fontStyle8, var2.string2()) + 8.0F + var1.method_442(Typography.fontStyle9, var2.string()))
                    + 4.0F;
            case MUSIC:
                return Math.min(var1.method_442(Typography.fontStyle9, var2.string()), 90.0F) + 6.0F + 10.0F;
            default:
                throw new MatchException(null, null);
        }
    }

    private void method_1382(UiRenderer var1, Palette var2, float var3, float var4) {
        var var6 = this;
        var3 = var3;
        int var5 = 0;

        for (int var9 = 0; var9 < 3; var9 = var5) {
            float var10 = var5 * 2.1F;
            float var8;
            if (var6.islandState4.flag2()) {
                var8 = 0.5F + 0.5F * (float)Math.sin(var6.floatValue5 * 0.007F + var10);
            } else {
                var8 = 0.1F;
            }

            float var11 = 3.0F + 6.0F * var8;
            var1.method_479(var3, var4 - var11 / 2.0F, 2.0F, var11, Float.MAX_VALUE, var2.intValue31());
            var5++;
            var3 += 4.0F;
        }
    }

    public boolean method_1383(double var1, double var3) {
        return var1 >= this.floatValue6 && var1 < this.floatValue6 + this.floatValue4 && var3 >= this.floatValue2 && var3 < this.floatValue2 + this.floatValue9;
    }

    private static float method_1384(NumberSetting var0, float var1) {
        return var1 <= 0.0F ? 0.0F : (float)(var1 * var0.getValue() / 100.0);
    }

    private static boolean method_1385(Watermark.IslandState var0, Watermark.IslandState var1) {
        return var0.equals(var1)
            || var0.islandMode() == Watermark.IslandMode.GAUGE && var1.islandMode() == Watermark.IslandMode.GAUGE
            || var0.islandMode() == Watermark.IslandMode.MUSIC && var1.islandMode() == Watermark.IslandMode.MUSIC;
    }

    private Watermark.Music method_1386() {
        BedBreaker var4 = Onyx.moduleManager.bedBreaker;
        if (!Onyx.moduleManager.bedBreaker.isToggled()) {
            return null;
        } else {
            BlockPos var5;
            if ((var5 = var4.getBlockPos3()) != null && mc.theWorld != null) {
                float var2;
                int var3 = Math.round((var2 = Math.clamp(mc.playerController.getCurBlockDamage(), 0.0F, 1.0F)) * 100.0F);
                String var6 = mc.theWorld.getBlockState(var5).getBlock().getLocalizedName();
                return new Watermark.Music(var6, var3 + "%", var2, (float)var3, false);
            } else {
                return new Watermark.Music("Bed", "Idle", 0.0F, 0.0F, false);
            }
        }
    }

    public boolean method_1387() {
        return mc.thePlayer != null && mc.theWorld != null && (this.isToggled() || this.flag);
    }

    private void method_1388(boolean var1) {
        Palette var7 = Theme.getPalette();
        Spring var10000 = this.spring2;
        Spring var5;
        int var6;
        if (!var1) {
            Spring var3 = this.spring2;
            var6 = var7.intValue13();
            var5 = var3;
        } else {
            var6 = var7.intValue31();
            var5 = var10000;
        }

        var5.method_618(var6);
    }

    private Watermark.Music method_1389() {
        Scaffold var2 = Onyx.moduleManager.scaffold;
        if (this.scaffoldBlocks.method_146() && var2.isToggled()) {
            int var3;
            if ((var3 = var2.method_1663()) > this.intValue) {
                this.intValue = var3;
            }

            Watermark.Music var10000;
            String var10003 = String.valueOf(var3);
            float var10004;
            int var10005;
            if (this.intValue > 0) {
                var10004 = Math.clamp((float)var3 / this.intValue, 0.0F, 1.0F);
                var10005 = var3;
            } else {
                var10004 = 0.0F;
                var10005 = var3;
            }

            var10000 = new Watermark.Music("Blocks", var10003, var10004, (float)var10005, var3 <= this.lowBlocks.method_149());
            return var10000;
        } else {
            this.intValue = 0;
            return null;
        }
    }

    public Watermark() {
        super("Watermark", "Dynamic island that reacts to what the client is doing", Category.HUD);
        this.ghost = HealthBarAnimator.ghost;
        this.statusIcons = new StatusIcons();
    }

    private void method_1390() {
        var var2 = this;
        if (this.isToggled()) {
            Iterator var1 = Onyx.moduleManager.getArrayList().iterator();

            label39:
            while (true) {
                Iterator var4 = var1;

                while (var4.hasNext()) {
                    Module var7;
                    if ((var7 = (Module)var1.next()) == var2) {
                        continue label39;
                    }

                    if (var7 == Onyx.moduleManager.clickGUI) {
                        var4 = var1;
                    } else {
                        boolean var3;
                        if ((var3 = var7.isToggled()) == var2.set.contains(var7)) {
                            var4 = var1;
                        } else {
                            Watermark var6;
                            if (!var3) {
                                var2.set.remove(var7);
                                var6 = var2;
                            } else {
                                var2.set.add(var7);
                                var6 = var2;
                            }

                            if (!var6.flag2) {
                                continue label39;
                            }

                            if (!var2.moduleToggles.method_146()) {
                                var4 = var1;
                            } else {
                                var2.gauge = new Watermark.Gauge(var7.getName(), var3);
                                var2.floatValue8 = var2.hold.method_151() * 1000.0F;
                                var4 = var1;
                            }
                        }
                    }
                }

                var2.flag2 = true;
                return;
            }
        }
    }

    private void method_1391(float var1) {
        this.music = this.method_1389();
        if (this.music == null) {
            this.music = this.method_1386();
        }

        String var3;
        if (this.music == null) {
            var3 = null;
        } else {
            var3 = this.music.string2();
        }

        if (!Objects.equals(var3, this.string) && this.animation.method_604()) {
            Watermark var5;
            float var6;
            label36: {
                Watermark var4;
                if (this.music == null) {
                    var4 = this;
                } else {
                    if (this.music.floatValue2() < this.floatValue7) {
                        var6 = -1.0F;
                        var5 = this;
                        break label36;
                    }

                    var4 = this;
                }

                var6 = 1.0F;
                var5 = var4;
            }

            var5.floatValue3 = var6;
            this.animation.method_607(0.0F).method_603(1.0F, 100.0F, Easings.easing7);
        }

        this.string = var3;
        Watermark var8;
        float var9;
        if (this.music != null) {
            var9 = this.music.floatValue2();
            var8 = this;
        } else {
            var9 = 0.0F;
            var8 = this;
        }

        var8.floatValue7 = var9;
        Animation var11;
        float var12;
        if (this.music != null) {
            Animation var10 = this.animation5;
            var12 = this.music.floatValue();
            var11 = var10;
        } else {
            var12 = 0.0F;
            var11 = this.animation5;
        }

        var11.method_603(var12, 250.0F, Easings.easing2);
        this.animation5.method_605(var1);
        this.animation.method_605(var1);
    }

    private static void method_1392(NumberSetting var0, float var1, float var2) {
        if (!(var2 <= 0.0F)) {
            var0.setValue(Math.clamp(var1 / var2, 0.0F, 1.0F) * 100.0);
        }
    }

    private void method_1393(UiRenderer var1, Palette var2, float var3, float var4) {
        String var32 = var1.method_469(Typography.fontStyle9, this.islandState4.string(), 90.0F);
        var1.method_432(Typography.fontStyle9, var32, var3, var4, var2.intValue15());
        if (!this.islandState4.string2().isEmpty()) {
            var3 = var3 + var1.method_442(Typography.fontStyle9, var32) + 6.0F;
            float var33 = method_1379(var1, this.islandState4.string2());
            float var10002 = var4 - 6.0F;
            UiRenderer var13;
            float var14;
            float var15;
            float var16;
            float var17;
            float var18;
            int var19;
            if (!this.islandState4.flag()) {
                float var11 = Float.MAX_VALUE;
                float var10 = 12.0F;
                float var8 = var10002;
                var19 = var2.intValue11();
                var18 = var11;
                var17 = var10;
                var16 = var33;
                var15 = var8;
                var14 = var3;
                var13 = var1;
            } else {
                var19 = var2.intValue34();
                var18 = Float.MAX_VALUE;
                var17 = 12.0F;
                var16 = var33;
                var15 = var10002;
                var14 = var3;
                var13 = var1;
            }

            var13.method_479(var14, var15, var16, var17, var18, var19);
            FontStyle var10001 = Typography.fontStyle8;
            String var35 = this.islandState4.string2();
            float var10003 = var3 + var33 / 2.0F;
            UiRenderer var26;
            FontStyle var27;
            String var28;
            float var29;
            float var30;
            int var31;
            if (!this.islandState4.flag()) {
                float var23 = var10003;
                String var22 = var35;
                FontStyle var21 = var10001;
                var31 = var2.intValue7();
                var30 = var4;
                var29 = var23;
                var28 = var22;
                var27 = var21;
                var26 = var1;
            } else {
                var31 = var2.intValue17();
                var30 = var4;
                var29 = var10003;
                var28 = var35;
                var27 = var10001;
                var26 = var1;
            }

            var26.method_484(var27, var28, var29, var30, var31);
        }
    }

    private record Gauge(String string, boolean flag) {
    }

    private static enum IslandMode {
        IDLE,
        TOGGLE,
        GAUGE,
        MUSIC;
    }

    private record IslandState(
        Watermark.IslandMode islandMode, String string, String string2, boolean flag, ResourceLocation resourceLocation, float floatValue, boolean flag2
    ) {
    }

    private record Music(String string, String string2, float floatValue, float floatValue2, boolean flag) {
    }

    public static enum Stat implements NamedEnum {
        FPS("FPS"),
        USERNAME("Username"),
        PING("Ping"),
        PACKET_LOSS("Packet loss"),
        SERVER("Server");

        private final String string;

        @Override
        public String getDisplayName() {
            return this.string;
        }

        private Stat(String var3) {
            this.string = var3;
        }
    }

    private record Toggle(String string, String string2, ResourceLocation resourceLocation, float floatValue, boolean flag) {
    }
}
