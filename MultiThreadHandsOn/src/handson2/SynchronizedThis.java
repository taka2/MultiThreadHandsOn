package handson2;

/**
 * this�ɑ΂��郍�b�N�̎擾�́A�O��������\�Ȃ��߁A
 * synchronized(this)�͔����������悢���Ƃ������T���v���B
 */
public class SynchronizedThis {
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
		// �O������擾���ꂽ���b�N����������܂ŏ������҂������
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
