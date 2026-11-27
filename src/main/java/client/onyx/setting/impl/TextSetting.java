package client.onyx.setting.impl;

import client.onyx.setting.ValueSetting;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class TextSetting extends ValueSetting<String> {
    public TextSetting visibleWhen(BooleanSupplier var1) {
        super.visibleWhen(var1);
        return this;
    }

    @Override
    public JsonElement save() {
        return new JsonPrimitive(this.getValue());
    }

    public TextSetting onChange(Consumer<String> var1) {
        super.onChange(var1);
        return this;
    }

    public TextSetting visibleWhen(ValueSetting<Boolean> var1) {
        super.visibleWhen(var1);
        return this;
    }

    public TextSetting describe(String var1) {
        super.describe(var1);
        return this;
    }

    public TextSetting(String var1, String var2) {
        super(var1, var2);
    }

    public <O> TextSetting visibleWhen(ValueSetting<O> var1, Predicate<O> var2) {
        super.visibleWhen(var1, var2);
        return this;
    }

    public String sanitize(String var1) {
        return var1 == null ? "" : var1;
    }

    @Override
    public void load(JsonElement var1) {
        if (var1.isJsonPrimitive() && var1.getAsJsonPrimitive().isString()) {
            this.setValue(var1.getAsString());
        }
    }
}
