package handson2;

import java.util.Timer;

/** 
 * Timerを固定遅延実行するサンプル
 */
public class TimerSample1 {

	public static void main(String[] args) {
		Timer timer = new Timer();
		timer.schedule(new MyTimerTask(), 0, 1000);
	}

}
