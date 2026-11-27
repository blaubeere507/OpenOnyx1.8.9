package client.onyx.module.render;

import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.render.ui.UiRenderer;
import client.onyx.render.world.BoxRenderer;
import client.onyx.render.world.EntityBounds;
import client.onyx.render.world.WorldProjection;
import client.onyx.setting.NamedEnum;
import client.onyx.setting.SettingSection;
import client.onyx.setting.TabbedSettingGroup;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.ColorSetting;
import client.onyx.setting.impl.EnumSetting;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.ui.theme.color.ColorUtils;
import client.onyx.util.TargetGroup;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;

public final class Boxes extends Module {
    private static final double DOUBLE_0_1251 = 0.125;
    private static final double DOUBLE_0_061 = 0.06;
    private static final float FLOAT_2_01 = 2.0F;
    public final EnumSetting<Boxes.Shape> shape = new EnumSetting<>("Shape", Boxes.Shape.TWO_D)
        .describe("A flat rectangle around the entity, or a cuboid sitting on it in the world");
    public final EnumSetting<Boxes.BoxStyle> style = new EnumSetting<>("Style", Boxes.BoxStyle.OUTLINE)
        .describe("Lines only, or lines over a translucent fill");
    public final NumberSetting fillOpacity = new NumberSetting("Fill opacity", 18.0, 1.0, 60.0, 1.0)
        .method_147("%")
        .describe("How solid the fill sits behind the outline")
        .visibleWhen(() -> this.style.method_168(Boxes.BoxStyle.FILLED));
    public final NumberSetting lineWidth = new NumberSetting("Line width", 1.5, 0.5, 5.0, 0.5).method_147(" px");
    public final BooleanSetting throughWalls = new BooleanSetting("Through walls", true)
        .describe("Keeps the box visible when the entity is behind blocks")
        .visibleWhen(() -> this.shape.method_168(Boxes.Shape.THREE_D));
    public final NumberSetting distance = new NumberSetting("Distance", 256.0, 8.0, 256.0, 8.0)
        .method_147(" m")
        .describe("Maximum distance at which a box is drawn");
    public final Boxes.TargetSettings targetSettings = new Boxes.TargetSettings();
    private final UiRenderer uiRenderer = new UiRenderer().method_460();

    private static AxisAlignedBB method_1076(AxisAlignedBB var0) {
        double var1 = var0.maxY - var0.minY;
        var1 = Math.min(0.125, var1 * 0.5);
        return new AxisAlignedBB(var0.minX, var0.minY, var0.minZ, var0.maxX, var0.maxY - var1, var0.maxZ);
    }

    private static AxisAlignedBB method_1077(EntityLivingBase var0, float var1) {
        double var2 = var0.lastTickPosX + (var0.posX - var0.lastTickPosX) * var1 - var0.posX;
        double var4 = var0.lastTickPosY + (var0.posY - var0.lastTickPosY) * var1 - var0.posY;
        double var6 = var0.lastTickPosZ + (var0.posZ - var0.lastTickPosZ) * var1 - var0.posZ;
        AxisAlignedBB var9 = var0.getEntityBoundingBox().offset(var2, var4, var6).expand(0.06, 0.06, 0.06);
        return method_1078(var0) ? method_1076(var9) : var9;
    }

    public Boxes() {
        super("Boxes", "Outlines entities with a 2D or 3D box", Category.RENDER);
    }

    private static boolean method_1078(EntityLivingBase var0) {
        return var0 instanceof EntityPlayer && ((EntityPlayer)var0).isSneaking();
    }

    public void method_1079(float var1) {
        var var2 = this;
        if (this.method_1081(Boxes.Shape.TWO_D) && WorldProjection.isFlag()) {
            List var6;
            if (!(var6 = this.method_1083()).isEmpty()) {
                ScaledResolution var5 = new ScaledResolution(mc);
                float var12 = var5.getScaledWidth();
                float var11 = var5.getScaledHeight();
                this.uiRenderer.method_468();
                Iterator var4 = var6.iterator();

                label31:
                while (true) {
                    Iterator var8 = var4;

                    while (var8.hasNext()) {
                        Boxes.BoxData var3;
                        EntityBounds.Projected var9;
                        if ((var9 = EntityBounds.method_551((var3 = (Boxes.BoxData)var4.next()).entityLivingBase(), var1, var12, var11)) == null) {
                            continue label31;
                        }

                        if (var9.floatValue4() < 2.0F) {
                            var8 = var4;
                        } else {
                            int var10 = var3.intValue();
                            float var7 = var2.lineWidth.method_151();
                            if (var2.style.method_168(Boxes.BoxStyle.FILLED)) {
                                var2.uiRenderer
                                    .method_434(var9.floatValue2(), var9.floatValue(), var9.floatValue4(), var9.floatValue3(), var2.method_1082(var10));
                            }

                            var2.uiRenderer.method_494(var9.floatValue2(), var9.floatValue(), var9.floatValue4(), var9.floatValue3(), 0.0F, var7, var10);
                            var8 = var4;
                        }
                    }

                    var2.uiRenderer.method_483();
                    return;
                }
            }
        }
    }

