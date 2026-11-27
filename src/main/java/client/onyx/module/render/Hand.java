package client.onyx.module.render;

import client.onyx.Onyx;
import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.render.effect.HandMaskRenderer;
import client.onyx.render.effect.HandTransformRenderer;
import client.onyx.setting.NamedEnum;
import client.onyx.setting.SettingGroup;
import client.onyx.setting.SettingSection;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.ButtonSetting;
import client.onyx.setting.impl.ColorSetting;
import client.onyx.setting.impl.EnumSetting;
import client.onyx.setting.impl.NumberSetting;
import java.util.Locale;
import java.util.function.BooleanSupplier;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public final class Hand extends Module {
    private static final double DOUBLE_45_01 = 45.0;
    private static final double DOUBLE_N0_521 = -0.52;
    private static final double DOUBLE_0_41 = 0.4;
    private static final float FLOAT_0_51 = 0.5F;
    private static final double DOUBLE_N0_721 = -0.72;
    private static final double DOUBLE_0_561 = 0.56;
    private static final long LONG_1400000001 = 140000000L;
    private static final long LONG_3000000001 = 300000000L;
    private static final long LONG_2600000001 = 260000000L;
    private static final float FLOAT_0_251 = 0.25F;
    public final Hand.EffectSettings effectSettings = new Hand.EffectSettings();
    public final Hand.TransformSettings mainHand = new Hand.TransformSettings("Main hand");
    public final Hand.SwingSettings swingSettings = new Hand.SwingSettings();
    private final Hand.HandState handState = new Hand.HandState();
    private long longValue = -1L;
    private float floatValue;
    private float floatValue2;

    public boolean method_1274(ItemStack var1, float var2) {
        if (!this.method_1301(var1)) {
            return false;
        } else {
            Hand var3;
            float var5;
            if (HandMaskRenderer.isFlag2()) {
                var5 = this.floatValue2;
                var3 = this;
            } else {
                var5 = Math.max(Math.clamp(var2, 0.0F, 1.0F), this.method_1278());
                this.floatValue2 = var5;
                var3 = this;
            }

            var3.method_1292(var5);
            return true;
        }
    }

    private void method_1275(float var1) {
        float var2 = -0.4F * (float)Math.sin(Math.sqrt(var1) * Math.PI);
        float var3 = 0.2F * (float)Math.sin(Math.sqrt(var1) * Math.PI * 2.0);
        float var4 = -0.2F * (float)Math.sin(var1 * Math.PI);
        GlStateManager.translate(var2, var3, var4);
        this.method_1297(var1);
    }

    private boolean method_1276() {
        return this.isToggled()
            && mc.thePlayer != null
            && mc.thePlayer.isBlocking()
            && mc.thePlayer.getHeldItem() != null
            && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword;
    }

    public void method_1277() {
        float var2 = HandTransformRenderer.method_419();
        GlStateManager.scale(this.mainHand.scaleX.method_151() * var2, this.mainHand.scaleY.method_151() * var2, this.mainHand.scaleZ.method_151() * var2);
    }

    private float method_1278() {
        if (!mc.gameSettings.keyBindAttack.isKeyDown()) {
            this.longValue = -1L;
            return 0.0F;
        } else {
            if (this.longValue < 0L) {
                this.longValue = System.nanoTime();
            }

            return (float)((System.nanoTime() - this.longValue) % 260000000L) / 2.6E8F;
        }
    }

    public float method_1279() {
        return this.effectSettings.trailSettings.itemColor.method_146() ? 0.0F : 1.0F;
    }

    private boolean method_1280() {
        ItemStack var2;
        return !this.swingSettings.swordsOnly.method_146() || (var2 = mc.thePlayer.getHeldItem()) != null && var2.getItem() instanceof ItemSword;
    }

    private int method_1281() {
        if (mc.thePlayer == null) {
            return -1;
        } else {
            ItemStack var2;
            if ((var2 = mc.thePlayer.getHeldItem()) != null && var2.getItem() != null) {
                String var3;
                if ((var3 = var2.getItem().getUnlocalizedName().toLowerCase(Locale.ROOT)).contains("diamond")) {
                    return -11149856;
                } else if (var3.contains("gold")) {
                    return -11174;
                } else if (var3.contains("iron")) {
                    return -2564376;
                } else if (var3.contains("emerald")) {
                    return -13250449;
                } else if (var3.contains("redstone")) {
                    return -1950917;
                } else {
                    return !var3.contains("lapis") && !var3.contains("dyepowder") ? -1644826 : -13543724;
                }
            } else {
                return -1;
            }
        }
    }

    private boolean method_1282() {
        return this.swingSettings.method_61() && this.swingSettings.replaceVanillaSwing.method_146() && this.method_1280();
    }

    public int method_1283() {
        Hand.TrailSettings var2;
        return (var2 = this.effectSettings.trailSettings).itemColor.method_146() ? this.method_1281() : var2.color.getValue();
    }

    public float method_1284() {
        return 0.5F;
    }

    public Hand.TrailSettings method_1285() {
        if (!this.isToggled() || mc.thePlayer == null) {
            return null;
        } else {
            return this.effectSettings.method_61() && this.effectSettings.effect.method_168(Hand.EffectType.SMOKE) ? this.effectSettings.trailSettings : null;
        }
    }

    public Hand() {
        super("Hand", "Adjust the first-person hand and replace the sword swing", Category.RENDER);
        this.swingSettings.speed.onChange(var1 -> this.handState.method_1142());
    }

    private static NumberSetting method_1286(String var0, double var1) {
        return new NumberSetting(var0, var1, -360.0, 360.0, 1.0).method_147("deg");
    }

    private static float method_1287(float var0, float var1, float var2) {
        return var0 + (var1 - var0) * var2;
    }

    private static float method_1288(Hand.SwingCurve var0, float var1) {
        switch (var0) {
            case LINEAR:
                return var1;
            case SMOOTH:
                return var1 * var1 * (3.0F - 2.0F * var1);
            case EASE_OUT:
                return 1.0F - (1.0F - var1) * (1.0F - var1);
            case SIN:
                return (float)Math.sin(var1 * Math.PI / 2.0);
            case SQRT:
                return (float)Math.sqrt(var1);
            default:
                throw new MatchException(null, null);
        }
    }

    private static float method_1289(float var0, float var1) {
        return Math.max(0.01F, method_1287(1.0F, var0, var1));
    }

    @Override
    public void onDisable() {
        this.handState.method_1142();
    }

    private static void method_1290(float var0, float var1, float var2, float var3) {
        if (var0 != 0.0F) {
            GlStateManager.rotate(var0, var1, var2, var3);
        }
    }

    private boolean method_1291() {
        return Onyx.moduleManager.killAura.isToggled() && Onyx.moduleManager.killAura.getEntityLivingBase2() != null;
    }

    private void method_1292(float var1) {
        float var2;
        GlStateManager.translate(0.0F, (var2 = -((float)Math.sin(Math.sqrt(var1) * Math.PI)) * 2.0F) / 10.0F + 0.1F, 0.0F);
        method_1290(var2 * 10.0F, 0.0F, 1.0F, 0.0F);
        method_1290(250.0F, 0.2F, 1.0F, -0.6F);
        method_1290(-10.0F, 1.0F, 0.5F, 1.0F);
        method_1290(-var2 * 20.0F, 1.0F, 0.5F, 1.0F);
    }

    private float method_1293(float var1) {
        if (this.method_1291()) {
            long var2 = Hand.HandState.method_1144(140000000L, this.swingSettings.speed.method_151());
            return this.handState.method_1145(var2);
        } else {
            this.handState.method_1146();
            return this.handState.method_1143(var1, Hand.HandState.method_1144(300000000L, this.swingSettings.speed.method_151()));
        }
    }

    public boolean method_1294(float var1) {
        if (!this.isToggled() || mc.thePlayer == null) {
            return false;
        } else if (this.method_1298()) {
            return true;
        } else if (!this.swingSettings.method_61()) {
            this.handState.method_1142();
            this.method_1275(var1);
            return true;
        } else if (!this.method_1280()) {
            return false;
        } else if (this.swingSettings.matchVanilla.method_146()) {
            this.method_1275(var1);
            return this.method_1282();
        } else {
            float var2;
            Hand var5;
            if (!HandMaskRenderer.isFlag2()) {
                float var3 = this.swingSettings.strength.method_151() / 100.0F;
                float var7 = this.method_1293(Math.clamp(var1, 0.0F, 1.0F));
                var2 = (float)Math.sin(method_1288(this.swingSettings.curve.getValue(), var7) * Math.PI) * var3;
                this.floatValue = var2;
                var5 = this;
            } else {
                var2 = this.floatValue;
                var5 = this;
            }

            var5.method_1295(var2);
            return this.method_1282();
        }
    }

    private void method_1295(float var1) {
        GlStateManager.translate(
            this.swingSettings.numberSetting.method_151() * var1,
            this.swingSettings.numberSetting6.method_151() * var1,
            this.swingSettings.numberSetting3.method_151() * var1
        );
        method_1290(this.swingSettings.numberSetting7.method_151() * var1, 1.0F, 0.0F, 0.0F);
        method_1290(this.swingSettings.numberSetting8.method_151() * var1, 0.0F, 1.0F, 0.0F);
        method_1290(this.swingSettings.numberSetting2.method_151() * var1, 0.0F, 0.0F, 1.0F);
        GlStateManager.scale(
            method_1289(this.swingSettings.numberSetting9.method_151(), var1),
            method_1289(this.swingSettings.numberSetting5.method_151(), var1),
            method_1289(this.swingSettings.numberSetting4.method_151(), var1)
        );
    }

    public Hand.GlintSettings method_1296() {
        if (!this.isToggled() || mc.thePlayer == null) {
            return null;
        } else {
            return this.effectSettings.method_61() && this.effectSettings.effect.method_168(Hand.EffectType.GLINT) ? this.effectSettings.glintSettings : null;
        }
    }

    private void method_1297(float var1) {
        float var2 = (float)Math.sin(var1 * var1 * Math.PI);
        float var3 = (float)Math.sin(Math.sqrt(var1) * Math.PI);
        method_1290(var2 * -20.0F, 0.0F, 1.0F, 0.0F);
        method_1290(var3 * -20.0F, 0.0F, 0.0F, 1.0F);
        method_1290(var3 * -80.0F, 1.0F, 0.0F, 0.0F);
    }

    private boolean method_1298() {
        return this.method_1276() && this.swingSettings.method_61();
    }

    public boolean method_1299(float var1, float var2) {
        if (this.isToggled() && mc.thePlayer != null) {
            GlStateManager.translate(
                this.mainHand.positionX.method_151(), this.mainHand.positionY.method_151() - var1 * 0.6F, this.mainHand.positionZ.method_151()
            );
            HandTransformRenderer.method_415();
            method_1290(this.mainHand.numberSetting2.method_151(), 1.0F, 0.0F, 0.0F);
            method_1290(this.mainHand.numberSetting.method_151(), 0.0F, 1.0F, 0.0F);
            method_1290(this.mainHand.numberSetting3.method_151(), 0.0F, 0.0F, 1.0F);
            this.method_1297(Math.clamp(var2, 0.0F, 1.0F));
            this.method_1277();
            return true;
        } else {
            return false;
        }
    }

    public boolean method_1300(float var1) {
        if (this.isToggled() && mc.thePlayer != null) {
            boolean var4;
            if (!this.method_1298()) {
                var4 = true;
            } else {
                var4 = false;
            }

            float var5;
            if (this.method_1282()) {
                var5 = 0.0F;
            } else {
                var5 = var1 * 0.6F;
            }

            GlStateManager.translate(this.mainHand.positionX.method_151(), this.mainHand.positionY.method_151() - var5, this.mainHand.positionZ.method_151());
            HandTransformRenderer.method_415();
            if (var4) {
                method_1290(this.mainHand.numberSetting2.method_151(), 1.0F, 0.0F, 0.0F);
                method_1290(this.mainHand.numberSetting.method_151(), 0.0F, 1.0F, 0.0F);
                method_1290(this.mainHand.numberSetting3.method_151(), 0.0F, 0.0F, 1.0F);
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean method_1301(ItemStack var1) {
        return this.method_1298() && var1 != null && var1.getItem() instanceof ItemSword;
    }

    public static final class EffectSettings extends SettingSection {
        public final EnumSetting<Hand.EffectType> effect = new EnumSetting<>("Effect", Hand.EffectType.GLINT);
        public final Hand.GlintSettings glintSettings = new Hand.GlintSettings().visibleWhen(() -> this.effect.method_168(Hand.EffectType.GLINT));
        public final Hand.TrailSettings trailSettings = new Hand.TrailSettings().visibleWhen(() -> this.effect.method_168(Hand.EffectType.SMOKE));

        private EffectSettings() {
            super("Effects", false);
        }
    }

    public static enum EffectType implements NamedEnum {
        GLINT("Glint"),
        SMOKE("Smoke");

        private final String string;

        private EffectType(String var3) {
            this.string = var3;
        }

        @Override
        public String getDisplayName() {
            return this.string;
        }
    }

    public static final class GlintSettings extends SettingGroup {
        public final ColorSetting color = new ColorSetting("Color", -3105793).method_116();
        public final ColorSetting color2 = new ColorSetting("Color 2", -6559745)
            .method_116()
            .method_125(14.0)
            .describe("When following the accent, shifted slightly so the glint keeps a gradient");
        public final NumberSetting alpha = new NumberSetting("Alpha", 100.0, 0.0, 100.0, 5.0).method_147("%");
        public final BooleanSetting outlineOnly = new BooleanSetting("Outline only", false);
        public final NumberSetting outline = new NumberSetting("Outline", 2.0, 0.0, 8.0, 0.5).method_147(" px");
        public final NumberSetting glow = new NumberSetting("Glow", 125.0, 0.0, 200.0, 5.0).method_147("%");
        public final NumberSetting fill = new NumberSetting("Fill", 35.0, 0.0, 200.0, 5.0).method_147("%").visibleWhen(() -> !this.outlineOnly.method_146());
        public final NumberSetting padding = new NumberSetting("Padding", 16.0, 0.0, 20.0, 1.0)
            .method_147(" px")
            .describe("Distance the glint sits away from the item");
        public final NumberSetting smoothing = new NumberSetting("Smoothing", 30.0, 0.0, 32.0, 0.5)
            .method_147(" px")
            .describe("Softens the silhouette before the glint traces it");
        public final NumberSetting speed = new NumberSetting("Speed", 1.0, 0.1, 5.0, 0.05).method_147("x");

        public Hand.GlintSettings visibleWhen(BooleanSupplier var1) {
            super.visibleWhen(var1);
            return this;
        }

        private GlintSettings() {
            super("Glint");
        }
    }

    private static final class HandState {
        private static final float FLOAT_1_0EN41 = 1.0E-4F;
        private long longValue2 = -1L;
        private long longValue = 300000000L;
        private long longValue3 = -1L;
        private float floatValue;

        private void method_1142() {
            this.longValue2 = -1L;
            this.longValue = 300000000L;
            this.floatValue = 0.0F;
            this.longValue3 = -1L;
        }

        private float method_1143(float var1, long var2) {
            long var4 = System.nanoTime();
            boolean var7;
            if (!(var1 > 1.0E-4F) || !(this.floatValue <= 1.0E-4F) && !(var1 + 1.0E-4F < this.floatValue)) {
                var7 = false;
            } else {
                var7 = true;
            }

            this.floatValue = var1;
            if (var7) {
                this.longValue2 = var4;
                this.longValue = var2;
            }

            if (this.longValue2 < 0L) {
                return 0.0F;
            } else {
                float var9;
                if ((var9 = (float)(var4 - this.longValue2) / (float)this.longValue) >= 1.0F) {
                    this.longValue2 = -1L;
                    return 0.0F;
                } else {
                    return Math.clamp(var9, 0.0F, 1.0F);
                }
            }
        }

        private static long method_1144(long var0, float var2) {
            return Math.max(1L, (long)((float)var0 / Math.max(var2, 0.01F)));
        }

        private float method_1145(long var1) {
            long var3 = System.nanoTime();
            if (this.longValue3 < 0L) {
                this.longValue3 = var3;
            }

            return (float)((var3 - this.longValue3) % var1) / (float)var1;
        }

        private void method_1146() {
            this.longValue3 = -1L;
        }
    }

    public static enum SwingCurve implements NamedEnum {
        LINEAR("Linear"),
        SMOOTH("Smooth"),
        EASE_OUT("Ease out"),
        SIN("Sin"),
        SQRT("Square root");

        private final String string;

        @Override
        public String getDisplayName() {
            return this.string;
        }

        private SwingCurve(String var3) {
            this.string = var3;
        }
    }

    public static final class SwingSettings extends SettingSection {
        public final BooleanSetting swordsOnly = new BooleanSetting("Swords only", false);
        public final BooleanSetting replaceVanillaSwing = new BooleanSetting("Replace vanilla swing", true);
        public final BooleanSetting matchVanilla = new BooleanSetting("Match vanilla", false)
            .describe("Uses vanilla's own swing motion instead of the curve below");
        public final NumberSetting strength = new NumberSetting("Strength", 100.0, 0.0, 200.0, 5.0).method_147("%");
        public final EnumSetting<Hand.SwingCurve> curve = new EnumSetting<>("Curve", Hand.SwingCurve.SMOOTH);
        public final NumberSetting speed = new NumberSetting("Speed", 1.0, 0.1, 5.0, 0.05).method_147("x");
        public final NumberSetting numberSetting = this.method_1306("Animation X");
        public final NumberSetting numberSetting6 = this.method_1306("Animation Y");
        public final NumberSetting numberSetting3 = this.method_1306("Animation Z");
        public final NumberSetting numberSetting7 = this.method_1305("Animation rotation X", -51.0);
        public final NumberSetting numberSetting8 = this.method_1305("Animation rotation Y", 0.0);
        public final NumberSetting numberSetting2 = this.method_1305("Animation rotation Z", 0.0);
        public final NumberSetting numberSetting9 = this.method_1304("Animation scale X");
        public final NumberSetting numberSetting5 = this.method_1304("Animation scale Y");
        public final NumberSetting numberSetting4 = this.method_1304("Animation scale Z");

        private NumberSetting method_1304(String var1) {
            return new NumberSetting(var1, 1.0, 0.1, 3.0, 0.01);
        }

        private NumberSetting method_1305(String var1, double var2) {
            return new NumberSetting(var1, var2, -720.0, 720.0, 1.0).method_147("deg");
        }

        private SwingSettings() {
            super("Animation", true);
        }

        private NumberSetting method_1306(String var1) {
            return new NumberSetting(var1, 0.0, -2.0, 2.0, 0.01);
        }
    }

    public static final class TrailSettings extends SettingGroup {
        public final BooleanSetting itemColor = new BooleanSetting("Item color", true).describe("Takes the trail color from the hand and the held item");
        public final ColorSetting color = new ColorSetting("Color", -10079233).method_116().visibleWhen(() -> !this.itemColor.method_146());
        public final NumberSetting intensity = new NumberSetting("Intensity", 0.85, 0.1, 1.5, 0.01);
        public final NumberSetting speed = new NumberSetting("Speed", 1.15, 0.2, 3.0, 0.05).method_147("x");
        public final NumberSetting length = new NumberSetting("Length", 0.72, 0.1, 1.0, 0.05);
        public final NumberSetting trailFade = new NumberSetting("Trail fade", 0.91, 0.55, 0.98, 0.01);
        public final NumberSetting trailSoftness = new NumberSetting("Trail softness", 1.35, 0.45, 2.5, 0.05);
        public final NumberSetting trailBlur = new NumberSetting("Trail blur", 1.55, 0.2, 3.0, 0.05);
        public final NumberSetting handSoftness = new NumberSetting("Hand softness", 1.3, 0.45, 2.5, 0.05);
        public final NumberSetting handBlur = new NumberSetting("Hand blur", 1.45, 0.2, 3.0, 0.05);
        public final NumberSetting smoke = new NumberSetting("Smoke", 0.55, 0.0, 0.8, 0.05);

        public float method_1160() {
            return 0.25F;
        }

        public Hand.TrailSettings visibleWhen(BooleanSupplier var1) {
            super.visibleWhen(var1);
            return this;
        }

        private TrailSettings() {
            super("Trail");
        }
    }

    public static final class TransformSettings extends SettingGroup {
        public final ButtonSetting resetToVanilla = new ButtonSetting("Reset to vanilla", () -> {
            this.positionX.setValue(0.56);
            this.positionY.setValue(-0.52);
            this.positionZ.setValue(-0.72);
            this.scaleX.setValue(0.4);
            this.scaleY.setValue(0.4);
            this.scaleZ.setValue(0.4);
            this.numberSetting2.setValue(0.0);
            this.numberSetting.setValue(45.0);
            this.numberSetting3.setValue(0.0);
        }).describe("Restore the hand transform to Minecraft's own first-person placement");
        public final NumberSetting positionX = new NumberSetting("Position X", 0.56, -2.0, 2.0, 0.01);
        public final NumberSetting positionY = new NumberSetting("Position Y", -0.52, -2.0, 2.0, 0.01);
        public final NumberSetting positionZ = new NumberSetting("Position Z", -0.72, -2.0, 2.0, 0.01);
        public final NumberSetting scaleX = new NumberSetting("Scale X", 0.4, 0.02, 3.0, 0.01);
        public final NumberSetting scaleY = new NumberSetting("Scale Y", 0.4, 0.02, 3.0, 0.01);
        public final NumberSetting scaleZ = new NumberSetting("Scale Z", 0.4, 0.02, 3.0, 0.01);
        public final NumberSetting numberSetting2 = Hand.method_1286("Rotation X", 0.0);
        public final NumberSetting numberSetting = Hand.method_1286("Rotation Y", 45.0);
        public final NumberSetting numberSetting3 = Hand.method_1286("Rotation Z", 0.0);

        private TransformSettings(String var1) {
            super(var1);
        }
    }
}
