package com.cat.io;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class AutoWriteClient {

	private static final String EXIT = "exit";

	public void connect(String host, int port) {
		try {
			Socket client = new Socket(host, port);
			System.out.println("client loading...");

			OutputStream out = client.getOutputStream();
			/*while (true) {
				*//*try {
					Thread.sleep((int) (Math.random() * 4 * 1000));
					out.write(("yes\r").getBytes());
				} catch (InterruptedException e) {
					e.printStackTrace();
					break;
				}*//*
			}*/

			for (int i = 0; i < 100; i++) {
				out.write("a1b2c3d4f5=\r".getBytes());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
