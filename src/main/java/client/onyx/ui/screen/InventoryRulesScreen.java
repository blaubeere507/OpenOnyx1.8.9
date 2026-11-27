package client.onyx.ui.screen;

import client.onyx.Onyx;
import client.onyx.input.KeyTyped;
import client.onyx.input.MouseClick;
import client.onyx.module.player.InventoryManager;
import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easings;
import client.onyx.render.font.FontStyle;
import client.onyx.render.ui.UiRenderer;
import client.onyx.setting.impl.ItemListSetting;
import client.onyx.ui.clickgui.ModuleRow;
import client.onyx.ui.component.Button;
import client.onyx.ui.component.Icons;
import client.onyx.ui.component.RenderContext;
import client.onyx.ui.component.SearchField;
import client.onyx.ui.component.TextField;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.Typography;
import client.onyx.util.item.ItemCatalog;
import client.onyx.util.item.ItemMatcher;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class InventoryRulesScreen extends GuiScreen {
    private static final float FLOAT_4_01 = 4.0F;
    private static final float FLOAT_49_01 = 49.0F;
    private static final float FLOAT_116_01 = 116.0F;
    private static final float FLOAT_260_01 = 260.0F;
    private static final float FLOAT_28_01 = 28.0F;
    private static final int INT_4001 = 400;
    private static final float FLOAT_13_01 = 13.0F;
    private static final float FLOAT_18_01 = 18.0F;
    private static final float FLOAT_32_01 = 32.0F;
    private static final float FLOAT_18_02 = 18.0F;
    private static final float FLOAT_4_02 = 4.0F;
    private static final float FLOAT_20_01 = 20.0F;
    private static final float FLOAT_97_01 = 97.0F;
    private static final float FLOAT_100_01 = 100.0F;
    private static final float FLOAT_430_01 = 430.0F;
    private static final float FLOAT_3_01 = 3.0F;
    private static final int INT_11 = 1;
    private static final float FLOAT_5_01 = 5.0F;
    private static final int INT_21 = 2;
    private static final float FLOAT_13_02 = 13.0F;
    private static final float FLOAT_2_01 = 2.0F;
    private static final float FLOAT_560_01 = 560.0F;
    private static final float FLOAT_5_02 = 5.0F;
    private static final float FLOAT_26_01 = 26.0F;
    private static final float FLOAT_8_01 = 8.0F;
    private static final float FLOAT_18_03 = 18.0F;
    private static final float FLOAT_22_01 = 22.0F;
    private static final float FLOAT_8_02 = 8.0F;
    private static final float FLOAT_16_01 = 16.0F;
    private static final float FLOAT_3_02 = 3.0F;
    private static final float FLOAT_92_01 = 92.0F;
    private static final int INT_01 = 0;
    private static final float FLOAT_16_02 = 16.0F;
    private static final float FLOAT_20_02 = 20.0F;
    private static final float FLOAT_9_01 = 9.0F;
    private static final float FLOAT_7_01 = 7.0F;
    private static final float FLOAT_38_01 = 38.0F;
    private static final float FLOAT_120_01 = 120.0F;
    private final InventoryManager inventoryManager;
    private final UiRenderer uiRenderer;
    private final SearchField searchField;
    private final ModuleRow moduleRow;
    private final Button button3;
    private final Button button;
    private final Button button2;
    private final List<InventoryRulesScreen.Rule> list;
    private final List<InventoryRulesScreen.Rule> list4;
    private final List<InventoryRulesScreen.Rule> list3;
    private final List<InventoryRulesScreen.Rule> list2;
    private final Animation[] animationArray;
    private List<ItemCatalog.Entry> list5;
    private int intValue;
    private int intValue3;
    private final GuiScreen guiScreen;
    private boolean flag;
    private float floatValue;
    private float floatValue2;
    private float floatValue3;
    private long longValue;
    private float floatValue4;
    private float floatValue5;
    private float floatValue6;
    private ItemMatcher itemMatcher;
    private int intValue2;
    private float floatValue7;
    private float floatValue8;
    private float floatValue9;
    private float floatValue10;
    private float floatValue11;
    private float floatValue12;
    private float floatValue13;
    private float floatValue14;
    private float floatValue15;
    private float floatValue16;
    private float floatValue17;
    private String string;

    private void method_2230() {
        this.floatValue2 = Math.min(560.0F, this.width - 40.0F);
        this.floatValue = Math.min(430.0F, this.height - 40.0F);
        this.floatValue12 = (this.width - this.floatValue2) / 2.0F;
        this.floatValue5 = (this.height - this.floatValue) / 2.0F;
        this.floatValue14 = this.floatValue12 + 18.0F;
        this.floatValue9 = this.floatValue2 - 36.0F;
        float var3 = Math.min(260.0F, this.floatValue9);
        float var2 = this.floatValue5 + 42.0F;
        this.moduleRow.method_2258(this.floatValue12 + (this.floatValue2 - var3) / 2.0F, var2, var3, 30.0F);
        this.floatValue13 = var2 + 30.0F + 14.0F;
        float var4 = this.floatValue13 + 97.0F + 10.0F;
        this.searchField.method_2258(this.floatValue14, var4, this.floatValue9, 40.0F);
        this.floatValue8 = var4 + 40.0F + 8.0F;
        float var5;
        this.floatValue15 = (var5 = this.floatValue5 + this.floatValue - 18.0F - 30.0F) - 12.0F;
        var2 = 292.0F;
        var2 = this.floatValue12 + this.floatValue2 - 18.0F - var2;
        this.button3.method_2258(var2, var5, 92.0F, 30.0F);
        this.button.method_2258(var2 + 92.0F + 8.0F, var5, 92.0F, 30.0F);
        this.button2.method_2258(var2 + 200.0F, var5, 92.0F, 30.0F);
    }

    private void method_2231(RenderContext var1) {
        this.list3.clear();
        this.list2.clear();
        float var2;
        if (!((var2 = this.floatValue15 - this.floatValue8) <= 0.0F)) {
            this.uiRenderer.method_443(this.floatValue14, this.floatValue8, this.floatValue9, var2);
            float var4;
            float var5 = this.method_2234(var1, this.method_2253(var1, var4 = this.floatValue8 - this.floatValue17) + 8.0F);
            this.uiRenderer.method_486();
            this.floatValue3 = Math.max(0.0F, var5 + this.floatValue17 - this.floatValue15);
            this.floatValue17 = Math.min(this.floatValue17, this.floatValue3);
            this.method_2233(this.floatValue8, var2, this.floatValue17, this.floatValue3);
        }
    }

    private String method_2232(ItemMatcher var1) {
        return this.uiRenderer.method_469(Typography.fontStyle16, var1.method_2012(), 116.0F);
    }

    private void method_2233(float var1, float var2, float var3, float var4) {
        if (!(var4 <= 0.0F) && !(var2 <= 0.0F)) {
            float var8 = var2 + var4;
            float var9 = Math.max(18.0F, var2 * var2 / var8);
            float var6 = var2 - var9;
            float var7 = this.floatValue14 + this.floatValue9 - 3.0F;
            this.uiRenderer.method_479(var7, var1, 3.0F, var2, Float.MAX_VALUE, Theme.method_180(Theme.getPalette().intValue12(), 0.35F));
            this.uiRenderer.method_479(var7, var1 + var6 * (var3 / var4), 3.0F, var9, Float.MAX_VALUE, Theme.method_180(Theme.getPalette().intValue7(), 0.7F));
        }
    }

    private float method_2234(RenderContext var1, float var2) {
        var var3 = this;
        int var7 = Math.max(1, (int)((this.floatValue9 - 7.0F + 4.0F) / 32.0F));
        float var4 = var2;
        int var5 = 0;

        for (int var14 = 0; var14 < var3.list5.size(); var14 = ++var5) {
            int var6 = var5 % var7;
            int var15 = var5 / var7;
            float var17 = var3.floatValue14 + 32.0F * var6;
            float var16;
            var4 = (var16 = var2 + 32.0F * var15) + 28.0F;
            ItemCatalog.Entry var8;
            ItemMatcher var9;
            if (!(var16 + 28.0F < var3.floatValue8)
                && !(var16 > var3.floatValue15)
                && (var9 = ItemMatcher.method_2011((var8 = var3.list5.get(var5)).itemStack())) != null) {
                boolean var10 = var3.method_2252(var9);
                boolean var12;
                if (var1.floatValue3 >= var17
                    && var1.floatValue3 < var17 + 28.0F
                    && var1.floatValue2 >= var16
                    && var1.floatValue2 < var16 + 28.0F
                    && var1.floatValue2 >= var3.floatValue8
                    && var1.floatValue2 < var3.floatValue15) {
                    var12 = true;
                } else {
                    var12 = false;
                }

                int var13;
                if (var10) {
                    var13 = Theme.getPalette().intValue34();
                } else if (var12) {
                    var13 = Theme.getPalette().intValue11();
                } else {
                    var13 = Theme.getPalette().intValue19();
                }

                var3.uiRenderer.method_479(var17, var16, 28.0F, 28.0F, 4.0F, var13);
                var3.method_2247(var8.itemStack(), var17 + 5.0F, var16 + 5.0F, 18.0F);
                var3.list2.add(new InventoryRulesScreen.Rule(var17, var16, 28.0F, 28.0F, var9, -1));
                if (var12) {
                    var3.string = var8.string2();
                }
            }
        }

        if (var3.list5.isEmpty()) {
            var3.uiRenderer
                .method_454(
                    Typography.fontStyle2, "No items match that search", var3.floatValue14, var2 + 4.0F, Theme.method_180(Theme.getPalette().intValue7(), 0.6F)
                );
            return var2 + 22.0F;
        } else {
            return var4;
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        int var3;
        if ((var3 = Mouse.getEventDWheel()) != 0) {
            float var4 = var3 / 120.0F;
            float var2;
            if ((var2 = this.method_2242()) >= this.floatValue4 && var2 < this.floatValue4 + this.floatValue11 && this.floatValue7 > 0.0F) {
                this.floatValue16 = Math.clamp(this.floatValue16 - var4 * 27.0F, 0.0F, this.floatValue7);
            } else {
                this.floatValue17 = Math.clamp(this.floatValue17 - var4 * 32.0F, 0.0F, this.floatValue3);
            }
        } else {
            super.handleMouseInput();
        }
    }

    private ItemListSetting method_2235() {
        switch (this.intValue) {
            case 1:
                return this.inventoryManager.keep;
            case 2:
                return this.inventoryManager.drop;
            default:
                return null;
        }
    }

    private void method_2236() {
        TextField.method_2410();
        this.mc.displayGuiScreen(this.guiScreen);
    }

    private String method_2237() {
        return this.intValue == 0
            ? new StringBuilder().insert(0, "Slot ").append(this.intValue2 + 1).append(" is not claimed by anything yet").toString()
            : "Nothing on this list";
    }

    private void method_2238(float var1, float var2) {
        Iterator var4;
        for (Iterator var5 = var4 = this.list.iterator(); var5.hasNext(); var5 = var4) {
            InventoryRulesScreen.Rule var8;
            if ((var8 = (InventoryRulesScreen.Rule)var4.next()).method_2291(var1, var2)) {
                InventoryRulesScreen var7;
                if (this.intValue3 < 0) {
                    this.inventoryManager.slots.method_136(var8.intValue(), this.itemMatcher);
                    var7 = this;
                } else {
                    this.inventoryManager.slots.method_139(this.intValue3, var8.intValue(), this.itemMatcher);
                    var7 = this;
                }

                var7.method_2239(var8.intValue());
                return;
            }
        }

        if (this.intValue3 >= 0) {
            this.inventoryManager.slots.method_130(this.intValue3, this.itemMatcher);
        }
    }

    private void method_2239(int var1) {
        if (var1 != this.intValue2) {
            this.animationArray[this.intValue2].method_603(0.0F, 250.0F, Easings.easing2);
            this.intValue2 = var1;
            this.animationArray[var1].method_603(1.0F, 300.0F, Easings.easing2);
            this.floatValue16 = 0.0F;
        }
    }

    public InventoryRulesScreen(GuiScreen var1) {
        var var5 = this;
        this.inventoryManager = Onyx.moduleManager.inventoryManager;
        this.uiRenderer = new UiRenderer();
        this.searchField = new SearchField("Search items");
        this.moduleRow = new ModuleRow(new String[]{"Slots", "Keep", "Drop"}, var1x -> {
            this.intValue = var1x;
            this.floatValue17 = 0.0F;
            this.floatValue16 = 0.0F;
        });
        this.button3 = new Button(Button.Variant.OUTLINED, "Clear", () -> {
            ItemListSetting var2x;
            if ((var2x = this.method_2235()) == null) {
                this.inventoryManager.slots.method_131(this.intValue2);
            } else {
                var2x.method_115();
            }
        });
        this.button = new Button(Button.Variant.TONAL, "Reset", () -> {
            this.inventoryManager.slots.reset();
            this.inventoryManager.keep.reset();
            this.inventoryManager.drop.reset();
        });
        this.button2 = new Button(Button.Variant.FILLED, "Done", this::method_2236);
        this.list = new ArrayList<>();
        this.list4 = new ArrayList<>();
        this.list3 = new ArrayList<>();
        this.list2 = new ArrayList<>();
        this.animationArray = new Animation[9];
        this.list5 = List.of();
        this.intValue = 0;
        this.intValue3 = -1;
        this.guiScreen = var1;
        int var4 = 0;

        for (int var2 = 0; var2 < var5.animationArray.length; var2 = var4) {
            Animation[] var6 = var5.animationArray;
            int var7 = var4;
            Animation var10001 = new Animation(var4 == var5.intValue2 ? 1.0F : 0.0F);
            var4++;
            var6[var7] = var10001;
        }

        var5.searchField.method_2549(var1x -> {
            this.list5 = ItemCatalog.method_1985(var1x, 400);
            this.floatValue17 = 0.0F;
        });
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.longValue = 0L;
        this.list5 = ItemCatalog.method_1985(this.searchField.method_2404(), 400);
    }

    private static int method_2240(int var0, int var1, float var2) {
        float var7 = Math.clamp(var2, 0.0F, 1.0F);
        int var3 = (int)((var0 >>> 24 & 0xFF) + ((var1 >>> 24 & 0xFF) - (var0 >>> 24 & 0xFF)) * var7);
        int var4 = (int)((var0 >>> 16 & 0xFF) + ((var1 >>> 16 & 0xFF) - (var0 >>> 16 & 0xFF)) * var7);
        int var5 = (int)((var0 >>> 8 & 0xFF) + ((var1 >>> 8 & 0xFF) - (var0 >>> 8 & 0xFF)) * var7);
        var1 = (int)((var0 & 0xFF) + ((var1 & 0xFF) - (var0 & 0xFF)) * var7);
        return var3 << 24 | var4 << 16 | var5 << 8 | var1;
    }

    @Override
    public void drawScreen(int var1, int var2, float var3) {
        this.method_2230();
        var3 = this.method_2243();
        Animation[] var14;
        int var5 = (var14 = this.animationArray).length;
        int var6 = 0;

        for (int var8 = 0; var8 < var5; var8 = var6) {
            Animation var10000 = var14[var6];
            Animation var10001 = var14[var6];
            var6++;
            var10000.method_605(var3);
        }

        RenderContext var15 = new RenderContext(this.uiRenderer, var3, var1, var2);
        this.uiRenderer.method_468();
        InventoryRulesScreen var10;
        if (this.mc.theWorld == null) {
            this.uiRenderer.method_492(0.0F, 0.0F, this.width, this.height);
            var10 = this;
        } else {
            this.uiRenderer.method_434(0.0F, 0.0F, this.width, this.height, Theme.method_176(0.7F));
            var10 = this;
        }

        var10.uiRenderer.method_479(this.floatValue12, this.floatValue5, this.floatValue2, this.floatValue, 28.0F, Theme.getPalette().intValue21());
        this.uiRenderer.method_454(Typography.fontStyle18, "Inventory rules", this.floatValue14, this.floatValue5 + 26.0F, Theme.getPalette().intValue15());
        this.uiRenderer
            .method_438(
                Typography.fontStyle2,
                this.method_2248(),
                this.floatValue12 + this.floatValue2 - 18.0F,
                this.floatValue5 + 26.0F + 3.0F,
                Theme.method_180(Theme.getPalette().intValue7(), 0.85F)
            );
        this.moduleRow.method_2206(var15);
        this.moduleRow.method_2186(var15);
        this.list4.clear();
        this.list.clear();
        this.string = null;
        InventoryRulesScreen var12;
        if (this.intValue != 0) {
            this.floatValue4 = this.floatValue13;
            this.floatValue11 = method_2246(97.0F);
            this.method_2254(var15, this.method_2235().getValue(), -1);
            var12 = this;
        } else {
            this.method_2244(var15);
            this.floatValue4 = this.floatValue13 + 38.0F + 10.0F;
            this.floatValue11 = method_2246(49.0F);
            this.method_2254(var15, this.inventoryManager.slots.method_135(this.intValue2), this.intValue2);
            var12 = this;
        }

        var12.searchField.method_2206(var15);
        this.searchField.method_2186(var15);
        this.method_2231(var15);
        if (this.string != null) {
            this.uiRenderer
                .method_432(
                    Typography.fontStyle16,
                    this.uiRenderer.method_469(Typography.fontStyle16, this.string, Math.max(40.0F, this.floatValue9 - 276.0F - 16.0F - 12.0F)),
                    this.floatValue14,
                    this.button3.getFloatValue() + 15.0F,
                    Theme.method_180(Theme.getPalette().intValue7(), 0.9F)
                );
        }

        Button[] var18;
        var6 = (var18 = this.method_2255()).length;
        int var7 = 0;

        for (int var13 = 0; var13 < var6; var13 = var7) {
            Button var16 = var18[var7];
            var7++;
            var16.method_2206(var15);
            var16.method_2186(var15);
        }

        if (this.flag) {
            this.method_2249(var15);
        }

        this.uiRenderer.method_483();
    }

    private void method_2241(InventoryRulesScreen.Rule var1) {
        if (var1.intValue() < 0) {
            ItemListSetting var2;
            if ((var2 = this.method_2235()) != null) {
                var2.method_113(var1.itemMatcher());
            }
        } else {
            this.inventoryManager.slots.method_130(var1.intValue(), var1.itemMatcher());
        }
    }

    @Override
    public void mouseReleased(int var1, int var2, int var3) {
        int var10 = var2;
        Button[] var4;
        int var5 = (var4 = this.method_2255()).length;
        int var6 = 0;

        for (int var7 = 0; var7 < var5; var7 = var6) {
            Button var12 = var4[var6];
            float var10001 = var1;
            var6++;
            var12.method_2189(var10001, var10, var3);
        }

        InventoryRulesScreen var9;
        label20: {
            this.searchField.method_2189(var1, var10, var3);
            if (this.itemMatcher != null) {
                if (this.flag) {
                    this.method_2238(var1, var10);
                    var9 = this;
                    break label20;
                }

                if (this.intValue3 < 0) {
                    this.method_2245(this.itemMatcher);
                }
            }

            var9 = this;
        }

        var9.itemMatcher = null;
        this.intValue3 = -1;
        this.flag = false;
        super.mouseReleased(var1, var10, var3);
    }

    private float method_2242() {
        int var2 = new ScaledResolution(this.mc).getScaleFactor();
        return (float)(this.mc.displayHeight - Minecraft.getScaledMouseY()) / var2;
    }

    @Override
    public void keyTyped(char var1, int var2) throws IOException {
        KeyTyped var3 = new KeyTyped(var2, 0, 0, var1);
        if (!this.searchField.method_2212(var3)) {
            if (var1 < ' ' || !this.searchField.method_2193(new MouseClick(var1, 0))) {
                if (var2 == 1) {
                    this.method_2236();
                } else {
                    super.keyTyped(var1, var2);
                }
            }
        }
    }

    private float method_2243() {
        long var1 = System.nanoTime();
        if (this.longValue == 0L) {
            this.longValue = var1;
            return 16.0F;
        } else {
            float var10000 = (float)(var1 - this.longValue) / 1000000.0F;
            this.longValue = var1;
            return Math.min(var10000, 100.0F);
        }
    }

    private void method_2244(RenderContext var1) {
        var var3 = this;
        float var5 = 382.0F;
        var5 = this.floatValue12 + (this.floatValue2 - var5) / 2.0F;
        int var4 = 0;

        for (int var14 = 0; var14 < 9; var14 = var4) {
            float var15 = var5 + 43.0F * var4;
            var3.list.add(new InventoryRulesScreen.Rule(var15, var3.floatValue13, 38.0F, 38.0F, null, var4));
            boolean var9;
            if (var3.flag
                && var1.floatValue3 >= var15
                && var1.floatValue3 < var15 + 38.0F
                && var1.floatValue2 >= var3.floatValue13
                && var1.floatValue2 < var3.floatValue13 + 38.0F) {
                var9 = true;
            } else {
                var9 = false;
            }

            float var6 = var3.animationArray[var4].method_606();
            int var7 = Theme.getPalette().intValue11();
            int var10;
            if (var9) {
                var10 = Theme.getPalette().intValue34();
            } else {
                var10 = method_2240(var7, Theme.getPalette().intValue5(), var6);
            }

            var3.uiRenderer.method_479(var15, var3.floatValue13, 38.0F, 38.0F, 8.0F, var10);
            float var12;
            float var13;
            if (!var9) {
                var13 = 0.0F;
                var12 = var6;
            } else {
                var13 = 1.0F;
                var12 = var6;
            }

            float var2;
            if ((var2 = Math.max(var12, var13)) > 0.01F) {
                var3.uiRenderer.method_494(var15, var3.floatValue13, 38.0F, 38.0F, 8.0F, 1.5F, Theme.method_180(Theme.getPalette().intValue31(), var2));
            }

            List var16;
            if (!(var16 = var3.inventoryManager.slots.method_135(var4)).isEmpty()) {
                var3.method_2247(((ItemMatcher)var16.get(0)).method_2010(), var15 + 9.0F, var3.floatValue13 + 13.0F + 2.5F, 20.0F);
            }

            if (var16.size() > 1) {
                var3.uiRenderer
                    .method_438(
                        Typography.fontStyle13,
                        new StringBuilder().insert(0, "+").append(var16.size() - 1).toString(),
                        var15 + 38.0F - 3.0F,
                        var3.floatValue13 + 38.0F - 11.0F,
                        Theme.getPalette().intValue15()
                    );
            }

            UiRenderer var10000 = var3.uiRenderer;
            FontStyle var10001 = Typography.fontStyle13;
            String var10002 = String.valueOf(var4 + 1);
            float var10003 = var15 + 19.0F;
            float var10004 = var3.floatValue13 + 4.0F;
            int var10005 = Theme.getPalette().intValue7();
            float var10007 = 0.4F * var6;
            var4++;
            var10000.method_446(var10001, var10002, var10003, var10004, Theme.method_180(var10005, 0.55F + var10007));
        }
    }

    @Override
    public void mouseClickMove(int var1, int var2, int var3, long var4) {
        if (this.itemMatcher != null && !this.flag && (Math.abs(var1 - this.floatValue6) > 3.0F || Math.abs(var2 - this.floatValue10) > 3.0F)) {
            this.flag = true;
        }

        if (!this.flag) {
            super.mouseClickMove(var1, var2, var3, var4);
        }
    }

    private void method_2245(ItemMatcher var1) {
        if (var1 != null) {
            ItemListSetting var2;
            if ((var2 = this.method_2235()) != null) {
                var2.method_109(var1);
            } else if (!this.inventoryManager.slots.method_135(this.intValue2).contains(var1)) {
                this.inventoryManager.slots.method_136(this.intValue2, var1);
            } else {
                this.inventoryManager.slots.method_130(this.intValue2, var1);
            }
        }
    }

    private static float method_2246(float var0) {
        float var2 = 27.0F;
        return Math.max(1, (int)((var0 + 5.0F) / var2)) * var2 - 5.0F;
    }

    private void method_2247(ItemStack var1, float var2, float var3, float var4) {
        if (var1 != null && var1.getItem() != null) {
            this.uiRenderer.method_441();
            this.uiRenderer.method_451(var2, var3);
            this.uiRenderer.method_491(var4 / 16.0F, 0.0F, 0.0F);
            this.uiRenderer.method_456(var1, 0.0F, 0.0F);
            this.uiRenderer.method_435();
        }
    }

    private String method_2248() {
        if (this.intValue == 0) {
            return "Pick a slot, then click or drag an item onto it";
        } else {
            return this.intValue == 1 ? "Never thrown away, even if the Drop list says so" : "Thrown away when Drop junk is on";
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private void method_2249(RenderContext var1) {
        if (this.itemMatcher != null) {
            this.method_2247(this.itemMatcher.method_2010(), var1.floatValue3 - 6.5F, var1.floatValue2 - 6.5F, 13.0F);
        }
    }

    private float method_2250(ItemMatcher var1) {
        return 33.0F + this.uiRenderer.method_442(Typography.fontStyle16, this.method_2232(var1)) + 4.0F + 9.0F;
    }

    private void method_2251(ItemMatcher var1, int var2, float var3, float var4) {
        this.itemMatcher = var1;
        this.intValue3 = var2;
        this.floatValue6 = var3;
        this.floatValue10 = var4;
        this.flag = false;
    }

    private boolean method_2252(ItemMatcher var1) {
        ItemListSetting var3;
        return (var3 = this.method_2235()) == null ? this.inventoryManager.slots.method_135(this.intValue2).contains(var1) : var3.method_114(var1);
    }

    private float method_2253(RenderContext var1, float var2) {
        var var3 = this;
        float var26 = this.floatValue14;
        var2 = var2;
        ItemMatcher.ItemGroup[] var4;
        int var5 = (var4 = ItemMatcher.ItemGroup.values()).length;
        int var6 = 0;

        for (int var25 = 0; var25 < var5; var25 = var6) {
            ItemMatcher var7 = ItemMatcher.method_2001(var4[var6]);
            float var8 = var3.method_2250(var7);
            if (var26 + var8 > var3.floatValue14 + var3.floatValue9 - 7.0F && var26 > var3.floatValue14) {
                var26 = var3.floatValue14;
                var2 += 27.0F;
            }

            boolean var9 = var3.method_2252(var7);
            boolean var12;
            if (var1.floatValue3 >= var26
                && var1.floatValue3 < var26 + var8
                && var1.floatValue2 >= var2
                && var1.floatValue2 < var2 + 22.0F
                && var1.floatValue2 >= var3.floatValue8
                && var1.floatValue2 < var3.floatValue15) {
                var12 = true;
            } else {
                var12 = false;
            }

            int var13;
            if (var9) {
                var13 = Theme.getPalette().intValue34();
            } else if (var12) {
                var13 = Theme.getPalette().intValue11();
            } else {
                var13 = Theme.getPalette().intValue19();
            }

            var3.uiRenderer.method_479(var26, var2, var8, 22.0F, Float.MAX_VALUE, var13);
            var3.method_2247(var7.method_2010(), var26 + 8.0F, var2 + 4.5F, 13.0F);
            UiRenderer var10000 = var3.uiRenderer;
            FontStyle var10001 = Typography.fontStyle16;
            String var10002 = var3.method_2232(var7);
            float var10003 = var26 + 8.0F + 13.0F + 4.0F;
            float var10004 = var2 + 11.0F;
            UiRenderer var19;
            FontStyle var20;
            String var21;
            float var22;
            float var23;
            int var24;
            if (!var9) {
                float var18 = var10004;
                float var17 = var10003;
                String var16 = var10002;
                FontStyle var15 = var10001;
                UiRenderer var14 = var10000;
                var24 = Theme.getPalette().intValue15();
                var23 = var18;
                var22 = var17;
                var21 = var16;
                var20 = var15;
                var19 = var14;
            } else {
                var24 = Theme.getPalette().intValue17();
                var23 = var10004;
                var22 = var10003;
                var21 = var10002;
                var20 = var10001;
                var19 = var10000;
            }

            var19.method_432(var20, var21, var22, var23, var24);
            if (var9) {
                Icons.method_2226(var3.uiRenderer, var26 + var8 - 8.0F - 4.5F, var2 + 11.0F, 11.0F, Theme.getPalette().intValue17());
            }

            var3.list3.add(new InventoryRulesScreen.Rule(var26, var2, var8, 22.0F, var7, -1));
            var6++;
            var26 += var8 + 5.0F;
        }

        return var2 + 22.0F;
    }

    @Override
    public void mouseClicked(int var1, int var2, int var3) throws IOException {
        Button[] var4;
        int var12 = (var4 = this.method_2255()).length;
        int var6 = 0;

        for (int var7 = 0; var7 < var12; var7 = ++var6) {
            if (var4[var6].method_2183(var1, var2, var3)) {
                return;
            }
        }

        if (!this.moduleRow.method_2183(var1, var2, var3)) {
            if (!this.searchField.method_2183(var1, var2, var3)) {
                if (var3 != 0) {
                    super.mouseClicked(var1, var2, var3);
                } else {
                    Iterator var17;
                    for (Iterator var8 = var17 = this.list.iterator(); var8.hasNext(); var8 = var17) {
                        InventoryRulesScreen.Rule var13;
                        if ((var13 = (InventoryRulesScreen.Rule)var17.next()).method_2291(var1, var2)) {
                            this.method_2239(var13.intValue());
                            return;
                        }
                    }

                    Iterator var18;
                    for (Iterator var9 = var18 = this.list4.iterator(); var9.hasNext(); var9 = var18) {
                        InventoryRulesScreen.Rule var14;
                        if ((var14 = (InventoryRulesScreen.Rule)var18.next()).method_2291(var1, var2)) {
                            if (var1 >= var14.floatValue3() + var14.floatValue4() - 8.0F - 9.0F - 2.0F) {
                                this.method_2241(var14);
                                return;
                            }

                            this.method_2251(var14.itemMatcher(), var14.intValue(), var1, var2);
                            return;
                        }
                    }

                    if (var2 >= this.floatValue8 && var2 < this.floatValue15) {
                        Iterator var19;
                        for (Iterator var10 = var19 = this.list3.iterator(); var10.hasNext(); var10 = var19) {
                            InventoryRulesScreen.Rule var15;
                            if ((var15 = (InventoryRulesScreen.Rule)var19.next()).method_2291(var1, var2)) {
                                this.method_2251(var15.itemMatcher(), -1, var1, var2);
                                return;
                            }
                        }

                        Iterator var20;
                        for (Iterator var11 = var20 = this.list2.iterator(); var11.hasNext(); var11 = var20) {
                            InventoryRulesScreen.Rule var16;
                            if ((var16 = (InventoryRulesScreen.Rule)var20.next()).method_2291(var1, var2)) {
                                this.method_2251(var16.itemMatcher(), -1, var1, var2);
                                return;
                            }
                        }
                    }

                    super.mouseClicked(var1, var2, var3);
                }
            }
        }
    }

    private void method_2254(RenderContext var1, List<ItemMatcher> var2, int var3) {
        var var5 = this;
        if (var2.isEmpty()) {
            this.floatValue7 = 0.0F;
            this.floatValue16 = 0.0F;
            this.uiRenderer
                .method_454(
                    Typography.fontStyle2,
                    this.method_2237(),
                    this.floatValue14,
                    this.floatValue4 + 4.0F,
                    Theme.method_180(Theme.getPalette().intValue7(), 0.6F)
                );
        } else {
            this.uiRenderer.method_443(this.floatValue14, this.floatValue4, this.floatValue9, this.floatValue11);
            float var4 = this.floatValue14;
            float var35 = this.floatValue4 - this.floatValue16;

            Iterator var36;
            for (Iterator var34 = var36 = var2.iterator(); var34.hasNext(); var34 = var36) {
                ItemMatcher var6 = (ItemMatcher)var36.next();
                float var7 = var5.method_2250(var6);
                if (var4 + var7 > var5.floatValue14 + var5.floatValue9 - 7.0F && var4 > var5.floatValue14) {
                    var4 = var5.floatValue14;
                    var35 += 27.0F;
                }

                if (var35 + 22.0F >= var5.floatValue4 && var35 < var5.floatValue4 + var5.floatValue11) {
                    boolean var9;
                    if (var1.floatValue3 >= var4 && var1.floatValue3 < var4 + var7 && var1.floatValue2 >= var35 && var1.floatValue2 < var35 + 22.0F) {
                        var9 = true;
                    } else {
                        var9 = false;
                    }

                    UiRenderer var10000 = var5.uiRenderer;
                    UiRenderer var16;
                    float var17;
                    float var18;
                    float var19;
                    float var20;
                    float var21;
                    int var22;
                    if (!var9) {
                        float var15 = Float.MAX_VALUE;
                        float var14 = 22.0F;
                        UiRenderer var10 = var5.uiRenderer;
                        var22 = Theme.getPalette().intValue11();
                        var21 = var15;
                        var20 = var14;
                        var19 = var7;
                        var18 = var35;
                        var17 = var4;
                        var16 = var10;
                    } else {
                        var22 = Theme.getPalette().intValue5();
                        var21 = Float.MAX_VALUE;
                        var20 = 22.0F;
                        var19 = var7;
                        var18 = var35;
                        var17 = var4;
                        var16 = var10000;
                    }

                    var16.method_479(var17, var18, var19, var20, var21, var22);
                    var5.method_2247(var6.method_2010(), var4 + 8.0F, var35 + 4.5F, 13.0F);
                    var5.uiRenderer
                        .method_432(Typography.fontStyle16, var5.method_2232(var6), var4 + 8.0F + 13.0F + 4.0F, var35 + 11.0F, Theme.getPalette().intValue15());
                    var10000 = var5.uiRenderer;
                    float var10001 = var4 + var7 - 8.0F - 4.5F;
                    float var10002 = var35 + 11.0F;
                    int var10004 = Theme.getPalette().intValue7();
                    UiRenderer var28;
                    float var29;
                    float var30;
                    float var31;
                    int var32;
                    float var33;
                    if (!var9) {
                        int var27 = var10004;
                        float var26 = 9.0F;
                        float var25 = var10002;
                        float var24 = var10001;
                        UiRenderer var23 = var10000;
                        var33 = 0.7F;
                        var32 = var27;
                        var31 = var26;
                        var30 = var25;
                        var29 = var24;
                        var28 = var23;
                    } else {
                        var33 = 1.0F;
                        var32 = var10004;
                        var31 = 9.0F;
                        var30 = var10002;
                        var29 = var10001;
                        var28 = var10000;
                    }

                    Icons.method_2223(var28, var29, var30, var31, Theme.method_180(var32, var33));
                    var5.list4.add(new InventoryRulesScreen.Rule(var4, var35, var7, 22.0F, var6, var3));
                }

                var4 += var7 + 5.0F;
            }

            var5.uiRenderer.method_486();
            var5.floatValue7 = Math.max(0.0F, var35 + 22.0F + var5.floatValue16 - (var5.floatValue4 + var5.floatValue11));
            var5.floatValue16 = Math.min(var5.floatValue16, var5.floatValue7);
            var5.method_2233(var5.floatValue4, var5.floatValue11, var5.floatValue16, var5.floatValue7);
        }
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        TextField.method_2410();
    }

    private Button[] method_2255() {
        return new Button[]{this.button3, this.button, this.button2};
    }

    private record Rule(float floatValue3, float floatValue, float floatValue4, float floatValue2, ItemMatcher itemMatcher, int intValue) {
        public boolean method_2291(float var1, float var2) {
            return var1 >= this.floatValue3
                && var1 < this.floatValue3 + this.floatValue4
                && var2 >= this.floatValue
                && var2 < this.floatValue + this.floatValue2;
        }
    }
}
