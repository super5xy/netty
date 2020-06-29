package cn.super5xy.nio.nioserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Author: super5xy
 * @Date: 2020/6/25 14:00
 */
public class NIOClient {
    public static void main(String[] args) throws IOException {

        SocketChannel socketChannel = SocketChannel.open();
        //非阻塞
        socketChannel.configureBlocking(false);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);

        if(!socketChannel.connect(inetSocketAddress)){

            while (!socketChannel.finishConnect()){
                System.out.println("连接需要时间，可以做其他事");
            }
        }

        //连接成功
        String str = "Hello 尚硅谷";
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
        //将buffer数据写入channel
        socketChannel.write(buffer);
        System.in.read();


    }
}
