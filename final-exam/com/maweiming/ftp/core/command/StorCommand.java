/**
 * fshows.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.maweiming.ftp.core.command;

import com.maweiming.ftp.core.NettpAttrKeys;
import com.maweiming.ftp.core.util.CommandUtils;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;
import sun.net.ftp.FtpClient;

import java.io.*;
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
                if (ctx.attr(NettpAttrKeys.TRANSFER_MODE).get().equals(FtpClient.TransferType.BINARY)) {

                    binary(ctx, socket, file);
                } else {
                    // ASCII mode
                    ascii(ctx, socket, file);
                }

            }
            close(socket, printWriter);
        }
    }

    /**
     * 二进制传输
     */
    private void binary(ChannelHandlerContext ctx, Socket socket, File file) {
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        CommandUtils.send("150 Opening binary mode " + file.getName(), ctx);
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
            bis = new BufferedInputStream(socket.getInputStream());
            byte[] buf = new byte[1024];
            int l = 0;
            while ((l = bis.read(buf, 0, 1024)) != -1) {
                bos.write(buf, 0, l);
            }
        } catch (Exception e) {
            System.err.println("StorCommand>binary,file streams operating error");
        } finally {
            try {
                if (null != bis) {
                    bis.close();
                }
                if (null != bos) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("文件上传成功 > " + file.getName());
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
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(new FileOutputStream(file), true);
            String s;
            while ((s = br.readLine()) != null) {
                pw.println(s);
            }
        } catch (IOException e) {
            System.err.println("StorCommand>ascii,file streams operating error");
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