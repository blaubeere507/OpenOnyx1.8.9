package client.onyx.util.world;

import client.onyx.rotation.Rotation;
import client.onyx.util.IMinecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public final class EntityRaycast implements IMinecraft {
    public static MovingObjectPosition method_1937(Entity var0, double var1, Rotation var3) {
        Entity var4;
        if ((var4 = mc.getRenderViewEntity()) != null && var0 != null) {
            Vec3 var11 = var4.getPositionEyes(1.0F);
            Vec3 var6 = var3.method_1049();
            Vec3 var7 = var11.addVector(var6.xCoord * var1, var6.yCoord * var1, var6.zCoord * var1);
            float var9 = var0.getCollisionBorderSize();
            MovingObjectPosition var8;
            return (var8 = var0.getEntityBoundingBox().expand(var9, var9, var9).calculateIntercept(var11, var7)) == null
                ? null
                : new MovingObjectPosition(var0, var8.hitVec);
        } else {
            return null;
        }
    }

    private EntityRaycast() {
    }
}
