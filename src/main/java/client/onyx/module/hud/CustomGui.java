package client.onyx.module.hud;

import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.render.font.FontFiles;
import client.onyx.render.gui.ChatOpenAnimation;
import client.onyx.render.gui.CustomFontRenderer;
import client.onyx.render.gui.GuiOpenAnimation;
import client.onyx.render.gui.HotbarAnimator;
import client.onyx.setting.NamedEnum;
import client.onyx.setting.SettingGroup;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.EnumSetting;
import client.onyx.setting.impl.NumberSetting;

public final class CustomGui extends Module {
    public final CustomGui.FontSettings fontSettings = new CustomGui.FontSettings();
    public final CustomGui.ChatSettings chatSettings = new CustomGui.ChatSettings();
    public final CustomGui.ScoreboardSettings scoreboardSettings = new CustomGui.ScoreboardSettings();
    public final CustomGui.TitleSettings titleSettings = new CustomGui.TitleSettings();
    public final CustomGui.BossbarSettings bossbarSettings = new CustomGui.BossbarSettings();
    public final CustomGui.HotbarSettings hotbarSettings = new CustomGui.HotbarSettings();
    public final CustomGui.StatusBarSettings statusBarSettings = new CustomGui.StatusBarSettings();
    public final CustomGui.ShadowSettings shadowSettings = new CustomGui.ShadowSettings();
    public final CustomGui.OpenAnimationSettings openAnimationSettings = new CustomGui.OpenAnimationSettings();

    public CustomGui() {
        super("CustomGui", "Reskins Minecraft's own HUD and screens", Category.HUD);
    }

    @Override
    public void onDisable() {
        ChatOpenAnimation.method_995();
        GuiOpenAnimation.method_869();
        HotbarAnimator.method_981();
    }

    public static final class BossbarSettings extends SettingGroup {
        public final BooleanSetting segments = new BooleanSetting("Segments", false).describe("Break the bar into notches instead of one continuous fill");
        public final NumberSetting positionX = new NumberSetting("Position X", 50.0, 0.0, 100.0, 0.01).visibleWhen(() -> false);
        public final NumberSetting positionY = new NumberSetting("Position Y", 2.0, 0.0, 100.0, 0.01).visibleWhen(() -> false);
        public final NumberSetting scale = new NumberSetting("Scale", 1.0, 0.5, 2.0, 0.05).visibleWhen(() -> false);

        private BossbarSettings() {
            super("Bossbar", true);
        }
    }

    public static final class ChatSettings extends SettingGroup {
        public final NumberSetting hotbarLift = new NumberSetting("Hotbar lift", 14.0, 0.0, 32.0, 1.0)
            .method_147(" px")
            .describe("How far the hotbar moves up to make room for the chat box");
        public final NumberSetting duration = new NumberSetting("Duration", 300.0, 80.0, 600.0, 10.0)
            .method_147(" ms")
            .describe("How long the chat takes to open and close");
        public final BooleanSetting smoothCaret = new BooleanSetting("Smooth caret", true)
            .describe("The text cursor glides as you type and breathes instead of blinking");

        private ChatSettings() {
            super("Chat", true);
        }
    }

    public static final class FontSettings extends SettingGroup {
        public final EnumSetting<CustomGui.FontWeight> weight = new EnumSetting<>("Weight", CustomGui.FontWeight.MEDIUM)
            .onChange(var0 -> CustomFontRenderer.method_932())
            .describe("How heavy the replacement font sits against the world");
        public final NumberSetting size = new NumberSetting("Size", 1.0, 0.85, 1.2, 0.01)
            .method_147("x")
            .onChange(var0 -> CustomFontRenderer.method_932())
            .describe("Scales the letters inside the 9px line the game lays text out on");
        public final NumberSetting letterSpacing = new NumberSetting("Letter spacing", 0.0, -1.0, 1.0, 0.05)
            .method_147(" px")
            .describe("Tightens or opens the gap between letters, in the game's own pixels");
        public final BooleanSetting shadow = new BooleanSetting("Shadow", true).describe("Keep the drop shadow the game asks for");

        private FontSettings() {
            super("Font", true);
        }
    }

    public static enum FontWeight implements NamedEnum {
        REGULAR("Regular", FontFiles.Weight.REGULAR),
        MEDIUM("Medium", FontFiles.Weight.MEDIUM);

        private final String string;
        private final FontFiles.Weight weight;

        @Override
        public String getDisplayName() {
            return this.string;
        }

