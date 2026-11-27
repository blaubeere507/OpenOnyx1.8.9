package client.onyx.module.hud.targethud;

import client.onyx.Onyx;
import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easing;
import client.onyx.render.anim.Easings;
import client.onyx.render.anim.Spring;
import client.onyx.render.font.FontFiles;
import client.onyx.render.font.FontStyle;
import client.onyx.render.ui.HealthBarAnimator;
import client.onyx.render.ui.HotbarRenderer;
import client.onyx.render.ui.PanelRenderer;
import client.onyx.render.ui.StatusIcons;
import client.onyx.render.ui.UiRenderer;
import client.onyx.render.world.WorldProjection;
import client.onyx.rotation.HitVecUtil;
import client.onyx.setting.impl.ButtonSetting;
import client.onyx.setting.impl.EnumSetting;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.ui.component.ComponentHost;
import client.onyx.ui.screen.TargetHudEditorScreen;
import client.onyx.ui.theme.ColorRoles;
import client.onyx.ui.theme.Palette;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.Typography;
import client.onyx.util.TextFormatting;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Mouse;

public class TargetHUD extends Module {
    private static final float FLOAT_1_01 = 1.0F;
    private static final float FLOAT_0_941 = 0.94F;
    private static final float FLOAT_3_01 = 3.0F;
    private static final float FLOAT_400_01 = 400.0F;
    private static final float FLOAT_9_01 = 9.0F;
    private static final float FLOAT_0_51 = 0.5F;
    private static final float FLOAT_28_01 = 28.0F;
    private static final float FLOAT_6_01 = 6.0F;
    private static final float FLOAT_8_01 = 8.0F;
    private static final float FLOAT_8_02 = 8.0F;
    private static final float FLOAT_1_02 = 1.0F;
    private static final float FLOAT_100_01 = 100.0F;
    private static final float FLOAT_8_03 = 8.0F;
    private static final float FLOAT_1_351 = 1.35F;
    private static final float FLOAT_0_81 = 0.8F;
    private static final float FLOAT_12_01 = 12.0F;
    private static final float FLOAT_0_551 = 0.55F;
    private static final float FLOAT_4_01 = 4.0F;
    private static final double DOUBLE_0_051 = 0.05;
    private static final float FLOAT_2_01 = 2.0F;
    private static final float FLOAT_80_01 = 80.0F;
    private static final String STRING_CONST = " · ";
    private static final float FLOAT_0_52 = 0.5F;
    private static final float FLOAT_11_01 = 11.0F;
    private static final float FLOAT_10_01 = 10.0F;
    private static final float FLOAT_0_31 = 0.3F;
    private static final String STRING_CONST2 = "target-hud";
    private static final float FLOAT_62_01 = 62.0F;
    private static final float FLOAT_26_01 = 26.0F;
    private static final int INT_31 = 3;
    public ButtonSetting editLayout = new ButtonSetting("Edit layout", () -> mc.displayGuiScreen(new TargetHudEditorScreen(mc.currentScreen)))
        .describe("Move, rescale, recolour and retitle the card's elements");
    public NumberSetting scale = new NumberSetting("Scale", 0.85, 0.5, 2.0, 0.05).method_147("x").describe("Card size");
    public NumberSetting linger = new NumberSetting("Linger", 1.0, 0.0, 3.0, 0.1).method_147("s").describe("How long the card holds a target it has lost");
    public TargetHudElementSettings unnamed2 = new TargetHudElementSettings(TargetHudElement.AVATAR, true, "", () -> Theme.getPalette().intValue11());
    public TargetHudElementSettings name2 = new TargetHudElementSettings(TargetHudElement.NAME, true, "{name}", () -> Theme.getPalette().intValue15());
    public TargetHudElementSettings health = new TargetHudElementSettings(TargetHudElement.HEALTH, true, "{health}", () -> Theme.getPalette().intValue31());
    public TargetHudElementSettings distanceM = new TargetHudElementSettings(
        TargetHudElement.DISTANCE, true, "{distance}m", () -> Theme.getPalette().intValue7()
    );
    public TargetHudElementSettings status = new TargetHudElementSettings(TargetHudElement.STATUS, true, "{status}", () -> Theme.getPalette().intValue7());
    public TargetHudElementSettings unnamed = new TargetHudElementSettings(TargetHudElement.HEALTH_BAR, true, "", () -> Theme.getPalette().intValue31());
    public EnumSetting<TargetHUD.AnchorSpace> position = new EnumSetting<>("Position", TargetHUD.AnchorSpace.SCREEN)
        .describe("Fixed on the screen, or floating above the target in the world");
    public NumberSetting heightOffset = new NumberSetting("Height offset", 0.6, -3.0, 3.0, 0.1)
        .method_147("m")
        .describe("How far above the target's head the card floats")
        .visibleWhen(this.position, var0 -> var0 == TargetHUD.AnchorSpace.WORLD);
    public NumberSetting opacity = new NumberSetting("Opacity", 100.0, 5.0, 100.0, 1.0)
        .method_147("%")
        .describe("Overall opacity of the card - the rendered mob avatar is drawn by the game and stays solid");
    public NumberSetting positionX = new NumberSetting("Position X", 50.0, 0.0, 100.0, 0.01).visibleWhen(() -> false);
    public NumberSetting positionY = new NumberSetting("Position Y", 78.26, 0.0, 100.0, 0.01).visibleWhen(() -> false);
    private final Animation animation5 = new Animation(0.0F);
    private final Animation animation3 = new Animation(1.0F);
    private final Animation animation = new Animation(62.0F);
    private final Animation animation2 = new Animation(1.0F);
    private final Animation animation6 = new Animation(1.0F);
    private final Animation animation4 = new Animation(0.0F);
    private final Spring spring = new Spring(Theme.getPalette().intValue31());
    private final Spring spring2 = new Spring(Theme.getPalette().intValue7());
    private final ComponentHost componentHost = new ComponentHost();
    private float floatValue = 1.0F;
    private HealthBarAnimator.Ghost ghost;
    private final StatusIcons statusIcons;
    private float floatValue2;
    private float floatValue3;
    private float floatValue4;
    private boolean flag;
    private EntityLivingBase entityLivingBase;
    private float floatValue5;
    private float floatValue6;
    private EntityLivingBase entityLivingBase2;
    private float floatValue7;
    private List<TargetHudElementSettings> list;
    private float floatValue8;
    private float floatValue9;
    private float floatValue10;
    private long longValue;
    private boolean flag2;
    private boolean flag3;
    private float floatValue11;

