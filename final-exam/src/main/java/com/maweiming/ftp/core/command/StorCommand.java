/**
 * fshows.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.maweiming.ftp.core.command;

import com.maweiming.ftp.core.NettpAttrKeys;
import com.maweiming.ftp.core.util.CommandUtils;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 上传文件
 *
 * @author CoderMa
 * @version StorCommand.java, v 0.1 2021-01-04 6:56 下午 CoderMa
 */
public class StorCommand extends AbstractFtpCommand {
    public StorCommand() {
        super("STOR");
    }

    @Override
    public void execute(ChannelHandlerContext ctx, String param) {
        Socket socket = ctx.attr(NettpAttrKeys.SOCKET).get();
        PrintWriter printWriter = ctx.attr(NettpAttrKeys.PRINT_WRITER).get();
        if (socket.isClosed()) {
            System.out.println("socker 已关闭");
            return;
        }
        if (StringUtils.isBlank(param)) {
            CommandUtils.send("501 No filename given", ctx);
        } else {
            File file = new File(CommandUtils.currDirectory + "/" + param);
            if (file.exists()) {
                CommandUtils.send("550 File already exists", ctx);
            } else {
                //ctx.attr(NettpAttrKeys.TRANSFER_MODE).get();
                //这里可以获取传输类型,暂时只使用二进制模式
                //二进制
                binary(ctx, socket, file);
            }
            close(socket, printWriter);
        }
    }

    /**
     * 二进制传输
     */
    private void binary(ChannelHandlerContext ctx, Socket socket, File file) {
        CommandUtils.send("150 Opening binary mode " + file.getName(), ctx);
        try {
            IOUtils.copy(socket.getInputStream(), new FileOutputStream(file));
        } catch (Exception e) {
            System.err.println("StorCommand > IOUtils copy error");
        } finally {
            try {
                socket.getInputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("文件上传成功 > " + file.getName());
        send("226 File transfer successful.", ctx);
    }

}