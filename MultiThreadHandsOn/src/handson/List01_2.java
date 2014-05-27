package handson;

/**
 * スレッドセーフでない順序数生成クラス
 * スレッドセーフ版
 */
public class List01_2 {
	private static final int NUM_LOOP = 500;

	public static void main(String[] args) {
		// スレッド間で共有するオブジェクト
		final UnsafeSequence seq = new UnsafeSequence();

		Runnable r = new Runnable() {
			public void run() {
				try {
					for(int i=0; i<NUM_LOOP; i++) {
						System.out.println(seq.getNext());
						Thread.sleep(10);
					}
				} catch(InterruptedException e) {
					// 何もしない
				}
			}
		};
		// スレッド1開始
		new Thread(r).start();
		// スレッド2開始
		new Thread(r).start();
	}

	/**
	 * スレッドセーフでない順序数生成クラス
	 */
	private static class UnsafeSequence {
		private int nextValue = 1;
		
		/* 重複のない数を返す */
		public synchronized int getNext() {
			return nextValue++;
		}
	}
}
