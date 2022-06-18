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

import java.nio.charset.StandardCharsets;
import java.util.Random;

@Slf4j
public class client3 {
    public static void main(String[] args) {
        client();
    }

    private static void client() {
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
                            char c = '0';
                            Random random = new Random();
                            ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
                            for (int i = 0; i < 10; i++) {
                                StringBuilder sb = makeString(c, random.nextInt(256) + 1);
                                c++;
                                buf.writeBytes(sb.toString().getBytes(StandardCharsets.UTF_8));
                            }
                            ctx.writeAndFlush(buf);
                        }
                    });
                }
            });
            ChannelFuture future = bootstrap.connect("localhost", 8084).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
        } finally {
            worker.shutdownGracefully();
        }

    }

    public static StringBuilder makeString(char c, int len) {
        StringBuilder sb = new StringBuilder(len + 2);
        for (int i = 0; i < len; i++) {
            sb.append(c);
        }
        sb.append("\n");
        return sb;
    }
}
