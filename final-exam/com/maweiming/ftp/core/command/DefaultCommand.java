/**
 * fshows.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.maweiming.ftp.core.command;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author CoderMa
 * @version DefaultCommand.java, v 0.1 2021-01-04 7:10 下午 CoderMa
 */
public class DefaultCommand extends AbstractFtpCommand {
    public DefaultCommand(String cmd) {
        super(cmd);
    }

    @Override
    public void execute(ChannelHandlerContext ctx, String param) {
        send("501 Unknown command", ctx);
    }
}