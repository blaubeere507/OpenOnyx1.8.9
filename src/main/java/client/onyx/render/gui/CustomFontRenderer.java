package client.onyx.render.gui;

import client.onyx.Onyx;
import client.onyx.module.hud.CustomGui;
import client.onyx.render.font.FontFiles;
import client.onyx.render.font.FontStyle;
import client.onyx.render.font.GlyphAtlasFont;
import java.util.Random;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public final class CustomFontRenderer {
    private static final Random random = new Random();
    private static final float FLOAT_1_01 = 1.0F;
    private static FontFiles.Weight weight;
    private static final int INT_165798361 = 16579836;
    private static float floatValue;
    private static FontStyle fontStyle;
    private static final float FLOAT_0_21 = 0.2F;
    private static FontStyle fontStyle2;
    private static final float FLOAT_7_01 = 7.0F;
    private static final float FLOAT_7_02 = 7.0F;
    private static boolean flag;
    private static final String STRING_CONST = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static boolean flag2;

    private static float method_921(FontRenderer var0, char var1, float var2, float var3, int var4, boolean var5) {
        float var14; // declared by DeclareMissing
        char var13 = var1;
        FontRenderer var15 = var0;
        String var6 = String.valueOf(var1);
        flag2 = true;

        try {
            var15.drawString(var6, var2, var3, var4, false);
            float var16 = var15.getCharWidth(var13);
            float var9;
            float var10;
            if (!var5) {
                float var8 = var16;
                var10 = 0.0F;
                var9 = var8;
            } else {
                var10 = var15.offsetBold;
                var9 = var16;
            }

            var14 = var9 + var10;
        } finally {
            flag2 = false;
            GlStateManager.enableTexture2D();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        }

        return var14;
    }

    public static boolean method_922(char var0) {
        char var1 = var0;

        try {
            return var1 == 167 || var1 == ' ' || method_929(false).method_646().method_638(var1);
        } catch (Throwable var2) {
            flag = true;
            Onyx.LOGGER.warn("Custom GUI font stood down to the bitmap font after a failure", var2);
            return false;
        }
    }

    public static float method_923(char var0) {
        FontStyle var2;
        return var0 == 167 ? -1.0F : (var2 = method_929(false)).method_646().method_626(var0) * var2.method_645() + method_930();
    }

    private static void method_924(FontRenderer var0, String var1, float var2, float var3, int var4, boolean var5) {
        FontRenderer var6 = var0;
        if (!var1.isEmpty()) {
            FontStyle var30;
            GlyphAtlasFont var7 = (var30 = method_929(false)).method_646();
            GlyphAtlasFont var8 = method_929(true).method_646();
            int var9 = 0;

            for (int var20 = 0; var20 < var1.length(); var20 = var9) {
                var7.method_635(var1.charAt(var9));
                var8.method_635(var1.charAt(var9++));
            }

            if (var7.getIntValue2() != -1) {
                float var34 = var30.method_645();
                int var33 = var4;
                GlStateManager.enableTexture2D();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                var2 = var2;
                int var31 = var4;
                boolean var10 = false;
                boolean var11 = false;
                boolean var12 = false;
                boolean var13 = false;
                boolean var14 = false;
                int var15 = 0;

                for (int var29 = 0; var29 < var1.length(); var29 = ++var15) {
                    char var16;
                    if ((var16 = var1.charAt(var15)) == 167 && var15 < var1.length() - 1) {
                        char var17 = Character.toLowerCase(var1.charAt(++var15));
                        if ("0123456789abcdef".indexOf(var17) >= 0) {
                            var14 = false;
                            var13 = false;
                            var12 = false;
                            var11 = false;
                            var10 = false;
                            int var35 = var6.getColorCode(var17);
                            int var36 = var33 & 0xFF000000;
                            int var22;
                            int var23;
                            if (!var5) {
                                int var21 = var36;
                                var23 = var35;
                                var22 = var21;
                            } else {
                                var23 = (var35 & 16579836) >> 2;
                                var22 = var36;
                            }

                            var31 = var22 | var23 & 16777215;
                        } else {
                            switch (var17) {
                                case 'k':
                                    var12 = true;
                                    break;
                                case 'l':
                                    var10 = true;
                                    break;
                                case 'm':
                                    var14 = true;
                                    break;
                                case 'n':
                                    var13 = true;
                                    break;
                                case 'o':
                                    var11 = true;
                                case 'p':
                                case 'q':
                                default:
                                    break;
                                case 'r':
                                    var14 = false;
                                    var13 = false;
                                    var12 = false;
                                    var11 = false;
                                    var10 = false;
                                    var31 = var33;
                            }
                        }
                    } else {
                        char var24;
                        if (var12) {
                            var24 = method_927(var16);
                        } else {
                            var24 = var16;
                        }

                        if (!method_922(var24)) {
                            var2 += method_921(var6, var24, var2, var3, var31, var10) + method_930();
                        } else {
                            GlyphAtlasFont var25;
                            if (var10) {
                                var25 = var8;
                            } else {
                                var25 = var7;
                            }

                            float var10000 = var7.method_626(var16) * var34 + method_930();
                            float var27;
                            float var28;
                            if (!var10) {
                                float var26 = var10000;
                                var28 = 0.0F;
                                var27 = var26;
                            } else {
                                var28 = method_937(var6);
                                var27 = var10000;
                            }

                            float var19 = var27 + var28;
                            method_938(var25, var24, var2, var3, var34, var31, var11);
                            if (var14) {
                                method_934(var2, var3 + 3.8500001F, var19, var31);
                            }

                            if (var13) {
                                method_934(var2, var3 + 7.0F + 1.0F, var19, var31);
                            }

                            var2 += var19;
                        }
                    }
                }

                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.resetColor();
            }
        }
    }

    private static int method_925(FontRenderer var0, String var1, float var2, float var3, int var4, boolean var5) {
        int var6 = var4;
        if (var1 == null) {
            return 0;
        } else {
            if ((var4 & -67108864) == 0) {
                var6 = var4 | 0xFF000000;
            }

            if (var5) {
                method_924(var0, var1, var2 + 1.0F, var3 + 1.0F, method_935(var6), true);
            }

            method_924(var0, var1, var2, var3, var6, false);
            return (int)(var2 + method_933(var0, var1));
        }
    }

    private static float method_926(FontRenderer var0, char var1) {
        int var6; // declared by DeclareMissing
        char var5 = var1;
        FontRenderer var7 = var0;
        flag2 = true;

        try {
            var6 = var7.getCharWidth(var5);
        } finally {
            flag2 = false;
        }

        return var6;
    }

    private static char method_927(char var0) {
        return var0 == ' ' ? var0 : "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".charAt(random.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".length()));
    }

    private static FontFiles.Weight method_928(FontFiles.Weight var0) {
        switch (var0) {
            case REGULAR:
                return FontFiles.Weight.SEMI_BOLD;
            case MEDIUM:
                return FontFiles.Weight.BOLD;
            default:
                return FontFiles.Weight.BOLD;
        }
    }

    private static FontStyle method_929(boolean var0) {
        CustomGui var1;
        float var4;
        if ((var1 = CustomGuiState.method_977()) == null) {
            var4 = 1.0F;
        } else {
            var4 = var1.fontSettings.size.method_151();
        }

        FontFiles.Weight var5;
        if (var1 == null) {
            var5 = FontFiles.Weight.REGULAR;
        } else {
            var5 = var1.fontSettings.weight.getValue().getWeight();
        }

        if (fontStyle2 == null || var4 != floatValue || var5 != weight) {
            fontStyle2 = method_940(var5, var4);
            fontStyle = method_940(method_928(var5), var4);
            floatValue = var4;
            weight = var5;
        }

        return var0 ? fontStyle : fontStyle2;
    }

    private static float method_930() {
        CustomGui var0;
        return (var0 = CustomGuiState.method_977()) == null ? 0.0F : var0.fontSettings.letterSpacing.method_151();
    }

    public static int method_931(FontRenderer var0, String var1, float var2, float var3, int var4, boolean var5) {
        FontRenderer var6 = var0;

        try {
            return method_925(var6, var1, var2, var3, var4, var5);
        } catch (Throwable var7) {
            flag = true;
            flag2 = false;
            Onyx.LOGGER.warn("Custom GUI font stood down to the bitmap font after a failure", var7);
            return var0.drawString(var1, var2, var3, var4, var5);
        }
    }

    private CustomFontRenderer() {
    }

    public static void method_932() {
        fontStyle2 = null;
        fontStyle = null;
        flag = false;
    }

    public static int method_933(FontRenderer var0, String var1) {
        FontRenderer var2 = var0;
        if (var1 != null && !var1.isEmpty()) {
            float var11 = 0.0F;
            boolean var4 = false;
            int var3 = 0;

            for (int var10 = 0; var10 < var1.length(); var10 = ++var3) {
                char var5;
                if ((var5 = var1.charAt(var3)) == 167 && var3 < var1.length() - 1) {
                    char var6;
                    if ((var6 = Character.toLowerCase(var1.charAt(++var3))) == 'l') {
                        var4 = true;
                    } else if (var6 == 'r' || "0123456789abcdef".indexOf(var6) >= 0) {
                        var4 = false;
                    }
                } else {
                    float var8;
                    float var9;
                    if (!method_922(var5)) {
                        var9 = method_926(var2, var5) + method_930();
                        var8 = var11;
                    } else {
                        var9 = method_923(var5);
                        var8 = var11;
                    }

                    var11 = var8 + var9;
                    if (var4) {
                        var11 += var2.offsetBold;
                    }
                }
            }

            return Math.round(var11);
        } else {
            return 0;
        }
    }

    private static void method_934(float var0, float var1, float var2, int var3) {
        GlStateManager.disableTexture2D();
        GL11.glColor4f((var3 >> 16 & 0xFF) / 255.0F, (var3 >> 8 & 0xFF) / 255.0F, (var3 & 0xFF) / 255.0F, (var3 >>> 24) / 255.0F);
        GL11.glBegin(7);
        GL11.glVertex2f(var0, var1);
        GL11.glVertex2f(var0, var1 + 1.0F);
        GL11.glVertex2f(var0 + var2, var1 + 1.0F);
        GL11.glVertex2f(var0 + var2, var1);
        GL11.glEnd();
        GlStateManager.enableTexture2D();
    }

    private static int method_935(int var0) {
        return var0 & 0xFF000000 | (var0 & 16579836) >> 2;
    }

    public static boolean method_936() {
        CustomGui var0;
        return !flag && !flag2 && CustomGuiState.isFlag() && (var0 = CustomGuiState.method_977()) != null && var0.isToggled() && var0.fontSettings.method_61();
    }

    private static float method_937(FontRenderer var0) {
        return var0.offsetBold;
    }

    private static void method_938(GlyphAtlasFont var0, char var1, float var2, float var3, float var4, int var5, boolean var6) {
        GlyphAtlasFont.Glyph var12;
        if (!(var12 = var0.method_635(var1)).method_647()) {
            int var7;
            if ((var7 = var0.getIntValue2()) != -1) {
                GlStateManager.bindTexture(var7);
                GL11.glColor4f((var5 >> 16 & 0xFF) / 255.0F, (var5 >> 8 & 0xFF) / 255.0F, (var5 & 0xFF) / 255.0F, (var5 >>> 24) / 255.0F);
                var2 += var12.floatValue3() * var4;
                float var16 = var3 + 7.0F + var12.floatValue4() * var4;
                float var17 = var2 + var12.floatValue2() * var4;
                var4 = var16 + var12.floatValue7() * var4;
                float var9;
                if (var6) {
                    var9 = (var3 + 7.0F - var16) * 0.2F;
                } else {
                    var9 = 0.0F;
                }

                float var10;
                if (var6) {
                    var10 = (var3 + 7.0F - var4) * 0.2F;
                } else {
                    var10 = 0.0F;
                }

                GL11.glBegin(7);
                GL11.glTexCoord2f(var12.floatValue(), var12.floatValue6());
                GL11.glVertex2f(var2 + var9, var16);
                GL11.glTexCoord2f(var12.floatValue(), var12.floatValue9());
                GL11.glVertex2f(var2 + var10, var4);
                GL11.glTexCoord2f(var12.floatValue5(), var12.floatValue9());
                GL11.glVertex2f(var17 + var10, var4);
                GL11.glTexCoord2f(var12.floatValue5(), var12.floatValue6());
                GL11.glVertex2f(var17 + var9, var16);
                GL11.glEnd();
            }
        }
    }

    public static boolean method_939() {
        CustomGui var0;
        return (var0 = CustomGuiState.method_977()) == null || var0.fontSettings.shadow.method_146();
    }

    private static FontStyle method_940(FontFiles.Weight var0, float var1) {
        FontFiles.Size var6 = FontFiles.Size.PT24;
        float var3;
        float var5;
        if ((var3 = FontFiles.method_625(FontFiles.Size.PT24, var0).getFloatValue() / var6.getFloatValue()) > 0.0F) {
            var5 = 7.0F / var3;
        } else {
            var5 = var6.getFloatValue();
        }

        return new FontStyle(var6, var0, var5 * var1, 9.0F);
    }
}
