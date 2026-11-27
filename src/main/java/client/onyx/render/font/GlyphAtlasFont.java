package client.onyx.render.font;

import client.onyx.Onyx;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.LineMetrics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public final class GlyphAtlasFont {
    private static final GlyphAtlasFont.Glyph glyph2 = new GlyphAtlasFont.Glyph(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
    private static final GlyphAtlasFont.Glyph glyph = glyph2;
    private static final int INT_81 = 8;
    private static final int INT_5121 = 512;
    private static final int INT_31 = 3;
    private static final int INT_20481 = 2048;
    private static final int INT_82 = 8;
    private static final int INT_1921 = 192;
    private static final float FLOAT_100_01 = 100.0F;
    public static final int INT_41 = 4;
    private static final int INT_121 = 12;
    private static final float FLOAT_0_721 = 0.72F;
    private static final float FLOAT_0_211 = 0.21F;
    private final Map<Character, GlyphAtlasFont.Glyph> map = new HashMap<>();
    private final FontRenderContext fontRenderContext = new FontRenderContext(null, true, true);
    private int intValue2 = -1;
    private final Font font;
    private final float floatValue2;
    private final int intValue5;
    private final float floatValue;
    private final float floatValue3;
    private boolean flag;
    private int intValue;
    private int intValue3;
    private int intValue4;

    public float method_626(char var1) {
        return this.method_635(var1).floatValue8();
    }

    private static byte[] method_627(byte[] var0, int var1, int var2, int var3, int var4) {
        byte[] var10 = var0;
        byte[] var5 = new byte[var3 * var4];
        int var6 = 0;

        for (int var13 = 0; var13 < var4; var13 = ++var6) {
            int var7;
            int var8 = Math.min((var7 = var6 * 2) + 1, var2 - 1);
            int var9 = 0;

            for (int var12 = 0; var12 < var3; var12 = var9) {
                int var14;
                int var11 = Math.min((var14 = var9 * 2) + 1, var1 - 1);
                int var15 = (var10[var7 * var1 + var14] & 255)
                    + (var10[var7 * var1 + var11] & 255)
                    + (var10[var8 * var1 + var14] & 255)
                    + (var10[var8 * var1 + var11] & 255);
                int var10001 = var6 * var3 + var9;
                byte var10002 = (byte)(var15 >> 2);
                var9++;
                var5[var10001] = var10002;
            }
        }

        return var5;
    }

    public float getFloatValue() {
        return this.floatValue;
    }

    private float method_628(char var1) {
        return -this.method_640(var1).y * this.floatValue2;
    }

    private void method_629(int var1, int var2, int var3, int var4, int var5, byte[] var6) {
        ByteBuffer var7;
        (var7 = BufferUtils.createByteBuffer(var4 * var5)).put(var6, 0, var4 * var5).flip();
        GL11.glTexSubImage2D(3553, var1, var2, var3, var4, var5, 6406, 5121, var7);
    }

    public static GlyphAtlasFont method_630(String var0, float var1) {
        String var2 = var0;

        try {
            InputStream var9 = GlyphAtlasFont.class.getResourceAsStream(var2);

            GlyphAtlasFont var3;
            try {
                if (var9 == null) {
                    throw new IOException(new StringBuilder().insert(0, "Missing font ").append(var2).toString());
                }

                var3 = new GlyphAtlasFont(Font.createFont(0, var9), var1);
            } catch (Throwable var7) {
                if (var9 != null) {
                    try {
                        var9.close();
                    } catch (Throwable var6) {
                        var7.addSuppressed(var6);
                        throw var7;
                    }

                    throw var7;
                }

                throw var7;
            }

            if (var9 != null) {
                var9.close();
            }

            return var3;
        } catch (Exception var8) {
            Onyx.LOGGER.error("Failed to load font '{}'", var0, var8);
            return new GlyphAtlasFont(new Font("SansSerif", 0, 1), var1);
        }
    }

    private GlyphAtlasFont(Font var1, float var2) {
        float var7 = Math.min(var2 * 4.0F, 192.0F);
        LineMetrics var4;
        float var6;
        float var11;
        if ((var11 = (var4 = var1.deriveFont(100.0F).getLineMetrics("Ayg", this.fontRenderContext)).getAscent() + var4.getDescent()) <= 0.0F) {
            var6 = 1.0F;
        } else {
            var6 = 100.0F / var11;
        }

        this.font = var1.deriveFont(var7 * var6);
        this.floatValue2 = var2 / var7;
        int var9 = (int)var7 + 16;
        this.intValue5 = Math.min(2048, Math.max(512, Integer.highestOneBit(var9 * 12 - 1) * 2));
        float var10 = this.method_628('H');
        float var8 = this.method_637('p');
        this.floatValue = var10 > 0.0F ? var10 : var2 * 0.72F;
        this.floatValue3 = var8 > 0.0F ? var8 : var2 * 0.21F;
    }

    public float method_631(String var1) {
        var var2 = this;
        float var5 = 0.0F;
        int var3 = 0;

        for (int var4 = 0; var4 < var1.length(); var4 = var3) {
            char var10002 = var1.charAt(var3);
            var3++;
            var5 += var2.method_626(var10002);
        }

        return var5;
    }

    private static int method_632(int var0) {
        return var0 + 8 - 1 & -8;
    }

    private int[] method_633(int var1, int var2) {
        if (this.flag) {
            return null;
        } else {
            var1 = method_632(var1);
            int var4 = method_632(var2);
            if (this.intValue + var1 > this.intValue5) {
                this.intValue = 0;
                this.intValue3 = this.intValue3 + this.intValue4;
                this.intValue4 = 0;
            }

            if (this.intValue3 + var4 > this.intValue5) {
                this.flag = true;
                Onyx.LOGGER.warn("Glyph atlas full at {}px", this.intValue5);
                return null;
            } else {
                int[] var7 = new int[]{this.intValue, this.intValue3};
                this.intValue += var1;
                this.intValue4 = Math.max(this.intValue4, var4);
                return var7;
            }
        }
    }

    public float getFloatValue3() {
        return this.floatValue3;
    }

    private void method_634(BufferedImage var1, int var2, int var3, int var4, int var5) {
        var var14 = this;
        if (this.intValue2 == -1) {
            this.method_641();
        }

        int[] var12;
        byte[] var6 = new byte[(var12 = ((DataBufferInt)var1.getRaster().getDataBuffer()).getData()).length];
        int var7 = 0;

        for (int var9 = 0; var9 < var12.length; var9 = var7) {
            int var10001 = var7;
            byte var10002 = (byte)(var12[var7] >>> 24);
            var7++;
            var6[var10001] = var10002;
        }

        GlStateManager.bindTexture(this.intValue2);
        GL11.glPixelStorei(3317, 1);
        var7 = var4;
        int var13 = var5;
        var4 = 0;

        for (int var10 = 0; var10 <= 3; var10 = var4) {
            var14.method_629(var4, var2 >> var4, var3 >> var4, var7, var13, var6);
            if (var4 == 3) {
                break;
            }

            var5 = Math.max(1, var7 >> 1);
            int var8 = Math.max(1, var13 >> 1);
            var6 = method_627(var6, var7, var13, var5, var8);
            var4++;
            var7 = var5;
            var13 = var8;
        }

        GL11.glPixelStorei(3317, 4);
    }

    public GlyphAtlasFont.Glyph method_635(char var1) {
        GlyphAtlasFont.Glyph var3;
        if ((var3 = this.map.get(var1)) != null) {
            return var3;
        } else {
            GlyphAtlasFont.Glyph var4 = this.method_636(var1);
            this.map.put(var1, var4);
            return var4;
        }
    }

    private GlyphAtlasFont.Glyph method_636(char var1) {
        if (!this.font.canDisplay(var1)) {
            return glyph;
        } else {
            GlyphVector var9 = this.font.createGlyphVector(this.fontRenderContext, new char[]{var1});
            float var5 = var9.getGlyphMetrics(0).getAdvanceX() * this.floatValue2;
            Rectangle var3;
            if ((var3 = var9.getPixelBounds(this.fontRenderContext, 0.0F, 0.0F)).width > 0 && var3.height > 0) {
                int var4 = var3.width + 16;
                int var2 = var3.height + 16;
                if (var4 <= this.intValue5 && var2 <= this.intValue5) {
                    BufferedImage var6;
                    Graphics2D var7 = (var6 = new BufferedImage(var4, var2, 2)).createGraphics();
                    var7.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    var7.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                    var7.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
                    var7.setColor(Color.WHITE);
                    var7.setFont(this.font);
                    var7.drawGlyphVector(var9, 8 - var3.x, 8 - var3.y);
                    var7.dispose();
                    int[] var10;
                    if ((var10 = this.method_633(var4, var2)) == null) {
                        return glyph2;
                    } else {
                        this.method_634(var6, var10[0], var10[1], var4, var2);
                        return new GlyphAtlasFont.Glyph(
                            (float)var10[0] / this.intValue5,
                            (float)var10[1] / this.intValue5,
                            (float)(var10[0] + var4) / this.intValue5,
                            (float)(var10[1] + var2) / this.intValue5,
                            (var3.x - 8) * this.floatValue2,
                            (var3.y - 8) * this.floatValue2,
                            var4 * this.floatValue2,
                            var2 * this.floatValue2,
                            var5
                        );
                    }
                } else {
                    return glyph2;
                }
            } else {
                return new GlyphAtlasFont.Glyph(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, var5);
            }
        }
    }

    private float method_637(char var1) {
        Rectangle var4 = this.method_640(var1);
        return (var4.y + var4.height) * this.floatValue2;
    }

    public boolean method_638(char var1) {
        return Character.isWhitespace(var1) || this.method_639(var1) && !this.method_635(var1).method_647();
    }

    public int getIntValue2() {
        return this.intValue2;
    }

    public boolean method_639(char var1) {
        return this.font.canDisplay(var1);
    }

    private Rectangle method_640(char var1) {
        return this.font.createGlyphVector(this.fontRenderContext, new char[]{var1}).getPixelBounds(this.fontRenderContext, 0.0F, 0.0F);
    }

    private void method_641() {
        var var1 = this;
        this.intValue2 = TextureUtil.glGenTextures();
        GlStateManager.bindTexture(this.intValue2);
        GL11.glTexParameteri(3553, 10241, 9987);
        GL11.glTexParameteri(3553, 10240, 9729);
        GL11.glTexParameteri(3553, 10242, 33071);
        GL11.glTexParameteri(3553, 10243, 33071);
        GL11.glTexParameteri(3553, 33085, 3);
        GL11.glPixelStorei(3317, 1);
        int var4 = 0;

        for (int var3 = 0; var3 <= 3; var3 = var4) {
            int var2 = var1.intValue5 >> var4;
            GL11.glTexImage2D(3553, var4++, 6406, var2, var2, 0, 6406, 5121, BufferUtils.createByteBuffer(var2 * var2));
        }

        GL11.glPixelStorei(3317, 4);
    }

    public record Glyph(
        float floatValue,
        float floatValue6,
        float floatValue5,
        float floatValue9,
        float floatValue3,
        float floatValue4,
        float floatValue2,
        float floatValue7,
        float floatValue8
    ) {
        public boolean method_647() {
            return this.floatValue2 <= 0.0F || this.floatValue7 <= 0.0F;
        }
    }
}
