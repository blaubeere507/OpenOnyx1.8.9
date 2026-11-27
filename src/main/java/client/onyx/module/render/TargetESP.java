package client.onyx.module.render;

import client.onyx.Onyx;
import client.onyx.event.impl.AttackEvent;
import client.onyx.event.impl.PostPlayerUpdateEvent;
import client.onyx.event.impl.WorldLoadEvent;
import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.render.world.TargetMarkAnimator;
import client.onyx.render.world.TargetMarkFrame;
import client.onyx.render.world.TargetMarkParams;
import client.onyx.render.world.TargetMarkRenderer;
import client.onyx.render.world.TargetMarkState;
import client.onyx.render.world.TargetRingRenderer;
import client.onyx.setting.NamedEnum;
import client.onyx.setting.SettingGroup;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.ColorSetting;
import client.onyx.setting.impl.EnumSetting;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.ui.theme.Theme;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.EntityLivingBase;

public final class TargetESP extends Module {
    private static final float FLOAT_100_01 = 100.0F;
    public EnumSetting<TargetESP.Style> style = new EnumSetting<>("Style", TargetESP.Style.CRYSTALS);
    public ColorSetting color = new ColorSetting("Color", -8857601).method_116().describe("Colour of the mark on the target");
    public ColorSetting glowColor = new ColorSetting("Glow color", -8857601)
        .visibleWhen(this.style, var0 -> var0 == TargetESP.Style.CRYSTALS)
        .describe("Colour of the halo around each shard");
    public NumberSetting glow = new NumberSetting("Glow", 75.0, 0.0, 300.0, 5.0)
        .method_147("%")
        .visibleWhen(this.style, var0 -> var0 == TargetESP.Style.CRYSTALS)
        .describe("How much light the halos throw");
    public NumberSetting glowSize = new NumberSetting("Glow size", 70.0, 25.0, 300.0, 5.0)
        .method_147("%")
        .visibleWhen(this.style, var0 -> var0 == TargetESP.Style.CRYSTALS)
        .describe("Radius of each halo");
    public NumberSetting crystalOpacity = new NumberSetting("Crystal opacity", 100.0, 0.0, 100.0, 5.0)
        .method_147("%")
        .visibleWhen(this.style, var0 -> var0 == TargetESP.Style.CRYSTALS)
        .describe("How solid the orbiting shards are");
    public TargetESP.RingSettings ringSettings = new TargetESP.RingSettings();
    public TargetESP.StrikeSettings strikeSettings = new TargetESP.StrikeSettings();
    private final TargetMarkAnimator<EntityLivingBase> targetMarkAnimator = new TargetMarkAnimator<>();
    private final TargetMarkState targetMarkState = new TargetMarkState();
    private final TargetRingRenderer targetRingRenderer = new TargetRingRenderer();
    private final TargetMarkRenderer targetMarkRenderer = new TargetMarkRenderer();
    private long longValue;

    @EventHandler
    private void onWorldLoad(WorldLoadEvent var1) {
        this.method_1222();
    }

    private TargetMarkFrame method_1218() {
        return new TargetMarkFrame(
            this.color.method_118(),
            this.ringSettings.size.method_151(),
            this.ringSettings.speed.method_151(),
            this.ringSettings.start.getValue().getFloatValue(),
            this.ringSettings.glow.method_151() / 100.0F,
            this.ringSettings.gradient.method_146(),
            this.ringSettings.trail.method_146(),
            this.ringSettings.trailLength.method_151(),
            this.ringSettings.trailOpacity.method_151() / 100.0F,
            this.ringSettings.particles.method_146(),
            this.ringSettings.particleHeight.method_151(),
            this.ringSettings.particleLife.method_151(),
            this.ringSettings.redOnImpact.method_146(),
            this.ringSettings.impactFadeIn.method_151(),
            this.ringSettings.impactFadeOut.method_151(),
            this.ringSettings.impactIntensity.method_151()
        );
    }

    private EntityLivingBase method_1219() {
        if (mc.theWorld != null && mc.thePlayer != null) {
            EntityLivingBase var2;
            return (var2 = Onyx.moduleManager.killAura.getEntityLivingBase2()) != null && var2.isEntityAlive() && var2.worldObj == mc.theWorld ? var2 : null;
        } else {
            return null;
        }
    }

