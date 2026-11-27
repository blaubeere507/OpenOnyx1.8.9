package client.onyx.setting.impl;

import client.onyx.setting.ValueSetting;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

public class BlockListSetting extends ValueSetting<Set<Block>> {
    public static final Comparator<Block> comparator = Comparator.comparing(BlockListSetting::method_86);

    @Override
    public void load(JsonElement var1) {
        if (var1.isJsonArray()) {
            TreeSet var2 = new TreeSet<>(comparator);
            Iterator var6 = var1.getAsJsonArray().iterator();

            label27:
            while (true) {
                Iterator var4 = var6;

                while (var4.hasNext()) {
                    JsonElement var3;
                    if (!(var3 = (JsonElement)var6.next()).isJsonPrimitive()) {
                        continue label27;
                    }

                    if (!var3.getAsJsonPrimitive().isString()) {
                        var4 = var6;
                    } else {
                        ResourceLocation var8 = new ResourceLocation(var3.getAsString());
                        if (!Block.blockRegistry.containsKey(var8)) {
                            var4 = var6;
                        } else {
                            var2.add(Block.blockRegistry.getObject(var8));
                            var4 = var6;
                        }
                    }
                }

                this.setValue(var2);
                return;
            }
        }
    }

    public void method_84(Block var1) {
        if (!this.method_85(var1)) {
            this.method_89(var1);
        } else {
            this.method_88(var1);
        }
    }

    @Override
    public JsonElement save() {
        JsonArray var1 = new JsonArray();

        Iterator var5;
        for (Iterator var4 = var5 = this.getValue().iterator(); var4.hasNext(); var4 = var5) {
            Block var3 = (Block)var5.next();
            var1.add(new JsonPrimitive(method_86(var3)));
        }

        return var1;
    }

    public boolean method_85(Block var1) {
        return this.getValue().contains(var1);
    }

    public BlockListSetting describe(String var1) {
        super.describe(var1);
        return this;
    }

    public <O> BlockListSetting visibleWhen(ValueSetting<O> var1, Predicate<O> var2) {
        super.visibleWhen(var1, var2);
        return this;
    }

    public Set<Block> sanitize(Set<Block> var1) {
        return method_87(var1);
    }

    public static String method_86(Block var0) {
        return String.valueOf(Block.blockRegistry.getNameForObject(var0));
    }

    public BlockListSetting onChange(Consumer<Set<Block>> var1) {
        super.onChange(var1);
        return this;
    }

    public BlockListSetting visibleWhen(ValueSetting<Boolean> var1) {
        super.visibleWhen(var1);
        return this;
    }

    public static Set<Block> method_87(Iterable<Block> var0) {
        TreeSet var1 = new TreeSet<>(comparator);
        Iterator var4 = var0.iterator();

        while (var4.hasNext()) {
            Block var3;
            if ((var3 = (Block)var4.next()) != null) {
                var1.add(var3);
            }
        }

        return var1;
    }

    public void method_88(Block var1) {
        TreeSet var3;
        (var3 = new TreeSet<>(comparator)).addAll(this.getValue());
        var3.remove(var1);
        this.setValue(var3);
    }

    public BlockListSetting(String var1, Block... var2) {
        super(var1, method_87(List.of(var2)));
    }

    public BlockListSetting visibleWhen(BooleanSupplier var1) {
        super.visibleWhen(var1);
        return this;
    }

    public void method_89(Block var1) {
        TreeSet var3;
        (var3 = new TreeSet<>(comparator)).addAll(this.getValue());
        var3.add(var1);
        this.setValue(var3);
    }
}
