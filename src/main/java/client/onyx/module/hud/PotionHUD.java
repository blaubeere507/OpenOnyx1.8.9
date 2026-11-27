package client.onyx.module.hud;

import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easing;
import client.onyx.render.anim.Easings;
import client.onyx.render.font.FontStyle;
import client.onyx.render.ui.HealthBarAnimator;
import client.onyx.render.ui.HotbarRenderer;
import client.onyx.render.ui.PanelRenderer;
import client.onyx.render.ui.StatusIcons;
import client.onyx.render.ui.UiRenderer;
import client.onyx.setting.NamedEnum;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.EnumSetting;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.ui.component.ComponentHost;
import client.onyx.ui.theme.ColorRoles;
import client.onyx.ui.theme.Palette;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.Typography;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

public final class PotionHUD extends Module {
    private static final ResourceLocation resourceLocation = new ResourceLocation("textures/gui/container/inventory.png");
    private static final String[] stringArray = new String[]{"", "II", "III", "IV", "V", "VI", "VII", "VIII"};
    private static final float FLOAT_3_01 = 3.0F;
    private static final int INT_2001 = 200;
    private static final float FLOAT_10_01 = 10.0F;
    private static final float FLOAT_100_01 = 100.0F;
    private static final float FLOAT_5_01 = 5.0F;
    private static final float FLOAT_10_02 = 10.0F;
    private static final float FLOAT_5_02 = 5.0F;
    private static final float FLOAT_12_01 = 12.0F;
    private static final float FLOAT_6_01 = 6.0F;
    private static final float FLOAT_5_03 = 5.0F;
    private static final float FLOAT_1_01 = 1.0F;
    private static final float FLOAT_1_02 = 1.0F;
    private static final float FLOAT_0_941 = 0.94F;
    private static final float FLOAT_18_01 = 18.0F;
    private static final float FLOAT_16_01 = 16.0F;
    private static final float FLOAT_110_01 = 110.0F;
    private static final float FLOAT_6_02 = 6.0F;
    private static final int INT_31 = 3;
    private static final float FLOAT_31_01 = 31.0F;
    private static final float FLOAT_20_01 = 20.0F;
    private static final float FLOAT_4_01 = 4.0F;
    private static final String STRING_CONST = "potions";
    public final NumberSetting scale = new NumberSetting("Scale", 0.85, 0.5, 2.0, 0.05).method_147("x").describe("Potion list size");
    public final EnumSetting<PotionHUD.SortKey> sort = new EnumSetting<>("Sort", PotionHUD.SortKey.DURATION).describe("Row order");
    public final BooleanSetting icons = new BooleanSetting("Icons", true).describe("Show the vanilla effect icon");
    public final BooleanSetting duration = new BooleanSetting("Duration", true).describe("Show the time remaining");
    public final BooleanSetting amplifier = new BooleanSetting("Amplifier", true).describe("Show the effect level as a roman numeral");
    public final BooleanSetting potionColor = new BooleanSetting("Potion color", true).describe("Tint each row with the potion's own colour");
    public final BooleanSetting lowTimeWarning = new BooleanSetting("Low time warning", true).describe("Turn the timer red as an effect runs out");
    public final BooleanSetting hideAmbient = new BooleanSetting("Hide ambient", false).describe("Skip beacon effects");
    public final NumberSetting positionX = new NumberSetting("Position X", 6.78, 0.0, 100.0, 0.01).visibleWhen(() -> false);
    public final NumberSetting positionY = new NumberSetting("Position Y", 45.0, 0.0, 100.0, 0.01).visibleWhen(() -> false);
    private final Map<Integer, PotionHUD.PotionAnimation> map = new HashMap<>();
    private final Animation animation2 = new Animation(0.0F);
    private final Animation animation = new Animation(36.0F);
    private final Animation animation3 = new Animation(57.0F);
    private final ComponentHost componentHost = new ComponentHost();
    private HealthBarAnimator.Ghost ghost;
    private final StatusIcons statusIcons;
    private float floatValue;
    private boolean flag;
    private float floatValue2;
    private float floatValue3;
    private float floatValue4;
    private long longValue;
    private boolean flag2;
    private float floatValue5;
    private boolean flag3;
    private float floatValue6;

