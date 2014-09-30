package handson3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * コンテキストスイッチのコストを見るためのサンプル（マルチスレッド版）
 */
public class ContextSwitch2 {
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
		ExecutorService service = Executors.newFixedThreadPool(1000);		
		for(int i=0; i<1000; i++) {
			service.submit(task);
		}
		service.shutdown();
		try {
			service.awaitTermination(60, TimeUnit.SECONDS);
		} catch(InterruptedException e) {
			// 何もしない
		}
		long endTime = System.currentTimeMillis();
		System.out.println("time = " + (endTime - startTime) + "[ms]");
	}
}
