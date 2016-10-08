package com.cat.netty.talk;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.net.SocketAddress;

public class MessageFilter extends ChannelHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		SocketAddress address = ctx.channel().remoteAddress();
		System.out.println(address + " ==>connected in filter.");
		//add session
		Session.update(ctx.channel());
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.print("receive data in filter:");
		Util.read(msg);

		//EventExecutorGroup executorGroup = new DefaultEventExecutor();
		ctx.fireChannelRead(msg);
		System.out.println();
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("read complete in filter.");
		super.channelReadComplete(ctx);
//		ctx.flush();
	}
}
