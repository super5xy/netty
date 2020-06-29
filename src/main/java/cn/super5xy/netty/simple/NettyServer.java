package cn.super5xy.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author: super5xy
 * @Date: 2020/6/26 18:11
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        //说明 创建了两个线程组 bossGroup只处理连接请求 workGroup处理业务处理
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {


        //创建服务器端启动对象
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class) //服务器通道的实现
                .option(ChannelOption.SO_BACKLOG,128) //设置线程队列 连接个数
                .childOption(ChannelOption.SO_KEEPALIVE,true) //设置保持活动连接状态
                .childHandler(new ChannelInitializer<SocketChannel>() { //创建通道对象

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new NettyServerHandler());
                    }
                });

        System.out.println(".....服务器 is ready...");
        //绑定一个端口并且同步启动服务器
        ChannelFuture cf = bootstrap.bind(6668).sync();

        //对关闭通道进行监听
        cf.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }
}
