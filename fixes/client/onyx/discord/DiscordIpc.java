package client.onyx.discord;

import client.onyx.Onyx;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Locale;

public final class DiscordIpc {
    private static final boolean flag2 = System.getProperty("os.name", "").toLowerCase(Locale.ROOT).contains("win");
    public static final int INT_21 = 2;
    public static final int INT_11 = 1;
    public static final int INT_01 = 0;
    private SocketChannel socketChannel;
    private RandomAccessFile randomAccessFile;
    private volatile boolean flag;

    private boolean method_2081(int var1) {
        var var2 = this;

        try {
            if (flag2) {
                var2.randomAccessFile = new RandomAccessFile(new StringBuilder().insert(0, "\\\\.\\pipe\\discord-ipc-").append(var1).toString(), "rw");
            } else {
                String var4 = method_2084(System.getenv("XDG_RUNTIME_DIR"), System.getenv("TMPDIR"), "/tmp");
                var2.socketChannel = SocketChannel.open(StandardProtocolFamily.UNIX);
                var2.socketChannel.connect(UnixDomainSocketAddress.of(Path.of(var4, new StringBuilder().insert(0, "discord-ipc-").append(var1).toString())));
            }

            return true;
        } catch (Exception var3) {
            this.method_2082();
            return false;
        }
    }

    public boolean isFlag() {
        return this.flag;
    }

    private void method_2082() {
        var var1 = this;
        this.flag = false;

        try {
            if (var1.randomAccessFile != null) {
                var1.randomAccessFile.close();
            }
        } catch (IOException var3) {
            Onyx.LOGGER.debug("Failed to close Discord IPC pipe", var3);
        }

        try {
            if (var1.socketChannel != null) {
                var1.socketChannel.close();
            }
        } catch (IOException var2) {
            Onyx.LOGGER.debug("Failed to close Discord IPC socket", var2);
        }

        this.randomAccessFile = null;
        this.socketChannel = null;
    }

    public void method_2083() {
        var var1 = this;
        boolean var3 = this.flag;
        this.flag = false;

        try {
            if (var3) {
                var1.method_2086(2, "{}");
            }
        } catch (IOException var2) {
        }

        this.method_2082();
    }

    private static String method_2084(String... var0) {
        String[] var1 = var0;
        int var2 = var0.length;
        int var6 = 0;

        for (int var5 = 0; var5 < var2; var5 = ++var6) {
            String var4;
            if ((var4 = var1[var6]) != null) {
                return var4;
            }
        }

        return "/tmp";
    }

    public boolean method_2085(String var1) {
        var var2 = this;
        int var5 = 0;

        for (int var3 = 0; var3 < 10; var3 = ++var5) {
            if (var2.method_2081(var5)) {
                try {
                    boolean var10006 = false;
                    var2.method_2086(0, "{\"v\":1,\"client_id\":\"" + var1 + "\"}");
                    var2.flag = true;
                    return true;
                } catch (IOException var4) {
                    var2.method_2082();
                }
            }
        }

        return false;
    }

    public void method_2086(int var1, String var2) throws IOException {
        var var3 = this;
        byte[] var7 = var2.getBytes(StandardCharsets.UTF_8);
        ByteBuffer var6 = ByteBuffer.allocate(8 + var7.length).order(ByteOrder.LITTLE_ENDIAN);
        var6.putInt(var1);
        var6.putInt(var7.length);
        var6.put(var7);
        var6.flip();
        if (this.randomAccessFile != null) {
            this.randomAccessFile.write(var6.array());
        } else if (this.socketChannel == null) {
            throw new IOException("Discord IPC is not connected");
        } else {
            for (ByteBuffer var5 = var6; var5.hasRemaining(); var5 = var6) {
                var3.socketChannel.write(var6);
            }
        }
    }
}
