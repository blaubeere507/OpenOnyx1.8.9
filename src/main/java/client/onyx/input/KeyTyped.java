package client.onyx.input;

import net.minecraft.client.gui.GuiScreen;

public record KeyTyped(int intValue, int intValue2, int intValue3, char charValue) {
    public boolean method_1() {
        return this.method_2();
    }

    public boolean method_2() {
        return GuiScreen.isCtrlKeyDown() && GuiScreen.isCtrlCharTyped(this.charValue);
    }

    public boolean method_3() {
        return GuiScreen.isShiftKeyDown();
    }

    public boolean method_4() {
        return GuiScreen.isAltKeyDown();
    }

    public KeyTyped(int var1) {
        this(var1, 0, 0, '\u0000');
        boolean var10003 = false;
        boolean var10002 = false;
    }

    public KeyTyped(int var1, int var2, int var3) {
        this(var1, var2, var3, '\u0000');
        boolean var10004 = false;
    }
}
