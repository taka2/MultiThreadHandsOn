package handson3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * ロックの粒度を小さくするサンプル
 */
public class ShrinkLockScope {
	public static void main(String[] args) {
		final List<Integer> sharedObject = new ArrayList<Integer>();

		// ロックスコープが大きいタスク
		Runnable task1 = new Runnable() {
			public void run() {
				synchronized(sharedObject) {
					// 何か処理
					doSomething();
					
					// 共有オブジェクトに対する処理
					sharedObject.add(1);
					
					System.out.println("Done.");
				}
			}
		};
		// ロックスコープを小さくしたタスク
		Runnable task2 = new Runnable() {
			public void run() {
				// 何か処理
				doSomething();
				
				synchronized(sharedObject) {	
					// 共有オブジェクトに対する処理
					sharedObject.add(1);
				}
				
				System.out.println("Done.");
			}
		};
		ExecutorService service = Executors.newCachedThreadPool();
		System.out.println("ロックスコープが大きいタスクを2つ実行");
		Future<?> future1 = service.submit(task1);
		Future<?> future2 = service.submit(task1);
		
		// タスクの完了を待つ。
		try {
			future1.get();
			future2.get();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("ロックスコープを小さくしたタスクを2つ実行");
		service.submit(task2);
		service.submit(task2);
		service.shutdown();
		
		
	}
	
	private static void doSomething() {
		try {
			Thread.sleep(2000);
		} catch(InterruptedException e) {
			// 何もしない
		}
	}
}
