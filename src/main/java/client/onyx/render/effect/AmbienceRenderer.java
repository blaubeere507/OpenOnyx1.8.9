package client.onyx.render.effect;

import client.onyx.Onyx;
import client.onyx.module.render.Ambience;
import client.onyx.render.shader.ShaderProgram;
import client.onyx.ui.theme.color.ColorUtils;
import client.onyx.util.IMinecraft;

public final class AmbienceRenderer implements IMinecraft {
    private static final long longValue = System.nanoTime();
    private static ShaderProgram shaderProgram;
    private static boolean flag;

    private AmbienceRenderer() {
    }

    public static void method_824() {
        if (Onyx.moduleManager != null) {
            Ambience var0 = Onyx.moduleManager.ambience;
            if (Onyx.moduleManager.ambience.method_1303() && PostProcessing.method_538()) {
                if (!flag) {
                    flag = true;
                    shaderProgram = ShaderProgram.method_820("onyx_ambience");
                }

                if (shaderProgram != null && shaderProgram.isFlag()) {
                    boolean var1 = var0.lightSettings.method_61();
                    boolean var2 = var0.colorGradingSettings.method_61();
                    int var8;
                    if (var1) {
                        var8 = var0.lightSettings.color.method_118();
                    } else {
                        var8 = -1;
                    }

                    int var3 = var8;
                    float var9;
                    if (var1) {
                        var9 = var0.lightSettings.strength.method_151() / 100.0F;
                    } else {
                        var9 = 0.0F;
                    }

                    float var14 = var9;
                    float var10;
                    if (var2) {
                        var10 = var0.colorGradingSettings.saturation.method_151() / 100.0F;
                    } else {
                        var10 = 1.0F;
                    }

                    float var4 = var10;
                    float var11;
                    if (var2) {
                        var11 = var0.colorGradingSettings.contrast.method_151() / 100.0F;
                    } else {
                        var11 = 1.0F;
                    }

                    float var5 = var11;
                    float var12;
                    if (var2) {
                        var12 = var0.colorGradingSettings.vignette.method_151() / 100.0F;
                    } else {
                        var12 = 0.0F;
                    }

                    float var6 = var12;
                    float var13;
                    if (var2) {
                        var13 = var0.colorGradingSettings.filmGrain.method_151() / 100.0F;
                    } else {
                        var13 = 0.0F;
                    }

                    float var15 = var13;
                    float var7 = (float)(System.nanoTime() - longValue) / 1.0E9F;
                    PostProcessing.method_535(
                        shaderProgram,
                        var7x -> {
                            var7x.method_822(
                                "uLightColorAndStrength",
                                ColorUtils.method_201(var3) / 255.0F,
                                ColorUtils.method_210(var3) / 255.0F,
                                ColorUtils.method_214(var3) / 255.0F,
                                var14
                            );
                            var7x.method_822("uGradeParams", var4, var5, var6, var15);
                            var7x.method_821("uTime", var7);
                        }
                    );
                }
            }
        }
    }
}
