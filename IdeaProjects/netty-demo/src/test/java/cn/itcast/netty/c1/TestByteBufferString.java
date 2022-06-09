package cn.itcast.netty.c1;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static cn.itcast.netty.c1.ByteBufferUtil.debugAll;

public class TestByteBufferString {
    public static void main(String[] args) {
        // 1. 字符串转为 ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put("hello".getBytes(StandardCharsets.UTF_8));
        debugAll(buffer);

        // 2. Charset
        ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("中国");
        debugAll(buffer2);

        // 3. wrap
        ByteBuffer buffer3 = ByteBuffer.wrap("中国".getBytes());
        debugAll(buffer3);

        buffer.flip();
        System.out.println(StandardCharsets.UTF_8.decode(buffer).toString());


        System.out.println(StandardCharsets.UTF_8.decode(buffer2).toString());
        System.out.println(StandardCharsets.UTF_8.decode(buffer3).toString());

    }

    public static class TestByteBufferRead {
        public static void main(String[] args) {
            ByteBuffer buffer = ByteBuffer.allocate(10);
            buffer.put(new byte[] {'a', 'b', 'c', 'd'});

            buffer.flip();
    //        // rewind 从头开始读
    //        buffer.get(new byte[4]);
    //        debugAll(buffer);
    //        buffer.rewind();
    //        debugAll(buffer);
    //        System.out.println((char) buffer.get());

            // mark & reset
            // mark 做一个标记，记录position位置，reset是将position 重置到 mark 的位置
    //        System.out.println((char) buffer.get()); // a
    //        System.out.println((char) buffer.get()); // b
    //
    //        buffer.mark(); // 加标记，索引2的位置
    //        System.out.println((char) buffer.get()); // c
    //        System.out.println((char) buffer.get()); // d
    //
    //        buffer.reset(); // 将 position 重置到 mark 标记的位置（索引2）
    //        System.out.println((char) buffer.get()); // c
    //        System.out.println((char) buffer.get()); // d

            // get(i) 不会改变 position 的索引位置
            System.out.println((char) buffer.get(3));
            debugAll(buffer); // position不会变（还是0）
        }
    }
}
