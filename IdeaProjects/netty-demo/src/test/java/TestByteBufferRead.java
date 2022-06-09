import java.nio.ByteBuffer;

import static cn.itcast.netty.c1.ByteBufferUtil.debugAll;

public class TestByteBufferRead {
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
