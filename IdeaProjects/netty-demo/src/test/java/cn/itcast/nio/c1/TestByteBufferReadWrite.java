package cn.itcast.nio.c1;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;

import static cn.itcast.nio.c1.ByteBufferUtil.debugAll;

@Slf4j
public class TestByteBufferReadWrite {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        buffer.put((byte) 0x61); // 'a';
        buffer.put(new byte[]{0x62,0x63,0x64}); // b c d
        debugAll(buffer);

        buffer.flip();
        log.debug("读取到的字符{}", (char)buffer.get());
        debugAll(buffer);

        buffer.compact();
        debugAll(buffer);

        buffer.put(new byte[]{0x65, 0x66});
        debugAll(buffer);
    }
}
