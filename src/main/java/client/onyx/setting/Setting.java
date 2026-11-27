package client.onyx.setting;

import client.onyx.module.Module;
import client.onyx.util.IMinecraft;
import com.google.gson.JsonElement;
import java.util.List;
import java.util.function.BooleanSupplier;

public abstract class Setting implements IMinecraft {
    public static final BooleanSupplier booleanSupplier = () -> false;
    public static int order = 0;
    private final int intValue = order++;
    private String description = "";
    private BooleanSupplier booleanSupplier2 = () -> true;
    private final String name;
    private Module module;
    private Setting parent;

    public abstract JsonElement save();

    public void setParentEnabled(boolean var1) {
    }

    public void method_56() {
        if (this.module != null) {
            this.module.syncSettingsEnabled();
        }
    }

    public String getDescription() {
        return this.description;
    }

    public int getIntValue() {
        return this.intValue;
    }

    public Setting getParent() {
        return this.parent;
    }

    public String method_57() {
        return this.parent == null ? this.name : new StringBuilder().insert(0, this.parent.method_57()).append(".").append(this.name).toString();
    }

    public boolean method_58() {
        return this.booleanSupplier2 != booleanSupplier;
    }

    public Setting describe(String var1) {
        this.description = var1;
        return this;
    }

    public Setting visibleWhen(BooleanSupplier var1) {
        this.booleanSupplier2 = var1;
        return this;
    }

    public abstract void load(JsonElement var1);

    public boolean isVisible() {
        return this.booleanSupplier2.getAsBoolean();
    }

    public void attach(Module var1, Setting var2) {
        this.module = var1;
        this.parent = var2;
    }

    public Setting(String var1) {
        this.name = var1;
    }

    public List<Setting> getList() {
        return List.of();
    }

    public Module getModule() {
        return this.module;
    }

    public String getName() {
        return this.name;
    }
}
