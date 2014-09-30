package handson3;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Runnable����Ȃ��āACallable�����������^�X�N�����s�O��
 * shutdownNow��reject������ǂ��Ȃ邩�𒲂ׂ�T���v��
 */
public class RejectCallableTask {
	// �X���b�h�v�[����
	private static final int NUM_THREADS = 10;
	// �^�X�N��
	private static final int NUM_TASKS = 100;

	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
		for(int i=1; i<=NUM_TASKS; i++) {
			executor.submit(new GetCurrentDateTask());
		}
		List<Runnable> runnableList = executor.shutdownNow();
		for(Runnable r : runnableList) {
			System.out.println(r.getClass().getName());
		}

		// �ő�5�b�V���b�g�_�E���̊�����҂�
		try {
			executor.awaitTermination(5, TimeUnit.SECONDS);
		} catch(Exception e) {
			e.printStackTrace();
		}

		System.out.println("done");
	}

	/**
	 * ���ݎ�����Ԃ��^�X�N
	 */
	private static class GetCurrentDateTask implements Callable<Date> {
		public Date call() {
			return new Date();
		}	
	}
}
