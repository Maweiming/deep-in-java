/**
 * fshows.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.maweiming.ftp.core.command;

import com.maweiming.ftp.core.NettpAttrKeys;
import com.maweiming.ftp.core.util.CommandUtils;
import io.netty.channel.ChannelHandlerContext;

import java.io.File;

/**
 * 改变工作目录
 *
 * @author CoderMa
 * @version CwdCommand.java, v 0.1 2021-01-04 6:32 下午 CoderMa
 */
public class CwdCommand extends AbstractFtpCommand {
    public CwdCommand() {
        super("CWD");
    }

    @Override
    public void execute(ChannelHandlerContext ctx, String param) {
        ctx.attr(NettpAttrKeys.CWD).set(param);
        String fileName = CommandUtils.currDirectory;
        //上一级
        if (param.equals("..")) {
            int ind = fileName.lastIndexOf("/");
            if (ind > 0) {
                fileName = fileName.substring(0, ind);
            }
        } else if ((param != null) && (!param.equals("."))) {
            fileName = fileName + "/" + param;
        }
        // 判断路径是否存在
        File f = new File(fileName);
        if (f.exists() && f.isDirectory()) {
            CommandUtils.currDirectory = fileName;
            send("250 The current directory has been changed to " + CommandUtils.currDirectory, ctx);
        } else {
            send("550 Requested action not taken. File unavailable.", ctx);
        }
    }
}