package client.onyx.placement;

import client.onyx.util.geometry.ClosestPoint;
import client.onyx.util.geometry.Ray;
import client.onyx.util.math.AabbUtil;
import client.onyx.util.player.BlockPlacer;
import java.util.Comparator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

public class PlacementPlan {
    private final PlacementPlanner placementPlanner;
    private final AimPointSelection aimPointSelection;
    private final ItemStack itemStack;
    private final EyePosition eyePosition;

    private static AxisAlignedBB method_28(BlockPos var0) {
        return BlockPlacer.method_25(var0).offset(var0.getX(), var0.getY(), var0.getZ());
    }

    public PlacementPlanner getPlacementPlanner() {
        return this.placementPlanner;
    }

    public static Comparator<BlockPos> method_29(Ray var0) {
        return Comparator.comparingDouble(var1 -> {
            AxisAlignedBB var5 = method_28(var1);
            double var2;
            ClosestPoint var6;
            if ((var6 = var0.method_2036(var5)) != null) {
                var2 = var6.doubleValue();
            } else {
                var2 = Double.POSITIVE_INFINITY;
            }

            return -var2;
        });
    }

    public static Comparator<BlockPos> method_30(Vec3 var0) {
        return Comparator.comparingDouble(var1 -> -AabbUtil.method_2029(method_28(var1), var0));
    }

    public AimPointSelection getAimPointSelection() {
        return this.aimPointSelection;
    }

    public EyePosition getEyePosition() {
        return this.eyePosition;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public PlacementPlan(PlacementPlanner var1, AimPointSelection var2, ItemStack var3, EyePosition var4) {
        this.placementPlanner = var1;
        this.aimPointSelection = var2;
        this.itemStack = var3;
        this.eyePosition = var4;
    }
}
