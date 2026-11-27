package client.onyx.placement;

import client.onyx.rotation.Rotation;
import client.onyx.rotation.RotationManager;
import client.onyx.util.IMinecraft;
import client.onyx.util.geometry.AlignedFace;
import client.onyx.util.math.AabbUtil;
import client.onyx.util.player.BlockPlacer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;

public final class PlacementSearch implements IMinecraft {
    private static final Comparator<PlacementSearch.FaceHit> comparator = Comparator.<PlacementSearch.FaceHit>comparingDouble(
            var0 -> var0.vec31.subtract(0.5, 0.5, 0.5).multiply(new Vec3(var0.enumFacing.getDirectionVec())).lengthSqr()
        )
        .thenComparingDouble(var0 -> var0.vec31.yCoord);

    private static boolean method_38(IBlockState var0, BlockPos var1) {
        AxisAlignedBB var3;
        return !method_40(var0)
            && (var3 = BlockPlacer.method_16(var0, var1)).maxY >= 1.0
            && var3.minX <= 0.5
            && var3.maxX >= 0.5
            && var3.minZ <= 0.5
            && var3.maxZ >= 0.5;
    }

    private static PlacementSearch.FaceHit method_39(IBlockState var0, BlockPos var1, PlacementSearch.Candidate var2, PlacementPlan var3) {
        List var4 = List.of(BlockPlacer.method_16(var0, var1));
        PlacementSearch.FaceHit var5 = null;
        Iterator var13 = var4.iterator();

        label34:
        while (true) {
            for (Iterator var10 = var13; var10.hasNext(); var10 = var13) {
                AlignedFace var11;
                AlignedFace var7 = var11 = AabbUtil.method_2028((AxisAlignedBB)var13.next(), var2.enumFacing);
                if (var7.getVec31().yCoord >= 0.9) {
                    AlignedFace var8;
                    AlignedFace var9;
                    if ((var8 = var7.method_2066(0.6).method_2063()) != null) {
                        var9 = var8;
                    } else {
                        var9 = var11;
                    }

                    var7 = var9;
                }

                Vec3 var14;
                if ((var14 = var3.getAimPointSelection().getAimPoint().method_32(var7, var1)) != null) {
                    PlacementSearch.FaceHit var12 = new PlacementSearch.FaceHit(var11, var2.enumFacing, var14);
                    if (var5 == null || comparator.compare(var12, var5) > 0) {
                        var5 = var12;
                    }
                    continue label34;
                }
            }

            return var5;
        }
    }

    private static boolean method_40(IBlockState var0) {
        Material var2;
        return (var2 = var0.getBlock().getMaterial()) == Material.air || var2.isLiquid();
    }

    private static PlacementSearch.Candidate method_41(BlockPos var0, PlacementSearch.PlaceType var1, PlacementPlan var2) {
        BlockPos var3 = var0;
        Vec3 var7 = var2.getEyePosition().method_36();
        ArrayList var4 = new ArrayList(6);
        EnumFacing[] var5;
        int var6 = (var5 = EnumFacing.values()).length;
        int var11 = 0;

        for (int var10 = 0; var10 < var6; var10 = ++var11) {
            EnumFacing var8 = var5[var11];
            PlacementSearch.Candidate var9;
            if ((var9 = method_43(var3, var8, var1)) != null && (var2.getAimPointSelection().isFlag() || !(var9.method_34(var7) < 0.0))) {
                var4.add(var9);
            }
        }

        Rotation var15 = RotationManager.INSTANCE.getRotation2();
        PlacementSearch.Candidate var16 = null;
        float var12 = Float.POSITIVE_INFINITY;
        Iterator var17 = var4.iterator();

        while (var17.hasNext()) {
            PlacementSearch.Candidate var18;
            Rotation var13 = Rotation.method_1058((var18 = (PlacementSearch.Candidate)var17.next()).vec31, var7);
            float var14;
            if ((var14 = var15.method_1047(var13)) < var12) {
                var12 = var14;
                var16 = var18;
            }
        }

        return var16;
    }

