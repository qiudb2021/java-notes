package cn.itcast.netty.c5;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloWorldClient {
    public static void main(String[] args) {
            send();

        System.out.println("finish");
    }

    private static void send() {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(worker);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        // 会在连接 channel 建立成功后，会触发 active 事件
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            ByteBuf buf = ctx.alloc().buffer(16);
                            buf.writeBytes(new byte[] {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17});
                            ctx.writeAndFlush(buf);
                            ctx.channel().close();
                            super.channelActive(ctx);
                        }
                    });
                }
            });
            ChannelFuture future = bootstrap.connect("127.0.0.1", 8082).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("client error: ", e);
        } finally {
            worker.shutdownGracefully();
        }
    }
}
