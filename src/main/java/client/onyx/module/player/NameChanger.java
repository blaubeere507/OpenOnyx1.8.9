package client.onyx.module.player;

import client.onyx.Onyx;
import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.module.ModuleManager;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.TextSetting;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;

public final class NameChanger extends Module {
    public TextSetting displayName = new TextSetting("Display name", "").describe("Shown instead of your real name, locally only");
    public BooleanSetting tabList = new BooleanSetting("Tab list", true);
    public BooleanSetting scoreboard = new BooleanSetting("Scoreboard", true);
    public BooleanSetting chat = new BooleanSetting("Chat", true).describe("Substitute your name inside chat messages too");
    public BooleanSetting nametag = new BooleanSetting("Nametag", true).describe("Your own in-world nametag (third person)");

    public static String method_1677() {
        NameChanger var0;
        return (var0 = method_1678()) == null ? "" : var0.displayName.getValue();
    }

    private static NameChanger method_1678() {
        ModuleManager var0 = Onyx.moduleManager;
        return Onyx.moduleManager == null ? null : var0.nameChanger;
    }

    private static boolean method_1679(GameProfile var0) {
        return mc.thePlayer != null && var0 != null && var0.getId() != null && var0.getId().equals(mc.thePlayer.getGameProfile().getId());
    }

    public static String method_1680(EntityPlayer var0) {
        return method_1683(var0) ? method_1677() : var0.getName();
    }

    private boolean method_1681() {
        return this.isToggled() && !this.displayName.getValue().isBlank();
    }

    public static String method_1682(String var0) {
        NameChanger var2;
        if ((var2 = method_1678()) == null || !var2.method_1681() || !var2.scoreboard.method_146()) {
            return var0;
        } else {
            return mc.thePlayer != null && var0.equals(mc.thePlayer.getGameProfile().getName()) ? var2.displayName.getValue() : var0;
        }
    }

    public NameChanger() {
        super("NameChanger", "Overrides your own displayed name locally", Category.PLAYER);
    }

    public static boolean method_1683(EntityLivingBase var0) {
        NameChanger var2;
        return (var2 = method_1678()) != null && var2.method_1681() && var2.nametag.method_146() && var0 == mc.thePlayer;
    }

    public static String method_1684(NetworkPlayerInfo var0, String var1) {
        NameChanger var3;
        return (var3 = method_1678()) != null && var3.method_1681() && var3.tabList.method_146() && method_1679(var0.getGameProfile())
            ? ScorePlayerTeam.formatPlayerName(var0.getPlayerTeam(), var3.displayName.getValue())
            : var1;
    }

    public static boolean method_1685() {
        NameChanger var0;
        return (var0 = method_1678()) != null && var0.method_1681() && var0.chat.method_146() && mc.thePlayer != null;
    }
}
