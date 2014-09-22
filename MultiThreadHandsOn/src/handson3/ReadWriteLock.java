package handson3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**
 * ReadWriteLock���g���ă��b�N���ׂ������䂷��T���v��
 */
public class ReadWriteLock {
	// ���b�N�擾�ł��Ȃ������ꍇ�̃��g���C��
	private static final int LOOP_COUNT = 3;

	public static void main(String[] args) {
		final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
		
		// �ǂݍ��݃��b�N���擾����^�X�N
		Runnable readLockTask = new Runnable() {
			public void run() {
				ThreadLocal<ReadLock> threadLocal = new ThreadLocal<ReadLock>();
				for(int i=0; i<LOOP_COUNT; i++) {
					ReadLock readLock = null;
					if(threadLocal.get() == null) {
						readLock = lock.readLock();
						threadLocal.set(readLock);
					} else {
						readLock = threadLocal.get();
					}

					readLock.lock();
					try {
						System.out.println(Thread.currentThread().getName() + ": " + i);
					} finally {
						readLock.unlock();
					}
				}
			}
		};

		// �������݃��b�N���擾����^�X�N
		Runnable writeLockTask = new Runnable() {
			public void run() {
				ThreadLocal<WriteLock> threadLocal = new ThreadLocal<WriteLock>();
				for(int i=0; i<LOOP_COUNT; i++) {
					WriteLock writeLock = null;
					if(threadLocal.get() == null) {
						writeLock = lock.writeLock();
						threadLocal.set(writeLock);
					} else {
						writeLock = threadLocal.get();
					}

					writeLock.lock();
					try {
						System.out.println(Thread.currentThread().getName() + ": " + i);
					} finally {
						writeLock.unlock();
					}
				}
			}
		};

		ExecutorService service = Executors.newCachedThreadPool();
		
		try {
			System.out.println("Read vs Read");
			Future<?> future1 = service.submit(readLockTask);
			Future<?> future2 = service.submit(readLockTask);
			future1.get();
			future2.get();
			
			System.out.println("Read vs Write");
			Future<?> future3 = service.submit(readLockTask);
			Future<?> future4 = service.submit(writeLockTask);
			future3.get();
			future4.get();
			
			System.out.println("Write vs Write");
			Future<?> future5 = service.submit(writeLockTask);
			Future<?> future6 = service.submit(writeLockTask);
			future5.get();
			future6.get();
		} catch(Exception e) {
			e.printStackTrace();
		}
		service.shutdown();
	}
}
