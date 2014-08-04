package handson2;

public class SynchronizedStaticMethod {
	public static void main(String args[]) {
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
				// ‰½‚à‚µ‚È‚¢
			}
		}

		public synchronized static void method2() {
			try {
				System.out.println("method2 called.");
				Thread.sleep(3000);
			} catch(InterruptedException e) {
				// ‰½‚à‚µ‚È‚¢
			}
		}
	}
}
