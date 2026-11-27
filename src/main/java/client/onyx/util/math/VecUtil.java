package client.onyx.util.math;

import java.util.Iterator;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;

public class VecUtil {
    public static double method_1939(Vec3 var0, double var1, double var3, double var5) {
        return var0.xCoord * var1 + var0.yCoord * var3 + var0.zCoord * var5;
    }

    public static double method_1940(Vec3 var0, Vec3 var1) {
        return method_1946(var0, var1.xCoord, var1.zCoord);
    }

    public static Vec3 method_1941(Vec3 var0, double var1, double var3, double var5) {
        return var0.multiply(var1, var3, var5);
    }

    public static Vec3 method_1942(Vec3 var0, double var1) {
        return new Vec3(var0.xCoord, var1, var0.zCoord);
    }

    public static boolean method_1943(Vec3 var0, double var1) {
        return Math.abs(var0.lengthSqr() - 1.0) < var1;
    }

    public static Vec3 method_1944(Vec3i var0) {
        return Vec3.upFromBottomCenterOf(var0, 1.0);
    }

    public static BlockPos method_1945(Vec3 var0, double var1, double var3, double var5) {
        return new BlockPos(Math.floor(var0.xCoord + var1), Math.floor(var0.yCoord + var3), Math.floor(var0.zCoord + var5));
    }

    public static double method_1946(Vec3 var0, double var1, double var3) {
        return Math.sqrt(method_1963(var0, var1, var3));
    }

    private static boolean method_1947(double var0) {
        return Math.abs(var0) < 1.0E-5;
    }

    public static double method_1948(Vec3 var0) {
        return Math.atan2(-var0.xCoord, var0.zCoord) * (180.0 / Math.PI);
    }

    public static Vec3 method_1949(Iterable<Vec3> var0) {
        double var1 = 0.0;
        double var3 = 0.0;
        double var5 = 0.0;
        int var7 = 0;

        Iterator var8;
        for (Iterator var10 = var8 = var0.iterator(); var10.hasNext(); var10 = var8) {
            Vec3 var11 = (Vec3)var8.next();
            var1 += var11.xCoord;
            var3 += var11.yCoord;
            var7++;
            var5 += var11.zCoord;
        }

        return new Vec3(var1 / var7, var3 / var7, var5 / var7);
    }

    public static Vec3 method_1950(Vec3 var0, double var1) {
        double var3;
        return method_1947(var3 = var0.lengthSqr()) ? Vec3.ZERO : var0.scale(var1 / Math.sqrt(var3));
    }

    public static Vec3 method_1951(Vec3 var0, double var1, Vec3 var3) {
        return new Vec3(Math.fma(var1, var3.xCoord, var0.xCoord), Math.fma(var1, var3.yCoord, var0.yCoord), Math.fma(var1, var3.zCoord, var0.zCoord));
    }

    public static double method_1952(Vec3 var0, Vec3 var1) {
        return method_1963(var0, var1.xCoord, var1.zCoord);
    }

    public static Vec3 method_1953(Vec3i var0) {
        return Vec3.atBottomCenterOf(var0);
    }

    public static Vec3 method_1954(Vec3i var0) {
        return Vec3.atCenterOf(var0);
    }

    public static long method_1955(Vec3i var0) {
        long var1 = var0.getX();
        long var3 = var0.getY();
        long var5 = var0.getZ();
        return var1 * var1 + var3 * var3 + var5 * var5;
    }

    public static BlockPos method_1956(Vec3 var0) {
        return new BlockPos(Math.floor(var0.xCoord), Math.floor(var0.yCoord), Math.floor(var0.zCoord));
    }

    public static Vec3 method_1957(Vec3 var0, double var1) {
        return new Vec3(var0.xCoord, var0.yCoord, var1);
    }

    public static Vec3 method_1958(Vec3 var0, double var1) {
        return new Vec3(var1, var0.yCoord, var0.zCoord);
    }

    public static boolean method_1959(Vec3 var0) {
        return method_1947(var0.lengthSqr());
    }

    public static Vec3 method_1960(Vec3i var0, double var1) {
        return Vec3.upFromBottomCenterOf(var0, var1);
    }

    public static AxisAlignedBB method_1961(Vec3 var0, double var1) {
        return new AxisAlignedBB(var0.xCoord - var1, var0.yCoord - var1, var0.zCoord - var1, var0.xCoord + var1, var0.yCoord + var1, var0.zCoord + var1);
    }

    public static Vec3 method_1962(Vec3 var0, double var1, double var3, double var5) {
        return new Vec3(var1, var3, var5);
    }

    public static double method_1963(Vec3 var0, double var1, double var3) {
        var1 = var0.xCoord - var1;
        var3 = var0.zCoord - var3;
        return var1 * var1 + var3 * var3;
    }

    public static boolean method_1964(Vec3 var0) {
        return method_1943(var0, 1.0E-4);
    }

    public static double method_1965(Vec3 var0, Vec3i var1) {
        return method_1963(var0, var1.getX(), var1.getZ());
    }

    public static Vec3 method_1966(Vec3 var0) {
        return method_1964(var0) ? var0 : var0.normalize();
    }
}
