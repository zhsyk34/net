package com.cat.netty.talk;

public class TestClient {

	public static void main(String[] args) throws InterruptedException {
//		new BaseClient().connect("localhost", 8899);
		new Client().start();
	}
}
