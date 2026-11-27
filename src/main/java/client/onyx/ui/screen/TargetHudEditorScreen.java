package client.onyx.ui.screen;

import client.onyx.Onyx;
import client.onyx.input.KeyTyped;
import client.onyx.input.MouseClick;
import client.onyx.module.hud.targethud.TargetHUD;
import client.onyx.module.hud.targethud.TargetHudElementSettings;
import client.onyx.render.font.FontStyle;
import client.onyx.render.ui.HealthBarAnimator;
import client.onyx.render.ui.UiRenderer;
import client.onyx.ui.clickgui.setting.SettingView;
import client.onyx.ui.component.Button;
import client.onyx.ui.component.Popup;
import client.onyx.ui.component.RenderContext;
import client.onyx.ui.theme.ColorRoles;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.Typography;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

public class TargetHudEditorScreen extends GuiScreen {
    private static final float FLOAT_16_01 = 16.0F;
    private static final float FLOAT_2_01 = 2.0F;
    private static final float FLOAT_12_01 = 12.0F;
    private static final float FLOAT_100_01 = 100.0F;
    private static final float FLOAT_46_01 = 46.0F;
    private static final float FLOAT_210_01 = 210.0F;
    private static final float FLOAT_9_01 = 9.0F;
    private static final float FLOAT_20_01 = 20.0F;
    private static final float FLOAT_120_01 = 120.0F;
    private static final float FLOAT_10_01 = 10.0F;
    private static final float FLOAT_2_41 = 2.4F;
    private static final float FLOAT_12_02 = 12.0F;
    private static final float FLOAT_5_01 = 5.0F;
    private final TargetHUD targetHUD;
    private final UiRenderer uiRenderer;
    private final Popup popup;
    private final Button button2;
    private final Button button;
    private HealthBarAnimator.Ghost ghost;
    private final List<float[]> list;
    private final GuiScreen guiScreen;
    private float floatValue;
    private float floatValue2;
    private TargetHudElementSettings targetHudElementSettings;
    private float floatValue3;
    private float floatValue4;
    private long longValue;
    private float floatValue5;
    private float floatValue6;
    private float floatValue7;
    private TargetHudElementSettings targetHudElementSettings2;
    private float floatValue8;
    private float floatValue9;
    private TargetHUD.TargetState targetState;
    private float floatValue10;
    private float floatValue11;
    private float floatValue12;
    private float floatValue13;

    @Override
    public void keyTyped(char var1, int var2) throws IOException {
        KeyTyped var4 = new KeyTyped(var2, 0, 0, var1);
        Iterator var6 = this.popup.getList().iterator();

        while (var6.hasNext()) {
            if (((SettingView)var6.next()).method_2212(var4)) {
                return;
            }
        }

        if (var1 >= ' ') {
            MouseClick var7 = new MouseClick(var1, 0);
            Iterator var5 = this.popup.getList().iterator();

            while (var5.hasNext()) {
                if (((SettingView)var5.next()).method_2193(var7)) {
                    return;
                }
            }
        }

        if (var2 == 1) {
            this.method_2313();
        } else {
            super.keyTyped(var1, var2);
        }
    }

    @Override
    public void initGui() {
        this.longValue = 0L;
        if (this.targetHudElementSettings == null) {
            this.targetHudElementSettings = this.targetHUD.method_1437().get(0);
        }

        this.method_2321();
    }

    @Override
    public void onGuiClosed() {
        this.targetHUD.method_1465();
    }

    private void method_2309() {
        float var4 = 100.0F;
        float var2 = this.width - 210.0F - 12.0F;
        float var3 = this.height - 46.0F + 10.0F;
        this.button2.method_2258(var2, var3, var4, 30.0F);
        this.button.method_2258(var2 + var4 + 10.0F, var3, var4, 30.0F);
    }

