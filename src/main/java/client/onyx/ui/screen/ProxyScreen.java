package client.onyx.ui.screen;

import client.onyx.input.KeyTyped;
import client.onyx.input.MouseClick;
import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easings;
import client.onyx.render.effect.FrostedGlass;
import client.onyx.render.ui.PanelRenderer;
import client.onyx.render.ui.UiRenderer;
import client.onyx.ui.account.ProxyButton;
import client.onyx.ui.component.RenderContext;
import client.onyx.ui.theme.Theme;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class ProxyScreen extends GuiScreen {
    private static final float FLOAT_0_921 = 0.92F;
    private static final float FLOAT_100_01 = 100.0F;
    private static final float FLOAT_0_51 = 0.5F;
    private static final float FLOAT_0_321 = 0.32F;
    private static final float FLOAT_16_01 = 16.0F;
    private static final float FLOAT_0_721 = 0.72F;
    private final ProxyButton proxyButton = new ProxyButton(this::method_2263);
    private final UiRenderer uiRenderer = new UiRenderer();
    private final Animation animation = new Animation(0.0F);
    private final Animation animation2 = new Animation(0.0F);
    private float floatValue4 = 1.0F;
    private final GuiScreen guiScreen;
    private float floatValue;
    private boolean flag;
    private long longValue;
    private float floatValue2;
    private float floatValue3;

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private void method_2261() {
        float var2 = Math.min(this.width * 0.5F / this.proxyButton.getFloatValue4(), this.height * 0.72F / this.proxyButton.getFloatValue2());
        this.floatValue4 = Math.min(Theme.getFloatValue(), var2);
        this.floatValue2 = Theme.getFloatValue();
        this.proxyButton.method_2353(this.width / this.floatValue4, this.height / this.floatValue4);
    }

    @Override
    public void mouseClicked(int var1, int var2, int var3) throws IOException {
        this.method_2262();
        if (!this.proxyButton.method_2183(this.floatValue, this.floatValue3, var3)) {
            super.mouseClicked(var1, var2, var3);
        }
    }

    public ProxyScreen(GuiScreen var1) {
        this.guiScreen = var1;
    }

    private void method_2262() {
        int var2 = new ScaledResolution(this.mc).getScaleFactor();
        this.floatValue = this.method_2265((float)Minecraft.getScaledMouseX() / var2);
        this.floatValue3 = this.method_2265((float)(this.mc.displayHeight - Minecraft.getScaledMouseY()) / var2);
    }

    @Override
    public void mouseClickMove(int var1, int var2, int var3, long var4) {
        float var6 = this.floatValue;
        float var8 = this.floatValue3;
        this.method_2262();
        if (!this.proxyButton.method_2188(this.floatValue, this.floatValue3, var3, this.floatValue - var6, this.floatValue3 - var8)) {
            super.mouseClickMove(var1, var2, var3, var4);
        }
    }

    @Override
    public void keyTyped(char var1, int var2) throws IOException {
        KeyTyped var3 = new KeyTyped(var2, 0, 0, var1);
        if (!this.proxyButton.method_2212(var3)) {
            if (var1 < ' ' || !this.proxyButton.method_2193(new MouseClick(var1, 0))) {
                if (var2 == 1) {
                    this.method_2263();
                } else {
                    super.keyTyped(var1, var2);
                }
            }
        }
    }

    private void method_2263() {
        this.mc.displayGuiScreen(this.guiScreen);
    }

    @Override
    public void mouseReleased(int var1, int var2, int var3) {
        this.method_2262();
        if (!this.proxyButton.method_2189(this.floatValue, this.floatValue3, var3)) {
            super.mouseReleased(var1, var2, var3);
        }
    }

    @Override
    public void drawScreen(int var1, int var2, float var3) {
        float var4 = this.method_2264();
        this.animation.method_605(var4);
        this.animation2.method_605(var4);
        if (this.floatValue2 != Theme.getFloatValue()) {
            this.method_2261();
        }

        this.method_2262();
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

        RenderContext var5 = new RenderContext(this.uiRenderer, var4, this.floatValue, this.floatValue3);
        float var7 = this.animation.method_606();
        this.uiRenderer.method_441();
        this.uiRenderer.method_481().method_652(this.floatValue4, this.floatValue4);
        this.uiRenderer.method_491(0.92F + 0.07999998F * var7, this.proxyButton.method_2354(), this.proxyButton.method_2350());
        this.proxyButton.method_2352(var7);
        this.proxyButton.method_2206(var5);
        this.proxyButton.method_2186(var5);
        this.uiRenderer.method_435();
        this.uiRenderer.method_483();
    }

    private float method_2264() {
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

    private float method_2265(double var1) {
        return (float)(var1 / this.floatValue4);
    }

    @Override
    public void initGui() {
        if (!this.flag) {
            this.flag = true;
            this.proxyButton.method_2351();
            this.animation.method_607(0.0F).method_603(1.0F, 400.0F, Easings.easing7);
            this.animation2.method_607(0.0F).method_603(1.0F, 250.0F, Easings.easing5);
        }

        this.longValue = 0L;
        this.method_2261();
    }

    @Override
    public void setWorldAndResolution(Minecraft var1, int var2, int var3) {
        super.setWorldAndResolution(var1, var2, var3);
        if (this.guiScreen != null && var1.theWorld == null) {
            this.guiScreen.setWorldAndResolution(var1, var2, var3);
        }

        this.method_2261();
    }
}
