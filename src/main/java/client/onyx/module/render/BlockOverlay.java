package client.onyx.module.render;

import client.onyx.Onyx;
import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.module.combat.BedBreaker;
import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easings;
import client.onyx.render.world.BlockOverlayRenderer;
import client.onyx.render.world.BlockOverlayState;
import client.onyx.setting.NamedEnum;
import client.onyx.setting.SettingGroup;
import client.onyx.setting.SettingSection;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.ColorSetting;
import client.onyx.setting.impl.EnumSetting;
import client.onyx.setting.impl.NumberSetting;
import java.util.Objects;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

public final class BlockOverlay extends Module {
    private static final float FLOAT_70_01 = 70.0F;
    private static final float FLOAT_60_01 = 60.0F;
    private static final float FLOAT_0_0041 = 0.004F;
    private static final double DOUBLE_16_01 = 16.0;
    private static final float FLOAT_100_01 = 100.0F;
    public final EnumSetting<BlockOverlay.Theme> theme = new EnumSetting<>("Theme", BlockOverlay.Theme.FILL)
        .describe("Outline on its own, over a transparent fill, or over the block's own texture");
    public final ColorSetting color = new ColorSetting("Color", -10178561).method_116().method_127();
    public final ColorSetting secondColor = new ColorSetting("Second color", -7055617)
        .method_116()
        .method_125(48.0)
        .describe("The other end of the glint's gradient")
        .visibleWhen(this.theme, var0 -> var0 == BlockOverlay.Theme.GLINT);
    public final NumberSetting lineWidth = new NumberSetting("Line width", 1.5, 0.5, 5.0, 0.5).method_147(" px");
    public final NumberSetting outlineOpacity = new NumberSetting("Outline opacity", 90.0, 5.0, 100.0, 5.0).method_147("%");
    public final NumberSetting fillOpacity = new NumberSetting("Fill opacity", 22.0, 1.0, 80.0, 1.0)
        .method_147("%")
        .visibleWhen(this.theme, var0 -> var0 != BlockOverlay.Theme.OUTLINE);
    public final NumberSetting padding = new NumberSetting("Padding", 0.6, 0.0, 5.0, 0.1)
        .method_147("%")
        .describe("How far the box stands off the block face, as a fraction of a block");
    public final BlockOverlay.GlintSettings glintSettings = new BlockOverlay.GlintSettings();
    public final BlockOverlay.AnimationSettings animationSettings = new BlockOverlay.AnimationSettings();
    public final BlockOverlay.BreakProgressSettings breakProgressSettings = new BlockOverlay.BreakProgressSettings();
    public final BooleanSetting onlyOnBedBreak = new BooleanSetting("Only on bed break", false)
        .describe("Hides the overlay unless BedBreaker is mining, so it marks the bed and nothing else");
    public final BooleanSetting throughWalls = new BooleanSetting("Through walls", true)
        .describe("Draws the whole box and its progress even when the block is buried");
    public final BooleanSetting hideVanillaOutline = new BooleanSetting("Hide vanilla outline", true)
        .describe("Suppresses the black wireframe this overlay is standing in for");
    private final Animation[] animationArray = new Animation[]{
        new Animation(0.0F), new Animation(0.0F), new Animation(0.0F), new Animation(0.0F), new Animation(0.0F), new Animation(0.0F)
    };
    private final Animation animation3 = new Animation(0.0F);
    private final Animation animation = new Animation(1.0F);
    private final Animation animation2 = new Animation(0.0F);
    private final BlockOverlayRenderer blockOverlayRenderer = new BlockOverlayRenderer();
    private double doubleValue;
    private float floatValue;
    private BlockPos blockPos;
    private BlockPos blockPos2;
    private boolean flag;
    private double doubleValue2;
    private double doubleValue3;
    private long longValue;

    private void method_1252(float var1) {
        float var6 = var1;
        Animation[] var4 = this.animationArray;
        int var3 = this.animationArray.length;
        int var2 = 0;

        for (int var5 = 0; var5 < var3; var5 = var2) {
            var4[var2++].method_605(var6);
        }

        this.animation3.method_605(var6);
        this.animation.method_605(var6);
        this.animation2.method_605(var6);
    }

