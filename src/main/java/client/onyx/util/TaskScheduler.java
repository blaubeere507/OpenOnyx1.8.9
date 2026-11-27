package client.onyx.util;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;
import net.minecraft.client.Minecraft;

public class TaskScheduler<T> {
    private final PriorityBlockingQueue<TaskScheduler.Task<T>> priorityBlockingQueue = new PriorityBlockingQueue<>(
        11, Comparator.comparingInt(var0 -> -var0.getIntValue2())
    );
    private int intValue;

    public T method_1928() {
        var var1 = this;
        TaskScheduler.Task var3;
        if ((var3 = this.priorityBlockingQueue.peek()) == null) {
            return null;
        } else {
            Minecraft var2;
            if ((var2 = Minecraft.getMinecraft()) == null || var2.isCallingFromMinecraftThread()) {
                while (var3.intValue <= var1.intValue || !var3.getToggleable().isEnabled()) {
                    var1.priorityBlockingQueue.remove();
                    if ((var3 = var1.priorityBlockingQueue.peek()) == null) {
                        return null;
                    }
                }
            }

            return (T)var3.getObject();
        }
    }

    public void method_1929() {
        this.method_1931(1);
    }

    public void method_1930(TaskScheduler.Task<T> var1) {
        this.priorityBlockingQueue.removeIf(var1x -> var1x.getToggleable() == var1.getToggleable());
        var1.intValue = var1.intValue + this.intValue;
        this.priorityBlockingQueue.add(var1);
    }

    public void method_1931(int var1) {
        this.intValue += var1;
    }

    public static class Task<T> {
        private int intValue;
        private final int intValue2;
        private final Toggleable toggleable;
        private final T object;

        public T getObject() {
            return this.object;
        }

        public Task(int var1, int var2, Toggleable var3, T var4) {
            this.intValue = var1;
            this.intValue2 = var2;
            this.toggleable = var3;
            this.object = (T)var4;
        }

        public int getIntValue2() {
            return this.intValue2;
        }

        public int getIntValue() {
            return this.intValue;
        }

        public Toggleable getToggleable() {
            return this.toggleable;
        }
    }
}
