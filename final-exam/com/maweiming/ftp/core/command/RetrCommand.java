/**
 * fshows.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.maweiming.ftp.core.command;

import com.maweiming.ftp.core.NettpAttrKeys;
import com.maweiming.ftp.core.util.CommandUtils;
import io.netty.channel.ChannelHandlerContext;
import sun.net.ftp.FtpClient;

import java.io.*;
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
        PrintWriter printWriter = ctx.attr(NettpAttrKeys.PRINT_WRITER).get();
        File file = new File(CommandUtils.currDirectory + "/" + param);
        if (!file.exists()) {
            send("550 File does not exist", ctx);
        } else {
            FtpClient.TransferType transferType = ctx.attr(NettpAttrKeys.TRANSFER_MODE).get();
            //判断传输类型
            if (transferType.equals(FtpClient.TransferType.BINARY)) {
                //二进制
                binary(ctx, socket, file);
            } else {
                //ASCII
                ascii(ctx, socket, file);
            }
        }
        close(socket, printWriter);
    }

    /**
     * 二进制传输
     */
    private void binary(ChannelHandlerContext ctx, Socket socket, File file) {
        send("150 Opening binary mode " + file.getName(), ctx);
        BufferedOutputStream fout = null;
        BufferedInputStream fin = null;
        try {
            fout = new BufferedOutputStream(socket.getOutputStream());
            fin = new BufferedInputStream(new FileInputStream(file));
            byte[] buf = new byte[1024];
            int i = 0;
            while ((i = fin.read(buf, 0, 1024)) != -1) {
                fout.write(buf, 0, i);
            }
        } catch (Exception e) {
            System.err.println("RetrCommand>binary,file streams operating error");
        } finally {
            try {
                if (null != fin) {
                    fin.close();
                }
                if (null != fout) {
                    fout.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("文件下载成功:" + file.getName());
        send("226 File transfer successful.", ctx);
    }

    /**
     * ASCII传输
     */
    private void ascii(ChannelHandlerContext ctx, Socket socket, File file) {
        send("150 Opening ASCII mode " + file.getName(), ctx);
        BufferedReader br = null;
        PrintWriter pw = null;
        try {
            br = new BufferedReader(new FileReader(file));
            pw = new PrintWriter(socket.getOutputStream(), true);
            String s;
            while ((s = br.readLine()) != null) {
                pw.println(s);
            }
        } catch (IOException e) {
            System.err.println("RetrCommand>ascii,file streams operating error");
        } finally {
            try {
                if (null != pw) {
                    pw.close();
                }
                if (null != br) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        send("226 File transfer successful.", ctx);
    }

}