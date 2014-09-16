package handson3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * ���b�N�̗��x������������T���v��
 */
public class ShrinkLockScope {
	public static void main(String[] args) {
		final List<Integer> sharedObject = new ArrayList<Integer>();

		// ���b�N�X�R�[�v���傫���^�X�N
		Runnable task1 = new Runnable() {
			public void run() {
				synchronized(sharedObject) {
					// ��������
					doSomething();
					
					// ���L�I�u�W�F�N�g�ɑ΂��鏈��
					sharedObject.add(1);
					
					System.out.println("Done.");
				}
			}
		};
		// ���b�N�X�R�[�v�������������^�X�N
		Runnable task2 = new Runnable() {
			public void run() {
				// ��������
				doSomething();
				
				synchronized(sharedObject) {	
					// ���L�I�u�W�F�N�g�ɑ΂��鏈��
					sharedObject.add(1);
				}
				
				System.out.println("Done.");
			}
		};
		ExecutorService service = Executors.newCachedThreadPool();
		System.out.println("���b�N�X�R�[�v���傫���^�X�N��2���s");
		Future<?> future1 = service.submit(task1);
		Future<?> future2 = service.submit(task1);
		
		// �^�X�N�̊�����҂B
		try {
			future1.get();
			future2.get();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("���b�N�X�R�[�v�������������^�X�N��2���s");
		service.submit(task2);
		service.submit(task2);
		service.shutdown();
		
		
	}
	
	private static void doSomething() {
		try {
			Thread.sleep(2000);
		} catch(InterruptedException e) {
			// �������Ȃ�
		}
	}
}
