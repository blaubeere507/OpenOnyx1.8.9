package client.onyx.ui.component;

import client.onyx.input.KeyTyped;
import client.onyx.input.MouseClick;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Consumer;
import net.minecraft.client.gui.GuiScreen;

public class TextBuffer {
    private static final char CHAR_82261 = '•';
    private static final int INT_1281 = 128;
    public static final float FLOAT_1000_01 = 1000.0F;
    private static final float FLOAT_550_01 = 550.0F;
    private final Deque<TextBuffer.Edit> deque = new ArrayDeque<>();
    private final Deque<TextBuffer.Edit> deque2 = new ArrayDeque<>();
    private String string = "";
    private float floatValue = Float.MAX_VALUE;
    private TextBuffer.EditKind editKind = TextBuffer.EditKind.NONE;
    private int intValue;
    private Consumer<String> consumer;
    private int intValue2;
    private Runnable runnable;
    private Runnable runnable2;
    private boolean flag;
    private float floatValue2;

    public void method_2418(Consumer<String> var1) {
        this.consumer = var1;
    }

    private TextBuffer.Edit method_2419() {
        return new TextBuffer.Edit(this.string, this.intValue2, this.intValue);
    }

    public boolean method_2420() {
        if (this.deque2.isEmpty()) {
            return false;
        } else {
            this.deque.push(this.method_2419());
            this.method_2441(this.deque2.pop());
            return true;
        }
    }

    private int method_2421(int var1) {
        var var6 = this;
        int var5;
        int var2 = var5 = Math.clamp((long)var1, 0, this.string.length());

        while (var2 < var6.string.length() && !Character.isLetterOrDigit(var6.string.charAt(var5))) {
            var2 = ++var5;
        }

        int var3 = var5;

        while (var3 < var6.string.length() && Character.isLetterOrDigit(var6.string.charAt(var5))) {
            var3 = ++var5;
        }

        return var5;
    }

    public void method_2422() {
        this.floatValue2 = 0.0F;
    }

    public void method_2423() {
        this.intValue = 0;
        this.intValue2 = this.string.length();
        this.method_2422();
    }

    public boolean method_2424() {
        return this.floatValue2 < 500.0F;
    }

    public int getIntValue2() {
        return this.intValue2;
    }

    public boolean method_2425() {
        if (this.deque.isEmpty()) {
            return false;
        } else {
            this.deque2.push(this.method_2419());
            this.method_2441(this.deque.pop());
            return true;
        }
    }

    public float method_2426(int var1, TextBuffer.ChangeListener var2) {
        String var4 = this.method_2429();
        return var2.method_2417(var4.substring(0, Math.clamp((long)var1, 0, var4.length())));
    }

    public void method_2427(boolean var1) {
        this.flag = var1;
    }

    public void method_2428(int var1) {
        this.intValue = this.method_2431(Math.clamp((long)var1, 0, this.string.length()));
        this.intValue2 = this.method_2421(Math.clamp((long)var1, 0, this.string.length()));
        this.method_2422();
    }

    public String method_2429() {
        return this.flag ? String.valueOf('•').repeat(this.string.length()) : this.string;
    }

    public void method_2430(int var1, boolean var2) {
        this.intValue2 = Math.clamp((long)var1, 0, this.string.length());
        if (!var2) {
            this.intValue = this.intValue2;
        }

        this.method_2422();
    }

    private int method_2431(int var1) {
        var var6 = this;
        int var5;
        int var2 = var5 = Math.clamp((long)var1, 0, this.string.length());

        while (var2 > 0 && !Character.isLetterOrDigit(var6.string.charAt(var5 - 1))) {
            var2 = --var5;
        }

        int var3 = var5;

        while (var3 > 0 && Character.isLetterOrDigit(var6.string.charAt(var5 - 1))) {
            var3 = --var5;
        }

        return var5;
    }

    private void method_2432(TextBuffer.EditKind var1) {
        var var2 = this;
        boolean var3;
        if (var1 == this.editKind && this.floatValue < 550.0F && !this.deque.isEmpty()) {
            var3 = true;
        } else {
            var3 = false;
        }

        if (!var3) {
            this.deque.push(this.method_2419());

            for (TextBuffer var4 = this; var4.deque.size() > 128; var4 = var2) {
                var2.deque.removeLast();
            }
        }

        var2.deque2.clear();
        var2.editKind = var1;
        var2.floatValue = 0.0F;
    }

