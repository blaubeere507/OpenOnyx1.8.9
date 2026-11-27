package client.onyx.module.render;

import client.onyx.event.impl.WorldLoadEvent;
import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.module.combat.bedwars.BedWarsObjectives;
import client.onyx.render.ui.UiRenderer;
import client.onyx.render.world.BoxRenderer;
import client.onyx.render.world.LineRenderer;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.ColorSetting;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.ui.theme.color.ColorUtils;
import client.onyx.util.Timer;
import client.onyx.util.player.BlockPlacer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.ChunkProviderClient;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public final class BedESP extends Module {
    private static final double DOUBLE_0_56251 = 0.5625;
    private static final double DOUBLE_0_0031 = 0.003;
    private static final int INT_81 = 8;
    public final BooleanSetting ownBed = new BooleanSetting("Own bed", true).describe("Highlight the bed the tracker believes is yours");
    public final BooleanSetting enemyBeds = new BooleanSetting("Enemy beds", true).describe("Highlight every other bed");
    public final ColorSetting ownColor = new ColorSetting("Own color", -9706613).describe("Colour of your own bed").visibleWhen(this.ownBed);
    public final ColorSetting enemyColor = new ColorSetting("Enemy color", -38302).describe("Colour of enemy beds").visibleWhen(this.enemyBeds);
    public final BooleanSetting defense = new BooleanSetting("Defense", false)
        .describe("Outline the blocks walling each bed in, coloured by what they are made of")
        .onChange(var1 -> this.method_1163());
    public final NumberSetting defenseRadius = new NumberSetting("Defense radius", 3.0, 1.0, 6.0, 1.0)
        .method_147(" m")
        .describe("How far around a bed counts as its defense")
        .visibleWhen(() -> this.defense.method_146() || this.defenseTags.method_146())
        .onChange(var1 -> this.method_1163());
    public final NumberSetting defenseOpacity = new NumberSetting("Defense opacity", 70.0, 10.0, 100.0, 5.0)
        .method_147("%")
        .describe("How strongly the defense outlines are drawn")
        .visibleWhen(this.defense);
    public final BooleanSetting defenseTags = new BooleanSetting("Defense tags", false)
        .describe("Float the blocks making up each bed's defense above the bed itself")
        .onChange(var1 -> this.method_1163());
    public final NumberSetting tagScale = new NumberSetting("Tag scale", 1.0, 0.5, 2.0, 0.05)
        .method_147("x")
        .describe("Defense tag size - it still shrinks with distance")
        .visibleWhen(this.defenseTags);
    public final NumberSetting tagFalloff = new NumberSetting("Tag falloff", 1.0, 0.0, 2.0, 0.05)
        .method_147("x")
        .describe("How hard a tag shrinks as its bed gets further away - 0 keeps every tag the same size however far off it is")
        .visibleWhen(this.defenseTags);
    public final BooleanSetting tagBlur = new BooleanSetting("Tag blur", true)
        .describe("Frost the world behind each defense tag instead of tinting it flat")
        .visibleWhen(this.defenseTags);
    public final BooleanSetting beam = new BooleanSetting("Beam", false).describe("Stand a light beam over every highlighted bed");
    public final NumberSetting beamHeight = new NumberSetting("Beam height", 24.0, 4.0, 128.0, 4.0)
        .method_147(" m")
        .describe("How far the beam reaches above the bed")
        .visibleWhen(this.beam);
    public final NumberSetting beamWidth = new NumberSetting("Beam width", 0.5, 0.1, 2.0, 0.1)
        .method_147(" m")
        .describe("How wide the beam column is")
        .visibleWhen(this.beam);
    public final NumberSetting beamOpacity = new NumberSetting("Beam opacity", 55.0, 10.0, 100.0, 5.0)
        .method_147("%")
        .describe("How strongly the beam is drawn at its base")
        .visibleWhen(this.beam);
    public final BooleanSetting beamColor = new BooleanSetting("Beam color", false)
        .describe("Draw every beam in one colour of its own instead of the bed's own/enemy colour")
        .visibleWhen(this.beam);
    public final ColorSetting beamTint = new ColorSetting("Beam tint", -8399617).describe("The colour every beam is drawn in").visibleWhen(this.beamColor);
    public final BooleanSetting ownBedBeam = new BooleanSetting("Own bed beam", true)
        .describe("Stand a beam over your own bed too - off leaves the column to enemy beds, which is one less thing lit up in the middle of your own base")
        .visibleWhen(this.beam);
    public final BooleanSetting ownBeamColor = new BooleanSetting("Own beam color", false)
        .describe("Give your own bed's beam a colour of its own instead of the one the rest of the beams use")
        .visibleWhen(() -> this.beam.method_146() && this.ownBedBeam.method_146());
    public final ColorSetting ownBeamTint = new ColorSetting("Own beam tint", -9706613)
        .describe("The colour your own bed's beam is drawn in")
        .visibleWhen(() -> this.beam.method_146() && this.ownBedBeam.method_146() && this.ownBeamColor.method_146());
    public final BooleanSetting fill = new BooleanSetting("Fill", true);
    public final BooleanSetting throughWalls = new BooleanSetting("Through walls", true);
    public final NumberSetting lineWidth = new NumberSetting("Line width", 1.5, 0.5, 5.0, 0.5).method_147(" px");
    public final NumberSetting refresh = new NumberSetting("Refresh", 500.0, 100.0, 2000.0, 50.0)
        .method_147("ms")
        .describe("How often the world is rescanned for beds");
    private final Timer timer = new Timer();
    private final List<BlockPos> list = new ArrayList<>();
    private final List<BedESP.DefenseBlock> list2 = new ArrayList<>();
    private final List<BedESP.BedOutline> list3 = new ArrayList<>();
    private final BedESPTagRenderer bedESPTagRenderer = new BedESPTagRenderer(this);
    private boolean flag;

    @Override
    public void onEnable() {
        this.method_1163();
    }

    private static List<BedESP.BedInfo> method_1161(Map<Integer, int[]> var0, Map<Integer, IBlockState> var1) {
        ArrayList var8 = new ArrayList();

        Iterator var3;
        for (Iterator var7 = var3 = var0.entrySet().iterator(); var7.hasNext(); var7 = var3) {
            Entry var4 = (Entry)var3.next();
            IBlockState var5;
            Block var6 = (var5 = (IBlockState)var1.get(var4.getKey())).getBlock();
            var8.add(new BedESP.BedInfo(new ItemStack(var6, 1, var6.damageDropped(var5)), var6.getLocalizedName(), ((int[])var4.getValue())[0]));
        }

        var8.sort(Comparator.comparingInt(BedESP.BedInfo::intValue).reversed());
        return var8;
    }

    public void method_1162(float var1) {
        var var2 = this;
        if (this.isToggled() && mc.theWorld != null && mc.thePlayer != null) {
            if (!this.flag || this.timer.method_2018(this.refresh.method_149())) {
                this.method_1165();
                this.timer.method_2017();
                this.flag = true;
            }

            if (!this.list.isEmpty()) {
                boolean var4 = false;
                float var5 = this.defenseOpacity.method_151() / 100.0F;
                List var8;
                if (this.defense.method_146()) {
                    var8 = this.list2;
                } else {
                    var8 = List.of();
                }

                Iterator var27;
                Iterator var9 = var27 = var8.iterator();

                while (var9.hasNext()) {
                    BedESP.DefenseBlock var3 = (BedESP.DefenseBlock)var27.next();
                    AxisAlignedBB var6;
                    if ((var6 = var2.method_1166(var3.blockPos())) == null) {
                        var9 = var27;
                    } else {
                        if (!var4) {
                            if (!BoxRenderer.method_519(var1, var2.throughWalls.method_146())) {
                                return;
                            }

                            var4 = true;
                        }

                        int var10001 = ColorUtils.method_209(var3.intValue(), var5);
                        float var10002 = var2.lineWidth.method_151();
                        AxisAlignedBB var13;
                        int var14;
                        float var15;
                        int var16;
                        if (!var2.fill.method_146()) {
                            float var12 = var10002;
                            int var11 = var10001;
                            var16 = 0;
                            var15 = var12;
                            var14 = var11;
                            var13 = var6;
                        } else {
                            var16 = ColorUtils.method_209(var3.intValue(), var5 * 0.18F);
                            var15 = var10002;
                            var14 = var10001;
                            var13 = var6;
                        }

                        BoxRenderer.method_518(var13, var14, var15, var16);
                        var9 = var27;
                    }
                }

                Iterator var28;
                Iterator var17 = var28 = var2.list.iterator();

                while (var17.hasNext()) {
                    BlockPos var29 = (BlockPos)var28.next();
                    boolean var31;
                    if ((var31 = BedWarsObjectives.INSTANCE.method_1545(var29)) && !var2.ownBed.method_146()) {
                        var17 = var28;
                    } else if (!var31 && !var2.enemyBeds.method_146()) {
                        var17 = var28;
                    } else {
                        AxisAlignedBB var30;
                        if ((var30 = var2.method_1173(var29)) == null) {
                            var17 = var28;
                        } else {
                            if (!var4) {
                                if (!BoxRenderer.method_519(var1, var2.throughWalls.method_146())) {
                                    return;
                                }

                                var4 = true;
                            }

                            Integer var19;
                            if (!var31) {
                                var19 = var2.enemyColor.getValue();
                            } else {
                                var19 = var2.ownColor.getValue();
                            }

                            int var7 = var19;
                            int var32 = ColorUtils.method_209(var7, ColorUtils.method_213(var7) / 255.0F * 0.95F);
                            float var33 = var2.lineWidth.method_151();
                            AxisAlignedBB var23;
                            int var24;
                            float var25;
                            int var26;
                            if (!var2.fill.method_146()) {
                                float var22 = var33;
                                int var21 = var32;
                                var26 = 0;
                                var25 = var22;
                                var24 = var21;
                                var23 = var30;
                            } else {
                                var26 = ColorUtils.method_209(var7, ColorUtils.method_213(var7) / 255.0F * 0.16F);
                                var25 = var33;
                                var24 = var32;
                                var23 = var30;
                            }

                            BoxRenderer.method_518(var23, var24, var25, var26);
                            var17 = var28;
                        }
                    }
                }

                if (var4) {
                    BoxRenderer.method_521();
                }

                var2.method_1168(var1);
            }
        }
    }

    private void method_1163() {
        this.list.clear();
        this.list2.clear();
        this.list3.clear();
        this.flag = false;
    }

    private static boolean method_1164(BlockPos var0) {
        BlockPos var3 = var0;
        EnumFacing[] var1;
        int var2 = (var1 = EnumFacing.values()).length;
        int var6 = 0;

        for (int var5 = 0; var5 < var2; var5 = ++var6) {
            EnumFacing var4 = var1[var6];
            if (BlockPlacer.method_19(var3.offset(var4))) {
                return true;
            }
        }

        return false;
    }

    private void method_1165() {
        var var3 = this;
        this.list.clear();
        IChunkProvider var10;
        if ((var10 = mc.theWorld.getChunkProvider()) instanceof ChunkProviderClient) {
            ChunkProviderClient var1 = (ChunkProviderClient)var10;
            int var19 = Block.getIdFromBlock(Blocks.bed);
            Iterator var17 = var1.getLoadedChunks().iterator();

            label60:
            while (true) {
                for (Iterator var12 = var17; var12.hasNext(); var12 = var17) {
                    Chunk var8;
                    if ((var8 = (Chunk)var17.next()) == null) {
                        continue label60;
                    }

                    if (!var8.isEmpty()) {
                        int var18 = var8.xPosition << 4;
                        int var4 = var8.zPosition << 4;
                        ExtendedBlockStorage[] var5;
                        int var6 = (var5 = var8.getBlockStorageArray()).length;
                        int var7 = 0;

                        for (int var14 = 0; var14 < var6; var14 = ++var7) {
                            ExtendedBlockStorage var15;
                            if ((var15 = var5[var7]) != null && !var15.isEmpty()) {
                                char[] var16 = var15.getData();
                                int var9 = var15.getYLocation();
                                int var2 = 0;

                                for (int var13 = 0; var13 < var16.length; var13 = ++var2) {
                                    char var11;
                                    if ((var11 = var16[var2]) >> 4 == var19 && (var11 & '\b') != 0) {
                                        var3.list.add(new BlockPos(var18 + (var2 & 15), var9 + (var2 >> 8 & 15), var4 + (var2 >> 4 & 15)));
                                    }
                                }
                            }
                        }
                        continue label60;
                    }
                }

                var3.method_1167();
                return;
            }
        }
    }

    private AxisAlignedBB method_1166(BlockPos var1) {
        return BlockPlacer.method_19(var1) ? null : BlockPlacer.method_25(var1).offset(var1.getX(), var1.getY(), var1.getZ()).expand(0.003, 0.003, 0.003);
    }

    private void method_1167() {
        var var1 = this;
        this.list2.clear();
        this.list3.clear();
        if (this.defense.method_146() || this.defenseTags.method_146()) {
            int var6 = this.defenseRadius.method_149();
            HashSet var17 = new HashSet();
            Iterator var19 = this.list.iterator();

            label121:
            while (true) {
                Iterator var25 = var19;

                while (var25.hasNext()) {
                    BlockPos var4 = (BlockPos)var19.next();
                    boolean var5;
                    if ((var5 = BedWarsObjectives.INSTANCE.method_1545(var4)) && !var1.ownBed.method_146()) {
                        var25 = var19;
                    } else if (!var5 && !var1.enemyBeds.method_146()) {
                        var25 = var19;
                    } else {
                        IBlockState var28;
                        if ((var28 = mc.theWorld.getBlockState(var4)).getBlock() instanceof BlockBed) {
                            BlockPos var29 = var4.offset(var28.getValue(BlockBed.FACING).getOpposite());
                            int var7 = Math.min(var4.getX(), var29.getX()) - var6;
                            int var8 = Math.max(var4.getX(), var29.getX()) + var6;
                            int var9 = Math.min(var4.getZ(), var29.getZ()) - var6;
                            int var10 = Math.max(var4.getZ(), var29.getZ()) + var6;
                            int var11 = var4.getY();
                            int var12 = Math.min(255, var4.getY() + var6);
                            LinkedHashMap<Integer, int[]> var13 = new LinkedHashMap<>();
                            LinkedHashMap var14 = new LinkedHashMap();
                            int var15 = 0;
                            HashSet var16 = new HashSet();
                            ArrayDeque var2 = new ArrayDeque();

                            for (BlockPos var18 : List.of(var4, var29)) {
                                var16.add(var18);
                                EnumFacing[] var3 = EnumFacing.values();
                                int var20 = var3.length;
                                int var21 = 0;

                                for (int var26 = 0; var26 < var20; var26 = ++var21) {
                                    EnumFacing var22 = var3[var21];
                                    BlockPos var23;
                                    if ((var23 = var18.offset(var22)).getY() >= var11) {
                                        var2.add(var23);
                                    }
                                }
                            }

                            label118:
                            while (true) {
                                ArrayDeque var10000 = var2;

                                while (!var10000.isEmpty()) {
                                    BlockPos var31;
                                    if ((var31 = (BlockPos)var2.poll()).getY() < var11) {
                                        continue label118;
                                    }

                                    if (var31.getY() > var12) {
                                        var10000 = var2;
                                    } else {
                                        if (var31.getX() < var7) {
                                            continue label118;
                                        }

                                        if (var31.getX() > var8) {
                                            var10000 = var2;
                                        } else {
                                            if (var31.getZ() < var9) {
                                                continue label118;
                                            }

                                            if (var31.getZ() > var10) {
                                                var10000 = var2;
                                            } else if (!var16.add(var31)) {
                                                var10000 = var2;
                                            } else {
                                                Block var32;
                                                IBlockState var33;
                                                if ((var32 = (var33 = mc.theWorld.getBlockState(var31)).getBlock()).getMaterial() == Material.air) {
                                                    var10000 = var2;
                                                } else if (!var32.getMaterial().isSolid()) {
                                                    var10000 = var2;
                                                } else {
                                                    if (!(var32 instanceof BlockBed)) {
                                                        int var34 = Block.getIdFromBlock(var32) << 4 | var32.damageDropped(var33) & 15;
                                                        var13.computeIfAbsent(var34, var0 -> new int[1])[0]++;
                                                        var15++;
                                                        var14.putIfAbsent(var34, var33);
                                                        if (method_1164(var31) && var17.add(var31)) {
                                                            var1.list2.add(new BedESP.DefenseBlock(var31, method_1169(var33)));
                                                        }

                                                        EnumFacing[] var35;
                                                        int var36 = (var35 = EnumFacing.values()).length;
                                                        int var37 = 0;

                                                        for (int var27 = 0; var27 < var36; var27 = var37) {
                                                            EnumFacing var24 = var35[var37];
                                                            var37++;
                                                            var2.add(var31.offset(var24));
                                                        }
                                                        continue label118;
                                                    }

                                                    var10000 = var2;
                                                }
                                            }
                                        }
                                    }
                                }

                                if (var1.defenseTags.method_146() && var15 > 0) {
                                    var1.list3.add(new BedESP.BedOutline(var4, var5, method_1161(var13, var14), var15));
                                }
                                continue label121;
                            }
                        }

                        var25 = var19;
                    }
                }

                return;
            }
        }
    }

    public List<BedESP.BedOutline> getList3() {
        return this.list3;
    }

    public BedESP() {
        super("BedESP", "Highlights beds through walls", Category.RENDER);
    }

    private void method_1168(float var1) {
        var var2 = this;
        if (this.beam.method_146()) {
            boolean var10 = false;
            float var8 = this.beamOpacity.method_151() / 100.0F;
            double var4 = this.beamWidth.getValue();
            double var6 = this.beamHeight.getValue();
            Iterator var3 = this.list.iterator();

            label49:
            while (true) {
                Iterator var19 = var3;

                while (var19.hasNext()) {
                    BlockPos var9 = (BlockPos)var3.next();
                    boolean var20;
                    if (var20 = BedWarsObjectives.INSTANCE.method_1545(var9)) {
                        if (!var2.ownBed.method_146()) {
                            continue label49;
                        }

                        if (!var2.ownBedBeam.method_146()) {
                            var19 = var3;
                            continue;
                        }
                    }

                    if (!var20 && !var2.enemyBeds.method_146()) {
                        var19 = var3;
                    } else {
                        AxisAlignedBB var11;
                        if ((var11 = var2.method_1173(var9)) == null) {
                            var19 = var3;
                        } else {
                            if (!var10) {
                                if (!LineRenderer.method_622(var1, var2.throughWalls.method_146())) {
                                    return;
                                }

                                var10 = true;
                            }

                            int var21;
                            float var12 = ColorUtils.method_213(var21 = var2.method_1172(var20)) / 255.0F * var8;
                            double var13 = var9.getY() + 0.5625;
                            double var15 = (var11.minX + var11.maxX) / 2.0;
                            double var17 = (var11.minZ + var11.maxZ) / 2.0;
                            LineRenderer.method_623(
                                var15, var17, var13, var13 + var6, var4, ColorUtils.method_209(var21, var12 * 0.55F), ColorUtils.method_209(var21, 0.0F)
                            );
                            LineRenderer.method_623(
                                var15, var17, var13, var13 + var6, var4 * 0.35, ColorUtils.method_209(var21, var12), ColorUtils.method_209(var21, 0.0F)
                            );
                            var19 = var3;
                        }
                    }
                }

                if (var10) {
                    LineRenderer.method_620();
                }

                return;
            }
        }
    }

    @EventHandler
    private void onWorldLoad(WorldLoadEvent var1) {
        this.method_1163();
    }

    private static int method_1169(IBlockState var0) {
        Block var4;
        if ((var4 = var0.getBlock()) == Blocks.obsidian) {
            return -15003098;
        } else if (var4 == Blocks.glass) {
            return -4199947;
        } else {
            int var3;
            MapColor var5;
            if ((var5 = var4.getMapColor(var0)) == null) {
                var3 = 0;
            } else {
                var3 = var5.colorValue;
            }

            return var3 == 0 ? -5197648 : 0xFF000000 | var3;
        }
    }

    public boolean method_1170() {
        return this.bedESPTagRenderer.method_1152();
    }

    @Override
    public void onDisable() {
        this.method_1163();
    }

    public void method_1171(UiRenderer var1, float var2, float var3) {
        this.bedESPTagRenderer.method_1151(var1, var2, var3);
    }

    private int method_1172(boolean var1) {
        if (var1 && this.ownBeamColor.method_146()) {
            return this.ownBeamTint.getValue();
        } else if (this.beamColor.method_146()) {
            return this.beamTint.getValue();
        } else {
            Integer var3;
            if (!var1) {
                var3 = this.enemyColor.getValue();
            } else {
                var3 = this.ownColor.getValue();
            }

            return var3;
        }
    }

    private AxisAlignedBB method_1173(BlockPos var1) {
        IBlockState var11;
        if (!((var11 = mc.theWorld.getBlockState(var1)).getBlock() instanceof BlockBed)) {
            return null;
        } else {
            BlockPos var12 = var1.offset(var11.getValue(BlockBed.FACING).getOpposite());
            double var3 = Math.min(var1.getX(), var12.getX()) - 0.003;
            double var5 = Math.min(var1.getZ(), var12.getZ()) - 0.003;
            double var7 = Math.max(var1.getX(), var12.getX()) + 1 + 0.003;
            double var9 = Math.max(var1.getZ(), var12.getZ()) + 1 + 0.003;
            return AxisAlignedBB.fromBounds(var3, var1.getY() - 0.003, var5, var7, var1.getY() + 0.5625 + 0.003, var9);
        }
    }

    public record BedInfo(ItemStack itemStack, String string, int intValue) {
    }

    public record BedOutline(BlockPos blockPos, boolean flag, List<BedESP.BedInfo> list, int intValue) {
    }

    private record DefenseBlock(BlockPos blockPos, int intValue) {
    }
}
