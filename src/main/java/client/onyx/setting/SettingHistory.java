package client.onyx.setting;

import java.util.ArrayDeque;
import java.util.Deque;

public final class SettingHistory {
    private static final Deque<SettingHistory.Entry> undoStack = new ArrayDeque<>();
    private static final Deque<SettingHistory.Entry> redoStack = new ArrayDeque<>();
    private static boolean replaying;
    private static final int MAX_ENTRIES = 96;
    private static final long MERGE_WINDOW_MS = 450L;
    private static boolean enabled;

    public static void runWithoutRecording(Runnable var0) {
        Runnable var1 = var0;
        boolean var5 = replaying;
        replaying = true;

        try {
            var1.run();
        } finally {
            replaying = var5;
        }
    }

    private SettingHistory() {
    }

    public static boolean undo() {
        if (undoStack.isEmpty()) {
            return false;
        } else {
            SettingHistory.Entry var0;
            runWithoutRecording((var0 = undoStack.pop()).undo()::run);
            redoStack.push(var0);
            return true;
        }
    }

    // $VF: Could not properly define all variable types!
    // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
    public static void record(Object var0, SettingHistory.Action var1, SettingHistory.Action var2) {
        if (enabled && !replaying) {
            long var3 = System.currentTimeMillis();
            SettingHistory.Entry last; // hand-fixed: obfuscated local reuse confused the decompiler
            if ((last = undoStack.peek()) != null && var0 != null && last.target() == var0 && var3 - last.time() <= 450L) {
                undoStack.pop();
                undoStack.push(new SettingHistory.Entry(var0, last.undo(), var2, var3));
            } else {
                undoStack.push(new SettingHistory.Entry(var0, var1, var2, var3));

                while (undoStack.size() > 96) {
                    undoStack.removeLast();
                }
            }

            redoStack.clear();
        }
    }

    public static boolean redo() {
        if (redoStack.isEmpty()) {
            return false;
        } else {
            SettingHistory.Entry var0;
            runWithoutRecording((var0 = redoStack.pop()).redo()::run);
            undoStack.push(var0);
            return true;
        }
    }

    public static void clear() {
        undoStack.clear();
        redoStack.clear();
    }

    public static void disable() {
        enabled = false;
        clear();
    }

    public static void enable() {
        enabled = true;
    }

    public interface Action {
        void run();
    }

    private record Entry(Object target, SettingHistory.Action undo, SettingHistory.Action redo, long time) {
    }
}
