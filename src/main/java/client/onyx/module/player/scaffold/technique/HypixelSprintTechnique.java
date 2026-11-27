package client.onyx.module.player.scaffold.technique;

import client.onyx.event.impl.JumpEvent;
import client.onyx.event.impl.MovementInputEvent;
import client.onyx.event.impl.PlayerUpdateEvent;
import client.onyx.event.impl.PostPlayerUpdateEvent;
import client.onyx.event.impl.StrafeEvent;
import client.onyx.module.player.scaffold.Scaffold;
import client.onyx.placement.PlacementTarget;
import client.onyx.rotation.MovementCorrection;
import client.onyx.rotation.Rotation;
import client.onyx.rotation.RotationManager;
import client.onyx.rotation.RotationMath;
import client.onyx.util.geometry.Ray;
import client.onyx.util.player.BlockPlacer;
import client.onyx.util.player.MovementInputState;
import client.onyx.util.player.MovementPredictor;
import client.onyx.util.world.BlockRaycast;
import client.onyx.util.world.HitboxMode;
import java.util.ArrayList;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

public class HypixelSprintTechnique extends ScaffoldTechnique {
    public static final HypixelSprintTechnique hypixelSprintTechnique = new HypixelSprintTechnique();
    private static final float[] floatValueArray = method_1754(180.0F);
    private static final float[] floatValueArray2 = method_1754(90.0F);
    private static final List<MovementInputState> list2 = List.of(
        MovementInputState.INSTANCE5,
        MovementInputState.INSTANCE,
        MovementInputState.INSTANCE3,
        MovementInputState.INSTANCE2,
        MovementInputState.INSTANCE6,
        MovementInputState.INSTANCE4,
        MovementInputState.INSTANCE9,
        MovementInputState.INSTANCE7
    );
    private static final int INT_21 = 2;
    private static final double DOUBLE_4_51 = 4.5;
    private static final int INT_101 = 10;
    private static final float FLOAT_90_01 = 90.0F;
    private static final float FLOAT_200_01 = 200.0F;
    private static final float FLOAT_83_01 = 83.0F;
    private static final float FLOAT_60_01 = 60.0F;
    private static final float FLOAT_45_01 = 45.0F;
    private static final int INT_31 = 3;
    private static final float FLOAT_0_11 = 0.1F;
    private float floatValue;
    private boolean flag2;
    private float floatValue2;
    private boolean flag22;
    private int intValue2;
    private float floatValue3;
    private boolean flag3;
    private float floatValue4;

    private float method_1744() {
        float var7 = mc.thePlayer.rotationYaw;
        boolean var5 = mc.gameSettings.keyBindForward.isKeyDown();
        boolean var4 = mc.gameSettings.keyBindBack.isKeyDown();
        boolean var3 = mc.gameSettings.keyBindLeft.isKeyDown();
        boolean var2 = mc.gameSettings.keyBindRight.isKeyDown();
        if (var5 && !var4) {
            if (var3 && !var2) {
                float var12;
                float var17 = var12 = var7 - 45.0F;
                float var22;
                return (var22 = var17 % 360.0F) < 0.0F ? var22 + 360.0F : var22;
            }

            if (var2 && !var3) {
                float var11;
                float var16 = var11 = var7 + 45.0F;
                float var21;
                return (var21 = var16 % 360.0F) < 0.0F ? var21 + 360.0F : var21;
            }
        } else if (var4 && !var5) {
            var7 += 180.0F;
            if (var3 && !var2) {
                float var10;
                float var15 = var10 = var7 + 45.0F;
                float var19;
                return (var19 = var15 % 360.0F) < 0.0F ? var19 + 360.0F : var19;
            }

            if (var2 && !var3) {
                float var9;
                float var14 = var9 = var7 - 45.0F;
                float var18;
                return (var18 = var14 % 360.0F) < 0.0F ? var18 + 360.0F : var18;
            }
        } else {
            if (var3 && !var2) {
                float var8;
                float var6 = var8 = var7 - 90.0F;
                float var13;
                return (var13 = var6 % 360.0F) < 0.0F ? var13 + 360.0F : var13;
            }

            if (var2 && !var3) {
                var7 += 90.0F;
            }
        }

        float var20;
        return (var20 = var7 % 360.0F) < 0.0F ? var20 + 360.0F : var20;
    }

