package client.onyx.util;

import java.util.function.BooleanSupplier;

public class ConditionalAction {
    private final BooleanSupplier booleanSupplier;
    private final Runnable runnable;
    private boolean flag;

    public ConditionalAction(BooleanSupplier var1, Runnable var2) {
        this.booleanSupplier = var1;
        this.runnable = var2;
    }

    public void method_1983() {
        if (!this.flag && this.booleanSupplier.getAsBoolean()) {
            this.runnable.run();
            this.flag = true;
        }
    }
}
