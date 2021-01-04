/**
 * fshows.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @author CoderMa
 * @version FsServerHandler.java, v 0.1 2021-01-03 09:05 下午 CoderMa
 */
public class FsServerHandler extends SimpleChannelInboundHandler<String> {
    /**
     * 建立连接时发送一条消息
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.write("欢迎来到Fs聊天室\n");
        ctx.write(String.format("当前时间:%s\n", new Date().toLocaleString()));
        ctx.flush();
    }

    /**
     * 处理客户端发来的消息
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, String request) {
        String response;
        boolean close = false;
        if (StringUtils.isBlank(request)) {
            response = "what are you弄啥嘞\n";
        } else {
            response = "收到来自客户端的消息：" + request + "\n";
        }
        ChannelFuture future = ctx.write(response);
        if (close) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    //异常处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}