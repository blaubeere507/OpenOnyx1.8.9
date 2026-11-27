package client.onyx.ui.screen;

import client.onyx.input.KeyTyped;
import client.onyx.input.MouseClick;
import client.onyx.module.Module;
import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easings;
import client.onyx.render.effect.FrostedGlass;
import client.onyx.render.ui.PanelRenderer;
import client.onyx.render.ui.UiRenderer;
import client.onyx.setting.SettingHistory;
import client.onyx.ui.clickgui.ClickGuiPanel;
import client.onyx.ui.component.RenderContext;
import client.onyx.ui.component.TextField;
import client.onyx.ui.theme.Theme;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

public class ClickGuiScreen extends GuiScreen {
    private static final float FLOAT_100_01 = 100.0F;
    private static final float FLOAT_0_621 = 0.62F;
    private static final int INT_1201 = 120;
    private static final float FLOAT_0_721 = 0.72F;
    private static final float FLOAT_16_01 = 16.0F;
    private final ClickGuiPanel clickGuiPanel = new ClickGuiPanel();
    private final UiRenderer uiRenderer = new UiRenderer();
    private final Animation animation = new Animation(0.0F);
    private final Animation animation2 = new Animation(0.0F);
    private float floatValue2 = 1.0F;
    private final Module module;
    private final GuiScreen guiScreen;
    private float floatValue;
    private float floatValue3;
    private long longValue;
    private boolean flag;
    private float floatValue4;
    private boolean flag2;

    private void method_2342() {
        int var2 = new ScaledResolution(this.mc).getScaleFactor();
        this.floatValue4 = this.method_2348((float)Minecraft.getScaledMouseX() / var2);
        this.floatValue = this.method_2348((float)(this.mc.displayHeight - Minecraft.getScaledMouseY()) / var2);
    }

    @Override
    public void mouseClicked(int var1, int var2, int var3) throws IOException {
        this.method_2342();
        if (!this.clickGuiPanel.method_2183(this.floatValue4, this.floatValue, var3)) {
            super.mouseClicked(var1, var2, var3);
        }
    }

    @Override
    public void drawScreen(int var1, int var2, float var3) {
        if (this.method_2344()) {
            this.method_2347();
        } else {
            float var4 = this.method_2345();
            this.animation.method_605(var4);
            this.animation2.method_605(var4);
            if (this.floatValue3 != Theme.getFloatValue()) {
                this.method_2343();
            }

            this.method_2342();
            this.uiRenderer.method_468();
            if (this.mc.theWorld == null) {
                this.uiRenderer.method_492(0.0F, 0.0F, this.width, this.height);
                this.uiRenderer.method_483();
                FrostedGlass.method_403();
                FrostedGlass.method_387();
                this.uiRenderer.method_468();
            } else {
                PanelRenderer.method_599();
                this.uiRenderer.method_434(0.0F, 0.0F, this.width, this.height, Theme.method_176(0.32F * this.animation2.method_606()));
            }

            RenderContext var5 = new RenderContext(this.uiRenderer, var4, this.floatValue4, this.floatValue);
            float var7 = this.animation.method_606();
            this.uiRenderer.method_441();
            this.uiRenderer.method_481().method_652(this.floatValue2, this.floatValue2);
            this.uiRenderer.method_491(0.92F + 0.08F * var7, this.clickGuiPanel.method_2185(), this.clickGuiPanel.method_2197());
            this.clickGuiPanel.method_2210(var7);
            this.clickGuiPanel.method_2206(var5);
            this.clickGuiPanel.method_2186(var5);
            this.uiRenderer.method_435();
            this.uiRenderer.method_483();
        }
    }

    private void method_2343() {
        float var2 = Math.min(this.width * 0.62F / this.clickGuiPanel.getFloatValue4(), this.height * 0.72F / this.clickGuiPanel.getFloatValue2());
        this.floatValue2 = Math.min(Theme.getFloatValue(), var2);
        this.floatValue3 = Theme.getFloatValue();
        this.clickGuiPanel.method_2204(this.width / this.floatValue2, this.height / this.floatValue2);
    }

    @Override
    public void initGui() {
        if (!this.flag2) {
            this.flag2 = true;
            SettingHistory.enable();
            this.animation.method_607(0.0F).method_603(1.0F, 400.0F, Easings.easing7);
            this.animation2.method_607(0.0F).method_603(1.0F, 250.0F, Easings.easing5);
        }

        this.longValue = 0L;
        this.flag = this.method_2346();
        this.method_2343();
    }

    public ClickGuiScreen(Module var1, GuiScreen var2) {
        this.module = var1;
        this.guiScreen = var2;
    }

    @Override
    public void onGuiClosed() {
        SettingHistory.disable();
        if (this.module != null && this.module.isToggled()) {
            this.module.toggle();
        }
    }

    private boolean method_2344() {
        boolean var2;
        if ((var2 = this.method_2346()) == this.flag) {
            return false;
        } else {
            this.flag = var2;
            return var2 && !TextField.method_2406();
        }
    }

    private float method_2345() {
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
    public void mouseReleased(int var1, int var2, int var3) {
        this.method_2342();
        if (!this.clickGuiPanel.method_2189(this.floatValue4, this.floatValue, var3)) {
            super.mouseReleased(var1, var2, var3);
        }
    }

    @Override
    public void mouseClickMove(int var1, int var2, int var3, long var4) {
        float var6 = this.floatValue4;
        float var8 = this.floatValue;
        this.method_2342();
        if (!this.clickGuiPanel.method_2188(this.floatValue4, this.floatValue, var3, this.floatValue4 - var6, this.floatValue - var8)) {
            super.mouseClickMove(var1, var2, var3, var4);
        }
    }

    private boolean method_2346() {
        return this.module != null && this.module.getBind().method_163() && this.module.getBind().method_157();
    }

    private void method_2347() {
        this.mc.displayGuiScreen(this.guiScreen);
    }

    @Override
    public void handleMouseInput() throws IOException {
        int var2;
        if ((var2 = Mouse.getEventDWheel()) != 0) {
            this.method_2342();
            if (this.clickGuiPanel.method_2211(this.floatValue4, this.floatValue, var2 / 120.0)) {
                return;
            }
        }

        super.handleMouseInput();
    }

    @Override
    public void setWorldAndResolution(Minecraft var1, int var2, int var3) {
        super.setWorldAndResolution(var1, var2, var3);
        this.method_2343();
    }

    @Override
    public void keyTyped(char var1, int var2) throws IOException {
        KeyTyped var3 = new KeyTyped(var2, 0, 0, var1);
        if (!this.clickGuiPanel.method_2212(var3)) {
            if (var2 != 1 || !this.clickGuiPanel.method_2203()) {
                if (this.module == null || !this.module.getBind().method_155(var2) || TextField.method_2406()) {
                    if (var1 < ' ' || !this.clickGuiPanel.method_2193(new MouseClick(var1, 0))) {
                        if (var2 == 1) {
                            this.method_2347();
                        } else {
                            super.keyTyped(var1, var2);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private float method_2348(double var1) {
        return (float)(var1 / this.floatValue2);
    }
}
