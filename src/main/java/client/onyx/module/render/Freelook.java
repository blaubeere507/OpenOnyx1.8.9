package client.onyx.module.render;

import client.onyx.event.impl.TickEvent;
import client.onyx.event.impl.WorldLoadEvent;
import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.render.world.CameraState;
import client.onyx.setting.NamedEnum;
import client.onyx.setting.impl.EnumSetting;
import client.onyx.setting.impl.NumberSetting;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.MathHelper;

public final class Freelook extends Module {
    private static final double DOUBLE_0_151 = 0.15;
    public final EnumSetting<Freelook.Perspective> perspective = new EnumSetting<>("Perspective", Freelook.Perspective.BACK)
        .describe("Which way the detached camera faces");
    public final NumberSetting sensitivity = new NumberSetting("Sensitivity", 100.0, 25.0, 200.0, 5.0)
        .method_147("%")
        .describe("Mouse speed while looking around");
    private float floatValue;
    private float floatValue2;
    private boolean flag;
    private float floatValue3;
    private int intValue;
    private float floatValue4;

    @EventHandler
    private void onWorldLoad(WorldLoadEvent var1) {
        this.method_1174();
    }

    @Override
    public void onDisable() {
        this.method_1174();
    }

    private void method_1174() {
        if (this.flag) {
            this.flag = false;
            mc.gameSettings.thirdPersonView = this.intValue;
        }
    }

    @EventHandler
    private void onTick(TickEvent var1) {
        this.floatValue3 = this.floatValue4;
        this.floatValue = this.floatValue2;
    }

    public boolean method_1175(float var1, float var2) {
        if (this.isToggled() && mc.thePlayer != null) {
            this.method_1177();
            double var3 = 0.15 * (this.sensitivity.method_151() / 100.0);
            this.floatValue4 += (float)(var1 * var3);
            this.floatValue2 = MathHelper.clamp_float((float)(this.floatValue2 - var2 * var3), -90.0F, 90.0F);
            return true;
        } else {
            return false;
        }
    }

    public Freelook() {
        super("Freelook", "Look around without turning your player", Category.RENDER);
    }

    @Override
    public void onEnable() {
        this.flag = false;
    }

    public void method_1176() {
        if (this.isToggled() && mc.thePlayer != null) {
            this.method_1177();
            CameraState.method_831(this.floatValue4, this.floatValue2, this.floatValue3, this.floatValue);
        }
    }

    private void method_1177() {
        if (!this.flag && mc.thePlayer != null) {
            this.floatValue4 = this.floatValue3 = mc.thePlayer.rotationYaw;
            this.floatValue2 = this.floatValue = mc.thePlayer.rotationPitch;
            this.intValue = mc.gameSettings.thirdPersonView;
            mc.gameSettings.thirdPersonView = this.perspective.getValue().getIntValue();
            this.flag = true;
        }
    }

    public static enum Perspective implements NamedEnum {
        BACK("Behind", 1),
        FRONT("In front", 2);

        private final String string;
        private final int intValue;

        private Perspective(String var3, int var4) {
            this.string = var3;
            this.intValue = var4;
        }

        public int getIntValue() {
            return this.intValue;
        }

        @Override
        public String getDisplayName() {
            return this.string;
        }
    }
}
