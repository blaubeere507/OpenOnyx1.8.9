package client.onyx.render.ui;

import client.onyx.render.effect.FrostedGlass;
import client.onyx.render.font.FontFiles;
import client.onyx.render.font.FontStyle;
import client.onyx.render.font.GlyphAtlasFont;
import client.onyx.render.shader.ShaderProgram;
import client.onyx.render.util.FilterMode;
import client.onyx.render.util.FrameTimeSmoother;
import client.onyx.ui.theme.ColorRoles;
import client.onyx.ui.theme.Spacing;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.color.ColorUtils;
import client.onyx.ui.theme.color.Hct;
import client.onyx.ui.theme.color.TonalPalette;
import client.onyx.util.IMinecraft;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayDeque;
import java.util.Deque;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public final class UiRenderer implements IMinecraft {
    private static final long longValue = System.nanoTime();
    private static final float[] floatValueArray = new float[]{
        1.0F,
        0.0F,
        -1.0F,
        0.0F,
        0.0F,
        1.0F,
        0.0F,
        -1.0F,
        0.70710677F,
        0.70710677F,
        0.70710677F,
        -0.70710677F,
        -0.70710677F,
        0.70710677F,
        -0.70710677F,
        -0.70710677F
    };
    private static final FloatBuffer floatBuffer3 = BufferUtils.createFloatBuffer(16);
    private static final FloatBuffer floatBuffer2 = BufferUtils.createFloatBuffer(16);
    private static final IntBuffer intBuffer = BufferUtils.createIntBuffer(16);
    private static final FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(3);
    private static final int INT_641 = 64;
    private static final float FLOAT_7_01 = 7.0F;
    private static ShaderProgram shaderProgram;
    private static final float FLOAT_0_721 = 0.72F;
    private static final float FLOAT_0_991 = 0.99F;
    private static final float FLOAT_40_01 = 40.0F;
    private static ShaderProgram shaderProgram2;
    private static final float FLOAT_8_01 = 8.0F;
    private static final float FLOAT_0_51 = 0.5F;
    private static final int INT_N11 = -1;
    private static final float FLOAT_8_02 = 8.0F;
    private static final float FLOAT_8_03 = 8.0F;
    private static ShaderProgram shaderProgram3;
    private static final int INT_74251 = 7425;
    private static final float FLOAT_0_707106771 = 0.70710677F;
    private static final int INT_241 = 24;
    private static final int INT_74241 = 7424;
    private static final float FLOAT_0_881 = 0.88F;
    private static final float FLOAT_6_28318551 = (float) (Math.PI * 2);
    private final FrameTimeSmoother frameTimeSmoother = new FrameTimeSmoother();
    private final FrameTimeSmoother frameTimeSmoother2 = new FrameTimeSmoother();
    private final Deque<int[]> deque = new ArrayDeque<>();
    private float floatValue2 = 1.0F;
    private float floatValue = 1.0F;
    private boolean flag = true;

    public float method_427(String var1, float var2) {
        return FontFiles.method_624().method_631(var1) * (var2 / 16.0F);
    }

    public void method_428(FontStyle var1, String var2, float var3, float var4, int var5) {
        this.method_454(var1, var2, var3, var4 + var1.method_643() - 7.0F * var1.method_645(), var5);
    }

    private void method_429(String var1, float var2, float var3, float var4, int var5) {
        this.method_441();
        GlStateManager.translate(var2, var3, 0.0F);
        GlStateManager.scale(var4, var4, 1.0F);
        this.method_457(FontFiles.method_624(), var1, var5);
        this.method_435();
    }

    public void method_430() {
        this.floatValue2 = this.frameTimeSmoother.method_383();
    }

    public void method_431(float var1, float var2, float var3, float var4, float var5, float var6, float var7, int var8) {
        if (!(var6 <= 0.0F)) {
            this.method_445(var1, var2 + var7, var3, var4, var5, var5, var5, var5, var6, var6 * 2.0F, var8, var8, var8, var8);
        }
    }

    public void method_432(FontStyle var1, String var2, float var3, float var4, int var5) {
        this.method_454(var1, var2, var3, this.method_433(var1, var4), var5);
    }

    public float method_433(FontStyle var1, float var2) {
        return var2 + var1.method_643() / 2.0F - 7.0F * var1.method_645();
    }

    public void method_434(float var1, float var2, float var3, float var4, int var5) {
        this.method_479(var1, var2, var3, var4, 0.0F, var5);
    }

    public void method_435() {
        GlStateManager.popMatrix();
        this.floatValue = this.frameTimeSmoother2.method_383();
    }

    public void method_436(
        ResourceLocation var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, int var10, int var11
    ) {
        this.method_478(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, -1);
    }

    private int method_437(int var1) {
        return this.floatValue2 >= 1.0F ? var1 : ColorUtils.method_209(var1, ColorUtils.method_213(var1) / 255.0F * this.floatValue2);
    }

    public void method_438(FontStyle var1, String var2, float var3, float var4, int var5) {
        this.method_454(var1, var2, var3 - this.method_442(var1, var2), var4, var5);
    }

    private static void method_439(float var0, float var1, float var2, float var3, int var4) {
        GL11.glColor4f((var4 >> 16 & 0xFF) / 255.0F, (var4 >> 8 & 0xFF) / 255.0F, (var4 & 0xFF) / 255.0F, (var4 >>> 24) / 255.0F);
        GL11.glTexCoord2f(var0 - var2, var1 - var3);
        GL11.glVertex2f(var0, var1);
    }

    public void method_440(float var1) {
        this.frameTimeSmoother.method_384(this.floatValue2);
        this.floatValue2 = Math.clamp(this.floatValue2 * var1, 0.0F, 1.0F);
    }

    public void method_441() {
        this.frameTimeSmoother2.method_384(this.floatValue);
        GlStateManager.pushMatrix();
    }

    public float method_442(FontStyle var1, String var2) {
        return var1.method_646().method_631(var2) * var1.method_645();
    }

    public void method_443(float var1, float var2, float var3, float var4) {
        int[] var6 = method_459(var1, var2, var1 + var3, var2 + var4);
        int[] var8;
        if ((var8 = this.deque.peek()) != null) {
            var6 = method_448(var6, var8);
        }

        this.deque.push(var6);
        method_447(var6);
    }

    public void method_444(float var1, float var2, float var3, float var4, float var5, int var6, int var7, int var8, int var9) {
        this.method_490(var1, var2, var3, var4, var5, var5, var5, var5, var6, var7, var8, var9);
    }

    private void method_445(
        float var1,
        float var2,
        float var3,
        float var4,
        float var5,
        float var6,
        float var7,
        float var8,
        float var9,
        float var10,
        int var11,
        int var12,
        int var13,
        int var14
    ) {
        if (!(var3 <= 0.0F) && !(var4 <= 0.0F)) {
            float var15 = var3 / 2.0F;
            float var16 = var4 / 2.0F;
            float var17 = var1 + var15;
            float var18 = var2 + var16;
            float var19 = var1 - var10;
            float var20 = var2 - var10;
            var1 = var1 + var3 + var10;
            var2 = var2 + var4 + var10;
            int var26 = this.method_437(var11);
            var11 = this.method_437(var12);
            var12 = this.method_437(var13);
            var13 = this.method_437(var14);
            ShaderProgram var30 = method_458();
            GlStateManager.disableTexture2D();
            UiRenderer var21;
            if (var30.isFlag()) {
                var30.method_817();
                var30.method_812("uHalfExtents", var15, var16);
                var30.method_822(
                    "uCornerRadii",
                    Spacing.method_173(var5, var3, var4),
                    Spacing.method_173(var6, var3, var4),
                    Spacing.method_173(var7, var3, var4),
                    Spacing.method_173(var8, var3, var4)
                );
                var30.method_821("uSoftness", var9);
                var30.method_821("uPixel", this.floatValue);
                var21 = this;
            } else {
                if (var9 != 0.0F) {
                    return;
                }

                var21 = this;
            }

            var21.method_464(var19, var20, var1, var2, var17, var18, var26, var11, var12, var13);
            ShaderProgram.method_819();
        }
    }

    public void method_446(FontStyle var1, String var2, float var3, float var4, int var5) {
        this.method_454(var1, var2, var3 - this.method_442(var1, var2) / 2.0F, var4, var5);
    }

    private static void method_447(int[] var0) {
        GL11.glEnable(3089);
        GL11.glScissor(var0[0], var0[1], var0[2], var0[3]);
    }

    private static int[] method_448(int[] var0, int[] var1) {
        int var2 = Math.max(var0[0], var1[0]);
        int var3 = Math.max(var0[1], var1[1]);
        int var4 = Math.min(var0[0] + var0[2], var1[0] + var1[2]);
        int var6 = Math.min(var0[1] + var0[3], var1[1] + var1[3]);
        return new int[]{var2, var3, Math.max(0, var4 - var2), Math.max(0, var6 - var3)};
    }

    private static float[] method_449(float var0, float var1) {
        floatBuffer.clear();
        GLU.gluProject(var0, var1, 0.0F, floatBuffer3, floatBuffer2, intBuffer, floatBuffer);
        return new float[]{floatBuffer.get(0), floatBuffer.get(1)};
    }

    private static ShaderProgram method_450() {
        if (shaderProgram2 == null) {
            shaderProgram2 = ShaderProgram.method_820("onyx_clouds");
        }

        return shaderProgram2;
    }

    public void method_451(float var1, float var2) {
        GlStateManager.translate(var1, var2, 0.0F);
    }

    public float method_452(FontStyle var1, float var2) {
        return var2 + 7.0F * var1.method_645();
    }

    private static int method_453(int var0) {
        int var2 = 1 + floatValueArray.length / 2;
        return ColorUtils.method_209(var0, 1.0F - (float)Math.pow(1.0F - ColorUtils.method_213(var0) / 255.0F, 1.0F / var2));
    }

    public void method_454(FontStyle var1, String var2, float var3, float var4, int var5) {
        this.method_471(var1, var2, var3, var4, this.method_437(var5));
    }

    public void method_455() {
        if (this.flag) {
            Cursors.method_806(Cursors.CursorType.TEXT);
        }
    }

    public void method_456(ItemStack var1, float var2, float var3) {
        if (var1 != null && var1.getItem() != null) {
            ShaderProgram.method_819();
            GlStateManager.enableTexture2D();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableDepth();
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableAlpha();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            RenderHelper.enableGUIStandardItemLighting();
            GlStateManager.pushMatrix();
            GlStateManager.translate(var2, var3, 0.0F);
            mc.getRenderItem().renderItemAndEffectIntoGUI(var1, 0, 0);
            GlStateManager.popMatrix();
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableFog();
            GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GlStateManager.disableTexture2D();
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
            GL11.glTexEnvi(8960, 8704, 8448);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 771);
            GlStateManager.disableAlpha();
            GlStateManager.disableDepth();
            GlStateManager.shadeModel(7425);
            GlStateManager.resetColor();
        }
    }

    private void method_457(GlyphAtlasFont var1, String var2, int var3) {
        if (!var2.isEmpty() && var3 >>> 24 != 0) {
            int var4 = 0;

            for (int var10 = 0; var10 < var2.length(); var10 = var4) {
                var1.method_635(var2.charAt(var4++));
            }

            if ((var4 = var1.getIntValue2()) != -1) {
                GlStateManager.enableTexture2D();
                GlStateManager.bindTexture(var4);
                GL11.glColor4f((var3 >> 16 & 0xFF) / 255.0F, (var3 >> 8 & 0xFF) / 255.0F, (var3 & 0xFF) / 255.0F, (var3 >>> 24) / 255.0F);
                GL11.glBegin(7);
                float var13 = 0.0F;
                var4 = 0;

                for (int var11 = 0; var11 < var2.length(); var11 = var4) {
                    GlyphAtlasFont.Glyph var12;
                    if (!(var12 = var1.method_635(var2.charAt(var4))).method_647()) {
                        float var6 = var13 + var12.floatValue3();
                        float var7 = 7.0F + var12.floatValue4();
                        float var8 = var6 + var12.floatValue2();
                        float var9 = var7 + var12.floatValue7();
                        GL11.glTexCoord2f(var12.floatValue(), var12.floatValue6());
                        GL11.glVertex2f(var6, var7);
                        GL11.glTexCoord2f(var12.floatValue(), var12.floatValue9());
                        GL11.glVertex2f(var6, var9);
                        GL11.glTexCoord2f(var12.floatValue5(), var12.floatValue9());
                        GL11.glVertex2f(var8, var9);
                        GL11.glTexCoord2f(var12.floatValue5(), var12.floatValue6());
                        GL11.glVertex2f(var8, var7);
                    }

                    var4++;
                    var13 += var12.floatValue8();
                }

                GL11.glEnd();
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.resetColor();
            }
        }
    }

    private static ShaderProgram method_458() {
        if (shaderProgram == null) {
            shaderProgram = ShaderProgram.method_820("onyx_shape");
        }

        return shaderProgram;
    }

    private static int[] method_459(float var0, float var1, float var2, float var3) {
        floatBuffer3.clear();
        floatBuffer2.clear();
        intBuffer.clear();
        GL11.glGetFloat(2982, floatBuffer3);
        GL11.glGetFloat(2983, floatBuffer2);
        GL11.glGetInteger(2978, intBuffer);
        float[] var8 = method_449(var0, var1);
        float[] var6 = method_449(var2, var3);
        int var9 = Math.round(Math.min(var8[0], var6[0]));
        int var11 = Math.round(Math.min(var8[1], var6[1]));
        int var4 = Math.round(Math.max(var8[0], var6[0]));
        int var7 = Math.round(Math.max(var8[1], var6[1]));
        return new int[]{var9, var11, Math.max(0, var4 - var9), Math.max(0, var7 - var11)};
    }

    public UiRenderer method_460() {
        this.flag = false;
        return this;
    }

    public void method_461(float var1, float var2, float var3, float var4, float var5, int var6) {
        float var7 = var3 - var1;
        float var8 = var4 - var2;
        float var10;
        if (!((var10 = (float)Math.sqrt(var7 * var7 + var8 * var8)) < 1.0E-4F)) {
            this.method_441();
            this.method_481().method_650((var1 + var3) / 2.0F, (var2 + var4) / 2.0F);
            this.method_481().method_648((float)Math.atan2(var8, var7));
            this.method_479(-var10 / 2.0F, -var5 / 2.0F, var10, var5, Float.MAX_VALUE, var6);
            this.method_435();
        }
    }

    public void method_462(float var1, float var2, float var3, float var4, float var5, int var6) {
        float var8;
        if (!((var8 = Spacing.method_173(var5, var3, var4)) <= 0.0F)) {
            this.method_445(
                var1 - var8,
                var2 - var8,
                var3 + var8 * 2.0F,
                var4 + var8 * 2.0F,
                var8 * 2.0F,
                var8 * 2.0F,
                var8 * 2.0F,
                var8 * 2.0F,
                -var8,
                0.0F,
                var6,
                var6,
                var6,
                var6
            );
        }
    }

    public void method_463(ResourceLocation var1, float var2, float var3, float var4) {
        this.method_436(var1, var2, var3, var4, var4, 8.0F, 8.0F, 8.0F, 8.0F, 64, 64);
        this.method_436(var1, var2, var3, var4, var4, 40.0F, 8.0F, 8.0F, 8.0F, 64, 64);
    }

    private void method_464(float var1, float var2, float var3, float var4, float var5, float var6, int var7, int var8, int var9, int var10) {
        GL11.glBegin(7);
        method_439(var1, var2, var5, var6, var7);
        method_439(var1, var4, var5, var6, var10);
        method_439(var3, var4, var5, var6, var9);
        method_439(var3, var2, var5, var6, var8);
        GL11.glEnd();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.resetColor();
    }

    public void method_465() {
        if (this.flag) {
            Cursors.method_806(Cursors.CursorType.POINTER);
        }
    }

    public void method_466() {
    }

    private void method_467(float var1, float var2, float var3, float var4, int var5) {
        int var7;
        GL11.glColor4f(((var7 = this.method_437(var5)) >> 16 & 0xFF) / 255.0F, (var7 >> 8 & 0xFF) / 255.0F, (var7 & 0xFF) / 255.0F, (var7 >>> 24) / 255.0F);
        GL11.glTexCoord2f(var3, var4);
        GL11.glVertex2f(var1, var2);
    }

    public void method_468() {
        boolean var10006 = true;
        this.floatValue = 1.0F / Math.max(1, new ScaledResolution(mc).getScaleFactor());
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GL11.glTexEnvi(8960, 8704, 8448);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 771);
        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
        GlStateManager.shadeModel(7425);
    }

    public String method_469(FontStyle var1, String var2, float var3) {
        var var5 = this;
        if (this.method_442(var1, var2) <= var3) {
            return var2;
        } else {
            String var4 = "...";
            var3 -= this.method_442(var1, var4);
            int var9 = 0;
            int var6 = var2.length();

            while (var9 < var6) {
                int var7 = (var9 + var6 + 1) / 2;
                if (!(var5.method_442(var1, var2.substring(0, var7)) <= var3)) {
                    var6 = var7 - 1;
                } else {
                    var9 = var7;
                }
            }

            return new StringBuilder().insert(0, var2.substring(0, var9)).append(var4).toString();
        }
    }

    public void method_470(String var1, float var2, float var3, float var4, int var5, float var6) {
        var var7 = this;
        GlyphAtlasFont var9 = FontFiles.method_624();
        var4 /= 16.0F;
        float var10 = var9.method_631(var1) * var4;
        var2 -= var10 / 2.0F;
        var3 += var4 * 1.0F;
        var5 = this.method_437(var5);
        if (var6 <= 0.0F) {
            this.method_429(var1, var2, var3, var4, var5);
        } else {
            var5 = method_453(var5);
            var6 /= 2.0F;
            byte var11 = 0;

            for (byte var8 = 0; var8 < floatValueArray.length; var8 = var11) {
                float var10002 = var2 + floatValueArray[var11] * var6;
                float var10003 = var3 + floatValueArray[var11 + 1] * var6;
                var11 += 2;
                var7.method_429(var1, var10002, var10003, var4, var5);
            }

            var7.method_429(var1, var2, var3, var4, var5);
        }
    }

    private void method_471(FontStyle var1, String var2, float var3, float var4, int var5) {
        float var7 = var1.method_645();
        this.method_441();
        GlStateManager.translate(var3, var4, 0.0F);
        GlStateManager.scale(var7, var7, 1.0F);
        this.method_457(var1.method_646(), var2, var5);
        this.method_435();
    }

    public boolean method_472(float var1, float var2, float var3, float var4, float var5, int var6) {
        int var7 = FrostedGlass.method_404();
        ShaderProgram var8 = method_480();
        if (var7 != -1 && var8.isFlag()) {
            int[] var9 = method_459(var1, var2, var1 + var3, var2 + var4);
            FrostedGlass.method_397(var9[0], var9[1], var9[2], var9[3]);
            GlStateManager.enableTexture2D();
            GlStateManager.bindTexture(var7);
            var8.method_817();
            var8.method_815("Sampler0", 0);
            var8.method_812("uHalfExtents", var3 / 2.0F, var4 / 2.0F);
            var8.method_822(
                "uCornerRadii",
                Spacing.method_173(var5, var3, var4),
                Spacing.method_173(var5, var3, var4),
                Spacing.method_173(var5, var3, var4),
                Spacing.method_173(var5, var3, var4)
            );
            var8.method_812("uResolution", mc.displayWidth, mc.displayHeight);
            var8.method_821("uPixel", this.floatValue);
            var8.method_821("uTint", ColorUtils.method_213(var6) / 255.0F * 0.88F);
            int var12 = this.method_437(var6 | 0xFF000000);
            this.method_464(var1, var2, var1 + var3, var2 + var4, var1 + var3 / 2.0F, var2 + var4 / 2.0F, var12, var12, var12, var12);
            ShaderProgram.method_819();
            return true;
        } else {
            this.method_479(var1, var2, var3, var4, var5, var6);
            return false;
        }
    }

    public void method_473(FontStyle var1, String var2, float var3, float var4, int var5, float var6) {
        var var11 = this;
        if (!(var6 <= 0.0F) && !var2.isEmpty() && var5 >>> 24 != 0) {
            int var10 = method_453(this.method_437(var5));
            byte var7 = 0;

            for (byte var8 = 0; var8 < floatValueArray.length; var8 = var7) {
                float var10003 = var3 + floatValueArray[var7] * var6;
                float var10005 = floatValueArray[var7 + 1] * var6;
                var7 += 2;
                var11.method_471(var1, var2, var10003, var4 + var10005, var10);
            }

            var11.method_471(var1, var2, var3, var4, var10);
        }
    }

    public void method_474() {
    }

    public void method_475(EntityLivingBase var1, float var2, float var3, int var4) {
        if (var1 != null && var4 > 0) {
            ShaderProgram.method_819();
            GlStateManager.enableTexture2D();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableDepth();
            GlStateManager.enableAlpha();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.pushMatrix();
            GlStateManager.translate(var2, var3, 0.0F);
            GuiInventory.drawEntityOnScreen(0, 0, var4, 0.0F, 0.0F, var1);
            GlStateManager.popMatrix();
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableColorMaterial();
            GlStateManager.disableFog();
            GlStateManager.disableLighting();
            GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GlStateManager.disableTexture2D();
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
            GL11.glTexEnvi(8960, 8704, 8448);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 771);
            GlStateManager.disableAlpha();
            GlStateManager.disableDepth();
            GlStateManager.shadeModel(7425);
            GlStateManager.resetColor();
        }
    }

    public void method_476(FontStyle var1, String var2, float var3, float var4, int var5) {
        this.method_438(var1, var2, var3, this.method_433(var1, var4), var5);
    }

    public void method_477(String var1, float var2, float var3, float var4, int var5) {
        this.method_470(var1, var2, var3, var4, var5, 0.0F);
    }

    public void method_478(
        ResourceLocation var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, int var10, int var11, int var12
    ) {
        this.method_487(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, FilterMode.PIXELS);
    }

    public void method_479(float var1, float var2, float var3, float var4, float var5, int var6) {
        this.method_482(var1, var2, var3, var4, var5, var5, var5, var5, var6);
    }

    private static ShaderProgram method_480() {
        if (shaderProgram3 == null) {
            shaderProgram3 = ShaderProgram.method_820("onyx_frost");
        }

        return shaderProgram3;
    }

    public Corners method_481() {
        return Corners.INSTANCE;
    }

    public void method_482(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, int var9) {
        this.method_490(var1, var2, var3, var4, var5, var6, var7, var8, var9, var9, var9, var9);
    }

    public void method_483() {
        for (UiRenderer var1 = this; !var1.deque.isEmpty(); var1 = this) {
            this.method_486();
        }

        ShaderProgram.method_819();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.resetColor();
        GlStateManager.shadeModel(7424);
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        if (this.flag) {
            Cursors.method_807();
        }
    }

    public void method_484(FontStyle var1, String var2, float var3, float var4, int var5) {
        this.method_446(var1, var2, var3, this.method_433(var1, var4), var5);
    }

    public void method_485(float var1, float var2, float var3, float var4, float var5, int var6) {
        float var9;
        if (!((var9 = ColorRoles.method_181(var6)) <= 0.0F)) {
            int var7 = Theme.getPalette().intValue29();
            this.method_431(var1, var2, var3, var4, var5, var9, 0.0F, Theme.method_180(var7, 0.055F));
            this.method_431(var1, var2, var3, var4, var5, var9 * 1.5F, var9 * 0.5F, Theme.method_180(var7, 0.075F));
        }
    }

    public void method_486() {
        if (!this.deque.isEmpty()) {
            this.deque.pop();
            int[] var2;
            if ((var2 = this.deque.peek()) == null) {
                GL11.glDisable(3089);
            } else {
                method_447(var2);
            }
        }
    }

    public void method_487(
        ResourceLocation var1,
        float var2,
        float var3,
        float var4,
        float var5,
        float var6,
        float var7,
        float var8,
        float var9,
        int var10,
        int var11,
        int var12,
        FilterMode var13
    ) {
        int var20 = this.method_437(var12);
        mc.getTextureManager().bindTexture(var1);
        GlStateManager.enableTexture2D();
        int var21 = var13.method_678();
        GL11.glColor4f((var20 >> 16 & 0xFF) / 255.0F, (var20 >> 8 & 0xFF) / 255.0F, (var20 & 0xFF) / 255.0F, (var20 >>> 24) / 255.0F);
        float var17;
        if (var8 < var10) {
            var17 = var13.method_673() / var10;
        } else {
            var17 = 0.0F;
        }

        float var18;
        if (var9 < var11) {
            var18 = var13.method_673() / var11;
        } else {
            var18 = 0.0F;
        }

        float var15 = var6 / var10 + var17;
        float var16 = var7 / var11 + var18;
        var6 = (var6 + var8) / var10 - var17;
        var7 = (var7 + var9) / var11 - var18;
        GL11.glBegin(7);
        GL11.glTexCoord2f(var15, var16);
        GL11.glVertex2f(var2, var3);
        GL11.glTexCoord2f(var15, var7);
        GL11.glVertex2f(var2, var3 + var5);
        GL11.glTexCoord2f(var6, var7);
        GL11.glVertex2f(var2 + var4, var3 + var5);
        GL11.glTexCoord2f(var6, var16);
        GL11.glVertex2f(var2 + var4, var3);
        GL11.glEnd();
        var13.method_675(var21);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.resetColor();
    }

    public void method_488(float var1, float var2, float var3, float var4, int var5) {
        this.method_494(var1 - var3, var2 - var3, var3 * 2.0F, var3 * 2.0F, Float.MAX_VALUE, var4, var5);
    }

    public void method_489(float var1, float var2, float var3, int var4) {
        this.method_479(var1 - var3, var2 - var3, var3 * 2.0F, var3 * 2.0F, Float.MAX_VALUE, var4);
    }

    public void method_490(
        float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, int var9, int var10, int var11, int var12
    ) {
        this.method_445(var1, var2, var3, var4, var5, var6, var7, var8, 0.0F, 0.0F, var9, var10, var11, var12);
    }

    public void method_491(float var1, float var2, float var3) {
        GlStateManager.translate(var2, var3, 0.0F);
        GlStateManager.scale(var1, var1, 1.0F);
        GlStateManager.translate(-var2, -var3, 0.0F);
        if (var1 > 0.0F) {
            this.floatValue /= var1;
        }
    }

    public void method_492(float var1, float var2, float var3, float var4) {
        if (!(var3 <= 0.0F) && !(var4 <= 0.0F)) {
            Hct var15 = Hct.method_188(Theme.getIntValue());
            double var6 = var15.getDoubleValue();
            double var8 = var15.getDoubleValue2() * 0.6;
            int var5 = TonalPalette.method_193(var6 - 25.0, var8).method_196(15);
            int var10 = TonalPalette.method_193(var6 + 20.0, var8).method_196(27);
            int var11 = TonalPalette.method_193(var6 + 60.0, var8).method_196(41);
            int var14 = TonalPalette.method_193(var6 - 45.0, var8).method_196(57);
            ShaderProgram var7;
            if (!(var7 = method_450()).isFlag()) {
                this.method_444(var1, var2, var3, var4, 0.0F, var10, var11, var5, var5);
            } else {
                GlStateManager.disableTexture2D();
                var7.method_817();
                var7.method_821("uAspect", var3 / var4);
                var7.method_821("uTime", (float)(System.nanoTime() - longValue) / 1.0E9F);
                var7.method_813("uGlow", var14);
                var7.method_813("uMid", var10);
                var7.method_813("uAccent", var11);
                var7.method_813("uDeep", var5);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glBegin(7);
                GL11.glTexCoord2f(0.0F, 0.0F);
                GL11.glVertex2f(var1, var2);
                GL11.glTexCoord2f(0.0F, 1.0F);
                GL11.glVertex2f(var1, var2 + var4);
                GL11.glTexCoord2f(1.0F, 1.0F);
                GL11.glVertex2f(var1 + var3, var2 + var4);
                GL11.glTexCoord2f(1.0F, 0.0F);
                GL11.glVertex2f(var1 + var3, var2);
                GL11.glEnd();
                ShaderProgram.method_819();
                GlStateManager.resetColor();
            }
        }
    }

    public void method_493(ResourceLocation var1, float var2, float var3, float var4, float var5, int var6, int var7, int var8, int var9) {
        mc.getTextureManager().bindTexture(var1);
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.shadeModel(7425);
        GlStateManager.disableCull();
        GlStateManager.disableAlpha();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 1, 0, 1);
        int var11 = FilterMode.MINIFIED.method_678();
        GL11.glBegin(7);
        this.method_467(var2, var3, 0.0F, 0.99F, var6);
        this.method_467(var2, var3 + var5, 0.99F, 0.99F, var7);
        this.method_467(var2 + var4, var3 + var5, 0.99F, 0.0F, var8);
        this.method_467(var2 + var4, var3, 0.0F, 0.0F, var9);
        GL11.glEnd();
        FilterMode.MINIFIED.method_675(var11);
        GlStateManager.depthMask(true);
        GlStateManager.enableAlpha();
        GlStateManager.enableCull();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 771);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.resetColor();
    }

    public void method_494(float var1, float var2, float var3, float var4, float var5, float var6, int var7) {
        this.method_445(var1, var2, var3, var4, var5, var5, var5, var5, -var6, 0.0F, var7, var7, var7, var7);
    }

    public void method_495(float var1, float var2, float var3, float var4, float var5, int var6) {
        var var8 = this;
        var5 *= (float) (Math.PI * 2);
        int var7 = 0;

        for (int var9 = 0; var9 < 24; var9 = var7) {
            float var10 = var5 + 4.523894F * (var7 / 24.0F);
            float var10001 = var1 + (float)Math.cos(var10) * var3;
            float var10002 = var2 + (float)Math.sin(var10) * var3;
            var7++;
            var8.method_489(var10001, var10002, var4 / 2.0F, var6);
        }
    }
}
