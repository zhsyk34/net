package com.cat.netty.talk;

import com.cat.netty.file.FileServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

public class Server {

	//private String host;
	private int port = 8899;
	private int backlog = 1000;

	private static final ServerBootstrap BOOTSTRAP = new ServerBootstrap();

	/*@ChannelHandler.Sharable
	private class ServerHandler extends SimpleChannelInboundHandler {
		@Override
		protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
			SocketAddress address = ctx.channel().remoteAddress();

			ByteBuf message = (ByteBuf) msg;
			System.out.print(address + ":");
			while (message.isReadable()) {
				System.out.print((char) message.readByte());
			}
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			Channel channel = ctx.channel();
			SocketAddress address = channel.remoteAddress();
			System.out.println(address + " closed.");
			ctx.close();
		}
	}*/

	public void start() throws InterruptedException {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			BOOTSTRAP.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class);
			BOOTSTRAP.option(ChannelOption.SO_BACKLOG, backlog);

			addLogHandler();

			//BOOTSTRAP.childHandler(new ReadTimeoutHandler(5));
//			BOOTSTRAP.childHandler(new MessageFilter());
//			BOOTSTRAP.childHandler(new ServerHandler());

			BOOTSTRAP.childHandler(new ChannelInitializer<Channel>() {
				@Override
				protected void initChannel(Channel ch) throws Exception {
					ch.pipeline().addLast(new MessageFilter(), new ServerHandler());
					//ch.pipeline().addLast(new MessageFilter(), new ServerHandler());
					/*ch.pipeline().addAfter(new EventExecutor() {
					});*/
				}
			});
			//addHandlers(new MessageFilter(), new ServerHandler());


			ChannelFuture future = BOOTSTRAP.bind(port).sync();
			System.out.println("Server start at port : " + port);
			future.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	private void addBaseHandler() {
		ChannelHandler[] channelHandlers = new ChannelHandler[]{
				new StringEncoder(CharsetUtil.UTF_8),
				new LineBasedFrameDecoder(1024),
				new StringDecoder(CharsetUtil.UTF_8),
				new FileServerHandler()
		};
		addHandlers(channelHandlers);
	}

	/**
	 * 心跳
	 */
	protected void heartBeat() {
		final int READ_IDEL_TIME_OUT = 4; // 读超时
		final int WRITE_IDEL_TIME_OUT = 5;// 写超时
		final int ALL_IDEL_TIME_OUT = 7; // 所有超时

		ChannelHandler heart = new IdleStateHandler(READ_IDEL_TIME_OUT, WRITE_IDEL_TIME_OUT, ALL_IDEL_TIME_OUT, TimeUnit.SECONDS);
		BOOTSTRAP.childHandler(heart);
	}

	protected void addLogHandler() {
		BOOTSTRAP.handler(new LoggingHandler(LogLevel.DEBUG));
	}

	protected void addHandler(ChannelHandler channelHandler) {
		BOOTSTRAP.childHandler(channelHandler);
	}

	protected void addHandlers(ChannelHandler... channelHandlers) {
		if (channelHandlers == null || channelHandlers.length == 0) {
			return;
		}
		BOOTSTRAP.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(channelHandlers);
			}
		});
	}
}
