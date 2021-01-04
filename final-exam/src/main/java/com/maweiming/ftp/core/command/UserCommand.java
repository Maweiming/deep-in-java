/**
 * fshows.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.maweiming.ftp.core.command;

import io.netty.channel.ChannelHandlerContext;

/**
 * 认证用户名
 *
 * @author CoderMa
 * @version UserCommand.java, v 0.1 2021-01-04 5:52 下午 CoderMa
 */
public class UserCommand extends AbstractFtpCommand {

    public UserCommand() {
        super("USER");
    }

    @Override
    public void execute(ChannelHandlerContext ctx, String param) {
        //由于不需要登陆，直接返回认证成功
        send("230 USER LOGGED IN SUCCESSFULLY", ctx);
    }

}