package client.onyx.module.hud.arraylist;

import client.onyx.Onyx;
import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easing;
import client.onyx.render.anim.Easings;
import client.onyx.render.anim.Spring;
import client.onyx.render.ui.HealthBarAnimator;
import client.onyx.render.ui.HotbarRenderer;
import client.onyx.render.ui.PanelRenderer;
import client.onyx.render.ui.StatusIcons;
import client.onyx.render.ui.UiRenderer;
import client.onyx.setting.Setting;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.ColorSetting;
import client.onyx.setting.impl.EnumSetSetting;
import client.onyx.setting.impl.EnumSetting;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.ui.component.ComponentHost;
import client.onyx.ui.theme.Palette;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.Typography;
import client.onyx.ui.theme.color.ColorUtils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import org.lwjgl.input.Mouse;

public final class ModuleList extends Module {
    private static final float FLOAT_100_01 = 100.0F;
    private static final float FLOAT_1_01 = 1.0F;
    private static final float FLOAT_2_01 = 2.0F;
    private static final float FLOAT_3_01 = 3.0F;
    private static final float FLOAT_14_01 = 14.0F;
    private static final float FLOAT_140_01 = 140.0F;
    private static final float FLOAT_4_01 = 4.0F;
    private static final float FLOAT_14_02 = 14.0F;
    private static final String STRING_CONST = "arraylist";
    private static final float FLOAT_0_01 = 0.0F;
    public EnumSetting<RowStyle> style = new EnumSetting<>("Style", RowStyle.ACCENT_BAR).describe("How each row is painted");
    public EnumSetting<RowAnimation> animation = new EnumSetting<>("Animation", RowAnimation.SLIDE_FADE).describe("How rows enter and leave");
    public EnumSetting<SortOrder> sort = new EnumSetting<>("Sort", SortOrder.WIDTH_DESC).describe("Row ordering");
    public EnumSetting<ColorMode> colors = new EnumSetting<>("Colors", ColorMode.ACCENT).describe("Where each row's colour comes from");
    public ColorSetting color = new ColorSetting("Color", -8691201).visibleWhen(this.colors, var0 -> var0 == ColorMode.STATIC);
    public ColorSetting firstColor = new ColorSetting("First Color", -8691201)
        .visibleWhen(this.colors, ColorMode::method_1479)
        .describe("Gradient: the top row. Fade: one end of the pulse");
    public ColorSetting secondColor = new ColorSetting("Second Color", -12598785)
        .visibleWhen(this.colors, ColorMode::method_1479)
        .describe("Gradient: the bottom row. Fade: the other end of the pulse");
    public NumberSetting colorSpeed = new NumberSetting("Color Speed", 1.0, 0.1, 5.0, 0.1).method_147("x").visibleWhen(this.colors, ColorMode::method_1480);
    public NumberSetting colorSpread = new NumberSetting("Color Spread", 12.0, 0.0, 60.0, 1.0)
        .visibleWhen(this.colors, ColorMode::method_1480)
        .describe("How far the colour shifts between neighbouring rows");
    public BooleanSetting suffix = new BooleanSetting("Suffix", true).describe("Show the module's mode next to its name");
    public BooleanSetting lowercase = new BooleanSetting("Lowercase", false).describe("Write every row in lower case");
    public EnumSetSetting<Category> categories = new EnumSetSetting<>(
            "Categories", Category.class, Category.COMBAT, Category.MOVEMENT, Category.PLAYER, Category.RENDER
        )
        .describe("Which categories the list shows");
    public BooleanSetting background = new BooleanSetting("Background", true).visibleWhen(() -> !this.style.method_168(RowStyle.TEXT));
    public BooleanSetting blur = new BooleanSetting("Blur", false)
        .visibleWhen(() -> !this.style.method_168(RowStyle.TEXT) && this.background.method_146())
        .describe("Frost each row over what is behind it (needs ClickGui's frosted glass)");
    public BooleanSetting textShadow = new BooleanSetting("Text Shadow", true).visibleWhen(() -> this.style.method_168(RowStyle.TEXT));
    public BooleanSetting outline = new BooleanSetting("Outline", false).visibleWhen(() -> this.style.method_168(RowStyle.PILLS));
    public NumberSetting scale = new NumberSetting("Scale", 1.0, 0.5, 2.0, 0.05).method_147("x").describe("Array list size");
    public NumberSetting rowSpacing = new NumberSetting("Row Spacing", 0.0, 0.0, 6.0, 0.5);
    public NumberSetting animationSpeed = new NumberSetting("Animation Speed", 1.0, 0.25, 3.0, 0.05).method_147("x");
    public NumberSetting positionX = new NumberSetting("Position X", 100.0, 0.0, 100.0, 0.01).visibleWhen(() -> false);
    public NumberSetting positionY = new NumberSetting("Position Y", 0.0, 0.0, 100.0, 0.01).visibleWhen(() -> false);
    private final Map<Module, ArrayListRenderer> map = new IdentityHashMap<>();
    private final List<Module> list = new ArrayList<>();
    private final Animation animation2 = new Animation(0.0F);
    private final Animation animation3 = new Animation(0.0F);
    private final Animation animation22 = new Animation(0.0F);
    private final ComponentHost componentHost = new ComponentHost();
    private final StatusIcons statusIcons = new StatusIcons();
    private boolean flag4 = true;
    private HealthBarAnimator.Ghost ghost;
    private boolean flag;
    private float floatValue;
    private boolean flag2;
    private long longValue;
    private float floatValue2;
    private float floatValue3;
    private boolean flag3;
    private float floatValue4;
    private float floatValue5;
    private float floatValue6;

