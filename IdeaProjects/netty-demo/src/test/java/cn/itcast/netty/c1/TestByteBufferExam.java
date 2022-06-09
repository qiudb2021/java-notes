package cn.itcast.netty.c1;

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
    }
}
