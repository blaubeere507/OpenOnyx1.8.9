package client.onyx.placement;

import client.onyx.util.IMinecraft;
import client.onyx.util.math.AabbUtil;
import client.onyx.util.player.BlockPlacer;
import client.onyx.util.player.MovementPredictor;
import java.util.Comparator;
import java.util.List;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;

public class PlacementPlanner implements IMinecraft {
    private final List<? extends Vec3i> list;
    private final Comparator<BlockPos> comparator;

    public static PlacementPlanner method_31() {
        return new PlacementPlanner(SearchPattern.NO_OFFSET.getList(), Comparator.comparingDouble(var0 -> {
            Vec3 var2 = MovementPredictor.method_1899(mc.thePlayer);
            return -AabbUtil.method_2029(BlockPlacer.method_25(var0).offset(var0.getX(), var0.getY(), var0.getZ()), var2);
        }));
    }

    public List<? extends Vec3i> getList() {
        return this.list;
    }

    public Comparator<BlockPos> getComparator() {
        return this.comparator;
    }

    public PlacementPlanner(List<? extends Vec3i> var1, Comparator<BlockPos> var2) {
        this.list = var1;
        this.comparator = var2;
    }
}
