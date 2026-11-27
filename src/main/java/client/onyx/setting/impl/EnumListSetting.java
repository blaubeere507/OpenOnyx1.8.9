package client.onyx.setting.impl;

import client.onyx.setting.ValueSetting;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class EnumListSetting<E extends Enum<E>> extends ValueSetting<List<E>> {
    private final Class<E> class_;
    private final List<E> list;

    @SafeVarargs
    public EnumListSetting(String var1, Class<E> var2, E... var3) {
        super(var1, List.of((E[])var3));
        this.class_ = var2;
        this.list = List.of((E[])var2.getEnumConstants());
    }

    public EnumListSetting<E> visibleWhen(ValueSetting<Boolean> var1) {
        super.visibleWhen(var1);
        return this;
    }

    public boolean method_140() {
        return this.getValue().isEmpty();
    }

    public Class<E> getClass_() {
        return this.class_;
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

    public void method_141(E var1) {
        ArrayList var3;
        if (!(var3 = new ArrayList<>(this.getValue())).remove(var1)) {
            var3.add(var1);
        }

        this.setValue(var3);
    }

    public void method_142(E var1, boolean var2) {
        if (this.method_143((E)var1) != var2) {
            this.method_141((E)var1);
        }
    }

    @Override
    public void load(JsonElement var1) {
        var var9 = this;
        if (var1.isJsonArray()) {
            ArrayList var2 = new ArrayList();
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
                                var2.add(var5);
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

    public boolean method_143(E var1) {
        return this.getValue().contains(var1);
    }

    public List<E> getList2() {
        return this.list;
    }

    public EnumListSetting<E> visibleWhen(BooleanSupplier var1) {
        super.visibleWhen(var1);
        return this;
    }

    public void method_144(int var1, int var2) {
        List var4 = this.getValue();
        if (var1 >= 0 && var1 < var4.size()) {
            if ((var2 = Math.clamp((long)var2, 0, var4.size() - 1)) != var1) {
                ArrayList var5;
                Enum var6 = (Enum)(var5 = new ArrayList<>(var4)).remove(var1);
                var5.add(var2, var6);
                this.setValue(var5);
            }
        }
    }

    public <O> EnumListSetting<E> visibleWhen(ValueSetting<O> var1, Predicate<O> var2) {
        super.visibleWhen(var1, var2);
        return this;
    }

    public List<E> sanitize(List<E> var1) {
        ArrayList var4 = new ArrayList();
        Iterator var5 = var1.iterator();

        while (var5.hasNext()) {
            Enum var3;
            if ((var3 = (Enum)var5.next()) != null && !var4.contains(var3)) {
                var4.add(var3);
            }
        }

        return List.copyOf(var4);
    }

    public EnumListSetting<E> describe(String var1) {
        super.describe(var1);
        return this;
    }

    public EnumListSetting<E> onChange(Consumer<List<E>> var1) {
        super.onChange(var1);
        return this;
    }
}
