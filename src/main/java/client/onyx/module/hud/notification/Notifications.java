package client.onyx.module.hud.notification;

import client.onyx.Onyx;
import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.module.combat.BedBreaker;
import client.onyx.module.hud.island.Watermark;
import client.onyx.module.player.scaffold.Scaffold;
import client.onyx.nowplaying.Track;
import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easing;
import client.onyx.render.anim.Easings;
import client.onyx.render.ui.HealthBarAnimator;
import client.onyx.render.ui.HotbarRenderer;
import client.onyx.render.ui.PanelRenderer;
import client.onyx.render.ui.StatusIcons;
import client.onyx.render.ui.UiRenderer;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.ui.component.ComponentHost;
import client.onyx.ui.theme.ColorRoles;
import client.onyx.ui.theme.Palette;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.Typography;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.block.BlockBed;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Mouse;

public final class Notifications extends Module {
    private static final float FLOAT_8_01 = 8.0F;
    private static final float FLOAT_1_01 = 1.0F;
    private static final float FLOAT_34_01 = 34.0F;
    private static final int INT_N97066131 = -9706613;
    private static final float FLOAT_2_01 = 2.0F;
    private static final String STRING_CONST = "notifications";
    private static final float FLOAT_10_01 = 10.0F;
    private static final float FLOAT_240_01 = 240.0F;
    private static final float FLOAT_4_51 = 4.5F;
    private static final float FLOAT_10_02 = 10.0F;
    private static final float FLOAT_130_01 = 130.0F;
    private static final float FLOAT_14_01 = 14.0F;
    private static final float FLOAT_100_01 = 100.0F;
    private static final float FLOAT_9_01 = 9.0F;
    private static final int INT_31 = 3;
    private static final float FLOAT_5_51 = 5.5F;
    private static final float FLOAT_5_01 = 5.0F;
    private static final float FLOAT_16_01 = 16.0F;
    public final NumberSetting scale = new NumberSetting("Scale", 0.85, 0.5, 2.0, 0.05).method_147("x").describe("Popup size");
    public final NumberSetting hold = new NumberSetting("Hold", 3.0, 0.5, 10.0, 0.1).method_147("s").describe("How long each popup stays up");
    public final NumberSetting maxStack = new NumberSetting("Max stack", 5.0, 1.0, 10.0, 1.0).describe("How many popups can be on screen at once");
    public final BooleanSetting moduleToggles = new BooleanSetting("Module toggles", true).describe("Announce modules turning on and off");
    public final BooleanSetting scaffoldBlocks = new BooleanSetting("Scaffold blocks", true).describe("Warn when Scaffold is running low on blocks");
    public final NumberSetting lowBlocks = new NumberSetting("Low blocks", 32.0, 1.0, 128.0, 1.0)
        .describe("Count at which the warning goes up")
        .visibleWhen(this.scaffoldBlocks);
    public final BooleanSetting bedBreaker = new BooleanSetting("Bed breaker", true).describe("Announce when BedBreaker takes a bed down");
    public final BooleanSetting nowPlaying = new BooleanSetting("Now playing", true).describe("Announce whatever the system starts playing");
    public final BooleanSetting progressBar = new BooleanSetting("Progress bar", true).describe("Show the time each popup has left");
    public final BooleanSetting subtitle = new BooleanSetting("Subtitle", true).describe("Show the second line of each popup");
    public final NumberSetting positionX = new NumberSetting("Position X", 82.0, 0.0, 100.0, 0.01).visibleWhen(() -> false);
    public final NumberSetting positionY = new NumberSetting("Position Y", 8.0, 0.0, 100.0, 0.01).visibleWhen(() -> false);
    private final List<Notifications.Popup> list = new ArrayList<>();
    private String string = "";
    private final Set<Module> set = Collections.newSetFromMap(new IdentityHashMap<>());
    private final ComponentHost componentHost = new ComponentHost();
    private final StatusIcons statusIcons = new StatusIcons();
    private HealthBarAnimator.Ghost ghost;
    private long longValue;
    private float floatValue;
    private float floatValue2;
    private BlockPos blockPos;
    private boolean flag;
    private boolean flag2;
    private float floatValue3;
    private float floatValue4;
    private float floatValue5;
    private boolean flag3;
    private float floatValue6;
    private boolean flag4;

