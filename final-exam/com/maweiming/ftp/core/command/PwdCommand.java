/**
 * fshows.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.maweiming.ftp.core.command;

import com.maweiming.ftp.core.util.CommandUtils;
import io.netty.channel.ChannelHandlerContext;

/**
 * 打印工作目录，返回主机的当前目录
 *
 * @author CoderMa
 * @version PwdCommand.java, v 0.1 2021-01-04 6:24 下午 CoderMa
 */
public class PwdCommand extends AbstractFtpCommand {
    public PwdCommand() {
        super("PWD");
    }

    @Override
    public void execute(ChannelHandlerContext ctx, String param) {
        send("257 " + CommandUtils.currDirectory, ctx);
    }
}