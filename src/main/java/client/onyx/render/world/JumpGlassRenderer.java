package client.onyx.render.world;

import client.onyx.render.effect.PostProcessing;
import client.onyx.render.shader.ShaderProgram;
import client.onyx.util.IMinecraft;
import java.util.List;
import net.minecraft.util.Vec3;

public final class JumpGlassRenderer implements IMinecraft {
    private static final float FLOAT_4_01 = 4.0F;
    private static final float FLOAT_0_051 = 0.05F;
    private static final int INT_21 = 2;
    private static final int INT_81 = 8;
    private static final float FLOAT_1_031 = 1.03F;
    private static final float FLOAT_0_121 = 0.12F;
    private static final int INT_161 = 16;
    private static final double DOUBLE_0_061 = 0.06;
    private static final float FLOAT_0_31 = 0.3F;
    private static final float FLOAT_0_161 = 0.16F;
    private final float[] floatValueArray2 = new float[32];
    private final float[] floatValueArray = new float[32];
    private final Vec3[] vec3Array = new Vec3[16];
    private final double[] doubleValueArray = new double[16];
    private float floatValue;
    private float floatValue2;
    private boolean flag;
    private float floatValue3;
    private ShaderProgram shaderProgram;
    private boolean flag2;
    private float floatValue4;

    private int method_1013(List<JumpCircleParams> var1, JumpCircle var2, long var3) {
        var var9 = this;
        Vec3 var5 = WorldProjection.getVec31();
        float var6 = WorldProjection.method_526() * 0.5F;
        this.floatValue3 = this.floatValue2 = Float.MAX_VALUE;
        this.floatValue = -Float.MAX_VALUE;
        this.floatValue4 = -Float.MAX_VALUE;
        this.flag2 = false;
        int var7 = 0;

        int var8;
        for (int var14 = var8 = var1.size() - 1; var14 >= 0 && var7 < 8; var14 = --var8) {
            JumpCircleParams var15 = (JumpCircleParams)var1.get(var8);
            float var10 = Math.clamp((float)(var3 - var15.longValue()) / (float)var2.longValue(), 0.0F, 1.0F);
            float var11;
            if (!((var11 = 1.0F - var10) <= 0.0F)
                && !((var10 = var2.floatValue6() * (1.0F - (float)Math.pow(1.0F - var10, 4.0))) < 0.05F)
                && var9.method_1015(var15.vec31(), var10)) {
                Vec3 var16;
                double var12 = Math.max((var16 = var15.vec31().subtract(var5)).lengthVector(), 0.1);
                float var18;
                if (!((var18 = Math.min((float)(var10 * var6 / var12), 0.3F)) <= 0.0F)) {
                    int var13 = var7 * 4;
                    var9.floatValueArray2[var13] = (float)var16.xCoord;
                    var9.floatValueArray2[var13 + 1] = (float)var16.yCoord;
                    var9.floatValueArray2[var13 + 2] = (float)var16.zCoord;
                    var9.floatValueArray2[var13 + 3] = var10;
                    var9.floatValueArray[var13] = var11;
                    var9.floatValueArray[var13 + 1] = var18;
                    var9.floatValueArray[var13 + 2] = 0.0F;
                    int var10001 = var13 + 3;
                    var7++;
                    var9.floatValueArray[var10001] = 0.0F;
                }
            }
        }

        return var7;
    }

    private PostProcessing.Target method_1014(int var1, JumpCircle var2) {
        var var3 = this;
        if (this.flag2) {
            return null;
        } else if (this.floatValue2 > this.floatValue) {
            return null;
        } else {
            int var5 = PostProcessing.method_534();
            int var4 = PostProcessing.method_536();
            if (var5 > 0 && var4 > 0) {
                float var9 = 0.0F;
                int var6 = 0;

                for (int var8 = 0; var8 < var1; var8 = var6) {
                    int var10002 = var6 * 4 + 1;
                    var6++;
                    var9 = Math.max(var9, var3.floatValueArray[var10002]);
                }

                float var14 = var2.floatValue() * 0.16F * var9 * 1.3F;
                float var11 = var2.floatValue5() * 0.12F * var9;
                var1 = (int)Math.ceil(Math.max(var14 * var5, var11 * var4)) + 1;
                int var13 = (int)Math.floor(var3.floatValue2 * var5) - 2;
                int var10 = (int)Math.floor(var3.floatValue3 * var4) - 2;
                var6 = (int)Math.ceil(var3.floatValue * var5) + 2;
                int var7 = (int)Math.ceil(var3.floatValue4 * var4) + 2;
                return new PostProcessing.Target(var13, var10, var6 - var13, var7 - var10, var1).method_786(var5, var4);
            } else {
                return null;
            }
        }
    }