    public void method_1336(UiRenderer var1, float var2, float var3) {
        var var6 = this;
        float var4 = this.method_1353();
        boolean var14;
        if (this.isToggled() && mc.currentScreen instanceof GuiChat) {
            var14 = true;
        } else {
            var14 = false;
        }

        this.method_1341();
        this.method_1342();
        this.method_1356(var4);
        ArrayList var24;
        if ((var24 = new ArrayList<>(this.list)).isEmpty() && var14) {
            var24.add(this.method_1340());
        }

        Palette var7 = Theme.getPalette();
        float var8 = this.scale.method_151();
        float var9;
        this.floatValue3 = (var9 = this.method_1338(var1, var24)) * var8;
        this.floatValue = (var24.size() * 34.0F + Math.max(0, var24.size() - 1) * 5.0F) * var8;
        if (var24.isEmpty()) {
            HotbarRenderer.method_855("notifications");
            this.flag2 = this.method_1352();
        } else {
            float var10 = var2 - this.floatValue3 - 20.0F;
            float var11 = var3 - this.floatValue - 20.0F;
            this.floatValue4 = 10.0F + method_1344(this.positionX, var10);
            this.floatValue5 = 10.0F + method_1344(this.positionY, var11);
            HotbarRenderer.method_857("notifications", this.floatValue4, this.floatValue5, this.floatValue3, this.floatValue);
            boolean var12 = this.method_1339(var14, var10, var11, var2, var3);
            if (var14 && var12 && !this.flag) {
                float[] var13;
                if ((var13 = this.statusIcons.method_865(true))[0] != 0.0F || var13[1] != 0.0F) {
                    method_1348(this.positionX, method_1344(this.positionX, var10) + var13[0], var10);
                    method_1348(this.positionY, method_1344(this.positionY, var11) + var13[1], var11);
                }
            } else {
                this.statusIcons.method_865(false);
            }

            ComponentHost var17;
            float var18;
            boolean var19;
            Notifications var20;
            label54: {
                this.floatValue4 = 10.0F + method_1344(this.positionX, var10);
                this.floatValue5 = 10.0F + method_1344(this.positionY, var11);
                ComponentHost var15;
                float var16;
                if (!var14) {
                    var16 = var4;
                    var15 = this.componentHost;
                } else {
                    if (var12) {
                        var20 = this;
                        var19 = true;
                        var18 = var4;
                        var17 = this.componentHost;
                        break label54;
                    }

                    var16 = var4;
                    var15 = this.componentHost;
                }

                var20 = this;
                var19 = false;
                var18 = var16;
                var17 = var15;
            }

            var17.method_2220(var18, var19, var20.flag);
            if (this.flag) {
                HealthBarAnimator.method_862(var1, var2, var3, this.ghost);
            }

            PanelRenderer.method_602();
            boolean var21;
            if (this.positionY.getValue() > 50.0) {
                var21 = true;
            } else {
                var21 = false;
            }

            boolean var27 = var21;
            var1.method_441();
            var1.method_451(this.floatValue4, this.floatValue5);
            var1.method_491(var8, 0.0F, 0.0F);
            int var25 = 0;

            for (int var23 = 0; var23 < var24.size(); var23 = var25) {
                Notifications.Popup var26 = (Notifications.Popup)var24.get(var25);
                int var22;
                if (var27) {
                    var22 = var24.size() - 1 - var25;
                } else {
                    var22 = var25;
                }

                float var10004 = var22;
                var25++;
                var6.method_1350(var1, var7, var26, var10004 * 39.0F, var9);
            }

            var1.method_435();
        }
    }

    public boolean method_1337(double var1, double var3) {
        return var1 >= this.floatValue4 && var1 < this.floatValue4 + this.floatValue3 && var3 >= this.floatValue5 && var3 < this.floatValue5 + this.floatValue;
    }