    public void method_1220(float var1) {
        if (this.isToggled()) {
            long var2 = System.nanoTime();
            float var6;
            if (this.longValue == 0L) {
                var6 = 0.0F;
            } else {
                var6 = Math.min((float)(var2 - this.longValue) / 1000000.0F, 100.0F);
            }

            this.longValue = var2;
            TargetESP var8;
            if (!this.method_1221()) {
                this.targetMarkState.method_759();
                var8 = this;
            } else {
                this.targetMarkState.method_761(var6);
                var8 = this;
            }

            EntityLivingBase var10 = var8.method_1219();
            this.targetMarkAnimator.method_765(var10, var6);
            EntityLivingBase var9;
            EntityLivingBase var10000 = var9 = this.targetMarkAnimator.getObject2();
            float var5 = this.targetMarkAnimator.getFloatValue();
            if (var10000 != null && !(var5 <= 0.0F) && var9.worldObj == mc.theWorld) {
                if (this.style.method_168(TargetESP.Style.RING)) {
                    this.targetMarkRenderer.method_753(var9, var5, var1, this.method_1218());
                } else {
                    this.targetRingRenderer.method_770(var9, var5, System.currentTimeMillis(), var1, this.method_1223(), this.targetMarkState);
                }
            }
        }
    }

    @Override
    public void onEnable() {
        this.method_1222();
    }

    @EventHandler
    private void onAttack(AttackEvent var1) {
        if (this.method_1221() && var1.getEntityPlayer() == mc.thePlayer) {
            if (var1.getEntity() == this.targetMarkAnimator.getObject2()) {
                this.targetMarkState.method_762(this.strikeSettings.shardsPerHit.method_149(), this.strikeSettings.speed.method_151() / 100.0F);
            }
        }
    }

    @EventHandler
    private void onPostPlayerUpdate(PostPlayerUpdateEvent var1) {
        if (this.style.method_168(TargetESP.Style.RING)) {
            TargetMarkRenderer var3;
            EntityLivingBase var4;
            TargetESP var5;
            label18: {
                EntityLivingBase var6 = this.targetMarkAnimator.getObject2();
                TargetMarkRenderer var2;
                if (var6 == null) {
                    var2 = this.targetMarkRenderer;
                } else {
                    if (var6.worldObj == mc.theWorld) {
                        var5 = this;
                        var4 = var6;
                        var3 = this.targetMarkRenderer;
                        break label18;
                    }

                    var2 = this.targetMarkRenderer;
                }

                var5 = this;
                var4 = null;
                var3 = var2;
            }

            var3.method_750(var4, var5.targetMarkAnimator.getFloatValue(), this.method_1218());
        }
    }

    private boolean method_1221() {
        return this.style.method_168(TargetESP.Style.CRYSTALS) && this.strikeSettings.method_61();
    }

    @Override
    public void onDisable() {
        this.method_1222();
    }

    private void method_1222() {
        this.targetMarkAnimator.method_766();
        this.targetMarkState.method_759();
        this.targetMarkRenderer.method_749();
        this.longValue = 0L;
    }

    private TargetMarkParams method_1223() {
        return new TargetMarkParams(
            this.glow.method_151() / 100.0F,
            this.glowSize.method_151() / 100.0F,
            this.crystalOpacity.method_151() / 100.0F,
            this.strikeSettings.reach.method_151() / 100.0F,
            this.color.method_118(),
            this.glowColor.method_118(),
            this.strikeSettings.flash.method_118()
        );
    }

    public TargetESP() {
        super("TargetESP", "Marks the KillAura target", Category.RENDER);
        this.ringSettings.visibleWhen(() -> this.style.method_168(TargetESP.Style.RING));
        this.strikeSettings.visibleWhen(() -> this.style.method_168(TargetESP.Style.CRYSTALS));
    }

