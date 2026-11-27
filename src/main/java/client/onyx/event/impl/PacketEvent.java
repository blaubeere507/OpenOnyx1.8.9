package client.onyx.event.impl;

import client.onyx.event.Event;
import net.minecraft.network.Packet;

public class PacketEvent extends Event {
    private final PacketDirection packetDirection;
    private final Packet<?> packet;
    private final boolean flag;

    public Packet<?> getPacket() {
        return this.packet;
    }

    public PacketEvent(PacketDirection var1, Packet<?> var2, boolean var3) {
        this.packetDirection = var1;
        this.packet = var2;
        this.flag = var3;
    }

    public boolean isFlag() {
        return this.flag;
    }

    public PacketDirection getPacketDirection() {
        return this.packetDirection;
    }

    public PacketEvent(PacketDirection var1, Packet<?> var2) {
        this(var1, var2, true);
        boolean var10003 = true;
    }
}
