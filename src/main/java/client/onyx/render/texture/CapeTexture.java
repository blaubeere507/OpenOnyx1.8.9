package client.onyx.render.texture;

import client.onyx.Onyx;
import client.onyx.util.IMinecraft;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import javax.imageio.ImageIO;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

public final class CapeTexture implements IMinecraft {
    private static final ResourceLocation resourceLocation2 = new ResourceLocation("onyx", "capes/local");
    private static final ResourceLocation resourceLocation = new ResourceLocation("onyx", "capes/source");
    private static final ExecutorService executorService = Executors.newSingleThreadExecutor(var0 -> {
        Thread var10000 = new Thread(var0, "onyx-cape-loader");
        var10000.setDaemon(true);
        return var10000;
    });

    public static void method_660(BufferedImage var0, Consumer<ResourceLocation> var1) {
        mc.addScheduledTask(() -> {
            mc.getTextureManager().deleteTexture(resourceLocation2);
            mc.getTextureManager().loadTexture(resourceLocation2, new DynamicTexture(var0));
            var1.accept(resourceLocation2);
        });
    }

    private CapeTexture() {
    }

    public static ResourceLocation method_661(BufferedImage var0) {
        mc.getTextureManager().deleteTexture(resourceLocation);
        mc.getTextureManager().loadTexture(resourceLocation, new DynamicTexture(var0));
        return resourceLocation;
    }

    public static void method_662(Consumer<ResourceLocation> var0, Consumer<String> var1) {
        java.awt.image.BufferedImage var6; // declared by DeclareMissing
        try {
            var6 = LogoTexture.method_659();
        } catch (IOException var5) {
            Onyx.LOGGER.warn("CapeChanger: failed to draw the default cape", var5);
            var1.accept("Can't draw the default cape");
            return;
        }

        method_660(var6, var0);
    }

    public static void method_663(String var0, Consumer<BufferedImage> var1, Consumer<String> var2) {
        if (!var0.isEmpty()) {
            executorService.submit(() -> {
                String var4 = var0;

                BufferedImage var3;
                try {
                    var3 = ImageIO.read(new File(var4));
                } catch (IOException var6) {
                    Onyx.LOGGER.warn("CapeChanger: failed to read '{}'", var0, var6);
                    var2.accept("Can't read that file");
                    return;
                }

                if (var3 == null) {
                    var2.accept("Not a readable PNG");
                } else {
                    mc.addScheduledTask(() -> var1.accept(var3));
                }
            });
        }
    }

    public static void method_664(BufferedImage var0, ImageFit.FitMode var1, int var2, double var3, double var5, double var7, Consumer<ResourceLocation> var9) {
        executorService.submit(() -> {
            ImageFit.FitMode var11 = var1;
            BufferedImage var12 = var0;

            try {
                method_660(ImageFit.method_666(var12, var11, var2, var3, var5, var7), var9);
            } catch (RuntimeException var10) {
                Onyx.LOGGER.warn("CapeChanger: failed to compose the cape", var10);
            }
        });
    }
}
