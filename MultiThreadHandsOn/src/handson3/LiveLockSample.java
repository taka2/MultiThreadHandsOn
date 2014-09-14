package handson3;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ライブロックを再現するサンプル
 */
public class LiveLockSample {
	// スレッドプール数
	private static final int FIXED_THREAD_POOL_COUNT = 10;
	
	public static void main(String[] args) {
		final Queue<Integer> queue = new ConcurrentLinkedQueue<Integer>();
		
		// キューにデータを投入するタスク
		Runnable enqueueTask = new Runnable() {
			int counter = 1;
			public void run() {
				queue.add(counter);
				counter++;
			}
		};

		// キューに入ったデータを処理するタスク
		Runnable dequeueTask = new Runnable() {
			public void run() {
				while(true) {
					synchronized(queue) {
						if(queue.size() == 0) {
							continue;
						}

						// キューからデータの取り出し（削除はしない）
						Integer data = queue.peek();
						
						// データをチェック
						if(data > 5) {
							System.out.println("Invalid data. data=" + data);
	
							// エラーになったデータはキューから削除しないでリトライ
							continue;
						}
						
						// データを処理
						System.out.println("Process data. data=" + data);
						
						// キューからデータ取り出し＆削除
						queue.poll();
					}
				}
			}
		};
		
		// キューにデータを投入するタスクは1秒間隔で実行
		ScheduledExecutorService scheduledService = Executors.newScheduledThreadPool(1);
		scheduledService.scheduleAtFixedRate(enqueueTask, 0, 1, TimeUnit.SECONDS);
		
		// キューに入ったデータを処理するタスクを実行
		ExecutorService service = Executors.newFixedThreadPool(FIXED_THREAD_POOL_COUNT);
		service.submit(dequeueTask);
	}
}
