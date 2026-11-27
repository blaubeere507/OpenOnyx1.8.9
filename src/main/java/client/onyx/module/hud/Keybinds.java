package client.onyx.module.hud;

import client.onyx.Onyx;
import client.onyx.module.Category;
import client.onyx.module.Module;
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
import client.onyx.ui.theme.ColorRoles;
import client.onyx.ui.theme.Palette;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.Typography;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import org.lwjgl.input.Mouse;

public final class Keybinds extends Module {
    private static final float FLOAT_31_01 = 31.0F;
    private static final float FLOAT_100_01 = 100.0F;
    private static final float FLOAT_1_01 = 1.0F;
    private static final float FLOAT_16_01 = 16.0F;
    private static final float FLOAT_20_01 = 20.0F;
    private static final float FLOAT_6_01 = 6.0F;
    private static final float FLOAT_1_02 = 1.0F;
    private static final float FLOAT_12_01 = 12.0F;
    private static final int INT_31 = 3;
    private static final float FLOAT_6_02 = 6.0F;
    private static final float FLOAT_4_01 = 4.0F;
    private static final float FLOAT_0_941 = 0.94F;
    private static final float FLOAT_10_01 = 10.0F;
    private static final float FLOAT_5_01 = 5.0F;
    private static final float FLOAT_5_02 = 5.0F;
    private static final float FLOAT_20_02 = 20.0F;
    private static final float FLOAT_5_03 = 5.0F;
    private static final float FLOAT_100_02 = 100.0F;
    private static final float FLOAT_3_01 = 3.0F;
    private static final String STRING_CONST = "keybinds";
    public NumberSetting scale = new NumberSetting("Scale", 0.85, 0.5, 2.0, 0.05).method_147("x").describe("Keybind list size");
    public NumberSetting positionX = new NumberSetting("Position X", 6.78, 0.0, 100.0, 0.01).visibleWhen(() -> false);
    public NumberSetting positionY = new NumberSetting("Position Y", 18.79, 0.0, 100.0, 0.01).visibleWhen(() -> false);
    private final Map<Module, Keybinds.Row> map = new IdentityHashMap<>();
    private final Animation animation = new Animation(0.0F);
    private final Animation animation3 = new Animation(38.0F);
    private final Animation animation2 = new Animation(57.0F);
    private final ComponentHost componentHost = new ComponentHost();
    private HealthBarAnimator.Ghost ghost;
    private final StatusIcons statusIcons;
    private boolean flag;
    private float floatValue;
    private float floatValue2;
    private float floatValue3;
    private boolean flag2;
    private boolean flag3;
    private float floatValue4;
    private long longValue;
    private float floatValue5;
    private float floatValue6;

