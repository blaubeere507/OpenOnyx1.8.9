package client.onyx.module.player.scaffold.feature;

import client.onyx.event.impl.MovementInputEvent;
import client.onyx.event.impl.PacketDirection;
import client.onyx.event.impl.PacketEvent;
import client.onyx.event.impl.PostJumpEvent;
import client.onyx.event.impl.PostTickEvent;
import client.onyx.event.impl.SprintEvent;
import client.onyx.event.impl.WorldLoadEvent;
import client.onyx.module.movement.NoSlow;
import client.onyx.module.player.scaffold.Scaffold;
import client.onyx.module.player.scaffold.ScaffoldPlacement;
import client.onyx.rotation.MovementCorrection;
import client.onyx.rotation.Rotation;
import client.onyx.rotation.RotationManager;
import client.onyx.rotation.RotationMath;
import client.onyx.rotation.RotationRequest;
import client.onyx.setting.NamedEnum;
import client.onyx.setting.Setting;
import client.onyx.setting.SettingSection;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.EnumSetting;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.util.IMinecraft;
import client.onyx.util.Timer;
import client.onyx.util.player.MovementPredictor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class SprintControlSettings extends SettingSection implements IMinecraft {
    public static final SprintControlSettings sprintControlSettings = new SprintControlSettings();
    private static final long LONG_3291 = 329L;
    public EnumSetting<SprintControlSettings.SprintMode> mode = new EnumSetting<>(
        "Mode",
        SprintControlSettings.SprintMode.DO_NOT_CHANGE,
        SprintControlSettings.SprintMode.DO_NOT_CHANGE,
        SprintControlSettings.SprintMode.FORCE_SPRINT,
        SprintControlSettings.SprintMode.FORCE_NO_SPRINT,
        SprintControlSettings.SprintMode.KEEP_Y
    );
    public NumberSetting jumpForwardChance = new NumberSetting("Jump forward chance", 100.0, 0.0, 100.0, 1.0)
        .method_147("%")
        .describe("Chance to redirect the sprint jump impulse along the movement direction")
        .visibleWhen(() -> this.mode.method_168(SprintControlSettings.SprintMode.KEEP_Y));
    public NumberSetting maxFlick = new NumberSetting("Max flick", 75.0, 0.0, 180.0, 1.0)
        .method_147("°")
        .describe("Skips the jump-forward snap when it would flick the sent yaw further than this")
        .visibleWhen(() -> this.mode.method_168(SprintControlSettings.SprintMode.KEEP_Y));
    public BooleanSetting blinkJump = new BooleanSetting("Blink jump", true)
        .describe("Holds outgoing packets from the jump until the next placement, so the rotation flip and the ascent arrive as one lag burst")
        .visibleWhen(Setting.booleanSupplier);
    private final transient ScaffoldPlacement scaffoldPlacement = new ScaffoldPlacement();
    private final transient List<Packet> list2 = new CopyOnWriteArrayList<>();
    private final transient Timer timer = new Timer(System.currentTimeMillis());
    private transient boolean flag2;
    private transient boolean flag22;
    private transient boolean flag3;

    @EventHandler(
        priority = -10
    )
    private void onSprint(SprintEvent var1) {
        switch ((SprintControlSettings.SprintMode)this.mode.getValue()) {
            case DO_NOT_CHANGE:
            default:
                break;
            case FORCE_SPRINT:
                if (var1.getMovementInputState().method_1919()) {
                    var1.method_2617(true);
                    return;
                }
                break;
            case FORCE_NO_SPRINT:
                var1.method_2617(false);
                return;
            case NO_SPRINT_ON_PLACE:
                if (this.flag22 && !mc.thePlayer.willJumpThisTick()) {
                    var1.method_2617(false);
                    return;
                }
                break;
            case NO_SPRINT_ON_GROUND:
                SprintEvent var3;
                boolean var4;
                if (mc.thePlayer.onGround) {
                    var4 = false;
                    var3 = var1;
                } else {
                    var4 = true;
                    var3 = var1;
                }

                var3.method_2617(var4);
                return;
            case KEEP_Y:
                if (!var1.getMovementInputState().method_1919()) {
                    return;
                }

                if (this.flag22 && !mc.thePlayer.willJumpThisTick()) {
                    var1.method_2617(false);
                    return;
                }

                if (mc.thePlayer.movementInput.moveForward >= NoSlow.method_1529()) {
                    var1.method_2617(true);
                    return;
                }
        }
    }

    // $VF: Could not inline inconsistent finally blocks
    // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
    private void method_1775() {
        this.flag3 = false;
        if (!this.list2.isEmpty()) {
            ArrayList var4 = new ArrayList<>(this.list2);
            this.list2.clear();
            if (mc.getNetHandler() != null) {
                this.flag2 = true;

                try {
                    mc.getNetHandler().getNetworkManager().sendGrouped(var4);
                } catch (Throwable var3) {
                    this.flag2 = false;
                    throw var3;
                }

                this.flag2 = false;
            }
        }
    }

    @Override
    public void method_64() {
        this.method_1775();
    }

    public boolean method_1776() {
        return this.isEnabled()
            && (this.mode.method_168(SprintControlSettings.SprintMode.FORCE_SPRINT) || this.mode.method_168(SprintControlSettings.SprintMode.KEEP_Y));
    }

    @Override
    public void method_62() {
        this.flag22 = false;
        this.method_1777();
    }

    @EventHandler(
        priority = -500
    )
    private void onMovementInput(MovementInputEvent var1) {
        if (this.mode.method_168(SprintControlSettings.SprintMode.KEEP_Y) && mc.thePlayer != null) {
            if (!this.flag22) {
                if (var1.getMovementInputState().method_1919()) {
                    if (mc.thePlayer.willJumpThisTick(var1.isFlag2())) {
                        RotationRequest var2;
                        if ((var2 = RotationManager.INSTANCE.getActiveRequest()) != null && var2.getMovementCorrection() != MovementCorrection.OFF) {
                            if (ThreadLocalRandom.current().nextInt(100) < this.jumpForwardChance.method_149()) {
                                float var4 = MovementPredictor.method_1888(mc.thePlayer, var1.getMovementInputState());
                                Rotation var7;
                                if ((var7 = RotationManager.INSTANCE.getServerRotation()) == null) {
                                    var7 = MovementPredictor.method_1910(mc.thePlayer);
                                }

                                if (!(Math.abs(RotationMath.method_1042(var4, var7.getYaw())) > this.maxFlick.method_151())) {
                                    float var8 = (float)ThreadLocalRandom.current().nextDouble(60.0, 70.0);
                                    float var5 = this.scaffoldPlacement
                                        .method_1714(var4, Scaffold.INSTANCE.scaffoldRotationSettings.rotationVariance.getValue());
                                    RotationManager.INSTANCE.method_1018(new Rotation(var5, var8).method_1050());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private SprintControlSettings() {
        super("Sprint control", false);
    }

    private void method_1777() {
        this.flag3 = false;
        this.flag2 = false;
        this.list2.clear();
    }

    @EventHandler
    private void onPostJump(PostJumpEvent var1) {
        if (this.mode.method_168(SprintControlSettings.SprintMode.KEEP_Y) && this.blinkJump.getValue()) {
            if (mc.thePlayer != null && MovementPredictor.method_1896(mc.thePlayer)) {
                this.method_1775();
                this.flag3 = true;
                this.timer.method_2017();
            }
        }
    }

    public void method_1778() {
        this.flag22 = true;
    }

    @EventHandler
    private void onWorldLoad(WorldLoadEvent var1) {
        this.method_1777();
    }

    @EventHandler(
        priority = 500
    )
    private void onPacket(PacketEvent var1) {
        if (!this.flag2 && !var1.isCancelled()) {
            if (var1.getPacketDirection() == PacketDirection.INCOMING) {
                if (var1.getPacket() instanceof S08PacketPlayerPosLook) {
                    this.method_1775();
                }
            } else if (this.flag3) {
                if (var1.getPacket() instanceof C08PacketPlayerBlockPlacement) {
                    this.method_1775();
                } else {
                    this.list2.add(var1.getPacket());
                    var1.cancel();
                }
            }
        }
    }

    @EventHandler(
        priority = 1000
    )
    private void onPostTick(PostTickEvent var1) {
        if (this.flag22) {
            this.flag22 = false;
        }

        if (this.flag3 && mc.thePlayer != null && (mc.thePlayer.onGround || this.timer.method_2019(329L))) {
            this.method_1775();
        }
    }

    public static enum SprintMode implements NamedEnum {
        DO_NOT_CHANGE("Do not change"),
        FORCE_SPRINT("Force sprint"),
        FORCE_NO_SPRINT("Force no sprint"),
        NO_SPRINT_ON_PLACE("No sprint on place"),
        NO_SPRINT_ON_GROUND("No sprint on ground"),
        KEEP_Y("Keep Y");

        private final String string;

        @Override
        public String getDisplayName() {
            return this.string;
        }

        private SprintMode(String var3) {
            this.string = var3;
        }
    }
}
