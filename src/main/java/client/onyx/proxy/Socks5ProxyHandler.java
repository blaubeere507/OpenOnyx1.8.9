package client.onyx.proxy;

import client.onyx.Onyx;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class Socks5ProxyHandler extends ChannelDuplexHandler {
    private static final int INT_31 = 3;
    private static final int INT_21 = 2;
    private static final int INT_01 = 0;
    private static final int INT_51 = 5;
    private static final int INT_11 = 1;
    private static final int INT_12 = 1;
    private static final int INT_41 = 4;
    private static final int INT_13 = 1;
    private Socks5ProxyHandler.State state = Socks5ProxyHandler.State.CONNECTING;
    private final InetSocketAddress inetSocketAddress;
    private final String string;
    private final int intValue;
    private final String string2;
    private final String string3;
    private InetSocketAddress inetSocketAddress2;
    private ByteBuf byteBuf;
    private ScheduledFuture<?> scheduledFuture;
    private ChannelPromise channelPromise;

    @Override
    public void handlerRemoved(ChannelHandlerContext var1) {
        if (this.byteBuf != null) {
            this.byteBuf.release();
            this.byteBuf = null;
        }

        this.method_232();
    }

    private void method_230(ChannelHandlerContext var1) {
        byte[] var2;
        if ((var2 = this.string.getBytes(StandardCharsets.UTF_8)).length > 0 && var2.length <= 255) {
            ByteBuf var7 = Unpooled.buffer(7 + var2.length);
            var7.writeByte(5);
            var7.writeByte(1);
            var7.writeByte(0);
            var7.writeByte(3);
            var7.writeByte(var2.length);
            var7.writeBytes(var2);
            var7.writeShort(this.intValue);
            var1.writeAndFlush(var7);
        } else {
            byte var5;
            byte[] var6;
            if ((var6 = this.inetSocketAddress2.getAddress().getAddress()).length == 4) {
                var5 = 1;
            } else {
                var5 = 4;
            }

            ByteBuf var4;
            (var4 = Unpooled.buffer(6 + var6.length)).writeByte(5);
            var4.writeByte(1);
            var4.writeByte(0);
            var4.writeByte(var5);
            var4.writeBytes(var6);
            var4.writeShort(this.intValue);
            var1.writeAndFlush(var4);
        }
    }

    private boolean method_231(ChannelHandlerContext var1, ByteBuf var2) {
        switch (this.state) {
            case GREETING:
                return this.method_237(var1, var2);
            case AUTH:
                return this.method_239(var1, var2);
            case CONNECT:
                return this.method_233(var1, var2);
            default:
                return false;
        }
    }

    private void method_232() {
        if (this.scheduledFuture != null) {
            this.scheduledFuture.cancel(false);
            this.scheduledFuture = null;
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext var1) {
        if (this.state != Socks5ProxyHandler.State.CONNECTED) {
            this.method_235(var1, new IllegalStateException("SOCKS5 proxy closed the connection"));
        }

        var1.fireChannelInactive();
    }

    private boolean method_233(ChannelHandlerContext var1, ByteBuf var2) {
        if (var2.readableBytes() < 4) {
            return false;
        } else {
            var2.markReaderIndex();
            short var3 = var2.readUnsignedByte();
            short var4 = var2.readUnsignedByte();
            var2.skipBytes(1);

            short var6 = switch (var2.readUnsignedByte()) {
                case 1 -> 4;
                default -> -1;
                case 3 -> {
                    if (var2.isReadable()) {
                        yield var2.readUnsignedByte();
                    } else {
                        yield -1;
                    }
                }
                case 4 -> 16;
            };

            if (var6 < 0) {
                this.method_235(var1, new IllegalStateException("SOCKS5 proxy returned an invalid address"));
                return true;
            } else if (var2.readableBytes() < var6 + 2) {
                var2.resetReaderIndex();
                return false;
            } else {
                var2.skipBytes(var6 + 2);
                if (var3 == 5 && var4 == 0) {
                    this.state = Socks5ProxyHandler.State.CONNECTED;
                    ByteBuf var7;
                    if (var2.isReadable()) {
                        var7 = var2.readSlice(var2.readableBytes()).retain();
                    } else {
                        var7 = null;
                    }

                    this.method_232();
                    Onyx.LOGGER.info("SOCKS5 tunnel established to {}:{}", this.string, this.intValue);
                    var1.fireChannelActive();
                    this.channelPromise.trySuccess();
                    if (var7 != null) {
                        var1.fireChannelRead(var7);
                    }

                    return true;
                } else {
                    this.method_235(
                        var1, new IllegalStateException(new StringBuilder().insert(0, "SOCKS5 CONNECT failed: ").append(method_238(var4)).toString())
                    );
                    return true;
                }
            }
        }
    }

    private void method_234(ChannelHandlerContext var1) {
        boolean var4;
        if (!this.string2.isBlank()) {
            var4 = true;
        } else {
            var4 = false;
        }

        ByteBuf var8 = Unpooled.buffer(3);
        var8.writeByte(5);
        var8.writeByte(1);
        ByteBuf var6;
        byte var7;
        if (!var4) {
            var7 = 0;
            var6 = var8;
        } else {
            var7 = 2;
            var6 = var8;
        }

        var6.writeByte(var7);
        var1.writeAndFlush(var8);
    }

    private void method_235(ChannelHandlerContext var1, Throwable var2) {
        if (this.state != Socks5ProxyHandler.State.FAILED && this.state != Socks5ProxyHandler.State.CONNECTED) {
            this.state = Socks5ProxyHandler.State.FAILED;
            this.method_232();
            Onyx.LOGGER.warn("SOCKS5 tunnel to {}:{} failed: {}", this.string, this.intValue, var2.getMessage());
            if (this.channelPromise != null) {
                this.channelPromise.tryFailure(var2);
            }

            var1.close();
        }
    }

    private void method_236(ChannelHandlerContext var1) {
        byte[] var2 = this.string2.getBytes(StandardCharsets.UTF_8);
        byte[] var4 = this.string3.getBytes(StandardCharsets.UTF_8);
        if (var2.length != 0 && var2.length <= 255 && var4.length <= 255) {
            ByteBuf var5 = Unpooled.buffer(3 + var2.length + var4.length);
            var5.writeByte(1);
            var5.writeByte(var2.length);
            var5.writeBytes(var2);
            var5.writeByte(var4.length);
            var5.writeBytes(var4);
            var1.writeAndFlush(var5);
        } else {
            this.method_235(var1, new IllegalArgumentException("SOCKS5 credentials must be at most 255 bytes"));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext var1, Throwable var2) {
        this.method_235(var1, var2);
    }

    private boolean method_237(ChannelHandlerContext var1, ByteBuf var2) {
        if (var2.readableBytes() < 2) {
            return false;
        } else {
            short var5 = var2.readUnsignedByte();
            short var3 = var2.readUnsignedByte();
            if (var5 != 5) {
                this.method_235(var1, new IllegalStateException("SOCKS5 proxy returned an invalid greeting"));
            } else if (var3 == 0) {
                this.state = Socks5ProxyHandler.State.CONNECT;
                this.method_230(var1);
            } else if (var3 == 2 && !this.string2.isBlank()) {
                this.state = Socks5ProxyHandler.State.AUTH;
                this.method_236(var1);
            } else {
                this.method_235(var1, new IllegalStateException("SOCKS5 proxy rejected the configured authentication"));
            }

            return true;
        }
    }

    public Socks5ProxyHandler(InetSocketAddress var1, String var2, int var3, String var4, String var5) {
        this.inetSocketAddress = var1;
        this.string = var2 == null ? "" : var2;
        this.intValue = var3;
        this.string2 = var4 == null ? "" : var4;
        this.string3 = var5 == null ? "" : var5;
    }

    @Override
    public void connect(ChannelHandlerContext var1, SocketAddress var2, SocketAddress var3, ChannelPromise var4) {
        if (var2 instanceof InetSocketAddress var5) {
            this.inetSocketAddress2 = var5;
            this.channelPromise = var4;
            this.scheduledFuture = var1.executor()
                .schedule(() -> this.method_235(var1, new IllegalStateException("SOCKS5 proxy connection timed out")), 10L, TimeUnit.SECONDS);
            var1.connect(this.inetSocketAddress, var3, var1.newPromise()).addListener(var2x -> {
                if (!var2x.isSuccess()) {
                    this.method_235(var1, var2x.cause());
                }
            });
        } else {
            var4.tryFailure(new IllegalArgumentException("SOCKS5 requires an internet address"));
        }
    }

    private static String method_238(int var0) {
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

    @Override
    public void channelActive(ChannelHandlerContext var1) {
        if (this.state == Socks5ProxyHandler.State.CONNECTING) {
            this.state = Socks5ProxyHandler.State.GREETING;
            this.method_234(var1);
        }
    }

    private boolean method_239(ChannelHandlerContext var1, ByteBuf var2) {
        if (var2.readableBytes() < 2) {
            return false;
        } else {
            short var5 = var2.readUnsignedByte();
            short var3 = var2.readUnsignedByte();
            if (var5 == 1 && var3 == 0) {
                this.state = Socks5ProxyHandler.State.CONNECT;
                this.method_230(var1);
            } else {
                this.method_235(var1, new IllegalStateException("SOCKS5 proxy rejected the username or password"));
            }

            return true;
        }
    }

    // $VF: Could not inline inconsistent finally blocks
    // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
    @Override
    public void channelRead(ChannelHandlerContext var1, Object var2) {
        var var3 = this;
        if (var2 instanceof ByteBuf var8) {
            var var4 = this;

            try {
                if (var4.byteBuf == null) {
                    var3.byteBuf = var1.alloc().buffer(var8.readableBytes());
                }

                var3.byteBuf.writeBytes(var8);

                while (
                    var3.state != Socks5ProxyHandler.State.CONNECTED
                        && var3.state != Socks5ProxyHandler.State.FAILED
                        && var3.byteBuf != null
                        && var3.byteBuf.isReadable()
                        && var3.method_231(var1, var3.byteBuf)
                ) {
                }
            } catch (Throwable var7) {
                var8.release();
                throw var7;
            }

            var8.release();
            if (var3.state == Socks5ProxyHandler.State.CONNECTED) {
                var1.pipeline().remove(var3);
            } else {
                if (var3.state != Socks5ProxyHandler.State.CONNECTED && var3.byteBuf != null) {
                    var3.byteBuf.discardReadBytes();
                }
            }
        } else {
            var1.fireChannelRead(var2);
        }
    }

    private static enum State {
        CONNECTING,
        GREETING,
        AUTH,
        CONNECT,
        CONNECTED,
        FAILED;
    }
}
