package client.onyx.event.impl;

import client.onyx.event.Event;
import net.minecraft.client.multiplayer.WorldClient;

public class WorldLoadEvent extends Event {
    private final WorldClient worldClient;

    public WorldLoadEvent(WorldClient var1) {
        this.worldClient = var1;
    }

    public WorldClient getWorldClient() {
        return this.worldClient;
    }
}
