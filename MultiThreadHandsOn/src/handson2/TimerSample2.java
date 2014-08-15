package handson2;

import java.util.Timer;

/** 
 * Timerを固定頻度実行するサンプル
 */
public class TimerSample2 {

	public static void main(String[] args) {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new MyTimerTask(), 0, 1000);
	}

}
