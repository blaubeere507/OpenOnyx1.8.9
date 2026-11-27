package client.onyx.module.hud.targethud;

import client.onyx.render.font.GlyphAtlasFont;
import java.util.Locale;

public final class TargetHudFormatter {
    public static final String STRING_CONST = "{name} {health} {max} {hearts} {distance} {status}";

    private static String method_1508(float var0) {
        return String.format(Locale.ROOT, "%.1f", var0);
    }

    public static String method_1509(GlyphAtlasFont var0, String var1) {
        String var8 = var1;
        GlyphAtlasFont var9 = var0;
        int var2 = 0;

        for (int var7 = 0; var7 < var8.length(); var7 = ++var2) {
            if (!var9.method_638(var8.charAt(var2))) {
                StringBuilder var4;
                (var4 = new StringBuilder(var8.length())).append(var8, 0, var2);
                int var3 = var2;

                for (int var6 = var2; var6 < var8.length(); var6 = ++var3) {
                    char var5 = var8.charAt(var3);
                    var4.append(var9.method_638(var5) ? var5 : '?');
                }

                return var4.toString();
            }
        }

        return var8;
    }

    private TargetHudFormatter() {
    }

    private static String method_1510(String var0, TargetHudFormatter.Token var1, HealthReadout var2) {
        byte var5;
        label48: {
            String var3 = var0.toLowerCase(Locale.ROOT);
            byte var10 = -1;
            switch (var3.hashCode()) {
                case -1221262756:
                    if (var3.equals("health")) {
                        boolean var15 = true;
                        var5 = 1;
                        break label48;
                    }
                    break;
                case -1221256979:
                    if (var3.equals("hearts")) {
                        byte var14 = 3;
                        var5 = 3;
                        break label48;
                    }
                    break;
                case -892481550:
                    if (var3.equals("status")) {
                        var10 = 5;
                    }
                    break;
                case 107876:
                    if (var3.equals("max")) {
                        byte var13 = 2;
                        var5 = 2;
                        break label48;
                    }
                    break;
                case 3373707:
                    if (var3.equals("name")) {
                        boolean var12 = false;
                        var5 = 0;
                        break label48;
                    }
                    break;
                case 288459765:
                    if (var3.equals("distance")) {
                        byte var11 = 4;
                        var5 = 4;
                        break label48;
                    }
            }

            var5 = var10;
        }

        switch (var5) {
            case 0:
                return var1.string2();
            case 1:
                float var7;
                if (!var2.method_1502()) {
                    var7 = var1.floatValue3();
                } else {
                    var7 = var1.floatValue3() / 2.0F;
                }

                return method_1508(var7);
            case 2:
                float var9;
                if (!var2.method_1502()) {
                    var9 = var1.floatValue();
                } else {
                    var9 = var1.floatValue() / 2.0F;
                }

                return method_1508(var9);
            case 3:
                return method_1508(var1.floatValue3() / 2.0F);
            case 4:
                return String.valueOf(Math.round(var1.floatValue2()));
            case 5:
                return var1.string();
            default:
                return null;
        }
    }

    public static String method_1511(String var0, TargetHudFormatter.Token var1, HealthReadout var2) {
        String var3 = var0;
        if (var0 == null || var0.isEmpty()) {
            return "";
        } else if (var0.indexOf(123) < 0) {
            return var0;
        } else {
            StringBuilder var4 = new StringBuilder(var0.length() + 8);
            int var9 = 0;
            int var8 = 0;

            StringBuilder var10000;
            while (true) {
                if (var8 >= var3.length()) {
                    var10000 = var4;
                    break;
                }

                int var5;
                if ((var5 = var3.indexOf(123, var9)) < 0) {
                    var10000 = var4;
                    var4.append(var3, var9, var3.length());
                    break;
                }

                int var6;
                if ((var6 = var3.indexOf(125, var5 + 1)) < 0) {
                    var4.append(var3, var9, var3.length());
                    var10000 = var4;
                    break;
                }

                var4.append(var3, var9, var5);
                String var7 = method_1510(var3.substring(var5 + 1, var6), var1, var2);
                var4.append(var7 != null ? var7 : var3.substring(var5, var6 + 1));
                var9 = var6 + 1;
                var8 = var9;
            }

            return var10000.toString();
        }
    }

    public record Token(String string2, float floatValue3, float floatValue, float floatValue2, String string) {
    }
}