    public void method_2433(String var1) {
        this.method_2436(var1);
    }

    public boolean method_2434(KeyTyped var1) {
        if (KeyShortcuts.method_2271(var1, 30)) {
            this.method_2423();
            return true;
        } else if (KeyShortcuts.method_2271(var1, 46)) {
            this.method_2435();
            return true;
        } else if (KeyShortcuts.method_2271(var1, 45)) {
            if (this.method_2435()) {
                this.method_2439("", TextBuffer.EditKind.DELETE);
            }

            return true;
        } else if (KeyShortcuts.method_2271(var1, 47)) {
            String var28 = GuiScreen.getClipboardString();
            TextBuffer var4;
            String var5;
            if (var28 != null) {
                var5 = var28.replaceAll("[\\r\\n]", " ");
                var4 = this;
            } else {
                var5 = "";
                var4 = this;
            }

            var4.method_2436(var5);
            return true;
        } else if (KeyShortcuts.method_2274(var1)) {
            return this.method_2425();
        } else if (KeyShortcuts.method_2276(var1)) {
            return this.method_2420();
        } else {
            boolean var2 = var1.method_3();
            switch (var1.intValue()) {
                case 1:
                    if (this.runnable != null) {
                        this.runnable.run();
                    }

                    return true;
                case 14:
                    if (!this.method_2443()) {
                        if (this.intValue2 > 0) {
                            TextBuffer var9;
                            int var10;
                            if (!KeyShortcuts.method_2270(var1)) {
                                var10 = this.intValue2 - 1;
                                var9 = this;
                            } else {
                                var10 = this.method_2431(this.intValue2);
                                var9 = this;
                            }

                            var9.intValue = var10;
                            this.method_2439("", TextBuffer.EditKind.DELETE);
                        }
                    } else {
                        this.method_2439("", TextBuffer.EditKind.DELETE);
                    }

                    return true;
                case 28:
                case 156:
                    if (this.runnable2 != null) {
                        this.runnable2.run();
                    }

                    return true;
                case 199:
                    this.method_2430(0, var2);
                    return true;
                case 203:
                    TextBuffer var18;
                    int var19;
                    boolean var20;
                    if (!KeyShortcuts.method_2270(var1)) {
                        int var30 = this.intValue2 - 1;
                        var20 = var2;
                        var19 = var30;
                        var18 = this;
                    } else {
                        int var31 = this.method_2431(this.intValue2);
                        var20 = var2;
                        var19 = var31;
                        var18 = this;
                    }

                    var18.method_2430(var19, var20);
                    return true;
                case 205:
                    TextBuffer var23;
                    int var24;
                    boolean var25;
                    if (!KeyShortcuts.method_2270(var1)) {
                        int var10001 = this.intValue2 + 1;
                        var25 = var2;
                        var24 = var10001;
                        var23 = this;
                    } else {
                        int var29 = this.method_2421(this.intValue2);
                        var25 = var2;
                        var24 = var29;
                        var23 = this;
                    }

                    var23.method_2430(var24, var25);
                    return true;
                case 207:
                    this.method_2430(this.string.length(), var2);
                    return true;
                case 211:
                    if (!this.method_2443()) {
                        if (this.intValue2 < this.string.length()) {
                            TextBuffer var14;
                            int var15;
                            if (!KeyShortcuts.method_2270(var1)) {
                                var15 = this.intValue2 + 1;
                                var14 = this;
                            } else {
                                var15 = this.method_2421(this.intValue2);
                                var14 = this;
                            }

                            var14.intValue = var15;
                            this.method_2439("", TextBuffer.EditKind.DELETE);
                        }
                    } else {
                        this.method_2439("", TextBuffer.EditKind.DELETE);
                    }

                    return true;
                default:
                    return false;
            }
        }
    }

    private boolean method_2435() {
        String var3;
        if (!this.method_2443()) {
            var3 = this.string;
        } else {
            var3 = this.method_2438();
        }

        if (!this.flag && !var3.isEmpty()) {
            GuiScreen.setClipboardString(var3);
            return true;
        } else {
            return false;
        }
    }

