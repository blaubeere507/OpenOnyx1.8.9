package client.onyx.ui.clickgui.config;

import client.onyx.Onyx;
import client.onyx.config.ConfigManager;
import client.onyx.input.KeyTyped;
import client.onyx.input.MouseClick;
import client.onyx.render.ui.UiRenderer;
import client.onyx.ui.component.Button;
import client.onyx.ui.component.Component;
import client.onyx.ui.component.Grid;
import client.onyx.ui.component.KeyShortcuts;
import client.onyx.ui.component.Label;
import client.onyx.ui.component.RenderContext;
import client.onyx.ui.component.SearchField;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ConfigListPanel extends Component {
    private static final float FLOAT_120_01 = 120.0F;
    private static final float FLOAT_10_01 = 10.0F;
    private static final float FLOAT_8_01 = 8.0F;
    private final SearchField searchField = new SearchField("Config name");
    private final Button button = new Button(Button.Variant.TONAL, "New", "\ue145", () -> {
        String var4;
        if (!(var4 = this.searchField.method_2404().trim()).isEmpty() && !this.method_2512().method_2637(var4)) {
            this.label.method_2398("Use 1-64 letters, digits, - or _", "\ue001");
        } else if (!var4.isEmpty() && this.method_2512().method_2646(var4)) {
            this.label.method_2398("Config already exists; use its menu to overwrite", "\ue001");
        } else {
            String var3;
            if (var4.isEmpty()) {
                var3 = this.method_2512().method_2624("new");
            } else {
                var3 = var4;
            }

            if (var3 != null && this.method_2512().method_2634(var3)) {
                this.configListView.method_2525();
                this.searchField.method_2409("");
                this.searchField.method_2407(false);
                this.label.method_2401(new StringBuilder().insert(0, "Created ").append(var3).toString());
            } else {
                this.label.method_2398("Could not create a config", "\ue001");
            }
        }
    });
    private final Button button2 = new Button(Button.Variant.OUTLINED, "Save as", "\ue161", this::method_2518);
    private final Button button3 = new Button(Button.Variant.TEXT, "Open folder", "\ue2c8", () -> {
        this.method_2512().method_2649();
        this.label.method_2401("Opened config folder");
    });
    private final ConfigListView configListView = new ConfigListView(this::method_2523, var1 -> {
        String var4 = var1.getString();
        if (!this.method_2512().method_2629(var4)) {
            this.label.method_2398(new StringBuilder().insert(0, "Could not save ").append(var4).toString(), "\ue001");
        } else {
            this.label.method_2401(new StringBuilder().insert(0, "Saved ").append(var4).toString());
        }
    }, var1 -> {
        this.grid.method_2482();
        this.configRow = var1;
    });
    private final Grid grid2 = new Grid();
    private final Grid grid = new Grid();
    private final Label label = new Label();
    private final Set<String> set = new LinkedHashSet<>();
    private List<String> list2 = List.of();
    private ConfigRow configRow;
    private String string;
    private boolean flag4;

    @Override
    public boolean method_2189(float var1, float var2, int var3) {
        return this.searchField.method_2189(var1, var2, var3)
            | this.button.method_2189(var1, var2, var3)
            | this.button2.method_2189(var1, var2, var3)
            | this.button3.method_2189(var1, var2, var3)
            | this.configListView.method_2189(var1, var2, var3);
    }

    private void method_2509(ConfigRow var1) {
        String var3;
        if ((var3 = this.method_2512().method_2625(var1.getString())) == null) {
            this.label.method_2398(new StringBuilder().insert(0, "Could not duplicate ").append(var1.getString()).toString(), "\ue001");
        } else {
            this.configListView.method_2525();
            this.label.method_2401(new StringBuilder().insert(0, "Duplicated as ").append(var3).toString());
        }
    }

    private void method_2510(ConfigRow var1) {
        String var4 = var1.getString();
        if (!this.method_2512().method_2652(var4)) {
            this.label.method_2398(new StringBuilder().insert(0, "Could not delete ").append(var4).toString(), "\ue001");
        } else {
            this.configListView.method_2525();
            this.label.method_2401(new StringBuilder().insert(0, "Deleted ").append(var4).toString());
        }
    }

    private void method_2511(ConfigRow var1) {
        this.list2 = this.method_2512().method_2655().stream().filter(var1x -> !var1x.equals(var1.getString())).toList();
        if (this.list2.isEmpty()) {
            this.label.method_2398("No other configs to copy visuals to", "\ue001");
        } else {
            this.string = var1.getString();
            this.set.clear();
            this.flag4 = true;
        }
    }

    private ConfigManager method_2512() {
        return Onyx.configManager;
    }

    @Override
    public void method_2186(RenderContext var1) {
        this.searchField.method_2186(var1);
        this.button.method_2186(var1);
        this.button2.method_2186(var1);
        this.button3.method_2186(var1);
        this.configListView.method_2186(var1);
        this.grid2.method_2186(var1);
        this.grid.method_2186(var1);
        this.label.method_2186(var1);
    }

    private List<Grid.Cell> method_2513(ConfigRow var1) {
        ArrayList var3;
        (var3 = new ArrayList()).add(Grid.Cell.method_2382("\ue161", "Overwrite with current", () -> this.method_2522(var1)));
        var3.add(Grid.Cell.method_2382("\ue14d", "Duplicate", () -> this.method_2509(var1)));
        var3.add(Grid.Cell.method_2382("\ue14d", "Copy visuals to", () -> this.method_2511(var1)));
        var3.add(Grid.Cell.method_2383());
        var3.add(Grid.Cell.method_2386("\ue872", "Delete", () -> this.method_2510(var1)));
        return var3;
    }

    @Override
    public boolean method_2212(KeyTyped var1) {
        if (this.grid.method_2212(var1)) {
            return true;
        } else if (this.grid2.method_2212(var1)) {
            return true;
        } else if (this.searchField.method_2212(var1)) {
            return true;
        } else if (KeyShortcuts.method_2266(var1)) {
            this.searchField.method_2407(true);
            this.searchField.method_2413();
            return true;
        } else if (KeyShortcuts.method_2273(var1) && !this.searchField.method_2404().isBlank()) {
            this.method_2518();
            return true;
        } else if (var1.intValue() == 200) {
            return this.configListView.method_2281(0, -1);
        } else if (var1.intValue() == 208) {
            return this.configListView.method_2281(0, 1);
        } else if (KeyShortcuts.method_2275(var1) && this.configListView.method_2524() != null) {
            this.method_2523(this.configListView.method_2524());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean method_2183(float var1, float var2, int var3) {
        return this.grid.method_2183(var1, var2, var3)
            || this.grid2.method_2183(var1, var2, var3)
            || this.searchField.method_2183(var1, var2, var3)
            || this.button.method_2183(var1, var2, var3)
            || this.button2.method_2183(var1, var2, var3)
            || this.button3.method_2183(var1, var2, var3)
            || this.configListView.method_2183(var1, var2, var3);
    }

    private boolean method_2514() {
        return !this.list2.isEmpty() && this.set.containsAll(this.list2);
    }

    public String method_2515() {
        int var2;
        return (var2 = this.configListView.method_2526()) == 1 ? "1 config" : var2 + " configs";
    }

    @Override
    public boolean method_2193(MouseClick var1) {
        return this.searchField.method_2193(var1);
    }

    private void method_2516(RenderContext var1) {
        UiRenderer var7 = var1.uiRenderer;
        float var2 = this.button.method_2464(var7);
        float var5 = this.button2.method_2464(var7);
        float var8 = this.button3.method_2464(var7);
        float var4 = Math.max(120.0F, this.floatValue4 - var2 - var5 - var8 - 30.0F);
        float var3 = this.floatValue + 5.0F;
        this.searchField.method_2258(this.floatValue3, this.floatValue, var4, 40.0F);
        this.button.method_2258(this.searchField.getFloatValue3() + this.searchField.getFloatValue4() + 10.0F, var3, var2, 30.0F);
        this.button2.method_2258(this.button.getFloatValue3() + var2 + 10.0F, var3, var5, 30.0F);
        this.button3.method_2258(this.floatValue3 + this.floatValue4 - var8, var3, var8, 30.0F);
        this.configListView.method_2258(this.floatValue3, this.floatValue + 40.0F + 8.0F, this.floatValue4, this.floatValue2 - 40.0F - 8.0F);
        this.label.method_2258(this.floatValue3, this.floatValue, this.floatValue4, this.floatValue2);
    }

    private void method_2517(String var1) {
        if (!this.set.add(var1)) {
            this.set.remove(var1);
        }
    }

    private void method_2518() {
        String var2 = this.searchField.method_2404().trim();
        if (!this.method_2512().method_2637(var2)) {
            this.label.method_2398("Use 1-64 letters, digits, - or _", "\ue001");
        } else if (this.method_2512().method_2646(var2)) {
            this.label.method_2398("Config already exists; use its menu to overwrite", "\ue001");
        } else if (!this.method_2512().method_2629(var2)) {
            this.label.method_2398(new StringBuilder().insert(0, "Could not save ").append(var2).toString(), "\ue001");
        } else {
            this.configListView.method_2525();
            this.searchField.method_2409("");
            this.searchField.method_2407(false);
            this.label.method_2401(new StringBuilder().insert(0, "Saved ").append(var2).toString());
        }
    }

    public void method_2519() {
        this.configListView.method_2525();
        this.searchField.method_2409("");
        this.searchField.method_2407(false);
        this.grid2.method_2482();
        this.grid.method_2482();
        this.set.clear();
    }

    public void method_2520() {
        this.searchField.method_2407(false);
        this.grid2.method_2482();
        this.grid.method_2482();
        this.label.method_2400();
    }

    @Override
    public boolean method_2211(float var1, float var2, double var3) {
        return this.grid.method_2211(var1, var2, var3) || this.grid2.method_2211(var1, var2, var3) || this.configListView.method_2211(var1, var2, var3);
    }

    @Override
    public boolean method_2188(float var1, float var2, int var3, float var4, float var5) {
        return this.searchField.method_2188(var1, var2, var3, var4, var5);
    }

    public ConfigListPanel() {
        this.searchField.method_2550(this::method_2518);
    }

    private List<Grid.Cell> method_2521() {
        var var1 = this;
        ArrayList var5;
        (var5 = new ArrayList()).add(Grid.Cell.method_2385("Select all", this::method_2514, () -> {
            if (!this.method_2514()) {
                this.set.addAll(this.list2);
            } else {
                this.set.clear();
            }
        }));
        var5.add(Grid.Cell.method_2383());
        Iterator var2 = this.list2.iterator();

        for (Iterator var4 = var2; var4.hasNext(); var4 = var2) {
            String var3 = (String)var2.next();
            var5.add(Grid.Cell.method_2385(var3, () -> this.set.contains(var3), () -> this.method_2517(var3)));
        }

        var5.add(Grid.Cell.method_2383());
        var5.add(
            Grid.Cell.method_2384(
                "\ue14d",
                "Copy to selected",
                () -> {
                    if (this.set.isEmpty()) {
                        this.label.method_2398("Select at least one config", "\ue001");
                    } else {
                        ConfigManager.LoadResult var4x;
                        if ((var4x = this.method_2512().method_2623(this.string, new ArrayList<>(this.set))).intValue() == 0) {
                            this.label.method_2398("Could not copy visuals", "\ue001");
                        } else {
                            ConfigListPanel var3x;
                            if (var4x.list().isEmpty()) {
                                this.label
                                    .method_2401(new StringBuilder().insert(0, "Copied visuals to ").append(var4x.intValue()).append(" config(s)").toString());
                                var3x = this;
                            } else {
                                this.label
                                    .method_2398(
                                        new StringBuilder()
                                            .insert(0, "Copied to ")
                                            .append(var4x.intValue())
                                            .append("; failed: ")
                                            .append(String.join(", ", var4x.list()))
                                            .toString(),
                                        "\ue001"
                                    );
                                var3x = this;
                            }

                            var3x.set.clear();
                            this.grid.method_2482();
                        }
                    }
                }
            )
        );
        return var5;
    }

    private void method_2522(ConfigRow var1) {
        String var4 = var1.getString();
        if (!this.method_2512().method_2629(var4)) {
            this.label.method_2398(new StringBuilder().insert(0, "Could not update ").append(var4).toString(), "\ue001");
        } else {
            this.label.method_2401(new StringBuilder().insert(0, "Updated ").append(var4).toString());
        }
    }

    @Override
    public void method_2206(RenderContext var1) {
        this.method_2516(var1);
        this.searchField.method_2206(var1);
        this.button.method_2206(var1);
        this.button2.method_2206(var1);
        this.button3.method_2206(var1);
        this.configListView.method_2206(var1);
        if (this.configRow != null) {
            this.grid2.method_2481(this.method_2513(this.configRow), this.configRow.method_2611(), this, var1.uiRenderer);
            this.configRow = null;
        }

        if (this.flag4) {
            this.grid.method_2485(this.method_2521(), this.grid2, this, var1.uiRenderer);
            this.flag4 = false;
        }

        this.grid2.method_2206(var1);
        this.grid.method_2206(var1);
        this.label.method_2206(var1);
    }

    private void method_2523(ConfigRow var1) {
        String var4 = var1.getString();
        if (!this.method_2512().method_2650(var4)) {
            this.label.method_2398(new StringBuilder().insert(0, "Could not load ").append(var4).toString(), "\ue001");
        } else {
            this.label.method_2401(new StringBuilder().insert(0, "Loaded ").append(var4).toString());
        }
    }
}
