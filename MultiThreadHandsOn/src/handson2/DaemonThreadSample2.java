package handson2;

/**
 * デーモンスレッドを利用するサンプル
 */
public class DaemonThreadSample2 {

	public static void main(String[] args) {
		Runnable r = new Runnable() {
			public void run() {
				try {
					for(int i=0; i<5; i++) {
						System.out.println("i = " + i);
						Thread.sleep(100);
					}
				} catch(InterruptedException e) {
					// 何もしない
				}
			}
		};
		Thread t = new Thread(r);
		t.setDaemon(true);
		t.start();
	}

}