    private float method_1338(UiRenderer var1, List<Notifications.Popup> var2) {
        var var5 = this;
        float var3 = 130.0F;

        Iterator var8;
        for (Iterator var6 = var8 = var2.iterator(); var6.hasNext(); var6 = var8) {
            Notifications.Popup var4 = (Notifications.Popup)var8.next();
            float var7 = var1.method_442(Typography.fontStyle9, var4.notification.string2());
            if (var5.subtitle.method_146() && var4.notification.string() != null) {
                var7 = Math.max(var7, var1.method_442(Typography.fontStyle13, var4.notification.string()));
            }

            var3 = Math.max(var3, 45.0F + var7);
        }

        return Math.min(var3, 240.0F);
    }

    private boolean method_1339(boolean var1, float var2, float var3, float var4, float var5) {
        float var6 = Minecraft.getScaledMouseX() * var4 / mc.displayWidth;
        float var11 = var5 - Minecraft.getScaledMouseY() * var5 / mc.displayHeight - 1.0F;
        boolean var8 = this.method_1352();
        boolean var9 = this.method_1337(var6, var11);
        Notifications var10;
        if (var1 && var8) {
            if (!this.flag2 && var9) {
                this.flag = true;
                this.floatValue6 = var6 - this.floatValue4;
                this.floatValue2 = var11 - this.floatValue5;
            }

            var10 = this;
        } else {
            this.flag = false;
            this.ghost = HealthBarAnimator.ghost;
            var10 = this;
        }

        var10.flag2 = var8;
        if (this.flag) {
            this.ghost = HealthBarAnimator.method_861(
                var6 - this.floatValue6,
                var11 - this.floatValue2,
                this.floatValue3,
                this.floatValue,
                var4,
                var5,
                10.0F,
                HotbarRenderer.method_856("notifications")
            );
            method_1348(this.positionX, this.ghost.floatValue4() - 10.0F, var2);
            method_1348(this.positionY, this.ghost.floatValue2() - 10.0F, var3);
        }

        return var9;
    }

    private Notifications.Popup method_1340() {
        Notifications.Popup var10000 = new Notifications.Popup(
            new Notification("Notifications", "Drag to move", NotificationType.INFO), this.hold.method_151() * 1000.0F
        );
        var10000.animation.method_607(1.0F);
        return var10000;
    }

    private void method_1341() {
        var var2 = this;
        if (this.moduleToggles.method_146() && Onyx.moduleManager != null) {
            Iterator var1 = Onyx.moduleManager.getArrayList().iterator();

            label47:
            while (true) {
                Iterator var5 = var1;

                while (var5.hasNext()) {
                    Module var8;
                    if ((var8 = (Module)var1.next()) == var2) {
                        continue label47;
                    }

                    if (var8 == Onyx.moduleManager.clickGUI) {
                        var5 = var1;
                    } else {
                        boolean var3 = var8.isToggled();
                        boolean var4 = var2.set.contains(var8);
                        if (var3 != var4) {
                            Notifications var7;
                            if (!var3) {
                                var2.set.remove(var8);
                                var7 = var2;
                            } else {
                                var2.set.add(var8);
                                var7 = var2;
                            }

                            if (var7.flag4) {
                                Notification var10001;
                                String var10003 = var8.getName();
                                String var10004;
                                boolean var10005;
                                if (var3) {
                                    var10004 = "Enabled";
                                    var10005 = var3;
                                } else {
                                    var10004 = "Disabled";
                                    var10005 = var3;
                                }

                                var10001 = new Notification(
                                    var10003, var10004, var10005 ? NotificationType.SUCCESS : NotificationType.INFO
                                );
                                var2.method_1355(var10001);
                            }
                            continue label47;
                        }

                        var5 = var1;
                    }
                }

                var2.flag4 = true;
                return;
            }
        } else {
            this.set.clear();
            this.flag4 = false;
        }
    }

    private void method_1342() {
        if (Onyx.moduleManager != null) {
            this.method_1346();
            this.method_1351();
            this.method_1347();
        }
    }

    private void method_1343() {
        this.flag3 = false;
        this.blockPos = null;
        this.string = "";
    }

