package client.onyx.ui.screen;

import client.onyx.input.KeyTyped;
import client.onyx.input.MouseClick;
import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easings;
import client.onyx.render.effect.FrostedGlass;
import client.onyx.render.ui.PanelRenderer;
import client.onyx.render.ui.UiRenderer;
import client.onyx.ui.account.AccountPanel;
import client.onyx.ui.component.RenderContext;
import client.onyx.ui.theme.Theme;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

public class AccountManagerScreen extends GuiScreen {
    private static final float FLOAT_16_01 = 16.0F;
    private static final float FLOAT_0_921 = 0.92F;
    private static final float FLOAT_0_621 = 0.62F;
    private static final int INT_1201 = 120;
    private static final float FLOAT_0_321 = 0.32F;
    private static final float FLOAT_0_721 = 0.72F;
    private static final float FLOAT_100_01 = 100.0F;
    private final AccountPanel accountPanel = new AccountPanel();
    private final UiRenderer uiRenderer = new UiRenderer();
    private final Animation animation = new Animation(0.0F);
    private final Animation animation2 = new Animation(0.0F);
    private float floatValue = 1.0F;
    private final GuiScreen guiScreen;
    private boolean flag;
    private long longValue;
    private float floatValue2;
    private float floatValue3;
    private float floatValue4;

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private void method_2215() {
        float var2 = Math.min(this.width * 0.62F / this.accountPanel.getFloatValue4(), this.height * 0.72F / this.accountPanel.getFloatValue2());
        this.floatValue = Math.min(Theme.getFloatValue(), var2);
        this.floatValue2 = Theme.getFloatValue();
        this.accountPanel.method_2304(this.width / this.floatValue, this.height / this.floatValue);
    }

    @Override
    public void mouseClicked(int var1, int var2, int var3) throws IOException {
        this.method_2218();
        if (!this.accountPanel.method_2183(this.floatValue3, this.floatValue4, var3)) {
            super.mouseClicked(var1, var2, var3);
        }
    }

    private float method_2216(double var1) {
        return (float)(var1 / this.floatValue);
    }

    public AccountManagerScreen(GuiScreen var1) {
        this.guiScreen = var1;
    }

    @Override
    public void setWorldAndResolution(Minecraft var1, int var2, int var3) {
        super.setWorldAndResolution(var1, var2, var3);
        if (this.guiScreen != null && var1.theWorld == null) {
            this.guiScreen.setWorldAndResolution(var1, var2, var3);
        }

        this.method_2215();
    }

    @Override
    public void drawScreen(int var1, int var2, float var3) {
        float var4 = this.method_2217();
        this.animation.method_605(var4);
        this.animation2.method_605(var4);
        if (this.floatValue2 != Theme.getFloatValue()) {
            this.method_2215();
        }

        this.method_2218();
        if (this.mc.theWorld == null && this.guiScreen != null) {
            this.guiScreen.drawScreen(-1, -1, var3);
        }

        this.uiRenderer.method_468();
        if (this.mc.theWorld == null) {
            if (this.guiScreen == null) {
                this.uiRenderer.method_492(0.0F, 0.0F, this.width, this.height);
                this.uiRenderer.method_483();
                FrostedGlass.method_403();
                FrostedGlass.method_387();
                this.uiRenderer.method_468();
            }
        } else {
            PanelRenderer.method_599();
            this.uiRenderer.method_434(0.0F, 0.0F, this.width, this.height, Theme.method_176(0.32F * this.animation2.method_606()));
        }

        RenderContext var5 = new RenderContext(this.uiRenderer, var4, this.floatValue3, this.floatValue4);
        float var7 = this.animation.method_606();
        this.uiRenderer.method_441();
        this.uiRenderer.method_481().method_652(this.floatValue, this.floatValue);
        this.uiRenderer.method_491(0.92F + 0.07999998F * var7, this.accountPanel.method_2296(), this.accountPanel.method_2305());
        this.accountPanel.method_2301(var7);
        this.accountPanel.method_2206(var5);
        this.accountPanel.method_2186(var5);
        this.uiRenderer.method_435();
        this.uiRenderer.method_483();
    }

    private float method_2217() {
        long var1 = System.nanoTime();
        if (this.longValue == 0L) {
            this.longValue = var1;
            return 16.0F;
        } else {
            float var10000 = (float)(var1 - this.longValue) / 1000000.0F;
            this.longValue = var1;
            return Math.min(var10000, 100.0F);
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        int var2;
        if ((var2 = Mouse.getEventDWheel()) != 0) {
            this.method_2218();
            if (this.accountPanel.method_2211(this.floatValue3, this.floatValue4, var2 / 120.0)) {
                return;
            }
        }

        super.handleMouseInput();
    }

    @Override
    public void mouseReleased(int var1, int var2, int var3) {
        this.method_2218();
        if (!this.accountPanel.method_2189(this.floatValue3, this.floatValue4, var3)) {
            super.mouseReleased(var1, var2, var3);
        }
    }

    private void method_2218() {
        int var2 = new ScaledResolution(this.mc).getScaleFactor();
        this.floatValue3 = this.method_2216((float)Minecraft.getScaledMouseX() / var2);
        this.floatValue4 = this.method_2216((float)(this.mc.displayHeight - Minecraft.getScaledMouseY()) / var2);
    }

    @Override
    public void mouseClickMove(int var1, int var2, int var3, long var4) {
        float var6 = this.floatValue3;
        float var8 = this.floatValue4;
        this.method_2218();
        if (!this.accountPanel.method_2188(this.floatValue3, this.floatValue4, var3, this.floatValue3 - var6, this.floatValue4 - var8)) {
            super.mouseClickMove(var1, var2, var3, var4);
        }
    }

    @Override
    public void keyTyped(char var1, int var2) throws IOException {
        KeyTyped var3 = new KeyTyped(var2, 0, 0, var1);
        if (!this.accountPanel.method_2212(var3)) {
            if (var2 != 1 || !this.accountPanel.method_2307()) {
                if (var1 < ' ' || !this.accountPanel.method_2193(new MouseClick(var1, 0))) {
                    if (var2 == 1) {
                        this.mc.displayGuiScreen(this.guiScreen);
                    } else {
                        super.keyTyped(var1, var2);
                    }
                }
            }
        }
    }

    @Override
    public void initGui() {
        if (!this.flag) {
            this.flag = true;
            this.animation.method_607(0.0F).method_603(1.0F, 400.0F, Easings.easing7);
            this.animation2.method_607(0.0F).method_603(1.0F, 250.0F, Easings.easing5);
        }

        this.longValue = 0L;
        this.method_2215();
    }
}
