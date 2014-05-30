package handson;

import java.util.Vector;

/**
 * �`�F�b�N�E�[���E�A�N�g����������
 * �X���b�h�Z�[�t��
 */
public class List05_2 {
	private static final int NUM_LIST_SIZE = 1000;
	private static final int NUM_LOOP = 100;

	public static void main(String[] args) {
		// �X���b�h�Ԃŋ��L����f�[�^
		final Vector<Integer> list = new Vector<Integer>();
		for(int i=0; i<NUM_LIST_SIZE; i++) {
			list.add(i);
		}

		Runnable r1 = new Runnable() {
			public void run() {
				for(int i=0; i<NUM_LOOP; i++) {
					Object lastObject = VectorHelper.getLast(list);
					System.out.println("lastObject = " + lastObject);
				}
			}
		};
		Runnable r2 = new Runnable() {
			public void run() {
				for(int i=0; i<NUM_LOOP; i++) {
					VectorHelper.deleteLast(list);
				}
			}
		};

		// �X���b�h1�J�n
		new Thread(r1).start();
		// �X���b�h2�J�n
		new Thread(r2).start();
	}

	/**
	 * �x�N�^�[�w���p�[
	 */
	private static class VectorHelper {
		public static <E> E getLast(Vector<E> list) {
			synchronized(list) {
				int lastIndex = list.size() - 1;
				return list.get(lastIndex);
			}
		}
		
		public static <E> void deleteLast(Vector<E> list) {
			synchronized(list) {
				int lastIndex = list.size() - 1;
				list.remove(lastIndex);
			}
		}
	}
}
