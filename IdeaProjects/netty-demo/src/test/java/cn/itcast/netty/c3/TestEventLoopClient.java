package cn.itcast.netty.c3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Scanner;

@Slf4j
public class TestEventLoopClient {
    public static void main(String[] args) throws InterruptedException {
        client3();
    }

    public static void client() throws InterruptedException {
        Channel channel = new Bootstrap()
                .group(new NioEventLoopGroup())
//                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                .connect(new InetSocketAddress("localhost", 8080))
                .sync()
                .channel();
        System.out.println(channel);
        System.out.println("");
    }
    public static void client2() throws InterruptedException {
        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
//                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                // 1. 连接到服务器
                // connect 异步非阻塞，main发起了调用，真正执行 connect 的是 nio 线程
                .connect(new InetSocketAddress("localhost", 8080));

        // 2.1 使用 sync 方法同步处理结果
        channelFuture.sync(); // 阻塞住当前线程，直到nio线程连接建立完毕

        // 无阻塞向下执行获取 channel(若没有调用 channelFuture.sync() 等待，则获取到的channel是还没建立好连接的)
        Channel channel = channelFuture.channel();
        log.debug("{}", channel);
        // 向服务器发送数据
        channel.writeAndFlush("hello, world");
        System.out.println("");

        // 2.2 使用 addListener(回调对象) 方法异步处理结果
//        channelFuture.addListener(new ChannelFutureListener() {
//            @Override
//            // 在 nio 线程建立好连接之后，会调用 operationComplete
//            public void operationComplete(ChannelFuture future) throws Exception {
//                Channel channel1 = future.channel();
//                log.debug("{}", channel1);
//                channel1.writeAndFlush("hello, world2");
//            }
//        });
    }

    public static void client3() throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        ChannelFuture channelFuture = new Bootstrap()
                .group(group)
//                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                // 1. 连接到服务器
                // connect 异步非阻塞，main发起了调用，真正执行 connect 的是 nio 线程
                .connect(new InetSocketAddress("localhost", 8080));

        channelFuture.sync();

        Channel channel = channelFuture.channel();

        log.debug("{}", channel);
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String line = scanner.nextLine();
                if ("q".equals(line)) {
                    channel.close(); // 异步非阻塞
                    break;
                }
                channel.writeAndFlush(line);
            }
        }, "input").start();

        // 获取 ClosedFuture 对象，1）同步处理关闭；2）异步处理关闭
//        ChannelFuture closeFuture = channel.closeFuture();
//        System.out.println("waiting close...");
//        closeFuture.sync();
//        log.debug("处理关闭后的操作");
        ChannelFuture closeFuture = channel.closeFuture();
        closeFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                log.debug("处理关闭之后的操作");
                group.shutdownGracefully();
            }
        });
    }
}
