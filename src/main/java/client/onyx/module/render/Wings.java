package client.onyx.module.render;

import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.render.world.WingFeather;
import client.onyx.render.world.WingFrame;
import client.onyx.render.world.WingParams;
import client.onyx.render.world.WingType;
import client.onyx.render.world.WingsRenderer;
import client.onyx.rotation.RotationVisuals;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.ColorSetting;
import client.onyx.setting.impl.EnumSetSetting;
import client.onyx.setting.impl.EnumSetting;
import client.onyx.setting.impl.NumberSetting;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public final class Wings extends Module {
    private static final float FLOAT_1_51 = 1.5F;
    private static final float FLOAT_0_151 = 0.15F;
    public EnumSetting<WingType> wingType = new EnumSetting<>("Wing type", WingType.ANGELIC).describe("Silhouette the wings are cut from");
    public EnumSetting<Wings.FillMode> fill = new EnumSetting<>("Fill", Wings.FillMode.SHADER)
        .describe("Flat colour, or an animated aurora inside the membrane");
    public EnumSetSetting<Wings.TargetMode> targets = new EnumSetSetting<>("Targets", Wings.TargetMode.class, Wings.TargetMode.SELF)
        .describe("Who the wings are drawn on");
    public NumberSetting scale = new NumberSetting("Scale", 1.0, 0.3, 3.0, 0.1).method_147("x");
    public NumberSetting angle = new NumberSetting("Angle", 20.0, 0.0, 90.0, 1.0).method_147("°").describe("How far the wings sweep back from the shoulders");
    public NumberSetting height = new NumberSetting("Height", 1.5, 0.8, 2.5, 0.05).describe("How high on the back the wings sit");
    public NumberSetting depth = new NumberSetting("Depth", 0.15F, 0.0, 0.5, 0.01).describe("How far the wings stand off the back");
    public BooleanSetting flapping = new BooleanSetting("Flapping", true);
    public NumberSetting flapStrength = new NumberSetting("Flap strength", 30.0, 5.0, 60.0, 1.0)
        .describe("How far the wings swing on every beat")
        .visibleWhen(this.flapping);
    public NumberSetting flapSpeed = new NumberSetting("Flap speed", 3.0, 0.5, 8.0, 0.5).describe("How quickly the wings beat").visibleWhen(this.flapping);
    public BooleanSetting throughWalls = new BooleanSetting("Through walls", false);
    public ColorSetting color = new ColorSetting("Color", -1).method_116();
    public NumberSetting opacity = new NumberSetting("Opacity", 100.0, 5.0, 100.0, 1.0).method_147("%").describe("Overall opacity of the wings");
    private final WingsRenderer wingsRenderer = new WingsRenderer();
    private final List<WingFrame> list = new ArrayList<>();

    private float method_1186(EntityPlayer var1, float var2) {
        return var1 == mc.thePlayer && RotationVisuals.method_2126(var1)
            ? RotationVisuals.method_2127(var2)
            : var1.prevRenderYawOffset + MathHelper.wrapAngleTo180_float(var1.renderYawOffset - var1.prevRenderYawOffset) * var2;
    }

    public Wings() {
        super("Wings", "Draws a pair of wings on player backs", Category.RENDER);
    }

    private void method_1187(EntityPlayer var1, float var2) {
        WingParams var3;
        if ((var3 = this.method_1189(var1)) != null) {
            Vec3 var4 = new Vec3(
                var1.prevPosX + (var1.posX - var1.prevPosX) * var2,
                var1.prevPosY + (var1.posY - var1.prevPosY) * var2,
                var1.prevPosZ + (var1.posZ - var1.prevPosZ) * var2
            );
            this.list.add(new WingFrame(var4, this.method_1186(var1, var2), this.method_1192(var1, var3, var2), this.wingType.getValue(), var3));
        }
    }

    private WingFeather method_1188() {
        return new WingFeather(
            this.fill.method_168(Wings.FillMode.SHADER),
            this.scale.method_151(),
            this.height.method_151() - 1.5F,
            this.depth.method_151() - 0.15F,
            this.throughWalls.method_146(),
            this.color.method_118(),
            this.opacity.method_151() / 100.0F
        );
    }

    private WingParams method_1189(EntityPlayer var1) {
        return var1.isInWater() ? null : this.method_1190(var1);
    }

    private WingParams method_1190(EntityPlayer var1) {
        return var1.isSneaking()
            ? new WingParams(0.0F, 0.0F, 0.96F, 0.1F, 18.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.18F, 4.5F, 0.06F, 0.02F, -11.0F, -4.0F, 0.12F)
            : new WingParams(0.0F, 0.0F, 1.38F, 0.1F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.18F, 4.5F, 0.06F, 0.02F, -11.0F, -4.0F, 0.12F);
    }

    public void method_1191(float var1) {
        var var2 = this;
        if (this.isToggled() && mc.theWorld != null && mc.thePlayer != null) {
            this.list.clear();
            if (this.targets.method_79(Wings.TargetMode.SELF) && mc.gameSettings.thirdPersonView != 0 && mc.thePlayer.isEntityAlive()) {
                this.method_1187(mc.thePlayer, var1);
            }

            if (this.targets.method_79(Wings.TargetMode.PLAYERS)) {
                Iterator var5 = mc.theWorld.playerEntities.iterator();

                label33:
                while (true) {
                    Iterator var4 = var5;

                    while (true) {
                        if (!var4.hasNext()) {
                            break label33;
                        }

                        EntityPlayer var3;
                        if ((var3 = (EntityPlayer)var5.next()) == mc.thePlayer) {
                            break;
                        }

                        if (!var3.isEntityAlive()) {
                            var4 = var5;
                        } else {
                            var2.method_1187(var3, var1);
                            var4 = var5;
                        }
                    }
                }
            }

            var2.wingsRenderer.method_846(var1, var2.list, var2.method_1188());
        }
    }

    @Override
    public void onDisable() {
        this.list.clear();
        this.wingsRenderer.close();
    }

    private float method_1192(EntityPlayer var1, WingParams var2, float var3) {
        float var4 = 0.0F;
        if (this.flapping.method_146()) {
            float var5 = var2.floatValue14() * (this.flapSpeed.method_151() / 3.0F);
            float var6 = var2.floatValue15() * (this.flapStrength.method_151() / 30.0F);
            var4 = (float)Math.sin((var1.ticksExisted + var3) * var5) * var6;
        }

        float var9 = Math.clamp((float)Math.sqrt(var1.motionX * var1.motionX + var1.motionZ * var1.motionZ) * 10.0F, 0.0F, 1.0F);
        return (var4 + var9 * var2.floatValue16() - this.angle.method_151()) * var2.floatValue6();
    }

    public static enum FillMode {
        SOLID,
        SHADER;
    }

    public static enum TargetMode {
        SELF,
        PLAYERS;
    }
}
