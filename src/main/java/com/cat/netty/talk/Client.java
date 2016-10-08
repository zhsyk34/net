package com.cat.netty.talk;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {

	private static final String HOST = System.getProperty("host", "127.0.0.1");
	private static final int PORT = Integer.parseInt(System.getProperty("port", "8899"));

	private static final Bootstrap BOOTSTRAP = new Bootstrap();

	private class ClientHandler extends ChannelInboundHandlerAdapter {
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			cause.printStackTrace();
			ctx.close();
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		}

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			ByteBuf message = Unpooled.buffer(110);
			for (int i = 0; i < message.capacity(); i++) {
				message.writeByte((byte) i);
			}
			ctx.channel().writeAndFlush(message);
		}
	}

	public void start() throws InterruptedException {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			BOOTSTRAP.group(group).channel(NioSocketChannel.class);
			BOOTSTRAP.option(ChannelOption.TCP_NODELAY, true);

			BOOTSTRAP.handler(new ClientHandler());

			ChannelFuture future = BOOTSTRAP.connect(HOST, PORT).sync();
			future.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
	}
}
