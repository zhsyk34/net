package com.cat.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server {

	private static final int PORT = 9999;

	public static void main(String[] args) throws IOException {
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

		ServerSocket server = serverSocketChannel.socket();
		InetSocketAddress address = new InetSocketAddress(PORT);
		server.bind(address);

		serverSocketChannel.configureBlocking(false);

		System.out.println("server start in port:" + PORT);

		Selector selector = Selector.open();
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		while (true) {
			selector.select();

			Set<SelectionKey> selectionKeys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = selectionKeys.iterator();

			while (iterator.hasNext()) {
				SelectionKey selectionKey = iterator.next();

				if (!selectionKey.isValid()) {
					continue;
				}

				if (selectionKey.isAcceptable()) {
					ServerSocketChannel serverChannel = (ServerSocketChannel) selectionKey.channel();
					SocketChannel socketChannel = serverChannel.accept();
					socketChannel.configureBlocking(false);
					socketChannel.register(selector, SelectionKey.OP_READ);

					Socket socket = socketChannel.socket();
					System.out.println("Get a client, the remote client address: " + socket.getRemoteSocketAddress());
				} else if (selectionKey.isReadable()) {
					SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

					Socket client = socketChannel.socket();
					client.setReceiveBufferSize(1024);
					System.out.println(client.getRemoteSocketAddress() + " receive data");
				}

				iterator.remove();
			}
		}
	}
}
