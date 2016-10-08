package com.cat.netty.talk;

import io.netty.buffer.ByteBuf;

public class Util {

	public static void read(Object object) {
		if (object instanceof ByteBuf) {
			ByteBuf buf = (ByteBuf) object;

			/*while (buf.isReadable()) {
				System.out.print((char) buf.readByte());
			}
			buf.readerIndex(0);*/

			System.out.println(buf.readableBytes());
			for (int i = 0; i < buf.readableBytes(); i++) {
				System.out.print((char) buf.getByte(i));
			}
		}
	}

}
