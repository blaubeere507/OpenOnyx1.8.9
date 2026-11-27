package client.onyx.module.player.scaffold.technique;

import client.onyx.event.impl.MovementInputEvent;
import client.onyx.event.impl.PostJumpEvent;
import client.onyx.event.impl.PostTickEvent;
import client.onyx.module.player.scaffold.Scaffold;
import client.onyx.module.player.scaffold.ScaffoldRotationSettings;
import client.onyx.placement.AimPointSelection;
import client.onyx.placement.CenterAimPoint;
import client.onyx.placement.EyePosition;
import client.onyx.placement.PlacementPlan;
import client.onyx.placement.PlacementPlanner;
import client.onyx.placement.PlacementSearch;
import client.onyx.placement.PlacementTarget;
import client.onyx.placement.SearchPattern;
import client.onyx.rotation.Rotation;
import client.onyx.rotation.RotationManager;
import client.onyx.rotation.RotationRequest;
import client.onyx.rotation.aim.AimModel;
import client.onyx.setting.NamedEnum;
import client.onyx.setting.Setting;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.EnumSetting;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.util.geometry.Ray;
import client.onyx.util.math.VecUtil;
import client.onyx.util.player.MovementInputState;
import client.onyx.util.player.MovementPredictor;
import client.onyx.util.world.BlockRaycast;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

public class HypixelKeepYTechnique extends ScaffoldTechnique {
    public static final HypixelKeepYTechnique hypixelKeepYTechnique = new HypixelKeepYTechnique();
    private static final float FLOAT_99_01 = 99.0F;
    private static final float FLOAT_84_01 = 84.0F;
    private static final long LONG_5001 = 500L;
    private static final float FLOAT_39_01 = 39.0F;
    private static final double DOUBLE_4_01 = 4.0;
    public EnumSetting<HypixelKeepYTechnique.RotationStyle> rotationStyle = new EnumSetting<>("Rotation style", HypixelKeepYTechnique.RotationStyle.DIAGONAL)
        .describe("Where the aim sits on the block face; falls back to Normal while moving diagonally")
        .visibleWhen(Setting.booleanSupplier);
    public NumberSetting rotationOffset = new NumberSetting("Rotation offset", 0.15, 0.0, 1.0, 0.01)
        .describe("Shifts the aim point from the face centre towards the player")
        .visibleWhen(() -> this.rotationStyle.method_168(HypixelKeepYTechnique.RotationStyle.OFFSET));
    public BooleanSetting aimCheck = new BooleanSetting("Aim check", true)
        .describe("Only place once the crosshair raytrace actually lands on the target block")
        .visibleWhen(Setting.booleanSupplier);
    public BooleanSetting strictAimCheck = new BooleanSetting("Strict aim check", true)
        .describe("Require the raytrace to hit the exact face, not just the block")
        .visibleWhen(Setting.booleanSupplier);
    public NumberSetting straightJumpBlocks = new NumberSetting("Straight jump blocks", 0.0, 0.0, 3.0, 1.0)
        .describe("Blocks laid on the ground before jumping while bridging straight")
        .visibleWhen(Setting.booleanSupplier);
    public NumberSetting diagonalJumpBlocks = new NumberSetting("Diagonal jump blocks", 0.0, 0.0, 3.0, 1.0)
        .describe("Blocks laid on the ground before jumping while bridging diagonally")
        .visibleWhen(Setting.booleanSupplier);
    public NumberSetting straightAirDelay = new NumberSetting("Straight air delay", 1.0, 0.0, 4.0, 1.0)
        .method_147(" ticks")
        .describe("Ticks to wait after the target appears in the arc before placing (straight)")
        .visibleWhen(Setting.booleanSupplier);
    public NumberSetting diagonalAirDelay = new NumberSetting("Diagonal air delay", 1.0, 0.0, 4.0, 1.0)
        .method_147(" ticks")
        .describe("Ticks to wait after the target appears in the arc before placing (diagonal)")
        .visibleWhen(Setting.booleanSupplier);
    public BooleanSetting keepYOnRightClick = new BooleanSetting("Keep Y on right click", false)
        .describe("Only pin the Y level while the use key is held")
        .visibleWhen(Setting.booleanSupplier);
    public NumberSetting idleAimSpeed = new NumberSetting("Idle aim speed", 100.0, 1.0, 180.0, 1.0)
        .method_147("°/t")
        .describe("How fast the aim turns back under your feet once you stop placing");
    private transient float floatValue = 99.0F;
    private final transient AimModel aimModel = (var1, var2, var3) -> var2.method_1056(var3, this.floatValue, 39.0F);
    private transient boolean flag2;
    private transient boolean flag22;
    private transient int intValue2;
    private transient int intValue22;
    private transient int intValue3;
    private transient boolean flag3;
    private transient long longValue;
    private transient boolean flag4;

