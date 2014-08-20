package handson2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

public class ExecutorShutdownSample1 {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		for(int i=1; i<=10; i++) {
			executor.submit(new MyTask(i));
		}
		executor.shutdown();
		
		// シャットダウン後は新たなタスクは受け付けられない
		try {
			executor.submit(new MyTask(100));
		} catch(RejectedExecutionException e) {
			e.printStackTrace();
		}

		// 最大5秒シャットダウンの完了を待つ
		try {
			executor.awaitTermination(5, TimeUnit.SECONDS);
		} catch(Exception e) {
			e.printStackTrace();
		}

		System.out.println("done");
	}

	private static class MyTask implements Runnable {
		private int numLoop;
		public MyTask(int numLoop) {
			this.numLoop = numLoop;
		}

		public void run() {
			for(int i=1; i<=numLoop; i++) {
				try {
					System.out.println(Thread.currentThread().getName() + ":" + i);
					Thread.sleep(1000);
				} catch(InterruptedException e) {
					// 何もしない
				}
			}
		}	
	}
}
