/**
 * fshows.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.maweiming.ftp.core.command;

import io.netty.channel.ChannelHandlerContext;

/**
 * 断开连接
 *
 * @author CoderMa
 * @version QuitCommand.java, v 0.1 2021-01-04 7:10 下午 CoderMa
 */
public class QuitCommand extends AbstractFtpCommand {

    public QuitCommand() {
        super("QUIT");
    }

    @Override
    public void execute(ChannelHandlerContext ctx, String param) {
        send("221 Closing connection", ctx);
    }
}