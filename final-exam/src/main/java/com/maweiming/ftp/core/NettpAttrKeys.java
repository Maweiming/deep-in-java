package com.maweiming.ftp.core; /**
 * fshows.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */

import io.netty.util.AttributeKey;
import sun.net.ftp.FtpClient;

import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author CoderMa
 * @version com.maweiming.ftp.core.NettpAttrKeys.java, v 0.1 2021-01-04 5:00 下午 CoderMa
 */
public class NettpAttrKeys {

    public static final AttributeKey<String> CWD = new AttributeKey<String>("CWD");
    public static final AttributeKey<Socket> SOCKET = new AttributeKey<Socket>("SOCKET");
    public static final AttributeKey<PrintWriter> PRINT_WRITER = new AttributeKey<PrintWriter>("PRINT_WRITER");
    public static final AttributeKey<FtpClient.TransferType> TRANSFER_MODE = new AttributeKey<FtpClient.TransferType>("TRANSFER_MODE");

}