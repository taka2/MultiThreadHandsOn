package handson2;

/**
 * ���b�N�p�̃v���C�x�[�g�����o��p�ӂ��邱�ƂŁA
 * �O������擾����郍�b�N�Ƌ������Ȃ����Ƃ������T���v���B
 */
public class PrivateLockObject {
	public static void main(String args[]) {
		final SharedObject sharedObject = new SharedObject();

		// �O�����烍�b�N���擾����X���b�h
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

		// �N���X�����Ń��b�N���擾���郁�\�b�h���ĂԃX���b�h
		// �O������擾���ꂽ���b�N�Ɋւ�炸���������
		new Thread(new Runnable() {
			public void run() {
				sharedObject.method1();
			}
		}).start();
	}

	static class SharedObject {
		private Object lockObject = new Object();
		public void method1() {
			synchronized(lockObject) {
				System.out.println("method1 called.");
			}
		}
	}
}
