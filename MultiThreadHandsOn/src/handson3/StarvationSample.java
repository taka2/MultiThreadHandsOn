package handson3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * �Q���Ԃ��Č�����T���v��
 */
public class StarvationSample {
	public static void main(String[] args) {
		final Object sharedObject = new Object();
		
		// ��U���b�N�����񂾂��x�Ɖ�����Ȃ��^�X�N
		Runnable infiniteLoopTask = new Runnable() {
			public void run() {
				synchronized(sharedObject) {
					while(true) {
					}
				}
			}
		};

		// ���b�N����낤�Ƃ��邪�A�擾�ł��Ȃ��^�X�N
		Runnable starvationTask = new Runnable() {
			public void run() {
				while(true) {
					synchronized(sharedObject) {
						System.out.println("starvationTask");
					}
				}
			}
		};
		
		// �������[�v�^�X�N��2�b��ɊJ�n
		ScheduledExecutorService scheduledService = Executors.newScheduledThreadPool(1);
		scheduledService.schedule(infiniteLoopTask, 2, TimeUnit.SECONDS);
		
		// �Q���ԃ^�X�N�͂����J�n
		ExecutorService service = Executors.newCachedThreadPool();
		service.submit(starvationTask);

		scheduledService.shutdown();
		service.shutdown();
	}
}
