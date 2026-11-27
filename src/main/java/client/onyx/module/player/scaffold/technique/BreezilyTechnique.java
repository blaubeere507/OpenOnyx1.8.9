package client.onyx.module.player.scaffold.technique;

import client.onyx.event.impl.MovementInputEvent;
import client.onyx.module.player.scaffold.Scaffold;
import client.onyx.placement.AimPointSelection;
import client.onyx.placement.CenterAimPoint;
import client.onyx.placement.EyePosition;
import client.onyx.placement.PlacementPlan;
import client.onyx.placement.PlacementPlanner;
import client.onyx.placement.PlacementSearch;
import client.onyx.placement.PlacementTarget;
import client.onyx.placement.SearchPattern;
import client.onyx.rotation.Rotation;
import client.onyx.setting.impl.RangeSetting;
import client.onyx.util.geometry.Ray;
import client.onyx.util.math.VecUtil;
import client.onyx.util.player.BlockPlacer;
import client.onyx.util.player.MovementInputState;
import client.onyx.util.player.MovementPredictor;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public class BreezilyTechnique extends ScaffoldTechnique {
    public static final BreezilyTechnique breezilyTechnique = new BreezilyTechnique();
    public RangeSetting rangeSetting = RangeSetting.method_104("Edge distance", 0.45, 0.5, 0.25, 0.5).method_94(" blocks");
    private transient double doubleValue = 0.45;
    private transient long longValue;
    private transient float floatValue;

    private BreezilyTechnique() {
        super("Breezily");
    }

    private Rotation method_1737(float var1) {
        return new Rotation(var1, 80.0F);
    }

    private static boolean method_1738(BlockPos var0) {
        IBlockState var2;
        return (var2 = BlockPlacer.method_23(var0)) == null || var2.getBlock().getMaterial() == Material.air;
    }

    @EventHandler(
        priority = -50
    )
    private void onMovementInput(MovementInputEvent var1) {
        float var10; // declared by DeclareMissing
        if (mc.thePlayer != null) {
            if (var1.getMovementInputState().flag2() && !mc.thePlayer.isSneaking()) {
                if (method_1738(new BlockPos(mc.thePlayer).add(0, -1, 0))) {
                    this.longValue = System.currentTimeMillis();
                } else if (System.currentTimeMillis() - this.longValue > 500L) {
                    return;
                }

                BreezilyTechnique var9;
                label66: {
                    double var2 = mc.thePlayer.posX - Math.floor(mc.thePlayer.posX);
                    double var4 = mc.thePlayer.posZ - Math.floor(mc.thePlayer.posZ);
                    double var6 = 1.0 - this.doubleValue;
                    var10 = 0.0F;
                    switch (EnumFacing.fromAngle(mc.thePlayer.rotationYaw)) {
                        case SOUTH:
                            if (var2 > var6) {
                                var10 = 1.0F;
                            }

                            if (var2 < this.doubleValue) {
                                var10 = -1.0F;
                                var9 = this;
                                break label66;
                            }
                            break;
                        case NORTH:
                            if (var2 > var6) {
                                var10 = -1.0F;
                            }

                            if (var2 < this.doubleValue) {
                                var10 = 1.0F;
                                var9 = this;
                                break label66;
                            }
                            break;
                        case EAST:
                            if (var4 > var6) {
                                var10 = -1.0F;
                            }

                            if (var4 < this.doubleValue) {
                                var10 = 1.0F;
                                var9 = this;
                                break label66;
                            }
                            break;
                        case WEST:
                            if (var4 > var6) {
                                var10 = 1.0F;
                            }

                            if (var4 < this.doubleValue) {
                                var10 = -1.0F;
                                var9 = this;
                                break label66;
                            }
                    }

                    var9 = this;
                }

                if (var9.floatValue != var10 && var10 != 0.0F) {
                    this.floatValue = var10;
                    this.doubleValue = this.rangeSetting.method_105();
                }

                MovementInputState var10001;
                boolean var10003 = var1.getMovementInputState().flag2();
                boolean var10004 = var1.getMovementInputState().flag4();
                boolean var10005;
                BreezilyTechnique var10006;
                if (this.floatValue == -1.0F) {
                    var10005 = true;
                    var10006 = this;
                } else {
                    var10005 = false;
                    var10006 = this;
                }

                var10001 = new MovementInputState(var10003, var10004, var10005, var10006.floatValue == 1.0F);
                var1.method_2621(var10001);
            }
        }
    }

    private Rotation method_1739(float var1) {
        return new Rotation(var1, 75.6F);
    }

    private Rotation method_1740(PlacementTarget var1) {
        float var2 = (float)(Math.floor(var1.rotation().getYaw() / 90.0F) * 90.0);
        return new Rotation(var2 + 45.0F, 75.0F);
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

    @Override
    public Rotation method_1735(PlacementTarget var1) {
        if (Scaffold.INSTANCE.getMovementInputState().equals(MovementInputState.INSTANCE8)) {
            return var1 == null ? null : this.method_1740(var1);
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

            return !var3 ? this.method_1739(var6) : this.method_1737(var6);
        }
    }
}
