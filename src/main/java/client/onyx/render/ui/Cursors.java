package client.onyx.render.ui;

import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;

public final class Cursors {
    private static final String[] stringArray = new String[]{
        "     ##         ",
        "    #..#        ",
        "    #..#        ",
        "    #..#        ",
        "    #..#        ",
        "    #..# ##     ",
        "    #..##..#    ",
        "    #..#..#.#   ",
        " ## #..#..#..#  ",
        "#..##........#  ",
        "#............#  ",
        " #...........#  ",
        "  #..........#  ",
        "  #.........#   ",
        "   #........#   ",
        "   #########    "
    };
    private static final String[] stringArray2 = new String[]{
        "                ",
        "   ##  ##       ",
        "   #.##.#       ",
        "    #..#        ",
        "    #..#        ",
        "    #..#        ",
        "    #..#        ",
        "    #..#        ",
        "    #..#        ",
        "    #..#        ",
        "    #..#        ",
        "    #..#        ",
        "    #..#        ",
        "   #.##.#       ",
        "   ##  ##       ",
        "                "
    };
    private static Cursors.CursorType cursorType2 = Cursors.CursorType.DEFAULT;
    private static Cursors.CursorType cursorType = Cursors.CursorType.DEFAULT;
    private static final int INT_N11 = -1;
    private static boolean flag;
    private static final int INT_161 = 16;
    private static final int INT_01 = 0;
    private static Cursor cursor;
    private static Cursor cursor2;
    private static boolean flag2;
    private static final int INT_N167772161 = -16777216;

    private static void method_804() {
        if (!flag2) {
            flag2 = true;
            if ((Cursor.getCapabilities() & 1) != 0 && Cursor.getMinCursorSize() <= 16 && Cursor.getMaxCursorSize() >= 16) {
                cursor = method_808(stringArray, 5, 0);
                cursor2 = method_808(stringArray2, 5, 7);
                if (cursor == null || cursor2 == null) {
                    flag = true;
                }
            } else {
                flag = true;
            }
        }
    }

    private Cursors() {
    }

    public static void method_805() {
        cursorType2 = Cursors.CursorType.DEFAULT;
        if (!flag && cursorType != Cursors.CursorType.DEFAULT) {
            try {
                Mouse.setNativeCursor(null);
            } catch (RuntimeException | LWJGLException var1) {
            }

            cursorType = Cursors.CursorType.DEFAULT;
        }
    }

    public static void method_806(Cursors.CursorType var0) {
        if (var0.ordinal() > cursorType2.ordinal()) {
            cursorType2 = var0;
        }
    }

    public static void method_807() {
        Cursors.CursorType var0 = cursorType2;
        cursorType2 = Cursors.CursorType.DEFAULT;
        if (!flag && var0 != cursorType) {
            method_804();
            if (!flag) {
                Cursor var1 = switch (var0) {
                    case DEFAULT -> null;
                    case POINTER -> cursor;
                    case TEXT -> cursor2;
                };

                try {
                    Mouse.setNativeCursor(var1);
                    cursorType = var0;
                } catch (RuntimeException | LWJGLException var3) {
                    flag = true;
                }
            }
        }
    }

    private static Cursor method_808(String[] var0, int var1, int var2) {
        String[] var4 = var0;
        IntBuffer var3 = BufferUtils.createIntBuffer(256);
        int var15 = 15;

        for (int var13 = 15; var13 >= 0; var13 = --var15) {
            String var5 = var4[var15];
            int var6 = 0;

            for (int var12 = 0; var12 < 16; var12 = ++var6) {
                IntBuffer var10;
                int var11;
                switch (var5.charAt(var6)) {
                    case '#':
                        var11 = -16777216;
                        var10 = var3;
                        break;
                    case '.':
                        var11 = -1;
                        var10 = var3;
                        break;
                    default:
                        var11 = 0;
                        var10 = var3;
                }

                var10.put(var11);
            }
        }

        var3.flip();

        try {
            return new Cursor(16, 16, var1, 15 - var2, 1, var3, null);
        } catch (RuntimeException | LWJGLException var14) {
            return null;
        }
    }

    public static enum CursorType {
        DEFAULT,
        POINTER,
        TEXT;
    }
}
