package client.onyx.ui.component;

import client.onyx.input.KeyTyped;
import client.onyx.input.MouseClick;
import client.onyx.render.anim.Animation;
import client.onyx.render.anim.Easings;
import client.onyx.render.font.FontStyle;
import client.onyx.render.ui.UiRenderer;
import client.onyx.ui.theme.Theme;

public abstract class TextField extends Component {
    private static final float FLOAT_3_01 = 3.0F;
    private static final long LONG_3201 = 320L;
    private static final float FLOAT_0_321 = 0.32F;
    private static final float FLOAT_1_21 = 1.2F;
    private static TextField INSTANCE;
    public final TextBuffer textBuffer = new TextBuffer();
    public final Animation animation = new Animation(0.0F);
    private UiRenderer uiRenderer;
    private boolean flag4;
    private float floatValue5;
    private long longValue;
    private int intValue;
    private float floatValue22;
    public boolean flag22;

    public String method_2404() {
        return this.textBuffer.getString();
    }

    private int method_2405(float var1) {
        return this.textBuffer.method_2448(var1 - this.method_2364() + this.floatValue22, this.method_2415());
    }

    @Override
    public boolean method_2193(MouseClick var1) {
        return this.flag22 && this.textBuffer.method_2447(var1);
    }

    public abstract float method_2364();

    @Override
    public void method_2206(RenderContext var1) {
        super.method_2206(var1);
        this.uiRenderer = var1.uiRenderer;
        this.animation.method_605(var1.floatValue);
        this.textBuffer.method_2452(var1.floatValue);
    }

    public static boolean method_2406() {
        return INSTANCE != null;
    }

    @Override
    public boolean method_2183(float var1, float var2, int var3) {
        if (var3 != 0) {
            return false;
        } else if (!this.method_2259(var1, var2)) {
            this.method_2407(false);
            return false;
        } else {
            return this.method_2411(var1, false);
        }
    }

    public void method_2407(boolean var1) {
        if (this.flag22 != var1) {
            this.flag22 = var1;
            this.flag4 = false;
            TextField var2;
            if (var1) {
                if (INSTANCE != null && INSTANCE != this) {
                    INSTANCE.method_2407(false);
                }

                INSTANCE = this;
                var2 = this;
            } else {
                if (INSTANCE == this) {
                    INSTANCE = null;
                }

                var2 = this;
            }

            var2.textBuffer.method_2422();
            if (!var1) {
                this.textBuffer.method_2444();
            }

            Animation var4;
            float var5;
            if (!var1) {
                Animation var3 = this.animation;
                var5 = 0.0F;
                var4 = var3;
            } else {
                var5 = 1.0F;
                var4 = this.animation;
            }

            var4.method_603(var5, 200.0F, Easings.easing6);
        }
    }

    public boolean method_2408() {
        return this.textBuffer.isFlag();
    }

    public abstract float method_2363();

    public void method_2409(String var1) {
        this.textBuffer.method_2437(var1);
        this.floatValue22 = 0.0F;
    }

    @Override
    public boolean method_2188(float var1, float var2, int var3, float var4, float var5) {
        if (this.flag4 && var3 == 0) {
            this.textBuffer.method_2430(this.method_2405(var1), true);
            return true;
        } else {
            return false;
        }
    }

    public static void method_2410() {
        if (INSTANCE != null) {
            INSTANCE.method_2407(false);
        }
    }

    public boolean method_2411(float var1, boolean var2) {
        long var3 = System.currentTimeMillis();
        boolean var6;
        if (Math.abs(var1 - this.floatValue5) <= 3.0F) {
            var6 = true;
        } else {
            var6 = false;
        }

        TextField var8;
        int var9;
        label27: {
            TextField var7;
            if (var3 - this.longValue > 320L) {
                var7 = this;
            } else {
                if (var6) {
                    var9 = this.intValue + 1;
                    var8 = this;
                    break label27;
                }

                var7 = this;
            }

            var9 = 0;
            var8 = var7;
        }

        var8.intValue = var9;
        this.longValue = var3;
        this.floatValue5 = var1;
        this.method_2407(true);
        if (this.intValue == 1) {
            this.textBuffer.method_2428(this.method_2405(var1));
            return true;
        } else if (this.intValue >= 2) {
            this.textBuffer.method_2423();
            return true;
        } else {
            this.textBuffer.method_2430(this.method_2405(var1), var2);
            this.flag4 = true;
            return true;
        }
    }

    public boolean isFlag22() {
        return this.flag22;
    }

    public float method_2412() {
        return Math.max(0.0F, this.method_2363() - this.method_2364());
    }

    public void method_2413() {
        this.textBuffer.method_2423();
    }

    public abstract FontStyle method_2369();

    public void method_2414(boolean var1) {
        this.textBuffer.method_2427(var1);
    }

    @Override
    public boolean method_2212(KeyTyped var1) {
        return this.flag22 && this.textBuffer.method_2434(var1);
    }

    private TextBuffer.ChangeListener method_2415() {
        UiRenderer var3 = this.uiRenderer;
        FontStyle var2 = this.method_2369();
        return var3 == null ? var0 -> 0.0F : var2x -> var3.method_442(var2, var2x);
    }

    @Override
    public boolean method_2189(float var1, float var2, int var3) {
        return this.flag4 = false;
    }

    public void method_2416(RenderContext var1, float var2, float var3, int var4) {
        UiRenderer var5 = var1.uiRenderer;
        TextBuffer.ChangeListener var6 = this.method_2415();
        String var7 = this.textBuffer.method_2429();
        float var11 = this.method_2412();
        float var9;
        if ((var9 = this.textBuffer.method_2449(var6)) - this.floatValue22 > var11) {
            this.floatValue22 = var9 - var11;
        }

        if (var9 - this.floatValue22 < 0.0F) {
            this.floatValue22 = var9;
        }

        this.floatValue22 = Math.clamp(this.floatValue22, 0.0F, Math.max(0.0F, var6.method_2417(var7) - var11));
        float var10 = this.method_2364() - this.floatValue22;
        var5.method_443(this.method_2364(), this.floatValue, var11, this.floatValue2);
        if (this.textBuffer.method_2443()) {
            float var12 = var10 + this.textBuffer.method_2426(this.textBuffer.method_2451(), var6);
            float var13 = var10 + this.textBuffer.method_2426(this.textBuffer.method_2446(), var6);
            var5.method_479(var12, var2 - var3 / 2.0F, var13 - var12, var3, 0.0F, Theme.method_180(var1.method_2256().intValue31(), 0.32F));
        }

        if (!var7.isEmpty()) {
            var5.method_432(this.method_2369(), var7, var10, var2, var4);
        }

        if (this.flag22 && this.textBuffer.method_2424()) {
            var5.method_479(var10 + var9, var2 - var3 / 2.0F, 1.2F, var3, Float.MAX_VALUE, var1.method_2256().intValue31());
        }

        var5.method_486();
    }
}
