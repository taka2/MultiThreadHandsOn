package handson3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * synchronized�œ��������J�E���^�ƁA�A�g�~�b�N�ϐ����g�����J�E���^�̔�r���s���T���v��
 */
public class AtomicVariable {
	// �^�X�N���i���ׂ̑傫���j
	private static final int LOOP_COUNT = 100000;
	
	// synchronized���g���ĕ��s�Ő��𐔂���N���X
	private static class Counter {
		private int counter;
		public synchronized int increment() {
			counter++;
			return counter;
		}
		public void resetCounter() {
			this.counter = 0;
		}
	}
	public static void main(String[] args) {
		final Counter counter = new Counter();

		// synchronized���g���ĕ��s�Ő��𐔂���^�X�N
		Runnable counterTask = new Runnable() {
			public void run() {
				for(int i=0; i<LOOP_COUNT; i++) {
					counter.increment();
				}
			}
		};
		
		final AtomicInteger atomicCounter = new AtomicInteger();

		// �A�g�~�b�N�ϐ����g���Đ��𐔂���^�X�N
		Runnable atomicCounterTask = new Runnable() {
			public void run() {
				for(int i=0; i<LOOP_COUNT; i++) {
					atomicCounter.incrementAndGet();
				}
			}
		};

		// �X���b�h����ς��Ȃ���^�X�N�����s����i�^�X�N���͒萔�Őݒ�j
		int[] NUM_THREADS = {1, 2, 3, 4, 5, 10, 20, 50, 100};
		System.out.println("���b�N�I�u�W�F�N�g���g���Đ��𐔂���^�X�N");
		for(int numThreads : NUM_THREADS) {
			long executionTime = executeTask(counterTask, numThreads);
			System.out.printf("numThreads = %3d, executionTime = %5d[ms]\n", numThreads, executionTime);
			counter.resetCounter();
		}
		
		System.out.println("�A�g�~�b�N�ϐ����g���Đ��𐔂���^�X�N");
		for(int numThreads : NUM_THREADS) {
			long executionTime = executeTask(atomicCounterTask, numThreads);
			System.out.printf("numThreads = %3d, executionTime = %5d[ms]\n", numThreads, executionTime);
			atomicCounter.set(0);
		}
	}
	
	/**
	 * �w�肵���^�X�N���w�肵���X���b�h���Ŏ��s����
	 * @param task �^�X�N
	 * @param numThreads �X���b�h��
	 * @return ������������[ms]
	 */
	private static long executeTask(Runnable task, int numThreads) {
		// �J�n����
		long startTime = System.currentTimeMillis();

		ExecutorService service = Executors.newFixedThreadPool(numThreads);

		List<Future<?>> taskResults = new ArrayList<Future<?>>();
		// �^�X�N�̎��s���˗�
		for(int i=0; i<numThreads; i++) {
			taskResults.add(service.submit(task));
		}
		
		// �^�X�N�̎��s������҂����킹
		for(Future<?> taskResult : taskResults) {
			try {
				taskResult.get();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

		service.shutdown();

		// �I������
		long endTime = System.currentTimeMillis();
		return (endTime - startTime);
	}
}
