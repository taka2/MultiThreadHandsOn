package handson2;

import java.util.Date;
import java.util.TimerTask;

public class MyTimerTask extends TimerTask {
	private static final int[] waitTime = {1000, 3000, 10, 10, 10};
	private int counter = 0;
	@Override
	public void run() {
		System.out.println("startTime: " + new Date());

		// ‰½‚©ˆ—
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
