package handson2;

/**
 * �f�[�����X���b�h�𗘗p����T���v��
 */
public class DaemonThreadSample2 {

	public static void main(String[] args) {
		Runnable r = new Runnable() {
			public void run() {
				try {
					for(int i=0; i<5; i++) {
						System.out.println("i = " + i);
						Thread.sleep(100);
					}
				} catch(InterruptedException e) {
					// �������Ȃ�
				}
			}
		};
		Thread t = new Thread(r);
		t.setDaemon(true);
		t.start();
	}

}
