package handson2;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * UncaughtExceptionHandlerの動作を見るサンプル
 */
public class UncaughtExceptionHandlerSample {
	public static void main(String[] args) {
		// UncaughtExceptionHandlerを定義
		UncaughtExceptionHandler ueh = new UncaughtExceptionHandler() {
			public void uncaughtException(Thread t, Throwable e) {
				// 通常、標準エラー出力に出るところ、標準出力に表示するよう変更
				e.printStackTrace(System.out);
			}
		};

		// カレントスレッドにHandlerをセット
		Thread.setDefaultUncaughtExceptionHandler(ueh);
		
		// ランタイム例外を（意図的に）スロー
		throw new RuntimeException("Runtime Exception!");
	}
}
