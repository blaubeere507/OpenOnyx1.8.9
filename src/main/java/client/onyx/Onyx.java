package client.onyx;

import client.onyx.account.AccountManager;
import client.onyx.command.CommandManager;
import client.onyx.config.ConfigManager;
import client.onyx.module.ModuleManager;
import client.onyx.module.combat.bedwars.BedWarsObjectives;
import client.onyx.rotation.RotationManager;
import client.onyx.simulation.SimulationManager;
import client.onyx.util.FrameWatchdog;
import client.onyx.util.player.ContainerTracker;
import client.onyx.util.player.HotbarManager;
import client.onyx.util.player.MovementPredictor;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import meteordevelopment.orbit.EventBus;
import meteordevelopment.orbit.IEventBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Onyx {
    private static final Onyx INSTANCE = new Onyx();
    public static final Logger LOGGER = LogManager.getLogger("onyx");
    public static final IEventBus EVENT_BUS = new EventBus();
    public static ModuleManager moduleManager;
    public static CommandManager commandManager;
    public static AccountManager accountManager;
    public static ConfigManager configManager;

    public static String getUser() {
        String var0;
        return (var0 = System.getProperty("onyx.user")) != null && !var0.isEmpty() ? var0 : "dev";
    }

    public void init() {
        EVENT_BUS.registerLambdaFactory("client.onyx", (var0, var1) -> (Lookup)var0.invoke(null, var1, MethodHandles.lookup()));
        moduleManager = new ModuleManager();
        EVENT_BUS.subscribe(moduleManager);
        EVENT_BUS.subscribe(MovementPredictor.INSTANCE);
        EVENT_BUS.subscribe(HotbarManager.INSTANCE);
        EVENT_BUS.subscribe(BedWarsObjectives.INSTANCE);
        EVENT_BUS.subscribe(ContainerTracker.INSTANCE);
        EVENT_BUS.subscribe(RotationManager.INSTANCE);
        EVENT_BUS.subscribe(SimulationManager.INSTANCE);
        accountManager = new AccountManager();
        accountManager.method_1830();
        commandManager = new CommandManager();
        configManager = new ConfigManager();
        configManager.method_2632();
        FrameWatchdog.method_2091();
        LOGGER.info("onyx client initialised");
    }

    public static Onyx getInstance() {
        return INSTANCE;
    }

    public void shutdown() {
        if (accountManager != null) {
            accountManager.method_1833();
        }

        if (configManager != null) {
            configManager.method_2643();
        }
    }
}
