package client.onyx.util;

public enum Importance {
    NOT_IMPORTANT(-20),
    NORMAL(0),
    IMPORTANT_FOR_USAGE_1(20),
    IMPORTANT_FOR_USAGE_2(30),
    IMPORTANT_FOR_USAGE_3(35),
    IMPORTANT_FOR_PLAYER_LIFE(40),
    IMPORTANT_FOR_USER_SAFETY(60);

    private final int intValue;

    private Importance(int var3) {
        this.intValue = var3;
    }

    public int getIntValue() {
        return this.intValue;
    }
}
