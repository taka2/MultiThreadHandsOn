package handson2;

import java.util.Timer;

/** 
 * Timer���Œ�p�x���s����T���v��
 */
public class TimerSample2 {

	public static void main(String[] args) {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new MyTimerTask(), 0, 1000);
	}

}
