package handson2;

/**
 * ロック用のプライベートメンバを用意することで、
 * 外部から取得されるロックと競合しないことを示すサンプル。
 */
public class PrivateLockObject {
	public static void main(String args[]) {
		final SharedObject sharedObject = new SharedObject();

		// 外部からロックを取得するスレッド
		new Thread(new Runnable() {
			public void run() {
				// 外部からインスタンスのロックを取得
				synchronized(sharedObject) {
					try {
						Thread.sleep(3000);
					} catch(InterruptedException e) {
						// 何もしない
					}
				}
			}
		}).start();

		// クラス内部でロックを取得するメソッドを呼ぶスレッド
		// 外部から取得されたロックに関わらず処理される
		new Thread(new Runnable() {
			public void run() {
				sharedObject.method1();
			}
		}).start();
	}

	static class SharedObject {
		private Object lockObject = new Object();
		public void method1() {
			synchronized(lockObject) {
				System.out.println("method1 called.");
			}
		}
	}
}
