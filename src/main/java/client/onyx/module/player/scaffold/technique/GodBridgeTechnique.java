package client.onyx.module.player.scaffold.technique;

import client.onyx.module.player.scaffold.Scaffold;
import client.onyx.module.player.scaffold.feature.PredictedPlacement;
import client.onyx.module.player.scaffold.feature.SneakController;
import client.onyx.placement.AimPointSelection;
import client.onyx.placement.CenterAimPoint;
import client.onyx.placement.EyePosition;
import client.onyx.placement.PlacementPlan;
import client.onyx.placement.PlacementPlanner;
import client.onyx.placement.PlacementSearch;
import client.onyx.placement.PlacementTarget;
import client.onyx.placement.SearchPattern;
import client.onyx.rotation.Rotation;
import client.onyx.setting.NamedEnum;
import client.onyx.setting.impl.EnumSetSetting;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.setting.impl.RangeSetting;
import client.onyx.simulation.SimulatedTick;
import client.onyx.simulation.SimulationManager;
import client.onyx.util.geometry.Ray;
import client.onyx.util.math.VecUtil;
import client.onyx.util.player.BlockPlacer;
import client.onyx.util.player.MovementInputState;
import client.onyx.util.player.MovementPredictor;
import client.onyx.util.world.BlockRaycast;
import client.onyx.util.world.HitboxMode;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class GodBridgeTechnique extends ScaffoldTechnique implements SneakController {
    public static final GodBridgeTechnique godBridgeTechnique = new GodBridgeTechnique();
    private static final float FLOAT_0_0174532921 = (float) (Math.PI / 180.0);
    private static final double DOUBLE_0_081 = 0.08;
    private static final double DOUBLE_0_419999986886978151 = 0.42F;
    private static final double DOUBLE_0_981 = 0.98;
    public EnumSetSetting<GodBridgeTechnique.SneakAction> modes = new EnumSetSetting<>(
            "Modes", GodBridgeTechnique.SneakAction.class, GodBridgeTechnique.SneakAction.JUMP
        )
        .method_77(false);
    public NumberSetting forceSneakBelowCount = new NumberSetting("Force sneak below count", 3.0, 0.0, 10.0, 1.0);
    public RangeSetting rangeSetting = RangeSetting.method_90("Sneak time", 1, 1, 1, 10).method_94(" ticks");
    private transient boolean flag2;

    private PredictedPlacement method_1756(GodBridgeTechnique.SneakAction var1) {
        switch (var1) {
            case JUMP:
                return new PredictedPlacement(true, 0, false, false);
            case SNEAK:
                return new PredictedPlacement(false, this.rangeSetting.method_95(), false, false);
            case STOP_INPUT:
                return new PredictedPlacement(false, 0, true, false);
            case BACKWARDS:
                return new PredictedPlacement(false, 0, false, true);
            default:
                throw new MatchException(null, null);
        }
    }

    private GodBridgeTechnique() {
        super("GodBridge");
    }

    private Rotation method_1757(PlacementTarget var1) {
        float var2 = (float)(Math.floor(var1.rotation().getYaw() / 90.0F) * 90.0);
        return new Rotation(var2 + 45.0F, 75.0F);
    }

    private double method_1758() {
        double var1 = 0.42F;
        if (mc.thePlayer.isPotionActive(Potion.jump)) {
            var1 += (mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F;
        }

        return var1;
    }

    @Override
    public Rotation method_1735(PlacementTarget var1) {
        if (Scaffold.INSTANCE.getMovementInputState().equals(MovementInputState.INSTANCE8)) {
            return var1 == null ? null : this.method_1757(var1);
        } else {
            boolean var3;
            float var6;
            if ((var6 = (float)(Math.rint((MovementPredictor.method_1888(mc.thePlayer, Scaffold.INSTANCE.getMovementInputState()) + 180.0F) / 45.0F) * 45.0))
                    % 90.0F
                == 0.0F) {
                var3 = true;
            } else {
                var3 = false;
            }

            return !var3 ? this.method_1760(var6) : this.method_1761(var6);
        }
    }

    @Override
    public PlacementTarget method_1727(Vec3 var1, boolean var2, Ray var3, ItemStack var4) {
        PlacementPlan var5 = new PlacementPlan(
            new PlacementPlanner(SearchPattern.NORMAL.getList(), PlacementPlan.method_30(var1)),
            new AimPointSelection(CenterAimPoint.INSTANCE),
            var4,
            new EyePosition(var1, var2)
        );
        return PlacementSearch.method_42(Scaffold.INSTANCE.method_1675(VecUtil.method_1956(var1)), var5);
    }

    private boolean method_1759() {
        double var1 = this.method_1758();
        double var3 = 0.0;

        for (double var5 = var1; var5 > 0.0; var5 = var1 = (var1 - 0.08) * 0.98) {
            var3 += var1;
        }

        return var3 >= 2.0;
    }

    private Rotation method_1760(float var1) {
        return new Rotation(var1, 75.6F);
    }

    private Rotation method_1761(float var1) {
        if (mc.thePlayer.onGround) {
            GodBridgeTechnique var6;
            boolean var7;
            label33: {
                GodBridgeTechnique var4;
                if (Math.floor(mc.thePlayer.posX + (float)Math.cos(var1 * (float) (Math.PI / 180.0)) * 0.5) != Math.floor(mc.thePlayer.posX)) {
                    var4 = this;
                } else {
                    if (Math.floor(mc.thePlayer.posZ + (float)Math.sin(var1 * (float) (Math.PI / 180.0)) * 0.5) == Math.floor(mc.thePlayer.posZ)) {
                        var7 = false;
                        var6 = this;
                        break label33;
                    }

                    var4 = this;
                }

                var7 = true;
                var6 = var4;
            }

            var6.flag2 = var7;
            EnumFacing var11 = EnumFacing.fromAngle(var1);
            BlockPos var12 = VecUtil.method_1956(
                MovementPredictor.method_1899(mc.thePlayer)
                    .addVector(var11.getFrontOffsetX() * 0.6, var11.getFrontOffsetY() * 0.6, var11.getFrontOffsetZ() * 0.6)
            );
            boolean var3 = method_1763(new BlockPos(mc.thePlayer).add(0, -1, 0));
            boolean var13 = method_1763(var12.add(0, -1, 0));
            if (var3 && var13) {
                GodBridgeTechnique var9;
                boolean var10;
                if (this.flag2) {
                    var10 = false;
                    var9 = this;
                } else {
                    var10 = true;
                    var9 = this;
                }

                var9.flag2 = var10;
            }
        }

        return new Rotation(var1 + (this.flag2 ? 45 : -45), 75.7F);
    }

    @Override
    public PredictedPlacement method_1762(PlacementTarget var1, Rotation var2) {
        if (!this.method_74()) {
            return PredictedPlacement.INSTANCE;
        } else {
            SimulatedTick var7;
            if (!(var7 = SimulationManager.INSTANCE.method_343().method_294(1)).flag()) {
                return PredictedPlacement.INSTANCE;
            } else {
                Vec3 var8 = var7.vec31().add(0.0, mc.thePlayer.getEyeHeight(), 0.0);
                MovingObjectPosition var12 = BlockRaycast.method_1997(BlockRaycast.method_1999(), HitboxMode.OUTLINE, false, var8, var2.method_1049());
                if (var1 == null) {
                    return PredictedPlacement.INSTANCE;
                } else {
                    boolean var11 = var1.method_51(var12);
                    boolean var13 = Scaffold.INSTANCE.method_1666(var12);
                    if (var11 && var13) {
                        return PredictedPlacement.INSTANCE;
                    } else {
                        GodBridgeTechnique.SneakAction var4;
                        if (Scaffold.INSTANCE.method_1663() < this.forceSneakBelowCount.method_149()) {
                            var4 = GodBridgeTechnique.SneakAction.SNEAK;
                        } else {
                            var4 = method_1764(this.modes.getValue());
                        }

                        GodBridgeTechnique var6;
                        GodBridgeTechnique.SneakAction var14;
                        if (var4 == GodBridgeTechnique.SneakAction.JUMP && this.method_1759()) {
                            EnumSet var9;
                            (var9 = EnumSet.copyOf(this.modes.getValue())).remove(GodBridgeTechnique.SneakAction.JUMP);
                            GodBridgeTechnique.SneakAction var10 = method_1764(var9);
                            GodBridgeTechnique.SneakAction var5;
                            if (var10 != null) {
                                var5 = var10;
                            } else {
                                var5 = GodBridgeTechnique.SneakAction.SNEAK;
                            }

                            var14 = var5;
                            var6 = this;
                        } else {
                            var14 = var4;
                            var6 = this;
                        }

                        return var6.method_1756(var14);
                    }
                }
            }
        }
    }

    private static boolean method_1763(BlockPos var0) {
        IBlockState var2;
        return (var2 = BlockPlacer.method_23(var0)) == null || var2.getBlock().getMaterial() == Material.air;
    }

    private static GodBridgeTechnique.SneakAction method_1764(EnumSet<GodBridgeTechnique.SneakAction> var0) {
        if (var0.isEmpty()) {
            return null;
        } else {
            ArrayList var2 = new ArrayList(var0);
            return (GodBridgeTechnique.SneakAction)var2.get(ThreadLocalRandom.current().nextInt(var2.size()));
        }
    }

    public static enum SneakAction implements NamedEnum {
        JUMP("Jump"),
        SNEAK("Sneak"),
        STOP_INPUT("Stop input"),
        BACKWARDS("Backwards");

        private final String string;

        @Override
        public String getDisplayName() {
            return this.string;
        }

        private SneakAction(String var3) {
            this.string = var3;
        }
    }
}
