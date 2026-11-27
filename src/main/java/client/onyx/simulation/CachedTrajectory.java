package client.onyx.simulation;

import net.minecraft.util.Vec3;

public class CachedTrajectory implements TrajectorySource {
    private final PlayerSimulation playerSimulation;
    private int intValue;

    public CachedTrajectory(PlayerSimulation var1) {
        this.playerSimulation = var1;
    }

    public int getIntValue() {
        return this.intValue;
    }

    @Override
    public Vec3 getVec31() {
        return this.playerSimulation.method_294(this.intValue).vec31();
    }

    @Override
    public void method_304() {
        this.intValue++;
    }

    public PlayerSimulation getPlayerSimulation() {
        return this.playerSimulation;
    }
}
