package client.onyx.simulation;

import client.onyx.event.impl.MovementInputEvent;
import client.onyx.event.impl.PostTickEvent;
import client.onyx.util.IMinecraft;
import client.onyx.util.player.MovementInputState;
import client.onyx.util.player.MovementPredictor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.player.EntityPlayer;

public final class SimulationManager implements IMinecraft {
    public static final SimulationManager INSTANCE = new SimulationManager();
    private final Map<EntityPlayer, PlayerSimulation> map = new ConcurrentHashMap<>();
    private PlayerSimulation playerSimulation;

    @EventHandler(
        priority = -10
    )
    private void onMovementInput(MovementInputEvent var1) {
        this.method_344(var1.getMovementInputState(), true);
    }

    public PlayerSimulation method_343() {
        PlayerSimulation var2;
        if ((var2 = this.playerSimulation) != null) {
            return var2;
        } else {
            SimulatedPlayer var3 = SimulatedPlayer.method_307(SimulatedPlayerInput.method_301(MovementPredictor.method_1885(mc.thePlayer)));
            PlayerSimulation var4 = new PlayerSimulation(var3);
            return this.playerSimulation = var4;
        }
    }

    @EventHandler(
        priority = 1000
    )
    private void onPostTick(PostTickEvent var1) {
        this.map.clear();
    }

    private void method_344(MovementInputState var1, boolean var2) {
        if (mc.thePlayer != null) {
            if (!var2
                || this.playerSimulation == null
                || !this.playerSimulation.getSimulatedPlayer().getSimulatedPlayerInput().getMovementInputState().equals(var1)) {
                SimulatedPlayer var4 = SimulatedPlayer.method_307(SimulatedPlayerInput.method_301(var1));
                this.playerSimulation = new PlayerSimulation(var4);
            }
        }
    }

    public PlayerSimulation method_345(EntityPlayer var1) {
        return this.map.computeIfAbsent(var1, var0 -> new PlayerSimulation(SimulatedPlayer.method_331(var0, SimulatedPlayerInput.method_302(var0))));
    }

    @EventHandler(
        priority = 500
    )
    private void onMovementInput2(MovementInputEvent var1) {
        this.playerSimulation = null;
        this.method_344(var1.getMovementInputState(), false);
    }

    @EventHandler
    private void onMovementInput3(MovementInputEvent var1) {
        this.method_344(var1.getMovementInputState(), true);
    }

    private SimulationManager() {
    }
}
