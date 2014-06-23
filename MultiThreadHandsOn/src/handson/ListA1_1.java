package handson;

/**
 * Object#wait��Thread#sleep�̈Ⴂ�����؂���\�[�X
 * Object#wait��
 */
public class ListA1_1 {
	private static final int NUM_LOOP = 20;

	public static void main(String[] args) {
		final Object monitor = new Object();

		Runnable r = new Runnable() {
			public void run() {
				try {
					for(int i=0; i<NUM_LOOP; i++) {
						synchronized(monitor) {
							System.out.println(Thread.currentThread().getName());
							
							// �E�G�C�g���ĕʃX���b�h�ɏ���������
							monitor.wait(1);
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
