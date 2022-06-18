package cn.itcast.netty.c5;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
public class TestRedis {
    /*
    set name zhangsan
    3
    $4
    name
    $8
    zhangsan
     */
    public static void main(String[] args) {
        // 13: 回车; 20: 换行
        final byte[] LINE = new byte[]{13, 20};
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(worker);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            ByteBuf buf = ctx.alloc().buffer();
                            buf.writeBytes("*3".getBytes(StandardCharsets.UTF_8));
                            buf.writeBytes(LINE);

                            buf.writeBytes("$3".getBytes(StandardCharsets.UTF_8));
                            buf.writeBytes(LINE);

                            buf.writeBytes("set".getBytes(StandardCharsets.UTF_8));
                            buf.writeBytes(LINE);

                            buf.writeBytes("$4".getBytes(StandardCharsets.UTF_8));
                            buf.writeBytes(LINE);

                            buf.writeBytes("name".getBytes(StandardCharsets.UTF_8));
                            buf.writeBytes(LINE);

                            buf.writeBytes("$8".getBytes(StandardCharsets.UTF_8));
                            buf.writeBytes(LINE);

                            buf.writeBytes("zhangsan".getBytes(StandardCharsets.UTF_8));
                            buf.writeBytes(LINE);

                            ctx.writeAndFlush(buf);
                        }

                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            ByteBuf buf = (ByteBuf) msg;
                            System.out.println(buf.toString(Charset.defaultCharset()));
                            super.channelRead(ctx, msg);
                        }
                    });
                }
            });
            ChannelFuture channelFuture = bootstrap.connect("localhost", 6379).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("client error ", e);
        } finally {
            worker.shutdownGracefully();
        }
    }
}
