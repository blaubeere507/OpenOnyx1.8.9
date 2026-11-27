package client.onyx.ui.theme;

public final class ColorRoles {
    public static final float[] floatValueArray = new float[]{0.0F, 1.0F, 3.0F, 6.0F, 8.0F, 12.0F};

    private ColorRoles() {
    }

    public static float method_181(int var0) {
        return floatValueArray[Math.clamp((long)var0, 0, floatValueArray.length - 1)];
    }

    public static int method_182(int var0, Palette var1) {
        switch (Math.clamp((long)var0, 0, 5)) {
            case 0:
                return var1.intValue32();
            case 1:
                return var1.intValue4();
            case 2:
                return var1.intValue21();
            case 3:
                return var1.intValue19();
            default:
                return var1.intValue11();
        }
    }
}
