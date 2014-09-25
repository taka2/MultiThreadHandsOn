package handson3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * synchronizedで同期を取るカウンタと、アトミック変数を使ったカウンタの比較を行うサンプル
 */
public class AtomicVariable {
	// タスク数（負荷の大きさ）
	private static final int LOOP_COUNT = 100000;
	
	// synchronizedを使って並行で数を数えるクラス
	private static class Counter {
		private int counter;
		public synchronized int increment() {
			counter++;
			return counter;
		}
		public void resetCounter() {
			this.counter = 0;
		}
	}
	public static void main(String[] args) {
		final Counter counter = new Counter();

		// synchronizedを使って並行で数を数えるタスク
		Runnable counterTask = new Runnable() {
			public void run() {
				for(int i=0; i<LOOP_COUNT; i++) {
					counter.increment();
				}
			}
		};
		
		final AtomicInteger atomicCounter = new AtomicInteger();

		// アトミック変数を使って数を数えるタスク
		Runnable atomicCounterTask = new Runnable() {
			public void run() {
				for(int i=0; i<LOOP_COUNT; i++) {
					atomicCounter.incrementAndGet();
				}
			}
		};

		// スレッド数を変えながらタスクを実行する（タスク数は定数で設定）
		int[] NUM_THREADS = {1, 2, 3, 4, 5, 10, 20, 50, 100};
		System.out.println("ロックオブジェクトを使って数を数えるタスク");
		for(int numThreads : NUM_THREADS) {
			long executionTime = executeTask(counterTask, numThreads);
			System.out.printf("numThreads = %3d, executionTime = %5d[ms]\n", numThreads, executionTime);
			counter.resetCounter();
		}
		
		System.out.println("アトミック変数を使って数を数えるタスク");
		for(int numThreads : NUM_THREADS) {
			long executionTime = executeTask(atomicCounterTask, numThreads);
			System.out.printf("numThreads = %3d, executionTime = %5d[ms]\n", numThreads, executionTime);
			atomicCounter.set(0);
		}
	}
	
	/**
	 * 指定したタスクを指定したスレッド数で実行する
	 * @param task タスク
	 * @param numThreads スレッド数
	 * @return かかった時間[ms]
	 */
	private static long executeTask(Runnable task, int numThreads) {
		// 開始時刻
		long startTime = System.currentTimeMillis();

		ExecutorService service = Executors.newFixedThreadPool(numThreads);

		List<Future<?>> taskResults = new ArrayList<Future<?>>();
		// タスクの実行を依頼
		for(int i=0; i<numThreads; i++) {
			taskResults.add(service.submit(task));
		}
		
		// タスクの実行完了を待ち合わせ
		for(Future<?> taskResult : taskResults) {
			try {
				taskResult.get();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

		service.shutdown();

		// 終了時刻
		long endTime = System.currentTimeMillis();
		return (endTime - startTime);
	}
}
