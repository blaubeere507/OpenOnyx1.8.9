package client.onyx.render.ui;

import org.lwjgl.input.Keyboard;

public final class StatusIcons {
    private static final float[] floatValueArray = new float[]{0.0F, 0.0F};
    private static final float FLOAT_1_01 = 1.0F;
    private boolean flag;
    private boolean flag2;
    private boolean flag3;
    private boolean flag4;

    public float[] method_865(boolean var1) {
        boolean var7;
        if (var1 && Keyboard.isKeyDown(203)) {
            var7 = true;
        } else {
            var7 = false;
        }

        boolean var8;
        if (var1 && Keyboard.isKeyDown(205)) {
            var8 = true;
        } else {
            var8 = false;
        }

        boolean var9;
        if (var1 && Keyboard.isKeyDown(200)) {
            var9 = true;
        } else {
            var9 = false;
        }

        boolean var10;
        if (var1 && Keyboard.isKeyDown(208)) {
            var10 = true;
        } else {
            var10 = false;
        }

        float var11 = 0.0F;
        float var2 = 0.0F;
        if (var7 && !this.flag4) {
            var11--;
        }

        if (var8 && !this.flag) {
            var11++;
        }

        if (var9 && !this.flag2) {
            var2--;
        }

        if (var10 && !this.flag3) {
            var2++;
        }

        this.flag4 = var7;
        this.flag = var8;
        this.flag2 = var9;
        this.flag3 = var10;
        return var11 == 0.0F && var2 == 0.0F ? floatValueArray : new float[]{var11, var2};
    }
}
