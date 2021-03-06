package handson2;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 同期化しないMapに対して、putとiterateを同時に行った場合、
 * ConcurrentModificationExceptionが発生してしまうことを確認するサンプル。
 */
public class NonThreadSafeMap1 {
	private static final int NUM_DATA = 10000;
	private static final int NUM_LOOP = 1000;
	public static void main(String args[]) {
		final Map<Integer, Integer> map = Collections.synchronizedMap(new HashMap<Integer, Integer>());
		
		new Thread(new Runnable() {
			public void run() {
				for(int i=0; i<NUM_DATA; i++) {
					map.put(i, i);
				}
			}
		}).start();
		new Thread(new Runnable() {
			public void run() {
				for(int i=0; i<NUM_LOOP; i++) {
					Iterator<Integer> ite = map.keySet().iterator();
					while(ite.hasNext()) {
						ite.next();
					}
				}
			}
		}).start();
	}
}
