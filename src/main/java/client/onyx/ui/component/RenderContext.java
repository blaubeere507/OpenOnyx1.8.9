package client.onyx.ui.component;

import client.onyx.render.ui.UiRenderer;
import client.onyx.ui.theme.Palette;
import client.onyx.ui.theme.Theme;

public final class RenderContext {
    public final UiRenderer uiRenderer;
    public final float floatValue;
    public final float floatValue3;
    public final float floatValue2;

    public Palette method_2256() {
        return Theme.getPalette();
    }

    public RenderContext(UiRenderer var1, float var2, float var3, float var4) {
        this.uiRenderer = var1;
        this.floatValue = var2;
        this.floatValue3 = var3;
        this.floatValue2 = var4;
    }
}
