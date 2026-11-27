package client.onyx.module.render;

import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.setting.impl.NumberSetting;

public final class ItemPhysics extends Module {
    public final NumberSetting rotationSpeed = new NumberSetting("Rotation speed", 0.0, 0.0, 3.0, 0.1).method_147("x");

    public ItemPhysics() {
        super("ItemPhysics", "Makes dropped items lie and rotate on the ground", Category.RENDER);
    }
}
