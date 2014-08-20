package handson2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

public class ExecutorShutdownSample1 {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		for(int i=1; i<=10; i++) {
			executor.submit(new MyTask(i));
		}
		executor.shutdown();
		
		// �V���b�g�_�E����͐V���ȃ^�X�N�͎󂯕t�����Ȃ�
		try {
			executor.submit(new MyTask(100));
		} catch(RejectedExecutionException e) {
			e.printStackTrace();
		}

		// �ő�5�b�V���b�g�_�E���̊�����҂�
		try {
			executor.awaitTermination(5, TimeUnit.SECONDS);
		} catch(Exception e) {
			e.printStackTrace();
		}

		System.out.println("done");
	}

	private static class MyTask implements Runnable {
		private int numLoop;
		public MyTask(int numLoop) {
			this.numLoop = numLoop;
		}

		public void run() {
			for(int i=1; i<=numLoop; i++) {
				try {
					System.out.println(Thread.currentThread().getName() + ":" + i);
					Thread.sleep(1000);
				} catch(InterruptedException e) {
					// �������Ȃ�
				}
			}
		}	
	}
}
