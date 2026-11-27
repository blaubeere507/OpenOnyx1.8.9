package client.onyx.render.ui;

import client.onyx.Onyx;
import client.onyx.module.hud.Keybinds;
import client.onyx.module.hud.PotionHUD;
import client.onyx.module.hud.arraylist.ModuleList;
import client.onyx.module.hud.island.IslandStats;
import client.onyx.module.hud.island.Watermark;
import client.onyx.module.hud.notification.Notifications;
import client.onyx.module.hud.targethud.TargetHUD;
import client.onyx.module.render.BedESP;
import client.onyx.module.render.Crosshair;
import client.onyx.module.render.TNTTimer;
import client.onyx.render.effect.HandTransformRenderer;
import client.onyx.render.gui.HotbarAnimator;
import net.minecraft.client.gui.ScaledResolution;

public final class StatusBarsRenderer {
    private static final UiRenderer uiRenderer = new UiRenderer().method_460();

    private StatusBarsRenderer() {
    }

    public static void method_858(ScaledResolution var0, float var1) {
        if (Onyx.moduleManager != null) {
            TargetHUD var22 = Onyx.moduleManager.targetHUD;
            Keybinds var24 = Onyx.moduleManager.keybinds;
            PotionHUD var4 = Onyx.moduleManager.potionHUD;
            Notifications var5 = Onyx.moduleManager.notifications;
            ModuleList var6 = Onyx.moduleManager.moduleList;
            Watermark var7;
            Watermark var10000 = var7 = Onyx.moduleManager.watermark;
            IslandStats var8 = Onyx.moduleManager.watermark.getIslandStats();
            BedESP var9 = Onyx.moduleManager.bedESP;
            Crosshair var10 = Onyx.moduleManager.crosshair;
            TNTTimer var11 = Onyx.moduleManager.tntTimer;
            boolean var12 = var22.method_1439();
            boolean var13 = var24.method_1399();
            boolean var14 = var4.method_1422();
            boolean var15 = var5.method_1354();
            boolean var16 = var6.method_1496();
            boolean var17 = var10000.method_1387();
            boolean var18 = var8.method_1309();
            boolean var19 = var9.method_1170();
            boolean var20 = var10.method_1233();
            boolean var21 = var11.isToggled();
            boolean var25 = HandTransformRenderer.method_417();
            boolean var23 = HotbarAnimator.method_979();
            if (var12 || var13 || var16 || var17 || var18 || var20 || var21 || var25 || var23 || var14 || var15 || var19) {
                float var26 = var0.getScaledWidth();
                float var3 = var0.getScaledHeight();
                uiRenderer.method_468();
                if (var23) {
                    HotbarAnimator.method_980(uiRenderer, var26, var3);
                }

                if (var21) {
                    var11.method_1070(uiRenderer, var26, var3, var1);
                }

                if (var20) {
                    var10.method_1230(uiRenderer, var26, var3);
                }

                if (var12) {
                    var22.method_1457(uiRenderer, var26, var3, var1);
                }

                if (var13) {
                    var24.method_1398(uiRenderer, var26, var3);
                }

                if (var14) {
                    var4.method_1409(uiRenderer, var26, var3);
                }

                if (var19) {
                    var9.method_1171(uiRenderer, var26, var3);
                }

                if (var16) {
                    var6.method_1484(uiRenderer, var26, var3);
                }

                if (var17) {
                    var7.method_1372(uiRenderer, var26, var3);
                }

                if (var18) {
                    var8.method_1324(uiRenderer, var26, var3);
                }

                if (var15) {
                    var5.method_1336(uiRenderer, var26, var3);
                }

                HandTransformRenderer.method_421(uiRenderer, var26, var3);
                uiRenderer.method_483();
            }
        }
    }
}
