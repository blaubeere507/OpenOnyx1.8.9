package client.onyx.setting;

public record Range(double doubleValue2, double doubleValue) {
    public Range(double doubleValue2, double doubleValue) {
        if (doubleValue2 > doubleValue) {
            double var10000 = doubleValue2;
            doubleValue2 = doubleValue;
            doubleValue = var10000;
        }

        this.doubleValue2 = doubleValue2;
        this.doubleValue = doubleValue;
    }

    public boolean method_59(double var1) {
        return var1 >= this.doubleValue2 && var1 <= this.doubleValue;
    }

    public double method_60() {
        return this.doubleValue - this.doubleValue2;
    }
}
