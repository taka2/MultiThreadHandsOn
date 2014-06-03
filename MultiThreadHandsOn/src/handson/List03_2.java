package handson;

/**
 * �X���b�h�Z�[�t�łȂ��ςȐ����N���X
 * �X���b�h�Z�[�t��
 */
public class List03_2 {
	private static final int NUM_LOOP = 1000;

	public static void main(String[] args) {
		// �X���b�h�Ԃŋ��L����I�u�W�F�N�g
		final MutableInteger sharedObject = new MutableInteger();

		Runnable r1 = new Runnable() {
			public void run() {
				sharedObject.set(100);
			}
		};
		Runnable r2 = new Runnable() {
			public void run() {
				int value = sharedObject.get();
				System.out.println((value == 100) + "" + value);
			}
		};
		
		for(int i=0; i<NUM_LOOP; i++) {
			sharedObject.set(1);
			// �X���b�h1�J�n
			Thread t1 = new Thread(r1);
			t1.start();
			// �X���b�h2�J�n
			Thread t2 = new Thread(r2);
			t2.start();
			
			try {
				// �����̃X���b�h���I���̂�҂����킹
				t1.join();
				t2.join();
			} catch(InterruptedException e) {
				// �������Ȃ�
			}
		}
	}

	/**
	 * �X���b�h�Z�[�t�ȉςȐ����N���X
	 */
	private static class MutableInteger {
		private int value;
		
		public synchronized int get() {
			return value;
		}
		
		public synchronized void set(int value) {
			this.value = value;
		}
	}
}
