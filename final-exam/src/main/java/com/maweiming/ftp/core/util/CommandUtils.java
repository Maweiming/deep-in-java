/**
 * fshows.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.maweiming.ftp.core.util;

import com.maweiming.ftp.core.NettpAttrKeys;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

/**
 * @author CoderMa
 * @version CommandUtils.java, v 0.1 2021-01-04 7:50 下午 CoderMa
 */
public class CommandUtils {

    public static String currDirectory;

    public static void send(String response, ChannelHandlerContext ctx) {
        String line = response + "\r\n";
        byte[] data = line.getBytes(CharsetUtil.US_ASCII);
        ctx.channel().writeAndFlush(Unpooled.wrappedBuffer(data));
    }

    public static void sendSocketDate(String response, ChannelHandlerContext ctx) {
        String line = response + "\r\n";
        ctx.attr(NettpAttrKeys.PRINT_WRITER).get().print(line);
    }

}