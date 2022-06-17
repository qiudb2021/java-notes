package cn.itcast.netty.c5;

import com.sun.corba.se.internal.CosNaming.BootstrapServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Server2 {
    public static void main(String[] args) {
        start();
    }

    private static void start() {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

       try {
           ServerBootstrap serverBootstrap = new ServerBootstrap();
           serverBootstrap.group(boss, worker);
           serverBootstrap.channel(NioServerSocketChannel.class);
           serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
               @Override
               protected void initChannel(SocketChannel ch) throws Exception {
                   ch.pipeline().addLast(new FixedLengthFrameDecoder(10));
                   ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
               }
           });
           ChannelFuture channelFuture = serverBootstrap.bind(8083).sync();
           channelFuture.channel().closeFuture().sync();
       } catch (InterruptedException e) {
           log.error("server error: ", e);
           boss.shutdownGracefully();
           worker.shutdownGracefully();
       } finally {

       }
    }
}
