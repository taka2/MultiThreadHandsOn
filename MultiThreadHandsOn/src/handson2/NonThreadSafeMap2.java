package handson2;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * iterate����Map�ɑ΂��ē��������s�����ƂŁA
 * ConcurrentModificationException���������Ȃ����Ƃ��m�F����T���v���B
 */
public class NonThreadSafeMap2 {
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
					synchronized(map) {
						Iterator<Integer> ite = map.keySet().iterator();
						while(ite.hasNext()) {
							ite.next();
						}
					}
				}
			}
		}).start();
	}
}
