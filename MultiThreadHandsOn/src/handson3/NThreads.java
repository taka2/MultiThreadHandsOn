package handson3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * �X���b�h���ƃX���[�v�b�g�̊֌W�𒲂ׂ�v���O����
 */
public class NThreads {
	// �^�X�N���i���ׂ̑傫���j
	private static final int LOOP_COUNT = 5000;
	
	// �t�@�C����ǂݍ���ōs����Ԃ��^�X�N
	private static class FileReadTask implements Callable<Integer> {
		public Integer call() throws IOException {
			BufferedReader br = new BufferedReader(new FileReader("src/handson3/NThreads.java"));
			int numLines = 0;
			while(br.readLine() != null) {
				numLines++;
			}
			br.close();
			return numLines;
		}
	}
	public static void main(String[] args) throws Exception {		
		// �҂�
		System.out.println("�J�n");
		Thread.sleep(15000);

		// �X���b�h����ς��Ȃ���^�X�N�����s����i�^�X�N���͒萔�Őݒ�j
		int[] NUM_THREADS = {1, 2, 3, 4, 5, 10, 20, 50, 100};
		for(int numThreads : NUM_THREADS) {
			long executionTime = executeTask(new FileReadTask(), numThreads);
			System.out.printf("numThreads = %3d, executionTime = %5d[ms]\n", numThreads, executionTime);
		}
	}
	
	/**
	 * �w�肵���^�X�N���w�肵���X���b�h���Ŏ��s����
	 * @param task �^�X�N
	 * @param numThreads �X���b�h��
	 * @return ������������[ms]
	 */
	private static long executeTask(Callable<Integer> task, int numThreads) {
		// �J�n����
		long startTime = System.currentTimeMillis();

		List<Future<Integer>> taskResults = new ArrayList<Future<Integer>>();
		int loopCount = LOOP_COUNT / numThreads;
		for(int j=0; j<loopCount; j++) {
			ExecutorService service = Executors.newFixedThreadPool(numThreads);
	
			// �^�X�N�̎��s���˗�
			for(int i=0; i<numThreads; i++) {
				taskResults.add(service.submit(task));
			}
			
			for(Future<Integer> taskResult : taskResults) {
				try {
					// �^�X�N�̎��s���ʂ��擾
					taskResult.get();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
	
			service.shutdown();
		}

		// �I������
		long endTime = System.currentTimeMillis();
		return (endTime - startTime);
	}
}
