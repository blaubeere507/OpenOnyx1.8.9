package client.onyx.module.render;

import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.render.world.ChinaHatFrame;
import client.onyx.render.world.ChinaHatParams;
import client.onyx.render.world.ChinaHatRenderer;
import client.onyx.render.world.ColorStyle;
import client.onyx.rotation.RotationVisuals;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.ColorSetting;
import client.onyx.setting.impl.EnumSetting;
import client.onyx.setting.impl.NumberSetting;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public final class ChinaHat extends Module {
    private static final float FLOAT_1_51 = 1.5F;
    private static final float FLOAT_0_221 = 0.22F;
    private static final float FLOAT_6_28318551 = (float) (Math.PI * 2);
    public NumberSetting radius = new NumberSetting("Radius", 0.65, 0.2, 3.0, 0.05).describe("How wide the brim sits");
    public NumberSetting height = new NumberSetting("Height", 0.35, 0.05, 1.5, 0.05).describe("How tall the cone stands");
    public NumberSetting offset = new NumberSetting("Offset", -0.02, -0.5, 1.5, 0.02).describe("How far above the head the brim sits");
    public NumberSetting tilt = new NumberSetting("Tilt", 100.0, 0.0, 100.0, 5.0).method_147("%").describe("How much of the head's pitch the hat takes on");
    public EnumSetting<ColorStyle> colors = new EnumSetting<>("Colors", ColorStyle.STATIC)
        .describe("Hold one colour, blend two around the hat, or walk the hue right round it");
    public ColorSetting color = new ColorSetting("Color", -10178561).method_116();
    public ColorSetting secondColor = new ColorSetting("Second color", -4949761)
        .visibleWhen(this.colors, var0 -> var0 == ColorStyle.GRADIENT)
        .describe("The colour the first one blends into");
    public NumberSetting colorSpeed = new NumberSetting("Color speed", 40.0, 0.0, 360.0, 5.0)
        .method_147("°/s")
        .visibleWhen(this.colors, var0 -> var0 != ColorStyle.STATIC)
        .describe("How fast the colours travel around the hat - zero holds them still");
    public NumberSetting opacity = new NumberSetting("Opacity", 90.0, 5.0, 100.0, 1.0).method_147("%").describe("Overall opacity of the hat");
    public NumberSetting spinSpeed = new NumberSetting("Spin speed", 65.0, 0.0, 360.0, 5.0)
        .method_147("°/s")
        .describe("How fast the line travels around the hat");
    public BooleanSetting glow = new BooleanSetting("Glow", true).describe("Soft halo right on the rim");
    public BooleanSetting trail = new BooleanSetting("Trail", true).describe("Line sweeping around the hat, and the fading wake behind it");
    public NumberSetting trailLength = new NumberSetting("Trail length", 350.0, 20.0, 360.0, 10.0)
        .method_147("°")
        .describe("How far back the wake reaches")
        .visibleWhen(this.trail);
    public BooleanSetting throughWalls = new BooleanSetting("Through walls", false);
    private final ChinaHatRenderer chinaHatRenderer = new ChinaHatRenderer();
    private final List<ChinaHatFrame> list = new ArrayList<>();
    private float floatValue;
    private long longValue;
    private float floatValue2;

    private void method_1211() {
        long var1 = System.nanoTime();
        if (this.longValue == 0L) {
            this.longValue = var1;
        } else {
            float var4 = Math.min((float)(var1 - this.longValue) / 1.0E9F, 0.25F);
            this.longValue = var1;
            this.floatValue2 = (this.floatValue2 + (float)Math.toRadians(this.spinSpeed.getValue()) * var4) % (float) (Math.PI * 2);
            this.floatValue = (this.floatValue + (float)Math.toRadians(this.colorSpeed.getValue()) * var4) % (float) (Math.PI * 2);
        }
    }

    public ChinaHat() {
        super("ChinaHat", "Floats a conical hat over your head", Category.RENDER);
    }

    public void method_1212(float var1) {
        if (this.isToggled() && mc.theWorld != null && mc.thePlayer != null) {
            this.method_1211();
            this.list.clear();
            if (mc.gameSettings.thirdPersonView != 0 && mc.thePlayer.isEntityAlive()) {
                this.method_1216(var1);
            }

            this.chinaHatRenderer.method_703(var1, this.list, this.method_1213());
        }
    }

    private ChinaHatParams method_1213() {
        return new ChinaHatParams(
            this.radius.method_151(),
            this.height.method_151(),
            this.colors.getValue(),
            this.color.method_118(),
            this.secondColor.method_118(),
            this.floatValue,
            this.opacity.method_151() / 100.0F,
            this.glow.method_146(),
            this.trail.method_146(),
            (float)Math.toRadians(this.trailLength.getValue()),
            this.floatValue2,
            this.throughWalls.method_146()
        );
    }

    @Override
    public void onEnable() {
        this.longValue = 0L;
    }

    private float method_1214(float var1) {
        return RotationVisuals.method_2126(mc.thePlayer)
            ? RotationVisuals.method_2128(var1)
            : mc.thePlayer.prevRotationYawHead + MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYawHead - mc.thePlayer.prevRotationYawHead) * var1;
    }

    private float method_1215(float var1) {
        return RotationVisuals.method_2126(mc.thePlayer)
            ? RotationVisuals.method_2129(var1)
            : mc.thePlayer.prevRotationPitch + MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationPitch - mc.thePlayer.prevRotationPitch) * var1;
    }

    private void method_1216(float var1) {
        EntityPlayerSP var5 = mc.thePlayer;
        double var3 = mc.thePlayer.prevPosY + (var5.posY - var5.prevPosY) * var1 + 1.5;
        if (var5.isSneaking()) {
            var3 -= 0.22F;
        }

        Vec3 var7 = new Vec3(var5.prevPosX + (var5.posX - var5.prevPosX) * var1, var3, var5.prevPosZ + (var5.posZ - var5.prevPosZ) * var1);
        float var6 = var5.height + this.offset.method_151() - 1.5F;
        this.list.add(new ChinaHatFrame(var7, var6, this.method_1214(var1), this.method_1215(var1) * (this.tilt.method_151() / 100.0F), 0.0F));
    }

    @Override
    public void onDisable() {
        this.list.clear();
        this.chinaHatRenderer.method_706();
    }
}
