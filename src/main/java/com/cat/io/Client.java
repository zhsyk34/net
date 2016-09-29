package com.cat.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

public class Client {

    private static final String EXIT = "exit";

    public void connect(String host, int port) {
        try {
            Socket client = new Socket(host, port);
            System.out.println("client connect...");

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

    private static class ClientReceiveHandler implements Runnable {

        private Socket socket;

        public ClientReceiveHandler(Socket socket) {
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
