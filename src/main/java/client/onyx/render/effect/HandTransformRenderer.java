package client.onyx.render.effect;

import client.onyx.Onyx;
import client.onyx.module.render.Hand;
import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easing;
import client.onyx.render.anim.Easings;
import client.onyx.render.ui.UiRenderer;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.ui.theme.Palette;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.Typography;
import client.onyx.util.IMinecraft;
import java.nio.FloatBuffer;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

public final class HandTransformRenderer implements IMinecraft {
    private static final FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
    private static final Matrix4f matrix4f = new Matrix4f();
    private static final Matrix4f matrix4f2 = new Matrix4f();
    private static final Animation animation3 = new Animation(0.0F);
    private static final Animation animation2 = new Animation(0.0F);
    private static final Animation animation = new Animation(0.0F);
    private static float floatValue7 = 1.0F;
    private static final float FLOAT_0_071 = 0.07F;
    private static final float FLOAT_0_031 = 0.03F;
    private static boolean flag;
    private static float floatValue;
    private static float floatValue2;
    private static final float FLOAT_70_01 = 70.0F;
    private static float floatValue3;
    private static boolean flag2;
    private static float floatValue4;
    private static float floatValue5;
    private static final float FLOAT_0_081 = 0.08F;
    private static final float FLOAT_1_051 = 1.05F;
    private static float floatValue6;
    private static long longValue;
    private static boolean flag3;
    private static final float FLOAT_0_051 = 0.05F;
    private static boolean flag4;
    private static final float FLOAT_55_01 = 55.0F;
    private static final float FLOAT_70_02 = 70.0F;
    private static final float FLOAT_22_01 = 22.0F;
    private static boolean flag5;
    private static float floatValue8;
    private static float floatValue9;
    private static final float FLOAT_14_01 = 14.0F;
    private static final float FLOAT_72_01 = 72.0F;
    private static boolean flag6;
    private static final float FLOAT_30_01 = 30.0F;
    private static float floatValue10;
    private static float floatValue11;

    private static float method_408() {
        long var0 = System.nanoTime();
        long var2 = longValue;
        longValue = var0;
        return var2 == 0L ? 0.0F : Math.min((float)(var0 - var2) / 1000000.0F, 100.0F);
    }

    private HandTransformRenderer() {
    }

    private static HandTransformRenderer.State method_409() {
        if (Onyx.moduleManager != null && mc.thePlayer != null) {
            Hand var0 = Onyx.moduleManager.hand;
            if (!Onyx.moduleManager.hand.isToggled()) {
                return null;
            } else {
                Hand.TransformSettings var1 = var0.mainHand;
                return new HandTransformRenderer.State(var1.positionX, var1.positionY, var1.positionZ, var1.scaleX, var1.scaleY, var1.scaleZ);
            }
        } else {
            return null;
        }
    }

    public static boolean method_410(double var0) {
        if (!flag3 && !flag2) {
            return false;
        } else {
            HandTransformRenderer.State var2;
            if ((var2 = method_409()) == null) {
                return false;
            } else {
                if (GuiScreen.isShiftKeyDown()) {
                    if (var2.method_788()) {
                        var2.numberSetting6.setValue(var2.numberSetting6.getValue() + var0 * 0.03F);
                    }
                } else if (var2.method_789()) {
                    double var3 = Math.pow(1.05F, var0);
                    method_418(var2.numberSetting4(), var3, var0);
                    method_418(var2.numberSetting5(), var3, var0);
                    method_418(var2.numberSetting3(), var3, var0);
                }

                return true;
            }
        }
    }

