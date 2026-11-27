package client.onyx.setting.impl;

import client.onyx.setting.ValueSetting;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class EnumSetSetting<E extends Enum<E>> extends ValueSetting<EnumSet<E>> {
    private boolean flag = true;
    private final Class<E> class_;
    private final List<E> list;

    public EnumSetSetting<E> method_77(boolean var1) {
        this.flag = var1;
        return this;
    }

    @Override
    public void load(JsonElement var1) {
        var var9 = this;
        if (var1.isJsonArray()) {
            EnumSet var2 = EnumSet.noneOf(this.class_);
            Iterator var8 = var1.getAsJsonArray().iterator();

            label37:
            while (true) {
                for (Iterator var6 = var8; var6.hasNext(); var6 = var8) {
                    JsonElement var3;
                    if (!(var3 = (JsonElement)var8.next()).isJsonPrimitive()) {
                        continue label37;
                    }

                    if (var3.getAsJsonPrimitive().isString()) {
                        String var10 = var3.getAsString();
                        Iterator var4 = var9.list.iterator();

                        while (var4.hasNext()) {
                            Enum var5;
                            if ((var5 = (Enum)var4.next()).name().equals(var10)) {
                                var2.add((E)var5);
                            }
                        }
                        continue label37;
                    }
                }

                var9.setValue(var2);
                return;
            }
        }
    }

    public void method_78(E var1, boolean var2) {
        if (this.method_79((E)var1) != var2) {
            this.method_81((E)var1);
        }
    }

    public <O> EnumSetSetting<E> visibleWhen(ValueSetting<O> var1, Predicate<O> var2) {
        super.visibleWhen(var1, var2);
        return this;
    }

    public boolean method_79(E var1) {
        return this.getValue().contains(var1);
    }

    public EnumSetSetting<E> visibleWhen(BooleanSupplier var1) {
        super.visibleWhen(var1);
        return this;
    }

    public boolean method_80() {
        return this.getValue().isEmpty();
    }

    public Class<E> getClass_() {
        return this.class_;
    }

    public EnumSetSetting<E> onChange(Consumer<EnumSet<E>> var1) {
        super.onChange(var1);
        return this;
    }

    public void method_81(E var1) {
        EnumSet var3;
        if (!(var3 = EnumSet.copyOf(this.getValue())).remove(var1)) {
            var3.add((E)var1);
        }

        this.setValue(var3);
    }

    public EnumSet<E> sanitize(EnumSet<E> var1) {
        return !this.flag && var1.isEmpty() && this.getValue() != null ? this.getValue() : EnumSet.copyOf(var1);
    }

    @Override
    public JsonElement save() {
        JsonArray var1 = new JsonArray();

        Iterator var5;
        for (Iterator var4 = var5 = this.getValue().iterator(); var4.hasNext(); var4 = var5) {
            Enum var3 = (Enum)var5.next();
            var1.add(new JsonPrimitive(var3.name()));
        }

        return var1;
    }

    public boolean isFlag() {
        return this.flag;
    }

    @SafeVarargs
    public EnumSetSetting(String var1, Class<E> var2, E... var3) {
        super(var1, var3.length == 0 ? EnumSet.noneOf(var2) : EnumSet.copyOf(List.of((E[])var3)));
        this.class_ = var2;
        this.list = List.of((E[])var2.getEnumConstants());
    }

    public List<E> getList2() {
        return this.list;
    }

    public EnumSetSetting<E> visibleWhen(ValueSetting<Boolean> var1) {
        super.visibleWhen(var1);
        return this;
    }

    public EnumSetSetting<E> describe(String var1) {
        super.describe(var1);
        return this;
    }
}
