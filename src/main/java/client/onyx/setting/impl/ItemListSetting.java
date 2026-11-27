package client.onyx.setting.impl;

import client.onyx.setting.ValueSetting;
import client.onyx.util.item.ItemMatcher;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.item.ItemStack;

public class ItemListSetting extends ValueSetting<List<ItemMatcher>> {
    public ItemListSetting describe(String var1) {
        super.describe(var1);
        return this;
    }

    public void method_109(ItemMatcher var1) {
        if (!this.method_114(var1)) {
            this.method_111(var1);
        } else {
            this.method_113(var1);
        }
    }

    public ItemListSetting visibleWhen(ValueSetting<Boolean> var1) {
        super.visibleWhen(var1);
        return this;
    }

    public ItemListSetting(String var1, String... var2) {
        super(var1, method_112(var2));
    }

    public boolean method_110(ItemStack var1) {
        Iterator var3 = this.getValue().iterator();

        while (var3.hasNext()) {
            if (((ItemMatcher)var3.next()).method_2014(var1)) {
                return true;
            }
        }

        return false;
    }

    public void method_111(ItemMatcher var1) {
        if (var1 != null && !this.method_114(var1)) {
            ArrayList var2;
            (var2 = new ArrayList<>(this.getValue())).add(var1);
            this.setValue(var2);
        }
    }

    private static List<ItemMatcher> method_112(String... var0) {
        ArrayList var7 = new ArrayList();
        String[] var5 = var0;
        int var4 = var0.length;
        int var3 = 0;

        for (int var6 = 0; var6 < var4; var6 = ++var3) {
            ItemMatcher var2;
            if ((var2 = ItemMatcher.method_2004(var5[var3])) != null && !var7.contains(var2)) {
                var7.add(var2);
            }
        }

        return var7;
    }

    public ItemListSetting visibleWhen(BooleanSupplier var1) {
        super.visibleWhen(var1);
        return this;
    }

    public ItemListSetting onChange(Consumer<List<ItemMatcher>> var1) {
        super.onChange(var1);
        return this;
    }

    @Override
    public JsonElement save() {
        JsonArray var1 = new JsonArray();

        Iterator var5;
        for (Iterator var4 = var5 = this.getValue().iterator(); var4.hasNext(); var4 = var5) {
            ItemMatcher var3 = (ItemMatcher)var5.next();
            var1.add(new JsonPrimitive(var3.getString2()));
        }

        return var1;
    }

    public List<ItemMatcher> sanitize(List<ItemMatcher> var1) {
        ArrayList var4 = new ArrayList();
        if (var1 == null) {
            return var4;
        } else {
            Iterator var5 = var1.iterator();

            while (var5.hasNext()) {
                ItemMatcher var3;
                if ((var3 = (ItemMatcher)var5.next()) != null && !var4.contains(var3)) {
                    var4.add(var3);
                }
            }

            return var4;
        }
    }

    public <O> ItemListSetting visibleWhen(ValueSetting<O> var1, Predicate<O> var2) {
        super.visibleWhen(var1, var2);
        return this;
    }

    @Override
    public void load(JsonElement var1) {
        if (var1.isJsonArray()) {
            ArrayList var2 = new ArrayList();
            Iterator var7 = var1.getAsJsonArray().iterator();

            label31:
            while (true) {
                for (Iterator var4 = var7; var4.hasNext(); var4 = var7) {
                    JsonElement var5;
                    if (!(var5 = (JsonElement)var7.next()).isJsonPrimitive()) {
                        continue label31;
                    }

                    if (var5.getAsJsonPrimitive().isString()) {
                        ItemMatcher var6;
                        if ((var6 = ItemMatcher.method_2004(var5.getAsString())) != null && !var2.contains(var6)) {
                            var2.add(var6);
                        }
                        continue label31;
                    }
                }

                this.setValue(var2);
                return;
            }
        }
    }

    public void method_113(ItemMatcher var1) {
        if (var1 != null && this.method_114(var1)) {
            ArrayList var2;
            (var2 = new ArrayList<>(this.getValue())).remove(var1);
            this.setValue(var2);
        }
    }

    public boolean method_114(ItemMatcher var1) {
        return this.getValue().contains(var1);
    }

    public void method_115() {
        this.setValue(new ArrayList<>());
    }
}