    private HypixelSprintTechnique() {
        super("HypixelSprint");
    }

    @Override
    public PlacementTarget method_1727(Vec3 var1, boolean var2, Ray var3, ItemStack var4) {
        return null;
    }

    private MovementInputState method_1745(float var1) {
        var var5 = this;
        MovementInputState var2 = MovementInputState.INSTANCE5;
        float var3 = Float.MAX_VALUE;

        for (MovementInputState var7 : list2) {
            float var6;
            if ((var6 = Math.abs(MathHelper.wrapAngleTo180_float(MovementPredictor.method_1886(var5.floatValue3, var7) - var1))) < var3) {
                var3 = var6;
                var2 = var7;
            }
        }

        return var2;
    }

    @Override
    public void method_62() {
        this.flag3 = false;
        this.flag2 = false;
        this.flag22 = false;
        this.intValue2 = 0;
        if (mc.thePlayer != null) {
            this.floatValue3 = mc.thePlayer.rotationYaw;
            this.floatValue2 = 83.0F;
            this.floatValue4 = this.method_1744() - 180.0F;
            this.floatValue = 83.0F;
        }
    }

    @EventHandler
    private void onPostPlayerUpdate(PostPlayerUpdateEvent var1) {
        if (mc.thePlayer != null) {
            float var2 = this.method_1744() - 180.0F;
            mc.thePlayer.rotationYawHead = var2;
            mc.thePlayer.renderYawOffset = var2;
        }
    }

    @Override
    public void method_64() {
        RotationManager.INSTANCE.method_1018(null);
    }

    @EventHandler(
        priority = -500
    )
    private void onMovementInput(MovementInputEvent var1) {
        MovementInputState var3 = var1.getMovementInputState();
        if (method_1755() == MovementCorrection.SILENT && var3.method_1919()) {
            float var4 = MovementPredictor.method_1886(mc.thePlayer.rotationYaw, var3);
            var1.method_2621(this.method_1745(var4));
        }
    }

    private Rotation method_1746() {
        return new Rotation(this.floatValue3, this.floatValue2, true);
    }

    private MovingObjectPosition method_1747(float var1, float var2) {
        Entity var3;
        Vec3 var5 = (var3 = mc.getRenderViewEntity()).getPositionEyes(1.0F);
        return BlockRaycast.method_1994(4.5, HitboxMode.COLLIDER, false, var5, Vec3.directionFromRotation(var2, var1), var3);
    }

    private MovingObjectPosition method_1748(BlockPos var1) {
        if (!BlockPlacer.method_22(BlockPlacer.method_20(var1), var1, null)) {
            return null;
        } else {
            BlockPos var16 = null;
            EnumFacing var9 = null;
            double var4 = Double.MAX_VALUE;
            int var6 = -3;

            for (int var15 = -3; var15 <= 3; var15 = ++var6) {
                int var7 = -2;

                for (int var14 = -2; var14 <= 0; var14 = ++var7) {
                    int var8 = -3;

                    for (int var13 = -3; var13 <= 3; var13 = ++var8) {
                        BlockPos var3;
                        EnumFacing var10;
                        double var11;
                        if (!BlockPlacer.method_19(var3 = var1.add(var6, var7, var8))
                            && (var10 = method_1749(var3, var1)) != EnumFacing.DOWN
                            && !((var11 = mc.thePlayer.getDistanceSqToCenter(var3)) >= var4)) {
                            var4 = var11;
                            var16 = var3;
                            var9 = var10;
                        }
                    }
                }
            }

            return var16 == null ? null : new MovingObjectPosition(Vec3.atCenterOf(var16), var9, var16);
        }
    }

