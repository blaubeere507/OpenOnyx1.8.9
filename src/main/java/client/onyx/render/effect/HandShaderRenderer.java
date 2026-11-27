package client.onyx.render.effect;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public final class HandShaderRenderer {
    private static final float[] floatValueArray7 = new float[]{-1.8F, -1.8F, -1.8F, 1.8F, 1.8F, 1.8F};
    private static final float[] floatValueArray5 = new float[]{-0.62F, -0.12F, -0.25F, -0.13F, 0.87F, 0.25F};
    private static final float[] floatValueArray3 = new float[]{-0.62F, -0.58F, -0.6F, -0.13F, 0.83F, 0.6F};
    private static final FloatBuffer floatBuffer2 = BufferUtils.createFloatBuffer(16);
    private static final FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
    private static final int[] intValueArray = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 0, 2, 1, 3, 4, 6, 5, 7, 0, 4, 1, 5, 2, 6, 3, 7};
    private static final float[] floatValueArray6 = new float[8];
    private static final float[] floatValueArray2 = new float[8];
    private static final float[] floatValueArray = new float[8];
    private static final float[] floatValueArray4 = new float[8];
    private static float floatValue;
    private static float floatValue2;
    private static boolean flag;
    private static final float FLOAT_1_0EN41 = 1.0E-4F;
    private static boolean flag2;
    private static float floatValue3;
    private static float floatValue4;

    private static void method_680(float[] var0) {
        float[] var1 = var0;
        floatBuffer2.clear();
        floatBuffer.clear();
        GL11.glGetFloat(2982, floatBuffer2);
        GL11.glGetFloat(2983, floatBuffer);
        int var25 = 0;

        for (int var18 = var25; var18 < 8; var18 = var25) {
            float[] var10;
            byte var11;
            if ((var25 & 1) != 0) {
                var11 = 3;
                var10 = var1;
            } else {
                var11 = 0;
                var10 = var1;
            }

            float var2 = var10[var11];
            float[] var13;
            byte var14;
            if ((var25 & 2) != 0) {
                var14 = 4;
                var13 = var1;
            } else {
                var14 = 1;
                var13 = var1;
            }

            float var3 = var13[var14];
            float[] var16;
            byte var17;
            if ((var25 & 4) != 0) {
                var17 = 5;
                var16 = var1;
            } else {
                var17 = 2;
                var16 = var1;
            }

            float var4 = var16[var17];
            float var5 = floatBuffer2.get(0) * var2 + floatBuffer2.get(4) * var3 + floatBuffer2.get(8) * var4 + floatBuffer2.get(12);
            float var6 = floatBuffer2.get(1) * var2 + floatBuffer2.get(5) * var3 + floatBuffer2.get(9) * var4 + floatBuffer2.get(13);
            float var7 = floatBuffer2.get(2) * var2 + floatBuffer2.get(6) * var3 + floatBuffer2.get(10) * var4 + floatBuffer2.get(14);
            floatValueArray6[var25] = floatBuffer.get(0) * var5 + floatBuffer.get(4) * var6 + floatBuffer.get(8) * var7 + floatBuffer.get(12);
            floatValueArray2[var25] = floatBuffer.get(1) * var5 + floatBuffer.get(5) * var6 + floatBuffer.get(9) * var7 + floatBuffer.get(13);
            float var8 = floatBuffer.get(2) * var5 + floatBuffer.get(6) * var6 + floatBuffer.get(10) * var7 + floatBuffer.get(14);
            floatValueArray[var25] = floatBuffer.get(3) * var5 + floatBuffer.get(7) * var6 + floatBuffer.get(11) * var7 + floatBuffer.get(15);
            int var10001 = var25;
            float var10002 = var8 + floatValueArray[var25];
            var25++;
            floatValueArray4[var10001] = var10002;
        }

        int var26 = 0;

        for (int var19 = 0; var19 < 8; var19 = ++var26) {
            if (floatValueArray4[var26] >= 0.0F) {
                method_684(floatValueArray6[var26], floatValueArray2[var26], floatValueArray[var26]);
            }
        }

        byte var27 = 0;

        for (byte var24 = 0; var24 < intValueArray.length; var24 = var27) {
            int var28 = intValueArray[var27];
            int var29 = intValueArray[var27 + 1];
            boolean var20;
            if (floatValueArray4[var28] >= 0.0F) {
                var20 = true;
            } else {
                var20 = false;
            }

            boolean var22;
            boolean var23;
            if (!(floatValueArray4[var29] >= 0.0F)) {
                var23 = false;
                var22 = var20;
            } else {
                var23 = true;
                var22 = var20;
            }

            if (var22 != var23) {
                float var30 = floatValueArray4[var28] / (floatValueArray4[var28] - floatValueArray4[var29]);
                method_684(
                    floatValueArray6[var28] + (floatValueArray6[var29] - floatValueArray6[var28]) * var30,
                    floatValueArray2[var28] + (floatValueArray2[var29] - floatValueArray2[var28]) * var30,
                    floatValueArray[var28] + (floatValueArray[var29] - floatValueArray[var28]) * var30
                );
            }

            var27 += 2;
        }
    }

    public static void method_681() {
        flag2 = false;
    }

    public static void method_682() {
        if (flag) {
            flag = false;
            GL11.glDisable(3089);
        }
    }

    public static void method_683(float var0, int var1, int var2) {
        if (flag2) {
            int var4 = Math.clamp((long)((int)Math.floor(floatValue * var1 - var0)), 0, var1);
            int var3 = Math.clamp((long)((int)Math.floor(floatValue3 * var2 - var0)), 0, var2);
            var1 = Math.clamp((long)((int)Math.ceil(floatValue2 * var1 + var0)), var4, var1);
            int var6 = Math.clamp((long)((int)Math.ceil(floatValue4 * var2 + var0)), var3, var2);
            if (var1 > var4 && var6 > var3) {
                GL11.glEnable(3089);
                GL11.glScissor(var4, var3, var1 - var4, var6 - var3);
                flag = true;
            }
        }
    }

    private static void method_684(float var0, float var1, float var2) {
        if (var2 > 1.0E-4F) {
            float var3 = var0 / var2 * 0.5F + 0.5F;
            var0 = var1 / var2 * 0.5F + 0.5F;
            if (Float.isFinite(var3) && Float.isFinite(var0)) {
                if (flag2) {
                    floatValue = Math.min(floatValue, var3);
                    floatValue3 = Math.min(floatValue3, var0);
                    floatValue2 = Math.max(floatValue2, var3);
                    floatValue4 = Math.max(floatValue4, var0);
                } else {
                    floatValue = var3;
                    floatValue3 = var0;
                    floatValue2 = var3;
                    floatValue4 = var0;
                    flag2 = true;
                }
            }
        }
    }

    public static void method_685() {
        method_680(floatValueArray7);
    }

    private HandShaderRenderer() {
    }

    public static boolean isFlag2() {
        return flag2;
    }

    public static void method_686(boolean var0) {
        float[] var1;
        if (var0) {
            var1 = floatValueArray3;
        } else {
            var1 = floatValueArray5;
        }

        method_680(var1);
    }
}
