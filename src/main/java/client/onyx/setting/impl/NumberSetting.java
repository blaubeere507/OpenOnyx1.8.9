package client.onyx.setting.impl;

import client.onyx.setting.ValueSetting;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class NumberSetting extends ValueSetting<Double> {
    private String string = "";
    private final double doubleValue2;
    private final double doubleValue;
    private final double doubleValue3;

    public NumberSetting method_147(String var1) {
        this.string = var1;
        return this;
    }

    public Double sanitize(Double var1) {
        double var2 = Math.clamp(var1, this.doubleValue2, this.doubleValue);
        return this.doubleValue3 <= 0.0
            ? var2
            : Math.round(
                    Math.clamp(
                            this.doubleValue2 + Math.round((var2 - this.doubleValue2) / this.doubleValue3) * this.doubleValue3,
                            this.doubleValue2,
                            this.doubleValue
                        )
                        * 1000000.0
                )
                / 1000000.0;
    }

    public String getString() {
        return this.string;
    }

    public void method_148(double var1) {
        this.setValue(this.doubleValue2 + Math.clamp(var1, 0.0, 1.0) * (this.doubleValue - this.doubleValue2));
    }

    public <O> NumberSetting visibleWhen(ValueSetting<O> var1, Predicate<O> var2) {
        super.visibleWhen(var1, var2);
        return this;
    }

    public double getDoubleValue2() {
        return this.doubleValue2;
    }

    public double getDoubleValue() {
        return this.doubleValue;
    }

    public int method_149() {
        return (int)Math.round(this.getValue());
    }

    public NumberSetting visibleWhen(ValueSetting<Boolean> var1) {
        super.visibleWhen(var1);
        return this;
    }

    public NumberSetting visibleWhen(BooleanSupplier var1) {
        super.visibleWhen(var1);
        return this;
    }

    public String method_150() {
        return (this.method_153() ? String.valueOf(this.method_149()) : String.format("%.2f", this.getValue())) + this.string;
    }

    public float method_151() {
        return this.getValue().floatValue();
    }

    public double method_152() {
        return this.doubleValue == this.doubleValue2 ? 0.0 : (this.getValue() - this.doubleValue2) / (this.doubleValue - this.doubleValue2);
    }

    @Override
    public void load(JsonElement var1) {
        if (var1.isJsonPrimitive() && var1.getAsJsonPrimitive().isNumber()) {
            this.setValue(var1.getAsDouble());
        }
    }

    public NumberSetting(String var1, double var2, double var4, double var6, double var8) {
        super(var1, var2);
        this.doubleValue2 = var4;
        this.doubleValue = var6;
        this.doubleValue3 = var8;
        this.setValue(this.sanitize(var2));
    }

    public boolean method_153() {
        return this.doubleValue3 >= 1.0 && this.doubleValue3 == Math.floor(this.doubleValue3);
    }

    @Override
    public JsonElement save() {
        return new JsonPrimitive(this.getValue());
    }

    public NumberSetting onChange(Consumer<Double> var1) {
        super.onChange(var1);
        return this;
    }

    public NumberSetting describe(String var1) {
        super.describe(var1);
        return this;
    }

    public double getDoubleValue3() {
        return this.doubleValue3;
    }
}
