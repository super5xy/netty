package cn.super5xy.nio;

import sun.nio.ch.FileChannelImpl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: super5xy
 * @Date: 2020/6/24 14:06
 */
public class NIOFileChannel01 {
    //写入文件
    public static void main(String[] args) throws Exception {
        String str = "hello,尚硅谷";


        //创建输出流 ->channel
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\super\\Desktop\\1.txt");

        //channel = FileChannelImpl.open(fd, path, false, true, append, this);
        FileChannel channel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //将str写入byteBuffer
        byteBuffer.put(str.getBytes());

        //flip，用于写入进channel
        byteBuffer.flip();

        //将bytebuffer数据写入channel
        channel.write(byteBuffer);

        fileOutputStream.close();
    }
}
