package client.onyx.module.player.scaffold.technique;

import client.onyx.module.player.scaffold.Scaffold;
import client.onyx.placement.AimPointSelection;
import client.onyx.placement.CenterAimPoint;
import client.onyx.placement.EyePosition;
import client.onyx.placement.PlacementPlan;
import client.onyx.placement.PlacementPlanner;
import client.onyx.placement.PlacementSearch;
import client.onyx.placement.PlacementTarget;
import client.onyx.rotation.Rotation;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.util.geometry.Ray;
import client.onyx.util.math.VecUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class ExpandTechnique extends ScaffoldTechnique {
    public static final ExpandTechnique expandTechnique = new ExpandTechnique();
    private static final float FLOAT_0_0174532921 = (float) (Math.PI / 180.0);
    public NumberSetting length = new NumberSetting("Length", 4.0, 1.0, 10.0, 1.0).method_147(" blocks");

    @Override
    public MovingObjectPosition method_1728(PlacementTarget var1, Rotation var2) {
        if (var1 == null) {
            return null;
        } else {
            MovingObjectPosition var4;
            return (var4 = super.method_1728(var1, var2)) != null && var1.method_51(var4) ? var4 : var1.method_52();
        }
    }

    private ExpandTechnique() {
        super("Expand");
    }

    @Override
    public Rotation method_1735(PlacementTarget var1) {
        return var1 == null ? null : Rotation.method_1058(VecUtil.method_1954(var1.blockPos2()), mc.thePlayer.getPositionEyes(1.0F));
    }

    @Override
    public PlacementTarget method_1727(Vec3 var1, boolean var2, Ray var3, ItemStack var4) {
        var var10 = this;
        PlacementPlan var8 = new PlacementPlan(
            PlacementPlanner.method_31(), new AimPointSelection(CenterAimPoint.INSTANCE, true), var4, new EyePosition(var1, var2)
        );
        int var9 = 0;

        for (int var5 = 0; var5 <= var10.length.method_149(); var5 = ++var9) {
            PlacementTarget var7;
            if ((var7 = PlacementSearch.method_42(Scaffold.INSTANCE.method_1675(var10.method_1742(var1, var9)), var8)) != null) {
                return var7;
            }
        }

        return null;
    }

    private BlockPos method_1742(Vec3 var1, int var2) {
        float var4 = mc.thePlayer.rotationYaw;
        return VecUtil.method_1956(var1)
            .add((int)(-((float)Math.sin(var4 * (float) (Math.PI / 180.0))) * var2), 0, (int)((float)Math.cos(var4 * (float) (Math.PI / 180.0)) * var2));
    }
}
