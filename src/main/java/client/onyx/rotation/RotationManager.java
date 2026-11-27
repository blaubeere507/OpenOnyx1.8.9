package client.onyx.rotation;

import client.onyx.Onyx;
import client.onyx.event.impl.PacketEvent;
import client.onyx.event.impl.PostTickEvent;
import client.onyx.event.impl.Render2DEvent;
import client.onyx.event.impl.StrafeEvent;
import client.onyx.util.ConditionalAction;
import client.onyx.util.IMinecraft;
import client.onyx.util.Importance;
import client.onyx.util.TaskScheduler;
import client.onyx.util.Toggleable;
import client.onyx.util.player.ContainerTracker;
import client.onyx.util.player.MovementPredictor;
import java.util.Objects;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class RotationManager implements IMinecraft {
    public static final RotationManager INSTANCE = new RotationManager();
    private final TaskScheduler<RotationRequest> taskScheduler = new TaskScheduler<>();
    private Rotation rotation = Rotation.INSTANCE;
    private Rotation rotation5 = Rotation.INSTANCE;
    private Rotation rotation2;
    private Rotation rotation3;
    private Rotation rotation4;
    private RotationRequest rotationRequest;

    public void method_1018(Rotation var1) {
        RotationManager var3;
        if (var1 != null) {
            if (this.rotation3 == null) {
                RotationManager var6;
                Rotation var7;
                if (mc.thePlayer == null) {
                    var7 = Rotation.INSTANCE;
                    var6 = this;
                } else {
                    var7 = MovementPredictor.method_1910(mc.thePlayer);
                    var6 = this;
                }

                var6.rotation2 = var7;
                var3 = this;
            } else {
                this.rotation2 = this.rotation3;
                var3 = this;
            }
        } else {
            this.rotation2 = null;
            var3 = this;
        }

        var3.rotation3 = var1;
    }

    public Rotation getRotation() {
        return this.rotation;
    }

    public boolean method_1019(RotationRequest var1) {
        return this.method_1026() && (!var1.isFlag() || !ContainerTracker.INSTANCE.method_1938() && !(mc.currentScreen instanceof GuiContainer));
    }

    public RotationRequest getRotationRequest() {
        return this.rotationRequest;
    }

    public RotationRequest getActiveRequest() {
        RotationRequest var2;
        return (var2 = this.method_1021()) != null ? var2 : this.rotationRequest;
    }

    private boolean method_1020() {
        return mc.thePlayer != null && mc.theWorld != null;
    }

    private RotationRequest method_1021() {
        return this.taskScheduler.method_1928();
    }

    public Rotation getRotation2() {
        return this.rotation;
    }

    public Rotation getServerRotation() {
        return this.rotation3;
    }

    @EventHandler(
        priority = -10
    )
    private void onStrafe(StrafeEvent var1) {
        if (this.method_1020()) {
            if (!MoveFix.isFlag4()) {
                RotationRequest var3;
                if ((var3 = this.getActiveRequest()) == null || var3.getMovementCorrection() != MovementCorrection.OFF) {
                    if (this.rotation3 != null) {
                        var1.method_2615(MovementPredictor.method_1900(var1.getVec32(), var1.getFloatValue(), this.rotation3.getYaw()));
                    }
                }
            }
        }
    }

    public void method_1022(float var1) {
        if (this.method_1020()) {
            RotationRequest var3;
            if ((var3 = this.getActiveRequest()) != null) {
                if (this.method_1019(var3) && var3.getMovementCorrection() == MovementCorrection.CHANGE_LOOK) {
                    if (this.rotation4 != null && this.rotation3 != null) {
                        RotationMath.method_1040(mc.thePlayer, this.rotation4.method_1053(this.rotation3, var1));
                    }
                }
            }
        }
    }

    public void method_1023(double var1, double var3) {
        if (this.method_1020()) {
            RotationRequest var6;
            if ((var6 = this.getActiveRequest()) != null) {
                if (this.method_1019(var6) && var6.getMovementCorrection() == MovementCorrection.CHANGE_LOOK) {
                    if (this.rotation4 != null) {
                        this.rotation4 = RotationMath.method_1038(this.rotation4, var1, var3);
                    }

                    if (this.rotation3 != null) {
                        this.method_1018(RotationMath.method_1038(this.rotation3, var1, var3));
                    }
                }
            }
        }
    }

    public void method_1024() {
        Rotation var1 = MovementPredictor.method_1910(mc.thePlayer);
        this.rotation4 = var1;
        RotationRequest var12;
        if ((var12 = this.getActiveRequest()) != null) {
            RotationManager var11;
            label57: {
                RotationRequest var3 = this.method_1021();
                if (this.method_1019(var12)) {
                    Rotation var5;
                    if (this.rotation3 != null) {
                        var5 = this.rotation3;
                    } else {
                        var5 = var1;
                    }

                    RotationRequest var8;
                    Rotation var9;
                    boolean var10;
                    if (var3 != null) {
                        var10 = false;
                        var9 = var5;
                        var8 = var12;
                    } else {
                        var10 = true;
                        var9 = var5;
                        var8 = var12;
                    }

                    Rotation var4 = var8.method_1036(var9, var10).method_1050();
                    float var13 = var4.method_1047(var1);
                    if (var3 == null
                        && (var12.getMovementCorrection() == MovementCorrection.CHANGE_LOOK || var12.getList().isEmpty() || var13 <= var12.getFloatValue())) {
                        if (this.rotation3 != null) {
                            mc.thePlayer.rotationYaw = RotationMath.method_1044(mc.thePlayer, this.rotation3);
                            mc.thePlayer.renderArmYaw = mc.thePlayer.rotationYaw;
                            mc.thePlayer.prevRenderArmYaw = mc.thePlayer.rotationYaw;
                        }

                        this.method_1018(null);
                        this.rotationRequest = null;
                        var11 = this;
                        break label57;
                    }

                    this.method_1018(var4);
                    this.rotationRequest = var12;
                    if (var3 != null && var3.getConditionalAction() != null) {
                        var3.getConditionalAction().method_1983();
                    }
                }

                var11 = this;
            }

            var11.taskScheduler.method_1929();
        }
    }

    @EventHandler(
        priority = -1000
    )
    private void onPacket(PacketEvent var1) {
        Packet var10000 = var1.getPacket();
        Objects.requireNonNull(var10000);
        Object var6 = var10000;
        Rotation var2;
        PacketEvent var5;
        switch (var6) {
            case C03PacketPlayer var9:
                C03PacketPlayer var8;
                if (!(var8 = (C03PacketPlayer)var6).getRotating()) {
                    return;
                }

                var2 = new Rotation(var8.getYaw(), var8.getPitch(), true);
                var5 = var1;
                break;
            case S08PacketPlayerPosLook var7:
                var2 = new Rotation(var7.getYaw(), var7.getPitch(), true);
                var5 = var1;
                break;
            default:
                return;
        }

        if (!var5.isCancelled()) {
            MoveFix.method_2146((this.rotation = var2).getYaw(), var2.getPitch());
        }

        this.rotation5 = var2;
    }

    private RotationManager() {
    }

    public void method_1025(Rotation var1, boolean var2, RotationSettings var3, Importance var4, Toggleable var5, ConditionalAction var6) {
        this.method_1028(var3.method_1029(var1, null, var2, var6), var4, var5);
    }

    private boolean method_1026() {
        return true;
    }

    public Rotation getRotation4() {
        return this.rotation4;
    }

    public Rotation getRotation22() {
        return this.rotation2;
    }

    public boolean method_1027() {
        if (mc.thePlayer == null) {
            return false;
        } else {
            return this.rotation3 != null
                ? this.rotation3.equals(this.rotation2)
                : MovementPredictor.method_1910(mc.thePlayer).equals(MovementPredictor.method_1887(mc.thePlayer));
        }
    }

    @EventHandler(
        priority = 1000
    )
    private void onPostTick(PostTickEvent var1) {
        if (this.method_1020()) {
            Onyx.EVENT_BUS.post(new Render2DEvent());
            this.method_1024();
        }
    }

    public void method_1028(RotationRequest var1, Importance var2, Toggleable var3) {
        if (this.method_1026()) {
            TaskScheduler var6 = this.taskScheduler;
            TaskScheduler.Task var10001;
            int var10003;
            Importance var10004;
            if (var1.getMovementCorrection() == MovementCorrection.CHANGE_LOOK) {
                var10003 = 1;
                var10004 = var2;
            } else {
                var10003 = var1.getIntValue();
                var10004 = var2;
            }

            var10001 = new TaskScheduler.Task(var10003, var10004.getIntValue(), var3, var1);
            var6.method_1930(var10001);
        }
    }
}
