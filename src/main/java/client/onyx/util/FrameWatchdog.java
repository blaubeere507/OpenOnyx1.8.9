package client.onyx.util;

import client.onyx.Onyx;
import java.lang.Thread.State;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public final class FrameWatchdog {
    private static final Map<String, int[]> map = new HashMap<>();
    private static volatile long longValue = System.nanoTime();
    private static final long LONG_2501 = 250L;
    private static final String STRING_CONST = "Client thread";
    private static final long LONG_100000000001 = 10000000000L;
    private static volatile boolean flag;
    private static final long LONG_150000000001 = 15000000000L;
    private static final float FLOAT_8_01 = 8.0F;
    private static final long LONG_30000000001 = 3000000000L;
    private static volatile long longValue2;
    private static final long LONG_50000000001 = 5000000000L;
    private static final int INT_401 = 40;
    private static final int INT_121 = 12;

    private static void method_2087(StackTraceElement[] var0) {
        if (var0.length != 0) {
            method_2092(var0[0].toString());
            StackTraceElement[] var1 = var0;
            int var2 = var0.length;
            int var6 = 0;

            for (int var5 = 0; var5 < var2; var5 = ++var6) {
                StackTraceElement var4;
                if ((var4 = var1[var6]).getClassName().startsWith("client.onyx.")) {
                    method_2092(new StringBuilder().insert(0, "onyx: ").append(var4).toString());
                    return;
                }
            }
        }
    }

    private static Thread method_2088() {
        for (Thread var1 : Thread.getAllStackTraces().keySet()) {
            if ("Client thread".equals(var1.getName())) {
                return var1;
            }
        }

        return null;
    }

    private static void method_2089(String var0, Thread var1) {
        MemoryMXBean var10000 = ManagementFactory.getMemoryMXBean();
        long var3 = var10000.getHeapMemoryUsage().getUsed() >> 20;
        long var5 = var10000.getHeapMemoryUsage().getMax() >> 20;
        StringBuilder var8 = new StringBuilder(2048);
        var8.append(var0).append(" — heap ").append(var3).append('/').append(var5).append(" MB");
        StringBuilder var10;
        if (var1 == null) {
            var10 = var8;
            var8.append("\n  the client thread is gone");
        } else {
            Map var9 = Thread.getAllStackTraces();
            method_2094(var8, var1, (StackTraceElement[])var9.get(var1));
            if (var1.getState() == State.BLOCKED || var1.getState() == State.WAITING) {
                Iterator var7 = var9.entrySet().iterator();

                while (var7.hasNext()) {
                    Entry var4;
                    if ((var4 = (Entry)var7.next()).getKey() != var1) {
                        method_2094(var8, (Thread)var4.getKey(), (StackTraceElement[])var4.getValue());
                    }
                }
            }

            var10 = var8;
        }

        method_2090(var10);
        Onyx.LOGGER.error(var8.toString());
    }

    private static void method_2090(StringBuilder var0) {
        StringBuilder var3 = var0;
        if (!map.isEmpty()) {
            ArrayList var4;
            (var4 = new ArrayList<>(map.entrySet())).sort(Comparator.<Entry>comparingInt(var0x -> ((int[])var0x.getValue())[0]).reversed());
            int var2 = 0;

            Iterator var7;
            for (Iterator var5 = var7 = map.values().iterator(); var5.hasNext(); var5 = var7) {
                int[] var1 = (int[])var7.next();
                var2 += var1[0];
            }

            var0.append("\n  where the time went, ").append(var2).append(" samples:");
            int var8 = 0;

            for (int var6 = var8; var6 < Math.min(12, var4.size()); var6 = var8) {
                Entry var9 = (Entry)var4.get(var8);
                StringBuilder var10000 = var3.append("\n      ").append(((int[])var9.getValue())[0]).append("x  ");
                var8++;
                var10000.append((String)var9.getKey());
            }

            map.clear();
        }
    }

    public static void method_2091() {
        if (!flag) {
            flag = true;
            Thread var0 = new Thread(() -> {
                long var0x = 0L;
                long var2 = 0L;
                long var4 = System.nanoTime();
                long var6 = longValue2;

                while (true) {
                    try {
                        Thread.sleep(250L);
                    } catch (InterruptedException var13) {
                        Thread.currentThread().interrupt();
                        return;
                    }

                    Thread var8;
                    if ((var8 = method_2088()) != null) {
                        method_2087(var8.getStackTrace());
                    }

                    long var9;
                    long var11;
                    if ((var11 = (var9 = System.nanoTime()) - longValue) >= 3000000000L) {
                        if (var0x == 0L || var9 - var0x >= 10000000000L) {
                            var0x = var9;
                            method_2089(new StringBuilder().insert(0, "No frame for ").append(var11 / 1000000L).append(" ms").toString(), var8);
                        }

                        var4 = var9;
                        var6 = longValue2;
                    } else {
                        var0x = 0L;
                        if (var9 - var4 >= 5000000000L) {
                            float var14 = (float)(longValue2 - var6) * 1.0E9F / (float)(var9 - var4);
                            var4 = var9;
                            var6 = longValue2;
                            if (!(var14 >= 8.0F) && var9 - var2 >= 15000000000L) {
                                var2 = var9;
                                method_2089(String.format("Frames have fallen to %.1f fps", var14), var8);
                            }
                        }
                    }
                }
            }, "onyx-freeze-watchdog");
            var0.setDaemon(true);
            var0.setPriority(1);
            var0.start();
            Onyx.LOGGER.warn("Freeze watchdog armed — a stall of {} s or a drop under {} fps will be reported here", 3L, 8);
        }
    }

    private FrameWatchdog() {
    }

    private static void method_2092(String var0) {
        map.computeIfAbsent(var0, var0x -> new int[1])[0]++;
    }

    public static void method_2093() {
        longValue = System.nanoTime();
        longValue2++;
    }

    private static void method_2094(StringBuilder var0, Thread var1, StackTraceElement[] var2) {
        StringBuilder var6 = var0;
        var0.append("\n  \"").append(var1.getName()).append("\" ").append(var1.getState());
        if (var2 != null) {
            int var5 = 0;

            for (int var3 = 0; var3 < Math.min(40, var2.length); var3 = var5) {
                var6.append("\n      at ").append(var2[var5++]);
            }
        }
    }
}