    private float method_1481() {
        switch ((RowStyle)this.style.getValue()) {
            case ACCENT_BAR:
                return 4.0F;
            case PILLS:
                return 5.0F;
            case TEXT:
                return 0.0F;
            default:
                throw new MatchException(null, null);
        }
    }

    @Override
    public void onEnable() {
        this.flag2 = false;
        this.longValue = 0L;
    }

    private float method_1482(UiRenderer var1, Module var2, ArrayListRenderer var3) {
        float var5 = Math.min(var1.method_442(Typography.fontStyle9, this.method_1491(var2)), 140.0F);
        return var3.string == null ? var5 : var5 + 3.0F + var1.method_442(Typography.fontStyle8, var3.string);
    }

    public boolean method_1483(double var1, double var3) {
        return var1 >= this.floatValue2 && var1 < this.floatValue2 + this.floatValue4 && var3 >= this.floatValue && var3 < this.floatValue + this.floatValue6;
    }

    public void method_1484(UiRenderer var1, float var2, float var3) {
        float var4 = this.method_1492();
        boolean var12;
        if (this.isToggled() && mc.currentScreen instanceof GuiChat) {
            var12 = true;
        } else {
            var12 = false;
        }

        boolean var6 = this.method_1488(var1, var12, var4);
        Animation var14;
        float var15;
        ModuleList var16;
        if (!var6) {
            Animation var13 = this.animation2;
            var16 = this;
            var15 = 0.0F;
            var14 = var13;
        } else {
            var16 = this;
            var15 = 1.0F;
            var14 = this.animation2;
        }

        Animation var20;
        float var21;
        ModuleList var22;
        float var23;
        if (!var6) {
            var23 = 200.0F;
            var22 = var16;
            var21 = var15;
            var20 = var14;
        } else {
            var23 = 300.0F;
            var22 = var16;
            var21 = var15;
            var20 = var14;
        }

        float var10002 = var22.method_1493(var23);
        Animation var27;
        float var28;
        float var29;
        Easing var30;
        if (!var6) {
            float var26 = var10002;
            var30 = Easings.easing;
            var29 = var26;
            var28 = var21;
            var27 = var20;
        } else {
            var30 = Easings.easing7;
            var29 = var10002;
            var28 = var21;
            var27 = var20;
        }

        var27.method_603(var28, var29, var30);
        this.animation2.method_605(var4);
        if (this.map.isEmpty() && this.animation2.method_604() && this.animation2.method_606() <= 0.0F) {
            this.method_1500();
            this.floatValue4 = 0.0F;
            this.floatValue6 = 0.0F;
            this.flag3 = this.method_1485();
        } else {
            Palette var41 = Theme.getPalette();
            float var7 = this.scale.method_151();
            this.floatValue4 = this.animation3.method_606() * var7;
            this.floatValue6 = this.animation22.method_606() * var7;
            float var8 = var2 - this.floatValue4 - 0.0F;
            float var40 = var3 - this.floatValue6 - 0.0F;
            this.floatValue2 = 0.0F + method_1494(this.positionX, var8);
            this.floatValue = 0.0F + method_1494(this.positionY, var40);
            HotbarRenderer.method_857("arraylist", this.floatValue2, this.floatValue, this.floatValue4, this.floatValue6);
            boolean var10 = this.method_1501(var12, var8, var40, var2, var3);
            if (var12 && var10 && !this.flag) {
                float[] var11;
                if ((var11 = this.statusIcons.method_865(true))[0] != 0.0F || var11[1] != 0.0F) {
                    method_1495(this.positionX, method_1494(this.positionX, var8) + var11[0], var8);
                    method_1495(this.positionY, method_1494(this.positionY, var40) + var11[1], var40);
                }
            } else {
                this.statusIcons.method_865(false);
            }

            this.floatValue2 = 0.0F + method_1494(this.positionX, var8);
            this.floatValue = 0.0F + method_1494(this.positionY, var40);
            this.floatValue2 = Math.round(this.floatValue2);
            this.floatValue = Math.round(this.floatValue);
            ModuleList var32;
            boolean var33;
            if (!(this.positionX.getValue() > 50.0)) {
                var33 = false;
                var32 = this;
            } else {
                var33 = true;
                var32 = this;
            }

            ComponentHost var36;
            float var37;
            boolean var38;
            ModuleList var39;
            label60: {
                var32.flag4 = var33;
                ComponentHost var34;
                float var35;
                if (!var12) {
                    var35 = var4;
                    var34 = this.componentHost;
                } else {
                    if (var10) {
                        var39 = this;
                        var38 = true;
                        var37 = var4;
                        var36 = this.componentHost;
                        break label60;
                    }

                    var35 = var4;
                    var34 = this.componentHost;
                }

                var39 = this;
                var38 = false;
                var37 = var35;
                var36 = var34;
            }

            var36.method_2220(var37, var38, var39.flag);
            if (this.flag) {
                HealthBarAnimator.method_862(var1, var2, var3, this.ghost);
            }

            var1.method_440(this.animation2.method_606());
            var1.method_441();
            var1.method_451(this.floatValue2, this.floatValue);
            var1.method_491(var7, 0.0F, 0.0F);
            this.method_1489(var1, var41);
            var1.method_435();
            var1.method_430();
            this.method_1500();
        }
    }

