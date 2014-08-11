package handson2;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/** 
 * Timer‚ğŒÅ’è’x‰„Às‚·‚éƒTƒ“ƒvƒ‹
 */
public class TimerSample1 {

	public static void main(String[] args) {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				try {
					// ‰½‚©ˆ—
					Thread.sleep(3000);
					System.out.println(new Date());
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		timer.schedule(task, 0, 1000);
	}

}