    private void method_1253(AxisAlignedBB var1) {
        float var3 = this.animationSettings.move.method_151();
        this.animationArray[0].method_603((float)(var1.minX - this.doubleValue2), var3, Easings.easing2);
        this.animationArray[1].method_603((float)(var1.minY - this.doubleValue3), var3, Easings.easing2);
        this.animationArray[2].method_603((float)(var1.minZ - this.doubleValue), var3, Easings.easing2);
        this.animationArray[3].method_603((float)(var1.maxX - this.doubleValue2), var3, Easings.easing2);
        this.animationArray[4].method_603((float)(var1.maxY - this.doubleValue3), var3, Easings.easing2);
        this.animationArray[5].method_603((float)(var1.maxZ - this.doubleValue), var3, Easings.easing2);
    }

    public void method_1254(float var1) {
        float var7 = this.method_1259();
        if (this.isToggled() && mc.theWorld != null && mc.thePlayer != null) {
            BlockPos var3;
            AxisAlignedBB var5;
            if ((var3 = this.method_1258(this.method_1261(), var7)) == null) {
                var5 = null;
            } else {
                var5 = this.method_1264(var3);
            }

            BlockOverlay var6;
            label27: {
                if (var5 == null) {
                    if (this.flag) {
                        this.flag = false;
                        this.animation3.method_603(0.0F, this.animationSettings.fade.method_151(), Easings.easing);
                        this.animation
                            .method_603(this.animationSettings.appearance.getValue().getFloatValue(), this.animationSettings.fade.method_151(), Easings.easing);
                        this.animation2.method_607(0.0F);
                        var6 = this;
                        break label27;
                    }
                } else {
                    this.method_1255(var3, var5);
                    this.animation2.method_603(this.method_1262(), 70.0F, Easings.easing8);
                }

                var6 = this;
            }

            var6.method_1252(var7);
            float var8 = this.animation3.method_606();
            if (!this.flag && var8 <= 0.004F) {
                this.blockPos2 = null;
            } else {
                this.blockOverlayRenderer.method_557(var1, this.blockPos2, this.method_1260(), var8, this.animation2.method_606(), this.method_1256());
            }
        } else {
            this.method_1257();
        }
    }

    private void method_1255(BlockPos var1, AxisAlignedBB var2) {
        boolean var5;
        if (this.blockPos2 == null) {
            var5 = true;
        } else {
            var5 = false;
        }

        boolean var6;
        if (!var5 && var1.distanceSq(this.blockPos2.getX(), this.blockPos2.getY(), this.blockPos2.getZ()) > 16.0) {
            var6 = true;
        } else {
            var6 = false;
        }

        if (!var1.equals(this.blockPos2)) {
            this.animation2.method_607(0.0F);
        }

        this.blockPos2 = var1;
        this.flag = true;
        boolean var7;
        if (!var5 && !var6) {
            this.method_1253(var2);
            var7 = var5;
        } else {
            this.doubleValue2 = var2.minX;
            this.doubleValue3 = var2.minY;
            this.doubleValue = var2.minZ;
            this.method_1265(var2);
            var7 = var5;
        }

        if (var7) {
            this.animation3.method_607(0.0F);
            this.animation.method_607(this.animationSettings.appearance.getValue().getFloatValue());
        }

        this.animation3.method_603(1.0F, this.animationSettings.appear.method_151(), Easings.easing7);
        this.animation.method_603(1.0F, this.animationSettings.appear.method_151(), Easings.easing7);
    }

    private BlockOverlayState method_1256() {
        float var2 = this.fillOpacity.method_151() / 100.0F;
        return new BlockOverlayState(
            this.theme.getValue(),
            this.color.getValue(),
            this.secondColor.method_118(),
            this.lineWidth.method_151(),
            this.outlineOpacity.method_151() / 100.0F,
            var2,
            Math.clamp(var2 * 3.0F, 0.0F, 1.6F),
            this.glintSettings.glintSpeed.method_151(),
            this.glintSettings.glintScale.method_151(),
            this.glintSettings.glintGlow.method_151() / 100.0F,
            this.breakProgressSettings.method_61(),
            this.breakProgressSettings.mode.getValue(),
            this.breakProgressSettings.direction.method_168(BlockOverlay.BreakDirection.UP),
            this.breakProgressSettings.progressOpacity.method_151() / 100.0F,
            this.throughWalls.method_146()
        );
    }

