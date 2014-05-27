package handson;

/**
 * numberとreadyのセット順が変えられて0をプリントすることがある（らしい）
 * 順序替え(reordering)という現象とのこと。しかし、再現せず。。。
 */
public class ListXX_1 {
	// スレッド間で共有する変数
	private static boolean ready;
	private static int number;

	public static void main(String[] args) throws Exception {
		Runnable r1 = new Runnable() {
			public void run() {
				while (!ready)
					Thread.yield();
				System.out.println(number);
			}
		};
		Runnable r2 = new Runnable() {
			public void run() {
				number = 42;
				ready = true;
			}
		};
		
		// スレッド1開始
		new Thread(r1).start();
		// スレッド2開始
		new Thread(r2).start();
	}
}
