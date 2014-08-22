package handson2;

/**
 * static��synchronized���\�b�h�͓���������邱�Ƃ������T���v��
 */
public class SynchronizedStaticMethod {
	public static void main(String args[]) {
		// ��Ɏ��s���ꂽ���\�b�h�i�����̏ꍇ��method1�j�̏I����҂��āA
		// ���̃��\�b�h�����s�����B
		new Thread(new Runnable() {
			public void run() {
				SharedObject.method1();
			}
		}).start();
		new Thread(new Runnable() {
			public void run() {
				SharedObject.method2();
			}
		}).start();
	}
	
	static class SharedObject {
		public synchronized static void method1() {
			try {
				System.out.println("method1 called.");
				Thread.sleep(3000);
			} catch(InterruptedException e) {
				// �������Ȃ�
			}
		}

		public synchronized static void method2() {
			try {
				System.out.println("method2 called.");
				Thread.sleep(3000);
			} catch(InterruptedException e) {
				// �������Ȃ�
			}
		}
	}
}
