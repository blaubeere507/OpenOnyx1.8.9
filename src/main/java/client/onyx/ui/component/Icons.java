package client.onyx.ui.component;

import client.onyx.module.Category;
import client.onyx.module.hud.island.Watermark;
import client.onyx.render.ui.UiRenderer;

public final class Icons {
    public static final String STRING_CONST = "\ue566";
    public static final String STRING_CONST2 = "\ue5c4";
    public static final String STRING_CONST3 = "\ue87d";
    public static final String STRING_CONST4 = "\ue7fe";
    public static final String STRING_CONST5 = "\ue640";
    public static final String STRING_CONST6 = "\ue7fd";
    public static final String STRING_CONST7 = "\ue9e0";
    public static final String STRING_CONST8 = "\ue5d5";
    public static final String STRING_CONST9 = "\ue8f4";
    public static final String STRING_CONST10 = "\ue5cd";
    public static final String STRING_CONST11 = "\ue8b6";
    public static final String STRING_CONST12 = "\ue5ca";
    public static final String STRING_CONST13 = "\uea77";
    public static final String STRING_CONST14 = "\ue001";
    public static final String STRING_CONST15 = "\ue9ba";
    private static final float FLOAT_0_061 = 0.06F;
    public static final String STRING_CONST16 = "\ue8b8";
    public static final String STRING_CONST17 = "\ue14d";
    public static final String STRING_CONST18 = "\ue872";
    public static final String STRING_CONST19 = "\ue7f4";
    public static final String STRING_CONST20 = "\ue73c";
    public static final String STRING_CONST21 = "\ue161";
    public static final String STRING_CONST22 = "\uea4b";
    public static final String STRING_CONST23 = "\ue88e";
    public static final String STRING_CONST24 = "\ue875";
    public static final String STRING_CONST25 = "\ue405";
    public static final String STRING_CONST26 = "\ue002";
    public static final String STRING_CONST27 = "\ue871";
    public static final String STRING_CONST28 = "\ue145";
    public static final String STRING_CONST29 = "\ue2c4";
    public static final String STRING_CONST30 = "\ue2c8";
    public static final String STRING_CONST31 = "\ue5d4";
    public static final String STRING_CONST32 = "\ue9e4";
    public static final String STRING_CONST33 = "\ue5cf";
    public static final String STRING_CONST34 = "\ue202";
    public static final String STRING_CONST35 = "\ue89e";
    public static final String STRING_CONST36 = "\ue312";

    public static void method_2221(UiRenderer var0, float var1, float var2, float var3, int var4) {
        var0.method_477("\ue5c4", var1, var2, var3, var4);
    }

    public static void method_2222(UiRenderer var0, float var1, float var2, float var3, int var4) {
        var0.method_477("\ue5cf", var1, var2, var3, var4);
    }

    public static void method_2223(UiRenderer var0, float var1, float var2, float var3, int var4) {
        var0.method_477("\ue5cd", var1, var2, var3, var4);
    }

    public static void method_2224(UiRenderer var0, Watermark.Stat var1, float var2, float var3, float var4, int var5) {
        var0.method_477(method_2225(var1), var2, var3, var4, var5);
    }

    private static String method_2225(Watermark.Stat var0) {
        switch (var0) {
            case FPS:
                return "\ue9e4";
            case USERNAME:
                return "\ue7fd";
            case PING:
                return "\ue640";
            case PACKET_LOSS:
                return "\ue202";
            case SERVER:
                return "\ue875";
            default:
                throw new MatchException(null, null);
        }
    }

    public static void method_2226(UiRenderer var0, float var1, float var2, float var3, int var4) {
        var0.method_470("\ue5ca", var1, var2, var3, var4, var3 * 0.06F);
    }

    private static String method_2227(Category var0) {
        switch (var0) {
            case COMBAT:
                return "\ue9e0";
            case MOVEMENT:
                return "\ue566";
            case PLAYER:
                return "\ue7fd";
            case RENDER:
                return "\ue8f4";
            case HUD:
                return "\ue871";
            default:
                throw new MatchException(null, null);
        }
    }

    public static void method_2228(UiRenderer var0, Category var1, float var2, float var3, float var4, int var5) {
        var0.method_477(method_2227(var1), var2, var3, var4, var5);
    }

    private Icons() {
    }

    public static void method_2229(UiRenderer var0, float var1, float var2, float var3, int var4) {
        var0.method_477("\ue8b6", var1, var2, var3, var4);
    }
}
