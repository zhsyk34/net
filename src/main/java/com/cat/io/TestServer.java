package com.cat.io;

import java.io.IOException;

public class TestServer {

    public static void main(String[] args) throws IOException {
        /*int r = 0;

        while (r != -1) {//读取输入流中的字节直到流的末尾返回1
            r = System.in.read();
            System.out.println(r);
        }*/
        /*Scanner sc = new Scanner(System.in);
        //sc.useDelimiter(",");
        while (sc.hasNext()) {
            System.out.println(sc.next());
        }*/

        new Server().bind(8888).start();
    }
}
