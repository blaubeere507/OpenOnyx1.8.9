package client.onyx.util;

import client.onyx.module.combat.AntiBot;
import client.onyx.util.player.TeamUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;

public enum TargetGroup {
    SELF,
    ENEMY,
    TEAM,
    MOBS;

    public static final String[] stringArray = new String[]{"Self", "Enemy", "Team", "Mobs"};

    public static TargetGroup method_2141(EntityLivingBase var0) {
        Minecraft var3 = IMinecraft.mc;
        if (IMinecraft.mc.thePlayer == null || var3.theWorld == null) {
            return null;
        } else if (!var0.isEntityAlive()) {
            return null;
        } else if (var0 instanceof EntityArmorStand) {
            return null;
        } else {
            EntityPlayer var2;
            if (var0 instanceof EntityPlayer && (var2 = (EntityPlayer)var0).isSpectator()) {
                return null;
            } else if (AntiBot.method_1638(var0)) {
                return null;
            } else if (var0 == var3.thePlayer) {
                return var3.gameSettings.thirdPersonView == 0 ? null : SELF;
            } else if (var0 instanceof EntityPlayer var4) {
                return TeamUtil.method_2155(var3.thePlayer, var4) ? TEAM : ENEMY;
            } else {
                return MOBS;
            }
        }
    }
}
