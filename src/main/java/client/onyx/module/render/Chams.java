package client.onyx.module.render;

import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.setting.SettingSection;
import client.onyx.setting.TabbedSettingGroup;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.ColorSetting;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.ui.theme.color.ColorUtils;
import client.onyx.util.TargetGroup;
import net.minecraft.entity.EntityLivingBase;

public class Chams extends Module {
    public final Chams.TargetSettings targetSettings = new Chams.TargetSettings();

    public float method_1178(TargetGroup var1) {
        Chams.ChamsSettings var3;
        return (var3 = this.targetSettings.method_1217(var1)).keepModel.method_146() ? var3.modelOpacity.method_151() / 100.0F : 0.0F;
    }

    public int method_1179(TargetGroup var1) {
        Chams.ChamsSettings var5 = this.targetSettings.method_1217(var1);
        int var3 = Math.round(Math.clamp(var5.opacity.method_151() / 100.0F, 0.05F, 1.0F) * 255.0F);
        return ColorUtils.method_199(var5.color.method_118(), var3);
    }

    public float method_1180(TargetGroup var1) {
        return this.targetSettings.method_1217(var1).wallOpacity.method_151() / 100.0F;
    }

    public Chams.ChamsSettings method_1181(TargetGroup var1) {
        return this.targetSettings.method_1217(var1);
    }

    public boolean method_1182(TargetGroup var1) {
        return this.targetSettings.method_1217(var1).throughWalls.method_146();
    }

    public Chams() {
        super("Chams", "Reskins entities with a graded color tint on the model itself", Category.RENDER);
    }

    public TargetGroup method_1183(EntityLivingBase var1) {
        if (!this.isToggled()) {
            return null;
        } else {
            TargetGroup var3;
            if ((var3 = TargetGroup.method_2141(var1)) == null) {
                return null;
            } else {
                return this.targetSettings.method_1217(var3).method_61() ? var3 : null;
            }
        }
    }

    public static final class ChamsSettings extends SettingSection {
        public final ColorSetting color;
        public final NumberSetting opacity;
        public final NumberSetting strength;
        public final BooleanSetting keepModel;
        public final NumberSetting modelOpacity;
        public final BooleanSetting throughWalls;
        public final NumberSetting wallOpacity;

        private ChamsSettings(String var1, boolean var2, int var3, boolean var4) {
            super(var1, var2);
            this.color = new ColorSetting("Color", var3);
            if (var4) {
                this.color.method_116();
            }

            this.opacity = new NumberSetting("Opacity", 100.0, 5.0, 100.0, 5.0).method_147("%");
            this.strength = new NumberSetting("Strength", 70.0, 0.0, 100.0, 5.0)
                .method_147("%")
                .describe("How far the skin is graded towards the tint - at 0% the skin is left exactly as the game drew it");
            this.keepModel = new BooleanSetting("Keep model", false).describe("Leaves the untouched model visible under the shading");
            this.modelOpacity = new NumberSetting("Model opacity", 50.0, 0.0, 100.0, 5.0).method_147("%").visibleWhen(this.keepModel::method_146);
            this.throughWalls = new BooleanSetting("Through walls", false)
                .describe("Draws a second pass that ignores depth, so the model stays readable behind blocks");
            this.wallOpacity = new NumberSetting("Wall opacity", 40.0, 5.0, 100.0, 5.0)
                .method_147("%")
                .describe("How strong the occluded pass is against the visible one")
                .visibleWhen(this.throughWalls::method_146);
        }
    }

    public static final class TargetSettings extends TabbedSettingGroup {
        public final Chams.ChamsSettings self = new Chams.ChamsSettings("Self", false, -2562832, true);
        public final Chams.ChamsSettings enemy = new Chams.ChamsSettings("Enemy", true, -3910337, true);
        public final Chams.ChamsSettings team = new Chams.ChamsSettings("Team", false, -8585312, false);
        public final Chams.ChamsSettings mobs = new Chams.ChamsSettings("Mobs", false, -3105793, false);

        private TargetSettings() {
            super("Targets", TargetGroup.stringArray);
        }

        public Chams.ChamsSettings method_1217(TargetGroup var1) {
            switch (var1) {
                case SELF:
                    return this.self;
                case ENEMY:
                    return this.enemy;
                case TEAM:
                    return this.team;
                case MOBS:
                    return this.mobs;
                default:
                    throw new MatchException(null, null);
            }
        }
    }
}
