package handson;

import java.util.ArrayList;
import java.util.List;

public class MultiProcessor_2 {

	// 計算回数
	private static final int NUM_CALCULATION = 10;

	public static void main(String[] args) {
		List<Thread> threadList = new ArrayList<Thread>();
		for(int i=0; i<NUM_CALCULATION; i++) {
			Runnable r = new Runnable() {
				public void run() {
					long sum = 0;
					for(int j=0; j<1000000000; j++) {
						sum += j;
					}
					System.out.println(sum);
				}
			};
			// スレッド1開始
			Thread t = new Thread(r);
			threadList.add(t);
			t.start();
		}
		
		// 全てのスレッドの完了を待つ
		final int threadListSize = threadList.size();
		for(int i=0; i<threadListSize; i++) {
			try {
				threadList.get(i).join();
			} catch(InterruptedException e) {
				// 何もしない
			}
		}
	}
}
