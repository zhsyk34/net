package com.cat.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

import static java.lang.System.out;

public class Server {

	private int port;

	public Server bind(int port) {
		this.port = port;
		return this;
	}

	public void start() {
		try {
			ServerSocket server = new ServerSocket(port);
			server.setReceiveBufferSize(128 * 2);
			out.println("server start in " + port);
			while (true) {
				Socket client = server.accept();
				new Thread(new ServerReceiveHandler(client)).start();
				new Thread(new ServerSendHandler(client)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static class ServerSendHandler implements Runnable {
		private Socket socket;

		public ServerSendHandler(Socket socket) {
			this.socket = socket;
			System.out.println(socket.getRemoteSocketAddress() + " connect.");
		}

		@Override
		public void run() {
			try {
				OutputStream out = socket.getOutputStream();
				Util.write(out);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static class ServerReceiveHandler implements Runnable {

		private Socket socket;

		public ServerReceiveHandler(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				InputStream input = socket.getInputStream();
				SocketAddress address = socket.getRemoteSocketAddress();
				Util.print2(input, "receive from client [" + address + "]:");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

