package com.maweiming.ftp; /**
 * fshows.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */

import com.maweiming.ftp.core.FtpCommandHandler;
import com.maweiming.ftp.core.util.CommandUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author CoderMa
 * @version NettyServer.java, v 0.1 2021-01-03 09:01 下午 CoderMa
 */
public class FtpNettyServer {

    private String ip;
    private int port;
    private String path;

    public FtpNettyServer(String ip, int port, String path) {
        this.ip = ip;
        this.port = port;
        this.path = path;
    }

    public static void main(String[] args) throws Exception {
        String path = "/Users/coderma/Desktop/ftp";
        new FtpNettyServer("127.0.0.1", 10241, path).server();
    }

    public void server() throws Exception {
        CommandUtils.currDirectory = path;
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(1);
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(workerGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new StringDecoder()).addLast(new StringEncoder())
                                    .addLast(new FtpCommandHandler());
                        }
                    });
            // 绑定端⼝ 启动服务端
            System.out.println(String.format("启动成功 > %s:%s", ip, port));
            serverBootstrap.bind(ip, port).channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}