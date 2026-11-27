package client.onyx.setting.impl;

import client.onyx.setting.ValueSetting;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class BooleanSetting extends ValueSetting<Boolean> {
    public BooleanSetting visibleWhen(ValueSetting<Boolean> var1) {
        super.visibleWhen(var1);
        return this;
    }

    public BooleanSetting(String var1, boolean var2) {
        super(var1, var2);
    }

    public void method_145() {
        BooleanSetting var2;
        boolean var3;
        if (this.getValue()) {
            var3 = false;
            var2 = this;
        } else {
            var3 = true;
            var2 = this;
        }

        var2.setValue(var3);
    }

    @Override
    public void load(JsonElement var1) {
        if (var1.isJsonPrimitive() && var1.getAsJsonPrimitive().isBoolean()) {
            this.setValue(var1.getAsBoolean());
        }
    }

    @Override
    public JsonElement save() {
        return new JsonPrimitive(this.getValue());
    }

    public BooleanSetting visibleWhen(BooleanSupplier var1) {
        super.visibleWhen(var1);
        return this;
    }

    public BooleanSetting onChange(Consumer<Boolean> var1) {
        super.onChange(var1);
        return this;
    }

    public BooleanSetting describe(String var1) {
        super.describe(var1);
        return this;
    }

    public boolean method_146() {
        return this.getValue();
    }

    public <O> BooleanSetting visibleWhen(ValueSetting<O> var1, Predicate<O> var2) {
        super.visibleWhen(var1, var2);
        return this;
    }
}
