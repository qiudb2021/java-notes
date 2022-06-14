package cn.itcast.nio.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class Server3 {
    public static void main(String[] args) throws IOException {
        // 1. 创建selector，管理多个 channel
        Selector selector = Selector.open();

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        // 2. 建立 selector 和 channel 的联系（注册）
        // SessionKey 就是将来事件发生后，通过它可以知道事件和哪个 channel 的事件
        SelectionKey sscKey = ssc.register(selector, 0, null);
        /*
            事件类型：
            1. accept  - 在有连接请求时触发
            2. connect - 客户端连接建立后触发
            3. read    - 可读事件
            4. write   - 可写事件
         */
        // sscKey 只关注 accept事件
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        log.debug("register key: {}", sscKey);

        ssc.bind(new InetSocketAddress(8080));

        while (true) {
            // 3. select 方法: 没有事件发生时线程阻塞，有事件发生时线程恢复运行
            // select 在事件未处理时，它不会阻塞
            selector.select();

            // 4. 处理事件 selectedKeys 包含了所有发生的事件
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                log.debug("key: {}", key);
                ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                SocketChannel sc = channel.accept();
                log.debug("{}", sc);
            }
        }
    }
}
