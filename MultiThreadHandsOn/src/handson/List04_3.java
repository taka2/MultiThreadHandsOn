package handson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * プット・イフ・アブセントを実装する
 * スレッドセーフ版
 * 100回リストに追加してるから、リストのサイズは100になるはず
 */
public class List04_3 {
	private static final int NUM_LOOP = 100;

	public static void main(String[] args) {
		// スレッド間で共有するオブジェクト
		final ListHelper<Integer> sharedObject = new ListHelper<Integer>();

		Runnable r = new Runnable() {
			public void run() {
				for(int i=1; i<=NUM_LOOP; i++) {
					sharedObject.putIfAbsent(i);
				}
			}
		};

		// スレッド1開始
		Thread t1 = new Thread(r);
		t1.start();
		// スレッド2開始
		Thread t2 = new Thread(r);
		t2.start();
		
		try {
			// 両方のスレッドが終わるのを待ち合わせ
			t1.join();
			t2.join();
		} catch(InterruptedException e) {
			// 何もしない
		}

		// Listの数を表示(100になるはず)
		System.out.println(sharedObject.list.size());
	}

	/**
	 * リストヘルパー
	 */
	private static class ListHelper<E> {
		private List<E> list = Collections.synchronizedList(new ArrayList<E>());

		public boolean putIfAbsent(E x) {
			synchronized(list) {
				boolean absent = !list.contains(x);
				if(absent) {
					list.add(x);
				}
				return absent;
			}
		}
	}
}
