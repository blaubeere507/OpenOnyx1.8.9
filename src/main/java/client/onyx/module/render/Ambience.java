package client.onyx.module.render;

import client.onyx.event.impl.PacketDirection;
import client.onyx.event.impl.PacketEvent;
import client.onyx.event.impl.TickEvent;
import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.setting.SettingGroup;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.ColorSetting;
import client.onyx.setting.impl.NumberSetting;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S03PacketTimeUpdate;

public final class Ambience extends Module {
    public BooleanSetting timeLock = new BooleanSetting("Time lock", false).describe("Locks the client-side time of day without changing server time");
    public NumberSetting time = new NumberSetting("Time", 6000.0, 0.0, 24000.0, 100.0)
        .method_147(" ticks")
        .describe("World time to display (0 = sunrise, 6000 = noon, 18000 = midnight)")
        .visibleWhen(this.timeLock);
    public Ambience.LightSettings lightSettings = new Ambience.LightSettings();
    public Ambience.ColorGradingSettings colorGradingSettings = new Ambience.ColorGradingSettings();
    private volatile S03PacketTimeUpdate s03PacketTimeUpdate;

    @EventHandler
    private void onTick(TickEvent var1) {
        if (mc.theWorld != null) {
            if (this.timeLock.method_146()) {
                mc.theWorld.setWorldTime(this.time.method_149());
            } else {
                this.method_1302();
            }
        }
    }

    public Ambience() {
        super("Ambience", "Cinematic world lighting and color grading", Category.RENDER);
    }

    @Override
    public void onDisable() {
        this.method_1302();
    }

    private void method_1302() {
        S03PacketTimeUpdate var2 = this.s03PacketTimeUpdate;
        this.s03PacketTimeUpdate = null;
        if (var2 != null && mc.theWorld != null) {
            mc.theWorld.setTotalWorldTime(var2.getTotalWorldTime());
            mc.theWorld.setWorldTime(var2.getWorldTime());
        }
    }

    public boolean method_1303() {
        return this.isToggled() && (this.lightSettings.method_61() || this.colorGradingSettings.method_61());
    }

    @EventHandler
    private void onPacket(PacketEvent var1) {
        if (var1.getPacketDirection() == PacketDirection.INCOMING) {
            if (!var1.isCancelled()) {
                if (this.timeLock.method_146()) {
                    Packet var3;
                    if ((var3 = var1.getPacket()) instanceof S03PacketTimeUpdate) {
                        S03PacketTimeUpdate var2 = (S03PacketTimeUpdate)var3;
                        this.s03PacketTimeUpdate = var2;
                        var1.cancel();
                    }
                }
            }
        }
    }

    @Override
    public void onEnable() {
        this.s03PacketTimeUpdate = null;
    }

    public final class ColorGradingSettings extends SettingGroup {
        public NumberSetting saturation;
        public NumberSetting contrast;
        public NumberSetting vignette;
        public NumberSetting filmGrain;

        private ColorGradingSettings() {
            super("Color Grading", false);
            String var10004 = "Color Grading";
            boolean var10005 = false;
            this.saturation = new NumberSetting("Saturation", 100.0, 0.0, 180.0, 5.0).method_147("%");
            this.contrast = new NumberSetting("Contrast", 100.0, 60.0, 160.0, 5.0).method_147("%");
            this.vignette = new NumberSetting("Vignette", 10.0, 0.0, 80.0, 5.0).method_147("%");
            this.filmGrain = new NumberSetting("Film Grain", 0.0, 0.0, 30.0, 1.0).method_147("%");
        }
    }

    public final class LightSettings extends SettingGroup {
        public ColorSetting color;
        public NumberSetting strength;

        private LightSettings() {
            super("Light Color", false);
            String var10002 = "Light Color";
            boolean var10003 = false;
            this.color = new ColorSetting("Color", -6718).method_116();
            this.strength = new NumberSetting("Strength", 18.0, 0.0, 100.0, 1.0).method_147("%");
        }
    }
}
