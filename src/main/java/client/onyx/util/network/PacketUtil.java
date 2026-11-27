package client.onyx.util.network;

import client.onyx.util.IMinecraft;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.Packet;

public class PacketUtil implements IMinecraft {
    private final List<Packet> list = new ArrayList<>();

    public void method_1978() {
        if (!this.list.isEmpty() && mc.getNetHandler() != null) {
            mc.getNetHandler().getNetworkManager().sendGrouped(this.list);
            this.list.clear();
        }
    }

    public PacketUtil method_1979(Packet var1) {
        if (var1 != null) {
            this.list.add(var1);
        }

        return this;
    }
}
