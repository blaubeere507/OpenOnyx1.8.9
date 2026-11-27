package client.onyx.ui.component;

import client.onyx.input.KeyTyped;
import client.onyx.input.MouseClick;
import client.onyx.util.IMinecraft;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Component implements IMinecraft {
    public final List<Component> list = new ArrayList<>();
    public boolean flag2 = true;
    public boolean flag;
    public float floatValue;
    public float floatValue2;
    public float floatValue3;
    public boolean flag3;
    public float floatValue4;

    public boolean method_2189(float var1, float var2, int var3) {
        boolean var8 = false;

        Iterator var5;
        for (Iterator var7 = var5 = this.list.iterator(); var7.hasNext(); var7 = var5) {
            Component var6 = (Component)var5.next();
            var8 |= var6.method_2189(var1, var2, var3);
        }

        return var8;
    }

    public boolean method_2211(float var1, float var2, double var3) {
        Iterator var6 = this.list.iterator();

        while (var6.hasNext()) {
            if (((Component)var6.next()).method_2211(var1, var2, var3)) {
                return true;
            }
        }

        return false;
    }

    public float getFloatValue4() {
        return this.floatValue4;
    }

    public float getFloatValue() {
        return this.floatValue;
    }

    public boolean isFlag() {
        return this.flag;
    }

    public boolean isFlag2() {
        return this.flag2;
    }

    public float method_2257(float var1) {
        return this.floatValue2;
    }

    public boolean method_2212(KeyTyped var1) {
        Iterator var3 = this.list.iterator();

        while (var3.hasNext()) {
            if (((Component)var3.next()).method_2212(var1)) {
                return true;
            }
        }

        return false;
    }

    public void method_2206(RenderContext var1) {
        Component var4;
        boolean var5;
        label24: {
            Component var3;
            if (!this.flag2) {
                var3 = this;
            } else {
                if (this.method_2259(var1.floatValue3, var1.floatValue2)) {
                    var5 = true;
                    var4 = this;
                    break label24;
                }

                var3 = this;
            }

            var5 = false;
            var4 = var3;
        }

        var4.flag = var5;

        Iterator var7;
        for (Iterator var6 = var7 = this.list.iterator(); var6.hasNext(); var6 = var7) {
            ((Component)var7.next()).method_2206(var1);
        }
    }

    public void method_2258(float var1, float var2, float var3, float var4) {
        this.floatValue3 = var1;
        this.floatValue = var2;
        this.floatValue4 = var3;
        this.floatValue2 = var4;
        this.method_2194();
    }

    public boolean isFlag3() {
        return this.flag3;
    }

    public List<Component> getList() {
        return this.list;
    }

    public boolean method_2193(MouseClick var1) {
        Iterator var3 = this.list.iterator();

        while (var3.hasNext()) {
            if (((Component)var3.next()).method_2193(var1)) {
                return true;
            }
        }

        return false;
    }

    public float getFloatValue3() {
        return this.floatValue3;
    }

    public void method_2186(RenderContext var1) {
        Iterator var4;
        for (Iterator var3 = var4 = this.list.iterator(); var3.hasNext(); var3 = var4) {
            ((Component)var4.next()).method_2186(var1);
        }
    }

    public boolean method_2188(float var1, float var2, int var3, float var4, float var5) {
        Iterator var7 = this.list.iterator();

        while (var7.hasNext()) {
            if (((Component)var7.next()).method_2188(var1, var2, var3, var4, var5)) {
                return true;
            }
        }

        return false;
    }

    public float getFloatValue2() {
        return this.floatValue2;
    }

    public void method_2194() {
    }

    public boolean method_2183(float var1, float var2, int var3) {
        Iterator var6 = this.list.iterator();

        while (var6.hasNext()) {
            Component var5;
            if ((var5 = (Component)var6.next()).flag2 && var5.method_2183(var1, var2, var3)) {
                return true;
            }
        }

        return false;
    }

    public boolean method_2259(float var1, float var2) {
        return var1 >= this.floatValue3 && var1 < this.floatValue3 + this.floatValue4 && var2 >= this.floatValue && var2 < this.floatValue + this.floatValue2;
    }

    public void method_2260(boolean var1) {
        this.flag2 = var1;
    }
}