    private static void method_411(UiRenderer var0, Palette var1, float var2, float var3) {
        float var4;
        if (!((var4 = animation.method_606()) <= 0.01F)) {
            HandTransformRenderer.State var5;
            if ((var5 = method_409()) != null) {
                float var9 = method_420(var3);
                var2 = var2 / 2.0F + floatValue10 / var9;
                var3 = var3 / 2.0F - floatValue / var9 - 30.0F;
                float var10 = animation2.method_606();
                float var7 = 14.0F + 4.0F * var10;
                int var8 = var1.intValue31();
                var0.method_440(var4);
                var0.method_489(var2, var3, var7, Theme.method_180(var8, 0.05F + 0.13F * var10));
                var0.method_488(var2, var3, var7, 1.5F, Theme.method_180(var8, 0.4F + 0.5F * var10));
                var0.method_489(var2, var3, 1.5F + var10, Theme.method_180(var8, 0.6F + 0.4F * var10));
                if (var10 > 0.01F) {
                    StringBuilder var14 = new StringBuilder();
                    if (var5.method_788()) {
                        var14.append(
                            String.format(
                                Locale.ROOT, "%.2f  %.2f  %.2f", var5.numberSetting.getValue(), var5.numberSetting2.getValue(), var5.numberSetting6.getValue()
                            )
                        );
                    }

                    if (var5.method_789()) {
                        if (var14.length() > 0) {
                            var14.append("  ");
                        }

                        var14.append(String.format(Locale.ROOT, "x%.2f", var5.numberSetting5.getValue()));
                    }

                    String var15 = var14.toString();
                    float var16 = var0.method_442(Typography.fontStyle11, var15) + 14.0F;
                    var3 = var3 + var7 + 7.0F;
                    var0.method_440(var10);
                    var0.method_479(var2 - var16 / 2.0F, var3, var16, 17.0F, 8.5F, var1.intValue19());
                    var0.method_484(Typography.fontStyle11, var15, var2, var3 + 8.5F, var1.intValue15());
                    var0.method_430();
                }

                var0.method_430();
            }
        }
    }

    private static void method_412(float var0, boolean var1) {
        var0 = (float)Math.exp(-var0 / 70.0F);
        boolean var3;
        if (var1 && flag6 && flag && method_409() != null) {
            var3 = true;
        } else {
            var3 = false;
        }

        Animation var5;
        float var6;
        if (!var3) {
            Animation var4 = animation;
            var6 = 0.0F;
            var5 = var4;
        } else {
            var6 = 1.0F;
            var5 = animation;
        }

        var5.method_603(var6, 200.0F, Easings.easing6);
        animation.method_605(var0);
        Animation var8;
        float var9;
        if (!flag5) {
            Animation var7 = animation2;
            var9 = 0.0F;
            var8 = var7;
        } else {
            var9 = 1.0F;
            var8 = animation2;
        }

        var8.method_603(var9, 150.0F, Easings.easing6);
        animation2.method_605(var0);
        Animation var11;
        float var12;
        if (!flag2) {
            Animation var10 = animation3;
            var12 = 0.0F;
            var11 = var10;
        } else {
            var12 = 1.0F;
            var11 = animation3;
        }

        Animation var15;
        float var16;
        float var17;
        if (!flag2) {
            var17 = 200.0F;
            var16 = var12;
            var15 = var11;
        } else {
            var17 = 250.0F;
            var16 = var12;
            var15 = var11;
        }

        Animation var21;
        float var22;
        float var23;
        Easing var24;
        if (!flag2) {
            var24 = Easings.easing6;
            var23 = var17;
            var22 = var16;
            var21 = var15;
        } else {
            var24 = Easings.easing7;
            var23 = var17;
            var22 = var16;
            var21 = var15;
        }

        var21.method_603(var22, var23, var24);
        animation3.method_605(var0);
        if (!flag2) {
            floatValue3 *= var0;
            floatValue9 *= var0;
            floatValue4 *= var0;
        }
    }

    private static void method_413(float var0, float var1, float var2) {
        HandTransformRenderer.State var5;
        if ((var5 = method_409()) != null && var5.method_788()) {
            float var4 = method_420(floatValue7);
            floatValue6 = method_425(floatValue6 + (var0 - floatValue8) * var4, var5.numberSetting);
            floatValue11 = method_425(floatValue11 - (var1 - floatValue2) * var4, var5.numberSetting2);
            floatValue8 = var0;
            floatValue2 = var1;
            var1 = 1.0F - (float)Math.exp(-var2 / 55.0F);
            floatValue3 = method_426(var5.numberSetting, floatValue3, floatValue6, var1);
            floatValue9 = method_426(var5.numberSetting2, floatValue9, floatValue11, var1);
        } else {
            flag2 = false;
        }
    }

    private static boolean method_414() {
        return mc.currentScreen instanceof GuiChat
            && mc.thePlayer != null
            && mc.gameSettings.thirdPersonView == 0
            && Onyx.moduleManager != null
            && Onyx.moduleManager.hand.isToggled();
    }

    public static void method_415() {
        flag6 = true;
        float var0 = animation3.method_606();
        GlStateManager.translate(floatValue3, floatValue9 + 0.05F * var0, floatValue4 + 0.07F * var0);
    }

    public static void method_416() {
        Matrix4f.invert(method_423(matrix4f2), matrix4f);
        flag = false;
        flag6 = false;
    }