    private boolean method_1408(boolean var1, float var2, float var3, float var4, float var5) {
        float var6 = Minecraft.getScaledMouseX() * var4 / mc.displayWidth;
        float var11 = var5 - Minecraft.getScaledMouseY() * var5 / mc.displayHeight - 1.0F;
        boolean var8 = this.method_1414();
        boolean var9 = this.method_1419(var6, var11);
        PotionHUD var10;
        if (var1 && var8) {
            if (!this.flag && var9) {
                this.flag3 = true;
                this.floatValue6 = var6 - this.floatValue4;
                this.floatValue3 = var11 - this.floatValue2;
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
                var6 - this.floatValue6, var11 - this.floatValue3, this.floatValue, this.floatValue5, var4, var5, 10.0F, HotbarRenderer.method_856("potions")
            );
            method_1410(this.positionX, this.ghost.floatValue4() - 10.0F, var2);
            method_1410(this.positionY, this.ghost.floatValue2() - 10.0F, var3);
        }

        return var9;
    }

    @Override
    public void onEnable() {
        this.flag2 = false;
        this.longValue = 0L;
    }

    public void method_1409(UiRenderer var1, float var2, float var3) {
        float var4 = this.method_1421();
        boolean var13;
        if (this.isToggled() && mc.currentScreen instanceof GuiChat) {
            var13 = true;
        } else {
            var13 = false;
        }

        List var6;
        boolean var37 = this.method_1416(var6 = this.method_1423(var13), var4);
        Animation var15;
        float var16;
        boolean var17;
        if (!var37) {
            Animation var14 = this.animation2;
            var17 = var37;
            var16 = 0.0F;
            var15 = var14;
        } else {
            var17 = var37;
            var16 = 1.0F;
            var15 = this.animation2;
        }

        Animation var20;
        float var21;
        float var22;
        boolean var23;
        if (!var17) {
            var23 = var37;
            var22 = 200.0F;
            var21 = var16;
            var20 = var15;
        } else {
            var23 = var37;
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
        this.animation2.method_605(var4);
        if (this.map.isEmpty() && this.animation2.method_604() && this.animation2.method_606() <= 0.0F) {
            this.method_1424();
            this.floatValue = 0.0F;
            this.floatValue5 = 0.0F;
            this.flag = this.method_1414();
        } else {
            Palette var38 = Theme.getPalette();
            this.method_1411(var1, var6, var4);
            float var8 = this.scale.method_151() * (0.94F + 0.060000002F * this.animation2.method_606());
            this.floatValue = this.animation.method_606() * var8;
            this.floatValue5 = this.animation3.method_606() * var8;
            float var9 = var2 - this.floatValue - 20.0F;
            float var10 = var3 - this.floatValue5 - 20.0F;
            this.floatValue4 = 10.0F + method_1425(this.positionX, var9);
            this.floatValue2 = 10.0F + method_1425(this.positionY, var10);
            HotbarRenderer.method_857("potions", this.floatValue4, this.floatValue2, this.floatValue, this.floatValue5);
            boolean var11 = this.method_1408(var13, var9, var10, var2, var3);
            if (var13 && var11 && !this.flag3) {
                float[] var12;
                if ((var12 = this.statusIcons.method_865(true))[0] != 0.0F || var12[1] != 0.0F) {
                    method_1410(this.positionX, method_1425(this.positionX, var9) + var12[0], var9);
                    method_1410(this.positionY, method_1425(this.positionY, var10) + var12[1], var10);
                }
            } else {
                this.statusIcons.method_865(false);
            }

            ComponentHost var33;
            float var34;
            boolean var35;
            PotionHUD var36;
            label55: {
                this.floatValue4 = 10.0F + method_1425(this.positionX, var9);
                this.floatValue2 = 10.0F + method_1425(this.positionY, var10);
                ComponentHost var31;
                float var32;
                if (!var13) {
                    var32 = var4;
                    var31 = this.componentHost;
                } else {
                    if (var11) {
                        var36 = this;
                        var35 = true;
                        var34 = var4;
                        var33 = this.componentHost;
                        break label55;
                    }

                    var32 = var4;
                    var31 = this.componentHost;
                }

                var36 = this;
                var35 = false;
                var34 = var32;
                var33 = var31;
            }

            var33.method_2220(var34, var35, var36.flag3);
            if (this.flag3) {
                HealthBarAnimator.method_862(var1, var2, var3, this.ghost);
            }

            PanelRenderer.method_602();
            var1.method_440(this.animation2.method_606());
            var1.method_441();
            var1.method_451(this.floatValue4, this.floatValue2 + (1.0F - this.animation2.method_606()) * 5.0F);
            var1.method_491(var8, 0.0F, 0.0F);
            this.method_1420(var1, var38, var6);
            var1.method_435();
            var1.method_430();
            this.method_1424();
        }
    }

    private static void method_1410(NumberSetting var0, float var1, float var2) {
        if (!(var2 <= 0.0F)) {
            var0.setValue(Math.clamp(var1 / var2, 0.0F, 1.0F) * 100.0);
        }
    }

    private void method_1411(UiRenderer var1, List<PotionHUD.PotionRow> var2, float var3) {
        var var7 = this;
        float var4 = 29.0F + var1.method_442(Typography.fontStyle9, "Potions");

        Iterator var5;
        for (Iterator var8 = var5 = var2.iterator(); var8.hasNext(); var8 = var5) {
            PotionHUD.PotionRow var6 = (PotionHUD.PotionRow)var5.next();
            float var9 = Math.min(var1.method_442(Typography.fontStyle8, var7.method_1412(var6)), 110.0F);
            float var10 = 18.0F + var9;
            if (var7.icons.method_146()) {
                var10 += 18.0F;
            }

            if (var7.duration.method_146()) {
                var10 += 10.0F + var1.method_442(Typography.fontStyle8, var7.method_1415(var6));
            }

            var4 = Math.max(var4, var10);
        }

        int var11 = var2.size();
        float var12 = 37.0F + var11 * 20.0F + Math.max(0, var11 - 1) * 4.0F;
        var7.animation.method_603(var4, 300.0F, Easings.easing2);
        var7.animation3.method_603(var12, 300.0F, Easings.easing2);
        var7.animation.method_605(var3);
        var7.animation3.method_605(var3);
    }

    private String method_1412(PotionHUD.PotionRow var1) {
        String var2 = this.method_1418(var1);
        if (this.amplifier.method_146() && var1.potionEffect() != null) {
            int var4;
            return (var4 = var1.potionEffect().getAmplifier()) > 0 && var4 < stringArray.length
                ? new StringBuilder().insert(0, var2).append(" ").append(stringArray[var4]).toString()
                : var2;
        } else {
            return var2;
        }
    }

    private void method_1413(UiRenderer var1, Palette var2, PotionHUD.PotionRow var3, float var4, float var5) {
        float var6 = 6.0F;
        int var9;
        if (var3.potion() == null) {
            var9 = var2.intValue31();
        } else {
            var9 = 0xFF000000 | var3.potion().getLiquidColor();
        }

        if (this.icons.method_146()) {
            if (this.potionColor.method_146()) {
                var1.method_479(6.0F, var4 + 1.0F, 18.0F, 18.0F, 4.0F, Theme.method_180(var9, 0.22F));
            }

            if (var3.potion() != null && var3.potion().hasStatusIcon()) {
                int var8 = var3.potion().getStatusIconIndex();
                var1.method_436(resourceLocation, 7.0F, var4 + 1.0F + 1.0F, 16.0F, 16.0F, var8 % 8 * 18, 198 + var8 / 8 * 18, 18.0F, 18.0F, 256, 256);
            }

            var6 += 24.0F;
        }

        String var36 = var1.method_469(Typography.fontStyle8, this.method_1412(var3), 110.0F);
        FontStyle var10001 = Typography.fontStyle8;
        float var10004 = var4 + 10.0F;
        UiRenderer var15;
        FontStyle var16;
        String var17;
        float var18;
        float var19;
        int var20;
        if (!this.potionColor.method_146()) {
            float var14 = var10004;
            FontStyle var11 = var10001;
            var20 = var2.intValue15();
            var19 = var14;
            var18 = var6;
            var17 = var36;
            var16 = var11;
            var15 = var1;
        } else {
            var20 = var9;
            var19 = var10004;
            var18 = var6;
            var17 = var36;
            var16 = var10001;
            var15 = var1;
        }

        var15.method_432(var16, var17, var18, var19, var20);
        if (this.duration.method_146()) {
            boolean var21;
            if (this.lowTimeWarning.method_146()
                && var3.potionEffect() != null
                && !var3.potionEffect().getIsPotionDurationMax()
                && var3.potionEffect().getDuration() < 200) {
                var21 = true;
            } else {
                var21 = false;
            }

            var10001 = Typography.fontStyle8;
            String var10002 = this.method_1415(var3);
            float var10003 = var5 - 6.0F;
            var10004 = var4 + 10.0F;
            UiRenderer var28;
            FontStyle var29;
            String var30;
            float var31;
            float var32;
            int var33;
            if (!var21) {
                float var26 = var10004;
                float var25 = var10003;
                String var24 = var10002;
                FontStyle var23 = var10001;
                var33 = var2.intValue7();
                var32 = var26;
                var31 = var25;
                var30 = var24;
                var29 = var23;
                var28 = var1;
            } else {
                var33 = var2.intValue13();
                var32 = var10004;
                var31 = var10003;
                var30 = var10002;
                var29 = var10001;
                var28 = var1;
            }

            var28.method_476(var29, var30, var31, var32, var33);
        }
    }

    private boolean method_1414() {
        return Mouse.isButtonDown(0);
    }

    private String method_1415(PotionHUD.PotionRow var1) {
        if (var1.potionEffect() == null) {
            return "0:00";
        } else {
            return var1.potionEffect().getIsPotionDurationMax() ? "**:**" : Potion.getDurationString(var1.potionEffect());
        }
    }

    private boolean method_1416(List<PotionHUD.PotionRow> var1, float var2) {
        var var3 = this;
        boolean var4 = false;

        Iterator var8;
        for (Iterator var5 = var8 = var1.iterator(); var5.hasNext(); var5 = var8) {
            PotionHUD.PotionRow var6 = (PotionHUD.PotionRow)var8.next();
            PotionHUD.PotionAnimation var7 = var3.map.computeIfAbsent(var6.intValue(), var0 -> new PotionHUD.PotionAnimation());
            var7.flag = true;
            var7.animation.method_603(1.0F, 150.0F, Easings.easing7);
            var7.animation.method_605(var2);
            var4 = true;
        }

        var3.map.entrySet().removeIf(var1x -> {
            PotionHUD.PotionAnimation var3x;
            if (!(var3x = var1x.getValue()).flag) {
                var3x.animation.method_603(0.0F, 100.0F, Easings.easing);
                var3x.animation.method_605(var2);
            }

            var3x.flag = false;
            return var3x.animation.method_604() && var3x.animation.method_606() <= 0.0F && var3x.animation.getFloatValue3() <= 0.0F;
        });
        return var4;
    }

    @Override
    public void onDisable() {
        this.flag2 = true;
        this.flag3 = false;
    }

    private Comparator<PotionHUD.PotionRow> method_1417() {
        switch ((PotionHUD.SortKey)this.sort.getValue()) {
            case DURATION:
                return Comparator.comparingInt(var0 -> var0.potionEffect() == null ? Integer.MAX_VALUE : var0.potionEffect().getDuration());
            case NAME:
                return Comparator.comparing(this::method_1418, String.CASE_INSENSITIVE_ORDER);
            case LEVEL:
                return Comparator.<PotionHUD.PotionRow>comparingInt(var0 -> var0.potionEffect() == null ? 0 : -var0.potionEffect().getAmplifier())
                    .thenComparing(this::method_1418, String.CASE_INSENSITIVE_ORDER);
            default:
                throw new MatchException(null, null);
        }
    }

    private String method_1418(PotionHUD.PotionRow var1) {
        return var1.potion() == null ? "Effect" : I18n.format(var1.potion().getName());
    }

    public PotionHUD() {
        super("PotionHUD", "Shows your active potion effects", Category.HUD);
        this.ghost = HealthBarAnimator.ghost;
        this.statusIcons = new StatusIcons();
    }

    public boolean method_1419(double var1, double var3) {
        return var1 >= this.floatValue4 && var1 < this.floatValue4 + this.floatValue && var3 >= this.floatValue2 && var3 < this.floatValue2 + this.floatValue5;
    }

    private void method_1420(UiRenderer var1, Palette var2, List<PotionHUD.PotionRow> var3) {
        var var5 = this;
        float var15 = this.animation.method_606();
        float var4 = this.animation3.method_606();
        int var12 = ColorRoles.method_182(3, var2);
        var1.method_485(0.0F, 0.0F, var15, var4, 12.0F, 3);
        PanelRenderer.method_598(var1, 0.0F, 0.0F, var15, var4, 12.0F, var12);
        float var13 = 14.0F;
        var1.method_477("\uea4b", 12.0F, var13, 12.0F, var2.intValue31());
        var1.method_432(Typography.fontStyle9, "Potions", 23.0F, var13, var2.intValue15());
        var1.method_434(6.0F, 25.0F, var15 - 12.0F, 1.0F, Theme.method_180(var2.intValue12(), 0.7F));
        float var14 = 31.0F;

        Iterator var6;
        for (Iterator var11 = var6 = var3.iterator(); var11.hasNext(); var11 = var6) {
            PotionHUD.PotionRow var7 = (PotionHUD.PotionRow)var6.next();
            PotionHUD.PotionAnimation var8;
            float var10;
            if ((var8 = var5.map.get(var7.intValue())) == null) {
                var10 = 1.0F;
            } else {
                var10 = var8.animation.method_606();
            }

            var1.method_440(var10);
            var1.method_441();
            var1.method_451((1.0F - var10) * 5.0F, 0.0F);
            var5.method_1413(var1, var2, var7, var14, var15);
            var1.method_435();
            var1.method_430();
            var14 += 24.0F;
        }

        var5.componentHost.method_2219(var1, 0.0F, 0.0F, var15, var4, 12.0F, var2.intValue15());
        var1.method_494(0.0F, 0.0F, var15, var4, 12.0F, 1.0F, var2.intValue12());
    }

    private float method_1421() {
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

    public boolean method_1422() {
        return mc.thePlayer != null && mc.theWorld != null && (this.isToggled() || this.flag2);
    }

    private List<PotionHUD.PotionRow> method_1423(boolean var1) {
        var var2 = this;
        ArrayList var4 = new ArrayList();
        if (this.isToggled() && mc.thePlayer != null) {
            Iterator var3;
            Iterator var6 = var3 = new ArrayList<>(mc.thePlayer.getActivePotionEffects()).iterator();

            while (var6.hasNext()) {
                PotionEffect var8;
                if ((var8 = (PotionEffect)var3.next()) == null) {
                    var6 = var3;
                } else if (var2.hideAmbient.method_146() && var8.getIsAmbient()) {
                    var6 = var3;
                } else {
                    Potion var7;
                    if (var8.getPotionID() >= 0 && var8.getPotionID() < Potion.potionTypes.length) {
                        var7 = Potion.potionTypes[var8.getPotionID()];
                    } else {
                        var7 = null;
                    }

                    if (var7 == null) {
                        var6 = var3;
                    } else {
                        var4.add(new PotionHUD.PotionRow(var8.getPotionID(), var7, var8));
                        var6 = var3;
                    }
                }
            }
        }

        if (var4.isEmpty() && var1) {
            var4.add(new PotionHUD.PotionRow(-1, null, null));
        }

        var4.sort(var2.method_1417());
        return var4;
    }

    private void method_1424() {
        if (!this.isToggled() && this.map.isEmpty() && this.animation2.method_604() && !(this.animation2.method_606() > 0.0F)) {
            this.flag2 = false;
            this.longValue = 0L;
            this.floatValue = 0.0F;
            this.floatValue5 = 0.0F;
            HotbarRenderer.method_855("potions");
        }
    }

    private static float method_1425(NumberSetting var0, float var1) {
        return var1 <= 0.0F ? 0.0F : (float)(var1 * var0.getValue() / 100.0);
    }

    private static final class PotionAnimation {
        private final Animation animation = new Animation(0.0F);
        private boolean flag;
    }

    private record PotionRow(int intValue, Potion potion, PotionEffect potionEffect) {
    }

    public static enum SortKey implements NamedEnum {
        DURATION("Time left"),
        NAME("Name"),
        LEVEL("Level");

        private final String string;

        private SortKey(String var3) {
            this.string = var3;
        }

        @Override
        public String getDisplayName() {
            return this.string;
        }
    }
}
