package client.onyx.module.render;

import client.onyx.Onyx;
import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.module.ModuleManager;
import client.onyx.setting.SettingSection;
import client.onyx.setting.TabbedSettingGroup;
import client.onyx.setting.impl.ColorSetting;
import client.onyx.setting.impl.EnumSetting;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.util.TargetGroup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class GlowESP extends Module {
    private static final String STRING_CONST = "onyx_glow_outline";
    private static final String STRING_CONST2 = "entity_outline";
    private static final String STRING_CONST3 = "onyx_glow_fill";
    public final GlowESP.TargetSettings targetSettings = new GlowESP.TargetSettings();
    public EnumSetting<GlowESP.GlowMode> mode = new EnumSetting<>("Mode", GlowESP.GlowMode.OUTLINE);
    public NumberSetting radius = new NumberSetting("Radius", 14.0, 2.0, 24.0, 1.0)
        .method_147(" px")
        .describe("How far the glow spreads around the entity")
        .visibleWhen(this.mode, var0 -> var0 == GlowESP.GlowMode.OUTLINE);
    public NumberSetting intensity = new NumberSetting("Intensity", 40.0, 10.0, 100.0, 5.0).method_147("%").describe("Opacity of the outer glow");

    public static String method_1237() {
        GlowESP var0;
        if ((var0 = method_1242()) == null || !var0.isToggled()) {
            return "entity_outline";
        } else {
            return var0.method_1239() ? "onyx_glow_fill" : "onyx_glow_outline";
        }
    }

    public static boolean method_1238(EntityLivingBase var0) {
        GlowESP var2;
        return (var2 = method_1242()) != null && var2.method_1241(var0);
    }

    public boolean method_1239() {
        return this.isToggled() && this.mode.method_168(GlowESP.GlowMode.FILL);
    }

    public GlowESP.ColorSettings method_1240(EntityLivingBase var1) {
        if (!this.isToggled()) {
            return null;
        } else {
            TargetGroup var3;
            if ((var3 = TargetGroup.method_2141(var1)) == null) {
                return null;
            } else {
                GlowESP.ColorSettings var4;
                return (var4 = this.targetSettings.method_1307(var3)).method_61() ? var4 : null;
            }
        }
    }

    public boolean method_1241(EntityLivingBase var1) {
        return this.method_1240(var1) != null;
    }

    public static GlowESP method_1242() {
        ModuleManager var0 = Onyx.moduleManager;
        return Onyx.moduleManager == null ? null : var0.glowESP;
    }

    public static int method_1243(EntityLivingBase var0) {
        GlowESP var1;
        GlowESP.ColorSettings var3;
        if ((var1 = method_1242()) == null) {
            var3 = null;
        } else {
            var3 = var1.method_1240(var0);
        }

        return var3 == null ? 16777215 : var3.color.method_118() & 16777215;
    }

    public GlowESP() {
        super("GlowESP", "Outline glow or liquid-metal fill for selected entities", Category.RENDER);
    }

    public static boolean method_1244(Entity var0) {
        GlowESP var2;
        return (var2 = method_1242()) == null || !var2.isToggled()
            ? var0 instanceof EntityPlayer
            : var0 instanceof EntityLivingBase && var2.method_1241((EntityLivingBase)var0);
    }

    public static boolean method_1245() {
        GlowESP var0;
        return (var0 = method_1242()) != null && var0.isToggled();
    }

    public static final class ColorSettings extends SettingSection {
        public final ColorSetting color;

        private ColorSettings(String var1, boolean var2, int var3, boolean var4) {
            super(var1, var2);
            this.color = new ColorSetting("Color", var3);
            if (var4) {
                this.color.method_116();
            }
        }
    }

    public static enum GlowMode {
        OUTLINE,
        FILL;
    }

    public static final class TargetSettings extends TabbedSettingGroup {
        public final GlowESP.ColorSettings self = new GlowESP.ColorSettings("Self", false, -2562832, false);
        public final GlowESP.ColorSettings enemy = new GlowESP.ColorSettings("Enemy", true, -8857601, false);
        public final GlowESP.ColorSettings team = new GlowESP.ColorSettings("Team", false, -8585312, false);
        public final GlowESP.ColorSettings mobs = new GlowESP.ColorSettings("Mobs", false, -3105793, false);

        private TargetSettings() {
            super("Targets", TargetGroup.stringArray);
        }

        public GlowESP.ColorSettings method_1307(TargetGroup var1) {
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
