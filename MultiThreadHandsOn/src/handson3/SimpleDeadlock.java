package handson3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ロック順デッドロックの単純なサンプル
 */
public class SimpleDeadlock {
	// 調整用スリープ時間
	private static final int SLEEP_TIME = 1000;

	public static void main(String[] args) {
		final Object left = new Object();
		final Object right = new Object();
		
		// left -> rightの順にロックを取得するタスク
		Runnable leftRightTask = new Runnable() {
			public void run() {
				synchronized(left) {
					try {
						Thread.sleep(SLEEP_TIME);
					} catch(InterruptedException e) {
						// 何もしない
					}
					
					synchronized(right) {
						System.out.println("leftRight");
					}
				}
			}
		};

		// right -> leftの順にロックを取得するタスク
		Runnable rightLeftTask = new Runnable() {
			public void run() {
				synchronized(right) {
					try {
						Thread.sleep(SLEEP_TIME);
					} catch(InterruptedException e) {
						// 何もしない
					}

					synchronized(left) {
						System.out.println("rightLeft");
					}
				}
			}
		};
		
		ExecutorService service = Executors.newCachedThreadPool();
		service.submit(leftRightTask);
		service.submit(rightLeftTask);

		service.shutdown();
	}
}
