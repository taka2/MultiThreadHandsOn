package handson;

/**
 * スレッドセーフでない可変な整数クラス
 * スレッドセーフ版
 */
public class List03_2 {
	private static final int NUM_LOOP = 1000;

	public static void main(String[] args) {
		// スレッド間で共有するオブジェクト
		final MutableInteger sharedObject = new MutableInteger();

		Runnable r1 = new Runnable() {
			public void run() {
				sharedObject.set(100);
			}
		};
		Runnable r2 = new Runnable() {
			public void run() {
				int value = sharedObject.get();
				System.out.println((value == 100) + "" + value);
			}
		};
		
		for(int i=0; i<NUM_LOOP; i++) {
			sharedObject.set(1);
			// スレッド1開始
			Thread t1 = new Thread(r1);
			t1.start();
			// スレッド2開始
			Thread t2 = new Thread(r2);
			t2.start();
			
			try {
				// 両方のスレッドが終わるのを待ち合わせ
				t1.join();
				t2.join();
			} catch(InterruptedException e) {
				// 何もしない
			}
		}
	}

	/**
	 * スレッドセーフな可変な整数クラス
	 */
	private static class MutableInteger {
		private int value;
		
		public synchronized int get() {
			return value;
		}
		
		public synchronized void set(int value) {
			this.value = value;
		}
	}
}
