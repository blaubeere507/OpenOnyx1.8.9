package client.onyx.util.world;

import client.onyx.rotation.Rotation;
import client.onyx.rotation.RotationManager;
import client.onyx.util.IMinecraft;
import client.onyx.util.player.MovementPredictor;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public final class BlockRaycast implements IMinecraft {
    public static Rotation method_1991() {
        Rotation var0;
        return (var0 = RotationManager.INSTANCE.getServerRotation()) != null ? var0 : MovementPredictor.method_1910(mc.thePlayer);
    }

    public static MovingObjectPosition method_1992(Rotation var0, double var1, HitboxMode var3, boolean var4, float var5) {
        return method_1994(var1, var3, var4, mc.thePlayer.getPositionEyes(var5), var0.method_1049(), mc.getRenderViewEntity());
    }

    public static MovingObjectPosition method_1993(Rotation var0, double var1) {
        return method_1992(var0, var1, HitboxMode.OUTLINE, false, 1.0F);
    }

    public static MovingObjectPosition method_1994(double var0, HitboxMode var2, boolean var3, Vec3 var4, Vec3 var5, Entity var6) {
        Vec3 var8 = var4.addVector(var5.xCoord * var0, var5.yCoord * var0, var5.zCoord * var0);
        return mc.theWorld.rayTraceBlocks(var4, var8, var3, var2.isFlag(), false);
    }

    public static MovingObjectPosition method_1995(Rotation var0) {
        return method_1992(var0, method_1999(), HitboxMode.OUTLINE, false, 1.0F);
    }

    public static MovingObjectPosition method_1996() {
        return method_1992(method_1991(), method_1999(), HitboxMode.OUTLINE, false, 1.0F);
    }

    private BlockRaycast() {
    }

    public static MovingObjectPosition method_1997(double var0, HitboxMode var2, boolean var3, Vec3 var4, Vec3 var5) {
        return method_1994(var0, var2, var3, var4, var5, mc.getRenderViewEntity());
    }

    public static boolean method_1998(Vec3 var0, Vec3 var1, Entity var2) {
        return var2.worldObj.rayTraceBlocks(var0, var1, false, true, false) == null;
    }

    public static double method_1999() {
        return mc.playerController.getBlockReachDistance();
    }

    public static boolean method_2000(Vec3 var0, Vec3 var1) {
        return method_1998(var0, var1, mc.thePlayer);
    }
}
