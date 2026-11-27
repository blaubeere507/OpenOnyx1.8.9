package client.onyx.module;

import client.onyx.event.impl.TickEvent;
import client.onyx.module.combat.AntiBot;
import client.onyx.module.combat.BedBreaker;
import client.onyx.module.combat.BedDefender;
import client.onyx.module.combat.JumpReset;
import client.onyx.module.combat.KillAura;
import client.onyx.module.combat.velocity.Velocity;
import client.onyx.module.hud.CustomGui;
import client.onyx.module.hud.DiscordRPC;
import client.onyx.module.hud.Keybinds;
import client.onyx.module.hud.PotionHUD;
import client.onyx.module.hud.arraylist.ModuleList;
import client.onyx.module.hud.island.Watermark;
import client.onyx.module.hud.notification.Notifications;
import client.onyx.module.hud.targethud.TargetHUD;
import client.onyx.module.movement.AutoSprint;
import client.onyx.module.movement.NoSlow;
import client.onyx.module.movement.safewalk.SafeWalk;
import client.onyx.module.player.ChestStealer;
import client.onyx.module.player.Deposit;
import client.onyx.module.player.FastPlace;
import client.onyx.module.player.InventoryManager;
import client.onyx.module.player.NameChanger;
import client.onyx.module.player.scaffold.Scaffold;
import client.onyx.module.render.Ambience;
import client.onyx.module.render.Animations;
import client.onyx.module.render.Arrows;
import client.onyx.module.render.BedESP;
import client.onyx.module.render.BlockOverlay;
import client.onyx.module.render.Boxes;
import client.onyx.module.render.Camera;
import client.onyx.module.render.CapeChanger;
import client.onyx.module.render.Chams;
import client.onyx.module.render.ChinaHat;
import client.onyx.module.render.ClickGUI;
import client.onyx.module.render.ContainerESP;
import client.onyx.module.render.Crosshair;
import client.onyx.module.render.ESP;
import client.onyx.module.render.FogBlur;
import client.onyx.module.render.FogRemove;
import client.onyx.module.render.Freelook;
import client.onyx.module.render.Fullbright;
import client.onyx.module.render.GlowESP;
import client.onyx.module.render.Hand;
import client.onyx.module.render.Hurtcam;
import client.onyx.module.render.ItemPhysics;
import client.onyx.module.render.JumpCircles;
import client.onyx.module.render.NoRender;
import client.onyx.module.render.ParticleLimiter;
import client.onyx.module.render.Particles;
import client.onyx.module.render.SeeInvisibles;
import client.onyx.module.render.SkeletonESP;
import client.onyx.module.render.SkinChanger;
import client.onyx.module.render.Skybox;
import client.onyx.module.render.TNTTimer;
import client.onyx.module.render.TargetESP;
import client.onyx.module.render.Trails;
import client.onyx.module.render.Trajectories;
import client.onyx.module.render.Wings;
import client.onyx.module.render.Zoom;
import client.onyx.util.IMinecraft;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import meteordevelopment.orbit.EventHandler;

public class ModuleManager implements IMinecraft {
    public ClickGUI clickGUI;
    public CustomGui customGui;
    public Animations animations;
    public ESP esp;
    public GlowESP glowESP;
    public TargetESP targetESP;
    public Particles particles;
    public Fullbright fullbright;
    public Crosshair crosshair;
    public ContainerESP containerESP;
    public TNTTimer tntTimer;
    public NoRender noRender;
    public ParticleLimiter particleLimiter;
    public FogRemove fogRemove;
    public ItemPhysics itemPhysics;
    public Camera camera;
    public Zoom zoom;
    public Ambience ambience;
    public Skybox skybox;
    public FogBlur fogBlur;
    public Chams chams;
    public Boxes boxes;
    public BlockOverlay blockOverlay;
    public Wings wings;
    public ChinaHat chinaHat;
    public Trails trails;
    public JumpCircles jumpCircles;
    public Hand hand;
    public AutoSprint autoSprint;
    public SafeWalk safeWalk;
    public NoSlow noSlow;
    public KillAura killAura;
    public AntiBot antiBot;
    public BedBreaker bedBreaker;
    public BedDefender bedDefender;
    public Velocity velocity;
    public JumpReset jumpReset;
    public Scaffold scaffold;
    public NameChanger nameChanger;
    public SkinChanger skinChanger;
    public CapeChanger capeChanger;
    public FastPlace fastPlace;
    public Deposit deposit;
    public InventoryManager inventoryManager;
    public ChestStealer chestStealer;
    public TargetHUD targetHUD;
    public Keybinds keybinds;
    public ModuleList moduleList;
    public Watermark watermark;
    public Notifications notifications;
    public PotionHUD potionHUD;
    public Arrows arrows;
    public BedESP bedESP;
    public SkeletonESP skeletonESP;
    public Trajectories trajectories;
    public SeeInvisibles seeInvisibles;
    public Freelook freelook;
    public Hurtcam hurtcam;
    public DiscordRPC discordRPC;
    private final ArrayList<Module> arrayList;
    private final Set<Module> set;

