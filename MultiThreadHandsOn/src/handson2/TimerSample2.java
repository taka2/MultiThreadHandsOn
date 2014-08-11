package handson2;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/** 
 * Timerを固定頻度実行するサンプル
 */
public class TimerSample2 {

	public static void main(String[] args) {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				try {
					// 何か処理
					Thread.sleep(3000);
					System.out.println(new Date());
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		timer.scheduleAtFixedRate(task, 0, 1000);
	}

}
