package cn.itcast.netty.chat.client;

import cn.itcast.netty.chat.message.LoginRequestMessage;
import cn.itcast.netty.chat.protocol.MessageCodecSharable;
import cn.itcast.netty.chat.protocol.ProcotolFrameDecoder;
import io.netty.bootstrap.Bootstrap;
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

import java.util.Scanner;

@Slf4j
public class ChatClient {
    public static void main(String[] args) {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(worker);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {
                    channel.pipeline().addLast(new ProcotolFrameDecoder());
                    channel.pipeline().addLast(LOGGING_HANDLER);
                    channel.pipeline().addLast(MESSAGE_CODEC);
                    channel.pipeline().addLast("client handler", new ChannelInboundHandlerAdapter() {
                        @Override
                        // 连接建立后触发 active 事件
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            // 负责接收用户在控制台的输入，负责向服务器发送各种消息
                            new Thread(() -> {
                                Scanner scanner = new Scanner(System.in);
                                System.out.println("请输入用户名: ");
                                String username = scanner.nextLine();
                                System.out.println("请输入密码: ");
                                String password = scanner.nextLine();

                                // 构造消息对象
                                LoginRequestMessage message = new LoginRequestMessage(username, password);
                                // 发送消息
                                ctx.writeAndFlush(message);

                                System.out.println("等待后续操作");
                            }, "system in").start();
                            super.channelActive(ctx);
                        }

                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            log.debug("msg: {}", msg);
                            super.channelRead(ctx, msg);
                        }
                    });

                }
            });
            ChannelFuture channelFuture = bootstrap.connect("localhost", 8081).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("client serror ", e);
        } finally {
            worker.shutdownGracefully();
        }
    }
}
