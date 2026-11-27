package client.onyx.module.combat;

import client.onyx.rotation.HitVecUtil;
import client.onyx.util.IMinecraft;
import client.onyx.util.player.TeamUtil;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

public enum TargetType implements IMinecraft {
    PLAYERS,
    TEAMMATES,
    MOBS,
    ANIMALS;

    private static boolean method_1582(Entity var0, float var1) {
        if (var1 >= 360.0F) {
            return true;
        } else {
            double var2 = var0.posX - mc.thePlayer.posX;
            double var4 = var0.posZ - mc.thePlayer.posZ;
            return Math.abs(MathHelper.wrapAngleTo180_float((float)Math.toDegrees(Math.atan2(-var2, var4)) - mc.thePlayer.rotationYaw)) <= var1 / 2.0F;
        }
    }

    public static boolean method_1583(double var0, float var2, Set<TargetType> var3) {
        float var6 = var2;
        if (mc.thePlayer != null && mc.theWorld != null) {
            double var4 = var0 * var0;
            Iterator var1 = mc.theWorld.loadedEntityList.iterator();

            label49:
            while (true) {
                Iterator var7 = var1;

                while (var7.hasNext()) {
                    Entity var8;
                    if (!((var8 = (Entity)var1.next()) instanceof EntityLivingBase)) {
                        continue label49;
                    }

                    EntityLivingBase var9;
                    if ((var9 = (EntityLivingBase)var8) == mc.thePlayer) {
                        var7 = var1;
                    } else if (!var9.isEntityAlive()) {
                        var7 = var1;
                    } else if (var9 instanceof EntityArmorStand) {
                        var7 = var1;
                    } else if (var9 instanceof EntityPlayer && ((EntityPlayer)var9).isSpectator()) {
                        var7 = var1;
                    } else if (!method_1584(var9, var3)) {
                        var7 = var1;
                    } else if (HitVecUtil.method_2131(var9) > var4) {
                        var7 = var1;
                    } else {
                        if (method_1582(var9, var6)) {
                            return true;
                        }

                        var7 = var1;
                    }
                }

                return false;
            }
        } else {
            return false;
        }
    }

    private static boolean method_1584(EntityLivingBase var0, Set<TargetType> var1) {
        if (var0 instanceof EntityPlayer) {
            EntityPlayer var2;
            if (AntiBot.method_1637(var2 = (EntityPlayer)var0)) {
                return false;
            } else {
                return !TeamUtil.method_2155(mc.thePlayer, var2) ? var1.contains(PLAYERS) : var1.contains(TEAMMATES);
            }
        } else {
            return var0 instanceof EntityAnimal ? var1.contains(ANIMALS) : var0 instanceof EntityLiving && var1.contains(MOBS);
        }
    }
}