    private boolean method_1485() {
        return Mouse.isButtonDown(0);
    }

    private static String method_1486(Module var0) {
        String var3;
        if ((var3 = var0.getSuffix()) != null && !var3.isEmpty()) {
            return var3;
        } else {
            Iterator var4 = var0.getVisibleSettings().iterator();

            while (var4.hasNext()) {
                Setting var2;
                if ((var2 = (Setting)var4.next()) instanceof EnumSetting) {
                    return EnumSetting.method_167((Enum<?>)((EnumSetting)var2).getValue());
                }
            }

            return null;
        }
    }

    public ModuleList() {
        super("Array List", "Lists every enabled module", Category.HUD);
        this.ghost = HealthBarAnimator.ghost;
    }

    private int method_1487(int var1, int var2, Module var3, Palette var4) {
        switch ((ColorMode)this.colors.getValue()) {
            case ACCENT:
                return var4.intValue31();
            case STATIC:
                return this.color.getValue();
            case FADE:
                double var15 = method_1490() * this.colorSpeed.method_151() + Math.toRadians(var1 * this.colorSpread.method_151());
                return Spring.method_616(this.firstColor.getValue(), this.secondColor.getValue(), (float)(Math.sin(var15) + 1.0) / 2.0F);
            case GRADIENT:
                int var16 = this.firstColor.getValue();
                int var10001 = this.secondColor.getValue();
                int var9;
                int var10;
                float var11;
                if (var2 > 1) {
                    int var8 = var10001;
                    int var7 = var16;
                    var11 = (float)var1 / (var2 - 1);
                    var10 = var8;
                    var9 = var7;
                } else {
                    var11 = 0.0F;
                    var10 = var10001;
                    var9 = var16;
                }

                return Spring.method_616(var9, var10, var11);
            case RAINBOW:
                float var5;
                float var12;
                if ((var5 = (float)(method_1490() * 60.0 * this.colorSpeed.method_151() + var1 * this.colorSpread.method_151()) % 360.0F) < 0.0F) {
                    var12 = var5 + 360.0F;
                } else {
                    var12 = var5;
                }

                return ColorUtils.method_202(var12, 0.75F, 1.0F, 255);
            case CATEGORY:
                return ColorUtils.method_202(var3.getCategory().ordinal() * 67.0F % 360.0F, 0.7F, 1.0F, 255);
            default:
                throw new MatchException(null, null);
        }
    }

