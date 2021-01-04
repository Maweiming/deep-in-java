/**
 * fshows.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.maweiming.ftp.core.command;

import cn.hutool.core.util.StrUtil;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author CoderMa
 * @version CommandFactory.java, v 0.1 2021-01-04 7:12 下午 CoderMa
 */
public class CommandFactory implements ICommandFactory {

    @Override
    public void start(ChannelHandlerContext ctx, String message) {
        FtpCommand ftpCommand = null;
        message = StrUtil.removeAll(message, '\r', '\n');
        System.out.println(message);
        String[] messageArray = message.split(" ");
        String command = messageArray[0];
        String param = messageArray.length > 1 ? messageArray[1] : null;
        //更多FTP命令请参考
        //https://zh.wikipedia.org/wiki/FTP%E5%91%BD%E4%BB%A4%E5%88%97%E8%A1%A8
        switch (command) {
            case "USER":
                ftpCommand = new UserCommand();
                break;
            case "TYPE":
                ftpCommand = new TypeCommand();
                break;
            case "SYST":
                ftpCommand = new SystCommand();
                break;
            case "FEAT":
                ftpCommand = new FeatCommand();
                break;
            case "PWD":
                ftpCommand = new PwdCommand();
                break;
            case "PORT":
                ftpCommand = new PortCommand();
                break;
            case "LIST":
                ftpCommand = new ListCommand();
                break;
            case "NLST":
                ftpCommand = new NlstCommand();
                break;
            case "CWD":
                ftpCommand = new CwdCommand();
                break;
            case "RETR":
                ftpCommand = new RetrCommand();
                break;
            case "STOR":
                ftpCommand = new StorCommand();
                break;
            case "QUIT":
                ftpCommand = new QuitCommand();
                break;
            default:
                ftpCommand = new DefaultCommand(command);
                break;
        }
        ftpCommand.execute(ctx, param);
    }

}