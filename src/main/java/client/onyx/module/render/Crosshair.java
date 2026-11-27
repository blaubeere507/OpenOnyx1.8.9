package client.onyx.module.render;

import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.render.ui.UiRenderer;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.ColorSetting;
import client.onyx.setting.impl.EnumSetting;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.color.ColorUtils;

public class Crosshair extends Module {
    private static final long longValue2 = System.nanoTime();
    public NumberSetting size = new NumberSetting("Size", 5.0, 2.0, 16.0, 0.5).method_147("px").describe("Radius of the ring");
    public NumberSetting thickness = new NumberSetting("Thickness", 1.5, 0.5, 4.0, 0.25).method_147("px");
    public BooleanSetting centerDot = new BooleanSetting("Center dot", true);
    public EnumSetting<Crosshair.ColorSource> color = new EnumSetting<>("Color", Crosshair.ColorSource.ACCENT)
        .describe("Rainbow shimmer, the global accent, or a fixed colour");
    public ColorSetting customColour = new ColorSetting("Custom colour", -1)
        .method_127()
        .visibleWhen(() -> this.color.method_168(Crosshair.ColorSource.CUSTOM));
    public NumberSetting follow = new NumberSetting("Follow", 40.0, 0.0, 100.0, 5.0)
        .method_147("%")
        .describe("How much the crosshair drifts with view movement");
    public BooleanSetting pulse = new BooleanSetting("Pulse", true).describe("Breathe with the idle animation");
    private float floatValue;
    private boolean flag;
    private float floatValue2;
    private float floatValue3;
    private float floatValue4;
    private long longValue;

    private int method_1229(float var1) {
        switch ((Crosshair.ColorSource)this.color.getValue()) {
            case RAINBOW:
                return ColorUtils.method_202(var1 * 60.0F % 360.0F, 0.75F, 1.0F, 255);
            case ACCENT:
                return Theme.getPalette().intValue31() | 0xFF000000;
            case CUSTOM:
                return this.customColour.getValue();
            default:
                throw new MatchException(null, null);
        }
    }

    public Crosshair() {
        super("Crosshair", "Animated dynamic crosshair", Category.RENDER);
    }

    public void method_1230(UiRenderer var1, float var2, float var3) {
        float var4 = this.method_1231();
        float var8 = (float)(System.nanoTime() - longValue2) / 1.0E9F;
        this.method_1235(var4);
        var2 = var2 / 2.0F + this.floatValue4;
        var3 = var3 / 2.0F + this.floatValue;
        float var7;
        if (this.pulse.method_146()) {
            var7 = this.method_1232(var8);
        } else {
            var7 = 1.0F;
        }

        var4 = this.size.method_151() * var7;
        float var6 = this.thickness.method_151();
        int var9 = this.method_1229(var8);
        var1.method_488(var2, var3, var4, var6, var9);
        if (this.centerDot.method_146()) {
            var1.method_489(var2, var3, Math.max(var6 * 0.9F, 0.75F), var9);
        }
    }

    private float method_1231() {
        long var1 = System.nanoTime();
        float var4;
        if (this.longValue == 0L) {
            var4 = 16.0F;
        } else {
            var4 = Math.min((float)(var1 - this.longValue) / 1000000.0F, 100.0F);
        }

        this.longValue = var1;
        return var4;
    }

    private float method_1232(float var1) {
        return 1.0F + 0.05F * (float)Math.sin(var1 * 2.2F);
    }

    public boolean method_1233() {
        return this.isToggled() && mc.thePlayer != null && mc.gameSettings.thirdPersonView == 0;
    }

    private static float method_1234(float var0) {
        if ((var0 = var0 % 360.0F) >= 180.0F) {
            var0 -= 360.0F;
        }

        if (var0 < -180.0F) {
            var0 += 360.0F;
        }

        return var0;
    }

    private void method_1235(float var1) {
        float var2 = mc.thePlayer.rotationYaw;
        float var6 = mc.thePlayer.rotationPitch;
        if (!this.flag) {
            this.flag = true;
            this.floatValue3 = var2;
            this.floatValue2 = var6;
        }

        float var4 = method_1234(var2 - this.floatValue3);
        float var5 = var6 - this.floatValue2;
        this.floatValue3 = var2;
        this.floatValue2 = var6;
        var2 = this.follow.method_151() / 100.0F * 1.1F;
        float var7 = this.size.method_151() * 2.0F;
        var4 = Math.clamp(-var4 * var2, -var7, var7);
        var2 = Math.clamp(-var5 * var2, -var7, var7);
        var1 = 1.0F - (float)Math.exp(-var1 / 70.0F);
        this.floatValue4 = this.floatValue4 + (var4 - this.floatValue4) * var1;
        this.floatValue = this.floatValue + (var2 - this.floatValue) * var1;
    }

    public static enum ColorSource {
        RAINBOW,
        ACCENT,
        CUSTOM;
    }
}
