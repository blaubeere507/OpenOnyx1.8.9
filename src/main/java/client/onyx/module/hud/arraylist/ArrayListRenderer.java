package client.onyx.module.hud.arraylist;

import client.onyx.render.anim.Animation;

public final class ArrayListRenderer {
    public final Animation animation = new Animation(0.0F);
    public final Animation animation2;
    public String string;
    public boolean flag;
    public int intValue;
    public float floatValue;

    public ArrayListRenderer(float var1) {
        this.animation2 = new Animation(var1);
    }
}