    public BlockOverlay() {
        super("BlockOverlay", "Highlights the block you are looking at", Category.RENDER);
        this.glintSettings.visibleWhen(() -> this.theme.method_168(BlockOverlay.Theme.GLINT));
    }

    @Override
    public void onDisable() {
        this.method_1257();
        this.blockOverlayRenderer.method_556();
    }

    private void method_1257() {
        this.blockPos = this.blockPos2 = null;
        this.floatValue = 0.0F;
        this.flag = false;
        this.longValue = 0L;
        Animation[] var1 = this.animationArray;
        int var2 = this.animationArray.length;
        int var5 = 0;

        for (int var4 = 0; var4 < var2; var4 = var5) {
            var1[var5++].method_607(0.0F);
        }

        this.animation3.method_607(0.0F);
        this.animation.method_607(1.0F);
        this.animation2.method_607(0.0F);
    }

    private BlockPos method_1258(BlockPos var1, float var2) {
        if (this.blockPos2 != null && !Objects.equals(var1, this.blockPos2)) {
            if (!Objects.equals(var1, this.blockPos)) {
                this.blockPos = var1;
                this.floatValue = 0.0F;
            }

            this.floatValue += var2;
            return this.floatValue >= 60.0F ? var1 : this.blockPos2;
        } else {
            this.blockPos = null;
            this.floatValue = 0.0F;
            return var1;
        }
    }

    private float method_1259() {
        long var1 = System.nanoTime();
        if (this.longValue == 0L) {
            this.longValue = var1;
            return 0.0F;
        } else {
            float var10000 = (float)(var1 - this.longValue) / 1000000.0F;
            this.longValue = var1;
            return Math.clamp(var10000, 0.0F, 100.0F);
        }
    }

    private AxisAlignedBB method_1260() {
        double var1 = this.doubleValue2 + this.animationArray[0].method_606();
        double var3 = this.doubleValue3 + this.animationArray[1].method_606();
        double var5 = this.doubleValue + this.animationArray[2].method_606();
        double var7 = this.doubleValue2 + this.animationArray[3].method_606();
        double var9 = this.doubleValue3 + this.animationArray[4].method_606();
        double var11 = this.doubleValue + this.animationArray[5].method_606();
        double var13 = (var1 + var7) / 2.0;
        double var15 = (var3 + var9) / 2.0;
        double var17 = (var5 + var11) / 2.0;
        double var19 = this.animation.method_606();
        double var21 = this.padding.getValue() / 100.0;
        return AxisAlignedBB.fromBounds(
            var13 + (var1 - var13) * var19 - var21,
            var15 + (var3 - var15) * var19 - var21,
            var17 + (var5 - var17) * var19 - var21,
            var13 + (var7 - var13) * var19 + var21,
            var15 + (var9 - var15) * var19 + var21,
            var17 + (var11 - var17) * var19 + var21
        );
    }

    private BlockPos method_1261() {
        BedBreaker var2;
        if (Onyx.moduleManager == null) {
            var2 = null;
        } else {
            var2 = Onyx.moduleManager.bedBreaker;
        }

        if (var2 != null && var2.isToggled() && var2.getBlockPos3() != null) {
            return var2.getBlockPos3();
        } else if (this.onlyOnBedBreak.method_146()) {
            return null;
        } else {
            MovingObjectPosition var3 = mc.objectMouseOver;
            return mc.objectMouseOver != null && var3.typeOfHit == MovingObjectType.BLOCK ? var3.getBlockPos() : null;
        }
    }

    private float method_1262() {
        if (this.breakProgressSettings.method_61() && mc.playerController != null) {
            BedBreaker var3;
            if (Onyx.moduleManager == null) {
                var3 = null;
            } else {
                var3 = Onyx.moduleManager.bedBreaker;
            }

            boolean var4;
            if (var3 != null && var3.isToggled() && var3.getBlockPos3() != null) {
                var4 = true;
            } else {
                var4 = false;
            }

            return !var4 && !mc.playerController.isHittingBlock() ? 0.0F : Math.clamp(mc.playerController.getCurBlockDamage(), 0.0F, 1.0F);
        } else {
            return 0.0F;
        }
    }

    public boolean method_1263() {
        return this.isToggled() && this.hideVanillaOutline.method_146() && this.method_1261() != null;
    }

