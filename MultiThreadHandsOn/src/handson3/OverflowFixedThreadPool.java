package handson3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 固定スレッドプール数以上のタスクを投入したらどうなるかを調べるサンプル
 * 結構負荷かけてるつもりだけど、淡々とキューイングして落ちない。
 */
public class OverflowFixedThreadPool {
	// スレッドプール数
	private static final int NUM_THREADS = 1;
	// タスク数
	private static final int NUM_TASKS = 10000000;
	// タスクの実行時間（スリープ時間）[ms]
	private static final int TASK_EXECUTION_TIME = 600000;

	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
		for(int i=1; i<=NUM_TASKS; i++) {
			executor.submit(new SleepTask());
		}
		System.out.println("done");
	}

	/**
	 * 指定秒数スリープするタスク
	 */
	private static class SleepTask implements Runnable {
		public void run() {
			try {
				Thread.sleep(TASK_EXECUTION_TIME);
			} catch(InterruptedException e) {
				// 何もしない。
			}
		}	
	}
}
