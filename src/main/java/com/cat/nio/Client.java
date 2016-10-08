package com.cat.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class Client {

	private static final String HOST = "localhost";
	private static final int PORT = 9999;

	public static void main(String[] args) throws IOException {
		SocketChannel client = SocketChannel.open();
		client.configureBlocking(false);

		InetSocketAddress address = new InetSocketAddress(HOST, PORT);
		boolean connect = client.connect(address);
		System.out.println(connect);

		//
		Selector selector = Selector.open();
		client.register(selector, SelectionKey.OP_READ);

		long begin = System.nanoTime();
		while (!client.finishConnect()) {
			//wait for connect
		}
		long end = System.nanoTime();
		System.out.println("connect spend time : " + (end - begin) / 1000 + " us.");

		//write
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		buffer.clear();
		buffer.put("Hello".getBytes());
		buffer.flip();
		client.write(buffer);
		/*while (buffer.hasRemaining()) {

		}*/

		//close
		client.close();
	}
}
