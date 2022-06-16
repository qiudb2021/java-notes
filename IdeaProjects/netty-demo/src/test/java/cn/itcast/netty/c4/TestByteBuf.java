package cn.itcast.netty.c4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.StringUtil;

import java.nio.charset.StandardCharsets;

import static cn.itcast.nio.c1.ByteBufferUtil.debugAll;

public class TestByteBuf {
    public static void main(String[] args) {
        c2();
    }

    private static void c2() {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        buf.writeBytes(new byte[]{1,2,3,4});
        log(buf);
        buf.writeInt(5);
        buf.writeInt(6);
        log(buf);

        System.out.println(buf.readByte());
        System.out.println(buf.readByte());
        System.out.println(buf.readByte());
        System.out.println(buf.readByte());
        log(buf);
    }
    private static void c1() {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        System.out.println(buf.getClass());
        log(buf);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 30; i++) {
            sb.append("a");
        }

        buf.writeBytes(sb.toString().getBytes(StandardCharsets.UTF_8));
        log(buf);
    }

    public static void log(ByteBuf buffer) {
        int length = buffer.readableBytes();
        int rows = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
        StringBuilder buf = new StringBuilder(rows * 80 * 2)
                .append("read index: ").append(buffer.readerIndex())
                .append(" write index: ").append(buffer.writerIndex())
                .append(" capacity: ").append(buffer.capacity())
                .append(StringUtil.NEWLINE);

        ByteBufUtil.appendPrettyHexDump(buf, buffer);
        System.out.println(buf.toString());
    }
}
