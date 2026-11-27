package client.onyx.util;

import java.util.Locale;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;

public final class HypixelUtil {
    private static final String STRING_CONST = "BED WARS";
    private static final int INT_11 = 1;

    public static boolean method_2176() {
        ServerData var0;
        return (var0 = IMinecraft.mc.getCurrentServerData()) != null && var0.serverIP != null && var0.serverIP.toLowerCase(Locale.ROOT).contains("hypixel.net");
    }

    private static String method_2177(String var0) {
        return var0 == null ? "" : EnumChatFormatting.getTextWithoutFormattingCodes(var0);
    }

    private static ScoreObjective method_2178() {
        if (IMinecraft.mc.theWorld == null) {
            return null;
        } else {
            Scoreboard var0;
            return (var0 = IMinecraft.mc.theWorld.getScoreboard()) == null ? null : var0.getObjectiveInDisplaySlot(1);
        }
    }

    public static boolean method_2179() {
        return method_2176() && method_2180().toUpperCase(Locale.ROOT).contains("BED WARS");
    }

    private static String method_2180() {
        ScoreObjective var0;
        return (var0 = method_2178()) == null ? "" : method_2177(var0.getDisplayName());
    }

    private HypixelUtil() {
    }
}