    private static float method_1344(NumberSetting var0, float var1) {
        return var1 <= 0.0F ? 0.0F : (float)(var1 * var0.getValue() / 100.0);
    }

    public static void method_1345(String var0, NotificationType var1) {
        method_1349(var0, null, var1);
    }

    private void method_1346() {
        Scaffold var4 = Onyx.moduleManager.scaffold;
        if (this.scaffoldBlocks.method_146() && var4 != null && var4.isToggled()) {
            boolean var3;
            int var5;
            if ((var5 = var4.method_1663()) <= this.lowBlocks.method_149()) {
                var3 = true;
            } else {
                var3 = false;
            }

            if (var3 && !this.flag3) {
                Notification var10001;
                String var10004;
                int var10005;
                if (var5 <= 0) {
                    var10004 = "Out of blocks";
                    var10005 = var5;
                } else {
                    var10004 = var5 + " blocks left";
                    var10005 = var5;
                }

                var10001 = new Notification("Scaffold", var10004, var10005 <= 0 ? NotificationType.ERROR : NotificationType.WARNING);
                this.method_1355(var10001);
            }

            this.flag3 = var3;
        } else {
            this.flag3 = false;
        }
    }

    private void method_1347() {
        Watermark var2 = Onyx.moduleManager.watermark;
        if (this.nowPlaying.method_146() && var2 != null) {
            Track var3;
            if ((var3 = var2.method_1366()) != null && var3.string2() != null && !var3.string2().isEmpty()) {
                if (!var3.string2().equals(this.string)) {
                    this.string = var3.string2();
                    this.method_1355(
                        new Notification(
                            var3.string2(), var3.string() != null && !var3.string().isEmpty() ? var3.string() : "Now playing", NotificationType.INFO
                        )
                    );
                }
            } else {
                this.string = "";
            }
        } else {
            this.string = "";
        }
    }

    public Notifications() {
        super("Notifications", "Popups for module toggles and client events", Category.HUD);
        this.ghost = HealthBarAnimator.ghost;
    }

    private static void method_1348(NumberSetting var0, float var1, float var2) {
        if (!(var2 <= 0.0F)) {
            var0.setValue(Math.clamp(var1 / var2, 0.0F, 1.0F) * 100.0);
        }
    }

    public static void method_1349(String var0, String var1, NotificationType var2) {
        if (Onyx.moduleManager != null) {
            Notifications var4 = Onyx.moduleManager.notifications;
            if (Onyx.moduleManager.notifications != null && var4.isToggled()) {
                var4.method_1355(new Notification(var0, var1, var2));
            }
        }
    }

    private void method_1350(UiRenderer var1, Palette var2, Notifications.Popup var3, float var4, float var5) {
        float var6;
        if (!((var6 = var3.animation.method_606()) <= 0.001F)) {
            int var11;
            if (var3.notification.integer() == null) {
                var11 = var3.notification.notificationType().method_1427(var2);
            } else {
                var11 = var3.notification.integer();
            }

            int var8 = ColorRoles.method_182(3, var2);
            var1.method_440(var6);
            var1.method_441();
            var1.method_451((1.0F - var6) * 14.0F, var4);
            var1.method_485(0.0F, 0.0F, var5, 34.0F, 12.0F, 3);
            PanelRenderer.method_598(var1, 0.0F, 0.0F, var5, 34.0F, 12.0F, var8);
            var1.method_477(var3.notification.notificationType().method_1426(), 18.0F, 17.0F, 16.0F, var11);
            var4 = 35.0F;
            var6 = var5 - var4 - 10.0F;
            boolean var12;
            if (this.subtitle.method_146() && var3.notification.string() != null) {
                var12 = true;
            } else {
                var12 = false;
            }

            String var9 = var1.method_469(Typography.fontStyle9, var3.notification.string2(), var6);
            Notifications var14;
            if (!var12) {
                var1.method_432(Typography.fontStyle9, var9, var4, 17.0F, var2.intValue15());
                var14 = this;
            } else {
                var1.method_432(Typography.fontStyle9, var9, var4, 11.5F, var2.intValue15());
                var1.method_432(
                    Typography.fontStyle13, var1.method_469(Typography.fontStyle13, var3.notification.string(), var6), var4, 21.5F, var2.intValue7()
                );
                var14 = this;
            }

            if (var14.progressBar.method_146() && var3.floatValue2 > 0.0F) {
                float var16 = Math.clamp(var3.floatValue / var3.floatValue2, 0.0F, 1.0F);
                var1.method_479(10.0F, 29.0F, (var5 - 20.0F) * var16, 2.0F, Float.MAX_VALUE, Theme.method_180(var11, 0.85F));
            }

            this.componentHost.method_2219(var1, 0.0F, 0.0F, var5, 34.0F, 12.0F, var2.intValue15());
            var1.method_494(0.0F, 0.0F, var5, 34.0F, 12.0F, 1.0F, var2.intValue12());
            var1.method_435();
            var1.method_430();
        }
    }

