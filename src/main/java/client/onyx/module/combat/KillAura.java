package client.onyx.module.combat;

import client.onyx.Onyx;
import client.onyx.event.impl.PacketDirection;
import client.onyx.event.impl.PacketEvent;
import client.onyx.event.impl.PlayerUpdateEvent;
import client.onyx.event.impl.PostPlayerUpdateEvent;
import client.onyx.event.impl.WorldLoadEvent;
import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.module.player.scaffold.Scaffold;
import client.onyx.rotation.AimSettings;
import client.onyx.rotation.Aimer;
import client.onyx.rotation.HitVecUtil;
import client.onyx.rotation.MoveFix;
import client.onyx.rotation.YawPitch;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.EnumSetSetting;
import client.onyx.setting.impl.EnumSetting;
import client.onyx.setting.impl.KeybindSetting;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.util.Timer;
import client.onyx.util.player.TeamUtil;
import java.util.Iterator;
import java.util.Random;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S19PacketEntityStatus;

public class KillAura extends Module {
    private static final long LONG_251 = 25L;
    public NumberSetting distance = new NumberSetting("Distance", 3.0, 3.0, 6.0, 0.05);
    public EnumSetSetting<KillAura.TargetFilter> targets = new EnumSetSetting<>(
            "Targets",
            KillAura.TargetFilter.class,
            KillAura.TargetFilter.PLAYERS,
            KillAura.TargetFilter.NAKED,
            KillAura.TargetFilter.NAKED_INVISIBLE,
            KillAura.TargetFilter.INVISIBLE
        )
        .describe("Teammates are excluded unless explicitly selected");
    public BooleanSetting throughWalls = new BooleanSetting("Through walls", false).describe("Allows targeting and attacking entities behind blocks");
    public EnumSetting<KillAura.HitCheck> hitCheck = new EnumSetting<>("Hit check", KillAura.HitCheck.RAYCAST)
        .describe("Holds the attack until the sent rotation actually covers the target");
    public NumberSetting hitAngle = new NumberSetting("Hit angle", 8.0, 1.0, 45.0, 1.0)
        .method_147("°")
        .visibleWhen(() -> this.hitCheck.method_168(KillAura.HitCheck.ANGLE));
    public NumberSetting switchDelay = new NumberSetting("Switch delay", 0.0, 0.0, 20.0, 1.0)
        .method_147("t")
        .describe("Extra ticks to hold the attack after the target changes");
    public NumberSetting cpsMin = new NumberSetting("CPS min", 7.0, 1.0, 20.0, 1.0);
    public NumberSetting cpsMax = new NumberSetting("CPS max", 10.0, 1.0, 20.0, 1.0);
    public BooleanSetting onlySword = new BooleanSetting("Only sword", false).describe("Only attacks while a sword is held in hand");
    public EnumSetting<KillAura.AutoBlockMode> autoblock = new EnumSetting<>("Autoblock", KillAura.AutoBlockMode.OFF).describe("Automatically blocks sword");
    public EnumSetting<KillAura.AttackMode> autoblockTrigger = new EnumSetting<>("Autoblock trigger", KillAura.AttackMode.ALWAYS)
        .describe("Whether the autoblock runs on its own, only while a key is held, or on a key that toggles it")
        .visibleWhen(this.autoblock, var0 -> var0 != KillAura.AutoBlockMode.OFF);
    public KeybindSetting autoblockKey = new KeybindSetting("Autoblock key")
        .describe("The key the hold and toggle triggers watch")
        .visibleWhen(() -> !this.autoblock.method_168(KillAura.AutoBlockMode.OFF) && !this.autoblockTrigger.method_168(KillAura.AttackMode.ALWAYS));
    public AimSettings aimSettings = new AimSettings();
    private final Aimer aimer = new Aimer(this.aimSettings);
    private final Random random = new Random();
    private final CombatHelper combatHelper = new CombatHelper();
    private final Timer timer = new Timer();
    private EntityLivingBase entityLivingBase;
    private EntityLivingBase entityLivingBase2;
    private long longValue;
    private boolean flag;
    private int intValue;
    private boolean flag2;

    private double method_1621(Entity var1) {
        return HitVecUtil.method_2131(var1);
    }

    @EventHandler
    public void onPacket(PacketEvent var1) {
        if (var1.getPacketDirection() == PacketDirection.OUTGOING) {
            this.combatHelper.method_1550(var1);
        } else if (this.method_1626()) {
            Packet var2;
            if ((var2 = var1.getPacket()) instanceof S19PacketEntityStatus) {
                S19PacketEntityStatus var5;
                S19PacketEntityStatus var3 = var5 = (S19PacketEntityStatus)var2;
                if (var3.getOpCode() == 2) {
                    if (mc.thePlayer != null && var5.getEntityId() == mc.thePlayer.getEntityId()) {
                        this.combatHelper.method_1554();
                    }
                }
            }
        }
    }

