package cn.itcast.netty.c3;

import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.NettyRuntime;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class TestEventLoop {
    public static void main(String[] args) {
        // 1. 创建事件循环组
        EventLoopGroup group = new NioEventLoopGroup(2); // io事件，普通任务，定时任务
//        EventLoopGroup group = new DefaultEventLoopGroup(); // 普通任务，定时任务

        // 打印系统CPU核心数
//        System.out.println("打印系统CPU核心数: " + NettyRuntime.availableProcessors());

        // 2. 获取下一个事件循环对象（group.next()实现轮循，实现负载均衡）
        for (int i = 0; i < 6; i++) {
            System.out.println("第 " + i + " 次调用 group.next(): "+ group.next());
        }

        // 3. 执行普通任务 - 提交任务到group中的某个EventLoop执行
/*        group.next().submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("ok");
        });*/

        // 4. 定时任务
        group.next().scheduleAtFixedRate(() -> {
            log.debug("ok");
        }, 0, 1, TimeUnit.SECONDS);

        log.debug("main");
    }
}
