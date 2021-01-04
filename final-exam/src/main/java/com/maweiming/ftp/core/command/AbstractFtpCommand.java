/**
 * fshows.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.maweiming.ftp.core.command;

import com.maweiming.ftp.core.util.CommandUtils;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.Supplier;

/**
 * @author CoderMa
 * @version FtpCommand.java, v 0.1 2021-01-04 5:54 下午 CoderMa
 */
public abstract class AbstractFtpCommand implements FtpCommand {

    protected final String cmd;

    public AbstractFtpCommand(String cmd) {
        this.cmd = cmd.trim().toUpperCase();
    }

    public static String getCommand(Supplier<String> function) {
        return function.get();
    }

    protected void send(String response, ChannelHandlerContext ctx) {
        System.out.println(String.format("cmd=%s,response=%s", cmd, response));
        if (StringUtils.isNotBlank(response)) {
            CommandUtils.send(response, ctx);
        }
    }

    @Override
    public String getCmd() {
        return cmd;
    }

    public void close(Socket socket, PrintWriter printWriter) {
        try {
            if (null != socket) {
                socket.close();
            }
            if (null != printWriter) {
                printWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}