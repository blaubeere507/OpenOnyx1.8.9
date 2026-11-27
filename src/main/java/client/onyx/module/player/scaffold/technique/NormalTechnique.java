package client.onyx.module.player.scaffold.technique;

import client.onyx.event.impl.PostJumpEvent;
import client.onyx.module.player.scaffold.Scaffold;
import client.onyx.placement.AimPoint;
import client.onyx.placement.AimPointMode;
import client.onyx.placement.AimPointSelection;
import client.onyx.placement.CenterAimPoint;
import client.onyx.placement.EdgeAimPoint;
import client.onyx.placement.EyePosition;
import client.onyx.placement.NearestRotationAimPoint;
import client.onyx.placement.PlacementPlan;
import client.onyx.placement.PlacementPlanner;
import client.onyx.placement.PlacementSearch;
import client.onyx.placement.PlacementTarget;
import client.onyx.placement.RandomAimPoint;
import client.onyx.placement.ScoredPoint;
import client.onyx.placement.SearchPattern;
import client.onyx.placement.StabilizedAimPoint;
import client.onyx.rotation.Rotation;
import client.onyx.setting.impl.EnumSetting;
import client.onyx.util.geometry.Ray;
import client.onyx.util.math.VecUtil;
import client.onyx.util.player.MovementPredictor;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class NormalTechnique extends ScaffoldTechnique {
    public static final NormalTechnique normalTechnique = new NormalTechnique();
    public EnumSetting<AimPointMode> rotationMode = new EnumSetting<>("Rotation mode", AimPointMode.STABILIZED);
    public SneakSettings sneakSettings;
    public TellySettings tellySettings;
    public DownSettings downSettings;
    public StabilizeSettings stabilizeSettings;
    private transient double doubleValue;

    @Override
    public Rotation method_1735(PlacementTarget var1) {
        if (TellySettings.tellySettings.method_61() && TellySettings.tellySettings.method_1765()) {
            switch ((TellySettings.JumpAim)TellySettings.tellySettings.jumpAim.getValue()) {
                case REVERSE:
                    return new Rotation(
                        (float)(Math.rint(MovementPredictor.method_1910(mc.thePlayer).getYaw() / 45.0F) * 45.0),
                        mc.thePlayer.rotationPitch < 45.0F ? 45.0F : mc.thePlayer.rotationPitch
                    );
                case RESET:
                    return null;
                default:
                    throw new MatchException(null, null);
            }
        } else {
            return super.method_1735(var1);
        }
    }

    private AimPoint method_1741(Vec3 var1, boolean var2, Ray var3) {
        ScoredPoint var5 = new ScoredPoint(var1.add(0.0, EyePosition.method_37(var2), 0.0), this.doubleValue);
        switch ((AimPointMode)this.rotationMode.getValue()) {
            case CENTER:
                return CenterAimPoint.INSTANCE;
            case RANDOM:
                return RandomAimPoint.INSTANCE;
            case STABILIZED:
                return new EdgeAimPoint(var5, var3);
            case NEAREST_ROTATION:
                return new StabilizedAimPoint(var5);
            case EDGE_POINT:
                return new NearestRotationAimPoint(var5);
            default:
                throw new MatchException(null, null);
        }
    }

    @Override
    public MovingObjectPosition method_1728(PlacementTarget var1, Rotation var2) {
        if (var1 == null) {
            return null;
        } else {
            MovingObjectPosition var4;
            if ((var4 = super.method_1728(var1, var2)) != null && var1.method_51(var4)) {
                return var4;
            } else {
                return DownSettings.method_1770() ? var1.method_52() : null;
            }
        }
    }

    @EventHandler(
        priority = -50
    )
    private void onPostJump(PostJumpEvent var1) {
        this.doubleValue = ThreadLocalRandom.current().nextDouble(-0.01, 0.01);
    }

    private NormalTechnique() {
        super("Normal");
        this.sneakSettings = SneakSettings.sneakSettings;
        this.tellySettings = TellySettings.tellySettings;
        this.downSettings = DownSettings.downSettings;
        this.stabilizeSettings = StabilizeSettings.stabilizeSettings;
        this.doubleValue = ThreadLocalRandom.current().nextDouble(-0.02, 0.02);
    }

    @Override
    public PlacementTarget method_1727(Vec3 var1, boolean var2, Ray var3, ItemStack var4) {
        Comparator var5 = this.method_1743(var1, var3);
        List var6;
        NormalTechnique var7;
        if (DownSettings.method_1770()) {
            var6 = SearchPattern.DOWN.getList();
            var7 = this;
        } else {
            var6 = SearchPattern.NORMAL.getList();
            var7 = this;
        }

        AimPoint var9 = var7.method_1741(var1, var2, var3);
        PlacementPlan var10 = new PlacementPlan(
            new PlacementPlanner(var6, var5), new AimPointSelection(var9, DownSettings.method_1770()), var4, new EyePosition(var1, var2)
        );
        return PlacementSearch.method_42(Scaffold.INSTANCE.method_1675(VecUtil.method_1956(var1)), var10);
    }
}
