package client.onyx.util.world;

public enum HitboxMode {
    OUTLINE(false),
    COLLIDER(true);

    private final boolean flag;

    private HitboxMode(boolean var3) {
        this.flag = var3;
    }

    public boolean isFlag() {
        return this.flag;
    }
}
