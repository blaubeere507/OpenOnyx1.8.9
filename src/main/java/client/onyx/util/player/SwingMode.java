package client.onyx.util.player;

import client.onyx.setting.NamedEnum;
import client.onyx.util.IMinecraft;
import net.minecraft.network.play.client.C0APacketAnimation;

public enum SwingMode implements NamedEnum, Runnable, IMinecraft {
    DO_NOT_HIDE("Do not hide", true),
    HIDE_BOTH("Hide for both", false),
    HIDE_CLIENT("Hide for client", true),
    HIDE_SERVER("Hide for server", false);

    private final String string;
    private final boolean flag;

    public boolean isFlag() {
        return this.flag;
    }

    private SwingMode(String var3, boolean var4) {
        this.string = var3;
        this.flag = var4;
    }

    @Override
    public void run() {
        switch (this) {
            case DO_NOT_HIDE:
                mc.thePlayer.swingItem();
                return;
            case HIDE_BOTH:
                return;
            case HIDE_CLIENT:
                mc.getNetHandler().addToSendQueue(new C0APacketAnimation());
                return;
            case HIDE_SERVER:
                mc.thePlayer.swingItemClientOnly();
        }
    }

    @Override
    public String getDisplayName() {
        return this.string;
    }

    public void method_14() {
        this.run();
    }

    public String getString() {
        return this.string;
    }
}