    private boolean method_1488(UiRenderer var1, boolean var2, float var3) {
        var var6 = this;
        this.list.clear();
        Iterator var36 = Onyx.moduleManager.getArrayList().iterator();

        label115:
        while (true) {
            Iterator var7 = var36;

            while (var7.hasNext()) {
                Module var4;
                if ((var4 = (Module)var36.next()) == var6) {
                    continue label115;
                }

                if (var4 == Onyx.moduleManager.clickGUI) {
                    var7 = var36;
                } else {
                    boolean var8;
                    if (var6.isToggled() && var4.isToggled() && var6.categories.method_79(var4.getCategory())) {
                        var8 = true;
                    } else {
                        var8 = false;
                    }

                    ArrayListRenderer var32 = var6.map.get(var4);
                    if (var8 && var32 == null) {
                        var32 = new ArrayListRenderer(0.0F);
                        var6.map.put(var4, var32);
                    }

                    if (var32 != null) {
                        if (var8) {
                            var32.string = var6.suffix.method_146() ? method_1486(var4) : null;
                            if (var32.string != null && var6.lowercase.method_146()) {
                                var32.string = var32.string.toLowerCase(Locale.ROOT);
                            }

                            var32.floatValue = var6.method_1482(var1, var4, var32);
                            var6.list.add(var4);
                        }

                        Animation var10;
                        float var11;
                        ModuleList var12;
                        if (!var8) {
                            Animation var9 = var32.animation;
                            var12 = var6;
                            var11 = 0.0F;
                            var10 = var9;
                        } else {
                            var12 = var6;
                            var11 = 1.0F;
                            var10 = var32.animation;
                        }

                        Animation var16;
                        float var17;
                        ModuleList var18;
                        float var19;
                        if (!var8) {
                            var19 = 200.0F;
                            var18 = var12;
                            var17 = var11;
                            var16 = var10;
                        } else {
                            var19 = 250.0F;
                            var18 = var12;
                            var17 = var11;
                            var16 = var10;
                        }

                        float var10002 = var18.method_1493(var19);
                        Animation var23;
                        float var24;
                        float var25;
                        Easing var26;
                        if (!var8) {
                            float var22 = var10002;
                            var26 = Easings.easing;
                            var25 = var22;
                            var24 = var17;
                            var23 = var16;
                        } else {
                            var26 = Easings.easing7;
                            var25 = var10002;
                            var24 = var17;
                            var23 = var16;
                        }

                        var23.method_603(var24, var25, var26);
                        var32.animation.method_605(var3);
                        var32.animation2.method_605(var3);
                        if (!var8 && var32.animation.method_604() && var32.animation.method_606() <= 0.0F) {
                            var6.map.remove(var4);
                        }
                        continue label115;
                    }

                    var7 = var36;
                }
            }

            var6.list.sort(var6.method_1498());
            float var37 = 14.0F + var6.rowSpacing.method_151();
            float var38 = var6.method_1481();
            float var5 = 0.0F;
            int var33 = 0;

            for (int var29 = 0; var29 < var6.list.size(); var29 = var33) {
                ArrayListRenderer var35;
                ArrayListRenderer var10000 = var35 = var6.map.get(var6.list.get(var33));
                var10000.intValue = var33;
                float var28;
                if (!var10000.flag) {
                    var35.animation2.method_607(var33 * var37);
                    var35.flag = true;
                    var28 = var5;
                } else {
                    var35.animation2.method_603(var33 * var37, var6.method_1493(100.0F), Easings.easing2);
                    var28 = var5;
                }

                var33++;
                var5 = Math.max(var28, var35.floatValue);
            }

            float var30;
            int var34;
            if ((var34 = var6.list.size()) == 0) {
                var30 = 0.0F;
            } else {
                var30 = var5 + var38 * 2.0F + var6.method_1499();
            }

            float var31;
            if (var34 == 0) {
                var31 = 0.0F;
            } else {
                var31 = var34 * var37 - var6.rowSpacing.method_151();
            }

            if (var34 > 0) {
                var6.animation3.method_603(var30, var6.method_1493(300.0F), Easings.easing2);
                var6.animation22.method_603(var31, var6.method_1493(300.0F), Easings.easing2);
            }

            var6.animation3.method_605(var3);
            var6.animation22.method_605(var3);
            if (var34 > 0) {
                return true;
            }

            return false;
        }
    }

