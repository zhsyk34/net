package com.cat.netty.talk;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Task {

	public static void main(String[] args) {
		//ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		ScheduledThreadPoolExecutor service = new ScheduledThreadPoolExecutor(1);

		Runnable task = () -> {
			System.out.println("begin task");
//			System.out.println("count: " + map.size());
			/*map.forEach((k, v) -> {
				System.out.println(k);
				v.writeAndFlush("hello,now is" + System.currentTimeMillis());
			});*/
		};
		service.scheduleWithFixedDelay(task, 1000, 2000, TimeUnit.MILLISECONDS);
	}
}
