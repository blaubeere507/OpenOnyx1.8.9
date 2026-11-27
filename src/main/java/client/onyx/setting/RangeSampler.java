package client.onyx.setting;

import client.onyx.setting.impl.RangeSetting;

public class RangeSampler {
    private final RangeSetting rangeSetting;
    private final boolean flag;
    private double doubleValue;

    public void method_53() {
        RangeSampler var3;
        double var4;
        if (!this.flag) {
            var4 = this.rangeSetting.method_105();
            var3 = this;
        } else {
            var4 = this.rangeSetting.method_95();
            var3 = this;
        }

        var3.doubleValue = var4;
    }

    public RangeSampler(RangeSetting var1) {
        this.rangeSetting = var1;
        this.flag = var1.method_107();
        var1.onChange(var1x -> this.method_53());
        this.method_53();
    }

    public int method_54() {
        return (int)Math.round(this.doubleValue);
    }

    public float method_55() {
        return (float)this.doubleValue;
    }

    public double getDoubleValue() {
        return this.doubleValue;
    }
}
