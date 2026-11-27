package client.onyx.module.combat;

import client.onyx.Onyx;
import client.onyx.event.impl.PlayerUpdateEvent;
import client.onyx.event.impl.WorldLoadEvent;
import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.setting.impl.BooleanSetting;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;

public class AntiBot extends Module {
    public BooleanSetting tabCheck = new BooleanSetting("Tab check", false).describe("Also flags any player whose name is absent from the tab list");
    public BooleanSetting hideVisuals = new BooleanSetting("Hide visuals", true)
        .describe(
            "Also keep the render modules off flagged bots - no ESP, glow, chams, boxes, skeleton or name tag on a player the combat modules already ignore"
        );
    private volatile Set<String> set = new HashSet<>();

    public static boolean method_1637(EntityPlayer var0) {
        AntiBot var2;
        if ((var2 = method_1639()) == null || !var2.isToggled()) {
            return false;
        } else if (mc.isSingleplayer()) {
            return false;
        } else if (var2.tabCheck.method_146() && !var2.set.contains(var0.getName())) {
            return true;
        } else if (var0 instanceof EntityPlayerSP) {
            return false;
        } else {
            NetHandlerPlayClient var3;
            if ((var3 = mc.getNetHandler()) == null) {
                return false;
            } else {
                NetworkPlayerInfo var4;
                if ((var4 = var3.getPlayerInfo(var0.getName())) == null) {
                    return true;
                } else {
                    ScorePlayerTeam var5;
                    return var0.getName().startsWith("§k")
                        ? var0.isInvisible()
                        : var4.getResponseTime() < 1
                            || (var5 = var4.getPlayerTeam()) != null && var5.getRegisteredName().isEmpty() && var5.getColorPrefix().equals("§c");
                }
            }
        }
    }

    public static boolean method_1638(Entity var0) {
        AntiBot var2;
        return (var2 = method_1639()) != null
            && var2.isToggled()
            && var2.hideVisuals.method_146()
            && var0 instanceof EntityPlayer
            && method_1637((EntityPlayer)var0);
    }

    public AntiBot() {
        super("AntiBot", "Stops the combat modules from targeting bots", Category.COMBAT);
    }

    private static AntiBot method_1639() {
        return Onyx.moduleManager == null ? null : Onyx.moduleManager.antiBot;
    }

    @EventHandler
    public void onPlayerUpdate(PlayerUpdateEvent var1) {
        if (mc.thePlayer != null && mc.getNetHandler() != null) {
            HashSet var6 = new HashSet();
            Iterator var5;
            Iterator var4 = var5 = mc.getNetHandler().getPlayerInfoMap().iterator();

            while (var4.hasNext()) {
                NetworkPlayerInfo var3;
                if ((var3 = (NetworkPlayerInfo)var5.next()).getGameProfile().getId().equals(mc.thePlayer.getUniqueID())) {
                    var4 = var5;
                } else {
                    var6.add(var3.getGameProfile().getName());
                    var4 = var5;
                }
            }

            this.set = var6;
        }
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent var1) {
        this.set = new HashSet<>();
    }

    @Override
    public void onDisable() {
        this.set = new HashSet<>();
    }
}
