package cn.itcast.nio.c1;

import java.nio.ByteBuffer;

public class TestByteBufferExam {
    public static void main(String[] args) {
        /*
        网络上有多条数据发送给服务端，数据之间使用 \n 进行分隔
        但由于某种原因这些数据在接收时，被进行了重新组合，例如原始数据有3条为
            Hello, world\n
            I'm zhangsan\n
            How are you?\n
        变成了下面的两个byteBuffer
            Hello,world\nI'm Zhangsan\nHo
            w are you?\n
        现在要求将错乱的数据恢复成原始数据
         */

        ByteBuffer source = ByteBuffer.allocate(32);
        source.put("Hello,World\nI'm zhangsan\nHo".getBytes());
        ByteBufferUtil.debugAll(source);

        split(source);
        source.put("w are you?\n".getBytes());
        split(source);
    }

    private static void split(ByteBuffer source) {
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            // 找到一个完整的消息
            if(source.get(i) == '\n') {
                int length = i + 1 - source.position();
                // 把这条完整消息存入新的 ByteBuffer;
                ByteBuffer target = ByteBuffer.allocate(length);
                // 从 source 读，向 target 写
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                target.flip();
                ByteBufferUtil.debugAll(target);
            }
        }
        source.compact();
    }
}
