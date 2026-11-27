package client.onyx.util;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public final class Workers {
    private static final AtomicInteger atomicInteger = new AtomicInteger();
    private static final ExecutorService executorService = Executors.newCachedThreadPool(new ThreadFactory() {
        @Override
        public Thread newThread(Runnable var1) {
            Thread var10000 = new Thread(var1, "onyx-worker-" + Workers.atomicInteger.incrementAndGet());
            var10000.setDaemon(true);
            return var10000;
        }
    });

    private Workers() {
    }

    public static Executor method_1935() {
        return executorService;
    }

    public static void method_1936(Runnable var0) {
        executorService.execute(var0);
    }
}
