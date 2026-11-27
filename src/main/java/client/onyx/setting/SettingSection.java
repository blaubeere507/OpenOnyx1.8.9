package client.onyx.setting;

import java.util.function.BooleanSupplier;

public class SettingSection extends SettingGroup {
    public SettingSection(String var1) {
        this(var1, false);
        boolean var10002 = false;
    }

    public SettingSection(String var1, boolean var2) {
        super(var1, var2);
    }

    public SettingSection visibleWhen(BooleanSupplier var1) {
        super.visibleWhen(var1);
        return this;
    }

    public SettingSection describe(String var1) {
        super.describe(var1);
        return this;
    }
}