    private float method_1432() {
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

    public void method_1433(UiRenderer var1, Palette var2, TargetHUD.TargetState var3, boolean var4) {
        UiRenderer var29 = var1;
        var var30 = this;
        float var5 = var3.floatValue3();
        float var6 = var3.floatValue2();
        int var7 = ColorRoles.method_182(3, var2);
        var1.method_485(0.0F, 0.0F, var5, var6, 12.0F, 3);
        PanelRenderer.method_598(var1, 0.0F, 0.0F, var5, var6, 12.0F, var7);
        var1.method_440(this.animation3.method_606());

        for (TargetHUD.ElementLayout var9 : var3.list()) {
            switch (var9.method_1394().getKind()) {
                case AVATAR:
                    var30.method_1446(var29, var2, var9, var7);
                    break;
                case BAR:
                    var30.method_1435(var29, var2, var9, var3.floatValue());
                    break;
                default:
                    var30.method_1450(var29, var2, var9);
            }
        }

        var29.method_430();
        if (var4) {
            var30.componentHost.method_2219(var29, 0.0F, 0.0F, var5, var6, 12.0F, var2.intValue15());
        }

        int var10007 = var2.intValue12();
        int var10008 = var2.intValue31();
        UiRenderer var19;
        float var20;
        float var21;
        float var22;
        float var23;
        float var24;
        float var25;
        int var26;
        int var27;
        float var28;
        if (!var4) {
            int var18 = var10008;
            int var17 = var10007;
            float var16 = 1.0F;
            float var15 = 12.0F;
            float var12 = 0.0F;
            float var11 = 0.0F;
            var28 = 0.0F;
            var27 = var18;
            var26 = var17;
            var25 = var16;
            var24 = var15;
            var23 = var6;
            var22 = var5;
            var21 = var12;
            var20 = var11;
            var19 = var29;
        } else {
            var28 = var30.animation4.method_606();
            var27 = var10008;
            var26 = var10007;
            var25 = 1.0F;
            var24 = 12.0F;
            var23 = var6;
            var22 = var5;
            var21 = 0.0F;
            var20 = 0.0F;
            var19 = var29;
        }

        var19.method_494(var20, var21, var22, var23, var24, var25, Spring.method_616(var26, var27, var28));
    }

    private static void method_1434(NumberSetting var0, float var1, float var2) {
        if (!(var2 <= 0.0F)) {
            var0.setValue(Math.clamp(var1 / var2, 0.0F, 1.0F) * 100.0);
        }
    }

    private void method_1435(UiRenderer var1, Palette var2, TargetHUD.ElementLayout var3, float var4) {
        float var5 = var3.floatValue();
        float var6 = var3.floatValue3();
        if (!(var5 <= 0.0F)) {
            int var7 = this.method_1442(var3.targetHudElementSettings(), var2);
            float var8;
            if ((var8 = var3.targetHudElementSettings().glow.method_151()) > 0.0F) {
                var1.method_431(var3.floatValue2(), var3.floatValue4(), var5 * this.animation2.method_606(), var6, Float.MAX_VALUE, var8, 0.0F, var7);
            }

            float var12 = this.method_1448(var1, var3, 0.0F, var5, this.animation2.method_606() * var5, var7);
            if (var3.targetHudElementSettings().absorption.method_146()) {
                var12 = this.method_1448(var1, var3, var12, var5, var4 * var5, var2.intValue27());
            }

            if (var3.targetHudElementSettings().damageTrail.method_146()) {
                var12 = this.method_1448(
                    var1, var3, var12, var5, (this.animation6.method_606() - this.animation2.method_606()) * var5, Theme.method_180(var2.intValue13(), 0.55F)
                );
            }

            float var9;
            if (var12 > 0.0F) {
                var9 = var12 + 3.0F;
            } else {
                var9 = 0.0F;
            }

            if (var5 - var9 > 0.5F) {
                var1.method_479(var3.floatValue2() + var9, var3.floatValue4(), var5 - var9, var6, Float.MAX_VALUE, var2.intValue11());
            }
        }
    }

    private float method_1436(FontStyle var1) {
        return var1.method_643() * 1.35F;
    }

    public TargetHUD() {
        super("TargetHUD", "Card showing your target's health and distance", Category.HUD);
        this.ghost = HealthBarAnimator.ghost;
        this.statusIcons = new StatusIcons();
    }

    public List<TargetHudElementSettings> method_1437() {
        if (this.list == null) {
            this.list = List.of(this.unnamed2, this.name2, this.health, this.distanceM, this.status, this.unnamed);
        }

        return this.list;
    }

    private String method_1438(TargetHudElementSettings var1, FontStyle var2, TargetHudFormatter.Token var3) {
        String var4 = TargetHudFormatter.method_1511(var1.text.getValue(), var3, var1.readout.getValue());
        return TargetHudFormatter.method_1509(var2.method_646(), var4);
    }

    public boolean method_1439() {
        return this.isToggled() && mc.thePlayer != null && mc.theWorld != null;
    }

    private boolean method_1440(boolean var1, float var2, float var3, float var4, float var5) {
        float var6 = Minecraft.getScaledMouseX() * var4 / mc.displayWidth;
        float var11 = var5 - Minecraft.getScaledMouseY() * var5 / mc.displayHeight - 1.0F;
        boolean var8 = this.method_1460();
        boolean var9 = this.method_1452(var6, var11);
        TargetHUD var10;
        if (var1 && var8) {
            if (!this.flag && var9) {
                this.flag3 = true;
                this.floatValue2 = var6 - this.floatValue4;
                this.floatValue3 = var11 - this.floatValue11;
            }

            var10 = this;
        } else {
            this.flag3 = false;
            this.ghost = HealthBarAnimator.ghost;
            var10 = this;
        }

        var10.flag = var8;
        if (this.flag3) {
            this.ghost = HealthBarAnimator.method_861(
                var6 - this.floatValue2,
                var11 - this.floatValue3,
                this.floatValue7,
                this.floatValue8,
                var4,
                var5,
                10.0F,
                HotbarRenderer.method_856("target-hud")
            );
            method_1434(this.positionX, this.ghost.floatValue4() - 10.0F, var2);
            method_1434(this.positionY, this.ghost.floatValue2() - 10.0F, var3);
        }

        return var9;
    }

    private void method_1441(boolean var1, float var2) {
        Object var3;
        if (var1) {
            var3 = mc.thePlayer;
        } else {
            var3 = this.method_1451();
        }

        if (var3 == null) {
            if (this.entityLivingBase2 != null) {
                this.floatValue5 += var2;
                if (this.floatValue5 >= this.linger.method_151() * 1000.0F) {
                    this.entityLivingBase2 = null;
                }
            }
        } else {
            this.entityLivingBase2 = (EntityLivingBase)var3;
            this.floatValue5 = 0.0F;
        }
    }

    private int method_1442(TargetHudElementSettings var1, Palette var2) {
        if (!var1.colour.isFlag4()) {
            return var1.colour.getValue();
        } else {
            switch (var1.getTargetHudElement()) {
                case AVATAR:
                    return var2.intValue11();
                case NAME:
                    return var2.intValue15();
                case HEALTH:
                case HEALTH_BAR:
                    return this.spring.method_617();
                case DISTANCE:
                    return var2.intValue7();
                case STATUS:
                    return this.spring2.method_617();
                default:
                    throw new MatchException(null, null);
            }
        }
    }

    public float method_1443() {
        return this.scale.method_151();
    }

    private void method_1444(float var1, float var2) {
        this.floatValue4 = Math.clamp(this.floatValue9 - this.floatValue7 * 0.5F, 10.0F, Math.max(10.0F, var1 - this.floatValue7 - 10.0F));
        this.floatValue11 = Math.clamp(this.floatValue10 - this.floatValue8, 10.0F, Math.max(10.0F, var2 - this.floatValue8 - 10.0F));
    }

    private void method_1445(float var1, float var2) {
        if (var1 < this.floatValue) {
            this.floatValue6 = 0.0F;
        }

        this.floatValue = var1;
        this.animation2.method_603(var1, 250.0F, Easings.easing2);
        this.animation2.method_605(var2);
        TargetHUD var3;
        if (var1 > this.animation6.getFloatValue3()) {
            this.animation6.method_603(var1, 250.0F, Easings.easing2);
            var3 = this;
        } else {
            if (var1 < this.animation6.getFloatValue3()) {
                this.floatValue6 += var2;
                if (this.floatValue6 >= 400.0F) {
                    this.animation6.method_603(var1, 500.0F, Easings.easing);
                }
            }

            var3 = this;
        }

        var3.animation6.method_605(var2);
    }

    private void method_1446(UiRenderer var1, Palette var2, TargetHUD.ElementLayout var3, int var4) {
        float var5 = var3.floatValue2();
        float var6 = var3.floatValue4();
        float var7 = var3.floatValue();
        int var13 = this.method_1442(var3.targetHudElementSettings(), var2);
        float var10;
        if ((var10 = var3.targetHudElementSettings().glow.method_151()) > 0.0F) {
            var1.method_431(var5, var6, var7, var7, 4.0F, var10, 0.0F, var13);
        }

        var1.method_479(var5, var6, var7, var7, 4.0F, var13);
        EntityLivingBase var11 = this.entityLivingBase;
        UiRenderer var8;
        if (this.entityLivingBase instanceof AbstractClientPlayer) {
            AbstractClientPlayer var14 = (AbstractClientPlayer)var11;
            var1.method_463(var14.getLocationSkin(), var5, var6, var7);
            var8 = var1;
        } else {
            int var12 = (int)Math.max(1.0F, 0.8F * var7 / Math.max(0.5F, this.entityLivingBase.height));
            var1.method_475(this.entityLivingBase, var5 + var7 / 2.0F, var6 + var7, var12);
            var8 = var1;
        }

        var8.method_462(var5, var6, var7, var7, 4.0F, var4);
    }

    private static float method_1447(NumberSetting var0, float var1) {
        return var1 <= 0.0F ? 0.0F : (float)(var1 * var0.getValue() / 100.0);
    }

    private float method_1448(UiRenderer var1, TargetHUD.ElementLayout var2, float var3, float var4, float var5, int var6) {
        float var8;
        if (var3 > 0.0F) {
            var8 = var3 + 3.0F;
        } else {
            var8 = 0.0F;
        }

        if ((var4 = Math.min(var5, var4 - var8)) <= 0.5F) {
            return var3;
        } else {
            var1.method_479(var2.floatValue2() + var8, var2.floatValue4(), var4, var2.floatValue3(), Float.MAX_VALUE, var6);
            return var8 + var4;
        }
    }

    private boolean method_1449(float var1, float var2, float var3) {
        if (this.entityLivingBase == null || !this.entityLivingBase.isEntityAlive() || this.entityLivingBase2 == null) {
            return this.method_1463(var1, var2);
        } else if (!WorldProjection.isFlag()) {
            return this.method_1463(var1, var2);
        } else {
            Vec3 var7;
            if ((var7 = new Vec3(
                        this.entityLivingBase.lastTickPosX + (this.entityLivingBase.posX - this.entityLivingBase.lastTickPosX) * var3,
                        this.entityLivingBase.lastTickPosY
                            + (this.entityLivingBase.posY - this.entityLivingBase.lastTickPosY) * var3
                            + this.entityLivingBase.height
                            + this.heightOffset.method_151(),
                        this.entityLivingBase.lastTickPosZ + (this.entityLivingBase.posZ - this.entityLivingBase.lastTickPosZ) * var3
                    ))
                    .subtract(WorldProjection.getVec31())
                    .dotProduct(WorldProjection.getVec32())
                <= 0.05) {
                return this.flag2 = false;
            } else {
                float[] var8;
                if ((var8 = WorldProjection.method_528(var7, var1, var2)) == null) {
                    return this.flag2 = false;
                } else {
                    float var5;
                    if (this.flag2) {
                        var5 = 28.0F;
                    } else {
                        var5 = 0.0F;
                    }

                    if (!(var8[0] < -var5) && !(var8[0] > var1 + var5) && !(var8[1] < -var5) && !(var8[1] > var2 + var5)) {
                        this.floatValue9 = var8[0];
                        this.floatValue10 = var8[1];
                        this.flag2 = true;
                        this.method_1444(var1, var2);
                        return true;
                    } else {
                        this.flag2 = false;
                        return false;
                    }
                }
            }
        }
    }

    private void method_1450(UiRenderer var1, Palette var2, TargetHUD.ElementLayout var3) {
        if (!var3.string().isEmpty()) {
            FontStyle var4 = var3.fontStyle();
            int var8 = this.method_1442(var3.targetHudElementSettings(), var2);
            float var5 = var3.targetHudElementSettings().glow.method_151();
            float var6 = var3.floatValue4() + var3.floatValue3() / 2.0F;
            var1.method_473(var4, var3.string(), var3.floatValue2(), var1.method_433(var4, var6), var8, var5);
            var1.method_432(var4, var3.string(), var3.floatValue2(), var6, var8);
            if (var3.targetHudElementSettings().readout.getValue().method_1503()) {
                float var10 = this.method_1436(var4);
                var1.method_477("\ue87d", var3.floatValue2() + var3.floatValue() - var10 / 2.0F, var6, var10, var8);
            }
        }
    }

    @Override
    public void onDisable() {
        this.entityLivingBase = null;
        this.entityLivingBase2 = null;
        this.flag2 = false;
        this.flag3 = false;
        this.floatValue5 = 0.0F;
        this.longValue = 0L;
        this.floatValue7 = 0.0F;
        this.floatValue8 = 0.0F;
        this.animation5.method_607(0.0F);
        this.animation3.method_607(1.0F);
        HotbarRenderer.method_855("target-hud");
    }

    private EntityLivingBase method_1451() {
        EntityLivingBase var2;
        return (var2 = Onyx.moduleManager.killAura.getEntityLivingBase2()) != null && var2.isEntityAlive() ? var2 : null;
    }

    public boolean method_1452(double var1, double var3) {
        return var1 >= this.floatValue4
            && var1 < this.floatValue4 + this.floatValue7
            && var3 >= this.floatValue11
            && var3 < this.floatValue11 + this.floatValue8;
    }

    private void method_1453(
        List<TargetHUD.ElementLayout> var1, TargetHudElementSettings var2, String var3, FontStyle var4, float var5, float var6, float var7, float var8
    ) {
        if (var2.method_1504()) {
            var1.add(new TargetHUD.ElementLayout(var2, var3, var4, var5 + var2.offsetX.method_151(), var6 + var2.offsetY.method_151(), var7, var8));
        }
    }

    private float method_1454(float var1) {
        if (mc.thePlayer != null
            && this.entityLivingBase != mc.thePlayer
            && mc.thePlayer.isEntityAlive()
            && this.entityLivingBase.isEntityAlive()
            && Float.isFinite(var1)) {
            float var4 = Math.max(mc.thePlayer.getMaxHealth(), 1.0F);
            float var5 = Math.clamp(mc.thePlayer.getHealth(), 0.0F, var4);
            float var3 = Math.max(mc.thePlayer.getAbsorptionAmount(), 0.0F);
            return var5 + var3 - var1;
        } else {
            return 0.0F;
        }
    }

    private int method_1455(Palette var1, boolean var2, float var3) {
        if (var2) {
            return var1.intValue31();
        } else {
            float var6;
            if ((var6 = this.method_1454(var3)) > 1.0F) {
                return var1.intValue31();
            } else {
                return var6 < -1.0F ? var1.intValue13() : var1.intValue7();
            }
        }
    }

    private float method_1456(TargetHudElementSettings var1, FontStyle var2) {
        return var1.readout.getValue().method_1503() ? 2.0F + this.method_1436(var2) : 0.0F;
    }

    public void method_1457(UiRenderer var1, float var2, float var3, float var4) {
        float var5 = this.method_1432();
        boolean var6 = mc.currentScreen instanceof GuiChat;
        this.method_1441(var6, var5);
        this.method_1467(var5);
        boolean var13;
        if (this.entityLivingBase2 == null) {
            var13 = true;
        } else {
            var13 = false;
        }

        Animation var15;
        float var16;
        boolean var17;
        if (!var13) {
            Animation var14 = this.animation5;
            var17 = var13;
            var16 = 1.0F;
            var15 = var14;
        } else {
            var17 = var13;
            var16 = 0.0F;
            var15 = this.animation5;
        }

        Animation var20;
        float var21;
        float var22;
        boolean var23;
        if (!var17) {
            var23 = var13;
            var22 = 300.0F;
            var21 = var16;
            var20 = var15;
        } else {
            var23 = var13;
            var22 = 200.0F;
            var21 = var16;
            var20 = var15;
        }

        Animation var27;
        float var28;
        float var29;
        Easing var30;
        if (!var23) {
            var30 = Easings.easing7;
            var29 = var22;
            var28 = var21;
            var27 = var20;
        } else {
            var30 = Easings.easing;
            var29 = var22;
            var28 = var21;
            var27 = var20;
        }

        var27.method_603(var28, var29, var30);
        this.animation5.method_605(var5);
        if (this.entityLivingBase == null) {
            this.floatValue7 = 0.0F;
            this.floatValue8 = 0.0F;
            this.flag = this.method_1460();
            HotbarRenderer.method_855("target-hud");
        } else {
            PanelRenderer.method_602();
            Palette var42 = Theme.getPalette();
            TargetHUD.TargetState var8 = this.method_1464(var1, var42, var6, var5);
            float var9 = this.scale.method_151() * (0.94F + 0.060000002F * this.animation5.method_606());
            this.floatValue7 = var8.floatValue3() * var9;
            this.floatValue8 = var8.floatValue2() * var9;
            boolean var31;
            if (this.position.method_168(TargetHUD.AnchorSpace.WORLD) && !var6 && this.method_1449(var2, var3, var4)) {
                var31 = true;
            } else {
                var31 = false;
            }

            boolean var43 = false;
            TargetHUD var32;
            if (var31) {
                this.flag = this.method_1460();
                this.statusIcons.method_865(false);
                HotbarRenderer.method_855("target-hud");
                var32 = this;
            } else {
                float var10 = var2 - this.floatValue7 - 20.0F;
                float var11 = var3 - this.floatValue8 - 20.0F;
                this.floatValue4 = 10.0F + method_1447(this.positionX, var10);
                this.floatValue11 = 10.0F + method_1447(this.positionY, var11);
                HotbarRenderer.method_857("target-hud", this.floatValue4, this.floatValue11, this.floatValue7, this.floatValue8);
                var43 = this.method_1440(var6, var10, var11, var2, var3);
                if (var6 && var43 && !this.flag3) {
                    float[] var12;
                    if ((var12 = this.statusIcons.method_865(true))[0] != 0.0F || var12[1] != 0.0F) {
                        method_1434(this.positionX, method_1447(this.positionX, var10) + var12[0], var10);
                        method_1434(this.positionY, method_1447(this.positionY, var11) + var12[1], var11);
                    }
                } else {
                    this.statusIcons.method_865(false);
                }

                this.floatValue4 = 10.0F + method_1447(this.positionX, var10);
                this.floatValue11 = 10.0F + method_1447(this.positionY, var11);
                var32 = this;
            }

            Animation var34;
            float var35;
            if (!var6) {
                Animation var33 = var32.animation4;
                var35 = 0.0F;
                var34 = var33;
            } else {
                var35 = 1.0F;
                var34 = var32.animation4;
            }

            ComponentHost var38;
            float var39;
            boolean var40;
            TargetHUD var41;
            label65: {
                var34.method_603(var35, 250.0F, Easings.easing2);
                this.animation4.method_605(var5);
                ComponentHost var36;
                float var37;
                if (!var6) {
                    var37 = var5;
                    var36 = this.componentHost;
                } else {
                    if (var43) {
                        var41 = this;
                        var40 = true;
                        var39 = var5;
                        var38 = this.componentHost;
                        break label65;
                    }

                    var37 = var5;
                    var36 = this.componentHost;
                }

                var41 = this;
                var40 = false;
                var39 = var37;
                var38 = var36;
            }

            var38.method_2220(var39, var40, var41.flag3);
            if (this.flag3) {
                HealthBarAnimator.method_862(var1, var2, var3, this.ghost);
            }

            var1.method_440(this.animation5.method_606());
            var1.method_440(this.opacity.method_151() / 100.0F);
            var1.method_441();
            var1.method_451(this.floatValue4, this.floatValue11);
            var1.method_491(var9, 0.0F, 0.0F);
            this.method_1433(var1, var42, var8, true);
            var1.method_435();
            var1.method_430();
            var1.method_430();
        }
    }

    private void method_1458(EntityLivingBase var1) {
        this.entityLivingBase = var1;
        this.flag2 = false;
        float var3 = Math.clamp(var1.getHealth() / Math.max(var1.getMaxHealth(), 1.0F), 0.0F, 1.0F);
        this.animation2.method_607(var3);
        this.animation6.method_607(var3);
        this.floatValue = var3;
        this.floatValue6 = 0.0F;
    }

    private void method_1459(Palette var1, boolean var2, float var3, float var4, float var5) {
        Spring var12 = this.spring;
        Spring var8;
        int var9;
        if (!(var3 <= 0.3F)) {
            Spring var6 = this.spring;
            var9 = var1.intValue31();
            var8 = var6;
        } else {
            var9 = var1.intValue13();
            var8 = var12;
        }

        var8.method_613(var9, 250.0F, Easings.easing2);
        this.spring.method_614(var5);
        this.spring2.method_613(this.method_1455(var1, var2, var4), 250.0F, Easings.easing2);
        this.spring2.method_614(var5);
    }

    private boolean method_1460() {
        return Mouse.isButtonDown(0);
    }

    public TargetHUD.TargetState method_1461(UiRenderer var1, Palette var2, float var3) {
        if (mc.thePlayer == null) {
            return null;
        } else {
            this.entityLivingBase = mc.thePlayer;
            this.entityLivingBase2 = mc.thePlayer;
            this.animation3.method_607(1.0F);
            this.animation5.method_607(1.0F);
            return this.method_1464(var1, var2, false, var3);
        }
    }

    private String method_1462(boolean var1, float var2) {
        if (var1) {
            return "Drag to move";
        } else {
            float var4;
            if ((var4 = this.method_1454(var2)) > 1.0F) {
                return "Winning";
            } else {
                return var4 < -1.0F ? "Losing" : "Even";
            }
        }
    }

    private boolean method_1463(float var1, float var2) {
        if (!this.flag2) {
            return false;
        } else {
            this.method_1444(var1, var2);
            return true;
        }
    }

    public TargetHUD.TargetState method_1464(UiRenderer var1, Palette var2, boolean var3, float var4) {
        float var58 = Math.max(this.entityLivingBase.getMaxHealth(), 1.0F);
        float var52 = Math.clamp(this.entityLivingBase.getHealth(), 0.0F, var58);
        float var6 = Math.max(this.entityLivingBase.getAbsorptionAmount(), 0.0F);
        float var7;
        this.method_1445(var7 = var52 / var58, var4);
        this.method_1459(var2, var3, var7, var52 + var6, var4);
        boolean var63 = this.unnamed.absorption.method_146();
        TargetHudFormatter.Token var53 = new TargetHudFormatter.Token(
            TextFormatting.method_2142(this.entityLivingBase.getName()),
            var52 + (var63 ? var6 : 0.0F),
            var58,
            (float)Math.sqrt(HitVecUtil.method_2131(this.entityLivingBase)),
            this.method_1462(var3, var52 + var6)
        );
        FontStyle var64 = this.method_1466(this.name2, Typography.fontStyle9);
        FontStyle var8 = this.method_1466(this.health, Typography.fontStyle9);
        FontStyle var9 = this.method_1466(this.distanceM, Typography.fontStyle8);
        FontStyle var10 = this.method_1466(this.status, Typography.fontStyle8);
        String var11 = var1.method_469(var64, this.method_1438(this.name2, var64, var53), 80.0F * this.name2.size.method_151());
        String var12 = this.method_1438(this.health, var8, var53);
        String var25;
        if (var3) {
            var25 = "";
        } else {
            var25 = this.method_1438(this.distanceM, var9, var53);
        }

        String var59 = var25;
        String var54 = this.method_1438(this.status, var10, var53);
        if (this.distanceM.method_1504() && this.status.method_1504() && !var25.isEmpty()) {
            var59 = new StringBuilder().insert(0, var25).append(" · ").toString();
        }

        float var26;
        if (this.unnamed2.method_1504()) {
            var26 = 26.0F * this.unnamed2.size.method_151();
        } else {
            var26 = 0.0F;
        }

        float var28;
        float var29;
        if (!this.unnamed2.method_1504()) {
            float var27 = 11.0F;
            var29 = 0.0F;
            var28 = var27;
        } else {
            var29 = var26 + 9.0F;
            var28 = 11.0F;
        }

        float var14 = var28 + var29;
        float var30;
        if (this.name2.method_1504()) {
            var30 = var1.method_442(var64, var11);
        } else {
            var30 = 0.0F;
        }

        float var31;
        if (this.health.method_1504()) {
            var31 = var1.method_442(var8, var12) + this.method_1456(this.health, var8);
        } else {
            var31 = 0.0F;
        }

        float var32;
        if (this.distanceM.method_1504()) {
            var32 = var1.method_442(var9, var59);
        } else {
            var32 = 0.0F;
        }

        float var33;
        if (this.status.method_1504()) {
            var33 = var1.method_442(var10, var54);
        } else {
            var33 = 0.0F;
        }

        float var35;
        float var36;
        label108: {
            float var71 = var30 + var31;
            float var34;
            if (!this.name2.method_1504()) {
                var34 = var71;
            } else {
                if (this.health.method_1504()) {
                    var36 = 8.0F;
                    var35 = var71;
                    break label108;
                }

                var34 = var71;
            }

            var36 = 0.0F;
            var35 = var34;
        }

        float var56 = var35 + var36;
        float var19 = var32 + var33;
        this.animation.method_603(Math.max(62.0F, Math.max(var56, var19)), 300.0F, Easings.easing2);
        this.animation.method_605(var4);
        float var57 = var14 + this.animation.method_606() + 11.0F;
        var4 = this.unnamed.thickness.method_151();
        float var37;
        TargetHUD var38;
        if (this.name2.method_1504()) {
            float var72 = var64.method_642();
            var38 = this;
            var37 = var72;
        } else {
            var38 = this;
            var37 = 0.0F;
        }

        float var40;
        float var41;
        if (!var38.health.method_1504()) {
            var41 = 0.0F;
            var40 = var37;
        } else {
            var41 = var8.method_642();
            var40 = var37;
        }

        var19 = Math.max(var40, var41);
        float var42;
        TargetHUD var43;
        if (this.distanceM.method_1504()) {
            float var73 = var9.method_642();
            var43 = this;
            var42 = var73;
        } else {
            var43 = this;
            var42 = 0.0F;
        }

        float var45;
        float var46;
        if (!var43.status.method_1504()) {
            var46 = 0.0F;
            var45 = var42;
        } else {
            var46 = var10.method_642();
            var45 = var42;
        }

        float var20;
        float var48;
        float var49;
        label101: {
            var20 = Math.max(var45, var46);
            float var74 = var19 + var20;
            float var47;
            if (!(var19 > 0.0F)) {
                var47 = var74;
            } else {
                if (var20 > 0.0F) {
                    var49 = 4.0F;
                    var48 = var74;
                    break label101;
                }

                var47 = var74;
            }

            var49 = 0.0F;
            var48 = var47;
        }

        float var21 = var48 + var49;
        float var22 = Math.max(var26, var21);
        float var23 = 8.0F + (var22 - var26) / 2.0F;
        float var24;
        var19 = (var24 = 8.0F + (var22 - var21) / 2.0F) + var19 / 2.0F;
        var20 = var24 + var21 - var20 / 2.0F;
        var21 = 8.0F + var22 + 6.0F;
        float var50;
        if (this.unnamed.method_1504()) {
            var50 = var21 + var4;
        } else {
            var50 = 8.0F + var22;
        }

        var22 = var50 + 8.0F;
        ArrayList var70 = new ArrayList(6);
        this.method_1453(var70, this.unnamed2, null, null, 11.0F, var23, var26, var26);
        this.method_1453(var70, this.name2, var11, var64, var14, var19 - var64.method_642() / 2.0F, var30, var64.method_642());
        this.method_1453(var70, this.health, var12, var8, var57 - 11.0F - var31, var19 - var8.method_642() / 2.0F, var31, var8.method_642());
        this.method_1453(var70, this.distanceM, var59, var9, var14, var20 - var9.method_642() / 2.0F, var32, var9.method_642());
        this.method_1453(var70, this.status, var54, var10, var14 + var32, var20 - var10.method_642() / 2.0F, var33, var10.method_642());
        this.method_1453(var70, this.unnamed, null, null, 11.0F, var21, Math.max(0.0F, var57 - 22.0F), var4);
        float var60 = var22;

        Iterator var62;
        for (Iterator var51 = var62 = var70.iterator(); var51.hasNext(); var51 = var62) {
            TargetHUD.ElementLayout var55 = (TargetHUD.ElementLayout)var62.next();
            var60 = Math.max(var60, var55.floatValue4() + var55.floatValue3() + 8.0F);
        }

        return new TargetHUD.TargetState(var57, var60, var70, var6 / var58);
    }

    public void method_1465() {
        this.entityLivingBase2 = this.entityLivingBase = null;
        this.flag2 = false;
        this.animation5.method_607(0.0F);
    }

    private FontStyle method_1466(TargetHudElementSettings var1, FontStyle var2) {
        FontFiles.Weight var3 = var1.weight.getValue();
        float var4;
        return (var4 = var1.size.method_151()) == 1.0F && var3 == var2.weight()
            ? var2
            : new FontStyle(var2.size(), var3, var2.floatValue2() * var4, var2.floatValue() * var4);
    }

    private void method_1467(float var1) {
        this.animation3.method_605(var1);
        if (this.entityLivingBase2 == this.entityLivingBase) {
            if (this.entityLivingBase != null && this.animation3.getFloatValue3() < 1.0F) {
                this.animation3.method_603(1.0F, 150.0F, Easings.easing7);
            }
        } else if (this.entityLivingBase2 == null) {
            if (this.animation5.method_604() && this.animation5.method_606() <= 0.0F) {
                this.entityLivingBase = null;
            }
        } else if (this.entityLivingBase == null || this.animation5.method_606() <= 0.0F) {
            this.method_1458(this.entityLivingBase2);
            this.animation3.method_607(1.0F);
        } else if (!(this.animation3.getFloatValue3() > 0.0F)) {
            if (this.animation3.method_604()) {
                this.method_1458(this.entityLivingBase2);
                this.animation3.method_607(0.0F).method_603(1.0F, 150.0F, Easings.easing7);
            }
        } else {
            this.animation3.method_603(0.0F, 100.0F, Easings.easing);
        }
    }

    public static enum AnchorSpace {
        SCREEN,
        WORLD;
    }

    public record ElementLayout(
        TargetHudElementSettings targetHudElementSettings,
        String string,
        FontStyle fontStyle,
        float floatValue2,
        float floatValue4,
        float floatValue,
        float floatValue3
    ) {
        public TargetHudElement method_1394() {
            return this.targetHudElementSettings.getTargetHudElement();
        }
    }

    public record TargetState(float floatValue3, float floatValue2, List<TargetHUD.ElementLayout> list, float floatValue) {
    }
}
