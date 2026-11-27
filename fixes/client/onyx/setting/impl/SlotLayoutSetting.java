package client.onyx.setting.impl;

import client.onyx.setting.ValueSetting;
import client.onyx.util.item.ItemMatcher;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.item.ItemStack;

public class SlotLayoutSetting extends ValueSetting<Map<Integer, List<ItemMatcher>>> {
    public static final int INT_91 = 9;

    public void method_129() {
        this.setValue(new LinkedHashMap<>());
    }

    public SlotLayoutSetting describe(String var1) {
        super.describe(var1);
        return this;
    }

    public void method_130(int var1, ItemMatcher var2) {
        if (var2 != null && this.method_135(var1).contains(var2)) {
            List var4;
            Map<Integer, List<ItemMatcher>> var5;
            if ((var4 = (List)(var5 = this.method_134()).get(var1)) != null) {
                var4.remove(var2);
                if (var4.isEmpty()) {
                    var5.remove(var1);
                }

                this.setValue(var5);
            }
        }
    }

    public void method_131(int var1) {
        if (!this.method_135(var1).isEmpty()) {
            Map<Integer, List<ItemMatcher>> var3;
            (var3 = this.method_134()).remove(var1);
            this.setValue(var3);
        }
    }

    public int method_132(ItemStack var1) {
        var var3 = this;
        int var2 = -1;
        int var8 = 0;

        label34:
        for (int var7 = 0; var7 < 9; var7 = ++var8) {
            Iterator var4 = var3.method_135(var8).iterator();

            while (true) {
                Iterator var6 = var4;

                while (true) {
                    if (!var6.hasNext()) {
                        continue label34;
                    }

                    ItemMatcher var5;
                    if ((var5 = (ItemMatcher)var4.next()).method_2014(var1)) {
                        if (var5.method_2002()) {
                            return var8;
                        }

                        if (var2 < 0) {
                            var2 = var8;
                        }
                        break;
                    }

                    var6 = var4;
                }
            }
        }

        return var2;
    }

    public boolean method_133(int var1, ItemStack var2) {
        Iterator var4 = this.method_135(var1).iterator();

        while (var4.hasNext()) {
            if (((ItemMatcher)var4.next()).method_2014(var2)) {
                return true;
            }
        }

        return false;
    }

    private Map<Integer, List<ItemMatcher>> method_134() {
        LinkedHashMap<Integer, List<ItemMatcher>> var1 = new LinkedHashMap<>();

        Iterator var2;
        for (Iterator var4 = var2 = this.getValue().entrySet().iterator(); var4.hasNext(); var4 = var2) {
            Entry var5 = (Entry)var2.next();
            var1.put((Integer)var5.getKey(), new ArrayList((Collection)var5.getValue()));
        }

        return var1;
    }

    public SlotLayoutSetting onChange(Consumer<Map<Integer, List<ItemMatcher>>> var1) {
        super.onChange(var1);
        return this;
    }

    public Map<Integer, List<ItemMatcher>> sanitize(Map<Integer, List<ItemMatcher>> var1) {
        LinkedHashMap var5 = new LinkedHashMap();
        if (var1 == null) {
            return var5;
        } else {
            int var4 = 0;

            for (int var7 = 0; var7 < 9; var7 = ++var4) {
                List var8;
                if ((var8 = (List)var1.get(var4)) != null) {
                    ArrayList var2 = new ArrayList();
                    Iterator var9 = var8.iterator();

                    while (var9.hasNext()) {
                        ItemMatcher var6;
                        if ((var6 = (ItemMatcher)var9.next()) != null && !var2.contains(var6)) {
                            var2.add(var6);
                        }
                    }

                    if (!var2.isEmpty()) {
                        var5.put(var4, var2);
                    }
                }
            }

            return var5;
        }
    }

    public List<ItemMatcher> method_135(int var1) {
        List var3;
        return (var3 = this.getValue().get(var1)) == null ? List.of() : var3;
    }

    public void method_136(int var1, ItemMatcher var2) {
        if (var2 != null && var1 >= 0 && var1 < 9) {
            if (!this.method_135(var1).contains(var2)) {
                Map<Integer, List<ItemMatcher>> var3;
                (var3 = this.method_134()).computeIfAbsent(var1, var0 -> new ArrayList<>()).add(var2);
                this.setValue(var3);
            }
        }
    }

