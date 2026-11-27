package client.onyx.module.render;

import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.render.ui.UiRenderer;
import client.onyx.render.world.WorldProjection;
import client.onyx.setting.impl.ColorSetting;
import client.onyx.setting.impl.EnumSetting;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.ui.theme.color.ColorUtils;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public final class Arrows extends Module {
    private static final ResourceLocation resourceLocation3 = new ResourceLocation("onyx", "textures/pointers/outline.png");
    private static final ResourceLocation resourceLocation = new ResourceLocation("onyx", "textures/pointers/solid.png");
    private static final ResourceLocation resourceLocation2 = new ResourceLocation("onyx", "textures/pointers/chevron.png");
    private static final float FLOAT_100_01 = 100.0F;
    private static final float FLOAT_100_02 = 100.0F;
    private static final double DOUBLE_90_01 = 90.0;
    public final EnumSetting<Arrows.ArrowDesign> design = new EnumSetting<>("Design", Arrows.ArrowDesign.OUTLINE).describe("Which arrow is drawn");
    public final NumberSetting distance = new NumberSetting("Distance", 60.0, 20.0, 150.0, 1.0)
        .method_147(" px")
        .describe("How far the pointers sit from the cursor");
    public final NumberSetting size = new NumberSetting("Size", 18.0, 8.0, 40.0, 1.0).method_147(" px").describe("How large each pointer is");
    public final ColorSetting color = new ColorSetting("Color", -8857601).method_116().describe("Colour the pointer starts from");
    public final ColorSetting secondColor = new ColorSetting("Second color", -4879105).method_116().method_125(60.0).describe("Colour the pointer runs into");
    public final NumberSetting opacity = new NumberSetting("Opacity", 49.0, 5.0, 100.0, 1.0).method_147("%").describe("How solid the pointers are");
    public final NumberSetting glow = new NumberSetting("Glow", 100.0, 0.0, 250.0, 5.0).method_147("%").describe("How much light the pointers throw");
    public final NumberSetting range = new NumberSetting("Range", 128.0, 8.0, 256.0, 8.0)
        .method_147(" m")
        .describe("Farthest a player can be and still get a pointer");
    private final UiRenderer uiRenderer = new UiRenderer().method_460();
    private long longValue;
    private float floatValue;

    private float method_1246() {
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

    private static int method_1247(int var0, int var1, float var2) {
        int var3 = Math.round(ColorUtils.method_213(var0) + (ColorUtils.method_213(var1) - ColorUtils.method_213(var0)) * var2);
        int var4 = Math.round(ColorUtils.method_201(var0) + (ColorUtils.method_201(var1) - ColorUtils.method_201(var0)) * var2);
        int var5 = Math.round(ColorUtils.method_210(var0) + (ColorUtils.method_210(var1) - ColorUtils.method_210(var0)) * var2);
        var0 = Math.round(ColorUtils.method_214(var0) + (ColorUtils.method_214(var1) - ColorUtils.method_214(var0)) * var2);
        return ColorUtils.method_204(var3, var4, var5, var0);
    }

    private void method_1248(float var1, int var2, int var3) {
        this.uiRenderer
            .method_493(
                this.method_1251(),
                -var1 / 2.0F + var1 / 18.0F,
                -var1 / 2.0F,
                var1,
                var1,
                var2,
                method_1247(var2, var3, 0.33333334F),
                method_1247(var2, var3, 0.6666667F),
                var3
            );
    }

    private void method_1249(float var1, float var2, float var3, float var4, float var5, float var6) {
        int var7 = ColorUtils.method_209(this.color.getValue(), ColorUtils.method_213(this.color.getValue()) / 255.0F * var5);
        int var9 = ColorUtils.method_209(this.secondColor.getValue(), ColorUtils.method_213(this.secondColor.getValue()) / 255.0F * var5);
        this.uiRenderer.method_441();
        this.uiRenderer.method_481().method_650(var1, var2);
        this.uiRenderer.method_481().method_648(var3);
        if (var6 > 0.0F) {
            var1 = var4 * (1.0F + 0.5F * Math.min(var6, 2.0F));
            var2 = 0.22F * Math.min(var6, 1.5F);
            this.method_1248(
                var1,
                ColorUtils.method_209(var7, ColorUtils.method_213(var7) / 255.0F * var2),
                ColorUtils.method_209(var9, ColorUtils.method_213(var9) / 255.0F * var2)
            );
        }

        this.method_1248(var4, var7, var9);
        this.uiRenderer.method_435();
    }

    public void method_1250(float var1) {
        var var2 = this;
        if (this.isToggled() && mc.theWorld != null && mc.thePlayer != null) {
            if (mc.gameSettings.thirdPersonView == 0) {
                ScaledResolution var18 = new ScaledResolution(mc);
                float var36 = var18.getScaledWidth();
                float var17 = var18.getScaledHeight();
                float var10000 = this.distance.method_151();
                float var26;
                float var27;
                if (!(mc.currentScreen instanceof GuiInventory)) {
                    float var25 = var10000;
                    var27 = 0.0F;
                    var26 = var25;
                } else {
                    var27 = 100.0F;
                    var26 = var10000;
                }

                float var4 = var26 + var27;
                this.floatValue = this.floatValue + (var4 - this.floatValue) * (float)(1.0 - Math.exp(-this.method_1246() / 90.0));
                double var5;
                double var7;
                float var28;
                if (WorldProjection.isFlag()) {
                    Vec3 var9 = WorldProjection.getVec31();
                    Vec3 var10;
                    Vec3 var40 = var10 = WorldProjection.getVec32();
                    var5 = var9.xCoord;
                    var7 = var9.zCoord;
                    var28 = var4 = (float)Math.toDegrees(Math.atan2(-var40.xCoord, var10.zCoord));
                } else {
                    var5 = mc.thePlayer.lastTickPosX + (mc.thePlayer.posX - mc.thePlayer.lastTickPosX) * var1;
                    var7 = mc.thePlayer.lastTickPosZ + (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ) * var1;
                    var28 = var4 = mc.thePlayer.prevRotationYaw + (mc.thePlayer.rotationYaw - mc.thePlayer.prevRotationYaw) * var1;
                }

                double var34 = Math.cos(Math.toRadians(var28));
                double var11 = Math.sin(Math.toRadians(var4));
                float var37 = var36 / 2.0F;
                var17 /= 2.0F;
                var4 = this.opacity.method_151() / 100.0F;
                float var13 = this.glow.method_151() / 100.0F;
                float var14 = this.size.method_151();
                double var15 = this.range.getValue();
                ArrayList var41 = new ArrayList<>(mc.theWorld.playerEntities);
                this.uiRenderer.method_468();
                Iterator var3 = var41.iterator();

                label48:
                while (true) {
                    Iterator var29 = var3;

                    while (var29.hasNext()) {
                        EntityPlayer var30;
                        if ((var30 = (EntityPlayer)var3.next()) == null) {
                            continue label48;
                        }

                        if (var30 == mc.thePlayer) {
                            var29 = var3;
                        } else {
                            if (!var30.isEntityAlive()) {
                                continue label48;
                            }

                            if (var30.isInvisible()) {
                                var29 = var3;
                            } else if (mc.thePlayer.getDistanceToEntity(var30) > var15) {
                                var29 = var3;
                            } else {
                                double var19 = var30.lastTickPosX + (var30.posX - var30.lastTickPosX) * var1 - var5;
                                double var21;
                                double var23 = -((var21 = var30.lastTickPosZ + (var30.posZ - var30.lastTickPosZ) * var1 - var7) * var34 - var19 * var11);
                                var19 = -(var19 * var34 + var21 * var11);
                                float var31 = (float)Math.atan2(var23, var19);
                                float var39 = (float)(var2.floatValue * Math.cos(var31)) + var37;
                                float var20 = (float)(var2.floatValue * Math.sin(var31)) + var17;
                                var2.method_1249(var39, var20, var31, var14, var4, var13);
                                var29 = var3;
                            }
                        }
                    }

                    var2.uiRenderer.method_483();
                    return;
                }
            }
        }
    }

    public Arrows() {
        super("Arrows", "Points at the players around you", Category.RENDER);
    }

    @Override
    public void onDisable() {
        this.floatValue = 0.0F;
        this.longValue = 0L;
    }

    private ResourceLocation method_1251() {
        switch ((Arrows.ArrowDesign)this.design.getValue()) {
            case SOLID:
                return resourceLocation;
            case CHEVRON:
                return resourceLocation2;
            default:
                return resourceLocation3;
        }
    }

    public static enum ArrowDesign {
        OUTLINE,
        SOLID,
        CHEVRON;
    }
}
