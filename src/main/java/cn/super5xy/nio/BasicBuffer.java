package cn.super5xy.nio;

import java.nio.IntBuffer;

/**
 * @Author: super5xy
 * @Date: 2020/6/23 17:55
 */
public class BasicBuffer {
    public static void main(String[] args) {
        //简单说明buffer的使用
        //创建buffer 创建一个intbuffer 长度为5 可以存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);

        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i*2);
        }

        //切换读写
        intBuffer.flip();

        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }
    }
}