    private boolean method_1015(Vec3 var1, float var2) {
        var var3 = this;
        if (this.flag2) {
            return true;
        } else {
            Vec3 var6 = WorldProjection.getVec31();
            Vec3 var4 = WorldProjection.getVec32();
            var2 *= 1.03F;
            int var5 = 0;
            int var16 = 0;

            for (int var12 = 0; var12 < 16; var12 = ++var16) {
                double var7 = var16 * (Math.PI / 8);
                Vec3 var9 = var1.addVector(Math.cos(var7) * var2, 0.0, Math.sin(var7) * var2);
                var3.vec3Array[var16] = var9;
                var3.doubleValueArray[var16] = var9.subtract(var6).dotProduct(var4);
                if (var3.doubleValueArray[var16] >= 0.06) {
                    var5++;
                }
            }

            if (var5 == 0) {
                return false;
            } else {
                float var17 = Float.MAX_VALUE;
                float var22 = Float.MAX_VALUE;
                float var8 = -Float.MAX_VALUE;
                float var23 = -Float.MAX_VALUE;
                int var18 = 0;

                for (int var15 = 0; var15 < 16; var15 = ++var18) {
                    int var20 = (var18 + 1) % 16;
                    boolean var13;
                    if (var3.doubleValueArray[var18] >= 0.06) {
                        var13 = true;
                    } else {
                        var13 = false;
                    }

                    boolean var14;
                    if (var3.doubleValueArray[var20] >= 0.06) {
                        var14 = true;
                    } else {
                        var14 = false;
                    }

                    if (var13) {
                        float[] var10;
                        if ((var10 = var3.method_1016(var3.vec3Array[var18])) == null) {
                            return true;
                        }

                        var17 = Math.min(var17, var10[0]);
                        var8 = Math.max(var8, var10[0]);
                        var22 = Math.min(var22, var10[1]);
                        var23 = Math.max(var23, var10[1]);
                    }

                    if (var13 != var14) {
                        double var24 = (var3.doubleValueArray[var18] - 0.06) / (var3.doubleValueArray[var18] - var3.doubleValueArray[var20]);
                        float[] var21;
                        if ((var21 = var3.method_1016(var3.vec3Array[var18].add(var3.vec3Array[var20].subtract(var3.vec3Array[var18]).scale(var24)))) == null) {
                            return true;
                        }

                        var17 = Math.min(var17, var21[0]);
                        var8 = Math.max(var8, var21[0]);
                        var22 = Math.min(var22, var21[1]);
                        var23 = Math.max(var23, var21[1]);
                    }
                }

                if (!(var8 < 0.0F) && !(var17 > 1.0F) && !(var23 < 0.0F) && !(var22 > 1.0F)) {
                    var3.floatValue2 = Math.min(var3.floatValue2, var17);
                    var3.floatValue = Math.max(var3.floatValue, var8);
                    var3.floatValue3 = Math.min(var3.floatValue3, var22);
                    var3.floatValue4 = Math.max(var3.floatValue4, var23);
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    private float[] method_1016(Vec3 var1) {
        float[] var3;
        if ((var3 = WorldProjection.method_528(var1, 1.0F, 1.0F)) == null) {
            this.flag2 = true;
            return null;
        } else {
            return new float[]{Math.clamp(var3[0], -4.0F, 4.0F), Math.clamp(1.0F - var3[1], -4.0F, 4.0F)};
        }
    }

    public void method_1017(List<JumpCircleParams> var1, JumpCircle var2, long var3) {
        if (var2.flag2() && !var1.isEmpty()) {
            if (!(var2.floatValue5() <= 0.0F) || !(var2.floatValue() <= 0.0F)) {
                if (PostProcessing.method_538() && WorldProjection.isFlag()) {
                    int var9;
                    if ((var9 = this.method_1013(var1, var2, var3)) != 0) {
                        PostProcessing.Target var10;
                        if ((var10 = this.method_1014(var9, var2)) != null || this.flag2) {
                            if (!this.flag) {
                                this.flag = true;
                                this.shaderProgram = ShaderProgram.method_814("onyx_jump_glass", "onyx_post", "onyx_jump_glass");
                            }

                            if (this.shaderProgram != null && this.shaderProgram.isFlag()) {
                                Vec3 var4 = WorldProjection.getVec32();
                                Vec3 var7 = WorldProjection.getVec33();
                                Vec3 var8;
                                Vec3 var6 = (var8 = new Vec3(-var7.xCoord, -var7.yCoord, -var7.zCoord)).crossProduct(var4);
                                PostProcessing.method_533(
                                    this.shaderProgram,
                                    var6x -> {
                                        var var11 = this;
                                        var6x.method_815("uCount", var9);
                                        var6x.method_821("uBlur", var2.floatValue5() * 0.12F);
                                        var6x.method_821("uWarp", var2.floatValue() * 0.16F);
                                        var6x.method_810("uForward", (float)var4.xCoord, (float)var4.yCoord, (float)var4.zCoord);
                                        var6x.method_810("uRight", (float)var8.xCoord, (float)var8.yCoord, (float)var8.zCoord);
                                        var6x.method_810("uUp", (float)var6.xCoord, (float)var6.yCoord, (float)var6.zCoord);
                                        var6x.method_812("uTanHalf", 1.0F / WorldProjection.method_524(), 1.0F / WorldProjection.method_526());
                                        int var10x = 0;

                                        for (int var7x = 0; var7x < var9; var7x = var10x) {
                                            int var9x = var10x * 4;
                                            var6x.method_822(
                                                "uLensA[" + var10x + "]",
                                                var11.floatValueArray2[var9x],
                                                var11.floatValueArray2[var9x + 1],
                                                var11.floatValueArray2[var9x + 2],
                                                var11.floatValueArray2[var9x + 3]
                                            );
                                            String var10001 = new StringBuilder().insert(0, "uLensB[").append(var10x).append("]").toString();
                                            float var10002 = var11.floatValueArray[var9x];
                                            float var10003 = var11.floatValueArray[var9x + 1];
                                            float var10004 = var11.floatValueArray[var9x + 2];
                                            int var10006 = var9x + 3;
                                            var10x++;
                                            var6x.method_822(var10001, var10002, var10003, var10004, var11.floatValueArray[var10006]);
                                        }
                                    },
                                    var10
                                );
                            }
                        }
                    }
                }
            }
        }
    }
}
