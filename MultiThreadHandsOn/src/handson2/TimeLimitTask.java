package handson2;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TimeLimitTask {
	// 制限時間（秒）
	private static final int TIMEOUT = 5;

	public static void main(String[] args) {
		Runnable r = new Runnable() {
			public void run() {
				long i=1;
				while(true) {
					printFizzBuzz(i);
					i++;
					try {
						Thread.sleep(0);
					} catch(InterruptedException e) {
						// インタラプトされたらループ終了
						break;
					}
				}
			}

			private void printFizzBuzz(long l) {
				if(l % 3 == 0) {
					System.out.print("Fizz");
				} else if(l % 5 == 0) {
					System.out.print("Buzz");
				} else {
					System.out.print(l);
				}
				System.out.println();
			}
		};

		// スレッド開始
		ExecutorService executor = Executors.newCachedThreadPool();
		Future<?> task = executor.submit(r);

		try {
			task.get(TIMEOUT, TimeUnit.SECONDS);
		} catch(TimeoutException e) {
			// タスクはfinallyブロックでキャンセルされる
		} catch(InterruptedException e) {
			// タスクはfinallyブロックでキャンセルされる
		} catch(ExecutionException e) {
			// タスク中で投げられた例外：再投する
			throw new RuntimeException(e);
		} finally {
			// タスクがすでに完了していたら無害
			task.cancel(true);	// 実行中ならインタラプトする
		}

		executor.shutdown();
	}
}
