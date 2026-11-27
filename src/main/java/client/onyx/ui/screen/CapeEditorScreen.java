package client.onyx.ui.screen;

import client.onyx.Onyx;
import client.onyx.module.render.CapeChanger;
import client.onyx.render.texture.ImageFit;
import client.onyx.render.ui.UiRenderer;
import client.onyx.render.util.FilterMode;
import client.onyx.ui.component.Button;
import client.onyx.ui.component.RenderContext;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.Typography;
import java.awt.image.BufferedImage;
import java.io.IOException;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

public class CapeEditorScreen extends GuiScreen {
    private static final ResourceLocation resourceLocation = new ResourceLocation("onyx", "capes/source");
    private static final float FLOAT_0_6251 = 0.625F;
    private static final float FLOAT_8_01 = 8.0F;
    private static final float FLOAT_96_01 = 96.0F;
    private static final float FLOAT_1_121 = 1.12F;
    private static final float FLOAT_0_561 = 0.56F;
    private final CapeChanger capeChanger;
    private final UiRenderer uiRenderer;
    private final Button button5;
    private final Button button;
    private final Button button4;
    private final Button button2;
    private final Button button3;
    private final GuiScreen guiScreen;
    private float floatValue;
    private boolean flag;
    private double doubleValue;
    private float floatValue2;
    private float floatValue3;
    private double doubleValue2;
    private float floatValue4;
    private float floatValue5;
    private float floatValue6;

    @Override
    public void mouseClickMove(int var1, int var2, int var3, long var4) {
        if (!this.flag) {
            super.mouseClickMove(var1, var2, var3, var4);
        } else {
            this.capeChanger.offsetX.setValue(this.doubleValue2 + (var1 - this.floatValue6) / this.floatValue4 * 100.0);
            this.capeChanger.offsetY.setValue(this.doubleValue + (var2 - this.floatValue) / this.floatValue5 * 100.0);
        }
    }