    private AxisAlignedBB method_1264(BlockPos var1) {
        return mc.theWorld.getBlockState(var1).getBlock().getMaterial() == Material.air ? null : BlockOverlayRenderer.method_553(var1);
    }

    private void method_1265(AxisAlignedBB var1) {
        this.animationArray[0].method_607((float)(var1.minX - this.doubleValue2));
        this.animationArray[1].method_607((float)(var1.minY - this.doubleValue3));
        this.animationArray[2].method_607((float)(var1.minZ - this.doubleValue));
        this.animationArray[3].method_607((float)(var1.maxX - this.doubleValue2));
        this.animationArray[4].method_607((float)(var1.maxY - this.doubleValue3));
        this.animationArray[5].method_607((float)(var1.maxZ - this.doubleValue));
    }

    public static final class AnimationSettings extends SettingGroup {
        public final NumberSetting appear = new NumberSetting("Appear", 140.0, 0.0, 600.0, 10.0).method_147(" ms");
        public final NumberSetting move = new NumberSetting("Move", 110.0, 0.0, 600.0, 10.0)
            .method_147(" ms")
            .describe("How long the box takes to slide to a neighbouring block");
        public final NumberSetting fade = new NumberSetting("Fade", 180.0, 0.0, 600.0, 10.0).method_147(" ms");
        public final EnumSetting<BlockOverlay.AppearMode> appearance = new EnumSetting<>("Appearance", BlockOverlay.AppearMode.POP)
            .describe("Fade alone, a small pop, or growing out of the centre of the block");

        public AnimationSettings() {
            super("Animation");
        }
    }

    public static enum AppearMode implements NamedEnum {
        FADE("Fade", 1.0F),
        POP("Pop", 0.62F),
        GROW("Grow", 0.0F);

        private final String string;
        private final float floatValue;

        private AppearMode(String var3, float var4) {
            this.string = var3;
            this.floatValue = var4;
        }

        public float getFloatValue() {
            return this.floatValue;
        }

        @Override
        public String getDisplayName() {
            return this.string;
        }
    }

    public static enum BreakDirection implements NamedEnum {
        DOWN("Top to bottom"),
        UP("Bottom to top");

        private final String string;

        @Override
        public String getDisplayName() {
            return this.string;
        }

        private BreakDirection(String var3) {
            this.string = var3;
        }
    }

    public static enum BreakMode implements NamedEnum {
        FILL("Fill"),
        GROW("Grow from centre");

        private final String string;

        @Override
        public String getDisplayName() {
            return this.string;
        }

        private BreakMode(String var3) {
            this.string = var3;
        }
    }

    public static final class BreakProgressSettings extends SettingSection {
        public final EnumSetting<BlockOverlay.BreakMode> mode = new EnumSetting<>("Mode", BlockOverlay.BreakMode.FILL)
            .describe("The fill rising across the block, or growing out of its centre");
        public final EnumSetting<BlockOverlay.BreakDirection> direction = new EnumSetting<>("Direction", BlockOverlay.BreakDirection.DOWN)
            .describe("Which way the fill travels as the block breaks");
        public final NumberSetting progressOpacity = new NumberSetting("Progress opacity", 45.0, 1.0, 100.0, 1.0)
            .method_147("%")
            .describe("How solid the broken part of the block is");

        public BreakProgressSettings() {
            super("Break progress", true);
            this.direction.visibleWhen(() -> this.mode.method_168(BlockOverlay.BreakMode.FILL));
        }
    }

    public static final class GlintSettings extends SettingGroup {
        public final NumberSetting glintSpeed = new NumberSetting("Glint speed", 1.0, 0.1, 4.0, 0.1);
        public final NumberSetting glintScale = new NumberSetting("Glint scale", 1.5, 0.5, 4.0, 0.1)
            .describe("How large the curtains are across the block face");
        public final NumberSetting glintGlow = new NumberSetting("Glint glow", 60.0, 0.0, 100.0, 5.0).method_147("%");

        public GlintSettings() {
            super("Glint");
        }
    }

    public static enum Theme implements NamedEnum {
        OUTLINE("Outline"),
        FILL("Outline + fill"),
        GLINT("Outline + glint");

        private final String string;

        private Theme(String var3) {
            this.string = var3;
        }

        @Override
        public String getDisplayName() {
            return this.string;
        }
    }
}
