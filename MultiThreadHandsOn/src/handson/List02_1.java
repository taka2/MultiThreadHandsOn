package handson;

/**
 * �x���������N���X
 * �X���b�h�Z�[�t����Ȃ���
 */
public class List02_1 {
	public static void main(String[] args) {
		// �X���b�h�Ԃŋ��L����I�u�W�F�N�g
		final LazyInitRace sharedObject = new LazyInitRace();

		Runnable r = new Runnable() {
			public void run() {
				ExpensiveObject obj = sharedObject.getInstance();
				System.out.println(obj.hashCode());
			}
		};
		// �X���b�h1�J�n
		new Thread(r).start();
		// �X���b�h2�J�n
		new Thread(r).start();
	}

	/**
	 * �X���b�h�Z�[�t�łȂ��x���������N���X
	 */
	private static class LazyInitRace {
		private ExpensiveObject instance = null;
		
		public ExpensiveObject getInstance() {
			if(instance == null)
				instance = new ExpensiveObject();
			return instance;
		}
	}

	/**
	 * �������Ɏ��Ԃ̂�����i�Ƃ���Ă���j�I�u�W�F�N�g
	 */
	private static class ExpensiveObject {
		public ExpensiveObject() {
			try {
				Thread.sleep(1000);
			} catch(InterruptedException e) {
				// �������Ȃ�
			}
		}
	}
}
