package client.onyx.util.player;

import client.onyx.Onyx;
import client.onyx.event.impl.GenericEvent;
import client.onyx.event.impl.PostTickEvent;
import client.onyx.event.impl.WorldLoadEvent;
import client.onyx.util.IMinecraft;
import meteordevelopment.orbit.EventHandler;

public class HotbarManager implements IMinecraft {
    public static final HotbarManager INSTANCE = new HotbarManager();
    private int intValue;
    private HotbarManager.SlotRequest slotRequest;

    private int method_1970() {
        return mc.thePlayer == null ? 0 : mc.thePlayer.inventory.currentItem;
    }

    public int method_1971() {
        return this.slotRequest != null ? this.slotRequest.intValue : this.method_1970();
    }

    public boolean method_1972(Object var1) {
        return this.slotRequest != null && this.slotRequest.object == var1;
    }

    public int method_1973() {
        return this.slotRequest != null ? this.slotRequest.intValue3 : this.method_1970();
    }

    public boolean method_1974(Object var1, int var2, int var3) {
        if (!method_1977(var2)) {
            throw new IllegalArgumentException(new StringBuilder().insert(0, "Invalid hotbar slot: ").append(var2).toString());
        } else {
            GenericEvent var4 = new GenericEvent(var1, var2);
            Onyx.EVENT_BUS.post(var4);
            if (var4.isCancelled()) {
                return false;
            } else {
                this.slotRequest = new HotbarManager.SlotRequest(var2, var1, var3, this.method_1971());
                this.intValue = 0;
                return true;
            }
        }
    }

    private HotbarManager() {
    }

    public boolean method_1975() {
        return this.slotRequest != null;
    }

    public void method_1976(Object var1) {
        if (this.slotRequest != null && this.slotRequest.object == var1) {
            this.slotRequest = null;
        }
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent var1) {
        this.slotRequest = null;
        this.intValue = 0;
    }

    @EventHandler(
        priority = 1001
    )
    public void onPostTick(PostTickEvent var1) {
        if (this.slotRequest != null) {
            if (this.intValue >= this.slotRequest.intValue2) {
                this.slotRequest = null;
            } else {
                this.intValue++;
            }
        }
    }

    public static boolean method_1977(int var0) {
        return var0 >= 0 && var0 < 9;
    }

    private record SlotRequest(int intValue3, Object object, int intValue2, int intValue) {
    }
}
