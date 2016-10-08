package com.cat.netty.talk;

public class TestServer {

	public static void main(String[] args) throws InterruptedException {
		Session.send();
		Server server = new Server();
		server.start();

	}
}
