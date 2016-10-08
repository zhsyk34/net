package com.cat.test;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 处理服务端 channel.
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		ByteBuf buf = (ByteBuf) msg;
		while (buf.isReadable()) {
			System.out.print((char)buf.readByte() + " ");
		}
		//System.out.println(ctx.channel().remoteAddress() + "->Server :" + msg.toString());
		ctx.write(msg); // (1)
		ctx.flush(); // (2)
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// 当出现异常就关闭连接
		cause.printStackTrace();
		ctx.close();
	}
}