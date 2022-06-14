package cn.itcast.nio.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static cn.itcast.nio.c1.ByteBufferUtil.debugRead;

@Slf4j
public class Server2 {
    public static void main(String[] args) throws IOException {
        // 使用 nio 来理解阻塞模式，单线程
        // 0. 创建ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 1. 创建服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();

        ssc.configureBlocking(false); // 非阻塞模式

        // 2. 绑定监听端口
        ssc.bind(new InetSocketAddress(8080));

        // 3. 连接集合
        List<SocketChannel> channels = new ArrayList<>();
        while (true) {
            // 4. accept 建立客户端连接，SocketChannel 用来与客户端通讯
            // log.debug("connecting...");
            SocketChannel sc = ssc.accept(); // 非阻塞模式，线程还会继续运行，如果没有连接建立，sc是null
            if (sc != null) {
                log.debug("connected...{}", sc);
                sc.configureBlocking(false); // 非阻塞模式
                channels.add(sc);
            }

            for (SocketChannel channel : channels) {
                // 5. 接收客户端发送的数据
//                log.debug("before read...{}", channel);
                int read = channel.read(buffer); // 非阻塞，线程仍然会继续运行，如果没有读到数据 read返回0
                if (read > 0) {
                    buffer.flip();
                    debugRead(buffer);
                    buffer.clear();
                    log.debug("after read...{}", channel);
                }
            }

        }
    }

}
