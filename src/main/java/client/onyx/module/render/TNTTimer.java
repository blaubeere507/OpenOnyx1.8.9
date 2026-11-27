package client.onyx.module.render;

import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.render.ui.UiRenderer;
import client.onyx.render.world.WorldProjection;
import client.onyx.setting.impl.ColorSetting;
import client.onyx.ui.theme.Typography;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.util.Vec3;

public final class TNTTimer extends Module {
    public final ColorSetting color = new ColorSetting("Color", -41892);

    public void method_1070(UiRenderer var1, float var2, float var3, float var4) {
        var var6 = this;
        if (this.isToggled() && mc.theWorld != null && WorldProjection.isFlag()) {
            Iterator var5 = mc.theWorld.loadedEntityList.iterator();

            label24:
            while (true) {
                Iterator var9 = var5;

                while (var9.hasNext()) {
                    Entity var10;
                    if (!((var10 = (Entity)var5.next()) instanceof EntityTNTPrimed)) {
                        continue label24;
                    }

                    EntityTNTPrimed var11 = (EntityTNTPrimed)var10;
                    float[] var7;
                    if ((
                            var7 = WorldProjection.method_528(
                                new Vec3(
                                    var11.lastTickPosX + (var11.posX - var11.lastTickPosX) * var4,
                                    var11.lastTickPosY + (var11.posY - var11.lastTickPosY) * var4 + 1.25,
                                    var11.lastTickPosZ + (var11.posZ - var11.lastTickPosZ) * var4
                                ),
                                var2,
                                var3
                            )
                        )
                        == null) {
                        var9 = var5;
                    } else {
                        String var12 = String.format("%.1fs", Math.max(0.0F, (var11.fuse - var4) / 20.0F));
                        float var8 = var1.method_442(Typography.fontStyle11, var12) + 8.0F;
                        var1.method_479(var7[0] - var8 / 2.0F, var7[1] - 7.0F, var8, 14.0F, 8.0F, -1206643431);
                        var1.method_484(Typography.fontStyle11, var12, var7[0], var7[1], var6.color.method_118());
                        var9 = var5;
                    }
                }

                return;
            }
        }
    }

    public TNTTimer() {
        super("TNTTimer", "Shows time remaining before TNT explodes", Category.RENDER);
    }
}