    private void method_2436(String var1) {
        if (!var1.isEmpty()) {
            this.method_2439(var1, TextBuffer.EditKind.INSERT);
        }
    }

    public void method_2437(String var1) {
        TextBuffer var3;
        String var4;
        if (var1 != null) {
            var4 = var1;
            var3 = this;
        } else {
            var4 = "";
            var3 = this;
        }

        var3.string = var4;
        this.intValue2 = this.string.length();
        this.intValue = this.intValue2;
        this.floatValue2 = 0.0F;
        this.deque.clear();
        this.deque2.clear();
        this.editKind = TextBuffer.EditKind.NONE;
    }

    public String method_2438() {
        return this.method_2443() ? this.string.substring(this.method_2451(), this.method_2446()) : "";
    }

    private void method_2439(String var1, TextBuffer.EditKind var2) {
        int var5 = this.method_2451();
        int var4 = this.method_2446();
        if (var5 != var4 || !var1.isEmpty()) {
            this.method_2432(var2);
            this.string = this.string.substring(0, var5) + var1 + this.string.substring(var4);
            this.intValue2 = var5 + var1.length();
            this.intValue = this.intValue2;
            this.floatValue2 = 0.0F;
            if (this.consumer != null) {
                this.consumer.accept(this.string);
            }
        }
    }

    public void method_2440(Runnable var1) {
        this.runnable2 = var1;
    }

    private void method_2441(TextBuffer.Edit var1) {
        this.string = var1.string();
        this.intValue2 = Math.clamp((long)var1.intValue2(), 0, this.string.length());
        this.intValue = Math.clamp((long)var1.intValue(), 0, this.string.length());
        this.floatValue2 = 0.0F;
        this.editKind = TextBuffer.EditKind.NONE;
        this.floatValue = Float.MAX_VALUE;
        if (this.consumer != null) {
            this.consumer.accept(this.string);
        }
    }

    public void method_2442(Runnable var1) {
        this.runnable = var1;
    }

    public String getString() {
        return this.string;
    }

    public boolean isFlag() {
        return this.flag;
    }

    public boolean method_2443() {
        return this.intValue2 != this.intValue;
    }

    public void method_2444() {
        this.intValue = this.intValue2;
        this.method_2422();
    }

    public boolean method_2445() {
        if (this.string.isEmpty()) {
            return false;
        } else {
            this.method_2423();
            this.method_2439("", TextBuffer.EditKind.DELETE);
            return true;
        }
    }

    public int method_2446() {
        return Math.max(this.intValue2, this.intValue);
    }

    public boolean method_2447(MouseClick var1) {
        if (!KeyShortcuts.method_2268(var1)) {
            return false;
        } else {
            this.method_2436(Character.toString(var1.intValue2()));
            return true;
        }
    }

    public int method_2448(float var1, TextBuffer.ChangeListener var2) {
        String var6 = this.method_2429();
        int var4 = 0;
        float var5 = Math.abs(var1);
        int var9 = 1;

        for (int var8 = 1; var8 <= var6.length(); var8 = ++var9) {
            float var7;
            if ((var7 = Math.abs(var2.method_2417(var6.substring(0, var9)) - var1)) <= var5) {
                var5 = var7;
                var4 = var9;
            }
        }

        return var4;
    }

    public float method_2449(TextBuffer.ChangeListener var1) {
        String var3 = this.method_2429();
        return var1.method_2417(var3.substring(0, Math.min(this.intValue2, var3.length())));
    }

    public boolean method_2450() {
        return this.string.isEmpty();
    }

    public int method_2451() {
        return Math.min(this.intValue2, this.intValue);
    }

    public void method_2452(float var1) {
        this.floatValue2 = (this.floatValue2 + var1) % 1000.0F;
        if (this.floatValue < Float.MAX_VALUE) {
            this.floatValue += var1;
        }
    }

    public interface ChangeListener {
        float method_2417(String var1);
    }

    private record Edit(String string, int intValue2, int intValue) {
    }

    private static enum EditKind {
        NONE,
        INSERT,
        DELETE;
    }
}