    private void method_1489(UiRenderer var1, Palette var2) {
        UiRenderer var35 = var1;
        var var36 = this;
        RowStyle var10 = this.style.getValue();
        boolean var19;
        if (this.background.method_146() && var10 != RowStyle.TEXT && this.blur.method_146()) {
            var19 = true;
        } else {
            var19 = false;
        }

        boolean var4 = var19;
        PanelRenderer.method_601(var19);
        float var5 = this.animation3.method_606();
        float var6 = this.animation22.method_606();
        float var7 = this.method_1481();
        int var8 = this.list.size();
        Iterator var9 = this.map.entrySet().iterator();

        label61:
        while (true) {
            for (Iterator var20 = var9; var20.hasNext(); var20 = var9) {
                Entry var3;
                Module var11 = (Module)(var3 = (Entry)var9.next()).getKey();
                float var12;
                ArrayListRenderer var37;
                if (!((var12 = (var37 = (ArrayListRenderer)var3.getValue()).animation.method_606()) <= 0.001F)) {
                    int var13 = var36.method_1487(var37.intValue, var8, var11, var2);
                    float var14 = var37.floatValue + var7 * 2.0F + var36.method_1499();
                    float var21;
                    if (var36.flag4) {
                        var21 = var5 - var14;
                    } else {
                        var21 = 0.0F;
                    }

                    float var16 = var37.animation2.method_606();
                    RowAnimation var17;
                    if ((var17 = var36.animation.getValue()).method_1477()) {
                        var35.method_440(var12);
                    }

                    var35.method_441();
                    if (var17.method_1478()) {
                        float var18 = (1.0F - var12) * 14.0F;
                        UiRenderer var23;
                        float var24;
                        if (!var36.flag4) {
                            var24 = -var18;
                            var23 = var35;
                        } else {
                            var24 = var18;
                            var23 = var35;
                        }

                        var23.method_451(var24, 0.0F);
                    }

                    if (var17.method_1476()) {
                        UiRenderer var27;
                        float var28;
                        float var29;
                        if (!var36.flag4) {
                            var29 = 0.0F;
                            var28 = var21;
                            var27 = var35;
                        } else {
                            var29 = var14;
                            var28 = var21;
                            var27 = var35;
                        }

                        var27.method_451(var28 + var29, var16 + 7.0F);
                        var35.method_491(0.9F + 0.1F * var12, 0.0F, 0.0F);
                        UiRenderer var32;
                        float var33;
                        float var34;
                        if (!var36.flag4) {
                            var34 = 0.0F;
                            var33 = var21;
                            var32 = var35;
                        } else {
                            var34 = var14;
                            var33 = var21;
                            var32 = var35;
                        }

                        var32.method_451(-(var33 + var34), -(var16 + 7.0F));
                    }

                    var36.method_1497(var35, var2, var10, var11, var37, var21, var16, var14, var7, var13, var4);
                    var35.method_435();
                    if (var17.method_1477()) {
                        var35.method_430();
                    }
                    continue label61;
                }
            }

            var36.componentHost.method_2219(var35, 0.0F, 0.0F, var5, var6, 0.0F, var2.intValue15());
            return;
        }
    }

