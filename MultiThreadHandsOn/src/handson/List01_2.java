package handson;

/**
 * �X���b�h�Z�[�t�łȂ������������N���X
 * �X���b�h�Z�[�t��
 */
public class List01_2 {
	private static final int NUM_LOOP = 500;

	public static void main(String[] args) {
		// �X���b�h�Ԃŋ��L����I�u�W�F�N�g
		final UnsafeSequence seq = new UnsafeSequence();

		Runnable r = new Runnable() {
			public void run() {
				try {
					for(int i=0; i<NUM_LOOP; i++) {
						System.out.println(seq.getNext());
						Thread.sleep(10);
					}
				} catch(InterruptedException e) {
					// �������Ȃ�
				}
			}
		};
		// �X���b�h1�J�n
		new Thread(r).start();
		// �X���b�h2�J�n
		new Thread(r).start();
	}

	/**
	 * �X���b�h�Z�[�t�łȂ������������N���X
	 */
	private static class UnsafeSequence {
		private int nextValue = 1;
		
		/* �d���̂Ȃ�����Ԃ� */
		public synchronized int getNext() {
			return nextValue++;
		}
	}
}
