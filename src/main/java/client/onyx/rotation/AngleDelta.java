package client.onyx.rotation;

public record AngleDelta(float floatValue, float floatValue2) {
    public float method_1046() {
        return (float)Math.hypot(this.floatValue, this.floatValue2);
    }
}
