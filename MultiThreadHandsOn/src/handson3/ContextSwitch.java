package handson3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * �R���e�L�X�g�X�C�b�`�̃R�X�g�����邽�߂̃T���v���i�V���O���X���b�h�Łj
 */
public class ContextSwitch {
	// �v�Z��
	private static final int CALC_AMOUNT = 1000000;

	public static void main(String[] args) {
		// �����v�Z����^�X�N
		Runnable task = new Runnable() {
			public void run() {
				for(int i=0; i<CALC_AMOUNT; i++) {
					Math.pow(i, 2);
				}
			}
		};
		
		long startTime = System.currentTimeMillis();
		ExecutorService service = Executors.newSingleThreadExecutor();
		for(int i=0; i<1000; i++) {
			service.submit(task);
		}
		service.shutdown();
		try {
			service.awaitTermination(5, TimeUnit.SECONDS);
		} catch(InterruptedException e) {
			// �������Ȃ�
		}
		long endTime = System.currentTimeMillis();
		System.out.println("time = " + (endTime - startTime) + "[ms]");
	}
}
