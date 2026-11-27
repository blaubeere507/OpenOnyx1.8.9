package client.onyx.module.movement;

import client.onyx.Onyx;
import client.onyx.event.impl.PlayerUpdateEvent;
import client.onyx.event.impl.WorldLoadEvent;
import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.module.combat.KillAura;
import client.onyx.module.player.scaffold.Scaffold;
import client.onyx.rotation.MoveFix;
import client.onyx.rotation.MoveFixMode;
import client.onyx.rotation.YawPitch;
import client.onyx.setting.impl.BooleanSetting;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class NoSlow extends Module {
    public BooleanSetting pauseOnKillAura = new BooleanSetting("Pause on KillAura", true)
        .describe("Fully disables NoSlow while KillAura is enabled, so the two never trade rotations");
    private boolean flag;
    private boolean flag2;

    private void method_1516() {
        this.flag2 = false;
        if (this.flag) {
            MoveFix.method_2147();
            this.flag = false;
        }
    }

    private static NoSlow method_1517() {
        return Onyx.moduleManager == null ? null : Onyx.moduleManager.noSlow;
    }

    public static boolean method_1518() {
        NoSlow var0;
        return (var0 = method_1517()) != null && method_1522() && var0.flag && mc.thePlayer != null && var0.method_1524() && !mc.thePlayer.isRiding();
    }

    public static void method_1519() {
        NoSlow var0;
        if ((var0 = method_1517()) != null && method_1522() && var0.flag) {
            if (mc.thePlayer != null && mc.thePlayer.movementInput != null) {
                if (!mc.thePlayer.isCollidedHorizontally) {
                    if (!(mc.thePlayer.movementInput.moveForward <= 0.0F)) {
                        mc.thePlayer.setSprinting(true);
                    }
                }
            }
        }
    }

    public static void method_1520() {
        NoSlow var0;
        if ((var0 = method_1517()) != null && method_1522()) {
            if (mc.thePlayer != null && mc.thePlayer.movementInput != null) {
                if (method_1518() && method_1527()) {
                    mc.thePlayer.movementInput.moveForward *= 0.2F;
                    mc.thePlayer.movementInput.moveStrafe *= 0.2F;
                }

                if (var0.flag2) {
                    mc.thePlayer.movementInput.jump = false;
                }
            }
        }
    }

    public NoSlow() {
        super("NoSlow", "Stops KillAura's blocking from slowing you down", Category.MOVEMENT);
    }

    private boolean method_1521() {
        if (!this.pauseOnKillAura.method_146()) {
            return false;
        } else {
            KillAura var2;
            if (Onyx.moduleManager == null) {
                var2 = null;
            } else {
                var2 = Onyx.moduleManager.killAura;
            }

            return var2 != null && var2.isToggled();
        }
    }

    public static boolean method_1522() {
        NoSlow var0;
        return (var0 = method_1517()) != null && var0.isToggled() && !Scaffold.INSTANCE.isToggled() && !var0.method_1521();
    }

    public static boolean method_1523() {
        NoSlow var0;
        return (var0 = method_1517()) != null && var0.flag;
    }

    private boolean method_1524() {
        return mc.thePlayer.isUsingItem();
    }

    private static float method_1525() {
        GameSettings var0 = mc.gameSettings;
        float var1 = 180.0F;
        if (var0.keyBindBack.isKeyDown()) {
            var1 = 0.0F;
            if (var0.keyBindRight.isKeyDown()) {
                var1 -= 45.0F;
            }

            if (var0.keyBindLeft.isKeyDown()) {
                var1 += 45.0F;
            }
        } else if (var0.keyBindForward.isKeyDown()) {
            if (var0.keyBindRight.isKeyDown()) {
                var1 = 225.0F;
            }

            if (var0.keyBindLeft.isKeyDown()) {
                var1 -= 45.0F;
            }
        } else {
            if (var0.keyBindRight.isKeyDown()) {
                var1 = 270.0F;
            }

            if (var0.keyBindLeft.isKeyDown()) {
                var1 -= 90.0F;
            }
        }

        return (MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw) + var1 % 360.0F + 360.0F) % 360.0F;
    }

    private boolean method_1526() {
        ItemStack var2;
        return (var2 = mc.thePlayer.getHeldItem()) != null && var2.getItem() != Items.bow;
    }

    private static boolean method_1527() {
        GameSettings var0 = mc.gameSettings;
        return mc.gameSettings.keyBindRight.isKeyDown() || var0.keyBindLeft.isKeyDown() || var0.keyBindForward.isKeyDown() || var0.keyBindBack.isKeyDown();
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent var1) {
        this.method_1516();
    }

    private boolean method_1528() {
        KillAura var2 = Onyx.moduleManager.killAura;
        return Onyx.moduleManager.killAura.isToggled() && var2.getEntityLivingBase2() != null;
    }

    @EventHandler(
        priority = -50
    )
    public void onPlayerUpdate(PlayerUpdateEvent var1) {
        this.flag2 = false;
        if (mc.thePlayer == null || mc.theWorld == null || !method_1522()) {
            this.method_1516();
        } else if (this.method_1528()) {
            this.flag = false;
        } else if (this.method_1524() && this.method_1526()) {
            float var2 = method_1525() + 180.0F;
            if (!mc.gameSettings.keyBindJump.isKeyDown() || !mc.thePlayer.onGround) {
                var2 -= 45.0F;
                this.flag2 = true;
            }

            MoveFix.method_2150(new YawPitch(var2, mc.thePlayer.rotationPitch), MoveFixMode.SILENT);
            this.flag = true;
        } else {
            this.method_1516();
        }
    }

    public static float method_1529() {
        return method_1518() ? 0.1F : 0.8F;
    }

    @Override
    public void onDisable() {
        this.method_1516();
    }
}
