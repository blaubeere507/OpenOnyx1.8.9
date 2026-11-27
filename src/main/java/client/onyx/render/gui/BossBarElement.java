package client.onyx.render.gui;

import client.onyx.module.hud.CustomGui;
import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easings;
import client.onyx.render.font.FontFiles;
import client.onyx.render.font.FontStyle;
import client.onyx.render.ui.UiRenderer;
import client.onyx.ui.theme.Palette;
import client.onyx.ui.theme.Theme;
import net.minecraft.entity.boss.BossStatus;

public final class BossBarElement extends HudElement {
    private static final FontStyle fontStyle = new FontStyle(FontFiles.Size.PT24, FontFiles.Weight.BOLD, 12.0F, 15.0F);
    private static final float FLOAT_0_51 = 0.5F;
    private static final float FLOAT_182_01 = 182.0F;
    private static final float FLOAT_3_01 = 3.0F;
    private static final float FLOAT_2_01 = 2.0F;
    private static final float FLOAT_0_71 = 0.7F;
    private static final float FLOAT_10_012 = 10.0F;
    private static final float FLOAT_6_01 = 6.0F;
    private final Animation animation = new Animation(0.0F);
    private final Animation animation2 = new Animation(0.0F);
    private String string2 = "";
    private final CustomGui customGui;
    private float floatValue7;

    @Override
    public float method_898(UiRenderer var1) {
        return 182.0F;
    }

    @Override
    public boolean method_899(boolean var1) {
        if (BossStatus.bossName != null && BossStatus.statusBarTime > 0) {
            BossStatus.statusBarTime--;
            this.string2 = BossStatus.bossName;
            this.floatValue7 = Math.clamp(BossStatus.healthScale, 0.0F, 1.0F);
            return true;
        } else if (var1) {
            this.string2 = "Boss";
            this.floatValue7 = 0.7F;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public float method_900(UiRenderer var1) {
        return fontStyle.floatValue() + 3.0F + 6.0F;
    }

    public BossBarElement(CustomGui var1) {
        super("mcgui:bossbar", var1.bossbarSettings.positionX, var1.bossbarSettings.positionY, var1.bossbarSettings.scale);
        this.customGui = var1;
    }

    @Override
    public void method_901(float var1) {
        this.animation.method_603(this.floatValue7, 200.0F, Easings.easing2);
        this.animation.method_605(var1);
        if (!(this.floatValue7 >= this.animation2.getFloatValue3())) {
            this.animation2.method_603(this.floatValue7, 500.0F, Easings.easing);
            this.animation2.method_605(var1);
        } else {
            this.animation2.method_607(Math.max(this.floatValue7, this.animation.method_606()));
        }
    }

    @Override
    public void method_902(UiRenderer var1, float var2, float var3, float var4) {
        Palette var10 = Theme.getPalette();
        IconFont.method_897(var1, fontStyle, this.string2, var2 / 2.0F, var1.method_433(fontStyle, fontStyle.floatValue() / 2.0F), var10.intValue15());
        var3 -= 6.0F;
        var1.method_479(0.0F, var3, var2, 6.0F, Float.MAX_VALUE, var10.intValue11());
        if (this.customGui.bossbarSettings.segments.method_146()) {
            this.method_903(var1, var10, var2, var3);
        } else {
            float var5;
            if ((var5 = var2 * this.animation2.method_606()) > 0.5F) {
                var1.method_479(0.0F, var3, var5, 6.0F, Float.MAX_VALUE, Theme.method_180(var10.intValue13(), 0.45F));
            }

            float var7;
            if ((var7 = var2 * this.animation.method_606()) > 0.5F) {
                var1.method_479(0.0F, var3, var7, 6.0F, Float.MAX_VALUE, var10.intValue13());
            }
        }
    }

    private void method_903(UiRenderer var1, Palette var2, float var3, float var4) {
        float var5;
        float var6 = (var5 = (var3 + 2.0F) / 10.0F) - 2.0F;
        float var11 = var3 * this.animation.method_606();
        int var7 = 0;

        for (int var9 = 0; var9 < 10.0F; var9 = var7) {
            float var8;
            if ((var8 = var7 * var5) >= var11) {
                return;
            }

            var1.method_443(var8, var4, Math.min(var6, var11 - var8), 6.0F);
            var7++;
            var1.method_479(var8, var4, var6, 6.0F, Float.MAX_VALUE, var2.intValue13());
            var1.method_486();
        }
    }
}
