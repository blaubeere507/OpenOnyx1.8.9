package client.onyx.util.player;

import client.onyx.event.impl.PostTickEvent;
import client.onyx.event.impl.WorldLoadEvent;
import client.onyx.rotation.Rotation;
import client.onyx.simulation.SimulatedPlayer;
import client.onyx.simulation.SimulatedPlayerInput;
import client.onyx.util.IMinecraft;
import client.onyx.util.math.VecUtil;
import client.onyx.util.world.CollisionUtil;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class MovementPredictor implements IMinecraft {
    public static final MovementPredictor INSTANCE = new MovementPredictor();
    private static int intValue;
    private static int intValue2;
    public static final float FLOAT_0_61 = 0.6F;
    public static final float FLOAT_1_81 = 1.8F;

    public static Vec3 method_1884(Vec3 var0, double var1) {
        return method_1914(var0, var1, 1.0, method_1885(mc.thePlayer));
    }

    public static MovementInputState method_1885(EntityPlayerSP var0) {
        return var0 != null && var0.movementInput != null
            ? new MovementInputState(var0.movementInput.untransformedForward, var0.movementInput.untransformedStrafe)
            : MovementInputState.INSTANCE8;
    }

    public static float method_1886(float var0, MovementInputState var1) {
        float var2 = var0;
        float var3;
        MovementInputState var4;
        if (var1.flag4() && !var1.flag2()) {
            var2 = var0 + 180.0F;
            var3 = -0.5F;
            var4 = var1;
        } else if (var1.flag2() && !var1.flag4()) {
            var3 = 0.5F;
            var4 = var1;
        } else {
            var3 = 1.0F;
            var4 = var1;
        }

        if (var4.flag() && !var1.flag3()) {
            var2 -= 90.0F * var3;
        }

        if (var1.flag3() && !var1.flag()) {
            var2 += 90.0F * var3;
        }

        return var2;
    }

    public static Rotation method_1887(Entity var0) {
        return new Rotation(var0.prevRotationYaw, var0.prevRotationPitch, true);
    }

    public static float method_1888(EntityPlayerSP var0, MovementInputState var1) {
        return method_1886(var0.rotationYaw, var1);
    }

    public static AxisAlignedBB method_1889(Vec3 var0) {
        double var1 = 0.3F;
        return new AxisAlignedBB(var0.xCoord - var1, var0.yCoord, var0.zCoord - var1, var0.xCoord + var1, var0.yCoord + 1.8F, var0.zCoord + var1);
    }

    public static double method_1890(Entity var0, Entity var1) {
        return Math.sqrt(method_1898(var0, var1));
    }

    public static boolean method_1891(EntityPlayerSP var0) {
        return method_1909(var0, method_1885(var0), 0.1, method_1899(var0));
    }

    public static double method_1892(AxisAlignedBB var0, Vec3 var1) {
        double var2 = Math.max(Math.max(var0.minX - var1.xCoord, var1.xCoord - var0.maxX), 0.0);
        double var4 = Math.max(Math.max(var0.minY - var1.yCoord, var1.yCoord - var0.maxY), 0.0);
        double var6 = Math.max(Math.max(var0.minZ - var1.zCoord, var1.zCoord - var0.maxZ), 0.0);
        return var2 * var2 + var4 * var4 + var6 * var6;
    }

    public static boolean method_1893(EntityPlayer var0, Vec3 var1) {
        AxisAlignedBB var3 = method_1889(var1).expand(-0.05, 0.0, -0.05).offset(0.0, var0.fallDistance - var0.stepHeight, 0.0);
        return mc.theWorld.getCollidingBoundingBoxes(var0, var3).isEmpty();
    }

    public static Vec3 method_1894(Vec3 var0) {
        return method_1914(var0, Math.sqrt(var0.xCoord * var0.xCoord + var0.zCoord * var0.zCoord), 1.0, method_1885(mc.thePlayer));
    }

    public static int method_1895(EntityPlayerSP var0) {
        return intValue2;
    }

    public static boolean method_1896(EntityPlayerSP var0) {
        return method_1902(var0) != 0.0F || method_1901(var0) != 0.0F;
    }

    public static Vec3 method_1897(Vec3 var0, double var1, double var3, MovementInputState var5, float var6) {
        if (var5 != null && !var5.method_1919()) {
            return new Vec3(0.0, var0.yCoord, 0.0);
        } else {
            double var7 = 1.0 - var3;
            double var9 = var0.xCoord * var7;
            var7 = var0.zCoord * var7;
            var1 *= var3;
            var3 = Math.toRadians(var6);
            var9 -= Math.sin(var3) * var1;
            var1 = var7 + Math.cos(var3) * var1;
            return new Vec3(var9, var0.yCoord, var1);
        }
    }

    public static double method_1898(Entity var0, Entity var1) {
        return method_1905(var0, var1.getPositionEyes(1.0F));
    }

    public static Vec3 method_1899(Entity var0) {
        return new Vec3(var0.posX, var0.posY, var0.posZ);
    }

    public static Vec3 method_1900(Vec3 var0, float var1, float var2) {
        double var3;
        if ((var3 = var0.lengthSqr()) < 1.0E-7) {
            return Vec3.ZERO;
        } else {
            Vec3 var6;
            float var7;
            if (!(var3 > 1.0)) {
                var7 = var1;
                var6 = var0;
            } else {
                Vec3 var13 = var0.normalize();
                var7 = var1;
                var6 = var13;
            }

            var0 = var6.scale(var7);
            float var12 = MathHelper.sin(var2 * (float) Math.PI / 180.0F);
            var2 = MathHelper.cos(var2 * (float) Math.PI / 180.0F);
            return new Vec3(var0.xCoord * var2 - var0.zCoord * var12, var0.yCoord, var0.zCoord * var2 + var0.xCoord * var12);
        }
    }

    public static float method_1901(EntityPlayerSP var0) {
        return var0.movementInput == null ? 0.0F : var0.movementInput.moveStrafe;
    }

    public static float method_1902(EntityPlayerSP var0) {
        return var0.movementInput == null ? 0.0F : var0.movementInput.moveForward;
    }

    public static Vec3 method_1903(Entity var0) {
        return new Vec3(var0.prevPosX, var0.prevPosY, var0.prevPosZ);
    }

    public static Vec3 method_1904(Entity var0) {
        return new Vec3(var0.motionX, var0.motionY, var0.motionZ);
    }

    public static double method_1905(Entity var0, Vec3 var1) {
        return method_1892(method_1912(var0), var1);
    }

    public static boolean method_1906(EntityPlayerSP var0, MovementInputState var1, double var2) {
        return method_1909(var0, var1, var2, method_1899(var0));
    }

    @EventHandler
    private void onPostTick(PostTickEvent var1) {
        if (mc.thePlayer == null) {
            intValue = 0;
            intValue2 = 0;
        } else if (!mc.thePlayer.onGround) {
            byte var2 = 0;
            intValue2 = var2;
            intValue++;
        } else {
            intValue = 0;
            intValue2++;
        }
    }

    public static void method_1907(Entity var0, Vec3 var1) {
        var0.motionX = var1.xCoord;
        var0.motionY = var1.yCoord;
        var0.motionZ = var1.zCoord;
    }

    public static int method_1908(EntityPlayerSP var0) {
        return intValue;
    }

    public static boolean method_1909(EntityPlayerSP var0, MovementInputState var1, double var2, Vec3 var4) {
        SimulatedPlayerInput var10;
        SimulatedPlayerInput var10000 = var10 = SimulatedPlayerInput.method_301(var1);
        var10000.inputKeys = InputKeysUtil.method_1968(
            var10000.inputKeys,
            var10.inputKeys.flag5(),
            var10.inputKeys.flag6(),
            var10.inputKeys.flag3(),
            var10.inputKeys.flag4(),
            false,
            false,
            var10.inputKeys.flag7()
        );
        SimulatedPlayer var11;
        SimulatedPlayer var13 = var11 = SimulatedPlayer.method_307(var10);
        var13.method_310(var4);
        var13.method_304();
        Vec3 var6;
        Vec3 var7;
        Vec3 var9;
        if ((var6 = var13.getVec32()).horizontalDistanceSqr() > 9.0E-6) {
            var7 = VecUtil.method_1942(var6, 0.0).normalize();
            var9 = var4;
        } else {
            float var8 = method_1888(var0, var1);
            var7 = Vec3.directionFromRotation(0.0F, var8);
            var9 = var4;
        }

        Vec3 var14 = var9.add(0.0, -0.1, 0.0);
        if (CollisionUtil.method_1925(var14, VecUtil.method_1951(var14, var2, var7)) != null) {
            return true;
        } else {
            Vec3 var12 = var11.getVec312().add(VecUtil.method_1942(var6, 0.0));
            return method_1893(var0, var4) || method_1893(var0, var12);
        }
    }

    @EventHandler
    private void onWorldLoad(WorldLoadEvent var1) {
        intValue = 0;
        intValue2 = 0;
    }

    public static Rotation method_1910(Entity var0) {
        return new Rotation(var0.rotationYaw, var0.rotationPitch, true);
    }

    public static boolean method_1911(EntityPlayerSP var0, double var1) {
        return method_1909(var0, method_1885(var0), var1, method_1899(var0));
    }

    public static AxisAlignedBB method_1912(Entity var0) {
        float var2 = var0.getCollisionBorderSize();
        return var0.getEntityBoundingBox().expand(var2, var2, var2);
    }

    public static float method_1913(EntityPlayerSP var0) {
        return method_1886(var0.rotationYaw, method_1885(var0));
    }

    public static Vec3 method_1914(Vec3 var0, double var1, double var3, MovementInputState var5) {
        MovementInputState var7;
        if (var5 == null) {
            var7 = method_1885(mc.thePlayer);
        } else {
            var7 = var5;
        }

        return method_1897(var0, var1, var3, var7, method_1888(mc.thePlayer, var7));
    }

    public static double method_1915(Entity var0) {
        return Math.sqrt(var0.motionX * var0.motionX + var0.motionZ * var0.motionZ);
    }
}
