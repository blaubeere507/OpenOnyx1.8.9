package client.onyx.util;

import java.util.Comparator;

public class ChainedComparator<T> implements Comparator<T> {
    private final Comparator<? super T>[] comparatorArray;

    @SafeVarargs
    public ChainedComparator(Comparator<? super T>... var1) {
        this.comparatorArray = var1;
    }

    @Override
    public int compare(T var1, T var2) {
        Comparator[] var5 = this.comparatorArray;
        int var4 = this.comparatorArray.length;
        int var8 = 0;

        for (int var7 = 0; var7 < var4; var7 = ++var8) {
            int var6;
            if ((var6 = var5[var8].compare(var1, var2)) != 0) {
                return var6;
            }
        }

        return 0;
    }
}
