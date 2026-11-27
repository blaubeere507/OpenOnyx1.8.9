package client.onyx.util;

import java.util.regex.Pattern;

public final class TextFormatting {
    private static final Pattern pattern = Pattern.compile("(?i)§[0-9A-FK-OR]");

    public static String method_2142(String var0) {
        if (var0 != null && var0.indexOf(167) >= 0) {
            return pattern.matcher(var0).replaceAll("").replace("§", "");
        } else {
            return var0 == null ? "" : var0;
        }
    }

    private TextFormatting() {
    }
}
