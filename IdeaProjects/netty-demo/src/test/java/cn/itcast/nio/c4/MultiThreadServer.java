package cn.itcast.nio.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

import static cn.itcast.nio.c1.ByteBufferUtil.debugAll;

@Slf4j
public class MultiThreadServer {
    public static void main(String[] args) throws IOException {
        Thread.currentThread().setName("boss");

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        Selector boss = Selector.open();
        SelectionKey bossKey = ssc.register(boss, 0, null);
        bossKey.interestOps(SelectionKey.OP_ACCEPT);

        ssc.bind(new InetSocketAddress(8080));

        // 1. 创建固定数量的worker 并初始化
//        Worker worker = new Worker("worker-0");
//        Worker2 worker = new Worker2("worker-0");
        Worker[] workers = new Worker[2];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new Worker("worker-"+i);
        }
        AtomicInteger index = new AtomicInteger();
        while (true) {
            boss.select();
            Iterator<SelectionKey> iter = boss.selectedKeys().iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();

                if (key.isAcceptable()) {
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    log.debug("connected...{}", sc.getRemoteAddress());

                    log.debug("before register...{}", sc.getRemoteAddress());
                    // 2. 关联selector
                    workers[index.getAndIncrement()%workers.length].register(sc);
                    log.debug("after register...{}", sc.getRemoteAddress());
                }
            }
        }
    }

    static class Worker implements Runnable {
        private Thread thread;
        private Selector selector;
        private String name;
        private volatile  boolean start =false; // 还未初始化
        private ConcurrentLinkedDeque<Runnable> queue = new ConcurrentLinkedDeque<>();

        public Worker(String name) {
            this.name = name;
        }

        // 初始化线程和selector
        public void register(SocketChannel sc) throws IOException {
            // 保证只执行一遍
            if (!start) {
                thread = new Thread(this, name);
                thread.start();
                selector = Selector.open();
                start = true;
            }
            // 向队列添加了任务，但这个任务并没有立刻执行
            queue.add(() -> {
                try {
                    sc.register(selector, SelectionKey.OP_READ, null);
                } catch (ClosedChannelException e) {
                    throw new RuntimeException(e);
                }
            });
            selector.wakeup();
        }

        @Override
        public void run() {
            while (true) {
                try {
                    selector.select(); // worker-0 阻塞， wakeup
                    Runnable task = queue.poll();
                    if (task != null) {
                        task.run(); // 执行了sc.register(selector, SelectionKey.OP_READ, null);
                    }
                    Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        iter.remove();

                        if (key.isReadable()) {
                            SocketChannel sc = (SocketChannel) key.channel();
                            sc.configureBlocking(false);

                            ByteBuffer buffer = ByteBuffer.allocate(16);
                            log.debug("{} read...{}", this.name, sc.getRemoteAddress());
                            sc.read(buffer);
                            buffer.flip();
                            debugAll(buffer);
                        }
                    }

                } catch (IOException o) {
                    o.printStackTrace();
                }

            }
        }
    }

    static class Worker2 implements Runnable {
        private Thread thread;
        private Selector selector;
        private String name;
        private volatile  boolean start =false; // 还未初始化

        public Worker2(String name) {
            this.name = name;
        }

        // 初始化线程和selector
        public void register(SocketChannel sc) throws IOException {
            // 保证只执行一遍
            if (!start) {
                thread = new Thread(this, name);
                thread.start();
                selector = Selector.open();
                start = true;
            }

            selector.wakeup(); //  唤醒 select 方法 boss
            sc.register(selector, SelectionKey.OP_READ, null); //  boss
        }

        @Override
        public void run() {
            while (true) {
                try {
                    selector.select(); // worker-0 阻塞， wakeup

                    Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        iter.remove();

                        if (key.isReadable()) {
                            SocketChannel sc = (SocketChannel) key.channel();
                            sc.configureBlocking(false);

                            ByteBuffer buffer = ByteBuffer.allocate(16);
                            log.debug("read...{}", sc.getRemoteAddress());
                            sc.read(buffer);
                            buffer.flip();
                            debugAll(buffer);
                        }
                    }

                } catch (IOException o) {
                    o.printStackTrace();
                }

            }
        }
    }


}
