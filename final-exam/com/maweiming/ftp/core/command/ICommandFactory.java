/**
 * fshows.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.maweiming.ftp.core.command;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author CoderMa
 * @version ICommandFactory.java, v 0.1 2021-01-04 7:13 下午 CoderMa
 */
public interface ICommandFactory {

    void start(ChannelHandlerContext ctx, String message);

}