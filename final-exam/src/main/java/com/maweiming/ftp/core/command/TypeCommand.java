/**
 * fshows.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.maweiming.ftp.core.command;

import com.maweiming.ftp.core.NettpAttrKeys;
import io.netty.channel.ChannelHandlerContext;
import sun.net.ftp.FtpClient;

/**
 * 设定传输模式（ASCII/二进制).
 *
 * @author CoderMa
 * @version TypeCommand.java, v 0.1 2021-01-04 6:04 下午 CoderMa
 */
public class TypeCommand extends AbstractFtpCommand {
    public TypeCommand() {
        super("TYPE");
    }

    @Override
    public void execute(ChannelHandlerContext ctx, String param) {
        String command = getCommand(() -> {
            if (param.equals("A")) {
                ctx.attr(NettpAttrKeys.TRANSFER_MODE).set(FtpClient.TransferType.ASCII);
                return "200 Type set to ASCII";
            } else if (param.equals("I")) {
                ctx.attr(NettpAttrKeys.TRANSFER_MODE).set(FtpClient.TransferType.BINARY);
                return "200 Type set to BINARY";
            } else
                return "504 Type set error";
        });
        send(command, ctx);
    }
}