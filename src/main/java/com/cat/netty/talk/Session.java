package com.cat.netty.talk;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.util.CharsetUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Session {

	private static final Map<String, Channel> map = new ConcurrentHashMap<>();

	public static void update(Channel channel) {
		map.put("1", channel);
	}

	public static void send() {
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
//		ScheduledThreadPoolExecutor service = new ScheduledThreadPoolExecutor(1);

		Runnable task = () -> {
			System.out.println("begin task");
			System.out.println("count: " + map.size());
			map.forEach((k, v) -> {
				System.out.println(k);
				ByteBuf buf = Unpooled.copiedBuffer("Hello", CharsetUtil.UTF_8);
				v.writeAndFlush(buf);
			});
		};
		service.scheduleWithFixedDelay(task, 1000, 5000, TimeUnit.MILLISECONDS);
	}
}