    @EventHandler
    private void onPostJump(PostJumpEvent var1) {
        this.flag4 = false;
    }

    private Rotation method_1720() {
        BlockPos var3 = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0, mc.thePlayer.posZ);
        Rotation var2 = Rotation.method_1058(new Vec3(var3.getX() + 0.5, var3.getY() + 1.0, var3.getZ() + 0.5), mc.thePlayer.getPositionEyes(1.0F));
        return this.method_1722(var3, EnumFacing.UP, var2, true);
    }

    private int method_1721() {
        return !this.method_1723(false) ? this.straightJumpBlocks.method_149() : this.diagonalJumpBlocks.method_149();
    }

    private Rotation method_1722(BlockPos var1, EnumFacing var2, Rotation var3, boolean var4) {
        float var5 = var3.getPitch();
        float var16 = this.method_1734();
        switch (this.method_1732()) {
            case DIAGONAL:
                boolean var14;
                if (!this.method_1731(var1, var2, var16 - 135.0F, var5) && !this.method_1731(var1, var2, var16 + 135.0F, var5)) {
                    var14 = false;
                } else {
                    var14 = true;
                }

                boolean var17 = this.method_1731(var1, var2, var16 - 180.0F, var5);
                float var9;
                if (!var4 && !var14 && !var17) {
                    var9 = var3.getYaw();
                } else if (!var4 && this.method_1723(true)) {
                    float var15;
                    if (var17) {
                        var15 = var16 - 180.0F;
                    } else {
                        var15 = var3.getYaw();
                    }

                    var9 = var15;
                } else if (!var4 && !var14) {
                    var9 = var16 - 180.0F;
                } else {
                    BlockPos var19 = new BlockPos(Math.floor(mc.thePlayer.posX), Math.floor(mc.thePlayer.posY) - 1.0, Math.floor(mc.thePlayer.posZ));
                    double var10 = var19.getX() + 0.5 - mc.thePlayer.posX;
                    if (MathHelper.wrapAngleTo180_float((float)(Math.toDegrees(Math.atan2(var19.getZ() + 0.5 - mc.thePlayer.posZ, var10)) - 90.0) - var16)
                        > 0.0F) {
                        var9 = var16 + 135.0F;
                        if (this.strictAimCheck.getValue() && !this.method_1731(var1, var2, var9, var5)) {
                            var9 = var16 - 135.0F;
                        }
                    } else {
                        var9 = var16 - 135.0F;
                        if (this.strictAimCheck.getValue() && !this.method_1731(var1, var2, var9, var5)) {
                            var9 = var16 + 135.0F;
                        }
                    }
                }

                return new Rotation(var9, var5);
            case BACK:
                if (var4) {
                    return new Rotation(var16 - 180.0F, var5);
                }

                boolean var12;
                if (!this.method_1731(var1, var2, var16 - 135.0F, var5) && !this.method_1731(var1, var2, var16 + 135.0F, var5)) {
                    var12 = false;
                } else {
                    var12 = true;
                }

                boolean var13;
                if (this.method_1731(var1, var2, var16 - 180.0F, var5) || !this.strictAimCheck.getValue() && var12) {
                    var13 = false;
                } else {
                    var13 = true;
                }

                Rotation var18;
                float var10002;
                float var10003;
                if (var13) {
                    var10002 = var3.getYaw();
                    var10003 = var5;
                } else {
                    var10002 = var16 - 180.0F;
                    var10003 = var5;
                }

                var18 = new Rotation(var10002, var10003);
                return var18;
            case NORMAL:
                float var7 = var3.getYaw();
                if (this.method_1731(var1, var2, var16 - 180.0F, var5)) {
                    var7 = var16 - 180.0F;
                } else if (this.method_1731(var1, var2, var16 - 135.0F, var5)) {
                    var7 = var16 - 135.0F;
                } else if (this.method_1731(var1, var2, var16 + 135.0F, var5)) {
                    var7 = var16 + 135.0F;
                }

                return new Rotation(var7, var5);
            case OFFSET:
                Rotation var8 = Rotation.method_1058(this.method_1729(var1, var2), mc.thePlayer.getPositionEyes(1.0F));
                if (this.strictAimCheck.getValue() && !this.method_1731(var1, var2, var8.getYaw(), var8.getPitch())) {
                    return new Rotation(var3.getYaw(), var8.getPitch());
                }

                return var8;
            default:
                throw new MatchException(null, null);
        }
    }

    private boolean method_1723(boolean var1) {
        float var9;
        if ((var9 = this.method_1734() % 360.0F) < 0.0F) {
            var9 += 360.0F;
        }

        float var10;
        float var10000 = var10 = var9 % 90.0F;
        float var4;
        float var5;
        if (!var1) {
            float var3 = var10000;
            var5 = 20.0F;
            var4 = var3;
        } else {
            var5 = 10.0F;
            var4 = var10000;
        }

        if (var4 > var5) {
            float var7;
            float var8;
            if (!var1) {
                var8 = 70.0F;
                var7 = var10;
            } else {
                var8 = 80.0F;
                var7 = var10;
            }

            if (var7 < var8) {
                return true;
            }
        }

        return false;
    }

    private long method_1724() {
        if (mc.getNetHandler() == null) {
            return 0L;
        } else {
            NetworkPlayerInfo var2;
            return (var2 = mc.getNetHandler().getPlayerInfo(mc.thePlayer.getUniqueID())) == null ? 0L : Math.max(0, var2.getResponseTime());
        }
    }

    private HypixelKeepYTechnique() {
        super("HypixelKeepY");
    }

    @Override
    public RotationRequest method_1725(Rotation var1, ScaffoldRotationSettings var2, boolean var3) {
        RotationRequest var5 = var2.method_1033(var1, var3);
        return new RotationRequest(var1, null, List.of(this.aimModel), var5.getIntValue(), var5.getFloatValue(), var3, var5.getMovementCorrection(), null);
    }

    public void method_1726() {
        if (this.method_74() && mc.thePlayer != null) {
            if (!mc.thePlayer.onGround) {
                this.intValue2 = 0;
            } else {
                this.intValue2++;
            }
        }
    }

    @Override
    public PlacementTarget method_1727(Vec3 var1, boolean var2, Ray var3, ItemStack var4) {
        BlockPos var5 = VecUtil.method_1956(var1);
        BlockPos var6;
        if (this.method_1730()) {
            if (this.intValue3 > mc.thePlayer.posY - 1.0) {
                return null;
            }

            var6 = new BlockPos(var5.getX(), this.intValue3, var5.getZ());
        } else {
            var6 = Scaffold.INSTANCE.method_1675(var5);
        }

        PlacementPlan var8 = new PlacementPlan(
            new PlacementPlanner(SearchPattern.NORMAL.getList(), this.method_1743(var1, var3)),
            new AimPointSelection(CenterAimPoint.INSTANCE),
            var4,
            new EyePosition(var1, var2)
        );
        PlacementTarget var9;
        if ((var9 = PlacementSearch.method_42(var6, var8)) != null && !this.flag4) {
            this.flag22 = this.flag4 = true;
            this.intValue22 = 0;
        }

        return var9;
    }

    @EventHandler(
        priority = 1000
    )
    private void onPostTick(PostTickEvent var1) {
        if (mc.thePlayer != null) {
            this.method_1736();
            if (this.flag22) {
                this.intValue22++;
            }
        }
    }

    @Override
    public MovingObjectPosition method_1728(PlacementTarget var1, Rotation var2) {
        if (var1 == null) {
            return null;
        } else if (this.method_1730() && this.intValue22 < this.method_1733() && this.intValue2 >= this.method_1721()) {
            return null;
        } else {
            this.flag22 = false;
            this.longValue = System.currentTimeMillis();
            MovingObjectPosition var4;
            if ((var4 = super.method_1728(var1, var2)) != null && var1.method_51(var4)) {
                return var4;
            } else if (!this.aimCheck.getValue()) {
                return var1.method_52();
            } else {
                return !this.strictAimCheck.getValue()
                        && var4 != null
                        && var4.typeOfHit == MovingObjectType.BLOCK
                        && var4.getBlockPos().equals(var1.blockPos())
                    ? var1.method_52()
                    : null;
            }
        }
    }

    private Vec3 method_1729(BlockPos var1, EnumFacing var2) {
        Vec3 var19 = new Vec3(
            var1.getX() + 0.5 + var2.getFrontOffsetX() * 0.5,
            var1.getY() + 0.5 + var2.getFrontOffsetY() * 0.5,
            var1.getZ() + 0.5 + var2.getFrontOffsetZ() * 0.5
        );
        Vec3 var4 = mc.thePlayer.getPositionEyes(1.0F);
        double var13;
        if (var2.getFrontOffsetX() != 0) {
            var13 = var19.xCoord;
        } else {
            var13 = MathHelper.clamp_double(var4.xCoord, var1.getX(), var1.getX() + 1.0);
        }

        double var15;
        if (var2.getFrontOffsetY() != 0) {
            var15 = var19.yCoord;
        } else {
            var15 = MathHelper.clamp_double(var4.yCoord, var1.getY(), var1.getY() + 1.0);
        }

        double var17;
        if (var2.getFrontOffsetZ() != 0) {
            var17 = var19.zCoord;
        } else {
            var17 = MathHelper.clamp_double(var4.zCoord, var1.getZ(), var1.getZ() + 1.0);
        }

        double var11 = this.rotationOffset.getValue();
        return new Vec3(
            var19.xCoord + (var13 - var19.xCoord) * var11, var19.yCoord + (var15 - var19.yCoord) * var11, var19.zCoord + (var17 - var19.zCoord) * var11
        );
    }

    @Override
    public void method_62() {
        if (mc.thePlayer != null) {
            this.intValue3 = MathHelper.floor_double(mc.thePlayer.posY) - 1;
            this.flag3 = false;
            this.intValue2 = 0;
            this.flag4 = false;
            HypixelKeepYTechnique var2;
            boolean var3;
            if (mc.thePlayer.onGround) {
                var3 = false;
                var2 = this;
            } else {
                var3 = true;
                var2 = this;
            }

            var2.flag22 = var3;
            HypixelKeepYTechnique var5;
            byte var6;
            if (!this.flag22) {
                var6 = 0;
                var5 = this;
            } else {
                var6 = -1;
                var5 = this;
            }

            var5.intValue22 = var6;
            this.flag2 = false;
            this.longValue = System.currentTimeMillis() - 600L;
        }
    }

    @EventHandler
    private void onMovementInput(MovementInputEvent var1) {
        if (mc.thePlayer != null && mc.currentScreen == null) {
            if (this.method_1730()) {
                if (var1.getMovementInputState().method_1919()) {
                    if (mc.thePlayer.onGround) {
                        if (Scaffold.INSTANCE.method_1663() > 0) {
                            if (this.intValue2 >= this.method_1721()) {
                                var1.method_2622(true);
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean method_1730() {
        return !mc.thePlayer.isPotionActive(Potion.jump) && (!this.keepYOnRightClick.getValue() || mc.gameSettings.keyBindUseItem.isKeyDown());
    }

    private boolean method_1731(BlockPos var1, EnumFacing var2, float var3, float var4) {
        MovingObjectPosition var6;
        return (var6 = BlockRaycast.method_1993(new Rotation(var3, var4), 4.0)) != null
            && var6.typeOfHit == MovingObjectType.BLOCK
            && var6.getBlockPos().equals(var1)
            && (!this.strictAimCheck.getValue() || var6.sideHit == var2);
    }

    private HypixelKeepYTechnique.RotationStyle method_1732() {
        return this.method_1723(false) ? HypixelKeepYTechnique.RotationStyle.NORMAL : this.rotationStyle.getValue();
    }

    private int method_1733() {
        return !this.method_1723(false) ? this.straightAirDelay.method_149() : this.diagonalAirDelay.method_149();
    }

    private float method_1734() {
        MovementInputState var2;
        return (var2 = Scaffold.INSTANCE.getMovementInputState()) != null && var2.method_1919()
            ? MovementPredictor.method_1888(mc.thePlayer, var2)
            : mc.thePlayer.rotationYaw;
    }

    @Override
    public Rotation method_1735(PlacementTarget var1) {
        if (mc.thePlayer == null) {
            return null;
        } else {
            if (mc.thePlayer.onGround) {
                this.flag2 = false;
            }

            if (System.currentTimeMillis() - this.longValue > this.method_1724() + 500L && Scaffold.INSTANCE.method_1663() > 0) {
                this.floatValue = this.idleAimSpeed.method_151();
                return this.method_1720();
            } else if (var1 == null) {
                return RotationManager.INSTANCE.getServerRotation();
            } else {
                boolean var5 = this.method_1730();
                boolean var3;
                if (this.flag2) {
                    this.floatValue = 39.0F;
                    var3 = var5;
                } else if (!var5) {
                    this.floatValue = this.idleAimSpeed.method_151();
                    var3 = var5;
                } else {
                    this.floatValue = (float)ThreadLocalRandom.current().nextDouble(84.0, 99.0);
                    var3 = var5;
                }

                if (var3 && !mc.thePlayer.onGround) {
                    this.flag2 = true;
                }

                return this.method_1722(var1.blockPos(), var1.enumFacing(), var1.rotation(), false);
            }
        }
    }

    private void method_1736() {
        int var3 = MathHelper.floor_double(mc.thePlayer.posY) - 1;
        if (!this.method_1730()) {
            this.intValue3 = var3;
            this.flag3 = false;
        } else if (mc.gameSettings.keyBindJump.isKeyDown()) {
            this.intValue3 = var3;
            this.flag3 = true;
        } else {
            if (this.flag3 || mc.thePlayer.onGround) {
                this.intValue3 = var3;
                this.flag3 = false;
            }
        }
    }

    public static enum RotationStyle implements NamedEnum {
        DIAGONAL("Diagonal"),
        BACK("Back"),
        NORMAL("Normal"),
        OFFSET("Offset");

        private final String string;

        @Override
        public String getDisplayName() {
            return this.string;
        }

        private RotationStyle(String var3) {
            this.string = var3;
        }
    }
}
