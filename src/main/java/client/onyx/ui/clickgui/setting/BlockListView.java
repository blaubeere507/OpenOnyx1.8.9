package client.onyx.ui.clickgui.setting;

import client.onyx.render.font.FontStyle;
import client.onyx.render.ui.UiRenderer;
import client.onyx.setting.impl.BlockListSetting;
import client.onyx.ui.component.Icons;
import client.onyx.ui.component.RenderContext;
import client.onyx.ui.component.SearchField;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.Typography;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import net.minecraft.block.Block;

public class BlockListView extends SettingView {
    private static final int INT_61 = 6;
    private static final float FLOAT_22_01 = 22.0F;
    private static final float FLOAT_9_01 = 9.0F;
    private static final float FLOAT_10_01 = 10.0F;
    private static final float FLOAT_6_012 = 6.0F;
    private static final float FLOAT_5_01 = 5.0F;
    private static final float FLOAT_22_02 = 22.0F;
    private final SearchField searchField = new SearchField("Search blocks");
    private final List<Block> list2 = new ArrayList<>();
    private final List<Block> list3 = new ArrayList<>();
    private final List<float[]> list22 = new ArrayList<>();
    private String string = "";
    private float floatValue22 = 60.0F;
    private int intValue = -1;
    private int intValue2 = -1;
    private final BlockListSetting blockListSetting;
    private float floatValue5;

    @Override
    public void method_2206(RenderContext var1) {
        var var2 = this;
        this.searchField.method_2258(this.floatValue3, this.method_2571(), this.floatValue4, 40.0F);
        this.floatValue5 = this.method_2571() + 40.0F + 6.0F;
        this.intValue = -1;
        int var14 = 0;

        for (int var6 = 0; var6 < var2.list2.size(); var6 = ++var14) {
            float var3 = var2.floatValue5 + var14 * 22.0F;
            if (var1.floatValue3 >= var2.floatValue3
                && var1.floatValue3 < var2.floatValue3 + var2.floatValue4
                && var1.floatValue2 >= var3
                && var1.floatValue2 < var3 + 22.0F) {
                var2.intValue = var14;
            }
        }

        var2.list3.clear();
        var2.list3.addAll(var2.blockListSetting.getValue());
        var2.list22.clear();
        float var15 = var2.floatValue3;
        float var10000 = var2.floatValue5 + var2.list2.size() * 22.0F;
        float var8;
        float var9;
        if (!var2.list2.isEmpty()) {
            float var7 = var10000;
            var9 = 6.0F;
            var8 = var7;
        } else {
            var9 = 0.0F;
            var8 = var10000;
        }

        float var16 = var8 + var9;
        var2.intValue2 = -1;
        int var4 = 0;

        for (int var10 = 0; var10 < var2.list3.size(); var10 = var4) {
            float var5 = Math.min(var2.method_2591(var1, var2.list3.get(var4)), var2.floatValue4);
            if (var15 > var2.floatValue3 && var15 + var5 > var2.floatValue3 + var2.floatValue4) {
                var15 = var2.floatValue3;
                var16 += 27.0F;
            }

            var2.list22.add(new float[]{var15, var16, var5});
            if (var1.floatValue3 >= var15 && var1.floatValue3 < var15 + var5 && var1.floatValue2 >= var16 && var1.floatValue2 < var16 + 22.0F) {
                var2.intValue2 = var4;
            }

            var4++;
            var15 += var5 + 5.0F;
        }

        BlockListView var12;
        float var13;
        if (!var2.list3.isEmpty()) {
            var13 = var16 + 22.0F;
            var12 = var2;
        } else {
            var13 = var16;
            var12 = var2;
        }

        var12.floatValue22 = var13 + 6.0F - var2.floatValue;
        var2.method_2206(var1);
    }

    public BlockListView(BlockListSetting var1) {
        super(var1);
        this.blockListSetting = var1;
        this.searchField
            .method_2549(
                var1x -> {
                    var var9 = this;
                    BlockListView var4;
                    String var5;
                    if (var1x != null) {
                        var5 = var1x.trim().toLowerCase(Locale.ROOT);
                        var4 = this;
                    } else {
                        var5 = "";
                        var4 = this;
                    }

                    var4.string = var5;
                    this.list2.clear();
                    if (!this.string.isEmpty()) {
                        Iterator var8 = Block.blockRegistry.iterator();

                        label35:
                        while (true) {
                            for (Iterator var6 = var8; var6.hasNext(); var6 = var8) {
                                Block var2;
                                if (BlockListSetting.method_86(var2 = (Block)var8.next()).contains(var9.string)
                                    || var2.getLocalizedName().toLowerCase(Locale.ROOT).contains(var9.string)) {
                                    var9.list2.add(var2);
                                    if (var9.list2.size() >= 6) {
                                        break label35;
                                    }
                                    continue label35;
                                }
                            }

                            return;
                        }
                    }
                }
            );
        this.list.add(this.searchField);
    }

