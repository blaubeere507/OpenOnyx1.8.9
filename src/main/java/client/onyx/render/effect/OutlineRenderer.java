package client.onyx.render.effect;

import client.onyx.module.render.GlowESP;
import java.nio.FloatBuffer;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.Shader;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public final class OutlineRenderer {
    private static final FloatBuffer floatBuffer2 = BufferUtils.createFloatBuffer(16);
    private static final FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
    private static final float[] floatValueArray = new float[2];
    private static final int[] intValueArray = new int[4];
    private static int intValue = 1;
    private static int intValue2 = 1;
    private static float floatValue;
    private static float floatValue2;
    private static final float FLOAT_1_0EN41 = 1.0E-4F;
    private static final double DOUBLE_0_51 = 0.5;
    private static boolean flag;
    private static boolean flag2;
    private static float floatValue3;
    private static float floatValue4;

    public static void method_653(float var0, int var1, int var2) {
        if (flag) {
            method_654(var0, var1, var2);
            GL11.glEnable(3089);
            GL11.glScissor(intValueArray[0], intValueArray[1], intValueArray[2], intValueArray[3]);
            flag2 = true;
        }
    }

    private static void method_654(float var0, int var1, int var2) {
        float var6 = (float)var1 / intValue;
        float var4 = (float)var2 / intValue2;
        int var5 = Math.clamp((long)((int)Math.floor((floatValue3 - var0) * var6) - 1), 0, var1 - 1);
        int var3 = Math.clamp((long)((int)Math.floor((floatValue4 - var0) * var4) - 1), 0, var2 - 1);
        var1 = Math.clamp((long)((int)Math.ceil((floatValue + var0) * var6) + 1), var5 + 1, var1);
        int var8 = Math.clamp((long)((int)Math.ceil((floatValue2 + var0) * var4) + 1), var3 + 1, var2);
        intValueArray[0] = var5;
        intValueArray[1] = var3;
        intValueArray[2] = var1 - var5;
        intValueArray[3] = var8 - var3;
    }

    private static boolean method_655(float var0, float var1, float var2) {
        float var3 = floatBuffer2.get(0) * var0 + floatBuffer2.get(4) * var1 + floatBuffer2.get(8) * var2 + floatBuffer2.get(12);
        float var4 = floatBuffer2.get(1) * var0 + floatBuffer2.get(5) * var1 + floatBuffer2.get(9) * var2 + floatBuffer2.get(13);
        var0 = floatBuffer2.get(2) * var0 + floatBuffer2.get(6) * var1 + floatBuffer2.get(10) * var2 + floatBuffer2.get(14);
        if (!((var2 = floatBuffer.get(3) * var3 + floatBuffer.get(7) * var4 + floatBuffer.get(11) * var0 + floatBuffer.get(15)) > 1.0E-4F)) {
            return false;
        } else {
            float var5 = floatBuffer.get(0) * var3 + floatBuffer.get(4) * var4 + floatBuffer.get(8) * var0 + floatBuffer.get(12);
            var0 = floatBuffer.get(1) * var3 + floatBuffer.get(5) * var4 + floatBuffer.get(9) * var0 + floatBuffer.get(13);
            floatValueArray[0] = (var5 / var2 * 0.5F + 0.5F) * intValue;
            floatValueArray[1] = (var0 / var2 * 0.5F + 0.5F) * intValue2;
            return Float.isFinite(floatValueArray[0]) && Float.isFinite(floatValueArray[1]);
        }
    }

    public static void method_656(List<Entity> var0, float var1, double var2, double var4, double var6) {
        List<Entity> var13 = var0;
        flag = false;
        if (GlowESP.method_1245() && !var0.isEmpty()) {
            Minecraft var8 = Minecraft.getMinecraft();
            intValue = Math.max(1, var8.displayWidth);
            intValue2 = Math.max(1, var8.displayHeight);
            floatBuffer2.clear();
            floatBuffer.clear();
            GL11.glGetFloat(2982, floatBuffer2);
            GL11.glGetFloat(2983, floatBuffer);
            float var37 = Float.MAX_VALUE;
            float var9 = Float.MAX_VALUE;
            float var10 = -Float.MAX_VALUE;
            float var11 = -Float.MAX_VALUE;
            int var12 = 0;

            for (int var34 = var12; var34 < var13.size(); var34 = ++var12) {
                Entity var35;
                Entity var10000 = var35 = (Entity)var13.get(var12);
                AxisAlignedBB var14 = var10000.getEntityBoundingBox();
                double var15 = var10000.lastTickPosX + (var35.posX - var35.lastTickPosX) * var1 - var35.posX - var2;
                double var17 = var35.lastTickPosY + (var35.posY - var35.lastTickPosY) * var1 - var35.posY - var4;
                double var19 = var10000.lastTickPosZ + (var35.posZ - var35.lastTickPosZ) * var1 - var35.posZ - var6;
                float var36 = (float)(var14.minX + var15 - 0.5);
                float var38 = (float)(var14.minY + var17 - 0.5);
                float var21 = (float)(var14.minZ + var19 - 0.5);
                float var39 = (float)(var14.maxX + var15 + 0.5);
                float var16 = (float)(var14.maxY + var17 + 0.5);
                float var40 = (float)(var14.maxZ + var19 + 0.5);
                int var18 = 0;

                for (int var33 = 0; var33 < 8; var33 = var18) {
                    float var22;
                    int var23;
                    if ((var18 & 1) == 0) {
                        var23 = var18;
                        var22 = var36;
                    } else {
                        var23 = var18;
                        var22 = var39;
                    }

                    float var25;
                    float var26;
                    int var27;
                    if ((var23 & 2) != 0) {
                        var27 = var18;
                        var26 = var16;
                        var25 = var22;
                    } else {
                        var27 = var18;
                        var26 = var38;
                        var25 = var22;
                    }

                    float var30;
                    float var31;
                    float var32;
                    if ((var27 & 4) != 0) {
                        var32 = var40;
                        var31 = var26;
                        var30 = var25;
                    } else {
                        var32 = var21;
                        var31 = var26;
                        var30 = var25;
                    }

                    if (!method_655(var30, var31, var32)) {
                        return;
                    }

                    var37 = Math.min(var37, floatValueArray[0]);
                    var9 = Math.min(var9, floatValueArray[1]);
                    var10 = Math.max(var10, floatValueArray[0]);
                    var18++;
                    var11 = Math.max(var11, floatValueArray[1]);
                }
            }

            floatValue3 = var37;
            floatValue4 = var9;
            floatValue = var10;
            floatValue2 = var11;
            flag = true;
        }
    }

    public static boolean isFlag() {
        return flag;
    }

    public static void method_657(Shader var0, float var1) {
        if (!flag) {
            var0.clearScissor();
        } else {
            Framebuffer var3 = var0.framebufferOut;
            method_654(var1, var3.framebufferTextureWidth, var3.framebufferTextureHeight);
            var0.setScissor(intValueArray[0], intValueArray[1], intValueArray[2], intValueArray[3]);
        }
    }

    public static void method_658() {
        if (flag2) {
            GL11.glDisable(3089);
            flag2 = false;
        }
    }

    private OutlineRenderer() {
    }
}
