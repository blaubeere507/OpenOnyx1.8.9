package client.onyx.module.render;

import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.render.world.BoxRenderer;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.ui.theme.color.ColorUtils;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public final class ContainerESP extends Module {
    public final BooleanSetting chests = new BooleanSetting("Chests", true);
    public final BooleanSetting enderChests = new BooleanSetting("Ender chests", false);
    public final BooleanSetting hoppers = new BooleanSetting("Hoppers", false);
    public final BooleanSetting furnaces = new BooleanSetting("Furnaces", false);
    public final BooleanSetting fill = new BooleanSetting("Fill", true);
    public final BooleanSetting throughWalls = new BooleanSetting("Through walls", true);
    public final NumberSetting lineWidth = new NumberSetting("Line width", 1.5, 0.5, 5.0, 0.5).method_147(" px");

    public void method_1184(float var1) {
        var var2 = this;
        if (this.isToggled() && mc.theWorld != null) {
            ArrayList var4 = new ArrayList<>(mc.theWorld.loadedTileEntityList);
            boolean var3 = false;
            Iterator var18;
            Iterator var6 = var18 = var4.iterator();

            while (var6.hasNext()) {
                TileEntity var16 = (TileEntity)var18.next();
                int var5;
                if ((var5 = var2.method_1185(var16)) == 0) {
                    var6 = var18;
                } else {
                    if (!var3) {
                        if (!BoxRenderer.method_519(var1, var2.throughWalls.method_146())) {
                            return;
                        }

                        var3 = true;
                    }

                    BlockPos var17;
                    AxisAlignedBB var10000 = AxisAlignedBB.fromBounds(
                        (var17 = var16.getPos()).getX() - 0.003,
                        var17.getY() - 0.003,
                        var17.getZ() - 0.003,
                        var17.getX() + 1.003,
                        var17.getY() + 1.003,
                        var17.getZ() + 1.003
                    );
                    int var10001 = ColorUtils.method_209(var5, 0.95F);
                    float var10002 = var2.lineWidth.method_151();
                    AxisAlignedBB var11;
                    int var12;
                    float var13;
                    int var14;
                    float var15;
                    if (!var2.fill.method_146()) {
                        float var9 = var10002;
                        int var8 = var10001;
                        AxisAlignedBB var7 = var10000;
                        var15 = 0.0F;
                        var14 = var5;
                        var13 = var9;
                        var12 = var8;
                        var11 = var7;
                    } else {
                        var15 = 0.14F;
                        var14 = var5;
                        var13 = var10002;
                        var12 = var10001;
                        var11 = var10000;
                    }

                    BoxRenderer.method_518(var11, var12, var13, ColorUtils.method_209(var14, var15));
                    var6 = var18;
                }
            }

            if (var3) {
                BoxRenderer.method_521();
            }
        }
    }

    private int method_1185(TileEntity var1) {
        if (var1 instanceof TileEntityChest && this.chests.method_146()) {
            return -19673;
        } else if (var1 instanceof TileEntityEnderChest && this.enderChests.method_146()) {
            return -7055617;
        } else if (var1 instanceof TileEntityHopper && this.hoppers.method_146()) {
            return -8947070;
        } else {
            return var1 instanceof TileEntityFurnace && this.furnaces.method_146() ? -3092272 : 0;
        }
    }

    public ContainerESP() {
        super("ContainerESP", "Highlights storage blocks", Category.RENDER);
    }
}