    @Override
    public boolean method_2183(float var1, float var2, int var3) {
        if (!this.method_2577() || var3 != 0) {
            return false;
        } else if (this.searchField.method_2183(var1, var2, var3)) {
            return true;
        } else if (this.intValue >= 0) {
            this.blockListSetting.method_84(this.list2.get(this.intValue));
            return true;
        } else if (this.intValue2 >= 0) {
            this.blockListSetting.method_88(this.list3.get(this.intValue2));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public float method_2387(RenderContext var1) {
        return this.floatValue22;
    }

    @Override
    public void method_2389(RenderContext var1, float var2) {
        var var3 = this;
        UiRenderer var30 = var1.uiRenderer;
        String var27 = this.list3.size() + " selected";
        float var4 = var30.method_442(Typography.fontStyle2, var27);
        this.method_2575(var1, this.floatValue4 - var4 - 12.0F);
        var30.method_476(
            Typography.fontStyle2, var27, this.floatValue3 + this.floatValue4, this.method_2578(), this.method_2572(var1.method_2256().intValue7())
        );
        this.searchField.method_2186(var1);
        int var28 = 0;

        for (int var25 = 0; var25 < var3.list2.size(); var25 = ++var28) {
            Block var31 = var3.list2.get(var28);
            float var5 = var3.floatValue5 + var28 * 22.0F;
            boolean var6 = var3.blockListSetting.method_85(var31);
            if (var3.intValue == var28) {
                var30.method_479(
                    var3.floatValue3, var5, var3.floatValue4, 22.0F, 4.0F, var3.method_2572(Theme.method_180(var1.method_2256().intValue15(), 0.08F))
                );
            }

            FontStyle var10001 = Typography.fontStyle15;
            String var10002 = var30.method_469(Typography.fontStyle15, method_2592(var31), var3.floatValue4 - 30.0F);
            float var10003 = var3.floatValue3 + 8.0F;
            float var10004 = var5 + 11.0F;
            UiRenderer var18;
            FontStyle var19;
            String var20;
            float var21;
            float var22;
            BlockListView var23;
            int var24;
            if (!var6) {
                float var15 = var10004;
                float var14 = var10003;
                String var13 = var10002;
                FontStyle var12 = var10001;
                var24 = var1.method_2256().intValue15();
                var23 = var3;
                var22 = var15;
                var21 = var14;
                var20 = var13;
                var19 = var12;
                var18 = var30;
            } else {
                var24 = var1.method_2256().intValue31();
                var23 = var3;
                var22 = var10004;
                var21 = var10003;
                var20 = var10002;
                var19 = var10001;
                var18 = var30;
            }

            var18.method_432(var19, var20, var21, var22, var23.method_2572(var24));
            if (var6) {
                Icons.method_2226(var30, var3.floatValue3 + var3.floatValue4 - 14.0F, var5 + 11.0F, 12.0F, var3.method_2572(var1.method_2256().intValue31()));
            }
        }

        int var29 = 0;

        for (int var26 = 0; var26 < var3.list22.size(); var26 = var29) {
            float[] var32 = var3.list22.get(var29);
            float var33 = var32[0];
            float var34 = var32[1];
            float var7 = var32[2];
            var30.method_479(var33, var34, var7, 22.0F, 8.0F, var3.method_2572(var1.method_2256().intValue5()));
            if (var3.intValue2 == var29) {
                var30.method_479(var33, var34, var7, 22.0F, 8.0F, var3.method_2572(Theme.method_180(var1.method_2256().intValue15(), 0.08F)));
            }

            int var8 = var1.method_2256().intValue30();
            float var9 = var7 - 18.0F - 10.0F - 5.0F;
            var30.method_432(
                Typography.fontStyle16,
                var30.method_469(Typography.fontStyle16, method_2592(var3.list3.get(var29)), var9),
                var33 + 9.0F,
                var34 + 11.0F,
                var3.method_2572(var8)
            );
            var7 = var33 + var7 - 9.0F - 5.0F;
            var9 = var34 + 11.0F;
            float var10 = 4.0F;
            var30.method_461(var7 - var10, var9 - var10, var7 + var10, var9 + var10, 1.4F, var3.method_2572(var8));
            float var37 = var7 - var10;
            float var38 = var9 + var10;
            float var39 = var7 + var10;
            float var40 = var9 - var10;
            var29++;
            var30.method_461(var37, var38, var39, var40, 1.4F, var3.method_2572(var8));
        }
    }

    private float method_2591(RenderContext var1, Block var2) {
        return 18.0F + var1.uiRenderer.method_442(Typography.fontStyle16, method_2592(var2)) + 10.0F + 5.0F;
    }

    private static String method_2592(Block var0) {
        return var0.getLocalizedName();
    }
}
