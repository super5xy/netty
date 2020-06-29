package cn.super5xy.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @Author: super5xy
 * @Date: 2020/6/24 15:14
 */
public class NIOFileChannel04 {
    //使用channel的transferFrom方法一次拷贝文件 对03的封装
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("1.png");

        FileChannel inputChannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("2.png");

        FileChannel outputchannel = fileOutputStream.getChannel();

        outputchannel.transferFrom(inputChannel, 0, inputChannel.size());

        fileInputStream.close();
        fileOutputStream.close();
    }
}
