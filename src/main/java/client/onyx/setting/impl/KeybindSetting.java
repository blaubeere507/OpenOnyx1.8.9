package client.onyx.setting.impl;

import client.onyx.module.BindMode;
import client.onyx.setting.ValueSetting;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class KeybindSetting extends ValueSetting<Integer> {
    public static final int INT_01 = 0;
    private EnumSetting<BindMode> enumSetting;

    public boolean method_154(int var1) {
        return this.method_163() && this.getValue() < 0 && this.getValue() == -var1 - 1;
    }

    public KeybindSetting visibleWhen(BooleanSupplier var1) {
        super.visibleWhen(var1);
        return this;
    }

    public boolean method_155(int var1) {
        return this.method_163() && this.getValue() == var1;
    }

    public <O> KeybindSetting visibleWhen(ValueSetting<O> var1, Predicate<O> var2) {
        super.visibleWhen(var1, var2);
        return this;
    }

    public int method_156() {
        return this.method_165() ? -this.getValue() - 1 : -1;
    }

    @Override
    public JsonElement save() {
        return new JsonPrimitive(this.getValue());
    }

    public KeybindSetting onChange(Consumer<Integer> var1) {
        super.onChange(var1);
        return this;
    }

    public boolean method_157() {
        return this.method_162() ? Keyboard.isKeyDown(this.method_159()) : this.method_165() && Mouse.isButtonDown(this.method_156());
    }

    public void method_158() {
        this.setValue(0);
    }

    public int method_159() {
        return this.method_162() ? this.getValue() : 0;
    }

    public KeybindSetting(String var1, int var2) {
        super(var1, var2);
    }

    public KeybindSetting method_160(EnumSetting<BindMode> var1) {
        this.enumSetting = var1;
        return this;
    }

    public KeybindSetting describe(String var1) {
        super.describe(var1);
        return this;
    }

    public EnumSetting<BindMode> getEnumSetting() {
        return this.enumSetting;
    }

    public void method_161(int var1) {
        if (var1 == 0) {
            this.method_158();
        } else {
            if (var1 > 0) {
                this.setValue(var1);
            }
        }
    }

    public boolean method_162() {
        return this.method_163() && this.getValue() > 0;
    }

    public boolean method_163() {
        return this.getValue() != 0;
    }

    public String method_164() {
        if (!this.method_163()) {
            return "None";
        } else if (this.method_162()) {
            String var3;
            return (var3 = Keyboard.getKeyName(this.getValue())) == null ? "None" : var3;
        } else {
            int var2;
            switch (var2 = this.method_156()) {
                case 0:
                    return "LMB";
                case 1:
                    return "RMB";
                case 2:
                    return "MMB";
                case 3:
                    return "Mouse 4";
                case 4:
                    return "Mouse 5";
                default:
                    return new StringBuilder().insert(0, "Mouse ").append(var2 + 1).toString();
            }
        }
    }

    public KeybindSetting(String var1) {
        this(var1, 0);
        boolean var10002 = false;
    }

    public boolean method_165() {
        return this.method_163() && this.getValue() < 0;
    }

    @Override
    public void load(JsonElement var1) {
        if (var1.isJsonPrimitive() && var1.getAsJsonPrimitive().isNumber()) {
            this.setValue(var1.getAsInt());
        }
    }

    public KeybindSetting visibleWhen(ValueSetting<Boolean> var1) {
        super.visibleWhen(var1);
        return this;
    }

    public void method_166(int var1) {
        if (var1 >= 0) {
            this.setValue(-var1 - 1);
        }
    }
}
