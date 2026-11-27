package client.onyx.ui.theme.color;

import java.util.HashMap;
import java.util.Map;

public final class TonalPalette {
    private final Map<Integer, Integer> map = new HashMap<>();
    private final double doubleValue;
    private final double doubleValue2;

    public Hct method_192(double var1) {
        return Hct.method_185(this.doubleValue, this.doubleValue2, var1);
    }

    public static TonalPalette method_193(double var0, double var2) {
        return new TonalPalette(var0, var2);
    }

    public static TonalPalette method_194(int var0) {
        Hct var2 = Hct.method_188(var0);
        return new TonalPalette(var2.getDoubleValue(), var2.getDoubleValue2());
    }

    public int method_195(double var1) {
        return Hct.method_185(this.doubleValue, this.doubleValue2, var1).getIntValue();
    }

    private TonalPalette(double var1, double var3) {
        this.doubleValue = var1;
        this.doubleValue2 = var3;
    }

    public double getDoubleValue2() {
        return this.doubleValue2;
    }

    public Map<Integer, Integer> getMap() {
        return this.map;
    }

    public int method_196(int var1) {
        return this.map.computeIfAbsent(var1, var1x -> Hct.method_185(this.doubleValue, this.doubleValue2, var1x.intValue()).getIntValue());
    }

    public double getDoubleValue() {
        return this.doubleValue;
    }
}
