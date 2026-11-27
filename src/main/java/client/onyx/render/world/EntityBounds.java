package client.onyx.render.world;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

public final class EntityBounds {
    private static final double DOUBLE_0_051 = 0.05;
    private static final double DOUBLE_8_01 = 8.0;
    private static final double DOUBLE_24_01 = 24.0;
    private static final double DOUBLE_0_058593751 = 0.05859375;
    private static final float FLOAT_1_01 = 1.0F;
    private static final int INT_11 = 1;
    private static final double DOUBLE_1_01 = 1.0;
    private static final double DOUBLE_4_01 = 4.0;
    private static final double DOUBLE_N8_51 = -8.5;
    private static final int INT_31 = 3;
    private static final int INT_41 = 4;
    private static final double DOUBLE_0_18751 = 0.1875;

    private static float method_550(Entity var0, float var1) {
        if (!(var0 instanceof EntityLivingBase)) {
            return var0.prevRotationYaw + (var0.rotationYaw - var0.prevRotationYaw) * var1;
        } else {
            EntityLivingBase var5;
            EntityLivingBase var4 = var5 = (EntityLivingBase)var0;
            return var4.prevRenderYawOffset + (var5.renderYawOffset - var5.prevRenderYawOffset) * var1;
        }
    }

    public static EntityBounds.Projected method_551(Entity var0, float var1, float var2, float var3) {
        if (!WorldProjection.isFlag()) {
            return null;
        } else {
            Vec3 var4;
            if ((var4 = method_552(var0, var1))
                    .addVector(0.0, var0.height * 0.5, 0.0)
                    .subtract(WorldProjection.getVec31())
                    .dotProduct(WorldProjection.getVec32())
                <= 0.05) {
                return null;
            } else {
                EntityBounds.Bounds var5 = EntityBounds.Bounds.method_385(var0);
                double var6;
                double var8 = Math.cos(var6 = Math.toRadians(method_550(var0, var1))) * var5.doubleValue3();
                double var10 = Math.sin(var6) * var5.doubleValue3();
                double var12 = -Math.sin(var6) * var5.doubleValue4();
                var6 = Math.cos(var6) * var5.doubleValue4();
                var1 = Float.MAX_VALUE;
                float var14 = -Float.MAX_VALUE;
                float var15 = Float.MAX_VALUE;
                float var16 = -Float.MAX_VALUE;
                int var39 = 0;

                for (int var38 = 0; var38 < 8; var38 = var39) {
                    double var23;
                    if ((var39 & 1) == 0) {
                        var23 = 1.0;
                    } else {
                        var23 = -1.0;
                    }

                    double var25;
                    if ((var39 & 2) == 0) {
                        var25 = 1.0;
                    } else {
                        var25 = -1.0;
                    }

                    double var10001 = var8 * var23 + var12 * var25;
                    Vec3 var31;
                    double var32;
                    double var34;
                    double var36;
                    if ((var39 & 4) != 0) {
                        double var28 = var10001;
                        double var10002 = var5.doubleValue2();
                        var36 = var10;
                        var34 = var10002;
                        var32 = var28;
                        var31 = var4;
                    } else {
                        double var43 = var5.doubleValue();
                        var36 = var10;
                        var34 = var43;
                        var32 = var10001;
                        var31 = var4;
                    }

                    Vec3 var19;
                    if ((var19 = var31.addVector(var32, var34, var36 * var23 + var6 * var25))
                            .subtract(WorldProjection.getVec31())
                            .dotProduct(WorldProjection.getVec32())
                        <= 0.05) {
                        return null;
                    }

                    float[] var22;
                    if ((var22 = WorldProjection.method_528(var19, var2, var3)) == null) {
                        return null;
                    }

                    var1 = Math.min(var1, var22[0]);
                    var14 = Math.max(var14, var22[0]);
                    var15 = Math.min(var15, var22[1]);
                    var39++;
                    var16 = Math.max(var16, var22[1]);
                }

                if (var16 - var15 <= 1.0F) {
                    return null;
                } else {
                    float[] var40 = WorldProjection.method_528(var4.addVector(0.0, var5.doubleValue(), 0.0), var2, var3);
                    float[] var18 = WorldProjection.method_528(var4.addVector(0.0, var5.doubleValue2(), 0.0), var2, var3);
                    return var40 != null && var18 != null ? new EntityBounds.Projected(var1, var15, var14 - var1, var16 - var15, var40[0], var18[0]) : null;
                }
            }
        }
    }

    private static Vec3 method_552(Entity var0, float var1) {
        return new Vec3(
            var0.lastTickPosX + (var0.posX - var0.lastTickPosX) * var1,
            var0.lastTickPosY + (var0.posY - var0.lastTickPosY) * var1,
            var0.lastTickPosZ + (var0.posZ - var0.lastTickPosZ) * var1
        );
    }

    private EntityBounds() {
    }

    private record Bounds(double doubleValue3, double doubleValue4, double doubleValue, double doubleValue2) {
        public static EntityBounds.Bounds method_385(Entity var0) {
            if (var0 instanceof EntityPlayer var4) {
                double var2 = (32.5 - method_386(var4, 4)) * 0.05859375;
                EntityBounds.Bounds var10000;
                double var10002 = (8.0 + method_386(var4, 3)) * 0.05859375;
                double var10003 = (4.0 + method_386(var4, 4)) * 0.05859375;
                double var10004;
                EntityPlayer var10005;
                if (var4.isSneaking()) {
                    var10004 = var2 - 0.1875;
                    var10005 = var4;
                } else {
                    var10004 = var2;
                    var10005 = var4;
                }

                var10000 = new EntityBounds.Bounds(var10002, var10003, var10004, -method_386(var10005, 1) * 0.05859375);
                return var10000;
            } else {
                return new EntityBounds.Bounds(var0.width * 0.5, var0.width * 0.5, var0.height, 0.0);
            }
        }

        private static double method_386(EntityLivingBase var0, int var1) {
            return var0.getEquipmentInSlot(var1) != null ? 1.0 : 0.0;
        }
    }

    public record Projected(float floatValue2, float floatValue, float floatValue4, float floatValue3, float floatValue6, float floatValue5) {
    }
}
