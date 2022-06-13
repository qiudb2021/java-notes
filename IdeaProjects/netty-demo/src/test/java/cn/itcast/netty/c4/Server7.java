package cn.itcast.netty.c4;

import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import static cn.itcast.netty.c1.ByteBufferUtil.debugAll;

@Slf4j
public class Server7 {
    public static void split(ByteBuffer source) {
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            // 找到一条完整的消息
            if (source.get(i) == '\n') {
                int length = i + 1 - source.position();
                // 把这条完整消息存入新的 ByteBuffer
                ByteBuffer target = ByteBuffer.allocate(length);
                // 从source读，向 target 写
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                debugAll(target);
            }
        }
        source.compact();
    }
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
            // select 在事件发生后，要么处理，要么取消，不能置之不理（死循环）
            selector.select();

            // 4. 处理事件 selectedKeys 包含了所有发生的事件
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                // 处理key时，要从selectedKey 集合中移除，否则下次处理就会有问题
                iter.remove();
                log.debug("key: {}", key);

                if (key.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    // 将一个ByteBuffer作为附件关联到 selectionKey 上
                    ByteBuffer buffer = ByteBuffer.allocate(16);
                    SelectionKey scKey = sc.register(selector, 0, buffer);
                    scKey.interestOps(SelectionKey.OP_READ);
                    log.debug("{}", sc);
                } else if (key.isReadable()) {
                    try {
                        SocketChannel channel = (SocketChannel) key.channel(); // 拿到触发事件的channel

                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        int read = channel.read(buffer); // 如果是正常断开，read方法返回值 -1
                        if (read == -1) {
                            log.debug("客户端正常断开");
                            key.cancel();
                        } else {
//                            buffer.flip();
//                            System.out.println(StandardCharsets.UTF_8.decode(buffer).toString());
                            split(buffer);
                            // 没有分离完整的消息
                            if (buffer.position() == buffer.limit()) {
                                ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() * 2);
                                buffer.flip();
                                newBuffer.put(buffer);
                                key.attach(newBuffer);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        // 因为客户端断开了，因此需要将key取消（从selector的keys集合中真正删除key）
                        key.cancel();
                        log.debug("客户端异常断开");
                    }
                }
            }
        }
    }

}
