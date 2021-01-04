/**
 * fshows.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.maweiming.ftp.core.command;

import com.maweiming.ftp.core.NettpAttrKeys;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 指定服务器要连接的地址和端口（和客户端进行socket通信）
 *
 * @author CoderMa
 * @version PortCommand.java, v 0.1 2021-01-04 6:24 下午 CoderMa
 */
public class PortCommand extends AbstractFtpCommand {
    public PortCommand() {
        super("PORT");
    }

    @Override
    public void execute(ChannelHandlerContext ctx, String param) {
        String command = getCommand(() -> {
            try {
                String[] stringSplit = param.split(",");
                if (stringSplit.length != 6) {
                    return "501 Syntax error in parameters or arguments";
                }
                String host = String.format("%s.%s.%s.%s", stringSplit[0], stringSplit[1], stringSplit[2], stringSplit[3]);
                Integer port = Integer.parseInt(stringSplit[4]) * 256 + Integer.parseInt(stringSplit[5]);
                Socket socket = new Socket(host, port);
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                ctx.attr(NettpAttrKeys.SOCKET).set(socket);
                ctx.attr(NettpAttrKeys.PRINT_WRITER).set(printWriter);
                return "200 Command okay";
            } catch (IOException e) {
                e.fillInStackTrace();
                return "501 Socket error";
            }
        });
        send(command, ctx);
    }
}