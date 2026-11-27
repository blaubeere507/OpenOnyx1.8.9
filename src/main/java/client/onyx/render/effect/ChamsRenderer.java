package client.onyx.render.effect;

import client.onyx.Onyx;
import client.onyx.module.render.Chams;
import client.onyx.render.shader.ShaderProgram;
import client.onyx.ui.theme.color.ColorUtils;
import client.onyx.util.IMinecraft;
import client.onyx.util.TargetGroup;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;

public final class ChamsRenderer implements IMinecraft {
    private static boolean flag = true;
    private static float floatValue;
    private static boolean flag2;
    private static boolean flag3;
    private static ShaderProgram shaderProgram;
    private static float floatValue2;
    private static float floatValue3;
    private static boolean flag4;
    private static float floatValue4;
    private static float floatValue5;

    public static void method_544() {
        if (flag2) {
            flag2 = false;
            ShaderProgram.method_819();
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            GlStateManager.disableBlend();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.resetColor();
        }
    }

    public static boolean method_545() {
        if (flag2 && flag4) {
            GlStateManager.disableDepth();
            GlStateManager.depthMask(false);
            shaderProgram.method_822("uTint", floatValue3, floatValue5, floatValue, floatValue2);
            return true;
        } else {
            return false;
        }
    }

    public static boolean method_546(Entity var0) {
        if (var0 instanceof EntityLivingBase var3) {
            if (!method_549()) {
                return false;
            } else {
                Chams var2 = Onyx.moduleManager.chams;
                TargetGroup var4;
                return (var4 = Onyx.moduleManager.chams.method_1183(var3)) != null && var2.method_1182(var4);
            }
        } else {
            return false;
        }
    }

    public static boolean method_547(EntityLivingBase var0) {
        if (!flag || Onyx.moduleManager == null) {
            return false;
        } else if (!OpenGlHelper.shadersSupported) {
            return false;
        } else {
            Chams var1 = Onyx.moduleManager.chams;
            TargetGroup var10;
            if ((var10 = Onyx.moduleManager.chams.method_1183(var0)) == null) {
                return false;
            } else {
                if (!flag3) {
                    flag3 = true;
                    shaderProgram = ShaderProgram.method_820("onyx_chams");
                }

                if (shaderProgram != null && shaderProgram.isFlag()) {
                    Chams.ChamsSettings var4 = var1.method_1181(var10);
                    int var3;
                    floatValue3 = ColorUtils.method_201(var3 = var1.method_1179(var10)) / 255.0F;
                    floatValue5 = ColorUtils.method_210(var3) / 255.0F;
                    floatValue = ColorUtils.method_214(var3) / 255.0F;
                    floatValue4 = ColorUtils.method_213(var3) / 255.0F;
                    flag4 = var1.method_1182(var10);
                    floatValue2 = floatValue4 * var1.method_1180(var10);
                    GlStateManager.enableBlend();
                    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 771);
                    GlStateManager.depthMask(true);
                    shaderProgram.method_817();
                    shaderProgram.method_815("Sampler0", 0);
                    shaderProgram.method_815("Sampler1", 1);
                    shaderProgram.method_822("uTint", floatValue3, floatValue5, floatValue, floatValue4);
                    shaderProgram.method_822("uStyle", var4.strength.method_151() / 100.0F, 0.0F, 0.0F, var1.method_1178(var10));
                    ShaderProgram var10000 = shaderProgram;
                    ShaderProgram var7;
                    String var8;
                    float var9;
                    if (!GL11.glIsEnabled(2912)) {
                        String var6 = "uFogEnabled";
                        ShaderProgram var5 = var10000;
                        var9 = 0.0F;
                        var8 = var6;
                        var7 = var5;
                    } else {
                        var9 = 1.0F;
                        var8 = "uFogEnabled";
                        var7 = var10000;
                    }

                    var7.method_821(var8, var9);
                    flag2 = true;
                    return true;
                } else {
                    flag = false;
                    return false;
                }
            }
        }
    }

    public static void method_548() {
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        shaderProgram.method_822("uTint", floatValue3, floatValue5, floatValue, floatValue4);
    }

    private ChamsRenderer() {
    }

    public static boolean method_549() {
        if (flag && Onyx.moduleManager != null) {
            if (!OpenGlHelper.shadersSupported) {
                return false;
            } else {
                Chams var0 = Onyx.moduleManager.chams;
                if (!Onyx.moduleManager.chams.isToggled()) {
                    return false;
                } else {
                    TargetGroup[] var1;
                    int var2 = (var1 = TargetGroup.values()).length;
                    int var3 = 0;

                    for (int var5 = 0; var5 < var2; var5 = ++var3) {
                        TargetGroup var4 = var1[var3];
                        if (var0.method_1181(var4).method_61() && var0.method_1182(var4)) {
                            return true;
                        }
                    }

                    return false;
                }
            }
        } else {
            return false;
        }
    }
}