    @Override
    public void load(JsonElement var1) {
        if (var1.isJsonObject()) {
            LinkedHashMap var6 = new LinkedHashMap();
            Iterator var13 = var1.getAsJsonObject().entrySet().iterator();

            label65:
            while (true) {
                Iterator var8 = var13;

                while (var8.hasNext()) {
                    Entry var11 = (Entry)var13.next();

                    int var4;
                    try {
                        var4 = Integer.parseInt((String)var11.getKey());
                    } catch (NumberFormatException var10) {
                        var8 = var13;
                        continue;
                    }

                    if (var4 < 0 || var4 >= 9) {
                        continue label65;
                    }

                    if (((JsonElement)var11.getValue()).isJsonArray()) {
                        ArrayList var5 = new ArrayList();
                        Iterator var12 = ((JsonElement)var11.getValue()).getAsJsonArray().iterator();

                        label54:
                        while (true) {
                            for (Iterator var9 = var12; var9.hasNext(); var9 = var12) {
                                JsonElement var2;
                                if (!(var2 = (JsonElement)var12.next()).isJsonPrimitive()) {
                                    continue label54;
                                }

                                if (var2.getAsJsonPrimitive().isString()) {
                                    ItemMatcher var14;
                                    if ((var14 = ItemMatcher.method_2004(var2.getAsString())) != null && !var5.contains(var14)) {
                                        var5.add(var14);
                                    }
                                    continue label54;
                                }
                            }

                            if (!var5.isEmpty()) {
                                var6.put(var4, var5);
                            }
                            continue label65;
                        }
                    }

                    var8 = var13;
                }

                this.setValue(var6);
                return;
            }
        }
    }

    public boolean method_137() {
        return this.getValue().isEmpty();
    }

    public static Map<Integer, List<ItemMatcher>> method_138(Object... var0) {
        Object[] var3 = var0;
        LinkedHashMap<Integer, List<ItemMatcher>> var1 = new LinkedHashMap<>();
        byte var2 = 0;

        for (byte var5 = 0; var5 + 1 < var3.length; var5 = var2) {
            int var6 = (Integer)var3[var2];
            ItemMatcher var4;
            if ((var4 = ItemMatcher.method_2004(String.valueOf(var3[var2 + 1]))) != null && var6 >= 0 && var6 < 9) {
                var1.computeIfAbsent(var6, var0x -> new ArrayList<>()).add(var4);
            }

            var2 += 2;
        }

        return var1;
    }

    public SlotLayoutSetting visibleWhen(ValueSetting<Boolean> var1) {
        super.visibleWhen(var1);
        return this;
    }

    public <O> SlotLayoutSetting visibleWhen(ValueSetting<O> var1, Predicate<O> var2) {
        super.visibleWhen(var1, var2);
        return this;
    }

    public SlotLayoutSetting visibleWhen(BooleanSupplier var1) {
        super.visibleWhen(var1);
        return this;
    }

    public SlotLayoutSetting(String var1, Map<Integer, List<ItemMatcher>> var2) {
        super(var1, var2);
    }

    @Override
    public JsonElement save() {
        var var3 = this;
        JsonObject var1 = new JsonObject();
        int var2 = 0;

        for (int var7 = 0; var7 < 9; var7 = ++var2) {
            List var8;
            if (!(var8 = var3.method_135(var2)).isEmpty()) {
                JsonArray var4 = new JsonArray();

                Iterator var9;
                for (Iterator var6 = var9 = var8.iterator(); var6.hasNext(); var6 = var9) {
                    ItemMatcher var5 = (ItemMatcher)var9.next();
                    var4.add(new JsonPrimitive(var5.getString2()));
                }

                var1.add(String.valueOf(var2), var4);
            }
        }

        return var1;
    }

    public void method_139(int var1, int var2, ItemMatcher var3) {
        if (var3 != null && var1 != var2 && var2 >= 0 && var2 < 9) {
            if (this.method_135(var1).contains(var3)) {
                Map<Integer, List<ItemMatcher>> var4;
                List var5;
                if ((var5 = (List)(var4 = this.method_134()).get(var1)) != null) {
                    var5.remove(var3);
                    if (var5.isEmpty()) {
                        var4.remove(var1);
                    }
                }

                List var7;
                if (!(var7 = var4.computeIfAbsent(var2, var0 -> new ArrayList<>())).contains(var3)) {
                    var7.add(var3);
                }

                this.setValue(var4);
            }
        }
    }
}
