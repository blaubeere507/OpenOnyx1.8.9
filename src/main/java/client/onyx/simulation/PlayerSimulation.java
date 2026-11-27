package client.onyx.simulation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PlayerSimulation {
    private final List<SimulatedTick> list = new ArrayList<>();
    private final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private final SimulatedPlayer simulatedPlayer;
    private int intValue;

    public SimulatedPlayer getSimulatedPlayer() {
        return this.simulatedPlayer;
    }

    public PlayerSimulation(SimulatedPlayer var1) {
        this.simulatedPlayer = var1;
        this.list.add(new SimulatedTick(var1));
    }

    public Iterator<SimulatedTick> method_292(final int var1, final int var2) {
        if (var2 >= 1200) {
            throw new IllegalStateException("tried to simulate a player for more than a minute!");
        } else {
            this.method_293(var2 + 1);
            return new Iterator<SimulatedTick>() {
                private int intValue = var1;

                @Override
                public boolean hasNext() {
                    return this.intValue <= var2;
                }

                public SimulatedTick next() {
                    return PlayerSimulation.this.method_294(this.intValue++);
                }
            };
        }
    }

    // $VF: Could not inline inconsistent finally blocks
    // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
    public void method_293(int var1) {
        int var4 = var1;
        var var5 = this;
        if (var1 < 0) {
            throw new IllegalStateException("ticks may not be negative");
        } else if (this.intValue < var1) {
            this.reentrantReadWriteLock.writeLock().lock();

            try {
                while (var5.intValue < var4) {
                    var5.simulatedPlayer.method_304();
                    var5.list.add(new SimulatedTick(var5.simulatedPlayer));
                    var5.intValue++;
                }
            } catch (Throwable var3) {
                var5.reentrantReadWriteLock.writeLock().unlock();
                throw var3;
            }

            var5.reentrantReadWriteLock.writeLock().unlock();
        }
    }

    // $VF: Could not inline inconsistent finally blocks
    // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
    public SimulatedTick method_294(int var1) {
        client.onyx.simulation.SimulatedTick var5; // declared by DeclareMissing
        int var4 = var1;
        var var6 = this;
        this.method_293(var1);
        this.reentrantReadWriteLock.readLock().lock();

        try {
            var5 = var6.list.get(var4);
        } catch (Throwable var3) {
            this.reentrantReadWriteLock.readLock().unlock();
            throw var3;
        }

        this.reentrantReadWriteLock.readLock().unlock();
        return var5;
    }

    // $VF: Could not inline inconsistent finally blocks
    // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
    public List<SimulatedTick> method_295(int var1, int var2) {
        java.util.List<client.onyx.simulation.SimulatedTick> var6; // declared by DeclareMissing
        int var5 = var1;
        var var7 = this;
        if (var2 >= 1200) {
            throw new IllegalStateException("tried to simulate a player for more than a minute!");
        } else {
            this.method_293(var2 + 1);
            this.reentrantReadWriteLock.readLock().lock();

            try {
                var6 = List.copyOf(var7.list.subList(var5, var2 + 1));
            } catch (Throwable var4) {
                this.reentrantReadWriteLock.readLock().unlock();
                throw var4;
            }

            this.reentrantReadWriteLock.readLock().unlock();
            return var6;
        }
    }

    public Iterator<SimulatedTick> method_296() {
        return new Iterator<SimulatedTick>() {
            private int intValue;

            public SimulatedTick next() {
                return PlayerSimulation.this.method_294(this.intValue++);
            }

            @Override
            public boolean hasNext() {
                return true;
            }
        };
    }
}
