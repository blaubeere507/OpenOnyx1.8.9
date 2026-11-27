package client.onyx.setting;

import client.onyx.Onyx;
import client.onyx.module.Module;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Iterator;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ModeSetting<M extends Mode> extends ValueSetting<M> {
    private final List<M> list;

    public ModeSetting<M> describe(String var1) {
        super.describe(var1);
        return this;
    }

    public <O> ModeSetting<M> visibleWhen(ValueSetting<O> var1, Predicate<O> var2) {
        super.visibleWhen(var1, var2);
        return this;
    }

    public ModeSetting<M> onChange(Consumer<M> var1) {
        super.onChange(var1);
        return this;
    }

    public M method_68() {
        return this.getValue();
    }

    public void method_69(Mode var1) {
        Iterator var4 = this.list.iterator();

        while (var4.hasNext()) {
            Mode var3;
            if ((var3 = (Mode)var4.next()) == var1) {
                this.setValue((M)var3);
                return;
            }
        }
    }

    @Override
    public JsonElement save() {
        JsonObject var4;
        (var4 = new JsonObject()).addProperty("mode", this.getValue().getName());
        JsonObject var2 = new JsonObject();
        Iterator var6;
        Iterator var5 = var6 = this.list.iterator();

        while (var5.hasNext()) {
            Mode var1;
            if (!(var1 = (Mode)var6.next()).method_58()) {
                var5 = var6;
            } else {
                var2.add(var1.getName(), var1.save());
                var5 = var6;
            }
        }

        var4.add("modes", var2);
        return var4;
    }

    public ModeSetting<M> visibleWhen(ValueSetting<Boolean> var1) {
        super.visibleWhen(var1);
        return this;
    }

    public List<M> getList2() {
        return this.list;
    }

    @Override
    public List<Setting> getList() {
        return List.copyOf(this.list);
    }

    public boolean method_70(M var1) {
        return this.getValue() == var1;
    }

    @Override
    public void load(JsonElement var1) {
        if (var1.isJsonPrimitive() && var1.getAsJsonPrimitive().isString()) {
            this.method_71(var1.getAsString());
        } else if (var1.isJsonObject()) {
            JsonElement var8;
            JsonObject var10;
            if ((var8 = (var10 = var1.getAsJsonObject()).get("modes")) != null && var8.isJsonObject()) {
                JsonObject var3 = var8.getAsJsonObject();
                Iterator var9 = this.list.iterator();

                label49:
                while (true) {
                    Iterator var6 = var9;

                    while (true) {
                        if (!var6.hasNext()) {
                            break label49;
                        }

                        Mode var4;
                        if (!(var4 = (Mode)var9.next()).method_58()) {
                            var6 = var9;
                        } else {
                            JsonElement var5;
                            if ((var5 = var3.get(var4.getName())) == null) {
                                break;
                            }

                            if (!var5.isJsonNull()) {
                                try {
                                    var4.load(var5);
                                } catch (RuntimeException var7) {
                                    Onyx.LOGGER.warn("Failed to load mode '{}'", var4.method_57(), var7);
                                }
                                break;
                            }

                            var6 = var9;
                        }
                    }
                }
            }

            JsonElement var11;
            if ((var11 = var10.get("mode")) != null && var11.isJsonPrimitive() && var11.getAsJsonPrimitive().isString()) {
                this.method_71(var11.getAsString());
            }
        }
    }

    public void method_71(String var1) {
        var1 = method_72(var1);
        Iterator var4 = this.list.iterator();

        while (var4.hasNext()) {
            Mode var3;
            if (method_72((var3 = (Mode)var4.next()).getName()).equals(var1)) {
                this.setValue((M)var3);
                return;
            }
        }
    }

    @Override
    public void setParentEnabled(boolean var1) {
        var var2 = this;

        Iterator var8;
        for (Iterator var7 = var8 = this.list.iterator(); var7.hasNext(); var7 = var8) {
            Mode var5;
            boolean var6;
            label20: {
                Mode var3;
                Mode var10000 = var3 = (Mode)var8.next();
                Mode var4;
                if (!var1) {
                    var4 = var10000;
                } else {
                    if (var3 == var2.getValue()) {
                        var6 = true;
                        var5 = var10000;
                        break label20;
                    }

                    var4 = var10000;
                }

                var6 = false;
                var5 = var4;
            }

            var5.setParentEnabled(var6);
        }
    }

    public ModeSetting<M> visibleWhen(BooleanSupplier var1) {
        super.visibleWhen(var1);
        return this;
    }

    @Override
    public void attach(Module var1, Setting var2) {
        var var7 = this;
        super.attach(var1, var2);

        Iterator var6;
        for (Iterator var4 = var6 = this.list.iterator(); var4.hasNext(); var4 = var6) {
            Mode var3 = (Mode)var6.next();
            var3.method_75(var7);
            var3.attach(var1, var7);
        }
    }

    @SafeVarargs
    public ModeSetting(String var1, M var2, M... var3) {
        super(var1, (M)var2);
        this.list = List.of((M[])var3);
        if (!this.list.contains(var2)) {
            throw new IllegalArgumentException(
                new StringBuilder().insert(0, "Default mode '").append(var2.getName()).append("' is not part of '").append(var1).append("'").toString()
            );
        }
    }

    public void setValue(M var1) {
        Mode var3 = this.getValue();
        super.setValue(var1);
        if (this.getValue() != var3) {
            this.method_56();
        }
    }

    private static String method_72(String var0) {
        String var2 = var0;
        if (var0 == null) {
            return "";
        } else {
            StringBuilder var1 = new StringBuilder(var0.length());
            int var5 = 0;

            for (int var4 = 0; var4 < var2.length(); var4 = ++var5) {
                char var3;
                if (Character.isLetterOrDigit(var3 = var2.charAt(var5))) {
                    var1.append(Character.toLowerCase(var3));
                }
            }

            return var1.toString();
        }
    }

    public boolean method_73(String var1) {
        return this.getValue().getName().equals(var1);
    }
}
