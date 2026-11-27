package client.onyx.module.render;

import client.onyx.event.impl.WorldLoadEvent;
import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.render.effect.PostProcessing;
import client.onyx.render.shader.ShaderProgram;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.ColorSetting;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.ui.theme.color.ColorUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.MathHelper;

public final class Hurtcam extends Module {
    private static final float FLOAT_100_01 = 100.0F;
    public final ColorSetting color = new ColorSetting("Color", -49088).describe("Tint of the damage wash");
    public final NumberSetting intensity = new NumberSetting("Intensity", 45.0, 5.0, 100.0, 5.0)
        .method_147("%")
        .describe("How strong the wash gets at full health loss");
    public final NumberSetting size = new NumberSetting("Size", 55.0, 10.0, 100.0, 5.0).method_147("%").describe("How far in from the edge the wash reaches");
    public final NumberSetting duration = new NumberSetting("Duration", 450.0, 100.0, 1500.0, 50.0)
        .method_147("ms")
        .describe("How long the wash takes to fade out");
    public final BooleanSetting scaleWithDamage = new BooleanSetting("Scale with damage", true).describe("Bigger hits flash brighter");
    public final BooleanSetting directional = new BooleanSetting("Directional", false).describe("Bias the wash towards where the hit came from");
    private long longValue;
    private float floatValue;
    private int intValue;
    private ShaderProgram shaderProgram;
    private boolean flag;
    private float floatValue2;
    private float floatValue3;

    public Hurtcam() {
        super("Hurtcam", "Soft red flash around the screen when you take damage", Category.RENDER);
    }

    private void method_1157() {
        this.floatValue3 = this.floatValue2 = 0.0F;
        this.intValue = 0;
        this.floatValue = 0.0F;
        this.longValue = 0L;
    }

    @Override
    public void onEnable() {
        this.method_1157();
    }

    @Override
    public void onDisable() {
        this.method_1157();
    }

    public void method_1158() {
        if (this.isToggled() && mc.thePlayer != null) {
            this.method_1159();
            if (!(this.floatValue2 <= 0.002F)) {
                if (PostProcessing.method_538()) {
                    if (!this.flag) {
                        this.shaderProgram = ShaderProgram.method_814("onyx_hurt", "onyx_post", "onyx_hurt");
                        this.flag = true;
                    }

                    if (this.shaderProgram != null && this.shaderProgram.isFlag()) {
                        int var1 = this.color.getValue();
                        float var2 = this.floatValue2 * (this.intensity.method_151() / 100.0F);
                        float var3 = this.size.method_151() / 100.0F;
                        boolean var5 = this.directional.method_146();
                        PostProcessing.method_535(
                            this.shaderProgram,
                            var5x -> {
                                var5x.method_810(
                                    "uTint", ColorUtils.method_201(var1) / 255.0F, ColorUtils.method_210(var1) / 255.0F, ColorUtils.method_214(var1) / 255.0F
                                );
                                var5x.method_821("uStrength", var2 * (ColorUtils.method_213(var1) / 255.0F));
                                var5x.method_821("uReach", var3);
                                var5x.method_821("uBias", this.floatValue);
                                ShaderProgram var8;
                                String var9;
                                float var10;
                                if (!var5) {
                                    String var7 = "uDirectional";
                                    var10 = 0.0F;
                                    var9 = var7;
                                    var8 = var5x;
                                } else {
                                    var10 = 1.0F;
                                    var9 = "uDirectional";
                                    var8 = var5x;
                                }

                                var8.method_821(var9, var10);
                            }
                        );
                    }
                }
            }
        }
    }

    private void method_1159() {
        long var1 = System.nanoTime();
        float var4;
        if (this.longValue == 0L) {
            var4 = 16.0F;
        } else {
            var4 = Math.min((float)(var1 - this.longValue) / 1000000.0F, 100.0F);
        }

        this.longValue = var1;
        int var10 = mc.thePlayer.hurtTime;
        if (mc.thePlayer.hurtTime > this.intValue) {
            this.floatValue3 = 1.0F;
            if (this.scaleWithDamage.method_146()) {
                float var2 = Math.max(1.0F, (float)mc.thePlayer.maxHurtTime);
                this.floatValue3 = MathHelper.clamp_float(var10 / var2, 0.35F, 1.0F);
            }

            this.floatValue2 = Math.max(this.floatValue2, this.floatValue3);
            this.floatValue = mc.thePlayer.attackedAtYaw;
        }

        this.intValue = var10;
        float var11 = this.duration.method_151();
        Hurtcam var7;
        float var8;
        float var9;
        if (!(var11 <= 0.0F)) {
            float var6 = this.floatValue2;
            var9 = var4 / var11 * this.floatValue3;
            var8 = var6;
            var7 = this;
        } else {
            var9 = this.floatValue2;
            var8 = this.floatValue2;
            var7 = this;
        }

        var7.floatValue2 = var8 - var9;
        if (this.floatValue2 < 0.0F) {
            this.floatValue2 = 0.0F;
        }
    }

    @EventHandler
    private void onWorldLoad(WorldLoadEvent var1) {
        this.method_1157();
    }
}
