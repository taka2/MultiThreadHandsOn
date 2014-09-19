package handson3;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ロックストライピングのサンプル
 */
public class LockStriping {
	// データ数
	private static final int NUM_DATA = 1000000;
	// 計算量
	private static final int CALC_AMOUNT = 10000;

	// 共有オブジェクト
	
	public static void main(String[] args) {
		// スレッド数、ストライプ数を変えながらタスクを実行する（タスク数は定数で設定）
		int[] NUM_THREADS = {4, 8, 16};
		int[] NUM_STRIPES = {1, 4, 16, 32, 64, 128};
		for(int numThreads : NUM_THREADS) {
			for(int numStripes : NUM_STRIPES) {
				final ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<Integer, Integer>(NUM_DATA, 0.75f, numStripes);
				for(int i=0; i<NUM_DATA; i++) {
					map.put(i, i);
				}
				long executionTime = executeTask(map, numThreads);
				System.out.printf("numThreads = %3d, numStripes = %3d, executionTime = %5d[ms]\n", numThreads, numStripes, executionTime);
				System.gc();
			}
		}
	}

	// キューに入ったデータを処理するタスク
	private static class DequeueTask implements Runnable {
		private Map<Integer, Integer> map;
		private int threadNumber;

		public DequeueTask(Map<Integer, Integer> map, Integer threadNumber) {
			this.map = map;
			this.threadNumber = threadNumber;
		}

		public void run() {
			for(int i=0; i<CALC_AMOUNT; i++) {
				int index = threadNumber * CALC_AMOUNT + i;
				map.get(index);
			}
		}
	}

	// スレッド数を指定して処理を実行
	private static long executeTask(Map<Integer, Integer> map, int numThreads) {
		long startTime = System.currentTimeMillis();

		// キューに入ったデータを処理するタスクを実行
		ExecutorService service = Executors.newFixedThreadPool(numThreads);
		for(int i=0; i<numThreads; i++) {
			service.submit(new DequeueTask(map, i));
		}
		
		service.shutdown();
		try {
			service.awaitTermination(60, TimeUnit.SECONDS);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		return (endTime - startTime);
	}
}
