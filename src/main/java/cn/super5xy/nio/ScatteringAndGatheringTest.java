package cn.super5xy.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @Author: super5xy
 * @Date: 2020/6/24 16:20
 *
 * Scattering 将数据写入到buffer时可以采用buffer数组。依次写入 一个满了写入另一个 [分散]
 * Gathering 从buffer读取数据时，可以采用buffer数组 依次读
 */
public class ScatteringAndGatheringTest {
    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7001);
        //绑定端口并启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0]= ByteBuffer.allocate(5);
        byteBuffers[1]= ByteBuffer.allocate(3);

        //等待客户端连接（telnet）
        SocketChannel socketChannel = serverSocketChannel.accept();

        //循环读取，等待客户端发送消息 8个字节循环读取
        while (true){
            int byteRead=0;
            while (byteRead<8){
                long read = socketChannel.read(byteBuffers);
                byteRead+=read;
                System.out.println("byteRead="+byteRead);

                Arrays.asList(byteBuffers).forEach(byteBuffer -> {
                    System.out.println("position="+byteBuffer.position()+"limit="+byteBuffer.limit());
                });

            }
            //反转，可写出
            Arrays.asList(byteBuffers).forEach(Buffer::flip);

            int byteWrite=0;
            while (byteWrite<8){
                long write = socketChannel.write(byteBuffers);
                byteWrite+=write;
            }

            Arrays.asList(byteBuffers).forEach(Buffer::clear);


        }


    }
}