    @EventHandler(
        priority = -500
    )
    private void onJump(JumpEvent var1) {
        MovementCorrection var3;
        if ((var3 = method_1755()) == MovementCorrection.SILENT || var3 == MovementCorrection.STRICT) {
            var1.method_2618(this.floatValue3);
        }
    }

    private static EnumFacing method_1749(BlockPos var0, BlockPos var1) {
        int var5 = var1.getX() - var0.getX();
        int var2 = var1.getY() - var0.getY();
        int var3 = var1.getZ() - var0.getZ();
        if (Math.abs(var5) >= Math.abs(var2) && Math.abs(var5) >= Math.abs(var3)) {
            return var5 > 0 ? EnumFacing.EAST : EnumFacing.WEST;
        } else if (Math.abs(var2) < Math.abs(var5) || Math.abs(var2) < Math.abs(var3)) {
            return var3 > 0 ? EnumFacing.SOUTH : EnumFacing.NORTH;
        } else {
            return var2 > 0 ? EnumFacing.UP : EnumFacing.DOWN;
        }
    }

    private Rotation method_1750(MovingObjectPosition var1) {
        var var2 = this;
        float var10 = this.method_1744() + 90.0F;
        float[] var12 = floatValueArray;
        int var4 = floatValueArray.length;
        int var5 = 0;

        for (int var14 = 0; var14 < var4; var14 = ++var5) {
            float var6 = var12[var5];
            float[] var7 = floatValueArray2;
            int var8 = floatValueArray2.length;
            int var9 = 0;

            for (int var13 = 0; var13 < var8; var13 = ++var9) {
                float var15 = var7[var9];
                float var11 = var10 + var6;
                float var16 = MathHelper.clamp_float(var15, -90.0F, 90.0F);
                MovingObjectPosition var3;
                if ((var3 = var2.method_1747(var11, var16)) != null
                    && var3.typeOfHit == MovingObjectType.BLOCK
                    && var3.getBlockPos().equals(var1.getBlockPos())
                    && var3.sideHit == var1.sideHit) {
                    return new Rotation(var11, var16);
                }
            }
        }

        return null;
    }

    private void method_1751() {
        if (!this.flag2) {
            HypixelSprintTechnique var4;
            boolean var5;
            label18: {
                this.flag2 = true;
                float var6 = (float)(200.0 - Math.random());
                this.floatValue3 = this.floatValue3 + MathHelper.clamp_float(MathHelper.wrapAngleTo180_float(this.floatValue4 - this.floatValue3), -var6, var6);
                float var2 = mc.thePlayer.rotationYaw;
                this.floatValue3 = mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(this.floatValue3 - var2);
                this.floatValue2 = this.floatValue2 + MathHelper.clamp_float(MathHelper.wrapAngleTo180_float(this.floatValue - this.floatValue2), -var6, var6);
                HypixelSprintTechnique var3;
                if (!(Math.abs(MathHelper.wrapAngleTo180_float(this.floatValue3 - this.floatValue4)) <= 0.1F)) {
                    var3 = this;
                } else {
                    if (Math.abs(MathHelper.wrapAngleTo180_float(this.floatValue2 - this.floatValue)) <= 0.1F) {
                        var5 = true;
                        var4 = this;
                        break label18;
                    }

                    var3 = this;
                }

                var5 = false;
                var4 = var3;
            }

            var4.flag3 = var5;
        }
    }

