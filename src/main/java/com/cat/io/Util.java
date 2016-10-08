package com.cat.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Util {

	public static void write(OutputStream out) {
		int i = 0;

		while (i != -1) {
			try {
				i = System.in.read();
				out.write(i);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void format(InputStream input) {
		print(input, null);
	}

	public static void print(InputStream input, String prefix) {
		try {
			int read = 0, count = 0;

			prefix = prefix == null ? "" : prefix;

			byte[] r = new byte[1024];
			while (read != -1) {
				read = input.read();

				if (read == 10 || read == 13) {
					String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
					System.out.println(time + " " + prefix + new String(Arrays.copyOf(r, count)));
					count = 0;
				} else {
					r[count++ % 1024] = (byte) read;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void print2(InputStream input, String prefix) {
		try {
			int read = 0;

			prefix = prefix == null ? "" : prefix;

			//ByteArrayOutputStream out = new ByteArrayOutputStream();

			byte[] r = new byte[1024 * 2];
			while (read != -1) {
				read = input.read(r, 0, r.length);
				System.out.println("len"+read);
				//System.out.println(read);
				//System.out.println("read " + read + " byte");

				//byte[] receive = Arrays.copyOf(r, read);
				pb(r);
				//System.out.println(receive.length+":"+new String(receive));
//				String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
//				System.out.println(time + " " + prefix + new String(receive));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void pb(byte[] bs) {
		for (int i = 0; i < bs.length; i++) {
			System.out.print((char) bs[i]);
		}
	}
}
