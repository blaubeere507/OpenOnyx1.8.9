package client.onyx.render.gui;

import client.onyx.Onyx;
import client.onyx.module.hud.CustomGui;
import client.onyx.module.render.NoRender;
import client.onyx.render.ui.UiRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;

public final class HotbarAnimator {
    private static float floatValue = Float.NaN;
    private static TextLayout textLayout;
    private static ChatAnimator chatAnimator;
    private static BossBarElement bossBarElement;
    private static TitleElement titleElement;
    private static ScoreboardElement scoreboardElement;

    public static float getFloatValue() {
        return floatValue;
    }

    private static CustomGui method_978() {
        CustomGui var0;
        if ((var0 = CustomGuiState.method_977()) == null) {
            return null;
        } else {
            if (scoreboardElement == null) {
                scoreboardElement = new ScoreboardElement(var0);
                titleElement = new TitleElement(var0);
                bossBarElement = new BossBarElement(var0);
                chatAnimator = new ChatAnimator(var0);
                textLayout = new TextLayout(var0);
            }

            return var0;
        }
    }

    public static boolean method_979() {
        CustomGui var0 = method_978();
        Minecraft var1 = Minecraft.getMinecraft();
        return var0 != null && var0.isToggled() && var1.thePlayer != null && var1.theWorld != null;
    }

    private HotbarAnimator() {
    }

    public static void method_980(UiRenderer var0, float var1, float var2) {
        CustomGui var3;
        if ((var3 = method_978()) != null) {
            Minecraft var10;
            if ((var10 = Minecraft.getMinecraft()).thePlayer != null && var10.theWorld != null) {
                float var5 = CustomGuiState.getFloatValue();
                boolean var11 = var10.currentScreen instanceof GuiChat;
                NoRender var6 = Onyx.moduleManager.noRender;
                CustomGui var7;
                if (var3.bossbarSettings.method_61() && !var6.method_1156(var6.bossBar)) {
                    bossBarElement.method_915(var0, var1, var2, var11);
                    var7 = var3;
                } else {
                    bossBarElement.method_908();
                    var7 = var3;
                }

                CustomGui var8;
                if (var7.scoreboardSettings.method_61() && !var6.method_1156(var6.scoreboard)) {
                    scoreboardElement.method_915(var0, var1, var2, var11);
                    var8 = var3;
                } else {
                    scoreboardElement.method_908();
                    var8 = var3;
                }

                float var9;
                if (var8.titleSettings.method_61()) {
                    titleElement.method_915(var0, var1, var2, var11);
                    var9 = var2;
                } else {
                    titleElement.method_908();
                    var9 = var2;
                }

                float var14 = var9;
                float var12 = var1 / 2.0F - 103.0F;
                float var15 = var1 / 2.0F + 103.0F;
                if (chatAnimator.method_988()) {
                    chatAnimator.method_982(var0, var1, var2, var5);
                    var14 = chatAnimator.getFloatValue2();
                    var12 = chatAnimator.getFloatValue4();
                    var15 = chatAnimator.method_989();
                }

                var1 = var14;
                if (textLayout.method_945()) {
                    textLayout.method_941(var0, var12, var15, var14, var5);
                    if (Float.isFinite(textLayout.getFloatValue5())) {
                        var1 = Math.min(var14, textLayout.getFloatValue5());
                    }
                }

                floatValue = var1;
            }
        }
    }

    public static void method_981() {
        if (scoreboardElement != null) {
            scoreboardElement.method_908();
            titleElement.method_908();
            bossBarElement.method_908();
            chatAnimator.method_990();
            textLayout.method_953();
            floatValue = Float.NaN;
        }
    }
}
