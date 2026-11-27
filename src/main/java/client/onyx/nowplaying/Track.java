package client.onyx.nowplaying;

import net.minecraft.util.ResourceLocation;

public record Track(String string2, String string, ResourceLocation resourceLocation, boolean flag, int intValue2, int intValue, long longValue) {
    public float method_269() {
        if (this.intValue <= 0) {
            return 0.0F;
        } else {
            long var3;
            if (this.flag) {
                var3 = (System.nanoTime() - this.longValue) / 1000000L;
            } else {
                var3 = 0L;
            }

            return Math.clamp((float)Math.min((long)this.intValue, this.intValue2 + var3) / this.intValue, 0.0F, 1.0F);
        }
    }
}