    public static final class RingSettings extends SettingGroup {
        public NumberSetting size = new NumberSetting("Size", 1.0, 0.5, 2.0, 0.05).describe("How wide the hoop stands off the target");
        public NumberSetting speed = new NumberSetting("Speed", 4.0, 0.5, 12.0, 0.5).describe("How fast it travels up and back down");
        public EnumSetting<TargetESP.StartHeight> start = new EnumSetting<>("Start", TargetESP.StartHeight.FEET)
            .describe("Where on the target the hoop sets off from");
        public NumberSetting glow = new NumberSetting("Glow", 100.0, 0.0, 300.0, 10.0).method_147("%").describe("How much light comes off the line");
        public BooleanSetting gradient = new BooleanSetting("Gradient", false).describe("Walk the hue around the hoop instead of holding the picked colour");
        public BooleanSetting trail = new BooleanSetting("Trail", true).describe("Drag a curtain of light behind the hoop");
        public NumberSetting trailLength = new NumberSetting("Trail length", 1.0, 0.25, 2.0, 0.05)
            .describe("How far back the curtain reaches")
            .visibleWhen(this.trail);
        public NumberSetting trailOpacity = new NumberSetting("Trail opacity", 100.0, 10.0, 300.0, 10.0)
            .method_147("%")
            .describe("How strongly the curtain is drawn")
            .visibleWhen(this.trail);
        public BooleanSetting particles = new BooleanSetting("Particles", true).describe("Shed glowing motes as the hoop goes");
        public NumberSetting particleHeight = new NumberSetting("Particle height", 1.0, 0.1, 1.0, 0.05)
            .describe("How much of the target's height the motes are spread over")
            .visibleWhen(this.particles);
        public NumberSetting particleLife = new NumberSetting("Particle life", 1.0, 0.3, 3.0, 0.1)
            .describe("How long a mote lasts before it is gone")
            .visibleWhen(this.particles);
        public BooleanSetting redOnImpact = new BooleanSetting("Red on impact", true).describe("Flush the ring red whenever the target is hit");
        public NumberSetting impactFadeIn = new NumberSetting("Impact fade in", 0.3, 0.05, 1.0, 0.01)
            .describe("How fast the flush comes on")
            .visibleWhen(this.redOnImpact);
        public NumberSetting impactFadeOut = new NumberSetting("Impact fade out", 0.08, 0.01, 0.5, 0.01)
            .describe("How fast it drains away again")
            .visibleWhen(this.redOnImpact);
        public NumberSetting impactIntensity = new NumberSetting("Impact intensity", 1.0, 0.1, 1.0, 0.05)
            .describe("How red it gets at its strongest")
            .visibleWhen(this.redOnImpact);

        private RingSettings() {
            super("Ring");
        }
    }

    public static enum StartHeight implements NamedEnum {
        FEET("Feet", -90.0F),
        MIDDLE("Middle", 0.0F),
        HEAD("Head", 90.0F);

        private final String string;
        private final float floatValue;

        @Override
        public String getDisplayName() {
            return this.string;
        }

        private StartHeight(String var3, float var4) {
            this.string = var3;
            this.floatValue = var4;
        }

        public float getFloatValue() {
            return this.floatValue;
        }
    }

    public static final class StrikeSettings extends SettingGroup {
        public NumberSetting shardsPerHit = new NumberSetting("Shards per hit", 1.0, 1.0, 6.0, 1.0).describe("How many shards lunge in on every hit");
        public NumberSetting reach = new NumberSetting("Reach", 55.0, 10.0, 90.0, 5.0).method_147("%").describe("How far towards the target a shard drives in");
        public NumberSetting speed = new NumberSetting("Speed", 100.0, 40.0, 250.0, 10.0)
            .method_147("%")
            .describe("Pace of the lunge and of the gap between shards");
        public ColorSetting flash = new ColorSetting("Flash", -1)
            .method_116()
            .method_122(() -> Theme.getPalette().intValue17())
            .describe("Colour a shard flashes to as it lands");

        private StrikeSettings() {
            super("Strike", true);
        }
    }

    public static enum Style implements NamedEnum {
        CRYSTALS("Crystals"),
        RING("Ring");

        private final String string;

        private Style(String var3) {
            this.string = var3;
        }

        @Override
        public String getDisplayName() {
            return this.string;
        }
    }
}
