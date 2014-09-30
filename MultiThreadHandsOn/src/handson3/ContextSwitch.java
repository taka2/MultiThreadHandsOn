package handson3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * コンテキストスイッチのコストを見るためのサンプル（シングルスレッド版）
 */
public class ContextSwitch {
	// 計算量
	private static final int CALC_AMOUNT = 1000000;

	public static void main(String[] args) {
		// 何か計算するタスク
		Runnable task = new Runnable() {
			public void run() {
				for(int i=0; i<CALC_AMOUNT; i++) {
					Math.pow(i, 2);
				}
			}
		};
		
		long startTime = System.currentTimeMillis();
		ExecutorService service = Executors.newSingleThreadExecutor();
		for(int i=0; i<1000; i++) {
			service.submit(task);
		}
		service.shutdown();
		try {
			service.awaitTermination(5, TimeUnit.SECONDS);
		} catch(InterruptedException e) {
			// 何もしない
		}
		long endTime = System.currentTimeMillis();
		System.out.println("time = " + (endTime - startTime) + "[ms]");
	}
}
