package client.onyx.module.render;

import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.NumberSetting;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;

public final class SeeInvisibles extends Module {
    public final BooleanSetting players = new BooleanSetting("Players", true).describe("Show invisible players");
    public final BooleanSetting mobs = new BooleanSetting("Mobs", true).describe("Show invisible hostile mobs");
    public final BooleanSetting animals = new BooleanSetting("Animals", false).describe("Show invisible passive mobs");
    public final NumberSetting opacity = new NumberSetting("Opacity", 60.0, 10.0, 100.0, 5.0)
        .method_147("%")
        .describe("How solid an invisible entity is drawn");

    public float method_1084() {
        return this.opacity.method_151() / 100.0F;
    }

    public boolean method_1085(EntityLivingBase var1) {
        if (!this.isToggled() || var1 == null || !var1.isInvisible()) {
            return false;
        } else if (var1 instanceof EntityPlayer) {
            return this.players.method_146();
        } else if (var1 instanceof IMob) {
            return this.mobs.method_146();
        } else {
            return var1 instanceof EntityAnimal ? this.animals.method_146() : this.animals.method_146();
        }
    }

    public SeeInvisibles() {
        super("SeeInvisibles", "Renders invisible entities", Category.RENDER);
    }
}
