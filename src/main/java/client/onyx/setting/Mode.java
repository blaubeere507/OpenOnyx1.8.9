package client.onyx.setting;

import java.util.function.BooleanSupplier;

public class Mode extends SettingGroup {
    private transient ModeSetting<?> modeSetting;

    public Mode describe(String var1) {
        super.describe(var1);
        return this;
    }

    public boolean method_74() {
        return this.modeSetting != null && this.modeSetting.getValue() == this;
    }

    public void method_75(ModeSetting<?> var1) {
        this.modeSetting = var1;
    }

    public Mode(String var1) {
        super(var1);
    }

    public void method_76() {
        if (this.modeSetting != null) {
            this.modeSetting.method_69(this);
        }
    }

    public Mode visibleWhen(BooleanSupplier var1) {
        super.visibleWhen(var1);
        return this;
    }
}
