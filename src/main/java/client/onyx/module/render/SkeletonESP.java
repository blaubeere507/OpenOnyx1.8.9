package client.onyx.module.render;

import client.onyx.Onyx;
import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.module.combat.AntiBot;
import client.onyx.render.effect.ColorGrading;
import client.onyx.render.world.TrailLineRenderer;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.ColorSetting;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.ui.theme.color.ColorUtils;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

public final class SkeletonESP extends Module {
    public final ColorSetting color = new ColorSetting("Color", -8857601).method_116().describe("Colour of the bones");
    public final BooleanSetting healthColor = new BooleanSetting("Health color", false).describe("Fade the bones from green to red as the target loses health");
    public final NumberSetting lineWidth = new NumberSetting("Line width", 1.5, 0.5, 5.0, 0.5).method_147(" px");
    public final BooleanSetting joints = new BooleanSetting("Joints", true).describe("Draw a dot at every joint");
    public final NumberSetting jointSize = new NumberSetting("Joint size", 3.0, 1.0, 8.0, 0.5).method_147(" px").visibleWhen(this.joints);
    public final BooleanSetting throughWalls = new BooleanSetting("Through walls", true);
    public final NumberSetting range = new NumberSetting("Range", 128.0, 8.0, 256.0, 8.0).method_147(" m");
    public final BooleanSetting mobs = new BooleanSetting("Mobs", false).describe("Also draw bones for non-player mobs");

    public SkeletonESP() {
        super("SkeletonESP", "Draws the bones of nearby players", Category.RENDER);
    }

    private void method_1071(Vec3 var1, Vec3 var2, int var3) {
        TrailLineRenderer.method_585(var1, var2, var3, var3);
    }

    private void method_1072(ColorGrading.Params var1, int var2) {
        TrailLineRenderer.method_595();
        this.method_1071(var1.vec310(), var1.vec36(), var2);
        this.method_1071(var1.vec36(), var1.vec32(), var2);
        this.method_1071(var1.vec36(), var1.vec38(), var2);
        this.method_1071(var1.vec36(), var1.vec39(), var2);
        this.method_1071(var1.vec38(), var1.vec35(), var2);
        this.method_1071(var1.vec39(), var1.vec311(), var2);
        this.method_1071(var1.vec32(), var1.vec31(), var2);
        this.method_1071(var1.vec32(), var1.vec37(), var2);
        this.method_1071(var1.vec31(), var1.vec34(), var2);
        this.method_1071(var1.vec37(), var1.vec33(), var2);
        TrailLineRenderer.method_586();
        if (this.joints.method_146()) {
            float var3 = this.jointSize.method_151();
            TrailLineRenderer.method_589(var1.vec310(), var3, var2);
            TrailLineRenderer.method_589(var1.vec36(), var3, var2);
            TrailLineRenderer.method_589(var1.vec32(), var3, var2);
            TrailLineRenderer.method_589(var1.vec38(), var3, var2);
            TrailLineRenderer.method_589(var1.vec39(), var3, var2);
            TrailLineRenderer.method_589(var1.vec35(), var3, var2);
            TrailLineRenderer.method_589(var1.vec311(), var3, var2);
            TrailLineRenderer.method_589(var1.vec34(), var3, var2);
            TrailLineRenderer.method_589(var1.vec33(), var3, var2);
        }
    }

    public void method_1073(float var1) {
        var var6 = this;
        if (this.isToggled() && mc.theWorld != null && mc.thePlayer != null) {
            double var2 = this.range.getValue();
            boolean var4 = false;
            Iterator var5 = mc.theWorld.loadedEntityList.iterator();

            label60:
            while (true) {
                Iterator var7 = var5;

                while (var7.hasNext()) {
                    EntityLivingBase var8;
                    if (!(var5.next() instanceof EntityLivingBase var8x) || (var8 = var8x) == mc.thePlayer) {
                        continue label60;
                    }

                    if (!var8.isEntityAlive()) {
                        var7 = var5;
                    } else if (AntiBot.method_1638((Entity)var8)) {
                        var7 = var5;
                    } else if (!(var8 instanceof EntityPlayer) && !var6.mobs.method_146()) {
                        var7 = var5;
                    } else if (var8.isInvisible() && !var6.method_1074((EntityLivingBase)var8)) {
                        var7 = var5;
                    } else if (mc.thePlayer.getDistanceToEntity((Entity)var8) > var2) {
                        var7 = var5;
                    } else {
                        if (!var4) {
                            if (!TrailLineRenderer.method_592(var1, var6.throughWalls.method_146(), var6.lineWidth.method_151())) {
                                return;
                            }

                            var4 = true;
                        }

                        var6.method_1072(ColorGrading.method_825((EntityLivingBase)var8, var1), var6.method_1075((EntityLivingBase)var8));
                        var7 = var5;
                    }
                }

                if (var4) {
                    TrailLineRenderer.method_594();
                }

                return;
            }
        }
    }

    private boolean method_1074(EntityLivingBase var1) {
        return Onyx.moduleManager != null && Onyx.moduleManager.seeInvisibles.method_1085(var1);
    }

    private int method_1075(EntityLivingBase var1) {
        int var2 = this.color.getValue();
        if (!this.healthColor.method_146()) {
            return var2;
        } else {
            float var4;
            if (var1.getMaxHealth() <= 0.0F) {
                var4 = 1.0F;
            } else {
                var4 = Math.clamp(var1.getHealth() / var1.getMaxHealth(), 0.0F, 1.0F);
            }

            int var6 = Math.round(255.0F - 100.0F * var4);
            int var5 = Math.round(90.0F + 137.0F * var4);
            return ColorUtils.method_204(ColorUtils.method_213(var2), var6, var5, 98);
        }
    }
}
