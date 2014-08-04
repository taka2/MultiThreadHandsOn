package handson2;

public class SynchronizedThis {
	public static void main(String args[]) {
		final SharedObject sharedObject = new SharedObject();
		
		new Thread(new Runnable() {
			public void run() {
				// �O������C���X�^���X�̃��b�N���擾
				synchronized(sharedObject) {
					try {
						Thread.sleep(3000);
					} catch(InterruptedException e) {
						// �������Ȃ�
					}
				}
			}
		}).start();
		new Thread(new Runnable() {
			public void run() {
				sharedObject.method1();
			}
		}).start();
	}

	static class SharedObject {
		public void method1() {
			synchronized(this) {
				System.out.println("method1 called.");
			}
		}
	}
}
