/**
 * fshows.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.maweiming.ftp.core.command;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author CoderMa
 * @version IMessage.java, v 0.1 2021-01-04 5:50 下午 CoderMa
 */
public interface FtpCommand {

    String getCmd();

    void execute(ChannelHandlerContext ctx, String param);

}