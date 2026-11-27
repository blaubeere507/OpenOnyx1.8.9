package client.onyx.util.geometry;

import java.util.Arrays;

public final class Polynomial {
    private double[] doubleValueArray;
    private int intValue;

    public double method_2031(int var1) {
        return this.doubleValueArray[var1];
    }

    public int getIntValue() {
        return this.intValue;
    }

    public void method_2032() {
        var var1 = this;
        if (this.intValue > 1) {
            int var8 = 1;

            for (int var6 = 1; var6 < var1.intValue; var6 = var8) {
                double var2 = var1.doubleValueArray[var8];
                int var4 = var8;

                for (int var5 = var8; var5 > 0 && var1.doubleValueArray[var4 - 1] > var2; var5 = var4) {
                    int var10001 = var4;
                    double var10002 = var1.doubleValueArray[var4 - 1];
                    var4--;
                    var1.doubleValueArray[var10001] = var10002;
                }

                var8++;
                var1.doubleValueArray[var4] = var2;
            }

            int var9 = 1;
            int var10 = 1;

            for (int var7 = 1; var7 < var1.intValue; var7 = ++var10) {
                double var3;
                if ((var3 = var1.doubleValueArray[var10]) != var1.doubleValueArray[var9 - 1]) {
                    var1.doubleValueArray[var9++] = var3;
                }
            }

            var1.intValue = var9;
        }
    }

    public Polynomial(int var1) {
        this.doubleValueArray = new double[var1];
    }

    public void method_2033(double var1) {
        if (Double.isFinite(var1)) {
            this.method_2034(var1);
        }
    }

    public void method_2034(double var1) {
        if (this.intValue == this.doubleValueArray.length) {
            this.doubleValueArray = Arrays.copyOf(this.doubleValueArray, Math.max(4, this.intValue * 2));
        }

        this.doubleValueArray[this.intValue++] = var1;
    }
}
