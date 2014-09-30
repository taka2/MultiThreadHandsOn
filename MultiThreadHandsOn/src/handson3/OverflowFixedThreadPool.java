package handson3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * �Œ�X���b�h�v�[�����ȏ�̃^�X�N�𓊓�������ǂ��Ȃ邩�𒲂ׂ�T���v��
 * ���\���ׂ����Ă���肾���ǁA�W�X�ƃL���[�C���O���ė����Ȃ��B
 */
public class OverflowFixedThreadPool {
	// �X���b�h�v�[����
	private static final int NUM_THREADS = 1;
	// �^�X�N��
	private static final int NUM_TASKS = 10000000;
	// �^�X�N�̎��s���ԁi�X���[�v���ԁj[ms]
	private static final int TASK_EXECUTION_TIME = 600000;

	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
		for(int i=1; i<=NUM_TASKS; i++) {
			executor.submit(new SleepTask());
		}
		System.out.println("done");
	}

	/**
	 * �w��b���X���[�v����^�X�N
	 */
	private static class SleepTask implements Runnable {
		public void run() {
			try {
				Thread.sleep(TASK_EXECUTION_TIME);
			} catch(InterruptedException e) {
				// �������Ȃ��B
			}
		}	
	}
}
