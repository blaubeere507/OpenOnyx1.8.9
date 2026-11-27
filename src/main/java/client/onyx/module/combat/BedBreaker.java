package client.onyx.module.combat;

import client.onyx.Onyx;
import client.onyx.event.impl.PlayerUpdateEvent;
import client.onyx.event.impl.WorldLoadEvent;
import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.module.combat.bedwars.BedWarsObjectives;
import client.onyx.rotation.HitVecUtil;
import client.onyx.rotation.MoveFix;
import client.onyx.rotation.MoveFixMode;
import client.onyx.rotation.RotationUtil;
import client.onyx.rotation.YawPitch;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.EnumSetting;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.util.Timer;
import client.onyx.util.player.BlockPlacer;
import client.onyx.util.player.SwingMode;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockBed.EnumPartType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

public class BedBreaker extends Module {
    private static final long LONG_101 = 10L;
    private static final long LONG_4001 = 400L;
    public NumberSetting range = new NumberSetting("Range", 4.5, 1.0, 6.0, 0.05).method_147("m");
    public EnumSetting<MoveFixMode> moveFix = new EnumSetting<>("Move fix", MoveFixMode.SILENT)
        .describe("Keeps movement consistent with the rotation the server sees");
    public EnumSetting<SwingMode> swing = new EnumSetting<>("Swing", SwingMode.DO_NOT_HIDE).describe("How the mining swing is shown");
    public BooleanSetting keepRotation = new BooleanSetting("Keep rotation", false)
        .describe("Holds the aim on the block the whole time instead of a brief flick per block");
    public BooleanSetting ignoreOwnBed = new BooleanSetting("Ignore own bed", true)
        .describe("Skips your team's bed, found from the BedWars start message and base teleport");
    public BooleanSetting prioritizeKillAura = new BooleanSetting("Prioritize KillAura", false)
        .describe("Pauses bed breaking while KillAura has a target so attacks take priority");
    private int intValue = -1;
    private final Timer timer = new Timer();
    private final Timer timer2 = new Timer();
    private boolean flag;
    private BlockPos blockPos;
    private BlockPos blockPos2;
    private BlockPos blockPos3;

    private EnumFacing method_1530(BlockPos var1, YawPitch var2) {
        Vec3 var3 = HitVecUtil.method_2133();
        Vec3 var7 = HitVecUtil.method_2132(var2.floatValue(), var2.floatValue2());
        double var4 = this.range.method_151() + 1.0;
        Vec3 var8 = var3.addVector(var7.xCoord * var4, var7.yCoord * var4, var7.zCoord * var4);
        MovingObjectPosition var9;
        return (var9 = mc.theWorld.rayTraceBlocks(var3, var8, false, false, false)) != null
                && var9.typeOfHit == MovingObjectType.BLOCK
                && var1.equals(var9.getBlockPos())
                && var9.sideHit != null
            ? var9.sideHit
            : this.method_1539(var1);
    }

    private BlockPos method_1531(BlockPos var1) {
        var var2 = this;
        Vec3 var17 = HitVecUtil.method_2133();
        boolean var18 = false;
        BlockPos var4 = null;
        double var5 = -1.0;
        double var7 = Double.MAX_VALUE;
        boolean var9 = false;
        BlockPos[] var10;
        int var11 = (var10 = this.method_1536(var1)).length;
        int var12 = 0;

        for (int var24 = 0; var24 < var11; var24 = ++var12) {
            BlockPos var13 = var10[var12];
            EnumFacing[] var14;
            int var15 = (var14 = new EnumFacing[]{EnumFacing.UP, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST}).length;
            int var16 = 0;

            for (int var23 = 0; var23 < var15; var23 = ++var16) {
                EnumFacing var25 = var14[var16];
                BlockPos var26 = var13.offset(var25);
                Block var3 = mc.theWorld.getBlockState(var26).getBlock();
                if (var2.method_1542(var26, var3)) {
                    var18 = true;
                } else {
                    double var19;
                    if (!(var3 instanceof BlockBed) && var2.method_1540(var26) && !((var19 = var2.method_1534(var26, var3)) <= 0.0)) {
                        if (var26.equals(var2.blockPos3)) {
                            var9 = true;
                        }

                        double var21 = var17.squareDistanceTo(HitVecUtil.method_2135(var17, var2.method_1541(var26)));
                        if (var19 > var5 || var19 == var5 && var21 < var7) {
                            var5 = var19;
                            var7 = var21;
                            var4 = var26;
                        }
                    }
                }
            }
        }

        if (var18) {
            return var1;
        } else {
            return var9 ? var2.blockPos3 : var4;
        }
    }

