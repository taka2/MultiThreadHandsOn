package handson3;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ConcurrentLinkedQueue�Ɠ���������LinkedList�̃p�t�H�[�}���X���r����T���v��
 */
public class ListPerformance {
	// �f�[�^��
	private static final int NUM_DATA = 2000000;
	// �v�Z��
	private static final int CALC_AMOUNT = 200;

	// ���L�I�u�W�F�N�g
	private static final Queue<Integer> queue = new LinkedList<Integer>();
	public static void main(String[] args) {
		// �X���b�h����ς��Ȃ���^�X�N�����s����i�^�X�N���͒萔�Őݒ�j
		int[] NUM_THREADS = {1, 2, 3, 4, 5, 10, 20, 50, 100};
		for(int numThreads : NUM_THREADS) {
			long executionTime = executeTask(numThreads);
			System.out.printf("numThreads = %3d, executionTime = %5d[ms]\n", numThreads, executionTime);
		}
	}

	// �L���[�ɓ������f�[�^����������^�X�N
	private static class DequeueTask implements Runnable {
		public void run() {
			while(true) {
				// �L���[����f�[�^�̎��o��
				Integer data = null;
				synchronized(queue) {
					data = queue.poll();
				}
				if(data == null) {
					// �L���[�T�C�Y���[���ɂȂ�����I��
					break;
				} else {
					// �Ȃ�炩�̌v�Z
					for(int i=0; i<CALC_AMOUNT; i++) {
						data = data + 1;
					}
				}
			}
		}
	}

	// �X���b�h�����w�肵�ď��������s
	private static long executeTask(int numThreads) {
		// �L���[�Ƀf�[�^�𓊓�
		synchronized(queue) {
			queue.clear();
			for(int i=0; i<NUM_DATA; i++) {
				queue.add(i);
			}
		}

		long startTime = System.currentTimeMillis();

		// �L���[�ɓ������f�[�^����������^�X�N�����s
		ExecutorService service = Executors.newFixedThreadPool(numThreads);
		for(int i=0; i<numThreads; i++) {
			service.submit(new DequeueTask());
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
