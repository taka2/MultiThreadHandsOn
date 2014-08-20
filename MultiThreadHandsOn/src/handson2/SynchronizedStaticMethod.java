package handson2;

/**
 * staticなsynchronizedメソッドは同期化されることを示すサンプル
 */
public class SynchronizedStaticMethod {
	public static void main(String args[]) {
		// 先に実行されたメソッド（多くの場合はmethod1）の終了を待って、
		// 次のメソッドが実行される。
		new Thread(new Runnable() {
			public void run() {
				SharedObject.method1();
			}
		}).start();
		new Thread(new Runnable() {
			public void run() {
				SharedObject.method2();
			}
		}).start();
	}
	
	static class SharedObject {
		public synchronized static void method1() {
			try {
				System.out.println("method1 called.");
				Thread.sleep(3000);
			} catch(InterruptedException e) {
				// 何もしない
			}
		}

		public synchronized static void method2() {
			try {
				System.out.println("method2 called.");
				Thread.sleep(3000);
			} catch(InterruptedException e) {
				// 何もしない
			}
		}
	}
}
