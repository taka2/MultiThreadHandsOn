package handson;

/**
 * Object#wait��Thread#sleep�̈Ⴂ�����؂���\�[�X
 * Thread#sleep��
 */
public class ListA1_2 {
	private static final int NUM_LOOP = 20;

	public static void main(String[] args) {
		final Object monitor = new Object();

		Runnable r = new Runnable() {
			public void run() {
				try {
					for(int i=0; i<NUM_LOOP; i++) {
						synchronized(monitor) {
							System.out.println(Thread.currentThread().getName());
							
							// �w�莞�ԃE�G�C�g����̂�
							Thread.sleep(1);
						}
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
}
