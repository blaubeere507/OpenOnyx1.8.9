package client.onyx.rotation;

import client.onyx.rotation.aim.AimModel;
import client.onyx.util.ConditionalAction;
import client.onyx.util.IMinecraft;
import client.onyx.util.player.MovementPredictor;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;

public class RotationRequest implements IMinecraft {
    private final Rotation rotation;
    private Entity entity;
    private final List<AimModel> list;
    private final int intValue;
    private final float floatValue;
    private final boolean flag;
    private final MovementCorrection movementCorrection;
    private final ConditionalAction conditionalAction;

    private Rotation method_1034(Rotation var1, Rotation var2) {
        var var7 = this;
        if (this.list.isEmpty()) {
            return var2;
        } else {
            Rotation var6 = var2;

            Iterator var3;
            for (Iterator var4 = var3 = this.list.iterator(); var4.hasNext(); var4 = var3) {
                var6 = ((AimModel)var3.next()).method_1059(var7, var1, var6);
            }

            return var6;
        }
    }

    public Rotation getRotation() {
        return this.rotation;
    }

    public void method_1035(Entity var1) {
        this.entity = var1;
    }

    public ConditionalAction getConditionalAction() {
        return this.conditionalAction;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public int getIntValue() {
        return this.intValue;
    }

    public MovementCorrection getMovementCorrection() {
        return this.movementCorrection;
    }

    public RotationRequest(Rotation var1, Entity var2, List<AimModel> var3, int var4, float var5, boolean var6, MovementCorrection var7, ConditionalAction var8) {
        this.rotation = var1;
        this.entity = var2;
        this.list = var3;
        this.intValue = var4;
        this.floatValue = var5;
        this.flag = var6;
        this.movementCorrection = var7;
        this.conditionalAction = var8;
    }

    public List<AimModel> getList() {
        return this.list;
    }

    public Rotation method_1036(Rotation var1, boolean var2) {
        if (var2) {
            this.entity = null;
            return this.method_1034(var1, MovementPredictor.method_1910(mc.thePlayer));
        } else {
            return this.method_1034(var1, this.rotation);
        }
    }

    public boolean isFlag() {
        return this.flag;
    }

    public float getFloatValue() {
        return this.floatValue;
    }
}
