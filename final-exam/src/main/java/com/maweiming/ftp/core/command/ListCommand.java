/**
 * fshows.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.maweiming.ftp.core.command;

import com.maweiming.ftp.core.NettpAttrKeys;
import com.maweiming.ftp.core.util.CommandUtils;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 如果指定了文件或目录，返回其信息；否则返回当前工作目录的信息
 *
 * @author CoderMa
 * @version ListCommand.java, v 0.1 2021-01-04 6:28 下午 CoderMa
 */
public class ListCommand extends AbstractFtpCommand {

    public ListCommand() {
        super("LIST");
    }

    public ListCommand(String cmd) {
        super(cmd);
    }

    @Override
    public void execute(ChannelHandlerContext ctx, String param) {
        try {
            PrintWriter printWriter = ctx.attr(NettpAttrKeys.PRINT_WRITER).get();
            Socket socket = ctx.attr(NettpAttrKeys.SOCKET).get();
            if (printWriter == null || socket.isClosed()) {
                send("425 No data connection was established", ctx);
            } else {
                String[] fileList = getFileList(param);
                if (fileList == null) {
                    send("550 File does not exist.", ctx);
                } else {
                    send("125 Opening ASCII mode data connection for file list.", ctx);
                    for (String file : fileList) {
                        CommandUtils.sendSocketDate(file, ctx);
                    }
                    send("226 Transfer complete.", ctx);
                    printWriter.close();
                    socket.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            send("451 Requested action aborted: local error in processing", ctx);
        }
    }

    private String[] getFileList(String param) {
        String path = CommandUtils.currDirectory;
        if (StringUtils.isNotBlank(param)) {
            path = path + CommandUtils.currDirectory + param;
        }
        File f = new File(path);
        if (f.exists()) {
            return f.list();
        } else {
            return null;
        }
    }

}