    @Override
    public void handleMouseInput() throws IOException {
        int var1;
        if ((var1 = Mouse.getEventDWheel()) != 0) {
            ScaledResolution var4 = new ScaledResolution(this.mc);
            float var3 = (float)(Mouse.getEventX() * var4.getScaledWidth()) / this.mc.displayWidth;
            float var5 = var4.getScaledHeight() - (float)(Mouse.getEventY() * var4.getScaledHeight()) / this.mc.displayHeight - 1.0F;
            if (var3 >= this.width - 210.0F - 12.0F && var5 >= this.floatValue7 && var5 < this.floatValue7 + this.floatValue13) {
                this.floatValue3 = Math.clamp(this.floatValue3 - var1 / 120.0F * 40.0F, 0.0F, Math.max(0.0F, this.floatValue9 - this.floatValue13));
                return;
            }
        }

        super.handleMouseInput();
    }

    private List<float[]> method_2310(TargetHudElementSettings var1) {
        ArrayList var4 = new ArrayList();
        Iterator var3;
        Iterator var5 = var3 = this.targetState.list().iterator();

        while (var5.hasNext()) {
            TargetHUD.ElementLayout var6;
            if ((var6 = (TargetHUD.ElementLayout)var3.next()).targetHudElementSettings() == var1) {
                var5 = var3;
            } else {
                var4.add(new float[]{var6.floatValue2(), var6.floatValue4(), var6.floatValue(), var6.floatValue3()});
                var5 = var3;
            }
        }

        return var4;
    }

    private float[] method_2311(TargetHUD.ElementLayout var1) {
        return new float[]{
            this.floatValue5 + var1.floatValue2() * 2.4F,
            this.floatValue6 + var1.floatValue4() * 2.4F,
            Math.max(var1.floatValue(), 1.0F) * 2.4F,
            Math.max(var1.floatValue3(), 1.0F) * 2.4F
        };
    }

    @Override
    public void mouseReleased(int var1, int var2, int var3) {
        int var6 = var3;
        this.targetHudElementSettings2 = null;
        this.ghost = HealthBarAnimator.ghost;

        Iterator var4;
        for (Iterator var5 = var4 = this.popup.getList().iterator(); var5.hasNext(); var5 = var4) {
            ((SettingView)var4.next()).method_2189(var1, var2, var6);
        }

        this.button2.method_2189(var1, var2, var6);
        this.button.method_2189(var1, var2, var6);
        super.mouseReleased(var1, var2, var6);
    }

    private TargetHUD.ElementLayout method_2312(TargetHudElementSettings var1) {
        Iterator var4 = this.targetState.list().iterator();

        while (var4.hasNext()) {
            TargetHUD.ElementLayout var3;
            if ((var3 = (TargetHUD.ElementLayout)var4.next()).targetHudElementSettings() == var1) {
                return var3;
            }
        }

        return null;
    }

    private void method_2313() {
        this.mc.displayGuiScreen(this.guiScreen);
    }

