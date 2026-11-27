package client.onyx.render.world;

public record WingParams(
    float floatValue10,
    float floatValue4,
    float floatValue11,
    float floatValue8,
    float floatValue2,
    float floatValue,
    float floatValue5,
    float floatValue6,
    float floatValue12,
    float floatValue16,
    float floatValue15,
    float floatValue3,
    float floatValue13,
    float floatValue7,
    float floatValue9,
    float floatValue14
) {
    private static float method_853(float var0, float var1, float var2) {
        return var1 + (var2 - var1) * var0;
    }

    public static WingParams method_854(WingParams var0, WingParams var1, float var2) {
        if (var2 <= 0.0F) {
            return var0;
        } else {
            return var2 >= 1.0F
                ? var1
                : new WingParams(
                    method_853(var2, var0.floatValue10, var1.floatValue10),
                    method_853(var2, var0.floatValue4, var1.floatValue4),
                    method_853(var2, var0.floatValue11, var1.floatValue11),
                    method_853(var2, var0.floatValue8, var1.floatValue8),
                    method_853(var2, var0.floatValue2, var1.floatValue2),
                    method_853(var2, var0.floatValue, var1.floatValue),
                    method_853(var2, var0.floatValue5, var1.floatValue5),
                    method_853(var2, var0.floatValue6, var1.floatValue6),
                    method_853(var2, var0.floatValue12, var1.floatValue12),
                    method_853(var2, var0.floatValue16, var1.floatValue16),
                    method_853(var2, var0.floatValue15, var1.floatValue15),
                    method_853(var2, var0.floatValue3, var1.floatValue3),
                    method_853(var2, var0.floatValue13, var1.floatValue13),
                    method_853(var2, var0.floatValue7, var1.floatValue7),
                    method_853(var2, var0.floatValue9, var1.floatValue9),
                    method_853(var2, var0.floatValue14, var1.floatValue14)
                );
        }
    }
}