    public static PlacementTarget method_42(BlockPos var0, PlacementPlan var1) {
        BlockPos var2 = var0;
        if (method_38(BlockPlacer.method_20(var2), var2)) {
            return null;
        } else {
            ArrayList<Vec3i> var5 = new ArrayList<>(var1.getPlacementPlanner().getList());
            Comparator<BlockPos> var3 = var1.getPlacementPlanner().getComparator();
            var5.sort((var2x, var3x) -> var3.compare(var2.add(var3x), var2.add(var2x)));
            Iterator var14;
            Iterator var6 = var14 = var5.iterator();

            while (var6.hasNext()) {
                Vec3i var10 = (Vec3i)var14.next();
                IBlockState var4;
                BlockPos var11;
                if (method_38(var4 = BlockPlacer.method_20(var11 = var2.add(var10)), var11)) {
                    var6 = var14;
                } else {
                    PlacementSearch.PlaceType var7;
                    if (method_40(var4)) {
                        var7 = PlacementSearch.PlaceType.PLACE_AT_NEIGHBOR;
                    } else {
                        var7 = PlacementSearch.PlaceType.REPLACE_EXISTING_BLOCK;
                    }

                    if (var7 == PlacementSearch.PlaceType.REPLACE_EXISTING_BLOCK && !BlockPlacer.method_22(var4, var11, var1.getItemStack())) {
                        var6 = var14;
                    } else {
                        PlacementSearch.Candidate var12;
                        if ((var12 = method_41(var11, var7, var1)) == null) {
                            var6 = var14;
                        } else {
                            var0 = var12.blockPos;
                            PlacementSearch.FaceHit var13;
                            if ((var13 = method_39(BlockPlacer.method_20(var12.blockPos), var0, var12, var1)) != null) {
                                Rotation var9 = Rotation.method_1058(var13.vec31.add(var0.getX(), var0.getY(), var0.getZ()), var1.getEyePosition().method_36());
                                return new PlacementTarget(var0, var11, var13.enumFacing, var13.alignedFace.getVec32().yCoord + var0.getY(), var9);
                            }

                            var6 = var14;
                        }
                    }
                }
            }

            return null;
        }
    }

    private static PlacementSearch.Candidate method_43(BlockPos var0, EnumFacing var1, PlacementSearch.PlaceType var2) {
        switch (var2) {
            case PLACE_AT_NEIGHBOR:
                IBlockState var3;
                if ((var3 = BlockPlacer.method_23(var0 = var0.offset(var1.getOpposite()))) == null) {
                    return null;
                } else {
                    if (var3.getBlock().isReplaceable(mc.theWorld, var0)) {
                        return null;
                    }

                    return new PlacementSearch.Candidate(var0, var1);
                }
            case REPLACE_EXISTING_BLOCK:
                return new PlacementSearch.Candidate(var0, var1);
            default:
                return null;
        }
    }

    private PlacementSearch() {
    }

    private static final class Candidate {
        private final BlockPos blockPos;
        private final EnumFacing enumFacing;
        private final Vec3 vec31;

        private double method_34(Vec3 var1) {
            Vec3 var3 = var1.subtract(this.vec31);
            return var3.dotProduct(new Vec3(this.enumFacing.getDirectionVec())) / var3.length();
        }

        private Candidate(BlockPos var1, EnumFacing var2) {
            this.blockPos = var1;
            this.enumFacing = var2;
            this.vec31 = AabbUtil.method_2024(
                new AxisAlignedBB(var1.getX(), var1.getY(), var1.getZ(), var1.getX() + 1.0, var1.getY() + 1.0, var1.getZ() + 1.0), var2
            );
        }
    }

    private record FaceHit(AlignedFace alignedFace, EnumFacing enumFacing, Vec3 vec31) {
    }

    public static enum PlaceType {
        PLACE_AT_NEIGHBOR,
        REPLACE_EXISTING_BLOCK;
    }
}
