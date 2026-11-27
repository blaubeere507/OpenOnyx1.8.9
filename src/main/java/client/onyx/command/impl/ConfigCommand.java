package client.onyx.command.impl;

import client.onyx.Onyx;
import client.onyx.command.Command;
import client.onyx.config.ConfigManager;
import java.util.List;
import java.util.Locale;

public class ConfigCommand extends Command {
    @Override
    public void method_346(String[] var1) {
        if (var1.length == 0) {
            this.method_354();
        } else {
            byte var4;
            label38: {
                String var2 = var1[0].toLowerCase(Locale.ROOT);
                byte var5 = -1;
                switch (var2.hashCode()) {
                    case -1268966290:
                        if (var2.equals("folder")) {
                            byte var10 = 3;
                            var4 = 3;
                            break label38;
                        }
                        break;
                    case 3198785:
                        if (var2.equals("help")) {
                            var5 = 5;
                        }
                        break;
                    case 3322014:
                        if (var2.equals("list")) {
                            byte var9 = 2;
                            var4 = 2;
                            break label38;
                        }
                        break;
                    case 3327206:
                        if (var2.equals("load")) {
                            boolean var8 = true;
                            var4 = 1;
                            break label38;
                        }
                        break;
                    case 3417674:
                        if (var2.equals("open")) {
                            byte var7 = 4;
                            var4 = 4;
                            break label38;
                        }
                        break;
                    case 3522941:
                        if (var2.equals("save")) {
                            boolean var6 = false;
                            var4 = 0;
                            break label38;
                        }
                }

                var4 = var5;
            }

            switch (var4) {
                case 0:
                    this.method_353(this.method_351(var1));
                    return;
                case 1:
                    this.method_355(this.method_351(var1));
                    return;
                case 2:
                    this.method_356();
                    return;
                case 3:
                case 4:
                    this.method_352();
                    return;
                case 5:
                    this.method_354();
                    return;
                default:
                    method_348(
                        new StringBuilder().insert(0, "Unknown subcommand: ").append(var1[0]).append(" (try ").append(this.getString2()).append(")").toString()
                    );
            }
        }
    }

    public ConfigCommand() {
        super("config", "Saves and loads client configs", ".config <save|load|list|folder|help> [name]", "cfg");
    }

    private String method_351(String[] var1) {
        return var1.length > 1 ? var1[1] : "default";
    }

    private void method_352() {
        Onyx.configManager.method_2649();
        method_350(new StringBuilder().insert(0, "Opened ").append(Onyx.configManager.method_2654()).toString());
    }

    private void method_353(String var1) {
        ConfigManager var2 = Onyx.configManager;
        if (!Onyx.configManager.method_2637(var1)) {
            method_348(new StringBuilder().insert(0, "Invalid name '").append(var1).append("', use letters, digits, - and _").toString());
        } else {
            boolean var10000 = var2.method_2629(var1);
            StringBuilder var10002;
            if (var10000) {
                var10002 = new StringBuilder();
                method_350(var10002.insert(0, "Saved config '").append(var1).append("'").toString());
            } else {
                var10002 = new StringBuilder();
                method_348(var10002.insert(0, "Could not save config '").append(var1).append("', see the log").toString());
            }
        }
    }

    private void method_354() {
        method_350(this.getString3());
        method_350(".config save [name] - writes the current settings");
        method_350(".config load [name] - applies a saved config");
        method_350(".config list - lists saved configs");
        method_350(".config folder - opens the config folder");
        method_350("[name] defaults to 'default', which loads on start and saves on quit");
    }

    private void method_355(String var1) {
        ConfigManager var2 = Onyx.configManager;
        if (!Onyx.configManager.method_2637(var1)) {
            method_348(new StringBuilder().insert(0, "Invalid name '").append(var1).append("', use letters, digits, - and _").toString());
        } else if (!var2.method_2646(var1)) {
            method_348(new StringBuilder().insert(0, "No config named '").append(var1).append("'").toString());
        } else {
            boolean var10000 = var2.method_2650(var1);
            StringBuilder var10002;
            if (var10000) {
                var10002 = new StringBuilder();
                method_350(var10002.insert(0, "Loaded config '").append(var1).append("'").toString());
            } else {
                var10002 = new StringBuilder();
                method_348(var10002.insert(0, "Could not load config '").append(var1).append("', see the log").toString());
            }
        }
    }

    private void method_356() {
        List var2;
        if ((var2 = Onyx.configManager.method_2655()).isEmpty()) {
            method_350("No configs saved yet");
        } else {
            method_350(var2.size() + " config(s): " + String.join(", ", var2));
        }
    }
}
