package client.onyx.module.render;

import client.onyx.module.Category;
import client.onyx.module.Module;
import net.minecraft.client.renderer.GlStateManager;

public final class FogRemove extends Module {
    private static final float FLOAT_8_5070587E371 = 8.5070587E37F;
    private static final float FLOAT_1_7014117E381 = 1.7014117E38F;

    public void method_1236(int var1) {
        if (this.isToggled()) {
            GlStateManager.setFog(9729);
            GlStateManager.setFogDensity(0.0F);
            GlStateManager.setFogStart(8.5070587E37F);
            GlStateManager.setFogEnd(1.7014117E38F);
        }
    }

    public FogRemove() {
        super("FogRemove", "Removes first-person environmental and distance fog", Category.RENDER);
    }
}
