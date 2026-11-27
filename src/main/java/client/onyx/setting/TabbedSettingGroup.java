package client.onyx.setting;

import java.util.List;

public class TabbedSettingGroup extends SettingGroup {
    private final List<String> list2;

    public List<String> getList2() {
        return this.list2;
    }

    public TabbedSettingGroup(String var1, String... var2) {
        super(var1);
        this.list2 = List.of(var2);
    }
}