    private float method_1532(ItemStack var1, Block var2) {
        if (var1 == null) {
            return 1.0F;
        } else {
            float var4;
            if (!var1.canHarvestBlock(var2) && var1.getItem() instanceof ItemPickaxe) {
                var4 = 1.0F;
            } else {
                var4 = var1.getStrVsBlock(var2);
            }

            float var3 = var4;
            int var5;
            if (var1.getItem() instanceof ItemTool && var4 > 1.0F && (var5 = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, var1)) > 0) {
                var3 = var4 + (var5 * var5 + 1);
            }

            return var3;
        }
    }

    @EventHandler
    public void onPlayerUpdate(PlayerUpdateEvent var1) {
        if (mc.thePlayer != null && mc.theWorld != null) {
            if (this.prioritizeKillAura.method_146() && Onyx.moduleManager.killAura.isToggled()) {
                if (Onyx.moduleManager.killAura.getEntityLivingBase() != null) {
                    this.timer2.method_2017();
                }

                if (!this.timer2.method_2018(400L)) {
                    this.method_1538();
                    return;
                }
            }

            BlockPos var4;
            if ((var4 = this.method_1543()) == null) {
                this.method_1538();
            } else {
                BlockPos var3;
                if ((var3 = this.method_1531(var4)) == null) {
                    this.method_1538();
                } else {
                    this.blockPos = var4;
                    YawPitch var5 = RotationUtil.method_2158(this.method_1544(var3), MoveFix.method_2145());
                    this.method_1535(var3, var5);
                    EnumFacing var6 = this.method_1530(var3, var5);
                    this.method_1533(var3);
                    mc.playerController.onPlayerDamageBlock(var3, var6);
                    this.swing.getValue().method_14();
                    this.blockPos2 = var3;
                    this.blockPos3 = var3;
                }
            }
        }
    }

    private void method_1533(BlockPos var1) {
        var var5 = this;
        Block var9 = mc.theWorld.getBlockState(var1).getBlock();
        int var2 = mc.thePlayer.inventory.currentItem;
        int var3 = mc.thePlayer.inventory.currentItem;
        float var4 = this.method_1532(mc.thePlayer.inventory.getStackInSlot(var2), var9);
        int var8 = 0;

        for (int var7 = 0; var7 < 9; var7 = ++var8) {
            float var6;
            if ((var6 = var5.method_1532(mc.thePlayer.inventory.getStackInSlot(var8), var9)) > var4) {
                var4 = var6;
                var3 = var8;
            }
        }

        if (var3 != var2) {
            if (var5.intValue == -1) {
                var5.intValue = var2;
            }

            mc.thePlayer.inventory.currentItem = var3;
        }
    }

    private double method_1534(BlockPos var1, Block var2) {
        float var4;
        if ((var4 = var2.getBlockHardness(mc.theWorld, var1)) < 0.0F) {
            return -1.0;
        } else {
            return var4 == 0.0F ? Double.MAX_VALUE : this.method_1537(var2) / var4;
        }
    }

    private void method_1535(BlockPos var1, YawPitch var2) {
        if (this.keepRotation.method_146()) {
            MoveFix.method_2150(var2, this.moveFix.getValue());
        } else if (!var1.equals(this.blockPos2)) {
            MoveFix.method_2150(var2, this.moveFix.getValue());
            this.timer.method_2017();
            this.flag = true;
        } else {
            if (this.flag && this.timer.method_2018(10L)) {
                MoveFix.method_2147();
                this.flag = false;
            }
        }
    }

    public BedBreaker() {
        super("BedBreaker", "Breaks nearby beds with the best tool", Category.COMBAT);
    }

    @Override
    public void onDisable() {
        this.method_1538();
    }

    private BlockPos[] method_1536(BlockPos var1) {
        IBlockState var7;
        if (!((var7 = mc.theWorld.getBlockState(var1)).getBlock() instanceof BlockBed)) {
            return new BlockPos[]{var1};
        } else {
            EnumPartType var3 = var7.getValue(BlockBed.PART);
            EnumFacing var8 = var7.getValue(BlockBed.FACING);
            BlockPos var5;
            EnumFacing var6;
            if (var3 != EnumPartType.HEAD) {
                var6 = var8;
                var5 = var1;
            } else {
                var6 = var8.getOpposite();
                var5 = var1;
            }

            BlockPos var9 = var5.offset(var6);
            return new BlockPos[]{var1, var9};
        }
    }

    public BlockPos getBlockPos3() {
        return this.blockPos3;
    }

    private float method_1537(Block var1) {
        var var2 = this;
        float var5 = 1.0F;
        int var3 = 0;

        for (int var4 = 0; var4 < 9; var4 = var3) {
            ItemStack var10002 = mc.thePlayer.inventory.getStackInSlot(var3);
            var3++;
            var5 = Math.max(var5, var2.method_1532(var10002, var1));
        }

        return var5;
    }

    private void method_1538() {
        if (this.blockPos3 != null) {
            mc.playerController.resetBlockRemoving();
            this.blockPos3 = null;
        }

        if (this.intValue != -1) {
            if (mc.thePlayer != null) {
                mc.thePlayer.inventory.currentItem = this.intValue;
                if (mc.playerController != null) {
                    mc.playerController.syncCurrentPlayItem();
                }
            }

            this.intValue = -1;
        }

        this.blockPos2 = null;
        this.blockPos = null;
        this.flag = false;
        MoveFix.method_2147();
    }

    private EnumFacing method_1539(BlockPos var1) {
        Vec3 var9 = HitVecUtil.method_2133();
        EnumFacing var3 = EnumFacing.UP;
        double var4 = Double.MAX_VALUE;
        EnumFacing[] var6;
        int var7 = (var6 = EnumFacing.values()).length;
        int var8 = 0;

        for (int var13 = 0; var13 < var7; var13 = ++var8) {
            EnumFacing var14 = var6[var8];
            if (BlockPlacer.method_19(var1.offset(var14))) {
                Vec3 var10 = new Vec3(
                    var1.getX() + 0.5 + var14.getFrontOffsetX() * 0.5,
                    var1.getY() + 0.5 + var14.getFrontOffsetY() * 0.5,
                    var1.getZ() + 0.5 + var14.getFrontOffsetZ() * 0.5
                );
                double var11;
                if ((var11 = var9.squareDistanceTo(var10)) < var4) {
                    var4 = var11;
                    var3 = var14;
                }
            }
        }

        return var3;
    }

    private boolean method_1540(BlockPos var1) {
        double var2 = this.range.method_151();
        Vec3 var6 = HitVecUtil.method_2133();
        return var6.squareDistanceTo(HitVecUtil.method_2135(var6, this.method_1541(var1))) <= var2 * var2;
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent var1) {
        this.method_1538();
    }

    private AxisAlignedBB method_1541(BlockPos var1) {
        return BlockPlacer.method_25(var1).offset(var1.getX(), var1.getY(), var1.getZ());
    }

    public BlockPos getBlockPos() {
        return this.blockPos;
    }

    private boolean method_1542(BlockPos var1, Block var2) {
        return BlockPlacer.method_19(var1) || var2.getMaterial().isReplaceable();
    }

    private BlockPos method_1543() {
        var var7 = this;
        double var1;
        double var10001 = var1 = this.range.method_151();
        double var3 = var10001 * var10001;
        Vec3 var5 = HitVecUtil.method_2133();
        if (this.blockPos != null
            && mc.theWorld.getBlockState(this.blockPos).getBlock() instanceof BlockBed
            && (!this.ignoreOwnBed.method_146() || !BedWarsObjectives.INSTANCE.method_1545(this.blockPos))
            && var5.squareDistanceTo(HitVecUtil.method_2135(var5, this.method_1541(this.blockPos))) <= var3) {
            return this.blockPos;
        } else {
            BlockPos var6 = new BlockPos(mc.thePlayer);
            int var18 = (int)Math.ceil(var1);
            BlockPos var8 = null;
            var1 = Double.MAX_VALUE;

            int var9;
            int var10;
            for (int var17 = var9 = -var18; var17 <= var18; var17 = ++var9) {
                int var11;
                for (int var16 = var10 = -var18; var16 <= var18; var16 = ++var10) {
                    for (int var15 = var11 = -var18; var15 <= var18; var15 = ++var11) {
                        BlockPos var12 = var6.add(var9, var10, var11);
                        double var13;
                        if (mc.theWorld.getBlockState(var12).getBlock() instanceof BlockBed
                            && (!var7.ignoreOwnBed.method_146() || !BedWarsObjectives.INSTANCE.method_1545(var12))
                            && !((var13 = var5.squareDistanceTo(HitVecUtil.method_2135(var5, var7.method_1541(var12)))) > var3)
                            && var13 < var1) {
                            var1 = var13;
                            var8 = var12;
                        }
                    }
                }
            }

            return var8;
        }
    }

    private Vec3 method_1544(BlockPos var1) {
        return HitVecUtil.method_2135(HitVecUtil.method_2133(), this.method_1541(var1));
    }
}
