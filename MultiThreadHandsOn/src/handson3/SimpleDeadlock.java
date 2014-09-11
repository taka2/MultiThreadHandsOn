package handson3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ���b�N���f�b�h���b�N�̒P���ȃT���v��
 */
public class SimpleDeadlock {
	// �����p�X���[�v����
	private static final int SLEEP_TIME = 1000;

	public static void main(String[] args) {
		final Object left = new Object();
		final Object right = new Object();
		
		// left -> right�̏��Ƀ��b�N���擾����^�X�N
		Runnable leftRightTask = new Runnable() {
			public void run() {
				synchronized(left) {
					try {
						Thread.sleep(SLEEP_TIME);
					} catch(InterruptedException e) {
						// �������Ȃ�
					}
					
					synchronized(right) {
						System.out.println("leftRight");
					}
				}
			}
		};

		// right -> left�̏��Ƀ��b�N���擾����^�X�N
		Runnable rightLeftTask = new Runnable() {
			public void run() {
				synchronized(right) {
					try {
						Thread.sleep(SLEEP_TIME);
					} catch(InterruptedException e) {
						// �������Ȃ�
					}

					synchronized(left) {
						System.out.println("rightLeft");
					}
				}
			}
		};
		
		ExecutorService service = Executors.newCachedThreadPool();
		service.submit(leftRightTask);
		service.submit(rightLeftTask);

		service.shutdown();
	}
}
