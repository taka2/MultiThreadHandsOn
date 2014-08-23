package handson2;

/**
 * シャットダウンフックのサンプル
 */
public class ShutdownHookSample {
	public static void main(String[] args) {
		// シャットダウンフックを定義
		Runnable shutdownHookTask = new Runnable() {
			public void run() {
				System.out.println("shutdown hook");
			}
		};

		// シャットダウンフックを追加
		Runtime.getRuntime().addShutdownHook(new Thread(shutdownHookTask));
	}
}
