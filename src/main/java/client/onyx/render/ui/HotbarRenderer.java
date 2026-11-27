package client.onyx.render.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class HotbarRenderer {
    private static final Map<String, float[]> map = new LinkedHashMap<>();

    public static void method_855(String var0) {
        map.remove(var0);
    }

    private HotbarRenderer() {
    }

    public static List<float[]> method_856(String var0) {
        String var2 = var0;
        ArrayList var1 = new ArrayList(map.size());
        Iterator var4 = map.entrySet().iterator();

        while (var4.hasNext()) {
            Entry var3;
            if (!((String)(var3 = (Entry)var4.next()).getKey()).equals(var2)) {
                var1.add((float[])var3.getValue());
            }
        }

        return var1;
    }

    public static void method_857(String var0, float var1, float var2, float var3, float var4) {
        map.put(var0, new float[]{var1, var2, var3, var4});
    }
}
