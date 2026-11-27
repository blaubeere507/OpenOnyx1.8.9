package client.onyx.render.texture;

import client.onyx.util.IMinecraft;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import net.minecraft.util.ResourceLocation;

public final class LogoTexture implements IMinecraft {
    private static final ResourceLocation resourceLocation = new ResourceLocation("onyx", "textures/gui/logo.png");
    private static final Color color4 = new Color(-14474196, true);
    private static final Color color = new Color(-15987695, true);
    private static final Color color3 = new Color(-16119282, true);
    private static final Color color2 = new Color(587202559, true);
    private static final int INT_81 = 8;
    private static final int INT_1281 = 128;
    private static final int INT_121 = 12;
    private static final float FLOAT_0_71 = 0.7F;
    private static final int INT_1761 = 176;
    private static final int INT_82 = 8;
    private static final int INT_961 = 96;
    private static final int INT_1361 = 136;
    private static final int INT_801 = 80;
    private static final int INT_2561 = 256;
    private static final int INT_83 = 8;
    private static final int INT_5121 = 512;

    private LogoTexture() {
    }

    public static BufferedImage method_659() throws IOException {
        InputStream var1 = mc.getResourceManager().getResource(resourceLocation).getInputStream();

        BufferedImage var0;
        try {
            var0 = ImageIO.read(var1);
        } catch (Throwable var7) {
            if (var1 != null) {
                try {
                    var1.close();
                } catch (Throwable var6) {
                    var7.addSuppressed(var6);
                    throw var7;
                }

                throw var7;
            }

            throw var7;
        }

        BufferedImage var4;
        if (var1 != null) {
            var1.close();
            var4 = var0;
        } else {
            var4 = var0;
        }

        if (var4 == null) {
            throw new IOException("logo.png is not a readable PNG");
        } else {
            BufferedImage var10000 = new BufferedImage(512, 256, 2);
            Graphics2D var2 = var10000.createGraphics();
            var2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            var2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            var2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            var2.setPaint(new GradientPaint(0.0F, 0.0F, color4, 0.0F, 136.0F, color));
            var2.fillRect(0, 0, 176, 136);
            var2.setPaint(color3);
            var2.fillRect(96, 8, 80, 128);
            var2.setPaint(color2);
            var2.setStroke(new BasicStroke(8.0F));
            var2.drawRect(20, 20, 56, 104);
            int var3;
            int var8 = Math.round((float)(var3 = Math.round(56.0F)) * var0.getHeight() / var0.getWidth());
            var2.drawImage(var0, 8 + (80 - var3) / 2, 8 + (128 - var8) / 2, var3, var8, null);
            var2.dispose();
            return var10000;
        }
    }
}