    private boolean method_1622() {
        return (!this.onlySword.method_146() || CombatHelper.method_1561())
            && this.intValue >= this.switchDelay.method_149()
            && this.method_1631(this.entityLivingBase2)
            && this.method_1624(MoveFix.method_2145());
    }

    public static boolean method_1623() {
        KillAura var1;
        if (Onyx.moduleManager == null) {
            var1 = null;
        } else {
            var1 = Onyx.moduleManager.killAura;
        }

        return var1 == null || var1.combatHelper.method_1569();
    }

    private boolean method_1624(YawPitch var1) {
        switch ((KillAura.HitCheck)this.hitCheck.getValue()) {
            case OFF:
                return true;
            case ANGLE:
                if (this.aimer.method_2122(var1) <= this.hitAngle.method_151()) {
                    return true;
                }

                return false;
            case RAYCAST:
                return this.aimer.method_2113(this.entityLivingBase2, var1, this.distance.getValue());
            default:
                throw new MatchException(null, null);
        }
    }

    private void method_1625() {
        mc.thePlayer.swingItem();
        mc.playerController.attackEntity(mc.thePlayer, this.entityLivingBase2);
    }

    private boolean method_1626() {
        if (!this.autoblock.method_168(KillAura.AutoBlockMode.WATCHDOG)) {
            return false;
        } else {
            switch ((KillAura.AttackMode)this.autoblockTrigger.getValue()) {
                case ALWAYS:
                    return true;
                case HOLD:
                    if (this.autoblockKey.method_163() && this.flag2) {
                        return true;
                    }

                    return false;
                case TOGGLE:
                    if (this.autoblockKey.method_163() && !this.flag) {
                        return false;
                    }

                    return true;
                default:
                    throw new MatchException(null, null);
            }
        }
    }

    @Override
    public void onDisable() {
        this.entityLivingBase2 = null;
        this.entityLivingBase = null;
        this.intValue = 0;
        this.longValue = 0L;
        this.flag = false;
        this.flag2 = false;
        this.combatHelper.method_1551(true);
        this.aimer.method_2120();
    }

    public static boolean method_1627() {
        KillAura var1;
        if (Onyx.moduleManager == null) {
            var1 = null;
        } else {
            var1 = Onyx.moduleManager.killAura;
        }

        return var1 != null && var1.isToggled() && var1.combatHelper.method_1568();
    }

    private boolean method_1628(EntityLivingBase var1) {
        if (var1 instanceof EntityPlayer) {
            EntityPlayer var5;
            if (AntiBot.method_1637(var5 = (EntityPlayer)var1)) {
                return false;
            } else {
                boolean var6;
                if (var1.getTotalArmorValue() == 0) {
                    var6 = true;
                } else {
                    var6 = false;
                }

                boolean var4 = var1.isInvisible();
                boolean var2 = this.targets.method_79(KillAura.TargetFilter.PLAYERS);
                if (var6 && this.targets.method_79(KillAura.TargetFilter.NAKED)) {
                    var2 = true;
                }

                if (var4 && this.targets.method_79(KillAura.TargetFilter.INVISIBLE)) {
                    var2 = true;
                }

                if (var6 && var4 && this.targets.method_79(KillAura.TargetFilter.NAKED_INVISIBLE)) {
                    var2 = true;
                }

                return method_1635(TeamUtil.method_2155(mc.thePlayer, var5), this.targets.method_79(KillAura.TargetFilter.TEAMMATES), var2);
            }
        } else {
            return var1 instanceof EntityAnimal
                ? this.targets.method_79(KillAura.TargetFilter.ANIMALS)
                : var1 instanceof EntityLiving && this.targets.method_79(KillAura.TargetFilter.MOBS);
        }
    }

    private void method_1629() {
        if (this.longValue == 0L || this.timer.method_2018(this.longValue - 25L)) {
            if (this.method_1622()) {
                this.method_1625();
                this.longValue = this.method_1633();
                this.timer.method_2017();
            }
        }
    }

    private void method_1630() {
        this.entityLivingBase = this.entityLivingBase2 = null;
        this.intValue = 0;
        this.flag = false;
        this.flag2 = false;
        this.combatHelper.method_1551(true);
        this.aimer.method_2120();
    }

    public EntityLivingBase getEntityLivingBase2() {
        return this.entityLivingBase2;
    }

    private boolean method_1631(Entity var1) {
        return this.throughWalls.method_146() || mc.thePlayer.canEntityBeSeen(var1);
    }

    @EventHandler
    public void onPostPlayerUpdate(PostPlayerUpdateEvent var1) {
        this.combatHelper.method_1549();
    }

