/**
 * fshows.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.maweiming.ftp.core.command;

import io.netty.channel.ChannelHandlerContext;

/**
 * 返回当前系统类型
 *
 * @author CoderMa
 * @version SystCommand.java, v 0.1 2021-01-04 6:22 下午 CoderMa
 */
public class SystCommand extends AbstractFtpCommand {
    public SystCommand() {
        super("SYST");
    }

    @Override
    public void execute(ChannelHandlerContext ctx, String param) {
        send("215 Fshows FTP Server", ctx);
    }
}