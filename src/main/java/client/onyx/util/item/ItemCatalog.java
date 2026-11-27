package client.onyx.util.item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public final class ItemCatalog {
    private static List<ItemCatalog.Entry> list;

    private ItemCatalog() {
    }

    private static List<ItemCatalog.Entry> method_1984() {
        ArrayList var0 = new ArrayList();
        Iterator var1 = Item.itemRegistry.iterator();

        label40:
        while (true) {
            Iterator var7 = var1;

            while (var7.hasNext()) {
                Item var2;
                if ((var2 = (Item)var1.next()) == null) {
                    var7 = var1;
                } else {
                    ResourceLocation var3;
                    if ((var3 = Item.itemRegistry.getNameForObject(var2)) != null) {
                        String var4 = var3.toString();
                        Iterator var9 = method_1987(var2).iterator();

                        while (true) {
                            Iterator var8 = var9;

                            while (true) {
                                if (!var8.hasNext()) {
                                    continue label40;
                                }

                                ItemStack var5;
                                if ((var5 = (ItemStack)var9.next()) == null) {
                                    break;
                                }

                                if (var5.getItem() == null) {
                                    var8 = var9;
                                } else {
                                    String var6 = method_1986(var5, var3.getResourcePath());
                                    var0.add(
                                        new ItemCatalog.Entry(
                                            var5, var4, var6, new StringBuilder().insert(0, var4).append(' ').append(var6).toString().toLowerCase(Locale.ROOT)
                                        )
                                    );
                                    var8 = var9;
                                }
                            }
                        }
                    }

                    var7 = var1;
                }
            }

            return var0;
        }
    }

    public static List<ItemCatalog.Entry> method_1985(String var0, int var1) {
        ArrayList var8 = new ArrayList();
        String var6;
        if (var0 == null) {
            var6 = "";
        } else {
            var6 = var0.trim().toLowerCase(Locale.ROOT);
        }

        String var4 = var6;
        Iterator var3 = method_1988().iterator();

        label32:
        while (true) {
            for (Iterator var7 = var3; var7.hasNext(); var7 = var3) {
                ItemCatalog.Entry var5 = (ItemCatalog.Entry)var3.next();
                if (var4.isEmpty() || var5.string3().contains(var4)) {
                    var8.add(var5);
                    if (var1 > 0 && var8.size() >= var1) {
                        break label32;
                    }
                    continue label32;
                }
            }

            return var8;
        }

        return var8;
    }

    private static String method_1986(ItemStack var0, String var1) {
        ItemStack var2 = var0;

        try {
            String var4;
            if ((var4 = var2.getDisplayName()) != null && !var4.isEmpty()) {
                return var4;
            }
        } catch (RuntimeException var3) {
        }

        return var1;
    }

    private static List<ItemStack> method_1987(Item var0) {
        Item var1 = var0;
        ArrayList var3 = new ArrayList();

        ArrayList var10000;
        label17: {
            try {
                var1.getSubItems(var1, null, var3);
            } catch (RuntimeException var2) {
                var10000 = var3;
                var3.clear();
                break label17;
            }

            var10000 = var3;
        }

        if (var10000.isEmpty()) {
            var3.add(new ItemStack(var0));
        }

        return var3;
    }

    public static List<ItemCatalog.Entry> method_1988() {
        if (list == null) {
            list = method_1984();
        }

        return list;
    }

    public record Entry(ItemStack itemStack, String string, String string2, String string3) {
    }
}
