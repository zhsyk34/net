package com.cat.io;

public class ClientTest {

	public static void main(String[] args) {
		//new Client().connect("localhost", 8888);

		for (int i = 0; i < 1; i++) {
			new Thread(() -> new AutoWriteClient().connect("localhost", 8888)).start();
		}
	}
}
