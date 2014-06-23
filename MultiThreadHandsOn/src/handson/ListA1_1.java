package handson;

/**
 * Object#waitとThread#sleepの違いを検証するソース
 * Object#wait版
 */
public class ListA1_1 {
	private static final int NUM_LOOP = 20;

	public static void main(String[] args) {
		final Object monitor = new Object();

		Runnable r = new Runnable() {
			public void run() {
				try {
					for(int i=0; i<NUM_LOOP; i++) {
						synchronized(monitor) {
							System.out.println(Thread.currentThread().getName());
							
							// ウエイトして別スレッドに処理を譲る
							monitor.wait(1);
						}
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
}
