package client.onyx.ui.theme;

public final class Spacing {
    public static final float FLOAT_32_01 = 32.0F;
    public static final float FLOAT_12_01 = 12.0F;
    public static final float FLOAT_28_01 = 28.0F;
    public static final float FLOAT_0_01 = 0.0F;
    public static final float FLOAT_48_01 = 48.0F;
    public static final float FLOAT_4_01 = 4.0F;
    public static final float FLOAT_16_01 = 16.0F;
    public static final float FLOAT_8_01 = 8.0F;
    public static final float FLOAT_20_01 = 20.0F;
    public static final float FLOAT_3_4028235E381 = Float.MAX_VALUE;

    private Spacing() {
    }

    public static float method_173(float var0, float var1, float var2) {
        return Math.min(var0, Math.min(var1, var2) / 2.0F);
    }
}
