package client.onyx.placement;

import client.onyx.util.math.VecUtil;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3i;

public enum SearchPattern {
    NO_OFFSET(List.of(BlockPos.ORIGIN)),
    NORMAL(method_50(0, -1, 1)),
    DOWN(method_50(0, -1, 1, -2, 2)),
    FULL(method_50(0, -1, 1, -2, 2, -3, 3, -4, 4));

    private final List<BlockPos> list;

    public boolean method_49(int var1, int var2, int var3) {
        return this.list.contains(new BlockPos(var1, var2, var3));
    }

    public List<BlockPos> getList() {
        return this.list;
    }

    private static List<BlockPos> method_50(int... var0) {
        int[] var1 = var0;
        ArrayList var17 = new ArrayList(var0.length * var0.length * 2);
        int[] var2 = var0;
        int var3 = var0.length;
        int var4 = 0;

        for (int var15 = 0; var15 < var3; var15 = ++var4) {
            int var5 = var2[var4];
            int[] var6 = var1;
            int var7 = var1.length;
            int var8 = 0;

            for (int var14 = 0; var14 < var7; var14 = ++var8) {
                int var9 = var6[var8];
                long var10 = new BlockPos(var5, 0, var9).toLong();
                long var12 = new BlockPos(var5, -1, var9).toLong();
                if (!var17.contains(var10)) {
                    var17.add(var10);
                }

                if (!var17.contains(var12)) {
                    var17.add(var12);
                }
            }
        }

        ArrayList var18 = new ArrayList(var17.size());

        Iterator var19;
        for (Iterator var16 = var19 = var17.iterator(); var16.hasNext(); var16 = var19) {
            long var20 = (Long)var19.next();
            var18.add(BlockPos.fromLong(var20));
        }

        var18.sort(SearchPattern.Offsets.comparator);
        return List.copyOf(var18);
    }

    private SearchPattern(List<BlockPos> var3) {
        this.list = var3;
    }

    private static final class Offsets {
        private static final Comparator<Vec3i> comparator = Comparator.comparingLong(VecUtil::method_1955)
            .thenComparingInt(Vec3i::getY)
            .thenComparingInt(Vec3i::getX)
            .thenComparingInt(Vec3i::getZ);
    }
}
