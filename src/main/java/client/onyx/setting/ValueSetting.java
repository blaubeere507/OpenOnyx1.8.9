package client.onyx.setting;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class ValueSetting<T> extends Setting {
    private Consumer<T> onChange = var0 -> {};
    private final T defaultValue;
    private T value;

    public ValueSetting<T> onChange(Consumer<T> var1) {
        this.onChange = var1;
        return this;
    }

    public void setValue(T var1) {
        Object var4;
        if (!(var4 = this.sanitize(var1)).equals(this.value)) {
            Object var2 = this.value;
            this.value = (T)var4;
            SettingHistory.record(this, () -> this.setValue((T)var2), () -> this.setValue((T)var4));
            this.onChange.accept((T)var4);
        }
    }

    public T getDefaultValue() {
        return this.defaultValue;
    }

    public T getValue() {
        return this.value;
    }

    public ValueSetting<T> describe(String var1) {
        super.describe(var1);
        return this;
    }

    public ValueSetting(String var1, T var2) {
        super(var1);
        this.defaultValue = (T)var2;
        this.value = (T)var2;
    }

    public <O> ValueSetting<T> visibleWhen(ValueSetting<O> var1, Predicate<O> var2) {
        return this.visibleWhen(() -> var2.test(var1.getValue()));
    }

    public ValueSetting<T> visibleWhen(ValueSetting<Boolean> var1) {
        return this.visibleWhen(var1::getValue);
    }

    public ValueSetting<T> visibleWhen(BooleanSupplier var1) {
        super.visibleWhen(var1);
        return this;
    }

    public boolean isDefault() {
        return this.value.equals(this.defaultValue);
    }

    public T sanitize(T var1) {
        return (T)var1;
    }

    public void reset() {
        this.setValue(this.defaultValue);
    }
}
