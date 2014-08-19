package handson2;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TimeLimitTask {
	// �������ԁi�b�j
	private static final int TIMEOUT = 5;

	public static void main(String[] args) {
		Runnable r = new Runnable() {
			public void run() {
				long i=1;
				while(true) {
					printFizzBuzz(i);
					i++;
					try {
						Thread.sleep(0);
					} catch(InterruptedException e) {
						// �C���^���v�g���ꂽ�烋�[�v�I��
						break;
					}
				}
			}

			private void printFizzBuzz(long l) {
				if(l % 3 == 0) {
					System.out.print("Fizz");
				} else if(l % 5 == 0) {
					System.out.print("Buzz");
				} else {
					System.out.print(l);
				}
				System.out.println();
			}
		};

		// �X���b�h�J�n
		ExecutorService executor = Executors.newCachedThreadPool();
		Future<?> task = executor.submit(r);

		try {
			task.get(TIMEOUT, TimeUnit.SECONDS);
		} catch(TimeoutException e) {
			// �^�X�N��finally�u���b�N�ŃL�����Z�������
		} catch(InterruptedException e) {
			// �^�X�N��finally�u���b�N�ŃL�����Z�������
		} catch(ExecutionException e) {
			// �^�X�N���œ�����ꂽ��O�F�ē�����
			throw new RuntimeException(e);
		} finally {
			// �^�X�N�����łɊ������Ă����疳�Q
			task.cancel(true);	// ���s���Ȃ�C���^���v�g����
		}

		executor.shutdown();
	}
}
