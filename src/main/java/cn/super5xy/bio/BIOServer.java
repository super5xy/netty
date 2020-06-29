package cn.super5xy.bio;

import cn.hutool.core.thread.ThreadFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @Author: super5xy
 * @Date: 2020/6/23 13:09
 * BIO服务器示例 来一个连接启动一个线程 线程会阻塞造成线程资源 浪费
 */
public class BIOServer {
    public static void main(String[] args) throws Exception {
        //创建线程池
//        Executors.newCachedThreadPool();

        ThreadFactory nameFactory = new ThreadFactoryBuilder().setNamePrefix("线程池").build();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(),nameFactory);

        System.out.println("服务器启动了");
        ServerSocket serverSocket = new ServerSocket(6666);

        //主线程循环等待连接，没有连接的时候在serverSocket.accept()阻塞
        while (true){
            //监听，等待连接
            //阻塞在这，telnet 127.0.0.1 6666连接服务器，一个连接过来了就新建一个线程处理
            Socket socket = serverSocket.accept();
            //创建一个线程
            threadPoolExecutor.execute(() -> {
                handler(socket);
            });
        }


//        threadPoolExecutor.shutdown();

    }

    public static void handler(Socket socket){

        byte[] bytes = new byte[1024];

        try {
            System.out.println("有客户端连接了");
            System.out.println("线程ID="+Thread.currentThread().getId()+"线程名字"+Thread.currentThread().getName());
            while (true){
                InputStream inputStream = socket.getInputStream();
                System.out.println("读取");
                //读完后会阻塞在这 就会造成线程的浪费
                int read = inputStream.read(bytes);
//                System.out.println(inputStream.getClass().getTypeName()); 是java.net.SocketInputStream
                System.out.println(read);
                if (read!=-1){
                    System.out.println(new String(bytes,0,read));
                }else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                System.out.println("socket销毁");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
