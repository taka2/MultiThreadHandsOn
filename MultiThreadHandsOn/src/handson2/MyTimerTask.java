package handson2;

import java.util.Date;
import java.util.TimerTask;

/**
 * Timer�̃T���v���A����сAScheduledThreadPool�̃T���v�����痘�p�����^�X�N
 * �^�X�N�̊J�n���ɓ�����\�����A�K�莞�ԃX���[�v����B
 * 1�`5��ڂ̃X���[�v���Ԃ͂��ꂼ��A1�b�A3�b�A10�~���b�A10�~���b�A10�~���b
 * 6��ڂ̎��s�ł�RuntimeException���X���[����B
 */
public class MyTimerTask extends TimerTask {
	private static final int[] waitTime = {1000, 3000, 10, 10, 10};
	private int counter = 0;
	@Override
	public void run() {
		System.out.println("startTime: " + new Date());

		// ��������
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
