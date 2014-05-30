package handson;

/**
 * 遅延初期化クラス
 * スレッドセーフ版
 */
public class List02_2 {
	public static void main(String[] args) {
		// スレッド間で共有するオブジェクト
		final LazyInitRace sharedObject = new LazyInitRace();

		Runnable r = new Runnable() {
			public void run() {
				ExpensiveObject obj = sharedObject.getInstance();
				System.out.println(obj.hashCode());
			}
		};
		// スレッド1開始
		new Thread(r).start();
		// スレッド2開始
		new Thread(r).start();
	}

	/**
	 * スレッドセーフな遅延初期化クラス
	 */
	private static class LazyInitRace {
		private ExpensiveObject instance = null;
		
		public ExpensiveObject getInstance() {
			synchronized(this) {
				if(instance == null)
					instance = new ExpensiveObject();
				return instance;
			}
		}
	}

	/**
	 * 初期化に時間のかかる（とされている）オブジェクト
	 */
	private static class ExpensiveObject {
		public ExpensiveObject() {
			try {
				Thread.sleep(1000);
			} catch(InterruptedException e) {
				// 何もしない
			}
		}
	}
}