    private void method_1351() {
        BedBreaker var2 = Onyx.moduleManager.bedBreaker;
        if (this.bedBreaker.method_146() && var2 != null && var2.isToggled() && mc.theWorld != null) {
            BlockPos var3;
            if ((var3 = var2.getBlockPos()) != null) {
                this.blockPos = var3;
            } else {
                if (this.blockPos != null && !(mc.theWorld.getBlockState(this.blockPos).getBlock() instanceof BlockBed)) {
                    this.method_1355(new Notification("BedBreaker", "Bed broken", NotificationType.SUCCESS, -9706613));
                }

                this.blockPos = null;
            }
        } else {
            this.blockPos = null;
        }
    }

    private boolean method_1352() {
        return Mouse.isButtonDown(0);
    }

    private float method_1353() {
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

    @Override
    public void onEnable() {
        this.flag4 = false;
        this.longValue = 0L;
    }

    public boolean method_1354() {
        return mc.thePlayer != null && mc.theWorld != null && this.isToggled();
    }

    @Override
    public void onDisable() {
        this.list.clear();
        this.set.clear();
        this.method_1343();
        this.flag4 = false;
        this.flag = false;
        HotbarRenderer.method_855("notifications");
    }

    private void method_1355(Notification var1) {
        var var4 = this;
        this.list.add(new Notifications.Popup(var1, this.hold.method_151() * 1000.0F));

        for (Notifications var2 = this; var2.list.size() > var4.maxStack.method_149(); var2 = var4) {
            var4.list.remove(0);
        }
    }

    private void method_1356(float var1) {
        this.list.removeIf(var1x -> {
            var1x.floatValue -= var1;
            boolean var3;
            if (var1x.floatValue <= 0.0F) {
                var3 = true;
            } else {
                var3 = false;
            }

            Animation var5;
            float var6;
            boolean var7;
            if (!var3) {
                Animation var4 = var1x.animation;
                var7 = var3;
                var6 = 1.0F;
                var5 = var4;
            } else {
                var7 = var3;
                var6 = 0.0F;
                var5 = var1x.animation;
            }

            Animation var10;
            float var11;
            float var12;
            boolean var13;
            if (!var7) {
                var13 = var3;
                var12 = 300.0F;
                var11 = var6;
                var10 = var5;
            } else {
                var13 = var3;
                var12 = 200.0F;
                var11 = var6;
                var10 = var5;
            }

            Animation var17;
            float var18;
            float var19;
            Easing var20;
            if (!var13) {
                var20 = Easings.easing7;
                var19 = var12;
                var18 = var11;
                var17 = var10;
            } else {
                var20 = Easings.easing;
                var19 = var12;
                var18 = var11;
                var17 = var10;
            }

            var17.method_603(var18, var19, var20);
            var1x.animation.method_605(var1);
            return var3 && var1x.animation.method_604() && var1x.animation.method_606() <= 0.0F;
        });
    }

    private static final class Popup {
        private final Animation animation = new Animation(0.0F);
        private final Notification notification;
        private final float floatValue2;
        private float floatValue;

        private Popup(Notification var1, float var2) {
            this.notification = var1;
            this.floatValue2 = var2;
            this.floatValue = var2;
        }
    }
}
