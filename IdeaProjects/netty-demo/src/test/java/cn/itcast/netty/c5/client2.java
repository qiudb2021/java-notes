package cn.itcast.netty.c5;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Random;

@Slf4j
public class client2 {
    public static void main(String[] args) throws InterruptedException {
//        fill10Bytes('1', 5);
//        fill10Bytes('2', 3);
//        fill10Bytes('3', 10);
        send();
    }

    public static byte[] fill10Bytes(char c, int len) {

        byte[] bytes = new byte[10];
        for (int i = 0; i < len; i++) {
            bytes[i] = (byte) c;
        }

        for (int i = len; i < 10; i++) {
            bytes[i] = '_';
        }

        StringBuilder s = new StringBuilder(bytes.length);
        for (int i = 0; i < bytes.length; i++) {
            s.append((char)bytes[i]);
        }

        System.out.println(s);
        return bytes;
    }

    private static void send() throws InterruptedException {
        NioEventLoopGroup worker = new NioEventLoopGroup();

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
                        char c = '0';
                        Random random = new Random();
                        for (int i = 0; i < 10; i++) {
                            byte[] bytes = fill10Bytes(c++, random.nextInt(10) + 1);
                            buf.writeBytes(bytes);
                        }
                        ctx.writeAndFlush(buf);
                        
                        super.channelActive(ctx);
                    }
                });
            }
        });
        bootstrap.connect("127.0.0.1", 8083).sync().channel().close();
    }
}