    private float method_1395() {
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

    public boolean method_1396(double var1, double var3) {
        return var1 >= this.floatValue4 && var1 < this.floatValue4 + this.floatValue5 && var3 >= this.floatValue2 && var3 < this.floatValue2 + this.floatValue3;
    }

    @Override
    public void onEnable() {
        this.flag = false;
        this.longValue = 0L;
    }

    private static String method_1397(String var0) {
        String var1;
        String[] var8;
        if ((var8 = (var1 = var0.trim().toUpperCase(Locale.ROOT)).split("\\s+")).length > 1) {
            StringBuilder var3 = new StringBuilder(var8.length);
            String[] var9;
            int var4 = (var9 = var8).length;
            int var5 = 0;

            for (int var7 = 0; var7 < var4; var7 = ++var5) {
                String var6;
                if (!(var6 = var9[var5]).isEmpty()) {
                    var3.append(var6.charAt(0));
                }
            }

            return var3.substring(0, Math.min(var3.length(), 3));
        } else {
            return var1.substring(0, Math.min(var1.length(), 3));
        }
    }

    public void method_1398(UiRenderer var1, float var2, float var3) {
        float var4 = this.method_1395();
        boolean var12;
        if (this.isToggled() && mc.currentScreen instanceof GuiChat) {
            var12 = true;
        } else {
            var12 = false;
        }

        boolean var36 = this.method_1407(var12, var4);
        Animation var14;
        float var15;
        boolean var16;
        if (!var36) {
            Animation var13 = this.animation;
            var16 = var36;
            var15 = 0.0F;
            var14 = var13;
        } else {
            var16 = var36;
            var15 = 1.0F;
            var14 = this.animation;
        }

        Animation var19;
        float var20;
        float var21;
        boolean var22;
        if (!var16) {
            var22 = var36;
            var21 = 200.0F;
            var20 = var15;
            var19 = var14;
        } else {
            var22 = var36;
            var21 = 300.0F;
            var20 = var15;
            var19 = var14;
        }

        Animation var26;
        float var27;
        float var28;
        Easing var29;
        if (!var22) {
            var29 = Easings.easing;
            var28 = var21;
            var27 = var20;
            var26 = var19;
        } else {
            var29 = Easings.easing7;
            var28 = var21;
            var27 = var20;
            var26 = var19;
        }

        var26.method_603(var27, var28, var29);
        this.animation.method_605(var4);
        if (this.map.isEmpty() && this.animation.method_604() && this.animation.method_606() <= 0.0F) {
            this.method_1406();
            this.floatValue5 = 0.0F;
            this.floatValue3 = 0.0F;
            this.flag2 = this.method_1401();
        } else {
            Palette var37 = Theme.getPalette();
            this.method_1402(var1, var4);
            float var7 = this.scale.method_151() * (0.94F + 0.060000002F * this.animation.method_606());
            this.floatValue5 = this.animation3.method_606() * var7;
            this.floatValue3 = this.animation2.method_606() * var7;
            float var8 = var2 - this.floatValue5 - 20.0F;
            float var9 = var3 - this.floatValue3 - 20.0F;
            this.floatValue4 = 10.0F + method_1400(this.positionX, var8);
            this.floatValue2 = 10.0F + method_1400(this.positionY, var9);
            HotbarRenderer.method_857("keybinds", this.floatValue4, this.floatValue2, this.floatValue5, this.floatValue3);
            boolean var10 = this.method_1403(var12, var8, var9, var2, var3);
            if (var12 && var10 && !this.flag3) {
                float[] var11;
                if ((var11 = this.statusIcons.method_865(true))[0] != 0.0F || var11[1] != 0.0F) {
                    method_1405(this.positionX, method_1400(this.positionX, var8) + var11[0], var8);
                    method_1405(this.positionY, method_1400(this.positionY, var9) + var11[1], var9);
                }
            } else {
                this.statusIcons.method_865(false);
            }

            ComponentHost var32;
            float var33;
            boolean var34;
            Keybinds var35;
            label55: {
                this.floatValue4 = 10.0F + method_1400(this.positionX, var8);
                this.floatValue2 = 10.0F + method_1400(this.positionY, var9);
                ComponentHost var30;
                float var31;
                if (!var12) {
                    var31 = var4;
                    var30 = this.componentHost;
                } else {
                    if (var10) {
                        var35 = this;
                        var34 = true;
                        var33 = var4;
                        var32 = this.componentHost;
                        break label55;
                    }

                    var31 = var4;
                    var30 = this.componentHost;
                }

                var35 = this;
                var34 = false;
                var33 = var31;
                var32 = var30;
            }

            var32.method_2220(var33, var34, var35.flag3);
            if (this.flag3) {
                HealthBarAnimator.method_862(var1, var2, var3, this.ghost);
            }

            PanelRenderer.method_602();
            var1.method_440(this.animation.method_606());
            var1.method_441();
            var1.method_451(this.floatValue4, this.floatValue2 + (1.0F - this.animation.method_606()) * 5.0F);
            var1.method_491(var7, 0.0F, 0.0F);
            this.method_1404(var1, var37);
            var1.method_435();
            var1.method_430();
            this.method_1406();
        }
    }

    public boolean method_1399() {
        return mc.thePlayer != null && mc.theWorld != null && (this.isToggled() || this.flag);
    }

    private static float method_1400(NumberSetting var0, float var1) {
        return var1 <= 0.0F ? 0.0F : (float)(var1 * var0.getValue() / 100.0);
    }

    public Keybinds() {
        super("Keybinds", "Shows enabled modules with assigned keybinds", Category.HUD);
        this.ghost = HealthBarAnimator.ghost;
        this.statusIcons = new StatusIcons();
    }

    @Override
    public void onDisable() {
        this.flag = true;
        this.flag3 = false;
    }

    private boolean method_1401() {
        return Mouse.isButtonDown(0);
    }

    private void method_1402(UiRenderer var1, float var2) {
        var var3 = this;
        float var4 = 29.0F + var1.method_442(Typography.fontStyle9, "Keybinds");
        Iterator var8;
        Iterator var7 = var8 = Onyx.moduleManager.getArrayList().iterator();

        while (var7.hasNext()) {
            Module var5 = (Module)var8.next();
            if (var3.map.get(var5) == null) {
                var7 = var8;
            } else {
                float var6 = Math.min(var1.method_442(Typography.fontStyle8, var5.getName()), 100.0F);
                var4 = Math.max(var4, 38.0F + var6);
                var7 = var8;
            }
        }

        int var9 = var3.map.size();
        float var10 = 37.0F + var9 * 20.0F + Math.max(0, var9 - 1) * 4.0F;
        var3.animation3.method_603(var4, 300.0F, Easings.easing2);
        var3.animation2.method_603(var10, 300.0F, Easings.easing2);
        var3.animation3.method_605(var2);
        var3.animation2.method_605(var2);
    }

    private boolean method_1403(boolean var1, float var2, float var3, float var4, float var5) {
        float var6 = Minecraft.getScaledMouseX() * var4 / mc.displayWidth;
        float var11 = var5 - Minecraft.getScaledMouseY() * var5 / mc.displayHeight - 1.0F;
        boolean var8 = this.method_1401();
        boolean var9 = this.method_1396(var6, var11);
        Keybinds var10;
        if (var1 && var8) {
            if (!this.flag2 && var9) {
                this.flag3 = true;
                this.floatValue = var6 - this.floatValue4;
                this.floatValue6 = var11 - this.floatValue2;
            }

            var10 = this;
        } else {
            this.flag3 = false;
            this.ghost = HealthBarAnimator.ghost;
            var10 = this;
        }

        var10.flag2 = var8;
        if (this.flag3) {
            this.ghost = HealthBarAnimator.method_861(
                var6 - this.floatValue, var11 - this.floatValue6, this.floatValue5, this.floatValue3, var4, var5, 10.0F, HotbarRenderer.method_856("keybinds")
            );
            method_1405(this.positionX, this.ghost.floatValue4() - 10.0F, var2);
            method_1405(this.positionY, this.ghost.floatValue2() - 10.0F, var3);
        }

        return var9;
    }

    private void method_1404(UiRenderer var1, Palette var2) {
        var var3 = this;
        float var5 = this.animation3.method_606();
        float var4 = this.animation2.method_606();
        int var11 = ColorRoles.method_182(3, var2);
        var1.method_485(0.0F, 0.0F, var5, var4, 12.0F, 3);
        PanelRenderer.method_598(var1, 0.0F, 0.0F, var5, var4, 12.0F, var11);
        float var12 = 14.0F;
        var1.method_477("\ue312", 12.0F, var12, 12.0F, var2.intValue31());
        var1.method_432(Typography.fontStyle9, "Keybinds", 23.0F, var12, var2.intValue15());
        var1.method_434(6.0F, 25.0F, var5 - 12.0F, 1.0F, Theme.method_180(var2.intValue12(), 0.7F));
        float var13 = 31.0F;
        Iterator var6;
        Iterator var10 = var6 = Onyx.moduleManager.getArrayList().iterator();

        while (var10.hasNext()) {
            Module var7 = (Module)var6.next();
            Keybinds.Row var8;
            if ((var8 = var3.map.get(var7)) == null) {
                var10 = var6;
            } else {
                float var9 = var8.animation.method_606();
                var1.method_440(var9);
                var1.method_441();
                var1.method_451((1.0F - var9) * 5.0F, 0.0F);
                var1.method_479(6.0F, var13, 20.0F, 20.0F, 4.0F, var2.intValue5());
                var1.method_484(Typography.fontStyle8, method_1397(var8.string), 16.0F, var13 + 10.0F, var2.intValue30());
                String var14 = var1.method_469(Typography.fontStyle8, var7.getName(), 100.0F);
                var1.method_432(Typography.fontStyle8, var14, 32.0F, var13 + 10.0F, var2.intValue15());
                var1.method_435();
                var1.method_430();
                var13 += 24.0F;
                var10 = var6;
            }
        }

        var3.componentHost.method_2219(var1, 0.0F, 0.0F, var5, var4, 12.0F, var2.intValue15());
        var1.method_494(0.0F, 0.0F, var5, var4, 12.0F, 1.0F, var2.intValue12());
    }

    private static void method_1405(NumberSetting var0, float var1, float var2) {
        if (!(var2 <= 0.0F)) {
            var0.setValue(Math.clamp(var1 / var2, 0.0F, 1.0F) * 100.0);
        }
    }

    private void method_1406() {
        if (!this.isToggled() && this.map.isEmpty() && this.animation.method_604() && !(this.animation.method_606() > 0.0F)) {
            this.flag = false;
            this.longValue = 0L;
            this.floatValue5 = 0.0F;
            this.floatValue3 = 0.0F;
            HotbarRenderer.method_855("keybinds");
        }
    }

    private boolean method_1407(boolean var1, float var2) {
        var var5 = this;
        boolean var7 = false;
        Iterator var4 = Onyx.moduleManager.getArrayList().iterator();

        label82:
        while (true) {
            Iterator var8 = var4;

            while (var8.hasNext()) {
                Module var27;
                if ((var27 = (Module)var4.next()) == var5) {
                    continue label82;
                }

                if (var27 == Onyx.moduleManager.clickGUI) {
                    var8 = var4;
                } else {
                    boolean var9;
                    if (!var5.isToggled() || !var27.getBind().method_163() || !var1 && !var27.isToggled()) {
                        var9 = false;
                    } else {
                        var9 = true;
                    }

                    Keybinds.Row var3 = var5.map.get(var27);
                    if (var9 && var3 == null) {
                        var3 = new Keybinds.Row(var27.getBind().method_164());
                        var5.map.put(var27, var3);
                    }

                    if (var3 != null) {
                        if (var27.getBind().method_163()) {
                            var3.string = var27.getBind().method_164();
                        }

                        Animation var11;
                        float var12;
                        boolean var13;
                        if (!var9) {
                            Animation var10 = var3.animation;
                            var13 = var9;
                            var12 = 0.0F;
                            var11 = var10;
                        } else {
                            var13 = var9;
                            var12 = 1.0F;
                            var11 = var3.animation;
                        }

                        Animation var16;
                        float var17;
                        float var18;
                        boolean var19;
                        if (!var13) {
                            var19 = var9;
                            var18 = 100.0F;
                            var17 = var12;
                            var16 = var11;
                        } else {
                            var19 = var9;
                            var18 = 150.0F;
                            var17 = var12;
                            var16 = var11;
                        }

                        Animation var23;
                        float var24;
                        float var25;
                        Easing var26;
                        if (!var19) {
                            var26 = Easings.easing;
                            var25 = var18;
                            var24 = var17;
                            var23 = var16;
                        } else {
                            var26 = Easings.easing7;
                            var25 = var18;
                            var24 = var17;
                            var23 = var16;
                        }

                        var23.method_603(var24, var25, var26);
                        var3.animation.method_605(var2);
                        if (var9) {
                            var7 = true;
                        }

                        if (!var9 && var3.animation.method_604() && var3.animation.method_606() <= 0.0F) {
                            var5.map.remove(var27);
                        }
                        continue label82;
                    }

                    var8 = var4;
                }
            }

            return var7;
        }
    }

    private static final class Row {
        private final Animation animation = new Animation(0.0F);
        private String string;

        private Row(String var1) {
            this.string = var1;
        }
    }
}
