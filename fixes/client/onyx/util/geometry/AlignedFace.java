package client.onyx.util.geometry;

import client.onyx.util.math.AabbUtil;
import client.onyx.util.math.VecUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;

public class AlignedFace {
    private final Vec3 vec32;
    private final Vec3 vec31;

    public AlignedFace method_2061(Vec3i var1) {
        return new AlignedFace(this.vec32.add(var1.getX(), var1.getY(), var1.getZ()), this.vec31.add(var1.getX(), var1.getY(), var1.getZ()));
    }

    private Vec3[] method_2062() {
        Vec3 var2;
        if (Shape.method_2052((var2 = this.method_2074()).xCoord, 0.0)) {
            Vec3[] var10000 = new Vec3[2];
            boolean var10005 = false;
            var10000[0] = new Vec3(0.0, var2.yCoord, 0.0);
            var10000[1] = new Vec3(0.0, 0.0, var2.zCoord);
            return var10000;
        } else if (Shape.method_2052(var2.yCoord, 0.0)) {
            return new Vec3[]{new Vec3(var2.xCoord, 0.0, 0.0), new Vec3(0.0, 0.0, var2.zCoord)};
        } else if (Shape.method_2052(var2.zCoord, 0.0)) {
            return new Vec3[]{new Vec3(0.0, var2.yCoord, 0.0), new Vec3(var2.xCoord, 0.0, 0.0)};
        } else {
            throw new IllegalStateException(
                new StringBuilder().insert(0, "Face must be axis aligned for this function to work. dimensions=").append(var2).toString()
            );
        }
    }

    public AlignedFace method_2063() {
        return Shape.method_2052(this.method_2072(), 0.0) ? null : this;
    }

    public AlignedFace(Vec3 var1, Vec3 var2) {
        this.vec32 = new Vec3(Math.min(var1.xCoord, var2.xCoord), Math.min(var1.yCoord, var2.yCoord), Math.min(var1.zCoord, var2.zCoord));
        this.vec31 = new Vec3(Math.max(var1.xCoord, var2.xCoord), Math.max(var1.yCoord, var2.yCoord), Math.max(var1.zCoord, var2.zCoord));
    }

    public Vec3 method_2064() {
        ThreadLocalRandom var2 = ThreadLocalRandom.current();
        Vec3 var10000;
        double var10002;
        AlignedFace var10003;
        if (this.vec32.xCoord == this.vec31.xCoord) {
            var10002 = this.vec32.xCoord;
            var10003 = this;
        } else {
            var10002 = var2.nextDouble(this.vec32.xCoord, this.vec31.xCoord);
            var10003 = this;
        }

        double var3;
        AlignedFace var10004;
        if (var10003.vec32.yCoord == this.vec31.yCoord) {
            var3 = this.vec32.yCoord;
            var10004 = this;
        } else {
            var3 = var2.nextDouble(this.vec32.yCoord, this.vec31.yCoord);
            var10004 = this;
        }

        var10000 = new Vec3(
            var10002, var3, var10004.vec32.zCoord == this.vec31.zCoord ? this.vec32.zCoord : var2.nextDouble(this.vec32.zCoord, this.vec31.zCoord)
        );
        return var10000;
    }

    public Vec3 getVec31() {
        return this.vec31;
    }

    public Plane method_2065() {
        Vec3[] var2 = this.method_2062();
        return Plane.method_2077(this.vec32, var2[0], var2[1]);
    }

    public AlignedFace method_2066(double var1) {
        return new AlignedFace(
            new Vec3(this.vec32.xCoord, Math.max(this.vec32.yCoord, var1), this.vec32.zCoord),
            new Vec3(this.vec31.xCoord, Math.max(this.vec31.yCoord, var1), this.vec31.zCoord)
        );
    }

    public AlignedFace method_2067(Vec3 var1) {
        return new AlignedFace(this.vec32.add(var1), this.vec31.add(var1));
    }

    public AlignedFace method_2068(AxisAlignedBB var1) {
        return new AlignedFace(AabbUtil.method_2025(var1, this.vec32), AabbUtil.method_2025(var1, this.vec31));
    }

