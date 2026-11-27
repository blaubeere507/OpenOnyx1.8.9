package client.onyx.util;

public class Timer {
    private long longValue;

    public void setLongValue(long var1) {
        this.longValue = var1;
    }

    public long method_2015() {
        return System.currentTimeMillis() - this.longValue;
    }

    public void method_2016(long var1) {
        this.longValue = Math.max(this.longValue, System.currentTimeMillis() + var1);
    }

    public void method_2017() {
        this.setLongValue(System.currentTimeMillis());
    }

    public boolean method_2018(long var1) {
        return this.longValue + var1 <= System.currentTimeMillis();
    }

    public boolean method_2019(long var1) {
        return this.longValue + var1 < System.currentTimeMillis();
    }

    public long method_2020(long var1) {
        return var1 - this.longValue;
    }

    public Timer() {
        this(0L);
    }

    public Timer(long var1) {
        this.longValue = var1;
    }

    public boolean method_2021() {
        return this.method_2019(0L);
    }
}
