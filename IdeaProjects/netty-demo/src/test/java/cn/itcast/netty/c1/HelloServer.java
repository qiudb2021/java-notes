package cn.itcast.netty.c1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class HelloServer {
    public static void main(String[] args) {
        // 1. 启动器，负责组装 netty 组件，启动服务器
        new ServerBootstrap()
                // 2. BossEventLoop, WorkerEventLoop(selector, thread), group 组
                .group(new NioEventLoopGroup())
                // 3. 选择 服务器的 ServerSocketChannel 实现
                .channel(NioServerSocketChannel.class) // accept、read; 16. 由某个EventLoop处理 read 事件，接收到ByteBuf
                // 4. boss 负责连接处理；worker(child)负责读写，决定了 worker(child) 能执行哪些操作(handler)
                .childHandler(
                        // 5. channel 代表和客户端进行数据读写的通道 Initializer 初始化，负责添加别的 handler
                        new ChannelInitializer<NioSocketChannel>() {
                        @Override // 12 客户端连接时触发
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            // 添加具体 handler
                            ch.pipeline().addLast(new StringDecoder()); // 17. 将 ByteBuf 转换为字符串 hello, world
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                                @Override // 18. 执行read方法，执行打印第17解码的字符串
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    System.out.println(msg);
                                }
                            });
                        }

                })
                // 6. 绑定监听端口
                .bind(8080);

    }
}
