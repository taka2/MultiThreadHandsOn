package handson3;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ConcurrentLinkedQueueと同期化したLinkedListのパフォーマンスを比較するサンプル
 */
public class ListPerformance {
	// データ数
	private static final int NUM_DATA = 2000000;
	// 計算量
	private static final int CALC_AMOUNT = 200;

	// 共有オブジェクト
	private static final Queue<Integer> queue = new LinkedList<Integer>();
	public static void main(String[] args) {
		// スレッド数を変えながらタスクを実行する（タスク数は定数で設定）
		int[] NUM_THREADS = {1, 2, 3, 4, 5, 10, 20, 50, 100};
		for(int numThreads : NUM_THREADS) {
			long executionTime = executeTask(numThreads);
			System.out.printf("numThreads = %3d, executionTime = %5d[ms]\n", numThreads, executionTime);
		}
	}

	// キューに入ったデータを処理するタスク
	private static class DequeueTask implements Runnable {
		public void run() {
			while(true) {
				// キューからデータの取り出し
				Integer data = null;
				synchronized(queue) {
					data = queue.poll();
				}
				if(data == null) {
					// キューサイズがゼロになったら終了
					break;
				} else {
					// なんらかの計算
					for(int i=0; i<CALC_AMOUNT; i++) {
						data = data + 1;
					}
				}
			}
		}
	}

	// スレッド数を指定して処理を実行
	private static long executeTask(int numThreads) {
		// キューにデータを投入
		synchronized(queue) {
			queue.clear();
			for(int i=0; i<NUM_DATA; i++) {
				queue.add(i);
			}
		}

		long startTime = System.currentTimeMillis();

		// キューに入ったデータを処理するタスクを実行
		ExecutorService service = Executors.newFixedThreadPool(numThreads);
		for(int i=0; i<numThreads; i++) {
			service.submit(new DequeueTask());
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
