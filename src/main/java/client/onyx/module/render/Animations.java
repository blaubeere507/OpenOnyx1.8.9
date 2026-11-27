package client.onyx.module.render;

import client.onyx.Onyx;
import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.module.ModuleManager;
import client.onyx.setting.impl.EnumSetting;

public class Animations extends Module {
    public EnumSetting<Animations.BlockAnimation> block = new EnumSetting<>("Block", Animations.BlockAnimation.SMOOTH)
        .describe("Raises the sword higher and lets it sweep along with the swing");
    public EnumSetting<Animations.SwingAnimation> swing = new EnumSetting<>("Swing", Animations.SwingAnimation.SMOOTH)
        .describe("Drives the item bob off the equip progress instead of the swing progress");

    public Animations() {
        super("Animations", "Changes the swinging and blocking item animations", Category.RENDER);
    }

    public static boolean method_1266() {
        Animations var0;
        return (var0 = method_1268()) != null && var0.isToggled() && var0.block.method_168(Animations.BlockAnimation.SMOOTH);
    }

    public static boolean method_1267() {
        Animations var0;
        return (var0 = method_1268()) != null && var0.isToggled() && var0.swing.method_168(Animations.SwingAnimation.SMOOTH);
    }

    private static Animations method_1268() {
        ModuleManager var0 = Onyx.moduleManager;
        return Onyx.moduleManager == null ? null : var0.animations;
    }

    public static enum BlockAnimation {
        VANILLA,
        SMOOTH;
    }

    public static enum SwingAnimation {
        VANILLA,
        SMOOTH;
    }
}
