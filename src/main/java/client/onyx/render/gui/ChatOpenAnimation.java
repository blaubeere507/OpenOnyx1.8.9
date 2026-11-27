package client.onyx.render.gui;

import client.onyx.module.hud.CustomGui;
import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easing;
import client.onyx.render.anim.Easings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;

public final class ChatOpenAnimation {
    private static final Animation animation = new Animation(0.0F);
    public static final float FLOAT_8_01 = 8.0F;

    public static void method_994(float var0) {
        CustomGui var14;
        if ((var14 = CustomGuiState.method_977()) != null && var14.isToggled() && var14.chatSettings.method_61()) {
            boolean var2 = Minecraft.getMinecraft().currentScreen instanceof GuiChat;
            var0 = var14.chatSettings.duration.method_151();
            Animation var4;
            float var5;
            float var6;
            if (!var2) {
                Animation var3 = animation;
                var6 = var0;
                var5 = 0.0F;
                var4 = var3;
            } else {
                var6 = var0;
                var5 = 1.0F;
                var4 = animation;
            }

            Animation var10;
            float var11;
            float var12;
            Easing var13;
            if (!var2) {
                var13 = Easings.easing;
                var12 = var6;
                var11 = var5;
                var10 = var4;
            } else {
                var13 = Easings.easing7;
                var12 = var6;
                var11 = var5;
                var10 = var4;
            }

            var10.method_603(var11, var12, var13);
            animation.method_605(var0);
        } else {
            animation.method_607(0.0F);
        }
    }

    public static void method_995() {
        animation.method_607(0.0F);
    }

    public static float method_996() {
        return animation.method_606();
    }

    public static float method_997() {
        CustomGui var0;
        return (var0 = CustomGuiState.method_977()) != null && var0.isToggled() && var0.chatSettings.method_61()
            ? animation.method_606() * var0.chatSettings.hotbarLift.method_151()
            : 0.0F;
    }

    private ChatOpenAnimation() {
    }
}