    private float method_2314() {
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

    private static boolean method_2315(float[] var0, float var1, float var2) {
        return var1 >= var0[0] && var1 <= var0[0] + var0[2] && var2 >= var0[1] && var2 <= var0[1] + var0[3];
    }

    private void method_2316(float var1, float var2) {
        if (this.targetState != null) {
            TargetHUD.ElementLayout var4;
            if ((var4 = this.method_2312(this.targetHudElementSettings2)) != null) {
                var1 = (var1 - this.floatValue11 - this.floatValue5) / 2.4F;
                var2 = (var2 - this.floatValue12 - this.floatValue6) / 2.4F;
                this.ghost = HealthBarAnimator.method_861(
                    var1,
                    var2,
                    var4.floatValue(),
                    var4.floatValue3(),
                    this.targetState.floatValue3(),
                    this.targetState.floatValue2(),
                    0.0F,
                    this.method_2310(this.targetHudElementSettings2)
                );
                this.targetHudElementSettings2.offsetX.setValue((double)(this.ghost.floatValue4() - this.floatValue8));
                this.targetHudElementSettings2.offsetY.setValue((double)(this.ghost.floatValue2() - this.floatValue10));
            }
        }
    }

    private void method_2317(int var1, int var2) {
        float var4 = this.width - 210.0F - 12.0F;
        float var8 = 12.0F;
        float var5 = this.height - 46.0F;
        this.uiRenderer.method_479(var4, var8, 210.0F, var5 - var8, 16.0F, ColorRoles.method_182(2, Theme.getPalette()));
        float var9 = var8 + 12.0F;
        this.uiRenderer.method_428(Typography.fontStyle18, "Elements", var4 + 12.0F, var9, Theme.getPalette().intValue15());
        float var10 = var9 + (Typography.fontStyle18.floatValue() + 4.0F);
        float var11 = this.method_2318(var4 + 12.0F, var10, 186.0F, var1, var2) + 8.0F;
        this.uiRenderer.method_434(var4 + 12.0F, var11, 186.0F, 1.0F, Theme.getPalette().intValue12());
        float var12;
        this.floatValue7 = var12 = var11 + 10.0F;
        this.floatValue13 = Math.max(0.0F, var5 - 12.0F - this.floatValue7);
        RenderContext var15 = new RenderContext(this.uiRenderer, this.floatValue4, var1, var2);
        float var16 = var4 + 12.0F;
        var4 = 186.0F;

        Iterator var13;
        for (Iterator var6 = var13 = this.popup.getList().iterator(); var6.hasNext(); var6 = var13) {
            SettingView var18 = (SettingView)var13.next();
            var18.method_2206(var15);
        }

        this.floatValue9 = this.popup.method_2553(var16, this.floatValue7 - this.floatValue3, var4);
        this.floatValue3 = Math.clamp(this.floatValue3, 0.0F, Math.max(0.0F, this.floatValue9 - this.floatValue13));
        this.floatValue9 = this.popup.method_2553(var16, this.floatValue7 - this.floatValue3, var4);
        this.uiRenderer.method_443(var16, this.floatValue7, var4, this.floatValue13);
        this.popup.method_2555(this.uiRenderer, var16, var4, Theme.getPalette().intValue12(), 1.0F);

        Iterator var14;
        for (Iterator var7 = var14 = this.popup.getList().iterator(); var7.hasNext(); var7 = var14) {
            SettingView var19 = (SettingView)var14.next();
            var19.method_2186(var15);
        }

        this.uiRenderer.method_486();
        this.method_2309();
        this.button2.method_2206(var15);
        this.button.method_2206(var15);
        this.button2.method_2186(var15);
        this.button.method_2186(var15);
    }

    private float method_2318(float var1, float var2, float var3, int var4, int var5) {
        var var6 = this;
        this.list.clear();
        float var25 = var1;
        var2 = var2;

        Iterator var7;
        for (Iterator var24 = var7 = this.targetHUD.method_1437().iterator(); var24.hasNext(); var24 = var7) {
            TargetHudElementSettings var8;
            String var9 = (var8 = (TargetHudElementSettings)var7.next()).getTargetHudElement().getDisplayName();
            float var10 = var6.uiRenderer.method_442(Typography.fontStyle2, var9) + 18.0F;
            if (var25 > var1 && var25 + var10 > var1 + var3) {
                var25 = var1;
                var2 += 25.0F;
            }

            boolean var14;
            if (var8 == var6.targetHudElementSettings) {
                var14 = true;
            } else {
                var14 = false;
            }

            boolean var15;
            if (var4 >= var25 && var4 < var25 + var10 && var5 >= var2 && var5 < var2 + 20.0F) {
                var15 = true;
            } else {
                var15 = false;
            }

            int var16;
            if (var14) {
                var16 = Theme.getPalette().intValue5();
            } else {
                int var27 = Theme.getPalette().intValue15();
                int var18;
                float var19;
                if (!var15) {
                    int var17 = var27;
                    var19 = 0.04F;
                    var18 = var17;
                } else {
                    var19 = 0.1F;
                    var18 = var27;
                }

                var16 = Theme.method_180(var18, var19);
            }

            int var20;
            if (var14) {
                var20 = Theme.getPalette().intValue30();
            } else {
                int var28 = Theme.getPalette().intValue7();
                int var22;
                float var23;
                if (!var8.method_1504()) {
                    int var21 = var28;
                    var23 = 0.45F;
                    var22 = var21;
                } else {
                    var23 = 1.0F;
                    var22 = var28;
                }

                var20 = Theme.method_180(var22, var23);
            }

            var6.uiRenderer.method_479(var25, var2, var10, 20.0F, Float.MAX_VALUE, var16);
            var6.uiRenderer.method_484(Typography.fontStyle2, var9, var25 + var10 / 2.0F, var2 + 10.0F, var20);
            var6.list.add(new float[]{var25, var2, var10, 20.0F});
            var25 += var10 + 5.0F;
        }

        return var2 + 20.0F;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void mouseClicked(int var1, int var2, int var3) throws IOException {
        var var4 = this;
        if (var3 == 0) {
            int var6 = 0;

            for (int var5 = 0; var5 < var4.list.size(); var5 = ++var6) {
                if (method_2315(var4.list.get(var6), var1, var2)) {
                    var4.method_2320(var4.targetHUD.method_1437().get(var6));
                    return;
                }
            }
        }

        Iterator var7 = var4.popup.getList().iterator();

        while (var7.hasNext()) {
            if (((SettingView)var7.next()).method_2183(var1, var2, var3)) {
                return;
            }
        }

        if (!var4.button2.method_2183(var1, var2, var3)) {
            if (!var4.button.method_2183(var1, var2, var3)) {
                if (var3 != 0 || !var4.method_2323(var1, var2)) {
                    var4.mouseClicked(var1, var2, var3);
                }
            }
        }
    }

    private void method_2319(TargetHUD.ElementLayout var1, int var2, int var3) {
        boolean var6;
        if (var1.targetHudElementSettings() == this.targetHudElementSettings) {
            var6 = true;
        } else {
            var6 = false;
        }

        float[] var24 = this.method_2311(var1);
        boolean var7;
        if (!var6 && (this.targetHudElementSettings2 != null || !method_2315(var24, var2, var3))) {
            var7 = false;
        } else {
            var7 = true;
        }

        if (var7) {
            int var8;
            if (var6) {
                var8 = Theme.getPalette().intValue31();
            } else {
                var8 = Theme.method_180(Theme.getPalette().intValue15(), 0.45F);
            }

            float var10001 = var24[0] - 2.0F;
            float var10002 = var24[1] - 2.0F;
            float var10003 = var24[2] + 4.0F;
            float var10004 = var24[3] + 4.0F;
            UiRenderer var15;
            float var16;
            float var17;
            float var18;
            float var19;
            float var20;
            float var21;
            int var22;
            if (!var6) {
                float var14 = 4.0F;
                float var13 = var10004;
                float var12 = var10003;
                float var11 = var10002;
                float var10 = var10001;
                UiRenderer var9 = this.uiRenderer;
                var22 = var8;
                var21 = 1.0F;
                var20 = var14;
                var19 = var13;
                var18 = var12;
                var17 = var11;
                var16 = var10;
                var15 = var9;
            } else {
                var22 = var8;
                var21 = 1.5F;
                var20 = 4.0F;
                var19 = var10004;
                var18 = var10003;
                var17 = var10002;
                var16 = var10001;
                var15 = this.uiRenderer;
            }

            var15.method_494(var16, var17, var18, var19, var20, var21, var22);
        }
    }

    private void method_2320(TargetHudElementSettings var1) {
        if (this.targetHudElementSettings != var1) {
            this.targetHudElementSettings = var1;
            this.method_2321();
        }
    }

    private void method_2321() {
        this.popup.method_2552();
        this.floatValue3 = 0.0F;
        if (this.targetHudElementSettings != null) {
            ArrayList var2;
            (var2 = new ArrayList()).add(this.targetHudElementSettings.getEnabled());
            var2.addAll(this.targetHudElementSettings.getList());
            this.popup.method_2556(var2);
        }
    }

    public TargetHudEditorScreen(GuiScreen var1) {
        this.targetHUD = Onyx.moduleManager.targetHUD;
        this.uiRenderer = new UiRenderer();
        this.popup = new Popup();
        this.button2 = new Button(Button.Variant.OUTLINED, "Reset", () -> {
            if (this.targetHudElementSettings != null) {
                this.targetHudElementSettings.method_1507();
            }
        });
        this.button = new Button(Button.Variant.FILLED, "Done", this::method_2313);
        this.ghost = HealthBarAnimator.ghost;
        this.list = new ArrayList<>();
        this.guiScreen = var1;
    }

    private void method_2322(int var1, int var2) {
        var var3 = this;
        this.floatValue = this.targetState.floatValue3() * 2.4F;
        this.floatValue2 = this.targetState.floatValue2() * 2.4F;
        this.floatValue5 = (this.width - 210.0F - 12.0F) / 2.0F - this.floatValue / 2.0F;
        this.floatValue6 = this.height / 2.0F - this.floatValue2 / 2.0F;
        this.uiRenderer.method_441();
        this.uiRenderer.method_451(this.floatValue5, this.floatValue6);
        this.uiRenderer.method_491(2.4F, 0.0F, 0.0F);
        this.targetHUD.method_1433(this.uiRenderer, Theme.getPalette(), this.targetState, false);
        if (this.targetHudElementSettings2 != null) {
            HealthBarAnimator.method_862(this.uiRenderer, this.targetState.floatValue3(), this.targetState.floatValue2(), this.ghost);
        }

        this.uiRenderer.method_435();

        Iterator var6;
        for (Iterator var5 = var6 = this.targetState.list().iterator(); var5.hasNext(); var5 = var6) {
            TargetHUD.ElementLayout var4 = (TargetHUD.ElementLayout)var6.next();
            var3.method_2319(var4, var1, var2);
        }
    }

    @Override
    public void drawScreen(int var1, int var2, float var3) {
        this.floatValue4 = this.method_2314();
        this.uiRenderer.method_468();
        TargetHudEditorScreen var5;
        if (this.mc.theWorld == null) {
            this.uiRenderer.method_492(0.0F, 0.0F, this.width, this.height);
            var5 = this;
        } else {
            this.uiRenderer.method_434(0.0F, 0.0F, this.width, this.height, Theme.method_176(0.55F));
            var5 = this;
        }

        var5.targetState = this.targetHUD.method_1461(this.uiRenderer, Theme.getPalette(), this.floatValue4);
        float var12 = this.width - 210.0F - 12.0F;
        this.uiRenderer.method_446(Typography.fontStyle14, "Target HUD", var12 / 2.0F, 22.0F, Theme.getPalette().intValue15());
        UiRenderer var8;
        FontStyle var9;
        String var10;
        float var11;
        if (this.targetState != null) {
            FontStyle var7 = Typography.fontStyle2;
            UiRenderer var6 = this.uiRenderer;
            var11 = var12;
            var10 = "Drag an element to move it, or pick one to edit";
            var9 = var7;
            var8 = var6;
        } else {
            var11 = var12;
            var10 = "Join a world to preview the card";
            var9 = Typography.fontStyle2;
            var8 = this.uiRenderer;
        }

        var8.method_446(var9, var10, var11 / 2.0F, 44.0F, Theme.method_180(Theme.getPalette().intValue7(), 0.85F));
        if (this.targetState != null) {
            this.method_2322(var1, var2);
        }

        this.method_2317(var1, var2);
        this.uiRenderer.method_483();
    }

    private boolean method_2323(float var1, float var2) {
        var var3 = this;
        if (this.targetState == null) {
            return false;
        } else {
            int var4;
            List var5;
            for (int var7 = var4 = (var5 = this.targetState.list()).size() - 1; var7 >= 0; var7 = --var4) {
                TargetHUD.ElementLayout var8 = (TargetHUD.ElementLayout)var5.get(var4);
                float[] var6;
                if (method_2315(var6 = var3.method_2311(var8), var1, var2)) {
                    var3.method_2320(var8.targetHudElementSettings());
                    var3.targetHudElementSettings2 = var8.targetHudElementSettings();
                    var3.floatValue11 = var1 - var6[0];
                    var3.floatValue12 = var2 - var6[1];
                    var3.floatValue8 = var8.floatValue2() - var8.targetHudElementSettings().offsetX.method_151();
                    var3.floatValue10 = var8.floatValue4() - var8.targetHudElementSettings().offsetY.method_151();
                    return true;
                }
            }

            return false;
        }
    }

    @Override
    public void mouseClickMove(int var1, int var2, int var3, long var4) {
        if (this.targetHudElementSettings2 != null) {
            this.method_2316(var1, var2);
        } else {
            Iterator var7 = this.popup.getList().iterator();

            while (var7.hasNext()) {
                if (((SettingView)var7.next()).method_2188(var1, var2, var3, 0.0F, 0.0F)) {
                    return;
                }
            }

            super.mouseClickMove(var1, var2, var3, var4);
        }
    }
}
