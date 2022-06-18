package cn.itcast.netty.c5;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.nio.charset.StandardCharsets;

public class TestLengthFileDecoder {
    public static void main(String[] args) {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4),
//                new LengthFieldBasedFrameDecoder(1024, 0, 4, 1, 4),
                new LoggingHandler(LogLevel.DEBUG)
        );

        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        /*buildMessage(buffer, "Hello, world");
        buildMessage(buffer, "hi!");*/

        buildMessage2(buffer, "Hello, world");
        buildMessage2(buffer, "hi!");
        channel.writeInbound(buffer);
    }

    private static void buildMessage(ByteBuf buffer, String content) {
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        int length = bytes.length;
        // 4 个字节的内容长度，实际内容
        buffer.writeInt(length);
        buffer.writeBytes(bytes);
    }

    private static void buildMessage2(ByteBuf buffer, String content) {
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        int length = bytes.length;
        // 4 个字节的内容长度，实际内容
        buffer.writeInt(length);
        // 1 个字节表示版本
        buffer.writeByte(1);
        // 消息内容
        buffer.writeBytes(bytes);
    }
}
