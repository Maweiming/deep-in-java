/**
 * fshows.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author CoderMa
 * @version NettyServer.java, v 0.1 2021-01-03 09:01 下午 CoderMa
 */
public class NettyServer {

    private String ip;
    private int port;

    public NettyServer(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        new NettyServer("127.0.0.1", 10241).server();
    }

    public void server() throws Exception {
        //⽤于服务端接受客户端的连接
        EventLoopGroup acceptorGroup = new NioEventLoopGroup(1);
        //用于处理请求
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(acceptorGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new StringDecoder()).addLast(new StringEncoder())
                                    .addLast(new FsServerHandler());
                        }
                    });

            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);  // 开启长连接
            // 绑定端⼝ 启动服务端
            ChannelFuture future = serverBootstrap.bind(ip, port).sync();
            System.out.println(String.format("启动成功 > %s:%s", ip, port));
            //监听服务端关闭 并阻塞等待
            future.channel().closeFuture().sync();
        } finally {
            //关闭EventLoopGroup对象
            acceptorGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}