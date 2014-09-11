package handson3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 飢餓状態を再現するサンプル
 */
public class StarvationSample {
	public static void main(String[] args) {
		final Object sharedObject = new Object();
		
		// 一旦ロックをつかんだら二度と解放しないタスク
		Runnable infiniteLoopTask = new Runnable() {
			public void run() {
				synchronized(sharedObject) {
					while(true) {
					}
				}
			}
		};

		// ロックを取ろうとするが、取得できないタスク
		Runnable starvationTask = new Runnable() {
			public void run() {
				while(true) {
					synchronized(sharedObject) {
						System.out.println("starvationTask");
					}
				}
			}
		};
		
		// 無限ループタスクは2秒後に開始
		ScheduledExecutorService scheduledService = Executors.newScheduledThreadPool(1);
		scheduledService.schedule(infiniteLoopTask, 2, TimeUnit.SECONDS);
		
		// 飢餓状態タスクはすぐ開始
		ExecutorService service = Executors.newCachedThreadPool();
		service.submit(starvationTask);

		scheduledService.shutdown();
		service.shutdown();
	}
}
