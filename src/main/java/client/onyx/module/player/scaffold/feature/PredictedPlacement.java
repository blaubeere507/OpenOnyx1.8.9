package client.onyx.module.player.scaffold.feature;

public record PredictedPlacement(boolean flag2, int intValue, boolean flag3, boolean flag) {
    public static final PredictedPlacement INSTANCE = new PredictedPlacement(false, 0, false, false);
}