    private static double method_1490() {
        return System.nanoTime() / 1.0E9;
    }

    private String method_1491(Module var1) {
        return !this.lowercase.method_146() ? var1.getName() : var1.getName().toLowerCase(Locale.ROOT);
    }

    private float method_1492() {
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

    private float method_1493(float var1) {
        return var1 / this.animationSpeed.method_151();
    }

    private static float method_1494(NumberSetting var0, float var1) {
        return var1 <= 0.0F ? 0.0F : (float)(var1 * var0.getValue() / 100.0);
    }

    private static void method_1495(NumberSetting var0, float var1, float var2) {
        if (!(var2 <= 0.0F)) {
            var0.setValue(Math.clamp(var1 / var2, 0.0F, 1.0F) * 100.0);
        }
    }

    public boolean method_1496() {
        return mc.thePlayer != null && mc.theWorld != null && (this.isToggled() || this.flag2);
    }

    private void method_1497(
        UiRenderer var1,
        Palette var2,
        RowStyle var3,
        Module var4,
        ArrayListRenderer var5,
        float var6,
        float var7,
        float var8,
        float var9,
        int var10,
        boolean var11
    ) {
        float var12 = this.method_1499();
        float var42 = var6 + var8 - var9;
        float var19;
        float var20;
        if (!this.flag4) {
            float var18 = var42;
            var20 = 0.0F;
            var19 = var18;
        } else {
            var20 = var12;
            var19 = var42;
        }

        float var13 = var19 - var20;
        var42 = var6 + var9;
        float var22;
        float var23;
        if (!this.flag4) {
            float var21 = var42;
            var23 = var12;
            var22 = var21;
        } else {
            var23 = 0.0F;
            var22 = var42;
        }

        var9 = var22 + var23;
        float var14 = var7 + 7.0F;
        int var24;
        if (var3 == RowStyle.ACCENT_BAR) {
            var24 = var2.intValue15();
        } else {
            var24 = var10;
        }

        int var25;
        if (var3 == RowStyle.TEXT) {
            var25 = var10;
        } else {
            var25 = var2.intValue15();
        }

        int var16 = Theme.method_180(var25, 0.6F);

        String var41 = (switch (var3) {
            case ACCENT_BAR -> {
                UiRenderer var27;
                label80: {
                    if (this.background.method_146()) {
                        int var17 = Theme.method_180(var2.intValue32(), 0.55F);
                        if (this.rowSpacing.method_151() > 0.0F) {
                            PanelRenderer.method_597(var1, var6, var7, var8, 14.0F, 0.0F, var17, var11);
                            var27 = var1;
                            break label80;
                        }

                        var1.method_443(var6, var7, var8, 14.0F);
                        PanelRenderer.method_597(var1, var6, var7 - 4.0F, var8, 22.0F, 0.0F, var17, var11);
                        var1.method_486();
                    }

                    var27 = var1;
                }

                UiRenderer var30;
                float var31;
                float var32;
                if (!this.flag4) {
                    var32 = var7;
                    var31 = var6;
                    var30 = var27;
                } else {
                    float var10001 = var6 + var8 - var12;
                    var32 = var7;
                    var31 = var10001;
                    var30 = var27;
                }

                var30.method_434(var31, var32, var12, 14.0F, var10);
                yield var1;
            }
            case PILLS -> {
                if (this.background.method_146()) {
                    PanelRenderer.method_597(var1, var6, var7, var8, 14.0F, 8.0F, Theme.method_180(var2.intValue32(), 0.72F), var11);
                    var1.method_479(var6, var7, var8, 14.0F, 8.0F, Theme.method_180(var10, 0.22F));
                }

                if (this.outline.method_146()) {
                    var1.method_494(var6, var7, var8, 14.0F, 8.0F, 1.0F, Theme.method_180(var10, 0.5F));
                }

                yield var1;
            }
            default -> var1;
        }).method_469(Typography.fontStyle9, this.method_1491(var4), 140.0F);
        String var39 = var5.string;
        float var34;
        if (var5.string == null) {
            var34 = 0.0F;
        } else {
            var34 = var1.method_442(Typography.fontStyle8, var39) + 3.0F;
        }

        ModuleList var35;
        label73: {
            if (var3 == RowStyle.TEXT && this.textShadow.method_146()) {
                int var38 = Theme.method_180(var2.intValue29(), 0.075F);
                if (this.flag4) {
                    var1.method_476(Typography.fontStyle9, var41, var13 - var34 + 1.0F, var14 + 1.0F, var38);
                    if (var39 != null) {
                        var1.method_476(Typography.fontStyle8, var39, var13 + 1.0F, var14 + 1.0F, var38);
                        var35 = this;
                        break label73;
                    }
                } else {
                    var1.method_432(Typography.fontStyle9, var41, var9 + 1.0F, var14 + 1.0F, var38);
                    if (var39 != null) {
                        var1.method_432(Typography.fontStyle8, var39, var9 + var1.method_442(Typography.fontStyle9, var41) + 3.0F + 1.0F, var14 + 1.0F, var38);
                    }
                }
            }

            var35 = this;
        }

        if (var35.flag4) {
            var1.method_476(Typography.fontStyle9, var41, var13 - var34, var14, var24);
            if (var39 != null) {
                var1.method_476(Typography.fontStyle8, var39, var13, var14, var16);
                return;
            }
        } else {
            var1.method_432(Typography.fontStyle9, var41, var9, var14, var24);
            if (var39 != null) {
                var1.method_432(Typography.fontStyle8, var39, var9 + var1.method_442(Typography.fontStyle9, var41) + 3.0F, var14, var16);
            }
        }
    }

    @Override
    public void onDisable() {
        this.flag2 = true;
        this.flag = false;
    }

    private Comparator<Module> method_1498() {
        switch ((SortOrder)this.sort.getValue()) {
            case WIDTH_DESC:
                return Comparator.<Module>comparingDouble(var1 -> -this.map.get(var1).floatValue).thenComparing(Module::getName);
            case WIDTH_ASC:
                return Comparator.<Module>comparingDouble(var1 -> this.map.get(var1).floatValue).thenComparing(Module::getName);
            case ALPHABETICAL:
                return Comparator.comparing(Module::getName);
            default:
                throw new MatchException(null, null);
        }
    }

    private float method_1499() {
        return this.style.method_168(RowStyle.ACCENT_BAR) ? 2.0F : 0.0F;
    }

    private void method_1500() {
        if (!this.isToggled() && this.map.isEmpty() && this.animation2.method_604() && !(this.animation2.method_606() > 0.0F)) {
            this.flag2 = false;
            this.longValue = 0L;
            this.floatValue4 = 0.0F;
            this.floatValue6 = 0.0F;
            HotbarRenderer.method_855("arraylist");
        }
    }

    private boolean method_1501(boolean var1, float var2, float var3, float var4, float var5) {
        float var6 = Minecraft.getScaledMouseX() * var4 / mc.displayWidth;
        float var11 = var5 - Minecraft.getScaledMouseY() * var5 / mc.displayHeight - 1.0F;
        boolean var8 = this.method_1485();
        boolean var9 = this.method_1483(var6, var11);
        ModuleList var10;
        if (var1 && var8) {
            if (!this.flag3 && var9) {
                this.flag = true;
                this.floatValue3 = var6 - this.floatValue2;
                this.floatValue5 = var11 - this.floatValue;
            }

            var10 = this;
        } else {
            this.flag = false;
            this.ghost = HealthBarAnimator.ghost;
            var10 = this;
        }

        var10.flag3 = var8;
        if (this.flag) {
            this.ghost = HealthBarAnimator.method_861(
                var6 - this.floatValue3, var11 - this.floatValue5, this.floatValue4, this.floatValue6, var4, var5, 0.0F, HotbarRenderer.method_856("arraylist")
            );
            method_1495(this.positionX, this.ghost.floatValue4() - 0.0F, var2);
            method_1495(this.positionY, this.ghost.floatValue2() - 0.0F, var3);
        }

        return var9;
    }
}
