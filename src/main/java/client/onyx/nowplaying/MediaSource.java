package client.onyx.nowplaying;

public interface MediaSource {
    MediaSource.Snapshot method_270();

    public record Snapshot(String string3, String string, boolean flag, int intValue, int intValue2, String string2, byte[] byteValueArray) {
    }
}
