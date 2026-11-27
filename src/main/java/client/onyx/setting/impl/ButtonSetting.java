package client.onyx.setting.impl;

import client.onyx.setting.Setting;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import java.util.function.BooleanSupplier;

public class ButtonSetting extends Setting {
    private final Runnable runnable;
    private String string;

    public ButtonSetting method_82(String var1) {
        this.string = var1;
        return this;
    }

    @Override
    public JsonElement save() {
        return JsonNull.INSTANCE;
    }

    public ButtonSetting describe(String var1) {
        super.describe(var1);
        return this;
    }

    public ButtonSetting visibleWhen(BooleanSupplier var1) {
        super.visibleWhen(var1);
        return this;
    }

    public String getString() {
        return this.string;
    }

    public ButtonSetting(String var1, Runnable var2) {
        super(var1);
        this.runnable = var2;
        this.string = var1;
    }

    @Override
    public void load(JsonElement var1) {
    }

    public void method_83() {
        this.runnable.run();
    }
}
