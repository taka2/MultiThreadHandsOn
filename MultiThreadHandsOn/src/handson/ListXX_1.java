package handson;

/**
 * number��ready�̃Z�b�g�����ς�����0���v�����g���邱�Ƃ�����i�炵���j
 * �����ւ�(reordering)�Ƃ������ۂƂ̂��ƁB�������A�Č������B�B�B
 */
public class ListXX_1 {
	// �X���b�h�Ԃŋ��L����ϐ�
	private static boolean ready;
	private static int number;

	public static void main(String[] args) throws Exception {
		Runnable r1 = new Runnable() {
			public void run() {
				while (!ready)
					Thread.yield();
				System.out.println(number);
			}
		};
		Runnable r2 = new Runnable() {
			public void run() {
				number = 42;
				ready = true;
			}
		};
		
		// �X���b�h1�J�n
		new Thread(r1).start();
		// �X���b�h2�J�n
		new Thread(r2).start();
	}
}
