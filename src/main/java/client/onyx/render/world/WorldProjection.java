package client.onyx.render.world;

import client.onyx.util.IMinecraft;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public final class WorldProjection implements IMinecraft {
    private static final FloatBuffer floatBuffer2 = BufferUtils.createFloatBuffer(16);
    private static final FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
    private static final FloatBuffer floatBuffer3 = BufferUtils.createFloatBuffer(3);
    private static final IntBuffer intBuffer = BufferUtils.createIntBuffer(16);
    private static Vec3 vec34 = new Vec3(0.0, 0.0, 0.0);
    private static Vec3 vec31 = new Vec3(0.0, 0.0, 0.0);
    private static Vec3 vec32 = new Vec3(0.0, 0.0, 1.0);
    private static Vec3 vec33 = new Vec3(1.0, 0.0, 0.0);
    private static boolean flag;

    static {
        intBuffer.put(0, 0);
        intBuffer.put(1, 0);
        intBuffer.put(2, 1);
        intBuffer.put(3, 1);
    }

    public static boolean isFlag() {
        return flag;
    }

    public static void method_523(float var0) {
        Entity var2;
        if ((var2 = mc.getRenderViewEntity()) == null) {
            flag = false;
        } else {
            floatBuffer2.clear();
            floatBuffer.clear();
            GL11.glGetFloat(2982, floatBuffer2);
            GL11.glGetFloat(2983, floatBuffer);
            vec34 = new Vec3(
                var2.prevPosX + (var2.posX - var2.prevPosX) * var0,
                var2.prevPosY + (var2.posY - var2.prevPosY) * var0,
                var2.prevPosZ + (var2.posZ - var2.prevPosZ) * var0
            );
            vec32 = method_525(0.0F, 0.0F, -1.0F);
            vec33 = method_525(-1.0F, 0.0F, 0.0F);
            vec31 = vec34.add(method_525(-floatBuffer2.get(12), -floatBuffer2.get(13), -floatBuffer2.get(14)));
            flag = true;
        }
    }

    public static Vec3 getVec31() {
        return vec31;
    }

    public static Vec3 getVec32() {
        return vec32;
    }

    public static Vec3 getVec33() {
        return vec33;
    }

    public static float method_524() {
        return Math.abs(floatBuffer.get(0));
    }

    private static Vec3 method_525(float var0, float var1, float var2) {
        return new Vec3(
            floatBuffer2.get(0) * var0 + floatBuffer2.get(1) * var1 + floatBuffer2.get(2) * var2,
            floatBuffer2.get(4) * var0 + floatBuffer2.get(5) * var1 + floatBuffer2.get(6) * var2,
            floatBuffer2.get(8) * var0 + floatBuffer2.get(9) * var1 + floatBuffer2.get(10) * var2
        );
    }

    public static float method_526() {
        return Math.abs(floatBuffer.get(5));
    }

    public static void method_527() {
        flag = false;
    }

    private WorldProjection() {
    }

    public static float[] method_528(Vec3 var0, float var1, float var2) {
        if (!flag) {
            return null;
        } else {
            floatBuffer3.clear();
            if (!GLU.gluProject(
                (float)(var0.xCoord - vec34.xCoord),
                (float)(var0.yCoord - vec34.yCoord),
                (float)(var0.zCoord - vec34.zCoord),
                floatBuffer2,
                floatBuffer,
                intBuffer,
                floatBuffer3
            )) {
                return null;
            } else {
                float var3;
                if (Float.isFinite(var3 = floatBuffer3.get(2)) && !(var3 < 0.0F) && !(var3 > 1.0F)) {
                    var1 = floatBuffer3.get(0) * var1;
                    float var5 = (1.0F - floatBuffer3.get(1)) * var2;
                    return Float.isFinite(var1) && Float.isFinite(var5) ? new float[]{var1, var5} : null;
                } else {
                    return null;
                }
            }
        }
    }
}