        private FontWeight(String var3, FontFiles.Weight var4) {
            this.string = var3;
            this.weight = var4;
        }

        public FontFiles.Weight getWeight() {
            return this.weight;
        }
    }

    public static final class HotbarSettings extends SettingGroup {
        public final NumberSetting scale = new NumberSetting("Scale", 1.0, 0.6, 1.6, 0.05).method_147("x");
        public final BooleanSetting slotGlide = new BooleanSetting("Slot glide", true).describe("The selection slides between slots instead of jumping");
        public final BooleanSetting itemPop = new BooleanSetting("Item pop", true).describe("Items grow into place when a slot changes");
        public final BooleanSetting blur = new BooleanSetting("Blur", true).describe("Frost the bar over what is behind it (needs ClickGui's frosted glass)");

        private HotbarSettings() {
            super("Hotbar", true);
        }
    }

    public static final class OpenAnimationSettings extends SettingGroup {
        public final NumberSetting duration = new NumberSetting("Duration", 300.0, 80.0, 600.0, 10.0).method_147(" ms");
        public final NumberSetting scaleFrom = new NumberSetting("Scale from", 0.92, 0.5, 1.0, 0.01)
            .method_147("x")
            .describe("How small the screen starts before it settles");
        public final BooleanSetting fade = new BooleanSetting("Fade", true).describe("Fade the whole screen in, not just scale it");
        public final BooleanSetting centeredContainers = new BooleanSetting("Centered containers", true)
            .describe("Stop the inventory sliding sideways when a potion effect is running");

        private OpenAnimationSettings() {
            super("Open animation", true);
        }
    }

    public static final class ScoreboardSettings extends SettingGroup {
        public final NumberSetting background = new NumberSetting("Background", 70.0, 0.0, 100.0, 1.0)
            .method_147("%")
            .describe("Opacity of the card behind the rows");
        public final NumberSetting minWidth = new NumberSetting("Min width", 60.0, 40.0, 260.0, 1.0)
            .method_147(" px")
            .describe("The card never draws narrower than this, however short its rows are");
        public final BooleanSetting blur = new BooleanSetting("Blur", true).describe("Frost the card over what is behind it (needs ClickGui's frosted glass)");
        public final NumberSetting positionX = new NumberSetting("Position X", 100.0, 0.0, 100.0, 0.01).visibleWhen(() -> false);
        public final NumberSetting positionY = new NumberSetting("Position Y", 45.0, 0.0, 100.0, 0.01).visibleWhen(() -> false);
        public final NumberSetting scale = new NumberSetting("Scale", 1.0, 0.5, 2.0, 0.05).visibleWhen(() -> false);

        private ScoreboardSettings() {
            super("Scoreboard", true);
        }
    }

    public static final class ShadowSettings extends SettingGroup {
        public final NumberSetting keepDim = new NumberSetting("Keep dim", 0.0, 0.0, 100.0, 1.0)
            .method_147("%")
            .describe("How much of the vanilla darkening to keep");

        private ShadowSettings() {
            super("No GUI shadow", true);
        }
    }

    public static enum StatusBarMode implements NamedEnum {
        ICONS("Icons"),
        BARS("Bars");

        private final String string;

        @Override
        public String getDisplayName() {
            return this.string;
        }

        private StatusBarMode(String var3) {
            this.string = var3;
        }
    }

    public static final class StatusBarSettings extends SettingGroup {
        public final EnumSetting<CustomGui.StatusBarMode> mode = new EnumSetting<>("Mode", CustomGui.StatusBarMode.ICONS)
            .describe("Redrawn icons, or continuous progress bars");
        public final BooleanSetting animate = new BooleanSetting("Animate", true).describe("Pop on gain, shake on damage, pulse when low");
        public final BooleanSetting damageTrail = new BooleanSetting("Damage trail", true).describe("A lighter ghost that catches up to the value after a hit");

        private StatusBarSettings() {
            super("Status bars", true);
        }
    }

    public static final class TitleSettings extends SettingGroup {
        public final NumberSetting positionX = new NumberSetting("Position X", 50.0, 0.0, 100.0, 0.01).visibleWhen(() -> false);
        public final NumberSetting positionY = new NumberSetting("Position Y", 38.0, 0.0, 100.0, 0.01).visibleWhen(() -> false);
        public final NumberSetting scale = new NumberSetting("Scale", 1.0, 0.5, 2.0, 0.05).visibleWhen(() -> false);

        private TitleSettings() {
            super("Title", true);
        }
    }
}
