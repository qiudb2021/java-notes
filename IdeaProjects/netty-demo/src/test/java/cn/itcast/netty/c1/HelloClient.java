package cn.itcast.netty.c1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

public class HelloClient {
    public static void main(String[] args) throws InterruptedException {
        //7. 启动类
        new Bootstrap()
                // 8. 添加 EventLoop
                .group(new NioEventLoopGroup())
                // 9. 选择客户端 channel 实现
                .channel(NioSocketChannel.class)
                // 10. 添加处理器
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override // 12. 在连接建立后被调用
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        // 15. 把 hello, world 转为 ByteBuf
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                // 11. 连接到服务器
                .connect(new InetSocketAddress("localhost", 8080))
                .sync() // 13. 阻塞，直到连接建立
                .channel() // 客户端与服务器连接对象
                // 14. 向服务器发送数据
                .writeAndFlush("hello, world");
    }
}
