package handson3;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Runnableじゃなくて、Callableを実装したタスクを実行前に
 * shutdownNowでrejectしたらどうなるかを調べるサンプル
 */
public class RejectCallableTask {
	// スレッドプール数
	private static final int NUM_THREADS = 10;
	// タスク数
	private static final int NUM_TASKS = 100;

	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
		for(int i=1; i<=NUM_TASKS; i++) {
			executor.submit(new GetCurrentDateTask());
		}
		List<Runnable> runnableList = executor.shutdownNow();
		for(Runnable r : runnableList) {
			System.out.println(r.getClass().getName());
		}

		// 最大5秒シャットダウンの完了を待つ
		try {
			executor.awaitTermination(5, TimeUnit.SECONDS);
		} catch(Exception e) {
			e.printStackTrace();
		}

		System.out.println("done");
	}

	/**
	 * 現在時刻を返すタスク
	 */
	private static class GetCurrentDateTask implements Callable<Date> {
		public Date call() {
			return new Date();
		}	
	}
}