    public static boolean method_417() {
        return method_414() && Onyx.moduleManager != null && method_409() != null;
    }

    private static void method_418(NumberSetting var0, double var1, double var3) {
        double var5;
        if (Math.abs((var1 = (var5 = var0.getValue()) * var1) - var5) < var0.getDoubleValue3()) {
            var1 = var5 + Math.signum(var3) * var0.getDoubleValue3();
        }

        var0.setValue(var1);
    }

    public static float method_419() {
        return 1.0F + 0.08F * animation3.method_606();
    }

    private static float method_420(float var0) {
        float var2 = Math.max(0.1F, -floatValue5);
        return (float)(2.0 * var2 * Math.tan(Math.toRadians(35.0)) / var0);
    }

    public static void method_421(UiRenderer var0, float var1, float var2) {
        float var6 = method_408();
        boolean var4 = method_414();
        floatValue7 = Math.max(1.0F, var2);
        float var12 = Minecraft.getScaledMouseX() * var1 / mc.displayWidth;
        float var3 = var2 - Minecraft.getScaledMouseY() * var2 / mc.displayHeight - 1.0F;
        boolean var7 = Mouse.isButtonDown(0);
        boolean var9;
        if (var4 && var7) {
            HandTransformRenderer.State var8;
            if (!flag4 && !flag2 && method_422(var12, var3, var1, var2, 22.0F) && (var8 = method_409()) != null) {
                flag2 = true;
                floatValue8 = var12;
                floatValue2 = var3;
                floatValue6 = var8.numberSetting.method_151();
                floatValue11 = var8.numberSetting2.method_151();
            }

            var9 = var7;
        } else {
            flag2 = false;
            var9 = var7;
        }

        flag4 = var9;
        boolean var10;
        if (!var4 || !flag2 && !method_422(var12, var3, var1, var2, 22.0F)) {
            var10 = false;
        } else {
            var10 = true;
        }

        flag5 = var10;
        boolean var11;
        if (!var4 || !flag2 && !method_422(var12, var3, var1, var2, 72.0F)) {
            var11 = false;
        } else {
            var11 = true;
        }

        flag3 = var11;
        if (flag2) {
            method_413(var12, var3, var6);
        }

        method_412(var6, var4);
        if (var4) {
            method_411(var0, Theme.getPalette(), var1, var2);
            if (flag5) {
                var0.method_465();
            }
        }
    }

    private static boolean method_422(float var0, float var1, float var2, float var3, float var4) {
        if (flag6 && flag && method_409() != null) {
            float var6 = method_420(var3);
            var2 = var2 / 2.0F + floatValue10 / var6 - var0;
            var1 = var3 / 2.0F - floatValue / var6 - 30.0F - var1;
            return var2 * var2 + var1 * var1 < var4 * var4;
        } else {
            return false;
        }
    }

    private static Matrix4f method_423(Matrix4f var0) {
        floatBuffer.clear();
        GL11.glGetFloat(2982, floatBuffer);
        floatBuffer.rewind();
        var0.load(floatBuffer);
        return var0;
    }

    public static void method_424() {
        if (Onyx.moduleManager != null) {
            Vector4f var0 = Matrix4f.transform(method_423(matrix4f2), new Vector4f(0.0F, 0.0F, 0.0F, 1.0F), null);
            Vector4f var1 = Matrix4f.transform(matrix4f, var0, null);
            floatValue10 = var1.x;
            floatValue = var1.y;
            floatValue5 = var1.z;
            flag = true;
        }
    }

    private static float method_425(float var0, NumberSetting var1) {
        return Math.clamp(var0, (float)var1.getDoubleValue2(), (float)var1.getDoubleValue());
    }

    private static float method_426(NumberSetting var0, float var1, float var2, float var3) {
        float var5 = var0.method_151() + var1;
        float var6;
        var0.setValue((double)(var6 = var5 + (var2 - var5) * var3));
        return var6 - var0.method_151();
    }

    private record State(
        NumberSetting numberSetting,
        NumberSetting numberSetting2,
        NumberSetting numberSetting6,
        NumberSetting numberSetting4,
        NumberSetting numberSetting5,
        NumberSetting numberSetting3
    ) {
        public boolean method_788() {
            return this.numberSetting != null && this.numberSetting2 != null && this.numberSetting6 != null;
        }

        public boolean method_789() {
            return this.numberSetting4 != null && this.numberSetting5 != null && this.numberSetting3 != null;
        }
    }
}
