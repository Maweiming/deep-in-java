/**
 * fshows.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.maweiming.ftp.core.command;

import io.netty.channel.ChannelHandlerContext;

/**
 * 获得服务器支持的特性列表
 *
 * @author CoderMa
 * @version FeatCommand.java, v 0.1 2021-01-04 6:23 下午 CoderMa
 */
public class FeatCommand extends AbstractFtpCommand {

    public FeatCommand() {
        super("FEAT");
    }

    @Override
    public void execute(ChannelHandlerContext ctx, String param) {
        send("211-Extensions supported:", ctx);
        send("211 END", ctx);
    }
}