package com.cat.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {

    private int port;

    public Server bind(int port) {
        this.port = port;
        return this;
    }

    public void start() {
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("server start in " + port);
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
        }

        @Override
        public void run() {
            try {
                OutputStream outputStream = socket.getOutputStream();

                int i = 0;

                while (i != -1) {
                    i = System.in.read();
                    outputStream.write(i);
                }
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
                System.out.println("client [" + socket.getRemoteSocketAddress() + "] connect.");
                InputStream inputStream = socket.getInputStream();

                int i = 0, count = 0;
                byte[] bytes = new byte[1024];
                while (i != -1) {
                    i = inputStream.read();
                    if (i == 13 || i == 10) {
                        System.err.println("enter");
                        System.out.println("receive from [" + socket.getRemoteSocketAddress() + "]:" + new String(Arrays.copyOf(bytes, count)));
                        count = 0;
                    } else {
                        bytes[count++] = (byte) i;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

