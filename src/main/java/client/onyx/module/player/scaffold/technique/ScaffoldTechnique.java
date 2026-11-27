package client.onyx.module.player.scaffold.technique;

import client.onyx.module.player.scaffold.ScaffoldRotationSettings;
import client.onyx.placement.PlacementPlan;
import client.onyx.placement.PlacementTarget;
import client.onyx.rotation.Rotation;
import client.onyx.rotation.RotationRequest;
import client.onyx.setting.Mode;
import client.onyx.util.IMinecraft;
import client.onyx.util.geometry.Ray;
import client.onyx.util.world.BlockRaycast;
import java.util.Comparator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public abstract class ScaffoldTechnique extends Mode implements IMinecraft {
    public abstract PlacementTarget method_1727(Vec3 var1, boolean var2, Ray var3, ItemStack var4);

    public Comparator<BlockPos> method_1743(Vec3 var1, Ray var2) {
        return var2 != null ? PlacementPlan.method_29(var2) : PlacementPlan.method_30(var1);
    }

    public Rotation method_1735(PlacementTarget var1) {
        return var1 == null ? null : var1.rotation();
    }

    public MovingObjectPosition method_1728(PlacementTarget var1, Rotation var2) {
        return BlockRaycast.method_1995(var2);
    }

    public ScaffoldTechnique(String var1) {
        super(var1);
    }

    public RotationRequest method_1725(Rotation var1, ScaffoldRotationSettings var2, boolean var3) {
        return var2.method_1033(var1, var3);
    }
}
