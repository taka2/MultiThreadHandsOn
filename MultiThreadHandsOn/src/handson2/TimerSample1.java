package handson2;

import java.util.Timer;

/** 
 * Timer���Œ�x�����s����T���v��
 */
public class TimerSample1 {

	public static void main(String[] args) {
		Timer timer = new Timer();
		timer.schedule(new MyTimerTask(), 0, 1000);
	}

}
