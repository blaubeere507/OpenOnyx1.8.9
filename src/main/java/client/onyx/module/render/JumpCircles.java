package client.onyx.module.render;

import client.onyx.event.impl.PostPlayerUpdateEvent;
import client.onyx.event.impl.WorldLoadEvent;
import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.render.world.JumpCircle;
import client.onyx.render.world.JumpCircleParams;
import client.onyx.render.world.JumpCircleRenderer;
import client.onyx.setting.NamedEnum;
import client.onyx.setting.SettingGroup;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.ColorSetting;
import client.onyx.setting.impl.EnumSetting;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.setting.impl.TextSetting;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

public final class JumpCircles extends Module {
    private static final double DOUBLE_0_011 = 0.01;
    public final EnumSetting<JumpCircles.CircleMode> mode = new EnumSetting<>("Mode", JumpCircles.CircleMode.RING);
    public final TextSetting text = new TextSetting("Text", "ONYX")
        .describe("Written around the ring, repeated all the way round")
        .visibleWhen(this.mode, var0 -> var0 == JumpCircles.CircleMode.TEXT);
    public final NumberSetting textSize = new NumberSetting("Text size", 0.22, 0.05, 0.6, 0.01)
        .describe("How tall the letters stand, in blocks")
        .visibleWhen(this.mode, var0 -> var0 == JumpCircles.CircleMode.TEXT);
    public final NumberSetting spinSpeed = new NumberSetting("Spin speed", 40.0, -360.0, 360.0, 5.0)
        .method_147("°/s")
        .describe("How fast the text travels around the ring")
        .visibleWhen(this.mode, var0 -> var0 == JumpCircles.CircleMode.TEXT);
    public final ColorSetting color = new ColorSetting("Color", -10178561).method_116();
    public final NumberSetting radius = new NumberSetting("Radius", 1.5, 0.3, 6.0, 0.1).describe("How wide the circle opens out to");
    public final NumberSetting opacity = new NumberSetting("Opacity", 70.0, 5.0, 100.0, 1.0).method_147("%").describe("Overall opacity of the circles");
    public final NumberSetting duration = new NumberSetting("Duration", 2.0, 0.25, 8.0, 0.25)
        .method_147(" s")
        .describe("How long a circle lasts before it is gone");
    public final BooleanSetting otherPlayers = new BooleanSetting("Other players", false).describe("Leave circles under everyone else's jumps too");
    public final BooleanSetting throughWalls = new BooleanSetting("Through walls", false);
    public final JumpCircles.DistortionSettings distortionSettings = new JumpCircles.DistortionSettings();
    private final JumpCircleRenderer jumpCircleRenderer = new JumpCircleRenderer();
    private final List<JumpCircleParams> list = new ArrayList<>();
    private final Map<Integer, Vec3> map = new HashMap<>();

    @Override
    public void onEnable() {
        this.list.clear();
        this.map.clear();
    }

    @EventHandler
    private void onPostPlayerUpdate(PostPlayerUpdateEvent var1) {
        var var3 = this;
        if (mc.thePlayer != null && mc.theWorld != null) {
            this.method_1269();
            HashSet var7 = new HashSet();
            Iterator var2 = mc.theWorld.playerEntities.iterator();

            label41:
            while (true) {
                Iterator var5 = var2;

                while (var5.hasNext()) {
                    EntityPlayer var6;
                    if ((var6 = (EntityPlayer)var2.next()) == null) {
                        continue label41;
                    }

                    if (!var6.isEntityAlive()) {
                        var5 = var2;
                    } else if (var6 != mc.thePlayer && !var3.otherPlayers.method_146()) {
                        var5 = var2;
                    } else {
                        var7.add(var6.getEntityId());
                        Vec3 var4 = new Vec3(var6.posX, var6.posY, var6.posZ);
                        if (!var6.onGround) {
                            if ((var4 = var3.map.remove(var6.getEntityId())) != null && method_1273(var6, var4)) {
                                var3.list.add(new JumpCircleParams(var4, System.currentTimeMillis()));
                            }
                            continue label41;
                        }

                        var3.map.put(var6.getEntityId(), var4);
                        var5 = var2;
                    }
                }

                var3.map.keySet().retainAll(var7);
                return;
            }
        }
    }

    private void method_1269() {
        long var1 = System.currentTimeMillis() - this.method_1272();
        this.list.removeIf(var2 -> var2.longValue() < var1);
    }

    @Override
    public void onDisable() {
        this.list.clear();
        this.map.clear();
        this.jumpCircleRenderer.method_1012();
    }

    private JumpCircle method_1270() {
        return new JumpCircle(
            this.mode.getValue(),
            this.color.method_118(),
            this.opacity.method_151() / 100.0F,
            this.radius.method_151(),
            this.method_1272(),
            this.spinSpeed.method_151(),
            this.text.getValue(),
            this.textSize.method_151(),
            this.throughWalls.method_146(),
            this.distortionSettings.method_61(),
            this.distortionSettings.blur.method_151() / 100.0F,
            this.distortionSettings.warp.method_151() / 100.0F
        );
    }

    public JumpCircles() {
        super("JumpCircles", "Draws a circle where a jump started", Category.RENDER);
    }

    @EventHandler
    private void onWorldLoad(WorldLoadEvent var1) {
        this.list.clear();
        this.map.clear();
    }

    public void method_1271(float var1) {
        if (this.isToggled() && mc.theWorld != null && mc.thePlayer != null) {
            this.method_1269();
            this.jumpCircleRenderer.method_1000(var1, this.list, this.method_1270());
        }
    }

    private long method_1272() {
        return (long)(this.duration.getValue() * 1000.0);
    }

    private static boolean method_1273(EntityPlayer var0, Vec3 var1) {
        return var0.motionY > 0.0 || var0.posY > var1.yCoord + 0.01;
    }

    public static enum CircleMode implements NamedEnum {
        RING("Ring"),
        TEXT("Text");

        private final String string;

        private CircleMode(String var3) {
            this.string = var3;
        }

        @Override
        public String getDisplayName() {
            return this.string;
        }
    }

    public final class DistortionSettings extends SettingGroup {
        public final NumberSetting blur;
        public final NumberSetting warp;

        private DistortionSettings() {
            super("Distortion", true);
            String var10002 = "Distortion";
            boolean var10003 = true;
            this.blur = new NumberSetting("Blur", 60.0, 0.0, 100.0, 1.0).method_147("%").describe("How far out of focus the ground inside the ring goes");
            this.warp = new NumberSetting("Warp", 45.0, 0.0, 100.0, 1.0).method_147("%").describe("How hard the view bends outwards at the rim of the lens");
        }
    }
}
