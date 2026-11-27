package client.onyx.simulation;

import net.minecraft.util.Vec3;

public interface TrajectorySource {
    Vec3 getVec31();

    void method_304();

    public record StaticTrajectory(Vec3 vec31) implements TrajectorySource {
        @Override
        public Vec3 getVec31() {
            return this.vec31;
        }

        @Override
        public void method_304() {
        }
    }
}
