package client.onyx.command;

import client.onyx.Onyx;
import client.onyx.command.impl.ConfigCommand;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class CommandManager {
    public static final String STRING_CONST = ".";
    public ConfigCommand configCommand;
    private final ArrayList<Command> arrayList;

    public boolean handleChatMessage(String var1) {
        if (var1 == null) {
            return false;
        } else {
            String var8;
            if (!(var8 = var1.trim()).startsWith(".")) {
                return false;
            } else {
                String var9;
                if ((var9 = var8.substring(".".length()).trim()).isEmpty()) {
                    return false;
                } else {
                    String[] var10;
                    String var2 = (var10 = var9.split("\\s+"))[0];
                    String[] var11 = Arrays.copyOfRange(var10, 1, var10.length);

                    Iterator var3;
                    for (Iterator var5 = var3 = this.arrayList.iterator(); var5.hasNext(); var5 = var3) {
                        Command var4;
                        if ((var4 = (Command)var3.next()).method_347(var2)) {
                            try {
                                var4.method_346(var11);
                            } catch (Exception var6) {
                                Command.method_348(new StringBuilder().insert(0, "Command failed: ").append(var6).toString());
                                Onyx.LOGGER.error("Command '{}' failed", var2, var6);
                            }

                            return true;
                        }
                    }

                    Command.method_348(new StringBuilder().insert(0, "Unknown command: ").append(var2).toString());
                    return true;
                }
            }
        }
    }

    public ArrayList<Command> getArrayList() {
        return this.arrayList;
    }

    public CommandManager() {
        var var1 = this;
        this.configCommand = new ConfigCommand();
        this.arrayList = new ArrayList<>();
        Field[] var4;
        int var2 = (var4 = this.getClass().getDeclaredFields()).length;
        int var3 = 0;

        for (int var5 = 0; var5 < var2; var5 = ++var3) {
            Field var7 = var4[var3];
            if (Command.class.isAssignableFrom(var7.getType())) {
                try {
                    var1.arrayList.add((Command)var7.get(var1));
                } catch (IllegalAccessException var6) {
                    var6.printStackTrace();
                }
            }
        }
    }
}
