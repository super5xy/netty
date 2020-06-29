package cn.super5xy.nio.nioserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author: super5xy
 * @Date: 2020/6/25 13:15
 * NIO服务器 注册channel和读取客户端发送的消息
 */
public class NIOServer {
    public static void main(String[] args) throws Exception {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));

        //设置非阻塞
        serverSocketChannel.configureBlocking(false);

        //selector
        Selector selector = Selector.open();

        //把serverSocketChannel注册到selector 注册会分配一个SelectionKey OP_ACCEPT类型的 这个key检测到ACCEPT行为就会被检测到
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //循环等待客户端连接
        while (true) {

            //等待一秒，没有事件就continue 事件包括连接事件 读写事件
            if (selector.select(1000) == 0) {
                System.out.println("服务器等待了一秒，没有事件发生");
                continue;
            }
            //有事件发生 把key取出来迭代处理 处理后删除key
            Set<SelectionKey> selectionKeys = selector.selectedKeys();


            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();

                //如果是连接事件，建立一个SocketChannel,有连接才会执行，不会阻塞
                if (selectionKey.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功,hashcode:"+socketChannel.hashCode());

                    //必须设置通道为 非阻塞，才能向 Selector 注册 ,否则Exception in thread "main" java.nio.channels.IllegalBlockingModeException
                    socketChannel.configureBlocking(false);
                    //注册到selector 给注册Channel的关联一个buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }

                //代表是可读事件 即上次注册的socketChannel
                if (selectionKey.isReadable()) {
                    SocketChannel channel = null;
                    try {
                        channel = (SocketChannel) selectionKey.channel();
                        //返回register第三个参数，即ByteBuffer.allocate(1024)
                        ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                        int read = channel.read(byteBuffer);
                        if (read==-1){
                            System.out.println("结束");
                            continue;
                        }
                        System.out.println("客户端发送的信息: " + new String(byteBuffer.array(),0,read));
                        byteBuffer.flip();
                    } catch (IOException e) {
                        //断开连接之后 取消注册 关闭channel
                        selectionKey.cancel();
                        channel.close();
                        System.out.println("客户端离线了");
                        e.printStackTrace();
                    }
                }
                //移除当前的selectionKey 防止重复操作
                //每次读操作，或者连接操作都是一次新的事件，会有新的key，不删除会导致重复操作
                iterator.remove();


            }

        }


    }
}
