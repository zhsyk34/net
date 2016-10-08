package com.cat.netty.talk;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.net.SocketAddress;

/**
 * 在 Channel 或者 ChannelPipeline 上调用write() 都会把事件在整个管道传播,但是在 ChannelHandler 级别上，
 * 从一个处理程序转到下一个却要通过在 ChannelHandlerContext 调用方法实现。
 */
public class ServerHandler extends ChannelHandlerAdapter {
	@Override
	public boolean isSharable() {
		//System.out.println(super.isSharable());
		return true;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		//cause.printStackTrace();
		System.err.println("error>>>>>>>>>>>>>>>");
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		SocketAddress address = ctx.channel().remoteAddress();
		System.out.println(address + " ==>connected in handler.");

	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.print("read in handler:");
//		SocketAddress address = ctx.channel().remoteAddress();
//		System.out.println(address + " : ");
		Util.read(msg);
		System.out.println();

//		ctx.write(msg);
		ctx.channel().writeAndFlush(msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("read complete in handler.");
//		super.channelReadComplete(ctx);

		ctx.flush();
	}

}
