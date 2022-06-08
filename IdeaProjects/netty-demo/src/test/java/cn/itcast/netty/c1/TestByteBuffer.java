package cn.itcast.netty.c1;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Slf4j
public class TestByteBuffer {
    public static void main(String[] args) {
        // FileChannel
        // 1. 输入输出流；2. RandomAccessFile
        try (FileChannel channel = new FileInputStream("data.txt").getChannel()) {
            // 准备缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(10);

            while (true) {
                // 从channel读取到buffer中
                int len = channel.read(buffer);
                log.debug("读取到的长度: {}", len);
                if (len == -1) {
                    // 没有内容了
                    break;
                }
                // buffer切换成读模式
                buffer.flip();
                // 输出buffer内容
                while (buffer.hasRemaining()) {
                    byte b = buffer.get();
                    log.debug("读到的字符: {}", (char)b);
                }
                // buffer切换成写模式
                buffer.clear();
            }

        } catch (IOException e) {
        }
    }
}
