package com.maweiming.ftp.core; /**
 * fshows.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */

import com.maweiming.ftp.core.command.CommandFactory;
import com.maweiming.ftp.core.util.CommandUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author CoderMa
 * @version FsServerHandler.java, v 0.1 2021-01-03 09:05 下午 CoderMa
 */
public class FtpCommandHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        CommandUtils.send("500 Unspecified error", ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        CommandUtils.send("220 Welcome to the Fs-FTP-Server", ctx);
    }

    /**
     * 处理客户端发来的消息
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, String request) throws Exception {
        new CommandFactory().start(ctx, request);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }


}