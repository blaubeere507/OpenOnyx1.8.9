package client.onyx.render.gui;

import client.onyx.module.hud.CustomGui;
import client.onyx.render.anim.Easings;
import client.onyx.render.font.FontFiles;
import client.onyx.render.font.FontStyle;
import client.onyx.render.ui.UiRenderer;
import client.onyx.ui.theme.Palette;
import client.onyx.ui.theme.Theme;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;

public final class TitleElement extends HudElement {
    private static final FontStyle fontStyle2 = new FontStyle(FontFiles.Size.PT72, FontFiles.Weight.BOLD, 36.0F, 44.0F);
    private static final FontStyle fontStyle = new FontStyle(FontFiles.Size.PT24, FontFiles.Weight.SEMI_BOLD, 16.0F, 24.0F);
    private static final String STRING_CONST = "Title";
    private static final float FLOAT_120_01 = 120.0F;
    private static final float FLOAT_0_881 = 0.88F;
    private static final float FLOAT_6_01 = 6.0F;
    private static final String STRING_CONST2 = "Subtitle";
    private static final float FLOAT_0_61 = 0.6F;
    private String string2 = "";
    private String string22 = "";
    private float floatValue7;
    private float floatValue22;

    @Override
    public boolean method_899(boolean var1) {
        GuiIngame var9;
        if ((var9 = Minecraft.getMinecraft().ingameGUI) != null && var9.getTitlesTimer() > 0) {
            TitleElement var4;
            String var5;
            if (var9.getDisplayedTitle() != null) {
                var5 = var9.getDisplayedTitle();
                var4 = this;
            } else {
                var5 = "";
                var4 = this;
            }

            var4.string2 = var5;
            TitleElement var7;
            String var8;
            if (var9.getDisplayedSubTitle() != null) {
                var8 = var9.getDisplayedSubTitle();
                var7 = this;
            } else {
                var8 = "";
                var7 = this;
            }

            var7.string22 = var8;
            if (!this.string2.isEmpty() || !this.string22.isEmpty()) {
                return this.method_918(var9);
            }
        }

        if (var1) {
            this.string2 = "Title";
            this.string22 = "Subtitle";
            this.floatValue7 = 1.0F;
            this.floatValue22 = 1.0F;
            return true;
        } else {
            this.floatValue7 = 0.0F;
            return false;
        }
    }

    public TitleElement(CustomGui var1) {
        super("mcgui:title", var1.titleSettings.positionX, var1.titleSettings.positionY, var1.titleSettings.scale);
    }

    private boolean method_918(GuiIngame var1) {
        float var2 = var1.getTitlesTimer();
        int var5 = Math.max(1, var1.getTitleFadeIn());
        int var4 = Math.max(1, var1.getTitleFadeOut());
        float var3 = 1.0F;
        this.floatValue22 = 1.0F;
        TitleElement var6;
        if (var2 > var1.getTitleFadeOut() + var1.getTitleDisplayTime()) {
            var3 = Math.clamp((var5 + var1.getTitleDisplayTime() + var1.getTitleFadeOut() - var2) / var5, 0.0F, 1.0F);
            this.floatValue22 = Math.clamp(var3 / 0.6F, 0.0F, 1.0F);
            var6 = this;
        } else {
            if (var2 <= var1.getTitleFadeOut()) {
                var3 = Math.clamp(var2 / var4, 0.0F, 1.0F);
            }

            var6 = this;
        }

        var6.floatValue7 = (float)Easings.easing2.method_608(var3);
        return this.floatValue7 > 0.03F;
    }

    @Override
    public float method_898(UiRenderer var1) {
        return Math.max(120.0F, Math.max(IconFont.method_889(var1, fontStyle2, this.string2), IconFont.method_889(var1, fontStyle, this.string22)));
    }

    @Override
    public void method_902(UiRenderer var1, float var2, float var3, float var4) {
        Palette var9 = Theme.getPalette();
        float var5 = (float)Easings.easing7.method_608(this.floatValue22);
        var5 = 0.88F + 0.120000005F * var5;
        var1.method_440(this.floatValue7);
        var1.method_441();
        var1.method_491(var5, var2 / 2.0F, var3 / 2.0F);
        float var7 = 0.0F;
        if (!this.string2.isEmpty()) {
            IconFont.method_897(
                var1, fontStyle2, this.string2, var2 / 2.0F, var1.method_433(fontStyle2, var7 + fontStyle2.floatValue() / 2.0F), var9.intValue15()
            );
            var7 += fontStyle2.floatValue() + 6.0F;
        }

        if (!this.string22.isEmpty()) {
            IconFont.method_897(var1, fontStyle, this.string22, var2 / 2.0F, var1.method_433(fontStyle, var7 + fontStyle.floatValue() / 2.0F), var9.intValue7());
        }

        var1.method_435();
        var1.method_430();
    }

    @Override
    public float method_900(UiRenderer var1) {
        float var2;
        if (this.string2.isEmpty()) {
            var2 = 0.0F;
        } else {
            var2 = fontStyle2.floatValue();
        }

        float var6 = var2;
        if (!this.string22.isEmpty()) {
            float var4;
            float var5;
            if (!(var2 > 0.0F)) {
                var5 = 0.0F;
                var4 = var2;
            } else {
                var5 = 6.0F;
                var4 = var2;
            }

            var6 = var4 + (var5 + fontStyle.floatValue());
        }

        return var6;
    }
}
