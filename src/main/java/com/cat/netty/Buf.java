package com.cat.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.DefaultEventExecutorGroup;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Buf {

	public static void main(String[] args) {
		byte[] arr1 = new byte[8];
		for (int i = 0; i < arr1.length; i++) {
			arr1[i] = (byte) ((i + 65) % 256);
		}
		ByteBuf buf = Unpooled.copiedBuffer(arr1);

		byte[] arr2 = new byte[8];
		for (int i = 0; i < arr2.length; i++) {
			arr2[i] = (byte) ((i + 65 + 25) % 256);
		}
		ByteBuf buf2 = Unpooled.copiedBuffer(arr2);

		//System.out.println(buf.hasArray());

		print(buf);
		read(buf2);

		CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();
		compositeByteBuf.addComponents(buf, buf2);

//		compositeByteBuf.readerIndex(0);
//		System.out.println(compositeByteBuf.readableBytes());
//		int index = compositeByteBuf.readerIndex();
//		System.out.println(index);
//		byte b = compositeByteBuf.readByte();
//		System.out.println(b);
//		byte b = compositeByteBuf.readByte();
//		System.out.println((char)b);

		System.out.println(buf.readerIndex());
		buf.readByte();
		buf.readInt();
		System.out.println(buf.readerIndex());
//		print(compositeByteBuf);

//		ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(8);
//		System.out.println(buffer.leng);
//		buffer.writeInt()

		Channel channel = null;
		ByteBufAllocator alloc = channel.alloc();
		ByteBuf bbf = alloc.directBuffer();
		int i = bbf.refCnt();

		ChannelPipeline cp = null;
//		cp.addAfter(null, null);
		ReferenceCountUtil.release(null);//释放

		DefaultEventExecutorGroup dd;

		SimpleChannelInboundHandler simpleChannelInboundHandler;

	}

	private static void print(ByteBuf buf) {
		for (int i = 0; i < buf.capacity(); i++) {
			System.out.print((char) buf.getByte(i) + " ");
		}
		System.out.println();
	}

	private static void read(ByteBuf buf) {
		while (buf.isReadable()) {
			System.out.print((char) buf.readByte() + " ");
		}
	}

	private static String get(byte[] bytes) {
		Charset charset = StandardCharsets.UTF_8;
		return new String(bytes, charset);
	}
}
