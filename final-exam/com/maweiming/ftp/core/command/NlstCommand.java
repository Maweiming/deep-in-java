/**
 * fshows.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.maweiming.ftp.core.command;

/**
 * 返回指定目录的文件名列表（和LIST一样）
 *
 * @author CoderMa
 * @version NlstCommand.java, v 0.1 2021-01-04 6:31 下午 CoderMa
 */
public class NlstCommand extends ListCommand {

    public NlstCommand() {
        super("NLST");
    }

}