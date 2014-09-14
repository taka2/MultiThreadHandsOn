package handson3;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ���C�u���b�N���Č�����T���v��
 */
public class LiveLockSample {
	// �X���b�h�v�[����
	private static final int FIXED_THREAD_POOL_COUNT = 10;
	
	public static void main(String[] args) {
		final Queue<Integer> queue = new ConcurrentLinkedQueue<Integer>();
		
		// �L���[�Ƀf�[�^�𓊓�����^�X�N
		Runnable enqueueTask = new Runnable() {
			int counter = 1;
			public void run() {
				queue.add(counter);
				counter++;
			}
		};

		// �L���[�ɓ������f�[�^����������^�X�N
		Runnable dequeueTask = new Runnable() {
			public void run() {
				while(true) {
					synchronized(queue) {
						if(queue.size() == 0) {
							continue;
						}

						// �L���[����f�[�^�̎��o���i�폜�͂��Ȃ��j
						Integer data = queue.peek();
						
						// �f�[�^���`�F�b�N
						if(data > 5) {
							System.out.println("Invalid data. data=" + data);
	
							// �G���[�ɂȂ����f�[�^�̓L���[����폜���Ȃ��Ń��g���C
							continue;
						}
						
						// �f�[�^������
						System.out.println("Process data. data=" + data);
						
						// �L���[����f�[�^���o�����폜
						queue.poll();
					}
				}
			}
		};
		
		// �L���[�Ƀf�[�^�𓊓�����^�X�N��1�b�Ԋu�Ŏ��s
		ScheduledExecutorService scheduledService = Executors.newScheduledThreadPool(1);
		scheduledService.scheduleAtFixedRate(enqueueTask, 0, 1, TimeUnit.SECONDS);
		
		// �L���[�ɓ������f�[�^����������^�X�N�����s
		ExecutorService service = Executors.newFixedThreadPool(FIXED_THREAD_POOL_COUNT);
		service.submit(dequeueTask);
	}
}
