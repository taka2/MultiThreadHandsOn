package handson2;

/**
 * �V���b�g�_�E���t�b�N�̃T���v��
 */
public class ShutdownHookSample {
	public static void main(String[] args) {
		// �V���b�g�_�E���t�b�N���`
		Runnable shutdownHookTask = new Runnable() {
			public void run() {
				System.out.println("shutdown hook");
			}
		};

		// �V���b�g�_�E���t�b�N��ǉ�
		Runtime.getRuntime().addShutdownHook(new Thread(shutdownHookTask));
	}
}
