package client.onyx.proxy;

import client.onyx.account.ProxyConfig;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public final class LocalProxyBridge implements Closeable {
    private static final int INT_327681 = 32768;
    private static final int INT_100001 = 10000;
    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
    private final Set<Socket> set = ConcurrentHashMap.newKeySet();
    private final Consumer<String> consumer;
    private final ServerSocket serverSocket;
    private volatile LocalProxyBridge.Upstream upstream;
    private volatile boolean flag;

    private static void method_242(Socket var0, Exception var1) {
        Exception var5 = var1;
        Socket var7 = var0;

        try {
            byte[] var6 = method_256(var5).getBytes(StandardCharsets.UTF_8);
            String var2 = new StringBuilder()
                .insert(0, "HTTP/1.1 502 Bad Gateway\r\nContent-Type: text/plain; charset=utf-8\r\nContent-Length: ")
                .append(var6.length)
                .append("\r\nConnection: close\r\n\r\n")
                .toString();
            OutputStream var3 = var7.getOutputStream();
            var3.write(var2.getBytes(StandardCharsets.US_ASCII));
            var3.write(var6);
            var3.flush();
        } catch (IOException var4) {
        }
    }

    private static void method_243(Socket var0, Socket var1, boolean var2) {
        Socket var4 = var1;
        var1 = var0;

        try {
            var1.getInputStream().transferTo(var4.getOutputStream());
            var4.getOutputStream().flush();
            if (var2 && !var4.isOutputShutdown()) {
                var4.shutdownOutput();
                return;
            }
        } catch (IOException var3) {
        }
    }

    private static LocalProxyBridge.ConnectTarget method_244(InputStream var0) throws IOException {
        int var2;
        String var3;
        String var4;
        if ((var2 = (var4 = method_246(var0)).indexOf("\r\n")) < 0) {
            var3 = var4;
        } else {
            var3 = var4.substring(0, var2);
        }

        String[] var5;
        if ((var5 = var3.split("\\s+", 3)).length >= 3 && var5[0].equalsIgnoreCase("CONNECT")) {
            return LocalProxyBridge.ConnectTarget.method_241(var5[1]);
        } else {
            throw new IOException("Proxy bridge only supports HTTPS CONNECT requests");
        }
    }

    private static void method_245(OutputStream var0, int var1) throws IOException {
        var0.write(var1 >>> 8 & 0xFF);
        var0.write(var1 & 0xFF);
    }

    public LocalProxyBridge(ProxyConfig.Protocol var1, InetSocketAddress var2, String var3, String var4, Consumer<String> var5) throws IOException {
        this.consumer = var5;
        this.method_250(var1, var2, var3, var4);
        this.serverSocket = new ServerSocket();
        this.serverSocket.bind(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 0), 32);
        this.executorService.execute(() -> {
            LocalProxyBridge var1x = this;

            label30:
            while (true) {
                for (LocalProxyBridge var2x = var1x; !var2x.flag; var2x = var1x) {
                    try {
                        Socket var5x = var1x.serverSocket.accept();
                        var1x.set.add(var5x);
                        var1x.executorService.execute(() -> this.method_258(var5x));
                    } catch (SocketException var3x) {
                        if (var1x.flag) {
                            continue label30;
                        }

                        throw new IllegalStateException("Proxy bridge stopped unexpectedly", var3x);
                    } catch (IOException var4x) {
                        if (var1x.flag) {
                            continue label30;
                        }

                        throw new IllegalStateException("Proxy bridge failed to accept a connection", var4x);
                    }
                }

                return;
            }
        });
    }

    private static String method_246(InputStream var0) throws IOException {
        InputStream var3 = var0;
        ByteArrayOutputStream var1 = new ByteArrayOutputStream();
        byte var2 = 0;

        while (var1.size() < 32768) {
            int var5;
            if ((var5 = var3.read()) < 0) {
                throw new IOException("Proxy connection closed while reading headers");
            }

            var1.write(var5);

            byte var4 = switch (var2) {
                case 0 -> {
                    if (var5 == 13) {
                        yield 1;
                    } else {
                        yield 0;
                    }
                }
                case 1 -> {
                    if (var5 == 10) {
                        yield 2;
                    } else if (var5 == 13) {
                        yield 1;
                    } else {
                        yield 0;
                    }
                }
                case 2 -> {
                    if (var5 == 13) {
                        yield 3;
                    } else {
                        yield 0;
                    }
                }
                case 3 -> {
                    if (var5 == 10) {
                        yield 4;
                    } else {
                        yield 0;
                    }
                }
                default -> var2;
            };

            var2 = var4;
            if (var4 == 4) {
                return var1.toString(StandardCharsets.US_ASCII);
            }
        }

        throw new IOException("Proxy request headers are too large");
    }

    @Override
    public void close() {
        this.flag = true;
        method_249(this.serverSocket);

        Iterator var3;
        for (Iterator var2 = var3 = this.set.iterator(); var2.hasNext(); var2 = var3) {
            method_249((Socket)var3.next());
        }

        this.set.clear();
        this.executorService.shutdownNow();
    }

    private static String method_247(int var0) {
        switch (var0) {
            case 1:
                return "general failure";
            case 2:
                return "connection not allowed by ruleset";
            case 3:
                return "network unreachable";
            case 4:
                return "host unreachable";
            case 5:
                return "connection refused";
            case 6:
                return "TTL expired";
            case 7:
                return "command not supported";
            case 8:
                return "address type not supported";
            default:
                return new StringBuilder().insert(0, "status ").append(var0).toString();
        }
    }

    private Socket method_248(LocalProxyBridge.ConnectTarget var1) throws IOException {
        var var3 = this;
        LocalProxyBridge.Upstream var2 = this.upstream;
        Socket var5 = new Socket();
        this.set.add(var5);

        try {
            var5.setTcpNoDelay(true);
            var5.connect(var2.inetSocketAddress(), 10000);
            var5.setSoTimeout(10000);
            if (var2.protocol() == ProxyConfig.Protocol.SOCKS5) {
                var3.method_257(var5, var1, var2);
                return var5;
            } else {
                method_255(var5, var1, var2);
                return var5;
            }
        } catch (RuntimeException | IOException var4) {
            this.set.remove(var5);
            method_249(var5);
            throw var4;
        }
    }

    private static void method_249(Closeable var0) {
        if (var0 != null) {
            try {
                var0.close();
            } catch (IOException var1) {
            }
        }
    }

    public void method_250(ProxyConfig.Protocol var1, InetSocketAddress var2, String var3, String var4) {
        LocalProxyBridge.Upstream var10001;
        String var10005;
        String var10006;
        if (var3 == null) {
            var10005 = "";
            var10006 = var4;
        } else {
            var10005 = var3;
            var10006 = var4;
        }

        var10001 = new LocalProxyBridge.Upstream(var1, var2, var10005, var10006 == null ? "" : var4);
        this.upstream = var10001;
    }

    private static int method_251(InputStream var0) throws IOException {
        int var2;
        if ((var2 = var0.read()) < 0) {
            throw new IOException("Proxy connection closed unexpectedly");
        } else {
            return var2;
        }
    }

    private static byte[] method_252(InputStream var0, int var1) throws IOException {
        byte[] var3;
        if ((var3 = var0.readNBytes(var1)).length != var1) {
            throw new IOException("Proxy connection closed unexpectedly");
        } else {
            return var3;
        }
    }

    private static void method_253(InputStream var0, int var1) throws IOException {
        method_252(var0, switch (var1) {
            case 1 -> 4;
            default -> throw new IOException("SOCKS5 proxy returned an invalid address type");
            case 3 -> method_251(var0);
            case 4 -> 16;
        });
    }

    private static void method_254(InputStream var0, OutputStream var1, LocalProxyBridge.Upstream var2) throws IOException {
        byte[] var5 = var2.string2().getBytes(StandardCharsets.UTF_8);
        byte[] var3 = var2.string().getBytes(StandardCharsets.UTF_8);
        if (var5.length != 0 && var5.length <= 255 && var3.length <= 255) {
            var1.write(1);
            var1.write(var5.length);
            var1.write(var5);
            var1.write(var3.length);
            var1.write(var3);
            var1.flush();
            if (method_251(var0) != 1 || method_251(var0) != 0) {
                throw new IOException("SOCKS5 proxy rejected the username or password");
            }
        } else {
            throw new IOException("SOCKS5 credentials must be between 1 and 255 bytes");
        }
    }

    private static void method_255(Socket var0, LocalProxyBridge.ConnectTarget var1, LocalProxyBridge.Upstream var2) throws IOException {
        int var11; // declared by DeclareMissing
        String var7 = var1.method_240();
        StringBuilder var8 = new StringBuilder()
            .insert(0, "CONNECT ")
            .append(var7)
            .append(" HTTP/1.1\r\n")
            .append("Host: ")
            .append(var7)
            .append("\r\n")
            .append("Proxy-Connection: keep-alive\r\n");
        if (!var2.string2().isBlank()) {
            String var3 = new StringBuilder().insert(0, var2.string2()).append(":").append(var2.string()).toString();
            var8.append("Proxy-Authorization: Basic ").append(Base64.getEncoder().encodeToString(var3.getBytes(StandardCharsets.UTF_8))).append("\r\n");
        }

        var8.append("\r\n");
        OutputStream var10002 = var0.getOutputStream();
        var10002.write(var8.toString().getBytes(StandardCharsets.US_ASCII));
        var10002.flush();
        String var9 = method_246(var0.getInputStream());
        String[] var10 = var9.substring(0, var9.indexOf("\r\n")).split("\\s+", 3);
        if (var10.length < 2) {
            throw new IOException("HTTP proxy returned an invalid response");
        } else {
            try {
                var11 = Integer.parseInt(var10[1]);
            } catch (NumberFormatException var5) {
                throw new IOException("HTTP proxy returned an invalid status", var5);
            }

            if (var11 < 200 || var11 >= 300) {
                throw new IOException(new StringBuilder().insert(0, "HTTP proxy CONNECT failed: ").append(var11).toString());
            }
        }
    }

    private static String method_256(Exception var0) {
        return var0.getMessage() == null ? "Proxy connection failed" : var0.getMessage();
    }

    private void method_257(Socket var1, LocalProxyBridge.ConnectTarget var2, LocalProxyBridge.Upstream var3) throws IOException {
        InputStream var21 = var1.getInputStream();
        OutputStream var4 = var1.getOutputStream();
        boolean var8;
        if (!var3.string2().isBlank()) {
            var8 = true;
        } else {
            var8 = false;
        }

        byte[] var10001 = new byte[]{5, 1, 0};
        OutputStream var13;
        byte[] var14;
        byte[] var15;
        byte var16;
        byte var17;
        if (!var8) {
            byte var12 = 2;
            byte[] var11 = var10001;
            byte[] var10 = var10001;
            var17 = 0;
            var16 = var12;
            var15 = var11;
            var14 = var10;
            var13 = var4;
        } else {
            var17 = 2;
            var16 = 2;
            var15 = var10001;
            var14 = var10001;
            var13 = var4;
        }

        var15[var16] = (byte)var17;
        var13.write(var14);
        var4.flush();
        int var6 = method_251(var21);
        int var7 = method_251(var21);
        if (var6 == 5 && var7 != 255) {
            LocalProxyBridge.ConnectTarget var18;
            if (var7 == 2) {
                method_254(var21, var4, var3);
                var18 = var2;
            } else {
                if (var7 != 0 || var8) {
                    throw new IOException("SOCKS5 proxy did not accept username/password authentication");
                }

                var18 = var2;
            }

            byte[] var19;
            if ((var19 = var18.string().getBytes(StandardCharsets.UTF_8)).length != 0 && var19.length <= 255) {
                var4.write(5);
                var4.write(1);
                var4.write(0);
                var4.write(3);
                var4.write(var19.length);
                var4.write(var19);
                method_245(var4, var2.intValue());
                var4.flush();
                int var22 = method_251(var21);
                int var20 = method_251(var21);
                method_251(var21);
                method_253(var21, method_251(var21));
                method_252(var21, 2);
                if (var22 != 5 || var20 != 0) {
                    throw new IOException(new StringBuilder().insert(0, "SOCKS5 CONNECT failed: ").append(method_247(var20)).toString());
                }
            } else {
                throw new IOException("SOCKS5 target host is invalid");
            }
        } else {
            throw new IOException("SOCKS5 proxy rejected authentication methods");
        }
    }

    // $VF: Could not inline inconsistent finally blocks
    // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
    private void method_258(Socket var1) {
        Socket var9 = var1;
        var var10 = this;
        Socket var2 = null;

        label41: {
            try {
                try {
                    var9.setTcpNoDelay(true);
                    var9.setSoTimeout(10000);
                    LocalProxyBridge.ConnectTarget var3 = method_244(var9.getInputStream());
                    var2 = var10.method_248(var3);
                    OutputStream var4 = var9.getOutputStream();
                    var4.write("HTTP/1.1 200 Connection Established\r\n\r\n".getBytes(StandardCharsets.US_ASCII));
                    var4.flush();
                    var9.setSoTimeout(0);
                    var2.setSoTimeout(0);
                    Socket var11 = var2;
                    Future var12 = var10.executorService.submit(() -> method_243(var9, var11, true));
                    method_243(var11, var9, false);
                    var12.cancel(true);
                    break label41;
                } catch (Exception var7) {
                    if (var10.consumer != null) {
                        var10.consumer.accept(method_256(var7));
                    }

                    method_242(var9, var7);
                }
            } catch (Throwable var8) {
                this.set.remove(var2);
                this.set.remove(var1);
                method_249(var2);
                method_249(var1);
                throw var8;
            }

            this.set.remove(var2);
            this.set.remove(var1);
            method_249(var2);
            method_249(var1);
            return;
        }

        this.set.remove(var2);
        this.set.remove(var1);
        method_249(var2);
        method_249(var1);
    }

    public InetSocketAddress method_259() {
        return (InetSocketAddress)this.serverSocket.getLocalSocketAddress();
    }

    private record ConnectTarget(String string, int intValue) {
        private String method_240() {
            String var2;
            if (this.string.indexOf(58) >= 0) {
                var2 = new StringBuilder().insert(0, "[").append(this.string).append("]").toString();
            } else {
                var2 = this.string;
            }

            return new StringBuilder().insert(0, var2).append(":").append(this.intValue).toString();
        }

        // $VF: Handled exception range with multiple entry points by splitting it
        // $VF: Duplicated exception handlers to handle obfuscated exceptions
        private static LocalProxyBridge.ConnectTarget method_241(String var0) throws IOException {
            String var1;
            String var4;
            if (var0.startsWith("[")) {
                int var7;
                if ((var7 = var0.indexOf(93)) < 0 || var7 + 2 > var0.length() || var0.charAt(var7 + 1) != ':') {
                    throw new IOException("Invalid CONNECT target");
                }

                var1 = var0.substring(1, var7);
                var4 = var0.substring(var7 + 2);
            } else {
                int var8;
                if ((var8 = var0.lastIndexOf(58)) <= 0 || var8 == var0.length() - 1) {
                    throw new IOException("Invalid CONNECT target");
                }

                var1 = var0.substring(0, var8);
                String var2 = var0.substring(var8 + 1);

                try {
                    var4 = var2;
                } catch (NumberFormatException var6) {
                    throw new IOException("Invalid CONNECT target port", var6);
                }
            }

            try {
                int var9;
                if ((var9 = Integer.parseInt(var4)) >= 1 && var9 <= 65535) {
                    return new LocalProxyBridge.ConnectTarget(var1, var9);
                } else {
                    throw new IOException("Invalid CONNECT target port");
                }
            } catch (NumberFormatException var5) {
                throw new IOException("Invalid CONNECT target port", var5);
            }
        }
    }

    private record Upstream(ProxyConfig.Protocol protocol, InetSocketAddress inetSocketAddress, String string2, String string) {
    }
}
