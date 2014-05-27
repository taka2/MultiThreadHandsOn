package handson;

import java.util.Vector;

/**
 * チェック・ゼン・アクトを実装する
 * スレッドセーフじゃない版
 */
public class List05_1 {
	private static final int NUM_LIST_SIZE = 1000;
	private static final int NUM_LOOP = 100;

	public static void main(String[] args) {
		// スレッド間で共有するデータ
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

		// スレッド1開始
		new Thread(r1).start();
		// スレッド2開始
		new Thread(r2).start();
	}

	/**
	 * リストヘルパー
	 */
	private static class VectorHelper {
		public static <E> E getLast(Vector<E> list) {
			int lastIndex = list.size() - 1;
			return list.get(lastIndex);
		}
		
		public static <E> void deleteLast(Vector<E> list) {
			int lastIndex = list.size() - 1;
			list.remove(lastIndex);
		}
	}
}
