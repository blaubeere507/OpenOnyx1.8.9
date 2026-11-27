package client.onyx.command;

import client.onyx.util.IMinecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public abstract class Command implements IMinecraft {
    private final String string;
    private final String string3;
    private final String string2;
    private final String[] stringArray;

    public String getString3() {
        return this.string3;
    }

    public abstract void method_346(String[] var1);

    public boolean method_347(String var1) {
        if (this.string.equalsIgnoreCase(var1)) {
            return true;
        } else {
            String[] var4 = this.stringArray;
            int var3 = this.stringArray.length;
            int var6 = 0;

            for (int var5 = 0; var5 < var3; var5 = ++var6) {
                if (var4[var6].equalsIgnoreCase(var1)) {
                    return true;
                }
            }

            return false;
        }
    }

    public static void method_348(String var0) {
        method_349(new ChatComponentText(var0).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
    }

    public Command(String var1, String var2, String var3, String... var4) {
        this.string = var1;
        this.string3 = var2;
        this.string2 = var3;
        this.stringArray = var4;
    }

    public static void method_349(IChatComponent var0) {
        if (mc.ingameGUI != null) {
            mc.ingameGUI
                .getChatGUI()
                .printChatMessage(
                    new ChatComponentText("")
                        .appendSibling(new ChatComponentText("[").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_GRAY)))
                        .appendSibling(new ChatComponentText("onyx").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.AQUA)))
                        .appendSibling(new ChatComponentText("] ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_GRAY)))
                        .appendSibling(var0)
                );
        }
    }

    public static void method_350(String var0) {
        method_349(new ChatComponentText(var0).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GRAY)));
    }

    public String[] getStringArray() {
        return this.stringArray;
    }

    public String getString2() {
        return this.string2;
    }

    public String getString() {
        return this.string;
    }
}
