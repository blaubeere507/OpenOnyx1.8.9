package client.onyx.module.render;

import client.onyx.event.impl.AttackEvent;
import client.onyx.event.impl.PostPlayerUpdateEvent;
import client.onyx.event.impl.WorldLoadEvent;
import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.render.world.Particle;
import client.onyx.render.world.ParticleSystem;
import client.onyx.setting.NamedEnum;
import client.onyx.setting.impl.BooleanSetting;
import client.onyx.setting.impl.EnumSetSetting;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.ui.theme.Theme;
import client.onyx.ui.theme.color.ColorUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public final class Particles extends Module {
    private static final ResourceLocation resourceLocation = new ResourceLocation("onyx", "textures/particle/firefly.png");
    public final EnumSetSetting<Particles.SpawnMode> spawnAt = new EnumSetSetting<>(
        "Spawn at", Particles.SpawnMode.class, Particles.SpawnMode.ATTACK, Particles.SpawnMode.WALKING
    );
    public final NumberSetting particlesOnHit = new NumberSetting("Particles on hit", 3.0, 1.0, 25.0, 1.0)
        .visibleWhen(this.spawnAt, var0 -> var0.contains(Particles.SpawnMode.ATTACK));
    public final NumberSetting particlesWhileWalking = new NumberSetting("Particles while walking", 2.0, 1.0, 15.0, 1.0)
        .visibleWhen(this.spawnAt, var0 -> var0.contains(Particles.SpawnMode.WALKING));
    public final BooleanSetting randomColor = new BooleanSetting("Random color", false);
    public final NumberSetting size = new NumberSetting("Size", 1.0, 0.5, 2.5, 0.05).method_147("x");
    public final NumberSetting duration = new NumberSetting("Duration", 1.0, 0.25, 3.0, 0.05).method_147("x");
    public final BooleanSetting physics = new BooleanSetting("Physics", true);
    public final NumberSetting physicsSpeed = new NumberSetting("Physics speed", 1.0, 0.25, 3.0, 0.05).method_147("x").visibleWhen(this.physics);
    private final List<ParticleSystem> list = new ArrayList<>();
    private final Random random = new Random();
    private int intValue;
    private int intValue2;

    private void method_1087() {
        this.list.removeIf(var0 -> !var0.isFlag());
    }

    private void method_1088() {
        Iterator var3;
        for (Iterator var2 = var3 = this.list.iterator(); var2.hasNext(); var2 = var3) {
            ((ParticleSystem)var3.next()).method_712();
        }

        this.list.clear();
        this.intValue = 0;
        this.intValue2 = 0;
    }

    private void method_1089(Vec3 var1, Vec3 var2, ParticleSystem.SpawnReason var3, int var4) {
        if (mc.theWorld != null) {
            this.list.add(new ParticleSystem(this, mc.theWorld, var3, var4, this.method_1092(), var1, var2));
        }
    }

    @EventHandler
    private void onAttack(AttackEvent var1) {
        var var8 = this;
        if (this.spawnAt.method_79(Particles.SpawnMode.ATTACK) && var1.getEntityPlayer() == mc.thePlayer) {
            Entity var7;
            if ((var7 = var1.getEntity()) != null) {
                int var2 = 0;

                for (int var5 = 0; var5 < var8.particlesOnHit.method_149(); var5 = var2) {
                    Vec3 var3 = new Vec3(var7.posX, var7.posY + var8.random.nextDouble() * var7.height, var7.posZ);
                    Vec3 var4 = new Vec3(var8.method_1091(-2.0, 2.0), 0.0, var8.method_1091(-2.0, 2.0));
                    ParticleSystem.SpawnReason var10003 = ParticleSystem.SpawnReason.ATTACK;
                    int var10004 = var8.intValue;
                    var2++;
                    var8.intValue++;
                    var8.method_1089(var3, var4, var10003, var10004);
                }
            }
        }
    }

    public Particles() {
        super("Particles", "Glowing fireflies on attacks and while walking", Category.RENDER);
    }

    public int method_1090(int var1, int var2) {
        return this.randomColor.method_146()
            ? var2
            : Particle.method_726(Theme.getPalette().intValue31(), Theme.getPalette().intValue27(), var1, System.currentTimeMillis());
    }

    @Override
    public void onEnable() {
        this.method_1088();
    }

    @EventHandler
    private void onWorldLoad(WorldLoadEvent var1) {
        this.method_1088();
    }

    @Override
    public void onDisable() {
        this.method_1088();
    }

    private double method_1091(double var1, double var3) {
        return var1 + this.random.nextDouble() * (var3 - var1);
    }

    private int method_1092() {
        return ColorUtils.method_202(this.random.nextFloat() * 360.0F, 0.75F + this.random.nextFloat() * 0.25F, 0.75F + this.random.nextFloat() * 0.25F, 255);
    }

    public void method_1093(float var1) {
        if (this.isToggled()) {
            this.method_1087();

            Iterator var12;
            for (Iterator var10 = var12 = this.list.iterator(); var10.hasNext(); var10 = var12) {
                ParticleSystem var3 = (ParticleSystem)var12.next();
                var3.method_711();
            }

            this.method_1087();
            if (!this.list.isEmpty()) {
                Entity var13;
                if ((var13 = mc.getRenderViewEntity()) != null) {
                    Vec3 var16 = new Vec3(
                        var13.prevPosX + (var13.posX - var13.prevPosX) * var1,
                        var13.prevPosY + (var13.posY - var13.prevPosY) * var1,
                        var13.prevPosZ + (var13.posZ - var13.prevPosZ) * var1
                    );
                    var1 = ActiveRenderInfo.getRotationX();
                    float var14 = ActiveRenderInfo.getRotationXZ();
                    float var4 = ActiveRenderInfo.getRotationZ();
                    float var5 = ActiveRenderInfo.getRotationYZ();
                    float var6 = ActiveRenderInfo.getRotationXY();
                    GlStateManager.pushMatrix();
                    GlStateManager.disableLighting();
                    GlStateManager.enableBlend();
                    GlStateManager.blendFunc(770, 1);
                    GlStateManager.depthMask(false);
                    GlStateManager.disableCull();
                    GlStateManager.disableAlpha();
                    GlStateManager.enableTexture2D();
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    mc.getTextureManager().bindTexture(resourceLocation);
                    GL11.glTexParameteri(3553, 10241, 9729);
                    GL11.glTexParameteri(3553, 10240, 9729);
                    Tessellator var7;
                    WorldRenderer var8 = (var7 = Tessellator.getInstance()).getWorldRenderer();
                    var8.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);

                    Iterator var9;
                    for (Iterator var11 = var9 = this.list.iterator(); var11.hasNext(); var11 = var9) {
                        ((ParticleSystem)var9.next()).method_719(var8, var16, var1, var14, var4, var5, var6);
                    }

                    var7.draw();
                    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                    GlStateManager.enableAlpha();
                    GlStateManager.enableCull();
                    GlStateManager.depthMask(true);
                    GlStateManager.disableBlend();
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    GlStateManager.popMatrix();
                }
            }
        }
    }

    @EventHandler
    private void onPostPlayerUpdate(PostPlayerUpdateEvent var1) {
        var var2 = this;
        this.method_1087();
        if (this.spawnAt.method_79(Particles.SpawnMode.WALKING) && mc.thePlayer != null && mc.theWorld != null) {
            if (mc.gameSettings.thirdPersonView != 0) {
                if (mc.thePlayer.prevPosX != mc.thePlayer.posX || mc.thePlayer.prevPosY != mc.thePlayer.posY || mc.thePlayer.prevPosZ != mc.thePlayer.posZ) {
                    int var7 = 0;

                    for (int var5 = 0; var5 < var2.particlesWhileWalking.method_149(); var5 = var7) {
                        Vec3 var6 = new Vec3(
                            mc.thePlayer.posX + var2.method_1091(-0.5, 0.5),
                            mc.thePlayer.posY + var2.random.nextDouble() * mc.thePlayer.height,
                            mc.thePlayer.posZ + var2.method_1091(-0.5, 0.5)
                        );
                        double var3 = 2.0 * (1.0 + var2.random.nextDouble());
                        Vec3 var8 = new Vec3(mc.thePlayer.motionX + var2.method_1091(-0.1, 0.1), 0.0, mc.thePlayer.motionZ + var2.method_1091(-0.1, 0.1))
                            .scale(var3);
                        ParticleSystem.SpawnReason var10003 = ParticleSystem.SpawnReason.WALKING;
                        int var10004 = var2.intValue2;
                        var7++;
                        var2.intValue2++;
                        var2.method_1089(var6, var8, var10003, var10004);
                    }
                }
            }
        }
    }

    public static enum SpawnMode implements NamedEnum {
        ATTACK("Attack"),
        WALKING("Walking");

        private final String string;

        @Override
        public String getDisplayName() {
            return this.string;
        }

        private SpawnMode(String var3) {
            this.string = var3;
        }
    }
}