    public ModuleManager() {
        var var1 = this;
        this.clickGUI = new ClickGUI();
        this.customGui = new CustomGui();
        this.animations = new Animations();
        this.esp = new ESP();
        this.glowESP = new GlowESP();
        this.targetESP = new TargetESP();
        this.particles = new Particles();
        this.fullbright = new Fullbright();
        this.crosshair = new Crosshair();
        this.containerESP = new ContainerESP();
        this.tntTimer = new TNTTimer();
        this.noRender = new NoRender();
        this.particleLimiter = new ParticleLimiter();
        this.fogRemove = new FogRemove();
        this.itemPhysics = new ItemPhysics();
        this.camera = new Camera();
        this.zoom = new Zoom();
        this.ambience = new Ambience();
        this.skybox = new Skybox();
        this.fogBlur = new FogBlur();
        this.chams = new Chams();
        this.boxes = new Boxes();
        this.blockOverlay = new BlockOverlay();
        this.wings = new Wings();
        this.chinaHat = new ChinaHat();
        this.trails = new Trails();
        this.jumpCircles = new JumpCircles();
        this.hand = new Hand();
        this.autoSprint = new AutoSprint();
        this.safeWalk = new SafeWalk();
        this.noSlow = new NoSlow();
        this.killAura = new KillAura();
        this.antiBot = new AntiBot();
        this.bedBreaker = new BedBreaker();
        this.bedDefender = new BedDefender();
        this.velocity = new Velocity();
        this.jumpReset = new JumpReset();
        this.scaffold = Scaffold.INSTANCE;
        this.nameChanger = new NameChanger();
        this.skinChanger = new SkinChanger();
        this.capeChanger = new CapeChanger();
        this.fastPlace = new FastPlace();
        this.deposit = Deposit.INSTANCE;
        this.inventoryManager = new InventoryManager();
        this.chestStealer = new ChestStealer();
        this.targetHUD = new TargetHUD();
        this.keybinds = new Keybinds();
        this.moduleList = new ModuleList();
        this.watermark = new Watermark();
        this.notifications = new Notifications();
        this.potionHUD = new PotionHUD();
        this.arrows = new Arrows();
        this.bedESP = new BedESP();
        this.skeletonESP = new SkeletonESP();
        this.trajectories = new Trajectories();
        this.seeInvisibles = new SeeInvisibles();
        this.freelook = new Freelook();
        this.hurtcam = new Hurtcam();
        this.discordRPC = new DiscordRPC();
        this.arrayList = new ArrayList<>();
        this.set = new HashSet<>();
        Field[] var4;
        int var2 = (var4 = this.getClass().getDeclaredFields()).length;
        int var3 = 0;

        for (int var5 = 0; var5 < var2; var5 = ++var3) {
            Field var7 = var4[var3];
            if (Module.class.isAssignableFrom(var7.getType())) {
                try {
                    Module var8 = (Module)var7.get(var1);
                    var8.initSettings();
                    var1.arrayList.add(var8);
                } catch (IllegalAccessException var6) {
                    var6.printStackTrace();
                }
            }
        }

        var1.arrayList.sort(Comparator.comparing(Module::getName, String.CASE_INSENSITIVE_ORDER).thenComparing(Module::getName));
    }

    public ArrayList<Module> method_1069(Category var1) {
        ArrayList var2 = new ArrayList();
        Iterator var5 = this.arrayList.iterator();

        while (var5.hasNext()) {
            Module var4;
            if ((var4 = (Module)var5.next()).getCategory() == var1) {
                var2.add(var4);
            }
        }

        return var2;
    }

    @EventHandler
    public void onTick(TickEvent var1) {
        var var3 = this;
        boolean var6;
        if (mc.currentScreen != null) {
            var6 = true;
        } else {
            var6 = false;
        }

        boolean var11 = var6;
        Iterator var2 = this.arrayList.iterator();

        label67:
        while (true) {
            Iterator var9 = var2;

            while (var9.hasNext()) {
                boolean var7;
                Module var10;
                if ((var10 = (Module)var2.next()).getBindMode().method_168(BindMode.HOLD) && var10.hasBindMode()) {
                    var7 = true;
                } else {
                    var7 = false;
                }

                boolean var8;
                if (!var11 && var10.getBind().method_163() && var10.getBind().method_157()) {
                    var8 = true;
                } else {
                    var8 = false;
                }

                if (var7) {
                    if (var8) {
                        var3.set.add(var10);
                        var10.setEnabled(true);
                        var9 = var2;
                    } else {
                        if (!var3.set.remove(var10)) {
                            continue label67;
                        }

                        var10.setEnabled(false);
                        var9 = var2;
                    }
                } else {
                    if (var11) {
                        continue label67;
                    }

                    if (var10.getBind().method_163()) {
                        if (var8 && var3.set.add(var10)) {
                            var10.toggle();
                        } else if (!var8) {
                            var3.set.remove(var10);
                        }
                        continue label67;
                    }

                    var9 = var2;
                }
            }

            return;
        }
    }

    public ArrayList<Module> getArrayList() {
        return this.arrayList;
    }
}
