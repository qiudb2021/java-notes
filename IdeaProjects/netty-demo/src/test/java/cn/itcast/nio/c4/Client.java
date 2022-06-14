package cn.itcast.nio.c4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Client {
    public static void main(String[] args) throws IOException {
        MultiClent();
    }

    public static void client() throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8080));
        sc.write(StandardCharsets.UTF_8.encode("0123456789abcdefghijklmnopqrstuvwxyz\n"));
        System.in.read();
    }
    public static void MultiClent() throws IOException{
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8080));
        sc.write(StandardCharsets.UTF_8.encode("1234567890abcdef"));
        System.in.read();
    }
}
