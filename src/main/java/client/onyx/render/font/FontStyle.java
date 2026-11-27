package client.onyx.render.font;

public record FontStyle(FontFiles.Size size, FontFiles.Weight weight, float floatValue2, float floatValue) {
    public float method_642() {
        return this.method_643() + this.method_644();
    }

    public float method_643() {
        return this.method_646().getFloatValue() * this.method_645();
    }

    public float method_644() {
        return this.method_646().getFloatValue3() * this.method_645();
    }

    public float method_645() {
        return this.floatValue2 / this.size.getFloatValue();
    }

    public GlyphAtlasFont method_646() {
        return FontFiles.method_625(this.size, this.weight);
    }
}
