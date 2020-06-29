package cn.super5xy.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: super5xy
 * @Date: 2020/6/24 14:51
 */
public class NIOFileChannel03 {
    //复制文件 使用一个buffer读取并写入
    public static void main(String[] args) throws Exception{
        FileInputStream fileInputStream = new FileInputStream("1.txt");

        FileChannel channel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");

        FileChannel channel1 = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        //循环读取
        while (true){
            //清空buffer
            byteBuffer.clear();
            int read = channel.read(byteBuffer);
            if (read==-1){
                break;
            }
            byteBuffer.flip();
            channel1.write(byteBuffer);
        }


        fileInputStream.close();
        fileOutputStream.close();

    }
}
