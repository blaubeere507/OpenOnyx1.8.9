package client.onyx.module.combat;

import client.onyx.event.impl.PacketEvent;
import client.onyx.util.IMinecraft;
import client.onyx.util.Timer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class CombatHelper implements IMinecraft {
    private static final long LONG_3291 = 329L;
    private static final int INT_21 = 2;
    private volatile boolean flag = true;
    private final List<Packet> list = new CopyOnWriteArrayList<>();
    private final Timer timer = new Timer(System.currentTimeMillis());
    private boolean flag2;
    private boolean flag3;
    private boolean flag4;
    private EntityLivingBase entityLivingBase;
    private boolean flag5;
    private boolean flag6;
    private boolean flag7;
    private boolean flag8;
    private boolean flag9;
    private boolean flag10;
    private volatile int intValue;
    private boolean flag11;
    private int intValue2;
    private boolean flag12;

    public void method_1549() {
        if (this.flag10) {
            switch (this.intValue2) {
                case 7:
                    this.method_1570();
                    this.intValue2 = 10;
                    return;
                case 8:
                    this.method_1570();
                    this.method_1567();
                    this.method_1560();
                    this.intValue2 = 9;
                    return;
                case 11:
                    this.method_1567();
                    this.method_1560();
                    this.intValue2 = 0;
                case 9:
                case 10:
            }
        }
    }

    public void method_1550(PacketEvent var1) {
        if (!this.flag2) {
            if (!var1.isCancelled()) {
                Packet var3 = var1.getPacket();
                if (mc.isCallingFromMinecraftThread()) {
                    this.method_1557(var3);
                    if (this.flag3) {
                        this.list.add(var3);
                        var1.cancel();
                    }
                }
            }
        }
    }

    public void method_1551(boolean var1) {
        if (var1) {
            this.intValue = 0;
        }

        if (this.flag4) {
            this.method_1567();
            this.flag3 = false;
            this.flag4 = false;
        }

        if (mc.thePlayer == null) {
            this.method_1556();
        } else if (!method_1561()) {
            this.method_1553();
            this.flag10 = false;
            this.flag7 = false;
            this.entityLivingBase = null;
        } else {
            if (this.flag9) {
                this.method_1566();
            }

            if (!this.flag10 || !this.method_1565() || this.method_1552()) {
                if (this.intValue <= 0) {
                    this.method_1553();
                }

                this.flag10 = false;
                this.flag7 = false;
                this.entityLivingBase = null;
            }
        }
    }

    private boolean method_1552() {
        if (!this.method_1565()) {
            return true;
        } else if (method_1561() && !this.flag11 && !this.flag12 && !this.flag8) {
            this.method_1564(new C07PacketPlayerDigging(Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            this.flag7 = false;
            return true;
        } else {
            return false;
        }
    }

    private void method_1553() {
        if (this.flag5) {
            this.flag5 = false;
            if (mc.thePlayer != null) {
                mc.thePlayer.stopUsingItem();
            }
        }
    }

    public void method_1554() {
        this.intValue = 2;
    }

    public boolean method_1555(EntityLivingBase var1, Runnable var2) {
        if (this.flag9) {
            this.method_1566();
            this.method_1551(true);
            return true;
        } else {
            if (this.flag5 && !mc.thePlayer.isUsingItem()) {
                this.flag5 = false;
            }

            if (this.intValue > 0) {
                this.intValue--;
                if (this.intValue > 0) {
                    this.method_1551(false);
                    return false;
                }
            }

            this.entityLivingBase = var1;
            if (!this.flag10) {
                CombatHelper var4;
                boolean var5;
                label97: {
                    CombatHelper var3;
                    if (this.flag5) {
                        var3 = this;
                    } else {
                        if (mc.thePlayer.isUsingItem()) {
                            var5 = true;
                            var4 = this;
                            break label97;
                        }

                        var3 = this;
                    }

                    var5 = false;
                    var4 = var3;
                }

                var4.flag7 = var5;
                CombatHelper var7;
                byte var8;
                if (!this.flag7) {
                    var8 = 1;
                    var7 = this;
                } else {
                    var8 = 0;
                    var7 = this;
                }

                var7.intValue2 = var8;
                this.flag10 = true;
                this.method_1559();
            }

            CombatHelper var9;
            label111: {
                if (this.intValue2 == 0 || this.intValue2 == 3 || this.intValue2 == 5) {
                    this.method_1560();
                    if (this.intValue2 != 0) {
                        if (this.intValue2 == 3) {
                            this.intValue2 = 6;
                            var9 = this;
                        } else {
                            this.intValue2 = 1;
                            var9 = this;
                        }
                        break label111;
                    }

                    if (this.method_1552()) {
                        this.intValue2 = 3;
                        var9 = this;
                        break label111;
                    }
                } else if (this.intValue2 == 1 || this.intValue2 == 6) {
                    this.intValue2 = 2;
                }

                var9 = this;
            }

            if (var9.intValue2 == 10) {
                this.intValue2 = 11;
            } else if (this.intValue2 == 9) {
                this.intValue2 = 0;
            } else {
                boolean var10;
                if (this.timer.method_2015() >= 329L) {
                    var10 = true;
                } else {
                    var10 = false;
                }

                if (this.intValue2 == 2 || this.intValue2 == 5 || this.intValue2 == 6 || var10) {
                    boolean var13 = this.method_1558(var2);
                    if (this.intValue2 != 5 && this.intValue2 != 6 && (var13 || var10)) {
                        this.intValue2 = 7;
                    }
                }
            }

            return true;
        }
    }

    public void method_1556() {
        this.list.clear();
        this.flag3 = false;
        this.flag4 = false;
        this.flag2 = false;
        this.flag10 = false;
        this.flag7 = false;
        this.intValue2 = 0;
        this.intValue = 0;
        this.entityLivingBase = null;
        this.flag5 = false;
        this.flag = true;
        this.flag9 = false;
        this.method_1566();
        this.timer.method_2017();
    }

    private void method_1557(Packet<?> var1) {
        if (var1 instanceof C02PacketUseEntity) {
            C02PacketUseEntity var2;
            C02PacketUseEntity var6 = var2 = (C02PacketUseEntity)var1;
            this.flag8 = true;
            if (var6.getAction() != net.minecraft.network.play.client.C02PacketUseEntity.Action.ATTACK || var2.getHitVec() != null) {
                this.flag11 = true;
            }
        }

        if (var1 instanceof C07PacketPlayerDigging) {
            this.flag12 = true;
        }

        if (var1 instanceof C08PacketPlayerBlockPlacement) {
            this.flag11 = true;
        }

        if (var1 instanceof C09PacketHeldItemChange var5 && mc.thePlayer != null && var5.getSlotId() != mc.thePlayer.inventory.currentItem) {
            this.flag12 = true;
        }

        if (var1 instanceof C0APacketAnimation) {
            this.flag6 = true;
        }

        if (var1 instanceof C03PacketPlayer) {
            this.flag = true;
            this.flag8 = false;
            this.flag11 = false;
            this.flag12 = false;
            this.flag6 = false;
        }
    }

    private boolean method_1558(Runnable var1) {
        if (!this.method_1565() && !this.flag6 && !this.flag12 && !this.flag11) {
            var1.run();
            return true;
        } else {
            return false;
        }
    }

    private void method_1559() {
        ItemStack var2;
        if ((var2 = mc.thePlayer.getHeldItem()) != null) {
            mc.thePlayer.setItemInUse(var2, var2.getMaxItemUseDuration());
            this.flag5 = true;
        }
    }

    private void method_1560() {
        this.flag3 = true;
        this.flag4 = true;
    }

    public static boolean method_1561() {
        ItemStack var0;
        return mc.thePlayer != null && (var0 = mc.thePlayer.getHeldItem()) != null && var0.getItem() instanceof ItemSword;
    }

    public void method_1562() {
        CombatHelper var2;
        boolean var3;
        if (this.flag) {
            var3 = false;
            var2 = this;
        } else {
            var3 = true;
            var2 = this;
        }

        var2.flag9 = var3;
        this.flag = false;
    }

    public boolean method_1563() {
        return this.flag10 || this.flag5;
    }

    private void method_1564(Packet<?> var1) {
        if (mc.getNetHandler() != null) {
            mc.getNetHandler().addToSendQueue(var1);
        }
    }

    private boolean method_1565() {
        return this.flag10 ? this.flag7 : mc.thePlayer.isUsingItem();
    }

    private void method_1566() {
        this.flag8 = false;
        this.flag11 = false;
        this.flag12 = false;
        this.flag6 = false;
    }

    // $VF: Could not inline inconsistent finally blocks
    // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
    private void method_1567() {
        this.timer.method_2017();
        this.method_1566();
        if (!this.list.isEmpty()) {
            ArrayList var4 = new ArrayList<>(this.list);
            this.list.clear();
            if (mc.getNetHandler() != null) {
                this.flag2 = true;

                try {
                    mc.getNetHandler().getNetworkManager().sendGrouped(var4);
                } catch (Throwable var3) {
                    this.flag2 = false;
                    throw var3;
                }

                this.flag2 = false;
            }
        }
    }

    public boolean method_1568() {
        return (this.flag10 || this.flag5) && !this.flag7;
    }

    public boolean method_1569() {
        return mc.thePlayer != null && !this.method_1565() && !this.flag6 && !this.flag12 && !this.flag11;
    }

    private void method_1570() {
        if (!this.method_1565()) {
            if (method_1561()) {
                ItemStack var2 = mc.thePlayer.getHeldItem();
                this.method_1564(new C08PacketPlayerBlockPlacement(var2));
                this.method_1559();
                this.flag7 = true;
            }
        }
    }
}
