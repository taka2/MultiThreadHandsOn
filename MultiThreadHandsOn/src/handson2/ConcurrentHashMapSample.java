package handson2;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ConcurrentHashMapを使って、
 * iterateと同時に書き換えを行ったときに、反映するかどうかのテスト
 */
public class ConcurrentHashMapSample {
	private static final int NUM_DATA = 10000;
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
				System.out.println("map.size(iterator取得前) = " + map.size());
				Iterator<Integer> ite = map.keySet().iterator();
				System.out.println("map.size(iterator取得後) = " + map.size());
				int count = 0;
				while(ite.hasNext()) {
					ite.next();
					count++;
				}
				System.out.println("iteratorループカウント = " + count);
			}
		}).start();
	}
}
