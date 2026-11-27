package client.onyx.render.util;

import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public enum FilterMode {
    PIXELS,
    SMOOTH,
    MINIFIED;

    private static final Set<Integer> set = new HashSet<>();
    private static final Set<Integer> set2 = new HashSet<>();

    private static boolean method_671() {
        int var0 = method_672(0, 4096);
        int var1 = method_672(0, 4097);
        int var2 = method_672(0, 4099);
        if (var0 > 0 && var1 > 0) {
            int var3 = 0;

            while (var0 > 1 || var1 > 1) {
                var0 = Math.max(1, var0 / 2);
                var3++;
                var1 = Math.max(1, var1 / 2);
                if (method_672(var3, 4096) != var0) {
                    return false;
                }

                if (method_672(var3, 4097) != var1) {
                    return false;
                }

                if (method_672(var3, 4099) != var2) {
                    return false;
                }
            }

            return GL11.glGetTexParameteri(3553, 33085) >= var3 && GL11.glGetTexParameterf(3553, 33083) >= var3;
        } else {
            return false;
        }
    }

    private static int method_672(int var0, int var1) {
        return GL11.glGetTexLevelParameteri(3553, var0, var1);
    }

    public float method_673() {
        return this == PIXELS ? 0.01F : 0.5F;
    }

    public static void method_674(int var0) {
        set.remove(var0);
        set2.remove(var0);
    }

    public void method_675(int var1) {
        if (this != PIXELS) {
            GL11.glTexParameteri(3553, 10241, var1);
            GL11.glTexParameteri(3553, 10240, var1);
        }
    }

    private static byte[] method_676(byte[] var0, int var1, int var2, int var3, int var4) {
        byte[] var11 = var0;
        byte[] var5 = new byte[var3 * var4 * 4];
        int var6 = 0;

        for (int var41 = 0; var41 < var4; var41 = ++var6) {
            int var7 = 0;

            for (int var40 = 0; var40 < var3; var40 = var7) {
                int var8 = 0;
                int var9 = 0;
                int var10 = 0;
                int var42 = 0;
                int var12 = 0;
                int var13 = 0;

                for (int var19 = 0; var19 < 2; var19 = ++var13) {
                    int var14;
                    if ((var14 = var6 * 2 + var13) < var2) {
                        int var15 = 0;

                        for (int var18 = 0; var18 < 2; var18 = ++var15) {
                            int var16;
                            if ((var16 = var7 * 2 + var15) < var1) {
                                var16 = (var14 * var1 + var16) * 4;
                                int var17 = var11[var16 + 3] & 255;
                                var8 += (var11[var16] & 255) * var17;
                                var9 += (var11[var16 + 1] & 255) * var17;
                                var10 += (var11[var16 + 2] & 255) * var17;
                                var12++;
                                var42 += var17;
                            }
                        }
                    }
                }

                var13 = (var6 * var3 + var7) * 4;
                byte[] var22;
                int var23;
                int var24;
                if (var42 != 0) {
                    var24 = var8 / var42;
                    var23 = var13;
                    var22 = var5;
                } else {
                    var24 = 0;
                    var23 = var13;
                    var22 = var5;
                }

                var22[var23] = (byte)var24;
                int var10001 = var13 + 1;
                byte[] var27;
                int var28;
                int var29;
                if (var42 != 0) {
                    int var26 = var10001;
                    var29 = var9 / var42;
                    var28 = var26;
                    var27 = var5;
                } else {
                    var29 = 0;
                    var28 = var10001;
                    var27 = var5;
                }

                var27[var28] = (byte)var29;
                var10001 = var13 + 2;
                byte[] var32;
                int var33;
                int var34;
                if (var42 != 0) {
                    int var31 = var10001;
                    var34 = var10 / var42;
                    var33 = var31;
                    var32 = var5;
                } else {
                    var34 = 0;
                    var33 = var10001;
                    var32 = var5;
                }

                var32[var33] = (byte)var34;
                var10001 = var13 + 3;
                byte[] var37;
                int var38;
                int var39;
                if (var12 != 0) {
                    int var36 = var10001;
                    var39 = var42 / var12;
                    var38 = var36;
                    var37 = var5;
                } else {
                    var39 = 0;
                    var38 = var10001;
                    var37 = var5;
                }

                byte var10002 = (byte)var39;
                var7++;
                var37[var38] = var10002;
            }
        }

        return var5;
    }

    private static void method_677() {
        int var0 = method_672(0, 4096);
        int var1 = method_672(0, 4097);
        int var2 = method_672(0, 4099);
        if (var0 > 0 && var1 > 0 && var2 > 0) {
            ByteBuffer var3 = BufferUtils.createByteBuffer(var0 * var1 * 4);
            GL11.glGetTexImage(3553, 0, 6408, 5121, var3);
            byte[] var4 = new byte[var0 * var1 * 4];
            var3.get(var4);
            int var8 = 0;
            int var7 = var0;

            while (var7 > 1 || var1 > 1) {
                int var5 = Math.max(1, var0 / 2);
                int var6 = Math.max(1, var1 / 2);
                var4 = method_676(var4, var0, var1, var5, var6);
                var8++;
                var0 = var5;
                var1 = var6;
                ByteBuffer var9 = BufferUtils.createByteBuffer(var4.length);
                var9.put(var4).flip();
                GL11.glTexImage2D(3553, var8, var2, var5, var6, 0, 6408, 5121, var9);
                var7 = var5;
            }

            GL11.glTexParameteri(3553, 33085, var8);
            GL11.glTexParameterf(3553, 33082, 0.0F);
            GL11.glTexParameterf(3553, 33083, var8);
        }
    }

    public int method_678() {
        if (this == PIXELS) {
            return 9728;
        } else {
            if (this == MINIFIED && method_679()) {
                GL11.glTexParameteri(3553, 10241, 9987);
            } else {
                GL11.glTexParameteri(3553, 10241, 9729);
            }

            GL11.glTexParameteri(3553, 10240, 9729);
            return 9728;
        }
    }

    private static boolean method_679() {
        int var0;
        if ((var0 = GL11.glGetInteger(32873)) <= 0 || set2.contains(var0)) {
            return false;
        } else if (set.contains(var0) && method_672(1, 4096) > 0) {
            return true;
        } else {
            set.remove(var0);
            if (!method_671()) {
                method_677();
            }

            if (method_671()) {
                set.add(var0);
                return true;
            } else {
                GL11.glTexParameteri(3553, 33085, 0);
                GL11.glTexParameterf(3553, 33083, 0.0F);
                set2.add(var0);
                return false;
            }
        }
    }
}
