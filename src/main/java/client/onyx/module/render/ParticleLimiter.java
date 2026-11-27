package client.onyx.module.render;

import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.util.HypixelUtil;
import java.util.concurrent.ThreadLocalRandom;

public final class ParticleLimiter extends Module {
    public final NumberSetting keep = new NumberSetting("Keep", 5.0, 5.0, 100.0, 5.0).method_147("%");

    public ParticleLimiter() {
        super("Particle Limiter", "Thins vanilla particles in the Hypixel Bed Wars lobby", Category.RENDER);
    }

    public boolean method_1147() {
        return this.isToggled() && HypixelUtil.method_2179() && ThreadLocalRandom.current().nextInt(100) >= this.keep.method_149();
    }
}
