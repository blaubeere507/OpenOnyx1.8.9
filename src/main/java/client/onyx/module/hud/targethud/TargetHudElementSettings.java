package client.onyx.module.hud.targethud;

import client.onyx.render.font.FontFiles;
import client.onyx.setting.Setting;
import client.onyx.setting.SettingSection;
import client.onyx.setting.ValueSetting;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.ColorSetting;
import client.onyx.setting.impl.EnumSetting;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.setting.impl.TextSetting;
import java.util.Iterator;
import java.util.function.IntSupplier;

public final class TargetHudElementSettings extends SettingSection {
    private static final double DOUBLE_150_01 = 150.0;
    private final TargetHudElement targetHudElement;
    public TextSetting text;
    public EnumSetting<HealthReadout> readout;
    public EnumSetting<FontFiles.Weight> weight;
    public NumberSetting size;
    public NumberSetting thickness;
    public NumberSetting offsetX;
    public NumberSetting offsetY;
    public ColorSetting colour;
    public NumberSetting glow;
    public BooleanSetting absorption;
    public BooleanSetting damageTrail;

    public boolean method_1504() {
        return this.method_61();
    }

    public float method_1505() {
        return this.targetHudElement.method_1512() ? 1.0F : this.size.method_151();
    }

    public TargetHudElementSettings(TargetHudElement var1, boolean var2, String var3, IntSupplier var4) {
        super(var1.getDisplayName(), var2);
        this.targetHudElement = var1;
        this.text = new TextSetting("Text", var3).describe("Supports {name} {health} {max} {hearts} {distance} {status}");
        method_1506(this.text, var1.method_1513());
        this.readout = new EnumSetting<>("Readout", HealthReadout.NUMBER).describe("How {health} and {max} are written out");
        method_1506(this.readout, var1.method_1514());
        this.weight = new EnumSetting<>(
                "Weight",
                FontFiles.Weight.MEDIUM,
                FontFiles.Weight.LIGHT,
                FontFiles.Weight.REGULAR,
                FontFiles.Weight.MEDIUM,
                FontFiles.Weight.SEMI_BOLD,
                FontFiles.Weight.BOLD
            )
            .describe("Font weight");
        method_1506(this.weight, var1.method_1513());
        this.size = new NumberSetting("Size", 1.0, 0.25, 3.0, 0.05).method_147("x");
        NumberSetting var10 = this.size;
        NumberSetting var6;
        boolean var7;
        if (var1.method_1512()) {
            NumberSetting var5 = var10;
            var7 = false;
            var6 = var5;
        } else {
            var7 = true;
            var6 = var10;
        }

        method_1506(var6, var7);
        this.thickness = new NumberSetting("Thickness", 3.0, 1.0, 12.0, 0.5);
        method_1506(this.thickness, var1.method_1512());
        this.offsetX = new NumberSetting("Offset X", 0.0, -150.0, 150.0, 0.5);
        this.offsetY = new NumberSetting("Offset Y", 0.0, -150.0, 150.0, 0.5);
        this.colour = new ColorSetting("Colour", -1).method_116().method_122(var4);
        this.glow = new NumberSetting("Glow", 0.0, 0.0, 12.0, 0.5).describe("Halo radius behind the element - 0 turns it off");
        this.absorption = new BooleanSetting("Absorption", true).describe("Count golden hearts as an extra bar segment");
        method_1506(this.absorption, var1.method_1512());
        this.damageTrail = new BooleanSetting("Damage trail", true).describe("Trailing bar that drains after a hit");
        method_1506(this.damageTrail, var1.method_1512());
    }

    private static void method_1506(Setting var0, boolean var1) {
        if (!var1) {
            var0.visibleWhen(Setting.booleanSupplier);
        }
    }

    public void method_1507() {
        this.getEnabled().reset();
        Iterator var3 = this.getList().iterator();

        while (var3.hasNext()) {
            Setting var2;
            if ((var2 = (Setting)var3.next()) instanceof ValueSetting) {
                ((ValueSetting)var2).reset();
            }
        }
    }

    public TargetHudElement getTargetHudElement() {
        return this.targetHudElement;
    }
}