    private Button[] method_2355() {
        return new Button[]{this.button5, this.button, this.button4, this.button2, this.button3};
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int var6;
        if ((var6 = Mouse.getEventDWheel()) != 0 && this.capeChanger.getBufferedImage() != null) {
            double var4;
            if (var6 > 0) {
                var4 = 1.12F;
            } else {
                var4 = 0.8928571390558262;
            }

            this.capeChanger.zoom.setValue(this.capeChanger.zoom.getValue() * var4);
            this.capeChanger.method_1112();
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public CapeEditorScreen(GuiScreen var1) {
        this.capeChanger = Onyx.moduleManager.capeChanger;
        this.uiRenderer = new UiRenderer();
        this.button5 = new Button(Button.Variant.OUTLINED, "Fit", () -> this.method_2359(ImageFit.FitMode.FIT));
        this.button = new Button(Button.Variant.OUTLINED, "Fill", () -> this.method_2359(ImageFit.FitMode.FILL));
        this.button4 = new Button(Button.Variant.OUTLINED, "Stretch", () -> this.method_2359(ImageFit.FitMode.STRETCH));
        this.button2 = new Button(Button.Variant.TONAL, "Reset", () -> {
            this.capeChanger.zoom.reset();
            this.capeChanger.offsetX.reset();
            this.capeChanger.offsetY.reset();
            this.capeChanger.method_1112();
        });
        this.button3 = new Button(Button.Variant.FILLED, "Done", this::method_2357);
        this.guiScreen = var1;
    }

    private void method_2356(BufferedImage var1) {
        this.uiRenderer.method_434(this.floatValue2, this.floatValue3, this.floatValue4, this.floatValue5, this.capeChanger.background.getValue() | 0xFF000000);
        double[] var2 = ImageFit.method_667(
            var1,
            ImageFit.method_670(this.capeChanger.fit.getValue(), var1),
            this.floatValue4,
            this.floatValue5,
            this.capeChanger.zoom.getValue() / 100.0,
            this.capeChanger.offsetX.getValue() / 100.0,
            this.capeChanger.offsetY.getValue() / 100.0
        );
        this.uiRenderer.method_443(this.floatValue2, this.floatValue3, this.floatValue4, this.floatValue5);
        this.uiRenderer
            .method_487(
                resourceLocation,
                this.floatValue2 + (float)var2[0],
                this.floatValue3 + (float)var2[1],
                (float)var2[2],
                (float)var2[3],
                0.0F,
                0.0F,
                var1.getWidth(),
                var1.getHeight(),
                var1.getWidth(),
                var1.getHeight(),
                -1,
                FilterMode.SMOOTH
            );
        this.uiRenderer.method_486();
        this.uiRenderer.method_494(this.floatValue2, this.floatValue3, this.floatValue4, this.floatValue5, 8.0F, 1.5F, Theme.getPalette().intValue31());
        this.uiRenderer
            .method_446(Typography.fontStyle2, "top", this.width / 2.0F, this.floatValue3 - 14.0F, Theme.method_180(Theme.getPalette().intValue7(), 0.7F));
        String var4 = ImageFit.method_670(this.capeChanger.fit.getValue(), var1).getDisplayName()
            + "  ·  "
            + Math.round(this.capeChanger.zoom.getValue())
            + "%";
        this.uiRenderer
            .method_446(
                Typography.fontStyle2,
                var4,
                this.width / 2.0F,
                this.floatValue3 + this.floatValue5 + 8.0F,
                Theme.method_180(Theme.getPalette().intValue7(), 0.85F)
            );
    }

    @Override
    public void keyTyped(char var1, int var2) throws IOException {
        if (var2 == 1) {
            this.method_2357();
        } else {
            super.keyTyped(var1, var2);
        }
    }

    @Override
    public void drawScreen(int var1, int var2, float var3) {
        this.method_2358();
        this.uiRenderer.method_468();
        CapeEditorScreen var7;
        if (this.mc.theWorld == null) {
            this.uiRenderer.method_492(0.0F, 0.0F, this.width, this.height);
            var7 = this;
        } else {
            this.uiRenderer.method_434(0.0F, 0.0F, this.width, this.height, Theme.method_176(0.7F));
            var7 = this;
        }

        var7.uiRenderer.method_446(Typography.fontStyle14, "Cape", this.width / 2.0F, 22.0F, Theme.getPalette().intValue15());
        this.uiRenderer
            .method_446(
                Typography.fontStyle2, "Drag to move, scroll to zoom", this.width / 2.0F, 44.0F, Theme.method_180(Theme.getPalette().intValue7(), 0.85F)
            );
        BufferedImage var10;
        if ((var10 = this.capeChanger.getBufferedImage()) != null) {
            this.method_2356(var10);
        } else {
            this.uiRenderer
                .method_446(
                    Typography.fontStyle14,
                    "No cape picture loaded",
                    this.width / 2.0F,
                    this.height / 2.0F,
                    Theme.method_180(Theme.getPalette().intValue15(), 0.6F)
                );
        }

        RenderContext var12 = new RenderContext(this.uiRenderer, 16.0F, var1, var2);
        Button[] var13;
        int var11 = (var13 = this.method_2355()).length;
        int var4 = 0;

        for (int var9 = 0; var9 < var11; var9 = var4) {
            Button var5 = var13[var4];
            var4++;
            var5.method_2206(var12);
            var5.method_2186(var12);
        }

        this.uiRenderer.method_483();
    }

    private void method_2357() {
        this.capeChanger.method_1108(false);
        this.mc.displayGuiScreen(this.guiScreen);
    }

    @Override
    public void mouseClicked(int var1, int var2, int var3) throws IOException {
        int var8 = var2;
        Button[] var4;
        int var5 = (var4 = this.method_2355()).length;
        int var6 = 0;

        for (int var7 = 0; var7 < var5; var7 = ++var6) {
            if (var4[var6].method_2183(var1, var8, var3)) {
                return;
            }
        }

        if (var3 == 0 && this.capeChanger.getBufferedImage() != null && this.method_2360(var1, var8)) {
            this.flag = true;
            this.capeChanger.method_1108(true);
            this.floatValue6 = var1;
            this.floatValue = var8;
            this.doubleValue2 = this.capeChanger.offsetX.getValue();
            this.doubleValue = this.capeChanger.offsetY.getValue();
        } else {
            super.mouseClicked(var1, var8, var3);
        }
    }

    @Override
    public void mouseReleased(int var1, int var2, int var3) {
        if (this.flag) {
            this.flag = false;
            this.capeChanger.method_1108(false);
        }

        Button[] var4;
        int var5 = (var4 = this.method_2355()).length;
        int var8 = 0;

        for (int var7 = 0; var7 < var5; var7 = var8) {
            Button var10000 = var4[var8];
            float var10001 = var1;
            var8++;
            var10000.method_2189(var10001, var2, var3);
        }

        super.mouseReleased(var1, var2, var3);
    }

    private void method_2358() {
        this.floatValue5 = this.height * 0.56F;
        this.floatValue4 = this.floatValue5 * 0.625F;
        this.floatValue2 = this.width / 2.0F - this.floatValue4 / 2.0F;
        this.floatValue3 = this.height / 2.0F - this.floatValue5 / 2.0F - 8.0F;
        float var3 = 512.0F;
        float var4 = this.width / 2.0F - var3 / 2.0F;
        float var2 = this.height - 46.0F;
        this.button5.method_2258(var4, var2, 96.0F, 30.0F);
        this.button.method_2258(var4 + 96.0F + 8.0F, var2, 96.0F, 30.0F);
        this.button4.method_2258(var4 + 208.0F, var2, 96.0F, 30.0F);
        this.button2.method_2258(var4 + 312.0F, var2, 96.0F, 30.0F);
        this.button3.method_2258(var4 + 416.0F, var2, 96.0F, 30.0F);
    }

    private void method_2359(ImageFit.FitMode var1) {
        this.capeChanger.fit.setValue(var1);
    }

    private boolean method_2360(float var1, float var2) {
        return var1 >= this.floatValue2
            && var1 <= this.floatValue2 + this.floatValue4
            && var2 >= this.floatValue3
            && var2 <= this.floatValue3 + this.floatValue5;
    }
}
