package client.onyx.util.geometry;

public enum LineBounds {
    UNBOUNDED(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY),
    FORWARD(0.0, Double.POSITIVE_INFINITY),
    SEGMENT_01(0.0, 1.0);

    private static final double[] doubleValueArray = new double[0];
    private static final double[] doubleValueArray3 = new double[]{0.0};
    private static final double[] doubleValueArray2 = new double[]{0.0, 1.0};
    private final double doubleValue2;
    private final double doubleValue;

    public double getDoubleValue() {
        return this.doubleValue;
    }

    public double method_2057(double var1) {
        if (!Double.isFinite(var1)) {
            return Double.NaN;
        } else {
            return !(var1 < this.doubleValue2 - 1.0E-9) && !(var1 > this.doubleValue + 1.0E-9) ? this.method_2060(var1) : Double.NaN;
        }
    }

    public double[] method_2058() {
        switch (this) {
            case UNBOUNDED:
                return doubleValueArray;
            case FORWARD:
                return doubleValueArray3;
            case SEGMENT_01:
                return doubleValueArray2;
            default:
                throw new MatchException(null, null);
        }
    }

    private LineBounds(double var3, double var5) {
        this.doubleValue2 = var3;
        this.doubleValue = var5;
    }

    public double method_2059(double var1) {
        return !Double.isFinite(var1) ? Double.NaN : this.method_2060(var1);
    }

    private double method_2060(double var1) {
        if (var1 < this.doubleValue2) {
            return this.doubleValue2;
        } else {
            return var1 > this.doubleValue ? this.doubleValue : var1;
        }
    }

    public double getDoubleValue2() {
        return this.doubleValue2;
    }
}
