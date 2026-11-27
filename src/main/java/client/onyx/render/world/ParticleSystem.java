package client.onyx.render.world;

import client.onyx.module.render.Particles;
import client.onyx.ui.theme.color.ColorUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockBasePressurePlate;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public final class ParticleSystem {
    private static final float FLOAT_0_11 = 0.1F;
    private static final double DOUBLE_1_0EN71 = 1.0E-7;
    private final long longValue = System.currentTimeMillis();
    private boolean flag = true;
    private final Particles particles;
    private final World world;
    private final ParticleSystem.SpawnReason spawnReason;
    private final int intValue;
    private final int intValue2;
    private double doubleValue3;
    private double doubleValue4;
    private double doubleValue6;
    private double doubleValue5;
    private double doubleValue;
    private double doubleValue2;
    private float floatValue;

    public void method_711() {
        if (this.flag) {
            if (!this.method_716()) {
                this.method_712();
            } else {
                long var1;
                if ((var1 = this.method_718()) >= this.spawnReason.longValue) {
                    this.method_712();
                } else {
                    ParticleSystem var4;
                    if (!this.particles.physics.method_146()) {
                        this.doubleValue3 = this.doubleValue3 + this.doubleValue5;
                        this.doubleValue4 = this.doubleValue4 + this.doubleValue;
                        this.doubleValue6 = this.doubleValue6 + this.doubleValue2;
                        var4 = this;
                    } else {
                        this.method_720(this.particles.physicsSpeed.getValue());
                        var4 = this;
                    }

                    var4.floatValue = Particle.method_723(var1, this.spawnReason.longValue2);
                }
            }
        }
    }

    public void method_712() {
        this.flag = false;
    }

    private boolean method_713(double var1, double var3, double var5) {
        BlockPos var7 = new BlockPos(var1, var3, var5);
        return this.world.isBlockLoaded(var7) && this.method_714(this.world.getBlockState(var7).getBlock());
    }

    private boolean method_714(Block var1) {
        return !(var1 instanceof BlockAir)
            && !(var1 instanceof BlockBush)
            && !(var1 instanceof BlockButton)
            && !(var1 instanceof BlockTorch)
            && !(var1 instanceof BlockLever)
            && !(var1 instanceof BlockBasePressurePlate)
            && !(var1 instanceof BlockCarpet)
            && !(var1 instanceof BlockLiquid);
    }

    public ParticleSystem(Particles var1, World var2, ParticleSystem.SpawnReason var3, int var4, int var5, Vec3 var6, Vec3 var7) {
        this.particles = var1;
        this.world = var2;
        this.spawnReason = var3;
        this.intValue = var4;
        this.intValue2 = var5;
        this.doubleValue3 = var6.xCoord;
        this.doubleValue4 = var6.yCoord;
        this.doubleValue6 = var6.zCoord;
        this.doubleValue5 = Particle.method_722(var7.xCoord);
        this.doubleValue = Particle.method_722(var7.yCoord);
        this.doubleValue2 = Particle.method_722(var7.zCoord);
    }

    private long method_715() {
        return Math.max(0L, System.currentTimeMillis() - this.longValue);
    }

    private boolean method_716() {
        return this.particles != null && this.particles.isToggled() && Particles.mc.theWorld == this.world;
    }

    public boolean isFlag() {
        return this.flag;
    }

    private double method_717(double var1, double var3, double var5) {
        BlockPos var9 = new BlockPos(var1, var3 - 1.0E-7, var5);
        if (!this.world.isBlockLoaded(var9)) {
            return Double.NaN;
        } else {
            IBlockState var8;
            if (!this.method_714((var8 = this.world.getBlockState(var9)).getBlock())) {
                return Double.NaN;
            } else {
                AxisAlignedBB var10;
                if ((var10 = var8.getBlock().getCollisionBoundingBox(this.world, var9, var8)) == null) {
                    return Double.NaN;
                } else if (var1 + 1.0E-7 < var10.minX || var1 - 1.0E-7 > var10.maxX || var5 + 1.0E-7 < var10.minZ || var5 - 1.0E-7 > var10.maxZ) {
                    return Double.NaN;
                } else {
                    return var3 <= var10.maxY + 1.0E-7 ? var10.maxY : Double.NaN;
                }
            }
        }
    }

    private long method_718() {
        return Particle.method_725(this.method_715(), this.particles.duration.getValue());
    }

    public void method_719(WorldRenderer var1, Vec3 var2, float var3, float var4, float var5, float var6, float var7) {
        if (this.flag && !(this.floatValue <= 0.0F)) {
            int var17 = this.particles.method_1090(this.intValue, this.intValue2);
            int var9 = Math.round((float)Math.pow(Math.clamp(this.floatValue, 0.0F, 1.0F), 0.72) * 255.0F);
            float var10 = 0.1F * this.particles.size.method_151();
            double var11 = this.doubleValue3 - var2.xCoord;
            double var13 = this.doubleValue4 - var2.yCoord;
            double var15 = this.doubleValue6 - var2.zCoord;
            method_721(
                var1,
                var11,
                var13,
                var15,
                var10,
                ColorUtils.method_201(var17),
                ColorUtils.method_210(var17),
                ColorUtils.method_214(var17),
                var9,
                var3,
                var4,
                var5,
                var6,
                var7
            );
            method_721(var1, var11, var13, var15, var10 * 0.5F, 255, 255, 255, var9, var3, var4, var5, var6, var7);
        }
    }

    private void method_720(double var1) {
        if (this.method_713(this.doubleValue3, this.doubleValue4, this.doubleValue6 + this.doubleValue2 * var1)) {
            this.doubleValue2 *= -0.8;
        }

        double var3 = 0.1F * this.particles.size.getValue() + 1.0E-7;
        double var10;
        if (this.doubleValue <= 0.0) {
            var10 = this.method_717(this.doubleValue3, this.doubleValue4 + this.doubleValue * var1 - var3, this.doubleValue6);
        } else {
            var10 = Double.NaN;
        }

        boolean var12;
        if (this.doubleValue <= 0.0) {
            var12 = Double.isFinite(var10);
        } else {
            var12 = this.method_713(this.doubleValue3, this.doubleValue4 + this.doubleValue * var1, this.doubleValue6);
        }

        if (var12) {
            this.doubleValue5 *= 0.999;
            this.doubleValue2 *= 0.999;
            this.doubleValue *= -0.7;
        }

        if (this.method_713(this.doubleValue3 + this.doubleValue5 * var1, this.doubleValue4, this.doubleValue6)) {
            this.doubleValue5 *= -0.8;
        }

        double var8 = this.doubleValue4 + this.doubleValue * var1;
        if (Double.isFinite(var10)) {
            var8 = Math.max(var8, var10 + var3);
        }

        this.doubleValue3 = this.doubleValue3 + this.doubleValue5 * var1;
        this.doubleValue4 = var8;
        this.doubleValue6 = this.doubleValue6 + this.doubleValue2 * var1;
        var3 = Math.pow(0.999999, var1);
        this.doubleValue5 /= var3;
        this.doubleValue -= 5.0E-5 * var1;
        this.doubleValue2 /= var3;
    }

    private static void method_721(
        WorldRenderer var0,
        double var1,
        double var3,
        double var5,
        float var7,
        int var8,
        int var9,
        int var10,
        int var11,
        float var12,
        float var13,
        float var14,
        float var15,
        float var16
    ) {
        var0.pos(var1 - var12 * var7 - var15 * var7, var3 - var13 * var7, var5 - var14 * var7 - var16 * var7)
            .tex(0.0, 0.0)
            .color(var8, var9, var10, var11)
            .endVertex();
        var0.pos(var1 - var12 * var7 + var15 * var7, var3 + var13 * var7, var5 - var14 * var7 + var16 * var7)
            .tex(0.0, 1.0)
            .color(var8, var9, var10, var11)
            .endVertex();
        var0.pos(var1 + var12 * var7 + var15 * var7, var3 + var13 * var7, var5 + var14 * var7 + var16 * var7)
            .tex(1.0, 1.0)
            .color(var8, var9, var10, var11)
            .endVertex();
        var0.pos(var1 + var12 * var7 - var15 * var7, var3 - var13 * var7, var5 + var14 * var7 - var16 * var7)
            .tex(1.0, 0.0)
            .color(var8, var9, var10, var11)
            .endVertex();
    }

    public static enum SpawnReason {
        ATTACK(5000L, 2000L),
        WALKING(3500L, 3000L);

        private final long longValue;
        private final long longValue2;

        private SpawnReason(long var3, long var5) {
            this.longValue = var3;
            this.longValue2 = var5;
        }
    }
}
