package handson3;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ���b�N�X�g���C�s���O�̃T���v��
 */
public class LockStriping {
	// �f�[�^��
	private static final int NUM_DATA = 1000000;
	// �v�Z��
	private static final int CALC_AMOUNT = 10000;

	// ���L�I�u�W�F�N�g
	
	public static void main(String[] args) {
		// �X���b�h���A�X�g���C�v����ς��Ȃ���^�X�N�����s����i�^�X�N���͒萔�Őݒ�j
		int[] NUM_THREADS = {4, 8, 16};
		int[] NUM_STRIPES = {1, 4, 16, 32, 64, 128};
		for(int numThreads : NUM_THREADS) {
			for(int numStripes : NUM_STRIPES) {
				final ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<Integer, Integer>(NUM_DATA, 0.75f, numStripes);
				for(int i=0; i<NUM_DATA; i++) {
					map.put(i, i);
				}
				long executionTime = executeTask(map, numThreads);
				System.out.printf("numThreads = %3d, numStripes = %3d, executionTime = %5d[ms]\n", numThreads, numStripes, executionTime);
				System.gc();
			}
		}
	}

	// �L���[�ɓ������f�[�^����������^�X�N
	private static class DequeueTask implements Runnable {
		private Map<Integer, Integer> map;
		private int threadNumber;

		public DequeueTask(Map<Integer, Integer> map, Integer threadNumber) {
			this.map = map;
			this.threadNumber = threadNumber;
		}

		public void run() {
			for(int i=0; i<CALC_AMOUNT; i++) {
				int index = threadNumber * CALC_AMOUNT + i;
				map.get(index);
			}
		}
	}

	// �X���b�h�����w�肵�ď��������s
	private static long executeTask(Map<Integer, Integer> map, int numThreads) {
		long startTime = System.currentTimeMillis();

		// �L���[�ɓ������f�[�^����������^�X�N�����s
		ExecutorService service = Executors.newFixedThreadPool(numThreads);
		for(int i=0; i<numThreads; i++) {
			service.submit(new DequeueTask(map, i));
		}
		
		service.shutdown();
		try {
			service.awaitTermination(60, TimeUnit.SECONDS);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		return (endTime - startTime);
	}
}
