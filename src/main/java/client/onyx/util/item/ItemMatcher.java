package client.onyx.util.item;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public final class ItemMatcher {
    public static final int INT_N11 = -1;
    public static final char CHAR_641 = '@';
    public static final char CHAR_1261 = '~';
    public static final char CHAR_351 = '#';
    private final ItemMatcher.Kind kind;
    private final Item item;
    private final int intValue;
    private final ItemMatcher.ItemGroup itemGroup;
    private final String string;
    private final String string2;

    public static ItemMatcher method_2001(ItemMatcher.ItemGroup var0) {
        return var0 == null ? null : new ItemMatcher(ItemMatcher.Kind.GROUP, null, -1, var0, null);
    }

    private ItemMatcher(ItemMatcher.Kind var1, Item var2, int var3, ItemMatcher.ItemGroup var4, String var5) {
        this.kind = var1;
        this.item = var2;
        this.intValue = var3;
        this.itemGroup = var4;
        this.string = var5;
        this.string2 = this.method_2006();
    }

    public boolean method_2002() {
        return this.kind == ItemMatcher.Kind.ITEM;
    }

    private ItemStack method_2003() {
        var var2 = this;
        Iterator var1 = Item.itemRegistry.iterator();

        while (var1.hasNext()) {
            Item var3;
            if ((var3 = (Item)var1.next()) != null && method_2007(var3).contains(var2.string)) {
                return new ItemStack(var3);
            }
        }

        return null;
    }

    public static ItemMatcher method_2004(String var0) {
        if (var0 == null) {
            return null;
        } else if ((var0 = var0.trim()).isEmpty()) {
            return null;
        } else if (var0.charAt(0) == '@') {
            try {
                return method_2001(ItemMatcher.ItemGroup.valueOf(var0.substring(1).toUpperCase(Locale.ROOT)));
            } catch (IllegalArgumentException var6) {
                return null;
            }
        } else if (var0.charAt(0) == '~') {
            return method_2008(var0.substring(1));
        } else {
            int var2 = var0.indexOf(35);
            int var4 = -1;
            if (var2 >= 0) {
                try {
                    var4 = Integer.parseInt(var0.substring(var2 + 1).trim());
                } catch (NumberFormatException var7) {
                    return null;
                }

                var0 = var0.substring(0, var2).trim();
            }

            ResourceLocation var3 = new ResourceLocation(var0);
            return !Item.itemRegistry.containsKey(var3) ? null : method_2005(Item.itemRegistry.getObject(var3), var4);
        }
    }

    public static ItemMatcher method_2005(Item var0, int var1) {
        return var0 == null ? null : new ItemMatcher(ItemMatcher.Kind.ITEM, var0, var1, null, null);
    }

    private String method_2006() {
        switch (this.kind) {
            case ITEM:
                if (this.intValue == -1) {
                    return method_2013(this.item);
                }

                return new StringBuilder().insert(0, method_2013(this.item)).append('#').append(this.intValue).toString();
            case GROUP:
                return '@' + this.itemGroup.name();
            case NAME:
                return '~' + this.string;
            default:
                throw new MatchException(null, null);
        }
    }

    @Override
    public String toString() {
        return this.string2;
    }

    private static String method_2007(Item var0) {
        ResourceLocation var2;
        return (var2 = Item.itemRegistry.getNameForObject(var0)) == null ? "" : var2.getResourcePath();
    }

    public static ItemMatcher method_2008(String var0) {
        if (var0 == null) {
            return null;
        } else {
            String var2;
            return (var2 = var0.trim().toLowerCase(Locale.ROOT)).isEmpty() ? null : new ItemMatcher(ItemMatcher.Kind.NAME, null, -1, null, var2);
        }
    }

    private String method_2009() {
        var var1 = this;

        try {
            String var3;
            if ((var3 = new ItemStack(var1.item, 1, var1.intValue == -1 ? 0 : var1.intValue).getDisplayName()) != null && !var3.isEmpty()) {
                return var3;
            }
        } catch (RuntimeException var2) {
        }

        return method_2007(this.item);
    }

    public ItemStack method_2010() {
        switch (this.kind) {
            case ITEM:
                return new ItemStack(this.item, 1, this.intValue == -1 ? 0 : this.intValue);
            case GROUP:
                return this.itemGroup.method_1881();
            case NAME:
                return this.method_2003();
            default:
                throw new MatchException(null, null);
        }
    }

    public static ItemMatcher method_2011(ItemStack var0) {
        return var0 != null && var0.getItem() != null ? method_2005(var0.getItem(), var0.getMetadata()) : null;
    }

    @Override
    public int hashCode() {
        return this.string2.hashCode();
    }

    public String method_2012() {
        switch (this.kind) {
            case ITEM:
                String var2 = this.method_2009();
                if (this.intValue == -1) {
                    return var2;
                }

                return new StringBuilder().insert(0, var2).append(" #").append(this.intValue).toString();
            case GROUP:
                return this.itemGroup.getString();
            case NAME:
                return new StringBuilder().insert(0, "*").append(this.string).append("*").toString();
            default:
                throw new MatchException(null, null);
        }
    }

    public static String method_2013(Item var0) {
        return String.valueOf(Item.itemRegistry.getNameForObject(var0));
    }

    public boolean method_2014(ItemStack var1) {
        if (var1 != null && var1.getItem() != null) {
            switch (this.kind) {
                case ITEM:
                    return var1.getItem() == this.item && (this.intValue == -1 || var1.getMetadata() == this.intValue);
                case GROUP:
                    return this.itemGroup.method_1882(var1);
                case NAME:
                    return method_2007(var1.getItem()).contains(this.string);
                default:
                    throw new MatchException(null, null);
            }
        } else {
            return false;
        }
    }

    public String getString2() {
        return this.string2;
    }

    @Override
    public boolean equals(Object var1) {
        return var1 instanceof ItemMatcher var3 && this.string2.equals(var3.string2);
    }

    public static enum ItemGroup {
        SWORD("Swords"),
        AXE("Axes"),
        PICKAXE("Pickaxes"),
        SHOVEL("Shovels"),
        HOE("Hoes"),
        TOOL("Any tool"),
        ARMOR("Armor"),
        BLOCK("Blocks"),
        FOOD("Food"),
        POTION("Potions"),
        BAD_POTION("Harmful potions"),
        ANY("Anything");

        private final String string;

        public String getString() {
            return this.string;
        }

        public boolean method_1880(Item var1) {
            switch (this) {
                case SWORD:
                    return var1 instanceof ItemSword;
                case AXE:
                    return var1 instanceof ItemAxe;
                case PICKAXE:
                    return var1 instanceof ItemPickaxe;
                case SHOVEL:
                    return var1 instanceof ItemSpade;
                case HOE:
                    return var1 instanceof ItemHoe;
                case TOOL:
                    if (!(var1 instanceof ItemTool) && !(var1 instanceof ItemHoe) && !(var1 instanceof ItemShears)) {
                        return false;
                    }

                    return true;
                case ARMOR:
                    return var1 instanceof ItemArmor;
                case BLOCK:
                    return var1 instanceof ItemBlock;
                case FOOD:
                    return var1 instanceof ItemFood;
                case POTION:
                    return var1 instanceof ItemPotion;
                case BAD_POTION:
                    return false;
                case ANY:
                    return true;
                default:
                    throw new MatchException(null, null);
            }
        }

        public ItemStack method_1881() {
            Object var2 = switch (this) {
                case SWORD -> Items.iron_sword;
                case AXE -> Items.iron_axe;
                case PICKAXE -> Items.iron_pickaxe;
                case SHOVEL -> Items.iron_shovel;
                case HOE -> Items.iron_hoe;
                case TOOL -> Items.iron_pickaxe;
                case ARMOR -> Items.iron_chestplate;
                case BLOCK -> Item.getItemFromBlock(Blocks.stone);
                case FOOD -> Items.bread;
                case POTION, BAD_POTION -> Items.potionitem;
                case ANY -> Items.book;
            };
            return var2 == null ? null : new ItemStack((Item)var2);
        }

        private ItemGroup(String var3) {
            this.string = var3;
        }

        public boolean method_1882(ItemStack var1) {
            if (var1 == null || var1.getItem() == null) {
                return false;
            } else {
                return this == BAD_POTION ? method_1883(var1) : this.method_1880(var1.getItem());
            }
        }

        private static boolean method_1883(ItemStack var0) {
            Item var4;
            if (!((var4 = var0.getItem()) instanceof ItemPotion)) {
                return false;
            } else {
                ItemPotion var1;
                ItemPotion var3 = var1 = (ItemPotion)var4;
                List var5;
                if ((var5 = var3.getEffects(var0)) == null) {
                    return false;
                } else {
                    Iterator var7 = var5.iterator();

                    while (var7.hasNext()) {
                        int var6;
                        if ((var6 = ((PotionEffect)var7.next()).getPotionID()) == Potion.moveSlowdown.id
                            || var6 == Potion.blindness.id
                            || var6 == Potion.poison.id
                            || var6 == Potion.digSlowdown.id
                            || var6 == Potion.weakness.id
                            || var6 == Potion.harm.id) {
                            return true;
                        }
                    }

                    return false;
                }
            }
        }
    }

    private static enum Kind {
        ITEM,
        GROUP,
        NAME;
    }
}
