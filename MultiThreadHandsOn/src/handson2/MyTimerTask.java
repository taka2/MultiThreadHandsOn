package handson2;

import java.util.Date;
import java.util.TimerTask;

/**
 * Timerのサンプル、および、ScheduledThreadPoolのサンプルから利用されるタスク
 * タスクの開始時に日時を表示し、規定時間スリープする。
 * 1～5回目のスリープ時間はそれぞれ、1秒、3秒、10ミリ秒、10ミリ秒、10ミリ秒
 * 6回目の実行ではRuntimeExceptionをスローする。
 */
public class MyTimerTask extends TimerTask {
	private static final int[] waitTime = {1000, 3000, 10, 10, 10};
	private int counter = 0;
	@Override
	public void run() {
		System.out.println("startTime: " + new Date());

		// 何か処理
		if(counter >= waitTime.length) {
			throw new RuntimeException();
		} else {
			try {
				Thread.sleep(waitTime[counter]);
				counter++;
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
