package client.onyx.setting.impl;

import client.onyx.setting.NamedEnum;
import client.onyx.setting.ValueSetting;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.Iterator;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class EnumSetting<E extends Enum<E>> extends ValueSetting<E> {
    private final List<E> list;

    @SafeVarargs
    public EnumSetting(String var1, E var2, E... var3) {
        super(var1, (E)var2);
        this.list = List.of((E[])var3);
        if (!this.list.contains(var2)) {
            throw new IllegalArgumentException(
                new StringBuilder().insert(0, "Default '").append(var2).append("' is not offered by '").append(var1).append("'").toString()
            );
        }
    }

    public <O> EnumSetting<E> visibleWhen(ValueSetting<O> var1, Predicate<O> var2) {
        super.visibleWhen(var1, var2);
        return this;
    }

    public static String method_167(Enum<?> var0) {
        if (var0 instanceof NamedEnum) {
            NamedEnum var8;
            return (var8 = (NamedEnum)var0).getDisplayName();
        } else {
            StringBuilder var7 = new StringBuilder(var0.name().length());
            String[] var5;
            int var4 = (var5 = var0.name().toLowerCase().split("_")).length;
            int var3 = 0;

            for (int var6 = 0; var6 < var4; var6 = ++var3) {
                String var2;
                if (!(var2 = var5[var3]).isEmpty()) {
                    StringBuilder var10000;
                    if (var7.isEmpty()) {
                        var7.append(Character.toUpperCase(var2.charAt(0)));
                        var10000 = var7;
                    } else {
                        var7.append(' ').append(var2.charAt(0));
                        var10000 = var7;
                    }

                    var10000.append(var2, 1, var2.length());
                }
            }

            return var7.toString();
        }
    }

    @Override
    public JsonElement save() {
        return new JsonPrimitive(this.getValue().name());
    }

    public EnumSetting(String var1, E var2) {
        super(var1, (E)var2);
        this.list = List.of((E[])var2.getDeclaringClass().getEnumConstants());
    }

    public EnumSetting<E> describe(String var1) {
        super.describe(var1);
        return this;
    }

    @Override
    public void load(JsonElement var1) {
        if (var1.isJsonPrimitive() && var1.getAsJsonPrimitive().isString()) {
            String var5 = var1.getAsString();
            Iterator var2 = this.list.iterator();

            while (var2.hasNext()) {
                Enum var3;
                if ((var3 = (Enum)var2.next()).name().equals(var5)) {
                    this.setValue((E)var3);
                    return;
                }
            }
        }
    }

    public EnumSetting<E> onChange(Consumer<E> var1) {
        super.onChange(var1);
        return this;
    }

    public List<E> getList2() {
        return this.list;
    }

    public EnumSetting<E> visibleWhen(BooleanSupplier var1) {
        super.visibleWhen(var1);
        return this;
    }

    public boolean method_168(E var1) {
        return this.getValue() == var1;
    }

    public void method_169() {
        this.setValue(this.list.get((this.list.indexOf(this.getValue()) + 1) % this.list.size()));
    }

    public EnumSetting<E> visibleWhen(ValueSetting<Boolean> var1) {
        super.visibleWhen(var1);
        return this;
    }
}
