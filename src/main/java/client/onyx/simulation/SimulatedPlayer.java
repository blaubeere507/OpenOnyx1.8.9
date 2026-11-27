package client.onyx.simulation;

import client.onyx.Onyx;
import client.onyx.event.impl.PositionEvent;
import client.onyx.util.IMinecraft;
import client.onyx.util.math.VecUtil;
import client.onyx.util.player.MovementPredictor;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class SimulatedPlayer implements TrajectorySource, IMinecraft {
    private static final double DOUBLE_1_01 = 1.0;
    private final EntityPlayer entityPlayer;
    private SimulatedPlayerInput simulatedPlayerInput;
    private Vec3 vec31;
    private Vec3 vec32;
    private AxisAlignedBB axisAlignedBB;
    private float floatValue2;
    private float floatValue;
    private boolean flag4;
    private double doubleValue;
    private int intValue;
    private boolean flag2;
    private boolean flag3;
    private boolean flag5;
    private boolean flag;
    private int intValue2;
    private boolean flag6;

    private boolean method_305() {
        return this.method_341().isMaterialInBB(this.axisAlignedBB.expand(-0.1F, -0.4F, -0.1F), Material.lava);
    }

    private void method_306() {
        this.doubleValue = 0.0;
    }

    public boolean isFlag() {
        return this.flag;
    }

    public static SimulatedPlayer method_307(SimulatedPlayerInput var0) {
        return method_321(mc.thePlayer, var0, MovementPredictor.method_1904(mc.thePlayer));
    }

    public boolean isFlag3() {
        return this.flag3;
    }

    public void method_308(SimulatedPlayerInput var1) {
        this.simulatedPlayerInput = var1;
    }

    private boolean method_309() {
        Block var2;
        return (
                    var2 = this.method_312(
                            new BlockPos(
                                MathHelper.floor_double(this.vec31.xCoord),
                                MathHelper.floor_double(this.axisAlignedBB.minY),
                                MathHelper.floor_double(this.vec31.zCoord)
                            )
                        )
                        .getBlock()
                )
                == Blocks.ladder
            || var2 == Blocks.vine;
    }

    public void method_310(Vec3 var1) {
        this.vec31 = var1;
        this.axisAlignedBB = MovementPredictor.method_1889(var1);
    }

    public float getFloatValue() {
        return this.floatValue;
    }

    public SimulatedPlayerInput getSimulatedPlayerInput() {
        return this.simulatedPlayerInput;
    }

    public void method_311(boolean var1) {
        this.flag2 = var1;
    }

    public int getIntValue() {
        return this.intValue;
    }

    private IBlockState method_312(BlockPos var1) {
        return this.method_341().getBlockState(var1);
    }

    private boolean method_313(AxisAlignedBB var1) {
        return this.method_341().getCollidingBoundingBoxes(this.entityPlayer, var1).isEmpty();
    }

    public boolean isFlag5() {
        return this.flag5;
    }

    public SimulatedPlayer method_314() {
        return new SimulatedPlayer(
            this.entityPlayer,
            this.simulatedPlayerInput,
            this.vec31,
            this.vec32,
            this.axisAlignedBB,
            this.floatValue2,
            this.floatValue,
            this.flag4,
            this.doubleValue,
            this.intValue,
            this.flag2,
            this.flag3,
            this.flag5,
            this.flag
        );
    }

    private float method_315() {
        float var2 = 0.02F;
        return this.simulatedPlayerInput.isFlag() ? (float)(var2 + var2 * 0.3) : var2;
    }

    public void method_316(Vec3 var1) {
        this.vec32 = var1;
    }

    private Vec3 method_317(Vec3 var1) {
        if (!this.method_309()) {
            return var1;
        } else {
            this.method_306();
            double var2 = MathHelper.clamp_double(var1.xCoord, -0.15F, 0.15F);
            double var4 = MathHelper.clamp_double(var1.zCoord, -0.15F, 0.15F);
            double var6;
            if ((var6 = Math.max(var1.yCoord, -0.15F)) < 0.0 && this.simulatedPlayerInput.inputKeys.flag2()) {
                var6 = 0.0;
            }

            return new Vec3(var2, var6, var4);
        }
    }

    public void method_318(int var1) {
        this.intValue2 = var1;
    }

    public void method_319(boolean var1) {
        this.flag3 = var1;
    }

    public Vec3 getVec32() {
        return this.vec32;
    }

    private float method_320() {
        return this.entityPlayer.getAIMoveSpeed();
    }

    private static SimulatedPlayer method_321(EntityPlayer var0, SimulatedPlayerInput var1, Vec3 var2) {
        SimulatedPlayer var6;
        Vec3 var10004 = MovementPredictor.method_1899(var0);
        AxisAlignedBB var10006 = var0.getEntityBoundingBox();
        float var10007 = var0.rotationYaw;
        float var10008 = var0.rotationPitch;
        boolean var10009 = var0.isSprinting();
        double var10010 = var0.fallDistance;
        EntityPlayerSP var4;
        boolean var10012;
        EntityPlayer var10013;
        if (var0 instanceof EntityPlayerSP && (var4 = (EntityPlayerSP)var0).movementInput != null && var4.movementInput.jump) {
            var10012 = true;
            var10013 = var0;
        } else {
            var10012 = false;
            var10013 = var0;
        }

        var6 = new SimulatedPlayer(
            var0,
            var1,
            var10004,
            var2,
            var10006,
            var10007,
            var10008,
            var10009,
            var10010,
            0,
            var10012,
            var10013.onGround,
            var0.isCollidedHorizontally,
            var0.isCollidedVertically
        );
        return var6;
    }

    private boolean method_322() {
        return this.method_341().isMaterialInBB(this.axisAlignedBB.expand(0.0, -0.4F, 0.0).contract(0.001, 0.001, 0.001), Material.water);
    }

    public void setDoubleValue(double var1) {
        this.doubleValue = var1;
    }

    public boolean isFlag2() {
        return this.flag2;
    }

    public float getFloatValue2() {
        return this.floatValue2;
    }

    public int getIntValue2() {
        return this.intValue2;
    }

    private void method_323(float var1, Vec3 var2) {
        this.vec32 = this.vec32.add(MovementPredictor.method_1900(var2, var1, this.floatValue2));
    }

    private IBlockState method_324() {
        return this.method_312(
            new BlockPos(
                MathHelper.floor_double(this.vec31.xCoord), MathHelper.floor_double(this.axisAlignedBB.minY) - 1, MathHelper.floor_double(this.vec31.zCoord)
            )
        );
    }

    public void method_325() {
        this.vec32 = VecUtil.method_1942(this.vec32, 0.42F);
        PotionEffect var2;
        if ((var2 = this.entityPlayer.getActivePotionEffect(Potion.jump)) != null) {
            this.vec32 = this.vec32.add(0.0, (var2.getAmplifier() + 1) * 0.1F, 0.0);
        }

        if (this.flag4) {
            float var3 = this.floatValue2 * (float) (Math.PI / 180.0);
            this.vec32 = this.vec32.add(-MathHelper.sin(var3) * 0.2F, 0.0, MathHelper.cos(var3) * 0.2F);
        }
    }

    private void method_326(Vec3 var1) {
        PositionEvent var19 = new PositionEvent(var1);
        Onyx.EVENT_BUS.post(var19);
        Vec3 var20 = var19.getVec31();
        if (this.method_338()) {
            var20 = var20.multiply(0.25, 0.05F, 0.25);
            this.vec32 = Vec3.ZERO;
        }

        Vec3 var2;
        Vec3 var21;
        if ((var2 = this.method_329(var21 = this.method_335(var20))).lengthSqr() > 1.0E-7) {
            this.vec31 = this.vec31.add(var2);
            this.axisAlignedBB = MovementPredictor.method_1889(this.vec31);
        }

        boolean var5;
        if (var21.xCoord != var2.xCoord) {
            var5 = true;
        } else {
            var5 = false;
        }

        boolean var6;
        if (var21.zCoord != var2.zCoord) {
            var6 = true;
        } else {
            var6 = false;
        }

        SimulatedPlayer var9;
        boolean var10;
        label80: {
            SimulatedPlayer var7;
            if (var5) {
                var7 = this;
            } else {
                if (!var6) {
                    var10 = false;
                    var9 = this;
                    break label80;
                }

                var7 = this;
            }

            var10 = true;
            var9 = var7;
        }

        var9.flag5 = var10;
        SimulatedPlayer var12;
        boolean var13;
        if (var21.yCoord == var2.yCoord) {
            var13 = false;
            var12 = this;
        } else {
            var13 = true;
            var12 = this;
        }

        SimulatedPlayer var15;
        boolean var16;
        label73: {
            var12.flag = var13;
            SimulatedPlayer var14;
            if (!this.flag) {
                var14 = this;
            } else {
                if (var21.yCoord < 0.0) {
                    var16 = true;
                    var15 = this;
                    break label73;
                }

                var14 = this;
            }

            var16 = false;
            var15 = var14;
        }

        var15.flag3 = var16;
        SimulatedPlayer var17;
        if (this.flag3) {
            this.method_306();
            var17 = this;
        } else {
            if (var2.yCoord < 0.0) {
                this.doubleValue = this.doubleValue - var2.yCoord;
            }

            var17 = this;
        }

        Vec3 var22 = var17.vec32;
        if (this.flag5 || this.flag) {
            Vec3 var10001;
            double var10003;
            SimulatedPlayer var10004;
            if (var5) {
                var10003 = 0.0;
                var10004 = this;
            } else {
                var10003 = var22.xCoord;
                var10004 = this;
            }

            double var24;
            boolean var10005;
            if (var10004.flag3) {
                var24 = 0.0;
                var10005 = var6;
            } else {
                var24 = var22.yCoord;
                var10005 = var6;
            }

            var10001 = new Vec3(var10003, var24, var10005 ? 0.0 : var22.zCoord);
            this.vec32 = var10001;
        }
    }

    public void method_327() {
        this.method_325();
    }

    public void method_328(AxisAlignedBB var1) {
        this.axisAlignedBB = var1;
    }

    private Vec3 method_329(Vec3 var1) {
        double var2 = var1.xCoord;
        double var4 = var1.yCoord;
        double var6 = var1.zCoord;
        double var8 = var2;
        double var10 = var6;
        AxisAlignedBB var12 = this.axisAlignedBB;
        AxisAlignedBB var13 = this.axisAlignedBB;

        List var14;
        Iterator var45;
        for (Iterator var33 = var45 = (var14 = this.method_341().getCollidingBoundingBoxes(this.entityPlayer, var13.addCoord(var2, var4, var6))).iterator();
            var33.hasNext();
            var33 = var45
        ) {
            AxisAlignedBB var16 = (AxisAlignedBB)var45.next();
            var4 = var16.calculateYOffset(var13, var4);
        }

        var13 = var13.offset(0.0, var4, 0.0);
        boolean var34;
        if (!this.flag3 && (var1.yCoord == var4 || !(var1.yCoord < 0.0))) {
            var34 = false;
        } else {
            var34 = true;
        }

        Iterator var65;
        for (Iterator var35 = var65 = var14.iterator(); var35.hasNext(); var35 = var65) {
            AxisAlignedBB var17 = (AxisAlignedBB)var65.next();
            var2 = var17.calculateXOffset(var13, var2);
        }

        var13 = var13.offset(var2, 0.0, 0.0);

        for (Iterator var36 = var65 = var14.iterator(); var36.hasNext(); var36 = var65) {
            AxisAlignedBB var70 = (AxisAlignedBB)var65.next();
            var6 = var70.calculateZOffset(var13, var6);
        }

        var13 = var13.offset(0.0, 0.0, var6);
        float var67 = this.entityPlayer.stepHeight;
        if (this.entityPlayer.stepHeight > 0.0F && var34 && (var8 != var2 || var10 != var6)) {
            double var71 = var2;
            double var19 = var4;
            double var21 = var6;
            var4 = var67;
            List var52 = this.method_341().getCollidingBoundingBoxes(this.entityPlayer, var12.addCoord(var8, var4, var10));
            AxisAlignedBB var46 = var12.addCoord(var8, 0.0, var10);
            double var23 = var4;

            Iterator var25;
            for (Iterator var37 = var25 = var52.iterator(); var37.hasNext(); var37 = var25) {
                var23 = ((AxisAlignedBB)var25.next()).calculateYOffset(var46, var23);
            }

            AxisAlignedBB var62 = var12.offset(0.0, var23, 0.0);
            double var72 = var8;

            Iterator var27;
            for (Iterator var38 = var27 = var52.iterator(); var38.hasNext(); var38 = var27) {
                var72 = ((AxisAlignedBB)var27.next()).calculateXOffset(var62, var72);
            }

            AxisAlignedBB var63 = var62.offset(var72, 0.0, 0.0);
            double var73 = var10;

            Iterator var47;
            for (Iterator var39 = var47 = var52.iterator(); var39.hasNext(); var39 = var47) {
                AxisAlignedBB var29 = (AxisAlignedBB)var47.next();
                var73 = var29.calculateZOffset(var63, var73);
            }

            AxisAlignedBB var64 = var63.offset(0.0, 0.0, var73);
            AxisAlignedBB var48 = var12;
            double var74 = var4;

            Iterator var31;
            for (Iterator var40 = var31 = var52.iterator(); var40.hasNext(); var40 = var31) {
                var74 = ((AxisAlignedBB)var31.next()).calculateYOffset(var48, var74);
            }

            AxisAlignedBB var49 = var48.offset(0.0, var74, 0.0);
            double var75 = var8;

            Iterator var54;
            for (Iterator var41 = var54 = var52.iterator(); var41.hasNext(); var41 = var54) {
                var75 = ((AxisAlignedBB)var54.next()).calculateXOffset(var49, var75);
            }

            AxisAlignedBB var50 = var49.offset(var75, 0.0, 0.0);
            var8 = var10;

            Iterator var56;
            for (Iterator var42 = var56 = var52.iterator(); var42.hasNext(); var42 = var56) {
                AxisAlignedBB var68 = (AxisAlignedBB)var56.next();
                var8 = var68.calculateZOffset(var50, var8);
            }

            AxisAlignedBB var51 = var50.offset(0.0, 0.0, var8);
            List var43;
            if (var72 * var72 + var73 * var73 > var75 * var75 + var8 * var8) {
                var2 = var72;
                var6 = var73;
                var4 = -var23;
                var13 = var64;
                var43 = var52;
            } else {
                var2 = var75;
                var6 = var8;
                var4 = -var74;
                var13 = var51;
                var43 = var52;
            }

            Iterator var57;
            for (Iterator var44 = var57 = var43.iterator(); var44.hasNext(); var44 = var57) {
                AxisAlignedBB var69 = (AxisAlignedBB)var57.next();
                var4 = var69.calculateYOffset(var13, var4);
            }

            if (var71 * var71 + var21 * var21 >= var2 * var2 + var6 * var6) {
                var2 = var71;
                var4 = var19;
                var6 = var21;
            }
        }

        return new Vec3(var2, var4, var6);
    }

    public void method_330(boolean var1) {
        this.flag5 = var1;
    }

    public static SimulatedPlayer method_331(EntityPlayer var0, SimulatedPlayerInput var1) {
        return method_321(var0, var1, MovementPredictor.method_1899(var0).subtract(MovementPredictor.method_1903(var0)));
    }

    @Override
    public Vec3 getVec31() {
        return this.vec31;
    }

    private void method_332(Vec3 var1) {
        boolean var7 = this.entityPlayer.capabilities.isFlying;
        double var3 = this.vec32.yCoord;
        if (this.method_322() && !var7) {
            double var16 = this.vec31.yCoord;
            float var14 = 0.8F;
            float var8 = 0.02F;
            float var9;
            if ((var9 = EnchantmentHelper.getDepthStriderModifier(this.entityPlayer)) > 3.0F) {
                var9 = 3.0F;
            }

            if (!this.flag3) {
                var9 *= 0.5F;
            }

            if (var9 > 0.0F) {
                var14 += (0.54600006F - var14) * var9 / 3.0F;
                var8 += (this.method_320() * 1.0F - var8) * var9 / 3.0F;
            }

            this.method_323(var8, var1);
            this.method_326(this.vec32);
            this.vec32 = this.vec32.multiply(var14, 0.8F, var14).add(0.0, -0.02, 0.0);
            if (this.flag5 && this.method_333(this.vec32.xCoord, this.vec32.yCoord + 0.6F - this.vec31.yCoord + var16, this.vec32.zCoord)) {
                this.vec32 = VecUtil.method_1942(this.vec32, 0.3F);
                return;
            }
        } else if (this.method_305() && !var7) {
            double var15 = this.vec31.yCoord;
            this.method_323(0.02F, var1);
            this.method_326(this.vec32);
            this.vec32 = this.vec32.scale(0.5).add(0.0, -0.02, 0.0);
            if (this.flag5 && this.method_333(this.vec32.xCoord, this.vec32.yCoord + 0.6F - this.vec31.yCoord + var15, this.vec32.zCoord)) {
                this.vec32 = VecUtil.method_1942(this.vec32, 0.3F);
                return;
            }
        } else {
            float var5 = 0.91F;
            if (this.flag3) {
                var5 = this.method_324().getBlock().slipperiness * 0.91F;
            }

            float var6 = 0.16277136F / (var5 * var5 * var5);
            float var11;
            if (!this.flag3) {
                var11 = this.method_315();
            } else {
                var11 = this.method_320() * var6;
            }

            this.method_323(var11, var1);
            this.vec32 = this.method_317(this.vec32);
            this.method_326(this.vec32);
            if (this.flag5 && this.method_309()) {
                this.vec32 = VecUtil.method_1942(this.vec32, 0.2);
            }

            double var12;
            if (var7) {
                var12 = var3 * 0.6;
            } else {
                var12 = (this.vec32.yCoord - 0.08) * 0.98F;
            }

            this.vec32 = new Vec3(this.vec32.xCoord * var5, var12, this.vec32.zCoord * var5);
            if (var7) {
                this.method_306();
            }
        }
    }

    public AxisAlignedBB getAxisAlignedBB() {
        return this.axisAlignedBB;
    }

    public Vec3 getVec312() {
        return this.vec31;
    }

    public EntityPlayer getEntityPlayer() {
        return this.entityPlayer;
    }

    private boolean method_333(double var1, double var3, double var5) {
        AxisAlignedBB var7;
        return this.method_313(var7 = this.axisAlignedBB.offset(var1, var3, var5)) && !this.method_341().isAnyLiquid(var7);
    }

    public void method_334(boolean var1) {
        this.flag4 = var1;
    }

    public boolean isFlag4() {
        return this.flag4;
    }

    private Vec3 method_335(Vec3 var1) {
        var var17 = this;
        if (this.flag3 && !(var1.yCoord > 0.0)) {
            double var2 = var1.xCoord;
            double var4 = var1.zCoord;
            double var6 = 0.05;
            double var8 = var2;

            while (var8 != 0.0 && var17.method_313(var17.axisAlignedBB.offset(var2, -1.0, 0.0))) {
                if (var2 < var6 && var2 >= -var6) {
                    var2 = 0.0;
                    var8 = 0.0;
                } else if (var2 > 0.0) {
                    var8 = var2 -= var6;
                } else {
                    var8 = var2 += var6;
                }
            }

            double var10 = var4;

            while (var10 != 0.0 && var17.method_313(var17.axisAlignedBB.offset(0.0, -1.0, var4))) {
                if (var4 < var6 && var4 >= -var6) {
                    var4 = 0.0;
                    var10 = 0.0;
                } else if (var4 > 0.0) {
                    var10 = var4 -= var6;
                } else {
                    var10 = var4 += var6;
                }
            }

            double var14 = var2;

            while (var14 != 0.0 && var4 != 0.0 && var17.method_313(var17.axisAlignedBB.offset(var2, -1.0, var4))) {
                double var12;
                if (var2 < var6 && var2 >= -var6) {
                    var2 = 0.0;
                    var12 = var4;
                } else if (var2 > 0.0) {
                    var2 -= var6;
                    var12 = var4;
                } else {
                    var2 += var6;
                    var12 = var4;
                }

                if (var12 < var6 && var4 >= -var6) {
                    var4 = 0.0;
                    var14 = var2;
                } else if (var4 > 0.0) {
                    var4 -= var6;
                    var14 = var2;
                } else {
                    var4 += var6;
                    var14 = var2;
                }
            }

            if (var1.xCoord != var2 || var1.zCoord != var4) {
                var17.flag6 = true;
            }

            return !var17.method_340() ? var1 : new Vec3(var2, var1.yCoord, var4);
        } else {
            return var1;
        }
    }

    public void method_336(boolean var1) {
        this.flag = var1;
    }

    public SimulatedPlayer(
        EntityPlayer var1,
        SimulatedPlayerInput var2,
        Vec3 var3,
        Vec3 var4,
        AxisAlignedBB var5,
        float var6,
        float var7,
        boolean var8,
        double var9,
        int var11,
        boolean var12,
        boolean var13,
        boolean var14,
        boolean var15
    ) {
        this.entityPlayer = var1;
        this.simulatedPlayerInput = var2;
        this.vec31 = var3;
        this.vec32 = var4;
        this.axisAlignedBB = var5;
        this.floatValue2 = var6;
        this.floatValue = var7;
        this.flag4 = var8;
        this.doubleValue = var9;
        this.intValue = var11;
        this.flag2 = var12;
        this.flag3 = var13;
        this.flag5 = var14;
        this.flag = var15;
    }

    @Override
    public void method_304() {
        this.flag6 = false;
        if (!(this.vec31.yCoord <= -70.0)) {
            this.simulatedPlayerInput.method_303();
            if (this.intValue > 0) {
                this.intValue--;
            }

            this.flag2 = this.simulatedPlayerInput.inputKeys.flag();
            double var1 = this.vec32.xCoord;
            double var3 = this.vec32.yCoord;
            double var5 = this.vec32.zCoord;
            if (Math.abs(var1) < 0.005) {
                var1 = 0.0;
            }

            if (Math.abs(var3) < 0.005) {
                var3 = 0.0;
            }

            if (Math.abs(var5) < 0.005) {
                var5 = 0.0;
            }

            SimulatedPlayer var7;
            label36: {
                this.vec32 = new Vec3(var1, var3, var5);
                if (this.flag2) {
                    if (this.method_322() || this.method_305()) {
                        this.vec32 = this.vec32.add(0.0, 0.04F, 0.0);
                        var7 = this;
                        break label36;
                    }

                    if (this.flag3 && this.intValue == 0) {
                        this.method_325();
                        this.intValue = 10;
                        var7 = this;
                        break label36;
                    }
                } else {
                    this.intValue = 0;
                }

                var7 = this;
            }

            var1 = var7.simulatedPlayerInput.getFloatValue2() * 0.98;
            var3 = this.simulatedPlayerInput.getFloatValue22() * 0.98;
            this.method_332(new Vec3(var1, 0.0, var3));
            this.intValue2++;
        }
    }

    public double getDoubleValue() {
        return this.doubleValue;
    }

    public void method_337(int var1) {
        this.intValue = var1;
    }

    private boolean method_338() {
        return this.method_312(VecUtil.method_1956(this.vec31)).getBlock() == Blocks.web;
    }

    public void method_339(float var1) {
        this.floatValue2 = var1;
    }

    public boolean isFlag6() {
        return this.flag6;
    }

    private boolean method_340() {
        return !this.simulatedPlayerInput.isFlag2() && (this.simulatedPlayerInput.inputKeys.flag2() || this.simulatedPlayerInput.isFlag3());
    }

    private World method_341() {
        return this.entityPlayer.worldObj;
    }

    public void method_342(float var1) {
        this.floatValue = var1;
    }
}
