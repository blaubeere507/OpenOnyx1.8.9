package client.onyx.module.render;

import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.render.world.BoxRenderer;
import client.onyx.render.world.TrailLineRenderer;
import client.onyx.setting.SettingGroup;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.ColorSetting;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.ui.theme.color.ColorUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public final class Trajectories extends Module {
    private static final double DOUBLE_0_031 = 0.03;
    private static final double DOUBLE_0_051 = 0.05;
    private static final double DOUBLE_0_991 = 0.99;
    public final ColorSetting color = new ColorSetting("Color", -8857601).method_116().describe("Colour of the predicted path");
    public final ColorSetting impactColor = new ColorSetting("Impact color", -38302).describe("Colour of the landing marker");
    public final NumberSetting lineWidth = new NumberSetting("Line width", 1.5, 0.5, 5.0, 0.5).method_147(" px");
    public final BooleanSetting impactMarker = new BooleanSetting("Impact marker", true).describe("Draw a box where the projectile lands");
    public final BooleanSetting throughWalls = new BooleanSetting("Through walls", false);
    public final NumberSetting maxTicks = new NumberSetting("Max ticks", 120.0, 20.0, 300.0, 10.0).describe("How far ahead to simulate");
    public final Trajectories.ProjectileSettings projectileSettings = new Trajectories.ProjectileSettings();

    public void method_1224(float var1) {
        if (this.isToggled() && mc.theWorld != null && mc.thePlayer != null) {
            Trajectories.ProjectileKind var7;
            if ((var7 = this.method_1225(mc.thePlayer.getHeldItem())) != null) {
                float var3;
                if (!((var3 = this.method_1226(var7)) <= 0.0F)) {
                    Trajectories.PredictedPath var8;
                    if ((var8 = this.method_1228(var7, var3, var1)).list().size() >= 2) {
                        if (TrailLineRenderer.method_592(var1, this.throughWalls.method_146(), this.lineWidth.method_151())) {
                            int var10;
                            int var10002 = var10 = this.color.getValue();
                            int var4 = ColorUtils.method_209(var10002, ColorUtils.method_213(var10002) / 255.0F * 0.35F);
                            TrailLineRenderer.method_587(var8.list(), var10, var4);
                            TrailLineRenderer.method_594();
                            if (this.impactMarker.method_146() && var8.vec31() != null) {
                                if (BoxRenderer.method_519(var1, this.throughWalls.method_146())) {
                                    int var9 = this.impactColor.getValue();
                                    double var5 = 0.12;
                                    BoxRenderer.method_518(
                                        AxisAlignedBB.fromBounds(
                                            var8.vec31().xCoord - var5,
                                            var8.vec31().yCoord - var5,
                                            var8.vec31().zCoord - var5,
                                            var8.vec31().xCoord + var5,
                                            var8.vec31().yCoord + var5,
                                            var8.vec31().zCoord + var5
                                        ),
                                        var9,
                                        this.lineWidth.method_151(),
                                        ColorUtils.method_209(var9, ColorUtils.method_213(var9) / 255.0F * 0.25F)
                                    );
                                    BoxRenderer.method_521();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public Trajectories() {
        super("Trajectories", "Predicts where thrown items will land", Category.RENDER);
    }

    private Trajectories.ProjectileKind method_1225(ItemStack var1) {
        if (var1 == null) {
            return null;
        } else if (var1.getItem() instanceof ItemEnderPearl) {
            return this.projectileSettings.enderPearls.method_146() ? Trajectories.ProjectileKind.PEARL : null;
        } else if (!(var1.getItem() instanceof ItemSnowball) && !(var1.getItem() instanceof ItemEgg)) {
            if (var1.getItem() instanceof ItemPotion) {
                return this.projectileSettings.splashPotions.method_146() && ItemPotion.isSplash(var1.getMetadata())
                    ? Trajectories.ProjectileKind.POTION
                    : null;
            } else if (!(var1.getItem() instanceof ItemBow)) {
                return null;
            } else {
                return this.projectileSettings.bows.method_146() ? Trajectories.ProjectileKind.ARROW : null;
            }
        } else {
            return this.projectileSettings.snowballsAndEggs.method_146() ? Trajectories.ProjectileKind.SNOWBALL : null;
        }
    }

    private float method_1226(Trajectories.ProjectileKind var1) {
        if (var1 != Trajectories.ProjectileKind.ARROW) {
            return var1.getFloatValue();
        } else if (!mc.thePlayer.isUsingItem()) {
            return 0.0F;
        } else {
            float var2;
            float var10000 = var2 = mc.thePlayer.getItemInUseDuration() / 20.0F;
            float var3;
            if ((var3 = (var10000 * var10000 + var2 * 2.0F) / 3.0F) < 0.1F) {
                return 0.0F;
            } else {
                if (var3 > 1.0F) {
                    var3 = 1.0F;
                }

                return var3 * 2.0F * 1.5F;
            }
        }
    }

    private Vec3 method_1227(Vec3 var1, Vec3 var2) {
        Vec3 var11 = var1;
        if (!this.projectileSettings.stopAtEntities.method_146()) {
            return null;
        } else {
            AxisAlignedBB var3 = AxisAlignedBB.fromBounds(
                    Math.min(var1.xCoord, var2.xCoord),
                    Math.min(var1.yCoord, var2.yCoord),
                    Math.min(var1.zCoord, var2.zCoord),
                    Math.max(var1.xCoord, var2.xCoord),
                    Math.max(var1.yCoord, var2.yCoord),
                    Math.max(var1.zCoord, var2.zCoord)
                )
                .expand(1.0, 1.0, 1.0);
            Vec3 var4 = null;
            double var5 = Double.MAX_VALUE;
            Iterator var13 = mc.theWorld.getEntitiesWithinAABBExcludingEntity(mc.thePlayer, var3).iterator();

            label35:
            while (true) {
                Iterator var10 = var13;

                while (var10.hasNext()) {
                    Entity var7;
                    if (!(var7 = (Entity)var13.next()).canBeCollidedWith()) {
                        continue label35;
                    }

                    if (var7 == mc.thePlayer) {
                        var10 = var13;
                    } else {
                        MovingObjectPosition var14;
                        if ((var14 = var7.getEntityBoundingBox().expand(0.3, 0.3, 0.3).calculateIntercept(var11, var2)) == null) {
                            continue label35;
                        }

                        if (var14.hitVec != null) {
                            double var8;
                            if ((var8 = var11.squareDistanceTo(var14.hitVec)) < var5) {
                                var5 = var8;
                                var4 = var14.hitVec;
                            }
                            continue label35;
                        }

                        var10 = var13;
                    }
                }

                return var4;
            }
        }
    }

    private Trajectories.PredictedPath method_1228(Trajectories.ProjectileKind var1, float var2, float var3) {
        var var24 = this;
        var3 = mc.thePlayer.rotationYaw;
        float var4 = mc.thePlayer.rotationPitch;
        double var5 = var3 / 180.0 * Math.PI;
        double var7 = var4 / 180.0 * Math.PI;
        double var9 = (var4 + var1.getFloatValue2()) / 180.0 * Math.PI;
        double var11 = mc.thePlayer.posX - Math.cos(var5) * 0.16;
        double var13 = mc.thePlayer.posY + mc.thePlayer.getEyeHeight() - 0.1F;
        double var15 = mc.thePlayer.posZ - Math.sin(var5) * 0.16;
        double var17 = -Math.sin(var5) * Math.cos(var7);
        var5 = Math.cos(var5) * Math.cos(var7);
        var7 = -Math.sin(var9);
        if ((var9 = Math.sqrt(var17 * var17 + var7 * var7 + var5 * var5)) < 1.0E-6) {
            return new Trajectories.PredictedPath(List.of(), null);
        } else {
            var17 = var17 / var9 * var2;
            var7 = var7 / var9 * var2;
            var5 = var5 / var9 * var2;
            var9 = var1.getDoubleValue();
            ArrayList var23 = new ArrayList();
            var23.add(new Vec3(var11, var13, var15));
            int var25 = this.maxTicks.method_149();
            int var27 = 0;

            for (int var21 = var27; var21 < var25; var21 = var27) {
                Vec3 var28 = new Vec3(var11, var13, var15);
                var11 += var17;
                var13 += var7;
                var15 += var5;
                Vec3 var19 = new Vec3(var11, var13, var15);
                MovingObjectPosition var20;
                if ((var20 = mc.theWorld.rayTraceBlocks(var28, var19, false, true, false)) != null && var20.hitVec != null) {
                    var23.add(var20.hitVec);
                    return new Trajectories.PredictedPath(var23, var20.hitVec);
                }

                Vec3 var29;
                if ((var29 = var24.method_1227(var28, var19)) != null) {
                    var23.add(var29);
                    return new Trajectories.PredictedPath(var23, var29);
                }

                var23.add(var19);
                if (var13 < 0.0) {
                    return new Trajectories.PredictedPath(var23, null);
                }

                var17 *= 0.99;
                var7 *= 0.99;
                var5 *= 0.99;
                var27++;
                var7 -= var9;
            }

            return new Trajectories.PredictedPath(var23, null);
        }
    }

    private record PredictedPath(List<Vec3> list, Vec3 vec31) {
    }

    private static enum ProjectileKind {
        PEARL(1.5F, 0.03, 0.0F),
        SNOWBALL(1.5F, 0.03, 0.0F),
        POTION(0.5F, 0.05, -20.0F),
        ARROW(0.0F, 0.05, 0.0F);

        private final float floatValue;
        private final double doubleValue;
        private final float floatValue2;

        public float getFloatValue() {
            return this.floatValue;
        }

        public double getDoubleValue() {
            return this.doubleValue;
        }

        private ProjectileKind(float var3, double var4, float var6) {
            this.floatValue = var3;
            this.doubleValue = var4;
            this.floatValue2 = var6;
        }

        public float getFloatValue2() {
            return this.floatValue2;
        }
    }

    public static final class ProjectileSettings extends SettingGroup {
        public final BooleanSetting enderPearls = new BooleanSetting("Ender pearls", true);
        public final BooleanSetting snowballsAndEggs = new BooleanSetting("Snowballs and eggs", true);
        public final BooleanSetting splashPotions = new BooleanSetting("Splash potions", true);
        public final BooleanSetting bows = new BooleanSetting("Bows", true);
        public final BooleanSetting stopAtEntities = new BooleanSetting("Stop at entities", true).describe("End the path where it would hit a player or mob");

        private ProjectileSettings() {
            super("Projectiles");
        }
    }
}
