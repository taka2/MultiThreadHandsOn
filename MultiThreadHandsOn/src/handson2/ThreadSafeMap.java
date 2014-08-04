package handson2;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadSafeMap {
	private static final int NUM_DATA = 10000;
	private static final int NUM_LOOP = 1000;
	public static void main(String args[]) {
		final Map<Integer, Integer> map = new ConcurrentHashMap<Integer, Integer>();
		
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
