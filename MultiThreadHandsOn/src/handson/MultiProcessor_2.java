package handson;

import java.util.ArrayList;
import java.util.List;

public class MultiProcessor_2 {

	// �v�Z��
	private static final int NUM_CALCULATION = 10;

	public static void main(String[] args) {
		List<Thread> threadList = new ArrayList<Thread>();
		for(int i=0; i<NUM_CALCULATION; i++) {
			Runnable r = new Runnable() {
				public void run() {
					long sum = 0;
					for(int j=0; j<1000000000; j++) {
						sum += j;
					}
					System.out.println(sum);
				}
			};
			// �X���b�h1�J�n
			Thread t = new Thread(r);
			threadList.add(t);
			t.start();
		}
		
		// �S�ẴX���b�h�̊�����҂�
		final int threadListSize = threadList.size();
		for(int i=0; i<threadListSize; i++) {
			try {
				threadList.get(i).join();
			} catch(InterruptedException e) {
				// �������Ȃ�
			}
		}
	}
}
