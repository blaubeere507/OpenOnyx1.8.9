package client.onyx.placement;

public class AimPointSelection {
    private final AimPoint aimPoint;
    private final boolean flag;

    public AimPointSelection(AimPoint var1) {
        this(var1, false);
        boolean var10002 = false;
    }

    public AimPointSelection(AimPoint var1, boolean var2) {
        this.aimPoint = var1;
        this.flag = var2;
    }

    public AimPoint getAimPoint() {
        return this.aimPoint;
    }

    public boolean isFlag() {
        return this.flag;
    }
}
