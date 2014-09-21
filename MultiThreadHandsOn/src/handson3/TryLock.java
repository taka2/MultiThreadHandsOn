package handson3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TryLock�Ńf�b�h���b�N��h���T���v��
 * SimpleDeadlock.java��synchronized��tryLock�ɒu������
 */
public class TryLock {
	// �����p�X���[�v����
	private static final int SLEEP_TIME = 1000;
	// ���b�N�擾�ł��Ȃ������ꍇ�̃��g���C��
	private static final int RETRY_COUNT = 3;

	public static void main(String[] args) {
		final ReentrantLock left = new ReentrantLock();
		final ReentrantLock right = new ReentrantLock();
		
		// left -> right�̏��Ƀ��b�N���擾����^�X�N
		Runnable leftRightTask = new Runnable() {
			public void run() {
				for(int i=0; i<RETRY_COUNT; i++) {
					System.out.println(Thread.currentThread().getName() + ": " + i);
					if(left.tryLock()) {
						try {
							Thread.sleep(SLEEP_TIME);
							
							if(right.tryLock()) {
								try {
									System.out.println("leftRight");
									break;
								} finally {
									right.unlock();
								}
							} else {
								System.out.println(Thread.currentThread().getName() + ": Lock right failed.");
							}
						} catch(InterruptedException e) {
							// �������Ȃ�
						} finally {
							left.unlock();
						}
					} else {
						System.out.println(Thread.currentThread().getName() + ": Lock left failed.");
					}
				}
			}
		};

		// right -> left�̏��Ƀ��b�N���擾����^�X�N
		Runnable rightLeftTask = new Runnable() {
			public void run() {
				for(int i=0; i<RETRY_COUNT; i++) {
					System.out.println(Thread.currentThread().getName() + ": " + i);
					if(right.tryLock()) {
						try {
							Thread.sleep(SLEEP_TIME);
							
							if(left.tryLock()) {
								try {
									System.out.println("rightLeft");
									break;
								} finally {
									left.unlock();
								}
							} else {
								System.out.println(Thread.currentThread().getName() + ": Lock left failed.");
							}
						} catch(InterruptedException e) {
							// �������Ȃ�
						} finally {
							right.unlock();
						}
					} else {
						System.out.println(Thread.currentThread().getName() + ": Lock right failed.");
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
