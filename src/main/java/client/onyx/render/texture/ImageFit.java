package client.onyx.render.texture;

import client.onyx.setting.NamedEnum;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import net.optifine.player.CapeUtils;

public final class ImageFit {
    private static final int INT_171 = 17;
    private static final int INT_81 = 8;
    public static final int INT_11 = 1;
    public static final int INT_321 = 32;
    private static final int INT_221 = 22;
    public static final int INT_12 = 1;
    public static final int INT_101 = 10;
    public static final int INT_121 = 12;
    private static final int INT_322 = 32;
    private static final float FLOAT_0_551 = 0.55F;
    public static final int INT_641 = 64;
    public static final int INT_161 = 16;

    public static boolean method_665(BufferedImage var0) {
        int var1 = var0.getWidth();
        int var3 = var0.getHeight();
        return var1 % 64 == 0 && var3 % 32 == 0 && var1 / 64 == var3 / 32;
    }

    public static BufferedImage method_666(BufferedImage var0, ImageFit.FitMode var1, int var2, double var3, double var5, double var7) {
        if (method_670(var1, var0) == ImageFit.FitMode.TEXTURE) {
            return CapeUtils.parseCape(var0);
        } else {
            int var14 = method_669(var0);
            int var10 = 10 * var14;
            int var11 = 16 * var14;
            BufferedImage var12;
            Graphics2D var13;
            Graphics2D var10001 = var13 = (var12 = new BufferedImage(64 * var14, 32 * var14, 2)).createGraphics();
            var10001.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            var10001.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            Color var16;
            var10001.setPaint(var16 = new Color(var2 | 0xFF000000, true));
            var10001.fillRect(0, 0, 22 * var14, 17 * var14);
            var10001.setPaint(method_668(var16));
            var10001.fillRect(12 * var14, 1 * var14, var10, var11);
            double[] var15 = method_667(var0, method_670(var1, var0), var10, var11, var3, var5, var7);
            var13.setClip(1 * var14, 1 * var14, var10, var11);
            var13.drawImage(
                var0,
                1 * var14 + (int)Math.round(var15[0]),
                1 * var14 + (int)Math.round(var15[1]),
                Math.max(1, (int)Math.round(var15[2])),
                Math.max(1, (int)Math.round(var15[3])),
                null
            );
            var13.dispose();
            return var12;
        }
    }

    public static double[] method_667(BufferedImage var0, ImageFit.FitMode var1, double var2, double var4, double var6, double var8, double var10) {
        double var14;
        double var22;
        if (var1 == ImageFit.FitMode.STRETCH) {
            var14 = var4;
            var22 = var2;
        } else {
            double var16 = var2 / var0.getWidth();
            double var18 = var4 / var0.getHeight();
            double var24;
            if (var1 == ImageFit.FitMode.FILL) {
                var24 = Math.max(var16, var18);
            } else {
                var24 = Math.min(var16, var18);
            }

            double var12 = var0.getWidth() * var24;
            var14 = var0.getHeight() * var24;
            var22 = var12;
        }

        double var28 = var22 * var6;
        var14 *= var6;
        double var30 = (var2 - var28) / 2.0 + var8 * var2;
        double var31 = (var4 - var14) / 2.0 + var10 * var4;
        return new double[]{var30, var31, var28, var14};
    }

    private ImageFit() {
    }

    private static Color method_668(Color var0) {
        return new Color((int)(var0.getRed() * 0.55F), (int)(var0.getGreen() * 0.55F), (int)(var0.getBlue() * 0.55F));
    }

    private static int method_669(BufferedImage var0) {
        int var2 = (int)Math.ceil(Math.max(var0.getWidth() / 10.0, var0.getHeight() / 16.0));
        return Math.max(8, Math.min(32, var2));
    }

    public static ImageFit.FitMode method_670(ImageFit.FitMode var0, BufferedImage var1) {
        if (var0 != ImageFit.FitMode.AUTO) {
            return var0;
        } else {
            return method_665(var1) ? ImageFit.FitMode.TEXTURE : ImageFit.FitMode.FIT;
        }
    }

    public static enum FitMode implements NamedEnum {
        AUTO("Auto"),
        FIT("Fit"),
        FILL("Fill"),
        STRETCH("Stretch"),
        TEXTURE("Texture");

        private final String string;

        @Override
        public String getDisplayName() {
            return this.string;
        }

        private FitMode(String var3) {
            this.string = var3;
        }
    }
}
