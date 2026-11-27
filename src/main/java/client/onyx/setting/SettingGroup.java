package client.onyx.setting;

import client.onyx.Onyx;
import client.onyx.module.Module;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.util.Toggleable;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.BooleanSupplier;

public class SettingGroup extends Setting implements Toggleable {
    private final List<Setting> list = new ArrayList<>();
    private final BooleanSetting enabled;
    private boolean flag;

    public boolean method_61() {
        return this.enabled == null || this.enabled.getValue();
    }

    public SettingGroup(String var1, boolean var2) {
        super(var1);
        this.enabled = new BooleanSetting("Enabled", var2);
        this.enabled.onChange(var1x -> this.method_56());
    }

    public SettingGroup(String var1) {
        super(var1);
        this.enabled = null;
    }

    public void method_62() {
    }

    private void method_63(Object var1, Class<?> var2) {
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
                            var3.list.add((Setting)var8);
                        }
                    } catch (IllegalAccessException var11) {
                        Onyx.LOGGER.error("Failed to read setting field '{}'", var12.getName(), var11);
                    }
                }
            }
        }
    }

    @Override
    public void load(JsonElement var1) {
        if (this.enabled != null && var1.isJsonPrimitive() && var1.getAsJsonPrimitive().isBoolean()) {
            this.enabled.load(var1);
        } else if (var1.isJsonObject()) {
            JsonObject var5 = var1.getAsJsonObject();
            if (this.enabled != null) {
                method_67(this.enabled, var5);
            }

            Iterator var2;
            for (Iterator var3 = var2 = this.list.iterator(); var3.hasNext(); var3 = var2) {
                method_67((Setting)var2.next(), var5);
            }
        }
    }

    public SettingGroup describe(String var1) {
        super.describe(var1);
        return this;
    }

    @Override
    public void setParentEnabled(boolean var1) {
        boolean var7; // declared by DeclareMissing
        boolean var3;
        if (var1 && this.method_61()) {
            var3 = true;
        } else {
            var3 = false;
        }

        SettingGroup var4;
        label27: {
            var7 = var3;
            if (var3 != this.flag) {
                if (this.flag = var3) {
                    Onyx.EVENT_BUS.subscribe(this);
                    this.method_62();
                    var4 = this;
                    break label27;
                }

                Onyx.EVENT_BUS.unsubscribe(this);
                this.method_64();
            }

            var4 = this;
        }

        Iterator var2;
        for (Iterator var5 = var2 = var4.list.iterator(); var5.hasNext(); var5 = var2) {
            ((Setting)var2.next()).setParentEnabled(var7);
        }
    }

    public BooleanSetting getEnabled() {
        return this.enabled;
    }

    public void method_64() {
    }

    public SettingGroup visibleWhen(BooleanSupplier var1) {
        super.visibleWhen(var1);
        return this;
    }

    @Override
    public JsonElement save() {
        JsonObject var1 = new JsonObject();
        if (this.enabled != null) {
            var1.add(this.enabled.getName(), this.enabled.save());
        }

        Iterator var5;
        Iterator var4 = var5 = this.list.iterator();

        while (var4.hasNext()) {
            Setting var3;
            if (!(var3 = (Setting)var5.next()).method_58()) {
                var4 = var5;
            } else {
                var1.add(var3.getName(), var3.save());
                var4 = var5;
            }
        }

        return var1;
    }

    @Override
    public List<Setting> getList() {
        return this.list;
    }

    public List<Setting> method_65() {
        return this.list.stream().filter(Setting::isVisible).toList();
    }

    public boolean method_66() {
        return this.enabled != null;
    }

    @Override
    public boolean isEnabled() {
        return this.flag;
    }

    public static void method_67(Setting var0, JsonObject var1) {
        Setting var2 = var0;
        if (var0.method_58()) {
            JsonElement var6;
            if ((var6 = var1.get(var0.getName())) != null && !var6.isJsonNull()) {
                try {
                    var2.load(var6);
                } catch (RuntimeException var5) {
                    Onyx.LOGGER.warn("Failed to load setting '{}'", var0.method_57(), var5);
                }
            } else {
                if (var0 instanceof SettingGroup) {
                    SettingGroup var3;
                    if ((var3 = (SettingGroup)var0).getEnabled() != null) {
                        method_67(var3.getEnabled(), var1);
                    }

                    Iterator var7;
                    for (Iterator var4 = var7 = var3.getList().iterator(); var4.hasNext(); var4 = var7) {
                        method_67((Setting)var7.next(), var1);
                    }
                }
            }
        }
    }

    @Override
    public void attach(Module var1, Setting var2) {
        var var3 = this;
        super.attach(var1, var2);
        if (this.enabled != null) {
            this.enabled.attach(var1, this);
        }

        this.list.clear();
        this.method_63(this, SettingGroup.class);
        this.list.sort(Comparator.comparingInt(Setting::getIntValue));
        HashSet var7 = new HashSet();

        Iterator var4;
        for (Iterator var5 = var4 = this.list.iterator(); var5.hasNext(); var5 = var4) {
            Setting var6 = (Setting)var4.next();
            if (!var7.add(var6.getName())) {
                Onyx.LOGGER.warn("Duplicate setting name '{}' in group '{}'", var6.getName(), var3.method_57());
            }

            var6.attach(var1, var3);
        }
    }
}
