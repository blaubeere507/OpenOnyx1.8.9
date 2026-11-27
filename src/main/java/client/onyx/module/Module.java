package client.onyx.module;

import client.onyx.Onyx;
import client.onyx.setting.Setting;
import client.onyx.setting.SettingHistory;
import client.onyx.setting.impl.EnumSetting;
import client.onyx.setting.impl.KeybindSetting;
import client.onyx.util.IMinecraft;
import client.onyx.util.Toggleable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Module implements IMinecraft, Toggleable {
    private final KeybindSetting bind = new KeybindSetting("Bind");
    private final EnumSetting<BindMode> bindMode = new EnumSetting<>("Bind Mode", BindMode.TOGGLE);
    private final List<Setting> settings = new ArrayList<>();
    private boolean enabled = false;
    private final String name;
    private final String description;
    private final Category category;

    public boolean hasBindMode() {
        return true;
    }

    public Category getCategory() {
        return this.category;
    }

    public boolean isListed() {
        return true;
    }

    private void collectSettings(Object var1, Class<?> var2) {
        var var3 = this;

        Class var7;
        for (Class var10 = var7 = var1.getClass(); var10 != null && var7 != var2; var10 = var7 = var7.getSuperclass()) {
            Field[] var4;
            int var5 = (var4 = var7.getDeclaredFields()).length;
            int var6 = 0;

            for (int var9 = 0; var9 < var5; var9 = ++var6) {
                Field var12 = var4[var6];
                if (Setting.class.isAssignableFrom(var12.getType()) && !Modifier.isStatic(var12.getModifiers()) && !Modifier.isTransient(var12.getModifiers())) {
                    try {
                        var12.setAccessible(true);
                        Object var8;
                        if ((var8 = var12.get(var1)) != null) {
                            var3.settings.add((Setting)var8);
                        }
                    } catch (IllegalAccessException var11) {
                        Onyx.LOGGER.error("Failed to read setting field '{}'", var12.getName(), var11);
                    }
                }
            }
        }
    }

    public EnumSetting<BindMode> getBindMode() {
        return this.bindMode;
    }

    public boolean isToggled() {
        return this.enabled;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean var1) {
        if (this.enabled != var1) {
            this.toggle();
        }
    }

    public Module(String var1, String var2, Category var3) {
        this.name = var1;
        this.description = var2;
        this.category = var3;
    }

    public void toggle() {
        SettingHistory.record(null, this::toggle, this::toggle);
        if (!this.enabled) {
            this.enabled = true;
            Onyx.EVENT_BUS.subscribe(this);
            this.onEnable();
            this.syncSettingsEnabled();
        } else {
            this.enabled = false;
            this.syncSettingsEnabled();
            this.onDisable();
            Onyx.EVENT_BUS.unsubscribe(this);
        }
    }

    public String getDescription() {
        return this.description;
    }

    public List<Setting> getVisibleSettings() {
        return this.settings.stream().filter(Setting::isVisible).toList();
    }

    public String getName() {
        return this.name;
    }

    public String getSuffix() {
        return null;
    }

    public void syncSettingsEnabled() {
        var var1 = this;

        Iterator var3;
        for (Iterator var2 = var3 = this.settings.iterator(); var2.hasNext(); var2 = var3) {
            ((Setting)var3.next()).setParentEnabled(var1.enabled);
        }
    }

    public KeybindSetting getBind() {
        return this.bind;
    }

    public List<Setting> getSettings() {
        return this.settings;
    }

    public void onDisable() {
    }

    public void initSettings() {
        var var3 = this;
        this.bind.attach(this, null);
        this.bindMode.attach(this, null);
        if (this.hasBindMode()) {
            this.bind.method_160(this.bindMode);
        }

        this.settings.clear();
        this.collectSettings(this, Module.class);
        this.settings.sort(Comparator.comparingInt(Setting::getIntValue));
        HashSet var1 = new HashSet();

        Iterator var2;
        for (Iterator var4 = var2 = this.settings.iterator(); var4.hasNext(); var4 = var2) {
            Setting var5 = (Setting)var2.next();
            if (!var1.add(var5.getName())) {
                Onyx.LOGGER.warn("Duplicate setting name '{}' in module '{}'", var5.getName(), var3.name);
            }

            var5.attach(var3, null);
        }
    }

    public void onEnable() {
    }
}
