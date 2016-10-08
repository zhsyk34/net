package com.cat.netty.talk;

import com.cat.io.Util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketAddress;

public class BaseClient {

	public void connect(String host, int port) {
		try {
			Socket client = new Socket(host, port);
			System.out.println("client loading...");

			new Thread(new ClientSendHandler(client)).start();
			new Thread(new ClientReceiveHandler(client)).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static class ClientSendHandler implements Runnable {
		private Socket socket;

		public ClientSendHandler(Socket socket) {
			this.socket = socket;
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

	private static class ClientReceiveHandler implements Runnable {

		private Socket socket;

		public ClientReceiveHandler(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				InputStream input = socket.getInputStream();
				SocketAddress address = socket.getRemoteSocketAddress();
				Util.print(input, "receive from server [" + address + "]:");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