    private EntityLivingBase method_1632() {
        var var4 = this;
        EntityLivingBase var5 = null;
        double var2 = this.distance.getValue() * this.distance.getValue();
        Iterator var9 = mc.theWorld.loadedEntityList.iterator();

        label45:
        while (true) {
            Iterator var8 = var9;

            while (var8.hasNext()) {
                Entity var1;
                if (!((var1 = (Entity)var9.next()) instanceof EntityLivingBase)) {
                    continue label45;
                }

                EntityLivingBase var10;
                if ((var10 = (EntityLivingBase)var1) == mc.thePlayer) {
                    var8 = var9;
                } else if (!var10.isEntityAlive()) {
                    var8 = var9;
                } else if (var10 instanceof EntityArmorStand) {
                    var8 = var9;
                } else {
                    EntityPlayer var6;
                    if (var10 instanceof EntityPlayer && (var6 = (EntityPlayer)var10).isSpectator()) {
                        var8 = var9;
                    } else if (!var4.method_1628(var10)) {
                        var8 = var9;
                    } else if (!var4.method_1631(var10)) {
                        var8 = var9;
                    } else {
                        double var11;
                        if ((var11 = var4.method_1621(var10)) > var2) {
                            var8 = var9;
                        } else {
                            var5 = var10;
                            var2 = var11;
                            var8 = var9;
                        }
                    }
                }
            }

            return var5;
        }
    }

    public KillAura() {
        super("KillAura", "Automatically aims and kills enemies", Category.COMBAT);
    }

    private long method_1633() {
        float var1 = Math.min(this.cpsMin.method_151(), this.cpsMax.method_151());
        float var3 = Math.max(this.cpsMin.method_151(), this.cpsMax.method_151());
        return (long)(1000.0 / (var1 + this.random.nextFloat() * (var3 - var1)));
    }

    private void method_1634() {
        boolean var2;
        if (mc.currentScreen == null && this.autoblockKey.method_163() && this.autoblockKey.method_157()) {
            var2 = true;
        } else {
            var2 = false;
        }

        if (var2 && !this.flag2 && this.autoblockTrigger.method_168(KillAura.AttackMode.TOGGLE)) {
            KillAura var4;
            boolean var5;
            if (this.flag) {
                var5 = false;
                var4 = this;
            } else {
                var5 = true;
                var4 = this;
            }

            var4.flag = var5;
        }

        this.flag2 = var2;
    }

    @EventHandler
    public void onPlayerUpdate(PlayerUpdateEvent var1) {
        if (mc.thePlayer != null && mc.theWorld != null) {
            this.method_1634();
            this.combatHelper.method_1562();
            if (!mc.thePlayer.isEntityAlive()) {
                this.method_1630();
            } else if (Scaffold.INSTANCE.isToggled()) {
                this.method_1630();
            } else {
                BedBreaker var6 = Onyx.moduleManager.bedBreaker;
                if (Onyx.moduleManager.bedBreaker.isToggled() && !var6.prioritizeKillAura.method_146() && var6.getBlockPos3() != null) {
                    this.method_1630();
                } else {
                    EntityLivingBase var7 = this.entityLivingBase2;
                    this.entityLivingBase = this.method_1632();
                    this.entityLivingBase2 = this.entityLivingBase;
                    if (this.entityLivingBase2 != null && (!this.onlySword.method_146() || CombatHelper.method_1561())) {
                        KillAura var3;
                        int var4;
                        if (this.entityLivingBase2 != var7) {
                            var4 = 0;
                            var3 = this;
                        } else {
                            var4 = this.intValue + 1;
                            var3 = this;
                        }

                        var3.intValue = var4;
                        boolean var8 = false;
                        boolean var5;
                        if (this.method_1626() && CombatHelper.method_1561()) {
                            var5 = this.combatHelper.method_1555(this.entityLivingBase2, this::method_1625);
                        } else {
                            this.combatHelper.method_1551(true);
                            var5 = var8;
                        }

                        if (!var5) {
                            this.method_1629();
                        }

                        this.aimer.method_2118(this.entityLivingBase2, this.distance.getValue());
                    } else {
                        this.entityLivingBase2 = null;
                        this.intValue = 0;
                        this.combatHelper.method_1551(true);
                        this.aimer.method_2120();
                    }
                }
            }
        }
    }

    public static boolean method_1635(boolean var0, boolean var1, boolean var2) {
        return var0 ? var1 : var2;
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent var1) {
        this.combatHelper.method_1556();
        this.method_1630();
    }

    public static boolean method_1636() {
        KillAura var1;
        if (Onyx.moduleManager == null) {
            var1 = null;
        } else {
            var1 = Onyx.moduleManager.killAura;
        }

        return var1 != null && var1.isToggled() && var1.combatHelper.method_1563();
    }

    public EntityLivingBase getEntityLivingBase() {
        return this.entityLivingBase;
    }

    public static enum AttackMode {
        ALWAYS,
        HOLD,
        TOGGLE;
    }

    public static enum AutoBlockMode {
        OFF,
        WATCHDOG;
    }

    public static enum HitCheck {
        OFF,
        ANGLE,
        RAYCAST;
    }

    public static enum TargetFilter {
        PLAYERS,
        TEAMMATES,
        NAKED,
        NAKED_INVISIBLE,
        INVISIBLE,
        MOBS,
        ANIMALS;
    }
}
