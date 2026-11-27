package client.onyx.util.world;

import client.onyx.util.IMinecraft;
import client.onyx.util.math.VecUtil;
import client.onyx.util.player.MovementInputState;
import client.onyx.util.player.MovementPredictor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class CollisionUtil implements IMinecraft {
    public static Vec3 method_1920(Vec3 var0, Vec3 var1, float var2) {
        Vec3 var7;
        if ((var7 = var1.subtract(var0)).lengthSqr() <= 1.0E-12) {
            return null;
        } else {
            List var18 = method_1922(var0, var1, var2);
            Vec3 var4 = var0;
            Vec3 var5 = var0.add(var7.scale(-1000.0));
            var7 = var1.add(var7.scale(1000.0));
            HashSet var6;
            HashSet var10000 = var6 = new HashSet();

            while (true) {
                var10000.clear();
                Iterator var15 = var18.iterator();

                while (var15.hasNext()) {
                    AxisAlignedBB var8;
                    if ((var8 = (AxisAlignedBB)var15.next()).isVecInside(var4)) {
                        var6.add(var8);
                    }
                }

                if (var6.isEmpty()) {
                    return var4;
                }

                Iterator var16 = var6.iterator();

                while (var16.hasNext()) {
                    if (((AxisAlignedBB)var16.next()).isVecInside(var1)) {
                        return null;
                    }
                }

                var0 = null;
                double var20 = Double.POSITIVE_INFINITY;
                Iterator var10 = var6.iterator();

                while (var10.hasNext()) {
                    AxisAlignedBB var11;
                    MovingObjectPosition var12;
                    if ((var12 = (var11 = (AxisAlignedBB)var10.next()).calculateIntercept(var7, var5)) == null) {
                        throw new IllegalArgumentException(
                            new StringBuilder()
                                .insert(0, "Raycast failed. This should be impossible. AABB=")
                                .append(var11)
                                .append(" from=")
                                .append(var0)
                                .append(" to=")
                                .append(var1)
                                .toString()
                        );
                    }

                    Vec3 var21 = var12.hitVec;
                    double var13;
                    if ((var13 = var12.hitVec.distanceToSqr(var1)) < var20) {
                        var20 = var13;
                        var0 = var21;
                    }
                }

                if (var0 == null) {
                    throw new IllegalStateException("Unable to resolve an edge collision");
                }

                var4 = var0;
                var10000 = var6;
                var18.removeAll(var6);
            }
        }
    }

    public static void method_1921() {
        mc.thePlayer.motionX = 0.0;
        mc.thePlayer.motionZ = 0.0;
    }

    private static List<AxisAlignedBB> method_1922(Vec3 var0, Vec3 var1, float var2) {
        AxisAlignedBB var10 = MovementPredictor.method_1889(var0);
        AxisAlignedBB var4 = MovementPredictor.method_1889(var1);
        AxisAlignedBB var11 = var10.union(var4);
        BlockPos var15 = VecUtil.method_1956(new Vec3(var11.minX - 0.3 - 1.0E-7, var11.minY - var2 - 1.0E-7, var11.minZ - 0.3 - 1.0E-7));
        BlockPos var12 = VecUtil.method_1956(new Vec3(var11.maxX + 0.3 + 1.0E-7, var11.minY + 1.0E-7, var11.maxZ + 0.3 + 1.0E-7));
        Vec3 var5;
        Vec3 var6 = var0.add((var5 = var1.subtract(var0)).scale(-1000.0));
        var1 = var1.add(var5.scale(1000.0));
        ArrayList var18 = new ArrayList();
        ArrayList var7 = new ArrayList();

        for (BlockPos var16 : BlockPos.getAllInBox(var15, var12)) {
            IBlockState var10001 = mc.theWorld.getBlockState(var16);
            var7.clear();
            method_1924(var10001, var16, var7);
            Iterator var17;
            Iterator var9 = var17 = var7.iterator();

            while (var9.hasNext()) {
                AxisAlignedBB var8 = (AxisAlignedBB)var17.next();
                if ((var8 = new AxisAlignedBB(var8.minX - 0.3, var8.minY - 1.0, var8.minZ - 0.3, var8.maxX + 0.3, var8.maxY + var2 + 0.05, var8.maxZ + 0.3))
                        .calculateIntercept(var6, var1)
                    == null) {
                    var9 = var17;
                } else {
                    var18.add(var8);
                    var9 = var17;
                }
            }
        }

        return var18;
    }

    public static MovementInputState method_1923(MovementInputState var0, float var1, float var2) {
        boolean var3 = var0.flag2();
        boolean var4 = var0.flag4();
        boolean var5 = var0.flag();
        boolean var6 = var0.flag3();
        float var7;
        if (var1 > -90.0F + var2 && var1 < 90.0F - var2) {
            var3 = true;
            var7 = var1;
        } else {
            if (var1 < -90.0F - var2 || var1 > 90.0F + var2) {
                var4 = true;
            }

            var7 = var1;
        }

        if (var7 > var2 && var1 < 180.0F - var2) {
            var6 = true;
        } else if (var1 > -180.0F + var2 && var1 < -var2) {
            var5 = true;
        }

        return new MovementInputState(var3, var4, var5, var6);
    }

    public static void method_1924(IBlockState var0, BlockPos var1, List<AxisAlignedBB> var2) {
        if (var0.getBlock().getMaterial() != Material.air) {
            AxisAlignedBB var3 = new AxisAlignedBB(
                var1.getX() - 1.0, var1.getY() - 1.0, var1.getZ() - 1.0, var1.getX() + 2.0, var1.getY() + 2.0, var1.getZ() + 2.0
            );
            var0.getBlock().addCollisionBoxesToList(mc.theWorld, var1, var0, var3, var2, null);
        }
    }

    public static Vec3 method_1925(Vec3 var0, Vec3 var1) {
        return method_1920(var0, var1, 0.5F);
    }

    public static float method_1926(Vec3 var0, float var1) {
        double var2 = VecUtil.method_1948(var0);
        float var5 = MathHelper.wrapAngleTo180_float(var1);
        return MathHelper.wrapAngleTo180_float((float)var2 - var5);
    }

    public static MovementInputState method_1927(MovementInputState var0, float var1) {
        return method_1923(var0, var1, 20.0F);
    }
}