    public void method_1080(float var1) {
        var var2 = this;
        if (this.method_1081(Boxes.Shape.THREE_D)) {
            List var5;
            if (!(var5 = this.method_1083()).isEmpty()) {
                if (BoxRenderer.method_519(var1, this.throughWalls.method_146())) {
                    Iterator var6;
                    for (Iterator var4 = var6 = var5.iterator(); var4.hasNext(); var4 = var6) {
                        Boxes.BoxData var3 = (Boxes.BoxData)var6.next();
                        int var7 = var3.intValue();
                        BoxRenderer.method_518(method_1077(var3.entityLivingBase(), var1), var7, var2.lineWidth.method_151(), var2.method_1082(var7));
                    }

                    BoxRenderer.method_521();
                }
            }
        }
    }

    private boolean method_1081(Boxes.Shape var1) {
        return this.isToggled() && this.shape.method_168(var1) && mc.thePlayer != null && mc.theWorld != null;
    }

    private int method_1082(int var1) {
        return this.style.method_168(Boxes.BoxStyle.FILLED) ? ColorUtils.method_209(var1, this.fillOpacity.method_151() / 100.0F) : 0;
    }

    private List<Boxes.BoxData> method_1083() {
        var var1 = this;
        ArrayList var4 = new ArrayList();
        double var2 = this.distance.getValue() * this.distance.getValue();
        Iterator var8 = mc.theWorld.loadedEntityList.iterator();

        label26:
        while (true) {
            Iterator var7 = var8;

            while (var7.hasNext()) {
                Entity var5;
                if (!((var5 = (Entity)var8.next()) instanceof EntityLivingBase)) {
                    continue label26;
                }

                TargetGroup var6;
                EntityLivingBase var9;
                if ((var6 = TargetGroup.method_2141(var9 = (EntityLivingBase)var5)) == null) {
                    var7 = var8;
                } else {
                    Boxes.ColorSettings var10;
                    if (!(var10 = var1.targetSettings.method_1086(var6)).method_61()) {
                        var7 = var8;
                    } else if (mc.thePlayer.getDistanceSqToEntity(var9) > var2) {
                        var7 = var8;
                    } else {
                        var4.add(new Boxes.BoxData(var9, var10.color.method_118()));
                        var7 = var8;
                    }
                }
            }

            var4.sort(Comparator.<Boxes.BoxData>comparingDouble(var0 -> mc.thePlayer.getDistanceSqToEntity(var0.entityLivingBase())).reversed());
            return var4;
        }
    }

    private record BoxData(EntityLivingBase entityLivingBase, int intValue) {
    }

    public static enum BoxStyle implements NamedEnum {
        OUTLINE("Outline"),
        FILLED("Outline + fill");

        private final String string;

        private BoxStyle(String var3) {
            this.string = var3;
        }

        @Override
        public String getDisplayName() {
            return this.string;
        }
    }

    public static final class ColorSettings extends SettingSection {
        public final ColorSetting color;

        private ColorSettings(String var1, boolean var2, int var3, boolean var4) {
            super(var1, var2);
            this.color = new ColorSetting("Color", var3);
            if (var4) {
                this.color.method_116();
            }
        }
    }

    public static enum Shape implements NamedEnum {
        TWO_D("2D"),
        THREE_D("3D");

        private final String string;

        private Shape(String var3) {
            this.string = var3;
        }

        @Override
        public String getDisplayName() {
            return this.string;
        }
    }

    public static final class TargetSettings extends TabbedSettingGroup {
        public final Boxes.ColorSettings self = new Boxes.ColorSettings("Self", false, -2562832, true);
        public final Boxes.ColorSettings enemy = new Boxes.ColorSettings("Enemy", true, -3910337, true);
        public final Boxes.ColorSettings team = new Boxes.ColorSettings("Team", false, -8585312, false);
        public final Boxes.ColorSettings mobs = new Boxes.ColorSettings("Mobs", false, -3105793, false);

        private TargetSettings() {
            super("Targets", TargetGroup.stringArray);
        }

        public Boxes.ColorSettings method_1086(TargetGroup var1) {
            switch (var1) {
                case SELF:
                    return this.self;
                case ENEMY:
                    return this.enemy;
                case TEAM:
                    return this.team;
                case MOBS:
                    return this.mobs;
                default:
                    throw new MatchException(null, null);
            }
        }
    }
}
