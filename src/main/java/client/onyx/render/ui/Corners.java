package client.onyx.render.ui;

import net.minecraft.client.renderer.GlStateManager;

public final class Corners {
    public static final Corners INSTANCE = new Corners();

    public Corners method_648(float var1) {
        GlStateManager.rotate((float)Math.toDegrees(var1), 0.0F, 0.0F, 1.0F);
        return this;
    }

    public Corners method_649() {
        GlStateManager.popMatrix();
        return this;
    }

    private Corners() {
    }

    public Corners method_650(float var1, float var2) {
        GlStateManager.translate(var1, var2, 0.0F);
        return this;
    }

    public Corners method_651() {
        GlStateManager.pushMatrix();
        return this;
    }

    public Corners method_652(float var1, float var2) {
        GlStateManager.scale(var1, var2, 1.0F);
        return this;
    }
}