    public Vec3 getVec32() {
        return this.vec32;
    }

    public Vec3 method_2069(Shape var1) {
        Vec3[] var15 = this.method_2062();
        Plane var16 = Plane.method_2077(this.vec32, var15[0], var15[1]);
        List var5 = this.method_2071();
        Vec3 var17 = var16.method_2075(var1);
        Vec3 var4 = this.method_2070();
        if (var17 != null) {
            boolean var3 = true;
            Iterator var6 = var5.iterator();

            while (true) {
                List var13;
                if (var6.hasNext()) {
                    Vec3 var20 = ((LineSegment)var6.next()).method_2038(0.5);
                    Vec3 var9 = var20.subtract(var4);
                    if (!(var20.subtract(var17).dotProduct(var9) <= 0.0)) {
                        continue;
                    }

                    var3 = false;
                    var13 = var5;
                } else {
                    var13 = var5;
                }

                if (var13.isEmpty() || var3) {
                    return var17;
                }
                break;
            }
        }

        Vec3 var18 = null;
        double var19 = Double.POSITIVE_INFINITY;
        Iterator var8 = var5.iterator();

        label39:
        while (true) {
            for (Iterator var14 = var8; var14.hasNext(); var14 = var8) {
                PointPair var10;
                if ((var10 = ((LineSegment)var8.next()).method_2051(var1)) != null) {
                    double var11;
                    if ((var11 = var10.vec32().distanceToSqr(var10.vec31())) < var19) {
                        var19 = var11;
                        var18 = var10.vec32();
                    }
                    continue label39;
                }
            }

            if (var18 != null) {
                return var18;
            }

            if (var17 != null) {
                return var17;
            }

            return var4;
        }
    }

    public Vec3 method_2070() {
        return this.vec32.lerp(this.vec31, 0.5);
    }

    private List<LineSegment> method_2071() {
        ArrayList var1 = new ArrayList(4);
        Vec3[] var3;
        if (!VecUtil.method_1959((var3 = this.method_2062())[0])) {
            var1.add(new LineSegment(this.vec32, this.vec32.add(var3[0])));
            var1.add(new LineSegment(this.vec31, this.vec31.subtract(var3[0])));
        }

        if (!VecUtil.method_1959(var3[1])) {
            var1.add(new LineSegment(this.vec32, this.vec32.add(var3[1])));
            var1.add(new LineSegment(this.vec31, this.vec31.subtract(var3[1])));
        }

        return var1;
    }

    public double method_2072() {
        Vec3 var2;
        return (var2 = this.method_2074()).xCoord * var2.yCoord + var2.yCoord * var2.zCoord + var2.xCoord * var2.zCoord;
    }

    public LineSegment method_2073(Shape var1) {
        ArrayList<ClosestPoint> var2 = new ArrayList<>(4);
        Iterator var7;
        Iterator var6 = var7 = this.method_2071().iterator();

        while (var6.hasNext()) {
            LineSegment var4 = (LineSegment)var7.next();
            PointPair var5;
            if ((var5 = var1.method_2051(var4)) == null) {
                var6 = var7;
            } else {
                var2.add(new ClosestPoint(var5.vec31(), var5.vec31().distanceToSqr(var5.vec32())));
                var6 = var7;
            }
        }

        var2.sort((var0, var1x) -> Double.compare(var0.doubleValue(), var1x.doubleValue()));
        if (var2.size() < 2) {
            return null;
        } else {
            Vec3 var8 = ((ClosestPoint)var2.get(0)).vec31();
            Vec3 var9;
            return VecUtil.method_1959((var9 = ((ClosestPoint)var2.get(1)).vec31()).subtract(var8)) ? null : new LineSegment(var8, var9);
        }
    }

    public Vec3 method_2074() {
        return new Vec3(this.vec31.xCoord - this.vec32.xCoord, this.vec31.yCoord - this.vec32.yCoord, this.vec31.zCoord - this.vec32.zCoord);
    }
}
