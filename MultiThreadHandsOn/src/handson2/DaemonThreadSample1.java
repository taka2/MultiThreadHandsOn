package handson2;

public class DaemonThreadSample1 {

	public static void main(String[] args) {
		Runnable r = new Runnable() {
			public void run() {
				try {
					for(int i=0; i<5; i++) {
						System.out.println("i = " + i);
						Thread.sleep(100);
					}
				} catch(InterruptedException e) {
					// ‰½‚à‚µ‚È‚¢
				}
			}
		};
		Thread t = new Thread(r);
		t.start();
	}

}
