/**
 * fshows.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.maweiming.ftp.core.command;

import com.maweiming.ftp.core.NettpAttrKeys;
import com.maweiming.ftp.core.util.CommandUtils;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 文件下载
 *
 * @author CoderMa
 * @version RetrCommand.java, v 0.1 2021-01-04 6:41 下午 CoderMa
 */
public class RetrCommand extends AbstractFtpCommand {
    public RetrCommand() {
        super("RETR");
    }

    @Override
    public void execute(ChannelHandlerContext ctx, String param) {
        Socket socket = ctx.attr(NettpAttrKeys.SOCKET).get();
        File file = new File(CommandUtils.currDirectory + "/" + param);
        if (!file.exists()) {
            send("550 File does not exist", ctx);
        } else {
            //ctx.attr(NettpAttrKeys.TRANSFER_MODE).get();
            //这里可以获取传输类型,暂时只使用二进制模式
            //二进制
            binary(ctx, socket, file);
        }
        close(socket, null);
    }

    /**
     * 二进制传输
     */
    private void binary(ChannelHandlerContext ctx, Socket socket, File file) {
        send("150 Opening binary mode " + file.getName(), ctx);
        try {
            IOUtils.copy(new FileInputStream(file), socket.getOutputStream());
        } catch (Exception e) {
            System.err.println("RetrCommand > IOUtils copy error");
        } finally {
            try {
                socket.getOutputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("文件下载成功:" + file.getName());
        send("226 File transfer successful.", ctx);
    }

}