package cn.super5xy.nio;


import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: super5xy
 * @Date: 2020/6/24 14:34
 */
public class NIOFileChannel02 {
    //读取01的文件
    public static void main(String[] args) throws Exception{
        File file = new File("C:\\Users\\super\\Desktop\\1.txt");
        FileInputStream fileInputStream = new FileInputStream(file);

        FileChannel channel = fileInputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

        //把数据读取到byteBuffer
        channel.read(byteBuffer);

        System.out.println(new String(byteBuffer.array()));

        fileInputStream.close();
    }
}
