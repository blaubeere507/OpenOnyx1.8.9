package client.onyx.module.combat.bedwars;

import client.onyx.event.impl.PacketDirection;
import client.onyx.event.impl.PacketEvent;
import client.onyx.event.impl.PostTickEvent;
import client.onyx.event.impl.WorldLoadEvent;
import client.onyx.util.IMinecraft;
import client.onyx.util.Timer;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.BlockBed;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;

public final class BedWarsObjectives implements IMinecraft {
    public static final BedWarsObjectives INSTANCE = new BedWarsObjectives();
    private static final long LONG_30001 = 3000L;
    private static final int INT_151 = 15;
    private static final int INT_351 = 35;
    private final Set<BlockPos> set = new HashSet<>();
    private final Timer timer = new Timer();
    private BlockPos blockPos;
    private boolean flag;
    private boolean flag2;

    public boolean method_1545(BlockPos var1) {
        return this.set.contains(var1);
    }

    @EventHandler
    public void onPostTick(PostTickEvent var1) {
        if (this.flag && this.timer.method_2019(3000L)) {
            this.flag = false;
            this.method_1547();
        }
    }

    public BlockPos method_1546() {
        if (mc.thePlayer != null && mc.theWorld != null && !this.set.isEmpty()) {
            BlockPos var1 = new BlockPos(mc.thePlayer);
            BlockPos var6 = null;
            double var3 = Double.MAX_VALUE;
            Iterator var10 = this.set.iterator();

            label25:
            while (true) {
                for (Iterator var9 = var10; var9.hasNext(); var9 = var10) {
                    BlockPos var2 = (BlockPos)var10.next();
                    if (mc.theWorld.getBlockState(var2).getBlock() instanceof BlockBed) {
                        double var7;
                        if ((var7 = var2.distanceSq(var1.getX(), var1.getY(), var1.getZ())) < var3) {
                            var3 = var7;
                            var6 = var2;
                        }
                        continue label25;
                    }
                }

                return var6;
            }
        } else {
            return null;
        }
    }

    private void method_1547() {
        var var4 = this;
        if (this.blockPos != null && mc.theWorld != null) {
            int var1 = -35;

            for (int var7 = -35; var7 <= 35; var7 = ++var1) {
                int var2 = -15;

                for (int var6 = -15; var6 <= 15; var6 = ++var2) {
                    int var3 = -35;

                    for (int var5 = -35; var5 <= 35; var5 = ++var3) {
                        BlockPos var8 = var4.blockPos.add(var1, var2, var3);
                        if (mc.theWorld.getBlockState(var8).getBlock() instanceof BlockBed) {
                            var4.set.add(var8);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPacket(PacketEvent var1) {
        if (var1.getPacketDirection() == PacketDirection.INCOMING) {
            Packet var4;
            if ((var4 = var1.getPacket()) instanceof S02PacketChat) {
                String var2;
                if ((var2 = ((S02PacketChat)var4).getChatComponent().getUnformattedText()).contains("Protect your bed and destroy the enemy bed")
                    || var2.contains("Destroy the enemy bed and then eliminate them")) {
                    this.flag2 = true;
                    return;
                }
            } else if (var4 instanceof S08PacketPlayerPosLook var5 && this.flag2) {
                this.flag2 = false;
                this.blockPos = new BlockPos(var5.getX(), var5.getY(), var5.getZ());
                this.flag = true;
                this.timer.method_2017();
            }
        }
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent var1) {
        this.method_1548();
    }

    private void method_1548() {
        this.set.clear();
        this.flag2 = false;
        this.flag = false;
        this.blockPos = null;
    }

    private BedWarsObjectives() {
    }
}
