package client.onyx.module.render;

import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.setting.NamedEnum;
import client.onyx.setting.impl.ColorSetting;
import client.onyx.setting.impl.EnumSetting;
import client.onyx.setting.impl.NumberSetting;

public class Skybox extends Module {
    private static final long longValue = System.nanoTime();
    public EnumSetting<Skybox.SkyPreset> preset = new EnumSetting<>("Preset", Skybox.SkyPreset.CLOUDS).describe("Sky theme to paint");
    public ColorSetting color = new ColorSetting("Color", -9335297).method_116().describe("Fixed sky color");
    public NumberSetting animationSpeed = new NumberSetting("Animation Speed", 1.0, 0.1, 5.0, 0.1).method_147("x").describe("Controls how fast the sky moves");
    public NumberSetting scale = new NumberSetting("Scale", 5.0, 1.0, 20.0, 0.5)
        .describe("Size of the pattern across the sky")
        .visibleWhen(() -> this.preset.getValue().method_1100());
    public NumberSetting intensity = new NumberSetting("Intensity", 1.0, 0.1, 5.0, 0.1)
        .method_147("%")
        .describe("Brightness the pattern is pushed to")
        .visibleWhen(() -> this.preset.getValue().method_1100());
    public NumberSetting strikeInterval = new NumberSetting("Strike Interval", 4.0, 1.0, 10.0, 0.5)
        .method_147("s")
        .describe("Seconds between lightning rolls")
        .visibleWhen(() -> this.preset.method_168(Skybox.SkyPreset.THUNDER));
    public NumberSetting strikeChance = new NumberSetting("Strike Chance", 0.65, 0.0, 1.0, 0.05)
        .describe("Odds a roll actually produces a bolt")
        .visibleWhen(() -> this.preset.method_168(Skybox.SkyPreset.THUNDER));
    public NumberSetting strikeGlow = new NumberSetting("Strike Glow", 1.0, 0.1, 3.0, 0.1)
        .method_147("x")
        .describe("How hard a bolt lights the storm")
        .visibleWhen(() -> this.preset.method_168(Skybox.SkyPreset.THUNDER));
    public EnumSetting<Skybox.Quality> quality = new EnumSetting<>("Quality", Skybox.Quality.BALANCED)
        .describe("Noise detail; fewer layers cost far less GPU time");
    public NumberSetting resolution = new NumberSetting("Resolution", 50.0, 25.0, 100.0, 25.0)
        .method_147("%")
        .describe("Fraction of the screen resolution the sky is drawn at");

    public float method_1198() {
        return this.strikeInterval.method_151();
    }

    public float method_1199() {
        return this.animationSpeed.method_151();
    }

    public int method_1200() {
        return this.color.method_118();
    }

    public boolean method_1201() {
        return this.isToggled();
    }

    public int method_1202() {
        return this.preset.getValue().method_1099();
    }

    public float method_1203() {
        return 0.85F;
    }

    public float method_1204() {
        return (float)(System.nanoTime() - longValue) / 1.0E9F;
    }

    public float method_1205() {
        return this.intensity.method_151() / 100.0F;
    }

    public float method_1206() {
        return this.strikeChance.method_151();
    }

    public float method_1207() {
        return this.strikeGlow.method_151();
    }

    public float method_1208() {
        return Math.clamp(this.resolution.method_151() / 100.0F, 0.25F, 1.0F);
    }

    public float method_1209() {
        return this.scale.method_151();
    }

    public Skybox() {
        super("Skybox", "Animated sky with a fixed custom color", Category.RENDER);
    }

    public int method_1210() {
        return this.quality.getValue().getIntValue();
    }

    public static enum Quality implements NamedEnum {
        FAST("Fast", 3),
        BALANCED("Balanced", 4),
        DETAILED("Detailed", 6);

        private final String string;
        private final int intValue;

        private Quality(String var3, int var4) {
            this.string = var3;
            this.intValue = var4;
        }

        @Override
        public String getDisplayName() {
            return this.string;
        }

        public int getIntValue() {
            return this.intValue;
        }
    }

    public static enum SkyPreset {
        CLOUDS,
        THUNDER,
        PULSAR;

        public int method_1099() {
            return this.ordinal();
        }

        public boolean method_1100() {
            return this == THUNDER || this == PULSAR;
        }
    }
}
