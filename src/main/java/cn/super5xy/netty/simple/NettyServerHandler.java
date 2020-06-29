package cn.super5xy.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @Author: super5xy
 * @Date: 2020/6/26 18:50
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取客户端消息
     * @param ctx 上下文对象含有pipeline管道
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx :"+ctx);

        //netty的byteBuf不是nio的byteBuffer
        ByteBuf byteBuf = (ByteBuf) msg;
        Channel channel = ctx.channel();
        ChannelPipeline pipeline = ctx.pipeline();
        System.out.println("客户端的消息: "+byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端的地址: "+ctx.channel().remoteAddress());
    }

    /**
     * 数据读取完毕
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello，客户端~", CharsetUtil.UTF_8));
    }

    /**
     * 异常处理 一般是关闭通道
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
