package client.onyx.setting.impl;

import client.onyx.setting.Range;
import client.onyx.setting.ValueSetting;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class RangeSetting extends ValueSetting<Range> {
    private String string = "";
    private final double doubleValue;
    private final double doubleValue2;
    private final double doubleValue3;

    public static RangeSetting method_90(String var0, int var1, int var2, int var3, int var4) {
        return new RangeSetting(var0, var1, var2, var3, var4, 1.0);
    }

    public float method_91() {
        return (float)this.getValue().doubleValue();
    }

    public double method_92() {
        return this.getValue().doubleValue2();
    }

    public Range sanitize(Range var1) {
        return new Range(this.method_102(var1.doubleValue2()), this.method_102(var1.doubleValue()));
    }

    public <O> RangeSetting visibleWhen(ValueSetting<O> var1, Predicate<O> var2) {
        super.visibleWhen(var1, var2);
        return this;
    }

    public double getDoubleValue2() {
        return this.doubleValue2;
    }

    public RangeSetting describe(String var1) {
        super.describe(var1);
        return this;
    }

    public float method_93() {
        return (float)this.getValue().doubleValue2();
    }

    public RangeSetting method_94(String var1) {
        this.string = var1;
        return this;
    }

    public int method_95() {
        Range var10000 = this.getValue();
        int var3 = (int)Math.round(var10000.doubleValue2());
        int var2;
        return (var2 = (int)Math.round(var10000.doubleValue())) <= var3 ? var3 : ThreadLocalRandom.current().nextInt(var3, var2 + 1);
    }

    public String method_96() {
        if (this.method_107()) {
            return this.method_101() == this.method_99()
                ? this.method_101() + this.string
                : this.method_101() + this.string + " - " + this.method_99() + this.string;
        } else {
            String var3 = String.format("%.2f", this.method_92());
            String var2 = String.format("%.2f", this.method_106());
            boolean var10000 = var3.equals(var2);
            StringBuilder var10002;
            if (var10000) {
                var10002 = new StringBuilder();
                return var10002.insert(0, var3).append(this.string).toString();
            } else {
                var10002 = new StringBuilder();
                return var10002.insert(0, var3).append(this.string).append(" - ").append(var2).append(this.string).toString();
            }
        }
    }

    public double method_97(double var1) {
        return this.method_102(this.doubleValue + Math.clamp(var1, 0.0, 1.0) * (this.doubleValue2 - this.doubleValue));
    }

    public RangeSetting onChange(Consumer<Range> var1) {
        super.onChange(var1);
        return this;
    }

    public void method_98(double var1) {
        this.setValue(new Range(this.method_92(), Math.max(this.method_102(var1), this.method_92())));
    }

    public int method_99() {
        return (int)Math.round(this.getValue().doubleValue());
    }

    public void method_100(double var1) {
        this.setValue(new Range(Math.min(this.method_102(var1), this.method_106()), this.method_106()));
    }

    @Override
    public void load(JsonElement var1) {
        if (var1.isJsonObject()) {
            JsonObject var6 = var1.getAsJsonObject();
            JsonElement var4 = var6.get("min");
            JsonElement var2 = var6.get("max");
            if (var4 != null && var2 != null && var4.isJsonPrimitive() && var2.isJsonPrimitive()) {
                if (var4.getAsJsonPrimitive().isNumber() && var2.getAsJsonPrimitive().isNumber()) {
                    this.setValue(new Range(var4.getAsDouble(), var2.getAsDouble()));
                }
            }
        }
    }

    public int method_101() {
        return (int)Math.round(this.getValue().doubleValue2());
    }

    private double method_102(double var1) {
        var1 = Math.clamp(var1, this.doubleValue, this.doubleValue2);
        return this.doubleValue3 <= 0.0
            ? var1
            : Math.round(
                    Math.clamp(
                            this.doubleValue + Math.round((var1 - this.doubleValue) / this.doubleValue3) * this.doubleValue3,
                            this.doubleValue,
                            this.doubleValue2
                        )
                        * 1000000.0
                )
                / 1000000.0;
    }

    @Override
    public JsonElement save() {
        JsonObject var2 = new JsonObject();
        var2.addProperty("min", this.method_92());
        var2.addProperty("max", this.method_106());
        return var2;
    }

    public float method_103() {
        return (float)this.method_105();
    }

    public static RangeSetting method_104(String var0, double var1, double var3, double var5, double var7) {
        return new RangeSetting(var0, var1, var3, var5, var7, 0.01);
    }

    public double getDoubleValue() {
        return this.doubleValue;
    }

    public RangeSetting visibleWhen(ValueSetting<Boolean> var1) {
        super.visibleWhen(var1);
        return this;
    }

    public double method_105() {
        Range var2;
        return (var2 = this.getValue()).method_60() <= 0.0
            ? var2.doubleValue2()
            : ThreadLocalRandom.current().nextDouble(var2.doubleValue2(), var2.doubleValue());
    }

    public RangeSetting(String var1, double var2, double var4, double var6, double var8, double var10) {
        super(var1, new Range(var2, var4));
        this.doubleValue = var6;
        this.doubleValue2 = var8;
        this.doubleValue3 = var10;
        this.setValue(this.sanitize(this.getValue()));
    }

    public String getString() {
        return this.string;
    }

    public RangeSetting visibleWhen(BooleanSupplier var1) {
        super.visibleWhen(var1);
        return this;
    }

    public double method_106() {
        return this.getValue().doubleValue();
    }

    public boolean method_107() {
        return this.doubleValue3 >= 1.0 && this.doubleValue3 == Math.floor(this.doubleValue3);
    }

    public double getDoubleValue3() {
        return this.doubleValue3;
    }

    public double method_108(double var1) {
        return this.doubleValue2 == this.doubleValue ? 0.0 : (var1 - this.doubleValue) / (this.doubleValue2 - this.doubleValue);
    }
}
