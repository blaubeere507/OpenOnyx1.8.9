package client.onyx.skin;

import net.minecraft.util.ResourceLocation;

public final class SkinOverride {
    private static volatile ResourceLocation resourceLocation;
    private static volatile String string;

    public static boolean method_266() {
        return resourceLocation != null;
    }

    public static ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    private SkinOverride() {
    }

    public static void method_267(ResourceLocation var0, String var1) {
        resourceLocation = var0;
        String var2;
        if (var1 == null) {
            var2 = "default";
        } else {
            var2 = var1;
        }

        string = var2;
    }

    public static void method_268() {
        resourceLocation = null;
        string = null;
    }

    public static String getString() {
        return string;
    }
}
