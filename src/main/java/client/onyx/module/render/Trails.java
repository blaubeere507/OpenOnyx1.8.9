package client.onyx.module.render;

import client.onyx.event.impl.PostPlayerUpdateEvent;
import client.onyx.event.impl.WorldLoadEvent;
import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.render.world.TrailParams;
import client.onyx.render.world.TrailPoint;
import client.onyx.render.world.TrailRenderer;
import client.onyx.setting.NamedEnum;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.ColorSetting;
import client.onyx.setting.impl.EnumSetting;
import client.onyx.setting.impl.NumberSetting;
import java.util.ArrayList;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.Vec3;

public final class Trails extends Module {
    private static final double DOUBLE_1_0EN81 = 1.0E-8;
    private static final float FLOAT_0_81 = 0.8F;
    public final EnumSetting<Trails.TrailMode> mode = new EnumSetting<>("Mode", Trails.TrailMode.WALL);
    public final ColorSetting color = new ColorSetting("Color", -10178561).method_116();
    public final NumberSetting length = new NumberSetting("Length", 1.0, 0.25, 5.0, 0.05).method_147(" s").describe("How long the trail stays behind you");
    public final NumberSetting opacity = new NumberSetting("Opacity", 60.0, 5.0, 100.0, 1.0).method_147("%").describe("Overall opacity of the trail");
    public final NumberSetting lineWidth = new NumberSetting("Line width", 1.5, 0.5, 5.0, 0.5)
        .method_147(" px")
        .describe("Thickness of the two lines bounding the wall")
        .visibleWhen(this.mode, var0 -> var0 == Trails.TrailMode.WALL);
    public final NumberSetting pointSize = new NumberSetting("Point size", 4.0, 1.0, 12.0, 0.5)
        .method_147(" px")
        .visibleWhen(this.mode, var0 -> var0 == Trails.TrailMode.POINTS);
    public final BooleanSetting firstPerson = new BooleanSetting("First person", false)
        .describe("Draw the trail in first person too, where it runs through the camera");
    public final BooleanSetting throughWalls = new BooleanSetting("Through walls", false);
    private final TrailRenderer trailRenderer = new TrailRenderer();
    private final List<TrailParams> list = new ArrayList<>();

    @Override
    public void onEnable() {
        this.list.clear();
    }

    private TrailPoint method_1193() {
        return new TrailPoint(
            this.mode.getValue(),
            this.color.method_118(),
            this.opacity.method_151() / 100.0F,
            (long)(this.length.getValue() * 1000.0),
            this.lineWidth.method_151(),
            this.pointSize.method_151(),
            this.throughWalls.method_146()
        );
    }

    private void method_1194() {
        long var1 = System.currentTimeMillis() - (long)(this.length.getValue() * 1000.0);
        this.list.removeIf(var2 -> var2.longValue() < var1);
    }

    public Trails() {
        super("Trails", "Leaves a fading trail behind you", Category.RENDER);
    }

    private List<TrailParams> method_1195(float var1) {
        ArrayList var3;
        (var3 = new ArrayList(this.list.size() + 1)).addAll(this.list);
        var3.add(
            new TrailParams(
                new Vec3(
                    mc.thePlayer.prevPosX + (mc.thePlayer.posX - mc.thePlayer.prevPosX) * var1,
                    mc.thePlayer.prevPosY + (mc.thePlayer.posY - mc.thePlayer.prevPosY) * var1,
                    mc.thePlayer.prevPosZ + (mc.thePlayer.posZ - mc.thePlayer.prevPosZ) * var1
                ),
                System.currentTimeMillis()
            )
        );
        return var3;
    }

    @EventHandler
    private void onPostPlayerUpdate(PostPlayerUpdateEvent var1) {
        if (mc.thePlayer != null && mc.theWorld != null) {
            this.method_1194();
            Vec3 var4 = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
            TrailParams var3;
            if (this.list.isEmpty()) {
                var3 = null;
            } else {
                var3 = this.list.get(this.list.size() - 1);
            }

            if (var3 == null || var3.vec31().squareDistanceTo(var4) > 1.0E-8) {
                this.list.add(new TrailParams(var4, System.currentTimeMillis()));
            }
        }
    }

    @EventHandler
    private void onWorldLoad(WorldLoadEvent var1) {
        this.list.clear();
    }

    private float method_1196() {
        float var10000 = mc.thePlayer.height;
        float var2;
        float var3;
        if (!mc.thePlayer.isSneaking()) {
            float var1 = var10000;
            var3 = 1.0F;
            var2 = var1;
        } else {
            var3 = 0.8F;
            var2 = var10000;
        }

        return var2 * var3;
    }

    @Override
    public void onDisable() {
        this.list.clear();
        this.trailRenderer.method_791();
    }

    public void method_1197(float var1) {
        if (this.isToggled() && mc.theWorld != null && mc.thePlayer != null) {
            if (this.firstPerson.method_146() || mc.gameSettings.thirdPersonView != 0) {
                this.method_1194();
                List var6;
                int var10000 = (var6 = this.method_1195(var1)).size();
                int var4;
                byte var5;
                if (!this.mode.method_168(Trails.TrailMode.WALL)) {
                    int var3 = var10000;
                    var5 = 1;
                    var4 = var3;
                } else {
                    var5 = 2;
                    var4 = var10000;
                }

                if (var4 >= var5) {
                    this.trailRenderer.method_793(var1, var6, this.method_1196(), this.method_1193());
                }
            }
        }
    }

    public static enum TrailMode implements NamedEnum {
        WALL("Wall"),
        POINTS("Points");

        private final String string;

        private TrailMode(String var3) {
            this.string = var3;
        }

        @Override
        public String getDisplayName() {
            return this.string;
        }
    }
}