    private void method_1752(BlockPos var1) {
        MovingObjectPosition var4;
        if ((var4 = this.method_1748(var1)) != null) {
            Rotation var2;
            if ((var2 = this.method_1750(var4)) == null) {
                this.flag3 = false;
                this.flag2 = true;
            } else {
                this.floatValue4 = var2.getYaw();
                this.floatValue = var2.getPitch();
                this.method_1751();
                if (mc.gameSettings.keyBindJump.isKeyDown() && mc.thePlayer.onGround) {
                    this.flag3 = false;
                }

                if (this.flag3) {
                    if (this.method_1753(var4.getBlockPos())) {
                        BlockPlacer.method_15(var4);
                        mc.thePlayer.swingItem();
                    }

                    this.intValue2 = 10;
                }
            }
        }
    }

    @EventHandler(
        priority = 1000
    )
    private void onPlayerUpdate(PlayerUpdateEvent var1) {
        if (mc.thePlayer != null && mc.theWorld != null && mc.playerController != null) {
            if (this.intValue2 > 0) {
                this.intValue2--;
            }

            this.flag2 = false;
            boolean var10 = Scaffold.INSTANCE.method_1670();
            BlockPos var7 = new BlockPos(mc.thePlayer).down();
            if (var10 && BlockPlacer.method_19(var7)) {
                this.method_1752(var7);
            }

            float var11;
            float var8 = Math.abs(MathHelper.wrapAngleTo180_float((var11 = this.method_1744() + 45.0F) - this.floatValue3));
            byte var4;
            byte var5;
            if (!(var8 > 60.0F)) {
                byte var3 = 10;
                var5 = 1;
                var4 = var3;
            } else {
                var5 = 2;
                var4 = 10;
            }

            int var9 = var4 - var5;
            if (this.intValue2 == var9 && !mc.gameSettings.keyBindJump.isKeyDown() && this.flag22) {
                this.floatValue3 = var11;
            }

            if (mc.thePlayer.onGround && mc.gameSettings.keyBindJump.isKeyDown()) {
                this.floatValue3 = this.method_1744();
            }

            HypixelSprintTechnique var6;
            if (method_1755() == MovementCorrection.CHANGE_LOOK) {
                RotationMath.method_1040(mc.thePlayer, this.method_1746());
                RotationManager.INSTANCE.method_1018(null);
                var6 = this;
            } else {
                RotationManager.INSTANCE.method_1018(this.method_1746());
                var6 = this;
            }

            var6.flag22 = true;
        }
    }

    private boolean method_1753(BlockPos var1) {
        double var2 = mc.playerController.getBlockReachDistance();
        return mc.thePlayer.getPositionEyes(1.0F).squareDistanceTo(Vec3.atCenterOf(var1)) <= var2 * var2;
    }

    @EventHandler(
        priority = -500
    )
    private void onStrafe(StrafeEvent var1) {
        MovementCorrection var2;
        float var4;
        if ((var2 = method_1755()) != MovementCorrection.SILENT && var2 != MovementCorrection.STRICT) {
            var4 = var1.getFloatValue2();
        } else {
            var4 = this.floatValue3;
        }

        var1.method_2615(MovementPredictor.method_1900(var1.getVec32(), var1.getFloatValue(), var4));
    }

    private static float[] method_1754(float var0) {
        float var2 = var0;
        ArrayList var1;
        (var1 = new ArrayList()).add(0.0F);
        var0 = 1.0F;

        for (float var4 = var0; var4 <= 20.0F; var4 = ++var0) {
            var1.add(-var0);
            var1.add(var0);
        }

        var0 = 22.0F;

        for (float var5 = 22.0F; var5 <= var2; var5 = var0) {
            var1.add(-var0);
            var1.add(var0);
            var0 += 2.0F;
        }

        float[] var9 = new float[var1.size()];
        int var3 = 0;

        for (int var6 = 0; var6 < var9.length; var6 = var3) {
            int var10001 = var3;
            float var10002 = (Float)var1.get(var3);
            var3++;
            var9[var10001] = var10002;
        }

        return var9;
    }

    private static MovementCorrection method_1755() {
        return Scaffold.INSTANCE.scaffoldRotationSettings.method_1032();
    }
}
