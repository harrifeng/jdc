package org.hfeng.netty._01;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.net.InetSocketAddress;

public class EchoServer {
    public static void main(String[] args) throws Exception{
	// if (args.length != 1) {
	//     System.err.println("Usage: <App> + <port>");
	//     return ;
	// }
	// int port = Integer.parseInt(args[0]);
	int port = 12345;
	new EchoServer(port).start();
    }

    private final int port;
    public EchoServer(int port) {
	this.port = port;
    }

    public void start() throws Exception {
	final EchoServerHandler serverHandler = new EchoServerHandler();
	EventLoopGroup group = new NioEventLoopGroup();
	try {
	    ServerBootstrap b = new ServerBootstrap();
	    b.group(group)
		.channel(NioServerSocketChannel.class)
		.localAddress(new InetSocketAddress(port))
		.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel ch) throws Exception{
			    ch.pipeline().addLast(serverHandler);
			}
		    });
	    ChannelFuture f = b.bind().sync();
	    System.out.println(EchoServer.class.getName() + " started and listening for connections on " + f.channel().localAddress());
	    f.channel().closeFuture().sync();

	} finally {
	    group.shutdownGracefully().sync();
	}
    }
}
