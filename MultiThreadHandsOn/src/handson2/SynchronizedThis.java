package handson2;

/**
 * thisに対するロックの取得は、外部からも可能なため、
 * synchronized(this)は避けた方がよいことを示すサンプル。
 */
public class SynchronizedThis {
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
		// 外部から取得されたロックが解放されるまで処理が待たされる
		new Thread(new Runnable() {
			public void run() {
				sharedObject.method1();
			}
		}).start();
	}

	static class SharedObject {
		public void method1() {
			synchronized(this) {
				System.out.println("method1 called.");
			}
		}
	}
}
