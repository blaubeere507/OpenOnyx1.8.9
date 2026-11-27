package client.onyx.module.player.scaffold.feature;

import client.onyx.module.player.scaffold.BlockSlotPicker;
import client.onyx.setting.Setting;
import client.onyx.setting.SettingSection;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.util.IMinecraft;
import client.onyx.util.geometry.Ray;
import client.onyx.util.math.VecUtil;
import client.onyx.util.player.MovementPredictor;
import client.onyx.util.world.CollisionUtil;
import java.util.ArrayDeque;
import net.minecraft.util.Vec3;

public class PredictionSettings extends SettingSection implements IMinecraft {
    public static final PredictionSettings predictionSettings = new PredictionSettings();
    private static final int INT_41 = 4;
    private final transient ArrayDeque<Vec3> arrayDeque = new ArrayDeque<>(5);
    public NumberSetting bootstrapBackoff = new NumberSetting("Bootstrap backoff", 0.2, 0.0, 0.4, 0.01);
    public NumberSetting predictionCutoffDistance = new NumberSetting("Prediction cutoff distance", 0.05, 0.0, 0.3, 0.01);
    public NumberSetting warmupPlacements = new NumberSetting("Warmup placements", 2.0, 0.0, 4.0, 1.0);

    @Override
    public void method_64() {
        this.method_1783();
    }

    private Vec3 method_1779(Vec3 var1, Vec3 var2) {
        return this.bootstrapBackoff.method_151() <= 0.0F ? var1 : var1.subtract(VecUtil.method_1950(var2, this.bootstrapBackoff.method_151()));
    }

    public Vec3 method_1780(Ray var1) {
        if (var1 != null && this.method_61()) {
            if (MovementPredictor.method_1911(mc.thePlayer, this.predictionCutoffDistance.method_151())) {
                return null;
            } else {
                Vec3 var5;
                if ((var5 = this.method_1784(var1)) == null) {
                    return null;
                } else {
                    Vec3 var6 = MovementPredictor.method_1899(mc.thePlayer);
                    Vec3 var7 = var5.subtract(var6);
                    Vec3 var8 = this.method_1779(var5, var7);
                    Vec3 var4;
                    if ((var4 = this.method_1782()) == null) {
                        BlockSlotPicker.SlotChoice var10;
                        return (var10 = BlockSlotPicker.getSlotChoice()) != null ? var8.add(var10.doubleValue(), 0.0, var10.doubleValue2()) : var8;
                    } else {
                        float var2 = (float)Math.atan2(var1.vec32().zCoord, var1.vec32().xCoord);
                        Vec3 var9 = var5.add(var4.rotateYaw(-var2));
                        return var8.lerp(var9, this.method_1785());
                    }
                }
            }
        } else {
            return null;
        }
    }

    public void method_1781(Ray var1, Vec3 var2) {
        if (var1 != null && this.method_61()) {
            if (var2 != null) {
                float var4 = (float)Math.atan2(var1.vec32().zCoord, var1.vec32().xCoord);
                Vec3 var5 = MovementPredictor.method_1899(mc.thePlayer).subtract(var2).rotateYaw(var4);
                this.arrayDeque.addLast(var5);
                if (this.arrayDeque.size() > 4) {
                    this.arrayDeque.removeFirst();
                }
            }
        }
    }

    public Vec3 method_1782() {
        return this.arrayDeque.isEmpty() ? null : VecUtil.method_1949(this.arrayDeque);
    }

    public void method_1783() {
        this.arrayDeque.clear();
    }

    public Vec3 method_1784(Ray var1) {
        Vec3 var10000 = var1.method_2044(MovementPredictor.method_1899(mc.thePlayer)).add(0.0, -0.1, 0.0);
        Vec3 var2;
        return (var2 = CollisionUtil.method_1925(var10000, var10000.add(VecUtil.method_1950(var1.vec32(), 3.0)))) == null
            ? null
            : VecUtil.method_1942(var2, mc.thePlayer.posY);
    }

    private PredictionSettings() {
        super("Prediction", false);
        this.visibleWhen(Setting.booleanSupplier);
    }

    private double method_1785() {
        return this.warmupPlacements.method_149() <= 0 ? 1.0 : Math.clamp((double)this.arrayDeque.size() / this.warmupPlacements.method_149(), 0.0, 1.0);
    }
}
